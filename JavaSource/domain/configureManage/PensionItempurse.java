package domain.configureManage;

import java.io.Serializable;
import java.util.Date;

public class PensionItempurse implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.id
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.itemType
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private Integer itemtype;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.itemName
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private String itemname;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.inputCode
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private String inputcode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.purse
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private Float purse;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.unit
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private String unit;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.identity
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private Integer identity;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.recorder_id
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private Long recorderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.recorderrName
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private String recorderrname;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.recordTime
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private Date recordtime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.notes
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private String notes;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.cleared
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.pricetype_id
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private Integer pricetypeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_itempurse.pricetype_name
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private String pricetypeName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_itempurse
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.id
	 * @return  the value of pension_itempurse.id
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.id
	 * @param id  the value for pension_itempurse.id
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.itemType
	 * @return  the value of pension_itempurse.itemType
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public Integer getItemtype() {
		return itemtype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.itemType
	 * @param itemtype  the value for pension_itempurse.itemType
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setItemtype(Integer itemtype) {
		this.itemtype = itemtype;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.itemName
	 * @return  the value of pension_itempurse.itemName
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public String getItemname() {
		return itemname;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.itemName
	 * @param itemname  the value for pension_itempurse.itemName
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setItemname(String itemname) {
		this.itemname = itemname == null ? null : itemname.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.inputCode
	 * @return  the value of pension_itempurse.inputCode
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public String getInputcode() {
		return inputcode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.inputCode
	 * @param inputcode  the value for pension_itempurse.inputCode
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setInputcode(String inputcode) {
		this.inputcode = inputcode == null ? null : inputcode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.purse
	 * @return  the value of pension_itempurse.purse
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public Float getPurse() {
		return purse;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.purse
	 * @param purse  the value for pension_itempurse.purse
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setPurse(Float purse) {
		this.purse = purse;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.unit
	 * @return  the value of pension_itempurse.unit
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.unit
	 * @param unit  the value for pension_itempurse.unit
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.identity
	 * @return  the value of pension_itempurse.identity
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public Integer getIdentity() {
		return identity;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.identity
	 * @param identity  the value for pension_itempurse.identity
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.recorder_id
	 * @return  the value of pension_itempurse.recorder_id
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public Long getRecorderId() {
		return recorderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.recorder_id
	 * @param recorderId  the value for pension_itempurse.recorder_id
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setRecorderId(Long recorderId) {
		this.recorderId = recorderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.recorderrName
	 * @return  the value of pension_itempurse.recorderrName
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public String getRecorderrname() {
		return recorderrname;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.recorderrName
	 * @param recorderrname  the value for pension_itempurse.recorderrName
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setRecorderrname(String recorderrname) {
		this.recorderrname = recorderrname == null ? null : recorderrname
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.recordTime
	 * @return  the value of pension_itempurse.recordTime
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public Date getRecordtime() {
		return recordtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.recordTime
	 * @param recordtime  the value for pension_itempurse.recordTime
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setRecordtime(Date recordtime) {
		this.recordtime = recordtime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.notes
	 * @return  the value of pension_itempurse.notes
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.notes
	 * @param notes  the value for pension_itempurse.notes
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.cleared
	 * @return  the value of pension_itempurse.cleared
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.cleared
	 * @param cleared  the value for pension_itempurse.cleared
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.pricetype_id
	 * @return  the value of pension_itempurse.pricetype_id
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public Integer getPricetypeId() {
		return pricetypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.pricetype_id
	 * @param pricetypeId  the value for pension_itempurse.pricetype_id
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setPricetypeId(Integer pricetypeId) {
		this.pricetypeId = pricetypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_itempurse.pricetype_name
	 * @return  the value of pension_itempurse.pricetype_name
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public String getPricetypeName() {
		return pricetypeName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_itempurse.pricetype_name
	 * @param pricetypeName  the value for pension_itempurse.pricetype_name
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	public void setPricetypeName(String pricetypeName) {
		this.pricetypeName = pricetypeName == null ? null : pricetypeName
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itempurse
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
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
		PensionItempurse other = (PensionItempurse) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getItemtype() == null ? other.getItemtype() == null
						: this.getItemtype().equals(other.getItemtype()))
				&& (this.getItemname() == null ? other.getItemname() == null
						: this.getItemname().equals(other.getItemname()))
				&& (this.getInputcode() == null ? other.getInputcode() == null
						: this.getInputcode().equals(other.getInputcode()))
				&& (this.getPurse() == null ? other.getPurse() == null : this
						.getPurse().equals(other.getPurse()))
				&& (this.getUnit() == null ? other.getUnit() == null : this
						.getUnit().equals(other.getUnit()))
				&& (this.getIdentity() == null ? other.getIdentity() == null
						: this.getIdentity().equals(other.getIdentity()))
				&& (this.getRecorderId() == null ? other.getRecorderId() == null
						: this.getRecorderId().equals(other.getRecorderId()))
				&& (this.getRecorderrname() == null ? other.getRecorderrname() == null
						: this.getRecorderrname().equals(
								other.getRecorderrname()))
				&& (this.getRecordtime() == null ? other.getRecordtime() == null
						: this.getRecordtime().equals(other.getRecordtime()))
				&& (this.getNotes() == null ? other.getNotes() == null : this
						.getNotes().equals(other.getNotes()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getPricetypeId() == null ? other.getPricetypeId() == null
						: this.getPricetypeId().equals(other.getPricetypeId()))
				&& (this.getPricetypeName() == null ? other.getPricetypeName() == null
						: this.getPricetypeName().equals(
								other.getPricetypeName()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itempurse
	 * @mbggenerated  Fri Nov 01 11:18:54 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getItemtype() == null) ? 0 : getItemtype().hashCode());
		result = prime * result
				+ ((getItemname() == null) ? 0 : getItemname().hashCode());
		result = prime * result
				+ ((getInputcode() == null) ? 0 : getInputcode().hashCode());
		result = prime * result
				+ ((getPurse() == null) ? 0 : getPurse().hashCode());
		result = prime * result
				+ ((getUnit() == null) ? 0 : getUnit().hashCode());
		result = prime * result
				+ ((getIdentity() == null) ? 0 : getIdentity().hashCode());
		result = prime * result
				+ ((getRecorderId() == null) ? 0 : getRecorderId().hashCode());
		result = prime
				* result
				+ ((getRecorderrname() == null) ? 0 : getRecorderrname()
						.hashCode());
		result = prime * result
				+ ((getRecordtime() == null) ? 0 : getRecordtime().hashCode());
		result = prime * result
				+ ((getNotes() == null) ? 0 : getNotes().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime
				* result
				+ ((getPricetypeId() == null) ? 0 : getPricetypeId().hashCode());
		result = prime
				* result
				+ ((getPricetypeName() == null) ? 0 : getPricetypeName()
						.hashCode());
		return result;
	}
}