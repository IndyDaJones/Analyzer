import java.sql.ResultSet;
import java.sql.SQLException;
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
		analyzeQuery(getQuery());		
	}
	private String getQuery() {
		Hashtable select = db.getSelection();
		String query ="SELECT ";
		int i = 0;
		if (!select.isEmpty()) {
			while (!select.isEmpty() && !select.get(i).equals("-")) {
				query += select.get(i);
				select.remove(i);
				if(!select.isEmpty()){query +=",";}
				i++;
			}
		}else {
			query += "*";
		}
		query +=" FROM " + db.getTargetTableName() + " WHERE ";
		
		Hashtable where = db.getWhereClause();
		int y = 0;
		if (!where.isEmpty()) {
			while (!where.isEmpty()&& !where.get(y).equals("-") ) {
				query += where.get(y);
				where.remove(y);
				y++;
			}
		}		
		log.log(Level.INFO,"DBHandler query: <"+query+">");
		return query;
	}
	
	private void analyzeQuery(String query) {
		ResultSet rs =  db.executeQuery(query);
		try {
			while((rs!=null) && (rs.next()))
			{
			    System.out.println(rs.getString(1) + " : " + rs.getString(2));
			}
		} catch (SQLException e) {
			log.log(Level.SEVERE,"SQL Exception catched: " + e.getLocalizedMessage());
		}
		log.log(Level.INFO,"called calculate");
	}
	
}
