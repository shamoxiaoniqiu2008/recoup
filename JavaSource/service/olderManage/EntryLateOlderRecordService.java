package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.olderManage.PensionLeaveMapper;
import persistence.olderManage.PensionNotbackontimeMapper;
import persistence.olderManage.PensionOlderMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

import com.centling.his.util.DateUtil;

import domain.olderManage.PensionLeave;
import domain.olderManage.PensionLeaveExample;
import domain.olderManage.PensionNotbackontime;
import domain.olderManage.PensionOlderExample;

@Service
public class EntryLateOlderRecordService {
	@Autowired
	private PensionNotbackontimeMapper pensionNotbackontimeMapper;
	@Autowired
	private PensionLeaveMapper pensionLeaveMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;

	public List<PensionNotbackontimeExtend> selectLateOlderRecords(
			Date startDate, Date endDate, Integer olderId, Integer backFlag) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if (endDate != null) {
			map.put("endDate", DateUtil.getNextDay(endDate));
		} else {
			map.put("endDate", null);
		}
		map.put("olderId", olderId);
		map.put("backFlag", backFlag);
		return pensionNotbackontimeMapper.selectLateOlderRecords(map);
	}

	public void insertLateOlderRecord(PensionNotbackontime insertedRow) {
		insertedRow.setCleared(2);
		pensionNotbackontimeMapper.insertSelective(insertedRow);

		Long leaveId = insertedRow.getLeaveId();
		PensionLeave leave = new PensionLeave();
		// 更新该对应的请假记录的预计返院时间
		leave.setId(leaveId);
		leave.setBackontime(2);
		leave.setExpectbacktime(insertedRow.getNewbacktime());
		pensionLeaveMapper.updateByPrimaryKeySelective(leave);
	}

	public List<PensionNotbackontimeExtend> fillField(Long olderId) {
		return pensionNotbackontimeMapper.fillField(olderId);
	}

	public void deleteLateOlderRecord(PensionNotbackontime selectedRow) {
		selectedRow.setCleared(1);
		pensionNotbackontimeMapper.updateByPrimaryKeySelective(selectedRow);
	}

	public void updateLateOlderRecord(PensionNotbackontime updatedRow) {
		pensionNotbackontimeMapper.updateByPrimaryKeySelective(updatedRow);
	}

	/**
	 * 老人请假未按时返院提醒
	 * 
	 * @param checkDate
	 */

	public void delayBackNotice(Date checkDate) {
		List<PensionLeave> vacationRecords = new ArrayList<PensionLeave>();
		PensionLeaveExample example = new PensionLeaveExample();
		example.or().andRealleavetimeIsNotNull().andRealbacktimeIsNull()
				.andExpectbacktimeLessThanOrEqualTo(checkDate)
				.andClearedEqualTo(2);
		vacationRecords = pensionLeaveMapper.selectByExample(example);
		for (PensionLeave record : vacationRecords) {
			String pensionOlderName = this.selectOlderName(record.getOlderId());
			String messageContent = "老人" + pensionOlderName + "未按时返院提醒！";
			String messagename = "【" + pensionOlderName + "】未按时返院提醒";
			// 消息类别
			String typeIdStr = JavaConfig
					.fetchProperty("EntryLateOlderRecordService.messagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);

			String url;

			try {
				url = messageMessage.selectMessageTypeUrl(messagetypeId);

				url = url + "?olderId=" + record.getOlderId() + "&recordId="
						+ record.getId();

				String deptId = systemConfig
						.selectProperty("DELAYBACK_DEPT_ID");
				String empId = systemConfig.selectProperty("DELAYBACK_EMP_ID");

				List<Long> dptIdList = new ArrayList<Long>();
				List<Long> empIdList = new ArrayList<Long>();

				if (deptId != null && !deptId.isEmpty()) {
					String[] dpt_id_arr = deptId.split(",");
					for (int i = 0; i < dpt_id_arr.length; i++) {
						dptIdList.add(Long.valueOf(dpt_id_arr[i]));
					}
				} else {
					dptIdList = null;
				}

				if (empId != null && !empId.isEmpty()) {
					String[] emp_id_arr = empId.split(",");
					for (int i = 0; i < emp_id_arr.length; i++) {
						empIdList.add(Long.valueOf(emp_id_arr[i]));
					}
				} else {
					empIdList = null;
				}

				messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
						messagename, messageContent, url, "pension_leave",
						record.getId());
			} catch (PmsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据主键查询老人请假记录
	 * 
	 * @param leaveId
	 * @return
	 */
	public PensionLeave selectLeaveRecord(Long leaveId) {
		PensionLeaveExample example = new PensionLeaveExample();
		example.or().andIdEqualTo(leaveId).andClearedEqualTo(2);
		return pensionLeaveMapper.selectByExample(example).get(0);
	}

	/**
	 * 根据主键查询老人姓名
	 * 
	 * @param olderId
	 * @return
	 */
	public String selectOlderName(Long olderId) {
		PensionOlderExample example = new PensionOlderExample();
		example.or().andIdEqualTo(olderId).andClearedEqualTo(2);
		return pensionOlderMapper.selectByExample(example).get(0).getName();
	}

}
