package controller.logisticsManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.dao.DataAccessException;

import service.logisticsManage.HaircutReservationService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionHaircutApplyExtend;
import domain.logisticsManage.PensionHaircutSchedule;

public class HaircutReservationController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionHaircutSchedule> records = new ArrayList<PensionHaircutSchedule>();
	/**
	 * 被选中的记录
	 */
	private PensionHaircutSchedule selectedRow = new PensionHaircutSchedule();
	/**
	 * 预约记录
	 */
	private PensionHaircutApplyExtend reserveOlderRow = new PensionHaircutApplyExtend();
	/**
	 * 预约被选中的理发项目的老人的明细
	 */
	private ArrayList<PensionHaircutApplyExtend> reservedOlderDetails = new ArrayList<PensionHaircutApplyExtend>();
	/**
	 * 将被取消的预约记录
	 */
	private PensionHaircutApplyExtend canceledOlderDetail;
	/**
	 * 绑定关于理发项目的输入法
	 */
	private String itemNameSql;
	/**
	 * 绑定关于老人的输入法
	 */
	private String olderNameSql;
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 项目主键用于查询条件
	 */
	private Long itemId;
	
	/**
	 * 预约和取消按钮是否可用
	 */
	private boolean disReserveButton = true;
	/**
	 * 注入业务
	 */
	private transient HaircutReservationService haircutReservationService;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee)SessionManager.getSessionAttribute(SessionManager.EMPLOYEE);
	/**
	 * @Description:初始化数据方法.
	 * @return: void
	 * @exception:
	 * @throws:
	 * @version: 1.0
	 * @author: Tim li
	 */
	@PostConstruct
	public void init() {
		this.initDate();
		initSql();
		selectHaircutItemRecords();
	}
	
	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		
		setItemNameSql("select phs.id as id," +
				"DATE_FORMAT(phs.start_time,'%Y-%m-%d %H:%i')  as startTime," +
				"DATE_FORMAT(phs.end_time,'%Y-%m-%d %H:%i')  as endTime," +
				"phs.haircut_address     as haircutAddress," +
				"phs.item_name           as itemName," +
				"phs.max_number          as maxNumber," +
				"phs.post_flag           as postFlag," +
				"phs.current_ordernumber as currentOrdernumber," +
				"phs.haircuted_number    as haircutedNumber," +
				"phs.cleared             as cleared " +
				"from pension_haircut_schedule phs " +
				"where phs.cleared = 2 " +
				"and phs.post_flag = 1");
		
		olderNameSql = "select po.name        as oldName," +
				"pbuilding.name as buildName," +
				"pf.name        as floorName," +
				"pr.name        as roomName," +
				"pb.name        as bedName," +
				"po.id          as olderId," +
				"po.inputCode   as inputCode," +
				"pf.id          as floorId," +
				"pr.id          as roomId," +
				"pb.id          as bedId," +
				"pbuilding.id   as buildId " +
				"from pension_older po " +
				"join pension_livingRecord plr " +
				"on po.id = plr.older_id " +
				"join pension_bed pb " +
				"on plr.bed_id = pb.id " +
				"join pension_room pr " +
				"on pb.room_id = pr.id " +
				"join pension_floor pf " +
				"on pr.floor_id = pf.id " +
				"join pension_building pbuilding " +
				"on pf.build_id = pbuilding.id " +
				"where po.cleared = 2 " +
				"and po.statuses = 3 ";
	}
	
	/**
	 * 查询理发记录
	 */
	public void selectHaircutItemRecords(){
		disReserveButton = true;
		selectedRow = new PensionHaircutSchedule();
		records = haircutReservationService.selectHaircutItemRecords(startDate,endDate,itemId);
		reservedOlderDetails.clear();
		
	}
	
	/**
	 * 将结束日期设置为今天，起始日期设置为一个月前的今天
	 */
	public void initDate(){
		
		Calendar calendar=Calendar.getInstance();
		endDate=new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		endDate=calendar.getTime();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate=calendar.getTime();
  
	}
	/**
	 * 查询被选中行对应的预约记录
	 * @param selectedRow
	 */
	public void selectReservedOlderDetails(){
		setReservedOlderDetails(haircutReservationService.selectReservedOlderDetails(selectedRow));
	}
	
	
	/**
	 * 预约选中的理发记录
	 */
	public void reserveHaircut(){
		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "预约成功";
		try {

			haircutReservationService.reserveHaircut(selectedRow,reserveOlderRow);
			selectReservedOlderDetails();
		
		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";

			
		} catch (Exception e) {

			e.getStackTrace();
			info = "出现未知异常，请联系系统管理员！";

		}
		clearReserveForm();
		if(info.equals("预约成功")){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);	
			context.addMessage(null, message);
			request.addCallbackParam("success", true);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);	
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		}
		
	}
	private void clearReserveForm() {
		reserveOlderRow = new PensionHaircutApplyExtend();
	}

	
	
	/**
	 * 取消已经预约的理发记录
	 */
	public void cancelHaircut(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;
		try{
			haircutReservationService.cancelHaircut(canceledOlderDetail,selectedRow);
			selectReservedOlderDetails();
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"取消成功！", "取消成功！");
		}catch(Exception e){
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"取消出错！", "取消出错！");
		}
		context.addMessage(null, message);
			
	}
	/**
	 * 将被选中行的记录 copy到 要取消的行中
	 * @throws PmsException 
	 * @throws NumberFormatException 
	 */
	public void copySelectToCanceledRow(PensionHaircutApplyExtend canceledOlderDetail) throws NumberFormatException, PmsException{
		this.canceledOlderDetail = canceledOlderDetail;
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		boolean success  = true;
		Long startTime = selectedRow.getStartTime().getTime();
		Long curTime = (new Date()).getTime();
		Long shortestCancelTime = haircutReservationService.getShortestCancelTime();;
		FacesMessage message = null;
		if((startTime-curTime)/(1000*60*60)<shortestCancelTime){
			message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"只有在理发开始前"+shortestCancelTime+"小时才可以取消预约！", "只有在理发开始前"+shortestCancelTime+"小时才可以取消预约！");
			context.addMessage(null, message);
			success = false;
		}else if(canceledOlderDetail.getCutedFlag() == 1){
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"该老人已理发，不能取消预约！", "该老人已理发，不能取消预约！");
			context.addMessage(null, message);
			success = false;
		}
		request.addCallbackParam("success", success);
		
	}
	
	/**
	 * 检查一下预约人数 是否大于 最大人数
	 * @param insertedRow
	 * @return
	 */
	public void checkNumber(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		Integer currentOrdernumber = selectedRow.getCurrentOrdernumber();
		Integer maxNumber = selectedRow.getMaxNumber();
		boolean success  = true;
		Long endTime = selectedRow.getEndTime().getTime();
		Long curTime = new Date().getTime();
		if(curTime>endTime){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该理发项目已结束,不能预约!", "该理发项目已结束,不能预约!");	
			context.addMessage(null, message);
			success = false;
		}
		if(maxNumber <= currentOrdernumber){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"人数已满,不能再预约!", "人数已满,不能再预约!");	
			context.addMessage(null, message);
			success = false;
		}
		request.addCallbackParam("success", success);
		
	}
	
	/**
	 * 情况selectForm
	 */
	public void clearSelectForm(){
		startDate = null;
		endDate = null;
		itemId = null;
	}
	
	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		
		setDisReserveButton(false);
		selectReservedOlderDetails();
		//绑定要预约理发的老人的输入法  去除已经预约过该项目的
		olderNameSql = "select po.name        as oldName," +
		"pbuilding.name as buildName," +
		"pf.name        as floorName," +
		"pr.name        as roomName," +
		"pb.name        as bedName," +
		"po.id          as olderId," +
		"po.inputCode   as inputCode," +
		"pf.id          as floorId," +
		"pr.id          as roomId," +
		"pb.id          as bedId," +
		"pbuilding.id   as buildId " +
		"from pension_older po " +
		"join pension_livingRecord plr " +
		"on po.id = plr.older_id " +
		"join pension_bed pb " +
		"on plr.bed_id = pb.id " +
		"join pension_room pr " +
		"on pb.room_id = pr.id " +
		"join pension_floor pf " +
		"on pr.floor_id = pf.id " +
		"join pension_building pbuilding " +
		"on pf.build_id = pbuilding.id " +
		"where po.cleared = 2 " +
		"and po.statuses = 3 " +
		"and not exists( " +
		"select * from pension_haircut_apply pha " +
		"where pha.older_id = po.id and pha.cleared = 2 " +
		"and pha.schedule_id = " + selectedRow.getId()+
		")";
		
	}
	
	/**
	 * 为已经预约的老人登记
	 */
	public void signHaircut(PensionHaircutApplyExtend signOlderDetail){
		
		FacesContext context = FacesContext.getCurrentInstance();
		Long startTime = selectedRow.getStartTime().getTime();
		Long curTime = new Date().getTime();
		if(curTime<startTime){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"还未到开始时间，不能登记!", "还未到开始时间，不能登记!");	
			context.addMessage(null, message);
		}else{
			haircutReservationService.signHaircut(signOlderDetail,selectedRow);
			selectReservedOlderDetails();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"登记成功!", "登记成功!");	
			context.addMessage(null, message);
	
		}
	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		disReserveButton = true;
		setReservedOlderDetails(new ArrayList<PensionHaircutApplyExtend >());
	}
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}
	
	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}

	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getItemId() {
		return itemId;
	}

	public List<PensionHaircutSchedule> getRecords() {
		return records;
	}

	public void setRecords(List<PensionHaircutSchedule> records) {
		this.records = records;
	}

	public void setHaircutReservationService(HaircutReservationService haircutReservationService) {
		this.haircutReservationService = haircutReservationService;
	}

	public HaircutReservationService getHaircutReservationService() {
		return haircutReservationService;
	}

	public PensionHaircutSchedule getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(PensionHaircutSchedule selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setItemNameSql(String itemNameSql) {
		this.itemNameSql = itemNameSql;
	}

	public String getItemNameSql() {
		return itemNameSql;
	}

	public void setReserveOlderRow(PensionHaircutApplyExtend reserveOlderRow) {
		this.reserveOlderRow = reserveOlderRow;
	}

	public PensionHaircutApplyExtend getReserveOlderRow() {
		return reserveOlderRow;
	}

	public void setReservedOlderDetails(ArrayList<PensionHaircutApplyExtend> reservedOlderDetails) {
		this.reservedOlderDetails = reservedOlderDetails;
	}

	public ArrayList<PensionHaircutApplyExtend> getReservedOlderDetails() {
		return reservedOlderDetails;
	}

	public void setOlderNameSql(String olderNameSql) {
		this.olderNameSql = olderNameSql;
	}

	public String getOlderNameSql() {
		return olderNameSql;
	}

	public void setDisReserveButton(boolean disReserveButton) {
		this.disReserveButton = disReserveButton;
	}

	public boolean isDisReserveButton() {
		return disReserveButton;
	}

	public void setCanceledOlderDetail(PensionHaircutApplyExtend canceledOlderDetail) {
		this.canceledOlderDetail = canceledOlderDetail;
	}

	public PensionHaircutApplyExtend getCanceledOlderDetail() {
		return canceledOlderDetail;
	}

}
