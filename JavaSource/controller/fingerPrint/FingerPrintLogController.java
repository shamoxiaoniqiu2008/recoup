package controller.fingerPrint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import domain.fingerPrint.PensionFingerPrintDaily;

import service.fingerPrint.FingerPrintLogService;
import util.DateUtil;

/**
 * 
 * @author:bill
 * @version: 1.0
 * @date:2013-12-3
 */

public class FingerPrintLogController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient FingerPrintLogService fingerPrintLogService;

	/**
	 * 指纹类型(查询)
	 */
	private int type = 1;
	/**
	 * 房间号(查询)
	 */
	private String roomNumber;
	/**
	 * 人员名称(查询)
	 */
	private String peopleName;
	/**
	 * 开始时间(查询)
	 */
	private Date startTime;
	/**
	 * 结束时间(查询)
	 */
	private Date endTime;
	/**
	 * 显示是否渲染
	 */
	private Boolean randerFalg;
	private String randerName;
	/**
	 * 指纹日志列表
	 */
	private List<PensionFingerPrintDaily> fingerPrintList = new ArrayList<PensionFingerPrintDaily>();

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		clearSearchForm();
		endTime = new Date();
		startTime = DateUtil.getBeforeDay(endTime, 7);
		searchFingerPrints();
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		type = 1;
		randerFalg = true;
		setRanderName("员工姓名");
		roomNumber = null;
		peopleName = null;
		startTime = null;
		endTime = null;
	}

	/**
	 * 更新渲染元素
	 */
	public void updateFlag() {
		if (type == 1) {
			randerFalg = true;
			setRanderName("员工姓名");
		} else {
			randerFalg = false;
			setRanderName("老人姓名");
		}
		searchFingerPrints();
	}

	/**
	 * 查询指纹机设备列表
	 */
	public void searchFingerPrints() {
		if (type == 1) {
			fingerPrintList = fingerPrintLogService.selectByEmployee(
					roomNumber, peopleName, startTime, endTime);
		} else {
			fingerPrintList = fingerPrintLogService.selectByOlder(roomNumber,
					peopleName, startTime, endTime);
		}
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	public String getPeopleName() {
		return peopleName;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setFingerPrintList(List<PensionFingerPrintDaily> fingerPrintList) {
		this.fingerPrintList = fingerPrintList;
	}

	public List<PensionFingerPrintDaily> getFingerPrintList() {
		return fingerPrintList;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setRanderFalg(Boolean randerFalg) {
		this.randerFalg = randerFalg;
	}

	public Boolean getRanderFalg() {
		return randerFalg;
	}

	public void setRanderName(String randerName) {
		this.randerName = randerName;
	}

	public String getRanderName() {
		return randerName;
	}

	public FingerPrintLogService getFingerPrintLogService() {
		return fingerPrintLogService;
	}

	public void setFingerPrintLogService(
			FingerPrintLogService fingerPrintLogService) {
		this.fingerPrintLogService = fingerPrintLogService;
	}

}
