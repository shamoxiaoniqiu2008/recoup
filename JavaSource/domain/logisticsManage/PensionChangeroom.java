package domain.logisticsManage;

import java.io.Serializable;
import java.util.Date;

public class PensionChangeroom implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_changeroom.id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_changeroom.apply_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	private Long applyId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_changeroom.oldbed_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	private Long oldbedId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_changeroom.newbed_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	private Long newbedId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_changeroom.mover_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	private Long moverId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_changeroom.changeroom_flag
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	private Integer changeroomFlag;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_changeroom.cleared
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_changeroom.note
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	private String note;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_changeroom
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_changeroom.id
	 * @return  the value of pension_changeroom.id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_changeroom.id
	 * @param id  the value for pension_changeroom.id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_changeroom.apply_id
	 * @return  the value of pension_changeroom.apply_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public Long getApplyId() {
		return applyId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_changeroom.apply_id
	 * @param applyId  the value for pension_changeroom.apply_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_changeroom.oldbed_id
	 * @return  the value of pension_changeroom.oldbed_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public Long getOldbedId() {
		return oldbedId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_changeroom.oldbed_id
	 * @param oldbedId  the value for pension_changeroom.oldbed_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public void setOldbedId(Long oldbedId) {
		this.oldbedId = oldbedId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_changeroom.newbed_id
	 * @return  the value of pension_changeroom.newbed_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public Long getNewbedId() {
		return newbedId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_changeroom.newbed_id
	 * @param newbedId  the value for pension_changeroom.newbed_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public void setNewbedId(Long newbedId) {
		this.newbedId = newbedId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_changeroom.mover_id
	 * @return  the value of pension_changeroom.mover_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public Long getMoverId() {
		return moverId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_changeroom.mover_id
	 * @param moverId  the value for pension_changeroom.mover_id
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public void setMoverId(Long moverId) {
		this.moverId = moverId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_changeroom.changeroom_flag
	 * @return  the value of pension_changeroom.changeroom_flag
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public Integer getChangeroomFlag() {
		return changeroomFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_changeroom.changeroom_flag
	 * @param changeroomFlag  the value for pension_changeroom.changeroom_flag
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public void setChangeroomFlag(Integer changeroomFlag) {
		this.changeroomFlag = changeroomFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_changeroom.cleared
	 * @return  the value of pension_changeroom.cleared
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_changeroom.cleared
	 * @param cleared  the value for pension_changeroom.cleared
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_changeroom.note
	 * @return  the value of pension_changeroom.note
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public String getNote() {
		return note;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_changeroom.note
	 * @param note  the value for pension_changeroom.note
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_changeroom
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
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
		PensionChangeroom other = (PensionChangeroom) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getApplyId() == null ? other.getApplyId() == null
						: this.getApplyId().equals(other.getApplyId()))
				&& (this.getOldbedId() == null ? other.getOldbedId() == null
						: this.getOldbedId().equals(other.getOldbedId()))
				&& (this.getNewbedId() == null ? other.getNewbedId() == null
						: this.getNewbedId().equals(other.getNewbedId()))
				&& (this.getMoverId() == null ? other.getMoverId() == null
						: this.getMoverId().equals(other.getMoverId()))
				&& (this.getChangeroomFlag() == null ? other
						.getChangeroomFlag() == null : this.getChangeroomFlag()
						.equals(other.getChangeroomFlag()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getNote() == null ? other.getNote() == null : this
						.getNote().equals(other.getNote()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_changeroom
	 * @mbggenerated  Tue Dec 24 13:52:14 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result
				+ ((getApplyId() == null) ? 0 : getApplyId().hashCode());
		result = prime * result
				+ ((getOldbedId() == null) ? 0 : getOldbedId().hashCode());
		result = prime * result
				+ ((getNewbedId() == null) ? 0 : getNewbedId().hashCode());
		result = prime * result
				+ ((getMoverId() == null) ? 0 : getMoverId().hashCode());
		result = prime
				* result
				+ ((getChangeroomFlag() == null) ? 0 : getChangeroomFlag()
						.hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime * result
				+ ((getNote() == null) ? 0 : getNote().hashCode());
		return result;
	}
}