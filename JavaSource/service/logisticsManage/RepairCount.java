package service.logisticsManage;

public class RepairCount {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 房间名
	 */
	private String roomName;
	/**
	 * 楼层名
	 */
	private String floorName;
	/**
	 * 大厦名
	 */
	private String buildName;
	/**
	 * 老人姓名
	 */
	private String olderName;
	/**
	 * 维修类型
	 */
	private Integer repairType;
	/**
	 * 维修次数
	 */
	private Integer repairCount;
	
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
	public String getOlderName() {
		return olderName;
	}
	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}
	public Integer getRepairCount() {
		return repairCount;
	}
	public void setRepairCount(Integer repairCount) {
		this.repairCount = repairCount;
	}
	public void setRepairType(Integer repairType) {
		this.repairType = repairType;
	}
	public Integer getRepairType() {
		return repairType;
	}
	
}
