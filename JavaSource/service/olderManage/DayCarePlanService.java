package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.employeeManage.PensionEmployeeMapper;
import persistence.nurseManage.PensionDaycareMapper;
import persistence.nurseManage.PensionDaycarePlanMapper;
import persistence.olderManage.PensionLivingrecordMapper;
import service.system.MessageMessage;
import util.DateUtil;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.nurseManage.PensionDaycare;
import domain.olderManage.PensionLivingrecordExample;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-18 上午9:03:44
 */
@Service
public class DayCarePlanService {
	@Autowired
	private PensionDaycarePlanMapper pensionDaycarePlanMapper;
	@Autowired
	private PensionDaycareMapper pensionDaycareMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionLivingrecordMapper pensionLivingrecordMapper;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;

	/**
	 * 按条件查询护理计划
	 * 
	 * @param olderId
	 * @return
	 */
	public List<CarePlanDomain> selectPlanList(Long olderId) {
		List<CarePlanDomain> planList = new ArrayList<CarePlanDomain>();
		planList = pensionDaycarePlanMapper.selectPlanList(olderId);
		return planList;
	}

	/**
	 * 更新一条护理计划
	 * 
	 * @param plan
	 */
	@Transactional
	public void updateCarePlan(CarePlanDomain plan) {
		try {
			pensionDaycarePlanMapper.updateByPrimaryKeySelective(plan);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 插入一条护理计划
	 * 
	 * @param plan
	 */
	@Transactional
	public void insertCarePlan(CarePlanDomain plan) {
		try {
			pensionDaycarePlanMapper.insertSelective(plan);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 保存并生成一条护理计划
	 * 
	 * @param plan
	 */
	@Transactional
	public void insertAndGenerateCarePlan(CarePlanDomain plan) {
		try {
			pensionDaycarePlanMapper.insertSelective(plan);
			insertDayCare(plan);
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
	public void insertDayCare(CarePlanDomain record) {
		try {

			Date startDate = record.getStartTime();
			String[] plan = record.getExecutePlan().replace(" ", "").split(",");
			List<String> planList = new ArrayList<String>();
			for (int i = 0; i < plan.length; i++) {
				planList.add(plan[i]);
			}
			for (String temp : planList) {

				PensionDaycare dayCare = new PensionDaycare();
				dayCare.setOlderId(record.getOlderId());
				dayCare.setOlderName(record.getOlderName());
				dayCare.setNurseId(record.getId());
				dayCare.setCleared(2);
				dayCare.setExecuteFlag(2);
				dayCare.setNurseType(1);
				dayCare.setNurseTime(util.DateUtil.addDate(startDate,
						Integer.valueOf(temp) - 1));
				if (record.getNurserId() == null) {
					PensionLivingrecordExample example = new PensionLivingrecordExample();
					example.or().andOlderIdEqualTo(record.getOlderId())
							.andClearedEqualTo(2);
					Long nurseId = pensionLivingrecordMapper
							.selectByExample(example).get(0).getNurseId()
							.longValue();
					String nurseName = pensionEmployeeMapper
							.selectByPrimaryKey(nurseId).getName();
					dayCare.setNurserId(nurseId);
					dayCare.setNurserName(nurseName);
				} else {
					dayCare.setNurserId(record.getNurserId());
					dayCare.setNurserName(record.getNurseName());
				}
				pensionDaycareMapper.insert(dayCare);
			}
			record.setGenerateFlag(1);
			pensionDaycarePlanMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 根据护理计划ID查询护理记录
	 * 
	 * @param olderId
	 * @return
	 */
	public List<DayCareDomain> selectDaycareList(Long planId) {
		List<DayCareDomain> careDomainList = new ArrayList<DayCareDomain>();
		careDomainList = pensionDaycareMapper.selectDayCareList(planId);
		return careDomainList;
	}

	/**
	 * 定时任务触发方法
	 * 
	 * @throws PmsException
	 */
	public void dayCareCheck(Date checkDate) throws PmsException {
		List<PensionDaycare> daycareList = new ArrayList<PensionDaycare>();
		String dateString = DateUtil.formatDate(checkDate);
		Date noticeDate = DateUtil.parseDate(dateString);
		daycareList = pensionDaycareMapper.selectNoticeDaycare(noticeDate);
		for (PensionDaycare record : daycareList) {
			String pensionOlderName = record.getOlderName();
			String messageContent = "";
			String messagename = "";
			if (record.getNurseType() == 1) {
				messageContent = "老人" + pensionOlderName + "日常护理提醒！";
				messagename = "【" + pensionOlderName + "】日常护理提醒";
			} else {
				messageContent = "老人" + pensionOlderName + "预约护理提醒！";
				messagename = "【" + pensionOlderName + "】预约护理提醒";
			}
			// 消息类别
			String typeIdStr = JavaConfig
					.fetchProperty("dayCarePlanService.messagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);

			String url;

			url = messageMessage.selectMessageTypeUrl(messagetypeId);
			url = url + "?olderId=" + record.getOlderId() + "&recordId="
					+ record.getId();

			List<Long> dptIdList = new ArrayList<Long>();
			List<Long> empIdList = new ArrayList<Long>();

			Long deptId = Long.valueOf(systemConfig
					.selectProperty("NURSE_DEPT_ID_REPORT"));
			dptIdList.add(deptId);

			Long empId = record.getNurserId();
			empIdList.add(empId);

			messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
					messagename, messageContent, url, "pension_daycare",
					record.getId());
		}
	}
}
