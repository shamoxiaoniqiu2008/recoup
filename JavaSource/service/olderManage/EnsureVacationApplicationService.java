package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.olderManage.PensionLeaveMapper;
import persistence.olderManage.PensionLeaveapproveMapper;
import persistence.olderManage.PensionOlderMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

import com.centling.his.util.DateUtil;

import domain.employeeManage.PensionEmployee;
import domain.olderManage.PensionLeave;
import domain.olderManage.PensionLeaveapprove;
import domain.olderManage.PensionLeaveapproveExample;

@Service
public class EnsureVacationApplicationService {
	@Autowired
	private PensionLeaveapproveMapper pensionLeaveapproveMapper;
	@Autowired
	private PensionLeaveMapper pensionLeaveMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;

	/**
	 * 查询假期申请记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @param olderId
	 * @param currentUser
	 * @param leaveId
	 * @return
	 */
	public List<PensionLeaveExtend> selectVacationApplicationRecords(
			Date startDate, Date endDate, Long olderId, Integer ensureFlag,
			PensionEmployee curEmployee, Long leaveId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if (endDate != null) {
			map.put("endDate", DateUtil.getNextDay(endDate));
		} else {
			map.put("endDate", null);
		}
		map.put("olderId", olderId);
		map.put("currentEmployeeId", curEmployee.getId());
		map.put("ensureFlag", ensureFlag);
		map.put("leaveId", leaveId);
		return pensionLeaveMapper.selectMyVacationApplicationRecords(map);
	}

	public void approve(PensionLeaveapprove approveRecord, Long employeeId,
			Long deptId) {
		PensionLeaveapproveExample example = new PensionLeaveapproveExample();
		example.or().andLeaveIdEqualTo(approveRecord.getLeaveId())
				.andDeptIdEqualTo(deptId);
		pensionLeaveapproveMapper.updateByExampleSelective(approveRecord,
				example);
		updateApprove(approveRecord.getLeaveId(), employeeId, deptId);
		List<Long> empIdList = selectEmptIdList();
		List<Long> deptIdList = selectDeptIdList();
		if (empIdList != null) {
			PensionLeave leaveTemp = new PensionLeave();
			leaveTemp.setId(approveRecord.getLeaveId());
			if (employeeId == empIdList.get(empIdList.size() - 1)) {
				if (approveRecord.getApproveresult() == 1) {
					leaveTemp.setFinalResult(3);
				} else {
					leaveTemp.setFinalResult(4);
				}
			} else {
				leaveTemp.setFinalResult(2);
			}
			pensionLeaveMapper.updateByPrimaryKeySelective(leaveTemp);
		} else {
			PensionLeave leaveTemp = new PensionLeave();
			leaveTemp.setId(approveRecord.getLeaveId());
			if ((deptId == deptIdList.get(deptIdList.size() - 1))
					|| (deptId.equals(deptIdList.get(deptIdList.size() - 1)))) {
				if (approveRecord.getApproveresult() == 1) {
					leaveTemp.setFinalResult(3);
				} else {
					leaveTemp.setFinalResult(4);
				}
			} else {
				leaveTemp.setFinalResult(2);
			}
			pensionLeaveMapper.updateByPrimaryKeySelective(leaveTemp);
		}
	}

	/**
	 * 更新一条评估记录
	 */
	@Transactional
	public void updateApprove(Long leaveId, Long employeeId, Long deptId) {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("entryVacationApplicationService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId,
				"pension_leave", leaveId, deptId);
	}

	/**
	 * 查询各部门请假审批结果
	 * 
	 * @param leaveId
	 * @return
	 */
	public List<PensionLeaveApproveExtend> selectDeptOptions(Long leaveId) {
		List<PensionLeaveApproveExtend> optionList = new ArrayList<PensionLeaveApproveExtend>();
		optionList = pensionLeaveapproveMapper.selectDeptOptions(leaveId);
		return optionList;
	}

	/**
	 * 根据系统参数分离部门Id
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectDeptIdList() {
		List<Long> deptIdList = new ArrayList<Long>();
		String living_apply_dpt_id = null;
		try {
			living_apply_dpt_id = systemConfig
					.selectProperty("VACATION_DPT_ID");
		} catch (PmsException e) {
			e.printStackTrace();
		}
		if (living_apply_dpt_id != null && !living_apply_dpt_id.isEmpty()) {
			String[] dpt_id_arr = living_apply_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				deptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			deptIdList = null;
		}
		return deptIdList;
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
					.selectProperty("VACATION_EMP_ID");
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
	 * 查询老人姓名
	 * 
	 * @param olderId
	 * @return
	 */
	public String selectOlderName(Long olderId) {
		String olderName = pensionOlderMapper.selectByPrimaryKey(olderId)
				.getName();
		return olderName;
	}

	public void setPensionLeaveapproveMapper(
			PensionLeaveapproveMapper pensionLeaveapproveMapper) {
		this.pensionLeaveapproveMapper = pensionLeaveapproveMapper;
	}

	public PensionLeaveapproveMapper getPensionLeaveapproveMapper() {
		return pensionLeaveapproveMapper;
	}

	public PensionLeaveMapper getPensionLeaveMapper() {
		return pensionLeaveMapper;
	}

	public void setPensionLeaveMapper(PensionLeaveMapper pensionLeaveMapper) {
		this.pensionLeaveMapper = pensionLeaveMapper;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}
}
