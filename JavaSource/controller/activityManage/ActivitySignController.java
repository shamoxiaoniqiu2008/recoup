/**  
 * @Title: ActivitySignController.java 
 * @Package controller.activityManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 下午1:17:32 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package controller.activityManage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import service.activityManage.ActivitySignService;
import util.PmsException;
import domain.activityManage.PensionActivity;
import domain.activityManage.PensionAttendolder;

/**
 * @ClassName: ActivitySignController
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-13 下午1:17:32
 * @version V1.0
 */
public class ActivitySignController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */

	private static final long serialVersionUID = 1411267192027779714L;

	private transient ActivitySignService activitySignService;

	// 定义老人姓名输入法变量
	private String nameToSql;// 修改康娱报名
	private String addNameToSql;// 新增康娱报名
	private String fitcolName;
	private String displaycolName;

	// 定义项目名称输入法变量
	private String itemToSql;
	private String fitcolItem;
	private String displaycolItem;

	// 定义修改和删除按钮可用性Flag
	private boolean modifyEnableFlag = true;
	private boolean deleteEnableFlag = true;

	// 定义报名时间输入框接收参数
	private Date startDate;
	private Date endDate;

	// 定义能否参加和是否参加下拉框接收参数
	// private Long attendCanOrNotFlag;
	private Long attendIsOrNotFlag;

	// 定义老人姓名和项目名称输入法接收变量
	private Long olderId;
	private String olderName;
	private Long itemId;
	private String itemName;

	// 老人报名对应的表对象
	private PensionAttendolder pensionAttendolder = new PensionAttendolder();

	// 选中的老人报名对象
	private PensionAttendolder selectedensionAttendolder = new PensionAttendolder();

	// 编辑的老人报名对象
	private PensionAttendolder editensionAttendolder = new PensionAttendolder();
	// 所有老人报名信息的对象List
	private List<PensionAttendolder> pensionAttendolderList = new ArrayList<PensionAttendolder>();

	// 删除确认提示信息
	private String strDeleteMsg;

	/**
	 * 
	 * @Title: init
	 * @Description: 页面加载初始化
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-13 下午2:34:10
	 * @version V1.0
	 */
	@PostConstruct
	public void init() {
		modifyEnableFlag = true;
		deleteEnableFlag = true;
		// 初始化老人姓名输入法
		initNameToSql();
		// 初始化康娱项目输入法
		initItemToSql();
		pensionAttendolderList = activitySignService
				.selectAllPensionAttendolderList();
	}

	/**
	 * 
	 * @Title: initNameToSql
	 * @Description:老人姓名输入法
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-5 上午11:18:48
	 * @version V1.0
	 */
	public void initNameToSql() {
		nameToSql = " select" + " po.id as olderId" + " ,po.name as olderName"
				+ " ,po.inputcode as inputCode"
				+ " ,case po.sex when 1 then '男' else '女' end as sexName"
				+ " from" + " pension_older po" + " where" + " po.statuses = 3"
				+ " and" + " po.cleared = 2" + " and po.id not in ("
				+ " select pa.older_id "
				+ " from pension_attendolder pa where pa.activity_id = "
				+ selectedensionAttendolder.getActivityId()
				+ " and pa.cleared = 2)";
		addNameToSql = " select" + " po.id as olderId"
				+ " ,po.name as olderName" + " ,po.inputcode as inputCode"
				+ " ,case po.sex when 1 then '男' else '女' end as sexName"
				+ " from" + " pension_older po" + " where" + " po.statuses = 3"
				+ " and" + " po.cleared = 2" + " and po.id not in ("
				+ " select pa.older_id"
				+ " from pension_attendolder pa where pa.activity_id = "
				+ pensionAttendolder.getActivityId() + " and pa.cleared = 2)";
		fitcolName = "olderId,inputCode";
		displaycolName = "编号:0,姓名:1,输入码:2,性别:3";
	}

	/**
	 * 
	 * @Title: initItemToSql
	 * @Description: 康娱项目输入法
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-13 下午3:32:28
	 * @version V1.0
	 */
	public void initItemToSql() {
		itemToSql = " select"
				+ " pa.id as itemId"
				+ " ,pa.itemname as itemName"
				+ " ,pa.inputcode as inputCode ,DATE_FORMAT(pa.startTime, '%Y-%m-%d %H:%i') as startTime"
				+ " from" + " pension_activity pa" + " WHERE"
				+ " pa.`cleared`=2 and pa.canceled=2";
		fitcolItem = "itemId,inputCode";
		displaycolItem = "编号:0,活动名称:1,输入码:2,举办时间:3";
	}

	/**
	 * 
	 * @Title: getListByCondition
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-13 上午9:45:39
	 * @version V1.0
	 */
	public void getListByCondition() {
		// FacesContext context = FacesContext.getCurrentInstance();
		if (itemName == null || "".equals(itemName.trim())) {
			itemId = null;
		}
		if (olderName == null || "".equals(olderName.trim())) {
			olderId = null;
		}
		pensionAttendolderList = activitySignService
				.selectPensionAttendolderListByCondition(olderId, itemId,
						attendIsOrNotFlag, startDate, endDate);
		// context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		// "查询完毕！", ""));
	}

	/**
	 * 
	 * @Title: addOlderAttend
	 * @Description: 新增报名记录
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午10:05:23
	 * @version V1.0
	 */
	public void addOlderAttend() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		PensionActivity pay = activitySignService
				.getPensionActivity(pensionAttendolder.getActivityId());
		if (pensionAttendolder.getItemName() == null
				|| pensionAttendolder.getOlderId() == null) {
			request.addCallbackParam("canAddFlag", false);
		} else if (pay.getStarttime().before(new Date())) {
			request.addCallbackParam("canAddFlag", false);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "项目" + pay.getItemname()
							+ "的开始日期为" + sdf.format(pay.getStarttime()) + ","
							+ "已经过期，不可以再报名！", ""));
		} else {
			try {
				activitySignService.saveOlderAttend(pensionAttendolder);
				activitySignService.updateActivityRecord(pensionAttendolder);
				pensionAttendolderList = activitySignService
						.selectAllPensionAttendolderList();
				context.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "老人"
								+ activitySignService
										.getOlderName(pensionAttendolder)
								+ "报名参加项目"
								+ activitySignService
										.getItemName(pensionAttendolder)
								+ "成功！", ""));
				pensionAttendolder = new PensionAttendolder();
				request.addCallbackParam("canAddFlag", true);
			} catch (PmsException e) {
				pensionAttendolder.setOlderId(null);
				pensionAttendolder.setOlderName(null);
				request.addCallbackParam("canAddFlag", false);
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
			}
		}

	}

	/**
	 * 
	 * @Title: setDefaultPara
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-22 上午10:15:14
	 * @version V1.0
	 */
	public void setDefaultPara() {
		pensionAttendolder.setEnlisttime(new Date());
	}

	/**
	 * 
	 * @Title: setSelectedFlag
	 * @Description: 选中一行，设定按钮的可用性
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午8:39:19
	 * @version V1.0
	 */
	public void setSelectedFlag(SelectEvent event) {
		modifyEnableFlag = false;
		deleteEnableFlag = false;
	}

	/**
	 * 
	 * @Title: setUnselectedFlag
	 * @Description: 取消选中一行，设定按钮的可用性
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午8:39:35
	 * @version V1.0
	 */
	public void setUnselectedFlag(UnselectEvent event) {
		modifyEnableFlag = true;
		deleteEnableFlag = true;
	}

	public void initEditAttend() {
		editensionAttendolder = new PensionAttendolder();
		editensionAttendolder.setId(selectedensionAttendolder.getId());
		editensionAttendolder.setActivityId(selectedensionAttendolder
				.getActivityId());
		editensionAttendolder.setItemName(selectedensionAttendolder
				.getItemName());
		editensionAttendolder
				.setOlderId(selectedensionAttendolder.getOlderId());
		editensionAttendolder.setOlderName(selectedensionAttendolder
				.getOlderName());
		editensionAttendolder.setStartDate(selectedensionAttendolder
				.getStartDate());
		editensionAttendolder.setEnlisttime(selectedensionAttendolder
				.getEnlisttime());

	}

	/**
	 * 
	 * @Title: modifyOlderAttend
	 * @Description: 更新报名记录
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午10:05:06
	 * @version V1.0
	 */
	public void modifyOlderAttend() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (editensionAttendolder.getActivityId() == null
				|| editensionAttendolder.getOlderId() == null
				|| editensionAttendolder.getEnlisttime() == null) {
			request.addCallbackParam("canSaveFlag", false);
		} else if (editensionAttendolder.getStartDate().before(new Date())) {
			request.addCallbackParam("canSaveFlag", false);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "项目"
							+ editensionAttendolder.getItemName() + "的开始日期为"
							+ sdf.format(editensionAttendolder.getStartDate())
							+ "," + "已经过期，不可以再报名！", ""));
		} else if (this.checkDuality(editensionAttendolder.getActivityId(),
				editensionAttendolder.getOlderId())) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "该老人已报名！", ""));
			request.addCallbackParam("canSaveFlag", false);
		} else {
			request.addCallbackParam("canSaveFlag", true);
			activitySignService.updatePensionAttendolder(editensionAttendolder);
			getListByCondition();
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "老人"
							+ activitySignService
									.getOlderName(editensionAttendolder)
							+ "报名项目"
							+ activitySignService
									.getItemName(editensionAttendolder)
							+ "修改成功！", ""));
		}

	}

	/**
	 * 判断修改老人报名信息时，修改后的信息是否已存在
	 * 
	 * @param activityId
	 * @param olderId2
	 * @return
	 */
	public boolean checkDuality(Long activityId, Long olderId2) {
		return activitySignService.checkDuality(activityId, olderId2).size() > 0 ? true
				: false;
	}

	/**
	 * 
	 * @Title: deleteOlderAttend
	 * @Description: 删除报名记录
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-15 上午10:04:56
	 * @version V1.0
	 */
	public void deleteOlderAttend() {
		FacesContext context = FacesContext.getCurrentInstance();
		activitySignService.deletePensionAttendolder(selectedensionAttendolder);
		pensionAttendolderList = activitySignService
				.selectAllPensionAttendolderList();
		modifyEnableFlag = true;
		deleteEnableFlag = true;
		context.addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "老人"
						+ activitySignService
								.getOlderName(selectedensionAttendolder)
						+ "报名项目"
						+ activitySignService
								.getItemName(selectedensionAttendolder)
						+ "删除成功！", ""));
	}

	/**
	 * 
	 * @Title: initParam
	 * @Description: 清空按钮调用的方法
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-17 上午10:08:09
	 * @version V1.0
	 */
	public void initParam() {
		olderId = null;
		olderName = "";
		itemId = null;
		itemName = "";
		startDate = null;
		endDate = null;
		// attendCanOrNotFlag = (long) 3;
		attendIsOrNotFlag = (long) 3;
	}

	/**
	 * 
	 * @Title: setDeleteMsg
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-17 上午10:17:43
	 * @version V1.0
	 */
	public void setDeleteMsg() {
		strDeleteMsg = "确实要删除老人"
				+ activitySignService.getOlderName(selectedensionAttendolder)
				+ "的报名项目"
				+ activitySignService.getItemName(selectedensionAttendolder)
				+ "吗？";
	}

	/**
	 * 
	 * @Title: initAddParam
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-10-30 上午9:32:52
	 * @version V1.0
	 */
	public void initAddParam() {
		pensionAttendolder = new PensionAttendolder();
	}

	public String getNameToSql() {
		return nameToSql;
	}

	public void setNameToSql(String nameToSql) {
		this.nameToSql = nameToSql;
	}

	public String getFitcolName() {
		return fitcolName;
	}

	public void setFitcolName(String fitcolName) {
		this.fitcolName = fitcolName;
	}

	public String getDisplaycolName() {
		return displaycolName;
	}

	public void setDisplaycolName(String displaycolName) {
		this.displaycolName = displaycolName;
	}

	public String getItemToSql() {
		return itemToSql;
	}

	public void setItemToSql(String itemToSql) {
		this.itemToSql = itemToSql;
	}

	public String getFitcolItem() {
		return fitcolItem;
	}

	public void setFitcolItem(String fitcolItem) {
		this.fitcolItem = fitcolItem;
	}

	public String getDisplaycolItem() {
		return displaycolItem;
	}

	public void setDisplaycolItem(String displaycolItem) {
		this.displaycolItem = displaycolItem;
	}

	public boolean isModifyEnableFlag() {
		return modifyEnableFlag;
	}

	public void setModifyEnableFlag(boolean modifyEnableFlag) {
		this.modifyEnableFlag = modifyEnableFlag;
	}

	public boolean isDeleteEnableFlag() {
		return deleteEnableFlag;
	}

	public void setDeleteEnableFlag(boolean deleteEnableFlag) {
		this.deleteEnableFlag = deleteEnableFlag;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	// public Long getAttendCanOrNotFlag() {
	// return attendCanOrNotFlag;
	// }
	//
	// public void setAttendCanOrNotFlag(Long attendCanOrNotFlag) {
	// this.attendCanOrNotFlag = attendCanOrNotFlag;
	// }

	public Long getAttendIsOrNotFlag() {
		return attendIsOrNotFlag;
	}

	public void setAttendIsOrNotFlag(Long attendIsOrNotFlag) {
		this.attendIsOrNotFlag = attendIsOrNotFlag;
	}

	/**
	 * @return the activitySignService
	 */
	public ActivitySignService getActivitySignService() {
		return activitySignService;
	}

	/**
	 * @param activitySignService
	 *            the activitySignService to set
	 */
	public void setActivitySignService(ActivitySignService activitySignService) {
		this.activitySignService = activitySignService;
	}

	/**
	 * @return the pensionAttendolder
	 */
	public PensionAttendolder getPensionAttendolder() {
		return pensionAttendolder;
	}

	/**
	 * @param pensionAttendolder
	 *            the pensionAttendolder to set
	 */
	public void setPensionAttendolder(PensionAttendolder pensionAttendolder) {
		this.pensionAttendolder = pensionAttendolder;
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
	 * @return the selectedensionAttendolder
	 */
	public PensionAttendolder getSelectedensionAttendolder() {
		return selectedensionAttendolder;
	}

	/**
	 * @param selectedensionAttendolder
	 *            the selectedensionAttendolder to set
	 */
	public void setSelectedensionAttendolder(
			PensionAttendolder selectedensionAttendolder) {
		this.selectedensionAttendolder = selectedensionAttendolder;
	}

	/**
	 * @return the strDeleteMsg
	 */
	public String getStrDeleteMsg() {
		return strDeleteMsg;
	}

	/**
	 * @param strDeleteMsg
	 *            the strDeleteMsg to set
	 */
	public void setStrDeleteMsg(String strDeleteMsg) {
		this.strDeleteMsg = strDeleteMsg;
	}

	public String getAddNameToSql() {
		return addNameToSql;
	}

	public void setAddNameToSql(String addNameToSql) {
		this.addNameToSql = addNameToSql;
	}

	public PensionAttendolder getEditensionAttendolder() {
		return editensionAttendolder;
	}

	public void setEditensionAttendolder(
			PensionAttendolder editensionAttendolder) {
		this.editensionAttendolder = editensionAttendolder;
	}

}
