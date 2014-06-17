package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.nurseManage.PensionCareAppointment;
import domain.system.PensionQuality;

import persistence.nurseManage.PensionCareAppointmentMapper;
import persistence.nurseManage.PensionDaycareMapper;
import persistence.olderManage.PensionOlderMapper;
import persistence.system.PensionQualityMapper;
import service.system.MessageMessage;
import util.JavaConfig;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-19 下午2:05:44
 */
@Service
public class DayCareService {
	@Autowired
	private PensionDaycareMapper pensionDaycareMapper;
	@Autowired
	private PensionQualityMapper pensionQualityMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private PensionCareAppointmentMapper pensionCareAppointmentMapper;

	/**
	 * 按条件查询护理记录
	 * 
	 * @param olderId
	 * @return
	 */
	public List<DayCareDomain> selectDayCareList(Long olderId, Long nurserId,
			Long recordId, String nurseType, Date startDate, Date endDate,
			String nurseFlag) {
		List<DayCareDomain> daycareList = new ArrayList<DayCareDomain>();
		daycareList = pensionDaycareMapper.selectDaycareList(olderId, nurserId,
				recordId, nurseType, startDate, endDate, nurseFlag);
		return daycareList;
	}

	/**
	 * 确认一条护理记录
	 * 
	 * @param record
	 */
	@Transactional
	public void updateDaycare(DayCareDomain record, Long deptId) {
		try {
			pensionDaycareMapper.updateByPrimaryKeySelective(record);
			if (record.getNurseType() == 2) {
				PensionCareAppointment careApp = new PensionCareAppointment();
				careApp.setGenerateFlag(1);
				careApp.setId(record.getNurseId());
				pensionCareAppointmentMapper
						.updateByPrimaryKeySelective(careApp);
			}
			// 定时任务
			String typeIdStr = JavaConfig
					.fetchProperty("dayCarePlanService.messagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);
			messageMessage.updateMessageProcessor(record.getNurserId(),
					messagetypeId, "pension_daycare", record.getId(), deptId);
			// 临时护理
			String typeStr = JavaConfig
					.fetchProperty("careAppointmentService.nurseMessagetypeId");
			Long messagetype = Long.valueOf(typeStr);
			messageMessage.updateMessageProcessor(record.getNurserId(),
					messagetype, "pension_daycare", record.getId(), deptId);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 更新输液记录并新增一条质检记录
	 */
	public void savePensionQuality(DayCareDomain domain) {
		try {
			pensionDaycareMapper.updateByPrimaryKeySelective(domain);
			// 新增质检记录
			PensionQuality quality = new PensionQuality();
			String qualityName = JavaConfig
					.fetchProperty("DayCareController.DAYCARE_QUALITY_TYPE");
			quality.setQualityType("DAYCARE_QUALITY_TYPE");
			quality.setQualityName(qualityName);
			quality.setOlderId(domain.getOlderId());
			quality.setOlderName(domain.getOlderName());
			quality.setItemId(domain.getId());
			quality.setCleared(2);
			quality.setTableName("pension_daycare");
			quality.setEvaluation(domain.getEvaluation());
			pensionQualityMapper.insert(quality);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
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
}
