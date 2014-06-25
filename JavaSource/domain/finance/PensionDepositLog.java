package domain.finance;

import java.io.Serializable;
import java.util.Date;

public class PensionDepositLog implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_deposit_log.id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_deposit_log.deposit_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private Long depositId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_deposit_log.deposit_type_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private Long depositTypeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_deposit_log.deposit_type_name
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private String depositTypeName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_deposit_log.tradeFee
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private Float tradefee;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_deposit_log.trader_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private Long traderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_deposit_log.trader_name
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private String traderName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_deposit_log.trade_date
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private Date tradeDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_deposit_log.settled_flag
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private Integer settledFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_deposit_log.settle_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private Long settleId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_deposit_log.backFlag
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private Integer backflag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_deposit_log
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_deposit_log.id
	 * @return  the value of pension_deposit_log.id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_deposit_log.id
	 * @param id  the value for pension_deposit_log.id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_deposit_log.deposit_id
	 * @return  the value of pension_deposit_log.deposit_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public Long getDepositId() {
		return depositId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_deposit_log.deposit_id
	 * @param depositId  the value for pension_deposit_log.deposit_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_deposit_log.deposit_type_id
	 * @return  the value of pension_deposit_log.deposit_type_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public Long getDepositTypeId() {
		return depositTypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_deposit_log.deposit_type_id
	 * @param depositTypeId  the value for pension_deposit_log.deposit_type_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public void setDepositTypeId(Long depositTypeId) {
		this.depositTypeId = depositTypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_deposit_log.deposit_type_name
	 * @return  the value of pension_deposit_log.deposit_type_name
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public String getDepositTypeName() {
		return depositTypeName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_deposit_log.deposit_type_name
	 * @param depositTypeName  the value for pension_deposit_log.deposit_type_name
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public void setDepositTypeName(String depositTypeName) {
		this.depositTypeName = depositTypeName == null ? null : depositTypeName
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_deposit_log.tradeFee
	 * @return  the value of pension_deposit_log.tradeFee
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public Float getTradefee() {
		return tradefee;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_deposit_log.tradeFee
	 * @param tradefee  the value for pension_deposit_log.tradeFee
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public void setTradefee(Float tradefee) {
		this.tradefee = tradefee;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_deposit_log.trader_id
	 * @return  the value of pension_deposit_log.trader_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public Long getTraderId() {
		return traderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_deposit_log.trader_id
	 * @param traderId  the value for pension_deposit_log.trader_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public void setTraderId(Long traderId) {
		this.traderId = traderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_deposit_log.trader_name
	 * @return  the value of pension_deposit_log.trader_name
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public String getTraderName() {
		return traderName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_deposit_log.trader_name
	 * @param traderName  the value for pension_deposit_log.trader_name
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public void setTraderName(String traderName) {
		this.traderName = traderName == null ? null : traderName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_deposit_log.trade_date
	 * @return  the value of pension_deposit_log.trade_date
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public Date getTradeDate() {
		return tradeDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_deposit_log.trade_date
	 * @param tradeDate  the value for pension_deposit_log.trade_date
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_deposit_log.settled_flag
	 * @return  the value of pension_deposit_log.settled_flag
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public Integer getSettledFlag() {
		return settledFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_deposit_log.settled_flag
	 * @param settledFlag  the value for pension_deposit_log.settled_flag
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public void setSettledFlag(Integer settledFlag) {
		this.settledFlag = settledFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_deposit_log.settle_id
	 * @return  the value of pension_deposit_log.settle_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public Long getSettleId() {
		return settleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_deposit_log.settle_id
	 * @param settleId  the value for pension_deposit_log.settle_id
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public void setSettleId(Long settleId) {
		this.settleId = settleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_deposit_log.backFlag
	 * @return  the value of pension_deposit_log.backFlag
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public Integer getBackflag() {
		return backflag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_deposit_log.backFlag
	 * @param backflag  the value for pension_deposit_log.backFlag
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	public void setBackflag(Integer backflag) {
		this.backflag = backflag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_deposit_log
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
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
		PensionDepositLog other = (PensionDepositLog) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getDepositId() == null ? other.getDepositId() == null
						: this.getDepositId().equals(other.getDepositId()))
				&& (this.getDepositTypeId() == null ? other.getDepositTypeId() == null
						: this.getDepositTypeId().equals(
								other.getDepositTypeId()))
				&& (this.getDepositTypeName() == null ? other
						.getDepositTypeName() == null : this
						.getDepositTypeName()
						.equals(other.getDepositTypeName()))
				&& (this.getTradefee() == null ? other.getTradefee() == null
						: this.getTradefee().equals(other.getTradefee()))
				&& (this.getTraderId() == null ? other.getTraderId() == null
						: this.getTraderId().equals(other.getTraderId()))
				&& (this.getTraderName() == null ? other.getTraderName() == null
						: this.getTraderName().equals(other.getTraderName()))
				&& (this.getTradeDate() == null ? other.getTradeDate() == null
						: this.getTradeDate().equals(other.getTradeDate()))
				&& (this.getSettledFlag() == null ? other.getSettledFlag() == null
						: this.getSettledFlag().equals(other.getSettledFlag()))
				&& (this.getSettleId() == null ? other.getSettleId() == null
						: this.getSettleId().equals(other.getSettleId()))
				&& (this.getBackflag() == null ? other.getBackflag() == null
						: this.getBackflag().equals(other.getBackflag()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_deposit_log
	 * @mbggenerated  Mon Nov 11 15:42:39 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getDepositId() == null) ? 0 : getDepositId().hashCode());
		result = prime
				* result
				+ ((getDepositTypeId() == null) ? 0 : getDepositTypeId()
						.hashCode());
		result = prime
				* result
				+ ((getDepositTypeName() == null) ? 0 : getDepositTypeName()
						.hashCode());
		result = prime * result
				+ ((getTradefee() == null) ? 0 : getTradefee().hashCode());
		result = prime * result
				+ ((getTraderId() == null) ? 0 : getTraderId().hashCode());
		result = prime * result
				+ ((getTraderName() == null) ? 0 : getTraderName().hashCode());
		result = prime * result
				+ ((getTradeDate() == null) ? 0 : getTradeDate().hashCode());
		result = prime
				* result
				+ ((getSettledFlag() == null) ? 0 : getSettledFlag().hashCode());
		result = prime * result
				+ ((getSettleId() == null) ? 0 : getSettleId().hashCode());
		result = prime * result
				+ ((getBackflag() == null) ? 0 : getBackflag().hashCode());
		return result;
	}

	private String olderName;
	private String deptName;

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}