package domain.stockManage;

import java.io.Serializable;
import java.util.Date;

public class PensionStorageoutRecord implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_storageout_record.id
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_storageout_record.storageout_no
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	private String storageoutNo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_storageout_record.storageout_time
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	private Date storageoutTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_storageout_record.operator_id
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	private Long operatorId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_storageout_record.storageout_type
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	private Integer storageoutType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_storageout_record.bank_flag
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	private Integer bankFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_storageout_record.cleared
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_storageout_record
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_storageout_record.id
	 * @return  the value of pension_storageout_record.id
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_storageout_record.id
	 * @param id  the value for pension_storageout_record.id
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_storageout_record.storageout_no
	 * @return  the value of pension_storageout_record.storageout_no
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public String getStorageoutNo() {
		return storageoutNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_storageout_record.storageout_no
	 * @param storageoutNo  the value for pension_storageout_record.storageout_no
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public void setStorageoutNo(String storageoutNo) {
		this.storageoutNo = storageoutNo == null ? null : storageoutNo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_storageout_record.storageout_time
	 * @return  the value of pension_storageout_record.storageout_time
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public Date getStorageoutTime() {
		return storageoutTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_storageout_record.storageout_time
	 * @param storageoutTime  the value for pension_storageout_record.storageout_time
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public void setStorageoutTime(Date storageoutTime) {
		this.storageoutTime = storageoutTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_storageout_record.operator_id
	 * @return  the value of pension_storageout_record.operator_id
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public Long getOperatorId() {
		return operatorId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_storageout_record.operator_id
	 * @param operatorId  the value for pension_storageout_record.operator_id
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_storageout_record.storageout_type
	 * @return  the value of pension_storageout_record.storageout_type
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public Integer getStorageoutType() {
		return storageoutType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_storageout_record.storageout_type
	 * @param storageoutType  the value for pension_storageout_record.storageout_type
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public void setStorageoutType(Integer storageoutType) {
		this.storageoutType = storageoutType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_storageout_record.bank_flag
	 * @return  the value of pension_storageout_record.bank_flag
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public Integer getBankFlag() {
		return bankFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_storageout_record.bank_flag
	 * @param bankFlag  the value for pension_storageout_record.bank_flag
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public void setBankFlag(Integer bankFlag) {
		this.bankFlag = bankFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_storageout_record.cleared
	 * @return  the value of pension_storageout_record.cleared
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_storageout_record.cleared
	 * @param cleared  the value for pension_storageout_record.cleared
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_storageout_record
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
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
		PensionStorageoutRecord other = (PensionStorageoutRecord) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getStorageoutNo() == null ? other.getStorageoutNo() == null
						: this.getStorageoutNo()
								.equals(other.getStorageoutNo()))
				&& (this.getStorageoutTime() == null ? other
						.getStorageoutTime() == null : this.getStorageoutTime()
						.equals(other.getStorageoutTime()))
				&& (this.getOperatorId() == null ? other.getOperatorId() == null
						: this.getOperatorId().equals(other.getOperatorId()))
				&& (this.getStorageoutType() == null ? other
						.getStorageoutType() == null : this.getStorageoutType()
						.equals(other.getStorageoutType()))
				&& (this.getBankFlag() == null ? other.getBankFlag() == null
						: this.getBankFlag().equals(other.getBankFlag()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_storageout_record
	 * @mbggenerated  Tue Nov 26 19:02:42 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime
				* result
				+ ((getStorageoutNo() == null) ? 0 : getStorageoutNo()
						.hashCode());
		result = prime
				* result
				+ ((getStorageoutTime() == null) ? 0 : getStorageoutTime()
						.hashCode());
		result = prime * result
				+ ((getOperatorId() == null) ? 0 : getOperatorId().hashCode());
		result = prime
				* result
				+ ((getStorageoutType() == null) ? 0 : getStorageoutType()
						.hashCode());
		result = prime * result
				+ ((getBankFlag() == null) ? 0 : getBankFlag().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		return result;
	}
}