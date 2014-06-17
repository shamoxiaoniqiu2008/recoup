package service.receptionManage;

import domain.receptionManage.PensionGuestrecord;

public class GuestRecordDomain extends PensionGuestrecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String visitTypeName;
	private  String olderName;
	private String bedName;
	private String relationShip;
	private Long countNo;
	public String getVisitTypeName() {
		return visitTypeName;
	}
	public void setVisitTypeName(String visitTypeName) {
		this.visitTypeName = visitTypeName;
	}
	public String getOlderName() {
		return olderName;
	}
	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}
	public String getBedName() {
		return bedName;
	}
	public void setBedName(String bedName) {
		this.bedName = bedName;
	}
	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}
	public String getRelationShip() {
		return relationShip;
	}
	public void setCountNo(Long countNo) {
		this.countNo = countNo;
	}
	public Long getCountNo() {
		return countNo;
	}

}
