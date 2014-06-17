package domain.reportManage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import domain.configureManage.PensionBuilding;

public class OlderLiquidityReport implements Serializable {
	
	private String olderName;
	private String buildingName;
	private String bedName;
	private String bedTypeName;
	private String nurseLevel;
	private String nurseName;
	private Date visitDate;
	private Date leaveDate;
	private String leaveReason;
	private Integer flag;//1 在院 2新入院 3新出院
	
	
	
	
	
	
	
	
	
	
	
	
	public String getOlderName() {
		return olderName;
	}
	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getBedName() {
		return bedName;
	}
	public void setBedName(String bedName) {
		this.bedName = bedName;
	}
	public String getBedTypeName() {
		return bedTypeName;
	}
	public void setBedTypeName(String bedTypeName) {
		this.bedTypeName = bedTypeName;
	}
	public String getNurseLevel() {
		return nurseLevel;
	}
	public void setNurseLevel(String nurseLevel) {
		this.nurseLevel = nurseLevel;
	}
	public String getNurseName() {
		return nurseName;
	}
	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public Date getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(Date leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
	
}