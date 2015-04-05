package mmkms.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BuyerAcquireInfo {
	private long buyerId;
	private String name;
	private double bid;
	private String comment;
	public BuyerAcquireInfo(){
		
	}
	public BuyerAcquireInfo(long buyerId, String name, double bid, String comment){
		this.buyerId = buyerId;
		this.bid = bid;
		this.comment = comment;
		this.name = name;
	}
	
	@XmlElement(nillable = false, required = false)
	public long getBuyerId(){
		return this.buyerId;
	}
	
	@XmlElement(nillable = false, required = false)
	public double getBid(){
		return this.bid;
	}
	
	@XmlElement(nillable = false, required = false)
	public String getComment(){
		return this.comment;
	}
	
	@XmlElement(nillable = false, required = false)
	public String getName(){
		return this.name;
	}

}
