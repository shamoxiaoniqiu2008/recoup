package domain.medicalManage;

import java.io.Serializable;
import java.util.Date;

public class PensionDrugreceiveDetail implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.id
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.record_id
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Long recordId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.drugreceive_id
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Long drugreceiveId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.total_amount
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Integer totalAmount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.single_dose
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Integer singleDose;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.unit
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private String unit;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.morning_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Integer morningFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.noon_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Integer noonFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.night_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Integer nightFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.before_after_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Integer beforeAfterFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.store_time
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Date storeTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.use_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Integer useFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.valid_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Integer validFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.shelflife_time
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Date shelflifeTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.cleared
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.note
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private String note;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_drugreceive_detail.amount_total
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private Integer amountTotal;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_drugreceive_detail
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.id
	 * @return  the value of pension_drugreceive_detail.id
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.id
	 * @param id  the value for pension_drugreceive_detail.id
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.record_id
	 * @return  the value of pension_drugreceive_detail.record_id
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Long getRecordId() {
		return recordId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.record_id
	 * @param recordId  the value for pension_drugreceive_detail.record_id
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.drugreceive_id
	 * @return  the value of pension_drugreceive_detail.drugreceive_id
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Long getDrugreceiveId() {
		return drugreceiveId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.drugreceive_id
	 * @param drugreceiveId  the value for pension_drugreceive_detail.drugreceive_id
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setDrugreceiveId(Long drugreceiveId) {
		this.drugreceiveId = drugreceiveId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.total_amount
	 * @return  the value of pension_drugreceive_detail.total_amount
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Integer getTotalAmount() {
		return totalAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.total_amount
	 * @param totalAmount  the value for pension_drugreceive_detail.total_amount
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.single_dose
	 * @return  the value of pension_drugreceive_detail.single_dose
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Integer getSingleDose() {
		return singleDose;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.single_dose
	 * @param singleDose  the value for pension_drugreceive_detail.single_dose
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setSingleDose(Integer singleDose) {
		this.singleDose = singleDose;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.unit
	 * @return  the value of pension_drugreceive_detail.unit
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.unit
	 * @param unit  the value for pension_drugreceive_detail.unit
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.morning_flag
	 * @return  the value of pension_drugreceive_detail.morning_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Integer getMorningFlag() {
		return morningFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.morning_flag
	 * @param morningFlag  the value for pension_drugreceive_detail.morning_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setMorningFlag(Integer morningFlag) {
		this.morningFlag = morningFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.noon_flag
	 * @return  the value of pension_drugreceive_detail.noon_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Integer getNoonFlag() {
		return noonFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.noon_flag
	 * @param noonFlag  the value for pension_drugreceive_detail.noon_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setNoonFlag(Integer noonFlag) {
		this.noonFlag = noonFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.night_flag
	 * @return  the value of pension_drugreceive_detail.night_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Integer getNightFlag() {
		return nightFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.night_flag
	 * @param nightFlag  the value for pension_drugreceive_detail.night_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setNightFlag(Integer nightFlag) {
		this.nightFlag = nightFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.before_after_flag
	 * @return  the value of pension_drugreceive_detail.before_after_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Integer getBeforeAfterFlag() {
		return beforeAfterFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.before_after_flag
	 * @param beforeAfterFlag  the value for pension_drugreceive_detail.before_after_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setBeforeAfterFlag(Integer beforeAfterFlag) {
		this.beforeAfterFlag = beforeAfterFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.store_time
	 * @return  the value of pension_drugreceive_detail.store_time
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Date getStoreTime() {
		return storeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.store_time
	 * @param storeTime  the value for pension_drugreceive_detail.store_time
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setStoreTime(Date storeTime) {
		this.storeTime = storeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.use_flag
	 * @return  the value of pension_drugreceive_detail.use_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Integer getUseFlag() {
		return useFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.use_flag
	 * @param useFlag  the value for pension_drugreceive_detail.use_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.valid_flag
	 * @return  the value of pension_drugreceive_detail.valid_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Integer getValidFlag() {
		return validFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.valid_flag
	 * @param validFlag  the value for pension_drugreceive_detail.valid_flag
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.shelflife_time
	 * @return  the value of pension_drugreceive_detail.shelflife_time
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Date getShelflifeTime() {
		return shelflifeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.shelflife_time
	 * @param shelflifeTime  the value for pension_drugreceive_detail.shelflife_time
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setShelflifeTime(Date shelflifeTime) {
		this.shelflifeTime = shelflifeTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.cleared
	 * @return  the value of pension_drugreceive_detail.cleared
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.cleared
	 * @param cleared  the value for pension_drugreceive_detail.cleared
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.note
	 * @return  the value of pension_drugreceive_detail.note
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public String getNote() {
		return note;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.note
	 * @param note  the value for pension_drugreceive_detail.note
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_drugreceive_detail.amount_total
	 * @return  the value of pension_drugreceive_detail.amount_total
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public Integer getAmountTotal() {
		return amountTotal;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_drugreceive_detail.amount_total
	 * @param amountTotal  the value for pension_drugreceive_detail.amount_total
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	public void setAmountTotal(Integer amountTotal) {
		this.amountTotal = amountTotal;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_drugreceive_detail
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
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
		PensionDrugreceiveDetail other = (PensionDrugreceiveDetail) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getRecordId() == null ? other.getRecordId() == null
						: this.getRecordId().equals(other.getRecordId()))
				&& (this.getDrugreceiveId() == null ? other.getDrugreceiveId() == null
						: this.getDrugreceiveId().equals(
								other.getDrugreceiveId()))
				&& (this.getTotalAmount() == null ? other.getTotalAmount() == null
						: this.getTotalAmount().equals(other.getTotalAmount()))
				&& (this.getSingleDose() == null ? other.getSingleDose() == null
						: this.getSingleDose().equals(other.getSingleDose()))
				&& (this.getUnit() == null ? other.getUnit() == null : this
						.getUnit().equals(other.getUnit()))
				&& (this.getMorningFlag() == null ? other.getMorningFlag() == null
						: this.getMorningFlag().equals(other.getMorningFlag()))
				&& (this.getNoonFlag() == null ? other.getNoonFlag() == null
						: this.getNoonFlag().equals(other.getNoonFlag()))
				&& (this.getNightFlag() == null ? other.getNightFlag() == null
						: this.getNightFlag().equals(other.getNightFlag()))
				&& (this.getBeforeAfterFlag() == null ? other
						.getBeforeAfterFlag() == null : this
						.getBeforeAfterFlag()
						.equals(other.getBeforeAfterFlag()))
				&& (this.getStoreTime() == null ? other.getStoreTime() == null
						: this.getStoreTime().equals(other.getStoreTime()))
				&& (this.getUseFlag() == null ? other.getUseFlag() == null
						: this.getUseFlag().equals(other.getUseFlag()))
				&& (this.getValidFlag() == null ? other.getValidFlag() == null
						: this.getValidFlag().equals(other.getValidFlag()))
				&& (this.getShelflifeTime() == null ? other.getShelflifeTime() == null
						: this.getShelflifeTime().equals(
								other.getShelflifeTime()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getNote() == null ? other.getNote() == null : this
						.getNote().equals(other.getNote()))
				&& (this.getAmountTotal() == null ? other.getAmountTotal() == null
						: this.getAmountTotal().equals(other.getAmountTotal()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_drugreceive_detail
	 * @mbggenerated  Tue Apr 15 16:04:57 CST 2014
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getRecordId() == null) ? 0 : getRecordId().hashCode());
		result = prime
				* result
				+ ((getDrugreceiveId() == null) ? 0 : getDrugreceiveId()
						.hashCode());
		result = prime
				* result
				+ ((getTotalAmount() == null) ? 0 : getTotalAmount().hashCode());
		result = prime * result
				+ ((getSingleDose() == null) ? 0 : getSingleDose().hashCode());
		result = prime * result
				+ ((getUnit() == null) ? 0 : getUnit().hashCode());
		result = prime
				* result
				+ ((getMorningFlag() == null) ? 0 : getMorningFlag().hashCode());
		result = prime * result
				+ ((getNoonFlag() == null) ? 0 : getNoonFlag().hashCode());
		result = prime * result
				+ ((getNightFlag() == null) ? 0 : getNightFlag().hashCode());
		result = prime
				* result
				+ ((getBeforeAfterFlag() == null) ? 0 : getBeforeAfterFlag()
						.hashCode());
		result = prime * result
				+ ((getStoreTime() == null) ? 0 : getStoreTime().hashCode());
		result = prime * result
				+ ((getUseFlag() == null) ? 0 : getUseFlag().hashCode());
		result = prime * result
				+ ((getValidFlag() == null) ? 0 : getValidFlag().hashCode());
		result = prime
				* result
				+ ((getShelflifeTime() == null) ? 0 : getShelflifeTime()
						.hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime * result
				+ ((getNote() == null) ? 0 : getNote().hashCode());
		result = prime
				* result
				+ ((getAmountTotal() == null) ? 0 : getAmountTotal().hashCode());
		return result;
	}
}