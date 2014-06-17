package controller.receptionManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.chart.PieChartModel;

import service.receptionManage.GuestRecordDomain;
import service.receptionManage.VisitorManageService;
import util.DateUtil;
import domain.dictionary.PensionDicRelationship;
import domain.receptionManage.PensionDicVisitstyle;
import domain.receptionManage.VisitOlderReport;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-09-04 下午05:16:44
 */
public class VisitorManageController implements Serializable {

	private static final long serialVersionUID = 1L;
	private transient VisitorManageService visitorManageService;

	/**
	 * 来访类型列表
	 */
	private List<PensionDicVisitstyle> visitStyleList = new ArrayList<PensionDicVisitstyle>();
	// 查询老人输入法
	private String olderSQL;
	// 新增探访时老人输入法
	private String addOlderSQL;
	// 主页面查询条件
	private String olderName;
	private Integer olderId;
	private Long styleId;
	private Date searchStartDate;
	private Date searchEndDate;
	// 统计对话框查询条件
	private String oldName;
	private Integer oldId;
	private Date startDate;
	private Date endDate;

	private boolean olderFlag;
	private boolean addOlderFlag;
	private boolean disableFlag;
	// 清空按钮可用标识
	private boolean blankFlag;
	// 来访类型可用标识
	private boolean editFlag;
	// 探访统计选中记录
	private GuestRecordDomain selectedVisitorRow;
	// 探访统计列表
	private List<GuestRecordDomain> guestStatisticsList = new ArrayList<GuestRecordDomain>();
	// 探访统计饼形图
	private PieChartModel visitOlderPie;
	private List<VisitOlderReport> visitOlderReports;// 表格显示
	// 老人总数
	private int totalOlderNumber;
	// 探访人次
	private Long totalVisitCount;
	/**
	 * 探访记录列表
	 */
	private List<GuestRecordDomain> guestRecordList = new ArrayList<GuestRecordDomain>();
	/**
	 * 新增探访记录对话框探访记录列表
	 */
	private List<PensionDicVisitstyle> addVisitStyleList = new ArrayList<PensionDicVisitstyle>();

	private GuestRecordDomain selectedRow;

	/**
	 * 操作标记 1 为新增 2 为修改
	 */
	private Short operationId;

	private GuestRecordDomain addGuestRecord;
	/**
	 * 访客与老人关系记录表
	 */
	private List<PensionDicRelationship> relationshipList;

	/**
	 * 页面初始化
	 */
	@PostConstruct
	public void init() {
		searchEndDate = new Date();
		searchStartDate = DateUtil.getBeforeMonthDay(searchEndDate, 1);
		endDate = searchEndDate;
		startDate = searchStartDate;
		initVisitStyle();
		initSql();
		searchAllVisitRecords();
		initRelationShip();
	}

	/**
	 * @Description: 初始化关系列表
	 */
	public void initRelationShip() {
		relationshipList = visitorManageService.selectRelationshipList();
	}

	/**
	 * 初始化来访类型
	 */
	public void initVisitStyle() {
		visitStyleList = visitorManageService.selectVisitTypeRecords();
		PensionDicVisitstyle style = new PensionDicVisitstyle();
		style.setId(0L);
		style.setVisittype("全部");
		visitStyleList.add(0, style);
	}

	/**
	 * 初始化输入法sql
	 */
	public void initSql() {
		olderSQL = "select po.id,po.`name`,po.inputCode,pb.`name` as bedName,"
				+ "pr.`name` as roomName,pf.`name` as floorName,pd.`name` as buildName  "
				+ "from pension_older po,pension_livingrecord pl,pension_bed pb,"
				+ "pension_room pr,pension_floor pf,pension_building pd "
				+ "where po.id=pl.older_id and pl.bed_id=pb.id and pr.id=pb.room_id "
				+ "and pr.floor_id=pf.id and pd.id=pf.build_id and po.cleared=2 and pl.cleared=2 ";
		addOlderSQL = "select po.id,po.`name`,po.inputCode,pb.`name` as bedName,"
				+ "pb.roomName,pb.floorName as floorName,"
				+ "pb.buildName as buildName  from pension_older po,"
				+ "pension_livingrecord pl,pension_bed pb where po.id=pl.older_id  "
				+ "and pl.bed_id=pb.id and po.cleared=2 "
				+ "and pl.cleared=2 and po.statuses =3";
	}

	/**
	 * 新增按钮，触发事件
	 */
	public void showAddForm() {
		operationId = 1;
		addGuestRecord = new GuestRecordDomain();
		addGuestRecord.setVisittype(4L);
		addVisitStyleList = visitorManageService.selectVisitTypeRecords();
		addOlderFlag = false;
		initRelationShip();
		disableFlag = false;
		blankFlag = false;
		editFlag = false;
	}

	/**
	 * 清空探访登记对话框
	 */
	public void clearAddForm() {
		addGuestRecord = new GuestRecordDomain();
	}

