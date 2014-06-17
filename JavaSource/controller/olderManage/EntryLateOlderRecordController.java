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
import org.springframework.dao.DataAccessException;

import domain.olderManage.PensionLeave;

import service.olderManage.EntryLateOlderRecordService;
import service.olderManage.PensionNotbackontimeExtend;

public class EntryLateOlderRecordController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionNotbackontimeExtend> records = new ArrayList<PensionNotbackontimeExtend>();
	/**
	 * 被选中的记录
	 */
	private PensionNotbackontimeExtend selectedRow = new PensionNotbackontimeExtend();
	/**
	 * 被录入的记录
	 */
	private PensionNotbackontimeExtend insertedRow = new PensionNotbackontimeExtend();
	/**
	 * 被修改的记录
	 */
	private PensionNotbackontimeExtend updatedRow = new PensionNotbackontimeExtend();
	/**
	 * 绑定关于老人请假离院的输入法
	 */
	private String leaveSql;
	/**
	 * 绑定请假未离院老人姓名的输入法
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
	private Integer olderId;
	/**
	 * 是否返院 用作查询条件
	 */
	private Integer backFlag = 2;
	/**
	 * 修改删除和提交按钮是否可用
	 */
	private boolean disDeleteAndUpdateButton = true;
	/**
	 * 注入业务
	 */
	private transient EntryLateOlderRecordService entryLateOlderRecordService;
	/**
	 * 
	 */
	private Long leaveId;

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
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String recordId = paramsMap.get("recordId");
		if (recordId != null) {
			leaveId = Long.parseLong(recordId);
		}
		initSql();
		selectLateOlderRecords();
	}

	public void checkInit() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (leaveId != null) {
			PensionLeave leaveRecord = entryLateOlderRecordService
					.selectLeaveRecord(leaveId);
			insertedRow.setOlderId(leaveRecord.getOlderId());
			insertedRow.setOlderName(entryLateOlderRecordService
					.selectOlderName(insertedRow.getOlderId()));
			insertedRow.setLeaveId(leaveId);
			fillField();
			requestContext.addCallbackParam("validate", true);
		} else {
			requestContext.addCallbackParam("validate", false);
		}
	}

	/**
	 * 初始化sql语句
	 */
	public void initSql() {

		leaveSql = "	select distinct  "
				+ "po.name			     as olderName,"
				+ "DATE_FORMAT(pl.realleavetime, '%Y-%m-%d %H:%i')  as realleavetime,"
				+ // 因为还没有引入指纹机 暂时用预离院时间 代替离院时间
				"DATE_FORMAT(pl.expectBackTime,'%Y-%m-%d %H:%i')    as oldbacktime,"
				+ "pl.escortName      as escortname,"
				+ "pl.escortPhone     as escortphone,"
				+ "po.inputCode		 as inputCode,"
				+ "pl.id			     as leaveId,"
				+ "pl.older_id		 as olderId,"
				+ "pbuilding.name	  as buildName,"
				+ "pf.name			     as floorName,"
				+ "pr.name			     as roomName,"
				+ "pb.name			     as bedName,"
				+ "pb.id              as bedId,"
				+ "pr.id              as roomId,"
				+ "pf.id     	        as floorId,"
				+ "pbuilding.id       as buildId,"
				+ "pl.cleared as cleared from pension_leave pl,"
				+ "pension_older po,pension_livingRecord plr,"
				+ "pension_bed pb,  pension_room pr,"
				+ "pension_floor pf ,pension_building pbuilding "
				+ "where po.id=pl.older_id and plr.older_id=po.id "
				+ "and  plr.bed_id = pb.id and pb.room_id = pr.id "
				+ "and pr.floor_id = pf.id and pf.build_id = pbuilding.id  and po.cleared=2 "
				+ "and pl.cleared = 2 and pl.isLeaved = 1 and pl.isBacked = 2 and po.statuses=4";
		olderNameSql = "select distinct po.name as oldName,"
				+ "pb.buildName as buildName,pb.floorName as floorName,"
				+ "pb.roomName as roomName,"
				+ "pb.name	as bedName,po.id as olderId,"
				+ "po.inputCode as inputCode  "
				+ " from pension_older po,pension_livingRecord plr,"
				+ " pension_bed pb,pension_leave pl "
				+ "where po.id = plr.older_id and plr.bed_id = pb.id "
				+ "and po.id=pl.older_id "
				+ " and po.cleared = 2 and pl.backOnTime=2 and pl.cleared=2";
	}

	/**
	 * 查询假期申请记录
	 */
	public void selectLateOlderRecords() {
		disDeleteAndUpdateButton = true;
		setRecords(entryLateOlderRecordService.selectLateOlderRecords(
				startDate, endDate, olderId, backFlag));

	}

	/**
	 * 删除申请记录
	 */
	public void deleteLateOlderRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		entryLateOlderRecordService.deleteLateOlderRecord(selectedRow);
		selectLateOlderRecords();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"删除成功！", "删除成功！");
		context.addMessage(null, message);
		disDeleteAndUpdateButton = true;

	}

	/**
	 * 录入申请记录
	 */
	public void insertLateOlderRecord() {

		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		try {
			if (checkTime(insertedRow)) {
				entryLateOlderRecordService.insertLateOlderRecord(insertedRow);
				selectLateOlderRecords();
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
	public void updateLateOlderRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (checkTime(updatedRow)) {
			entryLateOlderRecordService.updateLateOlderRecord(updatedRow);
			selectLateOlderRecords();
		} else {
			request.addCallbackParam("success", false);
			return;
		}
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"修改成功", "修改成功");
		context.addMessage(null, message);
		request.addCallbackParam("success", true);
	}

	public boolean checkTime(PensionNotbackontimeExtend checkedRow) {

		FacesContext context = FacesContext.getCurrentInstance();
		Long oldBackTime = checkedRow.getOldbacktime().getTime();
		Long newBackTime = checkedRow.getNewbacktime().getTime();
		if (newBackTime < oldBackTime) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"推迟时间不能小于预返院时间!", "推迟时间不能小于预返院时间!");
			context.addMessage(null, message);
			return false;

		} else {
			return true;
		}
	}

	/**
	 * 在后台对输入法填充的字段进行填充
	 */
	public void fillField() {
		PensionNotbackontimeExtend extend = entryLateOlderRecordService
				.fillField(insertedRow.getOlderId()).get(0);
		insertedRow.setBedId(extend.getBedId());
		insertedRow.setBedName(extend.getBedName());
		insertedRow.setBuildId(extend.getBuildId());
		insertedRow.setBuildName(extend.getBuildName());
		insertedRow.setFloorId(extend.getFloorId());
		insertedRow.setFloorName(extend.getFloorName());
		insertedRow.setRoomId(extend.getRoomId());
		insertedRow.setRoomName(extend.getRoomName());
		insertedRow.setEscortname(extend.getEscortname());
		insertedRow.setEscortphone(extend.getEscortphone());
		insertedRow.setRealleavetime(extend.getRealleavetime());
		insertedRow.setOldbacktime(extend.getOldbacktime());

	}

	/**
	 * 
	 * 清空insertFrom
	 */
	public void clearInsertForm() {
		insertedRow = new PensionNotbackontimeExtend();
	}

	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		olderId = null;
		backFlag = 0;
	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		setDisDeleteAndUpdateButton(false);
	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		setDisDeleteAndUpdateButton(true);
	}

	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordUpdatedRow() {
		// CloneUtil.deepCopy();
		// 浅拷贝
		updatedRow = selectedRow;
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

	public void setOlderId(Integer olderId) {
		this.olderId = olderId;
	}

	public Integer getOlderId() {
		return olderId;
	}

	public void setDisDeleteAndUpdateButton(boolean disDeleteAndUpdateButton) {
		this.disDeleteAndUpdateButton = disDeleteAndUpdateButton;
	}

	public boolean isDisDeleteAndUpdateButton() {
		return disDeleteAndUpdateButton;
	}

	public void setEntryLateOlderRecordService(
			EntryLateOlderRecordService entryLateOlderRecordService) {
		this.entryLateOlderRecordService = entryLateOlderRecordService;
	}

	public EntryLateOlderRecordService getEntryLateOlderRecordService() {
		return entryLateOlderRecordService;
	}

	public void setRecords(List<PensionNotbackontimeExtend> records) {
		this.records = records;
	}

	public List<PensionNotbackontimeExtend> getRecords() {
		return records;
	}

	public void setSelectedRow(PensionNotbackontimeExtend selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setInsertedRow(PensionNotbackontimeExtend insertedRow) {
		this.insertedRow = insertedRow;
	}

	public void setUpdatedRow(PensionNotbackontimeExtend updatedRow) {
		this.updatedRow = updatedRow;
	}

	public PensionNotbackontimeExtend getSelectedRow() {
		return selectedRow;
	}

	public PensionNotbackontimeExtend getInsertedRow() {
		return insertedRow;
	}

	public PensionNotbackontimeExtend getUpdatedRow() {
		return updatedRow;
	}

	public void setLeaveSql(String leaveSql) {
		this.leaveSql = leaveSql;
	}

	public String getLeaveSql() {
		return leaveSql;
	}

	public Integer getBackFlag() {
		return backFlag;
	}

	public void setBackFlag(Integer backFlag) {
		this.backFlag = backFlag;
	}

	public void setOlderNameSql(String olderNameSql) {
		this.olderNameSql = olderNameSql;
	}

	public String getOlderNameSql() {
		return olderNameSql;
	}

	public Long getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
	}
}
