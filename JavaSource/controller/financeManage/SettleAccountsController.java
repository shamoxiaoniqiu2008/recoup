package controller.financeManage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

import service.financeManage.SettleAccountsService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionTemppayment;
import domain.olderManage.PensionDepartapprove;
import domain.olderManage.PensionDepartregister;
import domain.olderManage.PensionOlder;


/**
 * 
 * 离院结算  author:mary.liu 
 *
 */

public class SettleAccountsController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient SettleAccountsService settleAccountsService;

	
	/**********************  查询   ************************/
	
	private Long olderId;
	private String olderName;
	private String bedName;
	
	private String selectSQL;
	
	/**********************  列表   ************************/
	
	private transient DataTable listTable;
	
	private List<PensionDepartregister> pensionDepartregisters;
	
	private PensionDepartregister selectPensionDepartregister;
	
	private PensionOlder selectOlder;
	
	/**********************  结算对话框   ************************/
	
	private List<PensionNormalpayment> paidNormals;//已缴费日常项目
	
	private List<PensionTemppayment> paidTemps;//已缴费临时项目
	
	private List<PensionNormalpayment> unPaidNormals;//未缴费日常项目
	
	private List<PensionTemppayment> unPaidTemps;//未缴费临时项目
	
	private List<PensionDepartapprove> approves; //离院审批记录

	private PensionEmployee employee;
	
	private Long departId;
	
	private Float totalFees;
	
	private Float left;
	
	private final static Integer YES=1;
	private final static Integer NO=2;
	
	private final static String YESSTR="是";
	private final static String NOSTR="否";
	//pension_departregister
	private final static Integer TO_DEPART=5;//待离院
	private final static Integer DEPART=6;//已结算
	
	//pension_older
	private final static Integer OLDER_DEPART=5;//离院
	
	private final static String TO_DEPART_STR="待结算";//待离院
	private final static String DEPART_STR="已离院";//已结算
	
	@PostConstruct
	public void init(){

		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String departIdStr = paramsMap.get("departId");
		if (departIdStr != null) {
			departId = Long.valueOf(departIdStr);
		} else {
			departId = null;
		}
		this.initSQL();
		employee=(PensionEmployee) SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
		this.search();
	}
	
	public void initSQL(){
		selectSQL="SELECT o.id AS id, o.name AS name, o.inputCode AS inputCode, IF(o.sex > 1,'女','男') AS sex, " 
				+"bu.name AS building_name, f.name AS floor_name, "
				+"r.name AS room_name, be.name AS bed_name, IF(o.statuses > 5,'已离院','待结算') AS isAgree "
				+"FROM  pension_older o, pension_livingrecord l, pension_building bu, pension_floor f, pension_room r, pension_bed be "
				+"WHERE bu.id=f.build_id AND f.id=r.floor_id AND r.id=be.room_id AND be.id=l.bed_id AND l.older_id=o.id AND o.statuses IN (5,6) "
				+"GROUP BY o.id,o.statuses";
	}

	/**
	 * check 常见结果列表dateTable 是否选择了数据，并且该条记录未结算
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectPayData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("payListForm:list");
		PensionDepartregister  arr = (PensionDepartregister) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}/*else if(OLDER_DEPART.equals(selectOlder.getStatuses())){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"该老人还没有确认离院，不能结算！","该老人还没有确认离院，不能结算！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}*/else if(DEPART_STR.equals(arr.getApproveResultStr())){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"该老人已结算，不能重复结算！","该老人已结算，不能重复结算！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据，并且该数据已结算
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectCloseData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("payListForm:list");
		PensionDepartregister  arr = (PensionDepartregister) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else if(TO_DEPART_STR.equals(arr.getApproveResultStr())){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"该老人未结算，不能撤销结算！","该老人未结算，不能撤销结算！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据，并且该数据已结算
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectNotifyData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("payListForm:list");
		PensionDepartregister  arr = (PensionDepartregister) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else if(YES.equals(arr.getIsgeneratenotify())){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"该老人已发送过医院通知！","该老人已发送过医院通知！");
			request.addCallbackParam("validate", true);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("payListForm:list");
		PensionDepartregister  arr = (PensionDepartregister) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * 点击结算，查询出该老人的费用信息
	 */
	public void beforeSettle(){
		totalFees=new Float(0);
		left=new Float(0);
		//已缴费日常缴费项目，未缴费日常缴费项目，已缴费总金额，未缴费总金额
		Map<String, List> pensionNormalPaymentMap = settleAccountsService.selectPensionNormalPaymentDetails(selectPensionDepartregister.getOlderId());
		paidNormals = pensionNormalPaymentMap.get("PAIDNORMALPAYMENTS");
		unPaidNormals = pensionNormalPaymentMap.get("UNPAIDNORMALPAYMENTS");
		List<Float> normalFees=pensionNormalPaymentMap.get("FEES");
		totalFees+=normalFees.get(0);
		left+=normalFees.get(1);
		//已缴费临时缴费项目，未缴费临时项目，已缴费总金额，未缴费总金额
		Map<String, List> pensionTempPaymentMap = settleAccountsService.selectPansionTempPaymentDetails(selectPensionDepartregister.getOlderId());
		paidTemps = pensionTempPaymentMap.get("PAIDTEMPPAYMENTS");
		unPaidTemps = pensionTempPaymentMap.get("UNPAIDTEMPPAYMENTS");
		List<Float> TempFees=pensionTempPaymentMap.get("FEES");
		totalFees+=TempFees.get(0);
		left+=TempFees.get(1);
	}
	
	
	/**
	 * 保存结算信息
	 */
	public void settleAccounts(){
		RequestContext request = RequestContext.getCurrentInstance();
		try{
			//结算
			selectPensionDepartregister=settleAccountsService.settleAccounts(selectPensionDepartregister,paidNormals,paidTemps,employee);
			//更改页面状态
			selectPensionDepartregister.setIsCloseStr(YESSTR);//已结算
			selectPensionDepartregister.setApproveResultStr(DEPART_STR);//已结算
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("结算成功！", "结算成功！"));
			request.addCallbackParam("validate", true);
		}catch(PmsException e){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
			request.addCallbackParam("validate", false);
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("结算时出错！", "结算时出错！"));
			request.addCallbackParam("validate", false);
		}
	}
	

	/**
	 * check 是否可以结算
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkBeforeSettle(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		if( unPaidNormals.size()>0||unPaidTemps.size()>0){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"该老人还有未缴费的项目，不能结算！","该老人还有未缴费的项目，不能结算！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	
	
	
	
	public void search() {
		if(departId!=null){
			pensionDepartregisters=settleAccountsService.selectDepartedInfo(departId);
			settleAccountsService.updateMessageProcessor(departId,employee.getId(),employee.getDeptId());
			departId=null;
		}else{
			if(new Long(0).equals(olderId)||olderName==null||"".equals(olderName)){
				olderId=null;
			}
			pensionDepartregisters=settleAccountsService.search(olderId);
		}
	}

	public void onChangeTab(TabChangeEvent e){
		
	}


	/**
	 * 选中一条缴费主记录，查询明细列表
	 * @param e
	 */
	public void onPensionDepartregisterSelect(SelectEvent e){
		selectOlder=settleAccountsService.selectOlder(selectPensionDepartregister.getOlderId());
	}
	
	public void onPensionDepartregisterUnselect(UnselectEvent e){
		
	}

	/**
	 * 撤销结算
	 */
	public void redoSettleAccounts(){
		try {
			//撤销结算
			settleAccountsService.redoSettleAccounts(selectPensionDepartregister.getId(),selectPensionDepartregister.getOlderId(),employee);
			//更改状态
			selectPensionDepartregister.setIsagree(TO_DEPART);//待离院
			selectPensionDepartregister.setApproveResultStr(TO_DEPART_STR);//待离院
			selectPensionDepartregister.setIspay(NO);//未离院结算
			selectPensionDepartregister.setIsCloseStr(NOSTR);//未结算
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("撤销结算成功！", "撤销结算成功！"));
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("撤销结算失败！", "撤销结算失败！"));
		}
	}

	/**
	 * 查看离院审批意见
	 */
	public void selectApproves(){
		approves=settleAccountsService.selectApproves(selectPensionDepartregister.getId());
	}







	public SettleAccountsService getSettleAccountsService() {
		return settleAccountsService;
	}
	
	public void setSettleAccountsService(SettleAccountsService settleAccountsService) {
		this.settleAccountsService = settleAccountsService;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getBedName() {
		return bedName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getSelectSQL() {
		return selectSQL;
	}

	public void setSelectSQL(String selectSQL) {
		this.selectSQL = selectSQL;
	}

	public List<PensionDepartregister> getPensionDepartregisters() {
		return pensionDepartregisters;
	}

	public void setPensionDepartregisters(
			List<PensionDepartregister> pensionDepartregisters) {
		this.pensionDepartregisters = pensionDepartregisters;
	}

	public PensionDepartregister getSelectPensionDepartregister() {
		return selectPensionDepartregister;
	}

	public void setSelectPensionDepartregister(
			PensionDepartregister selectPensionDepartregister) {
		this.selectPensionDepartregister = selectPensionDepartregister;
	}

	public List<PensionNormalpayment> getPaidNormals() {
		return paidNormals;
	}

	public void setPaidNormals(List<PensionNormalpayment> paidNormals) {
		this.paidNormals = paidNormals;
	}

	public List<PensionTemppayment> getPaidTemps() {
		return paidTemps;
	}

	public void setPaidTemps(List<PensionTemppayment> paidTemps) {
		this.paidTemps = paidTemps;
	}

	public List<PensionNormalpayment> getUnPaidNormals() {
		return unPaidNormals;
	}

	public void setUnPaidNormals(List<PensionNormalpayment> unPaidNormals) {
		this.unPaidNormals = unPaidNormals;
	}

	public List<PensionTemppayment> getUnPaidTemps() {
		return unPaidTemps;
	}

	public void setUnPaidTemps(List<PensionTemppayment> unPaidTemps) {
		this.unPaidTemps = unPaidTemps;
	}


	public DataTable getListTable() {
		return listTable;
	}

	public void setListTable(DataTable listTable) {
		this.listTable = listTable;
	}

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
	}

	public Long getDepartId() {
		return departId;
	}

	public void setDepartId(Long departId) {
		this.departId = departId;
	}

	public Float getTotalFees() {
		return totalFees;
	}

	public void setTotalFees(Float totalFees) {
		this.totalFees = totalFees;
	}

	public Float getLeft() {
		return left;
	}

	public void setLeft(Float left) {
		this.left = left;
	}

	public PensionOlder getSelectOlder() {
		return selectOlder;
	}

	public void setSelectOlder(PensionOlder selectOlder) {
		this.selectOlder = selectOlder;
	}

	public List<PensionDepartapprove> getApproves() {
		return approves;
	}

	public void setApproves(List<PensionDepartapprove> approves) {
		this.approves = approves;
	}
	

}
