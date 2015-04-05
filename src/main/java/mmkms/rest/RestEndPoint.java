package mmkms.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import mmkms.data.BaseResponse;
import mmkms.data.BasicIDNameOBJ;
import mmkms.data.BuyerAcquireInfoList;
import mmkms.data.IDNameResponse;
import mmkms.data.SellerPostObjList;
import mmkms.data.TransactionCreateResponse;
import mmkms.impl.GeneralImpl;
import mmkms.server.GeneralServer;

@XmlRootElement
@Path("/mmkms/")
public class RestEndPoint {
	GeneralServer server = new GeneralImpl();
	private static Logger logger = Logger.getLogger("RestEndPoint");

	@GET
	@Path("/login/getinfo")
	@Produces({ MediaType.APPLICATION_JSON })
	// @Produces({ MediaType.APPLICATION_JSON })
	public BasicIDNameOBJ getUserInfo(@QueryParam("fb_id") String fbId,
			@QueryParam("name") String name) throws Exception {
		try {
			logger.info(" >>> Reach rest point getUserInfo");
			logger.info(String.format("value validation, fb_id = %s, name=%s",
					fbId, name));
			return server.getLoginInfo(Long.parseLong(fbId), name);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw e;
		}

	}

	@POST
	@Path("/seller/post_item")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED,
			MediaType.APPLICATION_JSON })
	public IDNameResponse sellerPostItem(
			@FormParam("seller_id") String sellerId,
			@FormParam("longtitude") String lon,
			@FormParam("latitude") String lat,
			@FormParam("status") String status,
			@FormParam("place_id") String placeId,
			@FormParam("description") String desc,
			@FormParam("img_url") String url) throws NumberFormatException,
			Exception {
		try {
			logger.info(" >>> Reach rest point sellerPostItem");
			logger.info(String.format(
					"value validation, sellerId =%s, lon=%s, lat=%s, status = %s,placeId =%s, "
							+ "description = %s, img_url=%s", sellerId, lon,
					lat, status, placeId, desc, url));
			return server.sellerPostItem(Long.parseLong(sellerId),
					Double.parseDouble(lat), Double.parseDouble(lon), status,
					Long.parseLong(placeId), desc, url);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw e;
		}
	}

	@GET
	@Path("/buyer/get_seller_items")
	@Produces({ MediaType.APPLICATION_JSON })
	public SellerPostObjList buyerListSellerItems(
			@QueryParam("buyer_id") String buyerId,
			@QueryParam("latitudeStart") String latitudeStart,
			@QueryParam("latitudeEnd") String latitudeEnd,
			@QueryParam("longtitudeStart") String longtitudeStart,
			@QueryParam("longtitudeEnd") String longtitudeEnd)
			throws NumberFormatException, Exception {
		try {
			logger.info(" >>> Reach rest point buyerListSellerItems");
			logger.info(String.format(
					"value validation, buyerId =%s, lonStart=%s,lonEnd = %s latStart=%s, "
							+ "latend = %ss", buyerId, latitudeStart,
					latitudeEnd, longtitudeStart, longtitudeEnd));
			return server.buyerListSellerItems(Long.parseLong(buyerId),
					Double.parseDouble(latitudeStart),
					Double.parseDouble(latitudeEnd),
					Double.parseDouble(longtitudeStart),
					Double.parseDouble(longtitudeEnd));
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw e;
		}
	}
	
	@GET
	@Path("/seller/get_related_buyers")
	@Produces({ MediaType.APPLICATION_JSON })
	public BuyerAcquireInfoList sellerSeeAllBuyers(
			@QueryParam("seller_id") String sellerId)
			throws NumberFormatException, Exception {
		try {
			logger.info(" >>> Reach rest point sellerSeeAllBuyers");
			logger.info(String.format(
					"value validation, sellerId = %s",sellerId));
			return server.sellerSeeAllBuyers(Long.parseLong(sellerId));
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw e;
		}
	}

	@POST
	@Path("/buyer/place_order_on_seller_items")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED,
			MediaType.APPLICATION_JSON })
	public IDNameResponse buyerPlaceOrderOnSellerItems(
			@FormParam("buyer_id") String buyerId,
			@FormParam("buyer_name") String buyerName,
			@FormParam("sellerIdListStr") String sellerIdListStr,
			@FormParam("bids") String bids,
			@FormParam("comments") String comments)
			throws NumberFormatException, Exception {
		try {
			logger.info(" >>> Reach rest point buyerPlaceOrderOnSellerItems");
			logger.info(String
					.format("value validation, buyerId =%s, buyer_name=%s, sellerIdListStr=%s, bids = %s,comments =%s ",
							buyerId, buyerName, sellerIdListStr, bids, comments));
			return server.buyerPlaceOrderOnSellerItems(Long.parseLong(buyerId),
					buyerName, sellerIdListStr, bids, comments);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw e;
		}
	}
	
	@POST
	@Path("/finalizeTransaction")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED,
			MediaType.APPLICATION_JSON })
	public TransactionCreateResponse finalizeTransaction(
			@FormParam("buyer_id") String buyerId,
			@FormParam("seller_id") String sellerId)
			throws NumberFormatException, Exception {
		try {
			logger.info(" >>> Reach rest point finalizeTransaction");
			logger.info(String
					.format("value validation, buyerId =%s, sellerId=%s",
							buyerId, sellerId));
			return server.finalizeTransaction(Long.parseLong(buyerId),Long.parseLong(sellerId));
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw e;
		}
	}
}
