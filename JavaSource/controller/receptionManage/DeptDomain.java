package controller.receptionManage;

import domain.system.PensionDept;

public class DeptDomain extends PensionDept {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean deptCheckBox;
	public void setDeptCheckBox(boolean deptCheckBox) {
		this.deptCheckBox = deptCheckBox;
	}
	public boolean getDeptCheckBox() {
		return deptCheckBox;
	}

}
