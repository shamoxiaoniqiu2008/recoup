package domain.hrManage;

import java.io.Serializable;
import java.util.Date;

public class PensionStaffLeave implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.leave_type
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private Integer leaveType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.employee_id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private Long employeeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.employee_name
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private String employeeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.depart_id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private Long departId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.depart_name
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private String departName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.leave_date
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private Date leaveDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.start_time
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.end_time
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.older_status
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private Integer olderStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.recorder_id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private Long recorderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.recorder_name
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private String recorderName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.record_time
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private Date recordTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.cleared
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private Integer cleared;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pension_staff_leave.note
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private String note;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pension_staff_leave
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.id
     *
     * @return the value of pension_staff_leave.id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.id
     *
     * @param id the value for pension_staff_leave.id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.leave_type
     *
     * @return the value of pension_staff_leave.leave_type
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public Integer getLeaveType() {
        return leaveType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.leave_type
     *
     * @param leaveType the value for pension_staff_leave.leave_type
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setLeaveType(Integer leaveType) {
        this.leaveType = leaveType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.employee_id
     *
     * @return the value of pension_staff_leave.employee_id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public Long getEmployeeId() {
        return employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.employee_id
     *
     * @param employeeId the value for pension_staff_leave.employee_id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.employee_name
     *
     * @return the value of pension_staff_leave.employee_name
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.employee_name
     *
     * @param employeeName the value for pension_staff_leave.employee_name
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.depart_id
     *
     * @return the value of pension_staff_leave.depart_id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public Long getDepartId() {
        return departId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.depart_id
     *
     * @param departId the value for pension_staff_leave.depart_id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setDepartId(Long departId) {
        this.departId = departId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.depart_name
     *
     * @return the value of pension_staff_leave.depart_name
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public String getDepartName() {
        return departName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.depart_name
     *
     * @param departName the value for pension_staff_leave.depart_name
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setDepartName(String departName) {
        this.departName = departName == null ? null : departName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.leave_date
     *
     * @return the value of pension_staff_leave.leave_date
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public Date getLeaveDate() {
        return leaveDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.leave_date
     *
     * @param leaveDate the value for pension_staff_leave.leave_date
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.start_time
     *
     * @return the value of pension_staff_leave.start_time
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.start_time
     *
     * @param startTime the value for pension_staff_leave.start_time
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.end_time
     *
     * @return the value of pension_staff_leave.end_time
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.end_time
     *
     * @param endTime the value for pension_staff_leave.end_time
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.older_status
     *
     * @return the value of pension_staff_leave.older_status
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public Integer getOlderStatus() {
        return olderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.older_status
     *
     * @param olderStatus the value for pension_staff_leave.older_status
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setOlderStatus(Integer olderStatus) {
        this.olderStatus = olderStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.recorder_id
     *
     * @return the value of pension_staff_leave.recorder_id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public Long getRecorderId() {
        return recorderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.recorder_id
     *
     * @param recorderId the value for pension_staff_leave.recorder_id
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setRecorderId(Long recorderId) {
        this.recorderId = recorderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.recorder_name
     *
     * @return the value of pension_staff_leave.recorder_name
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public String getRecorderName() {
        return recorderName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.recorder_name
     *
     * @param recorderName the value for pension_staff_leave.recorder_name
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setRecorderName(String recorderName) {
        this.recorderName = recorderName == null ? null : recorderName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.record_time
     *
     * @return the value of pension_staff_leave.record_time
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public Date getRecordTime() {
        return recordTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.record_time
     *
     * @param recordTime the value for pension_staff_leave.record_time
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.cleared
     *
     * @return the value of pension_staff_leave.cleared
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public Integer getCleared() {
        return cleared;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.cleared
     *
     * @param cleared the value for pension_staff_leave.cleared
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setCleared(Integer cleared) {
        this.cleared = cleared;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pension_staff_leave.note
     *
     * @return the value of pension_staff_leave.note
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pension_staff_leave.note
     *
     * @param note the value for pension_staff_leave.note
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_staff_leave
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
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
        PensionStaffLeave other = (PensionStaffLeave) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLeaveType() == null ? other.getLeaveType() == null : this.getLeaveType().equals(other.getLeaveType()))
            && (this.getEmployeeId() == null ? other.getEmployeeId() == null : this.getEmployeeId().equals(other.getEmployeeId()))
            && (this.getEmployeeName() == null ? other.getEmployeeName() == null : this.getEmployeeName().equals(other.getEmployeeName()))
            && (this.getDepartId() == null ? other.getDepartId() == null : this.getDepartId().equals(other.getDepartId()))
            && (this.getDepartName() == null ? other.getDepartName() == null : this.getDepartName().equals(other.getDepartName()))
            && (this.getLeaveDate() == null ? other.getLeaveDate() == null : this.getLeaveDate().equals(other.getLeaveDate()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getOlderStatus() == null ? other.getOlderStatus() == null : this.getOlderStatus().equals(other.getOlderStatus()))
            && (this.getRecorderId() == null ? other.getRecorderId() == null : this.getRecorderId().equals(other.getRecorderId()))
            && (this.getRecorderName() == null ? other.getRecorderName() == null : this.getRecorderName().equals(other.getRecorderName()))
            && (this.getRecordTime() == null ? other.getRecordTime() == null : this.getRecordTime().equals(other.getRecordTime()))
            && (this.getCleared() == null ? other.getCleared() == null : this.getCleared().equals(other.getCleared()))
            && (this.getNote() == null ? other.getNote() == null : this.getNote().equals(other.getNote()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_staff_leave
     *
     * @mbggenerated Tue Oct 29 15:53:41 GMT+08:00 2013
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLeaveType() == null) ? 0 : getLeaveType().hashCode());
        result = prime * result + ((getEmployeeId() == null) ? 0 : getEmployeeId().hashCode());
        result = prime * result + ((getEmployeeName() == null) ? 0 : getEmployeeName().hashCode());
        result = prime * result + ((getDepartId() == null) ? 0 : getDepartId().hashCode());
        result = prime * result + ((getDepartName() == null) ? 0 : getDepartName().hashCode());
        result = prime * result + ((getLeaveDate() == null) ? 0 : getLeaveDate().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getOlderStatus() == null) ? 0 : getOlderStatus().hashCode());
        result = prime * result + ((getRecorderId() == null) ? 0 : getRecorderId().hashCode());
        result = prime * result + ((getRecorderName() == null) ? 0 : getRecorderName().hashCode());
        result = prime * result + ((getRecordTime() == null) ? 0 : getRecordTime().hashCode());
        result = prime * result + ((getCleared() == null) ? 0 : getCleared().hashCode());
        result = prime * result + ((getNote() == null) ? 0 : getNote().hashCode());
        return result;
    }
}