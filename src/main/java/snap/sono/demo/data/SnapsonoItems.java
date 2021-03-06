package snap.sono.demo.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SnapsonoItems {
	private List<SnapsonoItem> list;
	
	public SnapsonoItems(){
		list = new ArrayList<SnapsonoItem>();
	}
	
	public SnapsonoItems(List<SnapsonoItem>list){
		this.list = list;
	}
	
	@XmlElement(nillable = false, required = false)
	public List<SnapsonoItem> getList(){
		return this.list;
	}
}
