package controller.logisticsManage;

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

import service.logisticsManage.AmmeterManageService;
import domain.logisticsManage.PensionAmmeterExtend;

public class AmmeterManageController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionAmmeterExtend> records = new ArrayList<PensionAmmeterExtend>();
	/**
	 * 被选中的记录
	 */
	private PensionAmmeterExtend[] selectedRows;
	/**
	 * 被录入的记录
	 */
	private PensionAmmeterExtend insertedRow = new PensionAmmeterExtend();
	/**
	 * 被修改的记录
	 */
	private PensionAmmeterExtend updatedRow = new PensionAmmeterExtend();
	/**
	 * 房间主键用于查询条件
	 */
	private Long roomId;
	/**
	 * 是否可用 用作查询条件
	 */
	private Integer validFlag = 0;
	/**
	 * 绑定新增时关于房间信息的输入法
	 */
	private String insertRoomNameSql;
	/**
	 * 绑定查询时关于房间信息的输入法
	 */
	private String selectRoomNameSql;
	/**
	 * 修改和删除按钮是否可用
	 */
	private boolean disUpdateButton = true;
	/**
	 * 修改删除和提交按钮是否可用
	 */
	private boolean disDeleteButton = true;
	/**
	 * 注入业务
	 */
	private transient AmmeterManageService ammeterManageService;

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
		selectAmmeterRecords();
	}

	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		setInsertRoomNameSql("select pr.id        as roomId,"
				+ "pr.name      as roomName,"
				+ "pr.floorName as floorName,"
				+ "pr.buildName as buildName "
				+ "from pension_room pr "
				+ "where pr.id not in "
				+ "(select distinct pa.room_id  from pension_ammeter pa where  pa.cleared = 2) "
				+ "and pr.cleared = 2");
		selectRoomNameSql = " select pr.id        as roomId,"
				+ "pr.name      as roomName," + "pr.floorName as floorName,"
				+ "pr.buildName as buildName " + "from pension_room pr "
				+ "where pr.cleared = 2";
	}

	/**
	 * 查询假期申请记录
	 */
	public void selectAmmeterRecords() {
		disDeleteButton = true;
		disUpdateButton = true;
		selectedRows = null;
		records = ammeterManageService.selectAmmeterRecords(roomId, validFlag);

	}

	/**
	 * 删除申请记录
	 */
	public void deleteAmmeterRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		for (PensionAmmeterExtend selectedRow : selectedRows) {
			ammeterManageService.deleteAmmeterRecord(selectedRow);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"删除成功！", "删除成功！");
			context.addMessage(null, message);
		}
		disDeleteButton = true;
		selectAmmeterRecords();

	}

	/**
	 * 录入申请记录
	 */
	public void insertAmmeterRecord() {

		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		try {

			ammeterManageService.insertAmmeterRecord(insertedRow);
			selectAmmeterRecords();

		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";

		} catch (Exception e) {

			e.getStackTrace();
			info = "出现未知异常，请联系系统管理员！";

		}
		clearInsertForm();
		insertedRow.setInitDatetime(new Date());
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
	public void updateAmmeterRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "修改成功";
		ammeterManageService.updateAmmeterRecord(updatedRow);
		selectAmmeterRecords();
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
	 * 
	 * 清空insertFrom
	 */
	public void clearInsertForm() {
		insertedRow = new PensionAmmeterExtend();
		insertedRow.setInitDatetime(new Date());
	}

	/**
	 * 情况selectForm
	 */
	public void clearSelectForm() {
		roomId = null;
		validFlag = 0;
	}

	/**
	 * 在后台对输入法填充的字段进行填充
	 */
	public void fillField(PensionAmmeterExtend filledRow) {
		if (filledRow.getRoomId() != null) {
			PensionAmmeterExtend extend = ammeterManageService.fillField(
					filledRow.getRoomId()).get(0);
			filledRow.setBuildId(extend.getBuildId());
			filledRow.setBuildName(extend.getBuildName());
			filledRow.setFloorId(extend.getFloorId());
			filledRow.setFloorName(extend.getFloorName());
			filledRow.setRoomName(extend.getRoomName());
		} else {
			filledRow.setBuildId(null);
			filledRow.setBuildName(null);
			filledRow.setFloorId(null);
			filledRow.setFloorName(null);
			filledRow.setRoomName(null);

		}

	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {

		setDisDeleteButton(false);
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
			setDisDeleteButton(true);
		}
	}

	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordUpdatedRow() {
		updatedRow = selectedRows[0];
	}

	public void setDisUpdateButton(boolean disUpdateButton) {
		this.disUpdateButton = disUpdateButton;
	}

	public boolean isDisUpdateButton() {
		return disUpdateButton;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}

	public Integer getValidFlag() {
		return validFlag;
	}

	public void setRecords(List<PensionAmmeterExtend> records) {
		this.records = records;
	}

	public List<PensionAmmeterExtend> getRecords() {
		return records;
	}

	public void setAmmeterManageService(
			AmmeterManageService ammeterManageService) {
		this.ammeterManageService = ammeterManageService;
	}

	public AmmeterManageService getAmmeterManageService() {
		return ammeterManageService;
	}

	public void setDisDeleteButton(boolean disDeleteButton) {
		this.disDeleteButton = disDeleteButton;
	}

	public boolean isDisDeleteButton() {
		return disDeleteButton;
	}

	public PensionAmmeterExtend[] getSelectedRows() {
		return selectedRows;
	}

	public void setSelectedRows(PensionAmmeterExtend[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	public PensionAmmeterExtend getInsertedRow() {
		return insertedRow;
	}

	public void setInsertedRow(PensionAmmeterExtend insertedRow) {
		this.insertedRow = insertedRow;
	}

	public PensionAmmeterExtend getUpdatedRow() {
		return updatedRow;
	}

	public void setUpdatedRow(PensionAmmeterExtend updatedRow) {
		this.updatedRow = updatedRow;
	}

	public void setInsertRoomNameSql(String insertRoomNameSql) {
		this.insertRoomNameSql = insertRoomNameSql;
	}

	public String getInsertRoomNameSql() {
		return insertRoomNameSql;
	}

	public void setSelectRoomNameSql(String selectRoomNameSql) {
		this.selectRoomNameSql = selectRoomNameSql;
	}

	public String getSelectRoomNameSql() {
		return selectRoomNameSql;
	}

}
