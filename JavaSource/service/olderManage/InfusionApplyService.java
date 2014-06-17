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
import persistence.medicalManage.PensionInfusionRecordMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.medicalManage.PensionInfusionApply;
import domain.medicalManage.PensionInfusionRecord;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class InfusionApplyService {
	@Autowired
	private PensionInfusionApplyMapper pensionInfusionApplyMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionInfusionRecordMapper pensionInfusionRecordMapper;

	/**
	 * 按条件查询输液申请记录
	 * 
	 * @param olderId
	 * @param verifyFLag
	 * @param verifyResult
	 * @return
	 */
	public List<InfusionApplyDomain> selectApplyList(Long olderId,
			String verifyFLag, String verifyResult,Date startDate,Date endDate) {
		List<InfusionApplyDomain> applyList = new ArrayList<InfusionApplyDomain>();
		applyList = pensionInfusionApplyMapper.selectInfusionApplyList(olderId,
				verifyFLag, verifyResult, null,startDate,endDate);
		return applyList;
	}

	/**
	 * 更新一条输液申请记录
	 * 
	 * @param apply
	 */
	@Transactional
	public void updateInfusionApply(InfusionApplyDomain apply) {
		try {
			pensionInfusionApplyMapper.updateByPrimaryKeySelective(apply);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	public void sentMessage(PensionInfusionApply selectedRow)
			throws PmsException {
		String pensionOlderName = selectedRow.getOlderName();
		String messageContent = "老人" + pensionOlderName + "输液申请已提交！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("infusionApplyService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?olderId=" + selectedRow.getOlderId() + "&applyId="
				+ selectedRow.getId();

		String agent_apply_dpt_id = systemConfig
				.selectProperty("INFUSION_APPLY_DPT_ID");
		String agent_apply_emp_id = systemConfig
				.selectProperty("INFUSION_APPLY_EMP_ID");

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> empIdList = new ArrayList<Long>();

		if (agent_apply_dpt_id != null && !agent_apply_dpt_id.isEmpty()) {
			String[] dpt_id_arr = agent_apply_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}

		if (agent_apply_emp_id != null && !agent_apply_emp_id.isEmpty()) {
			String[] emp_id_arr = agent_apply_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				empIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			empIdList = null;
		}

		String messagename = "【" + pensionOlderName + "】输液申请审批";

		messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
				messagename, messageContent, url, "pension_infusion_apply",
				selectedRow.getId());

		pensionInfusionApplyMapper.updateByPrimaryKeySelective(selectedRow);
	}

	/**
	 * 插入一条输液申请
	 * 
	 * @param apply
	 */
	@Transactional
	public void insertInfusionApply(InfusionApplyDomain apply, int saveFlag) {
		try {
			if (saveFlag == 1) {
				apply.setSendFlag(1);
				pensionInfusionApplyMapper.insertSelective(apply);
				sentMessage(apply);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"输液申请发送消息成功", ""));
			} else {
				apply.setSendFlag(2);
				pensionInfusionApplyMapper.insertSelective(apply);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 插入输液记录
	 */
	public void insertInfusionRecord(PensionInfusionRecord record) {
		try {
			pensionInfusionRecordMapper.insert(record);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 查询一条输液计划生成的输液记录列表
	 * 
	 * @param applyId
	 * @return
	 */
	public List<InfusionRecordDomain> selectInfusionRecords(Long applyId) {
		List<InfusionRecordDomain> infusionDomainList = new ArrayList<InfusionRecordDomain>();
		infusionDomainList = pensionInfusionRecordMapper
				.selectInfusionRecordsByApplyId(applyId);
		return infusionDomainList;
	}
}
