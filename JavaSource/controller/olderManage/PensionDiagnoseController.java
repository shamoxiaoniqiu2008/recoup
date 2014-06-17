package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import service.olderManage.PensionDiagnoseDomain;
import service.olderManage.PensionDiagnoseService;
import util.DateUtil;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-28 下午01:16:44
 */
public class PensionDiagnoseController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient PensionDiagnoseService pensionDiagnoseService;

	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;

	private Date startDate;
	private Date endDate;
	/**
	 * 所有申请记录列表
	 */
	private List<PensionDiagnoseDomain> diagnoseRecordList = new ArrayList<PensionDiagnoseDomain>();
	/**
	 * 选中申请记录
	 */
	private PensionDiagnoseDomain selectedRow;
	/**
	 * 操作标记 1 为新增 2 为修改
	 */
	private Short operationId;
	/**
	 * 新增和修改对话框申请记录
	 */
	private PensionDiagnoseDomain addDiagnoseOlder = new PensionDiagnoseDomain();

	private String userName;
	private PensionEmployee curUser;
	/**
	 * 输入法sql
	 */
	private String olderSQL;
	private String newOlderSQL;
	/**
	 * 输入框可用标识
	 */
	private Boolean olderNameFlag;
	private Boolean blankFlag;

	@PostConstruct
	public void init() throws PmsException {
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		userName = curUser.getName();
		endDate = new Date();
		startDate = DateUtil.getBeforeDay(endDate, 7);
		searchDiagnoseRecords();
		initSql();
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		olderName = "";
		startDate = null;
		endDate = null;
	}

	/**
	 * 初始化输入法sql
	 */
	public void initSql() {

		olderSQL = "select pr.older_id as olderId,po.`name` as olderName,po.inputCode as inputCode,bed.`name` as bedName,"
				+ "room.`name` as roomName,floor.`name` as  floorName,build.`name` as buildName  "
				+ "from pension_livingrecord pr,pension_older po,pension_bed bed,"
				+ "pension_room room,pension_floor floor,pension_building build  where  "
				+ "po.id=pr.older_id and bed.id=pr.bed_id and room.id=bed.room_id  "
				+ "and room.floor_id=floor.id and build.id=floor.build_id  and pr.cleared=2 and po.cleared=2 ";
		newOlderSQL = "select pr.older_id as olderId,po.`name` as olderName,po.inputCode as inputCode,bed.`name` as bedName,"
				+ "room.`name` as roomName,floor.`name` as  floorName,build.`name` as buildName,if(po.statuses=3,'入住','请假') as olderState   "
				+ "from pension_livingrecord pr,pension_older po,pension_bed bed,"
				+ "pension_room room,pension_floor floor,pension_building build  where  "
				+ "po.id=pr.older_id and bed.id=pr.bed_id and room.id=bed.room_id  "
				+ "and room.floor_id=floor.id and build.id=floor.build_id  and pr.cleared=2 and po.cleared=2 and po.statuses in(3,4) ";
	}

	/**
	 * 查询出所有的老人申请记录
	 */
	public void searchDiagnoseRecords() {
		if (olderName == "" || olderName == null) {
			olderId = null;
		}
		diagnoseRecordList = pensionDiagnoseService.selectDiagnoseRecords(
				olderId, startDate, endDate);
		selectedRow = null;
	}

	/**
	 * 新增触发事件
	 */
	public void showAddForm() {
		operationId = 1;
		clearAddForm();
		addDiagnoseOlder.setDiagnosistime(new Date());
		blankFlag = false;
		olderNameFlag = false;
	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		addDiagnoseOlder = new PensionDiagnoseDomain();
		addDiagnoseOlder.setRecorderName(userName);
	}

	/**
	 * 修改触发事件
	 */
	public void showEditForm() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "未选中任何记录！", ""));
			request.addCallbackParam("showEdit", false);
		} else {
			operationId = 2;
			addDiagnoseOlder = selectedRow;
			olderNameFlag = true;
			blankFlag = true;
			request.addCallbackParam("showEdit", true);
		}
	}

	/**
	 * 选中一行触发
	 * 
	 * @param event
	 */
	public void selectRow(SelectEvent event) {

	}

	/**
	 * 未选中一行触发
	 * 
	 * @param event
	 */
	public void unSelectRow() {

	}

	/**
	 * 保存新增或修改的老人入住记录
	 */
	public void saveDiagnoseRecord() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (operationId == 1) {
			addDiagnoseOlder.setCleared(2);
			addDiagnoseOlder.setRecorderId(curUser.getId());
			try {
				pensionDiagnoseService.insertDiagnose(addDiagnoseOlder);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"新增巡诊记录成功！", ""));
				request.addCallbackParam("hide", true);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"新增巡诊记录失败！", ""));
				request.addCallbackParam("hide", false);
			}
		} else if (operationId == 2) {
			addDiagnoseOlder.setRecorderId(curUser.getId());
			try {
				pensionDiagnoseService.updateDiagnose(addDiagnoseOlder);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"修改巡诊记录成功！", ""));
				request.addCallbackParam("hide", true);
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"修改巡诊记录失败！", ""));
				request.addCallbackParam("hide", false);
			}
		}
		searchDiagnoseRecords();
	}

	/**
	 * 是否弹出删除确认对话框
	 */
	public void showDelForm() {
		final FacesContext context = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "未选中任何记录！", ""));
			request.addCallbackParam("del", false);
		} else {
			request.addCallbackParam("del", true);
		}
	}

	/**
	 * 修改入住记录清除标记
	 */
	public void delDiagnose() {
		try {
			pensionDiagnoseService.deleteDiagnose(selectedRow);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "删除巡诊记录成功！",
							""));
			selectedRow = null;
			searchDiagnoseRecords();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "删除巡诊记录失败！",
							""));
		}
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public void setSelectedRow(PensionDiagnoseDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionDiagnoseDomain getSelectedRow() {
		return selectedRow;
	}

	public void setOperationId(Short operationId) {
		this.operationId = operationId;
	}

	public Short getOperationId() {
		return operationId;
	}

	public void setOlderSQL(String olderSQL) {
		this.olderSQL = olderSQL;
	}

	public String getOlderSQL() {
		return olderSQL;
	}

	public void setAddLivingOlder(PensionDiagnoseDomain addLivingOlder) {
		this.addDiagnoseOlder = addLivingOlder;
	}

	public PensionDiagnoseDomain getAddLivingOlder() {
		return addDiagnoseOlder;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public void setOlderNameFlag(Boolean olderNameFlag) {
		this.olderNameFlag = olderNameFlag;
	}

	public Boolean getOlderNameFlag() {
		return olderNameFlag;
	}

	public void setPensionDiagnoseService(
			PensionDiagnoseService pensionDiagnoseService) {
		this.pensionDiagnoseService = pensionDiagnoseService;
	}

	public PensionDiagnoseService getPensionDiagnoseService() {
		return pensionDiagnoseService;
	}

	public void setDiagnoseRecordList(
			List<PensionDiagnoseDomain> diagnoseRecordList) {
		this.diagnoseRecordList = diagnoseRecordList;
	}

	public List<PensionDiagnoseDomain> getDiagnoseRecordList() {
		return diagnoseRecordList;
	}

	public void setAddDiagnoseOlder(PensionDiagnoseDomain addDiagnoseOlder) {
		this.addDiagnoseOlder = addDiagnoseOlder;
	}

	public PensionDiagnoseDomain getAddDiagnoseOlder() {
		return addDiagnoseOlder;
	}

	public void setNewOlderSQL(String newOlderSQL) {
		this.newOlderSQL = newOlderSQL;
	}

	public String getNewOlderSQL() {
		return newOlderSQL;
	}

	public void setBlankFlag(Boolean blankFlag) {
		this.blankFlag = blankFlag;
	}

	public Boolean getBlankFlag() {
		return blankFlag;
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
