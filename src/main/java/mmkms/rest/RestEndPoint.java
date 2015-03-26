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
import mmkms.data.IDNameResponse;
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
			logger.info(String.format("value validation, sellerId =%s, lon=%s, lat=%s, status = %s,placeId =%s, "
					+ "description = %s, img_url=%s", sellerId, lon, lat, status, placeId, desc, url));
			return server.sellerPostItem(Long.parseLong(sellerId),
					Double.parseDouble(lon), Double.parseDouble(lat), status,
					Long.parseLong(placeId), desc, url);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw e;
		}
	}
}
