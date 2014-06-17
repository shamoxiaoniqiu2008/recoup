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

import com.centling.his.util.SessionManager;

import service.logisticsManage.InvestigateService;
import util.PmsException;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionCheckApproveExtend;
import domain.logisticsManage.PensionCheckExtend;

public class InvestigateController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionCheckExtend> records = new ArrayList<PensionCheckExtend>();
	/**
	 * 用来在dialog中显示审批结果
	 */
	private List<PensionCheckApproveExtend> approves = new ArrayList<PensionCheckApproveExtend>();
	/**
	 * 被选中的记录
	 */
	private PensionCheckExtend selectedRow = new PensionCheckExtend();
	/**
	 * 被录入的记录
	 */
	private PensionCheckExtend insertedRow = new PensionCheckExtend();
	/**
	 * 被修改的记录
	 */
	private PensionCheckExtend updatedRow = new PensionCheckExtend();
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 排查房间主键
	 */
	private Long roomId;
	/**
	 * 排查维修申请表的主键，用在接收消息时做查询条件
	 */
	private Long checkId;
	/**
	 * 是否需要维修
	 */
	private Integer needrepairFlag = 0;
	/**
	 * 是否已提交 用作查询条件
	 */
	private Integer applysendFlag = 0;
	/**
	 * 排查类型
	 */
	private Integer checkType;
	/**
	 * 绑定房间查询条件的输入法
	 */
	private String roomSQL;
	/**
	 * 修改和删除按钮是否可用
	 */
	private boolean disUpdateButton = true;
	/**
	 * 修改删除和提交按钮是否可用
	 */
	private boolean disDeleteAndSubmitButton = true;
	/**
	 * insertForm确定并发送按钮 是否可见
	 */
	private boolean disOkAndSubmitButton = true;
	/**
	 * 绑定InsertForm的维修内容 要么不可见  要么可见并必填
	 */
	private boolean repairDetailRenderedAndRequredFlag = true;
	/**
	 * 绑定updateForm的维修内容 要么不可见  要么可见并必填
	 */
	private boolean updateDetailRenderedAndRequredFlag;
	/**
	 * 注入业务
	 */
	private transient InvestigateService investigateService;
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
		this.initDate();
		initSql();
		insertedRow.setNeedrepairFlag(1);
		//获取消息源发给本页面的参数
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
		.getExternalContext().getRequestParameterMap();
		String checkId = paramsMap.get("checkId");
		if(checkId != null) {
			this.setCheckId(Long.valueOf(checkId));
		} else {
			this.setCheckId(null);
		}
		//根据参数 和其余默认的查询条件查询出所有的排查维修申请
		selectRecords();
		if(checkId!=null){
			if(records!=null&&!records.isEmpty()){
				PensionCheckExtend record = records.get(0);
				selectedRow = record;
				setDisDeleteAndSubmitButton(false);
				setDisUpdateButton(false);
				approves = investigateService.selectApproveResults(record.getId());
			}
		}
		//之后 将其至空
		this.setCheckId(null);
	
	}
	
	/**
	 * 将结束日期设置为今天，起始日期设置为一周前的今天
	 */
	public void initDate(){
		
		Calendar calendar=Calendar.getInstance();
		endDate=new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		endDate=calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-7);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate=calendar.getTime();
  
	}
	
	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		roomSQL =  "select pr.id  as roomId," +
		"pr.name as roomName," +
		"pr.floorName as floorName," +
		"pr.buildName as buildName " +
		"from pension_room pr " +
		"where pr.cleared = 2 "; 
	}
	
	/**
	 * 查询假期申请记录
	 */
	public void selectRecords(){
		disDeleteAndSubmitButton = true;
		disUpdateButton = true;
		selectedRow = null;
		approves.clear();
		setRecords(investigateService.selectInvestigatationRecords(startDate,endDate,curEmployee.getId(),roomId,applysendFlag,needrepairFlag,checkType,checkId));
		
	}
	/**
	 * 删除申请记录
	 */
	public void deleteRecord(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		//如果申请未被提交
		if(selectedRow.getApplysendFlag() == null||selectedRow.getApplysendFlag()==2){
			investigateService.deleteInvestigatationRecord(selectedRow);
			selectRecords();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"删除成功！", "删除成功！");
			context.addMessage(null, message);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已提交的记录不能删除！", "已提交的记录不能删除");
			context.addMessage(null, message);
		}
		
		
	}
	
	/**
	 * 录入申请记录
	 */
	public void insertRecord(){
		
		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		try {
			
			insertedRow.setCheckerId(curEmployee.getId());
			insertedRow.setCheckTime(new Date());
			investigateService.insertInvestigatationRecord(insertedRow);
			selectRecords();
		} catch (DataAccessException e) {
			System.out.print(e);
			e.getStackTrace();
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
	public void updateRecord(){
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "修改成功";
		investigateService.updateInvestigatationRecord(updatedRow);
		selectRecords();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				info, info);	
		context.addMessage(null, message);
		request.addCallbackParam("success", true);
	}
	/**
	 * 提交申请记录
	 * @throws PmsException 
	 */
	public void submitRecord() throws PmsException{
		
		FacesContext context = FacesContext.getCurrentInstance();
		if(selectedRow.getApplysendFlag() == null||selectedRow.getApplysendFlag()==2){
			if(selectedRow.getNeedrepairFlag()==1){
				investigateService.submitInvestigatationRecord(selectedRow,curEmployee);
				selectRecords();
				clearInsertForm();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"提交成功！", "提交成功！");
				context.addMessage(null, message);
			}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"不需要维修的记录不能提交！", "不需要维修的记录不能提交！");
				context.addMessage(null, message);
			}
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已提交的记录不能再提交！", "已提交的记录不能再提交！");
			context.addMessage(null, message);
		}
	}
	/**
	 * 插入并提交
	 * @throws PmsException
	 */
	public void insertAndSubmitRecord()throws PmsException{
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "添加并提交成功";
		try {
			if(insertedRow.getNeedrepairFlag()==1){
				insertedRow.setCheckerId(curEmployee.getId());
				insertedRow.setCheckTime(new Date());
				investigateService.insertInvestigatationRecord(insertedRow);
				investigateService.submitInvestigatationRecord(insertedRow,curEmployee);
				selectRecords();
			}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,"不能提交不需要维修的记录！","不能提交不需要维修的记录！");	
				context.addMessage(null, message);
				request.addCallbackParam("success", false);
				return;
			}
			
		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";

			
		} catch (Exception e) {

			info = "出现未知异常，请联系系统管理员！";

		}
		clearInsertForm();
		if(info.equals("添加并提交成功")){
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
	 *  查询被选择的维修申请记录的审批结果
	 * @param selectedRow
	 */
	public void selectApproveResults(){
		approves = investigateService.selectApproveResults(selectedRow.getId());
	}
	
	/**
	 * 
	 * 清空insertFrom
	 */
	public void clearInsertForm() {
		insertedRow = new PensionCheckExtend();
		insertedRow.setNeedrepairFlag(2);
		disOkAndSubmitButton = true;
		repairDetailRenderedAndRequredFlag = true;
		this.changeInsertDialog();
	}
	/**
	 * 情况selectForm
	 */
	public void clearSelectForm(){
		startDate = null;
		endDate = null;
		applysendFlag = 0;
		checkType = 0;
		roomId = null;
		needrepairFlag = 0;
	}
	/**
	 * 在后台对输入法填充的字段进行填充
	 */
	public void fillField(){
		if(insertedRow.getRoomId() != null){
			PensionCheckExtend extend =  investigateService.fillField(insertedRow.getRoomId()).get(0);
			insertedRow.setFloorId(extend.getFloorId());
			insertedRow.setFloorName(extend.getFloorName());
			insertedRow.setRoomName(extend.getRoomName());
			insertedRow.setBuildId(extend.getBuildId());
			insertedRow.setBuildName(extend.getBuildName());
		}else{
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN,"房间不能为空!", "房间不能为空!"));
		}
	}
	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		
		setDisDeleteAndSubmitButton(false);
		setDisUpdateButton(false);
		selectApproveResults();

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		
		setDisUpdateButton(true);
		setDisDeleteAndSubmitButton(true);
		approves.clear();
		
	}
	/**
	 * 修改确定并发送按钮的状态
	 */
	public void changeDisOkAndSubmitButton(){
		if(insertedRow.getNeedrepairFlag() == 0){
			disOkAndSubmitButton = true;
		}else{
			disOkAndSubmitButton = false;
		}
		
	}
	
	public void checkDeletedRow(){
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		boolean success  = true;
		if(selectedRow.getApplysendFlag()==1){
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
		boolean success  = true;
		updatedRow = selectedRow;
		if(updatedRow.getApplysendFlag()==1){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该记录已经提交，不能修改!", "该记录已经提交，不能修改!");	
			context.addMessage(null, message);
			success = false;
			return;
		}
		Integer flag = updatedRow.getNeedrepairFlag();
		if(flag == 1){
			updateDetailRenderedAndRequredFlag = true;
		}else{
			updateDetailRenderedAndRequredFlag = false;
		}
		this.changeUpdateDialog();
		request.addCallbackParam("success", success);
	}
	
	/**
	 * 点击前台的维修类型 根据具体类型 来改变insertDialog的布局
	 */
	public void changeInsertDialog(){
		
		Integer flag = insertedRow.getNeedrepairFlag();
		//需要维修
		if(flag == 1){
			disOkAndSubmitButton = true;
			repairDetailRenderedAndRequredFlag = true;
			
		}else{
			disOkAndSubmitButton = false;
			repairDetailRenderedAndRequredFlag = false;
			insertedRow.setRepairDetail("");
			
		}
	}
	/**
	 * 点击前台的维修类型 根据具体类型 来改变updateDialog的布局
	 */
	public void changeUpdateDialog(){
		
		Integer updateflag = updatedRow.getNeedrepairFlag();
		if(updateflag == 1){
			updateDetailRenderedAndRequredFlag = true;
		}else{
			updateDetailRenderedAndRequredFlag = false;
			updatedRow.setRepairDetail("");
		}
		
	}

	public PensionCheckExtend getInsertedRow() {
		return insertedRow;
	}

	public void setInsertedRow(PensionCheckExtend insertedRow) {
		this.insertedRow = insertedRow;
	}

	public PensionCheckExtend getUpdatedRow() {
		return updatedRow;
	}

	public void setUpdatedRow(PensionCheckExtend updatedRow) {
		this.updatedRow = updatedRow;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setNeedrepairFlag(Integer needrepairFlag) {
		this.needrepairFlag = needrepairFlag;
	}

	public Integer getNeedrepairFlag() {
		return needrepairFlag;
	}

	public void setCheckType(Integer checkType) {
		this.checkType = checkType;
	}

	public Integer getCheckType() {
		return checkType;
	}

	public void setRecords(List<PensionCheckExtend> records) {
		this.records = records;
	}

	public List<PensionCheckExtend> getRecords() {
		return records;
	}

	public void setInvestigateService(InvestigateService investigateService) {
		this.investigateService = investigateService;
	}

	public InvestigateService getInvestigateService() {
		return investigateService;
	}

	public void setApplysendFlag(Integer applysendFlag) {
		this.applysendFlag = applysendFlag;
	}

	public Integer getApplysendFlag() {
		return applysendFlag;
	}

	public void setRoomSQL(String roomSQL) {
		this.roomSQL = roomSQL;
	}

	public String getRoomSQL() {
		return roomSQL;
	}

	public void setApproves(List<PensionCheckApproveExtend> approves) {
		this.approves = approves;
	}

	public List<PensionCheckApproveExtend> getApproves() {
		return approves;
	}

	public void setDisOkAndSubmitButton(boolean disOkAndSubmitButton) {
		this.disOkAndSubmitButton = disOkAndSubmitButton;
	}

	public boolean isDisOkAndSubmitButton() {
		return disOkAndSubmitButton;
	}

	public void setRepairDetailRenderedAndRequredFlag(
			boolean repairDetailRenderedAndRequredFlag) {
		this.repairDetailRenderedAndRequredFlag = repairDetailRenderedAndRequredFlag;
	}

	public boolean isRepairDetailRenderedAndRequredFlag() {
		return repairDetailRenderedAndRequredFlag;
	}

	public void setUpdateDetailRenderedAndRequredFlag(
			boolean updateDetailRenderedAndRequredFlag) {
		this.updateDetailRenderedAndRequredFlag = updateDetailRenderedAndRequredFlag;
	}

	public boolean isUpdateDetailRenderedAndRequredFlag() {
		return updateDetailRenderedAndRequredFlag;
	}

	public void setSelectedRow(PensionCheckExtend selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionCheckExtend getSelectedRow() {
		return selectedRow;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	public Long getCheckId() {
		return checkId;
	}
	
	
	
}
