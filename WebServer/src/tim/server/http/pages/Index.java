package tim.server.http.pages;

import java.util.ArrayList;

import tim.server.http.AbstractPage;
import tim.server.http.HTTPVar;
import tim.server.http.Session;

public class Index extends AbstractPage {
	private Session session = null;
	public Index(){
		super("index.j");
	}
	@Override
	public String getPage() {
		String ret = "this is the index page";
		if(vars!=null){
			ret+= " \n<br />vars are also set bellow\n<br />"+vars;
		}
		if(cookies!=null){
			ret+= " \n<br />the folloowing cookies (mm cookies) exist\n<br />"+cookies;
		}
		if(session!=null&&session.auth()){
			ret+= "\n<br />and you are logged in";
		}
		return ret;
	}

	@Override
	public void processVars(ArrayList<HTTPVar> vars) {
		this.vars = vars;
		processLogin(vars);
	}
	@Override
	public void processCookies(ArrayList<HTTPVar> c) {
		if(c==null){ System.err.println("Process what cookies?"); return; }
		processSessionCookie(c);
	}
	@Override
	public ArrayList<HTTPVar> getCookies() {
		ArrayList<HTTPVar> ret = new ArrayList<HTTPVar>();
		setSessionCookie(ret);
		return ret;
	}

}
