package tim.server.http;

public class HTTPVar {
	private String name, var;
	public HTTPVar(String name, String var){
		this.name = name;
		this.var = var;
	}
	public String getName(){
		return name;
	}
	public String getVar(){
		return var;
	}
	public String toString(){
		return name+":"+var;
	}
}
