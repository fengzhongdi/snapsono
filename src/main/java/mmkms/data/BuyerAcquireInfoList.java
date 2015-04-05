package mmkms.data;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BuyerAcquireInfoList {
	private List<BuyerAcquireInfo> list ;
	public BuyerAcquireInfoList(){
		
	}
	
	public BuyerAcquireInfoList(List<BuyerAcquireInfo> list){
		this.list = list;
	}

	@XmlElement(nillable = false, required = false)
	public List<BuyerAcquireInfo> getBuyerList(){
		return this.list;
	}
	
}
