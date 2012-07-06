package tim.server.http;
import java.util.ArrayList;


public interface HTMLPage {
	public String getPage();
	public void processVars(ArrayList<HTTPVar> vars);
	public void processCookies(ArrayList<HTTPVar> cookies);
	public ArrayList<HTTPVar> getCookies();
	public String getName();
}
