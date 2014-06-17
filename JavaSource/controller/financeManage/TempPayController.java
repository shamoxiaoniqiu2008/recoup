package controller.financeManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.primefaces.event.UnselectEvent;

import service.financeManage.TempPayService;

import com.centling.his.util.SessionManager;

import domain.dictionary.PensionDictRefundtype;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentdetail;

/**
 * 
 *
 *
 */
public class TempPayController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient TempPayService tempPayService;

	/********************** 查询 ************************/
	private Integer orderId;
	private String orderName;
	private String orderBedName;
	private Integer paidFlag;

	private Long priceTypeId;
	private Long olderId;
	
	private Date searchStartDate;
	private Date searchEndDate;

	/********************** 列表 ************************/
	private List<PensionTemppayment> tempPayments;
	private transient DataTable listTable;

	/********************** 新增/修改 ************************/
	private PensionTemppayment selectTempPayment;
	private List<PensionTemppaymentdetail> tempPaymentDetails;
	private Long projectId;
	private String projectName;
	private String projectNum;
	private Float num;// 明细数量
	private Float projectFees;
	private String projectRemark;
	private Date startDate;
	private Date endDate;
	private PensionEmployee employee;

	private boolean nameFlag;

	private Long selectRefundTypeId;// 选中的退费类型

	private List<PensionDictRefundtype> refundTypes;// 退费类型列表

	/********************** SQL ************************/
	private String selectSQL;
	private String addSQL;
	private String addDetailSQL;
	/********************** 常量 ************************/

	private final static Integer PAID_TRUE = 1;// 已缴费
	private final static Integer PAID_FLASE = 2;// 未缴费

	private final static String NO = "否";

	String tableName = "";
	Long keyId;

	@PostConstruct
	public void init() {
		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);

		// 当由消息进入时，读取预算的编号并显示
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String priceTypeIdStr = paramsMap.get("priceTypeId");
		// Map<String, String> paramsMap1 = FacesContext.getCurrentInstance()
		// .getExternalContext().getRequestParameterMap();
		String olderIdStr = paramsMap.get("olderId");
		tableName = paramsMap.get("tableName");
		String key = paramsMap.get("key");

		if (priceTypeIdStr != null && olderIdStr != null && key != null) {
			try {
				priceTypeId = new Long(priceTypeIdStr);
				olderId = new Long(olderIdStr);
				keyId = new Long(key);
			} catch (NumberFormatException e) {
				priceTypeId = null;
				olderId = null;
				keyId = null;
			}
		} else {
			priceTypeId = null;
			olderId = null;
			keyId = null;
		}
		this.initDate();
		this.initSQL();
		this.initRefundTypes();
		this.search();
	}
	
	/**
	 * 将结束日期设置为今天，默认显示一个月
	 */
	public void initDate(){
		
		Calendar calendar=Calendar.getInstance();
		searchEndDate=new Date();
		calendar.setTime(searchEndDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		searchEndDate=calendar.getTime();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		searchStartDate=calendar.getTime();
  
	}


	/**
	 * check是否由其他部门调用
	 */
	public void checkInit() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (priceTypeId != null && olderId != null) {
			selectTempPayment = new PensionTemppayment();
			tempPaymentDetails = new ArrayList<PensionTemppaymentdetail>();
			selectTempPayment.setOlderId(olderId);
			this.selectInfo();
			request.addCallbackParam("validate", true);
		} else {
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * 初始化退费类型
	 */
	public void initRefundTypes() {
		refundTypes = tempPayService.selectRefundTypes();
	}

	public void initSQL() {
		selectSQL = "SELECT o.id as id, " + "o.name as name, "
				+ "o.inputCode as inputCode, "
				+ "IF(o.sex < 2,'男','女') as sex, "
				+ "bu.name as building_name, " + "f.name as floor_name, "
				+ "r.name as room_name, " + "be.name as bed_name "
				+ "from pension_temppayment n," + "pension_older o, "
				+ "pension_livingrecord l, " + " pension_building bu, "
				+ "pension_floor f, " + "pension_room r, " + "pension_bed be "
				+ "where bu.id=f.build_id  " + "and f.id=r.floor_id "
				+ "and r.id=be.room_id " + "and be.id=l.bed_id "
				+ "and l.older_id=o.id " + "and n.older_id=o.id"
				+ " and o.statuses in (3,4,5) AND n.cleared = 2 GROUP BY o.id";
		addSQL = "SELECT o.id as id, " + "o.name as name, "
				+ "IF(o.sex < 2,'男','女') as sex,"
				+ "bu.name as building_name, " + "f.name as floor_name, "
				+ "r.name as room_name, " + "be.name as bed_name, "
				+ "dn.level as nurse_level, " + "db.level as bed_type, "
				+ "l.visitTime as visit_time, "
				+ "o.inputCode as inputCode,o.sex as sex_input, "
				+ "o.birthday as birthday " + "from pension_older o, "
				+ "pension_livingrecord l, " + "pension_building bu, "
				+ "pension_floor f, " + "pension_room r, " + "pension_bed be, "
				+ "pension_dic_bedtype db, " + "pension_dic_nurse dn "
				+ "where dn.id=l.nurseLevel_id " + "and db.id=l.bedType_id "
				+ "and bu.id=f.build_id  " + "and f.id=r.floor_id "
				+ "and r.id=be.room_id " + "and be.id=l.bed_id "
				+ "and l.older_id=o.id " + "and o.statuses in (3,4,5)";
		if (priceTypeId == null) {
			addDetailSQL = "select i.id as id, " + "i.itemName as name, "
					+ "i.unit as unit, " + "i.purse as purse, "
					+ "i.notes as notes, " + "i.inputCode as inputCode "
					+ "from pension_itempurse i " + "where i.itemType = 2  "
					+ "and i.identity = 1 and i.cleared = 2";// 添加临时项目
		} else {
			addDetailSQL = "select i.id as id, " + "i.itemName as name, "
					+ "i.unit as unit, " + "i.purse as purse, "
					+ "i.notes as notes, " + "i.inputCode as inputCode "
					+ "from pension_itempurse i " + "where i.itemType = 2  "
					+ "and i.identity = 1 and i.cleared = 2 "// 添加临时项目
					+ "and i.pricetype_id = " + priceTypeId;// 如果是别的功能页面调用，则只显示相应类别的价表
		}
	}

	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectData(FacesContext context, UIComponent component,
			Object obj) {
		RequestContext request = RequestContext.getCurrentInstance();
		DataTable listTable = (DataTable) component
				.findComponent("payListForm:list");
		PensionTemppayment arr = (PensionTemppayment) listTable.getSelection();
		if (arr == null || arr.getId() == null) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "请先选择数据！", "请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		} else if (PAID_TRUE.equals(arr.getIspay())) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "该临时缴费记录已收费，不能操作！",
					"该临时缴费记录已收费，不能操作！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		} else if (tempPayService.checkDeleteDate(arr)) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "该临时缴费记录由零点餐自动生成，不能操作！",
					"该临时缴费记录由零点餐自动生成，不能操作！");
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
		Calendar calendar=Calendar.getInstance();
		if(searchEndDate != null){
			calendar.setTime(searchEndDate);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 0);
			searchEndDate=calendar.getTime();
		}
		if(searchStartDate != null){
			calendar.setTime(searchStartDate);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			searchStartDate=calendar.getTime();
		}
		tempPayments = tempPayService.search(orderId, orderName, paidFlag,searchStartDate,searchEndDate);
		selectTempPayment = null;
	}

	/**
	 * 点击【新增】，初始化新增对话框信息
	 */
	public void add() {
		nameFlag = true;// 姓名输入框可用
		this.initAddDetailForm();
		selectTempPayment = new PensionTemppayment();
		selectTempPayment.setTotalfees(new Float(0));
		selectTempPayment.setGeneratorId(employee.getId());
		selectTempPayment.setGeneratorName(employee.getName());
		selectTempPayment.setGeneratetime(new Date());
		selectTempPayment.setIspay(PAID_FLASE);
		selectTempPayment.setCleared(PAID_FLASE);
		selectTempPayment.setIsclosed(PAID_FLASE);
		selectTempPayment.setIsCloseStr(NO);
		selectTempPayment.setIsPayStr(NO);
		tempPaymentDetails = new ArrayList<PensionTemppaymentdetail>();
	}

	/**
	 * 新增一条临时缴费明细
	 */
	public void addDetail() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			num = new Float(projectNum);
			if (num.floatValue() == 0.0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("缴费项目数量不能为0！", "缴费项目数量不能为0！"));
				request.addCallbackParam("validate", false);
				return;
			} else if (num.compareTo(new Float(0)) < 0
					&& selectRefundTypeId == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请选择退费类型！", "请选择退费类型！"));
				request.addCallbackParam("validate", false);
				return;
			} else {
				PensionTemppaymentdetail temp = new PensionTemppaymentdetail();
				temp.setItemsId(projectId);
				temp.setItemName(projectName);
				temp.setItemlfees(projectFees);
				temp.setNotes(projectRemark);
				temp.setNumber(num);
				temp.setRecordId(employee.getId());
				temp.setRecordName(employee.getName());
				temp.setRecordtime(new Date());
				temp.setTotalfees(projectFees * num);
				temp.setSource(PAID_TRUE);// 计费来源 价表的临时项目
				if (num.compareTo(new Float(0)) < 0) {
					temp.setRefundId(selectRefundTypeId);
					PensionDictRefundtype refundType = tempPayService
							.selectRefundTypeName(selectRefundTypeId);
					if (refundType != null) {
						temp.setRefundType(refundType.getRefundtypeName());
					}
				}
				tempPaymentDetails.add(temp);
				Float totalfees = tempPayService
						.calculateTotalFees(tempPaymentDetails);
				selectTempPayment.setTotalfees(totalfees);
				this.initAddDetailForm();
				request.addCallbackParam("validate", true);
			}

		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("数量 请输入数字！", "数量 请输入数字！"));
			request.addCallbackParam("validate", false);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("新增缴费明细失败！", "新增缴费明细失败！"));
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * 初始化新增明细的form
	 */
	public void initAddDetailForm() {
		selectRefundTypeId = null;
		projectId = null;
		projectName = null;
		projectFees = null;
		projectNum = null;
		projectRemark = null;
	}

	/**
	 * 保存临时缴费记录
	 */
	public void saveTempPayment() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (selectTempPayment.getStarttime().compareTo(
					selectTempPayment.getEndtime()) > 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("结束时间需要大于开始时间！", "结束时间需要大于开始时间！"));
				request.addCallbackParam("validate", false);
				return;
			}
			if (tempPaymentDetails.size() < 1) {
				selectTempPayment.setTotalfees(tempPayService.calculateTotalFees(tempPaymentDetails));
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("缴费明细条数不能为0！", "缴费明细条数不能为0！"));
				request.addCallbackParam("validate", false);
			} else if (selectTempPayment.getOlderId() == null
					|| selectTempPayment.getName() == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请首先指定老人！", "请首先指定老人！"));
				request.addCallbackParam("validate", false);
			} else {
				if (selectTempPayment.getId() != null) {
					selectTempPayment.setGeneratorId(employee.getId());
					selectTempPayment.setGeneratorName(employee.getName());
					selectTempPayment.setGeneratetime(new Date());
					tempPayService.updateTempPayment(selectTempPayment,
							tempPaymentDetails);
				} else {
					Long id = tempPayService.insertTempPayment(
							selectTempPayment, tempPaymentDetails, tableName,
							keyId, employee);
					selectTempPayment.setId(id);
					tempPayments.add(0, selectTempPayment);
				}
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("保存临时缴费项目成功！", "保存临时缴费项目成功！"));
				request.addCallbackParam("validate", true);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("保存临时缴费项目失败！", "保存临时缴费项目失败！"));
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * 点击【修改】
	 */
	public void modify() {
		nameFlag = false;// 姓名输入框不可用
		RequestContext request = RequestContext.getCurrentInstance();
		if (PAID_TRUE.equals(selectTempPayment.getIspay())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("该临时缴费记录已收费，不能再修改！", "该临时缴费记录已收费，不能再修改！"));
			request.addCallbackParam("validate", false);
		} else {
			this.initAddDetailForm();
			tempPaymentDetails = tempPayService
					.selectTempPaymentDetails(selectTempPayment.getId());
			request.addCallbackParam("validate", true);
		}

	}

	/**
	 * 点击【删除】
	 */
	public void delete() {
		try {
			if (selectTempPayment == null || selectTempPayment.getId() == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请先选择数据！", "请先选择数据！"));
				return;
			}
			if (PAID_TRUE.equals(selectTempPayment.getIspay())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("该项目已缴费，不能删除！", "该项目已缴费，不能删除！"));
			} else {
				tempPayService.deleteTempPaymentDetail(selectTempPayment
						.getId());
				tempPayments.remove(selectTempPayment);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("删除日常缴费项目成功！", "删除日常缴费项目成功！"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("删除日常缴费项目失败！", "删除日常缴费项目失败！"));
		}
	}

	public void onTempPaymentSelect(SelectEvent e) {

	}

	public void onTempPaymentUnselect(UnselectEvent e) {

	}

	/**
	 * 删除一条临时缴费明细
	 * @param tempPaymentDetail
	 */
	public void deleteTempPaymentDetail(
			PensionTemppaymentdetail tempPaymentDetail) {
		tempPaymentDetails.remove(tempPaymentDetail);
		Float totalfees = tempPayService.calculateTotalFees(tempPaymentDetails);
		selectTempPayment.setTotalfees(totalfees);
	}

	/**
	 * 查询老人信息
	 */
	public void selectInfo() {
		selectTempPayment = tempPayService.selectInfo(selectTempPayment
				.getOlderId());
		if (selectTempPayment == null) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "获取老人信息失败！", "获取老人信息失败！");
			throw new ValidatorException(message);
		}
		selectTempPayment.setTotalfees(tempPayService
				.calculateTotalFees(tempPaymentDetails));
		selectTempPayment.setGeneratorId(employee.getId());
		selectTempPayment.setGeneratorName(employee.getName());
		selectTempPayment.setGeneratetime(new Date());
		selectTempPayment.setIspay(PAID_FLASE);
		selectTempPayment.setCleared(PAID_FLASE);
		selectTempPayment.setIsclosed(PAID_FLASE);

	}

	public TempPayService getTempPayService() {
		return tempPayService;
	}

	public void setTempPayService(TempPayService tempPayService) {
		this.tempPayService = tempPayService;
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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public boolean isNameFlag() {
		return nameFlag;
	}

	public void setNameFlag(boolean nameFlag) {
		this.nameFlag = nameFlag;
	}

	public String getSelectSQL() {
		return selectSQL;
	}

	public void setSelectSQL(String selectSQL) {
		this.selectSQL = selectSQL;
	}

	public String getAddSQL() {
		return addSQL;
	}

	public void setAddSQL(String addSQL) {
		this.addSQL = addSQL;
	}

	public String getAddDetailSQL() {
		return addDetailSQL;
	}

	public void setAddDetailSQL(String addDetailSQL) {
		this.addDetailSQL = addDetailSQL;
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

	public String getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(String projectNum) {
		this.projectNum = projectNum;
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

	public Long getPriceTypeId() {
		return priceTypeId;
	}

	public void setPriceTypeId(Long priceTypeId) {
		this.priceTypeId = priceTypeId;
	}

	public Long getSelectRefundTypeId() {
		return selectRefundTypeId;
	}

	public void setSelectRefundTypeId(Long selectRefundTypeId) {
		this.selectRefundTypeId = selectRefundTypeId;
	}

	public List<PensionDictRefundtype> getRefundTypes() {
		return refundTypes;
	}

	public void setRefundTypes(List<PensionDictRefundtype> refundTypes) {
		this.refundTypes = refundTypes;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getKeyId() {
		return keyId;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	public Date getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(Date searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public Date getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

}
