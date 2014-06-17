/**  
 * @Title: ActivityCheckController.java 
 * @Package controller.activityManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午2:55:52 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package controller.activityManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;

import service.activityManage.ActivityCheckService;
import domain.activityManage.PensionAttendolder;

/**
 * @ClassName: ActivityCheckController
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午2:55:52
 * @version V1.0
 */
public class ActivityCheckController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */
	private static final long serialVersionUID = -2875776235121517114L;

	// 引入service
	private transient ActivityCheckService activityCheckService;
	// 康娱报名列表
	private List<PensionAttendolder> pensionAttendolderList = new ArrayList<PensionAttendolder>();

	// 定义老人姓名输入法变量
	private String nameToSql;
	private String fitcolName;
	private String displaycolName;

	// 定义项目名称输入法变量
	private String itemToSql;
	private String fitcolItem;
	private String displaycolItem;

	// 项目接收参数
	private Long itemId;
	private String itemName;
	// 老人接收参数
	private Long olderId;
	private String olderName;
	// 开始截止日期接收参数
	private Date startDate;
	private Date endDate;
	private boolean selectedAllFlag = false;
	private String noteString;
	private Long checkResultFlag;

	@PostConstruct
	public void init() {
		initNameToSql();
		initItemToSql();
		pensionAttendolderList = activityCheckService
				.searchAllAttendCheckInfo();
	}

	/**
	 * 
	 * @Title: initNameToSql
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 下午1:51:13
	 * @version V1.0
	 */
	public void initNameToSql() {
		nameToSql = " select po.id as olderId ,po.name as olderName"
				+ " ,po.inputcode as inputCode"
				+ " ,case po.sex when 1 then '男' else '女' end as sexName"
				+ " from pension_older po  where  po.statuses = 3"
				+ " and  po.cleared = 2";
		fitcolName = "olderId,inputCode";
		displaycolName = "编号:0,姓名:1,输入码:2,性别:3";
	}

	/**
	 * 
	 * @Title: initItemToSql
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 下午1:51:16
	 * @version V1.0
	 */
	public void initItemToSql() {
		itemToSql = " select" + " pa.id as itemId"
				+ " ,pa.itemname as itemName" + " ,pa.inputcode as inputCode"
				+ " from" + " pension_activity pa where pa.cleared = 2";
		fitcolItem = "itemId,inputCode";
		displaycolItem = "项目编号:0,项目名称:1,输入码:2";
	}

	/**
	 * 
	 * @Title: searchAttendCheckInfo
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 下午1:56:45
	 * @version V1.0
	 */
	public void searchAttendCheckInfo() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (itemName == null || "".equals(itemName.trim())) {
			itemId = null;
		}
		if (olderName == null || "".equals(olderName.trim())) {
			olderId = null;
		}
		pensionAttendolderList = activityCheckService.searchAttendCheckInfo(
				itemId, olderId, startDate, endDate);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"查询完毕！", ""));
	}

	/**
	 * 
	 * @Title: initParam
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 下午1:56:41
	 * @version V1.0
	 */
	public void initParam() {
		itemId = null;
		itemName = "";
		olderId = null;
		olderName = "";
		startDate = null;
		endDate = null;
	}

	/**
	 * 
	 * @Title: checkIsSelectAll
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 下午2:00:33
	 * @version V1.0
	 */
	public void checkIsSelectAll() {
		int number = 0;
		int listSize = pensionAttendolderList.size();
		for (PensionAttendolder pe : pensionAttendolderList) {
			if (pe.isSelectedFlag()) {
				number = number + 1;
			}
		}
		if (number == listSize) {
			selectedAllFlag = true;
		} else {
			selectedAllFlag = false;
		}
	}

	/**
	 * 
	 * @Title: saveCheckInfo
	 * @Description: TODO
	 * @param @param pensionAttendolder
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 下午2:13:27
	 * @version V1.0
	 */
	public void saveCheckInfo(PensionAttendolder pensionAttendolder) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (pensionAttendolder.getCheckresult() == 0) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请选择质检结果！", ""));
		} else {
			if (pensionAttendolder.getCheckresult() != null
					&& pensionAttendolder.getCheckresult() == 2) {
				if (pensionAttendolder.getNotes() == null
						|| "".equals(pensionAttendolder.getNotes().trim())) {
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_WARN, "请填写备注！", ""));
				} else {
					activityCheckService
							.updatePensionAttendolderCheck(pensionAttendolder);
					if (itemName == null || "".equals(itemName.trim())) {
						itemId = null;
					}
					if (olderName == null || "".equals(olderName.trim())) {
						olderId = null;
					}
					pensionAttendolderList = activityCheckService
							.searchAttendCheckInfo(itemId, olderId, startDate,
									endDate);
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_INFO, "质检完毕！", ""));
				}
			} else {
				activityCheckService
						.updatePensionAttendolderCheck(pensionAttendolder);
				if (itemName == null || "".equals(itemName.trim())) {
					itemId = null;
				}
				if (olderName == null || "".equals(olderName.trim())) {
					olderId = null;
				}
				pensionAttendolderList = activityCheckService
						.searchAttendCheckInfo(itemId, olderId, startDate,
								endDate);
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "质检完毕！", ""));
			}
		}
	}

	/**
	 * 
	 * @Title: setSelectedAllFlag
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 下午2:15:12
	 * @version V1.0
	 */
	public void setSelectedAllFlag(ValueChangeEvent event) {
		boolean flag = (Boolean) event.getNewValue();
		for (PensionAttendolder pa : pensionAttendolderList) {
			pa.setSelectedFlag(flag);
		}
	}

	public boolean checkIsSelected() {
		int indexNum = 0;
		for (PensionAttendolder pao : pensionAttendolderList) {
			if (pao.isSelectedFlag()) {
				indexNum = indexNum + 1;
			}
		}
		if (indexNum == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void checkDialogShow() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (!checkIsSelected()) {
			request.addCallbackParam("showFlag", false);
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请选择要确认的项目！", ""));
		} else {
			if (checkResultFlag == 0) {
				request.addCallbackParam("showFlag", false);
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, "请选择能质检结果！", ""));
			} else {
				request.addCallbackParam("showFlag", true);
			}
		}
	}

	public void saveCheckInfoList() {
		FacesContext context = FacesContext.getCurrentInstance();
		for (PensionAttendolder p : pensionAttendolderList) {
			if (p.isSelectedFlag()) {
				p.setCheckresult(checkResultFlag.intValue());
				p.setNotes(noteString);
				activityCheckService.updatePensionAttendolderCheck(p);
			}
		}
		if (itemName == null || "".equals(itemName.trim())) {
			itemId = null;
		}
		if (olderName == null || "".equals(olderName.trim())) {
			olderId = null;
		}
		pensionAttendolderList = activityCheckService.searchAttendCheckInfo(
				itemId, olderId, startDate, endDate);
		selectedAllFlag = false;
		checkResultFlag = (long) 0;
		noteString = "";
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"质检完毕！", ""));

	}

	/**
	 * @return the activityCheckService
	 */
	public ActivityCheckService getActivityCheckService() {
		return activityCheckService;
	}

	/**
	 * @param activityCheckService
	 *            the activityCheckService to set
	 */
	public void setActivityCheckService(
			ActivityCheckService activityCheckService) {
		this.activityCheckService = activityCheckService;
	}

	/**
	 * @return the pensionAttendolderList
	 */
	public List<PensionAttendolder> getPensionAttendolderList() {
		return pensionAttendolderList;
	}

	/**
	 * @param pensionAttendolderList
	 *            the pensionAttendolderList to set
	 */
	public void setPensionAttendolderList(
			List<PensionAttendolder> pensionAttendolderList) {
		this.pensionAttendolderList = pensionAttendolderList;
	}

	/**
	 * @return the nameToSql
	 */
	public String getNameToSql() {
		return nameToSql;
	}

	/**
	 * @param nameToSql
	 *            the nameToSql to set
	 */
	public void setNameToSql(String nameToSql) {
		this.nameToSql = nameToSql;
	}

	/**
	 * @return the fitcolName
	 */
	public String getFitcolName() {
		return fitcolName;
	}

	/**
	 * @param fitcolName
	 *            the fitcolName to set
	 */
	public void setFitcolName(String fitcolName) {
		this.fitcolName = fitcolName;
	}

	/**
	 * @return the displaycolName
	 */
	public String getDisplaycolName() {
		return displaycolName;
	}

	/**
	 * @param displaycolName
	 *            the displaycolName to set
	 */
	public void setDisplaycolName(String displaycolName) {
		this.displaycolName = displaycolName;
	}

	/**
	 * @return the itemToSql
	 */
	public String getItemToSql() {
		return itemToSql;
	}

	/**
	 * @param itemToSql
	 *            the itemToSql to set
	 */
	public void setItemToSql(String itemToSql) {
		this.itemToSql = itemToSql;
	}

	/**
	 * @return the fitcolItem
	 */
	public String getFitcolItem() {
		return fitcolItem;
	}

	/**
	 * @param fitcolItem
	 *            the fitcolItem to set
	 */
	public void setFitcolItem(String fitcolItem) {
		this.fitcolItem = fitcolItem;
	}

	/**
	 * @return the displaycolItem
	 */
	public String getDisplaycolItem() {
		return displaycolItem;
	}

	/**
	 * @param displaycolItem
	 *            the displaycolItem to set
	 */
	public void setDisplaycolItem(String displaycolItem) {
		this.displaycolItem = displaycolItem;
	}

	/**
	 * @return the itemId
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName
	 *            the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the olderName
	 */
	public String getOlderName() {
		return olderName;
	}

	/**
	 * @param olderName
	 *            the olderName to set
	 */
	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	/**
	 * @return the olderId
	 */
	public Long getOlderId() {
		return olderId;
	}

	/**
	 * @param olderId
	 *            the olderId to set
	 */
	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	/**
	 * @return the selectedAllFlag
	 */
	public boolean isSelectedAllFlag() {
		return selectedAllFlag;
	}

	/**
	 * @param selectedAllFlag
	 *            the selectedAllFlag to set
	 */
	public void setSelectedAllFlag(boolean selectedAllFlag) {
		this.selectedAllFlag = selectedAllFlag;
	}

	/**
	 * @return the noteString
	 */
	public String getNoteString() {
		return noteString;
	}

	/**
	 * @param noteString
	 *            the noteString to set
	 */
	public void setNoteString(String noteString) {
		this.noteString = noteString;
	}

	/**
	 * @return the checkResultFlag
	 */
	public Long getCheckResultFlag() {
		return checkResultFlag;
	}

	/**
	 * @param checkResultFlag
	 *            the checkResultFlag to set
	 */
	public void setCheckResultFlag(Long checkResultFlag) {
		this.checkResultFlag = checkResultFlag;
	}

}
