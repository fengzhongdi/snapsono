package snap.sono.demo.rest;

import java.util.ArrayList;
import java.util.List;
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

import snap.sono.demo.data.BaseResponse;
import snap.sono.demo.data.BasicIDNameOBJ;
import snap.sono.demo.data.NameLocation;
import snap.sono.demo.data.NameLocations;
import snap.sono.demo.data.SnapProfile;
import snap.sono.demo.data.SnapProfilesBO;
import snap.sono.demo.data.SnapsonoItems;
import snap.sono.demo.data.TransactionCreateResponse;
import snap.sono.demo.impl.GeneralImpl;
import snap.sono.demo.server.GeneralServer;


@XmlRootElement
@Path("/snapsono/")
public class RestEndPoint {
	GeneralServer server = new GeneralImpl();
	private static Logger logger = Logger.getLogger("RestEndPoint");
	
	@GET
    @Path("/dbaccess/firstTry")
	@Produces({MediaType.APPLICATION_JSON})
	//@Produces({ MediaType.APPLICATION_JSON })
    public SnapProfilesBO sayHelloList() throws Exception{
		return server.getSnapProfilesBO();
    }
	
	@GET
    @Path("/listItems")
	@Produces({MediaType.APPLICATION_JSON})
	//@Produces({ MediaType.APPLICATION_JSON })
    public SnapsonoItems getItems(@QueryParam("item_ids") String item_ids) throws Exception{
		logger.info(String.format("### checking parmeters: item_ids=%s", item_ids));
		return server.getItems(item_ids);
    }
	
	@GET
    @Path("/hello")
	@Produces({MediaType.APPLICATION_JSON})
	//@Produces({ MediaType.APPLICATION_JSON })
    public NameLocations getNameLocations() throws Exception{
		ArrayList<NameLocation> list = new ArrayList<NameLocation>();
		list.add(new NameLocation("kexi",20,20));
		return new NameLocations(list);
    }
	
	
	@POST
	@Path("/getlogin")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON })
    public BasicIDNameOBJ getLoginInfo(@FormParam("fb_id") String fbId) throws Exception{
		logger.info(">>>Start checking fb_id: " +fbId);
		try{
			Long fbID = Long.parseLong(fbId);
			return server.getLoginInfo(fbID);
		}catch(Exception ex){
			throw ex;
		}
    }

	@POST
	@Path("/post_item")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON })
    public BaseResponse postItem(@FormParam("item_name") String itemName,
    		@FormParam("owner_id") String ownerId,
    		@FormParam("status") String status,
    		@FormParam("comments") String comments,
    		@FormParam("fig_url") String figUrl,
    		@FormParam("price") String price,
    		@FormParam("other") String other
    		) throws Exception{
		logger.info(String.format("### checking parmeters: item_name=%s, owner_id=%s, "
				+"status = %s, comments=%s, fig_url=%s, price=%s, other=%s", itemName,
				ownerId,status,comments,figUrl,price,other));
		try{
			Long ownerID = Long.parseLong(ownerId);
			Double dPrice = Double.parseDouble(price);
			return server.postItem(itemName, ownerID, status, comments, figUrl, dPrice, other);
		}catch(Exception ex){
			throw ex;
		}
    }
	
	@POST
	@Path("/transaction/create")
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON })
    public TransactionCreateResponse createTransaction(@FormParam("seller_id") String sellerId,
    		@FormParam("buyer_id") String buyerId,
    		@FormParam("latitude") String latitude,
    		@FormParam("longtitude") String longtitude,
    		@FormParam("amount") String amount,
    		@FormParam("itemLists") String itemLists
    		) throws Exception{
		logger.info(String.format("### checking parmeters: seller_id=%s, buyer_id=%s, "
				+"latitude = %s, longtitude=%s, amount=%s, itemLists=%s", sellerId,
				buyerId,latitude,longtitude,amount,itemLists));
		try{
			return server.createTransaction(Long.parseLong(sellerId),
					Long.parseLong(sellerId),
					Double.parseDouble(latitude),
					Double.parseDouble(longtitude),
					Double.parseDouble(amount),
					itemLists);
		}catch(Exception ex){
			throw ex;
		}
    }

}
