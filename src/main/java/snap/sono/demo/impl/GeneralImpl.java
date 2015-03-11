package snap.sono.demo.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.*;
import java.util.*;

import org.apache.commons.httpclient.HttpStatus;

import snap.sono.demo.connecter.SnapMySQLManager;
import snap.sono.demo.data.BaseResponse;
import snap.sono.demo.data.BasicIDNameOBJ;
import snap.sono.demo.data.Constants;
import snap.sono.demo.data.SnapProfile;
import snap.sono.demo.data.SnapProfilesBO;
import snap.sono.demo.data.SnapsonoItem;
import snap.sono.demo.data.SnapsonoItems;
import snap.sono.demo.data.TransactionCreateResponse;
import snap.sono.demo.server.GeneralServer;

public class GeneralImpl implements GeneralServer {
	private Logger logger = Logger.getLogger("GeneralImpl");

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
			logger.severe(e.getMessage());
			e.printStackTrace();
		} finally {
			SnapMySQLManager.closeConnection(conn, stmt, rs);
		}
		return snapProfilesBO;
	}

	@Override
	public BasicIDNameOBJ getLoginInfo(Long fbid) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			logger.info(">>> Enter getLoginInfo. Ask manager to get connected");
			conn = SnapMySQLManager.getConnection();
			logger.info("Get connected");
			stmt = conn.createStatement();
			String sql = String
					.format("select snapsono_id from snapsono.mapping_fbid_snapsonoid where fb_id = %s;",
							fbid);
			logger.info("About to execute " + sql);
			rs = stmt.executeQuery(sql);
			logger.info("Finish sql ");
			long snapsono_id = -1;
			while (rs.next()) {
				snapsono_id = rs.getLong("snapsono_id");
			}
			if (snapsono_id < 0) {
				sql = String
						.format("insert into snapsono.mapping_fbid_snapsonoid(fb_id) values(%s);",
								fbid);
				SnapMySQLManager.runUpdateSql(sql);
				sql = String
						.format("select snapsono_id from snapsono.mapping_fbid_snapsonoid where fb_id = %s;",
								fbid);
				logger.info("About to execute " + sql);
				rs = stmt.executeQuery(sql);
				logger.info("Finish sql ");
				while (rs.next()) {
					snapsono_id = rs.getLong("snapsono_id");
				}
			}
			logger.info("<<< Exit getLoginInfo");
			return new BasicIDNameOBJ(snapsono_id, "");
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		} finally {
			SnapMySQLManager.closeConnection(conn, stmt, rs);
		}
		return null;
	}

	@Override
	public BaseResponse postItem(String itemName, Long ownerId, String status,
			String comments, String figUrl, double price, String other) {
		String msg = "";
		try {
			logger.info(">>> Enter getLoginInfo. Ask manager to get connected");
			String sql = String
					.format("insert into snapsono.dim_items (name,owner_id,status,comments,fig_url,price,other)"
							+ " values ('%s',%s,'%s','%s','%s',%s,'%s')",
							itemName, ownerId, status, comments, figUrl, price,
							other);
			SnapMySQLManager.runUpdateSql(sql);
			logger.info("<<< EXIT postItem");
			return new BaseResponse(HttpStatus.SC_OK, "Item stored in DB");
		} catch (Exception e) {
			logger.severe(e.getMessage());
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new BaseResponse(HttpStatus.SC_BAD_REQUEST, msg);
	}

	@Override
	public SnapsonoItems getItems(String itemIds) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			logger.info(">>> Enter getItems. Ask manager to get connected");
			List<SnapsonoItem> list = new ArrayList<SnapsonoItem>();
			conn = SnapMySQLManager.getConnection();
			logger.info("Get connected");
			stmt = conn.createStatement();
			String sql = "select * from snapsono.dim_items";
			if (itemIds != null && itemIds.length() > 0) {
				sql += String.format(" where item_id in (%s) ", itemIds);
			}
			logger.info("About to execute " + sql);
			rs = stmt.executeQuery(sql);
			logger.info("Finish sql ");
			while (rs.next()) {
				list.add(new SnapsonoItem(rs.getLong("item_id"), rs
						.getString("name"), rs.getLong("owner_id"), rs
						.getString("status"), rs.getString("comments"), rs
						.getString("fig_url"), rs.getDouble("price"), rs
						.getString("other")));
			}
			logger.info(String.format("<<< EXIT getItems, %d items found",
					list.size()));
			return new SnapsonoItems(list);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		} finally {
			SnapMySQLManager.closeConnection(conn, stmt, rs);
		}
		return null;
	}

	@Override
	public TransactionCreateResponse createTransaction(Long sellerId, Long userId,
			double latitude, double longtitude, double amount, String itemLists) {
		String msg = "";
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			logger.info(">>> Enter getLoginInfo. Ask manager to get connected");
			String uuid = String.valueOf(UUID.randomUUID());
			String sql = String
					.format("insert into snapsono.dim_transactions_info (seller_id,buyer_id,location_latitude,location_longtitude,transaction_amount,hash_id)"
							+ " values (%s,%s,%s,%s,%s,%s,%s,%s,'%s')",
							sellerId,userId,latitude,longtitude,amount,uuid);
			SnapMySQLManager.runUpdateSql(sql);
			sql = String.format("select transaction_id from snapsono.dim_transactions_info where hash_id = '%s'", uuid);
			conn = SnapMySQLManager.getConnection();
			logger.info("Get connected");
			stmt = conn.createStatement();
			logger.info("About to execute " + sql);
			rs = stmt.executeQuery(sql);
			logger.info("Finish sql ");
			long transactionId = -1;
			while (rs.next()) {
				transactionId = rs.getLong("transaction_id");
			}
			logger.info("Found transactionId = %d " + transactionId);
			
			if(transactionId<1)
				throw new Exception("Cannot initialize transactions!!!");
			
			HashSet<String> itemHash = new HashSet<String>();
			for(String item : itemLists.split(",")){
				itemHash.add(item);
			}
			List<String> sqls = new ArrayList<String>();
			for(String item : itemHash){
				sql = String.format("insert into snapsono.dim_transactions_items (transaction_id,item_id)"
						+" values (%s, %s) ", transactionId, item);
				sqls.add(sql);
			}
			sqls.add(String.format("insert into snapsono.dim_transactions_status_info (transaction_id, status)"
					+" values (%s, %s) ", transactionId, Constants.INITIAL));
			
			logger.info("<<< EXIT postItem");
			return new TransactionCreateResponse(HttpStatus.SC_OK, "Transaction created, with id = "+transactionId,transactionId);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			msg = e.getMessage();
			e.printStackTrace();
		}
		return new TransactionCreateResponse(HttpStatus.SC_BAD_REQUEST, msg, -1l);
	}

}
