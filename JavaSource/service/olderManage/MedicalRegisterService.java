package service.olderManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centling.his.util.DateUtil;
import domain.olderManage.PensionHospitalizegroup;
import domain.olderManage.PensionHospitalizeregister;
import domain.olderManage.PensionHospitalizeregisterExample;
import domain.olderManage.PensionOlder;
import domain.olderManage.PensionVisitrecord;

import persistence.olderManage.PensionHospitalizegroupMapper;
import persistence.olderManage.PensionHospitalizeregisterMapper;
import persistence.olderManage.PensionOlderMapper;
import persistence.olderManage.PensionVisitrecordMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

@Service
public class MedicalRegisterService {
	@Autowired
	private PensionHospitalizeregisterMapper pensionHospitalizeregisterMapper;
	@Autowired
	private PensionHospitalizegroupMapper pensionHospitalizegroupMapper;
	@Autowired
	private PensionVisitrecordMapper pensionVisitrecordMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 根据条件查询就医登记记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @param olderId
	 * @return
	 */
	public List<PensionHospitalizeregisterExtend> selectMedicalRegisterRecords(
			Date startDate, Date endDate, Integer olderId, Long grouped) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if (endDate != null) {
			map.put("endDate", DateUtil.getNextDay(endDate));
		} else {
			map.put("endDate", null);
		}
		map.put("olderId", olderId);
		map.put("groupId", null);
		map.put("grouped", grouped);

