package service.olderManage;

import java.util.Date;

import domain.olderManage.PensionHospitalizeregister;

public class PensionHospitalizeregisterExtend extends PensionHospitalizeregister {

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
	 * 就医事项类型名
	 */
	private String type;
	/**
	 * 就医出发时间 （其父类有一个leavetime 为离院时间）
	 */
	private Date leaveTime;
	/**
	 * 负责人姓名
	 */
	public String managerName;
	
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}

	public Long getFloorId() {
		return floorId;
	}

	public void setBedId(Long bedId) {
		this.bedId = bedId;
	}

	public Long getBedId() {
		return bedId;
	}

	public void setBuildId(Long buildId) {
		this.buildId = buildId;
	}

	public Long getBuildId() {
		return buildId;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getBedName() {
		return bedName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Date getLeaveTime() {
		return leaveTime;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	
}
