package snap.sono.demo.connecter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.HashMap;

import java.util.logging.*;

import snap.sono.demo.setting.SnapSessionSettings;

public class SnapMySQLManager {

	private static Logger logger = Logger.getLogger("SnapMySQLManager");

	public static Connection getConnection() throws Exception, IllegalAccessException, ClassNotFoundException{
		HashMap<String,String> settings = SnapSessionSettings.getSettings();
		String DB_URL = settings.get("url");
		String USER = settings.get("user");
		String PASS = settings.get("pwd");
		logger.info("About to regist");
		Driver driver =(Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
		DriverManager.registerDriver(driver);
		logger.info("finish regist, about to connect");
		return DriverManager.getConnection(DB_URL,USER,PASS);
	}
}
