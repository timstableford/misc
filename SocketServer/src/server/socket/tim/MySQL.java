package server.socket.tim;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQL {
	private Connection con = null;
    public MySQL(){

        String url = "jdbc:mysql://192.168.2.2:3306/tim_test";
        String user = "tim";
        String password = "alkesh56;";

        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(MySQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
    }
    public boolean hasUser(String u, String p){
    	ResultSet rs;
    	String name = null;
    	int pass = 0;
    	try {
    		Statement st = con.createStatement();
    		rs = st.executeQuery("SELECT * FROM users");
    		while(rs.next()){
    			name = rs.getString("name");
    			pass = rs.getInt("password");
    			if(u.equals(name)&&p.hashCode()==pass){
    				return true;
    			}
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return false;
    }
    public void close(){
    	if(con!=null){
    		try {
    			con.close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
    }
}