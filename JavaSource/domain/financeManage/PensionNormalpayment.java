package domain.financeManage;

import java.io.Serializable;
import java.util.Date;

public class PensionNormalpayment implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.older_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Long olderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.totalFees
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Float totalfees;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.NumberID
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private String numberid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.generateTime
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Date generatetime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.generator_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Long generatorId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.generator_name
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private String generatorName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.isPay
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Integer ispay;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.payee_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Long payeeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.payee_name
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private String payeeName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.notes
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private String notes;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.isClosed
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Integer isclosed;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.closed_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Long closedId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.closed_name
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private String closedName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.closedTime
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Date closedtime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.payTime
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Date paytime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.cleared
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.closeNumber
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Integer closenumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.settled_flag
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Integer settledFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_normalpayment.settle_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private Long settleId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_normalpayment
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.id
	 * @return  the value of pension_normalpayment.id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.id
	 * @param id  the value for pension_normalpayment.id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.older_id
	 * @return  the value of pension_normalpayment.older_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Long getOlderId() {
		return olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.older_id
	 * @param olderId  the value for pension_normalpayment.older_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.totalFees
	 * @return  the value of pension_normalpayment.totalFees
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Float getTotalfees() {
		return totalfees;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.totalFees
	 * @param totalfees  the value for pension_normalpayment.totalFees
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setTotalfees(Float totalfees) {
		this.totalfees = totalfees;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.NumberID
	 * @return  the value of pension_normalpayment.NumberID
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public String getNumberid() {
		return numberid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.NumberID
	 * @param numberid  the value for pension_normalpayment.NumberID
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setNumberid(String numberid) {
		this.numberid = numberid == null ? null : numberid.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.generateTime
	 * @return  the value of pension_normalpayment.generateTime
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Date getGeneratetime() {
		return generatetime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.generateTime
	 * @param generatetime  the value for pension_normalpayment.generateTime
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setGeneratetime(Date generatetime) {
		this.generatetime = generatetime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.generator_id
	 * @return  the value of pension_normalpayment.generator_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Long getGeneratorId() {
		return generatorId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.generator_id
	 * @param generatorId  the value for pension_normalpayment.generator_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setGeneratorId(Long generatorId) {
		this.generatorId = generatorId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.generator_name
	 * @return  the value of pension_normalpayment.generator_name
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public String getGeneratorName() {
		return generatorName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.generator_name
	 * @param generatorName  the value for pension_normalpayment.generator_name
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setGeneratorName(String generatorName) {
		this.generatorName = generatorName == null ? null : generatorName
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.isPay
	 * @return  the value of pension_normalpayment.isPay
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Integer getIspay() {
		return ispay;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.isPay
	 * @param ispay  the value for pension_normalpayment.isPay
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setIspay(Integer ispay) {
		this.ispay = ispay;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.payee_id
	 * @return  the value of pension_normalpayment.payee_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Long getPayeeId() {
		return payeeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.payee_id
	 * @param payeeId  the value for pension_normalpayment.payee_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setPayeeId(Long payeeId) {
		this.payeeId = payeeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.payee_name
	 * @return  the value of pension_normalpayment.payee_name
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public String getPayeeName() {
		return payeeName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.payee_name
	 * @param payeeName  the value for pension_normalpayment.payee_name
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName == null ? null : payeeName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.notes
	 * @return  the value of pension_normalpayment.notes
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.notes
	 * @param notes  the value for pension_normalpayment.notes
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.isClosed
	 * @return  the value of pension_normalpayment.isClosed
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Integer getIsclosed() {
		return isclosed;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.isClosed
	 * @param isclosed  the value for pension_normalpayment.isClosed
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setIsclosed(Integer isclosed) {
		this.isclosed = isclosed;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.closed_id
	 * @return  the value of pension_normalpayment.closed_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Long getClosedId() {
		return closedId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.closed_id
	 * @param closedId  the value for pension_normalpayment.closed_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setClosedId(Long closedId) {
		this.closedId = closedId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.closed_name
	 * @return  the value of pension_normalpayment.closed_name
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public String getClosedName() {
		return closedName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.closed_name
	 * @param closedName  the value for pension_normalpayment.closed_name
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setClosedName(String closedName) {
		this.closedName = closedName == null ? null : closedName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.closedTime
	 * @return  the value of pension_normalpayment.closedTime
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Date getClosedtime() {
		return closedtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.closedTime
	 * @param closedtime  the value for pension_normalpayment.closedTime
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setClosedtime(Date closedtime) {
		this.closedtime = closedtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.payTime
	 * @return  the value of pension_normalpayment.payTime
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Date getPaytime() {
		return paytime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.payTime
	 * @param paytime  the value for pension_normalpayment.payTime
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.cleared
	 * @return  the value of pension_normalpayment.cleared
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.cleared
	 * @param cleared  the value for pension_normalpayment.cleared
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.closeNumber
	 * @return  the value of pension_normalpayment.closeNumber
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Integer getClosenumber() {
		return closenumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.closeNumber
	 * @param closenumber  the value for pension_normalpayment.closeNumber
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setClosenumber(Integer closenumber) {
		this.closenumber = closenumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.settled_flag
	 * @return  the value of pension_normalpayment.settled_flag
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Integer getSettledFlag() {
		return settledFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.settled_flag
	 * @param settledFlag  the value for pension_normalpayment.settled_flag
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setSettledFlag(Integer settledFlag) {
		this.settledFlag = settledFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_normalpayment.settle_id
	 * @return  the value of pension_normalpayment.settle_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public Long getSettleId() {
		return settleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_normalpayment.settle_id
	 * @param settleId  the value for pension_normalpayment.settle_id
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	public void setSettleId(Long settleId) {
		this.settleId = settleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_normalpayment
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		PensionNormalpayment other = (PensionNormalpayment) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getOlderId() == null ? other.getOlderId() == null
						: this.getOlderId().equals(other.getOlderId()))
				&& (this.getTotalfees() == null ? other.getTotalfees() == null
						: this.getTotalfees().equals(other.getTotalfees()))
				&& (this.getNumberid() == null ? other.getNumberid() == null
						: this.getNumberid().equals(other.getNumberid()))
				&& (this.getGeneratetime() == null ? other.getGeneratetime() == null
						: this.getGeneratetime()
								.equals(other.getGeneratetime()))
				&& (this.getGeneratorId() == null ? other.getGeneratorId() == null
						: this.getGeneratorId().equals(other.getGeneratorId()))
				&& (this.getGeneratorName() == null ? other.getGeneratorName() == null
						: this.getGeneratorName().equals(
								other.getGeneratorName()))
				&& (this.getIspay() == null ? other.getIspay() == null : this
						.getIspay().equals(other.getIspay()))
				&& (this.getPayeeId() == null ? other.getPayeeId() == null
						: this.getPayeeId().equals(other.getPayeeId()))
				&& (this.getPayeeName() == null ? other.getPayeeName() == null
						: this.getPayeeName().equals(other.getPayeeName()))
				&& (this.getNotes() == null ? other.getNotes() == null : this
						.getNotes().equals(other.getNotes()))
				&& (this.getIsclosed() == null ? other.getIsclosed() == null
						: this.getIsclosed().equals(other.getIsclosed()))
				&& (this.getClosedId() == null ? other.getClosedId() == null
						: this.getClosedId().equals(other.getClosedId()))
				&& (this.getClosedName() == null ? other.getClosedName() == null
						: this.getClosedName().equals(other.getClosedName()))
				&& (this.getClosedtime() == null ? other.getClosedtime() == null
						: this.getClosedtime().equals(other.getClosedtime()))
				&& (this.getPaytime() == null ? other.getPaytime() == null
						: this.getPaytime().equals(other.getPaytime()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getClosenumber() == null ? other.getClosenumber() == null
						: this.getClosenumber().equals(other.getClosenumber()))
				&& (this.getSettledFlag() == null ? other.getSettledFlag() == null
						: this.getSettledFlag().equals(other.getSettledFlag()))
				&& (this.getSettleId() == null ? other.getSettleId() == null
						: this.getSettleId().equals(other.getSettleId()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_normalpayment
	 * @mbggenerated  Thu Dec 12 16:42:38 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getOlderId() == null) ? 0 : getOlderId().hashCode());
		result = prime * result
				+ ((getTotalfees() == null) ? 0 : getTotalfees().hashCode());
		result = prime * result
				+ ((getNumberid() == null) ? 0 : getNumberid().hashCode());
		result = prime
				* result
				+ ((getGeneratetime() == null) ? 0 : getGeneratetime()
						.hashCode());
		result = prime
				* result
				+ ((getGeneratorId() == null) ? 0 : getGeneratorId().hashCode());
		result = prime
				* result
				+ ((getGeneratorName() == null) ? 0 : getGeneratorName()
						.hashCode());
		result = prime * result
				+ ((getIspay() == null) ? 0 : getIspay().hashCode());
		result = prime * result
				+ ((getPayeeId() == null) ? 0 : getPayeeId().hashCode());
		result = prime * result
				+ ((getPayeeName() == null) ? 0 : getPayeeName().hashCode());
		result = prime * result
				+ ((getNotes() == null) ? 0 : getNotes().hashCode());
		result = prime * result
				+ ((getIsclosed() == null) ? 0 : getIsclosed().hashCode());
		result = prime * result
				+ ((getClosedId() == null) ? 0 : getClosedId().hashCode());
		result = prime * result
				+ ((getClosedName() == null) ? 0 : getClosedName().hashCode());
		result = prime * result
				+ ((getClosedtime() == null) ? 0 : getClosedtime().hashCode());
		result = prime * result
				+ ((getPaytime() == null) ? 0 : getPaytime().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime
				* result
				+ ((getClosenumber() == null) ? 0 : getClosenumber().hashCode());
		result = prime
				* result
				+ ((getSettledFlag() == null) ? 0 : getSettledFlag().hashCode());
		result = prime * result
				+ ((getSettleId() == null) ? 0 : getSettleId().hashCode());
		return result;
	}

	private String isPayStr;
	private String isCloseStr;
	
	private String name;//老人姓名
    private String sex;//性别
    private Date birthDay;//出生日期
    
    private String buildingName;//大厦�?    
    private String floorName;//楼层�?    
    private String roomName;//房间�?    
    private String bedName;//床位�?    
    private String nurseLevel;//护理级别
    private String bedLevel;//床位级别
    private Date beginDate;//入住时间
    
    private String DeptName;//收费部门名称--财务结帐时显示用

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getBedName() {
		return bedName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	


	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getNurseLevel() {
		return nurseLevel;
	}

	public void setNurseLevel(String nurseLevel) {
		this.nurseLevel = nurseLevel;
	}

	public String getBedLevel() {
		return bedLevel;
	}

	public void setBedLevel(String bedLevel) {
		this.bedLevel = bedLevel;
	}


	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}


	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIsPayStr() {
		return isPayStr;
	}

	public void setIsPayStr(String isPayStr) {
		this.isPayStr = isPayStr;
	}

	public String getIsCloseStr() {
		return isCloseStr;
	}

	public void setIsCloseStr(String isCloseStr) {
		this.isCloseStr = isCloseStr;
	}

	public String getDeptName() {
		return DeptName;
	}

	public void setDeptName(String deptName) {
		DeptName = deptName;
	}
    
    
    
    
}