package domain.finance;

import java.io.Serializable;
import java.util.Date;

public class PensionTemppaymentdetail implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.payment_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Long paymentId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.older_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Long olderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.items_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Long itemsId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.item_name
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private String itemName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.itemlFees
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Float itemlfees;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.startTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Date starttime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.endTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Date endtime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.number
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Float number;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.totalFees
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Float totalfees;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.generateTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Date generatetime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.isPay
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Integer ispay;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.payTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Date paytime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.record_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Long recordId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.record_name
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private String recordName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.recordTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Date recordtime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.notes
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private String notes;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.cleared
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.source
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Integer source;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.refund_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private Long refundId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_temppaymentdetail.refund_type
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private String refundType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_temppaymentdetail
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.id
	 * @return  the value of pension_temppaymentdetail.id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.id
	 * @param id  the value for pension_temppaymentdetail.id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.payment_id
	 * @return  the value of pension_temppaymentdetail.payment_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Long getPaymentId() {
		return paymentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.payment_id
	 * @param paymentId  the value for pension_temppaymentdetail.payment_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.older_id
	 * @return  the value of pension_temppaymentdetail.older_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Long getOlderId() {
		return olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.older_id
	 * @param olderId  the value for pension_temppaymentdetail.older_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.items_id
	 * @return  the value of pension_temppaymentdetail.items_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Long getItemsId() {
		return itemsId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.items_id
	 * @param itemsId  the value for pension_temppaymentdetail.items_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setItemsId(Long itemsId) {
		this.itemsId = itemsId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.item_name
	 * @return  the value of pension_temppaymentdetail.item_name
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.item_name
	 * @param itemName  the value for pension_temppaymentdetail.item_name
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName == null ? null : itemName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.itemlFees
	 * @return  the value of pension_temppaymentdetail.itemlFees
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Float getItemlfees() {
		return itemlfees;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.itemlFees
	 * @param itemlfees  the value for pension_temppaymentdetail.itemlFees
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setItemlfees(Float itemlfees) {
		this.itemlfees = itemlfees;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.startTime
	 * @return  the value of pension_temppaymentdetail.startTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Date getStarttime() {
		return starttime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.startTime
	 * @param starttime  the value for pension_temppaymentdetail.startTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.endTime
	 * @return  the value of pension_temppaymentdetail.endTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Date getEndtime() {
		return endtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.endTime
	 * @param endtime  the value for pension_temppaymentdetail.endTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.number
	 * @return  the value of pension_temppaymentdetail.number
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Float getNumber() {
		return number;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.number
	 * @param number  the value for pension_temppaymentdetail.number
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setNumber(Float number) {
		this.number = number;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.totalFees
	 * @return  the value of pension_temppaymentdetail.totalFees
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Float getTotalfees() {
		return totalfees;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.totalFees
	 * @param totalfees  the value for pension_temppaymentdetail.totalFees
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setTotalfees(Float totalfees) {
		this.totalfees = totalfees;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.generateTime
	 * @return  the value of pension_temppaymentdetail.generateTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Date getGeneratetime() {
		return generatetime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.generateTime
	 * @param generatetime  the value for pension_temppaymentdetail.generateTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setGeneratetime(Date generatetime) {
		this.generatetime = generatetime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.isPay
	 * @return  the value of pension_temppaymentdetail.isPay
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Integer getIspay() {
		return ispay;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.isPay
	 * @param ispay  the value for pension_temppaymentdetail.isPay
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setIspay(Integer ispay) {
		this.ispay = ispay;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.payTime
	 * @return  the value of pension_temppaymentdetail.payTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Date getPaytime() {
		return paytime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.payTime
	 * @param paytime  the value for pension_temppaymentdetail.payTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.record_id
	 * @return  the value of pension_temppaymentdetail.record_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Long getRecordId() {
		return recordId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.record_id
	 * @param recordId  the value for pension_temppaymentdetail.record_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.record_name
	 * @return  the value of pension_temppaymentdetail.record_name
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public String getRecordName() {
		return recordName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.record_name
	 * @param recordName  the value for pension_temppaymentdetail.record_name
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setRecordName(String recordName) {
		this.recordName = recordName == null ? null : recordName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.recordTime
	 * @return  the value of pension_temppaymentdetail.recordTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Date getRecordtime() {
		return recordtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.recordTime
	 * @param recordtime  the value for pension_temppaymentdetail.recordTime
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setRecordtime(Date recordtime) {
		this.recordtime = recordtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.notes
	 * @return  the value of pension_temppaymentdetail.notes
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.notes
	 * @param notes  the value for pension_temppaymentdetail.notes
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.cleared
	 * @return  the value of pension_temppaymentdetail.cleared
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.cleared
	 * @param cleared  the value for pension_temppaymentdetail.cleared
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.source
	 * @return  the value of pension_temppaymentdetail.source
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Integer getSource() {
		return source;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.source
	 * @param source  the value for pension_temppaymentdetail.source
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setSource(Integer source) {
		this.source = source;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.refund_id
	 * @return  the value of pension_temppaymentdetail.refund_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public Long getRefundId() {
		return refundId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.refund_id
	 * @param refundId  the value for pension_temppaymentdetail.refund_id
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_temppaymentdetail.refund_type
	 * @return  the value of pension_temppaymentdetail.refund_type
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public String getRefundType() {
		return refundType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_temppaymentdetail.refund_type
	 * @param refundType  the value for pension_temppaymentdetail.refund_type
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	public void setRefundType(String refundType) {
		this.refundType = refundType == null ? null : refundType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_temppaymentdetail
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
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
		PensionTemppaymentdetail other = (PensionTemppaymentdetail) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getPaymentId() == null ? other.getPaymentId() == null
						: this.getPaymentId().equals(other.getPaymentId()))
				&& (this.getOlderId() == null ? other.getOlderId() == null
						: this.getOlderId().equals(other.getOlderId()))
				&& (this.getItemsId() == null ? other.getItemsId() == null
						: this.getItemsId().equals(other.getItemsId()))
				&& (this.getItemName() == null ? other.getItemName() == null
						: this.getItemName().equals(other.getItemName()))
				&& (this.getItemlfees() == null ? other.getItemlfees() == null
						: this.getItemlfees().equals(other.getItemlfees()))
				&& (this.getStarttime() == null ? other.getStarttime() == null
						: this.getStarttime().equals(other.getStarttime()))
				&& (this.getEndtime() == null ? other.getEndtime() == null
						: this.getEndtime().equals(other.getEndtime()))
				&& (this.getNumber() == null ? other.getNumber() == null : this
						.getNumber().equals(other.getNumber()))
				&& (this.getTotalfees() == null ? other.getTotalfees() == null
						: this.getTotalfees().equals(other.getTotalfees()))
				&& (this.getGeneratetime() == null ? other.getGeneratetime() == null
						: this.getGeneratetime()
								.equals(other.getGeneratetime()))
				&& (this.getIspay() == null ? other.getIspay() == null : this
						.getIspay().equals(other.getIspay()))
				&& (this.getPaytime() == null ? other.getPaytime() == null
						: this.getPaytime().equals(other.getPaytime()))
				&& (this.getRecordId() == null ? other.getRecordId() == null
						: this.getRecordId().equals(other.getRecordId()))
				&& (this.getRecordName() == null ? other.getRecordName() == null
						: this.getRecordName().equals(other.getRecordName()))
				&& (this.getRecordtime() == null ? other.getRecordtime() == null
						: this.getRecordtime().equals(other.getRecordtime()))
				&& (this.getNotes() == null ? other.getNotes() == null : this
						.getNotes().equals(other.getNotes()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getSource() == null ? other.getSource() == null : this
						.getSource().equals(other.getSource()))
				&& (this.getRefundId() == null ? other.getRefundId() == null
						: this.getRefundId().equals(other.getRefundId()))
				&& (this.getRefundType() == null ? other.getRefundType() == null
						: this.getRefundType().equals(other.getRefundType()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_temppaymentdetail
	 * @mbggenerated  Sun Nov 10 09:44:01 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getPaymentId() == null) ? 0 : getPaymentId().hashCode());
		result = prime * result
				+ ((getOlderId() == null) ? 0 : getOlderId().hashCode());
		result = prime * result
				+ ((getItemsId() == null) ? 0 : getItemsId().hashCode());
		result = prime * result
				+ ((getItemName() == null) ? 0 : getItemName().hashCode());
		result = prime * result
				+ ((getItemlfees() == null) ? 0 : getItemlfees().hashCode());
		result = prime * result
				+ ((getStarttime() == null) ? 0 : getStarttime().hashCode());
		result = prime * result
				+ ((getEndtime() == null) ? 0 : getEndtime().hashCode());
		result = prime * result
				+ ((getNumber() == null) ? 0 : getNumber().hashCode());
		result = prime * result
				+ ((getTotalfees() == null) ? 0 : getTotalfees().hashCode());
		result = prime
				* result
				+ ((getGeneratetime() == null) ? 0 : getGeneratetime()
						.hashCode());
		result = prime * result
				+ ((getIspay() == null) ? 0 : getIspay().hashCode());
		result = prime * result
				+ ((getPaytime() == null) ? 0 : getPaytime().hashCode());
		result = prime * result
				+ ((getRecordId() == null) ? 0 : getRecordId().hashCode());
		result = prime * result
				+ ((getRecordName() == null) ? 0 : getRecordName().hashCode());
		result = prime * result
				+ ((getRecordtime() == null) ? 0 : getRecordtime().hashCode());
		result = prime * result
				+ ((getNotes() == null) ? 0 : getNotes().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime * result
				+ ((getSource() == null) ? 0 : getSource().hashCode());
		result = prime * result
				+ ((getRefundId() == null) ? 0 : getRefundId().hashCode());
		result = prime * result
				+ ((getRefundType() == null) ? 0 : getRefundType().hashCode());
		return result;
	}
}