package snap.sono.demo.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NameLocations {
	private List<NameLocation> snapList;
	
	public NameLocations(){
		snapList = new ArrayList<NameLocation>();
	}
	
	public NameLocations(List<NameLocation> list){
		this.snapList = list;
	}
	
	@XmlElement(nillable = false, required = false)
	public List<NameLocation> getSnapProfileList(){
		return this.snapList;
	}
}
