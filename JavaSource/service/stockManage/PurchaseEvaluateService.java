/**  
 * @Title: PurchaseApplyService.java 
 * @Package service.stockManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:48:01 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package service.stockManage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.stockManage.PensionPurchaseDetailMapper;
import persistence.stockManage.PensionPurchaseEvaluateMapper;
import persistence.stockManage.PensionPurchaseRecordMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.stockManage.PensionPurchaseDetail;
import domain.stockManage.PensionPurchaseDetailExample;
import domain.stockManage.PensionPurchaseEvaluate;
import domain.stockManage.PensionPurchaseEvaluateExample;
import domain.stockManage.PensionPurchaseRecord;
import domain.stockManage.PensionPurchaseRecordExample;

/**
 * @ClassName: PurchaseApplyService
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:48:01
 * @version V1.0
 */
@Service
public class PurchaseEvaluateService {
	@Autowired
	private PensionPurchaseRecordMapper pensionPurchaseRecordMapper;
	@Autowired
	private PensionPurchaseDetailMapper pensionPurchaseDetailMapper;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionPurchaseEvaluateMapper pensionPurchaseEvaluateMapper;
	@Autowired
	private MessageMessage messageMessage;

	/**
	 * 查询采购记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<PurchaseRecordDomain> selectApplyRecord(String purchaseNo,
			Long applyId, Long deptId) {
		List<PurchaseRecordDomain> applyRecordList = new ArrayList<PurchaseRecordDomain>();
		applyRecordList = pensionPurchaseRecordMapper.selectEvaluateRecord(
				purchaseNo, applyId, deptId);
		return applyRecordList;
	}

	/**
	 * 查询采购明细
	 * 
	 * @param recordId
	 * @return
	 */
	public List<PensionPurchaseDetail> selectApplyDetail(Long recordId) {
		List<PensionPurchaseDetail> detailList = new ArrayList<PensionPurchaseDetail>();
		detailList = pensionPurchaseDetailMapper.selectApplyDetail(recordId);
		return detailList;
	}

	/**
	 * 更新采购申请
	 * 
	 * @param record
	 */
	@Transactional
	public void updateApplyRecord(PensionPurchaseRecord record) {
		try {
			// 更新主记录
			pensionPurchaseRecordMapper.updateByPrimaryKeySelective(record);
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
			System.out.print(ex.getMessage());
		}
	}

	/**
	 * 查询审核部门列表
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectDeptId() {
		String purchase_evaluate_dpt_id = "";
		try {
			purchase_evaluate_dpt_id = systemConfig
					.selectProperty("PURCHASE_EVALUATE_DEPT_ID");
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Long> dptIdList = new ArrayList<Long>();
		if (purchase_evaluate_dpt_id != null
				&& !purchase_evaluate_dpt_id.isEmpty()) {
			String[] dpt_id_arr = purchase_evaluate_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}
		return dptIdList;
	}

	/**
	 * 查询审核人员列表
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectEmpId() {
		String purchase_evaluate_emp_id = "";
		try {
			purchase_evaluate_emp_id = systemConfig
					.selectProperty("PURCHASE_EVALUATE_EMP_ID");
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Long> empList = new ArrayList<Long>();
		if (purchase_evaluate_emp_id != null
				&& !purchase_evaluate_emp_id.isEmpty()) {
			String[] dpt_id_arr = purchase_evaluate_emp_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				empList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			empList = null;
		}
		return empList;
	}

	/**
	 * 更新审核结果
	 */
	public Integer updateEvalRecord(PensionPurchaseEvaluate record) {
		PensionPurchaseEvaluateExample example = new PensionPurchaseEvaluateExample();
		example.or().andApplyIdEqualTo(record.getApplyId())
				.andDeptIdEqualTo(record.getDeptId());
		pensionPurchaseEvaluateMapper.updateByExampleSelective(record, example);
		PensionPurchaseRecord apply = new PensionPurchaseRecord();
		List<Long> dptIdList = selectDeptId();
		List<Long> empIdList = selectEmpId();
		if (empIdList == null || empIdList.size() == 0) {
			if (dptIdList.get(dptIdList.size() - 1).equals(record.getDeptId())) {
				apply.setApproveFlag(1);
				if (record.getEvaluateResult() == 1) {
					apply.setApproveResult(1);
				} else if (record.getEvaluateResult() == 2) {
					apply.setApproveResult(2);
				}
				apply.setId(record.getApplyId());
				pensionPurchaseRecordMapper.updateByPrimaryKeySelective(apply);
			}
		} else {
			if (empIdList.get(empIdList.size() - 1).equals(
					record.getEvaluatorId())) {
				apply.setApproveFlag(1);
				if (record.getEvaluateResult() == 1) {
					apply.setApproveResult(1);
				} else if (record.getEvaluateResult() == 2) {
					apply.setApproveResult(2);
				}
				apply.setId(record.getApplyId());
				pensionPurchaseRecordMapper.updateByPrimaryKeySelective(apply);
			}
		}
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("purchaseApplyService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(record.getEvaluatorId(),
				messagetypeId, "pension_purchase_record", record.getApplyId(),
				record.getDeptId());
		return apply.getApproveResult();
	}

	/**
	 * 查询各部门审核意见
	 * 
	 * @param applyId
	 * @return
	 */
	public List<PurchaseEvalDomain> selectDeptEvalList(Long applyId) {
		List<PurchaseEvalDomain> evalList = new ArrayList<PurchaseEvalDomain>();
		evalList = pensionPurchaseEvaluateMapper.selectDeptEvalList(applyId);
		return evalList;
	}
}
