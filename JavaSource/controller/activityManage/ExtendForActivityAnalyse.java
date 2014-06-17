/**  
* @Title: ExtendForActivityAnalyse.java 
* @Package controller.activityManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-9-15 下午1:49:27 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package controller.activityManage;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName: ExtendForActivityAnalyse 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-15 下午1:49:27
 * @version V1.0 
 */
public class ExtendForActivityAnalyse implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	* @version V1.0
	*/ 
	
	private static final long serialVersionUID = 4236590150855936577L;
	private Long activityId;
	private String activityName;
	private String itemplace;
	private Date startDate;
	private Date attendDate;
	private Long activityerQty;//报名人数
	private Long attendNum;//参加人数
	private Long likeNum;//满意人数
	private Long unLikeNum;//不满意人数
	private Long unKnownNum;//未评价人数
	private Integer summary;//质检结果
	private String summaryName;//质检人
	private Date summaryDate;//质检时间
	private String summaryNote;//质检备注
	/**
	 * @return the activityId
	 */
	public Long getActivityId() {
		return activityId;
	}
	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}
	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	/**
	 * @return the activityerQty
	 */
	public Long getActivityerQty() {
		return activityerQty;
	}
	/**
	 * @param activityerQty the activityerQty to set
	 */
	public void setActivityerQty(Long activityerQty) {
		this.activityerQty = activityerQty;
	}
	/**
	 * @return the attendDate
	 */
	public Date getAttendDate() {
		return attendDate;
	}
	/**
	 * @param attendDate the attendDate to set
	 */
	public void setAttendDate(Date attendDate) {
		this.attendDate = attendDate;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the likeNum
	 */
	public Long getLikeNum() {
		return likeNum;
	}
	/**
	 * @param likeNum the likeNum to set
	 */
	public void setLikeNum(Long likeNum) {
		this.likeNum = likeNum;
	}
	/**
	 * @return the unLikeNum
	 */
	public Long getUnLikeNum() {
		return unLikeNum;
	}
	/**
	 * @param unLikeNum the unLikeNum to set
	 */
	public void setUnLikeNum(Long unLikeNum) {
		this.unLikeNum = unLikeNum;
	}
	/**
	 * @return the unKnownNum
	 */
	public Long getUnKnownNum() {
		return unKnownNum;
	}
	/**
	 * @param unKnownNum the unKnownNum to set
	 */
	public void setUnKnownNum(Long unKnownNum) {
		this.unKnownNum = unKnownNum;
	}
	public void setItemplace(String itemplace) {
		this.itemplace = itemplace;
	}
	public String getItemplace() {
		return itemplace;
	}
	public Long getAttendNum() {
		return attendNum;
	}
	public void setAttendNum(Long attendNum) {
		this.attendNum = attendNum;
	}
	public String getSummaryNote() {
		return summaryNote;
	}
	public void setSummaryNote(String summaryNote) {
		this.summaryNote = summaryNote;
	}
	public String getSummaryName() {
		return summaryName;
	}
	public void setSummaryName(String summaryName) {
		this.summaryName = summaryName;
	}
	public Date getSummaryDate() {
		return summaryDate;
	}
	public void setSummaryDate(Date summaryDate) {
		this.summaryDate = summaryDate;
	}
	public Integer getSummary() {
		return summary;
	}
	public void setSummary(Integer summary) {
		this.summary = summary;
	}
	
	
}
