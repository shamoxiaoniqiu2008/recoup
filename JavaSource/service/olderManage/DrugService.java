package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.medicalManage.PensionDicDrugreceiveMapper;
import persistence.medicalManage.PensionDrugreceiveDetailMapper;
import persistence.medicalManage.PensionDrugreceiveRecordMapper;
import persistence.nurseManage.PensionDispenseOlderMapper;
import persistence.olderManage.PensionDosageMapper;
import persistence.olderManage.PensionDosagedetailMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import domain.medicalManage.PensionDicDrugreceive;
import domain.medicalManage.PensionDicDrugreceiveExample;
import domain.medicalManage.PensionDrugreceiveDetail;
import domain.medicalManage.PensionDrugreceiveDetailExample;
import domain.medicalManage.PensionDrugreceiveRecord;
import domain.medicalManage.PensionDrugreceiveRecordExample;
import domain.nurseManage.PensionDispenseOlder;
import domain.nurseManage.PensionDispenseOlderExample;
import domain.olderManage.PensionDosage;
import domain.olderManage.PensionDosagedetail;
import domain.olderManage.PensionDosagedetailExample;

@Service
public class DrugService {

	@Autowired
	private PensionDosageMapper pensionDosageMapper;
	@Autowired
	private PensionDosagedetailMapper pensionDosagedetailMapper;
	@Autowired
	private PensionDispenseOlderMapper pensionDispenseOlderMapper;
	@Autowired
	private PensionDicDrugreceiveMapper pensionDicDrugreceiveMapper;
	@Autowired
	private PensionDrugreceiveDetailMapper pensionDrugreceiveDetailMapper;
	@Autowired
	private PensionDrugreceiveRecordMapper pensionDrugreceiveRecordMapper;
	@Autowired
	private MessageMessage messageMessage;

	private final static Integer YES = 1;
	private final static Integer NO = 2;

	private final static Integer MORNING = 1;
	private final static Integer NOON = 2;
	private final static Integer NIGHT = 3;

	public List<PensionDosage> select(Date startDate, Date endDate,
			Integer sendFlag, Long senderId, Long chargerId) {

		return pensionDosageMapper.selectDosages(startDate, endDate, sendFlag,
				senderId, chargerId);
	}

	public List<PensionDosagedetail> selectDosageDetails(Long dosageId) {
		PensionDosagedetailExample example = new PensionDosagedetailExample();
		example.or().andDosageIdEqualTo(dosageId).andClearedEqualTo(NO);
		return pensionDosagedetailMapper.selectByExample(example);
	}

	/**
	 * 保存一条配药单信息和其对应的明细信息
	 * 
	 * @param dosage
	 * @param dosageDetails
	 * @return
	 * @throws PmsException
	 */
	@Transactional
	public Long insertDosage(PensionDosage dosage,
			List<PensionDosagedetail> dosageDetails) throws PmsException {
		// 保存主记录
		pensionDosageMapper.insertSelective(dosage);
		// 保存明细
		this.insertDosageDetails(dosage.getId(), dosageDetails);
		return dosage.getId();
	}

	/**
	 * 保存一条配药单主记录对应的明细列表
	 * 
	 * @param id
	 * @param dosageDetails
	 * @throws PmsException
	 */
	@Transactional
	private void insertDosageDetails(Long id,
			List<PensionDosagedetail> dosageDetails) throws PmsException {
		for (PensionDosagedetail dosageDetail : dosageDetails) {
			// 存配药单明细表
			dosageDetail.setDosageId(id);
			dosageDetail.setId(null);
			dosageDetail.setIsensure(2);
			pensionDosagedetailMapper.insertSelective(dosageDetail);
			// 减库存
			this.updateDrugReceiveDetail(dosageDetail.getDrugReceiveDetailId(),
					dosageDetail.getDrugName(), dosageDetail.getPeriod());
		}

	}

	public void insertDosageDetail(Long dosageId, Long drugReceiveDetailId) {

	}

	public void deleteDosageDetails(Long id) {
		PensionDosagedetailExample example = new PensionDosagedetailExample();
		example.or().andDosageIdEqualTo(id);
		PensionDosagedetail dosageDetail = new PensionDosagedetail();
		dosageDetail.setCleared(YES);
		pensionDosagedetailMapper.updateByExampleSelective(dosageDetail,
				example);
	}

