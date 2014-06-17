package controller.receptionManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.receptionManage.AgentApplyService;
import util.DateUtil;
import util.PmsException;

import com.centling.his.util.SessionManager;
import org.primefaces.context.RequestContext;

import domain.employeeManage.PensionEmployee;
import domain.receptionManage.PensionAgentApply;
import domain.receptionManage.PensionAgentApprove;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-10-31 下午01:35:44
 */
public class AgentEvaluateController implements Serializable {

	private static final long serialVersionUID = 1L;
	private transient AgentApplyService agentApplyService;
	/**
	 * 查询条件
	 */
	private Long pensionOlderId;
	private String olderName;
	private String verifyFlag;
	private String verifyResult;
	private Long applyId;
	private Date startDate;
	private Date endDate;
	/**
	 * 选中申请记录
	 */
	private PensionAgentApply selectedRow;
	/**
	 * 所有申请记录列表
	 */
	private List<PensionAgentApply> agentEvaluationList = new ArrayList<PensionAgentApply>();

	/**
	 * 评估和修改对话框申请评估记录
	 */
	private PensionAgentApply evaAgentApply = new PensionAgentApply();
	/**
	 * 其他部门评估意见列表
	 */
	private List<PensionAgentApprove> deptEvaluateList = new ArrayList<PensionAgentApprove>();
	// 部门ID
	private Long deptId;
	private PensionEmployee curUser;
	private String olderSQL;
	/**
	 * 默认查询时长（月）
	 */
	private int monthNum;
	/**
	 * 发送代办服务评估部门列表
	 */

	private List<Long> deptList = new ArrayList<Long>();

	private PensionAgentApprove approveRecord = new PensionAgentApprove();

	@PostConstruct
	public void init() {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("olderId");
		String id = paramsMap.get("applyId");
		if (oldId != null) {
			pensionOlderId = Long.valueOf(oldId);
			olderName = agentApplyService.selectOlderName(pensionOlderId);
			applyId = Long.valueOf(id);
			startDate = null;
			endDate = null;
		} else {
			pensionOlderId = null;
			applyId = null;
			monthNum = agentApplyService.selectMonthNum();
			endDate = new Date();
			startDate = DateUtil.getBeforeMonthDay(endDate, monthNum);
		}
		initSql();
		// 当前用户及部门
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		deptId = curUser.getDeptId();

		searchAllEvaluations();

		// 从消息进入时，默认选中某条代办记录
		if (pensionOlderId != null) {
			if (agentEvaluationList.size() > 0 && agentEvaluationList != null) {
				selectedRow = agentEvaluationList.get(0);
				deptEvaluateList = agentApplyService
						.selectDeptEvaluations(selectedRow.getId());
			} else {
				selectedRow = null;
				deptEvaluateList = null;
			}
		}
		initDept();
	}

