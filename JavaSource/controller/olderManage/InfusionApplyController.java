package controller.olderManage;

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

import com.centling.his.util.SessionManager;

import service.olderManage.InfusionApplyDomain;
import service.olderManage.InfusionApplyService;
import service.olderManage.InfusionRecordDomain;
import util.DateUtil;
import util.PmsException;
import domain.employeeManage.PensionEmployee;
import domain.medicalManage.PensionInfusionRecord;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-10-30 上午08:16:44
 */

public class InfusionApplyController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient InfusionApplyService infusionApplyService;
	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;
	private String isAudit;
	private Date startDate;
	private Date endDate;

	private String auditResult;
	/**
	 * 输入法sql
	 */
	private String olderSql;
	/**
	 * 选中的输液申请记录
	 */
	private InfusionApplyDomain selectedRow;
	/**
	 * 输液申请列表
	 */
	private List<InfusionApplyDomain> infusionApplyList = new ArrayList<InfusionApplyDomain>();
	/**
	 * 新增输液申请对象
	 */
	private InfusionApplyDomain addInfusionApply = new InfusionApplyDomain();
	/**
	 * 编辑代办服务对象
	 */
	private InfusionApplyDomain editInfusionApply = new InfusionApplyDomain();
	/**
	 * 主页面触发按钮标记
	 */
	private int operationFlag;
	private int saveFlag;
	private Date birthday;
	/**
	 * 输液记录列表
	 */
	private List<InfusionRecordDomain> infusionRecordList = new ArrayList<InfusionRecordDomain>();

	private PensionEmployee curUser;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		startDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
		endDate = DateUtil.getBeforeDay(startDate, -7);
		initSql();
		searchApplications();
	}

	/**
	 * 初始化老人输入法
	 */
	public void initSql() {
		olderSql = "select po.id,po.name,po.inputCode from pension_older po where po.statuses not in(1,2)";
	}

	/**
	 * 查询输液申请列表
	 */
	public void searchApplications() {
		infusionApplyList = infusionApplyService.selectApplyList(olderId,
				isAudit, auditResult, startDate, endDate);
		selectedRow = null;
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearch() {
		olderId = null;
		olderName = "";
		isAudit = null;
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
		} else if (selectedRow.getSendFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该申请已提交，不可更改",
							""));
		} else {
			if (selectedRow.getInfusionFlag() == 1) {
				selectedRow.setInfusion(true);
			} else {
				selectedRow.setInfusion(false);
			}
			if (selectedRow.getHospitalproofFlag() == 1) {
				selectedRow.setProof(true);
			} else {
				selectedRow.setProof(false);
			}
			if (selectedRow.getHospitalSeal() == 1) {
				selectedRow.setSealFlag(true);
			} else {
				selectedRow.setSealFlag(false);
			}
			operationFlag = 2;
			editInfusionApply = selectedRow;
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
	 * 删除一条输液申请
	 */
	public void deleteApplication() {
		selectedRow.setCleared(1);
		infusionApplyService.updateInfusionApply(selectedRow);
		searchApplications();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "删除成功", ""));
		infusionApplyList = infusionApplyService.selectApplyList(olderId,
				isAudit, auditResult, startDate, endDate);
	}

	/**
	 * 查询输液记录
	 * 
	 * @return
	 */
	public void selectInfusionRecords(InfusionApplyDomain selectedRow) {
		infusionRecordList = infusionApplyService
				.selectInfusionRecords(selectedRow.getId());
	}

	/**
	 * 选中一行时触发
	 * 
	 * @return
	 */
	public void SetEnableFlag(SelectEvent e) {
		infusionRecordList = infusionApplyService
				.selectInfusionRecords(selectedRow.getId());
	}

	/**
	 * 未选中一行时触发
	 * 
	 * @return
	 */
	public void clearInfusionRecord(UnselectEvent e) {
		infusionRecordList = new ArrayList<InfusionRecordDomain>();
	}

	/**
	 * 保存一条输液申请并发送通知
	 * 
	 * @throws PmsException
	 */
	public void saveAndSendApplication() throws PmsException {
		saveFlag = 1;
		insertApplication(saveFlag);
	}

	/**
	 * 保存一条输液申请
	 * 
	 * @throws PmsException
	 */
	public void saveApplication() throws PmsException {
		saveFlag = 2;
		insertApplication(saveFlag);
	}

	public void insertApplication(int saveFlag) throws PmsException {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (addInfusionApply.getEndTime().before(
				addInfusionApply.getStartTime())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"结束日期不能小于开始日期", ""));
			requestContext.addCallbackParam("addSuccess", false);
		} else {
			if (addInfusionApply.getInfusion()) {
				addInfusionApply.setInfusionFlag(1);
			} else {
				addInfusionApply.setInfusionFlag(2);
			}
			if (addInfusionApply.getProof()) {
				addInfusionApply.setHospitalproofFlag(1);
			} else {
				addInfusionApply.setHospitalproofFlag(2);
			}
			if (addInfusionApply.isSealFlag()) {
				addInfusionApply.setHospitalSeal(1);
			} else {
				addInfusionApply.setHospitalSeal(2);
			}
			addInfusionApply.setExecuteNumbers(0);
			addInfusionApply.setCleared(2);
			addInfusionApply.setVerifyFlag(2);
			addInfusionApply.setGenerateFlag(2);
			addInfusionApply.setFamilySign(2);
			addInfusionApply.setApplyId(curUser.getId());
			addInfusionApply.setApplyName(curUser.getName());
			infusionApplyService
					.insertInfusionApply(addInfusionApply, saveFlag);
			infusionApplyList = infusionApplyService.selectApplyList(olderId,
					isAudit, auditResult, startDate, endDate);
			selectedRow = addInfusionApply;
			requestContext.addCallbackParam("addSuccess", true);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "新增申请成功", ""));
		}
	}

	/**
	 * 根据开始日期和结束日期获取持续时间
	 */
	public void selectLastTime() {
		if (operationFlag == 1) {
			Date startDate = addInfusionApply.getStartTime();
			Date endDate = addInfusionApply.getEndTime();
			if (endDate != null && startDate != null) {
				if (endDate.before(startDate)) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"结束日期不能小于开始日期", ""));
					return;
				}
				addInfusionApply.setLastTime(util.DateUtil.diffDate(endDate,
						startDate) + 1);
			}
		} else if (operationFlag == 2) {
			Date startDate = editInfusionApply.getStartTime();
			Date endDate = editInfusionApply.getEndTime();
			if (endDate != null && startDate != null) {
				if (endDate.before(startDate)) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"结束日期不能小于开始日期", ""));
					return;
				}
				editInfusionApply.setLastTime(util.DateUtil.diffDate(endDate,
						startDate) + 1);
				selectExecutePlan();
			}
		}
	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		addInfusionApply = new InfusionApplyDomain();
		operationFlag = 1;
	}

	/**
	 * 修改一条代办服务申请
	 */
	public void saveEditApplication() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (editInfusionApply.getEndTime().before(
				editInfusionApply.getStartTime())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"结束日期不能小于开始日期", ""));
			requestContext.addCallbackParam("editSuccess", false);
		} else {
			if (editInfusionApply.getInfusion()) {
				editInfusionApply.setInfusionFlag(1);
			} else {
				editInfusionApply.setInfusionFlag(2);
			}
			if (editInfusionApply.getProof()) {
				editInfusionApply.setHospitalproofFlag(1);
			} else {
				editInfusionApply.setHospitalproofFlag(2);
			}
			if (editInfusionApply.isSealFlag()) {
				editInfusionApply.setHospitalSeal(1);
			} else {
				editInfusionApply.setHospitalSeal(2);
			}
			infusionApplyService.updateInfusionApply(editInfusionApply);
			infusionApplyList = infusionApplyService.selectApplyList(olderId,
					isAudit, auditResult, startDate, endDate);
			selectedRow = editInfusionApply;
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "输液申请修改成功", ""));
			requestContext.addCallbackParam("editSuccess", true);
		}
	}

	/**
	 * 修改一条代办服务申请并发送通知
	 */
	public void saveAndSendEditApplication() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (editInfusionApply.getEndTime().before(
				editInfusionApply.getStartTime())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"结束日期不能小于开始日期", ""));
			requestContext.addCallbackParam("editSuccess", false);
		} else {
			if (editInfusionApply.getInfusion()) {
				editInfusionApply.setInfusionFlag(1);
			} else {
				editInfusionApply.setInfusionFlag(2);
			}
			if (editInfusionApply.getProof()) {
				editInfusionApply.setHospitalproofFlag(1);
			} else {
				editInfusionApply.setHospitalproofFlag(2);
			}
			if (editInfusionApply.isSealFlag()) {
				editInfusionApply.setHospitalSeal(1);
			} else {
				editInfusionApply.setHospitalSeal(2);
			}
			editInfusionApply.setSendFlag(1);
			infusionApplyService.updateInfusionApply(editInfusionApply);
			try {
				infusionApplyService.sentMessage(editInfusionApply);
			} catch (PmsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			infusionApplyList = infusionApplyService.selectApplyList(olderId,
					isAudit, auditResult, startDate, endDate);
			selectedRow = editInfusionApply;
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "输液申请修改并提交成功", ""));
			requestContext.addCallbackParam("editSuccess", true);
		}
	}

	/**
	 * 发送通知
	 */
	public void sentMessage() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选中一条记录！", ""));
			return;
		}
		if (selectedRow.getSendFlag() == 1) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "该申请已提交！", ""));
			return;
		}
		try {
			selectedRow.setSendFlag(1);
			infusionApplyService.updateInfusionApply(selectedRow);
			infusionApplyService.sentMessage(selectedRow);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "输液申请发送消息成功",
							""));
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		}
	}

	public void selectExecutePlan() {
		if (operationFlag == 1) {
			getExecutePlan(addInfusionApply);
		} else if (operationFlag == 2) {
			getExecutePlan(editInfusionApply);
		}
	}

	/**
	 * 由持续时间、次数生成执行计划
	 */
	public void getExecutePlan(InfusionApplyDomain domain) {
		String plan = "";
		int countNumbers = domain.getCountNumbers();
		int lastTime = domain.getLastTime();
		int frequency = countNumbers / lastTime;
		if (frequency >= 1) {
			int surplus = countNumbers % lastTime;
			for (int i = 1; i <= lastTime; i++) {
				for (int j = 1; j <= frequency; j++) {
					plan = plan + i + ", ";
				}
				if (surplus != 0) {
					plan = plan + i + ", ";
					surplus--;
				}
			}
		} else {
			frequency = lastTime / countNumbers;
			int j = 1;
			for (int i = 1; i <= lastTime;) {
				if (j <= countNumbers) {
					plan = plan + i + ", ";
					i += frequency;
					++j;
				} else {
					break;
				}
			}
		}
		plan = plan.substring(0, plan.length() - 2);
		domain.setExecutePlan(plan);
	}

	/**
	 * 生成输液记录
	 */
	public void generateInfusionPlan() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选中一条记录！", ""));
			return;
		}
		if (selectedRow.getVerifyFlag() == 2) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "该申请未审核！", ""));
			return;
		}
		if (selectedRow.getVerifyFlag() == 1
				&& selectedRow.getVerifyResult() == 2) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "该申请审核未通过！", ""));
			return;
		}
		if (selectedRow.getGenerateFlag() == 1) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "该申请已生成输液记录！", ""));
			return;
		}
		Date startDate = selectedRow.getStartTime();
		String[] plan = selectedRow.getExecutePlan().replace(" ", "")
				.split(",");
		List<String> planList = new ArrayList<String>();
		for (int i = 0; i < plan.length; i++) {
			planList.add(plan[i]);
		}
		PensionInfusionRecord record = new PensionInfusionRecord();
		record.setApplyId(selectedRow.getId());
		record.setCleared(2);
		record.setFinishFlag(2);
		record.setPayFlag(2);
		for (String temp : planList) {
			record.setInfusionTime(util.DateUtil.addDate(startDate,
					Integer.valueOf(temp) - 1));
			infusionApplyService.insertInfusionRecord(record);
		}
		selectedRow.setGenerateFlag(1);
		selectedRow.setFamilySign(1);
		infusionApplyService.updateInfusionApply(selectedRow);
		infusionRecordList = infusionApplyService
				.selectInfusionRecords(selectedRow.getId());
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"输液记录生成成功！", ""));
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

	public void setOlderSql(String olderSql) {
		this.olderSql = olderSql;
	}

	public String getOlderSql() {
		return olderSql;
	}

	public void setInfusionApplyService(
			InfusionApplyService infusionApplyService) {
		this.infusionApplyService = infusionApplyService;
	}

	public InfusionApplyService getInfusionApplyService() {
		return infusionApplyService;
	}

	public void setInfusionApplyList(List<InfusionApplyDomain> infusionApplyList) {
		this.infusionApplyList = infusionApplyList;
	}

	public List<InfusionApplyDomain> getInfusionApplyList() {
		return infusionApplyList;
	}

	public void setAddInfusionApply(InfusionApplyDomain addInfusionApply) {
		this.addInfusionApply = addInfusionApply;
	}

	public InfusionApplyDomain getAddInfusionApply() {
		return addInfusionApply;
	}

	public void setEditInfusionApply(InfusionApplyDomain editInfusionApply) {
		this.editInfusionApply = editInfusionApply;
	}

	public InfusionApplyDomain getEditInfusionApply() {
		return editInfusionApply;
	}

	public InfusionApplyDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(InfusionApplyDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setOperationFlag(int operationFlag) {
		this.operationFlag = operationFlag;
	}

	public int getOperationFlag() {
		return operationFlag;
	}

	public int getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(int saveFlag) {
		this.saveFlag = saveFlag;
	}

	public void setInfusionRecordList(
			List<InfusionRecordDomain> infusionRecordList) {
		this.infusionRecordList = infusionRecordList;
	}

	public List<InfusionRecordDomain> getInfusionRecordList() {
		return infusionRecordList;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
