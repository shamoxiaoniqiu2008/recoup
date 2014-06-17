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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.olderManage.InfusionApplyDomain;
import service.olderManage.InfusionEvaluateService;
import util.DateUtil;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.receptionManage.PensionAgentApprove;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-5 上午10:40:44
 */

public class InfusionEvaluateController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient InfusionEvaluateService infusionEvaluateService;
	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;
	private String isAudit;
	private String auditResult;
	private Date startDate;
	private Date endDate;

	/**
	 * 选中的输液申请记录
	 */
	private InfusionApplyDomain selectedRow;
	/**
	 * 输液申请列表
	 */
	private List<InfusionApplyDomain> infusionApplyList = new ArrayList<InfusionApplyDomain>();
	private PensionEmployee curUser;
	private Long deptId;
	private Long applyId;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("olderId");
		String applyRecordId = paramsMap.get("applyId");
		if (oldId != null) {
			olderId = Long.valueOf(oldId);
			olderName = infusionEvaluateService.selectOlderName(olderId);
			applyId = Long.valueOf(applyRecordId);
		} else {
			olderId = null;
			olderName = "";
			applyId = null;
		}
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		deptId = curUser.getDeptId();
		startDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
		endDate = DateUtil.getBeforeDay(startDate, -7);
		isAudit = "2";
		searchAllApplications();
		if (olderId != null) {
			if (infusionApplyList.size() > 0 && infusionApplyList != null) {
				selectedRow = infusionApplyList.get(0);
			} else {
				selectedRow = null;
			}
		}
	}

	/**
	 * 查询输液申请列表
	 */
	public void searchAllApplications() {
		if (olderId == null) {
			applyId = null;
		}
		infusionApplyList = infusionEvaluateService.selectApplyList(olderId,
				isAudit, auditResult, applyId, startDate, endDate);
		selectedRow = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "查询完成", ""));
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearch() {
		olderId = null;
		olderName = "";
		isAudit = null;
		auditResult = null;
		applyId = null;
		startDate = null;
		endDate = null;
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
		} else if (selectedRow.getVerifyFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该申请已审核，不可更改",
							""));
		} else {
			selectedRow.setVerifierId(curUser.getId());
			selectedRow.setVerifyFlag(1);
			selectedRow.setVerifierName(curUser.getName());
			selectedRow.setVerifyResult(1);
			selectedRow.setVerifyTime(new Date());
			infusionEvaluateService.updateInfusionApply(selectedRow, deptId);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "审核通过完成", ""));
		}
	}

	/**
	 * 审核不通过
	 */
	public void refuseApply() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		selectedRow.setVerifierId(curUser.getId());
		selectedRow.setVerifyFlag(1);
		selectedRow.setVerifierName(curUser.getName());
		selectedRow.setVerifyResult(2);
		selectedRow.setVerifyTime(new Date());
		infusionEvaluateService.updateInfusionApply(selectedRow, deptId);
		requestContext.addCallbackParam("success", true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "审核不通过已完成", ""));
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
		if (selectedRow.getVerifyFlag() == 1) {
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

	/**
	 * 选中一行时触发
	 * 
	 * @return
	 */
	public void setEnableFlag(SelectEvent e) {

	}

	/**
	 * 未选中一行时触发
	 * 
	 * @return
	 */
	public void setUnableFlag(UnselectEvent e) {

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

	public void setInfusionApplyList(List<InfusionApplyDomain> infusionApplyList) {
		this.infusionApplyList = infusionApplyList;
	}

	public List<InfusionApplyDomain> getInfusionApplyList() {
		return infusionApplyList;
	}

	public InfusionApplyDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(InfusionApplyDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setInfusionEvaluateService(
			InfusionEvaluateService infusionEvaluateService) {
		this.infusionEvaluateService = infusionEvaluateService;
	}

	public InfusionEvaluateService getInfusionEvaluateService() {
		return infusionEvaluateService;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getApplyId() {
		return applyId;
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

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

}
