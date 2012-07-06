package tim.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import tim.server.http.HTTPReader;


public class Client implements Runnable{
	private Socket clientSocket = null;
	private BufferedReader in = null;
	public Client(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	public BufferedReader getIn(){
		return in;
	}
	public InputStream getInputStream(){
		try {
			return clientSocket.getInputStream();
		} catch (IOException e) {
			System.err.println("Could not get input stream");
		}
		return null;
	}
	public OutputStream getOutputStream(){
		try {
			return clientSocket.getOutputStream();
		} catch (IOException e) {
			System.err.println("Could not give output stream");
		}
		return null;
	}
	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Could not create either input or output stream");
			close();
			return;
		}
		HTTPReader reader = new HTTPReader(this);
		reader.read();
	}
	public void close(){
		try {
			if(clientSocket!=null){
				clientSocket.close();
			}
		} catch (IOException e) {
			System.err.println("Could not close client socket");
		}
	}
}
