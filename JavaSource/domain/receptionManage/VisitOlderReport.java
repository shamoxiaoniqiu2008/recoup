package domain.receptionManage;

import java.io.Serializable;

public class VisitOlderReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String visitNumber;

	private Long olderCount;

	public void setVisitNumber(String visitNumber) {
		this.visitNumber = visitNumber;
	}

	public String getVisitNumber() {
		return visitNumber;
	}

	public void setOlderCount(Long olderCount) {
		this.olderCount = olderCount;
	}

	public Long getOlderCount() {
		return olderCount;
	}

}