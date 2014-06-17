package domain.logisticsManage;

public class PensionAmmeterExtend extends PensionAmmeter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Long getFloorId() {
		return floorId;
	}
	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}
	public String getFloorName() {
		return floorName;
	}
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	public Long getBuildId() {
		return buildId;
	}
	public void setBuildId(Long buildId) {
		this.buildId = buildId;
	}
	public String getBuildName() {
		return buildName;
	}
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
}
