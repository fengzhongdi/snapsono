package snap.sono.demo.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NameLocation {
	private  String name;
	private  int  latitude;
	private  int longitude;
	
	public NameLocation(){
		
	}
	
	public NameLocation(String name, int la, int lo){
		this.name = name;
		this.latitude = la;
		this.longitude = lo;
	}
	
	@XmlElement(nillable = false, required = false)
	public String getName(){
		return this.name;
	}
	
	@XmlElement(nillable = false, required = false)
	public int getLo(){
		return this.longitude;
	}

	@XmlElement(nillable = false, required = false)
	public int getla(){
		return this.latitude;
	}
}

