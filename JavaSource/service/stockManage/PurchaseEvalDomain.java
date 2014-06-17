package service.stockManage;

import domain.stockManage.PensionPurchaseEvaluate;

public class PurchaseEvalDomain extends PensionPurchaseEvaluate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 审核部门
	 */
	private String deptName;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
