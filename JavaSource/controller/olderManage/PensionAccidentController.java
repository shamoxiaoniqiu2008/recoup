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

import service.olderManage.PensionAccidentDomain;
import service.olderManage.PensionAccidentService;
import util.DateUtil;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.dictionary.PensionDicAccidenttype;
import domain.employeeManage.PensionEmployee;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-2 上午10:37:44
 */
public class PensionAccidentController implements Serializable {

	private static final long serialVersionUID = 1L;
	private transient PensionAccidentService pensionAccidentService;
	/**
	 * 查询条件
	 */
	private Long olderId;
	private String olderName;
	private Date startDate;
	private Date endDate;
	private Long accidenttypeId = 0L;
	private Long accidentId;
	/**
	 * 选中的事故记录
	 */
	private PensionAccidentDomain selectedRow;

	/**
	 * 所有事故记录列表
	 */
	private List<PensionAccidentDomain> accidentList = new ArrayList<PensionAccidentDomain>();

	/**
	 * 新增对话框事故对象
	 */
	private PensionAccidentDomain addAccident = new PensionAccidentDomain();
	/**
	 * 修改对话框事故对象
	 */
	private PensionAccidentDomain editAccident = new PensionAccidentDomain();
	// 当前登录用户
	private PensionEmployee curUser;
	// 事故类型
	private List<PensionDicAccidenttype> accidenttypeList = new ArrayList<PensionDicAccidenttype>();
	// 新增对话框事故类型
	private List<PensionDicAccidenttype> addAccidenttypeList = new ArrayList<PensionDicAccidenttype>();
	// 老人输入法
	private String olderSql;
	private String selectOlderSql;
	private Long deptId;

	@PostConstruct
	public void init() {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("olderId");
		String Id = paramsMap.get("accidentId");
		if (oldId != null) {
			olderId = Long.valueOf(oldId);
			accidentId = Long.valueOf(Id);
			olderName = pensionAccidentService.selectOlderName(olderId);
			startDate = null;
			endDate = null;
		} else {
			olderId = null;
			accidentId = null;
			endDate = new Date();
			startDate = DateUtil.getBeforeMonthDay(endDate, 1);
		}
		// 当前用户
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		initAccidentType();
		initSql();
		searchAllAccidents();
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		olderId = null;
		olderName = "";
		startDate = null;
		endDate = null;
		accidenttypeId = null;
	}

	public void initSql() {
		selectOlderSql = "select distinct pa.older_id,pa.older_name,po.inputCode  "
				+ "from pension_accident_record pa,pension_older po "
				+ "where pa.older_id=po.id and pa.cleared=2";
		olderSql = "select po.id,po.name,po.inputCode,pl.nurse_id as nurseId,pe.name as nurseName "
				+ "from pension_older po,pension_livingrecord pl,pension_employee pe "
				+ "where po.id=pl.older_id and pe.id=pl.nurse_id and  po.statuses in(3,4)";
	}

	/**
	 * 初始化查询老人的事故记录
	 */
	public void searchAllAccidents() {
		accidentList = pensionAccidentService.selectAccidentList(olderId,
				startDate, endDate, accidenttypeId, accidentId);
		selectedRow = null;
	}

	/**
	 * 根据条件查询老人的事故记录
	 */
	public void searchAccidents() {
		accidentList = pensionAccidentService.selectAccidentList(olderId,
				startDate, endDate, accidenttypeId, null);
		selectedRow = null;
	}

	/**
	 * 初始化事故类型
	 */
	public void initAccidentType() {
		addAccidenttypeList = pensionAccidentService.selectAccidentType();
		accidenttypeList = pensionAccidentService.selectAccidentType();
		PensionDicAccidenttype type = new PensionDicAccidenttype();
		type.setAccidenttypeName("全部");
		type.setId(0L);
		accidenttypeList.add(0, type);
	}

