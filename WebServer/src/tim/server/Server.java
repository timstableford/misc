package tim.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {

	/**
	 * @param args
	 */
	private boolean run = true;
	public final static String webRoot = "/home/tim/public_html";
	private ServerSocket serverSocket = null;
	private ArrayList<Client> clientList;
	public static void main(String[] args) {
		new Server(9000);

	}
	public Server(int port){
		clientList = new ArrayList<Client>();
		try{
			serverSocket = new ServerSocket(port);
		}catch(IOException e){
			System.err.println("Could not create socket");
			System.exit(0);
		}
		while(run){
			Socket clientSocket = null;
			try{
				clientSocket = serverSocket.accept();
				Client c = new Client(clientSocket);
				clientList.add(c);
				Thread t = new Thread(c);
				t.start();
			}catch(IOException e){
				System.err.println("Could not accept client");
				System.exit(0);
			}
		}
	}
	public void stop(){
		System.out.println("Stopping");
		run = false;
		for(Client c: clientList){
			c.close();
		}
		System.exit(0);
	}

}
