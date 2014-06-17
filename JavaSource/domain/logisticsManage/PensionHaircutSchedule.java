package domain.logisticsManage;

import java.io.Serializable;
import java.util.Date;

public class PensionHaircutSchedule implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_haircut_schedule.id
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_haircut_schedule.start_time
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private Date startTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_haircut_schedule.end_time
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private Date endTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_haircut_schedule.haircut_address
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private String haircutAddress;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_haircut_schedule.item_name
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private String itemName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_haircut_schedule.max_number
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private Integer maxNumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_haircut_schedule.post_flag
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private Integer postFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_haircut_schedule.current_ordernumber
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private Integer currentOrdernumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_haircut_schedule.haircuted_number
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private Integer haircutedNumber;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_haircut_schedule.cleared
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_haircut_schedule.note
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private String note;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_haircut_schedule
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_haircut_schedule.id
	 * @return  the value of pension_haircut_schedule.id
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_haircut_schedule.id
	 * @param id  the value for pension_haircut_schedule.id
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_haircut_schedule.start_time
	 * @return  the value of pension_haircut_schedule.start_time
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_haircut_schedule.start_time
	 * @param startTime  the value for pension_haircut_schedule.start_time
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_haircut_schedule.end_time
	 * @return  the value of pension_haircut_schedule.end_time
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_haircut_schedule.end_time
	 * @param endTime  the value for pension_haircut_schedule.end_time
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_haircut_schedule.haircut_address
	 * @return  the value of pension_haircut_schedule.haircut_address
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public String getHaircutAddress() {
		return haircutAddress;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_haircut_schedule.haircut_address
	 * @param haircutAddress  the value for pension_haircut_schedule.haircut_address
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public void setHaircutAddress(String haircutAddress) {
		this.haircutAddress = haircutAddress == null ? null : haircutAddress
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_haircut_schedule.item_name
	 * @return  the value of pension_haircut_schedule.item_name
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_haircut_schedule.item_name
	 * @param itemName  the value for pension_haircut_schedule.item_name
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName == null ? null : itemName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_haircut_schedule.max_number
	 * @return  the value of pension_haircut_schedule.max_number
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public Integer getMaxNumber() {
		return maxNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_haircut_schedule.max_number
	 * @param maxNumber  the value for pension_haircut_schedule.max_number
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_haircut_schedule.post_flag
	 * @return  the value of pension_haircut_schedule.post_flag
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public Integer getPostFlag() {
		return postFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_haircut_schedule.post_flag
	 * @param postFlag  the value for pension_haircut_schedule.post_flag
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public void setPostFlag(Integer postFlag) {
		this.postFlag = postFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_haircut_schedule.current_ordernumber
	 * @return  the value of pension_haircut_schedule.current_ordernumber
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public Integer getCurrentOrdernumber() {
		return currentOrdernumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_haircut_schedule.current_ordernumber
	 * @param currentOrdernumber  the value for pension_haircut_schedule.current_ordernumber
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public void setCurrentOrdernumber(Integer currentOrdernumber) {
		this.currentOrdernumber = currentOrdernumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_haircut_schedule.haircuted_number
	 * @return  the value of pension_haircut_schedule.haircuted_number
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public Integer getHaircutedNumber() {
		return haircutedNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_haircut_schedule.haircuted_number
	 * @param haircutedNumber  the value for pension_haircut_schedule.haircuted_number
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public void setHaircutedNumber(Integer haircutedNumber) {
		this.haircutedNumber = haircutedNumber;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_haircut_schedule.cleared
	 * @return  the value of pension_haircut_schedule.cleared
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_haircut_schedule.cleared
	 * @param cleared  the value for pension_haircut_schedule.cleared
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_haircut_schedule.note
	 * @return  the value of pension_haircut_schedule.note
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public String getNote() {
		return note;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_haircut_schedule.note
	 * @param note  the value for pension_haircut_schedule.note
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_haircut_schedule
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
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
		PensionHaircutSchedule other = (PensionHaircutSchedule) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getStartTime() == null ? other.getStartTime() == null
						: this.getStartTime().equals(other.getStartTime()))
				&& (this.getEndTime() == null ? other.getEndTime() == null
						: this.getEndTime().equals(other.getEndTime()))
				&& (this.getHaircutAddress() == null ? other
						.getHaircutAddress() == null : this.getHaircutAddress()
						.equals(other.getHaircutAddress()))
				&& (this.getItemName() == null ? other.getItemName() == null
						: this.getItemName().equals(other.getItemName()))
				&& (this.getMaxNumber() == null ? other.getMaxNumber() == null
						: this.getMaxNumber().equals(other.getMaxNumber()))
				&& (this.getPostFlag() == null ? other.getPostFlag() == null
						: this.getPostFlag().equals(other.getPostFlag()))
				&& (this.getCurrentOrdernumber() == null ? other
						.getCurrentOrdernumber() == null : this
						.getCurrentOrdernumber().equals(
								other.getCurrentOrdernumber()))
				&& (this.getHaircutedNumber() == null ? other
						.getHaircutedNumber() == null : this
						.getHaircutedNumber()
						.equals(other.getHaircutedNumber()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getNote() == null ? other.getNote() == null : this
						.getNote().equals(other.getNote()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_haircut_schedule
	 * @mbggenerated  Fri Nov 15 10:10:01 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getStartTime() == null) ? 0 : getStartTime().hashCode());
		result = prime * result
				+ ((getEndTime() == null) ? 0 : getEndTime().hashCode());
		result = prime
				* result
				+ ((getHaircutAddress() == null) ? 0 : getHaircutAddress()
						.hashCode());
		result = prime * result
				+ ((getItemName() == null) ? 0 : getItemName().hashCode());
		result = prime * result
				+ ((getMaxNumber() == null) ? 0 : getMaxNumber().hashCode());
		result = prime * result
				+ ((getPostFlag() == null) ? 0 : getPostFlag().hashCode());
		result = prime
				* result
				+ ((getCurrentOrdernumber() == null) ? 0
						: getCurrentOrdernumber().hashCode());
		result = prime
				* result
				+ ((getHaircutedNumber() == null) ? 0 : getHaircutedNumber()
						.hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime * result
				+ ((getNote() == null) ? 0 : getNote().hashCode());
		return result;
	}
	
	private int duration;//持续时间
	private String durationStr;//持续时间
	private String barber;//理发师
	
	
	
	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDurationStr() {
		return durationStr;
	}

	public void setDurationStr(String durationStr) {
		this.durationStr = durationStr;
	}

	public String getBarber() {
		return barber;
	}

	public void setBarber(String barber) {
		this.barber = barber;
	}

	

	
	
	
	
}