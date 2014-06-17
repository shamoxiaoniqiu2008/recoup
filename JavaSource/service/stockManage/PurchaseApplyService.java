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
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.employeeManage.PensionEmployeeMapper;
import persistence.stockManage.PensionPurchaseDetailMapper;
import persistence.stockManage.PensionPurchaseEvaluateMapper;
import persistence.stockManage.PensionPurchaseRecordMapper;
import persistence.stockManage.PensionStorageoutRecordMapper;
import service.system.MessageMessage;
import util.DateUtil;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.employeeManage.PensionEmployeeExample;
import domain.stockManage.PensionPurchaseDetail;
import domain.stockManage.PensionPurchaseDetailExample;
import domain.stockManage.PensionPurchaseEvaluate;
import domain.stockManage.PensionPurchaseRecord;

/**
 * @ClassName: PurchaseApplyService
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:48:01
 * @version V1.0
 */
@Service
public class PurchaseApplyService {
	@Autowired
	private PensionPurchaseRecordMapper pensionPurchaseRecordMapper;
	@Autowired
	private PensionPurchaseDetailMapper pensionPurchaseDetailMapper;
	@Autowired
	private PensionStorageoutRecordMapper pensionStorageoutRecordMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionPurchaseEvaluateMapper pensionPurchaseEvaluateMapper;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;

	/**
	 * 查询采购物资明细最大ID
	 * 
	 * @return
	 */
	public Long selectMaxDetailId() {
		PensionPurchaseDetailExample example = new PensionPurchaseDetailExample();
		example.or();
		example.setOrderByClause("id desc");
		List<PensionPurchaseDetail> detailList = pensionPurchaseDetailMapper
				.selectByExample(example);
		Long detailId;
		if (detailList.size() != 0) {
			detailId = detailList.get(0).getId();
		} else {
			detailId = 0L;
		}
		return detailId;
	}

	/**
	 * 查询采购记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<PensionPurchaseRecord> selectApplyRecord(Date startDate,
			Date endDate, String isAudit, String auditResult) {
		List<PensionPurchaseRecord> applyRecordList = new ArrayList<PensionPurchaseRecord>();
		applyRecordList = pensionPurchaseRecordMapper.selectApplyRecord(
				startDate, endDate, isAudit, auditResult);
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
	 * 生成采购单号
	 */
	public String selectPurchaseNo() {
		String purchaseDate = DateUtil.format(new Date(), "yyyyMMdd");
		long randomNo = (long) (Math.random() * 9000 + 1000);
		String purchaseNo = purchaseDate + Long.valueOf(randomNo);
		return purchaseNo;
	}

