package controller.financeManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

import service.financeManage.DailyPayService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.configureManage.PensionItempurse;
import domain.dictionary.PensionDictRefundtype;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionNormalpaymentdetail;
import domain.olderManage.PensionLivingLog;

/**
 * 日常缴费录入 author:mary liu
 * 
 * 
 */

public class DailyPayController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient DailyPayService dailyPayService;

	/********************** 查询 ************************/
	private Integer orderId;// 老人编号
	private String orderName;// 老人姓名
	private String sex;// 老人性别
	private String orderBedName;// 老人床号
	private Integer paidFlag;// 是否缴费标识
	private Date startDate;
	private Date endDate;

	/********************** 列表 ************************/
	private List<PensionNormalpayment> normalPayments;
	private transient DataTable listTable;

	/********************** 新增/修改 ************************/
	private PensionNormalpayment selectNormalPayment;
	private List<PensionNormalpaymentdetail> normalPaymentDetails;
	private PensionItempurse addItemPurse;// 添加的日常缴费明细
	private Long projectId;// 新增的日常缴费 价表编号
	private String projectName;// 明细价表名称
	private String projectNum;// 明细数量String
	private Float num;// 明细数量
	private Float projectFees;// 明细价表价格
	private String projectRemark;// 明细备注
	// 新增缴费明细--开始时间
	private Date payStartDate;
	// 新增缴费明细--结束时间
	private Date payEndDate;

	boolean naturalMonthFlag = false;// 自然月算法

	Boolean backFlag = false;// 退费标记

	PensionNormalpaymentdetail stopDetail;

	private PensionEmployee employee;// 当前用户

	private boolean nameFlag;// 新增/修改标识

	private boolean fixFlag;// 固定缴费标识

	private Long selectRefundTypeId;// 选中的退费类型

	private List<PensionDictRefundtype> refundTypes;// 退费类型列表

	/********************** 固定缴费 ************************/

	private List<PensionLivingLog> livingLogs;

	private List<PensionLivingLog> tempLivingLogs;// 后台临时

	private Date fixPayDate;

	private List<PensionNormalpaymentdetail> paidLivingPayments;

	/********************** SQL ************************/
	private String selectSQL;
	private String addSQL;
	private String addDetailSQL;
	/********************** 常量 ************************/

	private final static Integer PAID_TRUE = 1;// 已缴费
	private final static Integer PAID_FLASE = 2;// 未缴费
	private final static String NO = "否";// 未缴费

	@PostConstruct
	public void init() {
		this.initDate();
		this.search();
		this.initSQL();
		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		//根据系统参数设置，是否启用自然月算法
		naturalMonthFlag = dailyPayService.enableNaturalMonth();
		//初始化退费类型
		this.initRefundTypes();
	}

	/**
	 * 读取退费类型字典表中的退费类型
	 */
	public void initRefundTypes() {
		refundTypes = dailyPayService.selectRefundTypes();
	}

	/**
	 * 将结束日期设置为今天，默认显示一个月
	 */
	public void initDate() {

		Calendar calendar = Calendar.getInstance();
		endDate = new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		endDate = calendar.getTime();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate = calendar.getTime();

	}
	
	
	/**
	 * 清空
	 */
	public void clear(){
		startDate = null;
		endDate = null;
	}

	/**
	 * 初始化SQL语句
	 */
	public void initSQL() {
		selectSQL = "SELECT o.id as id, " + "o.name as name, "
				+ "o.inputCode as inputCode, "
				+ "IF(o.sex > 1,'女','男') as sex, "
				+ "bu.name as building_name, " + "f.name as floor_name, "
				+ "r.name as room_name, " + "be.name as bed_name "
				+ "from pension_normalpayment n," + "pension_older o, "
				+ "pension_livingrecord l, " + " pension_building bu, "
				+ "pension_floor f, " + "pension_room r, " + "pension_bed be "
				+ "where bu.id=f.build_id  " + "and f.id=r.floor_id "
				+ "and r.id=be.room_id " + "and be.id=l.bed_id "
				+ "and l.older_id=o.id " + "and n.older_id=o.id"
				+ " and o.statuses in (3,4,5) GROUP BY o.id";
		// 可以录入日常缴费的老人：pension_older 状态为3 在院 4 请假
		addSQL = "SELECT o.id as id, " + "o.name as name, "
				+ "IF(o.sex > 1,'女','男') as sex,"
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
		addDetailSQL = "select i.id as id, " + "i.itemName as name, "
				+ "i.unit as unit, " + "i.purse as purse, "
				+ "substring(i.notes,1,30) as notes, "
				+ "i.inputCode as inputCode " + "from pension_itempurse i "
				+ "where i.itemType = 1 and i.identity = 1 and i.cleared = 2";// 添加日常缴费项目
	}

	/**
	 * check 常见结果列表dateTable 是否选择了数据，验证选中的数据是否可以操作
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
		PensionNormalpayment arr = (PensionNormalpayment) listTable
				.getSelection();
		if (arr == null || arr.getId() == null) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "请先选择数据！", "请先选择数据！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		} else if (PAID_TRUE.equals(arr.getIspay())) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "该日常缴费记录已收费，不能操作！",
					"该日常缴费记录已收费，不能操作！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		} else if (dailyPayService.checkDeleteDate(arr)) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "该日常缴费记录由固定缴费自动生成，不能操作！",
					"该日常缴费记录由固定缴费自动生成，不能操作！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		} else {
			request.addCallbackParam("validate", true);
		}
	}

	/**
	 * check 常见结果列表dateTable 判断是否选中记录 & 选中的记录能否删除
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkDeleteData(FacesContext context, UIComponent component,
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

	/**
	 * 查询
	 */
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
		Calendar calendar = Calendar.getInstance();
		if (endDate != null) {
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 0);
			endDate = calendar.getTime();
		}
		if (startDate != null) {
			calendar.setTime(startDate);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			startDate = calendar.getTime();
		}
		normalPayments = dailyPayService.search(orderId, orderName, paidFlag,
				startDate, endDate);
		selectNormalPayment = null;
	}

	/**
	 * 点击【新增】，初始化 新增对话框的内容
	 */
	public void add() {
		nameFlag = true;// 姓名输入框可用
		backFlag = false;
		this.initAddDetailForm();
		selectNormalPayment = new PensionNormalpayment();
		selectNormalPayment.setTotalfees(new Float(0));
		selectNormalPayment.setGeneratorId(employee.getId());
		selectNormalPayment.setGeneratorName(employee.getName());
		selectNormalPayment.setGeneratetime(new Date());
		selectNormalPayment.setIspay(PAID_FLASE);
		selectNormalPayment.setCleared(PAID_FLASE);
		selectNormalPayment.setIsclosed(PAID_FLASE);
		selectNormalPayment.setIsCloseStr(NO);
		selectNormalPayment.setIsPayStr(NO);
		normalPaymentDetails = new ArrayList<PensionNormalpaymentdetail>();
	}

	/**
	 * 点击【新增】，新增一条明细
	 */
	public void addDetail() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			num = new Float(projectNum);
			if (num.floatValue() == 0.0) {//check缴费项目数量不能为0
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("缴费项目数量不能为0！", "缴费项目数量不能为0！"));
				request.addCallbackParam("validate", false);
				return;
			} else if (num.compareTo(new Float(0)) < 0
					&& selectRefundTypeId == null) {//如果缴费项目数量 <0 ，则需要指定退费类型
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请选择退费类型！", "请选择退费类型！"));
				request.addCallbackParam("validate", false);
				return;
			} else {
				PensionNormalpaymentdetail tempNormalpaymentdetail = new PensionNormalpaymentdetail();
				tempNormalpaymentdetail.setItemsId(projectId.longValue());
				tempNormalpaymentdetail.setItemName(projectName);
				tempNormalpaymentdetail.setItemlfees(projectFees);
				tempNormalpaymentdetail.setNotes(projectRemark);
				tempNormalpaymentdetail.setNumber(num);
				tempNormalpaymentdetail.setPayeeId(employee.getId());// 明细生成人ID
				tempNormalpaymentdetail.setPayeeName(employee.getName());// 明细生成人
				// tempNormalpaymentdetail.setPaytime(new Date());//明细生成时间
				tempNormalpaymentdetail.setTotalfees(projectFees * num);
				tempNormalpaymentdetail.setStartDate(payStartDate);
				tempNormalpaymentdetail.setEndDate(payEndDate);
				//如果缴费项目数量 <0 ，则需要指定退费类型
				if (num.compareTo(new Float(0)) < 0) {
					tempNormalpaymentdetail.setRefundId(selectRefundTypeId);
					PensionDictRefundtype refundType = dailyPayService
							.selectRefundTypeName(selectRefundTypeId);
					if (refundType != null) {
						tempNormalpaymentdetail.setRefundType(refundType
								.getRefundtypeName());
					}
				}
				normalPaymentDetails.add(tempNormalpaymentdetail);
				// 计算明细的合计数量
				Float totalfees = dailyPayService
						.calculateTotalFees(normalPaymentDetails);
				selectNormalPayment.setTotalfees(totalfees);
				this.initAddDetailForm();
				request.addCallbackParam("validate", true);
			}
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("数量 请输入数字！", "数量 请输入数字！"));
			request.addCallbackParam("validate", false);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("新增缴费明细出错！", "新增缴费明细出错！"));
			request.addCallbackParam("validate", false);
		}

	}

	/**
	 * 初始化新增明细Form
	 */
	public void initAddDetailForm() {
		backFlag = false;
		selectRefundTypeId = null;
		projectId = null;
		projectName = null;
		projectFees = null;
		projectNum = null;
		projectRemark = null;
		payStartDate = new Date();
		payEndDate = null;
	}

	/**
	 * 新增/保存 录入的日常缴费项目
	 */
	public void saveNormalPayment() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (normalPaymentDetails.size() < 1) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("缴费明细条数不能为0！", "缴费明细条数不能为0！"));
				request.addCallbackParam("validate", false);
			} else if (selectNormalPayment.getOlderId() == null
					|| selectNormalPayment.getName() == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请首先指定老人！", "请首先指定老人！"));
				request.addCallbackParam("validate", false);
			} else if (this.checkNormalPaymentDetails(normalPaymentDetails)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请将信息填写完整！", "请将信息填写完整！"));
				request.addCallbackParam("validate", false);
			} else {
				// 如果该记录有ID，则为修改记录
				if (selectNormalPayment.getId() != null) {
					selectNormalPayment.setGeneratorId(employee.getId());
					selectNormalPayment.setGeneratorName(employee.getName());
					selectNormalPayment.setGeneratetime(new Date());
					dailyPayService.updateNormalPayment(selectNormalPayment,
							normalPaymentDetails);
				} else {// 否则，新增该条记录
					Long id = dailyPayService.insertNormalPayment(
							selectNormalPayment, normalPaymentDetails);
					selectNormalPayment.setId(id);// 将新增记录的ID返回到页面对象上
					normalPayments.add(0, selectNormalPayment);// 将新增的记录置顶
				}
				dailyPayService.updateOlderItempurse(normalPaymentDetails);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("保存日常缴费项目成功！", "保存日常缴费项目成功！"));
				request.addCallbackParam("validate", true);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("保存日常缴费项目失败！", "保存日常缴费项目失败！"));
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * 检查列表中的每一项都填写完整
	 * 
	 * @param normalPaymentDetails
	 * @return
	 */
	public boolean checkNormalPaymentDetails(
			List<PensionNormalpaymentdetail> normalPaymentDetails) {
		boolean flag = false;
		for (PensionNormalpaymentdetail normalPaymentDetail : normalPaymentDetails) {
			if (normalPaymentDetail.getNumber() == null
					|| new Float(0).equals(normalPaymentDetail.getNumber())
					|| normalPaymentDetail.getStartDate() == null
					|| normalPaymentDetail.getEndDate() == null) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 点击【修改】按钮
	 */
	public void modify() {
		nameFlag = false;// 姓名输入框不可用
		RequestContext request = RequestContext.getCurrentInstance();
		//已收费的日常缴费项目不能再操作
		if (PAID_TRUE.equals(selectNormalPayment.getIspay())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("该日常缴费记录已收费，不能再修改！", "该日常缴费记录已收费，不能再修改！"));
			request.addCallbackParam("validate", false);
		} else {
			this.initAddDetailForm();
			// 根据日常缴费主记录的主键查询对应的缴费明细
			normalPaymentDetails = dailyPayService
					.selectNormalPaymentDetails(selectNormalPayment.getId());
			//设置主记录的总费用
			selectNormalPayment.setTotalfees(dailyPayService
					.calculateTotalFees(normalPaymentDetails));
			for (PensionNormalpaymentdetail normalPaymentDetail : normalPaymentDetails) {
				normalPaymentDetail.setPaytoDate(normalPaymentDetail
						.getEndDate());
			}
			request.addCallbackParam("validate", true);
		}

	}

	/**
	 * 根据起止日期查询缴费数量
	 */
	public void calPaynumByPayStartDate() {
		if (payStartDate != null && payEndDate != null) {
			Calendar calender = Calendar.getInstance();
			if (payStartDate.equals(payEndDate)) {
				projectNum = "" + 0;
			} else if (payStartDate.after(payEndDate)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("开始日期不能小于结束日期！", "开始日期不能小于结束日期！"));
				return;
			} else {
				calender.setTime(payStartDate);
				calender.set(Calendar.DAY_OF_MONTH,
						calender.get(Calendar.DAY_OF_MONTH) + 1);
				Date calDate = calender.getTime();
				projectNum = dailyPayService.culDisDate(calDate, payEndDate,
						naturalMonthFlag).toString();
				if (backFlag) {//如果是退费，在计算出的数量前加 -
					projectNum = "-" + projectNum;
				}
			}
		}
	}

	/**
	 * 根据起止日期查询缴费数量(应用于明细列表中计算起止日期对应的时间)
	 */
	public void calPaynumByPayStartDate(
			PensionNormalpaymentdetail normalpaymentdetail) {
		//当起至时间都不为空时
		if (normalpaymentdetail.getStartDate() != null
				&& normalpaymentdetail.getEndDate() != null) {
			//如果起止日期是同一天，则数量为0
			if (normalpaymentdetail.getEndDate().equals(
					normalpaymentdetail.getStartDate())
					|| normalpaymentdetail.getStartDate().equals(
							normalpaymentdetail.getEndDate())) {
				normalpaymentdetail.setNumber(new Float(0));
				
			} else if (normalpaymentdetail.getEndDate().before(
					normalpaymentdetail.getStartDate())) {//如果结束日期在起始日期之前
				//将计算数量的起始日期向后推一天
				Calendar calender = Calendar.getInstance();
				calender.setTime(normalpaymentdetail.getEndDate());
				calender.set(Calendar.DAY_OF_MONTH,
						calender.get(Calendar.DAY_OF_MONTH) + 1);
				Date calDate = calender.getTime();
				Float num = dailyPayService.culDisDate(calDate,
						normalpaymentdetail.getStartDate(), naturalMonthFlag);
				//数量为 负
				normalpaymentdetail.setNumber(-num);
			} else {//起始日期在结束日期之前
				//将计算数量的起始日期向后推一天
				Calendar calender = Calendar.getInstance();
				calender.setTime(normalpaymentdetail.getStartDate());
				calender.set(Calendar.DAY_OF_MONTH,
						calender.get(Calendar.DAY_OF_MONTH) + 1);
				Date calDate = calender.getTime();
				Float num = dailyPayService.culDisDate(calDate,
						normalpaymentdetail.getEndDate(), naturalMonthFlag);
				normalpaymentdetail.setNumber(num);
			}
			normalpaymentdetail.setTotalfees(normalpaymentdetail.getItemlfees()
					* normalpaymentdetail.getNumber());
			// 计算明细的合计数量
			Float totalfees = dailyPayService
					.calculateTotalFees(normalPaymentDetails);
			selectNormalPayment.setTotalfees(totalfees);
		}
	}

	/**
	 * 关闭 新增对话框
	 */
	public void closeNormalPayment() {
		// 根据日常缴费主记录的主键查询对应的缴费明细
		normalPaymentDetails = dailyPayService
				.selectNormalPaymentDetails(selectNormalPayment.getId());
		selectNormalPayment.setTotalfees(dailyPayService
				.calculateTotalFees(normalPaymentDetails));
	}

	/**
	 * 点击 删除 按钮
	 */
	public void delete() {
		try {
			if (selectNormalPayment == null
					|| selectNormalPayment.getId() == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请先选择数据！", "请先选择数据！"));
				return;
			}
			if (PAID_TRUE.equals(selectNormalPayment.getIspay())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("该项目已缴费，不能删除！", "该项目已缴费，不能删除！"));
			} else {
				dailyPayService.deleteNormalPaymentDetail(selectNormalPayment
						.getId());
				normalPayments.remove(selectNormalPayment);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("删除日常缴费项目成功！", "删除日常缴费项目成功！"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("删除日常缴费项目失败！", "删除日常缴费项目失败！"));
		}
	}

	public void onNormalPaymentSelect(SelectEvent e) {

	}

	public void onNormalPaymentUnselect(UnselectEvent e) {

	}

	/**
	 * 删除选中的日常缴费明细
	 */
	public void deleteNormalPaymentDetail(
			PensionNormalpaymentdetail normalPaymentDetail) {
		normalPaymentDetails.remove(normalPaymentDetail);// 从页面上删除该条明细
		// 计算明细的合计数量
		Float totalfees = dailyPayService
				.calculateTotalFees(normalPaymentDetails);
		selectNormalPayment.setTotalfees(totalfees);
	}

	/**
	 * 获取老人信息
	 */
	public void selectInfo() {
		selectNormalPayment = dailyPayService.selectInfo(selectNormalPayment
				.getOlderId());
		if (selectNormalPayment == null) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "获取老人信息失败！", "获取老人信息失败！");
			throw new ValidatorException(message);
		}
		selectNormalPayment.setTotalfees(dailyPayService
				.calculateTotalFees(normalPaymentDetails));
		selectNormalPayment.setGeneratorId(employee.getId());
		selectNormalPayment.setGeneratorName(employee.getName());
		selectNormalPayment.setGeneratetime(new Date());
		selectNormalPayment.setIspay(PAID_FLASE);
		selectNormalPayment.setCleared(PAID_FLASE);
		selectNormalPayment.setIsclosed(PAID_FLASE);

		// 提取老人常用收费项
		try {
			normalPaymentDetails = dailyPayService.selectOlderItempurseList(
					selectNormalPayment.getOlderId(), employee,
					naturalMonthFlag);
			selectNormalPayment.setTotalfees(dailyPayService
					.calculateTotalFees(normalPaymentDetails));
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("提取老人常用缴费项目失败！", "提取老人常用缴费项目失败！"));
		}

	}

	/**
	 * 根据数量计算起止日期
	 * 
	 * @param normalPaymentDetail
	 */
	public void calDateByNum(PensionNormalpaymentdetail normalPaymentDetail) {
		normalPaymentDetail.setTotalfees(normalPaymentDetail.getItemlfees()
				* normalPaymentDetail.getNumber());
		// 计算明细的合计数量
		Float totalfees = dailyPayService
				.calculateTotalFees(normalPaymentDetails);
		selectNormalPayment.setTotalfees(totalfees);
	}

	/**
	 * 固定缴费---已弃用
	 */
	public void fixPay() {
		selectNormalPayment = new PensionNormalpayment();
		selectNormalPayment.setTotalfees(new Float(0));
		selectNormalPayment.setGeneratorId(employee.getId());
		selectNormalPayment.setGeneratorName(employee.getName());
		selectNormalPayment.setGeneratetime(new Date());
		selectNormalPayment.setIspay(PAID_FLASE);
		selectNormalPayment.setCleared(PAID_FLASE);
		selectNormalPayment.setIsclosed(PAID_FLASE);
		selectNormalPayment.setIsCloseStr(NO);
		selectNormalPayment.setIsPayStr(NO);
		livingLogs = new ArrayList<PensionLivingLog>();
		fixPayDate = null;
		normalPaymentDetails = new ArrayList<PensionNormalpaymentdetail>();
		paidLivingPayments = new ArrayList<PensionNormalpaymentdetail>();
	}

	/**
	 * 停缴
	 */
	public void stopOlderItempurse(
			PensionNormalpaymentdetail normalPaymentDetail) {
		stopDetail = normalPaymentDetail;
	}

	/**
	 * 确认停缴
	 */
	public void stopOlderItempurse() {
		dailyPayService.stopOlderItempurse(selectNormalPayment.getOlderId(),
				stopDetail.getItemsId(), stopDetail.getId());
		this.deleteNormalPaymentDetail(stopDetail);
	}

	/**
	 * 根据缴费起始日期计算缴费结束日期
	 */
	public void calPayEndDateByPayStartDate() {
		/*
		 * //根据数量和起始日期算结束日期 if(projectNum != null && !projectNum.equals("") &&
		 * !new Float(0).equals(new Float(projectNum)) && payStartDate != null
		 * ){ payEndDate = this.CalEndDate(payStartDate, new Float(projectNum));
		 * }else//根据起止日期算缴费数量
		 */if (payStartDate != null && payEndDate != null) {
			this.calPaynumByPayStartDate();
		}

	}

	/**
	 * 根据起始日期和数量 计算结束日期
	 * 
	 * @param startDate
	 * @param num
	 * @return
	 */
	public Date CalEndDate(Date startDate, Float num) {
		if (startDate != null && num != null) {
			int integer = (int) num.floatValue();
			float decimal = num - integer;
			Calendar calender = Calendar.getInstance();
			calender.setTime(startDate);
			calender.set(Calendar.MONTH, calender.get(Calendar.MONTH) + integer);
			if (num.floatValue() > 0) {
				calender.set(Calendar.DAY_OF_MONTH,
						calender.get(Calendar.DAY_OF_MONTH)
								+ (int) (decimal * 30) - 1);
			} else if (num.floatValue() < 0) {
				calender.set(Calendar.DAY_OF_MONTH,
						calender.get(Calendar.DAY_OF_MONTH)
								+ (int) (decimal * 30) + 1);
			}
			return calender.getTime();
		} else {
			return null;
		}
	}

	/**
	 * 根据缴费起始日期计算缴费结束日期
	 */
	public void calPayEndDateByPayStartDate(
			PensionNormalpaymentdetail normalpaymentDetail) {
		/*
		 * //根据数量和起始日期算结束日期 if((normalpaymentDetail.getNumber() == null ||
		 * normalpaymentDetail.getNumber().equals("") || new Float(0).equals(new
		 * Float(normalpaymentDetail.getNumber()))) &&
		 * normalpaymentDetail.getStartDate() != null ){
		 * normalpaymentDetail.setEndDate
		 * (this.CalEndDate(normalpaymentDetail.getStartDate(), new
		 * Float(normalpaymentDetail.getNumber())));
		 * normalpaymentDetail.setTotalfees(normalpaymentDetail.getItemlfees() *
		 * normalpaymentDetail.getNumber()); }else//根据起止日期算缴费数量
		 */if (normalpaymentDetail.getStartDate() != null
				&& normalpaymentDetail.getEndDate() != null) {
			this.calPaynumByPayStartDate(normalpaymentDetail);
		}
	}

	/**
	 * 保存固定缴费信息
	 */
	/*
	 * public void saveFixPay(){ Date today=new Date(); try{
	 * if(livingLogs.size()<1){
	 * FacesContext.getCurrentInstance().addMessage(null,new
	 * FacesMessage("当前没有入住信息！","当前没有入住信息！")); return; } PensionLivingLog
	 * lastLivingLog = tempLivingLogs.get(livingLogs.size() - 1); Long
	 * fixPayDateFormat = dailyPayService.createCalendar(fixPayDate); Date
	 * lastPayDate = lastLivingLog.getPayToDate(); Date lastUpdateDate =
	 * lastLivingLog.getUpdateTime(); if (lastPayDate != null) { Long
	 * lastPayDateFormat= dailyPayService.createCalendar(lastPayDate); if
	 * (fixPayDateFormat
	 * .compareTo(lastPayDateFormat)<0||fixPayDateFormat.compareTo
	 * (lastPayDateFormat)==0) {
	 * FacesContext.getCurrentInstance().addMessage(null,new
	 * FacesMessage("指定的交款截止日期应大于上次交款截止日期！","指定的交款截止日期应大于上次交款截止日期！")); return; }
	 * } else if (lastPayDate == null) { Long lastUpdateDateFormat =
	 * dailyPayService.createCalendar(lastUpdateDate); if
	 * (fixPayDateFormat.compareTo(lastUpdateDateFormat)<0 ) {
	 * FacesContext.getCurrentInstance().addMessage(null,new
	 * FacesMessage("指定的交款截止日期应大于最后的换床日期！","指定的交款截止日期应大于最后的换床日期！")); return; }
	 * 
	 * } normalPaymentDetails = new ArrayList<PensionNormalpaymentdetail>();
	 * Map<String,List> returnMap = dailyPayService
	 * .createNormalPayment(tempLivingLogs, today, fixPayDate, employee.getId(),
	 * employee.getName()); List<PensionNormalpaymentdetail> fixPaymentDetails =
	 * returnMap.get("NORMALPAYMENTDETAILS");
	 * livingLogs=returnMap.get("LIVINGLOGS");
	 * normalPaymentDetails.addAll(fixPaymentDetails);
	 * tempLivingLogs=dailyPayService
	 * .selectLivingLogInfo(selectNormalPayment.getOlderId()); // 计算明细的合计数量
	 * Float totalfees = dailyPayService
	 * .calculateTotalFees(normalPaymentDetails);
	 * selectNormalPayment.setTotalfees(totalfees);
	 * FacesContext.getCurrentInstance().addMessage(null, new
	 * FacesMessage("指定截止日期成功！", "指定截止日期成功！")); }catch(PmsException e){
	 * FacesContext.getCurrentInstance().addMessage(null, new
	 * FacesMessage("指定截止日期时出错！", "指定截止日期时出错！")); } }
	 */

	public void onChangeTab(TabChangeEvent e) {
		if (livingLogs.size() < 1) {
			normalPaymentDetails = new ArrayList<PensionNormalpaymentdetail>();
		}
	}

	/**
	 * 查找老人的固定缴费信息
	 */
	public void selectFixInfo() {
		this.selectInfo();
		this.selectLivingLogs();
		if (livingLogs.size() < 1) {
			fixFlag = true;
		} else {
			fixFlag = false;
		}
		selectNormalPayment.setTotalfees(new Float(0));
		paidLivingPayments = dailyPayService
				.selectPaidPayment(selectNormalPayment.getOlderId());
	}

	/**
	 * 查找老人的换床记录
	 */
	public void selectLivingLogs() {
		livingLogs = dailyPayService.selectLivingLogInfo(selectNormalPayment
				.getOlderId());
		tempLivingLogs = dailyPayService
				.selectLivingLogInfo(selectNormalPayment.getOlderId());
		if (livingLogs.size() < 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage("该老人还没有指定床位等级和护理等级！",
									"该老人还没有指定床位等级和护理等级！"));
		}
	}

	/**
	 * 保存固定缴费生成的缴费记录
	 */
	public void saveFixNormalPayment() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (normalPaymentDetails.size() < 1) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("生成固定缴费的条数需要大于1！", "生成固定缴费的条数需要大于1！"));
				request.addCallbackParam("validate", false);
				return;
			}
			dailyPayService.saveFixNormalPayment(livingLogs, fixPayDate);
			Long id = dailyPayService.insertNormalPayment(selectNormalPayment,
					normalPaymentDetails);
			selectNormalPayment.setId(id);// 将新增记录的ID返回到页面对象上
			normalPayments.add(0, selectNormalPayment);// 将新增的记录置顶
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("生成固定缴费成功！", "生成固定缴费成功！"));
			request.addCallbackParam("validate", true);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("生成固定缴费失败！", "生成固定缴费失败！"));
			request.addCallbackParam("validate", false);
		}
	}

	public DailyPayService getDailyPayService() {
		return dailyPayService;
	}

	public void setDailyPayService(DailyPayService dailyPayService) {
		this.dailyPayService = dailyPayService;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public static Integer getPaidTrue() {
		return PAID_TRUE;
	}

	public static Integer getPaidFlase() {
		return PAID_FLASE;
	}

	public boolean isNameFlag() {
		return nameFlag;
	}

	public void setNameFlag(boolean nameFlag) {
		this.nameFlag = nameFlag;
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

	public List<PensionLivingLog> getLivingLogs() {
		return livingLogs;
	}

	public void setLivingLogs(List<PensionLivingLog> livingLogs) {
		this.livingLogs = livingLogs;
	}

	public Date getFixPayDate() {
		return fixPayDate;
	}

	public void setFixPayDate(Date fixPayDate) {
		this.fixPayDate = fixPayDate;
	}

	public boolean isFixFlag() {
		return fixFlag;
	}

	public void setFixFlag(boolean fixFlag) {
		this.fixFlag = fixFlag;
	}

	public List<PensionLivingLog> getTempLivingLogs() {
		return tempLivingLogs;
	}

	public void setTempLivingLogs(List<PensionLivingLog> tempLivingLogs) {
		this.tempLivingLogs = tempLivingLogs;
	}

	public List<PensionNormalpaymentdetail> getPaidLivingPayments() {
		return paidLivingPayments;
	}

	public void setPaidLivingPayments(
			List<PensionNormalpaymentdetail> paidLivingPayments) {
		this.paidLivingPayments = paidLivingPayments;
	}

	public Float getNum() {
		return num;
	}

	public void setNum(Float num) {
		this.num = num;
	}

	public List<PensionDictRefundtype> getRefundTypes() {
		return refundTypes;
	}

	public void setRefundTypes(List<PensionDictRefundtype> refundTypes) {
		this.refundTypes = refundTypes;
	}

	public Long getSelectRefundTypeId() {
		return selectRefundTypeId;
	}

	public void setSelectRefundTypeId(Long selectRefundTypeId) {
		this.selectRefundTypeId = selectRefundTypeId;
	}

	public Date getPayStartDate() {
		return payStartDate;
	}

	public void setPayStartDate(Date payStartDate) {
		this.payStartDate = payStartDate;
	}

	public Date getPayEndDate() {
		return payEndDate;
	}

	public void setPayEndDate(Date payEndDate) {
		this.payEndDate = payEndDate;
	}

	public Boolean getBackFlag() {
		return backFlag;
	}

	public void setBackFlag(Boolean backFlag) {
		this.backFlag = backFlag;
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
}
