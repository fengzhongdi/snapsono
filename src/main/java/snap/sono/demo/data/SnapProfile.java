package snap.sono.demo.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SnapProfile {
	private int userid;
	private String url;
	
	public SnapProfile(){
		
	}
	
	public SnapProfile(int userid, String url){
		this.userid = userid;
		this.url = url;
	}
	
	@XmlElement(nillable = false, required = false)
	public int getUserid(){
		return this.userid;
	}
	
	@XmlElement(nillable = false, required = false)
	public String getUrl(){
		return this.url;
	}
}
