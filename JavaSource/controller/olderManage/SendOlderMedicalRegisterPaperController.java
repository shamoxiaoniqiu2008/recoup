package controller.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

import service.olderManage.PensionHospitalizeregisterExtend;
import service.olderManage.SendOlderMedicalRegisterPaperService;
import util.PmsException;

public class SendOlderMedicalRegisterPaperController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionHospitalizeregisterExtend> records = new ArrayList<PensionHospitalizeregisterExtend>();
	/**
	 * 被选中的记录
	 */
	private PensionHospitalizeregisterExtend[] selectedRows;
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 分组Id
	 */
	private Long groupId;
	/**
	 * 老人Id
	 */
	private Long olderId;
	/**
	 * 修改按钮是否可用
	 */
	private boolean disSendButton = true;
	/**
	 * 注入业务方法
	 */
	private transient SendOlderMedicalRegisterPaperService sendOlderMedicalRegisterPaperService;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee)SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
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
		selectOlderMedicalRegisterRecords();
	}
	/**
	 * 初始化sql语句
	 */
	public void initSql() {
	
	}
	/**
	 * 查询老人就医登记记录
	 */
	public void selectOlderMedicalRegisterRecords(){
		disSendButton = true;
		selectedRows = null;
		setRecords(sendOlderMedicalRegisterPaperService.selectOlderMedicalRegisterRecords(startDate,endDate,olderId,groupId));
	}
	/**
	 * 发送老人就医登记单
	 * @throws PmsException 
	 */
	public void sendOlderMedicalRegisterRecord() throws PmsException{
		
		FacesContext context = FacesContext.getCurrentInstance();
		for(PensionHospitalizeregisterExtend selectedRow:selectedRows){	
			if(selectedRow.getSended() == null||selectedRow.getSended()==2){
				sendOlderMedicalRegisterPaperService.sendOlderMedicalRegisterPaper(selectedRow,curEmployee);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"提交成功！", "提交成功！");
				context.addMessage(null, message);
			}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"已提交的记录不能再提交！", "已提交的记录不能再提交！");
				context.addMessage(null, message);
			}
		}
		selectOlderMedicalRegisterRecords();
	}
	public void clearSelectForm(){
		startDate = null;
		endDate = null;
		olderId = null;
		groupId = null;
	}

	
	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		
		this.disSendButton = false;

	}
	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		if(selectedRows.length == 0){
			this.disSendButton = true;
		}
	}

	public void setDisSendButton(boolean disSendButton) {
		this.disSendButton = disSendButton;
	}

	public boolean isDisSendButton() {
		return disSendButton;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setSendOlderMedicalRegisterPaperService(
			SendOlderMedicalRegisterPaperService sendOlderMedicalRegisterPaperService) {
		this.sendOlderMedicalRegisterPaperService = sendOlderMedicalRegisterPaperService;
	}

	public SendOlderMedicalRegisterPaperService getSendOlderMedicalRegisterPaperService() {
		return sendOlderMedicalRegisterPaperService;
	}
	public void setRecords(List<PensionHospitalizeregisterExtend> records) {
		this.records = records;
	}
	public List<PensionHospitalizeregisterExtend> getRecords() {
		return records;
	}
	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}
	public Long getOlderId() {
		return olderId;
	}
	public PensionHospitalizeregisterExtend[] getSelectedRows() {
		return selectedRows;
	}
	public void setSelectedRows(PensionHospitalizeregisterExtend[] selectedRows) {
		this.selectedRows = selectedRows;
	}
	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}
	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}
	
}
