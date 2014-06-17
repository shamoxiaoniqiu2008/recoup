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

import service.receptionManage.LivingApplyService;
import service.receptionManage.LivingEvaluateService;
import service.receptionManage.PensionEvalDomain;
import service.receptionManage.PensionOlderDomain;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.dictionary.PensionDicNation;
import domain.dictionary.PensionDicPolitics;
import domain.employeeManage.PensionEmployee;
import domain.receptionManage.PensionApplyevaluate;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-09-01 下午04:39:44
 */
public class LivingEvaluateController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient LivingEvaluateService livingEvaluateService;
	private transient LivingApplyService livingApplyService;
	/**
	 * 查询条件
	 */
	private String personState;
	private String olderName;
	private Date startDate;
	private Date endDate;
	/**
	 * 所有申请记录列表
	 */
	private List<PensionOlderDomain> pensionOlderList = new ArrayList<PensionOlderDomain>();
	/**
	 * 民族列表
	 */
	private List<PensionDicNation> nationList;
	/**
	 * 政治面貌列表
	 */
	private List<PensionDicPolitics> politicList;
	/**
	 * 选中申请记录
	 */
	private PensionOlderDomain selectedRow;
	/**
	 * 评估和修改对话框申请评估记录
	 */
	private PensionOlderDomain evaPensionOlder = new PensionOlderDomain();
	/**
	 * 其他部门评估意见列表
	 */
	private List<PensionEvalDomain> deptEvaluateList = new ArrayList<PensionEvalDomain>();
	/**
	 * 操作标记 1 为新增 2 为修改
	 */
	private Short operationId;
	// 部门ID
	private Long deptId;
	// 按钮可用标识
	private boolean submitFlag;
	private PensionEmployee curUser;
	private String olderSQL;
	private Long pensionOlderId;

	@PostConstruct
	public void init() throws PmsException {

		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("olderId");
		if (oldId != null) {
			pensionOlderId = Long.valueOf(oldId);
		} else {
			pensionOlderId = null;
		}
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		deptId = curUser.getDeptId();
		searchAllApplications();
		if (pensionOlderId != null) {
			if (pensionOlderList.size() > 0 && pensionOlderList != null) {
				selectedRow = pensionOlderList.get(0);
				deptEvaluateList = livingEvaluateService
						.selectDeptEvaList(selectedRow.getApplyId());
			} else {
				selectedRow = null;
				deptEvaluateList = null;
			}
		}
		initNation();
		initPolitics();
		initSql();
	}

	/**
	 * 初始化输入法sql
	 */
	public void initSql() {

		olderSQL = "select po.id, po.name,po.inputCode from pension_older po,"
				+ "pension_livingapply pl,pension_applyevaluate pa where pl.older_id=po.id  "
				+ "and po.cleared=2 and pl.cleared=2 and pa.apply_id=pl.id and po.statuses in(1,2) and  pa.dept_id="
				+ deptId;
	}

	/**
	 * @Description: 初始化民族列表
	 */
	public void initNation() {
		nationList = livingApplyService.selectNationList();
	}

	/**
	 * @Description: 初始化政治面貌列表
	 */
	public void initPolitics() {
		setPoliticList(livingApplyService.selectPoliticList());
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		olderName = "";
		startDate = null;
		endDate = null;
		personState = "";
	}

	/**
	 * 查询出所有的老人申请记录
	 */
	public void searchAllApplications() {
		pensionOlderList = livingEvaluateService.selectEvaApplications(
				personState, pensionOlderId, startDate, endDate, deptId);
		deptEvaluateList = new ArrayList<PensionEvalDomain>();
		selectedRow = null;
	}

	/**
	 * 根据条件查询老人入住申请记录
	 */
	public void searchApplications() {
		if (olderName == null || olderName == "") {
			pensionOlderId = null;
		}
		pensionOlderList = livingEvaluateService.selectEvaApplications(
				personState, pensionOlderId, startDate, endDate, deptId);
		deptEvaluateList = new ArrayList<PensionEvalDomain>();
		selectedRow = null;
	}

	/**
	 * 保存评估意见
	 * 
	 * @throws PmsException
	 */
	public void saveEvaluation() throws PmsException {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (evaPensionOlder.getEvaluateresult() == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"请选择评估结果！", ""));
			request.addCallbackParam("hide", false);
		} else {
			PensionApplyevaluate evalution = new PensionApplyevaluate();
			evalution.setEvaluateresult(evaPensionOlder.getEvaluateresult());
			evalution.setEvaluatorId(curUser.getId().intValue());
			evalution.setNotes(evaPensionOlder.getEvaluateNotes());
			evalution.setId(selectedRow.getEvaluateId());
			if (operationId == 1) {
				String olderName = evaPensionOlder.getName();
				livingEvaluateService.updateEvaluate(evalution,
						evaPensionOlder.getId(), curUser.getId(), deptId,
						curUser.getId(), olderName);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"保存评估意见成功！", ""));

			} else {
				livingEvaluateService.updateEvaluate(evalution);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"修改评估意见成功！", ""));
			}
			pensionOlderList = livingEvaluateService.selectEvaApplications(
					personState, pensionOlderId, startDate, endDate, deptId);
			deptEvaluateList = livingEvaluateService
					.selectDeptEvaList(selectedRow.getApplyId());
			request.addCallbackParam("hide", true);
		}
	}

	/**
	 * 选中一行时，更新各部门意见列表
	 * 
	 * @param event
	 */
	public void selectDeptEvaluation(SelectEvent event) {
		deptEvaluateList = livingEvaluateService.selectDeptEvaList(selectedRow
				.getApplyId());
	}

	/**
	 * 不选中一行时，清空意见列表
	 * 
	 * @param event
	 */
	public void clearDeptEvaluation(UnselectEvent event) {
		deptEvaluateList = new ArrayList<PensionEvalDomain>();
	}

	/**
	 * 评估按钮触发事件
	 */
	public void selectPensionOlder() {

		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow != null) {
			if (selectedRow.getStatuses() != 1) {
				if (selectedRow.getFinalResult() == 1) {
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_INFO, "该入住申请已评估通过！", ""));
				} else if (selectedRow.getEvaluateresult() != null) {
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_INFO, "该入住申请已填写评估结果，只可修改！",
							""));
				} else {
					operationId = 1;
					submitFlag = false;
					evaPensionOlder = selectedRow;
					request.addCallbackParam("show", true);
				}
			} else if (selectedRow.getEvaluateresult() != null) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "该入住申请已填写评估结果，只可修改！", ""));
			} else {
				operationId = 1;
				submitFlag = false;
				evaPensionOlder = selectedRow;
				request.addCallbackParam("show", true);
			}
		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
			request.addCallbackParam("show", false);
		}
	}

	/**
	 * 修改按钮触发事件
	 */
	public void editPensionOlder() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow != null) {
			if (selectedRow.getStatuses() != 1
					&& selectedRow.getFinalResult() == 1) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "该入住申请已评估通过，不可修改！", ""));
			} else if (selectedRow.getEvaluateresult() == null) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "该入住申请未评估！", ""));
			} else {
				operationId = 2;
				submitFlag = false;
				evaPensionOlder = selectedRow;
				request.addCallbackParam("show", true);
			}
		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
			request.addCallbackParam("show", false);
		}
	}

	/**
	 * 查看按钮触发事件
	 */
	public void viewPensionOlder() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow != null) {
			if (selectedRow.getEvaluateresult() != null) {
				evaPensionOlder = selectedRow;
				submitFlag = true;
				request.addCallbackParam("show", true);
			} else {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "该入住申请尚未评估！", ""));
				request.addCallbackParam("show", false);
			}
		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
			request.addCallbackParam("show", false);
		}
	}

	public String getPersonState() {
		return personState;
	}

	public void setPersonState(String personState) {
		this.personState = personState;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
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

	public void setPensionOlderList(List<PensionOlderDomain> pensionOlderList) {
		this.pensionOlderList = pensionOlderList;
	}

	public List<PensionOlderDomain> getPensionOlderList() {
		return pensionOlderList;
	}

	public void setLivingEvaluateService(
			LivingEvaluateService livingEvaluateService) {
		this.livingEvaluateService = livingEvaluateService;
	}

	public LivingEvaluateService getLivingEvaluateService() {
		return livingEvaluateService;
	}

	public void setLivingApplyService(LivingApplyService livingApplyService) {
		this.livingApplyService = livingApplyService;
	}

	public LivingApplyService getLivingApplyService() {
		return livingApplyService;
	}

	public void setSelectedRow(PensionOlderDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionOlderDomain getSelectedRow() {
		return selectedRow;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setEvaPensionOlder(PensionOlderDomain evaPensionOlder) {
		this.evaPensionOlder = evaPensionOlder;
	}

	public PensionOlderDomain getEvaPensionOlder() {
		return evaPensionOlder;
	}

	public void setNationList(List<PensionDicNation> nationList) {
		this.nationList = nationList;
	}

	public List<PensionDicNation> getNationList() {
		return nationList;
	}

	public void setPoliticList(List<PensionDicPolitics> politicList) {
		this.politicList = politicList;
	}

	public List<PensionDicPolitics> getPoliticList() {
		return politicList;
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

	public void setOperationId(Short operationId) {
		this.operationId = operationId;
	}

	public Short getOperationId() {
		return operationId;
	}

	public void setDeptEvaluateList(List<PensionEvalDomain> deptEvaluateList) {
		this.deptEvaluateList = deptEvaluateList;
	}

	public List<PensionEvalDomain> getDeptEvaluateList() {
		return deptEvaluateList;
	}

	public void setSubmitFlag(boolean submitFlag) {
		this.submitFlag = submitFlag;
	}

	public boolean getSubmitFlag() {
		return submitFlag;
	}

	public Long getPensionOlderId() {
		return pensionOlderId;
	}

	public void setPensionOlderId(Long pensionOlderId) {
		this.pensionOlderId = pensionOlderId;
	}
}
