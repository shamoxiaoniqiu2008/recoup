package service.logisticsManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.logisticsManage.PensionChangeroomMapper;
import persistence.logisticsManage.PensionMoveApplyMapper;
import persistence.logisticsManage.PensionMoveApprovalMapper;
import persistence.olderManage.PensionOlderMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

import com.centling.his.util.DateUtil;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionChangeroomExtend;
import domain.logisticsManage.PensionMoveApply;
import domain.logisticsManage.PensionMoveApplyExtend;

@Service
public class MoveApproveService {

	@Autowired
	private PensionMoveApplyMapper pensionMoveApplyMapper;
	@Autowired
	private PensionMoveApprovalMapper pensionMoveApprovalMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private PensionChangeroomMapper pensionChangeroomMapper;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;

	public List<PensionMoveApplyExtend> selectMoveApplicationRecords(
			Date startDate, Date endDate, Long olderId, Integer ensureFlag,
			PensionEmployee curEmployee, Long applyId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if (endDate != null) {
			map.put("endDate", DateUtil.getNextDay(endDate));
		} else {
			map.put("endDate", null);
		}
		map.put("olderId", olderId);
		map.put("deptId", curEmployee.getDeptId());
		map.put("ensureFlag", ensureFlag);
		map.put("applyId", applyId);
		return pensionMoveApprovalMapper.selectMoveApplicationRecords(map);
	}

	public void approve(PensionMoveApplyExtend selectedRow, Long employeeId,
			Long deptId, String employeeName) throws PmsException {
		Long applyId = selectedRow.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptId", deptId);
		map.put("applyId", applyId);
		map.put("approverId", employeeId);
		map.put("deptId", deptId);
		map.put("approvalResult", 1);
		map.put("currentTime", new Date());

		pensionMoveApprovalMapper.check(map);
		updateApprove(applyId, employeeId, deptId);

		String messageContent = "员工" + employeeName + " 发送了一条审批结果通知！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("moveApproveService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?applyId=" + selectedRow.getId();

		String vacation_dpt_id = systemConfig
				.selectProperty("MOVE_APPROVE_SEND_DEPT_ID");
		String vacation_emp_id = systemConfig
				.selectProperty("MOVE_APPROVE_SEND_EMP_ID");

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> userIdList = new ArrayList<Long>();

		if (vacation_emp_id != null && !vacation_emp_id.isEmpty()) {
			String[] vacationEmpIds = vacation_emp_id.split(",");
			for (int i = 0; i < vacationEmpIds.length; i++) {
				userIdList.add(Long.valueOf(vacationEmpIds[i]));
			}
		} else if (vacation_dpt_id != null && !vacation_dpt_id.isEmpty()) {
			String[] vacationDptIds = vacation_dpt_id.split(",");
			for (int i = 0; i < vacationDptIds.length; i++) {
				dptIdList.add(Long.valueOf(vacationDptIds[i]));
			}
		} else {
			throw new PmsException("请设置申请发送到的部门或人员");
		}

		String messagename = "【" + employeeName + "】审批结果通知！";

		messageMessage.sendMessage(messagetypeId, dptIdList, userIdList,
				messagename, messageContent, url, "pension_move_apply",
				selectedRow.getId());

	}

	public void refuse(PensionMoveApplyExtend selectedRow, Long employeeId,
			Long deptId, String employeeName) throws PmsException {
		Long applyId = selectedRow.getId();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptId", deptId);
		map.put("applyId", applyId);
		map.put("approverId", employeeId);
		map.put("deptId", deptId);
		map.put("approvalResult", 2);
		map.put("currentTime", new Date());
		pensionMoveApprovalMapper.check(map);
		updateApprove(applyId, employeeId, deptId);

		String messageContent = "员工" + employeeName + " 发送了一条审批结果通知！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("moveApproveService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?applyId=" + selectedRow.getId();

		String vacation_dpt_id = systemConfig
				.selectProperty("MOVE_APPROVE_SEND_DEPT_ID");
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

		String messagename = "【" + employeeName + "】审批结果通知！";

		messageMessage.sendMessage(messagetypeId, dptIdList, userIdList,
				messagename, messageContent, url, "pension_move_apply",
				selectedRow.getId());

	}

	/**
	 * 根据搬家申请主键查询对应的调房记录
	 * 
	 * @param id
	 * @return
	 */
	public List<PensionChangeroomExtend> selectChangeroomApplicationRecords(
			Long applyId) {

		return pensionChangeroomMapper
				.selectChangeroomApplicationRecords(applyId);

	}

	/**
	 * 修改换房记录
	 * 
	 * @param selectedChangeRoomRow
	 */
	public void updateChangeroomApplicationRecord(
			PensionChangeroomExtend selectedChangeRoomRow) {
		pensionChangeroomMapper
				.updateByPrimaryKeySelective(selectedChangeRoomRow);

	}

	/**
	 * 更新一条评估记录
	 */
	@Transactional
	public void updateApprove(Long applyId, Long employeeId, Long deptId) {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("moveApplyService.messagetypeId");
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

	public void setPensionMoveApprovalMapper(
			PensionMoveApprovalMapper pensionMoveApprovalMapper) {
		this.pensionMoveApprovalMapper = pensionMoveApprovalMapper;
	}

	public PensionMoveApprovalMapper getPensionMoveApprovalMapper() {
		return pensionMoveApprovalMapper;
	}

	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public PensionChangeroomMapper getPensionChangeroomMapper() {
		return pensionChangeroomMapper;
	}

	public void setPensionChangeroomMapper(
			PensionChangeroomMapper pensionChangeroomMapper) {
		this.pensionChangeroomMapper = pensionChangeroomMapper;
	}

	public List<PensionChangeroomExtend> fillBedField(Long newbedId) {
		return pensionChangeroomMapper.fillBedField(newbedId);
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	/**
	 * 保存不同意原因
	 * 
	 * @param id
	 * @param note
	 *            add by mary.liu 2014-04-12
	 */
	public void refuseApprove(Long id, String note) {
		PensionMoveApply moveApply = new PensionMoveApply();
		moveApply.setId(id);
		moveApply.setNote(note);
		pensionMoveApplyMapper.updateByPrimaryKeySelective(moveApply);
	}

	/**
	 * 查询老人姓名
	 * 
	 * @param olderId
	 * @return
	 */
	public String selectOlderName(Long olderId) {
		return pensionOlderMapper.selectByPrimaryKey(olderId).getName();
	}
}
