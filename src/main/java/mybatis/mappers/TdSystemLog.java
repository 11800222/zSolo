package mybatis.mappers;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * td_system_log td_system_log
 * @author ares-maven-plugin
 */
public class TdSystemLog implements Serializable {
	private static final long serialVersionUID = 1L;

	public TdSystemLog() {
	}

	public TdSystemLog(String id, String notes) {
		this.id = id;
		this.notes = notes;
	}

	private String id;

	private String busid;
	private String businessSerial;

	private Date beginTime;

	private Date endTime;

	private String direction;

	private Date insert;

	private Date update;

	private String notes;

	private String channel;

	private String mobile;

	private String custid;

	private String request;

	private String response;
	private String ipaddr;

	private String retcode;

	private String errmsg;

	/**
	 * <p>ID</p>
	 * <p>唯一标识</p>
	 */
	public String getId() {
		return id;
	}

	/**
	 * <p>ID</p>
	 * <p>唯一标识</p>
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * <p>BUSID</p>
	 * <p>业务ID</p>
	 */
	public String getBusid() {
		return busid;
	}

	/**
	 * <p>BUSID</p>
	 * <p>业务ID</p>
	 */
	public void setBusid(String busid) {
		this.busid = busid;
	}

	/**
	 * <p>BUSINESS_SERIAL</p>
	 * <p>业务流水号</p>
	 */
	public String getBusinessSerial() {
		return businessSerial;
	}

	/**
	 * <p>BUSINESS_SERIAL</p>
	 * <p>业务流水号</p>
	 */
	public void setBusinessSerial(String businessSerial) {
		this.businessSerial = businessSerial;
	}

	/**
	 * <p>BEGIN_TIME</p>
	 * <p>开始时间</p>
	 */
	public Date getBeginTime() {
		return beginTime;
	}

	/**
	 * <p>BEGIN_TIME</p>
	 * <p>开始时间</p>
	 */
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * <p>END_TIME</p>
	 * <p>结束时间</p>
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * <p>END_TIME</p>
	 * <p>结束时间</p>
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * <p>DIRECTION</p>
	 * <p>系统方向. OUT2IN：流量平台调外部接口；</p>
	 * <p>IN2OUT：外部系统调用流量平台服务;</p>
	 * <p>IN2IN：流量平台内部接口调用</p>
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * <p>DIRECTION</p>
	 * <p>系统方向. OUT2IN：流量平台调外部接口；</p>
	 * <p>IN2OUT：外部系统调用流量平台服务;</p>
	 * <p>IN2IN：流量平台内部接口调用</p>
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * <p>insert</p>
	 */
	public Date getInsert() {
		return insert;
	}

	/**
	 * <p>insert</p>
	 */
	public void setInsert(Date insert) {
		this.insert = insert;
	}

	/**
	 * <p>update</p>
	 */
	public Date getUpdate() {
		return update;
	}

	/**
	 * <p>update</p>
	 */
	public void setUpdate(Date update) {
		this.update = update;
	}

	/**
	 * <p>NOTES</p>
	 * <p>备注</p>
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * <p>NOTES</p>
	 * <p>备注</p>
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * <p>CHANNEL</p>
	 * <p>渠道编码</p>
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * <p>CHANNEL</p>
	 * <p>渠道编码</p>
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * <p>MOBILE</p>
	 * <p>手机号码</p>
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * <p>MOBILE</p>
	 * <p>手机号码</p>
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * <p>CUSTID</p>
	 * <p>客户号码</p>
	 */
	public String getCustid() {
		return custid;
	}

	/**
	 * <p>CUSTID</p>
	 * <p>客户号码</p>
	 */
	public void setCustid(String custid) {
		this.custid = custid;
	}

	/**
	 * <p>REQUEST</p>
	 * <p>请求信息</p>
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * <p>REQUEST</p>
	 * <p>请求信息</p>
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * <p>RESPONSE</p>
	 * <p>回应信息</p>
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * <p>RESPONSE</p>
	 * <p>回应信息</p>
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	/**
	 * <p>IPADDR</p>
	 * <p>IP</p>
	 */
	public String getIpaddr() {
		return ipaddr;
	}

	/**
	 * <p>IPADDR</p>
	 * <p>IP</p>
	 */
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	/**
	 * <p>RETCODE</p>
	 * <p>响应状态</p>
	 */
	public String getRetcode() {
		return retcode;
	}

	/**
	 * <p>RETCODE</p>
	 * <p>响应状态</p>
	 */
	public void setRetcode(String retcode) {
		this.retcode = retcode;
	}

	/**
	 * <p>ERRMSG</p>
	 * <p>错误原因</p>
	 */
	public String getErrmsg() {
		return errmsg;
	}

	/**
	 * <p>ERRMSG</p>
	 * <p>错误原因</p>
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Map<String, Serializable> convertToMap() {
		HashMap<String, Serializable> map = new HashMap<String, Serializable>();
		map.put("id", id);
		map.put("busid", busid);
		map.put("businessSerial", businessSerial);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("direction", direction);
		map.put("insert", insert);
		map.put("update", update);
		map.put("notes", notes);
		map.put("channel", channel);
		map.put("mobile", mobile);
		map.put("custid", custid);
		map.put("request", request);
		map.put("response", response);
		map.put("ipaddr", ipaddr);
		map.put("retcode", retcode);
		map.put("errmsg", errmsg);
		return map;
	}

	@Override
	public String toString() {
		return getClass().getName() + "@" + Integer.toHexString(hashCode()) + "[" + "serialVersionUID=" + serialVersionUID + "id=" + id + "busid=" + busid + "businessSerial=" + businessSerial + "beginTime=" + beginTime + "endTime=" + endTime + "direction=" + direction + "insert=" + insert + "update=" + update + "notes=" + notes + "channel=" + channel + "mobile=" + mobile + "custid=" + custid + "request=" + request + "response=" + response + "ipaddr=" + ipaddr + "retcode=" + retcode + "errmsg=" + errmsg + "]";
	}

	public void fillDefaultValues() {
		if (id == null)
			id = "";
		if (busid == null)
			busid = "";
		if (businessSerial == null)
			businessSerial = "";
		if (beginTime == null)
			beginTime = new Date();
		if (endTime == null)
			endTime = new Date();
		if (direction == null)
			direction = "";
		if (insert == null)
			insert = new Date();
		if (update == null)
			update = new Date();
		if (notes == null)
			notes = "";
		if (channel == null)
			channel = "";
		if (mobile == null)
			mobile = "";
		if (custid == null)
			custid = "";
		if (request == null)
			request = "";
		if (response == null)
			response = "";
		if (ipaddr == null)
			ipaddr = "";
		if (retcode == null)
			retcode = "";
		if (errmsg == null)
			errmsg = "";
	}
}