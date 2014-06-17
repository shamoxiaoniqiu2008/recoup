package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.nurseManage.PensionCareAppointmentMapper;
import persistence.nurseManage.PensionDaycareMapper;
import persistence.olderManage.PensionOlderMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.employeeManage.PensionEmployee;
import domain.nurseManage.PensionDaycare;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-20 上午9:03:44
 */
@Service
public class CareAppointmentService {
	@Autowired
	private PensionCareAppointmentMapper pensionCareAppointmentMapper;
	@Autowired
	private PensionDaycareMapper pensionDaycareMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;

	/**
	 * 按条件查询护理计划
	 * 
	 * @param olderId
	 * @return
	 */
	public List<CareAppointmentDomain> selectAppRecords(Long olderId,
			Date startDate, Date endDate, Long careAppId) {
		List<CareAppointmentDomain> careAppList = new ArrayList<CareAppointmentDomain>();
		careAppList = pensionCareAppointmentMapper.selectCareAppList(olderId,
				startDate, endDate, careAppId);
		return careAppList;
	}

	/**
	 * 更新一条护理预约
	 * 
	 * @param care
	 */
	@Transactional
	public void updateCareApp(CareAppointmentDomain care) {
		try {
			pensionCareAppointmentMapper.updateByPrimaryKeySelective(care);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 更新并提交一条护理预约
	 * 
	 * @param care
	 */
	@Transactional
	public void updateAndSendCareApp(CareAppointmentDomain care) {
		try {
			pensionCareAppointmentMapper.updateByPrimaryKeySelective(care);
			sentMessage(care);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 插入一条护理预约
	 * 
	 * @param care
	 */
	@Transactional
	public void insertCareApp(CareAppointmentDomain care) {
		try {
			pensionCareAppointmentMapper.insertSelective(care);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 插入并提交一条护理预约
	 * 
	 * @param care
	 */
	@Transactional
	public void insertAndSendCareApp(CareAppointmentDomain care) {
		try {
			pensionCareAppointmentMapper.insertSelective(care);
			sentMessage(care);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 插入护理记录
	 */
	@Transactional
	public void insertDayCare(PensionDaycare care, PensionEmployee curUser) {
		try {
			pensionDaycareMapper.insertSelective(care);
			// 消息类别
			String typeIdStr = JavaConfig
					.fetchProperty("careAppointmentService.messagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);
			messageMessage.updateMessageProcessor(curUser.getId(),
					messagetypeId, "pension_care_appointment", care.getId(),
					curUser.getDeptId());
			// 给分配护理员发送消息
			sendNurseMessage(care);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 给分配护理员发送消息
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	@Transactional
	public void sendNurseMessage(PensionDaycare care) throws PmsException {
		String pensionOlderName = care.getOlderName();
		Long olderId = care.getOlderId();
		String messageContent = "老人" + pensionOlderName + "预约护理处理通知已提交！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("careAppointmentService.nurseMessagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?olderId=" + olderId + "&recordId=" + care.getId()
				+ "&typeId=2";

		List<Long> empIdList = new ArrayList<Long>();
		empIdList.add(care.getNurserId());

		String messagename = "【" + pensionOlderName + "】预约护理通知";
		messageMessage.sendMessage(messagetypeId, null, empIdList, messagename,
				messageContent, url, "pension_daycare", care.getId());
	}

	/**
	 * 发送消息
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	@Transactional
	public void sentMessage(CareAppointmentDomain selectedRow)
			throws PmsException {
		String pensionOlderName = selectedRow.getOlderName();
		Long olderId = selectedRow.getOlderId();
		String messageContent = "老人" + pensionOlderName + "预约护理通知已提交！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("careAppointmentService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?oldId=" + olderId + "&appId=" + selectedRow.getId();

		String care_app_dpt_id = systemConfig
				.selectProperty("CARE_APPOINTMENT_DEPT_ID");
		String care_app_emp_id = systemConfig
				.selectProperty("CARE_APPOINTMENT_EMP_ID");

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> empIdList = new ArrayList<Long>();

		if (care_app_dpt_id != null && !care_app_dpt_id.isEmpty()) {
			String[] dpt_id_arr = care_app_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}
		if (care_app_emp_id != null && !care_app_emp_id.isEmpty()) {
			String[] emp_id_arr = care_app_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				empIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			empIdList = null;
		}

		String messagename = "【" + pensionOlderName + "】预约护理通知";
		messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
				messagename, messageContent, url, "pension_care_appointment",
				selectedRow.getId());

		// 更新护理预约表发送标识
		selectedRow.setSendFlag(1);
		pensionCareAppointmentMapper.updateByPrimaryKeySelective(selectedRow);
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

	/**
	 * 修改护理员
	 * 
	 * @param care
	 */
	public void updateDaycare(PensionDaycare care, PensionEmployee curUser) {
		try {
			pensionDaycareMapper.updateByPrimaryKeySelective(care);
			// 给分配护理员发送消息
			sendNurseMessage(care);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 查询护理部id
	 * 
	 * @return
	 */
	public Long selectNurseDeptId() {
		Long deptId = null;
		try {
			deptId = Long.valueOf(systemConfig
					.selectProperty("NURSE_DEPT_ID_REPORT"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deptId;
	}
}
