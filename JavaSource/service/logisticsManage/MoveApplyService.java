package service.logisticsManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.centling.his.util.DateUtil;

import persistence.configureManage.PensionBedMapper;
import persistence.configureManage.PensionBuildingMapper;
import persistence.configureManage.PensionFloorMapper;
import persistence.configureManage.PensionRoomMapper;
import persistence.dictionary.PensionFamilyMapper;
import persistence.employeeManage.PensionEmployeeMapper;
import persistence.logisticsManage.PensionChangeroomMapper;
import persistence.logisticsManage.PensionMoveApplyMapper;
import persistence.logisticsManage.PensionMoveApprovalMapper;
import persistence.olderManage.PensionLivingLogMapper;
import persistence.olderManage.PensionLivingrecordMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

import domain.configureManage.PensionBed;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionFloor;
import domain.configureManage.PensionRoom;
import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionChangeroom;
import domain.logisticsManage.PensionChangeroomExtend;
import domain.logisticsManage.PensionMoveApplyExtend;
import domain.logisticsManage.PensionMoveApproval;
import domain.olderManage.PensionLivingLog;
import domain.olderManage.PensionLivingLogExample;
import domain.olderManage.PensionLivingrecord;
import domain.olderManage.PensionLivingrecordExample;

@Service
public class MoveApplyService {

	@Autowired
	private PensionMoveApplyMapper pensionMoveApplyMapper;
	@Autowired
	private PensionMoveApprovalMapper pensionMoveApprovalMapper;
	@Autowired
	private PensionChangeroomMapper pensionChangeroomMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
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
	private PensionLivingrecordMapper pensionLivingrecordMapperr;
	@Autowired
	private PensionFamilyMapper pensionFamilyMapper;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;

	public List<PensionMoveApplyExtend> selectMoveApplicationRecords(
			Date startDate, Date endDate, Long olderId, Integer sendFlag,
			Integer approveFlag, Long applyId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if (endDate != null) {
			map.put("endDate", DateUtil.getNextDay(endDate));
		} else {
			map.put("endDate", null);
		}
		map.put("olderId", olderId);
		map.put("applyId", applyId);
		map.put("sendFlag", sendFlag);
		map.put("approveFlag", approveFlag);
		return pensionMoveApplyMapper.selectMoveApplicationRecords(map);
	}

	public void deleteMoveApplicationRecord(PensionMoveApplyExtend selectedRow) {
		selectedRow.setCleared(1);
		pensionMoveApplyMapper.updateByPrimaryKeySelective(selectedRow);

	}

	public void insertMoveApplicationRecord(PensionMoveApplyExtend insertedRow) {
		insertedRow.setCleared(2);
		pensionMoveApplyMapper.insertSelective(insertedRow);

	}

	/**
	 * 处理被选中的搬家记录
	 * 
	 * @param selectedRow
	 * @param curEmployee
	 * @param changeRoomRecords
	 */
	public void handleMoveApplicationRecord(PensionMoveApplyExtend selectedRow,
			PensionEmployee curEmployee,
			List<PensionChangeroomExtend> changeRoomRecords) {
		selectedRow.setFinishFlag(1);
		selectedRow.setFinishTime(new Date());
		selectedRow.setProcessorId(curEmployee.getId());
		pensionMoveApplyMapper.updateByPrimaryKeySelective(selectedRow);
		// 更新信息
		updateApprove(selectedRow.getId(), curEmployee.getId(),
				curEmployee.getDeptId());
		if (selectedRow.getMoveType() == 1) {

			PensionChangeroom changeroomRecord = changeRoomRecords.get(0);
			changeroomRecord.setMoverId(curEmployee.getId());
			changeroomRecord.setChangeroomFlag(1);
			pensionChangeroomMapper
					.updateByPrimaryKeySelective(changeroomRecord);

			// 封装好 updateLivingRecord方法所需要的三个参数 livingrecord log和oldBedId
			PensionLivingrecordExample example = new PensionLivingrecordExample();
			example.or().andOlderIdEqualTo(selectedRow.getOlderId());
			// 根据老人Id查询出老人原来的入住记录
			PensionLivingrecord livingrecord = pensionLivingrecordMapperr
					.selectByExample(example).get(0);
			// 将bedId赋给oldBedId
			Long oldBedId = livingrecord.getBedId();
			// 将入住记录的bedId替换为新的床位Id
			livingrecord.setBedId(changeroomRecord.getNewbedId());
			// 封转log对象 作为参数
			PensionLivingLog log = new PensionLivingLog();
			PensionBed bed = bedMapper.selectByPrimaryKey(livingrecord
					.getBedId());
			log.setBedTypeId(Long.valueOf(bed.getTypeId()));
			log.setOlderId(selectedRow.getOlderId());
			livingrecord.setBedTypeId(Long.valueOf(bed.getTypeId()));
			// 更新老人入住记录
			updateLivingRecord(livingrecord, log, oldBedId);
		}

	}

