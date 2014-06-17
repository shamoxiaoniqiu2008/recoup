/**  
 * @Title: ApplyComfirmController.java 
 * @Package controller.activityManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午2:54:59 
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

import service.activityManage.ApplyComfirmService;
import domain.activityManage.PensionAttendolder;

/**
 * @ClassName: ApplyComfirmController
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午2:54:59
 * @version V1.0
 */
public class ApplyComfirmController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */
	private static final long serialVersionUID = 6969629080751072209L;

	// 引入service
	private transient ApplyComfirmService applyComfirmService;
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
	// 是否签到
	private Integer attended;
	// 能否参加
	private Long canAddFlag;
	// 字体颜色
	private String fontColor;
	// 是否全选
	private boolean selectedAllFlag = false;
	// 事由接收参数
	private String reasonString = null;
	// 备注接收参数
	private String noteString = null;

	private String signMsg;

	private PensionAttendolder selectedRecord;

	@PostConstruct
	public void init() {
		initParam();
		initNameToSql();
		initItemToSql();
		this.searchAttendInfo();
		// pensionAttendolderList = applyComfirmService.searchAllAttendInfo();
	}

	/**
	 * 
	 * @Title: initNameToSql
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-13 上午10:15:12
	 * @version V1.0
	 */
	public void initNameToSql() {
		nameToSql = " select" + " po.id as olderId" + " ,po.name as olderName"
				+ " ,po.inputcode as inputCode"
				+ " ,case po.sex when 1 then '男' else '女' end as sexName"
				+ " from" + " pension_older po" + " where" + " po.statuses = 3"
				+ " and" + " po.cleared = 2";
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
	 * @date 2013-11-13 上午10:15:24
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
	 * @Title: searchAttendInfo
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-13 下午2:49:15
	 * @version V1.0
	 */
	public void searchAttendInfo() {
		if (itemName == null || "".equals(itemName.trim())) {
			itemId = null;
		}
		if (olderName == null || "".equals(olderName.trim())) {
			olderId = null;
		}
		pensionAttendolderList = applyComfirmService.searchAttendInfo(itemId,
				olderId, startDate, endDate, attended);
	}

	/**
	 * 
	 * @Title: initParam
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-13 下午2:49:11
	 * @version V1.0
	 */
	public void initParam() {
		itemId = null;
		itemName = "";
		olderId = null;
		olderName = "";
		startDate = null;
		endDate = null;
		attended = 2;// 是否签到 默认否
	}

	/**
	 * 
	 * @Title: setFontColor
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-13 下午4:13:30
	 * @version V1.0
	 */
	public void setFontColor() {
		if (canAddFlag == 2) {
			fontColor = "redfont";
		} else {
			fontColor = null;
		}

	}

	/**
	 * 
	 * @Title: saveConfirmInfo
	 * @Description: TODO
	 * @param @param pensionAttendolder
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 上午8:46:01
	 * @version V1.0
	 */
	/*
	 * public void saveConfirmInfo(PensionAttendolder pensionAttendolder){
	 * FacesContext context = FacesContext.getCurrentInstance();
	 * if(pensionAttendolder.getAttended()== 2 &&
	 * (pensionAttendolder.getReason() == null ||
	 * "".equals(pensionAttendolder.getReason().trim()))){
	 * context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
	 * "请输入事由！", "")); }else{
	 * applyComfirmService.updatePensionAttendolder(pensionAttendolder);
	 * if(itemName == null || "".equals(itemName.trim())){ itemId = null; }
	 * if(olderName == null || "".equals(olderName.trim())){ olderId = null; }
	 * pensionAttendolderList = applyComfirmService.searchAttendInfo(itemId,
	 * olderId, startDate, endDate); context.addMessage(null, new
	 * FacesMessage(FacesMessage.SEVERITY_INFO, "确认完毕！", "")); } }
	 */

	/**
	 * 
	 * @Title: setSelectedAllFlag
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 上午9:23:00
	 * @version V1.0
	 */
	public void setSelectedAllFlag(ValueChangeEvent event) {
		boolean flag = (Boolean) event.getNewValue();
		for (PensionAttendolder pa : pensionAttendolderList) {
			pa.setSelectedFlag(flag);
		}
	}

	/**
	 * 
	 * @Title: checkIsSelected
	 * @Description: TODO
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 上午10:20:05
	 * @version V1.0
	 */
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

	/**
	 * 
	 * @Title: checkDialogShow
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 上午10:22:39
	 * @version V1.0
	 */
	public void checkDialogShow() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (!checkIsSelected()) {
			request.addCallbackParam("showFlag", false);
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请选择要确认的项目！", ""));
		} else {
			if (canAddFlag == 0) {
				if (reasonString == null || "".equals(reasonString.trim())) {
					request.addCallbackParam("showFlag", false);
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_WARN, "请选择能否参加！", ""));

				}
			} else {
				if (canAddFlag == 2) {
					if (reasonString == null || "".equals(reasonString.trim())) {
						request.addCallbackParam("showFlag", false);
						context.addMessage(null, new FacesMessage(
								FacesMessage.SEVERITY_WARN, "请输入事由！", ""));
					} else {
						request.addCallbackParam("showFlag", true);
					}
				} else {
					request.addCallbackParam("showFlag", true);
				}
			}
		}

	}

	/**
	 * 点击【已到】 add by mary.liu 2014-02-25
	 * 
	 * @param attendOlder
	 */
	public void saveConfirmInfo(PensionAttendolder pensionAttendolder) {
		if (pensionAttendolder.getIsattend() == 1) {
			if (pensionAttendolder.getAttended().equals(1)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "该老人已签到！",
								""));
				return;
			}
			pensionAttendolder.setAttended(1);// 设为 已签到
			pensionAttendolder.setReason("");
			applyComfirmService.updatePensionAttendolder(pensionAttendolder);
			this.searchAttendInfo();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "签到成功！", ""));
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"该老人不能参加该康娱活动！", ""));
		}
	}

	/**
	 * 
	 * @Title: saveConfirmInfoList
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 上午10:49:33
	 * @version V1.0
	 */
	/*
	 * public void saveConfirmInfoList(){ FacesContext context =
	 * FacesContext.getCurrentInstance(); for(PensionAttendolder p:
	 * pensionAttendolderList){ if(p.isSelectedFlag()){
	 * p.setAttended(canAddFlag.intValue()); p.setReason(reasonString);
	 * p.setNotes(noteString); applyComfirmService.updatePensionAttendolder(p);
	 * } } if(itemName == null || "".equals(itemName.trim())){ itemId = null; }
	 * if(olderName == null || "".equals(olderName.trim())){ olderId = null; }
	 * pensionAttendolderList = applyComfirmService.searchAttendInfo(itemId,
	 * olderId, startDate, endDate); selectedAllFlag = false; canAddFlag =
	 * (long) 0; reasonString = ""; noteString = ""; context.addMessage(null,
	 * new FacesMessage(FacesMessage.SEVERITY_INFO, "确认完毕！", ""));
	 * 
	 * }
	 */
	/**
	 * 
	 * @Title: checkIsSelectAll
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-14 下午1:59:18
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

	public void initNote(PensionAttendolder pensionAttendolder) {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if ((pensionAttendolder.getAttended() != null)
				&& (pensionAttendolder.getAttended().equals(2))
				&& (pensionAttendolder.getReason() != null)
				&& (!pensionAttendolder.getReason().equals(""))) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该老人未到事由已添加！",
							""));
			return;
		}
		selectedRecord = pensionAttendolder;
		selectedRecord.setReason("");
		requestContext.addCallbackParam("show", true);
	}

	public void unSignConfirm() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		selectedRecord.setAttended(2);// 设为未签到
		applyComfirmService.updatePensionAttendolder(selectedRecord);
		this.searchAttendInfo();
		requestContext.addCallbackParam("hide", true);
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
	 * @return the applyComfirmService
	 */
	public ApplyComfirmService getApplyComfirmService() {
		return applyComfirmService;
	}

	/**
	 * @param applyComfirmService
	 *            the applyComfirmService to set
	 */
	public void setApplyComfirmService(ApplyComfirmService applyComfirmService) {
		this.applyComfirmService = applyComfirmService;
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
	 * @return the canAddFlag
	 */
	public Long getCanAddFlag() {
		return canAddFlag;
	}

	/**
	 * @param canAddFlag
	 *            the canAddFlag to set
	 */
	public void setCanAddFlag(Long canAddFlag) {
		this.canAddFlag = canAddFlag;
	}

	/**
	 * @return the fontColor
	 */
	public String getFontColor() {
		return fontColor;
	}

	/**
	 * @param fontColor
	 *            the fontColor to set
	 */
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
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
	 * @return the reasonString
	 */
	public String getReasonString() {
		return reasonString;
	}

	/**
	 * @param reasonString
	 *            the reasonString to set
	 */
	public void setReasonString(String reasonString) {
		this.reasonString = reasonString;
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

	public Integer getAttended() {
		return attended;
	}

	public void setAttended(Integer attended) {
		this.attended = attended;
	}

	public String getSignMsg() {
		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public PensionAttendolder getSelectedRecord() {
		return selectedRecord;
	}

	public void setSelectedRecord(PensionAttendolder selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

}
