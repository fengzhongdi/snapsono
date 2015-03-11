package snap.sono.demo.server;

import snap.sono.demo.data.BaseResponse; 
import snap.sono.demo.data.BasicIDNameOBJ;
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
}
