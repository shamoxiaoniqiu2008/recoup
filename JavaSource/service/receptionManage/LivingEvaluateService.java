package service.receptionManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.olderManage.PensionOlderMapper;
import persistence.receptionManage.PensionApplyevaluateMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.olderManage.PensionOlder;
import domain.receptionManage.PensionApplyevaluate;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-9-1 下午04:52:44
 */
@Service
public class LivingEvaluateService {
	@Autowired
	private PensionOlderMapper pensitonOlderMapper;
	@Autowired
	private PensionApplyevaluateMapper pensionApplyevaluateMapper;

	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 根据条件查询老人入住申请记录列表
	 * 
	 * @return
	 */
	public List<PensionOlderDomain> selectEvaApplications(String personState,
			Long olderId, Date startDate, Date endDate, Long deptId) {
		List<PensionOlderDomain> applicationList = new ArrayList<PensionOlderDomain>();
		Long leadDeptId = selectLeadDeptId();
		Long leadEmptId = selectLeadEmpId();
		if (leadEmptId != null) {
			applicationList = pensitonOlderMapper.selectEvaApplications(
					personState, olderId, startDate, endDate, deptId,
					leadEmptId, null);
		} else {
			applicationList = pensitonOlderMapper.selectEvaApplications(
					personState, olderId, startDate, endDate, deptId, null,
					leadDeptId);
		}
		return applicationList;
	}

	/**
	 * 根据系统参数分离部门Id
	 * 
	 * @return
	 */
	public Long selectLeadDeptId() {
		List<Long> deptIdList = selectDeptIdList();
		if (deptIdList != null && deptIdList.size() != 0) {
			return deptIdList.get(deptIdList.size() - 1);
		}
		return null;
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
					.selectProperty("LIVING_APPLY_DPT_ID");
		} catch (PmsException e) {
			// TODO Auto-generated catch block
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
					.selectProperty("LIVING_APPLY_EMP_ID");
		} catch (PmsException e) {
			// TODO Auto-generated catch block
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

	/**
	 * 根据系统参数分离审批人员Id
	 * 
	 * @return
	 */
	public Long selectLeadEmpId() {
		List<Long> empIdList = selectEmptIdList();
		if (empIdList != null && empIdList.size() != 0) {
			return empIdList.get(empIdList.size() - 1);
		}
		return null;
	}

	/**
	 * 更新一条评估记录
	 * 
	 * @throws PmsException
	 */
	@Transactional
	public void updateEvaluate(PensionApplyevaluate evalution, Long olderId,
			Long userId, Long deptId, Long empId, String olderName)
			throws PmsException {
		pensionApplyevaluateMapper.updateByPrimaryKeySelective(evalution);
		Long leadDeptId = selectLeadDeptId();
		Long leadEmptId = selectLeadEmpId();
		if ((leadEmptId != null && empId.equals(leadEmptId))
				|| (leadDeptId != null && deptId.equals(leadDeptId))) {
			PensionOlder olderInfo = new PensionOlder();
			olderInfo.setId(olderId);
			olderInfo.setStatuses(2);
			pensitonOlderMapper.updateByPrimaryKeySelective(olderInfo);
			try {
				sentMessage(olderId, olderName);
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "发送消息成功",
								""));
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e
								.getMessage(), ""));
				e.printStackTrace();
			}
		}
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("LivingApplyController.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(userId, messagetypeId,
				"pension_older", olderId, deptId);
	}

	/**
	 * 更新一条入住申请评估记录
	 */
	@Transactional
	public void updateEvaluate(PensionApplyevaluate evalution) {
		pensionApplyevaluateMapper.updateByPrimaryKeySelective(evalution);
	}

	/**
	 * 查询其他部门评估意见
	 * 
	 * @param applyId
	 * @param deptId
	 * @return
	 */
	public List<PensionEvalDomain> selectDeptEvaList(Long applyId) {
		List<PensionEvalDomain> deptEvaDomainList = new ArrayList<PensionEvalDomain>();
		List<Long> deptList = selectDeptIdList();
		List<Long> emptList = selectEmptIdList();
		if (emptList != null && emptList.size() != 0) {
			for (Long empId : emptList) {
				PensionEvalDomain domain = new PensionEvalDomain();
				List<PensionEvalDomain> deptEvaList = pensionApplyevaluateMapper
						.selecDeptRecords(applyId, empId, null);
				if (deptEvaList.size() != 0) {
					domain = deptEvaList.get(0);
					deptEvaDomainList.add(domain);
				}
			}
		} else {
			for (Long deptId : deptList) {
				PensionEvalDomain domain = new PensionEvalDomain();
				List<PensionEvalDomain> deptEvaList = new ArrayList<PensionEvalDomain>();
				deptEvaList = pensionApplyevaluateMapper.selecDeptRecords(
						applyId, null, deptId);
				if (deptEvaList.size() != 0) {
					domain = deptEvaList.get(0);
					deptEvaDomainList.add(domain);
				}
			}
		}
		return deptEvaDomainList;
	}

	/**
	 * 发送消息
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	public void sentMessage(Long olderId, String olderName) throws PmsException {
		String messageContent = "老人" + olderName + "入住申请已审批！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("LivingEvaluateController.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?olderId=" + olderId;

		String living_evaluate_dpt_id = systemConfig
				.selectProperty("DESK_DEPT_ID");
		String living_evaluate_emp_id = systemConfig
				.selectProperty("DESK_EMP_ID");

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> empIdList = new ArrayList<Long>();

		if (living_evaluate_dpt_id != null && !living_evaluate_dpt_id.isEmpty()) {
			String[] dpt_id_arr = living_evaluate_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}

		if (living_evaluate_emp_id != null && !living_evaluate_emp_id.isEmpty()) {
			String[] emp_id_arr = living_evaluate_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				empIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			empIdList = null;
		}

		String messagename = "【" + olderName + "】入住审批已完成";

		messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
				messagename, messageContent, url, "pension_older", olderId);
	}

}
