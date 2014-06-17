package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centling.his.util.DateUtil;

import domain.employeeManage.PensionEmployee;
import domain.fingerPrint.PensionFingerPrintDaily;
import domain.olderManage.PensionLeaveapproveExample;
import domain.olderManage.PensionNotbackontime;
import domain.olderManage.PensionNotbackontimeExample;
import domain.olderManage.PensionOlder;
import domain.olderManage.PensionVisitrecord;

import persistence.fingerPrint.PensionFingerPrintDailyMapper;
import persistence.olderManage.PensionLeaveMapper;
import persistence.olderManage.PensionLeaveapproveMapper;
import persistence.olderManage.PensionNotbackontimeMapper;
import persistence.olderManage.PensionOlderMapper;
import persistence.olderManage.PensionVisitrecordMapper;

import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

@Service
public class ViewVacationApprovalService {

	@Autowired
	private PensionLeaveapproveMapper pensionLeaveapproveMapper;
	@Autowired
	private PensionLeaveMapper pensionLeaveMapper;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private PensionVisitrecordMapper pensionVisitrecordMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private PensionNotbackontimeMapper pensionNotbackontimeMapper;
	@Autowired
	private PensionFingerPrintDailyMapper pensionFingerPrintDailyMapper;

	/**
	 * 根据系统参数分离部门Id
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectDeptIdList(String deptParam) throws PmsException {
		String vacation_dpt_id = "";
		List<Long> dptIdList = new ArrayList<Long>();
		try {
			vacation_dpt_id = systemConfig.selectProperty(deptParam);
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (vacation_dpt_id != null && !vacation_dpt_id.isEmpty()) {
			String[] dpt_id_arr = vacation_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}
		return dptIdList;
	}

	/**
	 * 根据系统参数分离人员Id
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectEmpIdList(String empParam) throws PmsException {
		String vacation_emp_id = "";
		List<Long> empIdList = new ArrayList<Long>();
		try {
			vacation_emp_id = systemConfig.selectProperty(empParam);
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (vacation_emp_id != null && !vacation_emp_id.isEmpty()) {
			String[] emp_id_arr = vacation_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				empIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			empIdList = null;
		}
		return empIdList;
	}

	/**
	 * 查询其他部门评估意见
	 * 
	 * @param applyId
	 * @param deptId
	 * @return
	 */
	public List<PensionLeaveApproveExtend> selectDeptEvaList(Long leaveId,
			List<Long> empList, List<Long> deptList) {
		List<PensionLeaveApproveExtend> deptApproveExtendList = new ArrayList<PensionLeaveApproveExtend>();
		if (empList != null) {
			for (Long empId : empList) {
				PensionLeaveApproveExtend extend = new PensionLeaveApproveExtend();
				extend = pensionLeaveapproveMapper.selectDeptRecords(leaveId,
						null, empId);
				if (extend != null) {
					deptApproveExtendList.add(extend);
				}
			}
		} else {
			for (Long deptId : deptList) {
				PensionLeaveApproveExtend extend = new PensionLeaveApproveExtend();
				extend = pensionLeaveapproveMapper.selectDeptRecords(leaveId,
						deptId, null);
				if (extend != null) {
					deptApproveExtendList.add(extend);
				}
			}
		}
		return deptApproveExtendList;
	}

