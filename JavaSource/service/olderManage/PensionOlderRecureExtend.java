package service.olderManage;

import domain.olderManage.PensionOlderRecure;

public class PensionOlderRecureExtend extends PensionOlderRecure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String olderName;
	private String recureitemName;
	private String realnurseName;
	private String handlenurseName;
	private String detail;
	private Integer steps;
	public void setRecureitemName(String recureitemName) {
		this.recureitemName = recureitemName;
	}

	public String getRecureitemName() {
		return recureitemName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setRealnurseName(String realnurseName) {
		this.realnurseName = realnurseName;
	}

	public String getRealnurseName() {
		return realnurseName;
	}

	public void setHandlenurseName(String handlenurseName) {
		this.handlenurseName = handlenurseName;
	}

	public String getHandlenurseName() {
		return handlenurseName;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
	}

	public Integer getSteps() {
		return steps;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getDetail() {
		return detail;
	}



}
