package tim.server.http;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Session {
	public static ArrayList<Session> sessions = new ArrayList<Session>();
	public static ArrayList<User> users = new ArrayList<User>();
	public static boolean userListLoaded = false;
	private User user = null;
	public Session(String n, String p){
		user = new User(n,p);
		Session.loadUsers();
		if(auth()){
			Session.sessions.add(this);
		}
	}
	public int getSessID(){
		return String.valueOf(Session.sessions.indexOf(this)).hashCode();
	}
	public String getUser(){
		return user.getName();
	}
	public String getPass(){
		return user.getPassword();
	}
	public static void loadUsers(){
		if(userListLoaded){ return; }
		InputStream fstream = Session.class.getResourceAsStream("users");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		try {
			while ((strLine = br.readLine()) != null)   {
				if(strLine.contains(":")){
					String[] s = strLine.split(":");
					User u = new User(s[0],s[1]);
					Session.users.add(u);
				}
			}
		} catch (IOException e) {
			System.err.println("Could not read users file");
		}
		userListLoaded = true;
	}
	public boolean auth(){
		for(User u: Session.users){
			if(u.equals(user)){
				return true;
			}
		}
		return false;
	}
	public static Session findSession(int sID){
		for(Session s: Session.sessions){
			if(s.getSessID()==sID){
				return s;
			}
		}
		return null;
	}
}
