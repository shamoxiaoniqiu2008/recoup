package domain.dictionary;

import java.io.Serializable;

public class PensionDicDeposittype implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dic_deposittype.id
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dic_deposittype.type
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	private String type;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dic_deposittype.invalided
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	private Integer invalided;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dic_deposittype.inputCode
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	private String inputcode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dic_deposittype.note
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	private String note;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dic_deposittype.fee
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	private Float fee;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_dic_deposittype
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dic_deposittype.id
	 * @return  the value of pension_dic_deposittype.id
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dic_deposittype.id
	 * @param id  the value for pension_dic_deposittype.id
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dic_deposittype.type
	 * @return  the value of pension_dic_deposittype.type
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public String getType() {
		return type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dic_deposittype.type
	 * @param type  the value for pension_dic_deposittype.type
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dic_deposittype.invalided
	 * @return  the value of pension_dic_deposittype.invalided
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public Integer getInvalided() {
		return invalided;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dic_deposittype.invalided
	 * @param invalided  the value for pension_dic_deposittype.invalided
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public void setInvalided(Integer invalided) {
		this.invalided = invalided;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dic_deposittype.inputCode
	 * @return  the value of pension_dic_deposittype.inputCode
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public String getInputcode() {
		return inputcode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dic_deposittype.inputCode
	 * @param inputcode  the value for pension_dic_deposittype.inputCode
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public void setInputcode(String inputcode) {
		this.inputcode = inputcode == null ? null : inputcode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dic_deposittype.note
	 * @return  the value of pension_dic_deposittype.note
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public String getNote() {
		return note;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dic_deposittype.note
	 * @param note  the value for pension_dic_deposittype.note
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dic_deposittype.fee
	 * @return  the value of pension_dic_deposittype.fee
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public Float getFee() {
		return fee;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dic_deposittype.fee
	 * @param fee  the value for pension_dic_deposittype.fee
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	public void setFee(Float fee) {
		this.fee = fee;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_deposittype
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
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
		PensionDicDeposittype other = (PensionDicDeposittype) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getType() == null ? other.getType() == null : this
						.getType().equals(other.getType()))
				&& (this.getInvalided() == null ? other.getInvalided() == null
						: this.getInvalided().equals(other.getInvalided()))
				&& (this.getInputcode() == null ? other.getInputcode() == null
						: this.getInputcode().equals(other.getInputcode()))
				&& (this.getNote() == null ? other.getNote() == null : this
						.getNote().equals(other.getNote()))
				&& (this.getFee() == null ? other.getFee() == null : this
						.getFee().equals(other.getFee()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_deposittype
	 * @mbggenerated  Mon Dec 02 11:13:12 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getType() == null) ? 0 : getType().hashCode());
		result = prime * result
				+ ((getInvalided() == null) ? 0 : getInvalided().hashCode());
		result = prime * result
				+ ((getInputcode() == null) ? 0 : getInputcode().hashCode());
		result = prime * result
				+ ((getNote() == null) ? 0 : getNote().hashCode());
		result = prime * result
				+ ((getFee() == null) ? 0 : getFee().hashCode());
		return result;
	}
}