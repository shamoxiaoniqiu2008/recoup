package controller.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.employee.EmployeeManageService;
import service.system.DeptManageService;
import service.system.SystemConfigService;

import domain.employee.PensionEmployee;
import domain.system.PensionDept;
import domain.system.PensionSystemConfig;

public class SystemConfigController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionSystemConfig> records = new ArrayList<PensionSystemConfig>();
	/**
	 * 被选中的记录
	 */
	private PensionSystemConfig selectedRow;
	/**
	 * 被修改的记录
	 */
	private PensionSystemConfig updatedRow = new PensionSystemConfig();
	/**
	 * 绑定关于老人信息的输入法
	 */
	private String systemConfigSql;
	/**
	 * 老人主键用于查询条件
	 */
	private Long configId;
	private Integer type = 1;
	/**
	 * 修改和删除按钮是否可用
	 */
	private boolean disUpdateButton = true;
	/**
	 * 注入业务
	 */
	private transient SystemConfigService systemConfigService;
	private transient DeptManageService deptManageService;
	private transient EmployeeManageService employeeManageService;
	private List<PensionDept> depts = new ArrayList<PensionDept>();
	private PensionDept dept = new PensionDept();
	private List<PensionEmployee> emps = new ArrayList<PensionEmployee>();
	private PensionEmployee emp = new PensionEmployee();
	
	private String deptSQL;
	private String empSQL;
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
		selectSystemConfigRecords();
	}
	
	public void selectNames()
	{
		depts = deptManageService.selectById(string2List(updatedRow.getConfigValue()));
		emps = employeeManageService.selectById(string2List(updatedRow.getConfigValue()));
	}
	
	public List<Long> string2List(String value)
	{
		String[] vs = value.split(",");
		List<Long> ids = new ArrayList<Long>();
		for(String s:vs)
		{
			Long l = Long.valueOf(s);
			ids.add(l);
		}
		return ids;
	}
	
	public String list2String(List<Long>ids)
	{
		String value = "";
		if(ids == null)return value;
		for(int i=0;i<ids.size();i++)
		{
			if(i==0)
			{
				value = value+String.valueOf(ids.get(i));
			}
			else
			{
				value = value+","+String.valueOf(ids.get(i));
			}
		}
		return value;
	}
	/**
	 * 添加新值
	 * */
	public void addDeptValue()
	{
		Long value = dept.getId();
		List<Long> ids = string2List(updatedRow.getConfigValue());
		ids.add(value);
		updatedRow.setConfigValue(list2String(ids));
		selectNames();
		dept = new PensionDept();
	}
	/**
	 * 删除存在值
	 * */
	public void deleteDeptValue()
	{
		if(dept==null)
		{
			sendMessage2Client(FacesMessage.SEVERITY_WARN,"请选择一条记录","删除失败");
			return;
		}
		Long value = dept.getId();
		List<Long> ids = string2List(updatedRow.getConfigValue());
		ids.remove(value);
		updatedRow.setConfigValue(list2String(ids));
		selectNames();
	}
	
	public void sendMessage2Client(Severity severity, String summary, String detail)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(severity,summary, detail);	
		context.addMessage(null, message);
		request.addCallbackParam("success", false);
	}
	/**
	 * 添加新值
	 * */
	public void addEmpValue()
	{
		Long value = emp.getId();
		List<Long> ids = string2List(updatedRow.getConfigValue());
		ids.add(value);
		updatedRow.setConfigValue(list2String(ids));
		selectNames();
		emp = new PensionEmployee();
		
	}
	/**
	 * 删除存在值
	 * */
	public void deleteEmptValue()
	{
		if(emp==null)
		{
			sendMessage2Client(FacesMessage.SEVERITY_WARN,"请选择一条记录","删除失败");
			return;
		}		
		Long value = emp.getId();
		List<Long> ids = string2List(updatedRow.getConfigValue());
		ids.remove(value);
		updatedRow.setConfigValue(list2String(ids));
		selectNames();
	}
	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		
		systemConfigSql = "select psc.id as id," +
				"psc.config_name        as config_name," +
				"psc.config_description as config_description " +
				"from pension_system_config psc "+
				"where psc.type = " + type;
		if(selectedRow != null)
		{
			deptSQL = "select * from pension_dept d where d.cleared = 2 and d.id not in (" + selectedRow.getConfigValue()+")";
			empSQL = "select * from pension_employee d where d.cleared = 2 and d.id not in (" + selectedRow.getConfigValue()+")";
		}
		else
		{
			deptSQL = "select * from pension_dept d where d.cleared = 2";
			empSQL = "select * from pension_employee d where d.cleared = 2";
		}
	}
	
	/**
	 * 查询系统参数记录
	 */
	public void selectSystemConfigRecords(){
		disUpdateButton = true;
		selectedRow = null;
		setRecords(systemConfigService.selectSystemConfigRecords(configId,type));
		
	}
	
	/**
	 * 修改系统参数记录
	 */
	public void updateSystemConfigRecord(){
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "修改成功";
		systemConfigService.updateSystemConfigRecord(updatedRow);
		selectSystemConfigRecords();
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
	 * 情况selectForm
	 */
	public void clearSelectForm(){
		configId = null;
	}
	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		setDisUpdateButton(false);
		initSql();
	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		setDisUpdateButton(true);
	}
	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordUpdatedRow() {
		updatedRow = selectedRow;
	}
	
	public void setOlderNameSql(String olderNameSql) {
		this.systemConfigSql = olderNameSql;
	}
	
	public String getOlderNameSql() {
		return systemConfigSql;
	}
	
	public void setDisUpdateButton(boolean disUpdateButton) {
		this.disUpdateButton = disUpdateButton;
	}

	public boolean isDisUpdateButton() {
		return disUpdateButton;
	}

	public PensionSystemConfig getUpdatedRow() {
		return updatedRow;
	}

	public void setUpdatedRow(PensionSystemConfig updatedRow) {
		this.updatedRow = updatedRow;
	}

	public void setSystemConfigService(SystemConfigService systemConfigService) {
		this.systemConfigService = systemConfigService;
	}

	public SystemConfigService getSystemConfigService() {
		return systemConfigService;
	}

	public void setSystemConfigSql(String systemConfigSql) {
		this.systemConfigSql = systemConfigSql;
	}

	public String getSystemConfigSql() {
		return systemConfigSql;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setRecords(List<PensionSystemConfig> records) {
		this.records = records;
	}

	public List<PensionSystemConfig> getRecords() {
		return records;
	}

	public void setSelectedRow(PensionSystemConfig selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionSystemConfig getSelectedRow() {
		return selectedRow;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setDepts(List<PensionDept> depts) {
		this.depts = depts;
	}

	public List<PensionDept> getDepts() {
		return depts;
	}

	public void setDeptManageService(DeptManageService deptManageService) {
		this.deptManageService = deptManageService;
	}

	public DeptManageService getDeptManageService() {
		return deptManageService;
	}

	public void setEmployeeManageService(EmployeeManageService employeeManageService) {
		this.employeeManageService = employeeManageService;
	}

	public EmployeeManageService getEmployeeManageService() {
		return employeeManageService;
	}

	public void setDept(PensionDept dept) {
		this.dept = dept;
	}

	public PensionDept getDept() {
		return dept;
	}

	public void setEmps(List<PensionEmployee> emps) {
		this.emps = emps;
	}

	public List<PensionEmployee> getEmps() {
		return emps;
	}

	public void setEmp(PensionEmployee emp) {
		this.emp = emp;
	}

	public PensionEmployee getEmp() {
		return emp;
	}

	public void setDeptSQL(String deptSQL) {
		this.deptSQL = deptSQL;
	}

	public String getDeptSQL() {
		return deptSQL;
	}

	public void setEmpSQL(String empSQL) {
		this.empSQL = empSQL;
	}

	public String getEmpSQL() {
		return empSQL;
	}

}
