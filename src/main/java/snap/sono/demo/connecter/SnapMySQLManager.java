package snap.sono.demo.connecter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.*;

import snap.sono.demo.data.SnapsonoItem;
import snap.sono.demo.data.SnapsonoItems;
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
	
	public static void runUpdateSql(String sql){
		Connection conn = null;
		Statement stmt = null;
		try {
			logger.info(">>> RunUpdateSql. Ask manager to get connected");
			conn = SnapMySQLManager.getConnection();
			logger.info("Get connected");
			stmt = conn.createStatement();
			
			logger.info("About to execute " + sql);
			stmt.execute(sql);
			logger.info("Finish sql ");
			logger.info("<<< EXIT getItRunUpdateSql");
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(conn, stmt, null);
		}
	}
	
	public static void runUpdateSql(ArrayList<String> sqls){
		Connection conn = null;
		Statement stmt = null;
		try {
			logger.info(">>> RunUpdateSqls. Ask manager to get connected");
			conn = SnapMySQLManager.getConnection();
			logger.info("Get connected");
			stmt = conn.createStatement();
			
			String sql = "START TRANSACTION;";
			for(String s : sqls){
				sql+="\n"+s+";\n";
			}
			sql+="\n"+"COMMIT;";
			logger.info("About to execute " + sql);
			stmt.execute(sql);
			logger.info("Finish sql ");
			logger.info("<<< EXIT getItRunUpdateSqls");
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		} finally {
			closeConnection(conn, stmt, null);
		}
	}
	
	public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception se2) {
		}// nothing we can do
		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception se2) {
		}// nothing we can do
		try {
			if (rs != null)
				rs.close();
		} catch (Exception se2) {
		}// nothing we can do
	}
}
