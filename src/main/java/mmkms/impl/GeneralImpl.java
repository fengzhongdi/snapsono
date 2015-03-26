package mmkms.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.*;
import java.util.*;

import mmkms.connector.MySQLManager;
import mmkms.data.BaseResponse;
import mmkms.data.BasicIDNameOBJ;
import mmkms.data.IDNameResponse;
import mmkms.server.GeneralServer;

import org.apache.commons.httpclient.HttpStatus;

import snap.sono.demo.connecter.SnapMySQLManager;



public class GeneralImpl implements GeneralServer {
	private Logger logger = Logger.getLogger("GeneralImpl");

	

	@Override
	public BasicIDNameOBJ getLoginInfo(Long fbid, String name) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			logger.info(">>> Enter getLoginInfo.");
			String sql = String
					.format("select mmkms_id, name from mmkms.map_fbid_mmkmsInfo where fb_id = %s;",
							fbid);
			conn = SnapMySQLManager.getConnection();
			logger.info("Get connected");
			stmt = conn.createStatement();
			logger.info("About to execute " + sql);
			rs = stmt.executeQuery(sql);
			logger.info("Finish sql ");
			long mmkmsId = -1;
			String username = "";
			while (rs.next()) {
				mmkmsId = rs.getLong("mmkms_id");
				username = rs.getString("name");
			}
			if (mmkmsId < 0) {
				sql = String
						.format("insert into mmkms.map_fbid_mmkmsInfo (fb_id, name) values(%s, '%s');",
								fbid, name);
				MySQLManager.runUpdateSql(sql);
				sql = String
						.format("select mmkms_id from mmkms.map_fbid_mmkmsInfo where fb_id = %s;",
								fbid);
				logger.info("About to execute " + sql);
				rs = stmt.executeQuery(sql);
				logger.info("Finish sql ");
				while (rs.next()) {
					mmkmsId = rs.getLong("mmkms_id");
				}
				logger.info("<<< Finish adding record, exit getLoginInfo");
				return new BasicIDNameOBJ(mmkmsId, name);
			}else if(name !=null && name.length() > 0 && !username.equals(name)){
				MySQLManager.runUpdateSql(
					String.format("update  mmkms.map_fbid_mmkmsInfo set name = '%s' where fb_id = %s",
							name, fbid));
				logger.info("<<< Finish updating record, exit getLoginInfo");
				return new BasicIDNameOBJ(mmkmsId, name);
			}
			return new BasicIDNameOBJ(mmkmsId, username);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw e;
		} finally {
			MySQLManager.closeConnection(conn, stmt, rs);
		}
	}



	@Override
	public IDNameResponse sellerPostItem(Long sellerId, double lon, double lat,
			String status, Long placeId, String desc, String url) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			logger.info(">>> Enter postItem.");
			String uuid = String.valueOf(UUID.randomUUID());
			if(status.length() > 64 || url.length()>512 || desc.length() > 256)
				throw new Exception (String.format("String size too large, status = %s, "
						+ "desc = %s, url = %s", status, desc, url));
			String sql = String
					.format("insert into mmkms.dim_seller_item (seller_id, longtitude, latitude, "
							+" status, place_id, description, img_url, hashid)"
							+ " values (%s, %s, %s, '%s', %s, '%s', '%s', '%s');",
							sellerId, lon, lat, status, placeId, desc, url, uuid);
			MySQLManager.runUpdateSql(sql);
			conn = SnapMySQLManager.getConnection();
			logger.info("Get connected");
			stmt = conn.createStatement();
			sql = String.format("select item_id from mmkms.dim_seller_item where hashid='%s' ", uuid);
			logger.info("About to execute " + sql);
			rs = stmt.executeQuery(sql);
			logger.info("Finish sql ");
			long itemId = -1;
			while (rs.next()) {
				itemId = rs.getLong("item_id");
			}
			return new IDNameResponse(HttpStatus.SC_OK, "item saved with id = "+itemId, itemId);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw e;
		} finally {
			MySQLManager.closeConnection(conn, stmt, rs);
		}
	}


}
