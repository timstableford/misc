import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Comms implements Runnable {
	private Socket server;
	private String line;
	private PrintStream out;
	private Action action = Action.LOGIN;
	private MySQL m = null;
	private Server parent;
	Comms(Socket server, MySQL my, Server s) {
		this.server=server;
		m = my;
		parent = s;
	}

	public void run () {
		try {
			// Get input from the client
			DataInputStream in = new DataInputStream (server.getInputStream());
			out = new PrintStream(server.getOutputStream());
			Scanner sin = new Scanner(in);
			out.print("Enter username:");
			while(sin.hasNextLine()) {
				line = sin.nextLine();
				switch(action){
				case NONE:
					processCommand(line.trim());
					break;
				case LOGIN:
					login(line.trim());
				}
			}
		} catch (IOException ioe) {
			System.out.println("IOException on socket listen: " + ioe);
			ioe.printStackTrace();
		} catch (IllegalStateException e){
			System.out.println("No next line found");
		}
	}
	public void print(String p){
		out.println(p);
	}
	public void disconnect(){
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void login(String u){
		if(m.hasUser(u)){
			out.println("Logged in");
			action = Action.NONE;
		}else{
			out.print("Authentication failure\nEnter username:");
		}
	}
	public void processCommand(String c){
		if(c.equals("exit")){
			out.println("Disconnecting");
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(c.equals("kill")){
			parent.quit();
		}else{
			out.println("Command not recognized");
		}
	}
}
