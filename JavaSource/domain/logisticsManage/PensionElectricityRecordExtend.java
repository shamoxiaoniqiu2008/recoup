package domain.logisticsManage;

public class PensionElectricityRecordExtend extends PensionElectricityRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 录入人姓名
	 */
	private String inputuserName;
	/**
	 * 房间名
	 */
	private String roomName;
	/**
	 * 房间ID
	 */
	private Long roomId;
	/**
	 * 楼层名
	 */
	private String floorName;
	/**
	 * 大厦名
	 */
	private String buildName;
	/**
	 * 电表名称
	 */
	private String ammeterName;
	
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
	public void setInputuserName(String inputuserName) {
		this.inputuserName = inputuserName;
	}
	public String getInputuserName() {
		return inputuserName;
	}
	public void setAmmeterName(String ammeterName) {
		this.ammeterName = ammeterName;
	}
	public String getAmmeterName() {
		return ammeterName;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}
	public Long getRoomId() {
		return roomId;
	}

}
