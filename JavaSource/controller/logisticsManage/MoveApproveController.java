package controller.logisticsManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.logisticsManage.MoveApproveService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionChangeroomExtend;
import domain.logisticsManage.PensionMoveApplyExtend;

public class MoveApproveController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionMoveApplyExtend> records = new ArrayList<PensionMoveApplyExtend>();
	/**
	 * 被选中的请假记录
	 */
	private PensionMoveApplyExtend selectedRow = new PensionMoveApplyExtend();
	/**
	 * 用来在页面中显示的调房记录
	 */
	// private List<PensionChangeroomExtend> changeRoomRecords = new
	// ArrayList<PensionChangeroomExtend>();
	/**
	 * 被选中的调房记录
	 */
	private PensionChangeroomExtend selectedChangeRoomRow = new PensionChangeroomExtend();
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
	/**
	 * 老人姓名 查询条件显示
	 */
	private String olderName;
	/**
	 * 请假申请表的主键，用在接收消息时做查询条件
	 */
	private Long applyId;
	/**
	 * 绑定关于老人信息的输入法
	 */
	private String olderNameSql;
	/**
	 * 绑定关于床位的输入法
	 */
	private String bedNameSql;
	/**
	 * 绑定搬家人输入法
	 */
	private String employeeNameSql;
	/**
	 * 确认按钮是否可用
	 */
	private boolean disEnsureButton = true;
	/**
	 * 调房记录是否可更新
	 */
	private boolean disUpdateButton = true;
	/**
	 * 调房记录是否可见
	 */
	private boolean changeRoomRenderedFlag = false;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee) SessionManager
			.getSessionAttribute(SessionManager.EMPLOYEE);
	/**
	 * 注入业务对象.
	 */
	private transient MoveApproveService moveApproveService;

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
		this.initDate();
		initSql();
		// 获取消息源发给本页面的参数
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String applyId = paramsMap.get("applyId");
		String oldId = paramsMap.get("olderId");
		if (applyId != null) {
			this.applyId = Long.valueOf(applyId);
			olderId = Long.valueOf(oldId);
			olderName = moveApproveService.selectOlderName(olderId);
		} else {
			this.applyId = null;
			olderId = null;
			olderName = "";
		}
		// 根据参数 和其余默认的查询条件查询出所有的搬家申请
		selectMoveApplicationRecords();
		if (applyId != null) {
			if (records != null && !records.isEmpty()) {
				PensionMoveApplyExtend record = records.get(0);
				if (record != null && applyId != null) {
					if (record.getMoveType() != null
							&& record.getMoveType() == 1) {
						selectedRow = record;
						olderId = selectedRow.getOlderId();
						olderName = selectedRow.getOlderName();
						disEnsureButton = false;
						changeRoomRenderedFlag = true;
						// changeRoomRecords =
						// moveApproveService.selectChangeroomApplicationRecords(record.getId());
					}
				}
			}
		}

		// 之后 将其至空
		this.applyId = null;
	}

	/**
	 * 将结束日期设置为今天，起始日期设置为一周前的今天
	 */
	public void initDate() {

		Calendar calendar = Calendar.getInstance();
		endDate = new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		endDate = calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) - 7);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate = calendar.getTime();

	}

	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		olderNameSql = "select po.name	as oldName,"
				+ "pbuilding.name	  as buildName," + "pf.name		  as floorName,"
				+ "pr.name		  as roomName," + "pb.name		  as bedName,"
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
				+ "on pf.build_id = pbuilding.id " + "where po.cleared = 2 "
				+ "and po.statuses = 3";

		bedNameSql = " select pb.id			 as newbedId,"
				+ "pb.name		    as newBedname,"
				+ "pr.id          as newRoomId,"
				+ "pr.name        as newRoomName,"
				+ "pf.id          as newFloorId,"
				+ "pf.name        as newFloorName,"
				+ "pbuilding.id   as newBuildId,"
				+ "pbuilding.name as newBuildName " + "from pension_bed pb "
				+ "join pension_room pr " + "on pb.room_id = pr.id "
				+ "join pension_floor pf " + "on pr.floor_id = pf.id "
				+ "join pension_building pbuilding "
				+ "on pf.build_id = pbuilding.id " + "where 1 = 1";

		employeeNameSql = "select pe.name as moverName,"
				+ "pe.inputCode as inputCode, " + "pd.name	as deptName,"
				+ "pe.id as moverId " + "from pension_employee pe "
				+ "join pension_dept pd " + "on pe.dept_id = pd.id "
				+ "where pe.cleared = 2";

	}

	/**
	 * 查询假期申请记录
	 */
	public void selectMoveApplicationRecords() {
		disEnsureButton = true;
		selectedRow = null;
		changeRoomRenderedFlag = false;
		// changeRoomRecords.clear();
		setRecords(moveApproveService.selectMoveApplicationRecords(startDate,
				endDate, olderId, ensureFlag, curEmployee, applyId));

	}

	/**
	 * 点击【同意】 若为调房，显示调房对话框，若为更换家具，直接同意
	 */
	public void checkApprove() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			if (selectedRow.getApprovalResult() == null) {
				// 更换家具
				if (selectedRow.getMoveType() == 2) {
					moveApproveService.approve(selectedRow,
							curEmployee.getId(), curEmployee.getDeptId(),
							curEmployee.getName());
					selectMoveApplicationRecords();
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_INFO, "审批成功！", "审批成功！");
					context.addMessage(null, message);
					// 调房
				} else if (selectedRow.getMoveType() == 1) {
					RequestContext request = RequestContext
							.getCurrentInstance();
					List<PensionChangeroomExtend> changeRoomExtends = moveApproveService
							.selectChangeroomApplicationRecords(selectedRow
									.getId());
					if (changeRoomExtends.size() > 0) {
						selectedChangeRoomRow = changeRoomExtends.get(0);
						request.addCallbackParam("success", true);
					} else {
						FacesMessage message = new FacesMessage(
								FacesMessage.SEVERITY_INFO, "没有相应调房记录！",
								"没有相应调房记录！");
						context.addMessage(null, message);
						request.addCallbackParam("success", false);
					}
				}
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "已审批的记录不能再审批！",
						"已审批的记录不能再审批！");
				context.addMessage(null, message);
			}
		} catch (PmsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 绑定前台的同意按钮
	 * 
	 * @throws PmsException
	 */
	public void approve() throws PmsException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"请先选中记录！", "请先选中记录！");
			context.addMessage(null, message);
		} else {
			if (selectedRow.getApprovalResult() == null) {
				moveApproveService
						.updateChangeroomApplicationRecord(selectedChangeRoomRow);
				moveApproveService.approve(selectedRow, curEmployee.getId(),
						curEmployee.getDeptId(), curEmployee.getName());
				selectedRow.setApprovalResult(1);// 审批通过
				selectMoveApplicationRecords();
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "审批成功！", "审批成功！");
				context.addMessage(null, message);
				RequestContext.getCurrentInstance().addCallbackParam("success",
						true);
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "已审批的记录不能再审批！",
						"已审批的记录不能再审批！");
				context.addMessage(null, message);
			}
		}
	}

	/**
	 * 绑定前台的不同意按钮
	 * 
	 * @throws PmsException
	 */
	public void refuse() throws PmsException {
		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow.getNote() != null) {
			moveApproveService.refuse(selectedRow, curEmployee.getId(),
					curEmployee.getDeptId(), curEmployee.getName());
			moveApproveService.refuseApprove(selectedRow.getId(),
					selectedRow.getNote());
			selectMoveApplicationRecords();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"审批成功！", "审批成功！");
			context.addMessage(null, message);
			request.addCallbackParam("success", true);
		} else {
			request.addCallbackParam("success", false);
		}
	}

	/**
	 * 
	 */
	public void checkResult() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow.getApprovalResult() == null) {
			selectedRow.setNote(null);
			request.addCallbackParam("success", true);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已审批的记录不能再审批！", "已审批的记录不能再审批！");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		}
	}

	/**
	 * 查询假期申请记录
	 */
	public void selectChangeroomApplicationRecords() {
		disUpdateButton = true;
		selectedChangeRoomRow = null;
		// changeRoomRecords =
		// moveApproveService.selectChangeroomApplicationRecords(selectedRow.getId());
	}

	/**
	 * 修改申请记录
	 */
	public void updateChangeroomApplicationRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "修改成功";
		moveApproveService
				.updateChangeroomApplicationRecord(selectedChangeRoomRow);
		selectChangeroomApplicationRecords();
		if (info.equals("修改成功")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);
			context.addMessage(null, message);
			request.addCallbackParam("success", true);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		}

	}

	public void view(Long applyId) {
		RequestContext request = RequestContext.getCurrentInstance();
		List<PensionChangeroomExtend> changeRooms = moveApproveService
				.selectChangeroomApplicationRecords(applyId);
		if (changeRooms.size() > 0) {
			selectedChangeRoomRow = changeRooms.get(0);
			request.addCallbackParam("success", true);
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"没有调房记录！", "没有调房记录！");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		}
	}

	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		olderId = null;
		ensureFlag = 0;
		olderName = "";
	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {

		this.disEnsureButton = false;
		/*
		 * //如果为调房类型的 if(selectedRow.getMoveType()==1){
		 * setChangeRoomRenderedFlag(true); }else{
		 * setChangeRoomRenderedFlag(false); }
		 * selectChangeroomApplicationRecords();
		 */

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {

		this.disEnsureButton = true;
		setChangeRoomRenderedFlag(false);
		// changeRoomRecords.clear();

	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectChangeRoomRecord(SelectEvent e) {

		this.setDisUpdateButton(false);

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectChangeRoomRecord(UnselectEvent e) {

		this.setDisUpdateButton(true);

	}

	public void fillUpdateNewBedField() {
		if (selectedChangeRoomRow.getNewbedId() != null) {
			PensionChangeroomExtend extend = moveApproveService.fillBedField(
					selectedChangeRoomRow.getNewbedId()).get(0);
			selectedChangeRoomRow.setNewBuildId(extend.getNewBuildId());
			selectedChangeRoomRow.setNewBuildName(extend.getNewBuildName());
			selectedChangeRoomRow.setNewFloorId(extend.getNewFloorId());
			selectedChangeRoomRow.setNewFloorName(extend.getNewFloorName());
			selectedChangeRoomRow.setNewRoomId(extend.getNewRoomId());
			selectedChangeRoomRow.setNewRoomName(extend.getNewRoomName());
		} else {
			selectedChangeRoomRow.setNewBuildId(null);
			selectedChangeRoomRow.setNewBuildName(null);
			selectedChangeRoomRow.setNewFloorId(null);
			selectedChangeRoomRow.setNewFloorName(null);
			selectedChangeRoomRow.setNewRoomId(null);
			selectedChangeRoomRow.setNewRoomName(null);
		}
	}

	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordUpdatedRow() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		boolean success = true;
		if (selectedRow.getApprovalResult() != null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该记录已经审批，不能修改!", "该记录已经审批，不能修改!");
			context.addMessage(null, message);
			success = false;
		}
		request.addCallbackParam("success", success);
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

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setRecords(List<PensionMoveApplyExtend> records) {
		this.records = records;
	}

	public List<PensionMoveApplyExtend> getRecords() {
		return records;
	}

	public void setMoveApproveService(MoveApproveService moveApproveService) {
		this.moveApproveService = moveApproveService;
	}

	public MoveApproveService getMoveApproveService() {
		return moveApproveService;
	}

	/*
	 * public void setChangeRoomRecords(List<PensionChangeroomExtend>
	 * changeRoomRecords) { this.changeRoomRecords = changeRoomRecords; }
	 * 
	 * public List<PensionChangeroomExtend> getChangeRoomRecords() { return
	 * changeRoomRecords; }
	 */

	public void setSelectedChangeRoomRow(
			PensionChangeroomExtend selectedChangeRoomRow) {
		this.selectedChangeRoomRow = selectedChangeRoomRow;
	}

	public PensionChangeroomExtend getSelectedChangeRoomRow() {
		return selectedChangeRoomRow;
	}

	public void setChangeRoomRenderedFlag(boolean changeRoomRenderedFlag) {
		this.changeRoomRenderedFlag = changeRoomRenderedFlag;
	}

	public boolean isChangeRoomRenderedFlag() {
		return changeRoomRenderedFlag;
	}

	public void setDisUpdateButton(boolean disUpdateButton) {
		this.disUpdateButton = disUpdateButton;
	}

	public boolean isDisUpdateButton() {
		return disUpdateButton;
	}

	public void setBedNameSql(String bedNameSql) {
		this.bedNameSql = bedNameSql;
	}

	public String getBedNameSql() {
		return bedNameSql;
	}

	public void setEmployeeNameSql(String employeeNameSql) {
		this.employeeNameSql = employeeNameSql;
	}

	public String getEmployeeNameSql() {
		return employeeNameSql;
	}

	public PensionMoveApplyExtend getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(PensionMoveApplyExtend selectedRow) {
		this.selectedRow = selectedRow;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

}
