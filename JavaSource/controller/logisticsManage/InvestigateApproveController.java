package controller.logisticsManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.logisticsManage.InvestigateApproveService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionCheckApproveExtend;
import domain.logisticsManage.PensionCheckExtend;

public class InvestigateApproveController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6989987221913280040L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionCheckExtend> records = new ArrayList<PensionCheckExtend>();
	/**
	 * 被选中的维修申请记录
	 */
	private PensionCheckExtend selectedRow = new PensionCheckExtend();
	/**
	 * 被选中行对应的审批结果
	 */
	private List<PensionCheckApproveExtend> approves = new ArrayList<PensionCheckApproveExtend>();
	/**
	 * 拒绝原因
	 */
	private String refuseReason;
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 确认标记 用作查询条件 1为已确认 2为未确认
	 */
	private Integer ensureFlag;
	/**
	 * 排查维修申请表的主键，用在接收消息时做查询条件
	 */
	private Long checkId;
	/**
	 * 确认按钮是否可用
	 */
	private boolean disEnsureButton = true;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee)SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
	/**
	 * 注入业务对象.
	 */
	private transient InvestigateApproveService investigateApproveService;

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
		this.initDate();
		initSql();
		//获取消息源发给本页面的参数
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
		.getExternalContext().getRequestParameterMap();
		String checkId = paramsMap.get("checkId");
		if(checkId != null) {
			this.checkId = Long.valueOf(checkId);
			records = investigateApproveService.selectInvestigatationRecords(startDate,endDate,ensureFlag,curEmployee,this.checkId);
			selectedRow = records.get(0);
			selectApprovalResults();
			disEnsureButton = false;
			//之后 将其至空
			this.checkId = null;
		} else {
			this.checkId = null;
			//根据参数 和其余默认的查询条件查询出所有的请假申请
			selectInvestigatationRecords();
		}
		
	}
	
	/**
	 * 将结束日期设置为今天，起始日期设置为一周前的今天
	 */
	public void initDate(){
		
		Calendar calendar=Calendar.getInstance();
		endDate=new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		endDate=calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-7);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate=calendar.getTime();
  
	}
	
	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		
	}
	
	/**
	 * 查询排查记录
	 */
	public void selectInvestigatationRecords(){
		disEnsureButton = true;
		selectedRow = null;
		approves.clear();
		setRecords(investigateApproveService.selectInvestigatationRecords(startDate,endDate,ensureFlag,curEmployee,checkId));
		
	}
	
	/**
	 * 查看被选中行的审批结果
	 */
	public void selectApprovalResults(){
		approves = investigateApproveService.selectApprovalResults(selectedRow);
	}
	
	/**
	 * 绑定前台的同意按钮
	 * @throws PmsException 
	 */
	public void approve() throws PmsException{
		FacesContext context = FacesContext.getCurrentInstance();
		if(selectedRow.getApproveResult() == null){
			investigateApproveService.approve(selectedRow,curEmployee,approves);
			selectApprovalResults();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"审批成功！", "审批成功！");
			context.addMessage(null, message);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已审批的记录不能再审批！", "已审批的记录不能再审批！");
			context.addMessage(null, message);
		}
	}
	/**
	 * 判断是否已经被审批
	 */
	public void checkApprovaled(){
		
		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		if(selectedRow.getApproveResult() == null){
			request.addCallbackParam("success", true);
		}else{
			request.addCallbackParam("success", false);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已审批的记录不能再审批！", "已审批的记录不能再审批！");
			context.addMessage(null, message);
		}
	}
	
	/**
	 * 绑定前台的不同意按钮
	 * @throws PmsException 
	 */
	public void refuse() throws PmsException{
		
		FacesContext context = FacesContext.getCurrentInstance();
		investigateApproveService.refuse(selectedRow,curEmployee,refuseReason,approves);
		selectApprovalResults();
		refuseReason = null;
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"审批成功！", "审批成功！");
		context.addMessage(null, message);
		
	}
	
	public void clearSelectForm(){
		startDate = null;
		endDate = null;
		ensureFlag = 0;
	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		
		this.disEnsureButton = false;
		selectApprovalResults();

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		
		this.disEnsureButton = true;
		approves.clear();
	
	}
	
	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordUpdatedRow() {
		
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEnsureFlag(Integer ensureFlag) {
		this.ensureFlag = ensureFlag;
	}

	public Integer getEnsureFlag() {
		return ensureFlag;
	}

	public void setDisEnsureButton(boolean disEnsureButton) {
		this.disEnsureButton = disEnsureButton;
	}

	public boolean isDisEnsureButton() {
		return disEnsureButton;
	}

	public void setLeaveId(Long leaveId) {
		this.checkId = leaveId;
	}

	public Long getLeaveId() {
		return checkId;
	}

	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}

	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}

	public void setRecords(List<PensionCheckExtend> records) {
		this.records = records;
	}

	public List<PensionCheckExtend> getRecords() {
		return records;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	public Long getCheckId() {
		return checkId;
	}

	public void setInvestigateApproveService(InvestigateApproveService investigateApproveService) {
		this.investigateApproveService = investigateApproveService;
	}

	public InvestigateApproveService getInvestigateApproveService() {
		return investigateApproveService;
	}

	public void setApproves(List<PensionCheckApproveExtend> approves) {
		this.approves = approves;
	}

	public List<PensionCheckApproveExtend> getApproves() {
		return approves;
	}

	public PensionCheckExtend getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(PensionCheckExtend selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

}
