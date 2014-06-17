/**  
 * @Title: ActivityAnalyseController.java 
 * @Package controller.activityManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 下午1:24:05 
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

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.centling.his.util.SessionManager;

import domain.activityManage.PensionActivity;
import domain.activityManage.PensionAttendolder;
import domain.employeeManage.PensionEmployee;

import service.activityManage.ActivityAnalyseService;

/**
 * @ClassName: ActivityAnalyseController
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 下午1:24:05
 * @version V1.0
 */

public class ActivityAnalyseController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */

	private static final long serialVersionUID = -2021211382671555930L;

	private transient ActivityAnalyseService activityAnalyseService;

	private List<ExtendForActivityAnalyse> extendForActivityAnalyseList = new ArrayList<ExtendForActivityAnalyse>();

	private ExtendForActivityAnalyse selectedExtendForActivityAnalyse = new ExtendForActivityAnalyse();

	private List<PensionAttendolder> pensionAttendolderList = new ArrayList<PensionAttendolder>();

	// 定义项目名称输入法变量
	private String itemToSql;
	private String fitcolItem;
	private String displaycolItem;

	// 定义老人姓名和项目名称输入法接收变量
	private Long itemId;
	private String itemName;

	// 定义报名时间输入框接收参数
	private Date startDate;
	private Date endDate;

	// 定义能否参加和是否参加下拉框接收参数
	// private Long attendCanOrNotFlag;
	private Integer appraise;

	private PensionActivity activity;// 保存质检信息

	private PensionEmployee employee;

	/**
	 * 
	 * @Title: init
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 下午3:43:59
	 * @version V1.0
	 */
	@PostConstruct
	public void init() {
		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		initItemToSql();
		extendForActivityAnalyseList = activityAnalyseService
				.getExtendForActivityAnalyseList();
		selectedExtendForActivityAnalyse = null;
	}

	/**
	 * 
	 * @Title: initItemToSql
	 * @Description: 康娱项目输入法
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 下午2:33:59
	 * @version V1.0
	 */
	public void initItemToSql() {
		itemToSql = " select" + " pa.id as itemId"
				+ " ,pa.itemname as itemName" + " ,pa.inputcode as inputCode"
				+ " from" + " pension_activity pa where pa.cleared = 2";
		fitcolItem = "itemId,inputCode";
		displaycolItem = "编号:0,活动名称:1,输入码:2";
	}

	/**
	 * 
	 * @Title: setSelectedFlag
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 下午2:21:26
	 * @version V1.0
	 */
	public void setSelectedFlag(SelectEvent event) {
		pensionAttendolderList = activityAnalyseService
				.searchDetailList(selectedExtendForActivityAnalyse);
	}

	/**
	 * 
	 * @Title: setUnselectedFlag
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 下午2:21:29
	 * @version V1.0
	 */
	public void setUnselectedFlag(UnselectEvent event) {
		pensionAttendolderList = new ArrayList<PensionAttendolder>();
		selectedExtendForActivityAnalyse = null;
	}

	public void searchSumRecord() {
		if (itemName == null || "".equals(itemName.trim())) {
			itemId = null;
		}
		extendForActivityAnalyseList = activityAnalyseService
				.getExtendForActivityAnalyseListByCondition(itemId, startDate,
						endDate, appraise);
		pensionAttendolderList = new ArrayList<PensionAttendolder>();
		selectedExtendForActivityAnalyse = null;
	}

	/**
	 * 
	 * @Title: initParam
	 * @Description: 清空按钮调用
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-17 上午10:42:15
	 * @version V1.0
	 */
	public void initParam() {
		itemId = null;
		itemName = "";
		startDate = null;
		endDate = null;
		// attendCanOrNotFlag = (long) 3;
		appraise = null;
	}

	public void analyseActivity(Long appraiseId) {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		// 没有选中康娱项目
		if (selectedExtendForActivityAnalyse == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选中康娱项目！", ""));
		} else if (selectedExtendForActivityAnalyse.getSummary() != null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "该康娱项目已评价，不能重复操作！", ""));
		} else {// 选中康娱项目
			Date summarytime = new Date();
			activity = new PensionActivity();
			activity.setId(selectedExtendForActivityAnalyse.getActivityId());
			activity.setSummary(appraiseId.intValue());
			activity.setSummaryerId(employee.getId());
			activity.setSummaryername(employee.getName());
			activity.setSummarytime(summarytime);
			// 满意
			if (appraiseId.intValue() == 1) {
				selectedExtendForActivityAnalyse.setSummary(appraiseId
						.intValue());
				this.saveActivityAnalyse();
			} else if (appraiseId.intValue() == 2) {// 不满意
				request.addCallbackParam("validate", true);
			}
		}
	}

	/**
	 * 保存质检信息
	 */
	public void saveActivityAnalyse() {
		if (activity.getSummary().equals(2)) {
			selectedExtendForActivityAnalyse.setSummary(2);
		}
		activityAnalyseService.saveActivityAnalyse(activity);
		extendForActivityAnalyseList = activityAnalyseService
				.getExtendForActivityAnalyseListByCondition(itemId, startDate,
						endDate, appraise);
	}

	/**
	 * @return the activityAnalyseService
	 */
	public ActivityAnalyseService getActivityAnalyseService() {
		return activityAnalyseService;
	}

	/**
	 * @param activityAnalyseService
	 *            the activityAnalyseService to set
	 */
	public void setActivityAnalyseService(
			ActivityAnalyseService activityAnalyseService) {
		this.activityAnalyseService = activityAnalyseService;
	}

	/**
	 * @return the extendForActivityAnalyseList
	 */
	public List<ExtendForActivityAnalyse> getExtendForActivityAnalyseList() {
		return extendForActivityAnalyseList;
	}

	/**
	 * @param extendForActivityAnalyseList
	 *            the extendForActivityAnalyseList to set
	 */
	public void setExtendForActivityAnalyseList(
			List<ExtendForActivityAnalyse> extendForActivityAnalyseList) {
		this.extendForActivityAnalyseList = extendForActivityAnalyseList;
	}

	/**
	 * @return the selectedExtendForActivityAnalyse
	 */
	public ExtendForActivityAnalyse getSelectedExtendForActivityAnalyse() {
		return selectedExtendForActivityAnalyse;
	}

	/**
	 * @param selectedExtendForActivityAnalyse
	 *            the selectedExtendForActivityAnalyse to set
	 */
	public void setSelectedExtendForActivityAnalyse(
			ExtendForActivityAnalyse selectedExtendForActivityAnalyse) {
		this.selectedExtendForActivityAnalyse = selectedExtendForActivityAnalyse;
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

	public Integer getAppraise() {
		return appraise;
	}

	public void setAppraise(Integer appraise) {
		this.appraise = appraise;
	}

	public PensionActivity getActivity() {
		return activity;
	}

	public void setActivity(PensionActivity activity) {
		this.activity = activity;
	}

	/**
	 * @return the attendCanOrNotFlag
	 */
	// public Long getAttendCanOrNotFlag() {
	// return attendCanOrNotFlag;
	// }

	/**
	 * @param attendCanOrNotFlag
	 *            the attendCanOrNotFlag to set
	 */
	// public void setAttendCanOrNotFlag(Long attendCanOrNotFlag) {
	// this.attendCanOrNotFlag = attendCanOrNotFlag;
	// }

}