	/**
	 * 查询代办申请评估部门
	 * 
	 * @throws PmsException
	 */
	public void initDept() {
		try {
			deptList = agentApplyService.selectDeptIdList();
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 初始化输入法sql
	 */
	public void initSql() {
		olderSQL = "select po.id,po.name,po.inputCode from pension_older po where po.statuses in(3,4)";
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		pensionOlderId = null;
		olderName = "";
		verifyFlag = null;
		verifyResult = null;
		startDate = null;
		endDate = null;
	}

	/**
	 * 初始化查询老人代办评估记录
	 */
	public void searchAllEvaluations() {
		agentEvaluationList = agentApplyService.selectEvaluations(
				pensionOlderId, verifyFlag, verifyResult, applyId, startDate,
				endDate);
		deptEvaluateList = new ArrayList<PensionAgentApprove>();
		selectedRow = null;
	}

	/**
	 * 根据条件查询老人代办评估记录
	 */
	public void searchEvaluations() {
		agentEvaluationList = agentApplyService.selectEvaluations(
				pensionOlderId, verifyFlag, verifyResult, null, startDate,
				endDate);
		deptEvaluateList = new ArrayList<PensionAgentApprove>();
		selectedRow = null;
	}

	/**
	 * 选中一行时，更新各部门意见列表
	 * 
	 * @param event
	 */
	public void selectDeptEvaluation(SelectEvent event) {
		deptEvaluateList = agentApplyService.selectDeptEvaluations(selectedRow
				.getId());
	}

	/**
	 * 不选中一行时，清空意见列表
	 * 
	 * @param event
	 */
	public void clearDeptEvaluation(UnselectEvent event) {
		deptEvaluateList = new ArrayList<PensionAgentApprove>();
	}

	/**
	 * 审核一条代办申请通过
	 */
	public void evaluateAgent() {
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
		// 封装审核对象
		approveRecord = new PensionAgentApprove();
		approveRecord.setVerifierName(curUser.getName());
		approveRecord.setVerifyTime(new Date());
		approveRecord.setVerifyFlag(1);
		approveRecord.setVerifyResult(1);
		approveRecord.setVerifierId(curUser.getId());
		approveRecord.setApplyId(selectedRow.getId());
		approveRecord.setDepartId(deptId);
		if (deptList.get(deptList.size() - 1).equals(deptId)) {
			// 封装申请对象
			PensionAgentApply apply = new PensionAgentApply();
			apply.setId(selectedRow.getId());
			apply.setOlderName(selectedRow.getOlderName());
			apply.setOlderId(selectedRow.getOlderId());
			apply.setVerifyFlag(1);
			apply.setVerifyResult(1);
			agentApplyService.updateAgentEvaluate(apply, approveRecord);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"代办服务审核完成通知已发送！", ""));
		} else {
			Long applyId = selectedRow.getId();
			agentApplyService.updateAgentEvaluate(approveRecord, applyId);
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "审核成功！", ""));
		agentEvaluationList = agentApplyService.selectEvaluations(
				pensionOlderId, verifyFlag, verifyResult, selectedRow.getId(),
				startDate, endDate);
		deptEvaluateList = agentApplyService.selectDeptEvaluations(selectedRow
				.getId());
	}

	/**
	 * 审核一条代办申请不通过
	 */
	public void unEvaluateAgent() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		// 封装审核对象
		approveRecord.setVerifierName(curUser.getName());
		approveRecord.setVerifyTime(new Date());
		approveRecord.setVerifyFlag(1);
		approveRecord.setVerifyResult(2);
		approveRecord.setVerifierId(curUser.getId());
		approveRecord.setApplyId(selectedRow.getId());
		approveRecord.setDepartId(deptId);
		if (deptList.get(deptList.size() - 1).equals(deptId)) {
			// 封装申请对象
			PensionAgentApply apply = new PensionAgentApply();
			apply.setId(selectedRow.getId());
			apply.setVerifyFlag(1);
			apply.setVerifyResult(2);
			apply.setOlderName(selectedRow.getOlderName());
			apply.setOlderId(selectedRow.getOlderId());
			agentApplyService.updateAgentEvaluate(apply, approveRecord);
		} else {
			Long applyId = selectedRow.getId();
			agentApplyService.updateAgentEvaluate(approveRecord, applyId);
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "审核成功！", ""));
		requestContext.addCallbackParam("success", true);
		agentEvaluationList = agentApplyService.selectEvaluations(
				pensionOlderId, verifyFlag, verifyResult, selectedRow.getId(),
				startDate, endDate);
		deptEvaluateList = agentApplyService.selectDeptEvaluations(selectedRow
				.getId());
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
		approveRecord = new PensionAgentApprove();
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public void setSelectedRow(PensionAgentApply selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionAgentApply getSelectedRow() {
		return selectedRow;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public void setOlderSQL(String olderSQL) {
		this.olderSQL = olderSQL;
	}

	public String getOlderSQL() {
		return olderSQL;
	}

	public void setDeptEvaluateList(List<PensionAgentApprove> deptEvaluateList) {
		this.deptEvaluateList = deptEvaluateList;
	}

	public List<PensionAgentApprove> getDeptEvaluateList() {
		return deptEvaluateList;
	}

	public void setDeptList(List<Long> deptList) {
		this.deptList = deptList;
	}

	public List<Long> getDeptList() {
		return deptList;
	}

	public Long getPensionOlderId() {
		return pensionOlderId;
	}

	public void setPensionOlderId(Long pensionOlderId) {
		this.pensionOlderId = pensionOlderId;
	}

	public void setAgentApplyService(AgentApplyService agentApplyService) {
		this.agentApplyService = agentApplyService;
	}

	public AgentApplyService getAgentApplyService() {
		return agentApplyService;
	}

	public String getVerifyFlag() {
		return verifyFlag;
	}

	public void setVerifyFlag(String verifyFlag) {
		this.verifyFlag = verifyFlag;
	}

	public String getVerifyResult() {
		return verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	public void setAgentEvaluationList(
			List<PensionAgentApply> agentEvaluationList) {
		this.agentEvaluationList = agentEvaluationList;
	}

	public List<PensionAgentApply> getAgentEvaluationList() {
		return agentEvaluationList;
	}

	public void setEvaAgentApply(PensionAgentApply evaAgentApply) {
		this.evaAgentApply = evaAgentApply;
	}

	public PensionAgentApply getEvaAgentApply() {
		return evaAgentApply;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setApproveRecord(PensionAgentApprove approveRecord) {
		this.approveRecord = approveRecord;
	}

	public PensionAgentApprove getApproveRecord() {
		return approveRecord;
	}

}
