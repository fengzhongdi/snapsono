package snap.sono.demo.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestObjList {
	private List<RequestObj> list = new ArrayList<RequestObj>();
	
	public RequestObjList(){
		
	}
	
	public RequestObjList( List<RequestObj> list){
		this.list = list;
	}
	
	public void add(RequestObj requestObj){
		this.list.add(requestObj);
	}
	
	@XmlElement(nillable = false, required = false)
	public List<RequestObj> getRequestObjList(){
		return this.list;
	}
}
