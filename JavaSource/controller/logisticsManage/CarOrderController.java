package controller.logisticsManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.centling.his.util.SessionManager;

import service.logisticsManage.CarManageService;
import service.logisticsManage.CarOrderService;
import service.system.MessageMessage;
import util.DateUtil;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionVehicle;
import domain.logisticsManage.PensionVehicleOrder;

@SuppressWarnings("serial")
public class CarOrderController implements Serializable {

	/**
	 * 注入业务
	 */
	private transient CarOrderService carOrderService;
	private transient CarManageService carManageService;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;

	private ScheduleModel eventModel;

	private String carSQL;
	private String olderNameSql;
	private String carNumber = null;
	private String driverName = null;
	private String carType = null;
	private Long orderId = null;
	private Long selectedCarId = null;
	private ScheduleEvent event = new DefaultScheduleEvent();

	private PensionVehicleOrder selectedOrder;
	private PensionVehicle selectCarInfo;// 选中的车辆预约信息对应的车辆信息 add by mary
											// 2013-11-29
	private PensionVehicleOrder addOrder;
	private PensionVehicle updateCar;
	/* 车辆预约列表 */
	private List<PensionVehicleOrder> list = null;
	/* 出车管理列表 */
	private List<PensionVehicleOrder> manageList = null;
	private PensionVehicleOrder addManage = null;

	/**
	 * 查询条件
	 */
	private Long carNumberId;
	private String carNumberName;
	private Long olderId;
	private String olderName;
	private Integer isRegister;
	private Integer carStatus;

	/**
	 * 是否显示预计时间
	 */
	private Boolean isShow;
	// 预约时间显示，只可预约当天之后的日期
	private Date currentDate;

	// 出车检查
	private String contacter;// 联系人
	// 预计里程
	private Float expectDistance;
	// 出车收费
	private Date actualGotime;// 实际出车时间
	private Date actualBacktime;// 实际返回时间
	private Float actualDistance;// 实际里程
	private Float currentKilometers;// 当前公里数
	private Float candriveKilometers;// 剩余公里数
	private Float remainFuel;// 剩余油量

	private Date starDate;
	private Date endDate;
	/**
	 * 设置按钮的可用性
	 */
	private boolean checkFlag;// 是否出车检查
	private boolean feeFlag;// 是否出车计费
	private PensionEmployee employee;

