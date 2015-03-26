package mmkms.connector;
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

public class MySQLManager {

	private static Logger logger = Logger.getLogger("SnapMySQLManager");

	public static Connection getConnection() throws Exception{
		try{
			HashMap<String,String> settings = SnapSessionSettings.getSettings();
			String DB_URL = settings.get("url");
			String USER = settings.get("user");
			String PASS = settings.get("pwd");
			Driver driver =(Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
			//DriverManager.getConnection(url)
			//return DriverManager.getConnection(DB_URL,USER,PASS);
			String connStr = String.format("%s?user=%s&password=%s",DB_URL,USER,PASS);
			//logger.info("connect string is : "+connStr);
			return DriverManager.getConnection(connStr);
		}catch(Exception e){
			throw e;
		}
		
	}
	
	public static void runUpdateSql(String sql) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		try {
			logger.info(">>> RunUpdateSql. Ask manager to get connected");
			conn = MySQLManager.getConnection();
			stmt = conn.createStatement();
			logger.info("About to execute " + sql);
			stmt.execute(sql);
			logger.info("Finish sql ");
			logger.info("<<< EXIT getItRunUpdateSql");
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw e;
		} finally {
			closeConnection(conn, stmt, null);
		}
	}
	
	public static void runUpdateSqls(List<String> sqls) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		try {
			logger.info(">>> RunUpdateSqls. Ask manager to get connected");
			conn = MySQLManager.getConnection();
			logger.info("Get connected");
			stmt = conn.createStatement();
			
			String sql = "START TRANSACTION;";
			logger.info("running "+sql);
			stmt.execute(sql);
			for(String s : sqls){
				logger.info("running "+s);
				stmt.execute(s);
			}
			sql="COMMIT;";
			logger.info("running "+sql);
			stmt.execute(sql);
			logger.info("Finish sql ");
			logger.info("<<< EXIT getItRunUpdateSqls");
		} catch (Exception e) {
			logger.severe(e.getMessage());
			logger.info("about to roll back");
			stmt.execute("ROLLBACK;");
			e.printStackTrace();
			throw e;
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
