package mmkms.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.*;
import java.util.*;

import mmkms.connector.MySQLManager;
import mmkms.data.BaseResponse;
import mmkms.data.BasicIDNameOBJ;
import mmkms.data.BuyerAcquireInfo;
import mmkms.data.BuyerAcquireInfoList;
import mmkms.data.IDNameResponse;
import mmkms.data.SellerPostObj;
import mmkms.data.SellerPostObjList;
import mmkms.data.TransactionCreateResponse;
import mmkms.server.GeneralServer;

import org.apache.commons.httpclient.HttpStatus;

public class GeneralImpl implements GeneralServer {
	private Logger logger = Logger.getLogger("GeneralImpl");
	private static HashMap<Integer, HashMap<Integer, HashSet<SellerPostObj>>> taskPool = new HashMap<Integer, HashMap<Integer, HashSet<SellerPostObj>>>();
	private static HashMap<Long, SellerPostObj> sellerPostHash = new HashMap<Long, SellerPostObj>();

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
			conn = MySQLManager.getConnection();
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
			} else if (name != null && name.length() > 0
					&& !username.equals(name)) {
				MySQLManager
						.runUpdateSql(String
								.format("update  mmkms.map_fbid_mmkmsInfo set name = '%s' where fb_id = %s",
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
	public IDNameResponse sellerPostItem(Long sellerId, double latitude,
			double longtitude, String status, Long placeId, String desc,
			String url) throws Exception {
		try {
			logger.info(">>> Enter sellerPostItem.");
			deleteOldSellerItem(sellerId);
			SellerPostObj obj = new SellerPostObj(sellerId, latitude,
					longtitude, placeId, status, desc, url);
			sellerPostHash.put(sellerId, obj);
			int lat = (int) latitude, lon = (int) longtitude;
			sellerPostHash.put(sellerId, obj);
			if (taskPool.containsKey(lat)) {
				if (taskPool.get(lat).containsKey(lon)) {
					taskPool.get(lat).get(lon).add(obj);
				} else {
					HashSet<SellerPostObj> hashset = new HashSet<SellerPostObj>();
					hashset.add(obj);
					taskPool.get(lat).put(lon, hashset);
				}
			} else {
				HashMap<Integer, HashSet<SellerPostObj>> hash = new HashMap<Integer, HashSet<SellerPostObj>>();
				HashSet<SellerPostObj> hashset = new HashSet<SellerPostObj>();
				hashset.add(obj);
				hash.put(lon, hashset);
				taskPool.put(lat, hash);
			}
			logger.info("<<< Exit sellerPostItem.");
		} catch (Exception e) {
			throw e;
		}
		return new IDNameResponse(HttpStatus.SC_OK, "Seller post an item",
				sellerId, "");
	}

	@Override
	public SellerPostObjList buyerListSellerItems(Long buyerId,
			double latitudeStart, double latitudeEnd, double longtitudeStart,
			double longtitudeEnd) throws Exception {
		List<SellerPostObj> list = new ArrayList<SellerPostObj>();
		try {
			logger.info(">>> Enter buyerListSellerItems.");
			for (int lat = (int) latitudeStart; lat <= (int) latitudeEnd; lat++) {
				for (int lon = (int) longtitudeStart; lon <= (int) longtitudeEnd; lon++) {
					if (taskPool.containsKey(lat)
							&& taskPool.get(lat).containsKey(lon)) {
						for (SellerPostObj sellerObj : taskPool.get(lat).get(
								lon)) {
							if (sellerObj.getLaitude() <= latitudeEnd
									&& sellerObj.getLaitude() >= latitudeStart
									&& sellerObj.getLongtitude() <= longtitudeEnd
									&& sellerObj.getLongtitude() >= longtitudeStart) {
								list.add(sellerObj);
							}
						}
					}
				}
			}
			logger.info("<<< Exit buyerListSellerItems.");
		} catch (Exception e) {
			throw e;
		}
		return new SellerPostObjList(list);
	}

	@Override
	public IDNameResponse buyerPlaceOrderOnSellerItems(Long buyerId,
			String buyerName, String sellerIdListStr, String bids,
			String comments) throws Exception {
		int sellerNum = 0;
		try {
			logger.info(">>> Enter buyerPlaceOrderOnSellerItems.");
			logger.info(String.format(
					"sellerIdList is : %s, bids = %s, comments = %s",
					sellerIdListStr, bids, comments));
			String[] sellerIdArr = sellerIdListStr.split(","), bidArr = bids
					.split(","), commentArr = comments.split(",");
			for (int i = 0; i < sellerIdArr.length; i++) {
				BuyerAcquireInfo info = new BuyerAcquireInfo(buyerId,
						buyerName, Double.parseDouble(bidArr[i]), commentArr[i]);
				Long sellerID = Long.parseLong(sellerIdArr[i]);
				if (sellerPostHash.containsKey(sellerID)) {
					sellerPostHash.get(sellerID).addBuyer(info);
				}
			}
			sellerNum = sellerIdArr.length;
			logger.info("<<< Exit buyerPlaceOrderOnSellerItems.");
		} catch (Exception e) {
			throw e;
		}
		return new IDNameResponse(HttpStatus.SC_OK, String.format(
				"Buyer sellect %d sellers", sellerNum), buyerId);
	}

	@Override
	public BuyerAcquireInfoList sellerSeeAllBuyers(Long sellId)
			throws Exception {
		BuyerAcquireInfoList buyerAcquireInfoList = null;
		try {
			logger.info(">>> Enter sellerSeeAllBuyers.");
			buyerAcquireInfoList = new BuyerAcquireInfoList(sellerPostHash.get(
					sellId).getBuyerList());
			logger.info("<<< Exit sellerSeeAllBuyers.");
		} catch (Exception e) {
			throw e;
		}
		return buyerAcquireInfoList;
	}

	@Override
	public TransactionCreateResponse finalizeTransaction(Long buyerId,
			Long sellerId) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		long transactionId = -1;
		try {
			logger.info(">>> Enter finalizeTransaction.");
			String uuid = String.valueOf(UUID.randomUUID());
			SellerPostObj sellObj = sellerPostHash.get(sellerId);
			BuyerAcquireInfo buyerInfo = sellObj.getBuyer(buyerId);
			String sql = String
					.format("insert into mmkms.dim_transactions (seller_id,"
							+ "buyer_id, status, comments, img_url,price, latitude, longtitude,"
							+ "place_id,description,uuid) values (%s, %s, '%s','%s','%s',%s,"
							+ "%s,%s,%s,'%s','%s')", sellerId, buyerId, "open",
							buyerInfo.getComment(), sellObj.getImgUrl(),
							buyerInfo.getBid(), sellObj.getLaitude(),
							sellObj.getLongtitude(), sellObj.getPlaceId(),
							sellObj.getDesc(), uuid);
			MySQLManager.runUpdateSql(sql);
			sql = String
					.format("select transaction_id from mmkms.dim_transactions where uuid = '%s'",
							uuid);
			conn = MySQLManager.getConnection();
			logger.info("Get connected");
			stmt = conn.createStatement();
			logger.info("About to execute " + sql);
			rs = stmt.executeQuery(sql);
			logger.info("Finish sql ");
			while (rs.next()) {
				transactionId = rs.getLong("transaction_id");
			}
			logger.info("Found transactionId = " + transactionId);
			if (transactionId < 1)
				throw new Exception("Cannot initialize transactions!!!");
			deleteOldSellerItem(sellerId);
			logger.info("<<< Exit finalizeTransaction.");
		} catch (Exception e) {
			throw e;
		} finally {
			MySQLManager.closeConnection(conn, stmt, rs);
		}
		return new TransactionCreateResponse(HttpStatus.SC_OK,
				"Transaction created, with id = " + transactionId,
				transactionId);
	}

	private void deleteOldSellerItem(long sellerId) throws Exception {
		// private static HashMap<Integer, HashMap<Integer,
		// HashSet<SellerPostObj>>> taskPool = new HashMap<Integer,
		// HashMap<Integer, HashSet<SellerPostObj>>>();
		// private static HashMap<Long, SellerPostObj> sellerPostHash = new
		// HashMap<Long, SellerPostObj>();
		try {
			logger.info(">>> Enter deleteOldSellerItem.");
			SellerPostObj oldObj = sellerPostHash.get(sellerId);
			if (oldObj != null) {
				int lat = (int) oldObj.getLaitude(), lon = (int) oldObj
						.getLongtitude();
				if (taskPool.containsKey(lat)
						&& taskPool.get(lat).containsKey(lon)) {
					taskPool.get(lat).get(lon).remove(oldObj);
				}
				oldObj = null;
			}
			logger.info("<<< Exit deleteOldSellerItem.");
		} catch (Exception e) {
			throw e;
		}
	}
	/*
	 * @Override public IDNameResponse sellerPostItem(Long sellerId, double lon,
	 * double lat, String status, Long placeId, String desc, String url) throws
	 * Exception { Connection conn = null; Statement stmt = null; ResultSet rs =
	 * null; try { logger.info(">>> Enter postItem."); String uuid =
	 * String.valueOf(UUID.randomUUID()); if(status.length() > 64 ||
	 * url.length()>512 || desc.length() > 256) throw new Exception
	 * (String.format("String size too large, status = %s, " +
	 * "desc = %s, url = %s", status, desc, url)); String sql = String .format(
	 * "insert into mmkms.dim_seller_item (seller_id, longtitude, latitude, "
	 * +" status, place_id, description, img_url, hashid)" +
	 * " values (%s, %s, %s, '%s', %s, '%s', '%s', '%s');", sellerId, lon, lat,
	 * status, placeId, desc, url, uuid); MySQLManager.runUpdateSql(sql); conn =
	 * SnapMySQLManager.getConnection(); logger.info("Get connected"); stmt =
	 * conn.createStatement(); sql =
	 * String.format("select item_id from mmkms.dim_seller_item where hashid='%s' "
	 * , uuid); logger.info("About to execute " + sql); rs =
	 * stmt.executeQuery(sql); logger.info("Finish sql "); long itemId = -1;
	 * while (rs.next()) { itemId = rs.getLong("item_id"); } return new
	 * IDNameResponse(HttpStatus.SC_OK, "item saved with id = "+itemId, itemId);
	 * } catch (Exception e) { logger.severe(e.getMessage()); throw e; } finally
	 * { MySQLManager.closeConnection(conn, stmt, rs); } }
	 */

}
