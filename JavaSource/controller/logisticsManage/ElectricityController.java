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

import service.logisticsManage.ElectricityService;
import util.CustomException;
import util.PmsException;
import util.SystemConfig;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionAmmeter;
import domain.logisticsManage.PensionElectricityRecord;
import domain.logisticsManage.PensionOlderElectricity;

public class ElectricityController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionElectricityRecord> records = new ArrayList<PensionElectricityRecord>();
	/**
	 * 被选中的记录
	 */
	private PensionElectricityRecord selectedRow = new PensionElectricityRecord();
	// 前台增加绑定对象
	private PensionElectricityRecord pensionElectricityRecord = new PensionElectricityRecord();

	/**
	 * 电表录入起止时间 用作查询条件 add by mary.liu
	 */
	private Date inputStartTime;
	private Date inputEndTime;
	/**
	 * 是否计费标识 用作查询条件 add by mary.liu
	 */
	private Integer paidFlag;
	/**
	 * 大厦id用作查询条件
	 */
	private Long buildId;
	private String buildName;
	/**
	 * 楼层id用作查询条件
	 */
	private Long floorId;
	private String floorName;
	/**
	 * 房间id用作查询条件
	 */
	private Long roomId;
	private String roomName;
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
	 * 电表主键用于查询条件
	 */
	private Long ammeterId;
	/**
	 * 绑定关于电表信息的输入法
	 */
	private String ammeterNameSql;
	/**
	 * 录入按钮是否可用
	 */
	private boolean disInputButton = true;
	/**
	 * 注入业务
	 */
	private transient ElectricityService electricityService;
	private transient SystemConfig systemConfig;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee) SessionManager
			.getSessionAttribute(SessionManager.EMPLOYEE);
	// 电表房间输入法
	private String ammeterRoomSQL;
	// 房间电表输入法
	private String roomAmmeterSQL;

	private List<PensionOlderElectricity> pensionOlderElectricityList = new ArrayList<PensionOlderElectricity>();

	private boolean saveDetailButtonFlag = true;

	private Float sumElectricityQty = (float) 0;

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
		paidFlag = 2;//默认查询没有确认的记录 add by mary.liu
		initDate();//将电表录入结束日期设置为当天，起始时间设置为一月前 add by mary.liu
		initSql();
		selectElectricityRecordRecords();
	}

	/**
	 * 
	 * @Title: initSql
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-6 下午1:56:43
	 * @version V1.0
	 */
	public void initSql() {
		// 大厦输入法 add by justin.su 2013-12-06
		buildSQL = "select pb.id as buildId,pb.name as buildName from pension_building pb where pb.cleared = 2";
		// 楼层输入法 add by justin.su 2013-12-06
		floorSQL = "select pf.id as floorId," + "pf.name as floorName,"
				+ "pf.build_id as buildId,"
				+ // add by justin.su 增加大厦ID 2013-12-06
				"pf.buildName as buildName " + "from pension_floor pf "
				+ "where pf.cleared = 2 ";
		// 房间输入法 add by justin.su 2013-12-06
		roomSQL = "select pr.id  as roomId," + "pr.name as roomName,"
				+ "pr.floorName as floorName," + "pr.buildName as buildName "
				+ "from pension_room pr " + "where pr.cleared = 2 ";
		// 电表房间输入法
		ammeterRoomSQL = " select pa.id as ammeterId,pa.ammeter_name as ammeterName,pa.room_id as roomId,pr.name as roomName"
				+ " from pension_ammeter pa inner join pension_room pr on pa.room_id = pr.id"
				+ " where pa.valid_flag = 1 and pa.cleared = 2";
		roomAmmeterSQL = " select pa.room_id as roomId,pr.name as roomName,pa.id as ammeterId,pa.ammeter_name as ammeterName"
				+ " from pension_ammeter pa inner join pension_room pr on pa.room_id = pr.id"
				+ " where pa.valid_flag = 1 and pa.cleared = 2";
	}

	/**
	 * 
	 * @Title: changeFloorAndRoomSql
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-6 下午1:56:38
	 * @version V1.0
	 */
	public void changeFloorAndRoomSql() {
		if (buildId == null || "".equals(buildId)) {
			initSql();
		} else {
			floorSQL = "select pf.id as floorId,"
					+ "pf.name as floorName,pf.build_id as buildId,"
					+ "pf.buildName as buildName " + "from pension_floor pf "
					+ "where pf.cleared = 2 " + "and pf.build_id = " + buildId;

			roomSQL = "select pr.id  as roomId," + "pr.name as roomName,"
					+ "pr.floorName as floorName,"
					+ "pr.buildName as buildName " + "from pension_room pr "
					+ "join pension_floor pf " + "on pr.floor_id = pf.id "
					+ "where pr.cleared = 2 " + "and pf.build_id = " + buildId;
		}

	}

	/**
	 * 
	 * @Title: changeRoomSql
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-6 下午1:56:34
	 * @version V1.0
	 */
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
	 * 
	 * @Title: selectElectricityRecordRecords
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-6 下午1:56:29
	 * @version V1.0
	 */
	public void selectElectricityRecordRecords() {
		disInputButton = true;
		selectedRow = null;
		if (buildName == null || "".equals(buildName.trim())) {
			buildId = null;
		}
		if (floorName == null || "".equals(floorName.trim())) {
			floorId = null;
		}
		if (roomName == null || "".equals(roomName.trim())) {
			roomId = null;
		}
		records = electricityService.selectElectricityRecordRecords(inputStartTime,inputEndTime,
				paidFlag,buildId,floorId, roomId, ammeterId);
	}

	/**
	 * 
	 * @Title: clearSelectForm
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-10 上午9:43:14
	 * @version V1.0
	 */
	public void clearSelectForm() {
		this.initDate();//清空，将电表录入起止时间恢复为默认
		paidFlag = 2;//清空，将是否确认恢复为默认
		ammeterId = null;
		buildId = null;
		floorId = null;
		roomId = null;
	}

	/**
	 * 
	 * @Title: selectRecord
	 * @param @param e
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-9 下午8:15:51
	 * @version V1.0
	 */
	public void selectRecord(SelectEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		sumElectricityQty = (float) 0;
		disInputButton = false;
		pensionOlderElectricityList = electricityService
				.selectPensionOlderElectricityList(selectedRow);
		sumElectricityQty = selectedRow.getDegreesNumber()
				- selectedRow.getLastDegreesNumber();
		if (pensionOlderElectricityList.size() > 0) {
			if (selectedRow.getEnsureFlag() == 1) {
				saveDetailButtonFlag = true;
			} else {
				saveDetailButtonFlag = false;
			}
		} else {
			saveDetailButtonFlag = true;
		}
		try {
			// 根据系统参数设置电费价表主键，获取价表中电费金额 　add by mary.liu 2013-12-16
			Float electricityItempurse = electricityService
					.selectElectricityItemPurse();
			for (PensionOlderElectricity poe : pensionOlderElectricityList) {
				poe.setUnitPrice(electricityItempurse);
				poe.setInputuserId(curEmployee.getId());
				poe.setInputuserName(curEmployee.getName());
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (PmsException e1) {
			e1.printStackTrace();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					e1.getMessage(), " ");
			context.addMessage(null, message);
		}
	}
	
	/**
	 * 将结束日期设置为今天，起始日期设置为一个月前的今天
	 */
	public void initDate(){
		
		Calendar calendar=Calendar.getInstance();
		inputEndTime=new Date();
		calendar.setTime(inputEndTime);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		inputEndTime=calendar.getTime();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		inputStartTime=calendar.getTime();
  
	}

	/**
	 * 
	 * @Title: unSelectRecord
	 * @param @param e
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-9 下午8:15:55
	 * @version V1.0
	 */
	public void unSelectRecord(UnselectEvent e) {
		disInputButton = true;
		pensionOlderElectricityList = new ArrayList<PensionOlderElectricity>();
		saveDetailButtonFlag = true;
	}

	/**
	 * 
	 * @Title: initAddParam
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-6 下午3:04:31
	 * @version V1.0
	 */
	public void initAddParam() {
		pensionElectricityRecord = new PensionElectricityRecord();
		pensionElectricityRecord.setInputuserId(curEmployee.getId());
		pensionElectricityRecord.setInputTime(new Date());
		pensionElectricityRecord.setInputuserName(curEmployee.getName());
	}

	/**
	 * 
	 * @Title: saveRecord
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-9 下午2:18:49
	 * @version V1.0
	 */
	public void saveRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			PensionElectricityRecord aaa = electricityService
					.getNearistRecord(pensionElectricityRecord.getAmmeterId());
			if (null == aaa) {
				PensionAmmeter pat = electricityService
						.getPensionAmmeter(pensionElectricityRecord);
				if (null == pat) {
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_WARN, "请先维护电表信息！", " ");
					context.addMessage(null, message);
				} else {
					if (null == pat.getInitDegreeNumber()
							|| null == pat.getInitDatetime()) {
						FacesMessage message = new FacesMessage(
								FacesMessage.SEVERITY_WARN,
								"请先维护电表的初始读数和初始日期！", " ");
						context.addMessage(null, message);
					} else {
						if (pensionElectricityRecord.getDegreesNumber() < pat
								.getInitDegreeNumber()) {
							FacesMessage message = new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"本次读数不能小于电表的初始读数"
											+ pat.getInitDegreeNumber() + "！",
									" ");
							context.addMessage(null, message);
						} else {
							electricityService
									.insertPensionElectricityRecord(pensionElectricityRecord);
							selectElectricityRecordRecords();
							pensionElectricityRecord = new PensionElectricityRecord();
							initAddParam();
							FacesMessage message = new FacesMessage(
									FacesMessage.SEVERITY_INFO, "新增成功！", "新增成功");
							context.addMessage(null, message);
						}

					}
				}

			} else {
				if (!checkDate(pensionElectricityRecord.getInputTime(),
						aaa.getInputTime())) {
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_WARN, "录入日期不能在上一条记录的录入日期之前！",
							" ");
					context.addMessage(null, message);
				} else {
					if (pensionElectricityRecord.getDegreesNumber() < aaa
							.getDegreesNumber()) {
						FacesMessage message = new FacesMessage(
								FacesMessage.SEVERITY_WARN,
								"该电表的本次读数不能小于电表的上次读数" + aaa.getDegreesNumber()
										+ "！", " ");
						context.addMessage(null, message);
					} else {
						electricityService
								.insertPensionElectricityRecord(pensionElectricityRecord);
						selectElectricityRecordRecords();
						pensionElectricityRecord = new PensionElectricityRecord();
						initAddParam();
						FacesMessage message = new FacesMessage(
								FacesMessage.SEVERITY_INFO, "新增成功！", "新增成功");
						context.addMessage(null, message);
					}
				}
			}
		} catch (CustomException e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					e.getMessage(), " ");
			context.addMessage(null, message);
			e.printStackTrace();
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					e.getMessage(), " ");
			context.addMessage(null, message);
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: checkDate
	 * @param @param date1
	 * @param @param date2
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-10 下午6:02:51
	 * @version V1.0
	 */
	public boolean checkDate(Date date1, Date date2) {
		if (date1.before(date2)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @Title: updateRecord
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-9 下午2:47:51
	 * @version V1.0
	 */
	public void updateRecord() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (selectedRow.getDegreesNumber() < (selectedRow
					.getLastDegreesNumber())) {
				request.addCallbackParam("canEditFlag", false);
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "该电表的修改读数不能小于上次读数"
								+ selectedRow.getLastDegreesNumber() + "！", " ");
				context.addMessage(null, message);
			} else {
				electricityService.updatePensionElectricityRecord(selectedRow);
				records = electricityService.selectElectricityRecordRecords(
						inputStartTime,inputEndTime,paidFlag,
						buildId, floorId, roomId, ammeterId);
				pensionOlderElectricityList = electricityService
						.selectPensionOlderElectricityList(selectedRow);
				request.addCallbackParam("canEditFlag", true);
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "修改成功！", "修改成功");
				context.addMessage(null, message);
			}
		} catch (Exception e) {
			request.addCallbackParam("canEditFlag", false);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					e.getMessage(), " ");
			context.addMessage(null, message);
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: saveDetail
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-10 上午9:43:02
	 * @version V1.0
	 */
	public void saveDetail() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			electricityService
					.insertpensionOlderElectricityList(pensionOlderElectricityList);
			pensionOlderElectricityList = electricityService
					.selectPensionOlderElectricityList(selectedRow);
			// 根据系统参数设置电费价表主键，获取价表中电费金额 　add by mary.liu 2013-12-16
			Float electricityItempurse = electricityService
					.selectElectricityItemPurse();
			for (PensionOlderElectricity poe : pensionOlderElectricityList) {
				poe.setUnitPrice(electricityItempurse);
				poe.setInputuserId(curEmployee.getId());
				poe.setInputuserName(curEmployee.getName());
			}
			records = electricityService.selectElectricityRecordRecords(
					inputStartTime,inputEndTime,paidFlag,
					buildId, floorId, roomId, ammeterId);
			pensionOlderElectricityList = new ArrayList<PensionOlderElectricity>();
			request.addCallbackParam("canSaveFlag", true);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"确定成功！", "确定成功");
			context.addMessage(null, message);
			saveDetailButtonFlag = true;
		} catch (PmsException e) {
			e.printStackTrace();
			request.addCallbackParam("canSaveFlag", false);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					e.getMessage(), " ");
			context.addMessage(null, message);
			saveDetailButtonFlag = false;
		} catch (Exception e) {
			e.printStackTrace();
			request.addCallbackParam("canSaveFlag", false);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					e.getMessage(), " ");
			context.addMessage(null, message);
			saveDetailButtonFlag = false;
		}
	}

	/**
	 * 
	 * @Title: checkSum
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-11 上午11:10:44
	 * @version V1.0
	 */
	public void checkSum() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow.getEnsureFlag() == 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已经确认过的记录不可以再次确认！", " ");
			context.addMessage(null, message);
			request.addCallbackParam("canShowFlag", false);
		} else {
			Float checkSum = (float) 0;
			if (pensionOlderElectricityList.size() > 0) {
				for (PensionOlderElectricity poe : pensionOlderElectricityList) {
					checkSum = checkSum + poe.getEveryDegrees();
				}
			}
			if (sumElectricityQty.equals(checkSum)) {
				request.addCallbackParam("canShowFlag", true);
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "所有老人的用电量之和不等于总用电量！", " ");
				context.addMessage(null, message);
				request.addCallbackParam("canShowFlag", false);
			}
		}

	}

	public void setRoomNameSql(String roomNameSql) {
		this.ammeterNameSql = roomNameSql;
	}

	public String getRoomNameSql() {
		return ammeterNameSql;
	}

	public void setElectricityService(ElectricityService electricityService) {
		this.electricityService = electricityService;
	}

	public ElectricityService getElectricityService() {
		return electricityService;
	}

	public void setAmmeterId(Long ammeterId) {
		this.ammeterId = ammeterId;
	}

	public Long getAmmeterId() {
		return ammeterId;
	}

	public void setAmmeterNameSql(String ammeterNameSql) {
		this.ammeterNameSql = ammeterNameSql;
	}

	public String getAmmeterNameSql() {
		return ammeterNameSql;
	}

	public Long getBuildId() {
		return buildId;
	}

	public void setBuildId(Long buildId) {
		this.buildId = buildId;
	}

	public Long getFloorId() {
		return floorId;
	}

	public void setFloorId(Long floorId) {
		this.floorId = floorId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
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

	public void setDisInputButton(boolean disInputButton) {
		this.disInputButton = disInputButton;
	}

	public boolean isDisInputButton() {
		return disInputButton;
	}

	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}

	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}

	/**
	 * @return the buildName
	 */
	public String getBuildName() {
		return buildName;
	}

	/**
	 * @param buildName
	 *            the buildName to set
	 */
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	/**
	 * @return the floorName
	 */
	public String getFloorName() {
		return floorName;
	}

	/**
	 * @param floorName
	 *            the floorName to set
	 */
	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	/**
	 * @return the roomName
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * @param roomName
	 *            the roomName to set
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * @return the ammeterRoomSQL
	 */
	public String getAmmeterRoomSQL() {
		return ammeterRoomSQL;
	}

	/**
	 * @param ammeterRoomSQL
	 *            the ammeterRoomSQL to set
	 */
	public void setAmmeterRoomSQL(String ammeterRoomSQL) {
		this.ammeterRoomSQL = ammeterRoomSQL;
	}

	/**
	 * @return the roomAmmeterSQL
	 */
	public String getRoomAmmeterSQL() {
		return roomAmmeterSQL;
	}

	/**
	 * @param roomAmmeterSQL
	 *            the roomAmmeterSQL to set
	 */
	public void setRoomAmmeterSQL(String roomAmmeterSQL) {
		this.roomAmmeterSQL = roomAmmeterSQL;
	}

	/**
	 * @return the records
	 */
	public List<PensionElectricityRecord> getRecords() {
		return records;
	}

	/**
	 * @param records
	 *            the records to set
	 */
	public void setRecords(List<PensionElectricityRecord> records) {
		this.records = records;
	}

	/**
	 * @return the selectedRow
	 */
	public PensionElectricityRecord getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow
	 *            the selectedRow to set
	 */
	public void setSelectedRow(PensionElectricityRecord selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return the pensionElectricityRecord
	 */
	public PensionElectricityRecord getPensionElectricityRecord() {
		return pensionElectricityRecord;
	}

	/**
	 * @param pensionElectricityRecord
	 *            the pensionElectricityRecord to set
	 */
	public void setPensionElectricityRecord(
			PensionElectricityRecord pensionElectricityRecord) {
		this.pensionElectricityRecord = pensionElectricityRecord;
	}

	/**
	 * @return the pensionOlderElectricityList
	 */
	public List<PensionOlderElectricity> getPensionOlderElectricityList() {
		return pensionOlderElectricityList;
	}

	/**
	 * @param pensionOlderElectricityList
	 *            the pensionOlderElectricityList to set
	 */
	public void setPensionOlderElectricityList(
			List<PensionOlderElectricity> pensionOlderElectricityList) {
		this.pensionOlderElectricityList = pensionOlderElectricityList;
	}

	/**
	 * @return the systemConfig
	 */
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	/**
	 * @param systemConfig
	 *            the systemConfig to set
	 */
	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	/**
	 * @return the saveDetailButtonFlag
	 */
	public boolean isSaveDetailButtonFlag() {
		return saveDetailButtonFlag;
	}

	/**
	 * @param saveDetailButtonFlag
	 *            the saveDetailButtonFlag to set
	 */
	public void setSaveDetailButtonFlag(boolean saveDetailButtonFlag) {
		this.saveDetailButtonFlag = saveDetailButtonFlag;
	}

	public Date getInputStartTime() {
		return inputStartTime;
	}

	public void setInputStartTime(Date inputStartTime) {
		this.inputStartTime = inputStartTime;
	}

	public Date getInputEndTime() {
		return inputEndTime;
	}

	public void setInputEndTime(Date inputEndTime) {
		this.inputEndTime = inputEndTime;
	}

	public Integer getPaidFlag() {
		return paidFlag;
	}

	public void setPaidFlag(Integer paidFlag) {
		this.paidFlag = paidFlag;
	}

}
