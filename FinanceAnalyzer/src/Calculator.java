import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculator {
	private static final Logger log = Logger.getLogger( Calculator.class.getName() );
	DBConnection conn;
	public Calculator(){}
	
	void start() {
		log.log(Level.INFO,"call initDB");
		initDB();
		log.log(Level.INFO,"Database initiated");
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
	private void calculate() {
		try{ 
			Statement s = conn.createStatement();
        log.log(Level.INFO,"Connection established");

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
        conn.closeConnection();;
        }
		catch(Exception ex)
	    {
	        ex.printStackTrace();
	    }

	}
}
