package service.olderManage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.medicalManage.PensionDrugreceiveDetailMapper;
import persistence.medicalManage.PensionDrugreceiveRecordMapper;
import persistence.olderManage.PensionOlderMapper;
import service.system.MessageMessage;
import util.JavaConfig;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-14 上午9:03:44
 */
@Service
public class DrugReceiveEvaluateService {
	@Autowired
	private PensionDrugreceiveRecordMapper pensionDrugreceiveRecordMapper;
	@Autowired
	private PensionDrugreceiveDetailMapper pensionDrugreceiveDetailMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private MessageMessage messageMessage;

	/**
	 * 查询药物接收申请列表
	 * 
	 * @param olderId
	 * @param isAudit
	 * @param auditResult
	 * @return
	 */
	public List<DrugReceiveDomain> selectApplications(Long olderId,
			String isAudit, String auditResult, Long recordId) {
		List<DrugReceiveDomain> drugApplyList = pensionDrugreceiveRecordMapper
				.selectDrugRecords(olderId, isAudit, auditResult, recordId,
						null);
		return drugApplyList;
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
	 * 根据主记录ID查询明细列表
	 * 
	 * @param recordId
	 * @return
	 */
	public List<DrugReceiveDetailDomain> selectDrugDetails(Long recordId) {
		List<DrugReceiveDetailDomain> detailList = new ArrayList<DrugReceiveDetailDomain>();
		detailList = pensionDrugreceiveDetailMapper.selectDrugDetails(recordId);
		return detailList;
	}

	/**
	 * 更新药物接收申请
	 * 
	 * @param drugApply
	 */
	@Transactional
	public void updateDrugApply(DrugReceiveDomain drugApply, Long deptId) {
		try {
			pensionDrugreceiveRecordMapper
					.updateByPrimaryKeySelective(drugApply);
			// 消息类别
			String typeIdStr = JavaConfig
					.fetchProperty("drugReceiveApplyService.messagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);
			messageMessage.updateMessageProcessor(drugApply.getAuditorId(),
					messagetypeId, "pension_drugreceive_record",
					drugApply.getId(), deptId);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

}
