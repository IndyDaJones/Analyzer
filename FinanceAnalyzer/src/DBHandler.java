import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHandler {
	private static final Logger log = Logger.getLogger( DBHandler.class.getName() );
	DBConnection conn;
	DBHandlerProperty props;
	/**
	 * Constructor
	 */
	public DBHandler(){
		props = new DBHandlerProperty();
		log.log(Level.INFO,"call initDB");
		initDB();
		log.log(Level.INFO,"Database initiated");
	}
	/**
	 * This method creates a new database instance
	 */
	private void initDB(){
		log.log(Level.INFO,"Try to create DBConnection");
		conn = new DBConnection(props.getDBProperty("DatabaseLocation"));
	}
	public String getTargetTableName() {
		return props.getDBProperty("TargetTable");
	}
	private String getPropertyTableName() {
		return props.getDBProperty("PropertyTable");
	}
	/**
	 * Returns the selection of Selection parameters
	 */
	public Hashtable getSelection() {
		Statement s;
		Hashtable result = new Hashtable();
		int i = 0;
		try {
			s = conn.createStatement();
		    log.log(Level.INFO,"Statement established");
		    // Fetch table
		    String selTable = "SELECT SELECT_CRITERIA FROM " + getPropertyTableName();
		    log.log(Level.INFO,"Query to execute: " + selTable);
		    s.execute(selTable);
		    ResultSet rs = s.getResultSet();
		    while((rs!=null) && (rs.next())){
		    	log.log(Level.INFO,"Data form Database fetched: <"+ rs.getString(1)+">");
		    	result.put(i, rs.getString(1));
		    	i++;
		    }
		    s.close();
		    } catch (SQLException e) {
		    	conn.closeConnection();
		    	log.log(Level.SEVERE,"Exception catched: " +e.getLocalizedMessage());
		    }
		return result;
		}	
	/**
	 * Returns the selection of WHERE parameters
	 */
	public Hashtable getWhereClause() {
		Statement s;
		Hashtable result = new Hashtable();
		int i = 0;
		try {
			s = conn.createStatement();
		    log.log(Level.INFO,"Statement established");
		    // Fetch table
		    String selTable = "SELECT WHERE_CRITERIA FROM " + getPropertyTableName();
		    log.log(Level.INFO,"Query to execute: " +selTable);
		    s.execute(selTable);
		    ResultSet rs = s.getResultSet();
		    while((rs!=null) && (rs.next())){
		    	log.log(Level.INFO,"Data form Database fetched: <"+ rs.getString(1)+">");
		    	result.put(i, rs.getString(1));
		    	i++;
		    }
		    s.close();
		    } catch (SQLException e) {
		    	conn.closeConnection();
		    	log.log(Level.SEVERE,"Exception catched:" +e.getLocalizedMessage());
		    }
		return result;
		}
	
	public ResultSet executeQuery(String query) {
		ResultSet rs = null;
		try{ 
			Statement s = conn.createStatement();
			log.log(Level.INFO,"Statement established");

	        s.execute(query);
	        log.log(Level.INFO,"Query executed");
	        rs = s.getResultSet();
	        s.close();
        }catch(Exception ex){
    		conn.closeConnection();
        	log.log(Level.SEVERE,"SQLException catched: "+ex.getLocalizedMessage());
	    }
		return rs;
	}
	public void InsertResult(Hashtable result) {
		try{ 

			Statement s = conn.createStatement();
			 // create a table
            String tableName = "RsultTable_" + String.valueOf((int)(Math.random() * 1000.0));
            String createTable = "CREATE TABLE " + tableName + 
                                 " (customer_name Text(32), revenue Double)";
            s.execute(createTable);
            Enumeration res = result.keys();
            // enter value into table
            
            while(res.hasMoreElements()) {
            	String element = res.nextElement().toString();
            	log.log(Level.INFO,"Insert row: <"+element+">,<"+result.get(element)+">");
            	String addRow = "INSERT INTO " + tableName + " VALUES ( '" +
            			element +"',"+result.get(element) + ")";
              s.execute(addRow);
            }
            			
			log.log(Level.INFO,"Statement established");

	        log.log(Level.INFO,"Query executed");
       
	        s.close();
        
        }catch(Exception ex){
    		conn.closeConnection();
        	log.log(Level.SEVERE,"SQLException catched: "+ex.getLocalizedMessage());
	    }
	}
}
