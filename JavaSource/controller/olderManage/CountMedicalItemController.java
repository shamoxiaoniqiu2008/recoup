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

import service.olderManage.CountMedicalItemService;
import service.olderManage.MedicalGroupManageService;
import service.olderManage.PensionHospitalizegroupExtend;
import service.olderManage.PensionHospitalizeregisterExtend;
import service.olderManage.PensionItemcountExtend;
import service.olderManage.SendMedicalItemCountPaperService;
import util.PmsException;

public class CountMedicalItemController {
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
	 * 修改按钮是否可用
	 */
	private boolean disGenerateButton = true;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee)SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
	/**
	 * 注入业务
	 */
	private transient MedicalGroupManageService medicalGroupManageService;
	private transient CountMedicalItemService countMedicalItemService; 
	private transient SendMedicalItemCountPaperService sendMedicalItemCountPaperService;
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
		disGenerateButton = true;
		selectedGroupRows = null;
		selectedItemCountRow = null;
		setRecords(medicalGroupManageService.selectGroupRecords(null,null,null,groupId));
		
	}
	/**
	 * 查询被选中行对应的项目统计记录
	 * @param selectedRow
	 */
	public void selectMedicalItemRecords(PensionHospitalizegroupExtend selectedRow){
		viewedGroupRow = selectedRow;
		itemRecords = countMedicalItemService.selectMedicalItemRecord(viewedGroupRow);
		itemDetails = new ArrayList<PensionHospitalizeregisterExtend>();
	}
	
	/**
	 * 查询被选中行对应的项目明细
	 * @param selectedRow
	 */
	public void selectMedicalItemDetails(){
		itemDetails = countMedicalItemService.selectMedicalItemDetails(viewedGroupRow.getId(),selectedItemCountRow.getItemId());
	}
	/**
	 * 生成被查看分组的 项目统计单
	 */
	public void generateItemCountPaper(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		if(viewedGroupRow.getGenerated() == null||viewedGroupRow.getGenerated()==2){
			countMedicalItemService.generateItemCountPaper(viewedGroupRow,itemRecords);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"生成统计单成功！", "生成统计单成功！");
			context.addMessage(null, message);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已生成统计单的记录不能再生成！", "已生成统计单的记录不能再生成！");
			context.addMessage(null, message);
		}
		selectGroupRecords();
	}
	/**
	 * 生成并发送被查看分组的 项目统计单
	 * @throws PmsException 
	 */
	public void generateAndSendItemCountPaper() throws PmsException{
		
		FacesContext context = FacesContext.getCurrentInstance();
		if(viewedGroupRow.getGenerated() == null||viewedGroupRow.getGenerated()==2){
			countMedicalItemService.generateItemCountPaper(viewedGroupRow,itemRecords);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"生成统计单成功！", "生成统计单成功！");
			context.addMessage(null, message);
			sendMedicalItemCountPaperService.sendItemCountPaper(viewedGroupRow,itemRecords,curEmployee);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"发送统计单成功！", "发送统计单成功！");
			context.addMessage(null, message);
			
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已生成统计单的记录不能再生成！", "已生成统计单的记录不能再生成！");
			context.addMessage(null, message);
		}
		selectGroupRecords();
		
	}
	/**
	 * 生成并发送被查看分组的 项目统计单
	 * @throws PmsException 
	 */
	public void generateAndSendItemCountPapers() throws PmsException{

		FacesContext context = FacesContext.getCurrentInstance();
		for(PensionHospitalizegroupExtend selectedRow:selectedGroupRows){	
			
			List<PensionItemcountExtend> items =  countMedicalItemService.selectMedicalItemRecord(selectedRow);
			if(selectedRow.getGenerated() == null||selectedRow.getGenerated()==2){
				
				countMedicalItemService.generateItemCountPaper(selectedRow,items);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"生成统计单成功！", "生成统计单成功！");
				context.addMessage(null, message);
				sendMedicalItemCountPaperService.sendItemCountPaper(selectedRow,items,curEmployee);
				message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"发送统计单成功！", "发送统计单成功！");
				context.addMessage(null, message);
				
			}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"已生成统计单的记录不能再生成！", "已生成统计单的记录不能再生成！");
				context.addMessage(null, message);
			}
		}
		selectGroupRecords();
		
	}
	
	
	/**
	 * 生成被选中的分组的项目统计单
	 */
	public void generateItemCountPapers(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		for(PensionHospitalizegroupExtend selectedRow:selectedGroupRows){	
			
			List<PensionItemcountExtend> items =  countMedicalItemService.selectMedicalItemRecord(selectedRow);
			if(selectedRow.getGenerated() == null||selectedRow.getGenerated()==2){
				
				countMedicalItemService.generateItemCountPaper(selectedRow,items);
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"生成统计单成功！", "生成统计单成功！");
				context.addMessage(null, message);
			}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"已生成统计单的记录不能再生成！", "已生成统计单的记录不能再生成！");
				context.addMessage(null, message);
			}
		}
		selectGroupRecords();
	}
	
	/**
	 * 清空选择条件
	 */
	public void clearSelectForm(){
		startDate = null;
		endDate = null;
		groupId = null;
	}
	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		
		this.setDisGenerateButton(false);

	}
	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		if(selectedGroupRows.length == 0){
			this.setDisGenerateButton(true);
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
		itemDetails = new ArrayList<PensionHospitalizeregisterExtend>();
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
	public PensionItemcountExtend getSelectedItemCountRow() {
		return selectedItemCountRow;
	}
	public void setSelectedItemCountRow(PensionItemcountExtend selectedItemCountRow) {
		this.selectedItemCountRow = selectedItemCountRow;
	}
	public void setDisGenerateButton(boolean disGenerateButton) {
		this.disGenerateButton = disGenerateButton;
	}
	public boolean isDisGenerateButton() {
		return disGenerateButton;
	}
	public void setRecords(List<PensionHospitalizegroupExtend> records) {
		this.records = records;
	}
	public List<PensionHospitalizegroupExtend> getRecords() {
		return records;
	}
	public void setItemRecords(List<PensionItemcountExtend> itemRecords) {
		this.itemRecords = itemRecords;
	}
	public List<PensionItemcountExtend> getItemRecords() {
		return itemRecords;
	}
	public void setCountMedicalItemService(CountMedicalItemService countMedicalItemService) {
		this.countMedicalItemService = countMedicalItemService;
	}
	public CountMedicalItemService getCountMedicalItemService() {
		return countMedicalItemService;
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
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public MedicalGroupManageService getMedicalGroupManageService() {
		return medicalGroupManageService;
	}
	public void setMedicalGroupManageService(
			MedicalGroupManageService medicalGroupManageService) {
		this.medicalGroupManageService = medicalGroupManageService;
	}
	public void setItemDetails(List<PensionHospitalizeregisterExtend> itemDetails) {
		this.itemDetails = itemDetails;
	}
	public List<PensionHospitalizeregisterExtend> getItemDetails() {
		return itemDetails;
	}
	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}
	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}
	public SendMedicalItemCountPaperService getSendMedicalItemCountPaperService() {
		return sendMedicalItemCountPaperService;
	}
	public void setSendMedicalItemCountPaperService(
			SendMedicalItemCountPaperService sendMedicalItemCountPaperService) {
		this.sendMedicalItemCountPaperService = sendMedicalItemCountPaperService;
	}
}
