package snap.sono.demo.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestObj {
	private double latitude;
	private double longtitude;
	private long sellerId;
	private long placeId;
	private String status;
	private String imgUrl;
	private String description;

	public RequestObj() {

	}

	public RequestObj(long sellerId, double latitude, double longtitude,
	 long placeId,String status,String description, String imgUrl) {
		this.latitude = latitude;
		this.longtitude = longtitude;this.sellerId = sellerId;
		this.placeId=placeId;
		this.status = status;
		this.imgUrl = imgUrl;
		this.description = description;
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
}
