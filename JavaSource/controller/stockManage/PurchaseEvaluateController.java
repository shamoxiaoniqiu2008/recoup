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
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.stockManage.PurchaseEvalDomain;
import service.stockManage.PurchaseEvaluateService;
import service.stockManage.PurchaseRecordDomain;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.stockManage.PensionPurchaseDetail;
import domain.stockManage.PensionPurchaseEvaluate;

/**
 * @ClassName: PurchaseApplyController
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:49:41
 * @version V1.0
 */
public class PurchaseEvaluateController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */
	private static final long serialVersionUID = -6649046518368341195L;

	private transient PurchaseEvaluateService purchaseEvaluateService;
	// 查询条件--采购单号
	private String purchaseNo;
	private Long applyId;
	// 选中的采购记录
	private PurchaseRecordDomain selectedRow;
	// 采购记录列表
	private List<PurchaseRecordDomain> applyRecordList = new ArrayList<PurchaseRecordDomain>();
	// 采购明细列表
	private List<PensionPurchaseDetail> applyDetailList = new ArrayList<PensionPurchaseDetail>();
	// 各部门审核意见列表
	private List<PurchaseEvalDomain> deptEvaluateList = new ArrayList<PurchaseEvalDomain>();
	// 当前登录用户
	private PensionEmployee curUser;
	private Long deptId;
	//
	private PensionPurchaseEvaluate approveRecord = new PensionPurchaseEvaluate();

	@PostConstruct
	public void init() {
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		deptId = curUser.getDeptId();

		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String recordId = paramsMap.get("id");
		String recordNo = paramsMap.get("purchaseNo");
		if (recordId != null) {
			applyId = Long.valueOf(recordId);
			purchaseNo = recordNo;
		} else {
			applyId = null;
			purchaseNo = null;
		}
		applyRecordList = purchaseEvaluateService.selectApplyRecord(purchaseNo,
				applyId, deptId);
		if (applyRecordList.size() != 0) {
			selectedRow = applyRecordList.get(0);
			selectEvalDetail();
		}
	}

	/**
	 * 查询采购记录
	 */
	public void searchApplyRecord() {
		if (purchaseNo == "") {
			purchaseNo = null;
			applyId = null;
		}
		applyRecordList = purchaseEvaluateService.selectApplyRecord(purchaseNo,
				applyId, deptId);
		selectedRow = null;
		deptEvaluateList = new ArrayList<PurchaseEvalDomain>();
	}

	public void selectPurchaseRecords(PurchaseRecordDomain selectedRow) {
		applyDetailList = purchaseEvaluateService.selectApplyDetail(selectedRow
				.getId());
	}

	/**
	 * 审核通过
	 */
	public void passApplyRecord() {
		if (selectedRow == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条记录！",
							""));
			return;
		}
		if (selectedRow.getApproveFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该申请已审核！", ""));
			return;
		}
		PensionPurchaseEvaluate record = new PensionPurchaseEvaluate();
		record.setEvaluateResult(1);
		record.setEvaluatorId(curUser.getId());
		record.setEvaluatorName(curUser.getName());
		record.setApplyId(selectedRow.getId());
		record.setDeptId(deptId);
		record.setEvaluateDate(new Date());
		Integer evalResult = purchaseEvaluateService.updateEvalRecord(record);
		if (evalResult != null) {
			selectedRow.setApproveFlag(1);
			selectedRow.setApproveResult(evalResult);
		}
		deptEvaluateList = purchaseEvaluateService
				.selectDeptEvalList(selectedRow.getId());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "审核完成！", ""));
	}

	/**
	 * 审核不通过
	 */
	public void refuseCheck() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条记录！",
							""));
			return;
		}
		if (selectedRow.getApproveFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该申请已审核！", ""));
			return;
		}
		approveRecord.setNote("");
		requestContext.addCallbackParam("show", true);
	}

	public void refusePurchase() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		approveRecord.setEvaluateResult(2);
		approveRecord.setEvaluatorId(curUser.getId());
		approveRecord.setEvaluatorName(curUser.getName());
		approveRecord.setApplyId(selectedRow.getId());
		approveRecord.setDeptId(deptId);
		approveRecord.setEvaluateDate(new Date());
		purchaseEvaluateService.updateEvalRecord(approveRecord);
		Integer evalResult = purchaseEvaluateService
				.updateEvalRecord(approveRecord);
		if (evalResult != null) {
			selectedRow.setApproveFlag(1);
			selectedRow.setApproveResult(evalResult);
		}
		deptEvaluateList = purchaseEvaluateService
				.selectDeptEvalList(selectedRow.getId());
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "审核完成！", ""));
		requestContext.addCallbackParam("hide", true);
	}

	/**
	 * 查询各部门审核意见
	 */
	public void selectEvalDetail() {
		deptEvaluateList = purchaseEvaluateService
				.selectDeptEvalList(selectedRow.getId());
	}

	/**
	 * 清空意见列表
	 */
	public void clearEvalDetail() {
		deptEvaluateList = new ArrayList<PurchaseEvalDomain>();
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

	public PurchaseRecordDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(PurchaseRecordDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public List<PurchaseRecordDomain> getApplyRecordList() {
		return applyRecordList;
	}

	public void setApplyRecordList(List<PurchaseRecordDomain> applyRecordList) {
		this.applyRecordList = applyRecordList;
	}

	public List<PensionPurchaseDetail> getApplyDetailList() {
		return applyDetailList;
	}

	public void setApplyDetailList(List<PensionPurchaseDetail> applyDetailList) {
		this.applyDetailList = applyDetailList;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public void setPurchaseEvaluateService(
			PurchaseEvaluateService purchaseEvaluateService) {
		this.purchaseEvaluateService = purchaseEvaluateService;
	}

	public PurchaseEvaluateService getPurchaseEvaluateService() {
		return purchaseEvaluateService;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptEvaluateList(List<PurchaseEvalDomain> deptEvaluateList) {
		this.deptEvaluateList = deptEvaluateList;
	}

	public List<PurchaseEvalDomain> getDeptEvaluateList() {
		return deptEvaluateList;
	}

	public void setApproveRecord(PensionPurchaseEvaluate approveRecord) {
		this.approveRecord = approveRecord;
	}

	public PensionPurchaseEvaluate getApproveRecord() {
		return approveRecord;
	}

}
