package snap.sono.demo.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SnapProfilesBO {
	private List<SnapProfile> snapList;
	
	public SnapProfilesBO(){
		snapList = new ArrayList<SnapProfile>();
	}
	
	public SnapProfilesBO(List<SnapProfile> list){
		this.snapList = list;
	}
	
	@XmlElement(nillable = false, required = false)
	public List<SnapProfile> getSnapProfileList(){
		return this.snapList;
	}
	
}
