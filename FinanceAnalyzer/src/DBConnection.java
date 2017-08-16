import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



public class DBConnection {
	
	private static final Logger log = Logger.getLogger( Analyzer.class.getName() );
	Connection conn;
	public DBConnection(){
	try
    {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		conn=DriverManager.getConnection("jdbc:ucanaccess://C:\\\\Users\\\\j.nyffeler\\\\OneDrive\\\\Dokumente\\\\JAG\\\\Data\\\\Misc_Data.accdb");
    }

    catch(Exception ex)
    {
        ex.printStackTrace();
    }

	}
	Connection getConnection(){
		if (conn!= null) { 
			return conn;
		}else {
			return null;
		}
	}
	void closeConnection(){
		if (conn!= null) {
			try {
				conn.close(); 
			}
			catch (SQLException e) {
				log.log(Level.SEVERE,"Exception" +e);
			}
		}
	}
	Statement createStatement() throws SQLException { 
		return conn.createStatement();
	}
}