package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.medicalManage.PensionInfusionApplyMapper;
import persistence.olderManage.PensionOlderMapper;
import service.system.MessageMessage;
import util.JavaConfig;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-5 上午10:39:44
 */
@Service
public class InfusionEvaluateService {
	@Autowired
	private PensionInfusionApplyMapper pensionInfusionApplyMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;

	/**
	 * 按条件查询输液申请记录
	 * 
	 * @param olderId
	 * @param verifyFLag
	 * @param verifyResult
	 * @return
	 */
	public List<InfusionApplyDomain> selectApplyList(Long olderId,
			String verifyFLag, String verifyResult, Long applyId,
			Date startDate, Date endDate) {
		List<InfusionApplyDomain> applyList = new ArrayList<InfusionApplyDomain>();
		applyList = pensionInfusionApplyMapper.selectInfusionEvaluateList(
				olderId, verifyFLag, verifyResult, applyId, startDate, endDate);
		return applyList;
	}

	/**
	 * 更新一条输液申请记录
	 * 
	 * @param apply
	 */
	@Transactional
	public void updateInfusionApply(InfusionApplyDomain apply, Long deptId) {
		try {
			pensionInfusionApplyMapper.updateByPrimaryKeySelective(apply);
			// 消息类别
			String typeIdStr = JavaConfig
					.fetchProperty("infusionApplyService.messagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);
			messageMessage.updateMessageProcessor(apply.getVerifierId(),
					messagetypeId, "pension_infusion_apply", apply.getId(),
					deptId);
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
