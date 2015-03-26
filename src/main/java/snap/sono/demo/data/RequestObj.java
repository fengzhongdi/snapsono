package snap.sono.demo.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestObj {
	private double latitude;
	private double longtitude;
	private long snap_id;
	private String event;
	private String uuid;

	public RequestObj() {

	}

	public RequestObj(double latitude, double longtitude, long snap_id,
			String event, String uuid) {
		this.latitude = latitude;
		this.longtitude = longtitude;
		this.snap_id = snap_id;
		this.event = event;
		this.uuid = uuid;
	}

	@XmlElement(nillable = false, required = false)
	public double getLaitude() {
		return this.latitude;
	}

	@XmlElement(nillable = false, required = false)
	public double getLongtitude() {
		return this.longtitude;
	}

	@XmlElement(nillable = false, required = false)
	public Long getSnapId() {
		return this.snap_id;
	}

	@XmlElement(nillable = false, required = false)
	public String getEvent() {
		return this.event;
	}
	@XmlElement(nillable = false, required = false)
	public String getUUID() {
		return this.uuid;
	}
}
