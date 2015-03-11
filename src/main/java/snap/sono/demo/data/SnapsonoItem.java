package snap.sono.demo.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SnapsonoItem {
	private long itemID;
	private String itemName;
	private long ownerID;
	private String status;
	private String comments;
	private String figURL;
	private double price;
	private String other;
	public SnapsonoItem(){
		
	}
	public SnapsonoItem(long itemID,String itemName,long ownerID,String status,String comments,String figURL,double price,String other){
		this.itemID = itemID;
		this.itemName = itemName;
		this.ownerID = ownerID;
		this.status = status;
		this.comments = comments;
		this.figURL = figURL;
		this.price = price;
		this.other = other;
	}
	
	@XmlElement(nillable = false, required = false)
	public long getItemID(){
		return this.itemID;
	}
	
	@XmlElement(nillable = false, required = false)
	public String getItemName(){
		return this.itemName;
	}
	
	@XmlElement(nillable = false, required = false)
	public long getOwnerID(){
		return this.ownerID;
	}
	
	@XmlElement(nillable = false, required = false)
	public String getStatus(){
		return this.status;
	}
	
	@XmlElement(nillable = false, required = false)
	public String getComments(){
		return this.comments;
	}
	
	@XmlElement(nillable = false, required = false)
	public String getFigURL(){
		return this.figURL;
	}
	
	@XmlElement(nillable = false, required = false)
	public double getPrice(){
		return this.price;
	}
	
	@XmlElement(nillable = false, required = false)
	public String getOther(){
		return this.other;
	}
	
}
