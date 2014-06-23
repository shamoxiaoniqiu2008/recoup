package controller.employee;

import java.io.Serializable;
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

import service.employee.EmployeeManageService;
import util.DateUtil;
import util.Spell;

import com.onlyfido.util.SessionManager;

import domain.employee.PensionEmployee;

/**
 * 员工管理 author:mary liu
 * 
 * 
 */

public class EmployeeManageController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Integer NO_FLAG = 2;// 否

	private transient EmployeeManageService employeeManageService;

	// 查询
	private Long empId;
	private String empName;

	// 主记录
	private List<PensionEmployee> employees;
	private PensionEmployee selectEmployee;

	private String deptName;
	private String inputCode;

	private boolean addFlag;
	private boolean modifyFlag;
	private boolean viewFlag;

	private String selectEmpSql;
	private String selectDeptSql;

	// 当前用户
	private PensionEmployee employee;

	@PostConstruct
	public void init() {
		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		this.initSql();
		this.search();
	}

	public void initSql() {
		selectEmpSql = "select e.id,e.name,e.inputCode from pension_employee e where e.cleared = 2";
		selectDeptSql = "select dept.id,dept.name,dept.inputCode from pension_dept dept where dept.cleared = 2";
	}

	/**
	 * 按条件查询
	 */
	public void search() {
		if (empName == null || "".equals(empName)) {
			empId = null;
		}
		employees = employeeManageService.search(empId);
		selectEmployee = null;
	}

	public void onEmployeeUnselect(UnselectEvent e) {

	}

	public void onEmployeeSelect(SelectEvent e) {

	}

	/**
	 * 清除搜索框
	 */
	public void clearSearchForm() {
		empId = null;
		empName = null;
	}

	/**
	 * check 员工主记录dateTable 是否选择了数据
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectData(FacesContext context, UIComponent component,
			Object obj) {
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable listTable = (DataTable) component
				.findComponent("empForm:empList");
		PensionEmployee arr = (PensionEmployee) listTable.getSelection();
		if (arr == null || arr.getId() == null) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "请先选择数据！", "请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		} else {
			request.addCallbackParam("validate", true);
		}
	}

	/**
	 * 点击【新增】按钮
	 */
	public void add() {
		selectEmployee = new PensionEmployee();
		addFlag = true;
		modifyFlag = false;
		deptName = null;
		viewFlag = false;
		selectEmployee.setRestId(employee.getRestId());
		selectEmployee.setCleared(NO_FLAG);
	}

	/**
	 * 检查该输入数字的是否正确输入了数字
	 */
	public void checkInfo() {
		try {
			inputCode = Spell.getFirstSpell(selectEmployee.getName());
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请在工资处输入数字！", "请在工资处输入数字！"));
		}
	}

	/**
	 * 保存员工
	 */
	public void saveEmployee() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			selectEmployee.setInputcode(inputCode);
			selectEmployee.setAge(DateUtil.selectAge(selectEmployee
					.getBirthday()));
			if (addFlag) {// 新增-保存
				Long id = employeeManageService.saveEmployee(selectEmployee);
				selectEmployee.setId(id);
				employees.add(selectEmployee);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("新增一条员工记录成功！", "新增一条员工记录成功！"));
				request.addCallbackParam("validate", true);
			} else if (modifyFlag) {// 修改-更新
				employeeManageService.updateEmployee(selectEmployee);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("更新一条员工记录成功！", "更新一条员工记录成功！"));
				request.addCallbackParam("validate", true);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("保存失败！", "保存失败！"));
			request.addCallbackParam("validate", false);
			e.printStackTrace();
		}
	}

	/**
	 * 点击【修改】按钮
	 */
	public void modify() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectEmployee == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请选择一条数据", ""));
			requestContext.addCallbackParam("modify", false);
		} else {
			deptName = employeeManageService.selectDeptName(selectEmployee
					.getDeptId());
			addFlag = false;
			modifyFlag = true;
			viewFlag = false;
			requestContext.addCallbackParam("modify", true);
		}
	}

	public void checkDelete() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectEmployee == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请选择一条数据", ""));
			requestContext.addCallbackParam("del", false);
		} else {
			requestContext.addCallbackParam("del", true);
		}
	}

	/**
	 * 点击【查看明细】按钮
	 */
	public void view() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectEmployee == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请选择一条数据", ""));
			requestContext.addCallbackParam("view", false);
		} else {
			deptName = employeeManageService.selectDeptName(selectEmployee
					.getDeptId());
			requestContext.addCallbackParam("view", true);
		}
	}

	public void delete() {
		try {
			employeeManageService.deleteEmployee(selectEmployee.getId());
			employees.remove(selectEmployee);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("删除成功！", "删除成功！"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("删除失败！", "删除失败！"));
			e.printStackTrace();
		}
	}

	public EmployeeManageService getEmployeeManageService() {
		return employeeManageService;
	}

	public void setEmployeeManageService(
			EmployeeManageService employeeManageService) {
		this.employeeManageService = employeeManageService;
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

	public List<PensionEmployee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<PensionEmployee> employees) {
		this.employees = employees;
	}

	public PensionEmployee getSelectEmployee() {
		return selectEmployee;
	}

	public void setSelectEmployee(PensionEmployee selectEmployee) {
		this.selectEmployee = selectEmployee;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	public String getSelectEmpSql() {
		return selectEmpSql;
	}

	public void setSelectEmpSql(String selectEmpSql) {
		this.selectEmpSql = selectEmpSql;
	}

	public String getSelectDeptSql() {
		return selectDeptSql;
	}

	public void setSelectDeptSql(String selectDeptSql) {
		this.selectDeptSql = selectDeptSql;
	}

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
