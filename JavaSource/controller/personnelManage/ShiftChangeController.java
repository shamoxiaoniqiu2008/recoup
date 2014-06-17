package controller.personnelManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.personnelManage.ShiftChangeService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.hrManage.PensionShiftchangeRecord;
import domain.system.PensionSysUser;


/**
 * 交班管理   author:mary liu
 *
 *
 */

public class ShiftChangeController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient ShiftChangeService shiftChangeService;
	
	
	//查询
	private Date startDate;
	private Date endDate;
	private Integer shiftFlag;//交接班类型：1 交班；2 接班
	//消息
	private Long recordId;
/*	private Long deptId;
	private String deptName;
	private Long empId;
	private String empName;
	private String selectDeptSql;
	private String selectEmpSql;*/
	
	//主记录
	
	private List<PensionShiftchangeRecord> shiftChangeRecords;
	private PensionShiftchangeRecord selectShiftChangeRecord;
	
	//新增
	private String addDeptSql;
	private String newolderAmount;//新入住老人数
	private String outhospAmount;//出院老人数
	private String inhospAmount;//在院老人数
	private String returnhomeAmount;//回家老人数
	private String totalAmount;//在院老人总数
	
	//标识
	private boolean addFlag;
	private boolean modifyFlag;
	private boolean viewFlag;
	
	//有权限查看交班记录的角色列表
	private List<Long> roleList;
	
	//当前信息
	private PensionEmployee employee;//当前用户
	private PensionSysUser sysUser;
	
	@PostConstruct
	public void init(){
		employee=(PensionEmployee) SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
		sysUser=(PensionSysUser)SessionManager.getSessionAttribute(SessionManager.USER);

		//当由消息进入时，读取交接班记录的编号并显示
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String recordIdStr = paramsMap.get("recordId");
		if (recordIdStr != null) {
			recordId = Long.valueOf(recordIdStr);
		} else {
			recordId = null;
		}
		this.initSql();
		this.initDate();
		this.search();
	}
	
	public void initSql(){
		try {
			addDeptSql="select emp.id,emp.name,emp.inputCode "
					+" from pension_employee emp " 
					+" where emp.cleared = 2 and emp.dept_id = "+employee.getDeptId()
					+" and emp.id != "+employee.getId();
			//获取可以查看所有交班记录的角色列表
			roleList=shiftChangeService.selectShiftChangeRoles("SHIFT_CHANGE_CHECK_ROLE_ID");
			/*//属于该列表角色的用户登录，则有权查看所有的交班记录
			if(roleList.contains(sysUser.getRoleId())){
				selectDeptSql="select dept.id,dept.name,dept.inputCode "
						+" from pension_dept dept "
						+" where dept.cleared = 2";
				selectEmpSql="select emp.id,emp.name,emp.inputCode "
						+" from pension_employee emp " 
						+" where emp.cleared = 2";
			}else{//否则只能查看自己的交班记录
				selectDeptSql="select dept.id,dept.name,dept.inputCode "
						+" from pension_dept dept "
						+" where dept.cleared = 2 and dept.id = "+employee.getDeptId();
				selectEmpSql="select emp.id,emp.name,emp.inputCode "
						+" from pension_employee emp " 
						+" where emp.cleared = 2 and emp.id = "+employee.getId();
			}*/
		} catch (PmsException e) {
			/*//读取系统参数有误时，只能查看自己的交班记录
			selectDeptSql="select dept.id,dept.name,dept.inputCode "
					+" from pension_dept dept "
					+" where dept.cleared = 2 and dept.id = "+employee.getDeptId();
			selectEmpSql="select emp.id,emp.name,emp.inputCode "
					+" from pension_employee emp " 
					+" where emp.cleared = 2 and emp.id = "+employee.getId();
*/
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
			e.printStackTrace();
		}
	}
	
	/**
	 * 【清空】
	 */
	public void clearSearchForm(){
		startDate=null;
		endDate=null;
		shiftFlag = null;
	/*	deptId=null;
		deptName=null;
		empId=null;
		empName=null;*/
	}
	
	
	/**
	 * 当改变searchForm中选择的部门时，相应员工输入法可调出的员工也要改变
	 */
	public void onChangeSelectDeptId(){
		/*if(deptId==null||"".equals(deptId)||"".equals(deptName)){
			this.initSql();
		}else{
			if(roleList.contains(sysUser.getRoleId())){
				selectEmpSql="select emp.id,emp.name,emp.inputCode "
						+" from pension_employee emp " 
						+" where emp.cleared = 2 and emp.dept_id = "+deptId;
			}else{
				selectEmpSql="select emp.id,emp.name,emp.inputCode "
						+" from pension_employee emp " 
						+" where emp.cleared = 2 and emp.id = "+employee.getId();
			}
			
		}
		empId=null;
		empName=null;*/
	}
	
	
	/**
	 * 将结束日期设置为今天，起始日期设置为一个月前的今天
	 */
	public void initDate(){
		
		Calendar calendar=Calendar.getInstance();
		endDate=new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		endDate=calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 7);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate=calendar.getTime();
  
	}
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("shiftChangeForm:scheduleList");
		PensionShiftchangeRecord  arr = (PensionShiftchangeRecord) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectModifyData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("shiftChangeForm:scheduleList");
		PensionShiftchangeRecord  arr = (PensionShiftchangeRecord) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else if(!employee.getId().equals(arr.getShifterId())){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"您无权操作此数据！","您无权操作此数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else if(arr.getSended() == 1){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"该记录已提交！","该记录已提交！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectSendData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("shiftChangeForm:scheduleList");
		PensionShiftchangeRecord  arr = (PensionShiftchangeRecord) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else if(!employee.getId().equals(arr.getShifterId())){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"您无权操作此数据！","您无权操作此数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else if(arr.getSended() == 1){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"该记录已提交！","该记录已提交！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * 查询
	 */
	public void search(){
		/*if(deptId==null&&empId==null){
			deptId=employee.getDeptId();
			empId=employee.getId();
		}
		shiftChangeRecords=shiftChangeService.search(startDate,endDate,deptId,empId);*/
		if(recordId != null){
			shiftChangeRecords = new ArrayList<PensionShiftchangeRecord>();
			selectShiftChangeRecord = shiftChangeService.selectShiftChangeRecord(recordId);
			shiftChangeRecords.add(selectShiftChangeRecord);
		}else{
			shiftChangeRecords=shiftChangeService.search(startDate,endDate,employee.getId(),shiftFlag);
			selectShiftChangeRecord=null;
		}
	}
	
	public void onShiftChangeRecordSelect(SelectEvent e){
		
	}
	
	public void onShiftChangeRecordUnselect(UnselectEvent e){
		
	}
	/**
	 * 新增之前初始化新增Dig
	 */
	public void add(){
		addFlag=true;
		modifyFlag=false;
		viewFlag=false;
		selectShiftChangeRecord=new PensionShiftchangeRecord();
		selectShiftChangeRecord.setChangeTime(new Date());
		selectShiftChangeRecord.setDeptId(employee.getDeptId().intValue());
		selectShiftChangeRecord.setDeptName(shiftChangeService.selectDeptName(employee.getDeptId()));
		selectShiftChangeRecord.setSended(2);//未发送
		selectShiftChangeRecord.setAffirmed(2);//未确认
		newolderAmount=null;
		inhospAmount=null;
		outhospAmount=null;
		returnhomeAmount=null;
		totalAmount=null;
	}
	/**
	 * 保存 新增/修改 交班记录
	 */
	public void saveShiftChangeRecord(){
		RequestContext request = RequestContext.getCurrentInstance();
		try{
			selectShiftChangeRecord.setNewolderAmount(new Integer(newolderAmount));
			selectShiftChangeRecord.setInhospAmount(new Integer(inhospAmount));
			selectShiftChangeRecord.setReturnhomeAmount(new Integer(returnhomeAmount));
			selectShiftChangeRecord.setTotalAmount(new Integer(totalAmount));
			selectShiftChangeRecord.setOuthospAmount(new Integer(outhospAmount));
			selectShiftChangeRecord.setShifterId(employee.getId());
			selectShiftChangeRecord.setShifterName(employee.getName());
			if(addFlag){
				selectShiftChangeRecord=shiftChangeService.insertShiftChangeRecord(selectShiftChangeRecord);
				if(shiftChangeRecords==null){
					shiftChangeRecords=new ArrayList<PensionShiftchangeRecord>();
				}
				shiftChangeRecords.add(0, selectShiftChangeRecord);
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("新增交班记录成功！", "新增交班记录成功！"));
				request.addCallbackParam("validate", true);
			}else if(modifyFlag){
				shiftChangeService.updateShiftChangeRecord(selectShiftChangeRecord);
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("修改交班记录成功！", "修改交班记录成功！"));
				request.addCallbackParam("validate", true);
			}
		}catch(NumberFormatException e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("【数量】请填入人数！", "【数量】请填入人数！"));
			request.addCallbackParam("validate", false);
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("保存失败！", "保存失败！"));
			request.addCallbackParam("validate", false);
		}
	}
	
	/**
	 * 修改之前初始化新增Dig
	 */
	public void modify(){
		addFlag=false;
		modifyFlag=true;
		viewFlag=false;
		this.initShiftChangeRecord();
	}
	/**
	 * 放弃【保存】新增
	 */
	public void abandonSave(){
		selectShiftChangeRecord = new PensionShiftchangeRecord();
	}
	
	
	/**
	 * 确认消息
	 */
	public void affirmRecord(){
		try{
			//将消息设置为确认 并将交接班记录设为已确认
			shiftChangeService.updateMessageProcessor(selectShiftChangeRecord.getId(),employee.getId(),employee.getDeptId());
			selectShiftChangeRecord.setAffirmed(1);//已确认
			if(recordId != null && recordId == selectShiftChangeRecord.getId()){
				recordId = null;
			}
			this.search();
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("消息确认失败！", "消息确认失败！"));
		}
	}
	
	/**
	 * 初始化选中的交班记录
	 */
	public void initShiftChangeRecord(){
		newolderAmount=selectShiftChangeRecord.getNewolderAmount().toString();
		inhospAmount=selectShiftChangeRecord.getInhospAmount().toString();
		outhospAmount=selectShiftChangeRecord.getOuthospAmount().toString();
		returnhomeAmount=selectShiftChangeRecord.getReturnhomeAmount().toString();
		totalAmount=selectShiftChangeRecord.getTotalAmount().toString();
	}
	/**
	 * 删除选中的交班记录
	 */
	public void delete(){
		try{
			shiftChangeService.delete(selectShiftChangeRecord.getId());
			shiftChangeRecords.remove(selectShiftChangeRecord);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("删除交班记录成功！", "删除交班记录成功！"));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("修改交班记录失败！", "删除交班记录失败！"));
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化 查看
	 */
	public void view(){
		addFlag=false;
		modifyFlag=false;
		viewFlag=true;
		this.initShiftChangeRecord();
	}
	
	/**
	 * 提交
	 */
	public void send(){
		try {
			shiftChangeService.send(selectShiftChangeRecord.getId(), employee.getName(),selectShiftChangeRecord.getSuccessorId());
			selectShiftChangeRecord.setSended(1);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("提交交接班记录成功！", "提交交接班记录成功！"));
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("提交交接班记录时出错！","提交交接班记录时出错！"));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public ShiftChangeService getShiftChangeService() {
		return shiftChangeService;
	}

	public void setShiftChangeService(ShiftChangeService shiftChangeService) {
		this.shiftChangeService = shiftChangeService;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/*public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getSelectDeptSql() {
		return selectDeptSql;
	}

	public void setSelectDeptSql(String selectDeptSql) {
		this.selectDeptSql = selectDeptSql;
	}

	public String getSelectEmpSql() {
		return selectEmpSql;
	}

	public void setSelectEmpSql(String selectEmpSql) {
		this.selectEmpSql = selectEmpSql;
	}*/

	public List<PensionShiftchangeRecord> getShiftChangeRecords() {
		return shiftChangeRecords;
	}

	public void setShiftChangeRecords(
			List<PensionShiftchangeRecord> shiftChangeRecords) {
		this.shiftChangeRecords = shiftChangeRecords;
	}

	public PensionShiftchangeRecord getSelectShiftChangeRecord() {
		return selectShiftChangeRecord;
	}

	public void setSelectShiftChangeRecord(
			PensionShiftchangeRecord selectShiftChangeRecord) {
		this.selectShiftChangeRecord = selectShiftChangeRecord;
	}

	public String getAddDeptSql() {
		return addDeptSql;
	}

	public void setAddDeptSql(String addDeptSql) {
		this.addDeptSql = addDeptSql;
	}

	public String getNewolderAmount() {
		return newolderAmount;
	}

	public void setNewolderAmount(String newolderAmount) {
		this.newolderAmount = newolderAmount;
	}

	public String getOuthospAmount() {
		return outhospAmount;
	}

	public void setOuthospAmount(String outhospAmount) {
		this.outhospAmount = outhospAmount;
	}

	public String getInhospAmount() {
		return inhospAmount;
	}

	public void setInhospAmount(String inhospAmount) {
		this.inhospAmount = inhospAmount;
	}

	public String getReturnhomeAmount() {
		return returnhomeAmount;
	}

	public void setReturnhomeAmount(String returnhomeAmount) {
		this.returnhomeAmount = returnhomeAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<Long> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Long> roleList) {
		this.roleList = roleList;
	}

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
	}

	public PensionSysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(PensionSysUser sysUser) {
		this.sysUser = sysUser;
	}

	public boolean isAddFlag() {
		return addFlag;
	}

	public void setAddFlag(boolean addFlag) {
		this.addFlag = addFlag;
	}

	public boolean isModifyFlag() {
		return modifyFlag;
	}

	public void setModifyFlag(boolean modifyFlag) {
		this.modifyFlag = modifyFlag;
	}

	public boolean isViewFlag() {
		return viewFlag;
	}

	public void setViewFlag(boolean viewFlag) {
		this.viewFlag = viewFlag;
	}

	public Integer getShiftFlag() {
		return shiftFlag;
	}

	public void setShiftFlag(Integer shiftFlag) {
		this.shiftFlag = shiftFlag;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
}
