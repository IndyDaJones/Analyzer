import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Calculator {
	private static final Logger log = Logger.getLogger( Calculator.class.getName() );
	DBHandler db;
	public Calculator(){}
	
	public void start() {
		log.log(Level.INFO,"create DBHandler");
		db = new DBHandler();
		log.log(Level.INFO,"DBHandler created");
		Hashtable select = db.getSelection();
		Hashtable where = db.getWhereClause();
		
		//get WhereClauses
		
		db.calculate();
		log.log(Level.INFO,"called calculate");
	}
	
}
