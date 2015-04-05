package mmkms.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SellerPostObj {
	private double latitude;
	private double longtitude;
	private long sellerId;
	private long placeId;
	private String status;
	private String imgUrl;
	private String description;
	private List<BuyerAcquireInfo> buyerList;
	private long finalBuyer;

	public SellerPostObj() {
		buyerList = new ArrayList<BuyerAcquireInfo>();
	}

	public SellerPostObj(long sellerId, double latitude, double longtitude,
			long placeId, String status, String description, String imgUrl) {
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.sellerId = sellerId;
		this.placeId = placeId;
		this.status = status;
		this.imgUrl = imgUrl;
		this.description = description;
		this.buyerList = new ArrayList<BuyerAcquireInfo>();
	}
	
	public SellerPostObj(long sellerId, double latitude, double longtitude,
			long placeId, String status, String description, String imgUrl, List<BuyerAcquireInfo> buyerList) {
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.sellerId = sellerId;
		this.placeId = placeId;
		this.status = status;
		this.imgUrl = imgUrl;
		this.description = description;
		this.buyerList = buyerList;
	}

	@XmlElement(nillable = false, required = false)
	public double getLaitude() {
		return this.latitude;
	}

	@XmlElement(nillable = false, required = false)
	public double getLongtitude() {
		return this.longtitude;
	}

	@XmlElement(nillable = false, required = false)
	public Long getSellerId() {
		return this.sellerId;
	}

	@XmlElement(nillable = false, required = false)
	public Long getPlaceId() {
		return this.placeId;
	}

	@XmlElement(nillable = false, required = false)
	public String getStatus() {
		return this.status;
	}

	@XmlElement(nillable = false, required = false)
	public String getImgUrl() {
		return this.imgUrl;
	}

	@XmlElement(nillable = false, required = false)
	public String getDesc() {
		return this.description;
	}
	@XmlElement(nillable = false, required = false)
	public List<BuyerAcquireInfo> getBuyerList(){
		return this.buyerList;
	}

	@XmlElement(nillable = false, required = false)
	public long getFinalBuyer(){
		return this.finalBuyer;
	}
	public void addBuyer(BuyerAcquireInfo buyerInfo){
		this.buyerList.add(buyerInfo);
	}
	
	public BuyerAcquireInfo getBuyer(long buyerID){
		for(BuyerAcquireInfo buyerAcquireInfo:this.buyerList){
			if(buyerAcquireInfo.getBuyerId() == buyerID){
				return buyerAcquireInfo;
			}
		}
		return null;
	}

}
