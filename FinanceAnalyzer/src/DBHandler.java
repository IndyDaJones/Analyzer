import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHandler {
	private static final Logger log = Logger.getLogger( Calculator.class.getName() );
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
	 * This method starts the DB Handler
	 */
	public void start() {

		log.log(Level.INFO,"call calculate");
		calculate();
		log.log(Level.INFO,"called calculate");
	}
	/**
	 * This method creates a new database instance
	 */
	private void initDB(){
		log.log(Level.INFO,"Try to create DBConnection");
		conn = new DBConnection();
	}
	/**
	 * Returns the selection of Selection parameters
	 */
	public Hashtable getSelection() {
		Statement s;
		Hashtable result = new Hashtable();
		try {
			s = conn.createStatement();
		    log.log(Level.INFO,"Statement established");
		    // Fetch table
		    String selTable = "SELECT SELECT_CRITERIA FROM " + props.getDBProperty("Tablename");
		    log.log(Level.INFO,"Query to execute: " + selTable);
		    s.execute(selTable);
		    ResultSet rs = s.getResultSet();
		    while((rs!=null) && (rs.next())){
		    	log.log(Level.INFO,"Data form Database fetched: <"+ rs.getString(1)+">");
		    	result.put(1, rs.getString(1));
		    }
		    s.close();
		    } catch (SQLException e) {
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
		try {
			s = conn.createStatement();
		    log.log(Level.INFO,"Statement established");
		    // Fetch table
		    String selTable = "SELECT WHERE_CRITERIA FROM " + props.getDBProperty("Tablename");
		    log.log(Level.INFO,"Query to execute: " +selTable);
		    s.execute(selTable);
		    ResultSet rs = s.getResultSet();
		    while((rs!=null) && (rs.next())){
		    	log.log(Level.INFO,"Data form Database fetched: <"+ rs.getString(1)+">");
		    	result.put(1, rs.getString(1));
		    }
		    s.close();
		    } catch (SQLException e) {
		    	log.log(Level.SEVERE,"Exception catched:" +e.getLocalizedMessage());
		    }
		return result;
		}
	
	public void calculate() {
		try{ 
			Statement s = conn.createStatement();
        log.log(Level.INFO,"Statement established");

        // create a table
        String tableName = "myTable" + String.valueOf((int)(Math.random() * 1000.0));
        String createTable = "CREATE TABLE " + tableName + 
                             " (id Integer, name Text(32))";
        s.execute(createTable);
        log.log(Level.INFO,"Table created" +s.getUpdateCount());

        // enter value into table
        for(int i=0; i<25; i++)
        {
          String addRow = "INSERT INTO " + tableName + " VALUES ( " + 
                 String.valueOf((int) (Math.random() * 32767)) + ", 'Text Value " + 
                 String.valueOf(Math.random()) + "')";
          s.execute(addRow);
          log.log(Level.INFO,"Value entered" +i);
        }

        // Fetch table
        String selTable = "SELECT * FROM " + tableName;
        s.execute(selTable);
        ResultSet rs = s.getResultSet();
        while((rs!=null) && (rs.next()))
        {
            System.out.println(rs.getString(1) + " : " + rs.getString(2));
        }

        // drop the table
       // String dropTable = "DROP TABLE " + tableName;
        //s.execute(dropTable);

        // close and cleanup
        s.close();
        conn.closeConnection();
        }
		catch(Exception ex)
	    {
	        ex.printStackTrace();
	    }

	}
}
