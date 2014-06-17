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

import service.financeManage.PayDailyPayService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.configureManage.PensionItempurse;
import domain.dictionary.PensionDicPayway;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionNormalpaymentdetail;

/**
 * 
 *
 *
 */

public class PayDailyPayController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient PayDailyPayService paydailyPayService;

	/********************** 查询 ************************/
	private Integer orderId;
	private String orderName;
	private String sex;
	private String orderBedName;
	private Integer paidFlag;

	/********************** 列表 ************************/
	private List<PensionNormalpayment> normalPayments;
	private transient DataTable listTable;

	/********************** 新增/修改 ************************/
	private PensionNormalpayment selectNormalPayment;
	private List<PensionNormalpaymentdetail> normalPaymentDetails;
	private PensionItempurse addItemPurse;
	private Integer projectId;
	private String projectName;
	private Integer projectNum;
	private Float projectFees;
	private String projectRemark;
	private PensionEmployee employee;

	/********************** 缴费 ************************/
	private List<PensionNormalpaymentdetail> addNormalPaymentDetails;
	private String olderName;
	private Float totalFees;
	private Float change;
	private Float left;
	private boolean payFlag;
	private Long defaultPayway;// 默认付款方式
	private Long chargePayWay;// 默认找零方式

	private List<PensionDicPayway> payWays;

	/********************** SQL ************************/
	private String selectSQL;
	/********************** 常量 ************************/

	private final static Integer PAID_TRUE = 1;// 已缴费
	private final static Integer PAID_FLASE = 2;// 未缴费

	@PostConstruct
	public void init() {
		paidFlag = PAID_FLASE;
		this.search();
		this.initSQL();
		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
	}

	// 从字典表中获取支付方式，现金 支票 转账等
	private void initPayWays() {
		try {
			defaultPayway = paydailyPayService.selectDefaultPayWay();
			chargePayWay = defaultPayway;
			payWays = paydailyPayService.selectDicPayWays(defaultPayway);
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
		}
	}

	public void initSQL() {
		selectSQL = "SELECT o.id as id, " + "o.name as name, "
				+ "o.inputCode as inputCode, "
				+ "IF(o.sex < 2,'男','女') as sex, "
				+ "bu.name as building_name, " + "f.name as floor_name, "
				+ "r.name as room_name, " + "be.name as bed_name "
				+ "from pension_normalpayment n," + "pension_older o, "
				+ "pension_livingrecord l, " + " pension_building bu, "
				+ "pension_floor f, " + "pension_room r, " + "pension_bed be "
				+ "where bu.id=f.build_id  " + "and f.id=r.floor_id "
				+ "and r.id=be.room_id " + "and be.id=l.bed_id "
				+ "and l.older_id=o.id " + "and n.older_id=o.id"
				+ " and o.statuses in (3,4,5) GROUP BY o.id";
	}

	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 */
	public void checkSelectData(FacesContext context, UIComponent component,
			Object obj) {
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable listTable = (DataTable) component
				.findComponent("payListForm:list");
		PensionNormalpayment arr = (PensionNormalpayment) listTable
				.getSelection();
		if (arr == null || arr.getId() == null) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "请先选择数据！", "请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		} else {
			request.addCallbackParam("validate", true);
		}
	}

	public void search() {
		if ("".equals(orderId) || "".equals(orderName)) {
			orderId = null;
		}
		if ("".equals(orderName)) {
			orderName = null;
		}
		if (new Integer(0).equals(paidFlag) || "".equals(paidFlag)) {
			paidFlag = null;
		}
		normalPayments = paydailyPayService
				.search(orderId, orderName, paidFlag,null,null);
	}

	/**
	 * 点击 缴费 按钮
	 */
	public void beforePay() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (PAID_TRUE.equals(selectNormalPayment.getIspay())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("该记录已收费，不能重复收费！", "该记录已收费，不能重复收费！"));
			request.addCallbackParam("validate", false);
		} else {
			//查询日常缴费记录的明细列表
			normalPaymentDetails = paydailyPayService
					.selectNormalPaymentDetails(selectNormalPayment.getId());
			this.initPayWays();//初始化支付方式
			olderName = selectNormalPayment.getName();
			totalFees = selectNormalPayment.getTotalfees();
			left = new Float(0);
			change = new Float(0);
			payFlag = false;
			if (totalFees < 0) {
				payWays.get(0).setPayFee(Float.toString(-totalFees));
				request.addCallbackParam("validate", "back");
			} else {
				payWays.get(0).setPayFee(totalFees.toString());
				request.addCallbackParam("validate", "pay");
			}
		}

	}

	/*
	 * public void addDetail(){ PensionNormalpaymentdetail tempDetail=new
	 * PensionNormalpaymentdetail();
	 * tempDetail.setItemsId(projectId.longValue());
	 * tempDetail.setItemName(projectName);
	 * tempDetail.setItemlfees(projectFees); tempDetail.setCleared(PAID_FLASE);
	 * tempDetail.setNotes(projectRemark); tempDetail.setNumber(projectNum);
	 * tempDetail.setOlderId(selectNormalPayment.getOlderId());
	 * tempDetail.setPaymentId(selectNormalPayment.getId());
	 * totalFees=totalFees+projectFees*projectNum;
	 * addNormalPaymentDetails.add(tempDetail); // this.initAddDetailForm(); }
	 */

	/**
	 * 缴费
	 */
	public void pay() {
		try {
			//缴费
			paydailyPayService.pay(selectNormalPayment, normalPaymentDetails,
					payWays, totalFees, employee);
			selectNormalPayment.setIspay(PAID_TRUE);//设置为已收费
			selectNormalPayment.setPayeeId(employee.getId());//收费人
			selectNormalPayment.setPayeeName(employee.getName());
			selectNormalPayment.setPaytime(new Date());
			payFlag = true;
			if (totalFees < 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("退款成功！", "退款成功！"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("收款成功！", "收款成功！"));
			}
		} catch (Exception e) {
			if (totalFees < 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("退款时出错！", "退款时出错！"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("收款时出错！", "收款时出错！"));
			}
		}
	}

	public void onNormalPaymentSelect(SelectEvent e) {

	}

	public void onNormalPaymentUnselect(UnselectEvent e) {

	}

	/**
	 * check 支付数据
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkPayData(FacesContext context, UIComponent component,
			Object obj) {
		RequestContext request = RequestContext.getCurrentInstance();
		Float total = new Float(0);
		if (totalFees < 0) {//退费
			for (int i = 0; i < payWays.size(); i++) {
				try{
					String payFeeStr=payWays.get(i).getPayFee()==null||"".equals(payWays.get(i).getPayFee())? "0.0":payWays.get(i).getPayFee();
					Float payFee=new Float(payFeeStr);
					if(payFee.floatValue()<0){//填写的缴费金额为负
						FacesMessage message = new FacesMessage(
								FacesMessage.SEVERITY_ERROR,payWays.get(i).getPaywayName()+" 请输入正数！",payWays.get(i).getPaywayName()+" 请输入正数！");
						request.addCallbackParam("validate", false);
						throw new ValidatorException(message);
					}else{//累加缴费金额
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
			if (total.floatValue() < -totalFees.floatValue()) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "退款金额不足！", "退款金额不足！");
				request.addCallbackParam("validate", false);
				throw new ValidatorException(message);
			} else if (total.floatValue() > -totalFees.floatValue()) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "退款金额过多！", "退款金额过多！");
				request.addCallbackParam("validate", false);
				throw new ValidatorException(message);
			} else if (change.floatValue() != 0 || left.floatValue() != 0) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "请核对退款金额，不多退不少退！",
						"请核对退款金额，不多退不少退！");
				request.addCallbackParam("validate", false);
				throw new ValidatorException(message);
			} else {

				request.addCallbackParam("validate", true);
			}
		} else {//缴费
			for (int i = 0; i < payWays.size(); i++) {
				try{
					String payFeeStr=payWays.get(i).getPayFee()==null||"".equals(payWays.get(i).getPayFee())? "0.0":payWays.get(i).getPayFee();
					Float payFee=new Float(payFeeStr);
					if(payFee.floatValue()<0){//填写的数据为负
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
			if (total.floatValue() < totalFees.floatValue()) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "支付金额不足！", "支付金额不足！");
				request.addCallbackParam("validate", false);
				throw new ValidatorException(message);
			} else {
				if (change.floatValue() != 0) {
					for (int i = 0; i < payWays.size(); i++) {
						if (chargePayWay.equals(payWays.get(i).getId())) {
							payWays.get(i).setFee(
									payWays.get(i).getFee().floatValue()
											- change.floatValue());
						}
					}
				}
				request.addCallbackParam("validate", true);
			}
		}
	}

	/**
	 * 计算找零
	 */
	public void calculateChange() {
		Float total = new Float(0);
		if (totalFees < 0) {
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
			if (total.floatValue() < -totalFees.floatValue()) {
				change = new Float(0);
				left = -totalFees - total;
			} else if (total.floatValue() == -totalFees.floatValue()) {
				change = new Float(0);
				left = new Float(0);
			} else {
				left = new Float(0);
				change = total + totalFees;
			}
		} else {
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
			if (total.floatValue() > totalFees.floatValue()) {
				change = total - totalFees;
				left = new Float(0);
			} else if (total.floatValue() == totalFees.floatValue()) {
				change = new Float(0);
				left = new Float(0);
			} else {
				change = new Float(0);
				left = totalFees - total;
			}
		}
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

	public List<PensionNormalpayment> getNormalPayments() {
		return normalPayments;
	}

	public void setNormalPayments(List<PensionNormalpayment> normalPayments) {
		this.normalPayments = normalPayments;
	}

	public DataTable getListTable() {
		return listTable;
	}

	public void setListTable(DataTable listTable) {
		this.listTable = listTable;
	}

	public PensionNormalpayment getSelectNormalPayment() {
		return selectNormalPayment;
	}

	public void setSelectNormalPayment(PensionNormalpayment selectNormalPayment) {
		this.selectNormalPayment = selectNormalPayment;
	}

	public List<PensionNormalpaymentdetail> getNormalPaymentDetails() {
		return normalPaymentDetails;
	}

	public void setNormalPaymentDetails(
			List<PensionNormalpaymentdetail> normalPaymentDetails) {
		this.normalPaymentDetails = normalPaymentDetails;
	}

	public PensionItempurse getAddItemPurse() {
		return addItemPurse;
	}

	public void setAddItemPurse(PensionItempurse addItemPurse) {
		this.addItemPurse = addItemPurse;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(Integer projectNum) {
		this.projectNum = projectNum;
	}

	public Float getProjectFees() {
		return projectFees;
	}

	public void setProjectFees(Float projectFees) {
		this.projectFees = projectFees;
	}

	public String getProjectRemark() {
		return projectRemark;
	}

	public void setProjectRemark(String projectRemark) {
		this.projectRemark = projectRemark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * public static Short getPaidTrue() { return PAID_TRUE; }
	 * 
	 * public static Short getPaidFlase() { return PAID_FLASE; }
	 */

	public String getSelectSQL() {
		return selectSQL;
	}

	public void setSelectSQL(String selectSQL) {
		this.selectSQL = selectSQL;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getOrderBedName() {
		return orderBedName;
	}

	public void setOrderBedName(String orderBedName) {
		this.orderBedName = orderBedName;
	}

	public PayDailyPayService getPaydailyPayService() {
		return paydailyPayService;
	}

	public void setPaydailyPayService(PayDailyPayService paydailyPayService) {
		this.paydailyPayService = paydailyPayService;
	}

	public List<PensionNormalpaymentdetail> getAddNormalPaymentDetails() {
		return addNormalPaymentDetails;
	}

	public void setAddNormalPaymentDetails(
			List<PensionNormalpaymentdetail> addNormalPaymentDetails) {
		this.addNormalPaymentDetails = addNormalPaymentDetails;
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

	public static Integer getPaidTrue() {
		return PAID_TRUE;
	}

	public static Integer getPaidFlase() {
		return PAID_FLASE;
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
