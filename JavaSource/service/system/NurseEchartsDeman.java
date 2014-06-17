package service.system;


//导航页echarts饼状图显示当前护理员护理的员工
public class NurseEchartsDeman {

	private Long olderId;
	
	private String olderName;
	
	private String bulidingName;
	
	private String floorName;
	
	private String roomName;
	
	private Integer nurseFlag;//1--需要护理；2--不需护理
	
	private String details;
	
	private String photoUrl;

	public Long getOlderId() {
		return olderId;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getBulidingName() {
		return bulidingName;
	}

	public void setBulidingName(String bulidingName) {
		this.bulidingName = bulidingName;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	

	public Integer getNurseFlag() {
		return nurseFlag;
	}

	public void setNurseFlag(Integer nurseFlag) {
		this.nurseFlag = nurseFlag;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	
	
	
}
