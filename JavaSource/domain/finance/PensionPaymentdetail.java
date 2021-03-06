package domain.finance;

import java.io.Serializable;
import java.util.Date;

public class PensionPaymentdetail implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_paymentdetail.id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_paymentdetail.paymentID
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private Integer paymentid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_paymentdetail.payType_id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private Integer paytypeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_paymentdetail.payStyleName
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private String paystylename;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_paymentdetail.payStyle_id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private Long payStyle_id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_paymentdetail.money
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private Float money;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_paymentdetail.payTime
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private Date paytime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_paymentdetail.notes
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private String notes;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_paymentdetail.cleared
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_paymentdetail.payer_id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private Long payerId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_paymentdetail.pay_name
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private String payName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_paymentdetail
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_paymentdetail.id
	 * @return  the value of pension_paymentdetail.id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_paymentdetail.id
	 * @param id  the value for pension_paymentdetail.id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_paymentdetail.paymentID
	 * @return  the value of pension_paymentdetail.paymentID
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public Integer getPaymentid() {
		return paymentid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_paymentdetail.paymentID
	 * @param paymentid  the value for pension_paymentdetail.paymentID
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public void setPaymentid(Integer paymentid) {
		this.paymentid = paymentid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_paymentdetail.payType_id
	 * @return  the value of pension_paymentdetail.payType_id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public Integer getPaytypeId() {
		return paytypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_paymentdetail.payType_id
	 * @param paytypeId  the value for pension_paymentdetail.payType_id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public void setPaytypeId(Integer paytypeId) {
		this.paytypeId = paytypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_paymentdetail.payStyleName
	 * @return  the value of pension_paymentdetail.payStyleName
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public String getPaystylename() {
		return paystylename;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_paymentdetail.payStyleName
	 * @param paystylename  the value for pension_paymentdetail.payStyleName
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public void setPaystylename(String paystylename) {
		this.paystylename = paystylename == null ? null : paystylename.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_paymentdetail.payStyle_id
	 * @return  the value of pension_paymentdetail.payStyle_id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public Long getPayStyle_id() {
		return payStyle_id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_paymentdetail.payStyle_id
	 * @param payStyle_id  the value for pension_paymentdetail.payStyle_id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public void setPayStyle_id(Long payStyle_id) {
		this.payStyle_id = payStyle_id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_paymentdetail.money
	 * @return  the value of pension_paymentdetail.money
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public Float getMoney() {
		return money;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_paymentdetail.money
	 * @param money  the value for pension_paymentdetail.money
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public void setMoney(Float money) {
		this.money = money;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_paymentdetail.payTime
	 * @return  the value of pension_paymentdetail.payTime
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public Date getPaytime() {
		return paytime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_paymentdetail.payTime
	 * @param paytime  the value for pension_paymentdetail.payTime
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_paymentdetail.notes
	 * @return  the value of pension_paymentdetail.notes
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_paymentdetail.notes
	 * @param notes  the value for pension_paymentdetail.notes
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_paymentdetail.cleared
	 * @return  the value of pension_paymentdetail.cleared
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_paymentdetail.cleared
	 * @param cleared  the value for pension_paymentdetail.cleared
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_paymentdetail.payer_id
	 * @return  the value of pension_paymentdetail.payer_id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public Long getPayerId() {
		return payerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_paymentdetail.payer_id
	 * @param payerId  the value for pension_paymentdetail.payer_id
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_paymentdetail.pay_name
	 * @return  the value of pension_paymentdetail.pay_name
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public String getPayName() {
		return payName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_paymentdetail.pay_name
	 * @param payName  the value for pension_paymentdetail.pay_name
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	public void setPayName(String payName) {
		this.payName = payName == null ? null : payName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_paymentdetail
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
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
		PensionPaymentdetail other = (PensionPaymentdetail) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getPaymentid() == null ? other.getPaymentid() == null
						: this.getPaymentid().equals(other.getPaymentid()))
				&& (this.getPaytypeId() == null ? other.getPaytypeId() == null
						: this.getPaytypeId().equals(other.getPaytypeId()))
				&& (this.getPaystylename() == null ? other.getPaystylename() == null
						: this.getPaystylename()
								.equals(other.getPaystylename()))
				&& (this.getPayStyle_id() == null ? other.getPayStyle_id() == null
						: this.getPayStyle_id().equals(other.getPayStyle_id()))
				&& (this.getMoney() == null ? other.getMoney() == null : this
						.getMoney().equals(other.getMoney()))
				&& (this.getPaytime() == null ? other.getPaytime() == null
						: this.getPaytime().equals(other.getPaytime()))
				&& (this.getNotes() == null ? other.getNotes() == null : this
						.getNotes().equals(other.getNotes()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getPayerId() == null ? other.getPayerId() == null
						: this.getPayerId().equals(other.getPayerId()))
				&& (this.getPayName() == null ? other.getPayName() == null
						: this.getPayName().equals(other.getPayName()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_paymentdetail
	 * @mbggenerated  Mon Sep 23 16:02:48 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getPaymentid() == null) ? 0 : getPaymentid().hashCode());
		result = prime * result
				+ ((getPaytypeId() == null) ? 0 : getPaytypeId().hashCode());
		result = prime
				* result
				+ ((getPaystylename() == null) ? 0 : getPaystylename()
						.hashCode());
		result = prime
				* result
				+ ((getPayStyle_id() == null) ? 0 : getPayStyle_id().hashCode());
		result = prime * result
				+ ((getMoney() == null) ? 0 : getMoney().hashCode());
		result = prime * result
				+ ((getPaytime() == null) ? 0 : getPaytime().hashCode());
		result = prime * result
				+ ((getNotes() == null) ? 0 : getNotes().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime * result
				+ ((getPayerId() == null) ? 0 : getPayerId().hashCode());
		result = prime * result
				+ ((getPayName() == null) ? 0 : getPayName().hashCode());
		return result;
	}
}