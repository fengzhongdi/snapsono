package mmkms.server;

import mmkms.data.BasicIDNameOBJ;
import mmkms.data.BuyerAcquireInfoList;
import mmkms.data.IDNameResponse;
import mmkms.data.SellerPostObjList;
import mmkms.data.TransactionCreateResponse;

public interface GeneralServer {
	
	public BasicIDNameOBJ getLoginInfo(Long fbID, String name) throws Exception;
	
	public IDNameResponse sellerPostItem(Long sellerId, double lat, double lon, String status,
			Long place_id, String desc, String url) throws Exception;

	SellerPostObjList buyerListSellerItems(Long buyerId, double latitudeStart,
			double latitudeEnd, double longtitudeStart, double longtitudeEnd)
			throws Exception;


	IDNameResponse buyerPlaceOrderOnSellerItems(Long buyerId, String buyerName,
			String sellerIdListStr, String bids, String comments)
			throws Exception;

	BuyerAcquireInfoList sellerSeeAllBuyers(Long sellId) throws Exception;

	TransactionCreateResponse finalizeTransaction(Long buyerId, Long sellerId)
			throws Exception;

}
