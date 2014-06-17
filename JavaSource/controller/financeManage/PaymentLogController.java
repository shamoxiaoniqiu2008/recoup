package controller.financeManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

import service.financeManage.PaymentLogService;
import util.PmsException;
import domain.financeManage.PensionDeposit;
import domain.financeManage.PensionDepositLog;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionNormalpaymentdetail;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentdetail;
import domain.olderManage.PensionOlder;


/**
 * 查看缴费记录
 * @author:mary.liu
 */

public class PaymentLogController implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PaymentLogService paymentLogService;
	
	private String selectSQL;
	
	private Long olderId;
	
	private String olderName;
	
	private Integer status;
	
	private List<PensionOlder> olders;
	
	private List<PensionNormalpayment> normalPayments;
	
	private PensionNormalpayment selectNormal;
	
	private List<PensionNormalpaymentdetail> normalPaymentDetails;
	
	private List<PensionTemppayment> tempPayments;
	
	private PensionTemppayment selectTemp;
	
	private List<PensionTemppaymentdetail> tempPaymentDetails;
	
	private PensionDeposit deposit;
	
	private List<PensionDepositLog> depositLogs;
	
	
	
	@PostConstruct
	public void init(){
		this.search();
		this.initSQL();
	}

	public void initSQL(){
		selectSQL = "select  older.id as olderId,older.`name` as olderName,older.inputCode as inputCode, "
				+ "bed.buildName as buildingName, "
				+ "bed.floorName as floorName, "
				+ "bed.roomName as roomName, "
				+ "bed.`name` as bedName, "
				+ "case older.statuses WHEN 3 then '在院' when 4 then '请假' WHEN 5 then '已离院' else  '已结算' end as status_name, "
				+ "older.statuses as statuses "
				+ "from pension_older older  "
				+ "LEFT JOIN pension_livingrecord record "
				+ "ON older.id = record.older_id "
				+ "LEFT JOIN pension_bed bed  "
				+ "ON bed.id = record.bed_id "
				+ "where older.statuses in (3,4,5,6) "
				+ "and older.cleared = 2";
		//可以查询缴费记录的老人：pension_older 状态为3 在院 4 请假 5 待结算 6 已离院
	}
	
	public void search(){
		if(olderName==null||"".equals(olderName)){
			olderId=null;
		}
		olders=paymentLogService.search(olderId,status);
	}
	

	/**
	 * 根据选中的老人，查询老人的缴费明细
	 * @param older
	 */
	public void selectPaymentDetail(PensionOlder older){
		try {
			//日常缴费
			normalPayments=paymentLogService.selectNormalPayments(older.getId());
			normalPaymentDetails=new ArrayList<PensionNormalpaymentdetail>();
			//临时缴费
			tempPayments=paymentLogService.selectTempPayments(older.getId());
			tempPaymentDetails=new ArrayList<PensionTemppaymentdetail>();
			//押金
			deposit=paymentLogService.selectDeposit(older.getId());
			depositLogs=paymentLogService.selectDepositLogs(deposit.getId());
		} catch (PmsException e) {
			deposit=new PensionDeposit();
			depositLogs=new ArrayList<PensionDepositLog>();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
		}
	}
	
	public void onChangeTab(TabChangeEvent e){
		
	}
	
	public void onSelectNormalPayment(SelectEvent e){
		normalPaymentDetails=paymentLogService.selectNormalDetails(selectNormal.getId());
	}
	
	public void onUnselectNormalPayment(UnselectEvent e){
		normalPaymentDetails=new ArrayList<PensionNormalpaymentdetail>();
	}
	
	public void onSelectTempPayment(SelectEvent e){
		tempPaymentDetails=paymentLogService.selectTempDetails(selectTemp.getId());
	}
	public void onUnselectTempPayment(UnselectEvent e){
		tempPaymentDetails=new ArrayList<PensionTemppaymentdetail>();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public PaymentLogService getPaymentLogService() {
		return paymentLogService;
	}

	public void setPaymentLogService(PaymentLogService paymentLogService) {
		this.paymentLogService = paymentLogService;
	}

	public String getSelectSQL() {
		return selectSQL;
	}

	public void setSelectSQL(String selectSQL) {
		this.selectSQL = selectSQL;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<PensionOlder> getOlders() {
		return olders;
	}

	public void setOlders(List<PensionOlder> olders) {
		this.olders = olders;
	}

	public List<PensionNormalpayment> getNormalPayments() {
		return normalPayments;
	}

	public void setNormalPayments(List<PensionNormalpayment> normalPayments) {
		this.normalPayments = normalPayments;
	}

	public List<PensionNormalpaymentdetail> getNormalPaymentDetails() {
		return normalPaymentDetails;
	}

	public void setNormalPaymentDetails(
			List<PensionNormalpaymentdetail> normalPaymentDetails) {
		this.normalPaymentDetails = normalPaymentDetails;
	}

	public List<PensionTemppayment> getTempPayments() {
		return tempPayments;
	}

	public void setTempPayments(List<PensionTemppayment> tempPayments) {
		this.tempPayments = tempPayments;
	}

	public List<PensionTemppaymentdetail> getTempPaymentDetails() {
		return tempPaymentDetails;
	}

	public void setTempPaymentDetails(
			List<PensionTemppaymentdetail> tempPaymentDetails) {
		this.tempPaymentDetails = tempPaymentDetails;
	}

	public List<PensionDepositLog> getDepositLogs() {
		return depositLogs;
	}

	public void setDepositLogs(List<PensionDepositLog> depositLogs) {
		this.depositLogs = depositLogs;
	}

	public PensionDeposit getDeposit() {
		return deposit;
	}

	public void setDeposit(PensionDeposit deposit) {
		this.deposit = deposit;
	}

	public PensionNormalpayment getSelectNormal() {
		return selectNormal;
	}

	public void setSelectNormal(PensionNormalpayment selectNormal) {
		this.selectNormal = selectNormal;
	}

	public PensionTemppayment getSelectTemp() {
		return selectTemp;
	}

	public void setSelectTemp(PensionTemppayment selectTemp) {
		this.selectTemp = selectTemp;
	}

}
