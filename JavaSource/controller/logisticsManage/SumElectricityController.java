package controller.logisticsManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import domain.logisticsManage.PensionElectricityRecordExtend;

import service.logisticsManage.SumElectricityService;

public class SumElectricityController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7653069252349336476L;
	/**
	 * 
	 */
	private transient SumElectricityService sumElectricityService;
	/**
	 * 所有记录列表
	 */
	private List<PensionElectricityRecordExtend> records = new ArrayList<PensionElectricityRecordExtend>();
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

		initSQL();
		Calendar calendar = Calendar.getInstance();
		endDate = new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		endDate = calendar.getTime();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		startDate = calendar.getTime();
		//selectElectricityConsumptionRecords();

	}

	/**
	 * 初始化前台输入法的sql
	 */
	public void initSQL() {
		buildSQL = "select pb.id as buildId,pb.name as buildName from pension_building pb where pb.cleared = 2";
		floorSQL = "select pf.id as floorId," + "pf.name as floorName,"
				+ "pf.buildName as buildName " + "from pension_floor pf "
				+ "where pf.cleared = 2 ";

		roomSQL = "select pr.id  as roomId," + "pr.name as roomName,"
				+ "pr.floorName as floorName," + "pr.buildName as buildName "
				+ "from pension_room pr " + "where pr.cleared = 2 ";
	}

	public void changeFloorAndRoomSql() {
		if (buildId == null || "".equals(buildId)) {
			initSQL();
		} else {
			floorSQL = "select pf.id as floorId," + "pf.name as floorName,"
					+ "pf.buildName as buildName " + "from pension_floor pf "
					+ "where pf.cleared = 2 " + "and pf.build_id = " + buildId;

			roomSQL = "select pr.id  as roomId," + "pr.name as roomName,"
					+ "pr.floorName as floorName,"
					+ "pr.buildName as buildName " + "from pension_room pr "
					+ "join pension_floor pf " + "on pr.floor_id = pf.id "
					+ "where pr.cleared = 2 " + "and pf.build_id = " + buildId;
		}

	}

	public void changeRoomSql() {
		if (floorId == null || "".equals(floorId)) {
			roomSQL = "select pr.id  as roomId," + "pr.name as roomName,"
					+ "pr.floorName as floorName,"
					+ "pr.buildName as buildName " + "from pension_room pr "
					+ "where pr.cleared = 2 ";
		} else {
			roomSQL = "select pr.id as roomId," + "pr.name as roomName,"
					+ "pr.floorName as floorName,"
					+ "pr.buildName as buildName " + "from pension_room pr "
					+ "where pr.floor_id = " + floorId;
		}
	}

	/**
	 * 清空查询条件
	 */
	public void clearSelectForm() {
		buildId = null;
		floorId = null;
		roomId = null;
		startDate = null;
		endDate = null;
	}

	/**
	 * 根据查询条件 查询对应的维修次数
	 */
//<<<<<<< .mine
//	public void selectElectricityConsumptionRecords()
//	{
//		//setRecords(sumElectricityService.selectElectricityConsumptionRecords(startDate,endDate,buildId,floorId,roomId));
//=======
//	public void selectElectricityConsumptionRecords() {
//		// setRecords(sumElectricityService.selectElectricityConsumptionRecords(startDate,endDate,buildId,floorId,roomId));
//>>>>>>> .r1813
//	}

	/**
	 * 导出为excel文件
	 */
	public void export() {

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

	public void setSumElectricityService(
			SumElectricityService sumElectricityService) {
		this.sumElectricityService = sumElectricityService;
	}

	public SumElectricityService getSumElectricityService() {
		return sumElectricityService;
	}

	public void setRecords(List<PensionElectricityRecordExtend> records) {
		this.records = records;
	}

	public List<PensionElectricityRecordExtend> getRecords() {
		return records;
	}

}
