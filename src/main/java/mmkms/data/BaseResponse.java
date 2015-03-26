package mmkms.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseResponse {
	private int httpCode;
	private String message;
	public BaseResponse(){
		
	}
	public BaseResponse(int code, String message){
		this.httpCode = code;
		this.message = message;
	}
	
	@XmlElement(nillable = false, required = false)
	public int getCode(){
		return this.httpCode;
	}
	
	@XmlElement(nillable = false, required = false)
	public String getMessage(){
		return this.message;
	}
}
