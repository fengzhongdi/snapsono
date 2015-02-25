package snap.sono.demo.connecter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.sono.demo.setting.SnapSessionSettings;

public class SnapMySQLManager {

	private static Logger logger = LoggerFactory.getLogger("SnapMySQLManager");

	public static Connection getConnection() throws Exception, IllegalAccessException, ClassNotFoundException{
		HashMap<String,String> settings = SnapSessionSettings.getSettings();
		String DB_URL = settings.get("url");
		String USER = settings.get("user");
		String PASS = settings.get("pwd");
		logger.debug("About to regist");
		Driver driver =(Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
		DriverManager.registerDriver(driver);
		logger.debug("finish regist, about to connect");
		return DriverManager.getConnection(DB_URL,USER,PASS);
	}
}
