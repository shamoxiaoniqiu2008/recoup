package service.olderManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centling.his.util.DateUtil;

import domain.employeeManage.PensionEmployee;
import domain.olderManage.PensionLeave;
import domain.olderManage.PensionLeaveapprove;

import persistence.employeeManage.PensionEmployeeMapper;
import persistence.olderManage.PensionLeaveMapper;
import persistence.olderManage.PensionLeaveapproveMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;

@Service
public class EntryVacationApplicationService {

	@Autowired
	private PensionLeaveMapper pensionLeaveMapper;
	@Autowired
	private PensionLeaveapproveMapper pensionLeaveapproveMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;

	/**
	 * 查询假期申请记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @param olderId
	 * @param submitFlag
	 * @return
	 */
	public List<PensionLeaveExtend> selectVacationApplicationRecords(
			Date startDate, Date endDate, Long olderId, Integer submitFlag) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if (endDate != null) {
			map.put("endDate", DateUtil.getNextDay(endDate));
		} else {
			map.put("endDate", null);
		}
		map.put("olderId", olderId);
		map.put("submitFlag", submitFlag);
		return pensionLeaveMapper.selectVacationApplicationRecords(map);
	}

	/**
	 * 删除假期申请记录
	 * 
	 * @param selectedLeaveRow
	 */
	public void deleteVacationApplicationRecord(PensionLeaveExtend selectedRow) {

		selectedRow.setCleared(1);
		pensionLeaveMapper.updateByPrimaryKeySelective(selectedRow);
	}

	/**
	 * 录入假期申请记录
	 * 
	 * @param addedLeaveRow
	 */
	public void insertVacationApplicationRecord(PensionLeaveExtend addedLeaveRow) {
		addedLeaveRow.setCleared(2);
		addedLeaveRow.setFinalResult(1);
		pensionLeaveMapper.insertSelective(addedLeaveRow);
	}

	/**
	 * 录入并提交假期申请记录
	 * 
	 * @param addedLeaveRow
	 * @param curUser
	 */
	public void insertAndSubmitVacationApplicationRecord(
			PensionLeaveExtend addedLeaveRow, PensionEmployee curUser) {
		addedLeaveRow.setCleared(2);
		addedLeaveRow.setFinalResult(1);
		pensionLeaveMapper.insertSelective(addedLeaveRow);
		try {
			submitVacationApplicationRecord(addedLeaveRow, curUser);
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 修改假期申请记录
	 * 
	 * @param updatedLeaveRow
	 */
	public void updateVacationApplicationRecord(PensionLeave updatedLeaveRow) {
		pensionLeaveMapper.updateByPrimaryKeySelective(updatedLeaveRow);
	}

	/**
	 * 修改并提交假期申请记录
	 * 
	 * @param updatedLeaveRow
	 * @param curUser
	 */
	public void updateAndSubmitVacationApplicationRecord(
			PensionLeaveExtend updatedLeaveRow, PensionEmployee curUser) {
		pensionLeaveMapper.updateByPrimaryKeySelective(updatedLeaveRow);
		try {
			submitVacationApplicationRecord(updatedLeaveRow, curUser);
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public void submitVacationApplicationRecord(PensionLeaveExtend selectedRow,
			PensionEmployee curEmployee) throws PmsException {

		String messageContent = "老人" + selectedRow.getOlderName()
				+ " 提交了一条请假申请！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("entryVacationApplicationService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?leaveId=" + selectedRow.getId() + "&olderId="
				+ selectedRow.getOlderId();

		List<Long> dptIdList = selectDeptIdList();
		List<Long> userIdList = selectEmptIdList();

		String messagename = "【" + selectedRow.getOlderName() + "】请假申请";

		messageMessage.sendMessage(messagetypeId, dptIdList, userIdList,
				messagename, messageContent, url, "pension_leave",
				selectedRow.getId());

		selectedRow.setSended(1);// 将发送标志 表示为1已发送
		pensionLeaveMapper.updateByPrimaryKeySelective(selectedRow);
		if (userIdList != null) {
			for (Long emptId : userIdList) {
				PensionLeaveapprove pensionLeaveapprove = new PensionLeaveapprove();
				pensionLeaveapprove.setLeaveId(selectedRow.getId());
				pensionLeaveapprove.setCleared(2);
				Long deptId = pensionEmployeeMapper.selectByPrimaryKey(emptId)
						.getDeptId();
				pensionLeaveapprove.setDeptId(deptId);// 根据系统参数的不同
														// 发送的部门也不同
				pensionLeaveapprove.setApproverId(emptId);
				pensionLeaveapproveMapper.insertSelective(pensionLeaveapprove);
			}
		} else {
			for (Long deptId : dptIdList) {
				PensionLeaveapprove pensionLeaveapprove = new PensionLeaveapprove();
				pensionLeaveapprove.setLeaveId(selectedRow.getId());
				pensionLeaveapprove.setCleared(2);
				pensionLeaveapprove.setDeptId(deptId);// 根据系统参数的不同 发送的部门也不同
				pensionLeaveapproveMapper.insertSelective(pensionLeaveapprove);
			}
		}
		noticeAllDept(dptIdList, selectedRow.getOlderName(),
				selectedRow.getId());
	}

	/**
	 * 老人请假申请时向全院发送消息
	 * 
	 * @param deptIdList
	 * @param empIdList
	 * @throws PmsException
	 */
	public void noticeAllDept(List<Long> deptIdList, String olderName,
			Long leaveId) throws PmsException {
		String messageContent = "老人" + olderName + "提交了一条请假申请！";
		String messagename = "【" + olderName + "】提交了一条请假申请";
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
	 * 
	 * @Title: dealDate
	 * @Description: 日期处理
	 * @param @return
	 * @return Date
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 下午4:50:35
	 * @version V1.0
	 */
	public Date dealDate(Date leaveDate, Integer hour, Integer minute) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(leaveDate);
		cld.set(Calendar.HOUR_OF_DAY, hour.intValue());
		cld.set(Calendar.MINUTE, minute.intValue());
		Date date = cld.getTime();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm￼");
		String timePart = df.format(date);

		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		String datePart = df2.format(leaveDate);
		String dateTime = datePart + " " + timePart;
		SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm￼");
		Date newDateTime = null;
		try {
			newDateTime = df3.parse(dateTime);
		} catch (ParseException pe) {
			newDateTime = new Date();
		}
		return newDateTime;
	}

	/**
	 * 根据老人ID返回相应的大厦号，房间号等等
	 * 
	 * @param olderId
	 * @return
	 */
	public List<PensionLeaveExtend> fillField(Long olderId) {
		return pensionLeaveMapper.fillField(olderId);
	}

	public void setPensionLeaveMapper(PensionLeaveMapper pensionLeaveMapper) {
		this.pensionLeaveMapper = pensionLeaveMapper;
	}

	public PensionLeaveMapper getPensionLeaveMapper() {
		return pensionLeaveMapper;
	}

	public void setPensionLeaveapproveMapper(
			PensionLeaveapproveMapper pensionLeaveapproveMapper) {
		this.pensionLeaveapproveMapper = pensionLeaveapproveMapper;
	}

	public PensionLeaveapproveMapper getPensionLeaveapproveMapper() {
		return pensionLeaveapproveMapper;
	}

}
