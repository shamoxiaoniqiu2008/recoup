package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.configureManage.PensionBedMapper;
import persistence.configureManage.PensionBuildingMapper;
import persistence.configureManage.PensionFloorMapper;
import persistence.configureManage.PensionRoomMapper;
import persistence.dictionary.PensionDicBedtypeMapper;
import persistence.dictionary.PensionDicEventMapper;
import persistence.dictionary.PensionDicNurseMapper;
import persistence.dictionary.PensionDicRelationshipMapper;
import persistence.dictionary.PensionFamilyMapper;
import persistence.employeeManage.PensionEmployeeMapper;
import persistence.financeManage.PensionDepositMapper;
import persistence.financeManage.PensionOlderItempurseMapper;
import persistence.olderManage.LivingNoticeRecordMapper;
import persistence.olderManage.PensionLivingLogMapper;
import persistence.olderManage.PensionLivingnotifyMapper;
import persistence.olderManage.PensionLivingrecordMapper;
import persistence.olderManage.PensionOlderMapper;
import persistence.olderManage.PensionVisitrecordMapper;
import persistence.system.PensionDeptMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.configureManage.PensionBed;
import domain.configureManage.PensionBedExample;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionBuildingExample;
import domain.configureManage.PensionFloor;
import domain.configureManage.PensionFloorExample;
import domain.configureManage.PensionRoom;
import domain.configureManage.PensionRoomExample;
import domain.dictionary.PensionDicBedtype;
import domain.dictionary.PensionDicBedtypeExample;
import domain.dictionary.PensionDicEvent;
import domain.dictionary.PensionDicEventExample;
import domain.dictionary.PensionDicNurse;
import domain.dictionary.PensionDicNurseExample;
import domain.dictionary.PensionDicRelationship;
import domain.dictionary.PensionDicRelationshipExample;
import domain.dictionary.PensionFamily;
import domain.dictionary.PensionFamilyExample;
import domain.financeManage.PensionDeposit;
import domain.financeManage.PensionOlderItempurse;
import domain.olderManage.LivingNoticeRecord;
import domain.olderManage.LivingNoticeRecordExample;
import domain.olderManage.PensionLivingLog;
import domain.olderManage.PensionLivingLogExample;
import domain.olderManage.PensionLivingnotify;
import domain.olderManage.PensionLivingnotifyExample;
import domain.olderManage.PensionLivingrecord;
import domain.olderManage.PensionOlder;
import domain.olderManage.PensionVisitrecord;
import domain.system.PensionDept;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class LivingManageService {
	@Autowired
	private PensionOlderMapper pensitonOlderMapper;
	@Autowired
	private PensionDeptMapper deptMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionLivingrecordMapper pensionLivingrecordMapperr;
	@Autowired
	private PensionDicNurseMapper pensionDicNurseMapper;
	@Autowired
	private PensionDicBedtypeMapper pensionDicBedtypeMapper;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;
	@Autowired
	private PensionBedMapper bedMapper;
	@Autowired
	private PensionRoomMapper roomMapper;
	@Autowired
	private PensionBuildingMapper buildMapper;
	@Autowired
	private PensionFloorMapper floorMapper;
	@Autowired
	private PensionLivingLogMapper pensionLivingLogMapper;
	@Autowired
	private LivingNoticeRecordMapper livingNoticeRecordMapper;
	@Autowired
	private PensionDicEventMapper pensionDicEventMapper;
	@Autowired
	private PensionLivingnotifyMapper pensionLivingnotifyMapper;
	@Autowired
	private PensionVisitrecordMapper pensionVisitrecordMapper;
	@Autowired
	private PensionDepositMapper pensionDepositMapper;
	@Autowired
	private PensionDicRelationshipMapper pensionDicRelationshipMapper;
	@Autowired
	private PensionFamilyMapper pensionFamilyMapper;
	@Autowired
	private PensionOlderItempurseMapper pensionOlderItempurseMapper;

	/**
	 * 查询根据条件查询老人入住记录列表
	 * 
	 * @return
	 */
	public List<LivingRecordDomain> selectLivingRecords(Long olderId,
			Date StartDate, Date endDate, int pensionCategary) {
		List<LivingRecordDomain> livingRecords = new ArrayList<LivingRecordDomain>();
		livingRecords = pensionLivingrecordMapperr.selectLivingRecords(olderId,
				StartDate, endDate, pensionCategary);
		return livingRecords;
	}

	/**
	 * 插入一条入住记录
	 * 
	 * @throws PmsException
	 */
	@Transactional
	public void insertLivingRecord(PensionLivingrecord record,
			PensionLivingLog updateLog, List<PensionFamilyDomain> familyList,
			PensionOlderItempurse nurseItemRecord,
			PensionOlderItempurse bedItemRecord) {
		try {
			pensionLivingrecordMapperr.insertSelective(record);
			// 插入老人押金表
			PensionDeposit deposit = new PensionDeposit();
			deposit.setOlderId(record.getOlderId());
			pensionDepositMapper.insertSelective(deposit);
			// 修改老人状态为已入住
			PensionOlder older = new PensionOlder();
			older.setId(record.getOlderId());
			older.setStatuses(3);
			pensitonOlderMapper.updateByPrimaryKeySelective(older);
			// 修改床位老人数目
			PensionBed bed = bedMapper.selectByPrimaryKey(record.getBedId());
			Integer olderNo = bed.getOldernumber();
			bed.setOldernumber(olderNo + 1);
			bedMapper.updateByPrimaryKeySelective(bed);
			// 修改房间老人数目
			PensionRoom room = roomMapper.selectByPrimaryKey(bed.getRoomId());
			Integer roomPlderNo = room.getOldernumber();
			room.setOldernumber(roomPlderNo + 1);
			roomMapper.updateByPrimaryKeySelective(room);
			// 修改楼层老人数目
			PensionFloor floor = floorMapper.selectByPrimaryKey(room
					.getFloorId());
			Integer floorOlderNo = floor.getOldernumber();
			floor.setOldernumber(floorOlderNo + 1);
			floorMapper.updateByPrimaryKeySelective(floor);
			// 修改大厦老人数目
			PensionBuilding build = buildMapper.selectByPrimaryKey(floor
					.getBuildId());
			Integer buildOlderNo = build.getOldernumber();
			build.setOldernumber(buildOlderNo + 1);
			buildMapper.updateByPrimaryKeySelective(build);
			// 插入老人入住记录更新日志
			updateLog.setUpdateTime(record.getVisittime());
			pensionLivingLogMapper.insertSelective(updateLog);
			List<Long> empList = selectEmptIdList();
			List<Long> deptList = selectDeptIdList();
			insertLivingNotice(record, empList, deptList);
			// 插入老人护理费用
			pensionOlderItempurseMapper.insertSelective(nurseItemRecord);
			// 插入老人床位费用
			if (bedItemRecord != null) {
				pensionOlderItempurseMapper.insertSelective(bedItemRecord);
			}
			// 插入老人家属信息
			for (PensionFamilyDomain family : familyList) {
				family.setOlderId(record.getOlderId());
				pensionFamilyMapper.insertSelective(family);
			}
			// 插入老人入住探访信息
			List<Long> visitDeptIdList = selectVisitDeptIdList();
			List<Long> visitEmpIdList = selectVisitEmpIdList();
			if (visitEmpIdList == null) {
				for (Long deptId : visitDeptIdList) {
					PensionVisitrecord visitRecord = new PensionVisitrecord();
					visitRecord.setDeptId(deptId);
					visitRecord.setCleared(2);
					visitRecord.setKeyId(record.getId());
					int visitType = Integer.valueOf(JavaConfig
							.fetchProperty("LivingManageService.visitType"));
					visitRecord.setVisittype(visitType);
					pensionVisitrecordMapper.insertSelective(visitRecord);
				}
			} else {
				for (Long empId : visitEmpIdList) {
					PensionVisitrecord visitRecord = new PensionVisitrecord();
					visitRecord.setVisitorId(empId);
					Long deptId = pensionEmployeeMapper.selectByPrimaryKey(
							empId).getDeptId();
					visitRecord.setDeptId(deptId);
					visitRecord.setCleared(2);
					visitRecord.setKeyId(record.getId());
					int visitType = Integer.valueOf(JavaConfig
							.fetchProperty("LivingManageService.visitType"));
					visitRecord.setVisittype(visitType);
					pensionVisitrecordMapper.insertSelective(visitRecord);
				}
			}
			// 发送探访通知给相关部门
			sendVisitMessage(visitEmpIdList, visitDeptIdList, record);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "发送老人探访通知成功",
							""));
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, ex
							.getMessage(), ""));
		}

	}

	/**
	 * //发送探访通知给相关部门
	 * 
	 * @param deptIdList
	 * @throws PmsException
	 */
	public void sendVisitMessage(List<Long> empIdList, List<Long> deptIdList,
			PensionLivingrecord record) throws PmsException {
		String pensionOlderName = pensitonOlderMapper.selectByPrimaryKey(
				record.getOlderId()).getName();
		String messageContent = "老人" + pensionOlderName + "入住探访通知已发送！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("ViewVisitationRecordService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr.trim());
		String url;
		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?keyId=" + record.getId() + "&visittype=" + 1
				+ "&olderId=" + record.getOlderId();
		String messagename = "【" + pensionOlderName + "】入住探访通知";
		messageMessage.sendMessage(messagetypeId, deptIdList, empIdList,
				messagename, messageContent, url, "pension_visit_key",
				record.getId());
	}

	/**
	 * 查询探访老人的部门ID
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectVisitDeptIdList() throws PmsException {
		String visit_dpt_id = systemConfig.selectProperty("VISIT_DEPT_ID");

		List<Long> dptIdList = new ArrayList<Long>();
		if (visit_dpt_id != null && !visit_dpt_id.isEmpty()) {
			String[] dpt_id_arr = visit_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}
		return dptIdList;
	}

	/**
	 * 查询探访老人的人员ID
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectVisitEmpIdList() throws PmsException {
		String visit_emp_id = systemConfig.selectProperty("VISIT_EMP_ID");
		List<Long> empIdList = new ArrayList<Long>();
		if (visit_emp_id != null && !visit_emp_id.isEmpty()) {
			String[] emp_id_arr = visit_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				empIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			empIdList = null;
		}
		return empIdList;
	}

	@Transactional
	public void insertLivingNotice(PensionLivingrecord record,
			List<Long> empList, List<Long> deptList) {
		if (empList != null) {
			for (Long empId : empList) {
				// 插入入住通知主记录
				LivingNoticeRecord notice = new LivingNoticeRecord();
				Long deptId = pensionEmployeeMapper.selectByPrimaryKey(empId)
						.getDeptId();
				notice.setDeptId(deptId);
				notice.setOlderId(record.getOlderId());
				notice.setEmpId(empId);
				livingNoticeRecordMapper.insertSelective(notice);
				Integer eventType = Integer
						.valueOf(JavaConfig
								.fetchProperty("LivingManageController.receptionEventType"));
				PensionDicEventExample eventExample = new PensionDicEventExample();
				eventExample.or().andEmpIdEqualTo(empId)
						.andEventtypeEqualTo(eventType);
				List<PensionDicEvent> eventList = pensionDicEventMapper
						.selectByExample(eventExample);
				for (PensionDicEvent event : eventList) {
					PensionLivingnotify detail = new PensionLivingnotify();
					detail.setCleared(2);
					detail.setEventId(event.getId());
					detail.setHandleresult(2);
					detail.setRecordId(notice.getId().intValue());
					detail.setHandlerId(empId);
					pensionLivingnotifyMapper.insertSelective(detail);
				}
			}
		} else {
			for (Long deptId : deptList) {
				// 插入入住通知主记录
				LivingNoticeRecord notice = new LivingNoticeRecord();
				notice.setDeptId(deptId);
				notice.setOlderId(record.getOlderId());
				livingNoticeRecordMapper.insertSelective(notice);
				Integer eventType = Integer
						.valueOf(JavaConfig
								.fetchProperty("LivingManageController.receptionEventType"));
				PensionDicEventExample eventExample = new PensionDicEventExample();
				eventExample.or().andDeptIdEqualTo(deptId)
						.andEventtypeEqualTo(eventType);
				List<PensionDicEvent> eventList = pensionDicEventMapper
						.selectByExample(eventExample);
				for (PensionDicEvent event : eventList) {
					PensionLivingnotify detail = new PensionLivingnotify();
					detail.setCleared(2);
					detail.setEventId(event.getId());
					detail.setHandleresult(2);
					detail.setRecordId(notice.getId().intValue());
					detail.setDeptId(deptId);
					pensionLivingnotifyMapper.insertSelective(detail);
				}
			}
		}
	}

	/**
	 * 事件确认
	 * 
	 * @param domain
	 */
	@Transactional
	public void updateEventRecords(Long olderId, Long userId, Long deptId) {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("LivingEvaluateController.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(userId, messagetypeId,
				"pension_older", olderId, deptId);
	}

	/**
	 * 查询护理级别
	 * 
	 * @return
	 */
	public List<PensionDicNurse> selectNurseLevel() {
		PensionDicNurseExample example = new PensionDicNurseExample();
		example.or();
		example.setOrderByClause("id");
		List<PensionDicNurse> nurseLevelList = pensionDicNurseMapper
				.selectByExample(example);
		if (nurseLevelList != null && nurseLevelList.size() > 0) {
			return nurseLevelList;
		}
		return null;
	}

	/**
	 * 查询床位标准
	 * 
	 * @return
	 */
	public List<PensionDicBedtype> selectBedType() {
		PensionDicBedtypeExample example = new PensionDicBedtypeExample();
		example.or();
		example.setOrderByClause("id");
		List<PensionDicBedtype> bedTypeList = pensionDicBedtypeMapper
				.selectByExample(example);
		if (bedTypeList != null && bedTypeList.size() > 0) {
			return bedTypeList;
		}
		return null;
	}

	/**
	 * 根据系统参数分离部门Id
	 * 
	 * @return
	 */
	public Long selectLeadDeptId() {
		List<Long> deptIdList = new ArrayList<Long>();
		String living_apply_dpt_id = null;
		try {
			living_apply_dpt_id = systemConfig
					.selectProperty("LIVING_APPLY_DPT_ID");
		} catch (PmsException e) {
			e.printStackTrace();
		}
		if (living_apply_dpt_id != null && !living_apply_dpt_id.isEmpty()) {
			String[] dpt_id_arr = living_apply_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				deptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			return null;
		}
		return deptIdList.get(deptIdList.size() - 1);
	}

	/**
	 * 根据系统参数分离部门Id
	 * 
	 * @return
	 * @throws PmsException
	 */
	public Long selectLeadEmptId() {
		List<Long> emptIdList = new ArrayList<Long>();
		String living_apply_emp_id = null;
		try {
			living_apply_emp_id = systemConfig
					.selectProperty("LIVING_APPLY_EMP_ID");
		} catch (PmsException e) {
			e.printStackTrace();
		}
		if (living_apply_emp_id != null && !living_apply_emp_id.isEmpty()) {
			String[] emp_id_arr = living_apply_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				emptIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			return null;
		}
		return emptIdList.get(emptIdList.size() - 1);
	}

	/**
	 * 根据系统参数分离部门Id
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectEmptIdList() {
		List<Long> emptIdList = new ArrayList<Long>();
		String living_apply_emp_id = null;
		try {
			living_apply_emp_id = systemConfig
					.selectProperty("LIVING_NOTICE_EMP_ID");
		} catch (PmsException e) {
			e.printStackTrace();
		}
		if (living_apply_emp_id != null && !living_apply_emp_id.isEmpty()) {
			String[] emp_id_arr = living_apply_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				emptIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			emptIdList = null;
		}
		return emptIdList;
	}

	/**
	 * 查询护理部门id
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectNurseDeptIds() {
		List<Long> nurseDeptIds = new ArrayList<Long>();
		try {
			String strDeptIds = systemConfig
					.selectProperty("NURSE_DEPT_ID_REPORT");
			String [] str = strDeptIds.split(",");
			for(int i=0;i<str.length;i++){
				nurseDeptIds.add(Long.valueOf(str[i]));
			}
			System.out.println("得到的部门ID长度为："+nurseDeptIds.size());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (PmsException e) {
			e.printStackTrace();
		}
		return nurseDeptIds;
	}

	/**
	 * 查询用户姓名
	 * 
	 * @param employeeId
	 * @return
	 */
	public String selectUserName(Long employeeId) {
		String userName = pensionEmployeeMapper.selectByPrimaryKey(employeeId)
				.getName();
		return userName;
	}

	/**
	 * 更新老人入住记录
	 * 
	 * @param domain
	 * @param updateLog
	 * @param oldBedId
	 */
	@Transactional
	public boolean updateLivingRecord(LivingRecordDomain domain,
			PensionLivingLog updateLog, Long oldBedId, Long oldNurseTypeId,
			List<PensionFamilyDomain> familyList,
			PensionOlderItempurse nurseItemRecord,
			PensionOlderItempurse bedItemRecord) {
		pensionLivingrecordMapperr.updateByPrimaryKeySelective(domain);
		// 插入老人护理费用
		if (nurseItemRecord != null) {
			pensionOlderItempurseMapper.insertSelective(nurseItemRecord);
		}
		// 插入老人床位费用
		if (bedItemRecord != null) {
			pensionOlderItempurseMapper.insertSelective(bedItemRecord);
		}
		Date curDate = new Date();
		if (!oldBedId.equals(domain.getBedId())
				|| !oldNurseTypeId.equals(domain.getNurseLevelId())) {
			// 如果老人未入住时更改床位信息和护理级别
			if (domain.getVisittime().after(curDate)) {
				updateLivingLog(domain.getOlderId(), domain.getVisittime());
			} else {
				updateLivingLog(domain.getOlderId());
				updateLog.setUpdateTime(new Date());
				pensionLivingLogMapper.insertSelective(updateLog);
			}
			if (oldBedId != domain.getBedId()) {
				// 修改床位老人数目
				PensionBed bed = bedMapper
						.selectByPrimaryKey(domain.getBedId());
				Integer olderNo = bed.getOldernumber();
				bed.setOldernumber(olderNo + 1);
				bedMapper.updateByPrimaryKeySelective(bed);
				PensionBed oldBed = bedMapper.selectByPrimaryKey(oldBedId);
				Integer oldOlderNo = oldBed.getOldernumber();
				oldBed.setOldernumber(oldOlderNo - 1);
				bedMapper.updateByPrimaryKeySelective(oldBed);
				// 修改房间老人数目
				if (bed.getRoomId() != oldBed.getRoomId()) {
					PensionRoom room = roomMapper.selectByPrimaryKey(bed
							.getRoomId());
					Integer roomPlderNo = room.getOldernumber();
					room.setOldernumber(roomPlderNo + 1);
					roomMapper.updateByPrimaryKeySelective(room);
					PensionRoom oldRoom = roomMapper.selectByPrimaryKey(oldBed
							.getRoomId());
					Integer oldRoomOderNo = oldRoom.getOldernumber();
					oldRoom.setOldernumber(oldRoomOderNo - 1);
					roomMapper.updateByPrimaryKeySelective(oldRoom);
					if (room.getFloorId() != oldRoom.getFloorId()) {
						// 修改楼层老人数目
						PensionFloor floor = floorMapper
								.selectByPrimaryKey(room.getFloorId());
						Integer floorOlderNo = floor.getOldernumber();
						floor.setOldernumber(floorOlderNo + 1);
						floorMapper.updateByPrimaryKeySelective(floor);
						PensionFloor oldfloor = floorMapper
								.selectByPrimaryKey(oldRoom.getFloorId());
						Integer oldfloorOderNo = oldfloor.getOldernumber();
						oldfloor.setOldernumber(oldfloorOderNo - 1);
						floorMapper.updateByPrimaryKeySelective(oldfloor);
						if (floor.getBuildId() != oldfloor.getBuildId()) {
							// 修改大厦老人数目
							PensionBuilding build = buildMapper
									.selectByPrimaryKey(floor.getBuildId());
							Integer buildOlderNo = build.getOldernumber();
							build.setOldernumber(buildOlderNo + 1);
							buildMapper.updateByPrimaryKeySelective(build);
							// 修改大厦老人数目
							PensionBuilding oldBuild = buildMapper
									.selectByPrimaryKey(oldfloor.getBuildId());
							Integer oldBuildOlderNo = oldBuild.getOldernumber();
							build.setOldernumber(oldBuildOlderNo - 1);
							buildMapper.updateByPrimaryKeySelective(oldBuild);
						}
					}
				}
			}
		}
		// 更改老人家属信息
		// 删除原来的老人家属信息
		PensionFamilyExample fexample = new PensionFamilyExample();
		fexample.or().andOlderIdEqualTo(domain.getOlderId());
		pensionFamilyMapper.deleteByExample(fexample);
		// 添加新的老人家属信息
		for (PensionFamilyDomain family : familyList) {
			family.setOlderId(domain.getOlderId());
			pensionFamilyMapper.insertSelective(family);
		}
		return true;
	}

	// 更新床位及护理级别更改日志
	public void updateLivingLog(Long olderId) {
		PensionLivingLogExample example = new PensionLivingLogExample();
		example.or().andOlderIdEqualTo(olderId);
		example.setOrderByClause("update_time desc");
		PensionLivingLog oldLog = pensionLivingLogMapper.selectByExample(
				example).get(0);
		oldLog.setUpdateTimeNew(new Date());
		pensionLivingLogMapper.updateByPrimaryKeySelective(oldLog);
	}

	// 更新床位及护理级别更改日志(老人提前入住）
	public void updateLivingLog(Long olderId, Date visitTime) {
		PensionLivingLogExample example = new PensionLivingLogExample();
		example.or().andOlderIdEqualTo(olderId);
		example.setOrderByClause("update_time desc");
		PensionLivingLog oldLog = pensionLivingLogMapper.selectByExample(
				example).get(0);
		oldLog.setUpdateTime(visitTime);
		pensionLivingLogMapper.updateByPrimaryKeySelective(oldLog);
	}

	/**
	 * 查询接收入住申请评估通知的部门列表
	 * 
	 * @param deptIdList
	 * @return
	 * @throws PmsException
	 */
	public List<PensionDept> selectApplyDept() throws PmsException {
		List<Long> deptIdList = selectDeptIdList();
		List<PensionDept> deptList = new ArrayList<PensionDept>();
		PensionDept dept;
		for (Long deptId : deptIdList) {
			dept = deptMapper.selectByPrimaryKey(deptId);
			deptList.add(dept);
		}
		return deptList;
	}

	/**
	 * 查询管家部门ID
	 * 
	 * @return
	 */
	public Long selectDeskDeptId() {
		Long deptId = null;
		String desk_dept_id = null;
		try {
			desk_dept_id = systemConfig.selectProperty("DESK_DEPT_ID");
		} catch (PmsException e) {
			e.printStackTrace();
		}
		if (desk_dept_id != null && !desk_dept_id.isEmpty()) {
			deptId = Long.valueOf(desk_dept_id);
		} else {
			deptId = null;
		}
		return deptId;
	}

	/**
	 * 查询管家人员ID
	 * 
	 * @return
	 */
	public Long selectDeskEmptId() {
		Long deskEmpId = null;
		String desk_emp_id = null;
		try {
			desk_emp_id = systemConfig.selectProperty("DESK_EMP_ID");
		} catch (PmsException e) {
			e.printStackTrace();
		}
		if (desk_emp_id != null && !desk_emp_id.isEmpty()) {
			deskEmpId = Long.valueOf(desk_emp_id);
		} else {
			deskEmpId = null;
		}
		return deskEmpId;
	}

	/**
	 * 根据系统参数分离部门Id
	 * 
	 * @return
	 */
	public List<Long> selectDeptIdList() {
		String notice_dpt_id = "";
		try {
			notice_dpt_id = systemConfig.selectProperty("LIVING_NOTICE_DPT_ID");
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Long> dptIdList = new ArrayList<Long>();
		if (notice_dpt_id != null && !notice_dpt_id.isEmpty()) {
			String[] dpt_id_arr = notice_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}
		return dptIdList;

	}

	/**
	 * 发送消息
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	public void sentMessage(LivingRecordDomain selectedRow) throws PmsException {
		String pensionOlderName = selectedRow.getOlderName();
		Long olderId = selectedRow.getOlderId();
		String messageContent = "老人" + pensionOlderName + "入住通知已提交！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("LivingManageController.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?oldId=" + olderId;
		List<Long> dptIdList = selectDeptIdList();
		List<Long> empIdList = selectEmptIdList();
		List<PensionLivingnotify> noticeDetails;
		if (empIdList != null) {
			for (Long empId : empIdList) {
				noticeDetails = new ArrayList<PensionLivingnotify>();
				noticeDetails = selectEmpNoticeDetails(olderId, empId);
				List<Long> newEmpIdList = new ArrayList<Long>();
				newEmpIdList.add(empId);
				for (PensionLivingnotify detail : noticeDetails) {
					String eventName = pensionDicEventMapper
							.selectByPrimaryKey(detail.getEventId())
							.getEventname();
					String messagename = "【" + pensionOlderName + eventName
							+ "】入住通知";
					messageMessage.sendMessage(messagetypeId, dptIdList,
							newEmpIdList, messagename, messageContent, url,
							"pension_livingnotify", detail.getId());
				}
			}
		} else {
			for (Long deptId : dptIdList) {
				noticeDetails = new ArrayList<PensionLivingnotify>();
				noticeDetails = selectNoticeDetails(olderId, deptId);
				List<Long> newDptIdList = new ArrayList<Long>();
				newDptIdList.add(deptId);
				for (PensionLivingnotify detail : noticeDetails) {
					String eventName = pensionDicEventMapper
							.selectByPrimaryKey(detail.getEventId())
							.getEventname();
					String messagename = "【" + pensionOlderName + eventName
							+ "】入住通知";
					messageMessage.sendMessage(messagetypeId, newDptIdList,
							empIdList, messagename, messageContent, url,
							"pension_livingnotify", detail.getId());
				}
			}
		}
		// 通知全院
		noticeAllDept(dptIdList, pensionOlderName, olderId);
	}

	/**
	 * 入住时向全院发送消息
	 * 
	 * @param deptIdList
	 * @param empIdList
	 * @throws PmsException
	 */
	public void noticeAllDept(List<Long> deptIdList, String olderName,
			Long olderId) throws PmsException {
		String messageContent = "老人" + olderName + "入住通知已提交！";
		String messagename = "【" + olderName + "】入住通知";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("DepartRegisterController.comfirmId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?olderId=" + olderId;

		List<Long> visitDptIdList = selectVisitDeptIdList();
		List<Long> allDptIdList = selectAllDeptIdList();
		visitDptIdList.addAll(deptIdList);
		allDptIdList.removeAll(visitDptIdList);
		messageMessage.sendMessage(messagetypeId, allDptIdList, null,
				messagename, messageContent, url, "pension_livingrecord",
				olderId);
	}

	/**
	 * 查询全院所有部门ID列表
	 * 
	 * @return
	 */
	public List<Long> selectAllDeptIdList() {
		String all_dpt_id = "";
		try {
			all_dpt_id = systemConfig.selectProperty("ALL_NOTIFY_DPT_ID");
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Long> dptIdList = new ArrayList<Long>();
		if (all_dpt_id != null && !all_dpt_id.isEmpty()) {
			String[] dpt_id_arr = all_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}
		return dptIdList;
	}

	/**
	 * 查询部门处理事件
	 * 
	 * @param olderId
	 * @param deptId
	 * @return
	 */
	public List<PensionLivingnotify> selectNoticeDetails(Long olderId,
			Long deptId) {
		List<PensionLivingnotify> details = new ArrayList<PensionLivingnotify>();
		LivingNoticeRecordExample example = new LivingNoticeRecordExample();
		example.or().andOlderIdEqualTo(olderId).andDeptIdEqualTo(deptId);
		Long recordId = livingNoticeRecordMapper.selectByExample(example)
				.get(0).getId();
		PensionLivingnotifyExample exam = new PensionLivingnotifyExample();
		exam.or().andRecordIdEqualTo(recordId.intValue());
		details = pensionLivingnotifyMapper.selectByExample(exam);
		return details;
	}

	/**
	 * 查询人员处理事件
	 * 
	 * @param olderId
	 * @param empId
	 * @return
	 */
	public List<PensionLivingnotify> selectEmpNoticeDetails(Long olderId,
			Long empId) {
		List<PensionLivingnotify> details = new ArrayList<PensionLivingnotify>();
		LivingNoticeRecordExample example = new LivingNoticeRecordExample();
		example.or().andOlderIdEqualTo(olderId).andEmpIdEqualTo(empId);
		Long recordId = livingNoticeRecordMapper.selectByExample(example)
				.get(0).getId();
		PensionLivingnotifyExample exam = new PensionLivingnotifyExample();
		exam.or().andRecordIdEqualTo(recordId.intValue());
		details = pensionLivingnotifyMapper.selectByExample(exam);
		return details;
	}

	/**
	 * 查询老人家属列表
	 * 
	 * @return
	 */
	public List<PensionFamilyDomain> selectFamilyRecord(Long olderId) {
		List<PensionFamilyDomain> familyRecords = pensionLivingrecordMapperr
				.selectFamily(olderId);
		return familyRecords;
	}

	/**
	 * 查询关系列表
	 * 
	 * @return
	 */
	public List<PensionDicRelationship> selectRelationshipList() {
		PensionDicRelationshipExample example = new PensionDicRelationshipExample();
		example.or();
		List<PensionDicRelationship> relationList = pensionDicRelationshipMapper
				.selectByExample(example);
		return relationList;
	}

	/**
	 * 查询一条关系名称
	 * 
	 * @param relationId
	 * @return
	 */
	public String selectRelationStr(Long relationId) {
		PensionDicRelationshipExample example = new PensionDicRelationshipExample();
		example.or().andIdEqualTo(relationId);
		return pensionDicRelationshipMapper.selectByExample(example).get(0)
				.getRelationship();
	}

	/**
	 * 查询家属最大的Id
	 * 
	 * @return
	 */
	public Long selectFamilyMaxId() {
		PensionFamilyExample example = new PensionFamilyExample();
		example.or();
		example.setOrderByClause("id desc");
		List<PensionFamily> familyList = pensionFamilyMapper
				.selectByExample(example);
		Long maxId = 0L;
		if (familyList != null && familyList.size() != 0) {
			maxId = familyList.get(0).getId();
		}
		return maxId;
	}

	/**
	 * 查询所有未删除的大厦记录 add by mary 2013-12-10
	 */
	public List<PensionBuilding> selectBuildings(int categary) {
		PensionBuildingExample example = new PensionBuildingExample();
		example.or().andClearedEqualTo(2).andBuildingCategaryEqualTo(categary);
		return buildMapper.selectByExample(example);
	}

	/**
	 * 查询所有未删除的楼层记录 add by mary 2013-12-10
	 */
	public List<PensionFloor> selectFloors() {
		PensionFloorExample example = new PensionFloorExample();
		example.or().andClearedEqualTo(2);
		return floorMapper.selectByExample(example);
	}

	/**
	 * 查询所有未删除的房间记录 add by mary 2013-12-10
	 */
	public List<PensionRoom> selectRooms() {
		PensionRoomExample example = new PensionRoomExample();
		example.or().andClearedEqualTo(2);
		return roomMapper.selectByExample(example);
	}

	/**
	 * 查询所有未删除的老人数少于2的床位记录 add by mary 2013-12-10
	 */
	public List<PensionBed> selectBeds(int categary) {
		PensionBedExample example = new PensionBedExample();
		if (categary == 1) {
			example.or().andClearedEqualTo(2).andOldernumberLessThan(2);
		} else {
			example.or().andClearedEqualTo(2);
		}
		return bedMapper.selectByExample(example);
	}

	/**
	 * 查询床位价表ID
	 * 
	 * @param bedTypeId
	 * @return
	 */
	public Long selectBedItemId(Long bedTypeId) {
		return pensionDicBedtypeMapper.selectBedItemId(bedTypeId);
	}

	public List<PensionBuilding> selectBuildList(int pensionCategary) {
		List<PensionBuilding> buildList = new ArrayList<PensionBuilding>();
		buildList = selectBuildings(pensionCategary);
		for (PensionBuilding build : buildList) {
			List<PensionFloor> floorList = new ArrayList<PensionFloor>();
			floorList = selectFloors(build.getId());
			for (PensionFloor floor : floorList) {
				List<PensionBed> bedList = new ArrayList<PensionBed>();
				bedList = selectBeds(floor.getName());
				floor.setBedList(bedList);
			}
			build.setFloorList(floorList);
		}
		return buildList;
	}

	/**
	 * 查询所有未删除的大厦记录 add by mary 2013-12-10
	 */
	public List<PensionBuilding> selectBuildings() {
		PensionBuildingExample example = new PensionBuildingExample();
		example.or().andClearedEqualTo(2).andBuildingCategaryEqualTo(1);
		example.setOrderByClause("sortid asc");
		return buildMapper.selectByExample(example);
	}

	/**
	 * 查询所有未删除的楼层记录 add by wensy 2013-01-21
	 */
	public List<PensionFloor> selectFloors(Long buildId) {
		PensionFloorExample example = new PensionFloorExample();
		example.or().andClearedEqualTo(2).andBuildIdEqualTo(buildId);
		example.setOrderByClause("sortid desc");
		return floorMapper.selectByExample(example);
	}

	/**
	 * 查询所有未删除的床位记录 add by wensy 2014-01-21
	 */
	public List<PensionBed> selectBeds(String flooName) {
		List<PensionBed> bedList = new ArrayList<PensionBed>();
		bedList = bedMapper.selectBedList(flooName);
		return bedList;
	}

}
