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
import org.springframework.dao.DataAccessException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

import service.olderManage.EntryVacationApplicationService;
import service.olderManage.PensionLeaveExtend;
import util.DateUtil;
import util.PmsException;

public class EntryVacationApplicationController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionLeaveExtend> leaveRecords = new ArrayList<PensionLeaveExtend>();
	/**
	 * 被选中的记录
	 */
	private PensionLeaveExtend[] selectedRows;
	/**
	 * 被录入的记录
	 */
	private PensionLeaveExtend insertedRow = new PensionLeaveExtend();
	/**
	 * 被修改的记录
	 */
	private PensionLeaveExtend updatedRow = new PensionLeaveExtend();
	/**
	 * 绑定关于老人信息的输入法
	 */
	private String olderNameSql;
	/**
	 * 帮顶所有的员工姓名的输入法
	 */
	private String employeeNameSql;
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 老人主键用于查询条件
	 */
	private Long olderId;
	/**
	 * 是否已提交 用作查询条件
	 */
	private Integer submitFlag = 2;
	/**
	 * 修改和删除按钮是否可用
	 */
	private boolean disUpdateButton = true;
	/**
	 * 修改删除和提交按钮是否可用
	 */
	private boolean disDeleteAndSubmitButton = true;
	/**
	 * 注入业务
	 */
	private transient EntryVacationApplicationService entryVacationApplicationService;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee) SessionManager
			.getSessionAttribute(SessionManager.EMPLOYEE);

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
		startDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
		endDate = DateUtil.getBeforeDay(startDate, -7);
		initSql();
		selectVacationApplicationRecords();
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
				+ "and po.statuses < 5 " + "and po.statuses > 2 ";
		employeeNameSql = "select pe.name      as managerName,"
				+ "pe.inputCode as inputCode," + "pd.name      as deptName,"
				+ "pe.id        as managerId," + "pe.phone	  as phone "
				+ "from pension_employee pe " + "join pension_dept pd "
				+ "on pe.dept_id = pd.id " + "where pe.cleared = 2";

	}

	/**
	 * 查询假期申请记录
	 */
	public void selectVacationApplicationRecords() {
		disDeleteAndSubmitButton = true;
		disUpdateButton = true;
		selectedRows = null;
		leaveRecords = entryVacationApplicationService
				.selectVacationApplicationRecords(startDate, endDate, olderId,
						submitFlag);
		System.out.print("aaa");

	}

	/**
	 * 删除申请记录
	 */
	public void deleteVacationApplicationRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		for (PensionLeaveExtend selectedRow : selectedRows) {
			// 如果申请未被提交
			if (selectedRow.getSended() == null || selectedRow.getSended() == 2) {
				entryVacationApplicationService
						.deleteVacationApplicationRecord(selectedRow);
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "删除成功！", "删除成功！");
				context.addMessage(null, message);
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "已提交的记录不能删除！", "已提交的记录不能删除");
				context.addMessage(null, message);
			}

		}
		disDeleteAndSubmitButton = true;
		selectVacationApplicationRecords();

	}

	/**
	 * 录入申请记录
	 */
	public void insertVacationApplicationRecord() {

		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		try {
			if (checkTime(insertedRow)) {
				entryVacationApplicationService
						.insertVacationApplicationRecord(insertedRow);
			} else {
				request.addCallbackParam("success", false);
				return;
			}
			selectVacationApplicationRecords();
		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";

		} catch (Exception e) {

			e.getStackTrace();
			info = "出现未知异常，请联系系统管理员！";

		}
		clearInsertForm();
		if (info.equals("添加成功")) {
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

	/**
	 * 修改申请记录
	 */
	public void updateVacationApplicationRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "修改成功";
		if (checkTime(updatedRow)) {
			entryVacationApplicationService
					.updateVacationApplicationRecord(updatedRow);
			selectVacationApplicationRecords();
		} else {
			request.addCallbackParam("success", false);
			return;
		}
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

	/**
	 * 修改并提交请假申请记录
	 */
	public void updateAndSubmitVacationApplicationRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "修改成功";
		if (checkTime(updatedRow)) {
			entryVacationApplicationService
					.updateAndSubmitVacationApplicationRecord(updatedRow,
							curEmployee);
			selectVacationApplicationRecords();
		} else {
			request.addCallbackParam("success", false);
			return;
		}
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

	/**
	 * 提交申请记录
	 * 
	 * @throws PmsException
	 */
	public void submitVacationApplicationRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		for (PensionLeaveExtend selectedRow : selectedRows) {
			if (selectedRow.getSended() == null || selectedRow.getSended() == 2) {
				try {
					entryVacationApplicationService
							.submitVacationApplicationRecord(selectedRow,
									curEmployee);
				} catch (PmsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "提交成功！", "提交成功！");
				context.addMessage(null, message);
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "已提交的记录不能再提交！",
						"已提交的记录不能再提交！");
				context.addMessage(null, message);
			}
		}
		selectVacationApplicationRecords();
		clearInsertForm();
	}

	/**
	 * 插入并提交
	 * 
	 * @throws PmsException
	 */
	public void insertAndSubmitVacationApplicationRecord() throws PmsException {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "添加并提交成功";
		try {
			if (checkTime(insertedRow)) {
				entryVacationApplicationService
						.insertAndSubmitVacationApplicationRecord(insertedRow,
								curEmployee);
				selectVacationApplicationRecords();
			} else {
				request.addCallbackParam("success", false);
				return;
			}
		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";

		} catch (Exception e) {

			info = "出现未知异常，请联系系统管理员！";

		}
		clearInsertForm();
		if (info.equals("添加并提交成功")) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, info, info));
			request.addCallbackParam("success", true);
		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, info, info));
			request.addCallbackParam("success", false);
		}
	}

	public boolean checkTime(PensionLeaveExtend checkedRow) {

		FacesContext context = FacesContext.getCurrentInstance();
		Long expectBackTime = checkedRow.getExpectbacktime().getTime();
		Long expectLeaveTime = checkedRow.getExpectleavetime().getTime();
		Long curTime = new Date().getTime();
		if (expectLeaveTime <= curTime) {

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"预离院时间必须大于当前时间!", "预离院时间必须大于当前时间!");
			context.addMessage(null, message);
			return false;
		} else if (expectBackTime <= expectLeaveTime) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"预离院时间必须小于预返院时间!", "预离院时间必须小于预返院时间!");
			context.addMessage(null, message);
			return false;

		} else {
			return true;
		}

	}

	/**
	 * 
	 * 清空insertFrom
	 */
	public void clearInsertForm() {
		insertedRow = new PensionLeaveExtend();
	}

	/**
	 * 
	 * 清空updateFrom
	 */
	public void clearUpdateFrom() {
		updatedRow.setEscortname("");
		updatedRow.setEscortphone("");
		updatedRow.setExpectleavetime(null);
		updatedRow.setExpectbacktime(null);
		updatedRow.setLeavereason("");
	}

	/**
	 * 情况selectForm
	 */
	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		olderId = null;
		submitFlag = 0;
	}

	/**
	 * 在后台对输入法填充的字段进行填充
	 */
	public void fillField() {
		PensionLeaveExtend extend = entryVacationApplicationService.fillField(
				insertedRow.getOlderId()).get(0);
		insertedRow.setBedId(extend.getBedId());
		insertedRow.setBedName(extend.getBedName());
		insertedRow.setBuildId(extend.getBuildId());
		insertedRow.setBuildName(extend.getBuildName());
		insertedRow.setFloorId(extend.getFloorId());
		insertedRow.setFloorName(extend.getFloorName());
		insertedRow.setRoomId(extend.getRoomId());
		insertedRow.setRoomName(extend.getRoomName());

	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {

		setDisDeleteAndSubmitButton(false);
		if (selectedRows.length == 1) {
			setDisUpdateButton(false);
		} else {
			setDisUpdateButton(true);
		}

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		if (selectedRows.length == 1) {
			setDisUpdateButton(false);
		}
		if (selectedRows.length == 0) {
			setDisUpdateButton(true);
			setDisDeleteAndSubmitButton(true);
		}
	}

	public void checkDeletedRow() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		boolean success = true;
		if (selectedRows[0].getSended() == 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该记录已经提交，不能删除!", "该记录已经提交，不能删除!");
			context.addMessage(null, message);
			success = false;
		}
		request.addCallbackParam("success", success);
	}

	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordUpdatedRow() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		boolean success = true;
		updatedRow = selectedRows[0];
		if (updatedRow.getSended() == 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该记录已经提交，不能修改!", "该记录已经提交，不能修改!");
			context.addMessage(null, message);
			success = false;
		}
		request.addCallbackParam("success", success);
	}

	public void setEntryVacationApplicationService(
			EntryVacationApplicationService entryVacationApplicationService) {
		this.entryVacationApplicationService = entryVacationApplicationService;
	}

	public EntryVacationApplicationService getEntryVacationApplicationService() {
		return entryVacationApplicationService;
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

	public void setLeaveRecords(List<PensionLeaveExtend> leaveRecords) {
		this.leaveRecords = leaveRecords;
	}

	public List<PensionLeaveExtend> getLeaveRecords() {
		return leaveRecords;
	}

	public void setOlderNameSql(String olderNameSql) {
		this.olderNameSql = olderNameSql;
	}

	public String getOlderNameSql() {
		return olderNameSql;
	}

	public PensionLeaveExtend getInsertedRow() {
		return insertedRow;
	}

	public void setInsertedRow(PensionLeaveExtend insertedRow) {
		this.insertedRow = insertedRow;
	}

	public void setUpdatedRow(PensionLeaveExtend updatedRow) {
		this.updatedRow = updatedRow;
	}

	public PensionLeaveExtend getUpdatedRow() {
		return updatedRow;
	}

	public void setSelectedRows(PensionLeaveExtend[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	public PensionLeaveExtend[] getSelectedRows() {
		return selectedRows;
	}

	public void setDisUpdateButton(boolean disUpdateButton) {
		this.disUpdateButton = disUpdateButton;
	}

	public boolean isDisUpdateButton() {
		return disUpdateButton;
	}

	public void setDisDeleteAndSubmitButton(boolean disDeleteAndSubmitButton) {
		this.disDeleteAndSubmitButton = disDeleteAndSubmitButton;
	}

	public boolean isDisDeleteAndSubmitButton() {
		return disDeleteAndSubmitButton;
	}

	public void setSubmitFlag(Integer submitFlag) {
		this.submitFlag = submitFlag;
	}

	public Integer getSubmitFlag() {
		return submitFlag;
	}

	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}

	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public void setEmployeeNameSql(String employeeNameSql) {
		this.employeeNameSql = employeeNameSql;
	}

	public String getEmployeeNameSql() {
		return employeeNameSql;
	}

}
