/**  
* @Title: RecoverProgrammeController.java 
* @Package controller.configureManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-10-11 上午8:58:20 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package controller.configureManage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import domain.olderManage.PensionRecureDetail;

import service.configureManage.RecoverProgrammeService;

/** 
 * @ClassName: RecoverProgrammeController 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-10-11 上午8:58:20
 * @version V1.0 
 */
public class RecoverProgrammeController implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	* @version V1.0
	*/ 
	
	private static final long serialVersionUID = -8746077187560903321L;
	
	private transient RecoverProgrammeService recoverProgrammeService;
	
	//康复计划类扩展
	private PensionRecureDetailExtend pensionRecureDetailExtendForAdd = new PensionRecureDetailExtend();
	private PensionRecureDetail pensionRecureDetail = new PensionRecureDetail();
	//康复计划List
	private List<PensionRecureDetailExtend> pensionRecureDetailExtendList = new ArrayList<PensionRecureDetailExtend>();
	//选中的康复计划行
	private PensionRecureDetailExtend selectedRow = new PensionRecureDetailExtend();
	
	/**
	 * 按钮可用性控制参数
	 */
	private boolean addButtonEnableFlag = false;
	private boolean modifyButtonEnableFlag = false;
	private boolean deleteButtonEnableFlag = false;
	
	// 定义康复项目输入法变量
	private String itemToSql;
	private String fitcolItem;
	private String displaycolItem;
	
	//定义康复责任人输入法变量
	private String recoverToSql;
	private String fitcolRecover;
	private String displaycolRecover;
	
	private Long recoverId;
	private String recoverName;
	
	@PostConstruct
	public void init(){
		//获取所有康复项目记录
		pensionRecureDetailExtendList = recoverProgrammeService.getAllPensionRecureDetailExtendList();
		//按钮可用性初始化
		addButtonEnableFlag = false;
		modifyButtonEnableFlag = true;
		deleteButtonEnableFlag = true;
		//初始化康复项目输入法
		initItemToSql();
		//初始化康复负责人输入法
		initRecoverToSql();
	}
	
	/**
	 * 
	* @Title: initItemToSql 
	* @Description: 初始化项目输入法
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-12 上午9:54:33
	* @version V1.0
	 */
	public void initItemToSql(){
		itemToSql ="select"
								+" pdri.id as itemId"
								+" ,pdri.itemname as itemName"
								+" ,pdri.inputcode as inputCode"
							+" from"
								+" pension_dic_recure_item pdri";
		fitcolItem = "itemId,inputCode";
		displaycolItem = "编号:0:hide,名称:1,输入码:2";
	}
	
	/**
	 * 
	* @Title: initRecoverToSql 
	* @Description: 初始化康复负责人输入法
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-12 上午11:23:45
	* @version V1.0
	 */
	public void initRecoverToSql(){
		recoverToSql ="select"
									+" pe.id as userId"
									+" ,pe.name as userName"
									+" ,pe.inputcode as inputCode"
								+" from"
									+" pension_employee pe";
		fitcolRecover = "userId,inputCode";
		displaycolRecover = "编号:0:hide,姓名:1,输入码:2";
	}
	
	/**
	 * 
	* @Title: addPensionRecureDetail 
	* @Description: 新增康复项目
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-12 上午11:24:12
	* @version V1.0
	 */
	public void addPensionRecureDetail(){
		FacesContext context = FacesContext.getCurrentInstance();
		List<PensionRecureDetail> tempList = new ArrayList<PensionRecureDetail>();
		tempList = recoverProgrammeService.getPensionRecureDetailList(pensionRecureDetail);
		if(tempList.size() > 0){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"康复项目"+recoverProgrammeService.getRecoverNameById(pensionRecureDetail.getRecureitemId())+"步骤设定重复！","提示"));
		}else{
			recoverProgrammeService.insertInto(pensionRecureDetail);
			pensionRecureDetailExtendList = recoverProgrammeService.getAllPensionRecureDetailExtendList();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"康复项目"+recoverProgrammeService.getRecoverNameById(pensionRecureDetail.getRecureitemId())+"新增成功！","提示"));
			pensionRecureDetailExtendForAdd = new PensionRecureDetailExtend();
			pensionRecureDetail = new PensionRecureDetail();
		}
		
	}
	
	/**
	 * 
	* @Title: setRowSelectedPara 
	* @Description: TODO
	* @param @param event
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-14 上午8:52:55
	* @version V1.0
	 */
	public void setRowSelectedPara(SelectEvent event){
		modifyButtonEnableFlag = false;
		deleteButtonEnableFlag = false;
	}
	
	/**
	 * 
	* @Title: setRowUnSelectedPara 
	* @Description: TODO
	* @param @param unEvent
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-14 上午8:53:00
	* @version V1.0
	 */
	public void setRowUnSelectedPara(UnselectEvent unEvent){
		modifyButtonEnableFlag = true;
		deleteButtonEnableFlag = true;
	}
	
	/**
	 * 
	* @Title: updatePensionRecureDetail 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-14 上午9:56:31
	* @version V1.0
	 */
	public void updatePensionRecureDetail(){
		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		List<PensionRecureDetail> tempList = new ArrayList<PensionRecureDetail>();
		tempList = recoverProgrammeService.getPensionRecureDetailList(selectedRow.getPensionRecureDetail());
		if(tempList.size() > 0){
			request.addCallbackParam("closeFlag", false);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"康复项目"+recoverProgrammeService.getRecoverNameById(selectedRow.getPensionRecureDetail().getRecureitemId())+"步骤设定重复！","提示"));
		}else{
			request.addCallbackParam("closeFlag", true);
			recoverProgrammeService.updateDetail(selectedRow.getPensionRecureDetail());
			pensionRecureDetailExtendList = recoverProgrammeService.getAllPensionRecureDetailExtendListById(recoverId);
			modifyButtonEnableFlag = true;
			deleteButtonEnableFlag = true;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"康复项目"+selectedRow.getRecureItemName()+"修改成功！","提示"));
		}
	}
	
	/**
	 * 
	* @Title: deletePensionRecureDetail 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-14 上午11:28:32
	* @version V1.0
	 */
	public void deletePensionRecureDetail(){
		recoverProgrammeService.deleteDetail(selectedRow.getPensionRecureDetail().getId());
		pensionRecureDetailExtendList = recoverProgrammeService.getAllPensionRecureDetailExtendList();
		modifyButtonEnableFlag = true;
		deleteButtonEnableFlag = true;
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"康复项目"+selectedRow.getRecureItemName()+"删除成功！","提示"));
	}
	
	/**
	 * 
	* @Title: searchListById 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-14 下午1:41:50
	* @version V1.0
	 */
	public void searchListById(){
		FacesContext context = FacesContext.getCurrentInstance();
		if(recoverName == null || "".equals(recoverName.trim())){
			recoverId = null;
			pensionRecureDetailExtendList = recoverProgrammeService.getAllPensionRecureDetailExtendList();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"查询完毕！","提示"));
		}else{
			pensionRecureDetailExtendList = recoverProgrammeService.getPensionRecureDetailExtendListById(recoverId);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"查询完毕！","提示"));
		}
	}
	
	/**
	 * 
	* @Title: initParam 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-14 下午1:42:30
	* @version V1.0
	 */
	public void initParam(){
		FacesContext context = FacesContext.getCurrentInstance();
		recoverId = null;
		recoverName = "";
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"清空成功！","提示"));
	}
	
	/**
	 * 
	* @Title: autoGenerateExcPlanForAdd 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-16 下午2:03:03
	* @version V1.0
	 */
	public void autoGenerateExcPlanForAdd(){
		FacesContext context = FacesContext.getCurrentInstance();
		BigDecimal bigNumber = new BigDecimal(pensionRecureDetail.getNumber());
		BigDecimal bigTotalday = new BigDecimal(pensionRecureDetail.getTotalday());
		if(bigNumber == null || bigTotalday == null){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"持续时间和康复次数不能为空！","提示"));
		}else{
			BigDecimal  micDecimal = bigTotalday.divide(bigNumber, 0, BigDecimal.ROUND_FLOOR);
			int stepNum = micDecimal.intValue();
			int maxNum = bigNumber.intValue();
			int totalNum = bigTotalday.intValue();
			if(maxNum == 1){
				pensionRecureDetail.setScheme("1");
			}else{
				if(micDecimal.compareTo(new BigDecimal(1)) == -1){
					stepNum = 1;
					pensionRecureDetail.setScheme(excuteSchemaBellow(maxNum,stepNum,totalNum));
				}else{
					pensionRecureDetail.setScheme(excuteSchemaAbove(maxNum,stepNum));
				}
			}
		}
	}
	
	/**
	 * 
	* @Title: excuteSchemaAbove 
	* @Description: TODO
	* @param @param maxNum
	* @param @param stepNum
	* @param @return
	* @return String
	* @throws 
	* @author Justin.Su
	* @date 2013-10-16 下午2:02:59
	* @version V1.0
	 */
	public String excuteSchemaAbove(int maxNum,int stepNum){
		String str = "";
		int currentNum = 1;
		for(int i=0; i< maxNum; i++){
			if(i==0){
				str = String.valueOf(currentNum)+",";
			}else{
				currentNum = currentNum + stepNum;
				str = str + String.valueOf(currentNum)+",";
			}
		}
		str = str.substring(0, str.length()-1);
		return str;
	}
	
	/**
	 * 
	* @Title: excuteSchemaBellow 
	* @Description: TODO
	* @param @param maxNum
	* @param @param stepNum
	* @param @param totalNum
	* @param @return
	* @return String
	* @throws 
	* @author Justin.Su
	* @date 2013-10-16 下午2:02:55
	* @version V1.0
	 */
	public String excuteSchemaBellow(int maxNum,int stepNum,int totalNum){
		String str = "";
		int currentNum = 1;
		if(totalNum < maxNum){
			int subNum = maxNum - totalNum;
			for(int i=0;i<subNum;i++){
				str = str+String.valueOf(1)+",";
			}
		}else{
			str = String.valueOf(1)+",";
		}
		
		for(int i=0; i< totalNum; i++){
			if(i==0){
				str = str + String.valueOf(currentNum)+",";
			}else{
				currentNum = currentNum + stepNum;
				str = str + String.valueOf(currentNum)+",";
			}
		}
		str = str.substring(0, str.length()-1);
		return str;
	}
	
	/**
	 * 
	* @Title: autoGenerateExcPlanForEdit 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-16 下午2:02:49
	* @version V1.0
	 */
	public void autoGenerateExcPlanForEdit(){
		FacesContext context = FacesContext.getCurrentInstance();
		BigDecimal bigNumber = new BigDecimal(selectedRow.getPensionRecureDetail().getNumber());
		BigDecimal bigTotalday = new BigDecimal(selectedRow.getPensionRecureDetail().getTotalday());
		if(bigNumber == null || bigTotalday == null){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"持续时间和康复次数不能为空！","提示"));
		}else{
			BigDecimal  micDecimal = bigTotalday.divide(bigNumber, 0, BigDecimal.ROUND_FLOOR);
			int stepNum = micDecimal.intValue();
			int maxNum = bigNumber.intValue();
			int totalNum = bigTotalday.intValue();
			if(maxNum == 1){
				selectedRow.getPensionRecureDetail().setScheme("1");
			}else{
				if(micDecimal.compareTo(new BigDecimal(1)) == -1){
					stepNum = 1;
					selectedRow.getPensionRecureDetail().setScheme(excuteSchemaBellow(maxNum,stepNum,totalNum));
				}else{
					selectedRow.getPensionRecureDetail().setScheme(excuteSchemaAbove(maxNum,stepNum));
				}
			}
		}
	}
	
	
	/**
	 * @return the pensionRecureDetailExtendForAdd
	 */
	public PensionRecureDetailExtend getPensionRecureDetailExtendForAdd() {
		return pensionRecureDetailExtendForAdd;
	}

	/**
	 * @param pensionRecureDetailExtendForAdd the pensionRecureDetailExtendForAdd to set
	 */
	public void setPensionRecureDetailExtendForAdd(
			PensionRecureDetailExtend pensionRecureDetailExtendForAdd) {
		this.pensionRecureDetailExtendForAdd = pensionRecureDetailExtendForAdd;
	}

	/**
	 * @return the selectedRow
	 */
	public PensionRecureDetailExtend getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow the selectedRow to set
	 */
	public void setSelectedRow(PensionRecureDetailExtend selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return the addButtonEnableFlag
	 */
	public boolean isAddButtonEnableFlag() {
		return addButtonEnableFlag;
	}

	/**
	 * @param addButtonEnableFlag the addButtonEnableFlag to set
	 */
	public void setAddButtonEnableFlag(boolean addButtonEnableFlag) {
		this.addButtonEnableFlag = addButtonEnableFlag;
	}

	/**
	 * @return the modifyButtonEnableFlag
	 */
	public boolean isModifyButtonEnableFlag() {
		return modifyButtonEnableFlag;
	}

	/**
	 * @param modifyButtonEnableFlag the modifyButtonEnableFlag to set
	 */
	public void setModifyButtonEnableFlag(boolean modifyButtonEnableFlag) {
		this.modifyButtonEnableFlag = modifyButtonEnableFlag;
	}

	/**
	 * @return the deleteButtonEnableFlag
	 */
	public boolean isDeleteButtonEnableFlag() {
		return deleteButtonEnableFlag;
	}

	/**
	 * @param deleteButtonEnableFlag the deleteButtonEnableFlag to set
	 */
	public void setDeleteButtonEnableFlag(boolean deleteButtonEnableFlag) {
		this.deleteButtonEnableFlag = deleteButtonEnableFlag;
	}


	/**
	 * @return the recoverProgrammeService
	 */
	public RecoverProgrammeService getRecoverProgrammeService() {
		return recoverProgrammeService;
	}


	/**
	 * @param recoverProgrammeService the recoverProgrammeService to set
	 */
	public void setRecoverProgrammeService(RecoverProgrammeService recoverProgrammeService) {
		this.recoverProgrammeService = recoverProgrammeService;
	}


	/**
	 * @return the pensionRecureDetailExtendList
	 */
	public List<PensionRecureDetailExtend> getPensionRecureDetailExtendList() {
		return pensionRecureDetailExtendList;
	}


	/**
	 * @param pensionRecureDetailExtendList the pensionRecureDetailExtendList to set
	 */
	public void setPensionRecureDetailExtendList(
			List<PensionRecureDetailExtend> pensionRecureDetailExtendList) {
		this.pensionRecureDetailExtendList = pensionRecureDetailExtendList;
	}


	/**
	 * @return the itemToSql
	 */
	public String getItemToSql() {
		return itemToSql;
	}


	/**
	 * @param itemToSql the itemToSql to set
	 */
	public void setItemToSql(String itemToSql) {
		this.itemToSql = itemToSql;
	}


	/**
	 * @return the fitcolItem
	 */
	public String getFitcolItem() {
		return fitcolItem;
	}


	/**
	 * @param fitcolItem the fitcolItem to set
	 */
	public void setFitcolItem(String fitcolItem) {
		this.fitcolItem = fitcolItem;
	}


	/**
	 * @return the displaycolItem
	 */
	public String getDisplaycolItem() {
		return displaycolItem;
	}


	/**
	 * @param displaycolItem the displaycolItem to set
	 */
	public void setDisplaycolItem(String displaycolItem) {
		this.displaycolItem = displaycolItem;
	}

	/**
	 * @return the recoverToSql
	 */
	public String getRecoverToSql() {
		return recoverToSql;
	}

	/**
	 * @param recoverToSql the recoverToSql to set
	 */
	public void setRecoverToSql(String recoverToSql) {
		this.recoverToSql = recoverToSql;
	}

	/**
	 * @return the fitcolRecover
	 */
	public String getFitcolRecover() {
		return fitcolRecover;
	}

	/**
	 * @param fitcolRecover the fitcolRecover to set
	 */
	public void setFitcolRecover(String fitcolRecover) {
		this.fitcolRecover = fitcolRecover;
	}

	/**
	 * @return the displaycolRecover
	 */
	public String getDisplaycolRecover() {
		return displaycolRecover;
	}

	/**
	 * @param displaycolRecover the displaycolRecover to set
	 */
	public void setDisplaycolRecover(String displaycolRecover) {
		this.displaycolRecover = displaycolRecover;
	}

	/**
	 * @return the pensionRecureDetail
	 */
	public PensionRecureDetail getPensionRecureDetail() {
		return pensionRecureDetail;
	}

	/**
	 * @param pensionRecureDetail the pensionRecureDetail to set
	 */
	public void setPensionRecureDetail(PensionRecureDetail pensionRecureDetail) {
		this.pensionRecureDetail = pensionRecureDetail;
	}

	/**
	 * @return the recoverId
	 */
	public Long getRecoverId() {
		return recoverId;
	}

	/**
	 * @param recoverId the recoverId to set
	 */
	public void setRecoverId(Long recoverId) {
		this.recoverId = recoverId;
	}

	/**
	 * @return the recoverName
	 */
	public String getRecoverName() {
		return recoverName;
	}

	/**
	 * @param recoverName the recoverName to set
	 */
	public void setRecoverName(String recoverName) {
		this.recoverName = recoverName;
	}

	
	
	
	
	
}
