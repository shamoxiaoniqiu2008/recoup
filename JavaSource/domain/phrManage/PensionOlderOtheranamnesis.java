package domain.phrManage;

import java.io.Serializable;
import java.util.Date;

public class PensionOlderOtheranamnesis implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_otheranamnesis.id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_otheranamnesis.older_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Long olderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_otheranamnesis.older_name
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private String olderName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_otheranamnesis.otheranamnesis_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Long otheranamnesisId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_otheranamnesis.otheranamnesis_num
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Integer otheranamnesisNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_otheranamnesis.other_datetime
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Date otherDatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_otheranamnesis.modify_datetime
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Date modifyDatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_otheranamnesis.notes
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private String notes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_otheranamnesis.cleared
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Integer cleared;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pension_older_otheranamnesis
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_otheranamnesis.id
     *
     * @return the value of pension_older_otheranamnesis.id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_otheranamnesis.id
     *
     * @param id the value for pension_older_otheranamnesis.id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_otheranamnesis.older_id
     *
     * @return the value of pension_older_otheranamnesis.older_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Long getOlderId() {
        return olderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_otheranamnesis.older_id
     *
     * @param olderId the value for pension_older_otheranamnesis.older_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setOlderId(Long olderId) {
        this.olderId = olderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_otheranamnesis.older_name
     *
     * @return the value of pension_older_otheranamnesis.older_name
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public String getOlderName() {
        return olderName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_otheranamnesis.older_name
     *
     * @param olderName the value for pension_older_otheranamnesis.older_name
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setOlderName(String olderName) {
        this.olderName = olderName == null ? null : olderName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_otheranamnesis.otheranamnesis_id
     *
     * @return the value of pension_older_otheranamnesis.otheranamnesis_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Long getOtheranamnesisId() {
        return otheranamnesisId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_otheranamnesis.otheranamnesis_id
     *
     * @param otheranamnesisId the value for pension_older_otheranamnesis.otheranamnesis_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setOtheranamnesisId(Long otheranamnesisId) {
        this.otheranamnesisId = otheranamnesisId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_otheranamnesis.otheranamnesis_num
     *
     * @return the value of pension_older_otheranamnesis.otheranamnesis_num
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Integer getOtheranamnesisNum() {
        return otheranamnesisNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_otheranamnesis.otheranamnesis_num
     *
     * @param otheranamnesisNum the value for pension_older_otheranamnesis.otheranamnesis_num
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setOtheranamnesisNum(Integer otheranamnesisNum) {
        this.otheranamnesisNum = otheranamnesisNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_otheranamnesis.other_datetime
     *
     * @return the value of pension_older_otheranamnesis.other_datetime
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Date getOtherDatetime() {
        return otherDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_otheranamnesis.other_datetime
     *
     * @param otherDatetime the value for pension_older_otheranamnesis.other_datetime
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setOtherDatetime(Date otherDatetime) {
        this.otherDatetime = otherDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_otheranamnesis.modify_datetime
     *
     * @return the value of pension_older_otheranamnesis.modify_datetime
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_otheranamnesis.modify_datetime
     *
     * @param modifyDatetime the value for pension_older_otheranamnesis.modify_datetime
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_otheranamnesis.notes
     *
     * @return the value of pension_older_otheranamnesis.notes
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public String getNotes() {
        return notes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_otheranamnesis.notes
     *
     * @param notes the value for pension_older_otheranamnesis.notes
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_otheranamnesis.cleared
     *
     * @return the value of pension_older_otheranamnesis.cleared
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Integer getCleared() {
        return cleared;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_otheranamnesis.cleared
     *
     * @param cleared the value for pension_older_otheranamnesis.cleared
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setCleared(Integer cleared) {
        this.cleared = cleared;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_older_otheranamnesis
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
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
        PensionOlderOtheranamnesis other = (PensionOlderOtheranamnesis) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOlderId() == null ? other.getOlderId() == null : this.getOlderId().equals(other.getOlderId()))
            && (this.getOlderName() == null ? other.getOlderName() == null : this.getOlderName().equals(other.getOlderName()))
            && (this.getOtheranamnesisId() == null ? other.getOtheranamnesisId() == null : this.getOtheranamnesisId().equals(other.getOtheranamnesisId()))
            && (this.getOtheranamnesisNum() == null ? other.getOtheranamnesisNum() == null : this.getOtheranamnesisNum().equals(other.getOtheranamnesisNum()))
            && (this.getOtherDatetime() == null ? other.getOtherDatetime() == null : this.getOtherDatetime().equals(other.getOtherDatetime()))
            && (this.getModifyDatetime() == null ? other.getModifyDatetime() == null : this.getModifyDatetime().equals(other.getModifyDatetime()))
            && (this.getNotes() == null ? other.getNotes() == null : this.getNotes().equals(other.getNotes()))
            && (this.getCleared() == null ? other.getCleared() == null : this.getCleared().equals(other.getCleared()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_older_otheranamnesis
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOlderId() == null) ? 0 : getOlderId().hashCode());
        result = prime * result + ((getOlderName() == null) ? 0 : getOlderName().hashCode());
        result = prime * result + ((getOtheranamnesisId() == null) ? 0 : getOtheranamnesisId().hashCode());
        result = prime * result + ((getOtheranamnesisNum() == null) ? 0 : getOtheranamnesisNum().hashCode());
        result = prime * result + ((getOtherDatetime() == null) ? 0 : getOtherDatetime().hashCode());
        result = prime * result + ((getModifyDatetime() == null) ? 0 : getModifyDatetime().hashCode());
        result = prime * result + ((getNotes() == null) ? 0 : getNotes().hashCode());
        result = prime * result + ((getCleared() == null) ? 0 : getCleared().hashCode());
        return result;
    }
}