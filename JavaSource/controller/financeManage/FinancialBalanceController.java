package controller.financeManage;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

import service.financeManage.FinancialBalanceService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.dictionary.PensionDicPayway;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionDepositLog;
import domain.financeManage.PensionFinancialpayment;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionNormalpaymentdetail;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentdetail;
import domain.system.PensionSysUser;


/**
 * 
 *
 *
 */

public class FinancialBalanceController implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient FinancialBalanceService financialBalanceService;

	//查询
	private Date startDate;//开始日期
	private Date endDate;//结束日期
	private Long deptId;//部门编号
	private String deptName;//部门名称
	private Long empId;//员工编号
	private String empName;//员工姓名
	
	
	private String selectEmpSQL;
	private String selectDeptSQL;
	
	//主记录
	private List<PensionFinancialpayment> financialPayments;
	private PensionFinancialpayment selectFinancialPayment;
	
	//结帐
	private Date balanceStartDate;
	private Date balanceEndDate;
	private List<PensionNormalpayment> normalPayments;
	private List<PensionNormalpaymentdetail> normalPaymentDetails;
	private List<PensionTemppayment> tempPayments;
	private List<PensionTemppaymentdetail> tempPaymentDetails;
	private List<PensionDepositLog> depositLogs;
	private Float totalFee;//本次结帐金额
	private List<PensionDicPayway> payWays;
	
	private boolean seeFlag;//true标识 结帐，false 标识 查看详情
	
	
	private List<Long> roles;//可以查看财务结帐记录的角色
	private String deptIdsStr;//系统参数的设置的财务部门编号
	private List<Long> deptIds;//系统参数的设置的财务部门编号
	private Long financialBalanceType;//财务结帐类型
	
	
	private PensionEmployee employee;
	private PensionSysUser sysUser;
	
	private final static Integer YES_FLAG=1;//是
	private final static Integer NO_FLAG=2;//否
	
	private final static Long FINANCIAL_BALANCE_TYPE_PERSONAGE=new Long(1);//财务结帐-个人
	private final static Long FINANCIAL_BALANCE_TYPE_OFFICE=new Long(2);//财务结帐-科室
	private final static Long FINANCIAL_BALANCE_TYPE_HOSPITAL=new Long(3);//财务结帐-全院
	
	@PostConstruct
	public void init(){
		
		employee=(PensionEmployee) SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
		sysUser=(PensionSysUser)SessionManager.getSessionAttribute(SessionManager.USER);
		this.initSql();
		this.search();
	}
	
	/**
	 * 初始化SQL语句
	 */
	public void initSql(){
		try {
			//查询系统参数中配置的结帐类型  -- 本人；全科；全院
			financialBalanceType=financialBalanceService.selectFinancialBalanceType("FINANCIAL_BALANCE_TYPE");
			//查询系统参数中有权查看财务结帐信息的角色编号
			roles=financialBalanceService.selectFinancialBalanceRoles("FINANCIAL_BALANCE_CHECK_ROLE_ID");
			//查询系统参数中设置的财务部编号 
			Map<String,Object> deptIdMap=financialBalanceService.selectFinancialDepts("FINANCIAL_DEPT_ID");
			deptIds=(List<Long>)deptIdMap.get("DEPT_ID");
			deptIdsStr=(String)deptIdMap.get("DEPT_ID_STR");
		} catch (Exception e) {
			if(new Throwable("FINANCIAL_DEPT").equals(e.getCause())){
				deptIdsStr="";
			}else if(new Throwable("FINANCIAL_BALANCE_CHECK_ROLE").equals(e.getCause())){
				roles=new ArrayList<Long>();
			}
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
		}
		if(roles.contains(sysUser.getRoleId())){
			//如果当前用户属于有权查看财务结帐记录的角色，则查询的部门是所有的财务部门
			selectDeptSQL="select dept.id,dept.name,dept.inputCode from pension_dept dept where dept.cleared = 2 and dept.id in ("+deptIdsStr+" )";
			//如果结帐类型是个人，则能查询的员工是所有财务部门的所有员工
			selectEmpSQL="select emp.id,emp.name,emp.inputCode from pension_employee emp where emp.cleared = 2 and emp.dept_id in ("+deptIdsStr+" )";
		}else{
			//如果当前用户不属于有权查看财务结帐记录的角色，则查询的部门只能是当前部门
			selectDeptSQL="select dept.id,dept.name,dept.inputCode from pension_dept dept where dept.cleared = 2 and dept.id = "+employee.getDeptId();
			if(FINANCIAL_BALANCE_TYPE_PERSONAGE.equals(financialBalanceType)){//如果结帐类型是个人，则能查询的员工只有自己
				selectEmpSQL="select emp.id,emp.name,emp.inputCode from pension_employee emp where emp.cleared = 2 and emp.id = "+employee.getId();
			}else if(FINANCIAL_BALANCE_TYPE_OFFICE.equals(financialBalanceType)){//如果结帐类型是科室，则能查询的员工是本科室的所有员工
				selectEmpSQL="select emp.id,emp.name,emp.inputCode from pension_employee emp where emp.cleared = 2 and emp.dept_id = "+employee.getDeptId();
			}else if(FINANCIAL_BALANCE_TYPE_HOSPITAL.equals(financialBalanceType)){//如果结帐类型是全院，则能查询的员工是所有财务部门的所有员工
				selectEmpSQL="select emp.id,emp.name,emp.inputCode from pension_employee emp where emp.cleared = 2 and emp.dept_id in ("+deptIdsStr+" )";
			}
		}
		
	}
	
	/**
	 * 搜索条件 当更改选中的部门时，重置sql语句
	 */
	public void onSelectDeptIdChange(){
		//如果有选中的科室，则员工的输入法调用该科室下的员工
		if(deptId!=null && !"".equals(deptId)){
			selectEmpSQL="select emp.id,emp.name,emp.inputCode from pension_employee emp where dept.cleared = 2 and emp.dept_id in ("+deptId+" )";
			empId=null;
			empName=null;
		}else{//否则，重新初始化员工输入法
			if(FINANCIAL_BALANCE_TYPE_PERSONAGE.equals(financialBalanceType)){//如果结帐类型是个人，则能查询的员工只有自己
				selectEmpSQL="select emp.id,emp.name,emp.inputCode from pension_employee emp where emp.cleared = 2 and emp.id = "+employee.getId();
			}else if(FINANCIAL_BALANCE_TYPE_OFFICE.equals(financialBalanceType)){//如果结帐类型是科室，则能查询的员工是本科室的所有员工
				selectEmpSQL="select emp.id,emp.name,emp.inputCode from pension_employee emp where emp.cleared = 2 and emp.dept_id = "+employee.getDeptId();
			}else if(FINANCIAL_BALANCE_TYPE_HOSPITAL.equals(financialBalanceType)){//如果结帐类型是全院，则能查询的员工是所有财务部门的所有员工
				selectEmpSQL="select emp.id,emp.name,emp.inputCode from pension_employee emp where emp.cleared = 2 and emp.dept_id in ("+deptIdsStr+" )";
			}
		}
		
	}

	public void search(){
		//按照起止日期 部门编号 员工编号 查询结帐记录
		financialPayments=financialBalanceService.search(startDate,endDate,deptId,empId,financialBalanceType);
	}
	
	/**
	 * 【清空】查询条件
	 */
	public void clearSearchForm(){
		startDate=null;
		endDate=null;
		deptId=null;
		deptName=null;
		empId=null;
		empName=null;
	}
	
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("financialPaymentListForm:financialpaymentList");
		PensionFinancialpayment  arr = (PensionFinancialpayment) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据，并验证操作权限
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectBalancedData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("financialPaymentListForm:financialpaymentList");
		PensionFinancialpayment  arr = (PensionFinancialpayment) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else if( !arr.getPayeeId().equals(employee.getId()) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"您没有权限撤销该结帐记录！","您没有权限撤销该结帐记录！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * 点击结帐按钮，初始化结帐Dig信息
	 */
	public void beforeBalance(){
		balanceStartDate=null;
		balanceEndDate=null;
		normalPayments=new ArrayList<PensionNormalpayment>();
		normalPaymentDetails=new ArrayList<PensionNormalpaymentdetail>();
		tempPayments=new ArrayList<PensionTemppayment>();
		tempPaymentDetails=new ArrayList<PensionTemppaymentdetail>();
		depositLogs=new ArrayList<PensionDepositLog>();
		totalFee=null;
		payWays=new ArrayList<PensionDicPayway>();
		seeFlag=false;
	}
	
	/**
	 * 按照如入的查询的起止时间，查询该段时间内的可结帐缴费记录
	 */
	@SuppressWarnings("unchecked")
	public void searchBalanceInfo(){
		try {
			Map<String,Object> balanceInfoMap=financialBalanceService.searchBalanceInfo(financialBalanceType,balanceStartDate,balanceEndDate,employee.getId(),employee.getDeptId(),NO_FLAG);
			normalPayments=(List<PensionNormalpayment>) balanceInfoMap.get("NORMAL_PAYMENT");
			tempPayments=(List<PensionTemppayment>) balanceInfoMap.get("TEMP_PAYMENT");
			depositLogs=(List<PensionDepositLog>) balanceInfoMap.get("DEPOSIT_LOG");
			totalFee=(Float) balanceInfoMap.get("TOTAL_FEE");
			payWays=(List<PensionDicPayway>) balanceInfoMap.get("PAY_WAY");
			if(normalPayments.size()<1&&tempPayments.size()<1&&depositLogs.size()<1){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("没有需要结帐的缴费记录！", "没有需要结帐的缴费记录！"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("查询成功！", "查询成功！"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("查询结帐信息出错！", "查询结帐信息出错！"));
			e.printStackTrace();
		}
	}
	/**
	 * 结帐
	 */
	public void balance(){
		RequestContext request = RequestContext.getCurrentInstance();
		try{
			if(normalPayments.size()<1&&tempPayments.size()<1&&depositLogs.size()<1){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("没有缴费记录，不能结帐！", "没有缴费记录，不能结帐！"));
				request.addCallbackParam("validate", false);
				return;
			}else{
				//形成一条结帐记录
				PensionFinancialpayment financialPayment=new PensionFinancialpayment();
				financialPayment.setDeptId(employee.getDeptId());
				financialPayment.setDeptname(financialBalanceService.selectDeptName(employee.getDeptId()));
				financialPayment.setEndtime(balanceEndDate);
				financialPayment.setMoney(totalFee);
				financialPayment.setPayeeId(employee.getId());
				financialPayment.setPayeename(employee.getName());
				financialPayment.setPaytime(new Date());
				financialPayment.setStarttime(balanceStartDate);
				financialBalanceService.insertFinancialPayment(financialPayment,payWays,normalPayments,tempPayments,depositLogs);
				this.search();
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage( "财务结帐成功！", "财务结帐成功！"));
				request.addCallbackParam("validate", true);
			}
		}catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("财务结帐出错！", "财务结帐出错！"));
			request.addCallbackParam("validate", false);
			e.printStackTrace();
		}
	}
	
	public void onChangeTab(TabChangeEvent e){
		
	}
	
	/*
	 * 撤销结帐
	 */
	public void undoBalance(){
		try{
			financialBalanceService.undoBalance(selectFinancialPayment);
			this.search();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("撤销财务结帐成功！", "撤销财务结帐成功！"));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("撤销财务结帐出错！", "撤销财务结帐出错！"));
			e.printStackTrace();
		}
	}
	
	/*
	 * 查看选中的结帐记录的详细信息
	 */
	@SuppressWarnings("unchecked")
	public void beforeSee(){
		seeFlag=true;
		balanceStartDate=selectFinancialPayment.getStarttime();
		balanceEndDate=selectFinancialPayment.getEndtime();
		try {
			Map<String,Object> balanceInfoMap=financialBalanceService.searchBalanceInfo(financialBalanceType,balanceStartDate,balanceEndDate,selectFinancialPayment.getPayeeId(),selectFinancialPayment.getDeptId(),YES_FLAG);
			normalPayments=(List<PensionNormalpayment>) balanceInfoMap.get("NORMAL_PAYMENT");
			tempPayments=(List<PensionTemppayment>) balanceInfoMap.get("TEMP_PAYMENT");
			depositLogs=(List<PensionDepositLog>) balanceInfoMap.get("DEPOSIT_LOG");
			totalFee=(Float) balanceInfoMap.get("TOTAL_FEE");
			payWays=(List<PensionDicPayway>) balanceInfoMap.get("PAY_WAY");
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("查询结帐信息出错！", "查询结帐信息出错！"));
			e.printStackTrace();
		}
	}
	
	public void onFinancialPaymentSelect(SelectEvent e){
		
	}
	
	public void onFinancialPaymentUnselect(UnselectEvent e){
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public FinancialBalanceService getFinancialBalanceService() {
		return financialBalanceService;
	}

	public void setFinancialBalanceService(
			FinancialBalanceService financialBalanceService) {
		this.financialBalanceService = financialBalanceService;
	}

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
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

	public Long getDeptId() {
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


	public String getSelectEmpSQL() {
		return selectEmpSQL;
	}

	public void setSelectEmpSQL(String selectEmpSQL) {
		this.selectEmpSQL = selectEmpSQL;
	}

	public String getSelectDeptSQL() {
		return selectDeptSQL;
	}
	public void setSelectDeptSQL(String selectDeptSQL) {
		this.selectDeptSQL = selectDeptSQL;
	}

	public List<PensionFinancialpayment> getFinancialPayments() {
		return financialPayments;
	}

	public void setFinancialPayments(List<PensionFinancialpayment> financialPayments) {
		this.financialPayments = financialPayments;
	}

	public PensionFinancialpayment getSelectFinancialPayment() {
		return selectFinancialPayment;
	}

	public void setSelectFinancialPayment(
			PensionFinancialpayment selectFinancialPayment) {
		this.selectFinancialPayment = selectFinancialPayment;
	}

	public Date getBalanceStartDate() {
		return balanceStartDate;
	}

	public void setBalanceStartDate(Date balanceStartDate) {
		this.balanceStartDate = balanceStartDate;
	}

	public Date getBalanceEndDate() {
		return balanceEndDate;
	}

	public void setBalanceEndDate(Date balanceEndDate) {
		this.balanceEndDate = balanceEndDate;
	}

	public List<PensionNormalpayment> getNormalPayments() {
		return normalPayments;
	}

	public void setNormalPayments(List<PensionNormalpayment> normalPayments) {
		this.normalPayments = normalPayments;
	}

	public List<PensionNormalpaymentdetail> getNormalPaymentDetails() {
		return normalPaymentDetails;
	}

	public void setNormalPaymentDetails(
			List<PensionNormalpaymentdetail> normalPaymentDetails) {
		this.normalPaymentDetails = normalPaymentDetails;
	}

	public List<PensionTemppayment> getTempPayments() {
		return tempPayments;
	}

	public void setTempPayments(List<PensionTemppayment> tempPayments) {
		this.tempPayments = tempPayments;
	}

	public List<PensionTemppaymentdetail> getTempPaymentDetails() {
		return tempPaymentDetails;
	}

	public void setTempPaymentDetails(
			List<PensionTemppaymentdetail> tempPaymentDetails) {
		this.tempPaymentDetails = tempPaymentDetails;
	}

	public List<PensionDepositLog> getDepositLogs() {
		return depositLogs;
	}

	public void setDepositLogs(List<PensionDepositLog> depositLogs) {
		this.depositLogs = depositLogs;
	}

	public Float getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Float totalFee) {
		this.totalFee = totalFee;
	}

	public List<PensionDicPayway> getPayWays() {
		return payWays;
	}

	public void setPayWays(List<PensionDicPayway> payWays) {
		this.payWays = payWays;
	}

	public List<Long> getRoles() {
		return roles;
	}

	public void setRoles(List<Long> roles) {
		this.roles = roles;
	}

	public String getDeptIdsStr() {
		return deptIdsStr;
	}

	public void setDeptIdsStr(String deptIdsStr) {
		this.deptIdsStr = deptIdsStr;
	}

	public List<Long> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}

	public Long getFinancialBalanceType() {
		return financialBalanceType;
	}

	public void setFinancialBalanceType(Long financialBalanceType) {
		this.financialBalanceType = financialBalanceType;
	}

	public PensionSysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(PensionSysUser sysUser) {
		this.sysUser = sysUser;
	}

	public boolean isSeeFlag() {
		return seeFlag;
	}

	public void setSeeFlag(boolean seeFlag) {
		this.seeFlag = seeFlag;
	}

}
