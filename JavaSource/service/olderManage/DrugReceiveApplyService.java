package service.olderManage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.medicalManage.PensionDicDrugreceiveMapper;
import persistence.medicalManage.PensionDrugreceiveDetailMapper;
import persistence.medicalManage.PensionDrugreceiveRecordMapper;
import persistence.nurseManage.PensionDicDeliveryMapper;
import persistence.nurseManage.PensionDispenseOlderMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import util.SystemConfig;
import domain.medicalManage.PensionDicDrugreceive;
import domain.medicalManage.PensionDrugreceiveDetail;
import domain.medicalManage.PensionDrugreceiveDetailExample;
import domain.nurseManage.PensionDicDelivery;
import domain.nurseManage.PensionDicDeliveryExample;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-10 下午1:59:44
 */
@Service
public class DrugReceiveApplyService {
	@Autowired
	private PensionDrugreceiveRecordMapper pensionDrugreceiveRecordMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionDrugreceiveDetailMapper pensionDrugreceiveDetailMapper;
	@Autowired
	private PensionDispenseOlderMapper PensionDispenseOlderMapper;
	@Autowired
	private PensionDicDrugreceiveMapper pensionDicDrugreceiveMapper;
	@Autowired
	private PensionDicDeliveryMapper pensionDicDeliveryMapper;

	/**
	 * 查询药物接收申请列表
	 * 
	 * @param olderId
	 * @param isAudit
	 * @param auditResult
	 * @return
	 */
	public List<DrugReceiveDomain> selectApplications(Long olderId,
			String isAudit, String auditResult, Long deliery) {
		List<DrugReceiveDomain> drugApplyList = pensionDrugreceiveRecordMapper
				.selectDrugRecords(olderId, isAudit, auditResult, null, deliery);
		return drugApplyList;
	}

