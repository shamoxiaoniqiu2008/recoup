package service.receptionManage;

import domain.receptionManage.PensionApplyevaluate;

public class PensionEvalDomain extends PensionApplyevaluate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resultStr;
	private String deptName;
	private String evaluatorName;
	public String getResultStr() {
		return resultStr;
	}
	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public void setEvaluatorName(String evaluatorName) {
		this.evaluatorName = evaluatorName;
	}
	public String getEvaluatorName() {
		return evaluatorName;
	}
}
