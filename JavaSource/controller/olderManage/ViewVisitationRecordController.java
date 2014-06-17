package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.olderManage.PensionVisitrecordExtend;
import service.olderManage.ViewVisitationRecordService;
import service.receptionManage.PensionOlderDomain;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.olderManage.PensionVisitrecord;

public class ViewVisitationRecordController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionOlderDomain> records = new ArrayList<PensionOlderDomain>();
	/**
	 * 每条老人记录 都对应多条的探访记录 visitRecords 用来在前台显示该探访记录
	 */
	private List<PensionVisitrecordExtend> visitRecords = new ArrayList<PensionVisitrecordExtend>();
	/**
	 * 被选中的行
	 */
	private PensionOlderDomain selectedRow = new PensionOlderDomain();
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 是否探访
	 */
	private String visitFlag;
	/**
	 * 探访类型
	 */
	private String visitCatalog = "1";
	/**
	 * 查询条件 绑定请假返院时间或入住时间或就医返院时间
	 */
	private Date recordTime;
	/**
	 * 查询条件,是pension_leave,pension_hospitalregister和pension_livingrecord表的主键
	 */
	private Long keyId;
	/**
	 * 探访类型 1入住 2请假 3就医
	 */
	private String visitTypeStr;

	private String visitType;
	/**
	 * 用来绑定前台的探访类型下拉框
	 */
	private boolean visittypeFlag = false;
	/**
	 * 分别绑定前台的置为开始时间 和置为结束时间按钮
	 */
	private boolean disStartAndEndButton = true;
	/**
	 * 绑定前台的请假记录输入法 用于显示出所有的需要探访的请假记录
	 */
	private String pensionLeaveSql;
	/**
	 * 绑定前台的就医记录输入法 用于显示出所有的需要探访的就医记录
	 */
	private String pensionMedicalSql;
	/**
	 * 用来控制查询框中的老人的inputText 1为不显示任何 2 为显示请假输入法 2 为显示就医输入法
	 */
	private Integer selectedLeaveAndLivingAndMedicalRenderedFlag = 1;
	/**
	 * 注入业务
	 */
	private transient ViewVisitationRecordService viewVisitationRecordService;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee) SessionManager
			.getSessionAttribute(SessionManager.EMPLOYEE);

	private String timeLable;

	/**
	 * @Description:初始化数据方法.
	 * @return: void
	 * @exception:
	 * @throws:
	 * @version: 1.0
	 * @author: Tim li
	 * @throws PmsException
	 */
	@PostConstruct
	public void init() {
		initSql();
		timeLable = "入住时间";
		records = viewVisitationRecordService.selectOlderRecords(startDate,
				endDate, visitCatalog, visitFlag);
	}

	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		if (selectedRow.getId() != null) {
			pensionMedicalSql = "select phr.id as keyId,DATE_FORMAT(phr.backTime, '%Y-%m-%d %H:%i') as recordTime,phr.hospitalizeIssue from pension_hospitalizeregister phr  "
					+ "where phr.older_id=" + selectedRow.getId();
			pensionLeaveSql = "select pl.id as keyId,DATE_FORMAT(pl.realBackTime, '%Y-%m-%d %H:%i') as recordTime,pl.escortName,pl.leaveReason from pension_leave pl "
					+ "where pl.older_id=" + selectedRow.getId();
		} else {
			pensionMedicalSql = "select phr.id as keyId,phr.backTime as recordTime,phr.hospitalizeIssue from pension_hospitalizeregister phr  ";
			pensionLeaveSql = "select pl.id as keyId,pl.realBackTime as recordTime,pl.escortName,pl.leaveReason from pension_leave pl ";

		}

	}

	/**
	 * 查询老人记录
	 */
	public void selectOlderRecords() {
		records = viewVisitationRecordService.selectOlderRecords(startDate,
				endDate, visitCatalog, visitFlag);
		List<PensionOlderDomain> removeList = new ArrayList<PensionOlderDomain>();
		if (visitFlag.equals("1")) {
			for (PensionOlderDomain older : records) {
				List<PensionVisitrecord> unVisitRecords = viewVisitationRecordService
						.selectUnVisitRecords(older.getKeyId());
				if (unVisitRecords.size() > 0) {
					removeList.add(older);
				}
			}
		}
		records.removeAll(removeList);
	}

	/**
	 * 查询被选中行对应的探访记录
	 * 
	 * @param selectedRow
	 */
	public void selectVisitRecords(PensionOlderDomain selectedRow) {
		if (visitCatalog.equals("1")) {
			visitType = "1";
			visitTypeStr = "入住探访";
			selectedLeaveAndLivingAndMedicalRenderedFlag = 1;
		} else if (visitCatalog.equals("2")) {
			visitType = "2";
			visitTypeStr = "请假返院探访";
			selectedLeaveAndLivingAndMedicalRenderedFlag = 2;
		} else {
			visitType = "3";
			visitTypeStr = "就医返院探访";
			selectedLeaveAndLivingAndMedicalRenderedFlag = 3;
		}
		keyId = null;
		setRecordTime(null);
		this.selectedRow = selectedRow;
		initSql();
		selectVisitRecords();
	}

	public void selectVisitRecords() {
		if (recordTime == null) {
			// 如果探访类型为入住探访 那么keyId就为该老人的入住记录的主键
			if (visitCatalog.equals("1")) {
				keyId = viewVisitationRecordService.getLivingId(selectedRow
						.getId());

			} else {
				keyId = null;
			}
		}
		visitRecords = viewVisitationRecordService.selectVisitRecords(keyId,
				visitCatalog, selectedRow.getId());
	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {

	}

	/**
	 * 点击前台的维修类型 根据具体类型 来改变查询的布局
	 */
	public void changeSelectDialog() {
		// 员工
		if (!visitType.equals("1")) {
			selectedLeaveAndLivingAndMedicalRenderedFlag = Integer
					.valueOf(visitType);
			keyId = null;
			setRecordTime(null);
			selectVisitRecords();
		}
	}

	/**
	 * 清空选择条件
	 */
	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		visitFlag = "";
		visitCatalog = "1";
		timeLable = "入住时间";
	}

	public void changFlag() {
		if (visitCatalog.equals("1")) {
			timeLable = "入住时间";
		} else if (visitCatalog.equals("2")) {
			timeLable = "请假返院时间";
		} else {
			timeLable = "就医返院时间";
		}
	}

	public void setViewVisitationRecordService(
			ViewVisitationRecordService viewVisitationRecordService) {
		this.viewVisitationRecordService = viewVisitationRecordService;
	}

	public ViewVisitationRecordService getViewVisitationRecordService() {
		return viewVisitationRecordService;
	}

	public void setRecords(List<PensionOlderDomain> records) {
		this.records = records;
	}

	public List<PensionOlderDomain> getRecords() {
		return records;
	}

	public void setVisitRecords(List<PensionVisitrecordExtend> visitRecords) {
		this.visitRecords = visitRecords;
	}

	public List<PensionVisitrecordExtend> getVisitRecords() {
		return visitRecords;
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

	public void setSelectedRow(PensionOlderDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionOlderDomain getSelectedRow() {
		return selectedRow;
	}

	public void setDisStartAndEndButton(boolean disStartAndEndButton) {
		this.disStartAndEndButton = disStartAndEndButton;
	}

	public boolean isDisStartAndEndButton() {
		return disStartAndEndButton;
	}

	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}

	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}

	public void setVisittypeFlag(boolean visittypeFlag) {
		this.visittypeFlag = visittypeFlag;
	}

	public boolean isVisittypeFlag() {
		return visittypeFlag;
	}

	public void setPensionLeaveSql(String pensionLeaveSql) {
		this.pensionLeaveSql = pensionLeaveSql;
	}

	public String getPensionLeaveSql() {
		return pensionLeaveSql;
	}

	public void setPensionMedicalSql(String pensionMedicalSql) {
		this.pensionMedicalSql = pensionMedicalSql;
	}

	public String getPensionMedicalSql() {
		return pensionMedicalSql;
	}

	public void setKeyId(Long keyId) {
		this.keyId = keyId;
	}

	public Long getKeyId() {
		return keyId;
	}

	public void setSelectedLeaveAndLivingAndMedicalRenderedFlag(
			Integer selectedLeaveAndLivingAndMedicalRenderedFlag) {
		this.selectedLeaveAndLivingAndMedicalRenderedFlag = selectedLeaveAndLivingAndMedicalRenderedFlag;
	}

	public Integer getSelectedLeaveAndLivingAndMedicalRenderedFlag() {
		return selectedLeaveAndLivingAndMedicalRenderedFlag;
	}

	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}

	public Date getRecordTime() {
		return recordTime;
	}

	public void setVisitFlag(String visitFlag) {
		this.visitFlag = visitFlag;
	}

	public String getVisitFlag() {
		return visitFlag;
	}

	public void setVisitCatalog(String visitCatalog) {
		this.visitCatalog = visitCatalog;
	}

	public String getVisitCatalog() {
		return visitCatalog;
	}

	public void setTimeLable(String timeLable) {
		this.timeLable = timeLable;
	}

	public String getTimeLable() {
		return timeLable;
	}

	public void setVisitTypeStr(String visitTypeStr) {
		this.visitTypeStr = visitTypeStr;
	}

	public String getVisitTypeStr() {
		return visitTypeStr;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getVisitType() {
		return visitType;
	}

}
