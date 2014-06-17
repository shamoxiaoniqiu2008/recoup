package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.fingerPrint.PensionFingerPrintDailyMapper;
import persistence.olderManage.PensionHospitalizeregisterMapper;
import persistence.olderManage.PensionLeaveMapper;
import persistence.olderManage.PensionLivingrecordMapper;
import persistence.olderManage.PensionOlderMapper;
import persistence.olderManage.PensionVisitrecordMapper;
import service.receptionManage.PensionOlderDomain;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

import com.centling.his.util.DateUtil;

import domain.employeeManage.PensionEmployee;
import domain.fingerPrint.PensionFingerPrintDaily;
import domain.olderManage.PensionHospitalizeregister;
import domain.olderManage.PensionHospitalizeregisterExample;
import domain.olderManage.PensionLeave;
import domain.olderManage.PensionLeaveExample;
import domain.olderManage.PensionLivingrecord;
import domain.olderManage.PensionLivingrecordExample;
import domain.olderManage.PensionVisitrecord;

@Service
public class VisitationHandleService {
	@Autowired
	private PensionVisitrecordMapper pensionVisitrecordMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private PensionFingerPrintDailyMapper pensionFingerPrintDailyMapper;
	@Autowired
	private PensionLeaveMapper pensionLeaveMapper;
	@Autowired
	private PensionHospitalizeregisterMapper pensionHospitalizeregisterMapper;
	@Autowired
	private PensionLivingrecordMapper pensionLivingrecordMapper;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 查询被探访老人的探访记录
	 * 
	 * @param id
	 * @param visittype
	 * @param olderId
	 * @return
	 */
	public List<PensionVisitrecordExtend> selectVisitRecords(Long keyId,
			Integer visittype, Long olderId) {
		List<PensionVisitrecordExtend> records = pensionVisitrecordMapper
				.selectVisitRecords(keyId, visittype);
		if (keyId == null) {
			// 某一类型对应老人的所有keyId 例如请假表该老人所有的请假记录ID 就医表该老人所有的就医记录ID
			ArrayList<Long> keyIdByOlderIds = new ArrayList<Long>();
			if (visittype == 1) {
				PensionLivingrecordExample example = new PensionLivingrecordExample();
				example.or().andOlderIdEqualTo(olderId);
				List<PensionLivingrecord> livingRecords = pensionLivingrecordMapper
						.selectByExample(example);
				for (PensionLivingrecord livingRecord : livingRecords) {
					keyIdByOlderIds.add(livingRecord.getId());
				}
			} else if (visittype == 2) {
				PensionLeaveExample example = new PensionLeaveExample();
				example.or().andOlderIdEqualTo(olderId);
				List<PensionLeave> leaveRecords = pensionLeaveMapper
						.selectByExample(example);
				for (PensionLeave leaveRecord : leaveRecords) {
					keyIdByOlderIds.add(leaveRecord.getId());
				}
			} else {
				PensionHospitalizeregisterExample example = new PensionHospitalizeregisterExample();
				example.or().andOlderIdEqualTo(olderId);
				List<PensionHospitalizeregister> medicalRecords = pensionHospitalizeregisterMapper
						.selectByExample(example);
				for (PensionHospitalizeregister medicalRecord : medicalRecords) {
					keyIdByOlderIds.add(medicalRecord.getId());
				}
			}
			Iterator<PensionVisitrecordExtend> iterator = records.iterator();
			// 对查询出来的记录进行过滤 只保留该老人的keyId
			while (iterator.hasNext()) {
				PensionVisitrecordExtend record = iterator.next();
				if (!keyIdByOlderIds.contains(record.getKeyId())) {
					iterator.remove();
				}
			}
		}

		return records;
	}

	/**
	 * 处理一条探访通知
	 */
	@Transactional
	public void updateApprove(Long keyId, Long employeeId, Long deptId) {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("ViewVisitationRecordService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId,
				"pension_visit_key", keyId, deptId);
	}

	/**
	 * 查看探访老人记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @param olderId
	 * @return
	 */
	public List<PensionOlderDomain> selectOlderRecords(Long olderId,
			Long userId, Long deptId) {
		List<PensionOlderDomain> olderList = new ArrayList<PensionOlderDomain>();
		List<Long> visitEmpIdList = new ArrayList<Long>();
		try {
			visitEmpIdList = selectVisitEmpIdList();
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (visitEmpIdList != null) {
			olderList = pensionOlderMapper.selectOlderRecordsById(olderId,
					userId, deptId);
		} else {
			olderList = pensionOlderMapper.selectOlderRecordsById(olderId,
					null, deptId);
		}
		return olderList;
	}

	/**
	 * 查询入住探访老人的人员ID
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

	/**
	 * 查看指纹记录
	 * 
	 * @param selectedRow
	 * @param curEmployee
	 * @param usedFlag
	 * @param visitDate
	 * @param visitEndDate
	 * @return
	 */
	public List<PensionFingerPrintDaily> selectPrintRecords(
			PensionOlderDomain selectedRow, PensionEmployee curEmployee,
			Integer usedFlag, Date visitStartDate, Date visitEndDate) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("roomId", selectedRow.getRoomId());
		map.put("peopleId", curEmployee.getId());
		map.put("startDate", visitStartDate);
		map.put("endDate", DateUtil.getNextDay(visitEndDate));
		map.put("usedFlag", usedFlag);
		map.put("typeId", 1);

		return pensionFingerPrintDailyMapper.selectPrintRecords(map);

	}

