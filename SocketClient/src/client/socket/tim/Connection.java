package client.socket.tim;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Connection {
	private Socket cli = null;
	private PrintStream out = null;
	private Scanner in = null;
	private InputStreamReader input;
	private BufferedReader input2;
	public Connection(){
		try {
			cli = new Socket("192.168.2.2",6666);
			System.out.println("Connection established");
			input = new InputStreamReader (cli.getInputStream());
			input2 = new BufferedReader(input);
			out = new PrintStream(cli.getOutputStream());
			in = new Scanner(input2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean login(String u, String p){
		System.out.println("Logging in");
			String line = in.nextLine();
			System.out.println(line);
			if(line.equals("USER")){
				out.print(u);	
			}
			//String line = in.nextLine();
			//if(line.equals("PASS")){
				out.print(p);
			//}
		
		//String response = in.nextLine();
			String	response = null;
		switch(response){
		case "100":
			return true;
		case "101":
			return false;
		default:
			System.out.println("Error");
			return false;
		}
	}
}
