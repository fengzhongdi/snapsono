package mmkms.server;

import javax.ws.rs.QueryParam;

import mmkms.data.BaseResponse;
import mmkms.data.BasicIDNameOBJ;
import mmkms.data.IDNameResponse;
import snap.sono.demo.data.SnapProfilesBO;
import snap.sono.demo.data.SnapsonoItems;
import snap.sono.demo.data.TransactionCreateResponse;

public interface GeneralServer {
	
	public BasicIDNameOBJ getLoginInfo(Long fbID, String name) throws Exception;
	
	public IDNameResponse sellerPostItem(Long sellerId, double lon, double lat, String status,
			Long place_id, String desc, String url) throws Exception;

}
