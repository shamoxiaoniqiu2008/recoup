package domain.reportManage;

import java.io.Serializable;

public class PensionOldertypeAmount implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_oldertype_amount.id
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_oldertype_amount.report_id
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	private Long reportId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_oldertype_amount.oldertype_id
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	private Long oldertypeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_oldertype_amount.older_amount
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	private Integer olderAmount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_oldertype_amount.cleared
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_oldertype_amount.note
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	private String note;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_oldertype_amount.oldertype_name
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	private String oldertypeName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_oldertype_amount
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_oldertype_amount.id
	 * @return  the value of pension_oldertype_amount.id
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_oldertype_amount.id
	 * @param id  the value for pension_oldertype_amount.id
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_oldertype_amount.report_id
	 * @return  the value of pension_oldertype_amount.report_id
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public Long getReportId() {
		return reportId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_oldertype_amount.report_id
	 * @param reportId  the value for pension_oldertype_amount.report_id
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_oldertype_amount.oldertype_id
	 * @return  the value of pension_oldertype_amount.oldertype_id
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public Long getOldertypeId() {
		return oldertypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_oldertype_amount.oldertype_id
	 * @param oldertypeId  the value for pension_oldertype_amount.oldertype_id
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public void setOldertypeId(Long oldertypeId) {
		this.oldertypeId = oldertypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_oldertype_amount.older_amount
	 * @return  the value of pension_oldertype_amount.older_amount
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public Integer getOlderAmount() {
		return olderAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_oldertype_amount.older_amount
	 * @param olderAmount  the value for pension_oldertype_amount.older_amount
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public void setOlderAmount(Integer olderAmount) {
		this.olderAmount = olderAmount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_oldertype_amount.cleared
	 * @return  the value of pension_oldertype_amount.cleared
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_oldertype_amount.cleared
	 * @param cleared  the value for pension_oldertype_amount.cleared
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_oldertype_amount.note
	 * @return  the value of pension_oldertype_amount.note
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public String getNote() {
		return note;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_oldertype_amount.note
	 * @param note  the value for pension_oldertype_amount.note
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_oldertype_amount.oldertype_name
	 * @return  the value of pension_oldertype_amount.oldertype_name
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public String getOldertypeName() {
		return oldertypeName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_oldertype_amount.oldertype_name
	 * @param oldertypeName  the value for pension_oldertype_amount.oldertype_name
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	public void setOldertypeName(String oldertypeName) {
		this.oldertypeName = oldertypeName == null ? null : oldertypeName
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_oldertype_amount
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
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
		PensionOldertypeAmount other = (PensionOldertypeAmount) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getReportId() == null ? other.getReportId() == null
						: this.getReportId().equals(other.getReportId()))
				&& (this.getOldertypeId() == null ? other.getOldertypeId() == null
						: this.getOldertypeId().equals(other.getOldertypeId()))
				&& (this.getOlderAmount() == null ? other.getOlderAmount() == null
						: this.getOlderAmount().equals(other.getOlderAmount()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getNote() == null ? other.getNote() == null : this
						.getNote().equals(other.getNote()))
				&& (this.getOldertypeName() == null ? other.getOldertypeName() == null
						: this.getOldertypeName().equals(
								other.getOldertypeName()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_oldertype_amount
	 * @mbggenerated  Sat Nov 16 11:28:35 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getReportId() == null) ? 0 : getReportId().hashCode());
		result = prime
				* result
				+ ((getOldertypeId() == null) ? 0 : getOldertypeId().hashCode());
		result = prime
				* result
				+ ((getOlderAmount() == null) ? 0 : getOlderAmount().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime * result
				+ ((getNote() == null) ? 0 : getNote().hashCode());
		result = prime
				* result
				+ ((getOldertypeName() == null) ? 0 : getOldertypeName()
						.hashCode());
		return result;
	}
}