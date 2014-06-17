package service.olderManage;

import domain.olderManage.PensionLivingrecord;

public class LivingRecordDomain extends PensionLivingrecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nationName;
	private String politicName;
	private String nurseName;
	private String nurseLevel;
	private String bedType;
	private String sexStr;
	private String olderType;
	private String olderState;
	private String bedName;
	private String roomName;
	private String floorName;
	private String buildName;
	private String olderName;
	private String eventName;
	private int sex;
	private int types;
	private int statuses;
	private int pensionCategary;

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getTypes() {
		return types;
	}

	public void setTypes(int types) {
		this.types = types;
	}

	public int getStatuses() {
		return statuses;
	}

	public void setStatuses(int statuses) {
		this.statuses = statuses;
	}

	public String getBedName() {
		return bedName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
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

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getPoliticName() {
		return politicName;
	}

	public void setPoliticName(String politicName) {
		this.politicName = politicName;
	}

	public String getNurseLevel() {
		return nurseLevel;
	}

	public void setNurseLevel(String nurseLevel) {
		this.nurseLevel = nurseLevel;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getNurseName() {
		return nurseName;
	}

	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}

	public void setSexStr(String sexStr) {
		this.sexStr = sexStr;
	}

	public String getSexStr() {
		return sexStr;
	}

	public void setOlderType(String olderType) {
		this.olderType = olderType;
	}

	public String getOlderType() {
		return olderType;
	}

	public void setOlderState(String olderState) {
		this.olderState = olderState;
	}

	public String getOlderState() {
		return olderState;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getEventName() {
		return eventName;
	}

	public int getPensionCategary() {
		return pensionCategary;
	}

	public void setPensionCategary(int pensionCategary) {
		this.pensionCategary = pensionCategary;
	}

}
