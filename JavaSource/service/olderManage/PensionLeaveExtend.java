package service.olderManage;

import domain.olderManage.PensionLeave;

public class PensionLeaveExtend extends PensionLeave {

	
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
	 * 审批结果
	 */
	private Integer approveresult;
	/**
	 * 多个部门的综合审批结果
	 */
	private Integer approveresults;
	
	
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

	public void setApproveresult(Integer approveresult) {
		this.approveresult = approveresult;
	}

	public Integer getApproveresult() {
		return approveresult;
	}

	public void setApproveresults(Integer approveresults) {
		this.approveresults = approveresults;
	}

	public Integer getApproveresults() {
		return approveresults;
	}

	
}
