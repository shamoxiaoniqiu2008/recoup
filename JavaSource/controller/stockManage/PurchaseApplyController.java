/**  
 * @Title: PurchaseApplyController.java 
 * @Package controller.stockManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:49:41 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package controller.stockManage;

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

import service.stockManage.PurchaseApplyService;
import util.DateUtil;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.stockManage.PensionPurchaseDetail;
import domain.stockManage.PensionPurchaseRecord;

/**
 * @ClassName: PurchaseApplyController
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:49:41
 * @version V1.0
 */
public class PurchaseApplyController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */
	private static final long serialVersionUID = -6649046518368341195L;

	private transient PurchaseApplyService purchaseApplyService;
	// 查询条件
	private Date startDate;
	private Date endDate;
	private String isSend;
	private String auditResult;
	// 选中的采购记录
	private PensionPurchaseRecord selectedRow;
	// 采购记录列表
	private List<PensionPurchaseRecord> applyRecordList = new ArrayList<PensionPurchaseRecord>();
	// 采购明细列表
	private List<PensionPurchaseDetail> applyDetailList = new ArrayList<PensionPurchaseDetail>();
	// 新增采购记录
	private PensionPurchaseRecord addApplyRecord;
	// 新增采购明细
	private PensionPurchaseDetail addApplyDetail;
	// 新增采购明细列表
	private List<PensionPurchaseDetail> addApplyDetailList = new ArrayList<PensionPurchaseDetail>();
	// 当前登录用户
	private PensionEmployee curUser;
	// 采购数量
	private String purchaseQty;
	// 选中的物资明细
	private PensionPurchaseDetail selectedItemDetail;

	@PostConstruct
	public void init() {
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		startDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
		endDate = DateUtil.getBeforeDay(startDate, -7);
		searchApplyRecord();
	}

	/**
	 * 查询采购记录
	 */
	public void searchApplyRecord() {
		applyRecordList = purchaseApplyService.selectApplyRecord(startDate,
				endDate, isSend, auditResult);
		selectedRow = null;
		applyDetailList = new ArrayList<PensionPurchaseDetail>();
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearch() {
		startDate = null;
		endDate = null;
		isSend = null;
		auditResult = null;
	}

	/**
	 * 查询采购明细
	 */
	public void selectApplyDetail() {
		applyDetailList = purchaseApplyService.selectApplyDetail(selectedRow
				.getId());
	}

	/**
	 * 清空采购明细
	 */
	public void clearApplyDetail() {
		applyDetailList = new ArrayList<PensionPurchaseDetail>();
	}

	/**
	 * 初始化新增对话框
	 */
	public void initAddForm() {
		addApplyDetail = new PensionPurchaseDetail();
		addApplyDetailList = new ArrayList<PensionPurchaseDetail>();
		addApplyRecord = new PensionPurchaseRecord();
		purchaseQty = "";
		addApplyRecord.setPurchaseNo(purchaseApplyService.selectPurchaseNo());
		addApplyRecord.setPurchaseTime(new Date());
	}

	/**
	 * 初始化修改对话框
	 */
	public void initEditForm() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条记录！",
							""));
			return;
		}
		if (selectedRow.getSendFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"该采购记录已发送审核通知，不可修改！", ""));
			return;
		}
		addApplyRecord = selectedRow;
		addApplyDetailList = purchaseApplyService
				.selectApplyDetail(addApplyRecord.getId());
		addApplyDetail = new PensionPurchaseDetail();
		purchaseQty = "";
		request.addCallbackParam("editshow", true);
	}

	/**
	 * 增加一条明细
	 */
	public void addApplyDetail() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (purchaseQty != "" && purchaseQty != null) {
				Integer quality = Integer.valueOf(purchaseQty);
				if (!(quality.intValue() > 0)) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"采购数量  请输入正值！", ""));
					addApplyDetail.setPurchaseMoney(null);
					return;
				}
				addApplyDetail.setPurchaseQty(quality);
			}
			for (PensionPurchaseDetail detail : addApplyDetailList) {
				if (detail.getItemId().equals(addApplyDetail.getItemId())) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该物资已添加！", ""));
					return;
				}
			}
			addApplyDetail.setCleared(2);
			addApplyDetailList.add(addApplyDetail);
			addApplyDetail = new PensionPurchaseDetail();
			purchaseQty = "";
			request.addCallbackParam("addDetail", true);
		} catch (NumberFormatException e) {
			addApplyDetail.setPurchaseMoney(null);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"采购数量  只能输入数字！", ""));
		}
	}

	/**
	 * 删除主记录确认
	 */
	public void delApplyConfirm() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条记录！",
							""));
			return;
		}
		if (selectedRow.getSendFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"该采购申请发送审核通知，不可删除！", ""));
			return;
		}
		request.addCallbackParam("delRecord", true);
	}

	/**
	 * 删除入库记录
	 */
	public void deleteApply() {
		selectedRow.setCleared(1);
		purchaseApplyService.deleteApplyRecord(selectedRow);
		searchApplyRecord();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "删除成功！", ""));
	}

	/**
	 * 删除明细确认
	 */
	public void delDetailConfirm() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (selectedItemDetail == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条明细！",
							""));
			return;
		}
		request.addCallbackParam("confirmDel", true);
	}

	/**
	 * 删除明细
	 */
	public void deleteItemDetail() {
		addApplyDetailList.remove(selectedItemDetail);
		selectedItemDetail = new PensionPurchaseDetail();
	}

	/**
	 * 保存一条采购申请
	 */
	public void saveApplyRecord() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (addApplyDetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "物资明细列表不可为空！",
							""));
			return;
		}
		addApplyRecord.setPurchaserId(curUser.getId());
		addApplyRecord.setPurchaserName(curUser.getName());
		addApplyRecord.setCleared(2);
		addApplyRecord.setApproveFlag(2);
		addApplyRecord.setGenerateTime(new Date());
		addApplyRecord.setSendFlag(2);
		addApplyRecord.setTotalMoney(0.0F);
		purchaseApplyService.insertApplyRecord(addApplyRecord,
				addApplyDetailList);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "新增采购申请成功！", ""));
		searchApplyRecord();
		request.addCallbackParam("addHide", true);
	}

	/**
	 * 保存并提交一条采购申请
	 */
	public void saveAndSendApplyRecord() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (addApplyDetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "物资明细列表不可为空！",
							""));
			return;
		}
		addApplyRecord.setPurchaserId(curUser.getId());
		addApplyRecord.setPurchaserName(curUser.getName());
		addApplyRecord.setCleared(2);
		addApplyRecord.setApproveFlag(2);
		addApplyRecord.setGenerateTime(new Date());
		addApplyRecord.setSendFlag(1);
		addApplyRecord.setTotalMoney(0.0F);
		purchaseApplyService.insertAndSendApplyRecord(addApplyRecord,
				addApplyDetailList);
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"新增采购申请并提交成功！", ""));
		searchApplyRecord();
		request.addCallbackParam("addHide", true);
	}

	/**
	 * 更新入库记录
	 */
	public void updateApplyRecord() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (addApplyDetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "物资明细列表不可为空！",
							""));
			return;
		}
		purchaseApplyService.updateApplyRecord(addApplyRecord,
				addApplyDetailList);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "采购申请修改成功！", ""));
		applyRecordList = purchaseApplyService.selectApplyRecord(startDate,
				endDate, isSend, auditResult);
		applyDetailList = purchaseApplyService.selectApplyDetail(selectedRow
				.getId());
		request.addCallbackParam("edithide", true);
	}

	/**
	 * 更新并提交入库记录
	 */
	public void updateAndSendApplyRecord() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (addApplyDetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "物资明细列表不可为空！",
							""));
			return;
		}
		purchaseApplyService.updateAndSendApplyRecord(addApplyRecord,
				addApplyDetailList);
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"采购申请修改并提交成功！", ""));
		applyRecordList = purchaseApplyService.selectApplyRecord(startDate,
				endDate, isSend, auditResult);
		applyDetailList = purchaseApplyService.selectApplyDetail(selectedRow
				.getId());
		request.addCallbackParam("edithide", true);
	}

	/**
	 * 由出库记录生成采购单
	 */
	public void generateApply() {
		addApplyRecord = new PensionPurchaseRecord();
		addApplyDetail = new PensionPurchaseDetail();
		addApplyRecord.setPurchaseNo(purchaseApplyService.selectPurchaseNo());
		addApplyRecord.setPurchaserName(curUser.getName());
		addApplyRecord.setPurchaseTime(new Date());
		addApplyDetailList = purchaseApplyService.selectStorageoutList();
	}

	/**
	 * 发送采购审核通知
	 */
	public void sendMessage() {
		if (selectedRow == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条采购申请！",
							""));
			return;
		}
		if (selectedRow.getSendFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该申请审核通知已发送！",
							""));
			return;
		}
		purchaseApplyService.sendAndInsertEvaluate(selectedRow);
		applyRecordList = purchaseApplyService.selectApplyRecord(startDate,
				endDate, isSend, auditResult);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "审核通知发送成功！", ""));
	}

	/**
	 * 选中一行时触发
	 * 
	 */
	public void setEnableFlag(SelectEvent e) {

	}

	/**
	 * 未选中一行时触发
	 * 
	 */
	public void setUnableFlag(UnselectEvent e) {

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

	public PensionPurchaseRecord getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(PensionPurchaseRecord selectedRow) {
		this.selectedRow = selectedRow;
	}

	public List<PensionPurchaseRecord> getApplyRecordList() {
		return applyRecordList;
	}

	public void setApplyRecordList(List<PensionPurchaseRecord> applyRecordList) {
		this.applyRecordList = applyRecordList;
	}

	public List<PensionPurchaseDetail> getApplyDetailList() {
		return applyDetailList;
	}

	public void setApplyDetailList(List<PensionPurchaseDetail> applyDetailList) {
		this.applyDetailList = applyDetailList;
	}

	public void setPurchaseApplyService(
			PurchaseApplyService purchaseApplyService) {
		this.purchaseApplyService = purchaseApplyService;
	}

	public PurchaseApplyService getPurchaseApplyService() {
		return purchaseApplyService;
	}

	public PensionPurchaseRecord getAddApplyRecord() {
		return addApplyRecord;
	}

	public void setAddApplyRecord(PensionPurchaseRecord addApplyRecord) {
		this.addApplyRecord = addApplyRecord;
	}

	public PensionPurchaseDetail getAddApplyDetail() {
		return addApplyDetail;
	}

	public void setAddApplyDetail(PensionPurchaseDetail addApplyDetail) {
		this.addApplyDetail = addApplyDetail;
	}

	public List<PensionPurchaseDetail> getAddApplyDetailList() {
		return addApplyDetailList;
	}

	public void setAddApplyDetailList(
			List<PensionPurchaseDetail> addApplyDetailList) {
		this.addApplyDetailList = addApplyDetailList;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public String getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(String purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public PensionPurchaseDetail getSelectedItemDetail() {
		return selectedItemDetail;
	}

	public void setSelectedItemDetail(PensionPurchaseDetail selectedItemDetail) {
		this.selectedItemDetail = selectedItemDetail;
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend;
	}

	public String getIsSend() {
		return isSend;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getAuditResult() {
		return auditResult;
	}

}
