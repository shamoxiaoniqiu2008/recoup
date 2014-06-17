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

import service.olderManage.DayCareDomain;
import service.olderManage.DayCareService;
import util.DateUtil;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-18 上午08:30:44
 */

public class DayCareController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient DayCareService dayCareService;
	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;
	private Long careRecordId;
	private String nurseType;
	private Date startDate;
	private Date endDate;
	private String nurseFlag;

	private PensionEmployee curUser;

	/**
	 * 护理记录列表
	 */
	private List<DayCareDomain> dayCareList = new ArrayList<DayCareDomain>();

	/**
	 * 选中的护理记录
	 */
	private DayCareDomain selectedRow;
	/**
	 * 确认护理对象
	 */
	private DayCareDomain confirmDaycare;
	private String olderSql;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("olderId");
		String recordId = paramsMap.get("recordId");
		String typeId = paramsMap.get("typeId");
		if (oldId != null) {
			olderId = Long.valueOf(oldId);
			olderName = dayCareService.selectOlderName(olderId);
			careRecordId = Long.valueOf(recordId);
			nurseType = typeId;
		} else {
			olderId = null;
			olderName = "";
			careRecordId = null;
			typeId = null;
		}
		startDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
		endDate = startDate;
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		olderSql = "select distinct  po.id,po.name,po.inputCode from pension_daycare pd,"
				+ "pension_older po where po.id=pd.older_id and pd.nurser_id="
				+ curUser.getId();
		nurseFlag = "2";
		searchDayCareRecord();
	}

	/**
	 * 查询护理计划列表
	 */
	public void searchDayCareRecord() {
		if (olderId == null) {
			careRecordId = null;
		}
		dayCareList = dayCareService.selectDayCareList(olderId,
				curUser.getId(), careRecordId, nurseType, startDate, endDate,
				nurseFlag);
		selectedRow = null;
	}

	/**
	 * 点击确认按钮触发
	 */
	public void initConfirm() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
		} else if (selectedRow.getExecuteFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该护理记录已确认！",
							""));
		} else {
			confirmDaycare = selectedRow;
			requestContext.addCallbackParam("show", true);
		}
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

	/**
	 * 确认一条护理记录
	 * 
	 */
	public void confirmDaycare() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		confirmDaycare.setExecuteTime(new Date());
		confirmDaycare.setExecuteFlag(1);
		dayCareService.updateDaycare(confirmDaycare, curUser.getDeptId());
		searchDayCareRecord();
		requestContext.addCallbackParam("hide", true);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "护理记录确认成功", ""));
	}

	/**
	 * 初始化老人评价
	 */
	public void initEvaluation() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
		} else if (selectedRow.getExecuteFlag() == 2) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该护理记录尚未完成！",
							""));
		} else if (selectedRow.getEvaluation() != null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该护理记录已评价！",
							""));
		} else {
			confirmDaycare = selectedRow;
			requestContext.addCallbackParam("evalShow", true);
		}
	}

	/**
	 * 保存老人评价
	 */
	public void saveEvaluation() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		dayCareService.savePensionQuality(confirmDaycare);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "老人评价成功", ""));
		requestContext.addCallbackParam("evalHide", true);
	}

	/**
	 * 清空selectForm
	 */
	public void clearSelectForm() {
		olderName = null;
		olderId = null;
		careRecordId = null;
		nurseType = null;
		startDate = null;
		endDate = null;
		nurseFlag = null;
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

	public void setDayCareList(List<DayCareDomain> dayCareList) {
		this.dayCareList = dayCareList;
	}

	public List<DayCareDomain> getDayCareList() {
		return dayCareList;
	}

	public void setDayCareService(DayCareService dayCareService) {
		this.dayCareService = dayCareService;
	}

	public DayCareService getDayCareService() {
		return dayCareService;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public DayCareDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(DayCareDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setConfirmDaycare(DayCareDomain confirmDaycare) {
		this.confirmDaycare = confirmDaycare;
	}

	public DayCareDomain getConfirmDaycare() {
		return confirmDaycare;
	}

	public void setCareRecordId(Long careRecordId) {
		this.careRecordId = careRecordId;
	}

	public Long getCareRecordId() {
		return careRecordId;
	}

	public void setNurseType(String nurseType) {
		this.nurseType = nurseType;
	}

	public String getNurseType() {
		return nurseType;
	}

	public void setNurseFlag(String nurseFlag) {
		this.nurseFlag = nurseFlag;
	}

	public String getNurseFlag() {
		return nurseFlag;
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
