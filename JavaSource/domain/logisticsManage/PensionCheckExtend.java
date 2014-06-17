package domain.logisticsManage;

public class PensionCheckExtend extends PensionCheck {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4568347326671484372L;
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
	 * 审批结果
	 */
	private Integer approveResult;
	/**
	 * 审批人姓名
	 */
	private String  approverName;
	/**
	 * 审批部门
	 */
	private String  approverDept;
	/**
	 * 拒绝原因
	 */
	private String refuseReason;
	
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
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverDept(String approverDept) {
		this.approverDept = approverDept;
	}
	public String getApproverDept() {
		return approverDept;
	}
	public void setApproveResult(Integer approveResult) {
		this.approveResult = approveResult;
	}
	public Integer getApproveResult() {
		return approveResult;
	}
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
	public String getRefuseReason() {
		return refuseReason;
	}
}
