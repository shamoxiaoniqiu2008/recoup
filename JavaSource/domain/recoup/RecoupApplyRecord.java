package domain.recoup;

import java.io.Serializable;
import java.math.BigDecimal;

public class RecoupApplyRecord implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.id
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private Long id;
	
	
	private String userName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.receipt_no
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private String receiptNo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.proj_name
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private String projName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.proj_code
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private String projCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.apply_date
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private String applyDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.exp_type_code_p
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private String expTypeCodeP;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.exp_type_code
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private String expTypeCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.user_id
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private Integer userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.state
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private Integer state;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.money
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private BigDecimal money;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.pay_type
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private String payType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.pay_state
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private Integer payState;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.reason
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private String reason;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column recoup_apply_record.ohers
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private String ohers;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table recoup_apply_record
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.id
	 * @return  the value of recoup_apply_record.id
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.id
	 * @param id  the value for recoup_apply_record.id
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.receipt_no
	 * @return  the value of recoup_apply_record.receipt_no
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public String getReceiptNo() {
		return receiptNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.receipt_no
	 * @param receiptNo  the value for recoup_apply_record.receipt_no
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo == null ? null : receiptNo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.proj_name
	 * @return  the value of recoup_apply_record.proj_name
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public String getProjName() {
		return projName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.proj_name
	 * @param projName  the value for recoup_apply_record.proj_name
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setProjName(String projName) {
		this.projName = projName == null ? null : projName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.proj_code
	 * @return  the value of recoup_apply_record.proj_code
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public String getProjCode() {
		return projCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.proj_code
	 * @param projCode  the value for recoup_apply_record.proj_code
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setProjCode(String projCode) {
		this.projCode = projCode == null ? null : projCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.apply_date
	 * @return  the value of recoup_apply_record.apply_date
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public String getApplyDate() {
		return applyDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.apply_date
	 * @param applyDate  the value for recoup_apply_record.apply_date
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate == null ? null : applyDate.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.exp_type_code_p
	 * @return  the value of recoup_apply_record.exp_type_code_p
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public String getExpTypeCodeP() {
		return expTypeCodeP;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.exp_type_code_p
	 * @param expTypeCodeP  the value for recoup_apply_record.exp_type_code_p
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setExpTypeCodeP(String expTypeCodeP) {
		this.expTypeCodeP = expTypeCodeP == null ? null : expTypeCodeP.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.exp_type_code
	 * @return  the value of recoup_apply_record.exp_type_code
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public String getExpTypeCode() {
		return expTypeCode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.exp_type_code
	 * @param expTypeCode  the value for recoup_apply_record.exp_type_code
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setExpTypeCode(String expTypeCode) {
		this.expTypeCode = expTypeCode == null ? null : expTypeCode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.user_id
	 * @return  the value of recoup_apply_record.user_id
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.user_id
	 * @param userId  the value for recoup_apply_record.user_id
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.state
	 * @return  the value of recoup_apply_record.state
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.state
	 * @param state  the value for recoup_apply_record.state
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.money
	 * @return  the value of recoup_apply_record.money
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public BigDecimal getMoney() {
		return money;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.money
	 * @param money  the value for recoup_apply_record.money
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.pay_type
	 * @return  the value of recoup_apply_record.pay_type
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public String getPayType() {
		return payType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.pay_type
	 * @param payType  the value for recoup_apply_record.pay_type
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setPayType(String payType) {
		this.payType = payType == null ? null : payType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.pay_state
	 * @return  the value of recoup_apply_record.pay_state
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public Integer getPayState() {
		return payState;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.pay_state
	 * @param payState  the value for recoup_apply_record.pay_state
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.reason
	 * @return  the value of recoup_apply_record.reason
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.reason
	 * @param reason  the value for recoup_apply_record.reason
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column recoup_apply_record.ohers
	 * @return  the value of recoup_apply_record.ohers
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public String getOhers() {
		return ohers;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column recoup_apply_record.ohers
	 * @param ohers  the value for recoup_apply_record.ohers
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	public void setOhers(String ohers) {
		this.ohers = ohers == null ? null : ohers.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_apply_record
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
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
				&& (this.getProjName() == null ? other.getProjName() == null
						: this.getProjName().equals(other.getProjName()))
				&& (this.getProjCode() == null ? other.getProjCode() == null
						: this.getProjCode().equals(other.getProjCode()))
				&& (this.getApplyDate() == null ? other.getApplyDate() == null
						: this.getApplyDate().equals(other.getApplyDate()))
				&& (this.getExpTypeCodeP() == null ? other.getExpTypeCodeP() == null
						: this.getExpTypeCodeP()
								.equals(other.getExpTypeCodeP()))
				&& (this.getExpTypeCode() == null ? other.getExpTypeCode() == null
						: this.getExpTypeCode().equals(other.getExpTypeCode()))
				&& (this.getUserId() == null ? other.getUserId() == null : this
						.getUserId().equals(other.getUserId()))
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
	 * @mbggenerated  Fri Jul 04 09:25:06 CST 2014
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getReceiptNo() == null) ? 0 : getReceiptNo().hashCode());
		result = prime * result
				+ ((getProjName() == null) ? 0 : getProjName().hashCode());
		result = prime * result
				+ ((getProjCode() == null) ? 0 : getProjCode().hashCode());
		result = prime * result
				+ ((getApplyDate() == null) ? 0 : getApplyDate().hashCode());
		result = prime
				* result
				+ ((getExpTypeCodeP() == null) ? 0 : getExpTypeCodeP()
						.hashCode());
		result = prime
				* result
				+ ((getExpTypeCode() == null) ? 0 : getExpTypeCode().hashCode());
		result = prime * result
				+ ((getUserId() == null) ? 0 : getUserId().hashCode());
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

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}