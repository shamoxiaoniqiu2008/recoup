package controller.logisticsManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.dao.DataAccessException;

import service.logisticsManage.PensionRepairExtend;
import service.logisticsManage.RepairApplyService;
import service.olderManage.EntryVacationApplicationService;
import service.olderManage.PensionLeaveExtend;
import util.PmsException;

import com.centling.his.util.SessionManager;
import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionCheckExtend;

public class RepairApplyController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionRepairExtend> records = new ArrayList<PensionRepairExtend>();
	/**
	 * 被选中的记录
	 */
	private PensionRepairExtend[] selectedRows;
	/**
	 * 被录入的记录
	 */
	private PensionRepairExtend insertedRow = new PensionRepairExtend();
	/**
	 * 被修改的记录
	 */
	private PensionRepairExtend updatedRow = new PensionRepairExtend();
	/**
	 * 绑定关于老人信息的输入法
	 */
	private String olderNameSql;
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
	 * 维修人主键用于查询条件
	 */
	private Long repairerId;
	/**
	 * 是否已提交 用作查询条件
	 */
	private Integer sendFlag = 2;
	/**
	 * 是否已处理 用作查询条件
	 */
	private Integer handleFlag = 0;
	/**
	 * 修改和删除按钮是否可用
	 */
	private boolean disUpdateButton = true;
	/**
	 * 修改删除和提交按钮是否可用
	 */
	private boolean disDeleteAndSubmitButton = true;
	/**
	 * 用来绑定insertForm中RoomText是否可用
	 */
	private boolean roomTextReadOnlyFlag = true;
	/**
	 * 用来绑定insertForm中olderName是否可见
	 */
	private boolean olderNameRenderedFlag = true;
	/**
	 * 用来绑定updateForm中olderName是否可见
	 */
	private boolean updateOlderNameRenderedFlag = true;
	/**
	 * 注入业务
	 */
	private transient RepairApplyService repairApplyService;
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
		initSql();
		this.initDate();
		insertedRow.setRepairType(1);
		selectRecords();
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
	}

	/**
	 * 查询假期申请记录
	 */
	public void selectRecords() {
		disDeleteAndSubmitButton = true;
		disUpdateButton = true;
		selectedRows = null;
		setRecords(repairApplyService.selectRepairApplicationRecords(startDate,
				endDate, olderId, curEmployee.getId(), sendFlag, handleFlag));

	}

	/**
	 * 删除申请记录
	 */
	public void deleteRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		for (PensionRepairExtend selectedRow : selectedRows) {
			// 如果申请未被提交
			if (selectedRow.getSendFlag() == null
					|| selectedRow.getSendFlag() == 2) {
				repairApplyService.deleteRepairApplicationRecord(selectedRow);
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
		selectRecords();

	}

	/**
	 * 录入申请记录
	 */
	public void insertRecord() {

		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		try {

			insertedRow.setManagerId(curEmployee.getId());
			if (insertedRow.getRepairType() == 1
					&& insertedRow.getOlderId() == null) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "个人申请必须填写老人信息！",
						"个人申请必须填写老人信息！");
				context.addMessage(null, message);
				request.addCallbackParam("success", false);
				return;
			} else {
				insertedRow.setApplyTime(new Date());
				repairApplyService.insertRepairApplicationRecord(insertedRow);
				selectRecords();
			}

		} catch (DataAccessException e) {
			System.out.print(e);
			e.getStackTrace();
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
	public void updateRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "修改成功";
		repairApplyService.updateRepairApplicationRecord(updatedRow);
		selectRecords();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				info, info);
		context.addMessage(null, message);
		request.addCallbackParam("success", true);
	}

	/**
	 * 提交申请记录
	 * 
	 * @throws PmsException
	 */
	public void submitRecord() throws PmsException {

		FacesContext context = FacesContext.getCurrentInstance();
		for (PensionRepairExtend selectedRow : selectedRows) {
			if (selectedRow.getSendFlag() == null
					|| selectedRow.getSendFlag() == 2) {
				repairApplyService.submitRepairApplicationRecord(selectedRow,
						curEmployee);
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
		selectRecords();
		clearInsertForm();
	}

	/**
	 * 插入并提交
	 * 
	 * @throws PmsException
	 */
	public void insertAndSubmitRecord() throws PmsException {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "添加并提交成功";
		try {
			insertedRow.setManagerId(curEmployee.getId());
			repairApplyService.insertRepairApplicationRecord(insertedRow);
			repairApplyService.submitRepairApplicationRecord(insertedRow,
					curEmployee);
			selectRecords();

		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";

		} catch (Exception e) {

			info = "出现未知异常，请联系系统管理员！";

		}
		clearInsertForm();
		if (info.equals("添加并提交成功")) {
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
	 * 
	 * 清空insertFrom
	 */
	public void clearInsertForm() {
		insertedRow = new PensionRepairExtend();
		insertedRow.setRepairType(1);
		olderNameRenderedFlag = true;
		roomTextReadOnlyFlag = true;
	}

	/**
	 * 情况selectForm
	 */
	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		olderId = null;
		repairerId = null;
		sendFlag = 0;
		handleFlag = 0;
	}

	/**
	 * 当调用老人输入法时在后台对输入法填充的字段进行填充
	 */
	public void fillField() {

		if (insertedRow.getOlderName() != null
				&& !"".equals(insertedRow.getOlderName())) {
			PensionLeaveExtend extend = entryVacationApplicationService
					.fillField(insertedRow.getOlderId()).get(0);
			insertedRow.setBedId(extend.getBedId());
			insertedRow.setBedName(extend.getBedName());
			insertedRow.setBuildId(extend.getBuildId());
			insertedRow.setBuildName(extend.getBuildName());
			insertedRow.setFloorId(extend.getFloorId());
			insertedRow.setFloorName(extend.getFloorName());
			insertedRow.setRoomId(extend.getRoomId());
			insertedRow.setRoomName(extend.getRoomName());
		} else {
			insertedRow.setOlderId(null);
			insertedRow.setOlderName(null);
			insertedRow.setFloorId(null);
			insertedRow.setFloorName(null);
			insertedRow.setRoomName(null);
			insertedRow.setRoomId(null);
			insertedRow.setBuildId(null);
			insertedRow.setBuildName(null);
			insertedRow.setBedId(null);
			insertedRow.setBedName(null);
		}

	}

	/**
	 * 当调用房间输入法时在后台对输入法填充的字段进行填充
	 */
	public void fillRoomField() {

		if (insertedRow.getRoomName() != null
				&& !"".equals(insertedRow.getRoomName())) {
			List<PensionCheckExtend> extendss = repairApplyService
					.fillField(insertedRow.getRoomId());
			PensionCheckExtend extend = extendss.get(0);
			insertedRow.setOlderId(null);
			insertedRow.setOlderName(null);
			insertedRow.setFloorId(extend.getFloorId());
			insertedRow.setFloorName(extend.getFloorName());
			insertedRow.setRoomName(extend.getRoomName());
			insertedRow.setBuildId(extend.getBuildId());
			insertedRow.setBuildName(extend.getBuildName());
		} else {
			insertedRow.setOlderId(null);
			insertedRow.setOlderName(null);
			insertedRow.setFloorId(null);
			insertedRow.setFloorName(null);
			insertedRow.setRoomName(null);
			insertedRow.setRoomId(null);
			insertedRow.setBuildId(null);
			insertedRow.setBuildName(null);
			insertedRow.setBedId(null);
			insertedRow.setBedName(null);
		}

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
		if (selectedRows[0].getSendFlag() == 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该记录已经提交，不能删除!", "该记录已经提交，不能删除!");
			context.addMessage(null, message);
			success = false;
			return;
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
		if (updatedRow.getSendFlag() == 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该记录已经提交，不能修改!", "该记录已经提交，不能修改!");
			context.addMessage(null, message);
			success = false;
			return;
		}
		Integer type = updatedRow.getRepairType();
		if (type == 1) {
			updateOlderNameRenderedFlag = true;
		} else {
			updateOlderNameRenderedFlag = false;
		}

		request.addCallbackParam("success", success);
	}

	/**
	 * 点击前台的维修类型 根据具体类型 来改变insertDialog的布局
	 */
	public void changeInsertDialog() {

		Integer type = insertedRow.getRepairType();
		// 个人维修
		if (type != null && type == 2) {
			olderNameRenderedFlag = false;
			roomTextReadOnlyFlag = false;
		} else {
			olderNameRenderedFlag = true;
			roomTextReadOnlyFlag = true;

		}

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

	public void setOlderNameSql(String olderNameSql) {
		this.olderNameSql = olderNameSql;
	}

	public String getOlderNameSql() {
		return olderNameSql;
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

	public void setRepairApplyService(RepairApplyService repairApplyService) {
		this.repairApplyService = repairApplyService;
	}

	public RepairApplyService getRepairApplyService() {
		return repairApplyService;
	}

	public void setRecords(List<PensionRepairExtend> records) {
		this.records = records;
	}

	public List<PensionRepairExtend> getRecords() {
		return records;
	}

	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

	public Integer getSendFlag() {
		return sendFlag;
	}

	public void setHandleFlag(Integer handleFlag) {
		this.handleFlag = handleFlag;
	}

	public Integer getHandleFlag() {
		return handleFlag;
	}

	public void setRepairerId(Long repairerId) {
		this.repairerId = repairerId;
	}

	public Long getRepairerId() {
		return repairerId;
	}

	public PensionRepairExtend[] getSelectedRows() {
		return selectedRows;
	}

	public void setSelectedRows(PensionRepairExtend[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	public PensionRepairExtend getInsertedRow() {
		return insertedRow;
	}

	public void setInsertedRow(PensionRepairExtend insertedRow) {
		this.insertedRow = insertedRow;
	}

	public PensionRepairExtend getUpdatedRow() {
		return updatedRow;
	}

	public void setUpdatedRow(PensionRepairExtend updatedRow) {
		this.updatedRow = updatedRow;
	}

	public EntryVacationApplicationService getEntryVacationApplicationService() {
		return entryVacationApplicationService;
	}

	public void setEntryVacationApplicationService(
			EntryVacationApplicationService entryVacationApplicationService) {
		this.entryVacationApplicationService = entryVacationApplicationService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setRoomTextReadOnlyFlag(boolean roomTextReadOnlyFlag) {
		this.roomTextReadOnlyFlag = roomTextReadOnlyFlag;
	}

	public boolean isRoomTextReadOnlyFlag() {
		return roomTextReadOnlyFlag;
	}

	public void setOlderNameRenderedFlag(boolean olderNameRenderedFlag) {
		this.olderNameRenderedFlag = olderNameRenderedFlag;
	}

	public boolean isOlderNameRenderedFlag() {
		return olderNameRenderedFlag;
	}

	public void setUpdateOlderNameRenderedFlag(
			boolean updateOlderNameRenderedFlag) {
		this.updateOlderNameRenderedFlag = updateOlderNameRenderedFlag;
	}

	public boolean isUpdateOlderNameRenderedFlag() {
		return updateOlderNameRenderedFlag;
	}

}
