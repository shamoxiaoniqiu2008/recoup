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
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.personnelManage.StaffScheduleService;
import util.DateUtil;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.hrManage.PensionArrangeTemplate;
import domain.hrManage.PensionArrangeTemplateDetail;
import domain.hrManage.PensionStaffArrange;

/**
 * 日常缴费录入 author:mary liu
 * 
 * 
 */

public class StaffScheduleController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient StaffScheduleService staffScheduleService;

	private List<PensionArrangeTemplateDetail> arrangeTemplateDetails = new ArrayList<PensionArrangeTemplateDetail>();

	private List<PensionArrangeTemplateDetail> selectTemplateDetail = new ArrayList<PensionArrangeTemplateDetail>();
	// 查询
	private Date startDate;
	private Date endDate;
	private Long deptId;
	private String deptName;
	private Long empId;
	private String empName;
	private String selectDeptSql;
	private String selectEmpSql;
	private final String empSql = "select emp.id,emp.name as empName,emp.inputCode,emp.dept_id,dept.name as deptName "
			+ " from pension_employee emp "
			+ " left join pension_dept dept "
			+ " on emp.dept_id = dept.id "
			+ " where emp.rest_id = '1' and emp.cleared = 2 ";

	// 主列表
	private List<PensionStaffArrange> arranges;
	private PensionStaffArrange[] selectArranges;

	// 新增
	private List<PensionStaffArrange> addArranges;
	// 选中排班记录
	private PensionStaffArrange selectAddArrange;
	// 排班记录ID
	private Long arrangeId;
	private Long addDeptId;
	private String addDeptName;
	private Date addArrangeTime;
	private Long addEmpId;
	private String addEmpName;
	private Long addEmpDeptId;
	private String addEmpDeptName;
	private Long addArrangeId;
	private String addArrangeName;
	private String addStartTime;
	private String addEndTime;
	private String addNote;

	private String addEmpSql;
	private String addArrageSql;

	// 修改
	private PensionStaffArrange modifyArrange;
	private String modifyStartTime;
	private String modifyEndTime;

	// 复制
	private List<PensionStaffArrange> copyArranges;
	private Date originStartDate;
	private Date originEndDate;
	private Date copyStartDate;
	private Date copyEndDate;

	// 当前信息
	private PensionEmployee employee;// 当前用户

	/**
	 * add by wensy.yang
	 */
	private PensionArrangeTemplate template = new PensionArrangeTemplate();
	private List<PensionArrangeTemplateDetail> details = new ArrayList<PensionArrangeTemplateDetail>();
	private Long templateId;
	private String templateName;
	private Date creatStartDate;
	private Date creatEndDate;

	@PostConstruct
	public void init() {
		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		this.initSql();
		this.search();
		arrangeId = 1L;
	}

	public void initSql() {

		selectDeptSql = "select dept.id,dept.name,dept.inputCode from pension_dept dept where dept.cleared = 2";
		selectEmpSql = empSql;
		addEmpSql = empSql;
		addArrageSql = "select arr.id,arr.arrange_name,arr.input_code, "
				+ " DATE_FORMAT(arr.start_time,'%H:%m:%s'), "
				+ " DATE_FORMAT(arr.end_time,'%H:%m:%s'), "
				+ " IF(arr.across_flag > 1,'否','是') "
				+ " from pension_dic_arrange arr " + " where arr.cleared = 2";

	}

	public void onChangeSelectDeptId() {
		if (deptId == null || deptName == null || "".equals(deptName)) {
			selectEmpSql = empSql;
		} else {
			selectEmpSql = empSql + " and emp.dept_id = " + deptId;
		}
		empId = null;
		empName = null;
	}

	public void onChangeAddDeptId() {
		if (addDeptId != null && !"".equals(addDeptId)
				&& !"".equals(addDeptName)) {
			addEmpSql = empSql + " and emp.dept_id = " + addDeptId;
		} else {
			addEmpSql = empSql;
		}
	}

	public void clearSearchForm() {
		startDate = null;
		endDate = null;
		deptId = null;
		deptName = null;
		empId = null;
		empName = null;
	}

	public void search() {
		arranges = staffScheduleService.searchStaffArranges(startDate, endDate,
				deptId, empId);
		selectArranges = null;
	}

	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSingleSelectData() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectArranges == null || selectArranges.length < 1) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请先选择数据！", "请先选择数据！"));
			request.addCallbackParam("validate", false);
		} else if (selectArranges.length > 1) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选择一条数据进行操作！", "请选择一条数据进行操作！"));
			request.addCallbackParam("validate", false);
		} else if (selectArranges.length > 1
				&& selectArranges[0].getArrangeTime().before(new Date())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("该排班计划已执行，不能操作！", "该排班计划已执行，不能操作！"));
			request.addCallbackParam("validate", false);
		} else {
			modifyArrange = selectArranges[0];
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			modifyStartTime = sdf.format(selectArranges[0].getStartTime());
			modifyEndTime = sdf.format(selectArranges[0].getEndTime());
			request.addCallbackParam("validate", true);
		}
	}

	/**
	 * check 选中的要删除的记录
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSingleDeleteData() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectArranges == null || selectArranges.length < 1) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请先选择数据！", "请先选择数据！"));
			request.addCallbackParam("validate", false);
		} else {
			boolean flag = true;
			for (PensionStaffArrange selectArrange : selectArranges) {
				if (selectArrange.getArrangeTime().before(new Date())) {
					selectArranges = null; // 取消所有选中的记录
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage("不能删除当前日期之前的排班记录，请重新选择！",
									"不能删除当前日期之前的排班记录，请重新选择！"));
					flag = false;
					break;
				}
			}
			request.addCallbackParam("validate", flag);
		}
	}

	public void delete() {
		try {
			staffScheduleService.deleteStaffArrange(selectArranges);
			for (PensionStaffArrange staffArrange : selectArranges) {
				arranges.remove(staffArrange);
			}
			selectArranges = null;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("删除成功！", "删除成功！"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("删除失败！", "删除失败！"));
		}
	}

	public void add() {
		addArranges = new ArrayList<PensionStaffArrange>();
		addDeptId = null;
		addDeptName = null;
		addArrangeTime = null;
		addEmpSql = empSql;
		this.initAddArrange();
	}

	// 初始化新增排版表
	public void initAddArrange() {
		addEmpId = null;
		addEmpName = null;
		addArrangeId = null;
		addArrangeName = null;
		addNote = null;
		addStartTime = null;
		addEndTime = null;
		arrangeId += 1;
	}

	// 添加一条排版表
	public void addArrange() {
		PensionStaffArrange addArrange = new PensionStaffArrange();
		addArrange.setId(arrangeId);
		addArrange.setArrangeId(addArrangeId);
		addArrange.setArrangeName(addArrangeName);
		addArrange.setArrangeTime(addArrangeTime);
		// 如果没有指定员工部门，则自动填充该员工所属部门（由输入法获取）
		if (addDeptId != null && !"".equals(addDeptId)) {
			addArrange.setDepartId(addDeptId);
			addArrange.setDepartName(addDeptName);
		} else {
			addArrange.setDepartId(addEmpDeptId);
			addArrange.setDepartName(addEmpDeptName);
		}
		addArrange.setEmployeeId(addEmpId);
		addArrange.setEmployeeName(addEmpName);
		// 转化时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			addArrange.setEndTime(sdf.parse(addEndTime));
			addArrange.setStartTime(sdf.parse(addStartTime));
		} catch (ParseException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("转换时间格式出错！", "转换时间格式出错！"));
		}
		addArrange.setNote(addNote);
		addArrange.setRecorderId(employee.getId());
		addArrange.setRecorderName(employee.getName());
		addArrange.setRecordTime(new Date());
		addArranges.add(addArrange);
		this.initAddArrange();
	}

	// 删除一条排班记录
	public void deleteAddArrange() {
		if (selectAddArrange == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请先选中一条数据！", ""));
		} else {
			addArranges.remove(selectAddArrange);
		}
	}

	public void saveArranges() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (addArranges.size() < 1) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("新增值班记录的条数要大于0！", "新增值班记录的条数要大于0！"));
				request.addCallbackParam("validate", false);
				return;
			}
			addArranges = staffScheduleService.saveAddArranges(addArranges);
			arranges.addAll(addArranges);
			this.search();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("新增成功！", "新增成功！"));
			request.addCallbackParam("validate", true);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("新增失败！", "新增失败！"));
			request.addCallbackParam("validate", false);
		}
	}

	public void modifyArrange() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			modifyArrange.setEndTime(sdf.parse(modifyEndTime));
			modifyArrange.setStartTime(sdf.parse(modifyStartTime));
			staffScheduleService.updateArrange(modifyArrange);
			request.addCallbackParam("validate", true);
		} catch (ParseException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("转换时间格式出错！", "转换时间格式出错！"));
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkMultipleSelectDate() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectArranges == null || selectArranges.length < 1) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选中排班记录！", "请选中排班记录！"));
			request.addCallbackParam("validate", false);
		} else {
			selectArranges = staffScheduleService.sortArray(selectArranges);
			copyStartDate = null;
			copyEndDate = null;
			originStartDate = selectArranges[0].getArrangeTime();
			originEndDate = selectArranges[selectArranges.length - 1]
					.getArrangeTime();
			request.addCallbackParam("validate", true);
		}
	}

	public void checkDeptList() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectArranges == null || selectArranges.length < 1) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选中排班记录！", "请选中排班记录！"));
			request.addCallbackParam("validate", false);
		} else {
			int deptNum = 1;
			Long deptId = 0L;
			for (int j = 0; j < selectArranges.length; j++) {
				if (j == 0) {
					deptId = selectArranges[j].getDepartId();
				} else {
					if (selectArranges[j].getDepartId() != deptId) {
						deptNum++;
						break;
					} else {
						continue;
					}
				}
			}
			if (deptNum == 1) {
				selectArranges = staffScheduleService.sortArray(selectArranges);
				request.addCallbackParam("validate", true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请选择同一部门的排班记录！", "请选择同一部门的排班记录！"));
			}
		}
	}

	public void culCopyEndDate() {
		if (copyStartDate != null) {
			GregorianCalendar startDateGc = new GregorianCalendar();// 新建一GregorianCalendar类
			startDateGc.setTime(originStartDate);
			GregorianCalendar endDateGc = new GregorianCalendar();// 新建一GregorianCalendar类
			endDateGc.setTime(originEndDate);
			GregorianCalendar copyStartDateGc = new GregorianCalendar();// 新建一GregorianCalendar类
			copyStartDateGc.setTime(copyStartDate);
			GregorianCalendar copyEndDateGc = new GregorianCalendar();// 新建一GregorianCalendar类
			copyEndDateGc.setTime(copyStartDate);
			if (startDateGc.equals(endDateGc)) {
				copyEndDate = copyStartDate;
			} else {
				while (startDateGc.before(endDateGc)) {
					startDateGc.add(Calendar.DAY_OF_MONTH, 1);
					copyEndDateGc.add(Calendar.DAY_OF_MONTH, 1);
				}
				copyEndDate = copyEndDateGc.getTime();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请填写复制排班起始日期！", "请填写复制排班起始日期！"));
		}
	}

	public void saveCopyArranges() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (copyStartDate == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请填写复制排班起始日期！", "请填写复制排班起始日期！"));
				request.addCallbackParam("validate", false);
			} else if (copyStartDate != null && copyEndDate == null) {
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage("请点击按钮生成复制排班结束日期！",
										"请点击按钮生成复制排班结束日期！"));
				request.addCallbackParam("validate", false);
			} else {
				copyArranges = staffScheduleService.saveCopyArranges(
						selectArranges, copyStartDate, copyEndDate,
						originStartDate, originEndDate);
				this.search();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("复制人员排版表成功！", "复制人员排版表成功！"));
				request.addCallbackParam("validate", true);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("复制人员排版表失败！", "复制人员排版表失败！"));
			request.addCallbackParam("validate", false);
		}
	}

	public void saveSynchronizeArranges() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			template.setDeptId(selectArranges[0].getDepartId());
			template.setDeptName(selectArranges[0].getDepartName());
			template.setCleared(2);
			for (int i = 0; i < selectArranges.length; i++) {
				PensionArrangeTemplateDetail tempDetail = new PensionArrangeTemplateDetail();
				tempDetail.setCleared(2);
				tempDetail.setEndTime(selectArranges[i].getEndTime());
				tempDetail.setStartTime(selectArranges[i].getStartTime());
				tempDetail.setTemplateName(template.getTemplateName());
				tempDetail.setEmployeeId(selectArranges[i].getEmployeeId());
				tempDetail.setEmployeeName(selectArranges[i].getEmployeeName());
				tempDetail.setDepartId(selectArranges[i].getDepartId());
				tempDetail.setDepartName(selectArranges[i].getDepartName());
				tempDetail.setArrangeId(selectArranges[i].getArrangeId());
				tempDetail.setArrangeName(selectArranges[i].getArrangeName());
				tempDetail.setNote(selectArranges[i].getNote());
				details.add(tempDetail);
			}
			staffScheduleService.insertArrangeTemplate(template, details);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("创建人员排班模板成功！", "创建人员排班模板成功！"));
			request.addCallbackParam("validate", true);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("创建人员排班模板失败！", "创建人员排班模板失败！"));
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * 初始化创建排班表
	 */
	public void initCreat() {
		templateName = "";
		templateId = null;
		creatStartDate = null;
		creatEndDate = null;
		arrangeTemplateDetails = new ArrayList<PensionArrangeTemplateDetail>();
		selectTemplateDetail = new ArrayList<PensionArrangeTemplateDetail>();
	}

	/**
	 * 保存创建排班表
	 */
	public void saveCreatArranges() {
		RequestContext request = RequestContext.getCurrentInstance();
		List<PensionStaffArrange> arrangeList = new ArrayList<PensionStaffArrange>();
		Date startDate = creatStartDate;
		for (; !startDate.after(creatEndDate);) {
			for (PensionArrangeTemplateDetail temp : selectTemplateDetail) {
				PensionStaffArrange record = new PensionStaffArrange();
				record.setArrangeId(temp.getArrangeId());
				record.setArrangeName(temp.getArrangeName());
				record.setArrangeTime(startDate);
				record.setCleared(2);
				record.setDepartId(temp.getDepartId());
				record.setDepartName(temp.getDepartName());
				record.setEmployeeId(temp.getEmployeeId());
				record.setEmployeeName(temp.getEmployeeName());
				record.setEndTime(temp.getEndTime());
				record.setStartTime(temp.getStartTime());
				record.setRecorderId(employee.getId());
				record.setRecorderName(employee.getName());
				record.setRecordTime(new Date());
				record.setNote(temp.getNote());
				arrangeList.add(record);
			}
			startDate = DateUtil.getBeforeDay(startDate, -1);
		}
		staffScheduleService.saveAddArranges(arrangeList);
		search();
		request.addCallbackParam("validate", true);
	}

	/**
	 * 根据模板名称查询员工排班列表
	 */
	public void selectScheduleList() {
		if (templateName == null || "".equals(templateName)) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选择排班模板！",
							""));
			return;
		}
		arrangeTemplateDetails = staffScheduleService
				.searchArrangeTemplates(templateId);
		selectTemplateDetail = arrangeTemplateDetails;
	}

	/**
	 * 选中一行时触发
	 * 
	 */
	public void setEnableFlag(SelectEvent e) {

	}

	/**
	 * 未选中一行时触发
	 * 
	 */
	public void setUnableFlag(UnselectEvent e) {

	}

	public StaffScheduleService getStaffScheduleService() {
		return staffScheduleService;
	}

	public void setStaffScheduleService(
			StaffScheduleService staffScheduleService) {
		this.staffScheduleService = staffScheduleService;
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

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
	}

	public List<PensionStaffArrange> getArranges() {
		return arranges;
	}

	public void setArranges(List<PensionStaffArrange> arranges) {
		this.arranges = arranges;
	}

	public List<PensionStaffArrange> getAddArranges() {
		return addArranges;
	}

	public void setAddArranges(List<PensionStaffArrange> addArranges) {
		this.addArranges = addArranges;
	}

	public Long getAddDeptId() {
		return addDeptId;
	}

	public void setAddDeptId(Long addDeptId) {
		this.addDeptId = addDeptId;
	}

	public String getAddDeptName() {
		return addDeptName;
	}

	public void setAddDeptName(String addDeptName) {
		this.addDeptName = addDeptName;
	}

	public Date getAddArrangeTime() {
		return addArrangeTime;
	}

	public void setAddArrangeTime(Date addArrangeTime) {
		this.addArrangeTime = addArrangeTime;
	}

	public Long getAddEmpId() {
		return addEmpId;
	}

	public void setAddEmpId(Long addEmpId) {
		this.addEmpId = addEmpId;
	}

	public String getAddEmpName() {
		return addEmpName;
	}

	public void setAddEmpName(String addEmpName) {
		this.addEmpName = addEmpName;
	}

	public Long getAddArrangeId() {
		return addArrangeId;
	}

	public void setAddArrangeId(Long addArrangeId) {
		this.addArrangeId = addArrangeId;
	}

	public String getAddArrangeName() {
		return addArrangeName;
	}

	public void setAddArrangeName(String addArrangeName) {
		this.addArrangeName = addArrangeName;
	}

	public String getAddEmpSql() {
		return addEmpSql;
	}

	public void setAddEmpSql(String addEmpSql) {
		this.addEmpSql = addEmpSql;
	}

	public String getAddArrageSql() {
		return addArrageSql;
	}

	public void setAddArrageSql(String addArrageSql) {
		this.addArrageSql = addArrageSql;
	}

	public String getAddNote() {
		return addNote;
	}

	public void setAddNote(String addNote) {
		this.addNote = addNote;
	}

	public Long getAddEmpDeptId() {
		return addEmpDeptId;
	}

	public void setAddEmpDeptId(Long addEmpDeptId) {
		this.addEmpDeptId = addEmpDeptId;
	}

	public String getAddStartTime() {
		return addStartTime;
	}

	public void setAddStartTime(String addStartTime) {
		this.addStartTime = addStartTime;
	}

	public String getAddEndTime() {
		return addEndTime;
	}

	public void setAddEndTime(String addEndTime) {
		this.addEndTime = addEndTime;
	}

	public PensionStaffArrange getModifyArrange() {
		return modifyArrange;
	}

	public void setModifyArrange(PensionStaffArrange modifyArrange) {
		this.modifyArrange = modifyArrange;
	}

	public String getModifyStartTime() {
		return modifyStartTime;
	}

	public void setModifyStartTime(String modifyStartTime) {
		this.modifyStartTime = modifyStartTime;
	}

	public String getModifyEndTime() {
		return modifyEndTime;
	}

	public void setModifyEndTime(String modifyEndTime) {
		this.modifyEndTime = modifyEndTime;
	}

	public List<PensionStaffArrange> getCopyArranges() {
		return copyArranges;
	}

	public void setCopyArranges(List<PensionStaffArrange> copyArranges) {
		this.copyArranges = copyArranges;
	}

	public Date getCopyStartDate() {
		return copyStartDate;
	}

	public void setCopyStartDate(Date copyStartDate) {
		this.copyStartDate = copyStartDate;
	}

	public Date getCopyEndDate() {
		return copyEndDate;
	}

	public void setCopyEndDate(Date copyEndDate) {
		this.copyEndDate = copyEndDate;
	}

	public PensionStaffArrange[] getSelectArranges() {
		return selectArranges;
	}

	public void setSelectArranges(PensionStaffArrange[] selectArranges) {
		this.selectArranges = selectArranges;
	}

	public Date getOriginStartDate() {
		return originStartDate;
	}

	public void setOriginStartDate(Date originStartDate) {
		this.originStartDate = originStartDate;
	}

	public Date getOriginEndDate() {
		return originEndDate;
	}

	public String getAddEmpDeptName() {
		return addEmpDeptName;
	}

	public void setAddEmpDeptName(String addEmpDeptName) {
		this.addEmpDeptName = addEmpDeptName;
	}

	public String getEmpSql() {
		return empSql;
	}

	public void setOriginEndDate(Date originEndDate) {
		this.originEndDate = originEndDate;
	}

	public PensionStaffArrange getSelectAddArrange() {
		return selectAddArrange;
	}

	public void setSelectAddArrange(PensionStaffArrange selectAddArrange) {
		this.selectAddArrange = selectAddArrange;
	}

	public Long getArrangeId() {
		return arrangeId;
	}

	public void setArrangeId(Long arrangeId) {
		this.arrangeId = arrangeId;
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

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Date getCreatStartDate() {
		return creatStartDate;
	}

	public void setCreatStartDate(Date creatStartDate) {
		this.creatStartDate = creatStartDate;
	}

	public Date getCreatEndDate() {
		return creatEndDate;
	}

	public void setCreatEndDate(Date creatEndDate) {
		this.creatEndDate = creatEndDate;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public List<PensionArrangeTemplateDetail> getSelectTemplateDetail() {
		return selectTemplateDetail;
	}

	public void setSelectTemplateDetail(
			List<PensionArrangeTemplateDetail> selectTemplateDetail) {
		this.selectTemplateDetail = selectTemplateDetail;
	}

	public List<PensionArrangeTemplateDetail> getArrangeTemplateDetails() {
		return arrangeTemplateDetails;
	}

	public void setArrangeTemplateDetails(
			List<PensionArrangeTemplateDetail> arrangeTemplateDetails) {
		this.arrangeTemplateDetails = arrangeTemplateDetails;
	}
}