	/**
	 * 返回是否 需要指纹认证
	 * 
	 * @return
	 * @throws PmsException
	 */
	public boolean isPrint() throws PmsException {
		String printFlag = systemConfig.selectProperty("VISIT_PRINT_FLAG");
		Integer isPrint = Integer.valueOf(printFlag.trim());
		// 如果等于1 就是采用指纹机制 2为不采用
		return isPrint == 1;

	}

	/**
	 * 录入探访记录信息
	 * 
	 * @param enteredVisitrecord
	 */
	public void entryVisitRecord(PensionVisitrecord enteredVisitrecord) {
		enteredVisitrecord.setHandleFlag(1);
		pensionVisitrecordMapper
				.updateByPrimaryKeySelective(enteredVisitrecord);
	}

	/**
	 * 使开始和结束时间之内的所有指纹记录 都置为已用
	 * 
	 * @param startTime
	 * @param endTime
	 */
	public void beUsed(PensionFingerPrintDaily record) {
		pensionFingerPrintDailyMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 从系统参数中获取探访时间最大间隔
	 * 
	 * @return
	 * @throws PmsException
	 */
	public Long getLongestVisitTime() throws PmsException {
		String longest_visit_time = systemConfig
				.selectProperty("LONGEST_VISIT_TIME");
		return Long.valueOf(longest_visit_time);
	}

	public Date fillRecordTime(Long keyId, Integer visittype) {
		Date recordTime;
		if (visittype == 2) {
			recordTime = pensionLeaveMapper.selectByPrimaryKey(keyId)
					.getRealbacktime();
		} else if (visittype == 3) {
			recordTime = pensionHospitalizeregisterMapper.selectByPrimaryKey(
					keyId).getBacktime();
		} else {
			recordTime = null;
		}

		return recordTime;
	}

	/**
	 * 根据老人ID获得入住记录的主键
	 * 
	 * @param id
	 * @return
	 */
	public Long getLivingId(Long olderId) {
		PensionLivingrecordExample example = new PensionLivingrecordExample();
		example.or().andOlderIdEqualTo(olderId);
		return pensionLivingrecordMapper.selectByExample(example).get(0)
				.getId();
	}

	public void setPensionOlderMapper(PensionOlderMapper pensionOlderMapper) {
		this.pensionOlderMapper = pensionOlderMapper;
	}

	public PensionOlderMapper getPensionOlderMapper() {
		return pensionOlderMapper;
	}

	public void setPensionVisitrecordMapper(
			PensionVisitrecordMapper pensionVisitrecordMapper) {
		this.pensionVisitrecordMapper = pensionVisitrecordMapper;
	}

	public PensionVisitrecordMapper getPensionVisitrecordMapper() {
		return pensionVisitrecordMapper;
	}

	public void setPensionFingerPrintDailyMapper(
			PensionFingerPrintDailyMapper pensionFingerPrintDailyMapper) {
		this.pensionFingerPrintDailyMapper = pensionFingerPrintDailyMapper;
	}

	public PensionFingerPrintDailyMapper getPensionFingerPrintDailyMapper() {
		return pensionFingerPrintDailyMapper;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}

	public PensionLeaveMapper getPensionLeaveMapper() {
		return pensionLeaveMapper;
	}

	public void setPensionLeaveMapper(PensionLeaveMapper pensionLeaveMapper) {
		this.pensionLeaveMapper = pensionLeaveMapper;
	}

	public void setPensionHospitalizeregisterMapper(
			PensionHospitalizeregisterMapper pensionHospitalizeregisterMapper) {
		this.pensionHospitalizeregisterMapper = pensionHospitalizeregisterMapper;
	}

	public PensionHospitalizeregisterMapper getPensionHospitalizeregisterMapper() {
		return pensionHospitalizeregisterMapper;
	}

	public void setPensionLivingrecordMapper(
			PensionLivingrecordMapper pensionLivingrecordMapper) {
		this.pensionLivingrecordMapper = pensionLivingrecordMapper;
	}

	public PensionLivingrecordMapper getPensionLivingrecordMapper() {
		return pensionLivingrecordMapper;
	}
}
