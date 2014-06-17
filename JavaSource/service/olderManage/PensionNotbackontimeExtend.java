package service.olderManage;

import java.util.Date;

import domain.olderManage.PensionNotbackontime;

public class PensionNotbackontimeExtend extends PensionNotbackontime {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 床位Id
	 */
	private Long bedId;
	/**
	 * 床位名
	 */
	private String bedName;
	/**
	 * 房间id 
	 */
	private	Long roomId;
	/**
	 * 房间名
	 */
	private String roomName;
	/**
	 * 楼层Id
	 */
	private Long floorId;
	/**
	 * 楼层名
	 */
	private String floorName;
	/**
	 * 大厦Id
	 */
	private Long buildId;
	/**
	 * 大厦名
	 */
	private String buildName;
	/**
	 * 老人姓名
	 */
	private String olderName;
	/**
	 * 陪同人姓名
	 */
	private String escortname;
	/**
	 * 陪同人联系方式
	 */
	private String escortphone;
	/**
	 * 真实离院时间
	 */
	private Date realleavetime;
	
	
	
	
	public void setBedId(Long bedId) {
		this.bedId = bedId;
	}
	public Long getBedId() {
		return bedId;
	}
	public void setBedName(String bedName) {
		this.bedName = bedName;
	}
	public String getBedName() {
		return bedName;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public Long getRoomId() {
		return roomId;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}
	public Long getFloorId() {
		return floorId;
	}
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	public String getFloorName() {
		return floorName;
	}
	public void setBuildId(Long buildId) {
		this.buildId = buildId;
	}
	public Long getBuildId() {
		return buildId;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}
	public String getOlderName() {
		return olderName;
	}
	public void setEscortname(String escortname) {
		this.escortname = escortname;
	}
	public String getEscortname() {
		return escortname;
	}
	public void setRealleavetime(Date realleavetime) {
		this.realleavetime = realleavetime;
	}
	public Date getRealleavetime() {
		return realleavetime;
	}
	public void setEscortphone(String escortphone) {
		this.escortphone = escortphone;
	}
	public String getEscortphone() {
		return escortphone;
	}
	

}
