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

import service.receptionManage.AgentConfirmService;
import util.DateUtil;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.receptionManage.PensionAgentApply;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-1 下午01:10:44
 */
public class AgentConfirmController implements Serializable {

	private static final long serialVersionUID = 1L;
	private transient AgentConfirmService agentConfirmService;
	/**
	 * 查询条件
	 */
	private Long olderId;
	private String olderName;
	private Date startDate;
	private Date endDate;
	private Long applyId;
	/**
	 * 选中的代办服务记录
	 */
	private PensionAgentApply selectedRow;

	/**
	 * 所有申请记录列表
	 */
	private List<PensionAgentApply> agentList = new ArrayList<PensionAgentApply>();

	/**
	 * 确认对话框代办服务对象
	 */
	private PensionAgentApply agentConfirm = new PensionAgentApply();

	private PensionEmployee curUser;
	private String olderSQL;

	private String returnAmount;
	private String servetime;
	private Long itemPurseTypeId;

	@PostConstruct
	public void init() {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("olderId");
		String id = paramsMap.get("applyId");
		if (oldId != null) {
			olderId = Long.valueOf(oldId);
			applyId = Long.valueOf(id);
			olderName = agentConfirmService.selectOlderName(olderId);
		} else {
			olderId = null;
			applyId = null;
		}
		// 当前用户
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		initSql();
		startDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
		endDate = startDate;
		searchAllAgents();
		// 从消息进入时，默认选中某条代办记录
		if (olderId != null) {
			if (agentList.size() > 0 && agentList != null) {
				selectedRow = agentList.get(0);
			} else {
				selectedRow = null;
			}
		}
		try {
			itemPurseTypeId = agentConfirmService.selectItemPurseTypeId();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
		}
	}

	/**
	 * 初始化老人输入法
	 */
	public void initSql() {
		olderSQL = "select distinct pa.older_id,pa.older_name,po.inputCode from pension_agent_apply pa,"
				+ "pension_older po where pa.older_id=po.id and pa.cleared=2 and pa.agent_id="
				+ curUser.getId();
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		olderId = null;
		olderName = "";
		startDate = null;
		endDate = null;
	}

	/**
	 * 初始化老人代办服务列表
	 */
	public void searchAllAgents() {
		agentList = agentConfirmService.selectAgentList(olderId, startDate,
				endDate, curUser.getId(), applyId);
		selectedRow = null;
	}

	/**
	 * 查询老人代办评估记录
	 */
	public void searchAgents() {
		agentList = agentConfirmService.selectAgentList(olderId, startDate,
				endDate, curUser.getId(), null);
		selectedRow = null;
	}

	/**
	 * 选中一行时触发
	 * 
	 * @param event
	 */
	public void setEnableFlag(SelectEvent event) {

	}

	/**
	 * 检查是否弹出确认对话框
	 */
	public void checkAgent() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
			return;
		}
		if (selectedRow.getFinishFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该待办任务已确认", ""));
			return;
		}
		returnAmount = null;
		servetime = null;
		agentConfirm = selectedRow;
		requestContext.addCallbackParam("success", true);
	}

	/**
	 * 保存确认记录
	 */
	public void confirmAgent() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		try {
			Float moneyTemp = Float.valueOf(returnAmount);
			Float acturalServeTime = Float.valueOf(servetime);

			if (!(acturalServeTime.floatValue() > 0)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("实际服务时间 请输正值！", ""));
				requestContext.addCallbackParam("addSuccess", false);
				return;
			}
			if (moneyTemp > agentConfirm.getDebitAmount()) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"返还金额不能大于代收金额", ""));
				return;
			}
			agentConfirm.setFinishFlag(1);
			agentConfirm.setActualServetime(acturalServeTime);
			agentConfirm.setReturnAmount(moneyTemp);
			agentConfirmService.updateAgentApply(agentConfirm, curUser);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "确认成功", ""));
			requestContext.addCallbackParam("close", true);
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("代收金额和预计服务时间 只能输入数字！", "！"));
			requestContext.addCallbackParam("addSuccess", false);
		}
	}

	/**
	 * 费用录入
	 */
	public void temPay() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
			return;
		}
		if (selectedRow.getFinishFlag() == 2) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该代办服务尚未完成",
							""));
			return;
		}
		if (selectedRow.getPayFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该代办服务已录费", ""));
			return;
		}
		requestContext.addCallbackParam("older", selectedRow);
	}

	/**
	 * 不选中一行时触发
	 * 
	 * @param event
	 */
	public void setUnableFlag(UnselectEvent event) {
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

	public Long getPensionOlderId() {
		return olderId;
	}

	public void setPensionOlderId(Long pensionOlderId) {
		this.olderId = pensionOlderId;
	}

	public void setAgentConfirmService(AgentConfirmService agentConfirmService) {
		this.agentConfirmService = agentConfirmService;
	}

	public AgentConfirmService getAgentConfirmService() {
		return agentConfirmService;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setAgentList(List<PensionAgentApply> agentList) {
		this.agentList = agentList;
	}

	public List<PensionAgentApply> getAgentList() {
		return agentList;
	}

	public void setAgentConfirm(PensionAgentApply agentConfirm) {
		this.agentConfirm = agentConfirm;
	}

	public PensionAgentApply getAgentConfirm() {
		return agentConfirm;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public Long getOlderId() {
		return olderId;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getApplyId() {
		return applyId;
	}

	public String getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(String returnAmount) {
		this.returnAmount = returnAmount;
	}

	public String getServetime() {
		return servetime;
	}

	public void setServetime(String servetime) {
		this.servetime = servetime;
	}

	public void setItemPurseTypeId(Long itemPurseTypeId) {
		this.itemPurseTypeId = itemPurseTypeId;
	}

	public Long getItemPurseTypeId() {
		return itemPurseTypeId;
	}
}
