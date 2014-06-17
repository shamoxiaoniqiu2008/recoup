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

import service.olderManage.EnsureVacationApplicationService;
import service.olderManage.PensionLeaveApproveExtend;
import service.olderManage.PensionLeaveExtend;
import util.DateUtil;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.olderManage.PensionLeaveapprove;

public class EnsureVacationApplicationController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2850193773031401230L;
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
	 * 确认标记 用作查询条件 1为已确认 2为未确认
	 */
	private Integer ensureFlag;
	/**
	 * 老人主键用于查询条件
	 */
	private Long olderId;
	private String olderName;
	/**
	 * 请假申请表的主键，用在接收消息时做查询条件
	 */
	private Long leaveId;
	/**
	 * 绑定关于老人信息的输入法
	 */
	private String olderNameSql;
	/**
	 * 确认按钮是否可用
	 */
	private boolean disEnsureButton = true;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee) SessionManager
			.getSessionAttribute(SessionManager.EMPLOYEE);
	/**
	 * 注入业务对象.
	 */
	private transient EnsureVacationApplicationService ensureVacationApplicationService;

	private List<PensionLeaveApproveExtend> deptEvaluateList;

	private PensionLeaveapprove approveRecord = new PensionLeaveapprove();

	/**
	 * @Description:初始化数据方法.
	 * @return: void
	 * @exception:
	 * @throws:
	 * @version: 1.0
	 * @author: Tim li
	 */
	@PostConstruct
	public void init() {
		initSql();
		// 获取消息源发给本页面的参数
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String leaveRecordId = paramsMap.get("leaveId");
		String oldId = paramsMap.get("olderId");
		if (oldId != null) {
			olderId = Long.valueOf(oldId);
			olderName = ensureVacationApplicationService
					.selectOlderName(olderId);
			leaveId = Long.valueOf(leaveRecordId);
		} else {
			olderId = null;
			olderName = "";
			leaveId = null;
		}
		startDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
		endDate = DateUtil.getBeforeDay(startDate, -7);
		// 根据参数 和其余默认的查询条件查询出所有的请假申请
		selectVacationApplicationRecords();
		// 之后 将其至空
		this.leaveId = null;
	}

	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		olderNameSql = "select po.name				  as oldName,"
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
				+ "on pf.build_id = pbuilding.id " + "where po.cleared = 2;";
	}

	/**
	 * 查询假期申请记录
	 */
	public void selectVacationApplicationRecords() {
		if (leaveId != null) {
			startDate = null;
			endDate = null;
		}
		records = ensureVacationApplicationService
				.selectVacationApplicationRecords(startDate, endDate, olderId,
						ensureFlag, curEmployee, leaveId);
		if (records != null && records.size() > 0) {
			selectedRow = records.get(0);
			disEnsureButton = false;
			deptEvaluateList = ensureVacationApplicationService
					.selectDeptOptions(selectedRow.getId());
		} else {
			selectedRow = null;
			disEnsureButton = true;
			deptEvaluateList = new ArrayList<PensionLeaveApproveExtend>();
		}
	}

	/**
	 * 绑定前台的同意按钮
	 */
	public void approve() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow.getFinalResult() != 3
				&& selectedRow.getFinalResult() != 4) {
			approveRecord.setApproveresult(1);
			approveRecord.setApproverId(curEmployee.getId());
			approveRecord.setApprovetime(new Date());
			approveRecord.setLeaveId(selectedRow.getId());
			approveRecord.setDeptId(curEmployee.getDeptId());
			ensureVacationApplicationService.approve(approveRecord,
					curEmployee.getId(), curEmployee.getDeptId());
			records = ensureVacationApplicationService
					.selectVacationApplicationRecords(startDate, endDate,
							olderId, ensureFlag, curEmployee, leaveId);
			deptEvaluateList = ensureVacationApplicationService
					.selectDeptOptions(selectedRow.getId());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"审批成功！", "审批成功！");
			context.addMessage(null, message);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已审批的记录不能再审批！", "已审批的记录不能再审批！");
			context.addMessage(null, message);
		}
	}

	/**
	 * 不同意触发
	 */
	public void selectCheck() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow.getFinalResult() == 3
				|| selectedRow.getFinalResult() == 4) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该申请已审核！", ""));
			return;
		}
		requestContext.addCallbackParam("show", true);
		selectedRow.setNotes("");
	}

	/**
	 * 绑定前台的不同意按钮
	 */
	public void refuse() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow.getFinalResult() != 3
				&& selectedRow.getFinalResult() != 4) {
			approveRecord.setApproveresult(2);
			approveRecord.setApproverId(curEmployee.getId());
			approveRecord.setApprovetime(new Date());
			approveRecord.setLeaveId(selectedRow.getId());
			approveRecord.setDeptId(curEmployee.getDeptId());
			ensureVacationApplicationService.approve(approveRecord,
					curEmployee.getId(), curEmployee.getDeptId());
			records = ensureVacationApplicationService
					.selectVacationApplicationRecords(startDate, endDate,
							olderId, ensureFlag, curEmployee, leaveId);
			deptEvaluateList = ensureVacationApplicationService
					.selectDeptOptions(selectedRow.getId());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"审批成功！", "审批成功！");
			context.addMessage(null, message);
			requestContext.addCallbackParam("hide", true);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已审批的记录不能再审批！", "已审批的记录不能再审批！");
			context.addMessage(null, message);
		}
	}

	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		olderId = null;
		ensureFlag = 0;
	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		this.disEnsureButton = false;
		deptEvaluateList = ensureVacationApplicationService
				.selectDeptOptions(selectedRow.getId());

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		this.disEnsureButton = true;
		deptEvaluateList = new ArrayList<PensionLeaveApproveExtend>();
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

	public void setEnsureVacationApplicationService(
			EnsureVacationApplicationService ensureVacationApplicationService) {
		this.ensureVacationApplicationService = ensureVacationApplicationService;
	}

	public EnsureVacationApplicationService getEnsureVacationApplicationService() {
		return ensureVacationApplicationService;
	}

	public void setEnsureFlag(Integer ensureFlag) {
		this.ensureFlag = ensureFlag;
	}

	public Integer getEnsureFlag() {
		return ensureFlag;
	}

	public void setOlderNameSql(String olderNameSql) {
		this.olderNameSql = olderNameSql;
	}

	public String getOlderNameSql() {
		return olderNameSql;
	}

	public void setDisEnsureButton(boolean disEnsureButton) {
		this.disEnsureButton = disEnsureButton;
	}

	public boolean isDisEnsureButton() {
		return disEnsureButton;
	}

	public void setRecords(List<PensionLeaveExtend> records) {
		this.records = records;
	}

	public List<PensionLeaveExtend> getRecords() {
		return records;
	}

	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}

	public Long getLeaveId() {
		return leaveId;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}

	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}

	public void setDeptEvaluateList(
			List<PensionLeaveApproveExtend> deptEvaluateList) {
		this.deptEvaluateList = deptEvaluateList;
	}

	public List<PensionLeaveApproveExtend> getDeptEvaluateList() {
		return deptEvaluateList;
	}

	public void setSelectedRow(PensionLeaveExtend selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionLeaveExtend getSelectedRow() {
		return selectedRow;
	}

	public PensionLeaveapprove getApproveRecord() {
		return approveRecord;
	}

	public void setApproveRecord(PensionLeaveapprove approveRecord) {
		this.approveRecord = approveRecord;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

}