	/**
	 * 修改按钮，触发事件
	 */
	public void showEditForm() {
		final FacesContext context = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
			request.addCallbackParam("show", false);
		} else {
			blankFlag = true;
			disableFlag = false;
			operationId = 2;
			addGuestRecord = selectedRow;
			if (addGuestRecord.getVisittype() != 4) {
				relationshipList = new ArrayList<PensionDicRelationship>();
			} else {
				relationshipList = visitorManageService
						.selectRelationshipList();
			}
			addVisitStyleList = visitorManageService.selectVisitTypeRecords();
			addOlderFlag = true;
			editFlag = true;
			request.addCallbackParam("show", true);
		}
	}

	/**
	 * 查看按钮触发事件
	 */
	public void showViewForm() {
		final FacesContext context = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
			request.addCallbackParam("show", false);
		} else {
			addGuestRecord = selectedRow;
			addVisitStyleList = visitorManageService.selectVisitTypeRecords();
			request.addCallbackParam("show", true);
		}
	}

	/**
	 * 保存或修改一条访客记录
	 */
	public void saveGuestRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		if (addGuestRecord.getVisittype() == 4) {
			if (addGuestRecord.getOlderName() == ""
					|| addGuestRecord.getRelationId() == null) {
				facesContext.addMessage("", new FacesMessage(
						FacesMessage.SEVERITY_INFO, "老人姓名或关系不可为空！", ""));
				return;
			}
		}
		if (operationId == 1) {
			addGuestRecord.setCleared(2);
			addGuestRecord.setVisittime(new Date());
			visitorManageService.insertGuestRecord(addGuestRecord);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, "访客记录已增加", ""));
			searchVisitRecords();
		} else {
			visitorManageService.updateGuestRecord(addGuestRecord);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, "访客记录已修改", ""));
			searchVisitRecords();
		}
		request.addCallbackParam("success", true);
	}

	/**
	 * 查询所有访客记录
	 */
	public void searchAllVisitRecords() {
		guestRecordList = visitorManageService.selectAllGuestRecords(
				searchStartDate, searchEndDate);
	}

	/**
	 * 根据报表类生成饼图Map
	 * 
	 * @param reports
	 * @return
	 */
	public Map<String, Number> createPiaMap(List<VisitOlderReport> reports) {
		Map<String, Number> map = new HashMap<String, Number>();
		for (VisitOlderReport report : reports) {
			map.put(report.getVisitNumber(), report.getOlderCount());
		}
		return map;
	}

	/**
	 * 按条件查询访客记录
	 */
	public void searchVisitRecords() {
		if (olderName == "") {
			olderId = null;
		}
		guestRecordList = visitorManageService.selectGuestRecords(styleId,
				olderId, searchStartDate, searchEndDate);
		selectedRow = null;
	}

	/**
	 * 查询时，探访类型改变时，更新老人输入框的状态
	 */
	public void changFlag() {
		if ((styleId != 4) && (styleId != 0L)) {
			olderId = null;
			olderName = "";
			olderFlag = true;
		} else {
			olderFlag = false;
		}
	}

	public void clearSearchForm() {
		styleId = 99L;
		olderId = null;
		olderName = "";
		searchEndDate = null;
		searchStartDate = null;
	}

	/**
	 * 新增和编辑对话框，探访类型改变时，更新老人输入框的状态
	 */
	public void changAddFlag() {
		if (operationId == 1) {
			if (addGuestRecord.getVisittype() != 4) {
				addOlderFlag = true;
				relationshipList = new ArrayList<PensionDicRelationship>();
			} else {
				addOlderFlag = false;
				initRelationShip();
			}
		}
	}

	/**
	 * 查询老人探访统计情况
	 */
	public void searchstatistics() {
		totalVisitCount = 0L;
		if (oldName == null || oldName.equals("")) {
			oldId = null;
		}
		guestStatisticsList = visitorManageService.selectStatistics(oldId,
				startDate, endDate);
		totalOlderNumber = guestStatisticsList.size();
		for (GuestRecordDomain temp : guestStatisticsList) {
			totalVisitCount += temp.getCountNo();
		}

		List<VisitOlderReport> visitOlderReports = visitorManageService.search(
				startDate, endDate);
		visitOlderPie = new PieChartModel();
		List<VisitOlderReport> visitOlderTemp = visitorManageService
				.packVisitOlderReports(visitOlderReports);
		visitOlderPie.setData(this.createPiaMap(visitOlderTemp));
		System.out.println(visitOlderPie.getData().size());
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchStatistics() {
		oldId = null;
		startDate = null;
		endDate = null;
		oldName = "";
	}

	/**
	 * 选中一行时触发
	 * 
	 * @param event
	 */
	public void setEnableFlag(SelectEvent event) {

	}

	/**
	 * 未选中一行时触发
	 * 
	 * @param event
	 */
	public void setUnableFlag(UnselectEvent event) {

	}

	public VisitorManageService getVisitorManageService() {
		return visitorManageService;
	}

	public void setVisitorManageService(
			VisitorManageService visitorManageService) {
		this.visitorManageService = visitorManageService;
	}

	public void setVisitStyleList(List<PensionDicVisitstyle> visitStyleList) {
		this.visitStyleList = visitStyleList;
	}

	public List<PensionDicVisitstyle> getVisitStyleList() {
		return visitStyleList;
	}

	public void setOlderSQL(String olderSQL) {
		this.olderSQL = olderSQL;
	}

	public String getOlderSQL() {
		return olderSQL;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setGuestRecordList(List<GuestRecordDomain> guestRecordList) {
		this.guestRecordList = guestRecordList;
	}

	public List<GuestRecordDomain> getGuestRecordList() {
		return guestRecordList;
	}

	public void setSelectedRow(GuestRecordDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public GuestRecordDomain getSelectedRow() {
		return selectedRow;
	}

	public void setOperationId(Short operationId) {
		this.operationId = operationId;
	}

	public Short getOperationId() {
		return operationId;
	}

	public void setRelationshipList(
			List<PensionDicRelationship> relationshipList) {
		this.relationshipList = relationshipList;
	}

	public List<PensionDicRelationship> getRelationshipList() {
		return relationshipList;
	}

	public void setAddGuestRecord(GuestRecordDomain addGuestRecord) {
		this.addGuestRecord = addGuestRecord;
	}

	public GuestRecordDomain getAddGuestRecord() {
		return addGuestRecord;
	}

	public void setOlderFlag(boolean olderFlag) {
		this.olderFlag = olderFlag;
	}

	public boolean getOlderFlag() {
		return olderFlag;
	}

	public Long getStyleId() {
		return styleId;
	}

	public void setStyleId(Long styleId) {
		this.styleId = styleId;
	}

	public Integer getOlderId() {
		return olderId;
	}

	public void setOlderId(Integer olderId) {
		this.olderId = olderId;
	}

	public List<PensionDicVisitstyle> getAddVisitStyleList() {
		return addVisitStyleList;
	}

	public void setAddVisitStyleList(
			List<PensionDicVisitstyle> addVisitStyleList) {
		this.addVisitStyleList = addVisitStyleList;
	}

	public void setAddOlderFlag(boolean addOlderFlag) {
		this.addOlderFlag = addOlderFlag;
	}

	public boolean getAddOlderFlag() {
		return addOlderFlag;
	}

	public void setDisableFlag(boolean disableFlag) {
		this.disableFlag = disableFlag;
	}

	public boolean getDisableFlag() {
		return disableFlag;
	}

	public void setBlankFlag(boolean blankFlag) {
		this.blankFlag = blankFlag;
	}

	public boolean getBlankFlag() {
		return blankFlag;
	}

	public void setEditFlag(boolean editFlag) {
		this.editFlag = editFlag;
	}

	public boolean getEditFlag() {
		return editFlag;
	}

	public void setSelectedVisitorRow(GuestRecordDomain selectedVisitorRow) {
		this.selectedVisitorRow = selectedVisitorRow;
	}

	public GuestRecordDomain getSelectedVisitorRow() {
		return selectedVisitorRow;
	}

	public void setGuestStatisticsList(
			List<GuestRecordDomain> guestStatisticsList) {
		this.guestStatisticsList = guestStatisticsList;
	}

	public List<GuestRecordDomain> getGuestStatisticsList() {
		return guestStatisticsList;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldId(Integer oldId) {
		this.oldId = oldId;
	}

	public Integer getOldId() {
		return oldId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setAddOlderSQL(String addOlderSQL) {
		this.addOlderSQL = addOlderSQL;
	}

	public String getAddOlderSQL() {
		return addOlderSQL;
	}

	public void setSearchStartDate(Date searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public Date getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public Date getSearchEndDate() {
		return searchEndDate;
	}

	public void setVisitOlderPie(PieChartModel visitOlderPie) {
		this.visitOlderPie = visitOlderPie;
	}

	public PieChartModel getVisitOlderPie() {
		return visitOlderPie;
	}

	public void setVisitOlderReports(List<VisitOlderReport> visitOlderReports) {
		this.visitOlderReports = visitOlderReports;
	}

	public List<VisitOlderReport> getVisitOlderReports() {
		return visitOlderReports;
	}

	public void setTotalOlderNumber(int totalOlderNumber) {
		this.totalOlderNumber = totalOlderNumber;
	}

	public int getTotalOlderNumber() {
		return totalOlderNumber;
	}

	public void setTotalVisitCount(Long totalVisitCount) {
		this.totalVisitCount = totalVisitCount;
	}

	public Long getTotalVisitCount() {
		return totalVisitCount;
	}

}
