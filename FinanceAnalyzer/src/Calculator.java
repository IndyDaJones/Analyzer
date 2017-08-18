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
		Hashtable result = new Hashtable();
		ResultSet rs =  db.executeQuery(query);
		//regex.
		try {
			while((rs!=null) && (rs.next()))
			{	
				System.out.println(rs.getString(1) + " : " + rs.getString(2));
				if (!result.containsKey(rs.getString(1).substring(0, rs.getString(1).indexOf(",")))){
					log.log(Level.INFO,"New entry to hashtable: " + rs.getString(1).substring(0, rs.getString(1).indexOf(",")));
					result.put(rs.getString(1).substring(0, rs.getString(1).indexOf(",")), rs.getString(2));
				}else {
					log.log(Level.INFO,"Existing entry in hashtable: " + rs.getString(1).substring(0, rs.getString(1).indexOf(",")));
					int value_retrieved = Integer.parseInt(result.get(rs.getString(1).substring(0, rs.getString(1).indexOf(","))).toString());
					int value_new = Integer.parseInt(rs.getString(2).toString());
									
					log.log(Level.INFO,"Entry retrieved: " + result.get(rs.getString(1).substring(0, rs.getString(1).indexOf(","))));
					result.replace(rs.getString(1).substring(0, rs.getString(1).indexOf(",")), value_retrieved + value_new);
				}
			}
			log.log(Level.INFO,"Data in hashtable: " + result.size());
			result.forEach((k,v) ->log.log(Level.INFO,"Company: "+k+" 			Order Intake:"+v));
			db.InsertResult(result);
		} catch (SQLException e) {
			log.log(Level.SEVERE,"SQL Exception catched: " + e.getLocalizedMessage());
		}
		log.log(Level.INFO,"called calculate");
	}
	private void analyzeQuery(String query, int i) {
		Hashtable result = new Hashtable();
		ResultSet rs =  db.executeQuery(query);
		//regex.
		try {
			while((rs!=null) && (rs.next()))
			{	
				System.out.println(rs.getString(1) + " : " + rs.getString(2));
				if (!result.containsKey(rs.getString(1).substring(0, rs.getString(1).indexOf(",")))){
					log.log(Level.INFO,"New entry to hashtable: " + rs.getString(1).substring(0, rs.getString(1).indexOf(",")));
					result.put(rs.getString(1).substring(0, rs.getString(1).indexOf(",")), rs.getString(2));
				}else {
					log.log(Level.INFO,"Existing entry in hashtable: " + rs.getString(1).substring(0, rs.getString(1).indexOf(",")));
					int value_retrieved = Integer.parseInt(result.get(rs.getString(1).substring(0, rs.getString(1).indexOf(","))).toString());
					int value_new = Integer.parseInt(rs.getString(2).toString());
									
					log.log(Level.INFO,"Entry retrieved: " + result.get(rs.getString(1).substring(0, rs.getString(1).indexOf(","))));
					result.replace(rs.getString(1).substring(0, rs.getString(1).indexOf(",")), value_retrieved + value_new);
				}
			}
			log.log(Level.INFO,"Data in hashtable: " + result.size());
			result.forEach((k,v) ->log.log(Level.INFO,"Company: "+k+" 			Order Intake:"+v));
			db.InsertResult(result);
		} catch (SQLException e) {
			log.log(Level.SEVERE,"SQL Exception catched: " + e.getLocalizedMessage());
		}
		log.log(Level.INFO,"called calculate");
	}

	
}
