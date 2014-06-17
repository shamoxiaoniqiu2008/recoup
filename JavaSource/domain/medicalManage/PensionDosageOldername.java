package domain.medicalManage;

import java.io.Serializable;

public class PensionDosageOldername implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_dosage_oldername.id
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_dosage_oldername.name_id
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    private Long nameId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_dosage_oldername.dosage_name
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    private String dosageName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_dosage_oldername.older_id
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    private Long olderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_dosage_oldername.older_name
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    private String olderName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_dosage_oldername.cleared
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    private Integer cleared;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_dosage_oldername.note
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    private String note;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pension_dosage_oldername
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_dosage_oldername.id
     *
     * @return the value of pension_dosage_oldername.id
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_dosage_oldername.id
     *
     * @param id the value for pension_dosage_oldername.id
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_dosage_oldername.name_id
     *
     * @return the value of pension_dosage_oldername.name_id
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public Long getNameId() {
        return nameId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_dosage_oldername.name_id
     *
     * @param nameId the value for pension_dosage_oldername.name_id
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public void setNameId(Long nameId) {
        this.nameId = nameId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_dosage_oldername.dosage_name
     *
     * @return the value of pension_dosage_oldername.dosage_name
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public String getDosageName() {
        return dosageName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_dosage_oldername.dosage_name
     *
     * @param dosageName the value for pension_dosage_oldername.dosage_name
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public void setDosageName(String dosageName) {
        this.dosageName = dosageName == null ? null : dosageName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_dosage_oldername.older_id
     *
     * @return the value of pension_dosage_oldername.older_id
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public Long getOlderId() {
        return olderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_dosage_oldername.older_id
     *
     * @param olderId the value for pension_dosage_oldername.older_id
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public void setOlderId(Long olderId) {
        this.olderId = olderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_dosage_oldername.older_name
     *
     * @return the value of pension_dosage_oldername.older_name
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public String getOlderName() {
        return olderName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_dosage_oldername.older_name
     *
     * @param olderName the value for pension_dosage_oldername.older_name
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public void setOlderName(String olderName) {
        this.olderName = olderName == null ? null : olderName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_dosage_oldername.cleared
     *
     * @return the value of pension_dosage_oldername.cleared
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public Integer getCleared() {
        return cleared;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_dosage_oldername.cleared
     *
     * @param cleared the value for pension_dosage_oldername.cleared
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public void setCleared(Integer cleared) {
        this.cleared = cleared;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_dosage_oldername.note
     *
     * @return the value of pension_dosage_oldername.note
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_dosage_oldername.note
     *
     * @param note the value for pension_dosage_oldername.note
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dosage_oldername
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
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
        PensionDosageOldername other = (PensionDosageOldername) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getNameId() == null ? other.getNameId() == null : this.getNameId().equals(other.getNameId()))
            && (this.getDosageName() == null ? other.getDosageName() == null : this.getDosageName().equals(other.getDosageName()))
            && (this.getOlderId() == null ? other.getOlderId() == null : this.getOlderId().equals(other.getOlderId()))
            && (this.getOlderName() == null ? other.getOlderName() == null : this.getOlderName().equals(other.getOlderName()))
            && (this.getCleared() == null ? other.getCleared() == null : this.getCleared().equals(other.getCleared()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dosage_oldername
     *
     * @mbggenerated Tue Oct 29 14:28:46 GMT+08:00 2013
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getNameId() == null) ? 0 : getNameId().hashCode());
        result = prime * result + ((getDosageName() == null) ? 0 : getDosageName().hashCode());
        result = prime * result + ((getOlderId() == null) ? 0 : getOlderId().hashCode());
        result = prime * result + ((getOlderName() == null) ? 0 : getOlderName().hashCode());
        result = prime * result + ((getCleared() == null) ? 0 : getCleared().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        return result;
    }
}