package controller.financeManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.financeManage.BudgetService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionDictBudgettype;
import domain.financeManage.PensionFinancialbudget;
import domain.system.PensionDept;


/**
 * 
 *
 *
 */

public class MyBudgetController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient BudgetService budgetService;
	
	
	
	//查询
	private Date startDate;
	private Date endDate;
	private Integer approvalResult;
	
	private String money;
	
	private List<PensionFinancialbudget> financialBudgets=new ArrayList<PensionFinancialbudget>();
	
	private PensionFinancialbudget selectFinancialBudget;
	
	private PensionEmployee employee;
	
	private List<PensionDictBudgettype> budgettypes;
	
	private List<PensionDept> approveDepts;
	
	private List<PensionEmployee> approveEmployees;
	
	private final static Integer YES=1;
	private final static Integer NO=2;
	
	private final static String BUDGET_APPROVEING="正在审批";
	
	
	
	@PostConstruct
	public void init(){
		employee=(PensionEmployee) SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
		budgettypes=budgetService.selectBudgetTypes();
		this.search();
		
	}
	
	public void search(){
	/*	if((startDate==null&&endDate!=null)||(startDate!=null&&endDate==null)){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("按日期查询时，请同时输入开始日期和结果日期！", "按日期查询时，请同时输入开始日期和结果日期！"));
			return;
		}*/
		if(!new Integer(0).equals(approvalResult)&&!new Integer(1).equals(approvalResult)&&!new Integer(2).equals(approvalResult)){
			approvalResult=null;
		}
		financialBudgets=budgetService.selectMyBudgets(startDate,endDate,approvalResult,employee.getId(),null);
		selectFinancialBudget=null;
	}
	
	public void onFinancialBudgetSelect(SelectEvent e){
		
	}
	
	public void onFinancialBudgetUnselect(UnselectEvent e){
		
	}
	
	/**
	 * 清空搜索框
	 */
	public void clearSearchForm(){
		startDate=null;
		endDate=null;
		approvalResult=null;
	}
	
	/**
	 * 点击【新增】
	 */
	public void add(){
		try {
			//读取预算申请发送的部门和人员
			if ((approveDepts == null || approveDepts.size() == 0)
					&& (approveEmployees == null || approveEmployees.size() == 0)) {
				approveDepts = budgetService.selectApproveDepts();
				approveEmployees = budgetService.selectApproveEmployees();
			}
			if (approveDepts.size() == 0 && approveEmployees.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("您还没有指定预算申请发送的部门和人员，不能新增申请！",
								"您还没有指定预算申请发送的部门和人员，不能新增申请！"));
			}
			//初始化 财务申请
			money="";
			selectFinancialBudget = new PensionFinancialbudget();
			selectFinancialBudget.setApplyerId(employee.getId());
			selectFinancialBudget.setApplyername(employee.getName());
			selectFinancialBudget.setApplytime(new Date());
			selectFinancialBudget.setCleared(NO);
		} catch (PmsException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送消息
	 */
	public void send(){
		try {
			budgetService.sendMessage(selectFinancialBudget.getId(), employee.getName());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("发送预算申请消息成功！", "发送预算申请消息成功！"));
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("发送预算申请消息时出错！","发送预算申请消息时出错！"));
		}
	}
	
	/**
	 * 保存并发送消息
	 */
	public void saveAndSend(){
		this.saveMyFinancialBudget();
		this.send();
	}

	/**
	 * 保存预算申请
	 */
	public void saveMyFinancialBudget(){
		RequestContext request = RequestContext.getCurrentInstance();
		try{
			//验证 申请金额 必须为正值
			Float moneyTemp=Float.valueOf(money);
			if(!(moneyTemp.floatValue()>0)){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("金额 请输正值！", "金额 请输正值！"));
				request.addCallbackParam("validate", false);
				return;
			}
			//新增一条财务申请，
			if(selectFinancialBudget.getId()==null||new Long(0).equals(selectFinancialBudget.getId())){
				selectFinancialBudget.setMoney(moneyTemp);
				Long id=budgetService.insertMyFinancialBudget(selectFinancialBudget,approveDepts,approveEmployees);
				selectFinancialBudget.setId(id);
				selectFinancialBudget.setBudgetTypeName(budgetService.selectBudgetTypeName(selectFinancialBudget.getBudgettype().longValue()));
				selectFinancialBudget.setApproveResultStr(BUDGET_APPROVEING);
				financialBudgets.add(0, selectFinancialBudget);
			}else{//更新一条财务申请
				selectFinancialBudget.setMoney(moneyTemp);
				budgetService.updateMyFinancialBudget(selectFinancialBudget);
			}
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("保存预算申请成功！", "保存预算申请成功！"));
			request.addCallbackParam("validate", true);
		}catch(NumberFormatException e){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("金额 只能输入数字！", "金额 只能输入数字！"));
			request.addCallbackParam("validate", false);
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("保存预算申请时出错！", "保存预算申请时出错！"));
			request.addCallbackParam("validate", false);
		}
	}

	
	/**
	 * check 常见结果列表dateTable 是否选择了数据 该数据是否能操作
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("listForm:list");
		PensionFinancialbudget  arr = (PensionFinancialbudget) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else if(budgetService.checkDeleteData(selectFinancialBudget.getId())){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("已有人审批了该条申请，不能操作！", "已有人审批了该条申请，不能操作！"));
			request.addCallbackParam("validate", false);
		}else{
			money=arr.getMoney().toString();
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkMyData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("listForm:list");
		PensionFinancialbudget  arr = (PensionFinancialbudget) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * check 常见结果列表dateTable 是否已有审批结果
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSendData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("listForm:list");
		PensionFinancialbudget  arr = (PensionFinancialbudget) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else if( YES.equals(arr.getApproveresult())||NO.equals(arr.getApproveresult()) ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"该财务审批已有审批结果！","该财务审批已有审批结果！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	
	/**
	 * 删除财务申请
	 */
	public void delete(){
		try{
			budgetService.deleteFinancialBudget(selectFinancialBudget.getId());
			financialBudgets.remove(selectFinancialBudget);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("删除预算申请成功！", "删除预算申请成功！"));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("删除预算申请失败！", "删除预算申请失败！"));
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public BudgetService getBudgetService() {
		return budgetService;
	}

	public void setBudgetService(BudgetService budgetService) {
		this.budgetService = budgetService;
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

	public Integer getApprovalResult() {
		return approvalResult;
	}

	public void setApprovalResult(Integer approvalResult) {
		this.approvalResult = approvalResult;
	}

	public List<PensionFinancialbudget> getFinancialBudgets() {
		return financialBudgets;
	}

	public void setFinancialBudgets(List<PensionFinancialbudget> financialBudgets) {
		this.financialBudgets = financialBudgets;
	}

	public PensionFinancialbudget getSelectFinancialBudget() {
		return selectFinancialBudget;
	}

	public void setSelectFinancialBudget(
			PensionFinancialbudget selectFinancialBudget) {
		this.selectFinancialBudget = selectFinancialBudget;
	}

	

	public List<PensionDictBudgettype> getBudgettypes() {
		return budgettypes;
	}

	public void setBudgettypes(List<PensionDictBudgettype> budgettypes) {
		this.budgettypes = budgettypes;
	}

	public List<PensionDept> getApproveDepts() {
		return approveDepts;
	}

	public void setApproveDepts(List<PensionDept> approveDepts) {
		this.approveDepts = approveDepts;
	}

	public List<PensionEmployee> getApproveEmployees() {
		return approveEmployees;
	}

	public void setApproveEmployees(List<PensionEmployee> approveEmployees) {
		this.approveEmployees = approveEmployees;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
	}

}
