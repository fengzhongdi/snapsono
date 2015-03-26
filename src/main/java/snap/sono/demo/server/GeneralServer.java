package snap.sono.demo.server;

import javax.ws.rs.QueryParam;

import mmkms.data.BaseResponse;
import mmkms.data.BasicIDNameOBJ;
import snap.sono.demo.data.SnapProfilesBO;
import snap.sono.demo.data.SnapsonoItems;
import snap.sono.demo.data.TransactionCreateResponse;

public interface GeneralServer {
	public SnapProfilesBO getSnapProfilesBO();

	public BasicIDNameOBJ getLoginInfo(Long fbID);

	public BaseResponse postItem(String itemName, Long ownerId, String status,
			String comments, String figUrl, double price, String other);

	public SnapsonoItems getItems(String itemIds);

	public TransactionCreateResponse createTransaction(Long sellerId, Long userId,
			double latitude, double longtitude, double amount, String itemLists);
	
	public TransactionCreateResponse updateSTransactionStatus(Long transactionId, String status);

	public BaseResponse postRequest(long snap_id, double latitude, double longtitude, String event) throws Exception;
	
	public BaseResponse deleteRequest(String uuid, double latitude, double longtitude) throws Exception;
}
