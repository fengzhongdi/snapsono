package snap.sono.demo.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BasicIDNameOBJ {
	private long id;
	private String name;
	public BasicIDNameOBJ(){
		
	}
	public BasicIDNameOBJ(long id, String name){
		this.id = id;
		this.name = name;
	}

	@XmlElement(nillable = false, required = false)
	public Long getId(){
		return this.id;
	}

	@XmlElement(nillable = false, required = false)
	public String getName(){
		return this.name;
	}
}
