package controller.financeManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import service.financeManage.DepositService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.dictionary.PensionDicDeposittype;
import domain.dictionary.PensionDicPayway;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionDeposit;
import domain.financeManage.PensionDepositLog;


/**
 * 
 *
 *
 */

public class DepositController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient DepositService depositService;

	private Long depositId;//押金主记录表逐渐
	
	private String olderName;//老人姓名
	
	private Integer status;//老人状态
	
	private Integer depositStatus;//老人押金状态--未缴、正常
	
	private List<PensionDeposit> depositList;//押金主记录列表
	
	private PensionDeposit selectDepoait;
	
	private List<PensionDepositLog> depositLogs;//押金明细列表
	
	private List<PensionDepositLog> backDepositLogs;//可退押金明细列表
	
	private PensionDepositLog addDepositLog;//新缴纳的押金明细
	
	private List<PensionDicDeposittype> depositTypes; //押金类型
	
	private PensionDicDeposittype selectDepositType;//选中的押金类型
	
	private Long selectDepositTypeId;
	
	private PensionEmployee employee;
	
	private boolean backFlag;
	
	private Float totalFees;
	
	private Float change;
	
	private Float left;
	
	private List<PensionDicPayway> payWays;
	
	private Long defaultPayway;//默认付款方式
	private Long chargePayWay;//默认找零方式
	
	private String selectSQL;
	
	private Long backDepositLogId;//要退的押金编号
	
	private PensionDepositLog selectDepositLog;//选中的要退的押金
	
	private final static Integer YES_FLAG=1;
	private final static Integer NO_FLAG=2;
	
	
	@PostConstruct
	public void init(){
		this.initSQL();
		this.initDepositType();
		employee=(PensionEmployee)SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
		this.search();
		
	}
	
	
	//从字典表中获取支付方式，现金 支票 转账等
	private void initPayWays(){
		try {
			defaultPayway=depositService.selectDefaultPayWay();
			chargePayWay=defaultPayway;
			payWays=depositService.selectDicPayWays(defaultPayway);
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
		}
	}
	
	
	public void search(){
		if(olderName==null||"".equals(olderName)){
			depositId=null;
		}
		depositList=depositService.search(depositId,status,depositStatus);
	}
	
	//初始化押金类型列表--读取自字典表
	public void initDepositType(){
		depositTypes=depositService.selectDepositTypes();
		if(depositTypes.size()>0){
		}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("当前还没有设置押金类型！", "当前还没有设置押金类型！"));
		}
	}
	
	public void initSQL(){
		selectSQL = "select deposit.id as id, "
				+ "older.inputCode as inputCode, "
				+ "older.`name` as older_name, "
				+ "bed.buildName as building_name, "
				+ "bed.floorName as floor_name, "
				+ "bed.roomName as room_name, "
				+ "bed.`name` as bed_name "
				+ "from pension_deposit deposit "
				+ "LEFT JOIN pension_older older ON older.id = deposit.older_id "
				+ "LEFT JOIN pension_livingrecord record ON record.older_id = older.id "
				+ "LEFT JOIN pension_bed bed ON bed.id = record.bed_id";
	}
	
	public void onDepositSelect(SelectEvent e){
		depositLogs=depositService.selectDepositLogs(selectDepoait.getId());
	}
	
	public void onDepositUnselect(UnselectEvent e){
		depositLogs=new ArrayList<PensionDepositLog>();
	}
	
	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable  listTable = (DataTable) component.findComponent("listForm:list");
		PensionDeposit  arr = (PensionDeposit) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * 缴纳押金前
	 */
	public void beforePay(){
		this.initPayWays();
		this.initDepositLog();
		backFlag=false;//退回押金标识--否
	}
	
	/**
	 * 退回押金前
	 */
	public void beforeBack(){
		RequestContext request = RequestContext.getCurrentInstance();
		this.initPayWays();
		this.initDepositLog();
		backFlag=true;//退回押金标识--是
		//过滤该老人可退的押金列表
		backDepositLogs=depositService.filterBackDepositLogs(depositLogs);
		if(backDepositLogs.size()>0){//该老人有可退押金
			request.addCallbackParam("validate", true);
			selectDepositLog=null;
		}else{//该老人没有可退押金，给用户提示
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("该老人没有可退押金！", "该老人没有可退押金！"));
			request.addCallbackParam("validate", false);
		}
	}
	
	/**
	 * 初始化 一条押金明细
	 */
	public void initDepositLog(){
		addDepositLog=new PensionDepositLog();
		addDepositLog.setDepositId(selectDepoait.getId());
		addDepositLog.setTradeDate(new Date());
		addDepositLog.setTraderId(employee.getId());
		addDepositLog.setTraderName(employee.getName());
		selectDepositTypeId=null;
		for(PensionDicPayway payWay:payWays){
			payWay.setFee(new Float(0));
		}
		totalFees=new Float(0);
		change=new Float(0);
		left=new Float(0);
	}
	
	/**
	 * 获取选中的押金类型对应的押金额
	 */
	public void selectTotalFees(){
		try {
			if(selectDepositTypeId==null){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请先选中押金类型！", "请先选中押金类型！"));
				return ;
			}else{
				//从字典表中获取选中的押金类型
				selectDepositType=depositService.selectDepositType(selectDepositTypeId);
				//由配置文件中获取相应的押金额
				totalFees=selectDepositType.getFee();
				for(PensionDicPayway payWay: payWays){
					payWay.setFee(new Float(0));
				}
				payWays.get(0).setPayFee(totalFees.toString());
				change=new Float(0);
				left=new Float(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//根据押金额，现金，刷卡和转账额，计算找零
	public void calculateChange(){
		Float total = new Float(0);
		for (int i = 0; i < payWays.size(); i++) {
			try{
				String payFeeStr=payWays.get(i).getPayFee()==null||"".equals(payWays.get(i).getPayFee())? "0.0":payWays.get(i).getPayFee();
				Float payFee=new Float(payFeeStr);
				if(payFee.floatValue()<0){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(payWays.get(i).getPaywayName()+" 请输入正数！", payWays.get(i).getPaywayName()+" 请输入正数！"));
					return ;
				}else{
					total += payFee;
					payWays.get(i).setFee(payFee);
				}
			}catch(NumberFormatException e){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(payWays.get(i).getPaywayName()+" 请输入正数！", payWays.get(i).getPaywayName()+" 请输入正数！"));
				return ;
			}
		}
		if(total.floatValue()>totalFees.floatValue()){
			change=total-totalFees;
			left=new Float(0);
		}else if(total.floatValue()==totalFees.floatValue()){
			change=new Float(0);
			left=new Float(0);
		}else{
			change=new Float(0);
			left=totalFees-total;
		}
	}
	
	//点击【确定】，交款前，核对交款额
	public void checkPayData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		Float total = new Float(0);
		for (int i = 0; i < payWays.size(); i++) {
			try{
				String payFeeStr=payWays.get(i).getPayFee()==null||"".equals(payWays.get(i).getPayFee())? "0.0":payWays.get(i).getPayFee();
				Float payFee=new Float(payFeeStr);
				if(payFee.floatValue()<0){
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_ERROR,payWays.get(i).getPaywayName()+" 请输入正数！",payWays.get(i).getPaywayName()+" 请输入正数！");
					request.addCallbackParam("validate", false);
					throw new ValidatorException(message);
				}else{
					total += payFee;
					payWays.get(i).setFee(payFee);
				}
			}catch(NumberFormatException e){
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,payWays.get(i).getPaywayName()+" 请输入正数！",payWays.get(i).getPaywayName()+" 请输入正数！");
				request.addCallbackParam("validate", false);
				throw new ValidatorException(message);
			}
		}
		if(total.floatValue()<totalFees.floatValue()){
			FacesMessage message=null;
			if(backFlag){
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"退费金额不足！","退费金额不足！");
			}else{
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"支付金额不足！","支付金额不足！");
			}
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			if(change.floatValue()!=0){
				for(int i=0;i<payWays.size();i++){
					if(chargePayWay.equals( payWays.get(i).getId())){
						payWays.get(i).setFee(payWays.get(i).getFee().floatValue()-change.floatValue());
					}
				}
			}
			request.addCallbackParam("validate", true);
		}
	}
	
	/**
	 * 交纳押金 或 退回押金
	 */
	public void pay(){
		RequestContext request = RequestContext.getCurrentInstance();
		try{
			if(backFlag){//退回押金
				if(new Float(0).equals(totalFees)){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("请先选中所退押金！", "请先选中所退押金！"));
					request.addCallbackParam("validate", false);
					return ;
				}
				//退返押金时，必须不能多退不能少退
				if(!new Float(0).equals(change)||!new Float(0).equals(left)){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("请核对退款金额，不多退不少退！", "请核对退款金额，不多退不少退！"));
					request.addCallbackParam("validate", false);
					return ;
				}
				//将 该退返押金记录 的押金额置为 负
				addDepositLog.setTradefee(-totalFees);
				//将选中的退返押金状态 置为 已退
				this.filterDepositLogs(backDepositLogId,depositLogs);
				//将 该退返押金记录 新增到该老人的押金明细列表
				depositLogs.add(0, addDepositLog);
				//将老人的押金总额 减去所退押金额
				selectDepoait.setDeposit(selectDepoait.getDeposit()-totalFees);
				//向数据库中保存押金明细
				depositService.saveDepositLog(backDepositLogId,selectDepoait,addDepositLog,payWays,backFlag,employee.getId(),employee.getName());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("退回押金成功！", "退回押金成功！"));
			}else{//交纳押金
				if(selectDepositTypeId==null){
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("请先选中押金类型！", "请先选中押金类型！"));
					request.addCallbackParam("validate", false);
					return ;
				}
				addDepositLog.setDepositTypeId(selectDepositType.getId());
				addDepositLog.setDepositTypeName(selectDepositType.getType());
				addDepositLog.setTradefee(totalFees);
				selectDepoait.setDeposit(selectDepoait.getDeposit()+totalFees);
				Long depositLogId=depositService.saveDepositLog(null,selectDepoait,addDepositLog,payWays,backFlag,employee.getId(),employee.getName());
				addDepositLog.setId(depositLogId);
				addDepositLog.setBackflag(NO_FLAG);
				depositLogs.add(0, addDepositLog);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("缴纳押金成功！", "缴纳押金成功！"));
			}
			request.addCallbackParam("validate", true);
		}catch(Exception e){
			if(backFlag){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("退回押金失败！", "退回押金失败！"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("缴纳押金失败！", "缴纳押金失败！"));
			}
			request.addCallbackParam("validate", false);
		}
	}
	
	/**
	 * 退回押金时，将选中的押金明细置为 已退
	 */
	public void filterDepositLogs(Long depositLogId, List<PensionDepositLog> depositLogs) {
		for(PensionDepositLog depositLog: depositLogs){
			if(depositLogId.equals(depositLog.getId())){
				depositLog.setBackflag(YES_FLAG);
				break;
			}
		}
	}




	/**
	 * 
	 * @param depositLog
	 */
	public void backDepositLog(PensionDepositLog depositLog){
		totalFees=depositLog.getTradefee();
		backDepositLogId=depositLog.getId();
		addDepositLog.setDepositTypeId(depositLog.getDepositTypeId());
		addDepositLog.setDepositTypeName(depositLog.getDepositTypeName());
		payWays.get(0).setFee(totalFees);
	}

	
	public void onSelectDepositLog(SelectEvent e){
		totalFees=selectDepositLog.getTradefee();
		backDepositLogId=selectDepositLog.getId();
		addDepositLog.setDepositTypeId(selectDepositLog.getDepositTypeId());
		addDepositLog.setDepositTypeName(selectDepositLog.getDepositTypeName());
		for(PensionDicPayway payWay: payWays){
			payWay.setFee(new Float(0));
		}
		payWays.get(0).setPayFee(totalFees.toString());
		change=new Float(0);
		left=new Float(0);
	}
	
	public void onUnselectDepositLog(UnselectEvent e){
		totalFees=new Float(0);
		for(PensionDicPayway payWay: payWays){
			payWay.setFee(new Float(0));
		}
		payWays.get(0).setFee(totalFees);
		change=new Float(0);
		left=new Float(0);
	}
	
	
	
	
	
	
	
	



	public DepositService getDepositService() {
		return depositService;
	}




	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}




	public Long getDepositId() {
		return depositId;
	}




	public void setDepositId(Long depositId) {
		this.depositId = depositId;
	}




	public String getOlderName() {
		return olderName;
	}




	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}




	public Integer getStatus() {
		return status;
	}




	public void setStatus(Integer status) {
		this.status = status;
	}




	public Integer getDepositStatus() {
		return depositStatus;
	}




	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}




	public List<PensionDeposit> getDepositList() {
		return depositList;
	}




	public void setDepositList(List<PensionDeposit> depositList) {
		this.depositList = depositList;
	}




	public PensionDeposit getSelectDepoait() {
		return selectDepoait;
	}




	public void setSelectDepoait(PensionDeposit selectDepoait) {
		this.selectDepoait = selectDepoait;
	}




	public List<PensionDepositLog> getDepositLogs() {
		return depositLogs;
	}




	public void setDepositLogs(List<PensionDepositLog> depositLogs) {
		this.depositLogs = depositLogs;
	}




	public PensionDepositLog getAddDepositLog() {
		return addDepositLog;
	}




	public void setAddDepositLog(PensionDepositLog addDepositLog) {
		this.addDepositLog = addDepositLog;
	}




	public List<PensionDicDeposittype> getDepositTypes() {
		return depositTypes;
	}




	public void setDepositTypes(List<PensionDicDeposittype> depositTypes) {
		this.depositTypes = depositTypes;
	}




	public PensionDicDeposittype getSelectDepositType() {
		return selectDepositType;
	}




	public void setSelectDepositType(PensionDicDeposittype selectDepositType) {
		this.selectDepositType = selectDepositType;
	}




	public Long getSelectDepositTypeId() {
		return selectDepositTypeId;
	}




	public void setSelectDepositTypeId(Long selectDepositTypeId) {
		this.selectDepositTypeId = selectDepositTypeId;
	}




	public PensionEmployee getEmployee() {
		return employee;
	}




	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
	}




	public boolean isBackFlag() {
		return backFlag;
	}




	public List<PensionDicPayway> getPayWays() {
		return payWays;
	}


	public void setPayWays(List<PensionDicPayway> payWays) {
		this.payWays = payWays;
	}


	public Long getBackDepositLogId() {
		return backDepositLogId;
	}


	public void setBackDepositLogId(Long backDepositLogId) {
		this.backDepositLogId = backDepositLogId;
	}


	public void setBackFlag(boolean backFlag) {
		this.backFlag = backFlag;
	}




	public Float getTotalFees() {
		return totalFees;
	}




	public void setTotalFees(Float totalFees) {
		this.totalFees = totalFees;
	}



	public Float getChange() {
		return change;
	}




	public void setChange(Float change) {
		this.change = change;
	}




	public Float getLeft() {
		return left;
	}




	public void setLeft(Float left) {
		this.left = left;
	}




	public String getSelectSQL() {
		return selectSQL;
	}




	public void setSelectSQL(String selectSQL) {
		this.selectSQL = selectSQL;
	}




	public List<PensionDepositLog> getBackDepositLogs() {
		return backDepositLogs;
	}




	public void setBackDepositLogs(List<PensionDepositLog> backDepositLogs) {
		this.backDepositLogs = backDepositLogs;
	}




	public PensionDepositLog getSelectDepositLog() {
		return selectDepositLog;
	}




	public void setSelectDepositLog(PensionDepositLog selectDepositLog) {
		this.selectDepositLog = selectDepositLog;
	}


	public Long getDefaultPayway() {
		return defaultPayway;
	}


	public void setDefaultPayway(Long defaultPayway) {
		this.defaultPayway = defaultPayway;
	}


	public Long getChargePayWay() {
		return chargePayWay;
	}


	public void setChargePayWay(Long chargePayWay) {
		this.chargePayWay = chargePayWay;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