	public void updateMoveApplicationRecord(PensionMoveApplyExtend updatedRow) {
		pensionMoveApplyMapper.updateByPrimaryKeySelective(updatedRow);

	}

	public void submitMoveApplicationRecord(PensionMoveApplyExtend selectedRow,
			PensionEmployee curEmployee) throws PmsException {
		String messageContent = "老人" + selectedRow.getOlderName()
				+ " 提交了一条搬家申请！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("moveApplyService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?applyId=" + selectedRow.getId()+"&olderId="+selectedRow.getOlderId();

		String dpt_id = systemConfig.selectProperty("MOVE_APPLY_SEND_DEPT_ID");
		String emp_id = systemConfig.selectProperty("MOVE_APPLY_SEND_EMP_ID");

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> empIdList = new ArrayList<Long>();
		if (emp_id != null && !emp_id.isEmpty()) {
			String[] empIds = emp_id.split(",");
			for (int i = 0; i < empIds.length; i++) {
				empIdList.add(Long.valueOf(empIds[i]));
			}
			for (Long empId : empIdList) {

				Long deptId = pensionEmployeeMapper.selectByPrimaryKey(empId)
						.getDeptId();
				PensionMoveApproval pensionMoveApproval = new PensionMoveApproval();
				pensionMoveApproval.setApplyId(selectedRow.getId());
				pensionMoveApproval.setCleared(2);
				pensionMoveApproval.setDeptId(deptId);// 根据系统参数的不同 发送的部门也不同
				pensionMoveApprovalMapper.insertSelective(pensionMoveApproval);

			}

		} else if (dpt_id != null && !dpt_id.isEmpty()) {
			String[] dptIds = dpt_id.split(",");
			for (int i = 0; i < dptIds.length; i++) {
				dptIdList.add(Long.valueOf(dptIds[i]));
			}
			for (Long dptId : dptIdList) {
				PensionMoveApproval pensionMoveApproval = new PensionMoveApproval();
				pensionMoveApproval.setApplyId(selectedRow.getId());
				pensionMoveApproval.setCleared(2);
				pensionMoveApproval.setDeptId(dptId);// 根据系统参数的不同 发送的部门也不同
				pensionMoveApprovalMapper.insertSelective(pensionMoveApproval);
			}
		} else {
			throw new PmsException("请设置申请发送到的部门或人员");
		}

		String messagename = "【" + selectedRow.getOlderName() + "】搬家申请!";

		messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
				messagename, messageContent, url, "pension_move_apply",
				selectedRow.getId());
		
		selectedRow.setApplyTime(new Date());
		selectedRow.setSendFlag(1);// 将发送标志 表示为1已发送
		pensionMoveApplyMapper.updateByPrimaryKeySelective(selectedRow);

