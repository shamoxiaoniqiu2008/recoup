package service.olderManage;

import domain.olderManage.PensionLeaveapprove;

public class PensionLeaveApproveExtend extends PensionLeaveapprove {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 部门名称
	 */
	private String deptName;

	private String approverName;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproverName() {
		return approverName;
	}
}
