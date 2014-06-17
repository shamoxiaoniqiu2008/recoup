package controller.personnelManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import service.personnelManage.StaffOrderService;
import util.DateUtil;
import domain.hrManage.PensionStaffArrange;

public class StaffOrderController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 注入业务
	 */
	private transient StaffOrderService staffOrderService;
	private ScheduleModel eventModel;
	private Long selectedDeptd = null;
	private ScheduleEvent event = new DefaultScheduleEvent();

	private PensionStaffArrange selectedOrder;

	/* 人员排班列表 */
	private List<PensionStaffArrange> list = new ArrayList<PensionStaffArrange>();

	// 预约时间显示，只可预约当天之后的日期
	private Date currentDate;

	@PostConstruct
	public void init() {
		eventModel = new DefaultScheduleModel();
		currentDate = new Date();
		currentDate.setDate(currentDate.getDate() + 1);
		selectAllOrcerList();
	}

	/**
	 * 查询所有人员排班信息
	 */
	public void selectAllOrcerList() {
		list = staffOrderService.selectAllStaffArrangeRecords();
		selectedOrder = null;
	}

	/**
	 * 更新schedule显示内容
	 */
	public void onSelectedDept() {
		if (selectedDeptd == null) {
			eventModel.clear();
			return;
		}
		list = staffOrderService.selectStaffOrderRecords(selectedDeptd);
		eventModel.clear();
		for (int i = 0; i < list.size(); i++) {
			PensionStaffArrange order = list.get(i);
			String arrangeTime = DateUtil.getTime(order.getStartTime());
			String arrangeDate = DateUtil.getDate(order.getArrangeTime());
			Date arrageStartTime = DateUtil.parseDate(
					arrangeDate + " "+ arrangeTime, "yyyy-MM-dd HH:mm:ss");
			String arrangeEnd = DateUtil.getTime(order.getEndTime());
			Date arrageEndTime = DateUtil.parseDate(arrangeDate + " "
					+ arrangeEnd, "yyyy-MM-dd HH:mm:ss");
			DefaultScheduleEvent event = new DefaultScheduleEvent(
					order.getDepartName() + "[" + order.getEmployeeName()
							+ "]\n排班信息--" + order.getArrangeName(),
					arrageStartTime, arrageEndTime, order);
			eventModel.addEvent(event);
		}
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
		selectedOrder = (PensionStaffArrange) event.getData();
	}

	public PensionStaffArrange getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(PensionStaffArrange selectedOrder) {
		this.selectedOrder = selectedOrder;
	}

	public List<PensionStaffArrange> getList() {
		return list;
	}

	public void setList(List<PensionStaffArrange> list) {
		this.list = list;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public StaffOrderService getStaffOrderService() {
		return staffOrderService;
	}

	public void setStaffOrderService(StaffOrderService staffOrderService) {
		this.staffOrderService = staffOrderService;
	}

	public Long getSelectedDeptd() {
		return selectedDeptd;
	}

	public void setSelectedDeptd(Long selectedDeptd) {
		this.selectedDeptd = selectedDeptd;
	}

}
