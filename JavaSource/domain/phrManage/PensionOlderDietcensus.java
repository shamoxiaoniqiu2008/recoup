package domain.phrManage;

import java.io.Serializable;
import java.util.Date;

public class PensionOlderDietcensus implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_dietcensus.id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_dietcensus.older_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Long olderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_dietcensus.older_name
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private String olderName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_dietcensus.habit_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Long habitId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_dietcensus.modify_datetime
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Date modifyDatetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_older_dietcensus.cleared
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private Integer cleared;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pension_older_dietcensus
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_dietcensus.id
     *
     * @return the value of pension_older_dietcensus.id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_dietcensus.id
     *
     * @param id the value for pension_older_dietcensus.id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_dietcensus.older_id
     *
     * @return the value of pension_older_dietcensus.older_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Long getOlderId() {
        return olderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_dietcensus.older_id
     *
     * @param olderId the value for pension_older_dietcensus.older_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setOlderId(Long olderId) {
        this.olderId = olderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_dietcensus.older_name
     *
     * @return the value of pension_older_dietcensus.older_name
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public String getOlderName() {
        return olderName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_dietcensus.older_name
     *
     * @param olderName the value for pension_older_dietcensus.older_name
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setOlderName(String olderName) {
        this.olderName = olderName == null ? null : olderName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_dietcensus.habit_id
     *
     * @return the value of pension_older_dietcensus.habit_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Long getHabitId() {
        return habitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_dietcensus.habit_id
     *
     * @param habitId the value for pension_older_dietcensus.habit_id
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_dietcensus.modify_datetime
     *
     * @return the value of pension_older_dietcensus.modify_datetime
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_dietcensus.modify_datetime
     *
     * @param modifyDatetime the value for pension_older_dietcensus.modify_datetime
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_older_dietcensus.cleared
     *
     * @return the value of pension_older_dietcensus.cleared
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public Integer getCleared() {
        return cleared;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_older_dietcensus.cleared
     *
     * @param cleared the value for pension_older_dietcensus.cleared
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    public void setCleared(Integer cleared) {
        this.cleared = cleared;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_older_dietcensus
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
        PensionOlderDietcensus other = (PensionOlderDietcensus) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOlderId() == null ? other.getOlderId() == null : this.getOlderId().equals(other.getOlderId()))
            && (this.getOlderName() == null ? other.getOlderName() == null : this.getOlderName().equals(other.getOlderName()))
            && (this.getHabitId() == null ? other.getHabitId() == null : this.getHabitId().equals(other.getHabitId()))
            && (this.getModifyDatetime() == null ? other.getModifyDatetime() == null : this.getModifyDatetime().equals(other.getModifyDatetime()))
            && (this.getCleared() == null ? other.getCleared() == null : this.getCleared().equals(other.getCleared()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_older_dietcensus
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
        result = prime * result + ((getHabitId() == null) ? 0 : getHabitId().hashCode());
        result = prime * result + ((getModifyDatetime() == null) ? 0 : getModifyDatetime().hashCode());
        result = prime * result + ((getCleared() == null) ? 0 : getCleared().hashCode());
        return result;
    }
}