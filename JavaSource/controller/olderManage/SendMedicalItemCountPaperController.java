package controller.olderManage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

import service.olderManage.CountMedicalItemService;
import service.olderManage.MedicalGroupManageService;
import service.olderManage.PensionHospitalizegroupExtend;
import service.olderManage.PensionHospitalizeregisterExtend;
import service.olderManage.PensionItemcountExtend;
import service.olderManage.SendMedicalItemCountPaperService;
import util.PmsException;

public class SendMedicalItemCountPaperController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionHospitalizegroupExtend> records = new ArrayList<PensionHospitalizegroupExtend>();
	/**
	 * 每条分组记录 都对应着一个 项目记录   itemRecords 用来在前台显示该就医项目记录
	 */
	private List<PensionItemcountExtend> itemRecords = new ArrayList<PensionItemcountExtend>();
	/**
	 * 每条分组记录 都对应着一个 项目明细   itemDetails 用来在前台显示该就医项目记录
	 */
	private List<PensionHospitalizeregisterExtend> itemDetails = new ArrayList<PensionHospitalizeregisterExtend>(); 
	/**
	 * 被选中的分组记录
	 */
	private PensionHospitalizegroupExtend[] selectedGroupRows;
	/**
	 * 被查看的分组记录
	 */
	private PensionHospitalizegroupExtend viewedGroupRow;
	/**
	 * 被选中的项目统计记录
	 */
	private	PensionItemcountExtend selectedItemCountRow;
	/**
	 * 分组Id
	 */
	private Long groupId;
	/**
	 * 修改按钮是否可用
	 */
	private boolean disSendButton = true;
	/**
	 * 注入业务
	 */
	private transient CountMedicalItemService countMedicalItemService; 
	private transient SendMedicalItemCountPaperService sendMedicalItemCountPaperService; 
	private transient MedicalGroupManageService medicalGroupManageService; 
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
		selectGroupRecords();
	}
	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		
	}
	
	/**
	 * 查询分组记录
	 */
	public void selectGroupRecords(){
		setRecords(medicalGroupManageService.selectGroupRecords(null,null,null,groupId));
		
	}
	/**
	 * 查询被选中行对应的项目统计记录
	 * @param selectedRow
	 */
	public void selectMedicalItemRecords(PensionHospitalizegroupExtend selectedRow){
		viewedGroupRow = selectedRow;
		itemRecords = countMedicalItemService.selectMedicalItemRecord(viewedGroupRow);
		setItemDetails(new ArrayList<PensionHospitalizeregisterExtend>());
	}
	/**
	 * 查询被选中行对应的项目明细
	 * @param selectedRow
	 */
	public void selectMedicalItemDetails(){
		setItemDetails(countMedicalItemService.selectMedicalItemDetails(viewedGroupRow.getId(),selectedItemCountRow.getItemId()));
	}
	/**
	 * 发送被查看分组的 项目统计单
	 * @throws PmsException 
	 */
	public void sendItemCountPaper() throws PmsException{
		
		FacesContext context = FacesContext.getCurrentInstance();
		if(viewedGroupRow.getSended()== null||viewedGroupRow.getSended()==2){
			sendMedicalItemCountPaperService.sendItemCountPaper(viewedGroupRow,itemRecords,curEmployee);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"发送统计单成功！", "发送统计单成功！");
			context.addMessage(null, message);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已发送的统计单不能再发送！", "已发送的统计单不能再发送！");
			context.addMessage(null, message);
		}
		selectGroupRecords();
	}
	/**
	 * 发送被选中的分组的项目统计单
	 * @throws PmsException 
	 */
	public void sendItemCountPapers() throws PmsException{
		
		FacesContext context = FacesContext.getCurrentInstance();
		for(PensionHospitalizegroupExtend selectedRow:selectedGroupRows){	
			
			List<PensionItemcountExtend> items =  countMedicalItemService.selectMedicalItemRecord(selectedRow);
			if(selectedRow.getSended()== null||selectedRow.getSended()==2){
				
				sendMedicalItemCountPaperService.sendItemCountPaper(selectedRow,items,curEmployee);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"发送统计单成功！", "发送统计单成功！");
				context.addMessage(null, message);
			}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"已发送的统计单不能再发送！", "已发送的统计单不能再发送！");
				context.addMessage(null, message);
			}
		}
		selectGroupRecords();
	}
	
	/**
	 * 清空选择条件
	 */
	public void clearSelectForm(){
		groupId = null;
	}
	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		
		this.setDisSendButton(false);

	}
	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		if(selectedGroupRows.length == 0){
			this.setDisSendButton(true);
		}
	}
	/**
	 * detail被选中时候的触发事件
	 */
	public void selectDetail(SelectEvent e) {
		selectMedicalItemDetails();

	}
	
	/**
	 * detail不给选中时的触发事件
	 */
	public void unSelectDetail(UnselectEvent e) {
		setItemDetails(new ArrayList<PensionHospitalizeregisterExtend>());
	}
	public void setDisSendButton(boolean disSendButton) {
		this.disSendButton = disSendButton;
	}
	public boolean isDisSendButton() {
		return disSendButton;
	}
	public void setRecords(List<PensionHospitalizegroupExtend> records) {
		this.records = records;
	}
	public List<PensionHospitalizegroupExtend> getRecords() {
		return records;
	}
	public void setSendMedicalItemCountPaperService(
			SendMedicalItemCountPaperService sendMedicalItemCountPaperService) {
		this.sendMedicalItemCountPaperService = sendMedicalItemCountPaperService;
	}
	public SendMedicalItemCountPaperService getSendMedicalItemCountPaperService() {
		return sendMedicalItemCountPaperService;
	}
	public List<PensionItemcountExtend> getItemRecords() {
		return itemRecords;
	}
	public void setItemRecords(List<PensionItemcountExtend> itemRecords) {
		this.itemRecords = itemRecords;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public CountMedicalItemService getCountMedicalItemService() {
		return countMedicalItemService;
	}
	public void setCountMedicalItemService(
			CountMedicalItemService countMedicalItemService) {
		this.countMedicalItemService = countMedicalItemService;
	}
	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}
	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}

	public MedicalGroupManageService getMedicalGroupManageService() {
		return medicalGroupManageService;
	}
	public void setMedicalGroupManageService(
			MedicalGroupManageService medicalGroupManageService) {
		this.medicalGroupManageService = medicalGroupManageService;
	}
	public void setSelectedItemCountRow(PensionItemcountExtend selectedItemCountRow) {
		this.selectedItemCountRow = selectedItemCountRow;
	}
	public PensionItemcountExtend getSelectedItemCountRow() {
		return selectedItemCountRow;
	}
	public void setItemDetails(List<PensionHospitalizeregisterExtend> itemDetails) {
		this.itemDetails = itemDetails;
	}
	public List<PensionHospitalizeregisterExtend> getItemDetails() {
		return itemDetails;
	}
	public PensionHospitalizegroupExtend[] getSelectedGroupRows() {
		return selectedGroupRows;
	}
	public void setSelectedGroupRows(
			PensionHospitalizegroupExtend[] selectedGroupRows) {
		this.selectedGroupRows = selectedGroupRows;
	}
	public PensionHospitalizegroupExtend getViewedGroupRow() {
		return viewedGroupRow;
	}
	public void setViewedGroupRow(PensionHospitalizegroupExtend viewedGroupRow) {
		this.viewedGroupRow = viewedGroupRow;
	}
}
