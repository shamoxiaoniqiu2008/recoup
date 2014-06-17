package service.olderManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centling.his.util.DateUtil;

import persistence.olderManage.PensionHospitalizegroupMapper;
import persistence.olderManage.PensionHospitalizeregisterMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;

@Service
public class ViewItemCountPaperService {
	@Autowired
	private PensionHospitalizegroupMapper pensionHospitalizegroupMapper;

	@Autowired
	private PensionHospitalizeregisterMapper pensionHospitalizeregisterMapper;
	@Autowired
	private MessageMessage messageMessage;

	/**
	 * 查看分组记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @param managerId
	 * @return
	 */
	public List<PensionHospitalizegroupExtend> selectGroupRecords(Long groupId,
			Date startDate, Date endDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("startDate", startDate);
		if (endDate != null) {
			map.put("endDate", DateUtil.getNextDay(endDate));
		} else {
			map.put("endDate", null);
		}
		map.put("sended", 1);
		return pensionHospitalizegroupMapper.selectGroupRecords(map);

	}

	/*
	 * 获取当前分组的就医人员
	 */
	public List<PensionHospitalizeregisterExtend> selectRegisterRecords(
			Long groupId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", null);
		map.put("endDate", null);
		map.put("olderId", null);
		map.put("groupId", groupId);
		map.put("grouped", null);

		return pensionHospitalizeregisterMapper
				.selectMedicalRegisterRecords(map);
	}

	/**
	 * 获取分组统计信息
	 * 
	 * @param groupId
	 * @return
	 */
	public String selectGroupCollection(Long groupId) {
		List<HospitalGroupCollection> hospitalGroupCollectionList = pensionHospitalizegroupMapper
				.selectGroupCollection(groupId);
		float allMoney = 0;
		String messageContent = "";
		for (HospitalGroupCollection hgc : hospitalGroupCollectionList) {
			messageContent += hgc.getTypeName() + ":" + hgc.getCounts() + "人；";
			allMoney += hgc.getAdvance();
		}
		messageContent += "共收预交金:" + allMoney;
		return messageContent;
	}

	/**
	 * 更新一条评估记录
	 */
	@Transactional
	public void updateMessage(Long groupId, Long employeeId, Long deptId) {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("entryVacationApplicationService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId,
				"pension_hospitalizegroup", groupId, deptId);
	}

	/**
	 * 返院登记
	 * 
	 * @param updatedRow
	 * @throws PmsException
	 */
	public void backedRegister(PensionHospitalizeregisterExtend updatedRow)
			throws PmsException {

		try {
			pensionHospitalizeregisterMapper
					.updateByPrimaryKeySelective(updatedRow);
		} catch (Exception e) {
			throw new PmsException("操作失败");
		}
	}

	public PensionHospitalizegroupMapper getPensionHospitalizegroupMapper() {
		return pensionHospitalizegroupMapper;
	}

	public void setPensionHospitalizegroupMapper(
			PensionHospitalizegroupMapper pensionHospitalizegroupMapper) {
		this.pensionHospitalizegroupMapper = pensionHospitalizegroupMapper;
	}
}
