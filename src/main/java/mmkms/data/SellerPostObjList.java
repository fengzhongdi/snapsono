package mmkms.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SellerPostObjList {
	private List<SellerPostObj> list;
	
	public SellerPostObjList(){
		list = new ArrayList<SellerPostObj>();
	}
	
	public SellerPostObjList(List<SellerPostObj> list){
		this.list = list;
	}

	@XmlElement(nillable = false, required = false)
	public List<SellerPostObj> getSellerPostObjList(){
		return this.list;
	}
}
