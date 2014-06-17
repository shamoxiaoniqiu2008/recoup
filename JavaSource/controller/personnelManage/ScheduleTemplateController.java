package controller.personnelManage;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import service.personnelManage.StaffScheduleService;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionNormalpayment;
import domain.hrManage.PensionArrangeTemplate;
import domain.hrManage.PensionArrangeTemplateDetail;


/**
 * 日常缴费录入   author:mary liu
 *
 *
 */

public class ScheduleTemplateController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient StaffScheduleService staffScheduleService;
	
	//查询
	private Long templateId;
	private String templateName;
	private String templateSql = "SELECT pat.id,pat.template_name FROM pension_arrange_template pat WHERE pat.cleared = 2";
	private String addArrageSql="select arr.id,arr.arrange_name,arr.input_code, "
			+" DATE_FORMAT(arr.start_time,'%H:%m:%s'), "
			+" DATE_FORMAT(arr.end_time,'%H:%m:%s'), "
			+" IF(arr.across_flag > 1,'否','是') "
			+" from pension_dic_arrange arr "
			+" where arr.cleared = 2";
	private String selectDeptSql = "select pd.id,pd.name,pd.inputCode from pension_dept pd where pd.cleared = 2"; 
	private String selectEmpSql = "select pe.id,pe.name,pe.inputCode from pension_employee pe where pe.cleared = 2";
	
	//主记录
	private List<PensionArrangeTemplateDetail> arrangeTemplateDetails;
	private PensionArrangeTemplateDetail selectTemplateDetail;
	
	//新增/修改
	private boolean addFlag;
	private String startTimeHidden;
	private String endTimeHidden;
	private Long deptId;
	private String deptName;
	private Long empId;
	private String empName;
	private PensionArrangeTemplate template;
	private List<PensionArrangeTemplateDetail> details;
	
	
	//当前信息
	private PensionEmployee employee;//当前用户
	
	
	@PostConstruct
	public void init(){
		addFlag=false;//默认新增
		employee=(PensionEmployee) SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
		this.search();
	}
	
	public void search(){
		if(templateName == null || "".equals(templateName)){
			templateId = null;
		}
		arrangeTemplateDetails=staffScheduleService.searchArrangeTemplates(templateId);
		selectTemplateDetail=null;
	}
	
	
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("scheduleListForm:scheduleList");
		PensionArrangeTemplateDetail arr = (PensionArrangeTemplateDetail) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * 确认删除
	 */
	public void delete(){
		try{
			List<PensionArrangeTemplateDetail> details = new ArrayList<PensionArrangeTemplateDetail>();
			for(PensionArrangeTemplateDetail detail:arrangeTemplateDetails){
				if(detail.getTemplateId() == selectTemplateDetail.getTemplateId()){
					details.add(detail);
				}
			}
			staffScheduleService.deleteArrangeTemplate(selectTemplateDetail.getTemplateId());
			arrangeTemplateDetails.removeAll(details);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("删除成功！", "删除成功！"));
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("删除失败！", "删除失败！"));
		}
		
	}
	
	public void onArrangeTemplateSelect(SelectEvent e){
		
	}
	
	public void onArrangeTemplateUnselect(UnselectEvent e){
		
	}
	
	/**
	 * 点击【新增】
	 */
	public void add(){
		template = new PensionArrangeTemplate();
		selectTemplateDetail = new PensionArrangeTemplateDetail();
		details = new ArrayList<PensionArrangeTemplateDetail>();
		addFlag=false;
	}

	/**
	 * 新增明细
	 */
	public void addDetail(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			selectTemplateDetail.setStartTime(sdf.parse(startTimeHidden));
			selectTemplateDetail.setEndTime(sdf.parse(endTimeHidden));
		} catch (ParseException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("转换时间格式出错！", "转换时间格式出错！"));
		}
		selectTemplateDetail.setDepartId(template.getDeptId());
		selectTemplateDetail.setDepartName(template.getDeptName());
		selectTemplateDetail.setTemplateName(template.getTemplateName());
		details.add(selectTemplateDetail);
		selectTemplateDetail = new PensionArrangeTemplateDetail();
		startTimeHidden = null;
		endTimeHidden = null;
	}
	
	/**
	 * 删除明细
	 */
	public void deleteDetail(PensionArrangeTemplateDetail detail){
		details.remove(detail);
	}

	/**
	 * 改变选择的部门
	 */
	public void onChangeSelectDeptId(){
		if(template.getDeptId()!=null&&!"".equals(template.getDeptId())&&!"".equals(template.getDeptName())){
			selectEmpSql = "select pe.id,pe.name,pe.inputCode from pension_employee pe where pe.cleared = 2 and pe.dept_id = "+template.getDeptId();
			selectTemplateDetail = new PensionArrangeTemplateDetail();
			details = new ArrayList<PensionArrangeTemplateDetail>();
		}else{
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请先选择部门！", "请先选择部门！"));
		}
	}
	
	public void saveArrangeTemplate(){
		RequestContext request = RequestContext.getCurrentInstance();
		if(details.size() < 1){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("模板条目明细不能为空！", "模板条目明细不能为空！"));
			request.addCallbackParam("validate", false);//不关闭对话框
			return ;
		}
		try{
			if(addFlag){//修改
				staffScheduleService.updateArrangeTemplateDetail(template,details);
				this.search();
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("更新成功！", "更新成功！"));
				request.addCallbackParam("validate", true);//关闭对话框
			}else{//新增
				staffScheduleService.insertArrangeTemplate(template,details);
				this.search();
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("新增成功！", "新增成功！"));
				request.addCallbackParam("validate", true);//关闭对话框
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("保存失败！", "保存失败！"));
			request.addCallbackParam("validate", false);//不关闭对话框
		}
	}
	
	public void modify(){
		addFlag=true;//修改
		details = new ArrayList<PensionArrangeTemplateDetail>();
		for(PensionArrangeTemplateDetail detail:arrangeTemplateDetails){
			if(detail.getTemplateId() == selectTemplateDetail.getTemplateId()){
				details.add(detail);
			}
		}
		template = new PensionArrangeTemplate();
		template.setId(selectTemplateDetail.getTemplateId());
		template.setTemplateName(selectTemplateDetail.getTemplateName());
		template.setDeptId(selectTemplateDetail.getDepartId());
		template.setDeptName(selectTemplateDetail.getDepartName());
		selectEmpSql = "select pe.id,pe.name,pe.inputCode from pension_employee pe where pe.cleared = 2 and pe.dept_id = "+template.getDeptId();
		selectTemplateDetail = new PensionArrangeTemplateDetail();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public StaffScheduleService getStaffScheduleService() {
		return staffScheduleService;
	}

	public void setStaffScheduleService(StaffScheduleService staffScheduleService) {
		this.staffScheduleService = staffScheduleService;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
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
	}

	public List<PensionArrangeTemplateDetail> getArrangeTemplateDetails() {
		return arrangeTemplateDetails;
	}

	public void setArrangeTemplateDetails(
			List<PensionArrangeTemplateDetail> arrangeTemplateDetails) {
		this.arrangeTemplateDetails = arrangeTemplateDetails;
	}

	public PensionArrangeTemplateDetail getSelectTemplateDetail() {
		return selectTemplateDetail;
	}

	public void setSelectTemplateDetail(
			PensionArrangeTemplateDetail selectTemplateDetail) {
		this.selectTemplateDetail = selectTemplateDetail;
	}

	public boolean isAddFlag() {
		return addFlag;
	}

	public void setAddFlag(boolean addFlag) {
		this.addFlag = addFlag;
	}

	public String getStartTimeHidden() {
		return startTimeHidden;
	}

	public void setStartTimeHidden(String startTimeHidden) {
		this.startTimeHidden = startTimeHidden;
	}

	public String getEndTimeHidden() {
		return endTimeHidden;
	}

	public void setEndTimeHidden(String endTimeHidden) {
		this.endTimeHidden = endTimeHidden;
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

	public PensionArrangeTemplate getTemplate() {
		return template;
	}

	public void setTemplate(PensionArrangeTemplate template) {
		this.template = template;
	}

	public List<PensionArrangeTemplateDetail> getDetails() {
		return details;
	}

	public void setDetails(List<PensionArrangeTemplateDetail> details) {
		this.details = details;
	}

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
	}

	public String getTemplateSql() {
		return templateSql;
	}

	public String getAddArrageSql() {
		return addArrageSql;
	}

	public void setAddArrageSql(String addArrageSql) {
		this.addArrageSql = addArrageSql;
	}

	public void setTemplateSql(String templateSql) {
		this.templateSql = templateSql;
	}
	

	
}
