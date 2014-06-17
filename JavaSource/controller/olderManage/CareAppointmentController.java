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

import service.olderManage.CareAppointmentDomain;
import service.olderManage.CareAppointmentService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.nurseManage.PensionDaycare;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-20 上午08:30:44
 */

public class CareAppointmentController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient CareAppointmentService careAppointmentService;
	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;
	private Date startDate;
	private Date endDate;
	private Long careAppId;
	private int nurseFlag;

	/**
	 * 选中的护理预约记录
	 */
	private CareAppointmentDomain selectedRow;
	/**
	 * 护理预约列表
	 */
	private List<CareAppointmentDomain> careAppointmentList = new ArrayList<CareAppointmentDomain>();
	/**
	 * 新增护理预约对象
	 */
	private CareAppointmentDomain addAppointment = new CareAppointmentDomain();
	/**
	 * 输入法
	 */
	private String olderSql;
	private String nurseSql;
	/**
	 * 当前登录用户
	 */
	private PensionEmployee curUser;
	private Long deptId;
	private Long nurseDeptId;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		deptId = curUser.getDeptId();
		nurseDeptId = careAppointmentService.selectNurseDeptId();
		initParam();
		initSql();
		searchAppointment();
	}

	/**
	 * 初始化参数
	 */
	public void initParam() {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("oldId");
		String appId = paramsMap.get("appId");
		if (oldId != null) {
			olderId = Long.valueOf(oldId);
			olderName = careAppointmentService.selectOlderName(olderId);
			careAppId = Long.valueOf(appId);
		} else {
			olderId = null;
			olderName = "";
			careAppId = null;
		}
	}

	/**
	 * 初始化输入法SQL
	 */
	public void initSql() {
		olderSql = "select po.id,po.name,po.inputCode,pe.name as nurseName,"
				+ "pb.name as bedName,pb.roomName,pb.floorName,"
				+ "pb.buildName from pension_employee pe,pension_livingrecord pl,"
				+ "pension_older po,pension_bed pb where pl.nurse_id=pe.id "
				+ "and pl.older_id=po.id and pl.bed_id=pb.id and po.statuses in(3,4)";

		nurseSql = "select pe.id,pe.name,pe.inputCode from  "
				+ "pension_employee pe where pe.dept_id=" + nurseDeptId;
	}

	/**
	 * 查询护理计划列表
	 */
	public void searchAppointment() {
		if (olderId == null) {
			careAppId = null;
		}
		careAppointmentList = careAppointmentService.selectAppRecords(olderId,
				startDate, endDate, careAppId);
		selectedRow = null;
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		olderName = "";
		olderId = null;
		startDate = null;
		endDate = null;
	}

	/**
	 * 点击修改按钮触发
	 */
	public void initEditCareApp() {
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
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"该护理预约已发送通知，不可修改", ""));
		} else {
			addAppointment = selectedRow;
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
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"该护理预约已发送通知，不可删除", ""));
		} else {
			requestContext.addCallbackParam("del", true);
		}
	}

	/**
	 * 删除一条护理计划
	 */
	public void deleteCareApp() {
		selectedRow.setCleared(1);
		careAppointmentService.updateCareApp(selectedRow);
		searchAppointment();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "删除成功", ""));
	}

	/**
	 * 选中一行时触发
	 * 
	 */
	public void selectRow(SelectEvent e) {

	}

	/**
	 * 未选中一行时触发
	 * 
	 */
	public void unselectRow(UnselectEvent e) {

	}

	/**
	 * 保存一条护理预约
	 * 
	 */
	public void saveCareAppointment() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (addAppointment.getAppointmentTime().before(new Date())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"预约时间不能小于当前时间", ""));
			request.addCallbackParam("hide", false);
		} else {
			addAppointment.setCleared(2);
			addAppointment.setGenerateFlag(2);
			addAppointment.setSendFlag(2);
			addAppointment.setFamilySign(1);
			careAppointmentService.insertCareApp(addAppointment);
			careAppointmentList = careAppointmentService.selectAppRecords(
					olderId, startDate, endDate, careAppId);
			selectedRow = careAppointmentService.selectAppRecords(null, null,
					null, addAppointment.getId()).get(0);
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"新增护理预约成功", ""));
			request.addCallbackParam("hide", true);
		}
	}

	/**
	 * 保存并提交一条护理预约
	 * 
	 */
	public void saveAndSendCareAppointment() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (addAppointment.getAppointmentTime().before(new Date())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"预约时间不能小于当前时间", ""));
			requestContext.addCallbackParam("hide", false);
			return;
		}
		if (!addAppointment.isSignFlag()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "家属尚未签字，不能保存",
							""));
			requestContext.addCallbackParam("hide", false);
			return;
		}
		addAppointment.setCleared(2);
		addAppointment.setGenerateFlag(2);
		addAppointment.setSendFlag(1);
		addAppointment.setFamilySign(1);
		careAppointmentService.insertAndSendCareApp(addAppointment);
		careAppointmentList = careAppointmentService.selectAppRecords(olderId,
				startDate, endDate, careAppId);
		selectedRow = careAppointmentService.selectAppRecords(null, null, null,
				addAppointment.getId()).get(0);
		requestContext.addCallbackParam("hide", true);
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"护理预约新增并提交成功", ""));
	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		addAppointment = new CareAppointmentDomain();
		addAppointment.setSignFlag(true);
	}

	/**
	 * 修改一条护理计划
	 */
	public void updateCareAppointment() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (addAppointment.getAppointmentTime().before(new Date())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"预约时间不能小于当前时间", ""));
			requestContext.addCallbackParam("editSuccess", false);
		} else {
			careAppointmentService.updateCareApp(addAppointment);
			careAppointmentList = careAppointmentService.selectAppRecords(
					olderId, startDate, endDate, careAppId);
			selectedRow = addAppointment;
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "护理预约修改成功", ""));
			requestContext.addCallbackParam("editSuccess", true);
		}
	}

	/**
	 * 修改并提交一条护理计划
	 */
	public void updateAndSendCareAppointment() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (addAppointment.getAppointmentTime().before(new Date())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"预约时间不能小于当前时间", ""));
			requestContext.addCallbackParam("addSuccess", false);
		} else {
			addAppointment.setSendFlag(1);
			careAppointmentService.updateAndSendCareApp(addAppointment);
			careAppointmentList = careAppointmentService.selectAppRecords(
					olderId, startDate, endDate, careAppId);
			selectedRow = addAppointment;
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "护理预约修改并提交成功", ""));
			requestContext.addCallbackParam("editSuccess", true);
		}
	}

	/**
	 * 发送通知
	 * 
	 */
	public void sentMessage() {
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
			return;
		}
		if (selectedRow.getFamilySign() == 2) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"家属未签字确认！", ""));
			return;
		}
		if (selectedRow.getSendFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "不可重复发送通知！",
							""));
			return;
		}
		try {
			careAppointmentService.sentMessage(selectedRow);
			searchAppointment();
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							e.getMessage(), ""));
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "发送预约护理通知成功", ""));

	}

	/**
	 * 分配护理员触发
	 */
	public void nurseManage() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
			return;
		}
		if (selectedRow.getSendFlag() == 2) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"尚未发送通知！", ""));
			return;
		}
		if (selectedRow.getGenerateFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该护理已被执行！", ""));
			return;
		}
		addAppointment = selectedRow;
		if (addAppointment.getExecuteNurseName() != null) {
			nurseFlag = 2;
		} else {
			nurseFlag = 1;
		}
		requestContext.addCallbackParam("nurseShow", true);
	}

	/**
	 * 新增一条护理记录
	 */
	public void confirmNurse() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		PensionDaycare daycare = new PensionDaycare();
		if (nurseFlag == 1) {
			daycare.setCleared(2);
			daycare.setExecuteFlag(2);
			daycare.setNurseId(addAppointment.getId());
			daycare.setNurserId(addAppointment.getExecuteNurseId());
			daycare.setNurserName(addAppointment.getExecuteNurseName());
			daycare.setNurseTime(addAppointment.getAppointmentTime());
			daycare.setNurseType(2);
			daycare.setOlderId(addAppointment.getOlderId());
			daycare.setOlderName(addAppointment.getOlderName());
			careAppointmentService.insertDayCare(daycare, curUser);
		} else {
			daycare.setId(addAppointment.getDaycareId());
			daycare.setNurserId(addAppointment.getExecuteNurseId());
			daycare.setNurserName(addAppointment.getExecuteNurseName());
			daycare.setOlderId(addAppointment.getOlderId());
			daycare.setOlderName(addAppointment.getOlderName());
			careAppointmentService.updateDaycare(daycare, curUser);
		}
		careAppointmentList = careAppointmentService.selectAppRecords(olderId,
				startDate, endDate, careAppId);
		selectedRow = addAppointment;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "护理员分配成功！", ""));
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"分配护理员消息已发送！", ""));
		requestContext.addCallbackParam("nurseHide", true);
	}

	public CareAppointmentDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(CareAppointmentDomain selectedRow) {
		this.selectedRow = selectedRow;
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

	public void setCareAppointmentService(
			CareAppointmentService careAppointmentService) {
		this.careAppointmentService = careAppointmentService;
	}

	public CareAppointmentService getCareAppointmentService() {
		return careAppointmentService;
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

	public void setCareAppointmentList(
			List<CareAppointmentDomain> careAppointmentList) {
		this.careAppointmentList = careAppointmentList;
	}

	public List<CareAppointmentDomain> getCareAppointmentList() {
		return careAppointmentList;
	}

	public void setAddAppointment(CareAppointmentDomain addAppointment) {
		this.addAppointment = addAppointment;
	}

	public CareAppointmentDomain getAddAppointment() {
		return addAppointment;
	}

	public void setCareAppId(Long careAppId) {
		this.careAppId = careAppId;
	}

	public Long getCareAppId() {
		return careAppId;
	}

	public void setNurseSql(String nurseSql) {
		this.nurseSql = nurseSql;
	}

	public String getNurseSql() {
		return nurseSql;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setNurseFlag(int nurseFlag) {
		this.nurseFlag = nurseFlag;
	}

	public int getNurseFlag() {
		return nurseFlag;
	}

	public void setNurseDeptId(Long nurseDeptId) {
		this.nurseDeptId = nurseDeptId;
	}

	public Long getNurseDeptId() {
		return nurseDeptId;
	}

}
