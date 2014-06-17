package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import service.olderManage.LivingNoticeService;
import service.olderManage.LivingRecordDomain;
import service.olderManage.PensionNoticeDomain;
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

public class LivingNoticeController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient LivingNoticeService livingNoticeService;

	/**
	 * 调用输入法显示字段
	 */
	private String olderName;
	private Long olderId;
	/**
	 * 当前用户
	 */
	private String userName;
	private PensionEmployee curUser;
	private Long deptId;
	private String deptName;
	/**
	 * 部门待处理事件列表
	 */
	List<PensionNoticeDomain> unDealEventRecords = new ArrayList<PensionNoticeDomain>();

	/**
	 * 部门已处理事件列表
	 */
	List<PensionNoticeDomain> dealEventRecords = new ArrayList<PensionNoticeDomain>();
	/**
	 * 输入法sql
	 */
	private String olderSQL;
	private boolean disableFlag;
	private PensionNoticeDomain selectedRow;
	private LivingRecordDomain olderInfo;

	private Date startDate;
	private Date endDate;
	private String handleFlag;

	@PostConstruct
	public void init() throws PmsException {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("oldId");
		if (oldId != null) {
			olderId = Long.valueOf(oldId);
			olderName = livingNoticeService.selectOlderName(olderId);
		} else {
			olderId = null;
		}
		endDate = new Date();
		startDate = DateUtil.getBeforeDay(endDate, 7);
		handleFlag = "2";
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		deptId = curUser.getDeptId();
		deptName = livingNoticeService.selectDeptName(deptId);
		initSql();
		searchDeptEvent();
	}

	/**
	 * 初始化输入法sql
	 */
	public void initSql() {
		olderSQL = "select pr.older_id as olderId,po.`name` as olderName,"
				+ "str_to_date(DATE_FORMAT(pr.visitTime,'%Y-%m-%d'),'%Y-%m-%d') as visitTime,"
				+ "po.inputCode as inputCode,bed.`name` as bedName,"
				+ "room.`name` as roomName,floor.`name` as  floorName,"
				+ "build.`name` as buildName,if(po.sex=1,'男','女') AS sexStr   "
				+ "from pension_livingrecord pr,pension_older po,living_notice_record lnr,"
				+ "pension_bed bed,pension_room room,pension_floor floor,"
				+ "pension_building build  where  po.id=pr.older_id and bed.id=pr.bed_id   "
				+ " and room.id=bed.room_id  and lnr.older_id=po.id  "
				+ "and room.floor_id=floor.id and build.id=floor.build_id  "
				+ "and pr.cleared=2 and po.cleared=2  and lnr.dept_id="
				+ deptId;
	}

	/**
	 * 查询老人的处理事件
	 */
	public void searchDeptEvent() {
		if (olderName == "" || olderName == null) {
			olderId = null;
		}
		unDealEventRecords = livingNoticeService.selectUnDealEventList(deptId,
				olderId, startDate, endDate, handleFlag);
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		olderName = null;
		olderId = null;
		startDate = null;
		endDate = null;
		handleFlag = "";
	}

	/**
	 * 事件完成后确认
	 */
	public void confirmNotice() {
		final FacesContext context = FacesContext.getCurrentInstance();
		boolean checkFlag = false;
		for (PensionNoticeDomain temp : unDealEventRecords) {
			if (temp.getHandleFlag()) {
				checkFlag = true;
				temp.setHandlerId(curUser.getId());
				temp.setHandleresult(1);
				temp.setHandletime(new Date());
				temp.setCheckFlag(2);
				try {
					livingNoticeService.updateEventRecords(temp, temp.getId(),
							curUser.getId(), deptId);
					livingNoticeService.sentMessage(temp);
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_INFO, "确认并发送质检通知成功！", ""));
				} catch (Exception e) {
					context.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_INFO, "确认失败，请刷新页面后重试！", ""));
				}
			}
		}
		if (!checkFlag) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选中一条未确认记录！", ""));
		} else {
			searchDeptEvent();
		}
	}

	public void selectRow(SelectEvent e) {

	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public void setOlderSQL(String olderSQL) {
		this.olderSQL = olderSQL;
	}

	public String getOlderSQL() {
		return olderSQL;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setLivingNoticeService(LivingNoticeService livingNoticeService) {
		this.livingNoticeService = livingNoticeService;
	}

	public LivingNoticeService getLivingNoticeService() {
		return livingNoticeService;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public List<PensionNoticeDomain> getUnDealEventRecords() {
		return unDealEventRecords;
	}

	public void setUnDealEventRecords(
			List<PensionNoticeDomain> unDealEventRecords) {
		this.unDealEventRecords = unDealEventRecords;
	}

	public List<PensionNoticeDomain> getDealEventRecords() {
		return dealEventRecords;
	}

	public void setDealEventRecords(List<PensionNoticeDomain> dealEventRecords) {
		this.dealEventRecords = dealEventRecords;
	}

	public void setSelectedRow(PensionNoticeDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionNoticeDomain getSelectedRow() {
		return selectedRow;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDisableFlag(boolean disableFlag) {
		this.disableFlag = disableFlag;
	}

	public boolean getDisableFlag() {
		return disableFlag;
	}

	public void setOlderInfo(LivingRecordDomain olderInfo) {
		this.olderInfo = olderInfo;
	}

	public LivingRecordDomain getOlderInfo() {
		return olderInfo;
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

	public void setHandleFlag(String handleFlag) {
		this.handleFlag = handleFlag;
	}

	public String getHandleFlag() {
		return handleFlag;
	}
}
