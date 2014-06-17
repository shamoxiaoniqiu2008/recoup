package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import service.olderManage.DrugReceiveDetailDomain;
import service.olderManage.DrugReceiveDomain;
import service.olderManage.DrugReceiveEvaluateService;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-14 上午8:30:44
 */

public class DrugReceiveEvaluateController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient DrugReceiveEvaluateService drugReceiveEvaluateService;
	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;
	private String isAudit;
	private String auditResult;
	private Long receiveRecordId;
	/**
	 * 选中的药物接收申请记录
	 */
	private DrugReceiveDomain selectedRow;
	/**
	 * 药物接收申请列表
	 */
	private List<DrugReceiveDomain> drugApplyList = new ArrayList<DrugReceiveDomain>();

	/**
	 * 药物接收明细集合
	 */
	private List<DrugReceiveDetailDomain> drugDetailList;

	private PensionEmployee curUser;
	private Long deptId;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("olderId");
		String recordId = paramsMap.get("recordId");
		if (oldId != null) {
			olderId = Long.valueOf(oldId);
			olderName = drugReceiveEvaluateService.selectOlderName(olderId);
			receiveRecordId = Long.valueOf(recordId);
		} else {
			olderId = null;
			olderName = "";
			receiveRecordId = null;
		}
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		deptId = curUser.getDeptId();
		searchAllApplications();
		if (olderId != null) {
			if (drugApplyList.size() > 0 && drugApplyList != null) {
				selectedRow = drugApplyList.get(0);
				viewDetailList();
			} else {
				selectedRow = null;
			}
		}
	}

	/**
	 * 查询药物接收申请列表
	 */
	public void searchAllApplications() {
		if (olderId == null) {
			receiveRecordId = null;
		}
		drugApplyList = drugReceiveEvaluateService.selectApplications(olderId,
				isAudit, auditResult, receiveRecordId);
		selectedRow = null;
		drugDetailList = new ArrayList<DrugReceiveDetailDomain>();
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearch() {
		olderId = null;
		olderName = "";
		isAudit = null;
		auditResult = null;
	}

	/**
	 * 选中主记录时，查询明细
	 */
	public void viewDetailList() {
		drugDetailList = drugReceiveEvaluateService
				.selectDrugDetails(selectedRow.getId());
	}

	/**
	 * 清空明细
	 */
	public void clearDetailList() {
		drugDetailList = new ArrayList<DrugReceiveDetailDomain>();
	}

	/**
	 * 审核通过
	 */
	public void passApply() {
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
		} else if (selectedRow.getAuditFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该申请已审核！", ""));
		} else {
			selectedRow.setAuditFlag(1);
			selectedRow.setAuditResult(1);
			selectedRow.setAuditName(curUser.getName());
			selectedRow.setAuditorId(curUser.getId());
			selectedRow.setAuditTime(new Date());
			drugReceiveEvaluateService.updateDrugApply(selectedRow, deptId);
			drugApplyList = drugReceiveEvaluateService.selectApplications(
					olderId, isAudit, auditResult, receiveRecordId);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "审核通过完成", ""));
		}
	}

	/**
	 * 审核不通过
	 */
	public void refuseApply() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		selectedRow.setAuditFlag(1);
		selectedRow.setAuditResult(2);
		selectedRow.setAuditName(curUser.getName());
		selectedRow.setAuditorId(curUser.getId());
		selectedRow.setAuditTime(new Date());
		drugReceiveEvaluateService.updateDrugApply(selectedRow, deptId);
		drugApplyList = drugReceiveEvaluateService.selectApplications(olderId,
				isAudit, auditResult, receiveRecordId);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "审核不通过已完成", ""));
		requestContext.addCallbackParam("success", true);
	}

	/**
	 * 不同意触发
	 */
	public void selectCheck() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条记录！",
							""));
			return;
		}
		if (selectedRow.getAuditFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该申请已审核！", ""));
			return;
		}
		requestContext.addCallbackParam("show", true);
		selectedRow.setNote("");
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDrugReceiveEvaluateService(
			DrugReceiveEvaluateService drugReceiveEvaluateService) {
		this.drugReceiveEvaluateService = drugReceiveEvaluateService;
	}

	public DrugReceiveEvaluateService getDrugReceiveEvaluateService() {
		return drugReceiveEvaluateService;
	}

	public void setDrugDetailList(List<DrugReceiveDetailDomain> drugDetailList) {
		this.drugDetailList = drugDetailList;
	}

	public List<DrugReceiveDetailDomain> getDrugDetailList() {
		return drugDetailList;
	}

	public void setDrugApplyList(List<DrugReceiveDomain> drugApplyList) {
		this.drugApplyList = drugApplyList;
	}

	public List<DrugReceiveDomain> getDrugApplyList() {
		return drugApplyList;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public void setSelectedRow(DrugReceiveDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public DrugReceiveDomain getSelectedRow() {
		return selectedRow;
	}

	public void setReceiveRecordId(Long receiveRecordId) {
		this.receiveRecordId = receiveRecordId;
	}

	public Long getReceiveRecordId() {
		return receiveRecordId;
	}

}
