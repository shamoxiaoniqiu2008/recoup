package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.olderManage.PensionVisitrecordExtend;
import service.olderManage.VisitationHandleService;
import service.receptionManage.PensionOlderDomain;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.fingerPrint.PensionFingerPrintDaily;
import domain.olderManage.PensionVisitrecord;

public class VisitationHandleController implements Serializable {

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
	 * 当前员工的探访指纹记录
	 */
	private List<PensionFingerPrintDaily> printRecords = new ArrayList<PensionFingerPrintDaily>();
	/**
	 * 被选中的行
	 */
	private PensionOlderDomain selectedRow = new PensionOlderDomain();
	/**
	 * 当前员工对被选中的老人的探访记录
	 */
	private PensionVisitrecord enteredVisitrecord = new PensionVisitrecord();
	/**
	 * 被选中的指纹记录
	 */
	private PensionFingerPrintDaily[] selectedPrintRecords;
	/**
	 * 老人Id
	 */
	private Long olderId;
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
	private Integer visittype = 1;
	/**
	 * 使用标记 用作查询指纹的条件
	 */
	private Integer usedFlag = 2;
	/**
	 * 该页面的录入探访记录是否通过指纹验证 true为是 false为否
	 */
	private boolean printFlag = true;
	/**
	 * 开始日期 查询指纹记录的查询条件
	 */
	private Date visitStartDate;
	/**
	 * 结束日期 查询指纹记录的查询条件
	 */
	private Date visitEndDate;
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
	private transient VisitationHandleService visitationHandleService;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee) SessionManager
			.getSessionAttribute(SessionManager.EMPLOYEE);

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
	public void init() throws PmsException {
		// 获取消息源发给本页面的参数
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		initSql();
		// 获取老人探访的老人id
		String olderId = paramsMap.get("olderId");
		if (olderId != null) {
			this.olderId = Long.valueOf(olderId);
			// 获取探访类型
			visittype = Integer.valueOf(paramsMap.get("visittype"));
			selectedLeaveAndLivingAndMedicalRenderedFlag = visittype;
			keyId = Long.valueOf(paramsMap.get("keyId"));
			recordTime = visitationHandleService.fillRecordTime(keyId,
					visittype);

		} else {
			this.olderId = null;
		}
		records = visitationHandleService.selectOlderRecords(this.olderId,
				curEmployee.getId(), curEmployee.getDeptId());
		// 之后 将其至空
		this.olderId = null;
		printFlag = isPrint();// 初始化printFlag 即：是否采用指纹机制
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

		visittype = 1;
		keyId = null;
		setRecordTime(null);
		selectedLeaveAndLivingAndMedicalRenderedFlag = 1;
		records = visitationHandleService.selectOlderRecords(olderId,
				curEmployee.getId(), curEmployee.getDeptId());

	}

	/**
	 * 查询被选中行对应的探访记录
	 * 
	 * @param selectedRow
	 */
	public void selectVisitRecords(PensionOlderDomain selectedRow) {

		this.selectedRow = selectedRow;
		initSql();
		selectVisitRecords();

	}

	public void selectVisitRecords() {

		if (recordTime == null) {
			// 如果探访类型为入住探访 那么keyId就为该老人的入住记录的主键
			if (visittype == 1) {
				keyId = visitationHandleService
						.getLivingId(selectedRow.getId());

			} else {
				keyId = null;
			}
		}
		visitRecords = visitationHandleService.selectVisitRecords(keyId,
				visittype, selectedRow.getId());
		visitStartDate = selectedRow.getVisitTime();
		visitEndDate = new Date();
	}

	/**
	 * 将要录入的探访记录查询出来，并将当前员工对当前老人的探访指纹记录查询出来
	 */
	public void selectVisitAndPrintRecord() {

		RequestContext request = RequestContext.getCurrentInstance();
		Long curDeptId = curEmployee.getDeptId();
		boolean entryFlag = false;
		for (PensionVisitrecordExtend visitRecord : visitRecords) {
			if (visitRecord.getDeptId() == curDeptId) {

				enteredVisitrecord.setDeptId(curDeptId);
				enteredVisitrecord.setId(visitRecord.getId());
				enteredVisitrecord.setVisitorId(curEmployee.getId());
				enteredVisitrecord.setVisittype(visitRecord.getVisittype());
				enteredVisitrecord.setKeyId(visitRecord.getKeyId());
				entryFlag = true;
				break;
			}

		}
		if (entryFlag) {

			if (keyId != null) {
				// 如果是指纹机制 就查询指纹记录
				if (printFlag) {
					selectPrintRecord();
				}
			} else {
				if (visittype == 1) {
					// 如果是指纹机制 就查询指纹记录
					if (printFlag) {
						selectPrintRecord();
					}
				} else if (visittype == 2) {
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_WARN, "请选择要录入探访记录的请假记录！",
							"请选择要录入探访记录的请假记录！");
					context.addMessage(null, message);
					request.addCallbackParam("entryFlag", false);
					return;
				} else if (visittype == 3) {
					FacesContext context = FacesContext.getCurrentInstance();
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_WARN, "请选择要录入探访记录的就医记录！",
							"请选择要录入探访记录的就医记录！");
					context.addMessage(null, message);
					request.addCallbackParam("entryFlag", false);
					return;
				}
			}

		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"您没有该老人的探访任务，所以不能进行探访录入！", "您没有该老人的探访任务，所以不能进行探访录入！");
			context.addMessage(null, message);
		}
		request.addCallbackParam("entryFlag", entryFlag);
		request.addCallbackParam("printFlag", printFlag);

	}

	public void selectPrintRecord() {
		disStartAndEndButton = true;
		selectedPrintRecords = null;
		printRecords = visitationHandleService.selectPrintRecords(selectedRow,
				curEmployee, usedFlag, visitStartDate, visitEndDate);
	}

	/**
	 * 录入探访记录
	 * 
	 * @throws PmsException
	 */
	public void entryVisitRecord() {

		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (enteredVisitrecord.getLeavetime() != null
				&& enteredVisitrecord.getVisittime() != null) {
			// 更新到数据库中
			visitationHandleService.entryVisitRecord(enteredVisitrecord);
			// 更新消息
			visitationHandleService.updateApprove(keyId, curEmployee.getId(),
					curEmployee.getDeptId());
			// 重查
			visitRecords = visitationHandleService.selectVisitRecords(keyId,
					visittype, selectedRow.getId());
			// 将时间段内的记录置为可用
			BeUsed(enteredVisitrecord.getVisittime(),
					enteredVisitrecord.getLeavetime());
			enteredVisitrecord = new PensionVisitrecord();
			visittypeFlag = false;
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"保存成功！", "保存成功");
			context.addMessage(null, message);
			request.addCallbackParam("success", true);

		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"请输入探访时间！", "请输入探访时间！");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		}

	}

	// 使开始和结束时间之内的所有指纹记录 都置为已用

	private void BeUsed(Date startTime, Date endTime) {
		Long stime = startTime.getTime();
		Long etime = endTime.getTime();
		for (PensionFingerPrintDaily record : printRecords) {
			Long time = record.getAnLogDate().getTime();
			if (time >= stime && time <= etime) {
				record.setUsed(1);
				visitationHandleService.beUsed(record);
			}
		}
		selectPrintRecord();
	}

	/**
	 * 将被选中的指纹录入时间置为 开始探访时间
	 * 
	 * @throws PmsException
	 */
	public void beVisitTimes() throws PmsException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedPrintRecords != null && selectedPrintRecords.length == 2) {
			Date startTime, endTime;
			if (selectedPrintRecords[0].getAnLogDate().getTime() < selectedPrintRecords[1]
					.getAnLogDate().getTime()) {
				startTime = selectedPrintRecords[0].getAnLogDate();
				endTime = selectedPrintRecords[1].getAnLogDate();
			} else {
				startTime = selectedPrintRecords[1].getAnLogDate();
				endTime = selectedPrintRecords[0].getAnLogDate();
			}
			Long longestVisitTime = visitationHandleService
					.getLongestVisitTime();
			if ((endTime.getTime() - startTime.getTime()) / (1000 * 60) <= longestVisitTime) {
				enteredVisitrecord.setVisittime(startTime);
				enteredVisitrecord.setLeavetime(endTime);
			} else {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "指纹间隔必须小于限定的时间："
								+ longestVisitTime + "分钟！", "指纹间隔必须小于限定的时间："
								+ longestVisitTime + "分钟！");
				context.addMessage(null, message);
			}
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"请选择两条指纹记录作为开始探访时间和截止探访时间！", "请选择两条指纹记录作为开始探访时间和截止探访时间！");
			context.addMessage(null, message);
		}

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
		if (visittype != null) {
			selectedLeaveAndLivingAndMedicalRenderedFlag = visittype;
			keyId = null;
			setRecordTime(null);
			selectVisitRecords();
		}
	}

	/**
	 * 返回是否 需要指纹认证
	 * 
	 * @return
	 * @throws PmsException
	 */
	public boolean isPrint() throws PmsException {
		return visitationHandleService.isPrint();
	}

	/**
	 * 清空选择条件
	 */
	public void clearSelectForm() {
		olderId = null;
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

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setSelectedRow(PensionOlderDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionOlderDomain getSelectedRow() {
		return selectedRow;
	}

	public void setVisittype(Integer visittype) {
		this.visittype = visittype;
	}

	public Integer getVisittype() {
		return visittype;
	}

	public void setEnteredVisitrecord(PensionVisitrecord enteredVisitrecord) {
		this.enteredVisitrecord = enteredVisitrecord;
	}

	public PensionVisitrecord getEnteredVisitrecord() {
		return enteredVisitrecord;
	}

	public void setPrintRecords(List<PensionFingerPrintDaily> printRecords) {
		this.printRecords = printRecords;
	}

	public List<PensionFingerPrintDaily> getPrintRecords() {
		return printRecords;
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

	public void setUsedFlag(Integer usedFlag) {
		this.usedFlag = usedFlag;
	}

	public Integer getUsedFlag() {
		return usedFlag;
	}

	public void setSelectedPrintRecords(
			PensionFingerPrintDaily[] selectedPrintRecords) {
		this.selectedPrintRecords = selectedPrintRecords;
	}

	public PensionFingerPrintDaily[] getSelectedPrintRecords() {
		return selectedPrintRecords;
	}

	public Date getVisitStartDate() {
		return visitStartDate;
	}

	public void setVisitStartDate(Date visitStartDate) {
		this.visitStartDate = visitStartDate;
	}

	public Date getVisitEndDate() {
		return visitEndDate;
	}

	public void setVisitEndDate(Date visitEndDate) {
		this.visitEndDate = visitEndDate;
	}

	public void setPrintFlag(boolean printFlag) {
		this.printFlag = printFlag;
	}

	public boolean isPrintFlag() {
		return printFlag;
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

	public void setVisitationHandleService(
			VisitationHandleService visitationHandleService) {
		this.visitationHandleService = visitationHandleService;
	}

	public VisitationHandleService getVisitationHandleService() {
		return visitationHandleService;
	}

}
