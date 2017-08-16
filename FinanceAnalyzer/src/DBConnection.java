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
		try{
			conn=createConnection();
	    }catch(Exception ex){
	    	log.log(Level.SEVERE,"Connection exception catched" +ex.getLocalizedMessage());
	    }
	}
	/**
	 * Returns the connection if it is available. Otherwise a new connection is created
	 * @return Connection
	 */
	Connection getConnection(){
		if (conn!= null) {
			log.log(Level.INFO,"Connection already open");
			return conn;
		}else {
			log.log(Level.INFO,"No Connection available!");
			return createConnection();
		}
	}
	/**
	 * Creates a new connection
	 * @return Connection
	 */
	Connection createConnection(){
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			conn=DriverManager.getConnection("jdbc:ucanaccess://C:\\\\Users\\\\j.nyffeler\\\\OneDrive\\\\Dokumente\\\\JAG\\\\Data\\\\Misc_Data.accdb");
			log.log(Level.INFO,"Connection created!");
			return conn;
	    } catch(Exception ex) {
	    	log.log(Level.SEVERE,"Connection exception catched" +ex.getLocalizedMessage());
	    	return null;
	    }
	}
	/**
	 * Closes a connection
	 * @return Connection
	 */
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
	/**
	 * Creates a statement
	 * @return Statement
	 */

	Statement createStatement() throws SQLException { 
		return conn.createStatement();
	}
}