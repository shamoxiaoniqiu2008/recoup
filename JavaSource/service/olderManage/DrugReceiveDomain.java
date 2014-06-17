package service.olderManage;

import java.util.Date;

import domain.medicalManage.PensionDrugreceiveRecord;

public class DrugReceiveDomain extends PensionDrugreceiveRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean verifyFlag;
	private boolean resultFlag;
	private String relationShip;
	private String deliveryName;
	private Boolean validF;
	private String validFlag;

	private Integer totalAmount;
	private int singleDose;
	private String unit;
	private boolean useFlag;
	private Date shelflifeTime;
	private String drugreceiveName;
	private boolean morningFlag;
	private boolean noonFlag;
	private boolean nightFlag;
	private Long drugReceiveId;

	public boolean getMorningFlag() {
		return morningFlag;
	}

	public void setMorningFlag(boolean morningFlag) {
		this.morningFlag = morningFlag;
	}

	public boolean getNoonFlag() {
		return noonFlag;
	}

	public void setNoonFlag(boolean noonFlag) {
		this.noonFlag = noonFlag;
	}

	public boolean getNightFlag() {
		return nightFlag;
	}

	public void setNightFlag(boolean nightFlag) {
		this.nightFlag = nightFlag;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getSingleDose() {
		return singleDose;
	}

	public void setSingleDose(int singleDose) {
		this.singleDose = singleDose;
	}

	public boolean isUseFlag() {
		return useFlag;
	}

	public void setUseFlag(boolean useFlag) {
		this.useFlag = useFlag;
	}

	public Date getShelflifeTime() {
		return shelflifeTime;
	}

	public void setShelflifeTime(Date shelflifeTime) {
		this.shelflifeTime = shelflifeTime;
	}

	public String getDrugreceiveName() {
		return drugreceiveName;
	}

	public void setDrugreceiveName(String drugreceiveName) {
		this.drugreceiveName = drugreceiveName;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public Boolean getValidF() {
		return validF;
	}

	public void setValidF(Boolean validF) {
		this.validF = validF;
	}

	public boolean getVerifyFlag() {
		return verifyFlag;
	}

	public void setVerifyFlag(boolean verifyFlag) {
		this.verifyFlag = verifyFlag;
	}

	public boolean isResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}

	public void setDrugReceiveId(Long drugReceiveId) {
		this.drugReceiveId = drugReceiveId;
	}

	public Long getDrugReceiveId() {
		return drugReceiveId;
	}

}
