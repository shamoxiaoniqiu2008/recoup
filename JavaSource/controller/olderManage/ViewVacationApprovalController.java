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

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.fingerPrint.PensionFingerPrintDaily;

import service.olderManage.PensionLeaveApproveExtend;
import service.olderManage.PensionLeaveExtend;
import service.olderManage.ViewVacationApprovalService;
import util.DateUtil;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

public class ViewVacationApprovalController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionLeaveExtend> records = new ArrayList<PensionLeaveExtend>();
	/**
	 * 被选中的请假记录
	 */
	private PensionLeaveExtend selectedRow;
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 开始日期 用作查询指纹的查询条件
	 */
	private Date printStartDate;
	/**
	 * 截止日期 用作查询指纹的查询条件
	 */
	private Date printEndDate;
	/**
	 * 确认标记 用作查询条件 1为已确认 2为未确认
	 */
	private Integer passFlag = 0;
	/**
	 * 该页面的离返院登记是否通过指纹验证 true为是 false为否
	 */
	private boolean printFlag = true;
	/**
	 * 评估部门列表
	 */
	private List<Long> deptList = new ArrayList<Long>();
	/**
	 * 评估人员列表
	 */
	private List<Long> empList = new ArrayList<Long>();
	/**
	 * 可进行登记操作的部门ID
	 */
	private List<Long> signDeptIdList = new ArrayList<Long>();
	/**
	 * 其他部门评估意见列表
	 */
	private List<PensionLeaveApproveExtend> deptEvaluateList = new ArrayList<PensionLeaveApproveExtend>();
	/**
	 * 老人主键用于查询条件
	 */
	private Long olderId;
	/**
	 * 请假记录主键
	 */
	private Long leaveId;
	/**
	 * 绑定关于老人信息的输入法
	 */
	private String olderNameSql;
	/**
	 * 登记按钮是否可用
	 */
	private boolean disLeaveSignButton = true;
	private boolean disBackSignButton = true;
	private boolean disSignButton = true;
	/**
	 * 当前老人的指纹记录
	 */
	private List<PensionFingerPrintDaily> printRecords = new ArrayList<PensionFingerPrintDaily>();
	/**
	 * 注入业务对象.
	 */
	private transient ViewVacationApprovalService viewVacationApprovalService;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee) SessionManager
			.getSessionAttribute(SessionManager.EMPLOYEE);
	/**
	 * 当前部门ID
	 */
	private Long curDeptId;
	private SystemConfig systemConfig;

	/**
	 * @Description:初始化数据方法.
	 * @return: void
	 * @exception:
	 * @throws:
	 * @version: 1.0
	 * @author: Tim li
	 * @throws PmsException
	 */
	@PostConstruct
	public void init() throws PmsException {
		initSql();
		initDept();
		// 获取消息源发给本页面的参数
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String leaveId = paramsMap.get("leaveId");
		if (leaveId != null) {
			this.leaveId = Long.valueOf(leaveId);
		} else {
			this.leaveId = null;
		}
		startDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
		endDate = DateUtil.getBeforeDay(startDate, -7);
		selectVacationApplicationRecords();
		// viewVacationApprovalService.updateMessage(this.leaveId,
		// curEmployee.getId(), curEmployee.getDeptId());
		// 之后 将其至空
		this.leaveId = null;
		printStartDate = new Date();
		printEndDate = new Date();
		printFlag = isPrint();// 初始化printFlag 即：是否采用指纹机制
	}

	/**
	 * 查询请假申请评估部门
	 * 
	 * @throws PmsException
	 */
	public void initDept() throws PmsException {
		curDeptId = curEmployee.getDeptId();
		deptList = viewVacationApprovalService
				.selectDeptIdList("VACATION_DPT_ID");
		empList = viewVacationApprovalService
				.selectEmpIdList("VACATION_EMP_ID");
		signDeptIdList = viewVacationApprovalService
				.selectDeptIdList("LEAVE_BACK_DEPT_ID");

	}

	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		setOlderNameSql("select po.name				  as oldName,"
				+ "pbuilding.name	  as buildName," + "pr.name		  as roomName,"
				+ "pf.name		  as floorName," + "pb.name		  as bedName,"
				+ "po.id       		  as olderId,"
				+ "po.inputCode		  as inputCode,"
				+ "pf.id              as floorId,"
				+ "pr.id              as roomId,"
				+ "pb.id              as bedId,"
				+ "pbuilding.id       as buildId" + " from pension_older po"
				+ " join pension_livingRecord plr "
				+ "on po.id = plr.older_id " + "join pension_bed pb "
				+ "on plr.bed_id = pb.id " + "join pension_room pr "
				+ "on pb.room_id = pr.id " + "join pension_floor pf "
				+ "on pr.floor_id = pf.id "
				+ "join pension_building pbuilding "
				+ "on pf.build_id = pbuilding.id " + "where po.cleared = 2;");
	}

	/**
	 * 查询假期申请记录
	 */
	public void selectVacationApplicationRecords() {
		disBackSignButton = true;
		disLeaveSignButton = true;
		selectedRow = new PensionLeaveExtend();
		if (leaveId != null) {
			startDate = null;
			endDate = null;
		}
		setRecords(viewVacationApprovalService.selectVacationApproveRecords(
				startDate, endDate, olderId, passFlag, empList, deptList,
				leaveId, curEmployee));
	}

	public void selectDeptEvaList(PensionLeaveExtend selectedRow) {
		deptEvaluateList = viewVacationApprovalService.selectDeptEvaList(
				selectedRow.getId(), empList, deptList);
	}

	/**
	 * 离院登记通过指纹机制
	 * 
	 * @throws PmsException
	 */
	public void leavedSignByPrint() throws PmsException {

		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow.getFinalResult() != 3) {

			if (printRecords != null && !printRecords.isEmpty()) {

				Date signDate = printRecords.get(0).getAnLogDate();
				selectedRow.setRealleavetime(signDate);

				viewVacationApprovalService.leavedSign(selectedRow);
				viewVacationApprovalService.clearPrintRecord(printRecords);
				selectVacationApplicationRecords();
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "离院登记成功！", "离院登记成功！");
				context.addMessage(null, message);
				request.addCallbackParam("success", true);
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "无该老人的指纹记录，不能进行登记！",
						"无该老人的指纹记录，不能进行登记！");
				context.addMessage(null, message);
				request.addCallbackParam("success", false);
			}

		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"审批不通过的老人不能进行离院登记！", "审批不通过的老人不能进行离院登记！");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		}

	}

	/**
	 * 返院登记通过指纹机制
	 * 
	 * @throws PmsException
	 */
	public void backedSignByPrint() throws PmsException {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (printRecords != null && !printRecords.isEmpty()) {
			Date signDate = printRecords.get(0).getAnLogDate();
			selectedRow.setRealbacktime(signDate);
			viewVacationApprovalService.backedSign(selectedRow);
			viewVacationApprovalService.clearPrintRecord(printRecords);
			selectVacationApplicationRecords();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"返院登记成功！", "返院登记成功！");
			context.addMessage(null, message);
			request.addCallbackParam("success", true);

		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"无该老人的指纹记录，不能进行登记！", "无该老人的指纹记录，不能进行登记！");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		}

	}

	/**
	 * 离院登记不通过指纹机制
	 * 
	 * @throws PmsException
	 */
	public void leavedSign() throws PmsException {

		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow.getFinalResult() == 3) {

			selectedRow.setRealleavetime(new Date());
			viewVacationApprovalService.leavedSign(selectedRow);
			selectVacationApplicationRecords();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"离院登记成功！", "离院登记成功！");
			context.addMessage(null, message);

		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"审批不通过的老人不能进行离院登记！", "审批不通过的老人不能进行离院登记！");
			context.addMessage(null, message);
		}

	}
	
	
	/**
	 * 返院登记通过指纹机制
	 * 
	 * @throws PmsException
	 */
	public void backedSign() throws PmsException {

		FacesContext context = FacesContext.getCurrentInstance();
		selectedRow.setRealbacktime(new Date());
		viewVacationApprovalService.backedSign(selectedRow);
		selectVacationApplicationRecords();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"返院登记成功！", "返院登记成功！");
		context.addMessage(null, message);

	}

	/**
	 * 查询指纹记录
	 */
	public void selectPrintRecord() {

		setPrintRecords(viewVacationApprovalService.selectPrintRecords(
				selectedRow, printStartDate, printEndDate));
	}

	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		olderId = null;
		passFlag = 0;
	}

	/**
	 * datatable被选中时候的触发事件
	 * 
	 * @throws PmsException
	 */
	public void selectRecord(SelectEvent e) throws PmsException {

		disSignButton = false;
		disBackSignButton = true;
		disLeaveSignButton = true;

		if (signDeptIdList.contains(curDeptId)) {

			if (selectedRow.getIsleaved() == 2) {

				disLeaveSignButton = false;
				disBackSignButton = true;
			} else {
				if (selectedRow.getIsbacked() == 2) {

					disBackSignButton = false;
					disLeaveSignButton = true;
				} else {

					disBackSignButton = true;
					disLeaveSignButton = true;
				}
			}

		} else {
			disLeaveSignButton = true;
			disBackSignButton = true;
		}

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		disSignButton = true;
		disLeaveSignButton = true;
		disBackSignButton = true;
	}

	/**
	 * 返回是否 需要指纹认证
	 * 
	 * @return
	 * @throws PmsException
	 */
	public boolean isPrint() throws PmsException {
		return viewVacationApprovalService.isPrint();
	}

	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordUpdatedRow() {
		// CloneUtil.deepCopy();
		// 浅拷贝
		// chargerateUpdatedRow = chargerateSelectedRow;
	}

	public void setOlderNameSql(String olderNameSql) {
		this.olderNameSql = olderNameSql;
	}

	public String getOlderNameSql() {
		return olderNameSql;
	}

	public void setDeptList(List<Long> deptList) {
		this.deptList = deptList;
	}

	public List<Long> getDeptList() {
		return deptList;
	}

	public void setDeptEvaluateList(
			List<PensionLeaveApproveExtend> deptEvaluateList) {
		this.deptEvaluateList = deptEvaluateList;
	}

	public List<PensionLeaveApproveExtend> getDeptEvaluateList() {
		return deptEvaluateList;
	}

	public void setRecords(List<PensionLeaveExtend> records) {
		this.records = records;
	}

	public List<PensionLeaveExtend> getRecords() {
		return records;
	}

	public void setSelectedRow(PensionLeaveExtend selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionLeaveExtend getSelectedRow() {
		return selectedRow;
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

	public Integer getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(Integer passFlag) {
		this.passFlag = passFlag;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public ViewVacationApprovalService getViewVacationApprovalService() {
		return viewVacationApprovalService;
	}

	public void setViewVacationApprovalService(
			ViewVacationApprovalService viewVacationApprovalService) {
		this.viewVacationApprovalService = viewVacationApprovalService;
	}

	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}

	public Long getLeaveId() {
		return leaveId;
	}

	public void setDisLeaveSignButton(boolean disLeaveSignButton) {
		this.disLeaveSignButton = disLeaveSignButton;
	}

	public boolean isDisLeaveSignButton() {
		return disLeaveSignButton;
	}

	public void setDisBackSignButton(boolean disBackSignButton) {
		this.disBackSignButton = disBackSignButton;
	}

	public boolean isDisBackSignButton() {
		return disBackSignButton;
	}

	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}

	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}

	public void setCurDeptId(Long curDeptId) {
		this.curDeptId = curDeptId;
	}

	public Long getCurDeptId() {
		return curDeptId;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	public void setSignDeptIdList(List<Long> signDeptIdList) {
		this.signDeptIdList = signDeptIdList;
	}

	public List<Long> getSignDeptIdList() {
		return signDeptIdList;
	}

	public void setPrintRecords(List<PensionFingerPrintDaily> printRecords) {
		this.printRecords = printRecords;
	}

	public List<PensionFingerPrintDaily> getPrintRecords() {
		return printRecords;
	}

	public void setDisSignButton(boolean disSignButton) {
		this.disSignButton = disSignButton;
	}

	public boolean isDisSignButton() {
		return disSignButton;
	}

	public void setPrintStartDate(Date printStartDate) {
		this.printStartDate = printStartDate;
	}

	public Date getPrintStartDate() {
		return printStartDate;
	}

	public void setPrintEndDate(Date printEndDate) {
		this.printEndDate = printEndDate;
	}

	public Date getPrintEndDate() {
		return printEndDate;
	}

	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}

	public boolean isPrintFlag() {
		return printFlag;
	}

	public void setEmpList(List<Long> empList) {
		this.empList = empList;
	}

	public List<Long> getEmpList() {
		return empList;
	}

}
