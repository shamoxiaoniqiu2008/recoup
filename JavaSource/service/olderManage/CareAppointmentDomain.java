package service.olderManage;

import domain.nurseManage.PensionCareAppointment;

public class CareAppointmentDomain extends PensionCareAppointment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean signFlag;
	private boolean generFlag;
	private boolean sendF;

	private String buildName;
	private String floorName;
	private String roomName;
	private String bedName;
	private String respNurseName;
	private String executeNurseName;
	private Long executeNurseId;
	private Long daycareId;


	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getBedName() {
		return bedName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getRespNurseName() {
		return respNurseName;
	}

	public void setRespNurseName(String respNurseName) {
		this.respNurseName = respNurseName;
	}

	public String getExecuteNurseName() {
		return executeNurseName;
	}

	public void setExecuteNurseName(String executeNurseName) {
		this.executeNurseName = executeNurseName;
	}

	public boolean isSignFlag() {
		return signFlag;
	}

	public void setSignFlag(boolean signFlag) {
		this.signFlag = signFlag;
	}

	public boolean isGenerFlag() {
		return generFlag;
	}

	public void setGenerFlag(boolean generFlag) {
		this.generFlag = generFlag;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setSendF(boolean sendF) {
		this.sendF = sendF;
	}

	public boolean isSendF() {
		return sendF;
	}

	public void setExecuteNurseId(Long executeNurseId) {
		this.executeNurseId = executeNurseId;
	}

	public Long getExecuteNurseId() {
		return executeNurseId;
	}

	public void setDaycareId(Long daycareId) {
		this.daycareId = daycareId;
	}

	public Long getDaycareId() {
		return daycareId;
	}

}
