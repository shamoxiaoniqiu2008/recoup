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
import domain.financeManage.PensionFinancialbudget;

/**
 * 发放财务预算
 * 
 * @author mary.liu
 */

public class GrantBudgetController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient BudgetService budgetService;

	// 查询
	private Date startDate;
	private Date endDate;
	private Integer approvalResult;

	private List<PensionFinancialbudget> financialBudgets = new ArrayList<PensionFinancialbudget>();

	private List<PensionApprovalbudget> approvalBudgets = new ArrayList<PensionApprovalbudget>();

	private PensionFinancialbudget selectFinancialBudget;

	private PensionEmployee employee;

	private final static Integer YES = 1;

	@PostConstruct
	public void init() {
		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		this.search();

	}

	public void search() {
		/*
		 * if((startDate==null&&endDate!=null)||(startDate!=null&&endDate==null))
		 * { FacesContext.getCurrentInstance().addMessage(null, new
		 * FacesMessage("按日期查询时，请同时输入开始日期和结果日期！", "按日期查询时，请同时输入开始日期和结果日期！"));
		 * return; }
		 */
		try {
			if (!new Integer(0).equals(approvalResult)
					&& !new Integer(1).equals(approvalResult)
					&& !new Integer(2).equals(approvalResult)) {
				approvalResult = null;
			}
			financialBudgets = budgetService.selectApprovalBudgets(startDate,
					endDate, approvalResult, null, null, null);
			selectFinancialBudget = null;
			approvalBudgets = new ArrayList<PensionApprovalbudget>();
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
		}
	}

	public void onFinancialBudgetSelect(SelectEvent e) {
		approvalBudgets = budgetService
				.selectApprovalBudgets(selectFinancialBudget.getId());
	}

	public void onFinancialBudgetUnselect(UnselectEvent e) {
		approvalBudgets = new ArrayList<PensionApprovalbudget>();
	}

	public void clearSearchForm() {
		startDate = null;
		endDate = null;
		approvalResult = null;
	}

	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkGrantData(FacesContext context, UIComponent component,
			Object obj) {
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable listTable = (DataTable) component
				.findComponent("listForm:list");
		PensionFinancialbudget arr = (PensionFinancialbudget) listTable
				.getSelection();
		if (arr == null || arr.getId() == null) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "请先选择数据！", "请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		} else if (selectFinancialBudget.getGiverId() != null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("该财务申请已发放，不能重复发放！", "该财务申请已发放，不能重复发放！"));
			request.addCallbackParam("validate", false);
		}/*
		 * else if(!YES.equals(selectFinancialBudget.getApproveresult())){
		 * FacesContext.getCurrentInstance().addMessage(null, new
		 * FacesMessage("该申请还没有通过审批，不能发放！", "该申请还没有通过审批，不能发放！"));
		 * request.addCallbackParam("validate", false); }
		 */else {
			request.addCallbackParam("validate", true);
		}
	}

	/**
	 * check 查询时间 开始日期要不大于结束日期
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSearchDate(FacesContext context, UIComponent component,
			Object obj) {
		Calendar startDate = (Calendar) component
				.findComponent("searchForm:startDate");
		Date start = (Date) startDate.getValue();
		Calendar endDate = (Calendar) component
				.findComponent("searchForm:endDate");
		Date end = (Date) endDate.getValue();
		if (start != null && end != null && start.after(end)) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "日期时间应该不大于结束日期！",
					"日期时间应该不大于结束日期！");
			throw new ValidatorException(message);
		}
	}

	/**
	 * 发放财务预算申请
	 */
	public void grantBudget() {
		try {
			Date givetime = new Date();
			budgetService.grantBudget(selectFinancialBudget.getId(),
					employee.getId(), employee.getName(), givetime);
			selectFinancialBudget.setGiverId(employee.getId().intValue());
			selectFinancialBudget.setGivername(employee.getName());
			selectFinancialBudget.setGivetime(givetime);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("发放财务预算成功！", "发放财务预算成功！"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("发放财务预算时出错！", "发放财务预算时出错！"));
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

	public void setFinancialBudgets(
			List<PensionFinancialbudget> financialBudgets) {
		this.financialBudgets = financialBudgets;
	}

	public PensionFinancialbudget getSelectFinancialBudget() {
		return selectFinancialBudget;
	}

	public void setSelectFinancialBudget(
			PensionFinancialbudget selectFinancialBudget) {
		this.selectFinancialBudget = selectFinancialBudget;
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
