package domain.nurseManage;

import java.io.Serializable;
import java.util.Date;

public class PensionCareAppointment implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_care_appointment.id
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_care_appointment.older_id
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	private Long olderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_care_appointment.older_name
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	private String olderName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_care_appointment.appointment_time
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	private Date appointmentTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_care_appointment.family_sign
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	private Integer familySign;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_care_appointment.generate_flag
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	private Integer generateFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_care_appointment.send_flag
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	private Integer sendFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_care_appointment.cleared
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_care_appointment.note
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	private String note;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_care_appointment
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_care_appointment.id
	 * @return  the value of pension_care_appointment.id
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_care_appointment.id
	 * @param id  the value for pension_care_appointment.id
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_care_appointment.older_id
	 * @return  the value of pension_care_appointment.older_id
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public Long getOlderId() {
		return olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_care_appointment.older_id
	 * @param olderId  the value for pension_care_appointment.older_id
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_care_appointment.older_name
	 * @return  the value of pension_care_appointment.older_name
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public String getOlderName() {
		return olderName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_care_appointment.older_name
	 * @param olderName  the value for pension_care_appointment.older_name
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public void setOlderName(String olderName) {
		this.olderName = olderName == null ? null : olderName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_care_appointment.appointment_time
	 * @return  the value of pension_care_appointment.appointment_time
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public Date getAppointmentTime() {
		return appointmentTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_care_appointment.appointment_time
	 * @param appointmentTime  the value for pension_care_appointment.appointment_time
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_care_appointment.family_sign
	 * @return  the value of pension_care_appointment.family_sign
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public Integer getFamilySign() {
		return familySign;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_care_appointment.family_sign
	 * @param familySign  the value for pension_care_appointment.family_sign
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public void setFamilySign(Integer familySign) {
		this.familySign = familySign;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_care_appointment.generate_flag
	 * @return  the value of pension_care_appointment.generate_flag
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public Integer getGenerateFlag() {
		return generateFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_care_appointment.generate_flag
	 * @param generateFlag  the value for pension_care_appointment.generate_flag
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public void setGenerateFlag(Integer generateFlag) {
		this.generateFlag = generateFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_care_appointment.send_flag
	 * @return  the value of pension_care_appointment.send_flag
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public Integer getSendFlag() {
		return sendFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_care_appointment.send_flag
	 * @param sendFlag  the value for pension_care_appointment.send_flag
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_care_appointment.cleared
	 * @return  the value of pension_care_appointment.cleared
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_care_appointment.cleared
	 * @param cleared  the value for pension_care_appointment.cleared
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_care_appointment.note
	 * @return  the value of pension_care_appointment.note
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public String getNote() {
		return note;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_care_appointment.note
	 * @param note  the value for pension_care_appointment.note
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_care_appointment
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
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
		PensionCareAppointment other = (PensionCareAppointment) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getOlderId() == null ? other.getOlderId() == null
						: this.getOlderId().equals(other.getOlderId()))
				&& (this.getOlderName() == null ? other.getOlderName() == null
						: this.getOlderName().equals(other.getOlderName()))
				&& (this.getAppointmentTime() == null ? other
						.getAppointmentTime() == null : this
						.getAppointmentTime()
						.equals(other.getAppointmentTime()))
				&& (this.getFamilySign() == null ? other.getFamilySign() == null
						: this.getFamilySign().equals(other.getFamilySign()))
				&& (this.getGenerateFlag() == null ? other.getGenerateFlag() == null
						: this.getGenerateFlag()
								.equals(other.getGenerateFlag()))
				&& (this.getSendFlag() == null ? other.getSendFlag() == null
						: this.getSendFlag().equals(other.getSendFlag()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getNote() == null ? other.getNote() == null : this
						.getNote().equals(other.getNote()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_care_appointment
	 * @mbggenerated  Wed Nov 20 15:36:39 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getOlderId() == null) ? 0 : getOlderId().hashCode());
		result = prime * result
				+ ((getOlderName() == null) ? 0 : getOlderName().hashCode());
		result = prime
				* result
				+ ((getAppointmentTime() == null) ? 0 : getAppointmentTime()
						.hashCode());
		result = prime * result
				+ ((getFamilySign() == null) ? 0 : getFamilySign().hashCode());
		result = prime
				* result
				+ ((getGenerateFlag() == null) ? 0 : getGenerateFlag()
						.hashCode());
		result = prime * result
				+ ((getSendFlag() == null) ? 0 : getSendFlag().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime * result
				+ ((getNote() == null) ? 0 : getNote().hashCode());
		return result;
	}
}