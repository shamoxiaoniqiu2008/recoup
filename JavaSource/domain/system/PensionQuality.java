package domain.system;

import java.io.Serializable;
import java.util.Date;

public class PensionQuality implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.quality_type
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private String qualityType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.quality_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private String qualityName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.qualityer_id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private Long qualityerId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.qualityer_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private String qualityerName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.quality_result
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private String qualityResult;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.quality_time
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private Date qualityTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.older_id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private Long olderId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.older_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private String olderName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.evaluation
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private Integer evaluation;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.item_id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private Long itemId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.cleared
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.table_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private String tableName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_quality.note
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private String note;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.id
	 * @return  the value of pension_quality.id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.id
	 * @param id  the value for pension_quality.id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.quality_type
	 * @return  the value of pension_quality.quality_type
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public String getQualityType() {
		return qualityType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.quality_type
	 * @param qualityType  the value for pension_quality.quality_type
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setQualityType(String qualityType) {
		this.qualityType = qualityType == null ? null : qualityType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.quality_name
	 * @return  the value of pension_quality.quality_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public String getQualityName() {
		return qualityName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.quality_name
	 * @param qualityName  the value for pension_quality.quality_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setQualityName(String qualityName) {
		this.qualityName = qualityName == null ? null : qualityName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.qualityer_id
	 * @return  the value of pension_quality.qualityer_id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public Long getQualityerId() {
		return qualityerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.qualityer_id
	 * @param qualityerId  the value for pension_quality.qualityer_id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setQualityerId(Long qualityerId) {
		this.qualityerId = qualityerId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.qualityer_name
	 * @return  the value of pension_quality.qualityer_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public String getQualityerName() {
		return qualityerName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.qualityer_name
	 * @param qualityerName  the value for pension_quality.qualityer_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setQualityerName(String qualityerName) {
		this.qualityerName = qualityerName == null ? null : qualityerName
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.quality_result
	 * @return  the value of pension_quality.quality_result
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public String getQualityResult() {
		return qualityResult;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.quality_result
	 * @param qualityResult  the value for pension_quality.quality_result
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setQualityResult(String qualityResult) {
		this.qualityResult = qualityResult == null ? null : qualityResult
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.quality_time
	 * @return  the value of pension_quality.quality_time
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public Date getQualityTime() {
		return qualityTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.quality_time
	 * @param qualityTime  the value for pension_quality.quality_time
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setQualityTime(Date qualityTime) {
		this.qualityTime = qualityTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.older_id
	 * @return  the value of pension_quality.older_id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public Long getOlderId() {
		return olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.older_id
	 * @param olderId  the value for pension_quality.older_id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.older_name
	 * @return  the value of pension_quality.older_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public String getOlderName() {
		return olderName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.older_name
	 * @param olderName  the value for pension_quality.older_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setOlderName(String olderName) {
		this.olderName = olderName == null ? null : olderName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.evaluation
	 * @return  the value of pension_quality.evaluation
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public Integer getEvaluation() {
		return evaluation;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.evaluation
	 * @param evaluation  the value for pension_quality.evaluation
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.item_id
	 * @return  the value of pension_quality.item_id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.item_id
	 * @param itemId  the value for pension_quality.item_id
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.cleared
	 * @return  the value of pension_quality.cleared
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.cleared
	 * @param cleared  the value for pension_quality.cleared
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.table_name
	 * @return  the value of pension_quality.table_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.table_name
	 * @param tableName  the value for pension_quality.table_name
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName == null ? null : tableName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_quality.note
	 * @return  the value of pension_quality.note
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public String getNote() {
		return note;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_quality.note
	 * @param note  the value for pension_quality.note
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
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
		PensionQuality other = (PensionQuality) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getQualityType() == null ? other.getQualityType() == null
						: this.getQualityType().equals(other.getQualityType()))
				&& (this.getQualityName() == null ? other.getQualityName() == null
						: this.getQualityName().equals(other.getQualityName()))
				&& (this.getQualityerId() == null ? other.getQualityerId() == null
						: this.getQualityerId().equals(other.getQualityerId()))
				&& (this.getQualityerName() == null ? other.getQualityerName() == null
						: this.getQualityerName().equals(
								other.getQualityerName()))
				&& (this.getQualityResult() == null ? other.getQualityResult() == null
						: this.getQualityResult().equals(
								other.getQualityResult()))
				&& (this.getQualityTime() == null ? other.getQualityTime() == null
						: this.getQualityTime().equals(other.getQualityTime()))
				&& (this.getOlderId() == null ? other.getOlderId() == null
						: this.getOlderId().equals(other.getOlderId()))
				&& (this.getOlderName() == null ? other.getOlderName() == null
						: this.getOlderName().equals(other.getOlderName()))
				&& (this.getEvaluation() == null ? other.getEvaluation() == null
						: this.getEvaluation().equals(other.getEvaluation()))
				&& (this.getItemId() == null ? other.getItemId() == null : this
						.getItemId().equals(other.getItemId()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getTableName() == null ? other.getTableName() == null
						: this.getTableName().equals(other.getTableName()))
				&& (this.getNote() == null ? other.getNote() == null : this
						.getNote().equals(other.getNote()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime
				* result
				+ ((getQualityType() == null) ? 0 : getQualityType().hashCode());
		result = prime
				* result
				+ ((getQualityName() == null) ? 0 : getQualityName().hashCode());
		result = prime
				* result
				+ ((getQualityerId() == null) ? 0 : getQualityerId().hashCode());
		result = prime
				* result
				+ ((getQualityerName() == null) ? 0 : getQualityerName()
						.hashCode());
		result = prime
				* result
				+ ((getQualityResult() == null) ? 0 : getQualityResult()
						.hashCode());
		result = prime
				* result
				+ ((getQualityTime() == null) ? 0 : getQualityTime().hashCode());
		result = prime * result
				+ ((getOlderId() == null) ? 0 : getOlderId().hashCode());
		result = prime * result
				+ ((getOlderName() == null) ? 0 : getOlderName().hashCode());
		result = prime * result
				+ ((getEvaluation() == null) ? 0 : getEvaluation().hashCode());
		result = prime * result
				+ ((getItemId() == null) ? 0 : getItemId().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime * result
				+ ((getTableName() == null) ? 0 : getTableName().hashCode());
		result = prime * result
				+ ((getNote() == null) ? 0 : getNote().hashCode());
		return result;
	}
}