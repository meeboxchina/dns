package cn.meebox.dns;

public class IPv4 {
	private int id;
	private String registry;
	private String cc;
	private String recordtype;
	private java.net.InetAddress start;
	private int count;
	private String receiveddate;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegistry() {
		return registry;
	}
	public void setRegistry(String registry) {
		this.registry = registry;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getRecordtype() {
		return recordtype;
	}
	public void setRecordtype(String recordtype) {
		this.recordtype = recordtype;
	}
	public java.net.InetAddress getStart() {
		return start;
	}
	public void setStart(java.net.InetAddress start) {
		this.start = start;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getReceiveddate() {
		return receiveddate;
	}
	public void setReceiveddate(String receiveddate) {
		this.receiveddate = receiveddate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
