package domain.system;

import java.io.Serializable;
import java.util.Date;

public class PensionMessages implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.messageType_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private Long messagetypeId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.messageName
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private String messagename;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.dept_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private Long deptId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.user_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private Long userId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.isProcessor
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private Integer isprocessor;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.processor_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private Long processorId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.notes
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private String notes;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.cleared
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private Integer cleared;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.message_url
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private String messageUrl;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.message_date
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private Date messageDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.table_name
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private String tableName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.table_key_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private Long tableKeyId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column pension_messages.processor_date
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private Date processorDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.id
	 * @return  the value of pension_messages.id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.id
	 * @param id  the value for pension_messages.id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.messageType_id
	 * @return  the value of pension_messages.messageType_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public Long getMessagetypeId() {
		return messagetypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.messageType_id
	 * @param messagetypeId  the value for pension_messages.messageType_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setMessagetypeId(Long messagetypeId) {
		this.messagetypeId = messagetypeId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.messageName
	 * @return  the value of pension_messages.messageName
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public String getMessagename() {
		return messagename;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.messageName
	 * @param messagename  the value for pension_messages.messageName
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setMessagename(String messagename) {
		this.messagename = messagename == null ? null : messagename.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.dept_id
	 * @return  the value of pension_messages.dept_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.dept_id
	 * @param deptId  the value for pension_messages.dept_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.user_id
	 * @return  the value of pension_messages.user_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.user_id
	 * @param userId  the value for pension_messages.user_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.isProcessor
	 * @return  the value of pension_messages.isProcessor
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public Integer getIsprocessor() {
		return isprocessor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.isProcessor
	 * @param isprocessor  the value for pension_messages.isProcessor
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setIsprocessor(Integer isprocessor) {
		this.isprocessor = isprocessor;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.processor_id
	 * @return  the value of pension_messages.processor_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public Long getProcessorId() {
		return processorId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.processor_id
	 * @param processorId  the value for pension_messages.processor_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setProcessorId(Long processorId) {
		this.processorId = processorId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.notes
	 * @return  the value of pension_messages.notes
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.notes
	 * @param notes  the value for pension_messages.notes
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setNotes(String notes) {
		this.notes = notes == null ? null : notes.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.cleared
	 * @return  the value of pension_messages.cleared
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public Integer getCleared() {
		return cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.cleared
	 * @param cleared  the value for pension_messages.cleared
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setCleared(Integer cleared) {
		this.cleared = cleared;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.message_url
	 * @return  the value of pension_messages.message_url
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public String getMessageUrl() {
		return messageUrl;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.message_url
	 * @param messageUrl  the value for pension_messages.message_url
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl == null ? null : messageUrl.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.message_date
	 * @return  the value of pension_messages.message_date
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public Date getMessageDate() {
		return messageDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.message_date
	 * @param messageDate  the value for pension_messages.message_date
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.table_name
	 * @return  the value of pension_messages.table_name
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.table_name
	 * @param tableName  the value for pension_messages.table_name
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName == null ? null : tableName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.table_key_id
	 * @return  the value of pension_messages.table_key_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public Long getTableKeyId() {
		return tableKeyId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.table_key_id
	 * @param tableKeyId  the value for pension_messages.table_key_id
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setTableKeyId(Long tableKeyId) {
		this.tableKeyId = tableKeyId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column pension_messages.processor_date
	 * @return  the value of pension_messages.processor_date
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public Date getProcessorDate() {
		return processorDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column pension_messages.processor_date
	 * @param processorDate  the value for pension_messages.processor_date
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	public void setProcessorDate(Date processorDate) {
		this.processorDate = processorDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
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
		PensionMessages other = (PensionMessages) that;
		return (this.getId() == null ? other.getId() == null : this.getId()
				.equals(other.getId()))
				&& (this.getMessagetypeId() == null ? other.getMessagetypeId() == null
						: this.getMessagetypeId().equals(
								other.getMessagetypeId()))
				&& (this.getMessagename() == null ? other.getMessagename() == null
						: this.getMessagename().equals(other.getMessagename()))
				&& (this.getDeptId() == null ? other.getDeptId() == null : this
						.getDeptId().equals(other.getDeptId()))
				&& (this.getUserId() == null ? other.getUserId() == null : this
						.getUserId().equals(other.getUserId()))
				&& (this.getIsprocessor() == null ? other.getIsprocessor() == null
						: this.getIsprocessor().equals(other.getIsprocessor()))
				&& (this.getProcessorId() == null ? other.getProcessorId() == null
						: this.getProcessorId().equals(other.getProcessorId()))
				&& (this.getNotes() == null ? other.getNotes() == null : this
						.getNotes().equals(other.getNotes()))
				&& (this.getCleared() == null ? other.getCleared() == null
						: this.getCleared().equals(other.getCleared()))
				&& (this.getMessageUrl() == null ? other.getMessageUrl() == null
						: this.getMessageUrl().equals(other.getMessageUrl()))
				&& (this.getMessageDate() == null ? other.getMessageDate() == null
						: this.getMessageDate().equals(other.getMessageDate()))
				&& (this.getTableName() == null ? other.getTableName() == null
						: this.getTableName().equals(other.getTableName()))
				&& (this.getTableKeyId() == null ? other.getTableKeyId() == null
						: this.getTableKeyId().equals(other.getTableKeyId()))
				&& (this.getProcessorDate() == null ? other.getProcessorDate() == null
						: this.getProcessorDate().equals(
								other.getProcessorDate()));
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime
				* result
				+ ((getMessagetypeId() == null) ? 0 : getMessagetypeId()
						.hashCode());
		result = prime
				* result
				+ ((getMessagename() == null) ? 0 : getMessagename().hashCode());
		result = prime * result
				+ ((getDeptId() == null) ? 0 : getDeptId().hashCode());
		result = prime * result
				+ ((getUserId() == null) ? 0 : getUserId().hashCode());
		result = prime
				* result
				+ ((getIsprocessor() == null) ? 0 : getIsprocessor().hashCode());
		result = prime
				* result
				+ ((getProcessorId() == null) ? 0 : getProcessorId().hashCode());
		result = prime * result
				+ ((getNotes() == null) ? 0 : getNotes().hashCode());
		result = prime * result
				+ ((getCleared() == null) ? 0 : getCleared().hashCode());
		result = prime * result
				+ ((getMessageUrl() == null) ? 0 : getMessageUrl().hashCode());
		result = prime
				* result
				+ ((getMessageDate() == null) ? 0 : getMessageDate().hashCode());
		result = prime * result
				+ ((getTableName() == null) ? 0 : getTableName().hashCode());
		result = prime * result
				+ ((getTableKeyId() == null) ? 0 : getTableKeyId().hashCode());
		result = prime
				* result
				+ ((getProcessorDate() == null) ? 0 : getProcessorDate()
						.hashCode());
		return result;
	}
}