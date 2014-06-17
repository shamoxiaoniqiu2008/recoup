package service.stockManage;

import domain.stockManage.PensionPurchaseRecord;

public class PurchaseRecordDomain extends PensionPurchaseRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 审核部门
	 */
	private String deptName;
	/**
	 * 审核人
	 */
	private String evaluatorName;
	/**
	 * 审核结果
	 */
	private Integer evaluateResult;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getEvaluatorName() {
		return evaluatorName;
	}

	public void setEvaluatorName(String evaluatorName) {
		this.evaluatorName = evaluatorName;
	}

	public void setEvaluateResult(Integer evaluateResult) {
		this.evaluateResult = evaluateResult;
	}

	public Integer getEvaluateResult() {
		return evaluateResult;
	}


}