		return pensionHospitalizeregisterMapper
				.selectMedicalRegisterRecords(map);
	}

	/**
	 * 插入就医登记记录
	 * 
	 * @param insertedRow
	 */
	public void insertMedicalRegisterRecord(
			PensionHospitalizeregisterExtend insertedRow) {

		insertedRow.setCleared(2);
		pensionHospitalizeregisterMapper.insertSelective(insertedRow);
	}

	/**
	 * 删除选定的登记记录
	 * 
	 * @param selectedRow
	 */
	public void deleteMedicalRegisterRecord(
			PensionHospitalizeregisterExtend selectedRow) {
		if (selectedRow.getGroupId() != null) {
			PensionHospitalizegroup record = new PensionHospitalizegroup();
			record.setId(selectedRow.getGroupId());
			record.setCounts(-1);
			pensionHospitalizegroupMapper.updateCountsByPrimaryKey(record);
		}

		selectedRow.setCleared(1);
		pensionHospitalizeregisterMapper
				.updateByPrimaryKeySelective(selectedRow);
	}

	/**
	 * 修改选定的登记记录
	 * 
	 * @param updatedRow
	 */
	public void updateMedicalRegisterRecord(
			PensionHospitalizeregisterExtend updatedRow) {

		pensionHospitalizeregisterMapper
				.updateByPrimaryKeySelective(updatedRow);
	}

	/**
	 * 对选定的记录进行分组
	 * 
	 * @param selectedRow
	 * @param groupId
	 */
	public void groupingMedicalRegisterRecord(
			PensionHospitalizeregisterExtend selectedRow, Long groupId) {

		// 更新该老人的分组记录
		selectedRow.setGroupId(groupId);
		pensionHospitalizeregisterMapper
				.updateByPrimaryKeySelective(selectedRow);

		// 将分组人数更新:将绑定的分组的人数+1(如果被选中的记录 已经绑定了分组 那么要将该已绑定分组的人数-1)
		if (selectedRow.getGroupId() != null) {
			PensionHospitalizegroup group = pensionHospitalizegroupMapper
					.selectByPrimaryKey(selectedRow.getGroupId());
			group.setCounts(group.getCounts() - 1);
			pensionHospitalizegroupMapper.updateByPrimaryKeySelective(group);
		}
		PensionHospitalizegroup group = pensionHospitalizegroupMapper
				.selectByPrimaryKey(groupId);
		group.setCounts(group.getCounts() + 1);
		pensionHospitalizegroupMapper.updateByPrimaryKeySelective(group);

	}

	/**
	 * 添加到新分组
	 * 
	 * @param selectedRows
	 * @param insertedGroupRow
	 * @throws PmsException
	 */
	public void groupingMedicalRegisterRecordToNewGroup(
			PensionHospitalizeregisterExtend[] selectedRows,
			PensionHospitalizegroupExtend insertedGroupRow) throws PmsException {
		// 插入新分组
		try {
			if (selectedRows == null || size(selectedRows) == 0) {
				insertedGroupRow.setCounts(0);
			} else {
				insertedGroupRow.setCounts(size(selectedRows));
			}
			pensionHospitalizegroupMapper.insertSelective(insertedGroupRow);
		} catch (Exception e) {
			throw new PmsException("新建分组失败");
		}
		Long groupId = insertedGroupRow.getId();
		updateRegister(selectedRows, groupId);
	}

	/**
	 * 更新登记信息
	 * 
	 * @param selectedRows
	 * @param groupId
	 * @throws PmsException
	 */
	public void updateRegister(PensionHospitalizeregisterExtend[] selectedRows,
			Long groupId) throws PmsException {
		if (selectedRows != null && selectedRows.length > 0) {
			try {
				// 提取要更新的主键
				List<Long> listId = new ArrayList<Long>();
				for (int i = 0; i < selectedRows.length; i++) {
					// 已经分到其他组，又更新到本组操作
					if (selectedRows[i] != null) {
						if (selectedRows[i].getGroupId() != null) {
							PensionHospitalizegroup record = new PensionHospitalizegroup();
							record.setId(selectedRows[i].getGroupId());
							record.setCounts(-1);
							pensionHospitalizegroupMapper
									.updateCountsByPrimaryKey(record);
						}
						listId.add(selectedRows[i].getId());
					}
				}
				// 更新就医信息的分组信息
				for (Long id : listId) {
					PensionHospitalizeregisterExample example = new PensionHospitalizeregisterExample();
					example.or().andIdEqualTo(id);
					PensionHospitalizeregister record = new PensionHospitalizeregister();
					record.setGroupId(groupId);
					pensionHospitalizeregisterMapper.updateByExampleSelective(
							record, example);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new PmsException("添加失败");
			}
		}
	}

	/**
	 * 添加到已存在分组
	 * 
	 * @param selectedRow
	 * @param groupId
	 * @throws PmsException
	 */
	public void groupingMedicalRegisterRecordToGroup(
			PensionHospitalizeregisterExtend[] selectedRows, Long groupId)
			throws PmsException {
		try {
			updateRegister(selectedRows, groupId);
			PensionHospitalizegroup record = new PensionHospitalizegroup();
			record.setId(groupId);
			record.setCounts(size(selectedRows));
			pensionHospitalizegroupMapper.updateCountsByPrimaryKey(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PmsException("添加失败");
		}

	}

	public int size(PensionHospitalizeregisterExtend[] selectedRows) {
		int size = 0;
		for (PensionHospitalizeregisterExtend selectedRow : selectedRows) {
			if (selectedRow != null) {
				size++;
			}
		}
		return size;
	}

	/**
	 * 老人返院后的登记
	 * 
	 * @param updatedRow
	 * @throws PmsException
	 */
	public void backedRegister(PensionHospitalizeregisterExtend updatedRow)
			throws PmsException {

		String messageContent = "就医老人" + updatedRow.getOlderName() + " 返院通知！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("ViewVisitationRecordService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?keyId=" + updatedRow.getId() + "&visittype=" + 3
				+ "&olderId=" + updatedRow.getOlderId();// 探访类型为就医返院探访

		String vacation_dpt_id = systemConfig
				.selectProperty("VACATION_BACK_DEPT_ID");
		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> userIdList = new ArrayList<Long>();
		if (vacation_dpt_id != null && !vacation_dpt_id.isEmpty()) {
			String[] vacationDptIds = vacation_dpt_id.split(",");
			for (int i = 0; i < vacationDptIds.length; i++) {
				dptIdList.add(Long.valueOf(vacationDptIds[i]));
			}
		} else {
			throw new PmsException("请设置申请发送到的部门或人员");
		}

		String messagename = "【" + updatedRow.getOlderName() + "】就医返院通知！";

		messageMessage.sendMessage(messagetypeId, dptIdList, userIdList,
				messagename, messageContent, url, "pension_visit_key",
				updatedRow.getId());
		// updatedRow.setBacktime(new Date());
		updatedRow.setIsback(1);
		pensionHospitalizeregisterMapper
				.updateByPrimaryKeySelective(updatedRow);

		// 更新老人状态位为3在院
		PensionOlder older = new PensionOlder();
		older.setId(updatedRow.getOlderId());
		older.setStatuses(3);
		pensionOlderMapper.updateByPrimaryKeySelective(older);

		// 在探访记录表中添加记录
		for (Long deptId : dptIdList) {
			PensionVisitrecord visitRecord = new PensionVisitrecord();
			visitRecord.setDeptId(deptId);
			visitRecord.setCleared(2);
			visitRecord.setKeyId(updatedRow.getId());
			visitRecord.setVisittype(3);
			pensionVisitrecordMapper.insertSelective(visitRecord);
		}
	}

	public List<PensionHospitalizegroupExtend> selectGroupRecords(
			Date startDate, Date endDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if (endDate != null) {
			map.put("endDate", DateUtil.getNextDay(endDate));
		} else {
			map.put("endDate", null);
		}
		// map.put("managerId", managerId);
		// map.put("groupId", groupId);

		return pensionHospitalizegroupMapper.selectGroupRecords(map);

	}

	public List<PensionHospitalizegroupExtend> selectGroupRecordsAndManager(
			Date startDate, Date endDate, Long managerId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if (endDate != null) {
			map.put("endDate", DateUtil.getNextDay(endDate));
		} else {
			map.put("endDate", null);
		}

		if (managerId != null) {
			map.put("managerId", managerId);
		}
		// map.put("groupId", groupId);

		return pensionHospitalizegroupMapper.selectGroupRecords(map);

	}

	/**
	 * 修改分组
	 * 
	 * @param record
	 * @throws PmsException
	 */
	public void updateGroup(PensionHospitalizegroup record) throws PmsException {
		try {
			pensionHospitalizegroupMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			throw new PmsException("修改失败");
		}
	}

	/**
	 * 删除
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	public void deleteGroupRecord(PensionHospitalizegroupExtend selectedRow)
			throws PmsException {
		try {
			PensionHospitalizegroupExtend a = new PensionHospitalizegroupExtend();
			a.setId(selectedRow.getId());
			a.setCleared(1);
			pensionHospitalizegroupMapper.updateByPrimaryKeySelective(a);
		} catch (Exception e) {
			throw new PmsException("删除失败");
		}
	}

	/*
	 * 获取当前分组的就医人员
	 */
	public List<PensionHospitalizeregisterExtend> selectRegisterRecords(
			Long groupId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", null);
		map.put("endDate", null);
		map.put("olderId", null);
		map.put("groupId", groupId);
		map.put("grouped", null);

		return pensionHospitalizeregisterMapper
				.selectMedicalRegisterRecords(map);
	}

	/**
	 * 获取分组统计信息
	 * 
	 * @param groupId
	 * @return
	 */
	public String selectGroupCollection(Long groupId) {
		List<HospitalGroupCollection> hospitalGroupCollectionList = pensionHospitalizegroupMapper
				.selectGroupCollection(groupId);
		float allMoney = 0;
		String messageContent = "";
		for (HospitalGroupCollection hgc : hospitalGroupCollectionList) {
			messageContent += hgc.getTypeName() + ":" + hgc.getCounts() + "人；";
			allMoney += hgc.getAdvance();
		}
		messageContent += "共收预交金:" + allMoney;
		return messageContent;
	}

	/**
	 * 发送消息
	 * 
	 * @param viewedRow
	 * @throws PmsException
	 */
	public void sendItemCountPaper(PensionHospitalizegroupExtend viewedRow)
			throws PmsException {
		String messageContent = "";

		messageContent += selectGroupCollection(viewedRow.getId());
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("sendMedicalItemCountPaperService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		String url;
		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?groupId=" + viewedRow.getId();

		String vacation_dpt_id = systemConfig.selectProperty("MEDICAL_DPT_ID");
		String vacation_emp_id = systemConfig.selectProperty("MEDICAL_EMP_ID");
		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> empIdList = new ArrayList<Long>();

		if (vacation_dpt_id != null && !vacation_dpt_id.isEmpty()) {
			String[] vacationDptIds = vacation_dpt_id.split(",");
			for (int i = 0; i < vacationDptIds.length; i++) {
				dptIdList.add(Long.valueOf(vacationDptIds[i]));
			}
		} else {
			dptIdList = null;
		}

		if (vacation_emp_id != null && !vacation_emp_id.isEmpty()) {
			String[] vacationDptIds = vacation_emp_id.split(",");
			for (int i = 0; i < vacationDptIds.length; i++) {
				empIdList.add(Long.valueOf(vacationDptIds[i]));
			}
		} else {
			empIdList = null;
		}

		SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dsf = df3.format(viewedRow.getLeavetime());
		String messagename = "" + dsf + ":" + viewedRow.getManagerName()
				+ "有【陪同就医、买药医项目】，请安排好准备工作";

		messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
				messagename, messageContent, url, "pension_hospitalizegroup",
				viewedRow.getId());
		viewedRow.setSended(1);// 将发送标志 表示为1已发送
		PensionHospitalizegroupExtend temp = new PensionHospitalizegroupExtend();
		temp.setId(viewedRow.getId());
		temp.setSended(1);
		pensionHospitalizegroupMapper.updateByPrimaryKeySelective(viewedRow);
	}

	public void setPensionHospitalizeregisterMapper(
			PensionHospitalizeregisterMapper pensionHospitalizeregisterMapper) {
		this.pensionHospitalizeregisterMapper = pensionHospitalizeregisterMapper;
	}

	public PensionHospitalizeregisterMapper getPensionHospitalizeregisterMapper() {
		return pensionHospitalizeregisterMapper;
	}

	public void setPensionHospitalizegroupMapper(
			PensionHospitalizegroupMapper pensionHospitalizegroupMapper) {
		this.pensionHospitalizegroupMapper = pensionHospitalizegroupMapper;
	}

	public PensionHospitalizegroupMapper getPensionHospitalizegroupMapper() {
		return pensionHospitalizegroupMapper;
	}

	public PensionVisitrecordMapper getPensionVisitrecordMapper() {
		return pensionVisitrecordMapper;
	}

	public void setPensionVisitrecordMapper(
			PensionVisitrecordMapper pensionVisitrecordMapper) {
		this.pensionVisitrecordMapper = pensionVisitrecordMapper;
	}

	public PensionOlderMapper getPensionOlderMapper() {
		return pensionOlderMapper;
	}

	public void setPensionOlderMapper(PensionOlderMapper pensionOlderMapper) {
		this.pensionOlderMapper = pensionOlderMapper;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}
}
