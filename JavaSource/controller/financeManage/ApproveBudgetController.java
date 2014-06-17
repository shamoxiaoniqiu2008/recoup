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

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.financeManage.BudgetService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionApprovalbudget;
import domain.financeManage.PensionDictBudgettype;
import domain.financeManage.PensionFinancialbudget;
import domain.system.PensionDept;


/**
 * 预算审批
 *
 *
 */

public class ApproveBudgetController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient BudgetService budgetService;
	
	
	//查询
	private Date startDate;
	private Date endDate;
	private Integer approvalResult;
	
	private Long budgetId;
	
	private List<PensionFinancialbudget> financialBudgets=new ArrayList<PensionFinancialbudget>();
	
	private PensionFinancialbudget selectFinancialBudget;
	
	
	private List<PensionApprovalbudget> approvalBudgets=new ArrayList<PensionApprovalbudget>();
	
	private PensionApprovalbudget approvalBudget;
	
	private PensionEmployee employee;
	
	private List<PensionDictBudgettype> budgettypes;
	
	private List<PensionDept> approveDepts;
	
	private List<PensionEmployee> approveEmployees;
	
	private final static Integer YES=1;
	private final static Integer NO=2;
	
	
	@PostConstruct
	public void init(){
		
		//当由消息进入时，读取预算的编号并显示
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String budgetIdStr = paramsMap.get("budgetId");
		if (budgetIdStr != null) {
			budgetId = Long.valueOf(budgetIdStr);
		} else {
			budgetId = null;
		}
		
		employee=(PensionEmployee)SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
		budgettypes=budgetService.selectBudgetTypes();
		this.search();
		
	}
	
	/**
	 * 查询
	 */
	public void search(){
		try {
			if(!new Integer(0).equals(approvalResult)&&!new Integer(1).equals(approvalResult)&&!new Integer(2).equals(approvalResult)){
				approvalResult=null;
			}
			financialBudgets=budgetService.selectApprovalBudgets(startDate,endDate,approvalResult,employee.getId(),employee.getDeptId(),budgetId);
			budgetId=null;
			selectFinancialBudget=null;
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("该记录已删除！", "该记录已删除！"));
		}
	}
	
	public void onFinancialBudgetSelect(SelectEvent e){
		approvalBudgets=budgetService.selectApprovalBudgets(selectFinancialBudget.getId());
	}
	
	public void onFinancialBudgetUnselect(UnselectEvent e){
		approvalBudgets=new ArrayList<PensionApprovalbudget>();
	}
	
	public void clearSearchForm(){
		startDate=null;
		endDate=null;
		approvalResult=null;
	}
	
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据，是否能修改，是否能审批
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectData(FacesContext context,UIComponent component, Object obj){
			try {
				RequestContext request = RequestContext.getCurrentInstance();
				DataTable  listTable = (DataTable) component.findComponent("listForm:list");
				PensionFinancialbudget  arr = (PensionFinancialbudget) listTable.getSelection();
				if( arr == null || arr.getId() == null ){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
					request.addCallbackParam("validate", false);
					throw new ValidatorException(message);
				}/*else if(YES.equals(selectFinancialBudget.getApproveresult())||NO.equals(selectFinancialBudget.getApproveresult())){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("该申请已有审批结果，不能修改！", "该申请已有审批结果，不能修改！"));
					request.addCallbackParam("validate", false);
				}*/else if(selectFinancialBudget.getGiverId()!=null){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("该财务申请已发放，不能修改！", "该财务申请已发放，不能修改！"));
					request.addCallbackParam("validate", false);
				}else if(budgetService.checkSelectDate(selectFinancialBudget.getId(),employee.getId(),employee.getDeptId())){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("该申请已有当前部门的审批结果，不能再次审批！", "该申请已有当前部门的审批结果，不能再次审批！"));
					request.addCallbackParam("validate", false);
				}else{
					request.addCallbackParam("validate", true);
				}
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage(), e.getMessage()));
			}
	}
	
	/**
	 * check 查询条件 开始日期要不大于结束日期
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSearchDate(FacesContext context,UIComponent component, Object obj){
		Calendar  startDate = (Calendar) component.findComponent("searchForm:startDate");
		Date start=(Date)startDate.getValue();
		Calendar  endDate = (Calendar) component.findComponent("searchForm:endDate");
		Date end=(Date)endDate.getValue();
		if(start!=null && end!=null && start.after(end)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"日期时间应该不大于结束日期！","日期时间应该不大于结束日期！");
			throw new ValidatorException(message);
		}
	}
	
	/**
	 * 读取要审批的财务预算的详细信息
	 */
	public void approval(){
		try {
			approvalBudget=budgetService.selectApprovalBudget(selectFinancialBudget.getId(),//财务预算编号
					employee.getId(),//员工编号
					employee.getDeptId());//员工部门编号
			if(approvalBudget.getApprovalId()==null){//如果该审批是发向部门的，则补填当前审批人的编号姓名和审批日期
				approvalBudget.setApprovalId(employee.getId().intValue());
				approvalBudget.setApprovalName(employee.getName());
				approvalBudget.setApprovalTime(new Date());
			}
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
		}
	}
	
	/**
	 * 保存审批结果
	 */
	public void saveApprovalBudget(){
		RequestContext request = RequestContext.getCurrentInstance();
		try{
			approvalBudget.setApprovalTime(new Date());
			selectFinancialBudget=budgetService.updateApprovalBudget(approvalBudget,employee.getId(),employee.getDeptId());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("您的审批结果已提交！", "您的审批结果已提交！"));
			//将审批结果显示在页面上
			for(int i=0;i<financialBudgets.size();i++){
				if(financialBudgets.get(i).getId().equals(selectFinancialBudget.getId())){
					financialBudgets.remove(i);
					financialBudgets.add(i, selectFinancialBudget);
					break;
				}
			}
			approvalBudgets=budgetService.selectApprovalBudgets(selectFinancialBudget.getId());
			request.addCallbackParam("validate", true);
		}catch(PmsException e){
			approvalBudgets=budgetService.selectApprovalBudgets(selectFinancialBudget.getId());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("您的审批结果已提交！", "您的审批结果已提交！"));
			/*FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));*/
			request.addCallbackParam("validate", true);
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("提交评审结果时出错！", "提交评审结果时出错！"));
			request.addCallbackParam("validate", false);
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

	public Long getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}

	public PensionApprovalbudget getApprovalBudget() {
		return approvalBudget;
	}

	public void setApprovalBudget(PensionApprovalbudget approvalBudget) {
		this.approvalBudget = approvalBudget;
	}

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
	}

	public List<PensionApprovalbudget> getApprovalBudgets() {
		return approvalBudgets;
	}

	public void setApprovalBudgets(List<PensionApprovalbudget> approvalBudgets) {
		this.approvalBudgets = approvalBudgets;
	}
}
