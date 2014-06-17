package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import service.dictionary.DicEventService;
import service.olderManage.DepartApproveService;
import service.olderManage.DepartApprovenofityService;
import service.olderManage.DepartRecordService;
import service.olderManage.DepartRegisterService;
import service.olderManage.OlderService;
import service.system.MessageMessage;
import util.DateUtil;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

import com.centling.his.util.SessionManager;

import domain.dictionary.PensionDicEvent;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionTemppayment;
import domain.logisticsManage.PensionThingdamage;
import domain.olderManage.PensionDepartapprove;
import domain.olderManage.PensionDepartnotify;
import domain.olderManage.PensionDepartrecord;
import domain.olderManage.PensionDepartregister;
import domain.system.PensionSysUser;

/**
 * 离院管理模块 bill
 * 
 */

@ManagedBean(name = "departRegisterController")
@ViewScoped
public class DepartRegisterController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient DepartRegisterService departRegisterService;

	private transient DepartApproveService departApproveService;

	private transient DepartApprovenofityService departApprovenofityService;

	private transient DicEventService dicEventService;

	/********************** 查询 ************************/

	private Long olderId;
	private String olderName = "";
	private String bedName;

	private String selectSQL;
	private String olderNameSql;
	private Long departID;
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;

	private Integer APPLAY = 1;// 离院申请
	private Integer SENDED = 2;// 发送记录
	private Integer PRE_LEAVE = 3;// 审批中
	private Integer HAVE_LEAVE = 4;// 离院
	private Integer LEAVE_COMFIRM = 5;// 待离院
	private Integer LEAVE_FANCAIL = 6;// 待结算
	/**
	 * 设置按钮的可用性
	 */
	private boolean disabledFlag;
	/**
	 * 当前操作员工
	 */
	private PensionEmployee employee;
	private boolean disabledFlagForCancel;
	/**
	 * 审批部门是否可见，1是2否
	 */
	private boolean invilidFrom;
	/**
	 * 处理完成是否可见，1是2否
	 */
	private boolean checkDisableFrom;
	/**
	 * 确认离院是否可见，1是2否
	 */
	private boolean departComfirmDisableFrom;

	private transient DataTable listTable;
	/**
	 * 离院申请form value
	 */
	private List<PensionDepartregister> pensionDepartregisters;
	/**
	 * 离院审批form value
	 */
	private List<PensionDepartregister> pensionDepartapprove;
	/**
	 * 离院检查form value
	 */
	private List<PensionDepartregister> pensionDepartcheck;
	/**
	 * 离院审批部门form value
	 */
	private List<PensionDepartapprove> pensionDepartapproveList;
	/**
	 * 离院检查部门form value
	 */
	private List<PensionDepartnotify> pensionDepartnofityList;
	/**
	 * 离院检查部门form value
	 */
	private List<PensionDepartnotify> pensionDepartnofityListCheck;
	/**
	 * 离院检查选择单条记录
	 */
	private PensionDepartnotify selectPensionDepartnofity = new PensionDepartnotify();

	private PensionDepartregister selectPensionDepartregister = new PensionDepartregister();

	private PensionDepartregister selectPensionDepartapprove = new PensionDepartregister();

	private PensionDepartregister selectPensionDepartcheck = new PensionDepartregister();

	private PensionDepartregister insertedRow = new PensionDepartregister();

	private List<PensionNormalpayment> paidNormals;

	private List<PensionTemppayment> paidTemps;

	private List<PensionNormalpayment> unPaidNormals;

	private List<PensionTemppayment> unPaidTemps;

	private List<PensionThingdamage> pensionThingDamages;

	private PensionSysUser sysUser;

	private PensionDepartapprove approveRecord;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private DepartRecordService recordService;
	@Autowired
	private OlderService olderService;

	@PostConstruct
	public void init() {
		// 获取消息源发给本页面的参数
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String departId = paramsMap.get("departId");
		if (departId != null) {
			this.departID = Long.valueOf(departId);
		} else {
			this.departID = null;
		}
		// 根据参数 和其余默认的查询条件查询出所有的离院申请
		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		clearSelectForm();
		startDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
		endDate = DateUtil.getBeforeDay(startDate, -7);
		this.search();
		this.initSQL();
		disabledFlag = true;
		disabledFlagForCancel = true;
		invilidFrom = true;
		checkDisableFrom = true;
		departComfirmDisableFrom = true;
		this.departID = null;
	}

	public void initSQL() {
		selectSQL = "SELECT o.id as id,   o.name as name, "
				+ "o.inputCode as inputCode,   IF(o.sex > 1,'女','男') as sex,  "
				+ "bu.name as building_name,   f.name as floor_name,  "
				+ "r.name as room_name,   be.name as bed_name,  "
				+ "IF(n.isAgree > 1,'否','是') as isAgree   "
				+ "from pension_departregister n, "
				+ "pension_older o,   pension_livingrecord l,  "
				+ "pension_building bu,   pension_floor f,  "
				+ "pension_room r,   pension_bed be  "
				+ "where bu.id=f.build_id    and f.id=r.floor_id  "
				+ "and r.id=be.room_id   and be.id=l.bed_id  "
				+ "and l.older_id=o.id   and n.older_id=o.id "
				+ "and n.isAgree = 1";
		olderNameSql = "select po.name	as oldName,"
				+ "pbuilding.name	  as buildName," + "pf.name		  as floorName,"
				+ "pr.name		  as roomName," + "pb.name		  as bedName,"
				+ "IF(po.sex > 1,'女','男') as sex,"
				+ "po.id       		  as olderId,"
				+ "po.inputCode		  as inputCode,"
				+ "pf.id              as floorId,"
				+ "pr.id              as roomId,"
				+ "pb.id              as bedId,"
				+ "pbuilding.id       as buildId" + " from pension_older po"
				+ " join pension_livingRecord plr "
				+ "on po.id = plr.older_id " + "join pension_bed pb "
				+ "on plr.bed_id = pb.id " + "join pension_room pr "
				+ "on pb.room_id = pr.id " + "join pension_floor pf "
				+ "on pr.floor_id = pf.id "
				+ "join pension_building pbuilding "
				+ "on pf.build_id = pbuilding.id "
				+ "where po.cleared = 2 and po.statuses in(3,4)";
	}

	/**
	 * 删除申请记录
	 */
	public void deleteDepartRegisterRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "删除成功";
		try {
			departRegisterService
					.deleteDepartRegisterRecord(selectPensionDepartregister);
			this.search();
		} catch (DataAccessException e) {

			info = "删除操作写入数据库出现异常！";
		} catch (Exception e) {

			info = "出现未知异常，请联系系统管理员！";

		}
		clearInsertForm();
		if (info.equals("删除成功")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);
			context.addMessage(null, message);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);
			context.addMessage(null, message);
		}

	}

	/**
	 * 录入申请记录
	 */
	public void insertDepartRegisterRecord() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		try {
			if (insertedRow.getLeavetime().before(new Date())) {
				info = "离院日期必须大于当前日期！";
			} else {
				departRegisterService.insertDepartRegisterRecord(insertedRow);
				clearInsertForm();
				this.search();
			}
		} catch (DataAccessException e) {
			info = "添加操作写入数据库出现异常！";
		} catch (Exception e) {
			info = "出现未知异常，请联系系统管理员！";
		}
		if (info.equals("添加成功")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);
			context.addMessage(null, message);
			requestContext.addCallbackParam("add", true);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);
			context.addMessage(null, message);
			requestContext.addCallbackParam("add", false);
		}

	}

	/**
	 * 录入并发送申请
	 * 
	 * @throws PmsException
	 */
	public void saveAndSendRegister() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		String info = "添加并发送成功";
		try {
			if (insertedRow.getLeavetime().before(new Date())) {
				info = "离院日期必须大于当前日期！";
			} else {
				departRegisterService.insertDepartRegisterRecord(insertedRow);
				Long id = insertedRow.getId();
				sentMessage(departRegisterService.search(null, null, null,
						APPLAY, id).get(0));
				clearInsertForm();
				this.search();
			}
		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";
		} catch (Exception e) {

			info = "出现未知异常，请联系系统管理员！";

		}
		if (info.equals("添加并发送成功")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);
			context.addMessage(null, message);
			requestContext.addCallbackParam("add", true);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);
			context.addMessage(null, message);
			requestContext.addCallbackParam("add", false);
		}

	}

	/**
	 * 更新申请记录
	 */
	public void updateDepartRegisterRecord() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "更新成功";
		try {
			if (selectPensionDepartregister.getLeavetime().before(new Date())) {
				info = "离院日期必须大于当前日期！";
			} else {
				departRegisterService
						.updateDepartRegisterRecord(selectPensionDepartregister);
				clearInsertForm();
				this.search();
			}
		} catch (DataAccessException e) {

			info = "更新操作出现异常！";
		} catch (Exception e) {

			info = "出现未知异常，请联系系统管理员！";

		}
		if (info.equals("更新成功")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);
			context.addMessage(null, message);
			requestContext.addCallbackParam("add", true);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);
			context.addMessage(null, message);
			requestContext.addCallbackParam("add", false);
		}

	}

	/**
	 * 更新并发送申请
	 * 
	 * @throws PmsException
	 */
	public void updateAndSendRegister() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		try {
			if (selectPensionDepartregister.getLeavetime().before(new Date())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"离院日期必须大于当前日期！", ""));
				requestContext.addCallbackParam("add", false);
			} else {
				departRegisterService
						.updateDepartRegisterRecord(selectPensionDepartregister);
				sentMessage(selectPensionDepartregister);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"申请更新并提交成功", ""));
				clearInsertForm();
				requestContext.addCallbackParam("add", true);
			}
		} catch (PmsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 清空insertFrom
	 */
	public void clearInsertForm() {
		insertedRow = new PensionDepartregister();
	}

	/**
	 * 选中一条记录，设置按钮可用状态
	 */
	public void setEnableFlag(SelectEvent event) {
		// 修改、删除按钮状态
		disabledFlag = true;
		disabledFlagForCancel = true;
		invilidFrom = true;
		checkDisableFrom = true;
		departComfirmDisableFrom = false;
		if (selectPensionDepartregister.getIsagree() == APPLAY) {
			disabledFlag = false;
			disabledFlagForCancel = true;
		}
		if (selectPensionDepartregister.getIsagree() == SENDED) {
			disabledFlagForCancel = false;
			disabledFlag = true;
		}
		if (selectPensionDepartapprove.getIsagree() == SENDED
				|| selectPensionDepartapprove.getIsagree() == PRE_LEAVE) {
			invilidFrom = false;
			pensionDepartapproveList = departApproveService
					.selectDepartApprove(selectPensionDepartapprove.getId());
		}

		if (selectPensionDepartcheck.getId() != null) {
			pensionDepartnofityList = departApprovenofityService
					.selectDepartNotify(selectPensionDepartcheck.getId(),
							employee.getDeptId());
			pensionDepartnofityListCheck = departApprovenofityService
					.selectDepartNotify(selectPensionDepartcheck.getId(), null);
		}
		if (selectPensionDepartnofity.getHandleresult() != null
				&& selectPensionDepartnofity.getHandleresult() == 2) {
			checkDisableFrom = false;
		}

	}

	/**
	 * 选择事件，处理完成
	 */
	public void checkComfirm() {
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "处理成功";
		try {
			selectPensionDepartnofity.setHandleresult(1);
			selectPensionDepartnofity.setHandetime(new Date());
			selectPensionDepartnofity.setHandlerId(employee.getId().intValue());
			departApprovenofityService
					.updateDepartApproveRecord(selectPensionDepartnofity);
			pensionDepartnofityList = departApprovenofityService
					.selectDepartNotify(selectPensionDepartcheck.getId(),
							employee.getDeptId());
			// 当处理完成所有事件之后，将消息删除
			int i;
			for (i = 0; i < pensionDepartnofityList.size(); i++) {
				if (pensionDepartnofityList.get(i).getHandleresult() == 1)
					continue;
				else
					break;
			}
			if (i >= pensionDepartnofityList.size()) {
				String typeIdStr = JavaConfig
						.fetchProperty("DepartRegisterController.checkId");
				Long messagetypeId = Long.valueOf(typeIdStr);
				messageMessage.updateMessageProcessor(employee.getId(),
						messagetypeId, "pension_departnotify",
						selectPensionDepartcheck.getId(), employee.getDeptId());
			}
			checkDisableFrom = true;
		} catch (DataAccessException e) {
			info = "更新操作出现异常！";
		} catch (Exception e) {
			info = "出现未知异常，请联系系统管理员！";
		}
		if (info.equals("处理成功")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);
			context.addMessage(null, message);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);
			context.addMessage(null, message);
		}

	}

	/**
	 * 未选中一条记录，设置按钮可用状态
	 */
	public void setUnableFlag(UnselectEvent event) {
		checkDisableFrom = true;
		disabledFlag = true;
		disabledFlagForCancel = true;
		invilidFrom = true;
		departComfirmDisableFrom = true;
	}

	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		olderId = null;
		olderName = "";
	}

	/**
	 * 发送申请
	 * 
	 * @throws PmsException
	 */
	public void sendRegister() {
		try {
			sentMessage(selectPensionDepartregister);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "发送申请成功", ""));
		} catch (PmsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送通知
	 * 
	 * @throws PmsException
	 */
	public void sentMessage(PensionDepartregister departregister)
			throws PmsException {
		String messageContent = departregister.getName() + "老人离院申请！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("DepartRegisterController.approveId");
		String typeIdStrComfirm = JavaConfig
				.fetchProperty("DepartRegisterController.approveComfirmId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		Long messagetypeIdComfirm = Long.valueOf(typeIdStrComfirm);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?departId=" + departregister.getId();
		String urlcomfirm;

		urlcomfirm = messageMessage.selectMessageTypeUrl(messagetypeIdComfirm);
		urlcomfirm = urlcomfirm + "?departId=" + departregister.getId();

		String depart_dpt_id = systemConfig.selectProperty("DEPART_DPT_ID");
		String depart_emp_id = systemConfig.selectProperty("DEPART_EMP_ID");
		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> userIdList = new ArrayList<Long>();

		List<Long> dptIdListComfirm = new ArrayList<Long>();
		List<Long> empIdListComfirm = new ArrayList<Long>();

		if (depart_emp_id != null && !depart_emp_id.isEmpty()) {
			String[] budgetEmpId = depart_emp_id.split(",");
			for (int i = 0; i < budgetEmpId.length; i++) {
				if (i == 0) {
					empIdListComfirm.add(Long.valueOf(budgetEmpId[i]));
				} else {
					userIdList.add(Long.valueOf(budgetEmpId[i]));
				}
			}
		} else if (depart_dpt_id != null && !depart_dpt_id.isEmpty()) {
			String[] budgetDptId = depart_dpt_id.split(",");
			for (int i = 0; i < budgetDptId.length; i++) {
				if (i == 0)
					dptIdListComfirm.add(Long.valueOf(budgetDptId[i]));
				else
					dptIdList.add(Long.valueOf(budgetDptId[i]));
			}
		}

		String messagename = "【" + departregister.getName() + "】离院申请";

		messageMessage.sendMessage(messagetypeId, dptIdList, userIdList,
				messagename, messageContent, url, "pension_departregister",
				departregister.getId());
		messageMessage.sendMessage(messagetypeIdComfirm, dptIdListComfirm,
				empIdListComfirm, messagename, messageContent, urlcomfirm,
				"pension_departregister", departregister.getId());
		try {
			if (empIdListComfirm != null && empIdListComfirm.size() > 0) {
				userIdList.add(empIdListComfirm.get(0));
				dptIdList = departRegisterService.selectDeptIdList(userIdList);
			} else {
				dptIdList.add(dptIdListComfirm.get(0));
			}
			departregister.setIsagree(SENDED);
			departRegisterService.updateDepartRegisterRecord(departregister);
			for (int i = 0; i < dptIdList.size(); i++) {
				PensionDepartapprove departapprove = new PensionDepartapprove();
				departapprove.setDepartId(departregister.getId());
				departapprove.setDeptId((long) dptIdList.get(i));
				departApproveService.insertDepartApproveRecord(departapprove);
			}
			this.search();
		} catch (DataAccessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		}
	}

	/**
	 * 同意离院
	 * 
	 * 
	 */
	public int agreeDepart() {
		int i;
		pensionDepartapproveList = departApproveService
				.selectDepartApprove(selectPensionDepartapprove.getId());
		for (i = 0; i < pensionDepartapproveList.size(); i++) {
			PensionDepartapprove departapprove = pensionDepartapproveList
					.get(i);
			PensionDepartapprove departapproveComfirm = pensionDepartapproveList
					.get(pensionDepartapproveList.size() - 1);
			if (departapproveComfirm.getApproveresult() != 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"审批已经通过，你不能再做审核，请刷新记录！", ""));
				return -1;
			}
			if (departapprove.getDeptId() == employee.getDeptId()) {
				try {
					departapprove.setNotes("");
					departapprove.setApproveresult(1);
					departapprove.setApproverId(employee.getId());
					departapprove.setApprovetime(new Date());
					departApproveService
							.updateDepartApproveRecord(departapprove);

					String typeIdStr = JavaConfig
							.fetchProperty("DepartRegisterController.approveId");
					Long messagetypeId = Long.valueOf(typeIdStr);
					messageMessage.updateMessageProcessor(employee.getId(),
							messagetypeId, "pension_departregister",
							selectPensionDepartapprove.getId(),
							employee.getDeptId());

					selectPensionDepartapprove.setIsagree(PRE_LEAVE);
					departRegisterService
							.updateDepartRegisterRecord(selectPensionDepartapprove);
					// this.search();
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"同意离院", ""));
				} catch (DataAccessException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, e
									.getMessage(), ""));
					e.printStackTrace();
				}
				pensionDepartapproveList = departApproveService
						.selectDepartApprove(selectPensionDepartapprove.getId());
				return 0;
			}

		}
		if (i >= pensionDepartapproveList.size()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "您没有权限对其审核！",
							""));
		}
		return 0;
	}

	/**
	 * 继续入住
	 * 
	 * 
	 */
	public void disagreeDepart() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		approveRecord.setDepartId(selectPensionDepartapprove.getId());
		approveRecord.setApproveresult(2);
		approveRecord.setApproverId(employee.getId());
		approveRecord.setApprovetime(new Date());
		approveRecord.setDeptId(employee.getDeptId());
		departApproveService.updateDepartApproveRecord(approveRecord);

		String typeIdStr = JavaConfig
				.fetchProperty("DepartRegisterController.approveId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employee.getId(), messagetypeId,
				"pension_departregister", selectPensionDepartapprove.getId(),
				employee.getDeptId());

		selectPensionDepartapprove.setIsagree(PRE_LEAVE);
		departRegisterService
				.updateDepartRegisterRecord(selectPensionDepartapprove);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "同意继续入住", ""));
		requestContext.addCallbackParam("success", true);

		pensionDepartapproveList = departApproveService
				.selectDepartApprove(selectPensionDepartapprove.getId());

	}

	/**
	 * 同意离院确认
	 * 
	 * @throws PmsException
	 * 
	 * 
	 */
	public void agreeDepartComfirm() throws PmsException {

		if (agreeDepart() == -1)
			return;
		String messageContent = selectPensionDepartapprove.getName()
				+ "老人离院检查！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("DepartRegisterController.checkId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?departId=" + selectPensionDepartapprove.getId();

		List<Long> dptIdList = departRegisterService.selectDeptIdList();
		List<Long> empList = departRegisterService.selectEmptIdList();

		String messagename = "【" + selectPensionDepartapprove.getName()
				+ "】离院检查";

		messageMessage.sendMessage(messagetypeId, dptIdList, empList,
				messagename, messageContent, url, "pension_departnotify",
				selectPensionDepartapprove.getId());
		try {

			selectPensionDepartapprove.setIsagree(LEAVE_COMFIRM);
			departRegisterService
					.updateDepartRegisterRecord(selectPensionDepartapprove);

			String typeIds = JavaConfig
					.fetchProperty("DepartRegisterController.approveComfirmId");
			Long messageId = Long.valueOf(typeIds);
			messageMessage.updateMessageProcessor(employee.getId(), messageId,
					"pension_departregister",
					selectPensionDepartapprove.getId(), employee.getDeptId());

			// 在离院结算中插入费用记录
			PensionDepartrecord departrecord = new PensionDepartrecord();
			departrecord.setOlderId(selectPensionDepartapprove.getOlderId()
					.intValue());
			recordService.insertDepartApproveRecord(departrecord);

			// 在离院通知的部门中，逐个检查event事件，插入到离院通知
			List<PensionDicEvent> dicEvents = dicEventService.selectEventList(
					empList, dptIdList, 3);
			for (int i = 0; i < dicEvents.size(); i++) {
				PensionDepartnotify departnotify = new PensionDepartnotify();
				if (empList != null && empList.size() > 0) {
					departnotify
							.setDepartId(selectPensionDepartapprove.getId());
					departnotify.setDeptId(dicEvents.get(i).getDeptId()
							.longValue());
					departnotify.setEventId(dicEvents.get(i).getId());
					departnotify.setHandlerId(dicEvents.get(i).getEmpId()
							.intValue());
				} else {
					departnotify
							.setDepartId(selectPensionDepartapprove.getId());
					departnotify.setDeptId(dicEvents.get(i).getDeptId()
							.longValue());
					departnotify.setEventId(dicEvents.get(i).getId());
				}
				departApprovenofityService
						.insertDepartApproveRecord(departnotify);
			}
			// this.search();
		} catch (DataAccessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		}
		pensionDepartapproveList = departApproveService
				.selectDepartApprove(selectPensionDepartapprove.getId());
	}

	/**
	 * 继续入住确认
	 * 
	 * 
	 */
	public void disagreeDepartComfirm() {
		if (agreeDepart() == -1)
			return;
		try {
			selectPensionDepartapprove.setCleared(1);
			departRegisterService
					.updateDepartRegisterRecord(selectPensionDepartapprove);
			String typeIds = JavaConfig
					.fetchProperty("DepartRegisterController.approveComfirmId");
			Long messageId = Long.valueOf(typeIds);
			messageMessage.updateMessageProcessor(employee.getId(), messageId,
					"pension_departregister",
					selectPensionDepartapprove.getId(), employee.getDeptId());
			this.search();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "同意继续入住", ""));
		} catch (DataAccessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		}
		pensionDepartapproveList = departApproveService
				.selectDepartApprove(selectPensionDepartapprove.getId());

	}

	/**
	 * 确认老人正式离院
	 * 
	 * 
	 */
	public void departComfirm() {
		try {
			// 修改老人状态为退住5,修改床位老人数，自动减一

			olderService.updateOlderByKey(
					selectPensionDepartcheck.getOlderId(),
					selectPensionDepartcheck.getBed_id().longValue(), 5);
			selectPensionDepartcheck.setIsagree(LEAVE_FANCAIL);
			selectPensionDepartcheck.setRealLeavetime(new Date());
			departRegisterService
					.updateDepartRegisterRecord(selectPensionDepartcheck);
			this.search();
			// 发送消息通知全院
			String messageContent = selectPensionDepartcheck.getName()
					+ "老人正式离院！";
			// 消息类别
			String typeIdStr = JavaConfig
					.fetchProperty("DepartRegisterController.comfirmId");
			Long messagetypeId = Long.valueOf(typeIdStr);

			String url;

			url = messageMessage.selectMessageTypeUrl(messagetypeId);
			url = url + "?olderId=" + selectPensionDepartcheck.getOlderId();
			String depart_dpt_id = systemConfig
					.selectProperty("ALL_NOTIFY_DPT_ID");
			List<Long> dptIdList = new ArrayList<Long>();
			List<Integer> dptIdList_i = new ArrayList<Integer>();
			if (depart_dpt_id != null && !depart_dpt_id.isEmpty()) {
				String[] budgetDptId = depart_dpt_id.split(",");
				for (int i = 0; i < budgetDptId.length; i++) {
					dptIdList.add(Long.valueOf(budgetDptId[i]));
					dptIdList_i.add(Integer.valueOf(budgetDptId[i]));
				}
			} else {
				throw new PmsException("请设置申请通知到的部门");
			}
			String messagename = "【" + selectPensionDepartcheck.getName()
					+ "】老人正式离院";

			messageMessage.sendMessage(messagetypeId, dptIdList, null,
					messagename, messageContent, url, "pension_livingrecord",
					selectPensionDepartcheck.getOlderId());

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "老人["
							+ selectPensionDepartcheck.getName()
							+ "]正式离院，通知全院成功！", ""));
		} catch (DataAccessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		} catch (PmsException e) {
			e.printStackTrace();
		}
		pensionDepartapproveList = departApproveService
				.selectDepartApprove(selectPensionDepartcheck.getId());

	}

	/**
	 * 撤销发送通知
	 * 
	 * @throws PmsException
	 */
	public void cancelSend() throws PmsException {
		try {
			selectPensionDepartregister.setIsagree(APPLAY);
			departRegisterService
					.updateDepartRegisterRecord(selectPensionDepartregister);

			List<PensionDepartapprove> departapproves = departApproveService
					.selectByPrimaryKey(selectPensionDepartregister.getId());
			for (int i = 0; i < departapproves.size(); i++) {
				PensionDepartapprove departapprove = departapproves.get(i);
				departapprove.setCleared(1);
				departApproveService.updateDepartApproveRecord(departapprove);
			}
			String typeIdStr = JavaConfig
					.fetchProperty("DepartRegisterController.approveId");
			Long messagetypeId = Long.valueOf(typeIdStr);
			messageMessage.deleteMessage(messagetypeId,
					"pension_departregister",
					selectPensionDepartregister.getId());
			String typeIdStr2 = JavaConfig
					.fetchProperty("DepartRegisterController.approveComfirmId");
			Long messagetypeId2 = Long.valueOf(typeIdStr2);
			messageMessage.deleteMessage(messagetypeId2,
					"pension_departregister",
					selectPensionDepartregister.getId());
			this.search();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "撤销申请成功", ""));
		} catch (DataAccessException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		}
	}

	/**
	 * 继续入住触发
	 */
	public void selectCheck() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectPensionDepartapprove.getIsagree() == 5) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"审批已经通过，你不能再做审核，请刷新记录！", ""));
			return;
		}
		requestContext.addCallbackParam("show", true);
		approveRecord = new PensionDepartapprove();
	}

	/**
	 * 
	 * @Title: checkDate
	 * @Description: check当前日期与选中日期
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author bill.zhang
	 * @date 2013-9-24 下午3:56:17
	 * @version V1.0
	 */
	@SuppressWarnings("unused")
	private boolean checkDate(Date date) {
		Date sysDate = new Date();
		if (date.after(sysDate)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * 按条件查询
	 * */
	public void search() {
		if (endDate != null) {
			endDate.setDate(endDate.getDate() + 1);
			endDate.setSeconds(endDate.getSeconds() - 1);
		}
		pensionDepartregisters = departRegisterService.search(olderId,
				startDate, endDate, APPLAY, departID);
		selectPensionDepartregister = new PensionDepartregister();
		pensionDepartapprove = departRegisterService.search(olderId, startDate,
				endDate, PRE_LEAVE, departID);
		pensionDepartcheck = departRegisterService.searchByComfirm(olderId,
				startDate, endDate, LEAVE_COMFIRM, departID);
		disabledFlag = true;
		disabledFlagForCancel = true;
		invilidFrom = true;
		checkDisableFrom = true;
		departComfirmDisableFrom = true;
		if (pensionDepartnofityListCheck != null)
			pensionDepartnofityListCheck.clear();
		if (pensionDepartnofityListCheck != null)
			pensionDepartnofityList.clear();
		// selectPensionDepartapprove = new PensionDepartregister();
	}

	public void onChangeTab(TabChangeEvent e) {

	}

	public void onPensionDepartregisterSelect(SelectEvent e) {

	}

	public void onPensionDepartregisterUnselect(UnselectEvent e) {

	}

	public DepartRegisterService getDepartRegisterService() {
		return departRegisterService;
	}

	public void setDepartRegisterService(
			DepartRegisterService departRegisterService) {
		this.departRegisterService = departRegisterService;
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

	public List<PensionThingdamage> getPensionThingDamages() {
		return pensionThingDamages;
	}

	public void setPensionThingDamages(
			List<PensionThingdamage> pensionThingDamages) {
		this.pensionThingDamages = pensionThingDamages;
	}

	public PensionSysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(PensionSysUser sysUser) {
		this.sysUser = sysUser;
	}

	public DataTable getListTable() {
		return listTable;
	}

	public void setListTable(DataTable listTable) {
		this.listTable = listTable;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setInsertedRow(PensionDepartregister insertedRow) {
		this.insertedRow = insertedRow;
	}

	public PensionDepartregister getInsertedRow() {
		return insertedRow;
	}

	public void setDisabledFlag(boolean disabledFlag) {
		this.disabledFlag = disabledFlag;
	}

	public boolean isDisabledFlag() {
		return disabledFlag;
	}

	public void setOlderNameSql(String olderNameSql) {
		this.olderNameSql = olderNameSql;
	}

	public String getOlderNameSql() {
		return olderNameSql;
	}

	public void setDisabledFlagForCancel(boolean disabledFlagForCancel) {
		this.disabledFlagForCancel = disabledFlagForCancel;
	}

	public boolean isDisabledFlagForCancel() {
		return disabledFlagForCancel;
	}

	public void setPensionDepartapprove(
			List<PensionDepartregister> pensionDepartapprove) {
		this.pensionDepartapprove = pensionDepartapprove;
	}

	public List<PensionDepartregister> getPensionDepartapprove() {
		return pensionDepartapprove;
	}

	public void setDepartApproveService(
			DepartApproveService departApproveService) {
		this.departApproveService = departApproveService;
	}

	public DepartApproveService getDepartApproveService() {
		return departApproveService;
	}

	public void setHAVE_LEAVE(Integer hAVE_LEAVE) {
		HAVE_LEAVE = hAVE_LEAVE;
	}

	public Integer getHAVE_LEAVE() {
		return HAVE_LEAVE;
	}

	public void setPensionDepartapproveList(
			List<PensionDepartapprove> pensionDepartapproveList) {
		this.pensionDepartapproveList = pensionDepartapproveList;
	}

	public List<PensionDepartapprove> getPensionDepartapproveList() {
		return pensionDepartapproveList;
	}

	public void setInvilidFrom(boolean invilidFrom) {
		this.invilidFrom = invilidFrom;
	}

	public boolean isInvilidFrom() {
		return invilidFrom;
	}

	public void setSelectPensionDepartapprove(
			PensionDepartregister selectPensionDepartapprove) {
		this.selectPensionDepartapprove = selectPensionDepartapprove;
	}

	public PensionDepartregister getSelectPensionDepartapprove() {
		return selectPensionDepartapprove;
	}

	public void setLEAVE_COMFIRM(Integer lEAVE_COMFIRM) {
		LEAVE_COMFIRM = lEAVE_COMFIRM;
	}

	public Integer getLEAVE_COMFIRM() {
		return LEAVE_COMFIRM;
	}

	public void setLEAVE_FANCAIL(Integer lEAVE_FANCAIL) {
		LEAVE_FANCAIL = lEAVE_FANCAIL;
	}

	public Integer getLEAVE_FANCAIL() {
		return LEAVE_FANCAIL;
	}

	public void setPensionDepartcheck(
			List<PensionDepartregister> pensionDepartcheck) {
		this.pensionDepartcheck = pensionDepartcheck;
	}

	public List<PensionDepartregister> getPensionDepartcheck() {
		return pensionDepartcheck;
	}

	public void setSelectPensionDepartcheck(
			PensionDepartregister selectPensionDepartcheck) {
		this.selectPensionDepartcheck = selectPensionDepartcheck;
	}

	public PensionDepartregister getSelectPensionDepartcheck() {
		return selectPensionDepartcheck;
	}

	public void setPensionDepartnofityList(
			List<PensionDepartnotify> pensionDepartnofityList) {
		this.pensionDepartnofityList = pensionDepartnofityList;
	}

	public List<PensionDepartnotify> getPensionDepartnofityList() {
		return pensionDepartnofityList;
	}

	public void setSelectPensionDepartnofity(
			PensionDepartnotify selectPensionDepartnofity) {
		this.selectPensionDepartnofity = selectPensionDepartnofity;
	}

	public PensionDepartnotify getSelectPensionDepartnofity() {
		return selectPensionDepartnofity;
	}

	public void setCheckDisableFrom(boolean checkDisableFrom) {
		this.checkDisableFrom = checkDisableFrom;
	}

	public boolean isCheckDisableFrom() {
		return checkDisableFrom;
	}

	public DepartApprovenofityService getDepartApprovenofityService() {
		return departApprovenofityService;
	}

	public void setDepartApprovenofityService(
			DepartApprovenofityService departApprovenofityService) {
		this.departApprovenofityService = departApprovenofityService;
	}

	public void setDicEventService(DicEventService dicEventService) {
		this.dicEventService = dicEventService;
	}

	public DicEventService getDicEventService() {
		return dicEventService;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	public void setRecordService(DepartRecordService recordService) {
		this.recordService = recordService;
	}

	public DepartRecordService getRecordService() {
		return recordService;
	}

	public void setOlderService(OlderService olderService) {
		this.olderService = olderService;
	}

	public OlderService getOlderService() {
		return olderService;
	}

	public void setDepartComfirmDisableFrom(boolean departComfirmDisableFrom) {
		this.departComfirmDisableFrom = departComfirmDisableFrom;
	}

	public boolean isDepartComfirmDisableFrom() {
		return departComfirmDisableFrom;
	}

	public void setPensionDepartnofityListCheck(
			List<PensionDepartnotify> pensionDepartnofityListCheck) {
		this.pensionDepartnofityListCheck = pensionDepartnofityListCheck;
	}

	public List<PensionDepartnotify> getPensionDepartnofityListCheck() {
		return pensionDepartnofityListCheck;
	}

	public void setApproveRecord(PensionDepartapprove approveRecord) {
		this.approveRecord = approveRecord;
	}

	public PensionDepartapprove getApproveRecord() {
		return approveRecord;
	}

}