	@PostConstruct
	public void init() {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String orderIdStr = paramsMap.get("orderId");
		if (orderIdStr != null) {
			starDate = null;
			endDate = null;
			orderId = Long.valueOf(orderIdStr);
		} else {
			orderId = null;
			endDate = new Date();
			starDate = DateUtil.getBeforeMonthDay(endDate, 1);

		}
		eventModel = new DefaultScheduleModel();
		carNumber = "";
		driverName = "";
		currentDate = new Date();
		currentDate.setDate(currentDate.getDate() + 1);
		initSQL();
		clearAddForm();
		// clearSearchForm();
		isShow = true;
		checkFlag = false;
		feeFlag = false;
		isRegister = 3;
		carStatus = 4;
		selectAllOrcerList();
		if (orderId != null && manageList.size() > 0) {
			PensionVehicleOrder order = manageList.get(0);
			PensionVehicle vehicle = carOrderService.selectVehicle(order
					.getVehicleId());
			carNumber = vehicle.getPlateNumber();
			carType = vehicle.getVehicleType();
			driverName = carOrderService.selectContacter(vehicle
					.getContacterId());
			eventModel.clear();
			DefaultScheduleEvent event = new DefaultScheduleEvent("预约人["
					+ order.getOlderName() + "]\n预约事项["
					+ order.getMatterDetail() + "]", order.getExpectGotime(),
					order.getExpectBacktime(), order);
			eventModel.addEvent(event);
		}
		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);

	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		carNumberId = null;
		carNumberName = "";
		olderName = "";
		olderId = null;
		isRegister = 3;
		carStatus = 4;
		starDate = null;
		endDate = null;
	}

	/**
	 * 查询所有预约车辆信息
	 */
	public void selectAllOrcerList() {
		if (carStatus == 1)
			isShow = true;
		else
			isShow = false;
		if (carStatus == 4)
			carStatus = null;
		if (isRegister == 3)
			isRegister = null;
		checkFlag = false;
		feeFlag = false;
		manageList = carOrderService.selectCarOrderByMultiRecords(carNumberId,
				olderId, carStatus, isRegister, orderId, starDate, endDate);
		selectedOrder = null;
	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		addOrder = new PensionVehicleOrder();
	}

	/**
	 * 检查车牌是否为空
	 */
	public boolean invidCarNumber() {
		if (carNumber == "") {
			sendMessageToClient(FacesMessage.SEVERITY_WARN, "请先选择车牌号");
			return false;
		}
		clearAddForm();
		return true;
	}

	/**
	 * 选中一条记录，设置按钮可用状态
	 */
	public void setEnableFlag(SelectEvent event) {
		// 修改、删除按钮状态
		if (selectedOrder.getVehicleState() == 1) {
			checkFlag = true;
			feeFlag = false;
		} else if (selectedOrder.getVehicleState() == 2) {
			checkFlag = false;
			feeFlag = true;
		} else {
			checkFlag = false;
			feeFlag = false;
		}

	}

	public void selectCarById() {
		updateCar = carManageService.selectCarRecords(
				selectedOrder.getVehicleNumber()).get(0);
	}

	/**
	 * 未选中一条记录，设置按钮可用状态
	 */
	public void setUnableFlag(UnselectEvent event) {
		checkFlag = false;
		feeFlag = false;
		clearAddForm();
	}

	/**
	 * 逻辑删除车辆预约信息
	 */
	public void deleteCarOrderRecord() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			selectedOrder.setCleared(1);
			carOrderService.updateOrderRecord(selectedOrder);
			sendMessageToClient(FacesMessage.SEVERITY_INFO,
					selectedOrder.getOlderName() + "预约删除成功");
			onSelectedCar();
			request.addCallbackParam("validate", true);
		} catch (Exception e) {
			sendMessageToClient(FacesMessage.SEVERITY_INFO,
					selectedOrder.getOlderName() + "预约删除失败");
			onSelectedCar();
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * 保存车辆预约信息
	 */
	public void insertCarOrderRecord() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (invidDateTime(addOrder)) {// 验证时间不冲突，添加预约记录，关闭Dig
			addOrder.setVehicleId(selectedCarId);
			addOrder.setVehicleNumber(carNumber);
			carOrderService.insertOrderRecord(addOrder);
			addOrder.setVehicleState(1);// 未出车
			addOrder.setRegisterFlag(2);// 未登记
			sendMessageToClient(FacesMessage.SEVERITY_INFO,
					addOrder.getOlderName() + "预约添加成功！");
			request.addCallbackParam("validate", true);
			try {
				sentMessage(addOrder, "【" + addOrder.getOlderName() + "】预约车辆",
						"【" + addOrder.getOlderName() + "】预约车辆");
			} catch (PmsException e) {
				e.printStackTrace();
			}
		} else {// 验证时间冲突，不添加预约记录，不关闭Dig
			request.addCallbackParam("validate", false);
		}
		onSelectedCar();
	}

	/**
	 * 修改车辆预约信息
	 */
	public void updateCarOrderRecord() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (invidDateTime(selectedOrder)) {
			carOrderService.updateOrderRecord(selectedOrder);
			sendMessageToClient(FacesMessage.SEVERITY_INFO,
					selectedOrder.getOlderName() + "预约车辆更新成功！");
			request.addCallbackParam("validate", true);
		} else {
			sendMessageToClient(FacesMessage.SEVERITY_INFO,
					selectedOrder.getOlderName() + "预约车辆更新失败！");
			request.addCallbackParam("validate", false);
		}
		onSelectedCar();
	}

	/**
	 * 更新schedule显示内容
	 */
	public void onSelectedCar() {
		if (selectedCarId == null) {
			eventModel.clear();
			return;
		}
		list = carOrderService.selectCarOrderRecords(selectedCarId, null, null,
				null);
		eventModel.clear();
		for (int i = 0; i < list.size(); i++) {
			PensionVehicleOrder order = list.get(i);
			DefaultScheduleEvent event = new DefaultScheduleEvent("预约人["
					+ order.getOlderName() + "]\n预约事项["
					+ order.getMatterDetail() + "]", order.getExpectGotime(),
					order.getExpectBacktime(), order);
			eventModel.addEvent(event);
		}
	}

	/**
	 * 验证预约时间是否冲突
	 */
	public boolean invidDateTime(PensionVehicleOrder pvo) {
		if (pvo.getExpectGotime().after(pvo.getExpectBacktime())) {
			sendMessageToClient(FacesMessage.SEVERITY_WARN,
					pvo.getMatterDetail() + "出发与返回时间有误！");
			return false;
		}
		List<ScheduleEvent> events = eventModel.getEvents();
		events.remove(event);
		for (int i = 0; i < events.size(); i++) {
			ScheduleEvent event = events.get(i);
			boolean start1 = pvo.getExpectGotime().before(event.getStartDate());
			boolean start2 = pvo.getExpectGotime().after(event.getEndDate());
			boolean end1 = pvo.getExpectBacktime().before(event.getStartDate());
			boolean end2 = pvo.getExpectBacktime().after(event.getEndDate());
			if ((start1 && end1) || (start2 && end2)) {
				continue;
			} else {
				sendMessageToClient(FacesMessage.SEVERITY_WARN,
						pvo.getMatterDetail() + "预约时间冲突！");
				return false;
			}
		}
		return true;
	}

	/**
	 * 发送提示消息
	 */
	public void sendMessageToClient(Severity s, String message) {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		request.addCallbackParam("sendMessage", true);
		facesContext.addMessage("", new FacesMessage(s, message, ""));
	}

	public void initSQL() {
		carSQL = "select pension_vehicle.* ,pension_employee.name as employeeName "
				+ "from pension_vehicle right join pension_employee on pension_vehicle.contacter_id = pension_employee.id "
				+ "where pension_vehicle.cleared = 2";
		olderNameSql = "select po.name	as oldName,"
				+ "pbuilding.name	  as buildName," + "pr.name		  as roomName,"
				+ "pf.name		  as floorName," + "pb.name		  as bedName,"
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

	public void addEvent(ActionEvent actionEvent) {
		if (event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);

		event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
		selectedOrder = (PensionVehicleOrder) event.getData();
	}

	public void onDateSelect(SelectEvent selectEvent) {
		if (!invidCarNumber())
			return;
		Date time = (Date) selectEvent.getObject();
		if (time.before(currentDate)) {
			time.setYear(currentDate.getYear());
			time.setMonth(currentDate.getMonth());
			time.setDate(currentDate.getDate());
		}
		event = new DefaultScheduleEvent("", time, time);
		// 修改成当前时间的后一天
		// modify by mary 2014-04-12
		addOrder.setExpectGotime(new Date(new Date().getTime() + 86400000));
		Date newTime = new Date(time.getTime());
		newTime.setHours(newTime.getHours() + 4);
		addOrder.setExpectBacktime(null);// modify by mary 2014-04-12
		addOrder.setRegisterFlag(2);// 初始化未登记
		addOrder.setVehicleState(1);// 初始化未出车
	}

	/**
	 * 发送通知
	 * 
	 * @throws PmsException
	 */
	public void sentMessage(PensionVehicleOrder order, String messagename,
			String messageContent) throws PmsException {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("CarOrderController.notifyId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		String url;
		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?orderId=" + order.getId();

		String car_dpt_id = systemConfig.selectProperty("CAR_ORDER_DPT_ID");
		String car_emp_id = systemConfig.selectProperty("CAR_ORDER_EMP_ID");

		List<Long> carIdList = new ArrayList<Long>();
		List<Long> carEmpIdList = new ArrayList<Long>();

		if (car_emp_id != null && !car_emp_id.isEmpty()) {
			String[] carEmpId = car_emp_id.split(",");
			for (int i = 0; i < carEmpId.length; i++) {
				carEmpIdList.add(Long.valueOf(carEmpId[i]));
			}
		} else if (car_dpt_id != null && !car_dpt_id.isEmpty()) {
			String[] carId = car_dpt_id.split(",");
			for (int i = 0; i < carId.length; i++) {
				carIdList.add(Long.valueOf(carId[i]));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"请设置出车预约与发车发送的部门或人员！", ""));
		}
		messageMessage.sendMessage(messagetypeId, carIdList, carEmpIdList,
				messagename, messageContent, url, "pension_vehicle_order",
				order.getVehicleId());
	}

	/**
	 * 查询车辆信息--出车检查
	 * 
	 * @author centling Mary 2013-11-29
	 */
	public void selectOutCatInfo() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedOrder == null || selectedOrder.getVehicleId() == null) {
			sendMessageToClient(FacesMessage.SEVERITY_INFO, "请先选择数据！");
			request.addCallbackParam("validate", false);
		} else {
			selectCarInfo = carOrderService.selectVehicle(selectedOrder
					.getVehicleId());
			if (selectCarInfo == null) {
				sendMessageToClient(FacesMessage.SEVERITY_INFO, "获取车辆信息出错！");
			} else {
				contacter = carOrderService.selectContacter(selectCarInfo
						.getContacterId());
				expectDistance = selectedOrder.getExpectDistance();
			}
			actualGotime = new Date();
			request.addCallbackParam("validate", true);
		}
	}

	/**
	 * 查询车辆信息，初始化信息--出车计费
	 * 
	 * @author centling Mary 2013-11-29
	 */
	public void selectInCatInfo() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedOrder == null || selectedOrder.getVehicleId() == null) {
			sendMessageToClient(FacesMessage.SEVERITY_INFO, "请先选择数据！");
			request.addCallbackParam("validate", false);
		} else {
			selectCarInfo = carOrderService.selectVehicle(selectedOrder
					.getVehicleId());
			if (selectCarInfo == null) {
				sendMessageToClient(FacesMessage.SEVERITY_INFO, "获取车辆信息出错！");
				request.addCallbackParam("validate", false);
			}
			request.addCallbackParam("validate", true);
		}
		actualBacktime = selectedOrder.getExpectBacktime();
		actualDistance = null;
		currentKilometers = null;
		remainFuel = null;
	}

	/**
	 * 出车检查确认
	 * 
	 * @author centling Mary 2013-11-29
	 */
	public void ensureDrawOut() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (selectedOrder.getExpectDistance().floatValue() > selectCarInfo
					.getCandriveKilometers().floatValue()) {
				sendMessageToClient(FacesMessage.SEVERITY_INFO, "当前可用公里数不足！");
				request.addCallbackParam("validate", false);
			} else {
				// 如果没有修改预计里程 add by mary.liu 2014-02-24
				if (selectedOrder.getExpectDistance().equals(expectDistance)) {
					expectDistance = null;
				} else {
					selectedOrder.setExpectDistance(expectDistance);
				}
				carOrderService.ensureDrawOut(selectedOrder.getId(),
						actualGotime, selectCarInfo, expectDistance);
				selectedOrder.setActualGotime(actualGotime);// 实际出车时间
				selectedOrder.setRegisterFlag(1);// 是否出车登记 -- 是
				selectedOrder.setVehicleState(2);// 出车状态 -- 出车中
				this.sentMessage(selectedOrder, "出车通知",
						selectedOrder.getVehicleNumber() + "已出车！");
				checkFlag = false;
				feeFlag = true;
				sendMessageToClient(FacesMessage.SEVERITY_INFO, "出车检查成功！");
				request.addCallbackParam("validate", true);
			}
		} catch (Exception e) {
			sendMessageToClient(FacesMessage.SEVERITY_INFO, "出车检查出错！");
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * 出车计费
	 * 
	 * @author centling Mary 2013-11-29
	 */
	public void chargeFee() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			carOrderService.chargeFee(selectCarInfo.getId(),
					selectedOrder.getId(), actualBacktime, actualDistance,
					currentKilometers, candriveKilometers, remainFuel);
			selectedOrder.setActualBacktime(actualBacktime);
			selectedOrder.setActualDistance(actualDistance);
			selectedOrder.setVehicleState(3);
			feeFlag = false;// 【出车计费】不可用
			sendMessageToClient(FacesMessage.SEVERITY_INFO, "出车计费成功！");
			request.addCallbackParam("validate", true);
		} catch (Exception e) {
			sendMessageToClient(FacesMessage.SEVERITY_INFO, "出车计费出错！");
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * 是否选择了数据
	 */
	public void checkSelectDate() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedOrder == null || selectedOrder.getVehicleId() == null) {
			sendMessageToClient(FacesMessage.SEVERITY_INFO, "请先选择数据！");
			request.addCallbackParam("validate", false);
		} else {
			request.addCallbackParam("validate", true);
		}
	}

	/**
	 * 【费用录入】
	 */
	public void checkBeHandledAndPayed() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			request.addCallbackParam("olderId", selectedOrder.getOlderId());
			request.addCallbackParam("priceTypeId",
					carOrderService.getPriceTypeId());
			request.addCallbackParam("orderId", selectedOrder.getId());
			request.addCallbackParam("tableName", "pension_vehicle_order");
			request.addCallbackParam("success", true);
		} catch (PmsException e) {
			request.addCallbackParam("success", false);
		}

	}

	/**
	 * check 常见结果列表dateTable 是否可以修改和删除
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectDate(FacesContext context, UIComponent component,
			Object obj) {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedOrder.getRegisterFlag() == 1) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "该预约已登记，不能操作！", "该预约已登记，不能操作！");
			request.addCallbackParam("validate", false);
			throw new ValidatorException(message);
		} else {
			request.addCallbackParam("validate", true);
		}
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event moved", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Event resized", "Day delta:" + event.getDayDelta()
						+ ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void setCarOrderService(CarOrderService carOrderService) {
		this.carOrderService = carOrderService;
	}

	public CarOrderService getCarOrderService() {
		return carOrderService;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void setCarSQL(String carSQL) {
		this.carSQL = carSQL;
	}

	public String getCarSQL() {
		return carSQL;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setList(List<PensionVehicleOrder> list) {
		this.list = list;
	}

	public List<PensionVehicleOrder> getList() {
		return list;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setSelectedCarId(Long selectedCarId) {
		this.selectedCarId = selectedCarId;
	}

	public Long getSelectedCarId() {
		return selectedCarId;
	}

	public void setSelectedOrder(PensionVehicleOrder selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public PensionVehicleOrder getSelectedOrder() {
		return selectedOrder;
	}

	public void setAddOrder(PensionVehicleOrder addOrder) {
		this.addOrder = addOrder;
	}

	public PensionVehicleOrder getAddOrder() {
		return addOrder;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarType() {
		return carType;
	}

	public void setOlderNameSql(String olderNameSql) {
		this.olderNameSql = olderNameSql;
	}

	public String getOlderNameSql() {
		return olderNameSql;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setManageList(List<PensionVehicleOrder> manageList) {
		this.manageList = manageList;
	}

	public List<PensionVehicleOrder> getManageList() {
		return manageList;
	}

	public void setAddManage(PensionVehicleOrder addManage) {
		this.addManage = addManage;
	}

	public PensionVehicleOrder getAddManage() {
		return addManage;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setCarNumberId(Long carNumberId) {
		this.carNumberId = carNumberId;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public void setIsRegister(Integer isRegister) {
		this.isRegister = isRegister;
	}

	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}

	public Long getCarNumberId() {
		return carNumberId;
	}

	public Long getOlderId() {
		return olderId;
	}

	public Integer getIsRegister() {
		return isRegister;
	}

	public Integer getCarStatus() {
		return carStatus;
	}

	public void setCarNumberName(String carNumberName) {
		this.carNumberName = carNumberName;
	}

	public String getCarNumberName() {
		return carNumberName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setCheckFlag(boolean checkFlag) {
		this.checkFlag = checkFlag;
	}

	public boolean isCheckFlag() {
		return checkFlag;
	}

	public void setFeeFlag(boolean feeFlag) {
		this.feeFlag = feeFlag;
	}

	public boolean isFeeFlag() {
		return feeFlag;
	}

	public void setCarManageService(CarManageService carManageService) {
		this.carManageService = carManageService;
	}

	public CarManageService getCarManageService() {
		return carManageService;
	}

	public void setUpdateCar(PensionVehicle updateCar) {
		this.updateCar = updateCar;
	}

	public PensionVehicle getUpdateCar() {
		return updateCar;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	public PensionVehicle getSelectCarInfo() {
		return selectCarInfo;
	}

	public void setSelectCarInfo(PensionVehicle selectCarInfo) {
		this.selectCarInfo = selectCarInfo;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}

	public String getContacter() {
		return contacter;
	}

	public void setContacter(String contacter) {
		this.contacter = contacter;
	}

	public Date getActualGotime() {
		return actualGotime;
	}

	public void setActualGotime(Date actualGotime) {
		this.actualGotime = actualGotime;
	}

	public Date getActualBacktime() {
		return actualBacktime;
	}

	public void setActualBacktime(Date actualBacktime) {
		this.actualBacktime = actualBacktime;
	}

	public Float getActualDistance() {
		return actualDistance;
	}

	public void setActualDistance(Float actualDistance) {
		this.actualDistance = actualDistance;
	}

	public Float getCurrentKilometers() {
		return currentKilometers;
	}

	public void setCurrentKilometers(Float currentKilometers) {
		this.currentKilometers = currentKilometers;
	}

	public Float getCandriveKilometers() {
		return candriveKilometers;
	}

	public void setCandriveKilometers(Float candriveKilometers) {
		this.candriveKilometers = candriveKilometers;
	}

	public Float getRemainFuel() {
		return remainFuel;
	}

	public void setRemainFuel(Float remainFuel) {
		this.remainFuel = remainFuel;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Float getExpectDistance() {
		return expectDistance;
	}

	public void setExpectDistance(Float expectDistance) {
		this.expectDistance = expectDistance;
	}

	public Date getStarDate() {
		return starDate;
	}

	public void setStarDate(Date starDate) {
		this.starDate = starDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
