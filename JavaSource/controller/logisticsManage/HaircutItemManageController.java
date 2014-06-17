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

import service.logisticsManage.HaircutItemManageService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionHaircutApplyExtend;
import domain.logisticsManage.PensionHaircutSchedule;

public class HaircutItemManageController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionHaircutSchedule> records = new ArrayList<PensionHaircutSchedule>();
	/**
	 * 被选中的记录
	 */
	private PensionHaircutSchedule selectedRow;
	/**
	 * 被录入的记录
	 */
	private PensionHaircutSchedule insertedRow = new PensionHaircutSchedule();
	/**
	 * 被修改的记录
	 */
	private PensionHaircutSchedule updatedRow = new PensionHaircutSchedule();
	/**
	 * 预约被选中的理发项目的老人的明细
	 */
	private ArrayList<PensionHaircutApplyExtend> reservedOlderDetails = new ArrayList<PensionHaircutApplyExtend>();
	/**
	 * 绑定关于理发项目的输入法
	 */
	private String itemNameSql;
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 项目主键用于查询条件
	 */
	private Long itemId;
	/**
	 * 发布标记用于查询条件
	 */
	private Integer postFlag=0;
	/**
	 * 修改删除和发布按钮是否可用
	 */
	private boolean disUpdateDeleteAndPostButton = true;
	/**
	 * 注入业务
	 */
	private transient HaircutItemManageService haircutItemManageService;
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
		selectHaircutItemRecords();
	}
	
	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		itemNameSql = "select phs.id                  as id," +
		"DATE_FORMAT(phs.start_time,'%Y-%m-%d %H:%i')  as startTime," +
		"DATE_FORMAT(phs.end_time,'%Y-%m-%d %H:%i')  as endTime," +
		"phs.haircut_address     as haircutAddress," +
		"phs.item_name           as itemName," +
		"phs.max_number          as maxNumber," +
		"phs.post_flag           as postFlag," +
		"phs.current_ordernumber as currentOrdernumber," +
		"phs.haircuted_number    as haircutedNumber," +
		"phs.cleared             as cleared " +
		"from pension_haircut_schedule phs " +
		"where phs.cleared = 2 ";
	}
	
	/**
	 * 查询假期申请记录
	 */
	public void selectHaircutItemRecords(){
		disUpdateDeleteAndPostButton = true;
		selectedRow = null;
		records = haircutItemManageService.selectHaircutItemRecords(startDate,endDate,itemId,postFlag);
		reservedOlderDetails.clear();
	}
	/**
	 * 查询被选中行对应的预约记录
	 * @param selectedRow
	 */
	public void selectReservedOlderDetails(){
		setReservedOlderDetails(haircutItemManageService.selectReservedOlderDetails(selectedRow));
	}
	/**
	 * 删除申请记录
	 */
	public void deleteHaircutItemRecord(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		if(selectedRow.getPostFlag() == null||selectedRow.getPostFlag()==2){
			haircutItemManageService.deleteHaircutItemRecord(selectedRow);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"删除成功！", "删除成功！");
			context.addMessage(null, message);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已提交的记录不能删除！", "已提交的记录不能删除");
			context.addMessage(null, message);
		}
		disUpdateDeleteAndPostButton =true;
		selectHaircutItemRecords();
		
	}
	
	/**
	 * 录入申请记录
	 */
	public void insertHaircutItemRecord(){
		
		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		try {
			
			if(checkTime(insertedRow)){
				haircutItemManageService
				.insertHaircutItemRecord(insertedRow);
				
			}else{
				request.addCallbackParam("success", false);
				return;
			}
			selectHaircutItemRecords();
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
	public void updateHaircutItemRecord(){
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "修改成功";
		if(checkTime(updatedRow)){
			haircutItemManageService.updateHaircutItemRecord(updatedRow);
			selectHaircutItemRecords();
		}else{
			request.addCallbackParam("success", false);
			return;
		}
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
	 * 发布理发记录
	 * @throws PmsException 
	 */
	public void postHaircutItemRecord() throws PmsException{
		
		FacesContext context = FacesContext.getCurrentInstance();
		if(selectedRow.getPostFlag()== null||selectedRow.getPostFlag()==2){
			haircutItemManageService.postHaircutItemRecord(selectedRow);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"发布成功！", "发布成功！");
			context.addMessage(null, message);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已发布的记录不能再发布！", "已发布的记录不能再发布！");
			context.addMessage(null, message);
		}
		selectHaircutItemRecords();
		clearInsertForm();
	}
	/**
	 * 插入并提交
	 * @throws PmsException
	 */
	public void insertAndPostHaircutItemRecord(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "添加并发布成功";
		try {
			if(checkTime(insertedRow)){
				insertedRow.setPostFlag(1);
				haircutItemManageService.insertHaircutItemRecord(insertedRow);
				selectHaircutItemRecords();
			}else{
				request.addCallbackParam("success", false);
				return;
			}
		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";

			
		} catch (Exception e) {

			info = "出现未知异常，请联系系统管理员！";

		}
		clearInsertForm();
		if(info.equals("添加并发布成功")){
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
	
	public boolean checkTime(PensionHaircutSchedule insertedRow){
		
		FacesContext context = FacesContext.getCurrentInstance();
		Long endTime = insertedRow.getEndTime().getTime();
		Long startTime = insertedRow.getStartTime().getTime();
		Long curTime = new Date().getTime();
		if(startTime<curTime){
			
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"开始时间必须大于当前时间!", "开始时间必须大于当前时间!");	
			context.addMessage(null, message);
			return false;
		}else if(endTime<=startTime){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"开始时间必须小于结束时间!", "开始时间必须小于结束时间!");	
			context.addMessage(null, message);
			return false;
			
		}else{
			return true;
		}
		
	}
	
	/**
	 * 
	 * 清空insertFrom
	 */
	public void clearInsertForm() {
		insertedRow = new PensionHaircutSchedule();
	}
	/**
	 * 情况selectForm
	 */
	public void clearSelectForm(){
		startDate = null;
		endDate = null;
		itemId = null;
		postFlag = 0;
	}
	
	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		if(selectedRow.getPostFlag()==1){
			disUpdateDeleteAndPostButton=true;
		}else{
			disUpdateDeleteAndPostButton=false;
		}
		selectReservedOlderDetails();
		
	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
	
		setDisDeleteAndSubmitButton(true);
		reservedOlderDetails.clear();
	
	}
	public void checkDeletedRow(){
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		boolean success  = true;
		if(selectedRow.getPostFlag()==1){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该记录已经发布，不能删除!", "该记录已经发布，不能删除!");	
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
		boolean success  = true;
		updatedRow = selectedRow;
		if(updatedRow.getPostFlag()==1){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该记录已经提交，不能修改!", "该记录已经提交，不能修改!");	
			context.addMessage(null, message);
			success = false;
			return;
		}
		request.addCallbackParam("success", success);
	}
	/**
	 * 为已经预约的老人登记
	 */
	public void signHaircut(PensionHaircutApplyExtend signOlderDetail){
		
		FacesContext context = FacesContext.getCurrentInstance();
		Long startTime = selectedRow.getStartTime().getTime();
		Long curTime = new Date().getTime();
		if(curTime<startTime){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"还未到开始时间，不能登记!", "还未到开始时间，不能登记!");	
			context.addMessage(null, message);
		}else{
			haircutItemManageService.signHaircut(signOlderDetail,selectedRow);
			selectReservedOlderDetails();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"登记成功!", "登记成功!");	
			context.addMessage(null, message);
	
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

	public void setDisDeleteAndSubmitButton(boolean disDeleteAndSubmitButton) {
		this.disUpdateDeleteAndPostButton = disDeleteAndSubmitButton;
	}

	public boolean isDisDeleteAndSubmitButton() {
		return disUpdateDeleteAndPostButton;
	}

	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}

	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setDisDeleteButton(boolean disDeleteButton) {
		this.disUpdateDeleteAndPostButton = disDeleteButton;
	}

	public boolean isDisDeleteButton() {
		return disUpdateDeleteAndPostButton;
	}

	public void setHaircutItemManageService(HaircutItemManageService haircutItemManageService) {
		this.haircutItemManageService = haircutItemManageService;
	}

	public HaircutItemManageService getHaircutItemManageService() {
		return haircutItemManageService;
	}

	public void setItemNameSql(String itemNameSql) {
		this.itemNameSql = itemNameSql;
	}

	public String getItemNameSql() {
		return itemNameSql;
	}
	
	public List<PensionHaircutSchedule> getRecords() {
		return records;
	}

	public void setRecords(List<PensionHaircutSchedule> records) {
		this.records = records;
	}

	public PensionHaircutSchedule getInsertedRow() {
		return insertedRow;
	}

	public void setInsertedRow(PensionHaircutSchedule insertedRow) {
		this.insertedRow = insertedRow;
	}

	public PensionHaircutSchedule getUpdatedRow() {
		return updatedRow;
	}

	public void setUpdatedRow(PensionHaircutSchedule updatedRow) {
		this.updatedRow = updatedRow;
	}

	public void setPostFlag(Integer postFlag) {
		this.postFlag = postFlag;
	}

	public Integer getPostFlag() {
		return postFlag;
	}

	public void setReservedOlderDetails(ArrayList<PensionHaircutApplyExtend> reservedOlderDetails) {
		this.reservedOlderDetails = reservedOlderDetails;
	}

	public ArrayList<PensionHaircutApplyExtend> getReservedOlderDetails() {
		return reservedOlderDetails;
	}

	public void setDisUpdateDeleteAndPostButton(boolean disUpdateDeleteAndPostButton) {
		this.disUpdateDeleteAndPostButton = disUpdateDeleteAndPostButton;
	}

	public boolean isDisUpdateDeleteAndPostButton() {
		return disUpdateDeleteAndPostButton;
	}
	public PensionHaircutSchedule getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(PensionHaircutSchedule selectedRow) {
		this.selectedRow = selectedRow;
	}

}