		if (selectedRow.getMoveType() == 1) {

			PensionChangeroom changeroom = new PensionChangeroom();
			changeroom.setApplyId(selectedRow.getId());
			changeroom.setOldbedId(selectedRow.getBedId());
			changeroom.setCleared(2);
			pensionChangeroomMapper.insertSelective(changeroom);

		}

	}

	/**
	 * 根据申请ID查询出对应的调房记录
	 * 
	 * @param applyId
	 * @return
	 */
	public List<PensionChangeroomExtend> selectChangeroomApplicationRecords(
			Long applyId) {
		return pensionChangeroomMapper
				.selectChangeroomApplicationRecords(applyId);
	}

	/**
	 * 将被选中行置为已缴费
	 * 
	 * @param selectedRow
	 */
	public void payMoveApplicationRecord(PensionMoveApplyExtend selectedRow) {
		selectedRow.setPayFlag(1);
		pensionMoveApplyMapper.updateByPrimaryKeySelective(selectedRow);
	}

	/**
	 * 更新老人入住记录
	 * 
	 * @param domain
	 * @param updateLog
	 * @param oldBedId
	 */
	public boolean updateLivingRecord(PensionLivingrecord record,
			PensionLivingLog updateLog, long oldBedId) {
		pensionLivingrecordMapperr.updateByPrimaryKeySelective(record);

		updateLivingLog(record.getOlderId());
		updateLog.setUpdateTime(new Date());
		updateLog.setNurseLevelId(record.getNurseLevelId());
		pensionLivingLogMapper.insertSelective(updateLog);

		if (oldBedId != record.getBedId()) {
			// 修改床位老人数目
			PensionBed bed = bedMapper.selectByPrimaryKey(record.getBedId());
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
					PensionFloor floor = floorMapper.selectByPrimaryKey(room
							.getFloorId());
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

		return true;
	}

	/**
	 * 获得维修的价表类型ID
	 * 
	 * @return
	 * @throws PmsException
	 */
	public String getPriceTypeId() throws PmsException {

		return systemConfig.selectProperty("MOVE_ITEM_PURSE_TYPE_ID");
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
	 * 根据老人ID返回相应的大厦号，房间号等等
	 * 
	 * @param olderId
	 * @return
	 */
	public List<PensionMoveApplyExtend> fillField(Long olderId) {
		return pensionMoveApplyMapper.fillField(olderId);
	}

	/**
	 * 更新一条处理记录
	 */
	public void updateApprove(Long applyId, Long employeeId, Long deptId) {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("moveApproveService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId,
				"pension_move_apply", applyId, deptId);
	}

	public void setPensionMoveApplyMapper(
			PensionMoveApplyMapper pensionMoveApplyMapper) {
		this.pensionMoveApplyMapper = pensionMoveApplyMapper;
	}

	public PensionMoveApplyMapper getPensionMoveApplyMapper() {
		return pensionMoveApplyMapper;
	}

	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setPensionMoveApprovalMapper(
			PensionMoveApprovalMapper pensionMoveApprovalMapper) {
		this.pensionMoveApprovalMapper = pensionMoveApprovalMapper;
	}

	public PensionMoveApprovalMapper getPensionMoveApprovalMapper() {
		return pensionMoveApprovalMapper;
	}

	public void setPensionChangeroomMapper(
			PensionChangeroomMapper pensionChangeroomMapper) {
		this.pensionChangeroomMapper = pensionChangeroomMapper;
	}

	public PensionChangeroomMapper getPensionChangeroomMapper() {
		return pensionChangeroomMapper;
	}

	public PensionBedMapper getBedMapper() {
		return bedMapper;
	}

	public void setBedMapper(PensionBedMapper bedMapper) {
		this.bedMapper = bedMapper;
	}

	public PensionRoomMapper getRoomMapper() {
		return roomMapper;
	}

	public void setRoomMapper(PensionRoomMapper roomMapper) {
		this.roomMapper = roomMapper;
	}

	public PensionBuildingMapper getBuildMapper() {
		return buildMapper;
	}

	public void setBuildMapper(PensionBuildingMapper buildMapper) {
		this.buildMapper = buildMapper;
	}

	public PensionFloorMapper getFloorMapper() {
		return floorMapper;
	}

	public void setFloorMapper(PensionFloorMapper floorMapper) {
		this.floorMapper = floorMapper;
	}

	public PensionLivingLogMapper getPensionLivingLogMapper() {
		return pensionLivingLogMapper;
	}

	public void setPensionLivingLogMapper(
			PensionLivingLogMapper pensionLivingLogMapper) {
		this.pensionLivingLogMapper = pensionLivingLogMapper;
	}

	public PensionLivingrecordMapper getPensionLivingrecordMapperr() {
		return pensionLivingrecordMapperr;
	}

	public void setPensionLivingrecordMapperr(
			PensionLivingrecordMapper pensionLivingrecordMapperr) {
		this.pensionLivingrecordMapperr = pensionLivingrecordMapperr;
	}

	public PensionFamilyMapper getPensionFamilyMapper() {
		return pensionFamilyMapper;
	}

	public void setPensionFamilyMapper(PensionFamilyMapper pensionFamilyMapper) {
		this.pensionFamilyMapper = pensionFamilyMapper;
	}

	public void setPensionEmployeeMapper(
			PensionEmployeeMapper pensionEmployeeMapper) {
		this.pensionEmployeeMapper = pensionEmployeeMapper;
	}

	public PensionEmployeeMapper getPensionEmployeeMapper() {
		return pensionEmployeeMapper;
	}

}
