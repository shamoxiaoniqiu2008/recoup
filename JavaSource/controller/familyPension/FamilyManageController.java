package controller.familyPension;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import service.olderManage.LivingManageService;
import service.olderManage.LivingRecordDomain;
import service.olderManage.PensionFamilyDomain;
import util.DateUtil;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.configureManage.PensionBed;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionFloor;
import domain.configureManage.PensionRoom;
import domain.dictionary.PensionDicBedtype;
import domain.dictionary.PensionDicNurse;
import domain.dictionary.PensionDicRelationship;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionOlderItempurse;
import domain.olderManage.PensionLivingLog;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-28 下午01:16:44
 */

public class FamilyManageController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient LivingManageService livingManageService;

	/**
	 * 查询条件
	 */
	private String olderName;
	private Date StartDate;
	private Date endDate;
	private Long olderId;
	/**
	 * 所有申请记录列表
	 */
	private List<LivingRecordDomain> livingRecordList = new ArrayList<LivingRecordDomain>();
	/**
	 * 选中申请记录
	 */
	private LivingRecordDomain selectedRow;
	/**
	 * 操作标记 1 为新增 2 为修改
	 */
	private Short operationId;
	/**
	 * 新增和修改对话框申请记录
	 */
	private LivingRecordDomain addLivingOlder = new LivingRecordDomain();
	/**
	 * 护理级别List
	 */
	private List<PensionDicNurse> nurseLevelList = new ArrayList<PensionDicNurse>();
	/**
	 * 床位标准List
	 */
	private List<PensionDicBedtype> bedTypeList = new ArrayList<PensionDicBedtype>();;
	/**
	 * 护理员列表
	 */
	private List<PensionEmployee> nurseList = new ArrayList<PensionEmployee>();

	private String userName;
	private PensionEmployee curUser;
	private Long deptId;
	/**
	 * 输入法sql
	 */
	private String olderSQL;
	private String bedSQL;
	private String addOlderSQL;
	private String nurseLevelSQL;
	private String bedTypeSQL;
	private String nurseSQL;
	/**
	 * 输入框可用标识
	 */
	private Boolean addOlderStatuseFlag;
	private Boolean olderNameFlag;
	private Boolean blankFlag;
	private Boolean disableFlag;
	// 更新日志
	private PensionLivingLog updateLog = new PensionLivingLog();
	private long oldBedId;
	private long oldNurseTypeId;
	private Long oldBedType;
	// 入住审批拍板部门ID
	private Long leadDeptId;
	// 入住审批拍板人员ID
	private Long leadEmpId;
	// 管家部门ID
	private Long homeDeptId;
	// 管家人员ID
	private Long homeEmptId;
	private Date oldVisitTime;
	// 选中的家属记录
	private PensionFamilyDomain selectedFamilyRow;
	// 家属列表
	private List<PensionFamilyDomain> familyList = new ArrayList<PensionFamilyDomain>();

	// 新增家属对话框对象
	private PensionFamilyDomain addFamily;
	// 关系列表
	private List<PensionDicRelationship> relationDicList;
	// 新增家属信息对话框清空按钮禁用标识
	private Boolean blankRelationFlag;
	// 家属tab页按钮触发标识
	private int relationFlag;

	private List<Long> nurseDeptId;
	// 护理级别对应价表
	private Long nurseItemId;
	// 床位级别对应价表
	private Long bedItemId;
	// 是否缴纳床位费标识
	private boolean bedUseFlag;

	private int pensionCategary = 3;

	private List<PensionBuilding> buildList = new ArrayList<PensionBuilding>();

	private PensionBed selectedBed;

	@PostConstruct
	public void init() throws PmsException {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("olderId");
		if (oldId != null) {
			olderId = Long.valueOf(oldId);
			endDate = null;
			StartDate = null;
		} else {
			olderId = null;
			endDate = new Date();
			StartDate = DateUtil.getBeforeDay(endDate, 7);
		}
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		userName = curUser.getName();
		homeEmptId = livingManageService.selectDeskEmptId();
		if (homeEmptId == null) {
			homeDeptId = livingManageService.selectDeskDeptId();
		} else {
			homeDeptId = null;
		}
		searchLivingRecords();
		initSql();
		initDicList();
		initRelationShip();
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		olderName = "";
		olderId = null;
		StartDate = null;
		endDate = null;
	}

	/**
	 * 初始化输入法sql
	 */
	public void initSql() {
		leadDeptId = livingManageService.selectLeadDeptId();
		leadEmpId = livingManageService.selectLeadEmptId();
		nurseDeptId = livingManageService.selectNurseDeptIds();
		olderSQL = "select pr.older_id as olderId,po.`name` as olderName,po.inputCode as inputCode,"
				+ "bed.`name` as bedName,room.`name` as roomName,"
				+ "floor.`name` as  floorName,build.`name` as buildName  "
				+ "from pension_livingrecord pr,pension_older po,pension_bed bed,"
				+ "pension_room room,pension_floor floor,pension_building build,pension_livingapply pl  "
				+ "where po.id=pr.older_id and bed.id=pr.bed_id and room.id=bed.room_id  "
				+ "and pl.older_id=po.id and pl.pension_categary=3  "
				+ "and room.floor_id=floor.id and build.id=floor.build_id  and pr.cleared=2 and po.cleared=2 ";

		if (leadEmpId == null) {
			addOlderSQL = "select po.id,po.`name`,po.inputCode, '已评估' as statuses,pl.id as applyId,"
					+ " '居家养老'  as pensionCategary "
					+ "from pension_livingapply pl,pension_older po,"
					+ "pension_applyevaluate pe where pl.older_id=po.id and "
					+ "po.statuses=2 and po.cleared=2 and pl.cleared=2 and pl.pension_categary=3  "
					+ "and pe.apply_id=pl.id and pe.evaluateResult=1 and pe.dept_id="
					+ leadDeptId;
		} else {
			addOlderSQL = "select po.id,po.`name`,po.inputCode, '已评估' as statuses,pl.id as applyId, '居家养老'  as pensionCategary "
					+ "from pension_livingapply pl,pension_older po,"
					+ "pension_applyevaluate pe where pl.older_id=po.id and "
					+ "po.statuses=2 and po.cleared=2 and pl.cleared=2 and pl.pension_categary=3  "
					+ "and pe.apply_id=pl.id and pe.evaluateResult=1 and pe.evaluator_id="
					+ leadEmpId;
		}

		nurseLevelSQL = "SELECT pdn.id, pdn.`level`,pdn.inputCode,pi.purse,pdn.purseId "
				+ "FROM pension_dic_nurse pdn,pension_itempurse pi    "
				+ "where pi.id=pdn.purseId and pi.cleared=2 order by pdn.id asc";

		//add by justin.su 2014-06-04 修改输入法支持多部门
		if(nurseDeptId.size() > 0){
			String inStr = "";
			for(long l : nurseDeptId){
				inStr = inStr + String.valueOf(l)+",";
			}
			inStr = inStr.substring(0, inStr.length()-1);
			nurseSQL = "select p.id,p.name,p.inputCode from pension_employee p where p.cleared=2 and p.dept_id in ("
					+ inStr + ")";
		}else{
			nurseSQL = "select p.id,p.name,p.inputCode from pension_employee p where p.cleared=2";
		}
	}

	/**
	 * 初始化关系列表
	 */
	public void initRelationShip() {
		relationDicList = livingManageService.selectRelationshipList();
	}

	/**
	 * 根据条件查询老人入住申请记录
	 */
	public void searchLivingRecords() {
		livingRecordList = livingManageService.selectLivingRecords(olderId,
				StartDate, endDate, pensionCategary);
		selectedRow = null;
	}

	/**
	 * 新增触发事件
	 */
	public void showAddForm() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		if ((homeEmptId != null && homeEmptId != curUser.getId())
				|| (homeDeptId != null && homeDeptId != curUser.getDeptId())) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "您没有权限进行该操作！", ""));
			request.addCallbackParam("showEdit", false);
			return;
		}
		operationId = 1;
		addLivingOlder = new LivingRecordDomain();
		addLivingOlder.setStatuses(3);
		addOlderStatuseFlag = true;
		olderNameFlag = false;
		blankFlag = false;
		addLivingOlder.setVisittime(new Date());
		familyList = new ArrayList<PensionFamilyDomain>();
		bedUseFlag = false;
		request.addCallbackParam("showEdit", true);
	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		addLivingOlder = new LivingRecordDomain();
	}

	/**
	 * 修改触发事件
	 */
	public void showEditForm() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		if ((homeEmptId != null && homeEmptId != curUser.getId())
				|| (homeDeptId != null && homeDeptId != curUser.getDeptId())) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "您没有权限进行该操作！", ""));
			request.addCallbackParam("showEdit", false);
			return;
		}
		if (selectedRow == null) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "未选中任何记录！", ""));
			request.addCallbackParam("showEdit", false);
		} else if (selectedRow.getStatuses() == 5
				|| selectedRow.getStatuses() == 6) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "该老人已离院！", ""));
			request.addCallbackParam("showEdit", false);
		} else {
			oldBedId = selectedRow.getBedId();
			oldBedType = selectedRow.getBedTypeId();
			oldNurseTypeId = selectedRow.getNurseLevelId();
			operationId = 2;
			addLivingOlder = selectedRow;
			addOlderStatuseFlag = true;
			olderNameFlag = true;
			blankFlag = true;
			bedUseFlag = false;
			familyList = livingManageService.selectFamilyRecord(addLivingOlder
					.getOlderId());
			request.addCallbackParam("showEdit", true);
		}
	}

	public void initDicList() throws PmsException {
		nurseLevelList = livingManageService.selectNurseLevel();
		bedTypeList = livingManageService.selectBedType();
	}

	/**
	 * 封装老人入住记录更新日志
	 */
	public void getPensionUpdateLog() {
		updateLog.setNurseLevelId(addLivingOlder.getNurseLevelId());
		updateLog.setBedTypeId(addLivingOlder.getBedTypeId());
		updateLog.setOlderId(addLivingOlder.getOlderId());

	}

	/**
	 * 保存新增或修改的老人入住记录
	 */
	public void saveLivingRecord() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (familyList == null || familyList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "家属列表不可为空！",
							""));
			return;
		}
		if (!checkDefaultFamily()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "未设置紧急联系人！",
							""));
			return;
		}
		getPensionUpdateLog();
		if (operationId == 1) {
			// 封装老人护理费用
			PensionOlderItempurse nurseItemRecord = new PensionOlderItempurse();
			nurseItemRecord.setCleared(2);
			nurseItemRecord.setEnddate(addLivingOlder.getVisittime());
			nurseItemRecord.setItempurseId(nurseItemId);
			nurseItemRecord.setOlderId(addLivingOlder.getOlderId());
			nurseItemRecord.setStartdate(addLivingOlder.getVisittime());
			nurseItemRecord.setStopFlag(2);
			// 封装老人床位费用
			PensionOlderItempurse bedItemRecord = new PensionOlderItempurse();
			if (!bedUseFlag) {
				bedItemRecord.setCleared(2);
				bedItemRecord.setEnddate(addLivingOlder.getVisittime());
				bedItemId = livingManageService.selectBedItemId(addLivingOlder
						.getBedTypeId());
				bedItemRecord.setItempurseId(bedItemId);
				bedItemRecord.setOlderId(addLivingOlder.getOlderId());
				bedItemRecord.setStartdate(addLivingOlder.getVisittime());
				bedItemRecord.setStopFlag(2);
			} else {
				bedItemRecord = null;
			}

			addLivingOlder.setHandlerId(curUser.getId());
			addLivingOlder.setCleared(2);
			livingManageService.insertLivingRecord(addLivingOlder, updateLog,
					familyList, nurseItemRecord, bedItemRecord);
			livingManageService.updateEventRecords(addLivingOlder.getOlderId(),
					curUser.getId(), deptId);
			searchLivingRecords();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "新增入住记录成功！",
							""));
			request.addCallbackParam("addSuccess", true);
		} else if (operationId == 2) {
			// 封装老人护理费用
			PensionOlderItempurse nurseItemRecord = new PensionOlderItempurse();
			if (oldNurseTypeId != addLivingOlder.getNurseLevelId()) {
				nurseItemRecord.setCleared(2);
				nurseItemRecord.setEnddate(new Date());
				nurseItemRecord.setItempurseId(nurseItemId);
				nurseItemRecord.setOlderId(addLivingOlder.getOlderId());
				nurseItemRecord.setStartdate(new Date());
				nurseItemRecord.setStopFlag(2);
			} else {
				nurseItemRecord = null;
			}
			// 封装老人床位费用
			PensionOlderItempurse bedItemRecord = new PensionOlderItempurse();
			if (addLivingOlder.getBedTypeId() != oldBedType) {
				if (!bedUseFlag) {
					bedItemRecord.setCleared(2);
					bedItemRecord.setEnddate(new Date());
					bedItemId = livingManageService
							.selectBedItemId(addLivingOlder.getBedTypeId());
					bedItemRecord.setItempurseId(bedItemId);
					bedItemRecord.setOlderId(addLivingOlder.getOlderId());
					bedItemRecord.setStartdate(new Date());
					bedItemRecord.setStopFlag(2);
				} else {
					bedItemRecord = null;
				}
			} else {
				bedItemRecord = null;
			}
			addLivingOlder.setHandlerId(curUser.getId());
			livingManageService.updateLivingRecord(addLivingOlder, updateLog,
					oldBedId, oldNurseTypeId, familyList, nurseItemRecord,
					bedItemRecord);

			livingRecordList = livingManageService.selectLivingRecords(olderId,
					StartDate, endDate, pensionCategary);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "修改入住记录成功！",
							""));
			request.addCallbackParam("success", true);
		}
	}

	// 发送入住通知触发
	public void confirmSend() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		if ((homeEmptId != null && homeEmptId != curUser.getId())
				|| (homeDeptId != null && homeDeptId != curUser.getDeptId())) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "您没有权限进行该操作！", ""));
			return;
		}
		if (selectedRow == null) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
		} else if (selectedRow.getStatuses() == 5
				|| selectedRow.getStatuses() == 6) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "该老人已离院！", ""));
		} else {
			operationId = 3;
			sentMessage();
		}
	}

	/**
	 * 发送通知
	 * 
	 */
	public void sentMessage() {
		if (operationId == 1) {
			try {
				livingManageService.sentMessage(addLivingOlder);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"发送入住通知消息成功", ""));
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e
								.getMessage(), ""));
				e.printStackTrace();
			}
		} else {
			try {
				if (selectedRow.getStatuses() == 5
						|| selectedRow.getStatuses() == 6) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"该老人已离院！", ""));
				} else {
					livingManageService.sentMessage(selectedRow);
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"发送入住通知消息成功", ""));
				}
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e
								.getMessage(), ""));
			}
		}
	}

	/**
	 * 选中一行老人入住记录时触发
	 * 
	 * @param event
	 */
	public void selectRow(SelectEvent event) {
		selectedFamilyRow = null;
	}

	/**
	 * 选中一条家属记录时触发
	 */
	public void selectFamilyRow(SelectEvent e) {

	}

	/**
	 * 未选中一条家属记录时触发
	 */
	public void unselectFamilyRow(UnselectEvent e) {
		selectedFamilyRow = null;
	}

	/**
	 * 新增一条家属记录时触发
	 */
	public void initFamily() {
		relationFlag = 1;
		addFamily = new PensionFamilyDomain();
		blankRelationFlag = false;
	}

	/**
	 * 家属tab页修改按钮触发
	 */
	public void showEditFamily() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedFamilyRow == null) {
			requestContext.addCallbackParam("edit", false);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选中一条家属信息", ""));
		} else {
			relationFlag = 2;
			addFamily = selectedFamilyRow;
			blankRelationFlag = true;
			requestContext.addCallbackParam("edit", true);
		}
	}

	/**
	 * 删除一条家属记录
	 */
	public void delFamily() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (selectedFamilyRow == null) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选中一条家属信息", ""));
		} else {
			familyList.remove(selectedFamilyRow);
			selectedFamilyRow = null;
		}
	}

	/**
	 * 新增或修改一条家属信息到家属列表
	 */
	public void saveRelationshipRecord() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		// 关系转换
		String relationShip = livingManageService.selectRelationStr(Long
				.valueOf(addFamily.getRelationship()));
		addFamily.setRelationShipStr(relationShip);
		if (relationFlag == 1) {
			if (addFamily.getDefaultFamily()) {
				for (PensionFamilyDomain temp : familyList) {
					if (temp.getIsdefault() == 1) {
						facesContext.addMessage(null, new FacesMessage(
								FacesMessage.SEVERITY_WARN, "已存在紧急联系人!", ""));
						return;
					}
				}
				addFamily.setIsdefault(1);
			} else {
				addFamily.setIsdefault(2);
			}
			familyList.add(addFamily);
			addFamily = new PensionFamilyDomain();
		} else {
			if (addFamily.getDefaultFamily()) {
				for (PensionFamilyDomain temp : familyList) {
					if (temp.getIsdefault() == 1
							&& temp.getName() != addFamily.getName()) {
						temp.setDefaultFamily(false);
						temp.setIsdefault(2);
						break;
					}
				}
				addFamily.setIsdefault(1);
			} else {
				addFamily.setIsdefault(2);
			}
			requestContext.addCallbackParam("modifySuccess", true);
		}
	}

	/**
	 * 检查是否存在紧急联系人
	 * 
	 * @return
	 */
	public Boolean checkDefaultFamily() {
		Boolean isDefault = false;
		for (PensionFamilyDomain temp : familyList) {
			if (temp.getIsdefault() == 1) {
				isDefault = true;
				break;
			} else {
				isDefault = false;
			}
		}
		return isDefault;
	}

	/**
	 * 未选中一条老人入住记录时触发
	 * 
	 * @param e
	 */
	public void setUnableFlag(UnselectEvent e) {

	}

	private TreeNode root;
	private TreeNode selectedNode;

	public void createTree() {
		root = new DefaultTreeNode("Root", null);
		root.setExpanded(true);
		List<PensionBuilding> buildings = livingManageService
				.selectBuildings(pensionCategary);
		List<DefaultTreeNode> buildingNodes = new ArrayList<DefaultTreeNode>();
		for (int i = 0; i < buildings.size(); i++) {
			PensionBuilding building = buildings.get(i);
			DefaultTreeNode tempBuildingNode = new DefaultTreeNode(building,
					root);
			tempBuildingNode.setExpanded(true);
			buildingNodes.add(tempBuildingNode);
		}
		List<PensionFloor> floors = livingManageService.selectFloors();
		List<DefaultTreeNode> floorNodes = new ArrayList<DefaultTreeNode>();
		for (int i = 0; i < floors.size(); i++) {
			PensionFloor floor = floors.get(i);
			for (DefaultTreeNode buildingNode : buildingNodes) {
				if (((PensionBuilding) buildingNode.getData()).getName()
						.equals(floor.getBuildname())) {
					DefaultTreeNode tempFloorNode = new DefaultTreeNode(floor,
							buildingNode);
					tempFloorNode.setExpanded(true);
					floorNodes.add(tempFloorNode);
					break;
				}
			}
		}
		List<PensionRoom> rooms = livingManageService.selectRooms();
		List<DefaultTreeNode> roomNodes = new ArrayList<DefaultTreeNode>();
		for (int i = 0; i < rooms.size(); i++) {
			PensionRoom room = rooms.get(i);
			for (DefaultTreeNode floorNode : floorNodes) {
				if (((PensionFloor) floorNode.getData()).getName().equals(
						room.getFloorname())) {
					DefaultTreeNode tempRoomNode = new DefaultTreeNode(room,
							floorNode);
					tempRoomNode.setExpanded(true);
					roomNodes.add(tempRoomNode);
					break;
				}
			}
		}
		List<PensionBed> beds = livingManageService.selectBeds(pensionCategary);
		for (int i = 0; i < beds.size(); i++) {
			PensionBed bed = beds.get(i);
			for (DefaultTreeNode roomNode : roomNodes) {
				if (((PensionRoom) roomNode.getData()).getName().equals(
						bed.getRoomname())) {
					DefaultTreeNode tempRoomNode = new DefaultTreeNode("bed",
							bed, roomNode);
					break;
				}
			}
		}
	}

	public void onNodeSelect(NodeSelectEvent event) {
		RequestContext request = RequestContext.getCurrentInstance();
		if (!(event.getTreeNode().getData() instanceof PensionBed)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请选择床位！", "请选择床位！"));
			request.addCallbackParam("validate", false);
		} else {
			PensionBed selectBed = (PensionBed) event.getTreeNode().getData();
			if (selectBed != null) {
				addLivingOlder.setBedId(selectBed.getId());
				addLivingOlder.setBedType(selectBed.getTypename());
				addLivingOlder.setBedTypeId(selectBed.getTypeId().longValue());
				addLivingOlder.setBedName(selectBed.getName());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("选择成功！", "选择成功！"));
				request.addCallbackParam("validate", true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请选择床位！", "请选择床位！"));
				request.addCallbackParam("validate", false);
			}
		}
	}

	/**
	 * 选择一条床位记录
	 */
	public void selectOlderBed() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedBed == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请选择床位！", ""));
			request.addCallbackParam("validate", false);
			return;
		}
		if (selectedBed.getOldernumber() >= 2) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该床位老人已满！", ""));
			request.addCallbackParam("validate", false);
		} else {
			addLivingOlder.setBedId(selectedBed.getId());
			addLivingOlder.setBedType(selectedBed.getTypename());
			addLivingOlder.setBedTypeId(selectedBed.getTypeId().longValue());
			addLivingOlder.setBedName(selectedBed.getName());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("选择成功！", "选择成功！"));
			request.addCallbackParam("validate", true);
		}
	}

	public void initBedList() {
		buildList = livingManageService.selectBuildList(pensionCategary);
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setSelectedRow(LivingRecordDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public LivingRecordDomain getSelectedRow() {
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

	public Boolean getAddOlderStatuseFlag() {
		return addOlderStatuseFlag;
	}

	public void setAddOlderStatuseFlag(Boolean addOlderStatuseFlag) {
		this.addOlderStatuseFlag = addOlderStatuseFlag;
	}

	public void setLivingManageService(LivingManageService livingManageService) {
		this.livingManageService = livingManageService;
	}

	public LivingManageService getLivingManageService() {
		return livingManageService;
	}

	public void setLivingRecordList(List<LivingRecordDomain> livingRecordList) {
		this.livingRecordList = livingRecordList;
	}

	public List<LivingRecordDomain> getLivingRecordList() {
		return livingRecordList;
	}

	public void setAddLivingOlder(LivingRecordDomain addLivingOlder) {
		this.addLivingOlder = addLivingOlder;
	}

	public LivingRecordDomain getAddLivingOlder() {
		return addLivingOlder;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public Long getOlderId() {
		return olderId;
	}

	public List<PensionDicNurse> getNurseLevelList() {
		return nurseLevelList;
	}

	public void setNurseLevelList(List<PensionDicNurse> nurseLevelList) {
		this.nurseLevelList = nurseLevelList;
	}

	public List<PensionDicBedtype> getBedTypeList() {
		return bedTypeList;
	}

	public void setBedTypeList(List<PensionDicBedtype> bedTypeList) {
		this.bedTypeList = bedTypeList;
	}

	public void setNurseList(List<PensionEmployee> nurseList) {
		this.nurseList = nurseList;
	}

	public List<PensionEmployee> getNurseList() {
		return nurseList;
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

	public void setBedSQL(String bedSQL) {
		this.bedSQL = bedSQL;
	}

	public String getBedSQL() {
		return bedSQL;
	}

	public void setAddOlderSQL(String addOlderSQL) {
		this.addOlderSQL = addOlderSQL;
	}

	public String getAddOlderSQL() {
		return addOlderSQL;
	}

	public void setOlderNameFlag(Boolean olderNameFlag) {
		this.olderNameFlag = olderNameFlag;
	}

	public Boolean getOlderNameFlag() {
		return olderNameFlag;
	}

	public String getNurseLevelSQL() {
		return nurseLevelSQL;
	}

	public void setNurseLevelSQL(String nurseLevelSQL) {
		this.nurseLevelSQL = nurseLevelSQL;
	}

	public String getBedTypeSQL() {
		return bedTypeSQL;
	}

	public void setBedTypeSQL(String bedTypeSQL) {
		this.bedTypeSQL = bedTypeSQL;
	}

	public String getNurseSQL() {
		return nurseSQL;
	}

	public void setNurseSQL(String nurseSQL) {
		this.nurseSQL = nurseSQL;
	}

	public void setUpdateLog(PensionLivingLog updateLog) {
		this.updateLog = updateLog;
	}

	public PensionLivingLog getUpdateLog() {
		return updateLog;
	}

	public void setOldBedId(long oldBedId) {
		this.oldBedId = oldBedId;
	}

	public long getOldBedId() {
		return oldBedId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setBlankFlag(Boolean blankFlag) {
		this.blankFlag = blankFlag;
	}

	public Boolean getBlankFlag() {
		return blankFlag;
	}

	public void setLeadDeptId(Long leadDeptId) {
		this.leadDeptId = leadDeptId;
	}

	public Long getLeadDeptId() {
		return leadDeptId;
	}

	public void setOldVisitTime(Date oldVisitTime) {
		this.oldVisitTime = oldVisitTime;
	}

	public Date getOldVisitTime() {
		return oldVisitTime;
	}

	public void setDisableFlag(Boolean disableFlag) {
		this.disableFlag = disableFlag;
	}

	public Boolean getDisableFlag() {
		return disableFlag;
	}

	public Long getHomeDeptId() {
		return homeDeptId;
	}

	public void setHomeDeptId(Long homeDeptId) {
		this.homeDeptId = homeDeptId;
	}

	public void setFamilyList(List<PensionFamilyDomain> familyList) {
		this.familyList = familyList;
	}

	public List<PensionFamilyDomain> getFamilyList() {
		return familyList;
	}

	public void setAddFamily(PensionFamilyDomain addFamily) {
		this.addFamily = addFamily;
	}

	public PensionFamilyDomain getAddFamily() {
		return addFamily;
	}

	public void setRelationDicList(List<PensionDicRelationship> relationDicList) {
		this.relationDicList = relationDicList;
	}

	public List<PensionDicRelationship> getRelationDicList() {
		return relationDicList;
	}

	public void setBlankRelationFlag(Boolean blankRelationFlag) {
		this.blankRelationFlag = blankRelationFlag;
	}

	public Boolean getBlankRelationFlag() {
		return blankRelationFlag;
	}

	public void setRelationFlag(int relationFlag) {
		this.relationFlag = relationFlag;
	}

	public int getRelationFlag() {
		return relationFlag;
	}

	public PensionFamilyDomain getSelectedFamilyRow() {
		return selectedFamilyRow;
	}

	public void setSelectedFamilyRow(PensionFamilyDomain selectedFamilyRow) {
		this.selectedFamilyRow = selectedFamilyRow;
	}

	

	/**
	 * @return the nurseDeptId
	 */
	public List<Long> getNurseDeptId() {
		return nurseDeptId;
	}

	/**
	 * @param nurseDeptId the nurseDeptId to set
	 */
	public void setNurseDeptId(List<Long> nurseDeptId) {
		this.nurseDeptId = nurseDeptId;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public void setOldNurseTypeId(long oldNurseTypeId) {
		this.oldNurseTypeId = oldNurseTypeId;
	}

	public long getOldNurseTypeId() {
		return oldNurseTypeId;
	}

	public void setLeadEmpId(Long leadEmpId) {
		this.leadEmpId = leadEmpId;
	}

	public Long getLeadEmpId() {
		return leadEmpId;
	}

	public void setHomeEmptId(Long homeEmptId) {
		this.homeEmptId = homeEmptId;
	}

	public Long getHomeEmptId() {
		return homeEmptId;
	}

	public void setNurseItemId(Long nurseItemId) {
		this.nurseItemId = nurseItemId;
	}

	public Long getNurseItemId() {
		return nurseItemId;
	}

	public void setBedItemId(Long bedItemId) {
		this.bedItemId = bedItemId;
	}

	public Long getBedItemId() {
		return bedItemId;
	}

	public void setBedUseFlag(boolean bedUseFlag) {
		this.bedUseFlag = bedUseFlag;
	}

	public boolean isBedUseFlag() {
		return bedUseFlag;
	}

	public void setOldBedType(Long oldBedType) {
		this.oldBedType = oldBedType;
	}

	public Long getOldBedType() {
		return oldBedType;
	}

	public int getPensionCategary() {
		return pensionCategary;
	}

	public void setPensionCategary(int pensionCategary) {
		this.pensionCategary = pensionCategary;
	}

	public void setBuildList(List<PensionBuilding> buildList) {
		this.buildList = buildList;
	}

	public List<PensionBuilding> getBuildList() {
		return buildList;
	}
	
	public void setSelectedBed(PensionBed selectedBed) {
		this.selectedBed = selectedBed;
	}

	public PensionBed getSelectedBed() {
		return selectedBed;
	}
}
