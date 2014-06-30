package domain.recoup;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RecoupApplyRecord implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.id
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.receipt_no
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private String receiptNo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.proj_id
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private Long projId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.proj_name
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private String projName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.proj_code
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private String projCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.apply_date
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private Date applyDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.type_id1
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private Long typeId1;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.type_id2
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private Long typeId2;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.neme
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private String neme;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.state
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private Integer state;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.money
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private BigDecimal money;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.pay_type
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private Integer payType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.pay_state
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private Integer payState;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.reason
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private String reason;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.ohers
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private String ohers;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table recoup_apply_record
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.id
	 * @return  the value of recoup_apply_record.id
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.id
	 * @param id  the value for recoup_apply_record.id
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.receipt_no
	 * @return  the value of recoup_apply_record.receipt_no
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public String getReceiptNo() {
		return receiptNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.receipt_no
	 * @param receiptNo  the value for recoup_apply_record.receipt_no
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo == null ? null : receiptNo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.proj_id
	 * @return  the value of recoup_apply_record.proj_id
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public Long getProjId() {
		return projId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.proj_id
	 * @param projId  the value for recoup_apply_record.proj_id
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setProjId(Long projId) {
		this.projId = projId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.proj_name
	 * @return  the value of recoup_apply_record.proj_name
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public String getProjName() {
		return projName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.proj_name
	 * @param projName  the value for recoup_apply_record.proj_name
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setProjName(String projName) {
		this.projName = projName == null ? null : projName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.proj_code
	 * @return  the value of recoup_apply_record.proj_code
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public String getProjCode() {
		return projCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.proj_code
	 * @param projCode  the value for recoup_apply_record.proj_code
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setProjCode(String projCode) {
		this.projCode = projCode == null ? null : projCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.apply_date
	 * @return  the value of recoup_apply_record.apply_date
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public Date getApplyDate() {
		return applyDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.apply_date
	 * @param applyDate  the value for recoup_apply_record.apply_date
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.type_id1
	 * @return  the value of recoup_apply_record.type_id1
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public Long getTypeId1() {
		return typeId1;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.type_id1
	 * @param typeId1  the value for recoup_apply_record.type_id1
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setTypeId1(Long typeId1) {
		this.typeId1 = typeId1;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.type_id2
	 * @return  the value of recoup_apply_record.type_id2
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public Long getTypeId2() {
		return typeId2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.type_id2
	 * @param typeId2  the value for recoup_apply_record.type_id2
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setTypeId2(Long typeId2) {
		this.typeId2 = typeId2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.neme
	 * @return  the value of recoup_apply_record.neme
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public String getNeme() {
		return neme;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.neme
	 * @param neme  the value for recoup_apply_record.neme
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setNeme(String neme) {
		this.neme = neme == null ? null : neme.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.state
	 * @return  the value of recoup_apply_record.state
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.state
	 * @param state  the value for recoup_apply_record.state
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.money
	 * @return  the value of recoup_apply_record.money
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public BigDecimal getMoney() {
		return money;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.money
	 * @param money  the value for recoup_apply_record.money
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.pay_type
	 * @return  the value of recoup_apply_record.pay_type
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public Integer getPayType() {
		return payType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.pay_type
	 * @param payType  the value for recoup_apply_record.pay_type
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.pay_state
	 * @return  the value of recoup_apply_record.pay_state
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public Integer getPayState() {
		return payState;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.pay_state
	 * @param payState  the value for recoup_apply_record.pay_state
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.reason
	 * @return  the value of recoup_apply_record.reason
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.reason
	 * @param reason  the value for recoup_apply_record.reason
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.ohers
	 * @return  the value of recoup_apply_record.ohers
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public String getOhers() {
		return ohers;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.ohers
	 * @param ohers  the value for recoup_apply_record.ohers
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	public void setOhers(String ohers) {
		this.ohers = ohers == null ? null : ohers.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_apply_record
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
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
		RecoupApplyRecord other = (RecoupApplyRecord) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getReceiptNo() == null ? other.getReceiptNo() == null
						: this.getReceiptNo().equals(other.getReceiptNo()))
				&& (this.getProjId() == null ? other.getProjId() == null : this
						.getProjId().equals(other.getProjId()))
				&& (this.getProjName() == null ? other.getProjName() == null
						: this.getProjName().equals(other.getProjName()))
				&& (this.getProjCode() == null ? other.getProjCode() == null
						: this.getProjCode().equals(other.getProjCode()))
				&& (this.getApplyDate() == null ? other.getApplyDate() == null
						: this.getApplyDate().equals(other.getApplyDate()))
				&& (this.getTypeId1() == null ? other.getTypeId1() == null
						: this.getTypeId1().equals(other.getTypeId1()))
				&& (this.getTypeId2() == null ? other.getTypeId2() == null
						: this.getTypeId2().equals(other.getTypeId2()))
				&& (this.getNeme() == null ? other.getNeme() == null : this
						.getNeme().equals(other.getNeme()))
				&& (this.getState() == null ? other.getState() == null : this
						.getState().equals(other.getState()))
				&& (this.getMoney() == null ? other.getMoney() == null : this
						.getMoney().equals(other.getMoney()))
				&& (this.getPayType() == null ? other.getPayType() == null
						: this.getPayType().equals(other.getPayType()))
				&& (this.getPayState() == null ? other.getPayState() == null
						: this.getPayState().equals(other.getPayState()))
				&& (this.getReason() == null ? other.getReason() == null : this
						.getReason().equals(other.getReason()))
				&& (this.getOhers() == null ? other.getOhers() == null : this
						.getOhers().equals(other.getOhers()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_apply_record
	 * @mbggenerated  Tue Jul 01 05:39:16 CST 2014
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getReceiptNo() == null) ? 0 : getReceiptNo().hashCode());
		result = prime * result
				+ ((getProjId() == null) ? 0 : getProjId().hashCode());
		result = prime * result
				+ ((getProjName() == null) ? 0 : getProjName().hashCode());
		result = prime * result
				+ ((getProjCode() == null) ? 0 : getProjCode().hashCode());
		result = prime * result
				+ ((getApplyDate() == null) ? 0 : getApplyDate().hashCode());
		result = prime * result
				+ ((getTypeId1() == null) ? 0 : getTypeId1().hashCode());
		result = prime * result
				+ ((getTypeId2() == null) ? 0 : getTypeId2().hashCode());
		result = prime * result
				+ ((getNeme() == null) ? 0 : getNeme().hashCode());
		result = prime * result
				+ ((getState() == null) ? 0 : getState().hashCode());
		result = prime * result
				+ ((getMoney() == null) ? 0 : getMoney().hashCode());
		result = prime * result
				+ ((getPayType() == null) ? 0 : getPayType().hashCode());
		result = prime * result
				+ ((getPayState() == null) ? 0 : getPayState().hashCode());
		result = prime * result
				+ ((getReason() == null) ? 0 : getReason().hashCode());
		result = prime * result
				+ ((getOhers() == null) ? 0 : getOhers().hashCode());
		return result;
	}
}