	/**
	 * 删除采购申请及明细
	 */
	@Transactional
	public void deleteApplyRecord(PensionPurchaseRecord record) {
		try {
			// 将采购主记录清除标记置为1
			pensionPurchaseRecordMapper.updateByPrimaryKeySelective(record);
			// 将明细清除标记置为1
			List<PensionPurchaseDetail> detailList = pensionPurchaseDetailMapper
					.selectApplyDetail(record.getId());
			for (PensionPurchaseDetail temp : detailList) {
				temp.setCleared(1);
				pensionPurchaseDetailMapper.updateByPrimaryKeySelective(temp);
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 保存采购申请主记录及明细
	 * 
	 * @param record
	 * @param detailList
	 */
	@Transactional
	public void insertApplyRecord(PensionPurchaseRecord record,
			List<PensionPurchaseDetail> detailList) {
		try {
			// 主记录
			pensionPurchaseRecordMapper.insertSelective(record);
			for (PensionPurchaseDetail detail : detailList) {
				detail.setRecordId(record.getId());
				detail.setCleared(2);
				detail.setExpireDate(new Date());
				detail.setPurchasePrice(0.0f);
				detail.setPurchaseMoney(0.0f);
				pensionPurchaseDetailMapper.insertSelective(detail);
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 保存并发送采购申请
	 * 
	 * @param record
	 * @param detailList
	 */

	public void insertAndSendApplyRecord(PensionPurchaseRecord record,
			List<PensionPurchaseDetail> detailList) {
		try {
			// 主记录
			pensionPurchaseRecordMapper.insertSelective(record);
			for (PensionPurchaseDetail detail : detailList) {
				detail.setRecordId(record.getId());
				detail.setCleared(2);
				detail.setExpireDate(new Date());
				detail.setPurchasePrice(0.0f);
				detail.setPurchaseMoney(0.0f);
				pensionPurchaseDetailMapper.insertSelective(detail);
			}
			sentMessage(record);
			// 向采购审核表中插入记录
			List<Long> dptIdList = selectDeptId();
			List<Long> empIdList = selectEmpId();
			if (empIdList == null || empIdList.size() == 0) {
				for (Long deptId : dptIdList) {
					PensionPurchaseEvaluate evalRecord = new PensionPurchaseEvaluate();
					evalRecord.setApplyId(record.getId());
					evalRecord.setDeptId(deptId);
					pensionPurchaseEvaluateMapper.insertSelective(evalRecord);
				}
			} else {
				for (Long empId : empIdList) {
					PensionEmployeeExample example = new PensionEmployeeExample();
					example.or().andIdEqualTo(empId);
					Long deptId = pensionEmployeeMapper
							.selectByExample(example).get(0).getDeptId();
					PensionPurchaseEvaluate evalRecord = new PensionPurchaseEvaluate();
					evalRecord.setApplyId(record.getId());
					evalRecord.setDeptId(deptId);
					evalRecord.setEvaluatorId(empId);
					pensionPurchaseEvaluateMapper.insertSelective(evalRecord);
				}
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 更新采购申请及明细
	 * 
	 * @param record
	 * @param detailList
	 */
	@Transactional
	public void updateApplyRecord(PensionPurchaseRecord record,
			List<PensionPurchaseDetail> detailList) {
		try {
			// 更新主记录
			pensionPurchaseRecordMapper.updateByPrimaryKeySelective(record);
			// 更新明细
			PensionPurchaseDetailExample example = new PensionPurchaseDetailExample();
			example.or().andRecordIdEqualTo(record.getId());
			pensionPurchaseDetailMapper.deleteByExample(example);

			for (PensionPurchaseDetail temp : detailList) {
				temp.setRecordId(record.getId());
				temp.setExpireDate(new Date());
				temp.setPurchasePrice(0.0f);
				temp.setPurchaseMoney(0.0f);
				pensionPurchaseDetailMapper.insertSelective(temp);
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
			System.out.print(ex.getMessage());
		}
	}

	/**
	 * 更新并提交采购申请及明细
	 * 
	 * @param record
	 * @param detailList
	 */
	@Transactional
	public void updateAndSendApplyRecord(PensionPurchaseRecord record,
			List<PensionPurchaseDetail> detailList) {
		try {
			// 更新主记录
			record.setSendFlag(1);
			pensionPurchaseRecordMapper.updateByPrimaryKeySelective(record);
			// 更新明细
			PensionPurchaseDetailExample example = new PensionPurchaseDetailExample();
			example.or().andRecordIdEqualTo(record.getId());
			pensionPurchaseDetailMapper.deleteByExample(example);

			for (PensionPurchaseDetail temp : detailList) {
				temp.setRecordId(record.getId());
				temp.setExpireDate(new Date());
				temp.setPurchasePrice(0.0f);
				temp.setPurchaseMoney(0.0f);
				pensionPurchaseDetailMapper.insertSelective(temp);
			}
			sentMessage(record);
			// 向采购审核表中插入记录
			List<Long> dptIdList = selectDeptId();
			List<Long> empIdList = selectEmpId();
			if (empIdList == null || empIdList.size() == 0) {
				for (Long deptId : dptIdList) {
					PensionPurchaseEvaluate evalRecord = new PensionPurchaseEvaluate();
					evalRecord.setApplyId(record.getId());
					evalRecord.setDeptId(deptId);
					pensionPurchaseEvaluateMapper.insertSelective(evalRecord);
				}
			} else {
				for (Long empId : empIdList) {
					PensionEmployeeExample example2 = new PensionEmployeeExample();
					example.or().andIdEqualTo(empId);
					Long deptId = pensionEmployeeMapper
							.selectByExample(example2).get(0).getDeptId();
					PensionPurchaseEvaluate evalRecord = new PensionPurchaseEvaluate();
					evalRecord.setApplyId(record.getId());
					evalRecord.setDeptId(deptId);
					evalRecord.setEvaluatorId(empId);
					pensionPurchaseEvaluateMapper.insertSelective(evalRecord);
				}
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
			System.out.print(ex.getMessage());
		}
	}

	/**
	 * 查询采购单生成物资总额
	 */
	public Float selectTotalMoney() {
		Date startDate = DateUtil.getFirstMonthDay(new Date());
		Date endDate = DateUtil.getLastMonthDay(new Date());
		Float totalMoney = pensionStorageoutRecordMapper.selectTotalMoney(
				startDate, endDate);
		return totalMoney;
	}

	/**
	 * 查询采购单生成物资明细
	 * 
	 * @return
	 */
	public List<PensionPurchaseDetail> selectStorageoutList() {
		Date startDate = DateUtil.getFirstMonthDay(new Date());
		Date endDate = DateUtil.getLastMonthDay(new Date());
		List<PensionPurchaseDetail> detailList = new ArrayList<PensionPurchaseDetail>();
		detailList = pensionPurchaseDetailMapper.selectStorageoutDetail(
				startDate, endDate);
		return detailList;
	}

	/**
	 * 查询审核部门列表
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectDeptId() throws PmsException {
		String purchase_evaluate_dpt_id = systemConfig
				.selectProperty("PURCHASE_EVALUATE_DEPT_ID");
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
	public List<Long> selectEmpId() throws PmsException {
		String purchase_evaluate_emp_id = systemConfig
				.selectProperty("PURCHASE_EVALUATE_EMP_ID");
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
	 * 发送消息并向审核表中插入记录
	 */
	public void sendAndInsertEvaluate(PensionPurchaseRecord selectedRow) {
		// 发送消息
		try {
			sentMessage(selectedRow);
			// 更新申请表状态
			selectedRow.setSendFlag(1);
			pensionPurchaseRecordMapper
					.updateByPrimaryKeySelective(selectedRow);
			// 向采购审核表中插入记录
			List<Long> dptIdList = selectDeptId();
			List<Long> empIdList = selectEmpId();
			if (empIdList == null || empIdList.size() == 0) {
				for (Long deptId : dptIdList) {
					PensionPurchaseEvaluate evalRecord = new PensionPurchaseEvaluate();
					evalRecord.setApplyId(selectedRow.getId());
					evalRecord.setDeptId(deptId);
					pensionPurchaseEvaluateMapper.insertSelective(evalRecord);
				}
			} else {
				for (Long empId : empIdList) {
					PensionEmployeeExample example = new PensionEmployeeExample();
					example.or().andIdEqualTo(empId);
					Long deptId = pensionEmployeeMapper
							.selectByExample(example).get(0).getDeptId();
					PensionPurchaseEvaluate evalRecord = new PensionPurchaseEvaluate();
					evalRecord.setApplyId(selectedRow.getId());
					evalRecord.setDeptId(deptId);
					evalRecord.setEvaluatorId(empId);
					pensionPurchaseEvaluateMapper.insertSelective(evalRecord);
				}
			}

		} catch (PmsException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param selectedRow
	 * @throws PmsException
	 */
	@Transactional
	public void sentMessage(PensionPurchaseRecord selectedRow)
			throws PmsException {
		String purchaseNo = selectedRow.getPurchaseNo();
		String messageContent = "采购申请单" + purchaseNo + "已提交！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("purchaseApplyService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?id=" + selectedRow.getId() + "&purchaseNo="
				+ selectedRow.getPurchaseNo();

		String purchase_evaluate_dpt_id = systemConfig
				.selectProperty("PURCHASE_EVALUATE_DEPT_ID");
		String purchase_evaluate_emp_id = systemConfig
				.selectProperty("PURCHASE_EVALUATE_EMP_ID");

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> empIdList = new ArrayList<Long>();

		if (purchase_evaluate_dpt_id != null
				&& !purchase_evaluate_dpt_id.isEmpty()) {
			String[] dpt_id_arr = purchase_evaluate_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}

		if (purchase_evaluate_emp_id != null
				&& !purchase_evaluate_emp_id.isEmpty()) {
			String[] emp_id_arr = purchase_evaluate_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				empIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			empIdList = null;
		}

		String messagename = "【" + purchaseNo + "】采购申请单审批";

		messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
				messagename, messageContent, url, "pension_purchase_record",
				selectedRow.getId());

		// 更新申请单状态
	}
}
