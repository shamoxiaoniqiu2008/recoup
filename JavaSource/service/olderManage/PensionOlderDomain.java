package service.olderManage;

import java.util.Date;


import domain.olderManage.PensionOlder;

public class PensionOlderDomain extends PensionOlder {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long applyId;
	private String applyName;
	private String relationShip;
	private String phoneNum;
	private Date applyTime;
	private String notes;
	private int applyCleared;
	private Integer applySex;
	private String applySexStr;
	private String olderState;
	private String oldType;
	private String oldSex;
	private Integer evaluateresult;
	private String evaluateNotes;
	private Integer deptId;
	private Long evaluateId;
	private Long sendFlag;
	private String resultStr;
	private String bedName;
	private Long   roomId;
	private String roomName;
	private String floorName;
	private String buildName;
	private String bedType;
	private String nurseType;
	private String medType;
	private String insurance;
	private String nurseName;
	private String contractNum;
	private Date visitTime;
	private String nationName;
	private String politicName;
	private int finalResult;

	public String getBedName() {
		return bedName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getNurseType() {
		return nurseType;
	}

	public void setNurseType(String nurseType) {
		this.nurseType = nurseType;
	}

	public String getMedType() {
		return medType;
	}

	public void setMedType(String medType) {
		this.medType = medType;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getNurseName() {
		return nurseName;
	}

	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getResultStr() {
		return resultStr;
	}

	public void setResultStr(String resultStr) {
		this.resultStr = resultStr;
	}

	public String getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(String relationShip) {
		this.relationShip = relationShip;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyCleared(int applyCleared) {
		this.applyCleared = applyCleared;
	}

	public int getApplyCleared() {
		return applyCleared;
	}

	public void setApplySex(Integer applySex) {
		this.applySex = applySex;
	}

	public Integer getApplySex() {
		return applySex;
	}

	public void setOlderState(String olderState) {
		this.olderState = olderState;
	}

	public String getOlderState() {
		return olderState;
	}

	public void setOldSex(String oldSex) {
		this.oldSex = oldSex;
	}

	public String getOldSex() {
		return oldSex;
	}

	public void setOldType(String oldType) {
		this.oldType = oldType;
	}

	public String getOldType() {
		return oldType;
	}





	public void setApplySexStr(String applySexStr) {
		this.applySexStr = applySexStr;
	}

	public String getApplySexStr() {
		return applySexStr;
	}

	public void setEvaluateNotes(String evaluateNotes) {
		this.evaluateNotes = evaluateNotes;
	}

	public String getEvaluateNotes() {
		return evaluateNotes;
	}

	public void setEvaluateresult(Integer evaluateresult) {
		this.evaluateresult = evaluateresult;
	}

	public Integer getEvaluateresult() {
		return evaluateresult;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setEvaluateId(Long evaluateId) {
		this.evaluateId = evaluateId;
	}

	public Long getEvaluateId() {
		return evaluateId;
	}

	public void setSendFlag(Long sendFlag) {
		this.sendFlag = sendFlag;
	}

	public Long getSendFlag() {
		return sendFlag;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getNationName() {
		return nationName;
	}

	public void setPoliticName(String politicName) {
		this.politicName = politicName;
	}

	public String getPoliticName() {
		return politicName;
	}

	public void setFinalResult(int finalResult) {
		this.finalResult = finalResult;
	}

	public int getFinalResult() {
		return finalResult;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getRoomId() {
		return roomId;
	}

}
