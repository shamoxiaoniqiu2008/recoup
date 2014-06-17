package domain.caterManage;

import java.io.Serializable;
import java.util.Date;

public class PensionOrder implements Serializable {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_order.id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_order.older_id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private Long olderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_order.foodMenu_id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private Long foodMenuId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_order.eatTime
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private Date eattime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_order.isComfirm
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private Integer iscomfirm;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_order.isPay
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private Integer ispay;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_order.notes
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private String notes;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_order.cleared
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_order.order_number
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private Integer orderNumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_order.payment_id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private Long paymentId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_order.send_flag
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private Integer sendFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_order
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_order.id
	 * @return  the value of pension_order.id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_order.id
	 * @param id  the value for pension_order.id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_order.older_id
	 * @return  the value of pension_order.older_id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public Long getOlderId() {
		return olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_order.older_id
	 * @param olderId  the value for pension_order.older_id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_order.foodMenu_id
	 * @return  the value of pension_order.foodMenu_id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public Long getFoodMenuId() {
		return foodMenuId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_order.foodMenu_id
	 * @param foodMenuId  the value for pension_order.foodMenu_id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public void setFoodMenuId(Long foodMenuId) {
		this.foodMenuId = foodMenuId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_order.eatTime
	 * @return  the value of pension_order.eatTime
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public Date getEattime() {
		return eattime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_order.eatTime
	 * @param eattime  the value for pension_order.eatTime
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public void setEattime(Date eattime) {
		this.eattime = eattime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_order.isComfirm
	 * @return  the value of pension_order.isComfirm
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public Integer getIscomfirm() {
		return iscomfirm;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_order.isComfirm
	 * @param iscomfirm  the value for pension_order.isComfirm
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public void setIscomfirm(Integer iscomfirm) {
		this.iscomfirm = iscomfirm;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_order.isPay
	 * @return  the value of pension_order.isPay
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public Integer getIspay() {
		return ispay;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_order.isPay
	 * @param ispay  the value for pension_order.isPay
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public void setIspay(Integer ispay) {
		this.ispay = ispay;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_order.notes
	 * @return  the value of pension_order.notes
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_order.notes
	 * @param notes  the value for pension_order.notes
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_order.cleared
	 * @return  the value of pension_order.cleared
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_order.cleared
	 * @param cleared  the value for pension_order.cleared
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_order.order_number
	 * @return  the value of pension_order.order_number
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public Integer getOrderNumber() {
		return orderNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_order.order_number
	 * @param orderNumber  the value for pension_order.order_number
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_order.payment_id
	 * @return  the value of pension_order.payment_id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public Long getPaymentId() {
		return paymentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_order.payment_id
	 * @param paymentId  the value for pension_order.payment_id
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_order.send_flag
	 * @return  the value of pension_order.send_flag
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public Integer getSendFlag() {
		return sendFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_order.send_flag
	 * @param sendFlag  the value for pension_order.send_flag
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_order
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
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
		PensionOrder other = (PensionOrder) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getOlderId() == null ? other.getOlderId() == null
						: this.getOlderId().equals(other.getOlderId()))
				&& (this.getFoodMenuId() == null ? other.getFoodMenuId() == null
						: this.getFoodMenuId().equals(other.getFoodMenuId()))
				&& (this.getEattime() == null ? other.getEattime() == null
						: this.getEattime().equals(other.getEattime()))
				&& (this.getIscomfirm() == null ? other.getIscomfirm() == null
						: this.getIscomfirm().equals(other.getIscomfirm()))
				&& (this.getIspay() == null ? other.getIspay() == null : this
						.getIspay().equals(other.getIspay()))
				&& (this.getNotes() == null ? other.getNotes() == null : this
						.getNotes().equals(other.getNotes()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getOrderNumber() == null ? other.getOrderNumber() == null
						: this.getOrderNumber().equals(other.getOrderNumber()))
				&& (this.getPaymentId() == null ? other.getPaymentId() == null
						: this.getPaymentId().equals(other.getPaymentId()))
				&& (this.getSendFlag() == null ? other.getSendFlag() == null
						: this.getSendFlag().equals(other.getSendFlag()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_order
	 * @mbggenerated  Sun Nov 10 11:01:52 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getOlderId() == null) ? 0 : getOlderId().hashCode());
		result = prime * result
				+ ((getFoodMenuId() == null) ? 0 : getFoodMenuId().hashCode());
		result = prime * result
				+ ((getEattime() == null) ? 0 : getEattime().hashCode());
		result = prime * result
				+ ((getIscomfirm() == null) ? 0 : getIscomfirm().hashCode());
		result = prime * result
				+ ((getIspay() == null) ? 0 : getIspay().hashCode());
		result = prime * result
				+ ((getNotes() == null) ? 0 : getNotes().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime
				* result
				+ ((getOrderNumber() == null) ? 0 : getOrderNumber().hashCode());
		result = prime * result
				+ ((getPaymentId() == null) ? 0 : getPaymentId().hashCode());
		result = prime * result
				+ ((getSendFlag() == null) ? 0 : getSendFlag().hashCode());
		return result;
	}

	//是否交款扩展
    private String isPayName;
    //老人姓名
    private String olderName;
    //菜名
    private String menuName;
    //是否确认
    private String isConfirmName;
    //是否选中
    private boolean choseFlag;
    //是否可�?
    private boolean selectDisableFlag = false;
    //是否派送
    private String sendFlagName;
    
    private boolean tempSendFlag;
    /**
	 * @return the isPayName
	 */
	public String getIsPayName() {
		return isPayName;
	}

	/**
	 * @param isPayName the isPayName to set
	 */
	public void setIsPayName(String isPayName) {
		this.isPayName = isPayName;
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
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * @return the isConfirmName
	 */
	public String getIsConfirmName() {
		return isConfirmName;
	}

	/**
	 * @param isConfirmName the isConfirmName to set
	 */
	public void setIsConfirmName(String isConfirmName) {
		this.isConfirmName = isConfirmName;
	}

	/**
	 * @return the choseFlag
	 */
	public boolean isChoseFlag() {
		return choseFlag;
	}

	/**
	 * @param choseFlag the choseFlag to set
	 */
	public void setChoseFlag(boolean choseFlag) {
		this.choseFlag = choseFlag;
	}

	/**
	 * @return the selectDisableFlag
	 */
	public boolean isSelectDisableFlag() {
		return selectDisableFlag;
	}

	/**
	 * @param selectDisableFlag the selectDisableFlag to set
	 */
	public void setSelectDisableFlag(boolean selectDisableFlag) {
		this.selectDisableFlag = selectDisableFlag;
	}

	/**
	 * @return the sendFlagName
	 */
	public String getSendFlagName() {
		return sendFlagName;
	}

	/**
	 * @param sendFlagName the sendFlagName to set
	 */
	public void setSendFlagName(String sendFlagName) {
		this.sendFlagName = sendFlagName;
	}

	public boolean isTempSendFlag() {
		return tempSendFlag;
	}

	public void setTempSendFlag(boolean tempSendFlag) {
		this.tempSendFlag = tempSendFlag;
	}


	

	
    
}