package snap.sono.demo.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.sono.demo.connecter.SnapMySQLManager;
import snap.sono.demo.data.SnapProfile;
import snap.sono.demo.data.SnapProfilesBO;
import snap.sono.demo.server.GeneralServer;

public class GeneralImpl implements GeneralServer {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public SnapProfilesBO getSnapProfilesBO() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		SnapProfilesBO snapProfilesBO = new SnapProfilesBO();
		try {
			logger.info("Ask manager to get connected");
			conn = SnapMySQLManager.getConnection();
			logger.info("Get connected");
			stmt = conn.createStatement();
			String sql = "select * from snapsono.di_just_test";
			logger.info("About to execute " + sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("userid");
				String url = rs.getString("url");
				snapProfilesBO.getSnapProfileList().add(
						new SnapProfile(id, url));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
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
		return snapProfilesBO;
	}

}
