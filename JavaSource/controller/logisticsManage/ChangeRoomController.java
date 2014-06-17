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

import service.logisticsManage.ChangeRoomService;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionChangeroomExtend;

public class ChangeRoomController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6163663064727447767L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionChangeroomExtend> records = new ArrayList<PensionChangeroomExtend>();
	/**
	 * 被选中的记录
	 */
	private PensionChangeroomExtend[] selectedRows;
	/**
	 * 被录入的记录
	 */
	private PensionChangeroomExtend insertedRow = new PensionChangeroomExtend();
	/**
	 * 被修改的记录
	 */
	private PensionChangeroomExtend updatedRow = new PensionChangeroomExtend();
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
	 * 修改按钮是否可用
	 */
	private boolean disUpdateButton = true;
	/**
	 * 删除是否可用
	 */
	private boolean disDeleteButton = true;
	/**
	 * 注入业务
	 */
	private transient ChangeRoomService changeRoomService;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee)SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
	
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
		selectChangeroomApplicationRecords();
	}
	
	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		olderNameSql = "select po.name	as oldName," +
				"pbuilding.name	  as buildName," +
				"pf.name		  as floorName," +
				"pr.name		  as roomName," +
				"pb.name		  as bedName," +
				"po.id       		  as olderId," +
				"po.inputCode		  as inputCode," +
				"pf.id              as floorId," +
				"pr.id              as roomId," +
				"pb.id              as bedId," +
				"pbuilding.id       as buildId" +
				" from pension_older po" +
				" join pension_livingRecord plr " +
				"on po.id = plr.older_id " +
				"join pension_bed pb " +
				"on plr.bed_id = pb.id " +
				"join pension_room pr " +
				"on pb.room_id = pr.id " +
				"join pension_floor pf " +
				"on pr.floor_id = pf.id " +
				"join pension_building pbuilding " +
				"on pf.build_id = pbuilding.id " +
				"where po.cleared = 2 " +
				"and po.statuses = 3";
		
		bedNameSql = " select pb.id			 as newbedId," +
				"pb.name		    as newBedname," +
				"pr.id          as newRoomId," +
				"pr.name        as newRoomName," +
				"pf.id          as newFloorId," +
				"pf.name        as newFloorName," +
				"pbuilding.id   as newBuildId," +
				"pbuilding.name as newBuildName " +
				"from pension_bed pb " +
				"join pension_room pr " +
				"on pb.room_id = pr.id " +
				"join pension_floor pf " +
				"on pr.floor_id = pf.id " +
				"join pension_building pbuilding " +
				"on pf.build_id = pbuilding.id " +
				"where 1 = 1";
		
		employeeNameSql = "select pe.name as moverName," +
				"pe.inputCode as inputCode, " +
				"pd.name	as deptName," +
				"pe.id as moverId " +
				"from pension_employee pe " +
				"join pension_dept pd " +
				"on pe.dept_id = pd.id " +
				"where pe.cleared = 2";
	}
	
	/**
	 * 查询假期申请记录
	 */
	public void selectChangeroomApplicationRecords(){
		disDeleteButton = true;
		disUpdateButton = true;
		selectedRows = null;
		records = changeRoomService.selectChangeroomApplicationRecords(startDate,endDate,olderId);
		
	}
	/**
	 * 删除申请记录
	 */
	public void deleteChangeroomApplicationRecord(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		for(PensionChangeroomExtend selectedRow:selectedRows){
			changeRoomService.deleteChangeroomApplicationRecord(selectedRow);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"删除成功！", "删除成功！");
			context.addMessage(null, message);
			
		}
		disDeleteButton =true;
		selectChangeroomApplicationRecords();
		
	}
	
	/**
	 * 录入申请记录
	 */
	public void insertChangeroomApplicationRecord(){
		
		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		try {
			
			changeRoomService
			.insertChangeroomApplicationRecord(insertedRow);
			selectChangeroomApplicationRecords();
		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";

			
		} catch (Exception e) {

			e.getStackTrace();
			info = "出现未知异常，请联系系统管理员！";

		}
		clearInsertForm();
		if(info.equals("添加成功")){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);	
			context.addMessage(null, message);
			request.addCallbackParam("success", true);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);	
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		}
		
	}
	
	/**
	 * 修改申请记录
	 */
	public void updateChangeroomApplicationRecord(){
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "修改成功";
		changeRoomService.updateChangeroomApplicationRecord(updatedRow);
		selectChangeroomApplicationRecords();
		if(info.equals("修改成功")){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);	
			context.addMessage(null, message);
			request.addCallbackParam("success", true);
		}else{
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
		insertedRow = new PensionChangeroomExtend();
	}
	/**
	 * 情况selectForm
	 */
	public void clearSelectForm(){
		startDate = null;
		endDate = null;
		olderId = null;
	}
	/**
	 * 在后台对输入法填充的字段进行填充
	 */
	public void fillField(){
		/*if(insertedRow.getOlderId()!=null){
			PensionChangeroomExtend extend =  changeRoomService.fillField(insertedRow.getOlderId()).get(0);
			insertedRow.setOldBedId(extend.getOldBedId());
			insertedRow.setOldBedName(extend.getOldBedName());
			insertedRow.setOldBuildId(extend.getOldBuildId());
			insertedRow.setOldBuildName(extend.getOldBuildName());
			insertedRow.setOldFloorId(extend.getOldFloorId());
			insertedRow.setOldFloorName(extend.getOldFloorName());
			insertedRow.setOldRoomId(extend.getOldRoomId());
			insertedRow.setOldRoomName(extend.getOldRoomName());
		}else{
			insertedRow.setOldBedId(null);
			insertedRow.setOldBedName(null);
			insertedRow.setOldBuildId(null);
			insertedRow.setOldBuildName(null);
			insertedRow.setOldFloorId(null);
			insertedRow.setOldFloorName(null);
			insertedRow.setOldRoomId(null);
			insertedRow.setOldRoomName(null);
		}*/
		
	}
	/**
	 * 当调用房间输入法时在后台对输入法填充的字段进行填充
	 */
	public void fillBedField(){
		if(insertedRow.getNewbedId()!=null){
			PensionChangeroomExtend extend =  changeRoomService.fillBedField(insertedRow.getNewbedId()).get(0);
			insertedRow.setNewBuildId(extend.getNewBuildId());
			insertedRow.setNewBuildName(extend.getNewBuildName());
			insertedRow.setNewFloorId(extend.getNewFloorId());
			insertedRow.setNewFloorName(extend.getNewFloorName());
			insertedRow.setNewRoomId(extend.getNewRoomId());
			insertedRow.setNewRoomName(extend.getNewRoomName());
		}else{
			insertedRow.setNewBuildId(null);
			insertedRow.setNewBuildName(null);
			insertedRow.setNewFloorId(null);
			insertedRow.setNewFloorName(null);
			insertedRow.setNewRoomId(null);
			insertedRow.setNewRoomName(null);
		}
		
		
	}
	
	public void fillUpdateNewBedField(){
		if(updatedRow.getNewbedId()!=null){
			PensionChangeroomExtend extend =  changeRoomService.fillBedField(updatedRow.getNewbedId()).get(0);
			updatedRow.setNewBuildId(extend.getNewBuildId());
			updatedRow.setNewBuildName(extend.getNewBuildName());
			updatedRow.setNewFloorId(extend.getNewFloorId());
			updatedRow.setNewFloorName(extend.getNewFloorName());
			updatedRow.setNewRoomId(extend.getNewRoomId());
			updatedRow.setNewRoomName(extend.getNewRoomName());
		}else{
			updatedRow.setNewBuildId(null);
			updatedRow.setNewBuildName(null);
			updatedRow.setNewFloorId(null);
			updatedRow.setNewFloorName(null);
			updatedRow.setNewRoomId(null);
			updatedRow.setNewRoomName(null);
		}
	}
	
	
	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		
		setDisDeleteAndSubmitButton(false);
		if(selectedRows.length == 1){
			setDisUpdateButton(false);
		}else{
			setDisUpdateButton(true);
		}

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		if(selectedRows.length == 1){
			setDisUpdateButton(false);
		}
		if(selectedRows.length == 0){
			setDisUpdateButton(true);
			setDisDeleteAndSubmitButton(true);
		}
	}
	
	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordUpdatedRow() {
	
		updatedRow = selectedRows[0];
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
		this.disDeleteButton = disDeleteAndSubmitButton;
	}

	public boolean isDisDeleteAndSubmitButton() {
		return disDeleteButton;
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

	public void setRecords(List<PensionChangeroomExtend> records) {
		this.records = records;
	}

	public List<PensionChangeroomExtend> getRecords() {
		return records;
	}

	public void setDisDeleteButton(boolean disDeleteButton) {
		this.disDeleteButton = disDeleteButton;
	}

	public boolean isDisDeleteButton() {
		return disDeleteButton;
	}

	public void setChangeRoomService(ChangeRoomService changeRoomService) {
		this.changeRoomService = changeRoomService;
	}

	public ChangeRoomService getChangeRoomService() {
		return changeRoomService;
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
	
	public PensionChangeroomExtend[] getSelectedRows() {
		return selectedRows;
	}

	public void setSelectedRows(PensionChangeroomExtend[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	public PensionChangeroomExtend getInsertedRow() {
		return insertedRow;
	}

	public void setInsertedRow(PensionChangeroomExtend insertedRow) {
		this.insertedRow = insertedRow;
	}

	public PensionChangeroomExtend getUpdatedRow() {
		return updatedRow;
	}

	public void setUpdatedRow(PensionChangeroomExtend updatedRow) {
		this.updatedRow = updatedRow;
	}

}
