package mmkms.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import mmkms.data.BaseResponse;

@XmlRootElement
public class TransactionCreateResponse extends BaseResponse{
	private long transactionId;
	public TransactionCreateResponse(){
		super();
	}
	public TransactionCreateResponse(int returnCode, String msg, long transID){	
		super(returnCode,msg);
		this.transactionId = transID;
	}
	
	@XmlElement(nillable = false, required = false)
	public long getTransactionId(){
		return this.transactionId;
	}
}
