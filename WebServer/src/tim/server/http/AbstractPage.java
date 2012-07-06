package tim.server.http;

import java.util.ArrayList;

public abstract class AbstractPage implements HTMLPage {
	protected String name;
	protected ArrayList<HTTPVar> vars = null;
	protected ArrayList<HTTPVar> cookies = null;
	protected Session session = null;
	public AbstractPage(String name){
		this.name = name;
		cookies = new ArrayList<HTTPVar>();
	}
	public String getName(){
		return name;
	}
	public void setSessionCookie(ArrayList<HTTPVar> v){
		if(session!=null){
			HTTPVar var = new HTTPVar("sessID",String.valueOf(session.getSessID()));
			v.add(var);
		}
	}
	public void processSessionCookie(ArrayList<HTTPVar> v){
		try{
			HTTPVar a = findVar("sessID",v);
			if(a==null){ return; }
			String id = a.getVar();
			int sessID = Integer.parseInt(id);
			session = Session.findSession(sessID);
		}catch(NumberFormatException e){}
	}
	public void processLogin(ArrayList<HTTPVar> v){
		String user = findVar("user",vars).getVar();
		String pass = findVar("pass",vars).getVar();
		if(user!=null&&pass!=null){
			session = new Session(user,pass);
		}
	}
	public HTTPVar findVar(String name, ArrayList<HTTPVar> list){
		if(list==null){ return null; }
		for(HTTPVar v: list){
			if(v.getName().equals(name)){
				return v;
			}
		}
		return null;
	}
}
