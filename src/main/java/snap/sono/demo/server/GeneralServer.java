package snap.sono.demo.server;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import snap.sono.demo.data.BaseResponse;
import snap.sono.demo.data.BasicIDNameOBJ;
import snap.sono.demo.data.SnapProfilesBO;
import snap.sono.demo.data.SnapsonoItems;

public interface GeneralServer {
	public SnapProfilesBO getSnapProfilesBO();

	public BasicIDNameOBJ getLoginInfo(Long fbID);

	public BaseResponse postItem(String itemName, Long ownerId, String status,
			String comments, String figUrl, double price, String other);
	
	public SnapsonoItems getItems(String itemIds);
}
