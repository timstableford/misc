package tim.server.http;

public class User {
	private String name,password;
	public User(String name, String password){
		this.name = name;
		this.password = password;
	}
	public String getName(){
		return name;
	}
	public String getPassword(){
		return password;
	}
	public boolean equals(User u){
		if(name.equals(u.getName())&&password.equals(u.getPassword())){
			return true;
		}
		return false;
	}
}
