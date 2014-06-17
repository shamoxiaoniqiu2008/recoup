package domain.activityManage;

import java.io.Serializable;
import java.util.Date;

public class PensionAttendolder implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.activity_id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Long activityId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.older_id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Long olderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.enlistTime
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Date enlisttime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.isAttend
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Integer isattend;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.attended
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Integer attended;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.reason
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private String reason;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.appraise
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Integer appraise;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.checker_id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Long checkerId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.checkerName
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private String checkername;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.checkResult
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Integer checkresult;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.checkTime
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Date checktime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.notes
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private String notes;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.cleared
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.modify_remind
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Integer modifyRemind;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_attendolder.cancel_remind
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private Integer cancelRemind;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_attendolder
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.id
	 * @return  the value of pension_attendolder.id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.id
	 * @param id  the value for pension_attendolder.id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.activity_id
	 * @return  the value of pension_attendolder.activity_id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Long getActivityId() {
		return activityId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.activity_id
	 * @param activityId  the value for pension_attendolder.activity_id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.older_id
	 * @return  the value of pension_attendolder.older_id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Long getOlderId() {
		return olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.older_id
	 * @param olderId  the value for pension_attendolder.older_id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.enlistTime
	 * @return  the value of pension_attendolder.enlistTime
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Date getEnlisttime() {
		return enlisttime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.enlistTime
	 * @param enlisttime  the value for pension_attendolder.enlistTime
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setEnlisttime(Date enlisttime) {
		this.enlisttime = enlisttime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.isAttend
	 * @return  the value of pension_attendolder.isAttend
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Integer getIsattend() {
		return isattend;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.isAttend
	 * @param isattend  the value for pension_attendolder.isAttend
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setIsattend(Integer isattend) {
		this.isattend = isattend;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.attended
	 * @return  the value of pension_attendolder.attended
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Integer getAttended() {
		return attended;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.attended
	 * @param attended  the value for pension_attendolder.attended
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setAttended(Integer attended) {
		this.attended = attended;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.reason
	 * @return  the value of pension_attendolder.reason
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.reason
	 * @param reason  the value for pension_attendolder.reason
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.appraise
	 * @return  the value of pension_attendolder.appraise
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Integer getAppraise() {
		return appraise;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.appraise
	 * @param appraise  the value for pension_attendolder.appraise
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setAppraise(Integer appraise) {
		this.appraise = appraise;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.checker_id
	 * @return  the value of pension_attendolder.checker_id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Long getCheckerId() {
		return checkerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.checker_id
	 * @param checkerId  the value for pension_attendolder.checker_id
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setCheckerId(Long checkerId) {
		this.checkerId = checkerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.checkerName
	 * @return  the value of pension_attendolder.checkerName
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public String getCheckername() {
		return checkername;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.checkerName
	 * @param checkername  the value for pension_attendolder.checkerName
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setCheckername(String checkername) {
		this.checkername = checkername == null ? null : checkername.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.checkResult
	 * @return  the value of pension_attendolder.checkResult
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Integer getCheckresult() {
		return checkresult;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.checkResult
	 * @param checkresult  the value for pension_attendolder.checkResult
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setCheckresult(Integer checkresult) {
		this.checkresult = checkresult;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.checkTime
	 * @return  the value of pension_attendolder.checkTime
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Date getChecktime() {
		return checktime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.checkTime
	 * @param checktime  the value for pension_attendolder.checkTime
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.notes
	 * @return  the value of pension_attendolder.notes
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.notes
	 * @param notes  the value for pension_attendolder.notes
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.cleared
	 * @return  the value of pension_attendolder.cleared
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.cleared
	 * @param cleared  the value for pension_attendolder.cleared
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.modify_remind
	 * @return  the value of pension_attendolder.modify_remind
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Integer getModifyRemind() {
		return modifyRemind;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.modify_remind
	 * @param modifyRemind  the value for pension_attendolder.modify_remind
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setModifyRemind(Integer modifyRemind) {
		this.modifyRemind = modifyRemind;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_attendolder.cancel_remind
	 * @return  the value of pension_attendolder.cancel_remind
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public Integer getCancelRemind() {
		return cancelRemind;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_attendolder.cancel_remind
	 * @param cancelRemind  the value for pension_attendolder.cancel_remind
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	public void setCancelRemind(Integer cancelRemind) {
		this.cancelRemind = cancelRemind;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_attendolder
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
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
		PensionAttendolder other = (PensionAttendolder) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getActivityId() == null ? other.getActivityId() == null
						: this.getActivityId().equals(other.getActivityId()))
				&& (this.getOlderId() == null ? other.getOlderId() == null
						: this.getOlderId().equals(other.getOlderId()))
				&& (this.getEnlisttime() == null ? other.getEnlisttime() == null
						: this.getEnlisttime().equals(other.getEnlisttime()))
				&& (this.getIsattend() == null ? other.getIsattend() == null
						: this.getIsattend().equals(other.getIsattend()))
				&& (this.getAttended() == null ? other.getAttended() == null
						: this.getAttended().equals(other.getAttended()))
				&& (this.getReason() == null ? other.getReason() == null : this
						.getReason().equals(other.getReason()))
				&& (this.getAppraise() == null ? other.getAppraise() == null
						: this.getAppraise().equals(other.getAppraise()))
				&& (this.getCheckerId() == null ? other.getCheckerId() == null
						: this.getCheckerId().equals(other.getCheckerId()))
				&& (this.getCheckername() == null ? other.getCheckername() == null
						: this.getCheckername().equals(other.getCheckername()))
				&& (this.getCheckresult() == null ? other.getCheckresult() == null
						: this.getCheckresult().equals(other.getCheckresult()))
				&& (this.getChecktime() == null ? other.getChecktime() == null
						: this.getChecktime().equals(other.getChecktime()))
				&& (this.getNotes() == null ? other.getNotes() == null : this
						.getNotes().equals(other.getNotes()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getModifyRemind() == null ? other.getModifyRemind() == null
						: this.getModifyRemind()
								.equals(other.getModifyRemind()))
				&& (this.getCancelRemind() == null ? other.getCancelRemind() == null
						: this.getCancelRemind()
								.equals(other.getCancelRemind()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_attendolder
	 * @mbggenerated  Mon Mar 03 11:07:59 CST 2014
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getActivityId() == null) ? 0 : getActivityId().hashCode());
		result = prime * result
				+ ((getOlderId() == null) ? 0 : getOlderId().hashCode());
		result = prime * result
				+ ((getEnlisttime() == null) ? 0 : getEnlisttime().hashCode());
		result = prime * result
				+ ((getIsattend() == null) ? 0 : getIsattend().hashCode());
		result = prime * result
				+ ((getAttended() == null) ? 0 : getAttended().hashCode());
		result = prime * result
				+ ((getReason() == null) ? 0 : getReason().hashCode());
		result = prime * result
				+ ((getAppraise() == null) ? 0 : getAppraise().hashCode());
		result = prime * result
				+ ((getCheckerId() == null) ? 0 : getCheckerId().hashCode());
		result = prime
				* result
				+ ((getCheckername() == null) ? 0 : getCheckername().hashCode());
		result = prime
				* result
				+ ((getCheckresult() == null) ? 0 : getCheckresult().hashCode());
		result = prime * result
				+ ((getChecktime() == null) ? 0 : getChecktime().hashCode());
		result = prime * result
				+ ((getNotes() == null) ? 0 : getNotes().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime
				* result
				+ ((getModifyRemind() == null) ? 0 : getModifyRemind()
						.hashCode());
		result = prime
				* result
				+ ((getCancelRemind() == null) ? 0 : getCancelRemind()
						.hashCode());
		return result;
	}

	/**
	 * 扩展属性
	 */
	
	private String itemName;
	private String olderName;
	private String itemplace;
	private String addCanOrNotName;
	private String addIsOrNotName;
	private String checkResultName;
	private Date startDate;
	private String likeStr;
	private boolean selectedFlag;
	private String checkerName;
	private String appraiseName;
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	/**
	 * @return the olderName
	 */
	public String getOlderName() {
		return olderName;
	}

	/**
	 * @param olderName the olderName to set
	 */
	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}


	/**
	 * @return the addCanOrNotName
	 */
	public String getAddCanOrNotName() {
		return addCanOrNotName;
	}

	/**
	 * @param addCanOrNotName the addCanOrNotName to set
	 */
	public void setAddCanOrNotName(String addCanOrNotName) {
		this.addCanOrNotName = addCanOrNotName;
	}


	/**
	 * @return the addIsOrNotName
	 */
	public String getAddIsOrNotName() {
		return addIsOrNotName;
	}

	/**
	 * @param addIsOrNotName the addIsOrNotName to set
	 */
	public void setAddIsOrNotName(String addIsOrNotName) {
		this.addIsOrNotName = addIsOrNotName;
	}

	/**
	 * @return the checkResultName
	 */
	public String getCheckResultName() {
		return checkResultName;
	}

	/**
	 * @param checkResultName the checkResultName to set
	 */
	public void setCheckResultName(String checkResultName) {
		this.checkResultName = checkResultName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the likeStr
	 */
	public String getLikeStr() {
		return likeStr;
	}

	/**
	 * @param likeStr the likeStr to set
	 */
	public void setLikeStr(String likeStr) {
		this.likeStr = likeStr;
	}

	/**
	 * @return the selectedFlag
	 */
	public boolean isSelectedFlag() {
		return selectedFlag;
	}

	/**
	 * @param selectedFlag the selectedFlag to set
	 */
	public void setSelectedFlag(boolean selectedFlag) {
		this.selectedFlag = selectedFlag;
	}

	/**
	 * @return the checkerName
	 */
	public String getCheckerName() {
		return checkerName;
	}

	/**
	 * @param checkerName the checkerName to set
	 */
	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	/**
	 * @return the appraiseName
	 */
	public String getAppraiseName() {
		return appraiseName;
	}

	/**
	 * @param appraiseName the appraiseName to set
	 */
	public void setAppraiseName(String appraiseName) {
		this.appraiseName = appraiseName;
	}


	public void setItemplace(String itemplace) {
		this.itemplace = itemplace;
	}

	public String getItemplace() {
		return itemplace;
	}


	
	
	
}