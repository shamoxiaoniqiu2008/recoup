package domain.logisticsManage;

public class PensionCheckApproveExtend extends PensionCheckApprove {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 审批人姓名
	 */
	private String approverName;
	/**
	 * 审批部门名称
	 */
	private String deptName;
	
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
