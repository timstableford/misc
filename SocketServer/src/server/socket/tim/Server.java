package server.socket.tim;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
	private int port=6666, maxConnections=0;
	private MySQL sqlcon = null;
	private ArrayList<Comms> conList = new ArrayList<Comms>();
	public Server(){
		 int i=0;

		    try{
		      ServerSocket listener = new ServerSocket(port);
		      Socket server;
		      System.out.print("Connecting MySQL...");
		      sqlcon = new MySQL();
		      System.out.print("Done\nListening for clients\n");
		      while((i+1 < maxConnections) || (maxConnections == 0)){
		    	i++;
		        server = listener.accept();
		        Comms connection = new Comms(server, sqlcon, this);
		        Thread t = new Thread(connection);
		        conList.add(connection);
		        t.start();
		      }
		    } catch (IOException ioe) {
		      System.out.println("IOException on socket listen: " + ioe);
		      ioe.printStackTrace();
		    }
	}
	public void quit(){
		System.out.print("Closing MySQL connection...");
		sqlcon.close();
		System.out.print("Closed\nDisconnecting clients...");
		for(Comms c: conList){
			c.disconnect();
		}
		System.out.print("Done\nQuitting");
		System.exit(0);
	}
}
