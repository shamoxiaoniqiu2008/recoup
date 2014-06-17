package controller.reportManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

import service.reportManage.TotalNurseWeeklyService;
import util.PmsException;
import util.weekUtil;

import com.centling.his.util.SessionManager;

import domain.configureManage.PensionBuilding;
import domain.dictionary.PensionDicAccidenttype;
import domain.dictionary.PensionDicOldertype;
import domain.dictionary.PensionDicReporttype;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionNormalpayment;
import domain.reportManage.PensionWeeklyReport;
import domain.system.PensionDept;
import domain.system.PensionRole;
import domain.system.PensionSysUser;


/**
 * 总体护理情况周报  
 * author:mary liu
 */

public class TotalNurseWeeklyController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient TotalNurseWeeklyService totalNurseWeeklyService;
	
	//查询
	private List<Integer> yearList;//年份列表
	private Integer selectYear;//选中的年份
	private List<Integer> weekList;//星期列表
	private Integer selectWeek;//选中的星期
	private Date startDate;//选中的星期的开始日期
	private Date endDate;//选中的星期的结束日期
	private Date today;
	
	private boolean flag;//当选中年份周次的报表存在true；不存在 false
	private List<Long> roles;
	private boolean roleFlag;//当前用户有权生成报表时 true；否则false
	
	private Long reportId;
	
	//报表
	private Integer startOlderNum;//周初 老人数
	private Integer endOlderNum;//周末 老人数
	
	private Integer newInNum;
	private Integer newOutNum;
	
	private Integer nurseNum;//护理员 总数
	private Integer fixedNurseNum;//护理员 定岗
	
	private List<PensionDicOldertype> olderTypes;//老人类型
	private List<PensionDicAccidenttype> accidentTypes;//意外伤害类型
	private List<PensionBuilding> buildings;//大厦类型
	
	private PensionWeeklyReport weeklyReport;
	
	//页脚
	private List<PensionDicReporttype> reportTypes;
	private Long selectReporttypeId;
	

	//当前信息
	private PensionEmployee employee;//当前用户
	private PensionSysUser sysUser;
	
	private Integer maxNumAto;
	
	

	private static final Integer YES_FLAG = 1;//是
	private static final Integer NO_FLAG = 2;//否
	
	private static final String DEFAULT_CONTENT="无";//周报默认内容

	
	
	
	
	@PostConstruct
	public void init(){
		employee=(PensionEmployee) SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
		sysUser=(PensionSysUser) SessionManager.getSessionAttribute(SessionManager.USER);
		this.initReportTypes();//初始化报表类型
		this.initRoles();//从系统参数中读取有权限创建/修改周报的角色
		if(!this.initMessage()){//当不是由消息进入时，初始化查询Form
			this.initSearchForm();
		}
		
	}
	
	/**
	 * 从系统参数中读取有权限创建/修改周报的角色
	 */
	private void initRoles() {
		try {
			roles=totalNurseWeeklyService.selectRoles("NURSE_WEEKLY_REPORT_ROLE_ID");
			if(roles.contains(sysUser.getRoleId())){
				roleFlag=true;
			}else{
				roleFlag=false;
			}
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
			roles=new ArrayList<Long>();
			e.printStackTrace();
		}
	}

	/**
	 * 由消息进入时，根据周报编号显示周报内容
	 * 当存在该周报时，true；否则，false
	 * @return
	 */
	public boolean  initMessage(){
		try{
			Map<String, String> paramsMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
			String reportIdStr = paramsMap.get("reportId");
			if (reportIdStr != null) {
				reportId = Long.valueOf(reportIdStr);
				this.view();//按主键查看该条报表
				//设置 年份 周次 和起止日期
				selectYear=weeklyReport.getYearNumber();
				selectWeek=weeklyReport.getWeekNumber();
				startDate=weekUtil.getFirstDayOfWeek(selectYear,selectWeek);
				endDate=weekUtil.getLastDayOfWeek(selectYear,selectWeek);
				flag=true;//设置为查看
				//设置年份列表
				yearList=new ArrayList<Integer>();
				Integer startYear=totalNurseWeeklyService.selectstartYear("START_YEAR");
				//将起始年到今年的所有年份加入到年份列表中
				for(int i=startYear;i<=selectYear;i++){
					yearList.add(i);
				}
				//设置周次列表
				weekList=new ArrayList<Integer>();
				Integer maxWeekNum=weekUtil.getMaxWeekNumOfYear(selectYear);
				for(int i=1;i<=maxWeekNum;i++){
					weekList.add(i);
				}
				reportId = null;
				return true;
			} else {
				reportId = null;
				return false;
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
			reportId = null;
			return false;
		}
		
	}
	
	/**
	 * 初始化搜索条件
	 */
	public void initSearchForm(){
		try {
			yearList=new ArrayList<Integer>();
			//从系统参数中读取起始年份
			Integer startYear=totalNurseWeeklyService.selectstartYear("START_YEAR");
			today=new Date();
			Calendar calendar=new GregorianCalendar();
			calendar.setTime(today);
			selectYear=calendar.get(Calendar.YEAR);//默认选中当前的年份
			//将起始年到今年的所有年份加入到年份列表中
			for(int i=startYear;i<=selectYear;i++){
				yearList.add(i);
			}
			this.initWeekList();
		} catch (PmsException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 以当前选中的年份初始化星期列表
	 */
	public void initWeekList(){
		weekList=new ArrayList<Integer>();
		Integer maxWeekNum=weekUtil.getMaxWeekNumOfYear(selectYear);
		for(int i=1;i<=maxWeekNum;i++){
			weekList.add(i);
		}
		selectWeek=weekUtil.getWeekOfYear(today);
		this.initSearchDate();
	}
	
	/**
	 * 以当前选中的年份和星期数初始化起至日期
	 */
	public void initSearchDate(){
		startDate=weekUtil.getFirstDayOfWeek(selectYear,selectWeek);
		endDate=weekUtil.getLastDayOfWeek(selectYear,selectWeek);
		//true 表示该周的周报已存在，false 为不存在
		flag=totalNurseWeeklyService.checkExisted(selectYear,selectWeek);
		if(!flag){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("当前指定的年份周次还没有生成报表！", "当前指定的年份周次还没有生成报表！"));
		}
		this.initWeeklyReport();
	}
	
	/**
	 * 初始化周报类型
	 */
	public void initReportTypes(){
		try {
			reportTypes=totalNurseWeeklyService.selectReportTypes();
			selectReporttypeId=totalNurseWeeklyService.selectRepertType("REPORT_TYPE_ID");
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
			if(reportTypes.size()>0){
				selectReporttypeId=reportTypes.get(0).getId();
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("您还没有设置报表类型！", "您还没有设置报表类型！"));
			}
		}
	}
	
	
	public void initWeeklyReport(){
		startOlderNum=null;
		endOlderNum=null;
		newInNum=null;
		newOutNum=null;
		nurseNum=null;
		fixedNurseNum=null;
		weeklyReport=new PensionWeeklyReport();
		weeklyReport.setReporttypeId(selectReporttypeId);
		weeklyReport.setYearNumber(selectYear);
		weeklyReport.setWeekNumber(selectWeek);
		olderTypes=totalNurseWeeklyService.selectDicOlderTypes();
		accidentTypes=totalNurseWeeklyService.selectDicAccidenttypes();
		buildings=totalNurseWeeklyService.selectBuildings();
		for(PensionBuilding building:buildings){
			building.setOldernumber(0);
		}
		
		maxNumAto = olderTypes.size() > accidentTypes.size()?olderTypes.size():accidentTypes.size();
		maxNumAto = maxNumAto>buildings.size()?maxNumAto:buildings.size();
		
	}
	
	
	/**
	 * 点击【生成】，初始化表单信息
	 */
	@SuppressWarnings("unchecked")
	public void create(){
		try {
			weeklyReport=new PensionWeeklyReport();
			Map<String,Object> totalNurseWeeklyMap=totalNurseWeeklyService.createWeeklyReport(startDate,endDate);
			startOlderNum=(Integer) totalNurseWeeklyMap.get("StartOlderNum");
			endOlderNum=(Integer) totalNurseWeeklyMap.get("EndOlderNum");
			newInNum=(Integer) totalNurseWeeklyMap.get("NewInNum");
			newOutNum=(Integer) totalNurseWeeklyMap.get("NewOutNum");
			nurseNum=(Integer) totalNurseWeeklyMap.get("NurseNum");
			fixedNurseNum=(Integer) totalNurseWeeklyMap.get("FixedNurseNum");
			olderTypes=(List<PensionDicOldertype>) totalNurseWeeklyMap.get("OlderTypes");
			accidentTypes=(List<PensionDicAccidenttype>) totalNurseWeeklyMap.get("AccidentTypes");
			buildings=(List<PensionBuilding>) totalNurseWeeklyMap.get("Buildings");
			weeklyReport.setDepartId(employee.getDeptId());
			weeklyReport.setDepartName(totalNurseWeeklyService.selectDeptName(employee.getDeptId()));
			weeklyReport.setEmployeeCondition(DEFAULT_CONTENT);
			weeklyReport.setEmployeeId(employee.getId());
			weeklyReport.setEmployeeName(employee.getName());
			weeklyReport.setAssistsolveProblem(DEFAULT_CONTENT);
			weeklyReport.setEndTime(endDate);
			weeklyReport.setEvents(DEFAULT_CONTENT);
			weeklyReport.setMeals(DEFAULT_CONTENT);
			weeklyReport.setNextweekPlan(DEFAULT_CONTENT);
			weeklyReport.setNote(DEFAULT_CONTENT);
			weeklyReport.setOlderCondition(DEFAULT_CONTENT);
			weeklyReport.setProblemAndsolve(DEFAULT_CONTENT);
			weeklyReport.setReporttypeId(selectReporttypeId);
			weeklyReport.setReporttypeName(totalNurseWeeklyService.selectReportTypeName(selectReporttypeId));
			weeklyReport.setStartTime(startDate);
			weeklyReport.setSuggestion(DEFAULT_CONTENT);
			weeklyReport.setThisweekSum(DEFAULT_CONTENT);
			weeklyReport.setWeekNumber(selectWeek);
			weeklyReport.setYearNumber(selectYear);
			weeklyReport.setCreatedate(new Date());
			weeklyReport.setSendFlag(NO_FLAG);
		} catch (PmsException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 点击【查看】，查询选中年份和周数的周报
	 */
	@SuppressWarnings("unchecked")
	public void view(){
		try{
			Map<String,Object> weeklyReportMap=null;
			if(reportId!=null){
				weeklyReportMap=totalNurseWeeklyService.selectWeeklyReport(null,null,reportId);
			}else{
				weeklyReportMap=totalNurseWeeklyService.selectWeeklyReport(selectYear,selectWeek,null);
			}
			weeklyReport=(PensionWeeklyReport) weeklyReportMap.get("WEEKLYREPORT");
			olderTypes=(List<PensionDicOldertype>) weeklyReportMap.get("OlderTypes");
			accidentTypes=(List<PensionDicAccidenttype>) weeklyReportMap.get("AccidentTypes");
			buildings=(List<PensionBuilding>) weeklyReportMap.get("Buildings");
			startOlderNum=weeklyReport.getStartoldernum();
			endOlderNum=weeklyReport.getEndoldernum();
			newInNum=weeklyReport.getNewInNum();
			newOutNum=weeklyReport.getNewOutNum();
			nurseNum=weeklyReport.getNursenum();
			fixedNurseNum=weeklyReport.getFixednursenum();
			
			maxNumAto = olderTypes.size() > accidentTypes.size()?olderTypes.size():accidentTypes.size();
			maxNumAto = maxNumAto>buildings.size()?maxNumAto:buildings.size();
			
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage(), e.getMessage()));
			e.printStackTrace();
		}
	}

	/**
	 * 点击【提交】
	 */
	public void check(){
		RequestContext request = RequestContext.getCurrentInstance();
		//check报表的结束日期是否在当前日期之后，若是，给出提示
		if(weeklyReport.getCreatedate().before(weeklyReport.getEndTime())  ){
			request.addCallbackParam("validate", true);
		}else{//否则，直接保存+发送
			this.save();
			this.send();
		}
	}
	
	/**
	 * 保存周报信息
	 */
	public void save(){
		try{
			if(weeklyReport.getEmployeeId()==null){//还没有创建，不能保存
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请先创建报表再保存！", "请先创建报表再保存！"));
			}else if(YES_FLAG.equals(weeklyReport.getSendFlag())){//已发送，不能保存
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("该报表已发送，不能再修改保存！", "该报表已发送，不能再修改保存！"));
			}else{
				if(weeklyReport.getId()!=null){//更新
					totalNurseWeeklyService.updateWeeklyReport(weeklyReport);
					FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("更新成功！", "更新成功！"));
				}else{//新增
					weeklyReport.setStartoldernum(startOlderNum);
					weeklyReport.setEndoldernum(endOlderNum);
					weeklyReport.setNewInNum(newInNum);
					weeklyReport.setNewOutNum(newOutNum);
					weeklyReport.setNursenum(nurseNum);
					weeklyReport.setFixednursenum(fixedNurseNum);
					weeklyReport.setReporttypeName(totalNurseWeeklyService.selectReportTypeName(weeklyReport.getReporttypeId()));
					Long id=totalNurseWeeklyService.saveWeeklyReport(weeklyReport,olderTypes,accidentTypes,buildings);
					weeklyReport.setId(id);
					flag=true;
				}
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("保存失败！", "保存失败！"));
			e.printStackTrace();
		}
		
	}
	
	public void export(){
		
	}
	
	/**
	 * 发消息
	 */
	public void send(){
		try{
			if(weeklyReport.getId()!=null){
				totalNurseWeeklyService.sendMessages(weeklyReport.getId(), employee.getName());
				weeklyReport.setSendFlag(YES_FLAG);
			}else{
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("当前周报不能发送！", "当前周报不能发送！"));
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("发送失败！", "发送失败！"));
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public TotalNurseWeeklyService getTotalNurseWeeklyService() {
		return totalNurseWeeklyService;
	}

	public void setTotalNurseWeeklyService(
			TotalNurseWeeklyService totalNurseWeeklyService) {
		this.totalNurseWeeklyService = totalNurseWeeklyService;
	}

	public List<Integer> getYearList() {
		return yearList;
	}

	public void setYearList(List<Integer> yearList) {
		this.yearList = yearList;
	}

	public Integer getSelectYear() {
		return selectYear;
	}

	public void setSelectYear(Integer selectYear) {
		this.selectYear = selectYear;
	}

	public List<Integer> getWeekList() {
		return weekList;
	}

	public void setWeekList(List<Integer> weekList) {
		this.weekList = weekList;
	}

	public Integer getSelectWeek() {
		return selectWeek;
	}

	public void setSelectWeek(Integer selectWeek) {
		this.selectWeek = selectWeek;
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

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Integer getStartOlderNum() {
		return startOlderNum;
	}

	public void setStartOlderNum(Integer startOlderNum) {
		this.startOlderNum = startOlderNum;
	}

	public Integer getEndOlderNum() {
		return endOlderNum;
	}

	public void setEndOlderNum(Integer endOlderNum) {
		this.endOlderNum = endOlderNum;
	}

	public Integer getNurseNum() {
		return nurseNum;
	}

	public void setNurseNum(Integer nurseNum) {
		this.nurseNum = nurseNum;
	}

	public Integer getFixedNurseNum() {
		return fixedNurseNum;
	}

	public void setFixedNurseNum(Integer fixedNurseNum) {
		this.fixedNurseNum = fixedNurseNum;
	}

	public List<PensionDicOldertype> getOlderTypes() {
		return olderTypes;
	}

	public void setOlderTypes(List<PensionDicOldertype> olderTypes) {
		this.olderTypes = olderTypes;
	}

	public List<PensionDicAccidenttype> getAccidentTypes() {
		return accidentTypes;
	}

	public void setAccidentTypes(List<PensionDicAccidenttype> accidentTypes) {
		this.accidentTypes = accidentTypes;
	}

	public List<PensionBuilding> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<PensionBuilding> buildings) {
		this.buildings = buildings;
	}

	public PensionWeeklyReport getWeeklyReport() {
		return weeklyReport;
	}

	public void setWeeklyReport(PensionWeeklyReport weeklyReport) {
		this.weeklyReport = weeklyReport;
	}

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
	}

	public List<Long> getRoles() {
		return roles;
	}

	public void setRoles(List<Long> roles) {
		this.roles = roles;
	}

	public boolean isRoleFlag() {
		return roleFlag;
	}

	public void setRoleFlag(boolean roleFlag) {
		this.roleFlag = roleFlag;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public List<PensionDicReporttype> getReportTypes() {
		return reportTypes;
	}

	public void setReportTypes(List<PensionDicReporttype> reportTypes) {
		this.reportTypes = reportTypes;
	}

	public Long getSelectReporttypeId() {
		return selectReporttypeId;
	}

	public void setSelectReporttypeId(Long selectReporttypeId) {
		this.selectReporttypeId = selectReporttypeId;
	}

	public PensionSysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(PensionSysUser sysUser) {
		this.sysUser = sysUser;
	}

	public Integer getNewInNum() {
		return newInNum;
	}

	public void setNewInNum(Integer newInNum) {
		this.newInNum = newInNum;
	}

	public Integer getNewOutNum() {
		return newOutNum;
	}

	public void setNewOutNum(Integer newOutNum) {
		this.newOutNum = newOutNum;
	}

	public Integer getMaxNumAto() {
		return maxNumAto;
	}

	public void setMaxNumAto(Integer maxNumAto) {
		this.maxNumAto = maxNumAto;
	}
	
	
	

}
