package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.employeeManage.PensionEmployeeMapper;
import persistence.olderManage.PensionLivingnotifyMapper;
import persistence.olderManage.PensionLivingrecordMapper;
import persistence.olderManage.PensionOlderMapper;
import persistence.system.PensionDeptMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class LivingNoticeService {
	@Autowired
	private PensionLivingnotifyMapper pensionLivingnotifyMapper;
	@Autowired
	private PensionDeptMapper pensionDeptMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private PensionLivingrecordMapper pensionLivingrecordMapper;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;

	/**
	 * 查询部门未处理事件
	 * 
	 * @param deptId
	 * @param olderId
	 * @return
	 */
	public List<PensionNoticeDomain> selectUnDealEventList(Long deptId,
			Long olderId, Date startDate, Date endDate, String handleFlag) {
		List<PensionNoticeDomain> eventList = new ArrayList<PensionNoticeDomain>();
		eventList = pensionLivingnotifyMapper.selectUnDealEventList(deptId,
				olderId, startDate, endDate, handleFlag);
		for (PensionNoticeDomain temp : eventList) {
			if (temp.getHandlerId() != null) {
				temp.setHandleName(pensionEmployeeMapper.selectByPrimaryKey(
						temp.getHandlerId()).getName());
			}
			if (temp.getCheckresult() != null && temp.getCheckerId() != null) {
				temp.setCheckName(pensionEmployeeMapper.selectByPrimaryKey(
						temp.getCheckerId()).getName());
			}
		}
		return eventList;
	}

	/**
	 * 查询部门名称
	 * 
	 * @param deptId
	 * @return
	 */
	public String selectDeptName(Long deptId) {
		String deptName = pensionDeptMapper.selectByPrimaryKey(deptId)
				.getName();
		return deptName;
	}

	/**
	 * 事件确认
	 * 
	 * @param domain
	 */
	@Transactional
	public void updateEventRecords(PensionNoticeDomain domain, Long olderId,
			Long userId, Long deptId) {
		pensionLivingnotifyMapper.updateByPrimaryKeySelective(domain);
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("LivingManageController.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(userId, messagetypeId,
				"pension_livingnotify", olderId, deptId);
	}

	public LivingRecordDomain selectOlderRecord(Long olderId) {
		LivingRecordDomain record = new LivingRecordDomain();
		record = pensionLivingrecordMapper
				.selectLivingRecordsByOlderId(olderId);
		return record;

	}

	/**
	 * 发送消息
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	public void sentMessage(PensionNoticeDomain selectedRow)
			throws PmsException {
		String pensionOlderName = selectedRow.getOlderName();
		String messageContent = "老人" + pensionOlderName
				+ selectedRow.getEventName() + "质检通知已发送！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("LivingNoticeController.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?oldId=" + selectedRow.getOlderId();

		String living_check_dpt_id = systemConfig
				.selectProperty("LIVING_CHECK_DPT_ID");
		String living_check_emp_id = systemConfig
				.selectProperty("LIVING_CHECK_EMP_ID");

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> empIdList = new ArrayList<Long>();

		if (living_check_dpt_id != null && !living_check_dpt_id.isEmpty()) {
			String[] dpt_id_arr = living_check_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}

		if (living_check_emp_id != null && !living_check_emp_id.isEmpty()) {
			String[] emp_id_arr = living_check_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				empIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			empIdList = null;
		}

		String messagename = "【" + pensionOlderName
				+ selectedRow.getEventName() + "】质检通知";

		messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
				messagename, messageContent, url, "pension_livingnotify",
				selectedRow.getId());
	}

	/**
	 * 根据老人ID查询老人信息
	 * 
	 * @param olderId
	 * @return
	 */
	public String selectOlderName(Long olderId) {
		return pensionOlderMapper.selectByPrimaryKey(olderId).getName();
	}

}
