package mmkms.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IDNameResponse extends BaseResponse{
	
	private Long id = null;
	private String name = null;
	
	public IDNameResponse(){
		
	}
	
	public IDNameResponse(int code, String message){
		super(code, message);
	}
	
	public IDNameResponse(int code, String message, long id, String name){
		super(code, message);
		this.id = id;
		this.name = name;
	}
	
	public IDNameResponse(int code, String message, long id){
		super(code, message);
		this.id = id;
	}
	
	public IDNameResponse(int code, String message, String name){
		super(code, message);
		this.name = name;
	}
	
	@XmlElement(nillable = false, required = false)
	public Long getID(){
		return this.id;
	}
	
	@XmlElement(nillable = false, required = false)
	public String getName(){
		return this.name;
	}

}
