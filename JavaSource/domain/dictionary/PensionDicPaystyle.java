package domain.dictionary;

import java.io.Serializable;

public class PensionDicPaystyle implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dic_paystyle.id
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dic_paystyle.inputCode
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	private String inputcode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dic_paystyle.type
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	private String type;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dic_paystyle.note
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	private String note;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_dic_paystyle.invalided
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	private Integer invalided;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_dic_paystyle
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dic_paystyle.id
	 * @return  the value of pension_dic_paystyle.id
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dic_paystyle.id
	 * @param id  the value for pension_dic_paystyle.id
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dic_paystyle.inputCode
	 * @return  the value of pension_dic_paystyle.inputCode
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public String getInputcode() {
		return inputcode;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dic_paystyle.inputCode
	 * @param inputcode  the value for pension_dic_paystyle.inputCode
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public void setInputcode(String inputcode) {
		this.inputcode = inputcode == null ? null : inputcode.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dic_paystyle.type
	 * @return  the value of pension_dic_paystyle.type
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public String getType() {
		return type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dic_paystyle.type
	 * @param type  the value for pension_dic_paystyle.type
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dic_paystyle.note
	 * @return  the value of pension_dic_paystyle.note
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public String getNote() {
		return note;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dic_paystyle.note
	 * @param note  the value for pension_dic_paystyle.note
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_dic_paystyle.invalided
	 * @return  the value of pension_dic_paystyle.invalided
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public Integer getInvalided() {
		return invalided;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_dic_paystyle.invalided
	 * @param invalided  the value for pension_dic_paystyle.invalided
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public void setInvalided(Integer invalided) {
		this.invalided = invalided;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_paystyle
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
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
		PensionDicPaystyle other = (PensionDicPaystyle) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getInputcode() == null ? other.getInputcode() == null
						: this.getInputcode().equals(other.getInputcode()))
				&& (this.getType() == null ? other.getType() == null : this
						.getType().equals(other.getType()))
				&& (this.getNote() == null ? other.getNote() == null : this
						.getNote().equals(other.getNote()))
				&& (this.getInvalided() == null ? other.getInvalided() == null
						: this.getInvalided().equals(other.getInvalided()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_paystyle
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getInputcode() == null) ? 0 : getInputcode().hashCode());
		result = prime * result
				+ ((getType() == null) ? 0 : getType().hashCode());
		result = prime * result
				+ ((getNote() == null) ? 0 : getNote().hashCode());
		result = prime * result
				+ ((getInvalided() == null) ? 0 : getInvalided().hashCode());
		return result;
	}
}