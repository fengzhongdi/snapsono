package snap.sono.demo.data;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListAndString {
	private  List<String> list;
	private String name = "name";
	
	public ListAndString(){
		
	}
	
	public ListAndString(List<String> list, String name){
		this.list = list;
		this.name = name;
	}

}