	/**
	 * 选中一行时触发
	 * 
	 * @param event
	 */
	public void setEnableFlag(SelectEvent event) {

	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		Long deptId = pensionAccidentService.getAccidentDeptId();
		if (curUser.getDeptId() != deptId) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "您没有权限进行该操作",
							""));
			return;
		}
		addAccident = new PensionAccidentDomain();
		addAccident.setInputuserName(curUser.getName());
		addAccident.setInputuserId(curUser.getId());
		requestContext.addCallbackParam("showAdd", true);

	}

	/**
	 * 检查是否弹出修改对话框
	 * 
	 * @throws PmsException
	 */
	public void showEditForm() throws PmsException {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		Long deptId = pensionAccidentService.getAccidentDeptId();
		if (curUser.getDeptId() != deptId) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "您没有权限进行该操作",
							""));
			return;
		}
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
			return;
		}
		if (curUser.getId() != selectedRow.getInputuserId()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"您不是事故录入人，不可修改", ""));
			return;
		}
		if (selectedRow.getSendFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该事故记录已上报", ""));
			return;
		}
		editAccident = selectedRow;
		requestContext.addCallbackParam("editshow", true);
	}

	/**
	 * 保存新增事故记录并上报给相关部门
	 */
	public void saveAndSendAccident() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		addAccident.setInputTime(new Date());
		addAccident.setCleared(2);
		addAccident.setSendFlag(2);
		addAccident.setInputuserName(curUser.getName());
		pensionAccidentService.insertAccident(addAccident);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "新增成功", ""));
		requestContext.addCallbackParam("addsuccess", true);
		selectedRow = addAccident;
		sentMessage();
	}

	/**
	 * 保存新增事故记录
	 */
	public void saveAccident() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		addAccident.setInputTime(new Date());
		addAccident.setCleared(2);
		addAccident.setSendFlag(2);
		addAccident.setInputuserName(curUser.getName());
		pensionAccidentService.insertAccident(addAccident);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "新增成功", ""));
		accidentList = pensionAccidentService.selectAccidentList(olderId,
				startDate, endDate, accidenttypeId, null);
		selectedRow = addAccident;
		requestContext.addCallbackParam("addsuccess", true);
	}

	/**
	 * 保存修改的事故记录并上报给相关部门
	 */
	public void saveAndSendEditAccident() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		pensionAccidentService.updateAccident(editAccident);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "修改成功", ""));
		requestContext.addCallbackParam("editsuccess", true);
		selectedRow = editAccident;
		sentMessage();
	}

	/**
	 * 保存修改的事故记录
	 */
	public void saveEditAccident() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		pensionAccidentService.updateAccident(editAccident);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "修改成功", ""));
		accidentList = pensionAccidentService.selectAccidentList(olderId,
				startDate, endDate, accidenttypeId, null);
		requestContext.addCallbackParam("editsuccess", true);
	}

	/**
	 * 不选中一行时触发
	 * 
	 * @param event
	 */
	public void setUnableFlag(UnselectEvent event) {
	}

	/**
	 * 发送通知
	 * 
	 * @throws PmsException
	 */
	public void sentMessage() {
		Long deptId = pensionAccidentService.getAccidentDeptId();
		if (curUser.getDeptId() != deptId) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "您没有权限进行该操作",
							""));
			return;
		}
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
			return;
		}
		if (curUser.getId() != selectedRow.getInputuserId()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"您不是事故录入人，不可上报", ""));
			return;
		}
		if (selectedRow.getSendFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该事故记录已上报", ""));
			return;
		}
		try {
			pensionAccidentService.sentMessage(selectedRow);
			selectedRow.setSendFlag(1);
			pensionAccidentService.updateAccident(selectedRow);
			accidentList = pensionAccidentService.selectAccidentList(olderId,
					startDate, endDate, accidenttypeId, null);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "事故上报消息发送成功",
							""));
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		}
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public Long getPensionOlderId() {
		return olderId;
	}

	public void setPensionOlderId(Long pensionOlderId) {
		this.olderId = pensionOlderId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
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

	public void setPensionAccidentService(
			PensionAccidentService pensionAccidentService) {
		this.pensionAccidentService = pensionAccidentService;
	}

	public PensionAccidentService getPensionAccidentService() {
		return pensionAccidentService;
	}

	public void setAccidenttypeId(Long accidenttypeId) {
		this.accidenttypeId = accidenttypeId;
	}

	public Long getAccidenttypeId() {
		return accidenttypeId;
	}

	public void setAccidentList(List<PensionAccidentDomain> accidentList) {
		this.accidentList = accidentList;
	}

	public List<PensionAccidentDomain> getAccidentList() {
		return accidentList;
	}

	public void setAddAccident(PensionAccidentDomain addAccident) {
		this.addAccident = addAccident;
	}

	public PensionAccidentDomain getAddAccident() {
		return addAccident;
	}

	public void setAccidenttypeList(
			List<PensionDicAccidenttype> accidenttypeList) {
		this.accidenttypeList = accidenttypeList;
	}

	public List<PensionDicAccidenttype> getAccidenttypeList() {
		return accidenttypeList;
	}

	public void setEditAccident(PensionAccidentDomain editAccident) {
		this.editAccident = editAccident;
	}

	public PensionAccidentDomain getEditAccident() {
		return editAccident;
	}

	public void setAddAccidenttypeList(
			List<PensionDicAccidenttype> addAccidenttypeList) {
		this.addAccidenttypeList = addAccidenttypeList;
	}

	public List<PensionDicAccidenttype> getAddAccidenttypeList() {
		return addAccidenttypeList;
	}

	public void setOlderSql(String olderSql) {
		this.olderSql = olderSql;
	}

	public String getOlderSql() {
		return olderSql;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setSelectOlderSql(String selectOlderSql) {
		this.selectOlderSql = selectOlderSql;
	}

	public String getSelectOlderSql() {
		return selectOlderSql;
	}

	public PensionAccidentDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(PensionAccidentDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setAccidentId(Long accidentId) {
		this.accidentId = accidentId;
	}

	public Long getAccidentId() {
		return accidentId;
	}

}
