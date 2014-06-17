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

import service.olderManage.InfusionConfirmService;
import service.olderManage.InfusionRecordDomain;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.medicalManage.PensionInfusionApply;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-6 上午08:30:44
 */

public class InfusionConfirmController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient InfusionConfirmService infusionConfirmService;
	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;
	private Date startDate;
	private Date endDate;

	/**
	 * 选中的输液记录
	 */
	private InfusionRecordDomain selectedRecord;
	/**
	 * 输液计划列表
	 */
	private List<PensionInfusionApply> infusionApplyList = new ArrayList<PensionInfusionApply>();
	/**
	 * 输液记录列表
	 */
	private List<InfusionRecordDomain> infusionRecordList = new ArrayList<InfusionRecordDomain>();
	/**
	 * 输液记录确认对象
	 */
	private InfusionRecordDomain confirmRecord;
	private PensionEmployee curUser;
	private String employeeSql;
	private Long itemPurseTypeId;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		startDate = util.DateUtil.parseDate(util.DateUtil
				.formatDate(new Date()));
		endDate = startDate;
		employeeSql = "select pe.id,pe.name,pe.inputCode  "
				+ "from pension_employee pe where pe.dept_id="
				+ curUser.getDeptId();
		try {
			itemPurseTypeId = infusionConfirmService.selectItemPurseTypeId();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
		}
		selectInfusionRecords();
	}

	/**
	 * 点击确认按钮触发
	 */
	public void confirmShow() {
		RequestContext requstContext = RequestContext.getCurrentInstance();
		if (selectedRecord == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
		} else if (selectedRecord.getFinishFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该输液记录已确认", ""));
		} else {
			confirmRecord = selectedRecord;
			confirmRecord.setExecutorId(curUser.getId());
			confirmRecord.setExecutorName(curUser.getName());
			confirmRecord.setExecuteTime(new Date());
			requstContext.addCallbackParam("show", true);
		}
	}

	/**
	 * 选中一条输液计划，查询对应的输液记录
	 */
	public void selectInfusionRecords() {
		infusionRecordList = infusionConfirmService.selectInfusionRecords(
				startDate, endDate);
	}

	/**
	 * 未选中一条输液计划，更新对应的输液记录
	 */
	public void clearInfusionRecord(UnselectEvent e) {
		infusionRecordList = new ArrayList<InfusionRecordDomain>();
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
	 * 选中一条输液记录
	 * 
	 * @param e
	 */
	public void recordSelect(SelectEvent e) {
	}

	/**
	 * 未选中一条输液记录
	 * 
	 * @param e
	 */
	public void recordUnSelect(UnselectEvent e) {

	}

	/**
	 * 保存输液确认信息
	 */
	public void confirmInfusion() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		confirmRecord.setFinishFlag(1);
		infusionConfirmService.updateInfusionRecord(confirmRecord);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "输液确认成功", ""));
		requestContext.addCallbackParam("hide", true);
	}

	/**
	 * 费用录入
	 */
	public void temPay() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRecord == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
			return;
		}
		if (selectedRecord.getFinishFlag() == 2) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该输液记录尚未确认",
							""));
			return;
		}
		if (selectedRecord.getPayFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该输液记录已录费", ""));
			return;
		}
		requestContext.addCallbackParam("older", selectedRecord);
		requestContext.addCallbackParam("keyId", selectedRecord.getId());
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

	public void setInfusionConfirmService(
			InfusionConfirmService infusionConfirmService) {
		this.infusionConfirmService = infusionConfirmService;
	}

	public InfusionConfirmService getInfusionConfirmService() {
		return infusionConfirmService;
	}

	public void setSelectedRecord(InfusionRecordDomain selectedRecord) {
		this.selectedRecord = selectedRecord;
	}

	public InfusionRecordDomain getSelectedRecord() {
		return selectedRecord;
	}

	public void setInfusionRecordList(
			List<InfusionRecordDomain> infusionRecordList) {
		this.infusionRecordList = infusionRecordList;
	}

	public List<InfusionRecordDomain> getInfusionRecordList() {
		return infusionRecordList;
	}

	public void setConfirmRecord(InfusionRecordDomain confirmRecord) {
		this.confirmRecord = confirmRecord;
	}

	public InfusionRecordDomain getConfirmRecord() {
		return confirmRecord;
	}

	public void setInfusionApplyList(
			List<PensionInfusionApply> infusionApplyList) {
		this.infusionApplyList = infusionApplyList;
	}

	public List<PensionInfusionApply> getInfusionApplyList() {
		return infusionApplyList;
	}

	public void setEmployeeSql(String employeeSql) {
		this.employeeSql = employeeSql;
	}

	public String getEmployeeSql() {
		return employeeSql;
	}

	public void setItemPurseTypeId(Long itemPurseTypeId) {
		this.itemPurseTypeId = itemPurseTypeId;
	}

	public Long getItemPurseTypeId() {
		return itemPurseTypeId;
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

}
