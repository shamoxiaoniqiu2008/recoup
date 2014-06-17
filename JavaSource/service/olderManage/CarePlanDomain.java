package service.olderManage;

import domain.nurseManage.PensionDaycarePlan;

public class CarePlanDomain extends PensionDaycarePlan {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean signFlag;
	private boolean generFlag;
	private Long nurserId;
	private String nurseName;

	public boolean isSignFlag() {
		return signFlag;
	}

	public void setSignFlag(boolean signFlag) {
		this.signFlag = signFlag;
	}

	public boolean isGenerFlag() {
		return generFlag;
	}

	public void setGenerFlag(boolean generFlag) {
		this.generFlag = generFlag;
	}

	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}

	public String getNurseName() {
		return nurseName;
	}

	public void setNurserId(Long nurserId) {
		this.nurserId = nurserId;
	}

	public Long getNurserId() {
		return nurserId;
	}

}
