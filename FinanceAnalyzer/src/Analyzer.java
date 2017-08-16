import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Analyzer {
	private static final Logger log = Logger.getLogger( Analyzer.class.getName() );
	static String logFile;
	public static void main(String[] args) {
		/**
		 * Load WeatherService properties
		 */
		AnalyzerProperty props = new AnalyzerProperty();
		logFile = props. getServiceProperty("LogFileDest");
	
		initLogging();
		
		log.log(Level.INFO,"call Analyzer!");
		//initDevice(sys);
		log.log(Level.INFO,"Analyzer initiated");
		
		log.log(Level.INFO,"Database closed!");
		//db.closeDB();
		log.log(Level.INFO,"end main");
	}
	/**
	 * In this method the logging Handler is initialized
	 */
		private static void initLogging(){
			try {
				/**
				 * Using the setUseParentHandlers = false flag, the program no more writes in the console
				 */
				log.setUseParentHandlers(true);
				/**Logger globalLogger = Logger.getLogger("global");
				Handler[] handlers = globalLogger.getHandlers();
				for(Handler handler : handlers) {
				    globalLogger.removeHandler(handler);
				}*/
				Handler handler = new FileHandler( logFile +"Analyzer.log" );
				log.addHandler(handler);
				log.log(Level.INFO,"File location of Service:"+logFile);
			} catch (SecurityException e) {
				log.log(Level.SEVERE,e.getLocalizedMessage());
			} catch (IOException e) {
				log.log(Level.SEVERE,e.getLocalizedMessage());
			}
		}
}