	/**
	 * 根据查询条件查询出请假记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @param olderId
	 * @param passFlag
	 * @param deptList
	 * @param leaveId
	 * @param curEmployee
	 * @return
	 */
	public List<PensionLeaveExtend> selectVacationApproveRecords(
			Date startDate, Date endDate, Long olderId, Integer passFlag,
			List<Long> empList, List<Long> deptList, Long leaveId,
			PensionEmployee curEmployee) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if (endDate != null) {
			map.put("endDate", DateUtil.getNextDay(endDate));
		} else {
			map.put("endDate", null);
		}
		map.put("olderId", olderId);
		map.put("leaveId", leaveId);
		List<PensionLeaveExtend> records = pensionLeaveapproveMapper
				.selectVacationApproveRecords(map);
		Iterator<PensionLeaveExtend> iterator = records.iterator();
		while (iterator.hasNext()) {

			PensionLeaveExtend record = iterator.next();
			// 根据改请假记录是否在未按时返院表中有记录 来讲backontime指端置为1 或 2
			PensionNotbackontimeExample pensionNotbackontimeExample = new PensionNotbackontimeExample();
			pensionNotbackontimeExample.or().andLeaveIdEqualTo(record.getId());
			List<PensionNotbackontime> pensionNotbackontimes = pensionNotbackontimeMapper
					.selectByExample(pensionNotbackontimeExample);
			if (!pensionNotbackontimes.isEmpty()
					&& pensionNotbackontimes.get(0).getCleared() == 2) {
				record.setBackontime(2);
			} else {
				record.setBackontime(1);
			}
			// 将 所有部门都审核通过的请假记录的passFlag 都置为1
			PensionLeaveapproveExample example = new PensionLeaveapproveExample();
			example.or().andApproveresultEqualTo(1)
					.andLeaveIdEqualTo(record.getId());
			Integer ApproveNumber = pensionLeaveapproveMapper.selectByExample(
					example).size();
			// 当同意的部门数量等于发送的总部的数量时 为同意
			int evaluateNum;
			if (empList != null) {
				evaluateNum = empList.size();
			} else {
				evaluateNum = deptList.size();
			}
			if (ApproveNumber == evaluateNum) {
				if (passFlag == 2) {// 如果查询条件为未通过的 就把已通过的全部删除
					iterator.remove();
					continue;
				}
				record.setApproveresults(1);
			} else {
				if (passFlag == 1) {// 如果查询条件为未通过的 就把未通过的全部删除
					iterator.remove();
					continue;
				}
				record.setApproveresults(2);
			}
		}
		return records;
	}

	public void leavedSign(PensionLeaveExtend selectedRow) throws PmsException {

		String messageContent = "老人" + selectedRow.getOlderName() + " 请假离院通知！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("viewVacationApprovalService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?leaveId=" + selectedRow.getId();

		String vacation_dpt_id = systemConfig
				.selectProperty("VACATION_LEAVE_DEPT_ID");
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

		String messagename = "【" + selectedRow.getOlderName() + "】离院通知！";

		messageMessage.sendMessage(messagetypeId, dptIdList, userIdList,
				messagename, messageContent, url, "pension_leave",
				selectedRow.getId());

		// 通知全院
		List<Long> allDptIdList = selectAllDeptIdList();
		allDptIdList.removeAll(dptIdList);
		messageMessage.sendMessage(messagetypeId, allDptIdList, null,
				messagename, messageContent, url, "pension_leave",
				selectedRow.getId());

		selectedRow.setIsbacked(2);
		selectedRow.setIsleaved(1);

		pensionLeaveMapper.updateByPrimaryKeySelective(selectedRow);
		// 更新老人状态味为请假
		PensionOlder older = new PensionOlder();
		older.setId(selectedRow.getOlderId());
		older.setStatuses(4);
		pensionOlderMapper.updateByPrimaryKeySelective(older);

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

	public void backedSign(PensionLeaveExtend selectedRow) throws PmsException {

		String messageContent = "请假老人" + selectedRow.getOlderName() + " 返院通知！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("ViewVisitationRecordService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?keyId=" + selectedRow.getId() + "&visittype=" + 2
				+ "&olderId=" + selectedRow.getOlderId();// 探访类型为请假返院探访

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

		String messagename = "【" + selectedRow.getOlderName() + "】返院通知！";

		messageMessage.sendMessage(messagetypeId, dptIdList, userIdList,
				messagename, messageContent, url, "pension_visit_key",
				selectedRow.getId());

		// 通知全院
		noticeAllDept(dptIdList, selectedRow.getOlderName(),
				selectedRow.getId());
		// 更新请假记录的返院标记和真实返院时间
		selectedRow.setIsbacked(1);
		if (selectedRow.getBackontime() == null) {
			selectedRow.setBackontime(1);
		}
		// 更新老人状态位为3在院
		pensionLeaveMapper.updateByPrimaryKeySelective(selectedRow);
		PensionOlder older = new PensionOlder();
		older.setId(selectedRow.getOlderId());
		older.setStatuses(3);
		pensionOlderMapper.updateByPrimaryKeySelective(older);
		// 在探访记录表中添加记录
		for (Long deptId : dptIdList) {
			PensionVisitrecord visitRecord = new PensionVisitrecord();
			visitRecord.setDeptId(deptId);
			visitRecord.setCleared(2);
			visitRecord.setKeyId(selectedRow.getId());
			// visitRecord.setOlderId(selectedRow.getOlderId().intValue());
			visitRecord.setVisittype(2);
			pensionVisitrecordMapper.insertSelective(visitRecord);
		}
	}

	/**
	 * 老人返院时向全院发送消息
	 * 
	 * @param deptIdList
	 * @param empIdList
	 * @throws PmsException
	 */
	public void noticeAllDept(List<Long> deptIdList, String olderName,
			Long leaveId) throws PmsException {
		String messageContent = "请假老人" + olderName + " 返院通知！";
		String messagename = "【" + olderName + "】返院通知";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("viewVacationApprovalService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?leaveId=" + leaveId;

		List<Long> allDptIdList = selectAllDeptIdList();
		allDptIdList.removeAll(deptIdList);
		messageMessage.sendMessage(messagetypeId, allDptIdList, null,
				messagename, messageContent, url, "pension_leave", leaveId);
	}

	/**
	 * 返回是否 需要指纹认证
	 * 
	 * @return
	 * @throws PmsException
	 */
	public boolean isPrint() throws PmsException {
		String printFlag = systemConfig.selectProperty("VACATION_PRINT_FLAG");
		Integer isPrint = Integer.valueOf(printFlag.trim());
		// 如果等于1 就是采用指纹机制 2为不采用
		return isPrint == 1;

	}

	/**
	 * 将消息置为已处理
	 */
	@Transactional
	public void updateMessage(Long leaveId, Long employeeId, Long deptId) {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("viewVacationApprovalService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId,
				"pension_leave", leaveId, deptId);
	}

	/**
	 * 查看指纹记录
	 * 
	 * @param selectedRow
	 * @param printEndDate
	 * @param printStartDate
	 * @return
	 */
	public List<PensionFingerPrintDaily> selectPrintRecords(
			PensionLeaveExtend selectedRow, Date printStartDate,
			Date printEndDate) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("peopleId", selectedRow.getOlderId());
		map.put("usedFlag", 2);
		map.put("startDate", printStartDate);
		if (printEndDate != null) {
			map.put("endDate", DateUtil.getNextDay(printEndDate));
		} else {
			map.put("endDate", null);
		}
		map.put("typeId", 2);

		return pensionFingerPrintDailyMapper.selectPrintRecords(map);

	}

	/**
	 * 将指纹记录中的所有记录都置为不可用
	 * 
	 * @param printRecords
	 */
	public void clearPrintRecord(List<PensionFingerPrintDaily> printRecords) {
		for (PensionFingerPrintDaily record : printRecords) {
			record.setUsed(1);
			pensionFingerPrintDailyMapper.updateByPrimaryKeySelective(record);
		}
	}

	public void setPensionLeaveapproveMapper(
			PensionLeaveapproveMapper pensionLeaveapproveMapper) {
		this.pensionLeaveapproveMapper = pensionLeaveapproveMapper;
	}

	public PensionLeaveapproveMapper getPensionLeaveapproveMapper() {
		return pensionLeaveapproveMapper;
	}

	public void setPensionLeaveMapper(PensionLeaveMapper pensionLeaveMapper) {
		this.pensionLeaveMapper = pensionLeaveMapper;
	}

	public PensionLeaveMapper getPensionLeaveMapper() {
		return pensionLeaveMapper;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
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

	public void setPensionNotbackontimeMapper(
			PensionNotbackontimeMapper pensionNotbackontimeMapper) {
		this.pensionNotbackontimeMapper = pensionNotbackontimeMapper;
	}

	public PensionNotbackontimeMapper getPensionNotbackontimeMapper() {
		return pensionNotbackontimeMapper;
	}

	public PensionFingerPrintDailyMapper getPensionFingerPrintDailyMapper() {
		return pensionFingerPrintDailyMapper;
	}

	public void setPensionFingerPrintDailyMapper(
			PensionFingerPrintDailyMapper pensionFingerPrintDailyMapper) {
		this.pensionFingerPrintDailyMapper = pensionFingerPrintDailyMapper;
	}

}
