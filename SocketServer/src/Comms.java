import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Comms implements Runnable {
	private Socket server;
	private String line;
	private PrintStream out;
	private Action action = Action.NONE;
	private MySQL m = null;
	Comms(Socket server) {
		this.server=server;
	}

	public void run () {
		try {
			// Get input from the client
			DataInputStream in = new DataInputStream (server.getInputStream());
			out = new PrintStream(server.getOutputStream());
			Scanner sin = new Scanner(in);
			while(sin.hasNextLine()) {
				line = sin.nextLine();
				if(action==Action.NONE){
					if(line.equals("kill")){ System.exit(0); }
					if(line.equals("exit")){ 
						server.close();  
						}
					if(line.equals("connect")){
						out.print("Connecting... ");
						m = new MySQL(this);
						out.println("Connected");
					}
					if(m!=null&&line.equals("login")){
						action = Action.LOGIN;
						out.print("Enter username:");
					}
				}
				if(line.equals("login")==false&&action==Action.LOGIN){
					if(m.hasUser(line.trim())){
						out.println("We have that user");
					}else{
						out.println("We don't has that user");
					}
					action = Action.NONE;
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
}
