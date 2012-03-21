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
            //rs = st.executeQuery("SELECT VERSION()");

            //if (rs.next()) {
              //  c.print(rs.getString(1));
            //}

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(MySQL.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        }
    }
    public boolean hasUser(String u){
    	ResultSet rs;
    	String name = null;
    	try {
    		Statement st = con.createStatement();
    		rs = st.executeQuery("SELECT name FROM users");
    		while(rs.next()){
    			name = rs.getString("name");
    			if(name.equals(u)){
    				return true;
    			}
    		}
    		return false;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	if(u.equals(name)){
    		return true;
    	}else{
    		return false;
    	}
    }
    public void close(){
    	if(con!=null){
    		try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
}