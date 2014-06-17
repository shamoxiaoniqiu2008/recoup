package service.olderManage;

import java.util.Date;

import domain.olderManage.PensionLivingnotify;

public class PensionNoticeDomain extends PensionLivingnotify {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String eventName;
	private String handleName;
	private boolean handleFlag;
	private String checkName;
	private String checkResultStr;
	private boolean selectFlag;
	private String deptName;
	private String olderName;
	private Long olderId;
	private String bedName;
	private String floorName;
	private String buildName;
	private Date visitTime;

	public String getBedName() {
		return bedName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	private String roomName;

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getCheckResultStr() {
		return checkResultStr;
	}

	public void setCheckResultStr(String checkResultStr) {
		this.checkResultStr = checkResultStr;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventName() {
		return eventName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}

	public String getHandleName() {
		return handleName;
	}

	public void setHandleFlag(boolean handleFlag) {
		this.handleFlag = handleFlag;
	}

	public boolean getHandleFlag() {
		return handleFlag;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setSelectFlag(boolean selectFlag) {
		this.selectFlag = selectFlag;
	}

	public boolean isSelectFlag() {
		return selectFlag;
	}

}
