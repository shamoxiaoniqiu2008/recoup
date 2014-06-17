package controller.financeManage;

import java.io.Serializable;
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

import service.financeManage.PayTempPayService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.dictionary.PensionDicPayway;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentdetail;


/**
 * 
 * 临时项目缴费 author:mary.liu
 *
 */

public class PayTempPayController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient PayTempPayService payTempPayService;
	
	
	/**********************  查询   ************************/
	private Integer orderId;
	private String orderName;
	private String orderBedName;
	private Integer paidFlag;
	
	
	
	/**********************  列表   ************************/
	private List<PensionTemppayment> tempPayments; 
	private transient DataTable listTable;
	
	/**********************  新增/修改  ************************/
	private PensionTemppayment selectTempPayment;
	private List<PensionTemppaymentdetail> tempPaymentDetails;
	private PensionEmployee employee;
	
	/**********************  缴费   ************************/
	private List<PensionTemppaymentdetail> addTempPaymentDetails;
	private String olderName;
	private Float totalFees;
	private Float left;
	private Float change;
	private Long defaultPayway;//默认付款方式
	private Long chargePayWay;//默认找零方式
	
	private boolean payFlag;
	
	private List<PensionDicPayway> payWays;
	
	/**********************  SQL   ************************/
	private String selectSQL;
	private String addDetailSQL;
	/**********************  常量   ************************/
	
	private final static Integer PAID_TRUE=1;//已缴费
	private final static Integer PAID_FALSE=2;//未缴费
	
	
	@PostConstruct
	public void init(){
		paidFlag = PAID_FALSE;
		orderName=null;
		this.search();
		this.initSQL();
		this.initPayWays();
		employee=(PensionEmployee) SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
	}

	/**
	 * 从字典表中获取支付方式，现金 支票 转账等
	 */
	public void initPayWays(){
			try {
				defaultPayway=payTempPayService.selectDefaultPayWay();
				chargePayWay=defaultPayway;
				payWays=payTempPayService.selectDicPayWays(defaultPayway);
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(e.getMessage(), e.getMessage()));
			}
		}
	
	public void initSQL(){
		selectSQL = "SELECT o.id as id, " + "o.name as name, "
					+ "o.inputCode as inputCode, " + "IF(o.sex < 2,'男','女') as sex, "
					+ "bu.name as building_name, " + "f.name as floor_name, "
					+ "r.name as room_name, " + "be.name as bed_name "
					+ "from pension_temppayment n,"
					+ "pension_older o, " + "pension_livingrecord l, "
					+ " pension_building bu, " + "pension_floor f, "
					+ "pension_room r, " + "pension_bed be "
					+ "where bu.id=f.build_id  " + "and f.id=r.floor_id "
					+ "and r.id=be.room_id " + "and be.id=l.bed_id "
					+ "and l.older_id=o.id " + "and n.older_id=o.id"
					+ " and o.statuses in (3,4,5) AND n.cleared = 2 GROUP BY o.id";
		
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
		PensionTemppayment  arr = (PensionTemppayment) listTable.getSelection();
		if( arr == null || arr.getId() == null ){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请先选择数据！","请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	

	public void search(){
		if("".equals(orderId)||"".equals(orderName)){
			orderId=null;
		}
		if("".equals(orderName)){
			orderName=null;
		}
		if(new Integer(0).equals(paidFlag)||"".equals(paidFlag)){
			paidFlag=null;
		}
		tempPayments=payTempPayService.search(orderId,orderName,paidFlag,null,null);
	}
	
	
	
/*	public void initAddDetailForm(){
		projectId=null;
		projectName=null;
		projectFees=null;
		projectNum=null;
		projectRemark=null;
	}
*/	
	/**
	 * 点击【缴费】，check选中的记录
	 */
	public void beforePay(){
		RequestContext request = RequestContext.getCurrentInstance();
		//已收费的记录不能重复收费
		if(PAID_TRUE.equals(selectTempPayment.getIspay())){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("该记录已收费，不能重复收费！", "该记录已收费，不能重复收费！"));
			request.addCallbackParam("validate", false);
		}else{
			tempPaymentDetails=payTempPayService.selectTempPaymentDetails(selectTempPayment.getId());
			this.initPayWays();
			olderName=selectTempPayment.getName();
			totalFees=selectTempPayment.getTotalfees();
			change=new Float(0);
			left=new Float(0);
			payFlag=false;
			if(totalFees<0){
				payWays.get(0).setPayFee(Float.toString(-totalFees));
				request.addCallbackParam("validate", "back");
			}else{
				payWays.get(0).setPayFee(totalFees.toString());
				request.addCallbackParam("validate", "pay");
			}
		}
		
	}
	
	
	

	/**
	 * 点击【确定】，确定缴费
	 */
	public void pay(){
		try{
			//缴费
			payTempPayService.pay(selectTempPayment,tempPaymentDetails,payWays,totalFees, employee);
			selectTempPayment.setIspay(PAID_TRUE);
			selectTempPayment.setPayeeId(employee.getId());
			selectTempPayment.setPayeeName(employee.getName());
			selectTempPayment.setPaytime(new Date());
			payFlag=true;
			if(totalFees<0){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("退款成功！", "退款成功！"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("收款成功！", "收款成功！"));
			}
		}catch(Exception e){
			if(totalFees<0){
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("退款时出错！", "退款时出错！"));
			}else{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("收款时出错！", "收款时出错！"));
			}
		}
	}
	
	public void onTempPaymentSelect(SelectEvent e){
		
	}
	
	public void onTempPaymentUnselect(UnselectEvent e){
		
	}
	
	/**
	 * check支付数据
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkPayData(FacesContext context,UIComponent component, Object obj){
		RequestContext request = RequestContext.getCurrentInstance();
		Float total = new Float(0);
		if(totalFees<0){
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
			if(total.floatValue()<-totalFees.floatValue()){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"退款金额不足！","退款金额不足！");
				request.addCallbackParam("validate", false);
				throw new ValidatorException(message);
			}else if(total.floatValue()>-totalFees.floatValue()){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"退款金额过多！","退款金额过多！");
				request.addCallbackParam("validate", false);
				throw new ValidatorException(message);
			}else if(change.floatValue()!=0 || left.floatValue()!=0){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"请核对退款金额，不多退不少退！","请核对退款金额，不多退不少退！");
				request.addCallbackParam("validate", false);
				throw new ValidatorException(message);
			}else{
				request.addCallbackParam("validate", true);
			}
		}else{
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
			if(total<totalFees){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"支付金额不足！","支付金额不足！");
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
	}
	
	public void calculateChange(){
		Float total = new Float(0);
		if(totalFees<0){
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
			if(total.floatValue()<-totalFees.floatValue()){
				change=new Float(0);
				left=-totalFees-total;
			}else if(total.floatValue()==-totalFees.floatValue()){
				change=new Float(0);
				left=new Float(0);
			}else{
				left=new Float(0);
				change=total+totalFees;
			}
		}else{
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
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public PayTempPayService getPayTempPayService() {
		return payTempPayService;
	}

	public void setPayTempPayService(PayTempPayService payTempPayService) {
		this.payTempPayService = payTempPayService;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderBedName() {
		return orderBedName;
	}

	public void setOrderBedName(String orderBedName) {
		this.orderBedName = orderBedName;
	}

	public List<PensionTemppayment> getTempPayments() {
		return tempPayments;
	}

	public void setTempPayments(List<PensionTemppayment> tempPayments) {
		this.tempPayments = tempPayments;
	}

	public DataTable getListTable() {
		return listTable;
	}

	public void setListTable(DataTable listTable) {
		this.listTable = listTable;
	}

	public PensionTemppayment getSelectTempPayment() {
		return selectTempPayment;
	}

	public void setSelectTempPayment(PensionTemppayment selectTempPayment) {
		this.selectTempPayment = selectTempPayment;
	}

	public List<PensionTemppaymentdetail> getTempPaymentDetails() {
		return tempPaymentDetails;
	}

	public void setTempPaymentDetails(
			List<PensionTemppaymentdetail> tempPaymentDetails) {
		this.tempPaymentDetails = tempPaymentDetails;
	}

	

	public List<PensionTemppaymentdetail> getAddTempPaymentDetails() {
		return addTempPaymentDetails;
	}

	public void setAddTempPaymentDetails(
			List<PensionTemppaymentdetail> addTempPaymentDetails) {
		this.addTempPaymentDetails = addTempPaymentDetails;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
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

	public String getSelectSQL() {
		return selectSQL;
	}

	public void setSelectSQL(String selectSQL) {
		this.selectSQL = selectSQL;
	}

	public String getAddDetailSQL() {
		return addDetailSQL;
	}

	public void setAddDetailSQL(String addDetailSQL) {
		this.addDetailSQL = addDetailSQL;
	}

	public Float getLeft() {
		return left;
	}

	public void setLeft(Float left) {
		this.left = left;
	}

	public Integer getPaidFlag() {
		return paidFlag;
	}

	public void setPaidFlag(Integer paidFlag) {
		this.paidFlag = paidFlag;
	}

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
	}

	public boolean isPayFlag() {
		return payFlag;
	}

	public void setPayFlag(boolean payFlag) {
		this.payFlag = payFlag;
	}

	public List<PensionDicPayway> getPayWays() {
		return payWays;
	}

	public void setPayWays(List<PensionDicPayway> payWays) {
		this.payWays = payWays;
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