	/**
	 * 新增药物接收申请并发送审核消息
	 * 
	 * @param addDrugApply
	 * @param drugDetailList
	 */
	public void insertAndSendDrugApply(DrugReceiveDomain addDrugApply,
			List<DrugReceiveDetailDomain> drugDetailList) {
		try {
			// 插入药物接收主记录
			pensionDrugreceiveRecordMapper.insertSelective(addDrugApply);
			// 插入药物接收明细
			for (DrugReceiveDetailDomain detail : drugDetailList) {
				detail.setRecordId(addDrugApply.getId());
				detail.setAmountTotal(detail.getTotalAmount());
				pensionDrugreceiveDetailMapper.insertSelective(detail);
			}
			sentMessage(addDrugApply);
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
	public void sentMessage(DrugReceiveDomain selectedRow) throws PmsException {
		String pensionOlderName = selectedRow.getOlderName();
		String messageContent = "老人" + pensionOlderName + "药物接收申请已提交！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("drugReceiveApplyService.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?olderId=" + selectedRow.getOlderId() + "&recordId="
				+ selectedRow.getId();

		String drug_apply_dpt_id = systemConfig
				.selectProperty("DRUG_RECEIVE_DEPT_ID");
		String drug_apply_emp_id = systemConfig
				.selectProperty("DRUG_RECEIVE_EMP_ID");

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> empIdList = new ArrayList<Long>();

		if (drug_apply_dpt_id != null && !drug_apply_dpt_id.isEmpty()) {
			String[] dpt_id_arr = drug_apply_dpt_id.split(",");
			for (int i = 0; i < dpt_id_arr.length; i++) {
				dptIdList.add(Long.valueOf(dpt_id_arr[i]));
			}
		} else {
			dptIdList = null;
		}

		if (drug_apply_emp_id != null && !drug_apply_emp_id.isEmpty()) {
			String[] emp_id_arr = drug_apply_emp_id.split(",");
			for (int i = 0; i < emp_id_arr.length; i++) {
				empIdList.add(Long.valueOf(emp_id_arr[i]));
			}
		} else {
			empIdList = null;
		}
		String messagename = "【" + pensionOlderName + "】药物接收申请审批";

		messageMessage.sendMessage(messagetypeId, dptIdList, empIdList,
				messagename, messageContent, url, "pension_drugreceive_record",
				selectedRow.getId());
	}

	/**
	 * 删除药物接收主记录及明细
	 * 
	 * @param selectedRow
	 */
	@Transactional
	public void delDrugApply(DrugReceiveDomain selectedRow) {
		try {
			pensionDrugreceiveRecordMapper
					.updateByPrimaryKeySelective(selectedRow);
			PensionDrugreceiveDetailExample example = new PensionDrugreceiveDetailExample();
			example.or().andRecordIdEqualTo(selectedRow.getId());
			List<PensionDrugreceiveDetail> detailList = pensionDrugreceiveDetailMapper
					.selectByExample(example);
			for (PensionDrugreceiveDetail detail : detailList) {
				detail.setCleared(1);
				pensionDrugreceiveDetailMapper.updateByPrimaryKey(detail);
			}
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
		List<Long> deptIdList = new ArrayList<Long>();
		String[] deptIds = systemConfig.selectProperty("AGENT_APPLY_DPT_ID")
				.split(",");
		for (int i = 0; i < deptIds.length; i++) {
			deptIdList.add(Long.parseLong(deptIds[i]));
		}
		return deptIdList;
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
	 * 保存药物申请主记录及明细
	 * 
	 * @param saveFlag
	 * @param addDrugApply
	 * @param drugDetailList
	 */
	@Transactional
	public void insertDrugApply(DrugReceiveDomain addDrugApply,
			List<DrugReceiveDetailDomain> drugDetailList) {
		try {
			// 插入药物接收主记录
			pensionDrugreceiveRecordMapper.insertSelective(addDrugApply);
			// 插入药物接收明细
			for (DrugReceiveDetailDomain detail : drugDetailList) {
				detail.setRecordId(addDrugApply.getId());
				detail.setAmountTotal(detail.getTotalAmount());
				pensionDrugreceiveDetailMapper.insertSelective(detail);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 更新药物接收申请主记录及明细
	 * 
	 * @param saveFlag
	 * @param editDrugApply
	 * @param drugDetailList
	 */
	@Transactional
	public void updateDrugApply(DrugReceiveDomain editDrugApply,
			List<DrugReceiveDetailDomain> drugDetailList) {
		try {
			// 更新药物接收主记录
			pensionDrugreceiveRecordMapper
					.updateByPrimaryKeySelective(editDrugApply);
			// 更新药物明细
			PensionDrugreceiveDetailExample detailExample = new PensionDrugreceiveDetailExample();
			detailExample.or().andRecordIdEqualTo(editDrugApply.getId());
			pensionDrugreceiveDetailMapper.deleteByExample(detailExample);

			for (DrugReceiveDetailDomain detail : drugDetailList) {
				// 插入药物明细
				detail.setRecordId(editDrugApply.getId());
				detail.setAmountTotal(detail.getTotalAmount());
				pensionDrugreceiveDetailMapper.insertSelective(detail);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 更新药物接收申请主记录及明细并发送审核消息
	 * 
	 * @param editDrugApply
	 * @param drugDetailList
	 */
	@Transactional
	public void updateAndSendDrugApply(DrugReceiveDomain editDrugApply,
			List<DrugReceiveDetailDomain> drugDetailList) {
		try {
			// 更新药物接收主记录
			pensionDrugreceiveRecordMapper
					.updateByPrimaryKeySelective(editDrugApply);
			// 更新药物明细
			PensionDrugreceiveDetailExample detailExample = new PensionDrugreceiveDetailExample();
			detailExample.or().andRecordIdEqualTo(editDrugApply.getId());
			pensionDrugreceiveDetailMapper.deleteByExample(detailExample);

			for (DrugReceiveDetailDomain detail : drugDetailList) {
				// 插入药物明细
				detail.setRecordId(editDrugApply.getId());
				detail.setAmountTotal(detail.getTotalAmount());
				pensionDrugreceiveDetailMapper.insertSelective(detail);
			}
			sentMessage(editDrugApply);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 查询摆药老人列表
	 * 
	 * @param olderId
	 * @return
	 */
	public List<DispenseDomain> selectDispenseOlders(Long olderId) {
		List<DispenseDomain> olders = PensionDispenseOlderMapper
				.selectDispenseOlders(olderId);
		return olders;
	}

	/**
	 * 插入老人摆药信息表
	 * 
	 * @param dispense
	 */
	@Transactional
	public void insertDispenseOlder(DispenseDomain dispense) {
		try {
			PensionDispenseOlderMapper.insertSelective(dispense);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
		}
	}

	/**
	 * 更新老人摆药信息
	 * 
	 * @param dispense
	 */
	@Transactional
	public void updateDispenseOlder(DispenseDomain dispense) {
		try {
			PensionDispenseOlderMapper.updateByPrimaryKeySelective(dispense);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
		}
	}

	/**
	 * 查询药物明细最大ID
	 * 
	 * @return
	 */
	public Long selectMaxDetailId() {
		PensionDrugreceiveDetailExample example = new PensionDrugreceiveDetailExample();
		example.or();
		example.setOrderByClause("id desc");
		List<PensionDrugreceiveDetail> detailList = pensionDrugreceiveDetailMapper
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
	 * 查询药物字典(根据老人ID和药物名称)
	 * 
	 * @param olderId
	 * @param drugId
	 * @return
	 */
	public List<DrugDicDomain> selectDrugRecords(Long olderId, String drugName) {
		List<DrugDicDomain> drugList = new ArrayList<DrugDicDomain>();
		drugList = pensionDicDrugreceiveMapper
				.selectDrugList(olderId, drugName);
		return drugList;
	}

	/**
	 * 查询药物字典，根据主键
	 * 
	 * @param Id
	 * @return
	 */
	public PensionDicDrugreceive selectDrugDic(Long Id) {
		PensionDicDrugreceive drugDic = pensionDicDrugreceiveMapper
				.selectByPrimaryKey(Id);
		return drugDic;
	}

	/**
	 * 插入药物字典表
	 * 
	 * @param domain
	 */
	@Transactional
	public void insertDrugDic(DrugDicDomain domain) {
		try {
			pensionDicDrugreceiveMapper.insertSelective(domain);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
		}
	}

	/**
	 * 更新药物字典
	 * 
	 * @param domain
	 */
	@Transactional
	public void updateDrugDic(DrugDicDomain domain) {
		try {
			pensionDicDrugreceiveMapper.updateByPrimaryKeySelective(domain);
			// 更新药物明细
			PensionDrugreceiveDetailExample example = new PensionDrugreceiveDetailExample();
			example.or().andDrugreceiveIdEqualTo(domain.getId());
			List<PensionDrugreceiveDetail> detailList = pensionDrugreceiveDetailMapper
					.selectByExample(example);
			for (PensionDrugreceiveDetail temp : detailList) {
				temp.setValidFlag(domain.getValidFlag());
				pensionDrugreceiveDetailMapper
						.updateByPrimaryKeySelective(temp);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
		}
	}

	public List<PensionDicDelivery> selectDelieryList() {
		List<PensionDicDelivery> dicList = new ArrayList<PensionDicDelivery>();
		PensionDicDeliveryExample example = new PensionDicDeliveryExample();
		example.or();
		example.setOrderByClause("id asc");
		dicList = pensionDicDeliveryMapper.selectByExample(example);
		return dicList;
	}

	/**
	 * 更新药物接收主记录
	 * 
	 * @param record
	 */
	public void updateDrugRecord(DrugReceiveDomain record) {
		pensionDrugreceiveRecordMapper.updateByPrimaryKeySelective(record);
	}
}
