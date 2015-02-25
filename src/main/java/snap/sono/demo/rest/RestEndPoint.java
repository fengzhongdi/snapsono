package snap.sono.demo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
import snap.sono.demo.data.SnapProfilesBO;
import snap.sono.demo.impl.GeneralImpl;
import snap.sono.demo.server.GeneralServer;


@XmlRootElement
@Path("/snapsono/")
public class RestEndPoint {
	GeneralServer server = new GeneralImpl();
	private Logger logger = Logger.getLogger("RestEndPoint");
	
	
	@GET
    @Path("/dbaccess/firstTry")
	@Produces({MediaType.APPLICATION_JSON})
	//@Produces({ MediaType.APPLICATION_JSON })
    public SnapProfilesBO sayHelloList() throws Exception{
		return server.getSnapProfilesBO();
    }


}
