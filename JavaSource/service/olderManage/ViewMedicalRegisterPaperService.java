package service.olderManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centling.his.util.DateUtil;

import domain.employeeManage.PensionEmployee;

import persistence.olderManage.PensionHospitalizeregisterMapper;
import service.system.MessageMessage;
import util.JavaConfig;

@Service
public class ViewMedicalRegisterPaperService {

	@Autowired
	private PensionHospitalizeregisterMapper pensionHospitalizeregisterMapper;
	@Autowired
	private MessageMessage messageMessage;
	
	public List<PensionHospitalizeregisterExtend> selectOlderMedicalRegisterRecords(
			Date startDate, Date endDate, Long olderId, Long groupId,
			Long registerId, PensionEmployee curEmployee) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if(endDate != null){
			map.put("endDate", DateUtil.getNextDay(endDate));
		}else{
			map.put("endDate", null);
		}	
		map.put("groupId", groupId);
		map.put("olderId", olderId);
		map.put("registerId", registerId);
		map.put("sended", 1);
		return pensionHospitalizeregisterMapper.selectMedicalRegisterRecords(map); 
	}
	/**
	 * 将消息置为已处理
	 */
	@Transactional
	public void updateMessage(Long registerId,Long employeeId, Long deptId) {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("sendOlderMedicalRegisterPaperService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId,
				"pension_hospitalizeregister", registerId, deptId);
	}
	public void setPensionHospitalizeregisterMapper(
			PensionHospitalizeregisterMapper pensionHospitalizeregisterMapper) {
		this.pensionHospitalizeregisterMapper = pensionHospitalizeregisterMapper;
	}

	public PensionHospitalizeregisterMapper getPensionHospitalizeregisterMapper() {
		return pensionHospitalizeregisterMapper;
	}

}