	public void sendMessages(Long id, String name, Integer chargerId)
			throws PmsException {
		String messageContent = "员工 " + name + " 提交了一条配药单核对申请！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("DrugService.sendDrugPaper");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?dosageId=" + id;

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(Long.valueOf(chargerId));

		String messagename = "【" + name + "】配药单核对消息";

		messageMessage.sendMessage(messagetypeId, dptIdList, userIdList,
				messagename, messageContent, url, "pension_dosage", id);

		this.updateSendDosage(id);
	}

	public void updateSendDosage(Long id) {
		PensionDosage dosage = new PensionDosage();
		dosage.setId(id);
		dosage.setIssend(YES);
		pensionDosageMapper.updateByPrimaryKeySelective(dosage);

	}

	public List<PensionDosagedetail> selectEnsureDosageDetail(Date startDate,
			Date endDate, Long chargerId, String ensureFlag) {

		return pensionDosageMapper.selectEnsureDosages(startDate, endDate,
				chargerId, ensureFlag);
	}

	/**
	 * 判断一条配药单是否 已确认
	 * 
	 * @param id
	 * @return
	 */
	public boolean checkIsChecked(Long id) {
		boolean flag = true;
		List<PensionDosagedetail> dosagedetails = this
				.selectMyDosageDetails(id);
		for (PensionDosagedetail dosagedetail : dosagedetails) {
			if (YES.equals(dosagedetail.getIscheck())) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 核对选中的配药单明细
	 * 
	 * @param dosageDetails
	 * @param employeeId
	 * @param employeeName
	 * @throws PmsException
	 */
	@Transactional
	public void updateDosageDetailsCheck(PensionDosagedetail dosageDetail) {
		pensionDosagedetailMapper.updateByPrimaryKeySelective(dosageDetail);
		// 如果审核不通过，则撤销已减库存
		if (dosageDetail.getCheckresult().equals(NO.toString())) {
			this.undoDrugReceiveDetail(dosageDetail.getDrugReceiveDetailId());
		}
	}

	/**
	 * 减库存
	 * 
	 * @param drugReceiveDetailId
	 * @param drugName
	 * @param peroid
	 * @throws PmsException
	 */
	@Transactional
	public void updateDrugReceiveDetail(Long drugReceiveDetailId,
			String drugName, Integer peroid) throws PmsException {
		// 查询出对应的药品明细
		PensionDrugreceiveDetail drugReceiveDetail = pensionDrugreceiveDetailMapper
				.selectByPrimaryKey(drugReceiveDetailId);
		if (drugReceiveDetail == null || drugReceiveDetail.getId() == null) {
			throw new PmsException(drugName + " 没有对应药物接收明细，确认失败！");
		} else {
			Integer totalAmount = new Integer(0);
			Integer useFlag = YES;
			// 如果当前条药品接收明细的剩余库存 大于 单次用量，就从该条减库存
			if (drugReceiveDetail.getTotalAmount() >= drugReceiveDetail
					.getSingleDose()) {
				totalAmount = drugReceiveDetail.getTotalAmount()
						- drugReceiveDetail.getSingleDose();
				// 如果减库存之后的剩余量 小于 单次用量，将该条置为不可用
				if (totalAmount < drugReceiveDetail.getSingleDose()) {
					useFlag = NO;
				}
				// 根据主键 将剩余量和可用标识 更新回数据库
				PensionDrugreceiveDetail temp = new PensionDrugreceiveDetail();
				temp.setId(drugReceiveDetail.getId());
				temp.setTotalAmount(totalAmount);
				temp.setUseFlag(useFlag);
				pensionDrugreceiveDetailMapper
						.updateByPrimaryKeySelective(temp);
			} else {
				// 如果药品不足量，没有减库存，则抛出异常
				throw new PmsException(drugName + " 余量不足，确认失败！");
			}
		}

	}

	/**
	 * 确认选中的配药单明细
	 * 
	 * @param dosageDetails
	 * @param employeeId
	 * @param employeeName
	 */
	public void updateDosageDetailsEnsure(PensionDosagedetail[] dosageDetails,
			Long employeeId, String employeeName) {
		for (PensionDosagedetail dosageDetail : dosageDetails) {
			PensionDosagedetail temp = new PensionDosagedetail();
			temp.setIsensure(YES);
			temp.setEnsureId(employeeId);
			temp.setEnsureName(employeeName);
			temp.setId(dosageDetail.getId());
			pensionDosagedetailMapper.updateByPrimaryKeySelective(temp);
		}
	}

	public List<PensionDosage> selectByPrimaryKey(Long dosageId) {
		List<PensionDosage> dosages = new ArrayList<PensionDosage>();
		PensionDosage temp = pensionDosageMapper.selectByPrimaryKey(dosageId);
		dosages.add(temp);
		return dosages;
	}

	public List<PensionDosagedetail> selectMyDosageDetails(Long dosageId) {
		PensionDosagedetailExample example = new PensionDosagedetailExample();
		example.or().andDosageIdEqualTo(dosageId).andClearedEqualTo(NO);
		return pensionDosagedetailMapper.selectByExample(example);
	}

	/**
	 * 将消息设置为已读
	 * 
	 * @param dosageId
	 * @param employeeId
	 * @param deptId
	 */
	public void updateMessageProcessor(Long dosageId, Long employeeId,
			Long deptId) {
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("DrugService.sendDrugPaper");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId,
				"pension_dosage", dosageId, deptId);

	}

	/**
	 * 将消息设置为 已读
	 * 
	 * @param dosageId
	 * @param employeeId
	 * @param deptId
	 */
	public void checkDosageDetails(Long dosageId, Long employeeId, Long deptId) {
		List<PensionDosagedetail> DosageDetails = this
				.selectMyDosageDetails(dosageId);
		boolean flag = true;
		for (PensionDosagedetail dosageDetail : DosageDetails) {
			if (dosageDetail.getIscheck() == null) {
				flag = false;
			}
		}
		if (flag) {
			this.updateMessageProcessor(dosageId, employeeId, deptId);
		}
	}

	/**
	 * 删除一条配药单
	 * 
	 * @param id
	 */
	public void deleteDosage(Long id) {
		// 删除配药单主记录
		PensionDosage dosage = new PensionDosage();
		dosage.setId(id);
		dosage.setCleared(YES);
		pensionDosageMapper.updateByPrimaryKeySelective(dosage);
		// 撤销已减库存
		this.undoDrugReceiveDetails(id);
		// 删除配药单明细
		this.deleteDosageDetails(id);

	}

	/**
	 * 撤销已减库存
	 * 
	 * @param id
	 *            删除的配药单主记录
	 */
	@Transactional
	public void undoDrugReceiveDetails(Long id) {
		// 查询出该配药单主记录的所有明细
		PensionDosagedetailExample example = new PensionDosagedetailExample();
		example.or().andDosageIdEqualTo(id).andClearedEqualTo(NO);
		List<PensionDosagedetail> details = pensionDosagedetailMapper
				.selectByExample(example);
		// 根据每一条配药单明细，撤销已减库存
		for (PensionDosagedetail dosagedetail : details) {
			this.undoDrugReceiveDetail(dosagedetail.getDrugReceiveDetailId());
		}

	}

	/**
	 * 根据药品接收明细撤销已减库存
	 * 
	 * @param id
	 *            药品接受明细主键
	 */
	@Transactional
	public void undoDrugReceiveDetail(Long id) {
		PensionDrugreceiveDetail tempReceiveDetail = pensionDrugreceiveDetailMapper
				.selectByPrimaryKey(id);
		PensionDrugreceiveDetail drugReceiveDetail = new PensionDrugreceiveDetail();
		drugReceiveDetail.setId(id);
		// 撤销后的库存 = 现有库存 + 单次用量
		drugReceiveDetail.setTotalAmount(tempReceiveDetail.getTotalAmount()
				+ tempReceiveDetail.getSingleDose());
		// 设置为可用
		drugReceiveDetail.setUseFlag(YES);
		pensionDrugreceiveDetailMapper
				.updateByPrimaryKeySelective(drugReceiveDetail);

	}

	/**
	 * 根据配送类型，服药日期和服药时段生成配药单明细
	 * 
	 * @param addDosage
	 * @param eatTime
	 *            服药时间
	 * @param period
	 *            服药时段
	 * @return
	 * @throws PmsException
	 */
	public Map<String, List<Object>> createDetails(PensionDosage addDosage,
			Date eatTime, Integer period) throws PmsException {
		Map<String, List<Object>> drugMap = new HashMap<String, List<Object>>();
		// 未成功生成配药单药品
		List<Object> drugList = new ArrayList<Object>();

		Long nurseDeptId = addDosage.getNursedeptid();
		String nurseDeptName = addDosage.getNursedeptname();
		Long dosageId = addDosage.getId();
		// 查询该配药类型对应的老人列表
		PensionDispenseOlderExample example = new PensionDispenseOlderExample();
		example.or().andDeliveryIdEqualTo(addDosage.getDeliveryId())
				.andClearedEqualTo(NO).andValidFlagEqualTo(YES);
		List<PensionDispenseOlder> dispenseOlders = pensionDispenseOlderMapper
				.selectByExample(example);
		List<Object> details = new ArrayList<Object>();
		// 查询每个老人需要摆的药
		for (PensionDispenseOlder dispenseOlder : dispenseOlders) {
			PensionDicDrugreceiveExample example1 = new PensionDicDrugreceiveExample();
			if (MORNING.equals(period)) {
				example1.or().andOlderIdEqualTo(dispenseOlder.getOlderId())
						.andValidFlagEqualTo(YES).andClearedEqualTo(NO)
						.andMorningFlagEqualTo(YES);
			} else if (NOON.equals(period)) {
				example1.or().andOlderIdEqualTo(dispenseOlder.getOlderId())
						.andValidFlagEqualTo(YES).andClearedEqualTo(NO)
						.andNoonFlagEqualTo(YES);
			} else if (NIGHT.equals(period)) {
				example1.or().andOlderIdEqualTo(dispenseOlder.getOlderId())
						.andValidFlagEqualTo(YES).andClearedEqualTo(NO)
						.andNightFlagEqualTo(YES);
			}
			List<PensionDicDrugreceive> dicDrugReceives = pensionDicDrugreceiveMapper
					.selectByExample(example1);
			List<PensionDicDrugreceive> removeList = new ArrayList<PensionDicDrugreceive>();
			for (PensionDicDrugreceive dicDrugreceive : dicDrugReceives) {
				List<PensionDosagedetail> dosageDetailList = new ArrayList<PensionDosagedetail>();
				PensionDosagedetailExample dosageDetailExample = new PensionDosagedetailExample();
				dosageDetailExample
						.or()
						.andClearedEqualTo(2)
						.andPeriodEqualTo(period)
						.andEattimeEqualTo(eatTime)
						.andOlderIdEqualTo(dispenseOlder.getOlderId())
						.andDrugNameEqualTo(dicDrugreceive.getDrugreceiveName());
				dosageDetailList = pensionDosagedetailMapper
						.selectByExample(dosageDetailExample);
				if (dosageDetailList.size() > 0) {
					removeList.add(dicDrugreceive);
				}
			}
			dicDrugReceives.removeAll(removeList);
			// 查询每种药的接收明细
			for (PensionDicDrugreceive dicDrugreceive : dicDrugReceives) {
				List<DrugReceiveDetailDomain> drugReceiveDetails = pensionDrugreceiveDetailMapper
						.selecDrugDetailList(dicDrugreceive.getId(), eatTime, 1);
				List<DrugReceiveDetailDomain> drugUnusedDetails = pensionDrugreceiveDetailMapper
						.selecDrugDetailList(dicDrugreceive.getId(), eatTime, 2);
				if (drugReceiveDetails.size() > 0) {
					boolean numberFLag = false;
					for (PensionDrugreceiveDetail drugReceiveDetail : drugReceiveDetails) {
						if (drugReceiveDetail.getTotalAmount().floatValue() >= drugReceiveDetail
								.getSingleDose().floatValue()) {
							numberFLag = true;
							PensionDosagedetail detail = new PensionDosagedetail();
							// 明细对应的药品接收字典表
							detail.setDrugName(dicDrugreceive
									.getDrugreceiveName());
							detail.setSingleDose(drugReceiveDetail
									.getSingleDose());
							detail.setUnit(drugReceiveDetail.getUnit());
							detail.setDrugReceiveId(dicDrugreceive.getId());// 药品接受字典表主键
							detail.setDrugReceiveDetailId(drugReceiveDetail
									.getId());// 药品接收明细表主键
							detail.setDeptId(nurseDeptId);
							detail.setDeptName(nurseDeptName);
							detail.setDosageId(dosageId);
							detail.setEattime(eatTime);
							detail.setIseatfood(dicDrugreceive
									.getBeforeafterFlag());
							detail.setOlderId(dicDrugreceive.getOlderId());
							detail.setOlderName(dicDrugreceive.getOlderName());
							detail.setPeriod(period);
							details.add(detail);
							break;
						}
					}
					if (!numberFLag) {
						// 药量不足
						dicDrugreceive.setNote("药量不足");
						drugList.add(dicDrugreceive);
					}
				} else if (drugUnusedDetails.size() > 0) {
					dicDrugreceive.setNote("该药品不在用");
					drugList.add(dicDrugreceive);
				} else {
					// 无药物接收明细
					dicDrugreceive.setNote("未接收该药物");
					drugList.add(dicDrugreceive);
				}
			}
		}
		drugMap.put("drugFail", drugList);
		drugMap.put("drugSuccess", details);
		return drugMap;
	}

	/**
	 * 根据主键查找药品接受字典表信息
	 * 
	 * @param drugreceiveId
	 * @return
	 */
	@SuppressWarnings("unused")
	private PensionDicDrugreceive selectDicDrugreceive(Long drugreceiveId) {
		return pensionDicDrugreceiveMapper.selectByPrimaryKey(drugreceiveId);
	}

	/**
	 * 根据药品接受主记录编号和时段查询药品
	 * 
	 * @param id
	 *            药品接受主记录编号
	 * @param eatTime
	 * @param period
	 *            时段
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<PensionDrugreceiveDetail> selectDrugReceiveDetails(Long id,
			Date eatTime, Integer period) {
		PensionDrugreceiveDetailExample example = new PensionDrugreceiveDetailExample();
		if (MORNING.equals(period)) {
			example.or().andRecordIdEqualTo(id).andUseFlagEqualTo(YES) // 是否在用
					.andValidFlagEqualTo(YES) // 是否有效
					.andClearedEqualTo(NO) // 是否清除
					.andShelflifeTimeGreaterThanOrEqualTo(eatTime) // 是否保质期已过
					.andMorningFlagEqualTo(YES);
		} else if (NOON.equals(period)) {
			example.or().andRecordIdEqualTo(id).andUseFlagEqualTo(YES)
					.andValidFlagEqualTo(YES).andClearedEqualTo(NO)
					.andShelflifeTimeGreaterThanOrEqualTo(eatTime)
					.andNoonFlagEqualTo(YES);
		} else if (NIGHT.equals(period)) {
			example.or().andRecordIdEqualTo(id).andUseFlagEqualTo(YES)
					.andValidFlagEqualTo(YES).andClearedEqualTo(NO)
					.andShelflifeTimeGreaterThanOrEqualTo(eatTime)
					.andNightFlagEqualTo(YES);
		}
		return pensionDrugreceiveDetailMapper.selectByExample(example);
	}

	/**
	 * 查询药物接受主记录表中该老人审核通过的药品接受记录
	 * 
	 * @param olderId
	 * @return
	 */
	@SuppressWarnings("unused")
	private List<PensionDrugreceiveRecord> selectDrugReceiveRecords(Long olderId) {
		PensionDrugreceiveRecordExample example = new PensionDrugreceiveRecordExample();
		example.or().andOlderIdEqualTo(olderId).andAuditFlagEqualTo(YES)
				.andAuditResultEqualTo(YES).andClearedEqualTo(NO);
		return pensionDrugreceiveRecordMapper.selectByExample(example);
	}

}
