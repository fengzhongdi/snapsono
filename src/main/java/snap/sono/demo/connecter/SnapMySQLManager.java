package snap.sono.demo.connecter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnapMySQLManager {

	private static Logger logger = LoggerFactory.getLogger("SnapMySQLManager");

	public static Connection getConnection() throws Exception, IllegalAccessException, ClassNotFoundException{
		String DB_URL = "jdbc:mysql://54.69.23.214:3306/";
		String USER = "test123";
		String PASS = "test123";
		logger.debug("About to regist");
		Driver driver =(Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
		DriverManager.registerDriver(driver);
		logger.debug("finish regist, about to connect");
		return DriverManager.getConnection(DB_URL,USER,PASS);
	}
}
