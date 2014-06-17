package controller.receptionManage;

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

import service.receptionManage.AgentApplyDomain;
import service.receptionManage.AgentApplyService;
import util.DateUtil;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-10-30 上午08:16:44
 */

public class AgentApplyController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient AgentApplyService agentApplyService;
	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;
	private String isAudit;
	private String auditResult;
	private String isComplete;
	private Long applyId;
	private Date startDate;
	private Date endDate;
	/**
	 * 输入法sql
	 */
	private String olderSql;
	private String agentSql;
	/**
	 * 选中的代办服务申请记录
	 */
	private AgentApplyDomain selectedRow;
	/**
	 * 代办服务申请列表
	 */
	private List<AgentApplyDomain> agentApplyList = new ArrayList<AgentApplyDomain>();
	/**
	 * 新增代办服务对象
	 */
	private AgentApplyDomain addPensionAgent;
	/**
	 * 编辑代办服务对象
	 */
	private AgentApplyDomain editPensionAgent = new AgentApplyDomain();
	private String debitAmount;
	private String expectServetime;
	private int saveFlag;
	/**
	 * 默认查询时长（月）
	 */
	private int monthNum;
	/**
	 * 当前用户
	 */
	private PensionEmployee curUser;
	/**
	 * 评价显示标识
	 */
	private boolean evaluateFlag;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("olderId");
		String id = paramsMap.get("applyId");
		if (oldId != null) {
			olderId = Long.valueOf(oldId);
			olderName = agentApplyService.selectOlderName(olderId);
			applyId = Long.valueOf(id);
			startDate = null;
			endDate = null;
		} else {
			olderId = null;
			applyId = null;
			monthNum = agentApplyService.selectMonthNum();
			endDate = new Date();
			startDate = DateUtil.getBeforeMonthDay(endDate, monthNum);
		}
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		initSql();
		searchAllApplications();
		// 从消息进入时，默认选中某条代办记录
		if (olderId != null) {
			if (agentApplyList.size() > 0 && agentApplyList != null) {
				selectedRow = agentApplyList.get(0);
			} else {
				selectedRow = null;
			}
		}
		evaluateFlag = agentApplyService.selectEvaluateFlag();
	}

	/**
	 * 初始化老人输入法
	 */
	public void initSql() {
		olderSql = "select distinct pa.older_id,pa.older_name,po.inputCode from pension_agent_apply pa,"
				+ "pension_older po where pa.older_id=po.id and pa.cleared=2";
		agentSql = "select pe.id,pe.name,pe.inputCode from pension_employee pe where pe.cleared=2";
	}

	/**
	 * 初始化代办服务列表
	 */
	public void searchAllApplications() {
		agentApplyList = agentApplyService.selectApplications(olderId, isAudit,
				auditResult, isComplete, applyId, startDate, endDate);
		selectedRow = null;
	}

	/**
	 * 根据条件查询代办服务列表
	 */
	public void searchApplications() {
		agentApplyList = agentApplyService.selectApplications(olderId, isAudit,
				auditResult, isComplete, null, startDate, endDate);
		selectedRow = null;
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearch() {
		olderId = null;
		olderName = "";
		isAudit = null;
		isComplete = null;
		auditResult = null;
		startDate = null;
		endDate = null;
	}

	/**
	 * 点击修改按钮触发
	 */
	public void showEditForm() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
			requestContext.addCallbackParam("edit", false);
		} else if (selectedRow.getSendFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该申请已提交，不可更改",
							""));
			requestContext.addCallbackParam("edit", false);
		} else {
			editPensionAgent = selectedRow;
			debitAmount = editPensionAgent.getDebitAmount().toString();
			expectServetime = editPensionAgent.getExpectServetime().toString();
			requestContext.addCallbackParam("edit", true);
		}
	}

	/**
	 * 点击删除按钮触发
	 */
	public void showDelForm() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
		} else if (selectedRow.getSendFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该申请已提交，不可删除",
							""));
		} else {
			requestContext.addCallbackParam("del", true);
		}
	}

	/**
	 * 点击登记按钮触发
	 */
	public void showRegist() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
		} else if (selectedRow.getVerifyFlag() == 2
				|| selectedRow.getVerifyResult() == 2
				|| selectedRow.getAgentId() != null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"该申请未审核或审核未通过或已登记代办人，不可登记", ""));
		} else {
			editPensionAgent = selectedRow;
			requestContext.addCallbackParam("regist", true);
		}
	}

	/**
	 * 删除一条代办服务申请
	 */
	public void deleteApplication() {
		selectedRow.setCleared(1);
		saveFlag = 7;
		agentApplyService.updateAgentApply(selectedRow, saveFlag);
		searchApplications();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "删除成功", ""));
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

	/**
	 * 保存一条代办服务申请并发送通知
	 * 
	 * @throws PmsException
	 */
	public void saveAndSendApplication() throws PmsException {
		saveFlag = 1;
		insertApplication(saveFlag);
	}

	/**
	 * 保存一条代办服务申请
	 * 
	 * @throws PmsException
	 */
	public void saveApplication() throws PmsException {
		saveFlag = 2;
		insertApplication(saveFlag);
	}

	public void insertApplication(int saveFlag) throws PmsException {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		try {
			if (addPensionAgent.getServiceTime().before(new Date())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"服务日期不能小于当前日期", ""));
				requestContext.addCallbackParam("addSuccess", false);
				return;
			}
			Float moneyTemp = Float.valueOf(debitAmount);
			Float serveTime = Float.valueOf(expectServetime);

			if (!(moneyTemp.floatValue() > 0)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("代收金额 请输正值！", ""));
				requestContext.addCallbackParam("addSuccess", false);
				return;
			}
			if (!(serveTime.floatValue() > 0)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("预计服务时间 请输正值！", ""));
				requestContext.addCallbackParam("addSuccess", false);
				return;
			}
			addPensionAgent.setApplyTime(new Date());
			addPensionAgent.setVerifyFlag(2);
			addPensionAgent.setFinishFlag(2);
			addPensionAgent.setCleared(2);
			addPensionAgent.setExpectServetime(serveTime);
			addPensionAgent.setDebitAmount(moneyTemp);
			agentApplyService.insertAgentApply(addPensionAgent, saveFlag);
			agentApplyList = agentApplyService.selectApplications(olderId,
					isAudit, auditResult, isComplete, applyId, startDate,
					endDate);
			selectedRow = addPensionAgent;
			requestContext.addCallbackParam("addSuccess", true);
			if (saveFlag == 1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"新增申请并提交成功", ""));
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "新增申请成功",
								""));
			}
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("代收金额和预计服务时间 只能输入数字！", "！"));
			requestContext.addCallbackParam("addSuccess", false);
		}
	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		addPensionAgent = new AgentApplyDomain();
		debitAmount = null;
		expectServetime = null;
	}

	/**
	 * 修改一条代办服务申请
	 */
	public void saveEditApplication() {
		saveFlag = 4;
		updateEditApplication();
	}

	public void updateEditApplication() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		try {
			if (editPensionAgent.getServiceTime().before(new Date())) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "服务日期不能小于当前时间", ""));
				requestContext.addCallbackParam("editSuccess", false);
				return;
			}
			Float moneyTemp = Float.valueOf(debitAmount);
			Float serveTime = Float.valueOf(expectServetime);

			if (!(moneyTemp.floatValue() > 0)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("代收金额 请输正值！", ""));
				requestContext.addCallbackParam("addSuccess", false);
				return;
			}
			if (!(serveTime.floatValue() > 0)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("预计服务时间 请输正值！", ""));
				requestContext.addCallbackParam("addSuccess", false);
				return;
			}
			editPensionAgent.setExpectServetime(serveTime);
			editPensionAgent.setDebitAmount(moneyTemp);
			agentApplyService.updateAgentApply(editPensionAgent, saveFlag);
			agentApplyList = agentApplyService.selectApplications(olderId,
					isAudit, auditResult, isComplete, applyId, startDate,
					endDate);
			if (saveFlag == 3) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "代办服务修改并提交成功", ""));
			} else {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "代办服务修改成功", ""));
			}
			requestContext.addCallbackParam("editSuccess", true);
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("代收金额和预计服务时间 只能输入数字！", "！"));
			requestContext.addCallbackParam("addSuccess", false);
		}

	}

	/**
	 * 修改一条代办服务申请并发送通知
	 */
	public void saveAndSendEditApplication() {
		saveFlag = 3;
		updateEditApplication();
	}

	/**
	 * 发送通知
	 */
	public void sentMessage() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
			return;
		}
		if (selectedRow.getSendFlag() == 1) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "该申请已提交！", ""));
			return;
		}
		try {
			selectedRow.setSendFlag(1);
			agentApplyService.sentMessage(selectedRow);
			agentApplyList = agentApplyService.selectApplications(olderId,
					isAudit, auditResult, isComplete, applyId, startDate,
					endDate);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "发送消息成功", ""));
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		}
	}

	/**
	 * 登记代办人
	 */
	public void saveRegister() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		agentApplyService.agentRegist(editPensionAgent, curUser);
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"登记代办人并发送消息成功", ""));
		requestContext.addCallbackParam("registSuccess", true);
	}

	/**
	 * 保存老人评价
	 * 
	 * @throws PmsException
	 */
	public void saveEvaluation() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		agentApplyService.savePensionQuality(editPensionAgent);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "老人评价成功", ""));
		requestContext.addCallbackParam("evaluateSuccess", true);
	}

	/**
	 * 点击评价按钮触发
	 */
	public void showEvaluate() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
		} else if (selectedRow.getFinishFlag() == 2) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "该服务尚未完成，不允许评价！", ""));
		} else if (selectedRow.getEvaluation() != null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"该待办任务已评价", ""));
			return;
		} else {
			editPensionAgent = selectedRow;
			requestContext.addCallbackParam("evaluate", true);
		}
	}

	public AgentApplyDomain getAddPensionAgent() {
		return addPensionAgent;
	}

	public void setAddPensionAgent(AgentApplyDomain addPensionAgent) {
		this.addPensionAgent = addPensionAgent;
	}

	public AgentApplyDomain getEditPensionAgent() {
		return editPensionAgent;
	}

	public void setEditPensionAgent(AgentApplyDomain editPensionAgent) {
		this.editPensionAgent = editPensionAgent;
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

	public String getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(String isComplete) {
		this.isComplete = isComplete;
	}

	public AgentApplyDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(AgentApplyDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public List<AgentApplyDomain> getAgentApplyList() {
		return agentApplyList;
	}

	public void setAgentApplyList(List<AgentApplyDomain> agentApplyList) {
		this.agentApplyList = agentApplyList;
	}

	public AgentApplyService getAgentApplyService() {
		return agentApplyService;
	}

	public void setAgentApplyService(AgentApplyService agentApplyService) {
		this.agentApplyService = agentApplyService;
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

	public void setOlderSql(String olderSql) {
		this.olderSql = olderSql;
	}

	public String getOlderSql() {
		return olderSql;
	}

	public void setAgentSql(String agentSql) {
		this.agentSql = agentSql;
	}

	public String getAgentSql() {
		return agentSql;
	}

	public String getDebitAmount() {
		return debitAmount;
	}

	public void setDebitAmount(String debitAmount) {
		this.debitAmount = debitAmount;
	}

	public String getExpectServetime() {
		return expectServetime;
	}

	public void setExpectServetime(String expectServetime) {
		this.expectServetime = expectServetime;
	}

	public void setSaveFlag(int saveFlag) {
		this.saveFlag = saveFlag;
	}

	public int getSaveFlag() {
		return saveFlag;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getApplyId() {
		return applyId;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setMonthNum(int monthNum) {
		this.monthNum = monthNum;
	}

	public int getMonthNum() {
		return monthNum;
	}

	public void setEvaluateFlag(boolean evaluateFlag) {
		this.evaluateFlag = evaluateFlag;
	}

	public boolean isEvaluateFlag() {
		return evaluateFlag;
	}

}
