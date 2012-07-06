package tim.server.http;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


import tim.server.Client;
import tim.server.Server;
import tim.server.http.pages.Index;


public class HTTPReader {
	private Method method = null;
	private String path = null, version = null, host = null;
	private int port;
	private ArrayList<HTTPVar> httpvars;
	private boolean proceed = false;
	private Client client = null;
	private OutputStream out = null;
	private InputStream in = null;
	public ArrayList<HTMLPage> pages = new ArrayList<HTMLPage>();
	public ArrayList<HTTPVar> cookievars = new ArrayList<HTTPVar>();
	public HTTPReader(Client c){
		httpvars = new ArrayList<HTTPVar>();
		client = c;
		pages.add(new Index());
		out = client.getOutputStream();
		in = client.getInputStream();
	}
	public HTMLPage findPage(String n){
		for(HTMLPage h: pages){
			if(h.getName().equals(n)){
				return h;
			}
		}
		return null;
	}
	public void read(){
		if(client!=null&&out!=null&&in!=null){
			while(proceed==false){
				try {
					String line = client.getIn().readLine();
					processLine(line);
				} catch (IOException e) {
					System.err.println("Could not read line");
				}
			}
		}
	}
	public void processLine(String line){
		if(!line.contains(":")){
			String[] header = line.split(" ");
			switch(header[0].toUpperCase()){
			case "GET":
				method = Method.GET;
				break;
			case "POST":
				method = Method.POST;
				break;
			default:
				proceed = true;
				respond();
				client.close();
				return;
			}
			path = header[1];
			if(path.equals("/")){ path = "/index.j"; }
			path = path.substring(1, path.length());
			if(method==Method.GET){ processGet(); }
			version = header[2];
		}else{
			String[] header2 = line.split(":");
			if(header2[0].toUpperCase().equals("HOST")){
				host = header2[1].trim();
				try{
					port = Integer.parseInt(header2[2].trim());
				}catch(NumberFormatException e){
					System.err.println("Could not get port");
				}
			}else if(header2[0].toUpperCase().equals("COOKIE")){
				parseCookies(header2[1]);
			}
		}
	}
	public String toString(){
		return "method = "+method+"\npath = "+path+"\nversion = "+version+
				"\nhost:port = "+host+":"+port;
	}
	public void respondFile(){
		int status = 200;
		String fileExt = null;
		long length = 0;
		File f = new File(Server.webRoot+"/"+path);
		if(!f.exists()){ 
			status = 404; 
		}else{
			length = f.length();
			String fileName = f.getName();
			fileExt = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
		}
		try {
			out.write((version+" "+status+" OK\r\n").getBytes());
		} catch (IOException e2) {
			System.err.println("Could not write initial header");
		}
		if(status==404){
			try {
				out.write(("\r\npage not found").getBytes());
			} catch (IOException e1) {
				System.err.println("Could not write headers to client");
			}
		}
		if(status==200){
			try {
				String toWrite = "Content-Type: "+getMimeType(fileExt)+";charset=UTF-8\r\nContent-Length: "+length+"\r\n\r\n";
				out.write((toWrite).getBytes());
				FileInputStream fstream = new FileInputStream(f);
				DataInputStream in = new DataInputStream(fstream);
				byte[] input = new byte[(int) length];
				in.read(input);
				out.write(input);
			} catch (FileNotFoundException e) {
				System.err.println("Could not read file "+f);
			} catch (IOException e) {
				System.err.println("Could not write file to client");
			}
		}
	}
	public String getMimeType(String fileExt){
		String type = "text/html";
		switch(fileExt){
		case "html": case "htm":
			type = "text/html";
			break;
		case "txt":
			type = "text/plain";
			break;
		case "jpeg": case "jpg": case "png":
			type = "image/jpeg";
			break;
		default:
			System.err.println("format "+fileExt+" not recognized");
		}
		return type;
	}
	public String cookieList(ArrayList<HTTPVar> c){
		String ret = "";
		if(c!=null&&c.size()>0){
			ret = "Set-Cookie: ";
			for(int i=0;i<c.size();i++){
				ret+= c.get(i).getName()+"="+c.get(i).getVar();
				if((i+1)<c.size()){
					ret+= "; ";
				}
			}
			ret+="\r\n";
		}
		return ret;
	}
	public void respond(){
		if(path.substring(path.length()-2,path.length()).equals(".j")){
			int status = 200;
			String cookies = "";
			if(findPage(path)==null){ 
				status = 404; 
			}else{
				if(cookievars.size()>0&&findPage(path)!=null){
					findPage(path).processCookies(cookievars);
				}
				if(httpvars.size()>0&&findPage(path)!=null){
					findPage(path).processVars(httpvars);
				}
				cookies = cookieList(findPage(path).getCookies());
			}
			String toWrite = version+" "+status+" OK\r\nContent-Type: text/html\r\n"+cookies+"\r\n";
			try{
				out.write((toWrite).getBytes());
				if(status==404){
					out.write(("page not found").getBytes());
				}
				if(status==200){
					out.write((findPage(path).getPage()).getBytes());
				}
			}catch(IOException e){
				System.err.println("Could not write to client");
			}
		}else{
			respondFile();
		}
	}
	public void processGet(){
		if(!path.contains("?")){ return; }
		String[] path2 = path.split("\\?");
		path = path2[0];
		if(path2.length<2){ return; }
		if(path2[1].contains("&")){
			String[] variables = path2[1].split("&");
			for(int i=0; i<variables.length; i++){
				if(variables[i].contains("=")){
					String[] var = variables[i].split("=");
					if(var.length==1){
						String[] var2 = new String[2];
						var2[0] = var[0];
						var2[1] = "";
						var = var2;
					}
					HTTPVar v = new HTTPVar(var[0],var[1]);
					httpvars.add(v);
				}
			}
		}else{
			if(path2[1].contains("=")){
				String[] var = path2[1].split("=");
				if(var.length==1){
					String[] var2 = new String[2];
					var2[0] = var[0];
					var2[1] = "";
					var = var2;
				}
				HTTPVar v = new HTTPVar(var[0],var[1]);
				httpvars.add(v);
			}
		}

	}
	public String getVar(String varName){
		for(HTTPVar v: httpvars){
			if(v.getName().equals(varName)){
				return v.getVar();
			}
		}
		return null;
	}
	private void parseCookies(String cookie){
		if(cookie.equals("")||cookie.contains("=")==false){ return; }
		if(cookie.contains(";")){
			String[] cookieSplit = cookie.split(";");
			for(int i=0; i<cookieSplit.length; i++){
				String c = cookieSplit[i].trim();
				parseCookie(c);
			}
		}else{
			parseCookie(cookie.trim());
		}
	}
	private void parseCookie(String cookie){
		if(cookie.contains("=")==false){ return; }
		String[] split = cookie.split("=");
		cookievars.add(new HTTPVar(split[0],split[1]));
	}
}
