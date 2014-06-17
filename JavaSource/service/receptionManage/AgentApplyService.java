package service.receptionManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.employeeManage.PensionEmployeeMapper;
import persistence.olderManage.PensionOlderMapper;
import persistence.receptionManage.PensionAgentApplyMapper;
import persistence.receptionManage.PensionAgentApproveMapper;
import persistence.system.PensionQualityMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.employeeManage.PensionEmployee;
import domain.receptionManage.PensionAgentApply;
import domain.receptionManage.PensionAgentApprove;
import domain.receptionManage.PensionAgentApproveExample;
import domain.system.PensionQuality;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class AgentApplyService {
	@Autowired
	private PensionAgentApplyMapper pensionAgentApplyMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionAgentApproveMapper pensionAgentApproveMapper;
	@Autowired
	private PensionQualityMapper pensionQualityMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;

	/**
	 * 查询代办服务申请列表
	 * 
	 * @param olderId
	 * @param isAudit
	 * @param auditResult
	 * @param isComplete
	 * @return
	 */
	public List<AgentApplyDomain> selectApplications(Long olderId,
			String isAudit, String auditResult, String isComplete,
			Long applyId, Date startDate, Date endDate) {
		List<AgentApplyDomain> agentApplyList = pensionAgentApplyMapper
				.selectApplications(olderId, isAudit, auditResult, isComplete,
						applyId, startDate, endDate);
		return agentApplyList;
	}

	public List<PensionAgentApply> selectEvaluations(Long olderId,
			String isAudit, String auditResult, Long applyId, Date startDate,
			Date endDate) {
		List<PensionAgentApply> agentApplyList = pensionAgentApplyMapper
				.selectEvaluations(olderId, isAudit, auditResult, applyId,
						startDate, endDate);
		return agentApplyList;
	}

	/**
	 * 插入一条代办服务申请记录及评估记录
	 * 
	 * @param agentDomain
	 * @throws PmsException
	 */
	@Transactional
	public void insertAgentApply(AgentApplyDomain agentDomain, int saveFlag)
			throws PmsException {
		try {
			if (saveFlag == 1) {
				agentDomain.setSendFlag(1);
				pensionAgentApplyMapper.insertSelective(agentDomain);
				sentMessage(agentDomain);
			} else {
				agentDomain.setSendFlag(2);
				pensionAgentApplyMapper.insertSelective(agentDomain);
			}
			// 向代办服务审核表中插入记录
			String agent_apply_dpt_id = systemConfig
					.selectProperty("AGENT_APPLY_DPT_ID");
			String agent_apply_emp_id = systemConfig
					.selectProperty("AGENT_APPLY_EMP_ID");

			if (agent_apply_emp_id != null && !agent_apply_emp_id.isEmpty()) {
				String[] emp_id_arr = agent_apply_emp_id.split(",");
				for (int i = 0; i < emp_id_arr.length; i++) {
					Long empId = Long.valueOf(emp_id_arr[i]);
					Long deptId = pensionEmployeeMapper.selectByPrimaryKey(
							empId).getDeptId();
					PensionAgentApprove approve = new PensionAgentApprove();
					approve.setApplyId(agentDomain.getId());
					approve.setDepartId(deptId);
					approve.setVerifyFlag(2);
					approve.setCleared(2);
					approve.setVerifierId(empId);
					insertAgentApprove(approve);
				}
			} else if (agent_apply_dpt_id != null
					&& !agent_apply_dpt_id.isEmpty()) {
				String[] dpt_id_arr = agent_apply_dpt_id.split(",");
				for (int i = 0; i < dpt_id_arr.length; i++) {
					Long deptId = Long.valueOf(dpt_id_arr[i]);
					PensionAgentApprove approve = new PensionAgentApprove();
					approve.setApplyId(agentDomain.getId());
					approve.setDepartId(deptId);
					approve.setVerifyFlag(2);
					approve.setCleared(2);
					insertAgentApprove(approve);
				}
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 代办服务审核发送消息
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	public void sentMessage(AgentApplyDomain selectedRow) throws PmsException {
		String pensionOlderName = selectedRow.getOlderName();
		String messageContent = "老人" + pensionOlderName + "代办服务申请已提交！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("AgentApplyService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?olderId=" + selectedRow.getOlderId() + "&applyId="
				+ selectedRow.getId();

		String agent_apply_dpt_id = systemConfig
				.selectProperty("AGENT_APPLY_DPT_ID");
		String agent_apply_emp_id = systemConfig
				.selectProperty("AGENT_APPLY_EMP_ID");

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
		String messagename = "【" + pensionOlderName + "】代办服务审批";

		messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
				messagename, messageContent, url, "pension_agent_apply",
				selectedRow.getId());
		pensionAgentApplyMapper.updateByPrimaryKeySelective(selectedRow);
	}

	/**
	 * 向代办服务人发送通知
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	public void agentEmpMessage(AgentApplyDomain apply) {
		try {
			String pensionOlderName = apply.getOlderName();
			String messageContent = "请及时处理老人" + pensionOlderName + "代办服务！";
			// 消息类别
			String typeIdStr = JavaConfig
					.fetchProperty("AgentApplyService.empMessagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);

			String url;

			url = messageMessage.selectMessageTypeUrl(messagetypeId);

			url = url + "?olderId=" + apply.getOlderId() + "&applyId="
					+ apply.getId();

			List<Long> empIdList = new ArrayList<Long>();
			empIdList.add(apply.getAgentId());

			String messagename = "【" + pensionOlderName + "】代办服务处理";

			messageMessage.sendMessage(messagetypeId, null, empIdList,
					messagename, messageContent, url, "pension_agent_apply",
					apply.getId());
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 更新一条代办申请
	 * 
	 * @param domain
	 */
	@Transactional
	public void updateAgentApply(AgentApplyDomain domain, int saveFlag) {
		try {
			if (saveFlag == 3) {
				domain.setSendFlag(1);
				pensionAgentApplyMapper.updateByPrimaryKeySelective(domain);
				sentMessage(domain);
			} else {
				domain.setSendFlag(2);
				pensionAgentApplyMapper.updateByPrimaryKeySelective(domain);
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 保存老人评价，新增一条质检记录
	 * 
	 * @param domain
	 */
	public void savePensionQuality(AgentApplyDomain domain) {
		try {
			pensionAgentApplyMapper.updateByPrimaryKeySelective(domain);
			// 新增质检记录
			PensionQuality quality = new PensionQuality();
			String qualityName = JavaConfig
					.fetchProperty("AgentApplyController.AGENT_QUALITY_TYPE");
			quality.setQualityType("AGENT_QUALITY_TYPE");
			quality.setQualityName(qualityName);
			quality.setOlderId(domain.getOlderId());
			quality.setOlderName(domain.getOlderName());
			quality.setItemId(domain.getId());
			quality.setCleared(2);
			quality.setTableName("pension_agent_apply");
			quality.setEvaluation(domain.getEvaluation());
			pensionQualityMapper.insert(quality);
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 根据系统参数分离部门Id
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectDeptIdList() throws PmsException {

		String agent_apply_dpt_id = systemConfig
				.selectProperty("AGENT_APPLY_DPT_ID");
		String agent_apply_emp_id = systemConfig
				.selectProperty("AGENT_APPLY_EMP_ID");

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> empIdList = new ArrayList<Long>();

		if (agent_apply_emp_id != null && !agent_apply_emp_id.isEmpty()) {
			String[] emp_id_arr = agent_apply_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				Long empId = Long.valueOf(emp_id_arr[i]);
				Long deptId = pensionEmployeeMapper.selectByPrimaryKey(empId)
						.getDeptId();
				empIdList.add(Long.valueOf(emp_id_arr[i]));
				dptIdList.add(deptId);
			}
		} else {
			empIdList = null;
			if (agent_apply_dpt_id != null && !agent_apply_dpt_id.isEmpty()) {
				String[] dpt_id_arr = agent_apply_dpt_id.split(",");
				for (int i = 0; i < dpt_id_arr.length; i++) {
					dptIdList.add(Long.valueOf(dpt_id_arr[i]));
				}
			} else {
				dptIdList = null;
			}
		}
		return dptIdList;
	}

	/**
	 * 向代办申请评估表中插入记录
	 */
	@Transactional
	public void insertAgentApprove(PensionAgentApprove approve) {
		try {
			pensionAgentApproveMapper.insert(approve);
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 查询各部门评估意见列表
	 * 
	 * @param applyId
	 * @return
	 */
	public List<PensionAgentApprove> selectDeptEvaluations(Long applyId) {
		List<PensionAgentApprove> evaluationList = new ArrayList<PensionAgentApprove>();
		evaluationList = pensionAgentApproveMapper
				.selectDeptEvaluations(applyId);
		return evaluationList;
	}

	/**
	 * 更新代办申请表及代办审核表
	 */
	@Transactional
	public void updateAgentEvaluate(PensionAgentApply apply,
			PensionAgentApprove approve) {
		try {
			// 更新审核表
			PensionAgentApproveExample approveExample = new PensionAgentApproveExample();
			approveExample.or().andApplyIdEqualTo(approve.getApplyId())
					.andDepartIdEqualTo(approve.getDepartId());
			pensionAgentApproveMapper.updateByExampleSelective(approve,
					approveExample);
			// 更新申请表
			pensionAgentApplyMapper.updateByPrimaryKeySelective(apply);
			// 消息类别
			String typeIdStr = JavaConfig
					.fetchProperty("AgentApplyService.messagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);
			messageMessage.updateMessageProcessor(approve.getVerifierId(),
					messagetypeId, "pension_agent_apply", apply.getId(),
					approve.getDepartId());
			evalMessage(apply);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 向代办服务申请部门发送通知
	 * 
	 * @param selectedRow
	 */
	public void evalMessage(PensionAgentApply apply) {
		try {
			String pensionOlderName = apply.getOlderName();
			String messageContent = "老人" + pensionOlderName + "代办服务审批完成！";
			// 消息类别
			String typeIdStr = JavaConfig
					.fetchProperty("AgentApplyService.evalMessagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);

			String url;

			url = messageMessage.selectMessageTypeUrl(messagetypeId);

			url = url + "?olderId=" + apply.getOlderId() + "&applyId="
					+ apply.getId();

			String agent_eval_dpt_id = systemConfig
					.selectProperty("AGENT_EVAL_DEPT_ID");
			String agent_eval_emp_id = systemConfig
					.selectProperty("AGENT_EVAL_EMP_ID");

			List<Long> dptIdList = new ArrayList<Long>();
			List<Long> empIdList = new ArrayList<Long>();

			if (agent_eval_dpt_id != null && !agent_eval_dpt_id.isEmpty()) {
				String[] dpt_id_arr = agent_eval_dpt_id.split(",");
				for (int i = 0; i < dpt_id_arr.length; i++) {
					dptIdList.add(Long.valueOf(dpt_id_arr[i]));
				}
			} else {
				dptIdList = null;
			}

			if (agent_eval_emp_id != null && !agent_eval_emp_id.isEmpty()) {
				String[] emp_id_arr = agent_eval_emp_id.split(",");
				for (int i = 0; i < emp_id_arr.length; i++) {
					empIdList.add(Long.valueOf(emp_id_arr[i]));
				}
			} else {
				empIdList = null;
			}
			String messagename = "【" + pensionOlderName + "】代办服务审批完成";

			messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
					messagename, messageContent, url, "pension_agent_apply",
					apply.getId());
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 更新评估结果
	 * 
	 * @param approve
	 */
	public void updateAgentEvaluate(PensionAgentApprove approve, Long applyId) {
		try {
			PensionAgentApproveExample approveExample = new PensionAgentApproveExample();
			approveExample.or().andApplyIdEqualTo(approve.getApplyId())
					.andDepartIdEqualTo(approve.getDepartId());
			pensionAgentApproveMapper.updateByExampleSelective(approve,
					approveExample);
			// 消息类别
			String typeIdStr = JavaConfig
					.fetchProperty("AgentApplyService.messagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);
			messageMessage.updateMessageProcessor(approve.getVerifierId(),
					messagetypeId, "pension_agent_apply", applyId,
					approve.getDepartId());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 登记代办人并向代办人发送消息
	 * 
	 * @param apply
	 */
	@Transactional
	public void agentRegist(AgentApplyDomain apply, PensionEmployee curUser) {
		try {
			pensionAgentApplyMapper.updateByPrimaryKeySelective(apply);
			// 发送消息
			agentEmpMessage(apply);
			// 处理消息
			String typeIdStr = JavaConfig
					.fetchProperty("AgentApplyService.evalMessagetypeId");
			Long messagetypeId = Long.valueOf(typeIdStr);
			messageMessage.updateMessageProcessor(curUser.getId(),
					messagetypeId, "pension_agent_apply", apply.getId(),
					curUser.getDeptId());
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
		return pensionOlderMapper.selectByPrimaryKey(olderId).getName();
	}

	/**
	 * 查询默认查询时长（根据系统参数）
	 */
	public int selectMonthNum() {
		int monthNum = 0;
		try {
			monthNum = Integer.valueOf(systemConfig
					.selectProperty("AGENT_TIME"));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return monthNum;
	}

	/**
	 * 查询评价是否显示标识（根据系统参数）
	 * 
	 * @return
	 */
	public boolean selectEvaluateFlag() {
		boolean flag = false;
		try {
			String flagStr = systemConfig.selectProperty("AGENT_EVALUATE_FLAG");
			if (flagStr.equals("1")) {
				flag = true;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
}
