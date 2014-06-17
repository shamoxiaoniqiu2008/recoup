package controller.logisticsManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import service.logisticsManage.RepairCount;
import service.logisticsManage.SumRepairCountService;
import util.DateUtil;


public class SumRepairCountController implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private transient SumRepairCountService sumRepairCountService;
	/**
	 * 所有记录列表
	 */
	private List<RepairCount> repairRecords = new ArrayList<RepairCount>();
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 大厦id用作查询条件
	 */
	private Long buildId;
	/**
	 * 楼层id用作查询条件
	 */
	private Long floorId;
	/**
	 * 房间id用作查询条件
	 */
	private Long roomId;
	/**
	 * 绑定大厦查询条件的输入法
	 */
	private String buildSQL;
	/**
	 * 绑定楼层查询条件的输入法
	 */
	private String floorSQL;
	/**
	 * 绑定房间查询条件的输入法
	 */
	private String roomSQL;
	
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
		startDate = DateUtil.parseDate(DateUtil.getMonthBegin(DateUtil.formatDate(new Date())));  
		endDate = new Date();
		initSQL();
		selectRepairCountRecords();
	}
	
	/**
	 * 初始化前台输入法的sql
	 */
	public void initSQL()
	{
		buildSQL = "select pb.id as buildId,pb.name as buildName from pension_building pb where pb.cleared = 2";
		floorSQL =  "select pf.id as floorId," +
		"pf.name as floorName," +
		"pf.buildName as buildName " +
		"from pension_floor pf " +
		"where pf.cleared = 2 ";
		
		
		roomSQL =  "select pr.id  as roomId," +
		"pr.name as roomName," +
		"pr.floorName as floorName," +
		"pr.buildName as buildName " +
		"from pension_room pr " +
		"where pr.cleared = 2 "; 
	}
	
	public void changeFloorAndRoomSql(){
		if(buildId ==null||"".equals(buildId)){
			initSQL();
		}else{
			floorSQL = "select pf.id as floorId," +
			"pf.name as floorName," +
			"pf.buildName as buildName " +
			"from pension_floor pf " +
			"where pf.cleared = 2 " +
			"and pf.build_id = "+buildId;

			roomSQL = "select pr.id  as roomId," +
			"pr.name as roomName," +
			"pr.floorName as floorName," +
			"pr.buildName as buildName " +
			"from pension_room pr " +
			"join pension_floor pf " +
			"on pr.floor_id = pf.id " +
			"where pr.cleared = 2 " +
			"and pf.build_id = "+buildId;
		}
		
	}
	
	public void changeRoomSql(){
		if(floorId ==null||"".equals(floorId)){
			roomSQL =  "select pr.id  as roomId," +
			"pr.name as roomName," +
			"pr.floorName as floorName," +
			"pr.buildName as buildName " +
			"from pension_room pr " +
			"where pr.cleared = 2 "; 
		}else{
			roomSQL = "select pr.id as roomId," +
			"pr.name as roomName," +
			"pr.floorName as floorName," +
			"pr.buildName as buildName " +
			"from pension_room pr " +
			"where pr.floor_id = "+floorId;
		}
	}
	/**
	 * 清空查询条件
	 */
	public void clearSelectForm(){
		buildId = null;
		floorId = null;
		roomId = null;
		startDate = null;
		endDate = null;
	}
	/**
	 * 根据查询条件 查询对应的维修次数
	 */
	public void selectRepairCountRecords()
	{
		repairRecords = sumRepairCountService.selectRepairCountRecords(startDate,endDate,buildId,floorId,roomId);
	}
	
	public String getBuildSQL() {
		return buildSQL;
	}

	public void setBuildSQL(String buildSQL) {
		this.buildSQL = buildSQL;
	}

	public String getFloorSQL() {
		return floorSQL;
	}

	public void setFloorSQL(String floorSQL) {
		this.floorSQL = floorSQL;
	}

	public String getRoomSQL() {
		return roomSQL;
	}

	public void setRoomSQL(String roomSQL) {
		this.roomSQL = roomSQL;
	}


	public List<RepairCount> getRepairRecords() {
		return repairRecords;
	}

	public void setRepairRecords(List<RepairCount> repairRecords) {
		this.repairRecords = repairRecords;
	}

	public void setSumRepairCountService(SumRepairCountService sumRepairCountService) {
		this.sumRepairCountService = sumRepairCountService;
	}

	public SumRepairCountService getSumRepairCountService() {
		return sumRepairCountService;
	}

	public void setBuildId(Long buildId) {
		this.buildId = buildId;
	}

	public Long getBuildId() {
		return buildId;
	}

	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}

	public Long getFloorId() {
		return floorId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public Long getRoomId() {
		return roomId;
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

}
