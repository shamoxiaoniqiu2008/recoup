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
import org.springframework.dao.DataAccessException;

import service.logisticsManage.MoveApplyService;
import service.olderManage.PensionLeaveExtend;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionChangeroomExtend;
import domain.logisticsManage.PensionMoveApplyExtend;

public class MoveApplyController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3176310085853326759L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionMoveApplyExtend> records = new ArrayList<PensionMoveApplyExtend>();
	/**
	 * 被选中的记录
	 */
	private PensionMoveApplyExtend selectedRow = new PensionMoveApplyExtend();
	/**
	 * 被录入的记录
	 */
	private PensionMoveApplyExtend insertedRow = new PensionMoveApplyExtend();
	/**
	 * 被修改的记录
	 */
	private PensionMoveApplyExtend updatedRow = new PensionMoveApplyExtend();
	/**
	 * 用来在页面中显示的调房记录
	 */
	private List<PensionChangeroomExtend> changeRoomRecords = new ArrayList<PensionChangeroomExtend>();
	/**
	 * 被选中的调房记录
	 */
	private PensionChangeroomExtend selectedChangeRoomRow = new PensionChangeroomExtend();
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
	 * 申请主键 用作查询条件
	 */
	private Long applyId;
	/**
	 * 是否已提交 用作查询条件
	 */
	private Integer sendFlag = 0;
	/**
	 * 审批结果 用作查询条件
	 */
	private Integer approveFlag;
	/**
	 * 修改和删除按钮是否可用
	 */
	private boolean disUpdateButton = true;
	/**
	 * 修改删除和提交按钮是否可用
	 */
	private boolean disDeleteAndSubmitButton = true;
	/**
	 * 调房记录是否可见
	 */
	private boolean changeRoomRenderedFlag = false;
	/**
	 * 注入业务
	 */
	private transient MoveApplyService moveApplyService;
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
		this.initDate();
		initSql();
		// 获取消息源发给本页面的参数
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String applyId = paramsMap.get("applyId");
		if (applyId != null) {
			this.setApplyId(Long.valueOf(applyId));
		} else {
			this.setApplyId(null);
		}
		// 根据参数 和其余默认的查询条件查询出所有的请假申请
		selectMoveApplicationRecords();
		if (applyId != null) {
			if (records != null && !records.isEmpty()) {
				PensionMoveApplyExtend record = records.get(0);
				if (record != null && applyId != null) {
					if (record.getMoveType() != null
							&& record.getMoveType() == 1) {
						selectedRow = record;
						setDisDeleteAndSubmitButton(false);
						setDisUpdateButton(false);
						changeRoomRenderedFlag = true;
						changeRoomRecords = moveApplyService
								.selectChangeroomApplicationRecords(record
										.getId());
					}
				}

			}
		}
		// 之后 将其至空
		this.setApplyId(null);
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
	public void selectChangeroomApplicationRecords() {
		selectedChangeRoomRow = null;
		changeRoomRecords = moveApplyService
				.selectChangeroomApplicationRecords(selectedRow.getId());
	}

	/**
	 * 查询假期申请记录
	 */
	public void selectMoveApplicationRecords() {
		disDeleteAndSubmitButton = true;
		disUpdateButton = true;
		selectedRow = null;
		changeRoomRecords.clear();
		changeRoomRenderedFlag = false;
		records = moveApplyService.selectMoveApplicationRecords(startDate,
				endDate, olderId, sendFlag, approveFlag, applyId);
	}

	/**
	 * 删除申请记录
	 */
	public void deleteMoveApplicationRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		// 如果申请未被提交
		if (selectedRow.getSendFlag() == null || selectedRow.getSendFlag() == 2) {
			moveApplyService.deleteMoveApplicationRecord(selectedRow);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"删除成功！", "删除成功！");
			context.addMessage(null, message);
			disDeleteAndSubmitButton = true;
			selectMoveApplicationRecords();
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已提交的记录不能删除！", "已提交的记录不能删除");
			context.addMessage(null, message);
		}

	}

	/**
	 * 录入申请记录
	 */
	public void insertMoveApplicationRecord() {

		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		try {
			insertedRow.setApplyTime(new Date());
			moveApplyService.insertMoveApplicationRecord(insertedRow);
			selectMoveApplicationRecords();
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
	public void updateMoveApplicationRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "修改成功";
		moveApplyService.updateMoveApplicationRecord(updatedRow);
		selectMoveApplicationRecords();
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
	public void submitMoveApplicationRecord() throws PmsException {

		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow.getSendFlag() == null || selectedRow.getSendFlag() == 2) {
			moveApplyService.submitMoveApplicationRecord(selectedRow,
					curEmployee);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"提交成功！", "提交成功！");
			context.addMessage(null, message);
			selectMoveApplicationRecords();
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已提交的记录不能再提交！", "已提交的记录不能再提交！");
			context.addMessage(null, message);
		}

	}

	/**
	 * 插入并提交
	 * 
	 * @throws PmsException
	 */
	public void insertAndSubmitMoveApplicationRecord() throws PmsException {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "添加并提交成功";
		try {
			moveApplyService.insertMoveApplicationRecord(insertedRow);
			moveApplyService.submitMoveApplicationRecord(insertedRow,
					curEmployee);
			selectMoveApplicationRecords();

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

	public boolean checkTime(PensionLeaveExtend checkedRow) {

		FacesContext context = FacesContext.getCurrentInstance();
		Long expectBackTime = checkedRow.getExpectbacktime().getTime();
		Long expectLeaveTime = checkedRow.getExpectleavetime().getTime();
		Long curTime = new Date().getTime();
		if (expectLeaveTime < curTime) {

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"预离院时间必须大于当前时间!", "预离院时间必须大于当前时间!");
			context.addMessage(null, message);
			return false;
		} else if (expectBackTime < expectLeaveTime) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"预离院时间必须小于预返院时间!", "预离院时间必须小于预返院时间!");
			context.addMessage(null, message);
			return false;

		} else {
			return true;
		}

	}

	/**
	 * 处理
	 */
	public void handleMoveApplicationRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		moveApplyService.handleMoveApplicationRecord(selectedRow, curEmployee,
				changeRoomRecords);
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"处理成功！", "处理成功！");
		context.addMessage(null, message);
		selectMoveApplicationRecords();
		clearInsertForm();

	}

	public void checkBeHandled() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		// 审批通过
		if (selectedRow.getApprovalResult() != null
				&& selectedRow.getApprovalResult() == 1) {
			if (selectedRow.getFinishFlag() == 2) {
				request.addCallbackParam("success", true);
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "已处理的记录不能再处理！",
						"已处理的记录不能再处理！");
				context.addMessage(null, message);
				request.addCallbackParam("success", false);
			}

		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"未审批的和审批不通过的不能处理！", "未审批的审批不通过的不能 处理！");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		}

	}

	/**
	 * 将已交款字段置为已交款
	 * 
	 * @throws PmsException
	 */
	public void payMoveApplicationRecord() throws PmsException {
		moveApplyService.payMoveApplicationRecord(selectedRow);
	}

	/**
	 * 
	 * 清空insertFrom
	 */
	public void clearInsertForm() {
		insertedRow = new PensionMoveApplyExtend();
	}

	/**
	 * 情况selectForm
	 */
	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		olderId = null;
		sendFlag = 0;
		approveFlag = 0;
	}

	/**
	 * 在后台对输入法填充的字段进行填充
	 */
	public void fillField() {
		PensionMoveApplyExtend extend = moveApplyService.fillField(
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
		setDisUpdateButton(false);
		// 如果为调房类型的
		if (selectedRow.getMoveType() == 1 && selectedRow.getSendFlag() == 1) {
			setChangeRoomRenderedFlag(true);
		} else {
			setChangeRoomRenderedFlag(false);
		}
		selectChangeroomApplicationRecords();

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		setDisUpdateButton(true);
		setDisDeleteAndSubmitButton(true);
		setChangeRoomRenderedFlag(false);
		changeRoomRecords.clear();
	}

	public void checkDeletedRow() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		boolean success = true;
		if (selectedRow.getSendFlag() == 1) {
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
		updatedRow = selectedRow;
		if (updatedRow.getSendFlag() == 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该记录已经提交，不能修改!", "该记录已经提交，不能修改!");
			context.addMessage(null, message);
			success = false;
		}
		request.addCallbackParam("success", success);
	}

	/**
	 * 判断一下被选中的行是否已经缴费
	 * 
	 * @throws PmsException
	 */
	public void checkBePayed() throws PmsException {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		boolean success = true;
		if (selectedRow.getFinishFlag() != null
				&& selectedRow.getFinishFlag() == 2) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"未处理的记录不能缴费！", "未处理的记录不能缴费！");
			context.addMessage(null, message);
			success = false;
		}
		if (selectedRow.getPayFlag() != null && selectedRow.getPayFlag() == 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已缴费的记录不能再缴费！", "已缴费的记录不能再缴费！");
			context.addMessage(null, message);
			success = false;
		}
		if (success) {
			request.addCallbackParam("olderId", selectedRow.getOlderId());
			String priceTypeId = moveApplyService.getPriceTypeId();
			request.addCallbackParam("priceTypeId", priceTypeId);
			request.addCallbackParam("tableName", "pension_move_apply");
			request.addCallbackParam("applyId", selectedRow.getId());
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

	public void setOlderNameSql(String olderNameSql) {
		this.olderNameSql = olderNameSql;
	}

	public String getOlderNameSql() {
		return olderNameSql;
	}

	public PensionMoveApplyExtend getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(PensionMoveApplyExtend selectedRow) {
		this.selectedRow = selectedRow;
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
		this.sendFlag = submitFlag;
	}

	public Integer getSubmitFlag() {
		return sendFlag;
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

	public void setMoveApplyService(MoveApplyService moveApplyService) {
		this.moveApplyService = moveApplyService;
	}

	public MoveApplyService getMoveApplyService() {
		return moveApplyService;
	}

	public void setRecords(List<PensionMoveApplyExtend> records) {
		this.records = records;
	}

	public List<PensionMoveApplyExtend> getRecords() {
		return records;
	}

	public PensionMoveApplyExtend getInsertedRow() {
		return insertedRow;
	}

	public void setInsertedRow(PensionMoveApplyExtend insertedRow) {
		this.insertedRow = insertedRow;
	}

	public PensionMoveApplyExtend getUpdatedRow() {
		return updatedRow;
	}

	public void setUpdatedRow(PensionMoveApplyExtend updatedRow) {
		this.updatedRow = updatedRow;
	}

	public void setApproveFlag(Integer approveFlag) {
		this.approveFlag = approveFlag;
	}

	public Integer getApproveFlag() {
		return approveFlag;
	}

	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}

	public Integer getSendFlag() {
		return sendFlag;
	}

	public void setChangeRoomRecords(
			List<PensionChangeroomExtend> changeRoomRecords) {
		this.changeRoomRecords = changeRoomRecords;
	}

	public List<PensionChangeroomExtend> getChangeRoomRecords() {
		return changeRoomRecords;
	}

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

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getApplyId() {
		return applyId;
	}
}
