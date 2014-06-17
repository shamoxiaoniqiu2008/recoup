package service.financeManage;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.dictionary.PensionDictRefundtypeMapper;
import persistence.financeManage.PensionTemppaymentMapper;
import persistence.financeManage.PensionTemppaymentdetailMapper;
import persistence.logisticsManage.PensionMoveApplyMapper;
import persistence.logisticsManage.PensionRepairMapper;
import persistence.logisticsManage.PensionVehicleOrderMapper;
import persistence.medicalManage.PensionInfusionRecordMapper;
import persistence.receptionManage.PensionAgentApplyMapper;
import domain.dictionary.PensionDictRefundtype;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentdetail;
import domain.financeManage.PensionTemppaymentdetailExample;
import domain.logisticsManage.PensionMoveApply;
import domain.logisticsManage.PensionRepair;
import domain.logisticsManage.PensionVehicleOrder;
import domain.medicalManage.PensionInfusionRecord;
import domain.receptionManage.PensionAgentApply;

/**
 * 临时缴费
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class TempPayService {

	@Autowired
	private PensionTemppaymentMapper pensionTemppaymentMapper;
	@Autowired
	private PensionTemppaymentdetailMapper pensionTemppaymentdetailMapper;
	@Autowired
	private PensionDictRefundtypeMapper pensionDictRefundtypeMapper;
	@Autowired
	private PensionAgentApplyMapper pensionAgentApplyMapper;
	@Autowired
	private PensionInfusionRecordMapper pensionInfusionRecordMapper;
	@Autowired
	private PensionRepairMapper pensionRepairMapper;
	@Autowired
	private PensionMoveApplyMapper pensionMoveApplyMapper;
	@Autowired
	private PensionVehicleOrderMapper pensionVehicleOrderMapper;
	
	private final static Integer YES = 1;
	private final static Integer NO = 2;

	/**
	 * 临时缴费来源---零点餐
	 */
	private final static Integer TEMPPAYMENT_SCRAPPYOLDER = 2;

	/**
	 * 查询 临时缴费记录
	 * @param orderId
	 * @param orderName
	 * @param paidFlag
	 * @param searchStartDate
	 * @param searchEndDate
	 * @return
	 */
	public List<PensionTemppayment> search(Integer orderId, String orderName,
			Integer paidFlag, Date searchStartDate, Date searchEndDate) {
		return pensionTemppaymentMapper.selectTempPaymentInfo(orderId,
				orderName, paidFlag,searchStartDate,searchEndDate);
	}

	/**
	 * 插入临时缴费记录
	 * @param tempPayment
	 * @param temppaymentdetails
	 * @param tableName
	 * @param keyId
	 * @param curUser
	 * @return
	 */
	@Transactional
	public Long insertTempPayment(PensionTemppayment tempPayment,
			List<PensionTemppaymentdetail> temppaymentdetails,
			String tableName, Long keyId, PensionEmployee curUser) {
		//插入主记录
		pensionTemppaymentMapper.insertSelective(tempPayment);
		//返回主记录主键
		Long id = tempPayment.getId();
		//插入明细
		this.insertTempPaymentDetails(id, tempPayment.getStarttime(),
				tempPayment.getEndtime(), tempPayment.getOlderId(),
				temppaymentdetails);
		// 更新收费标识--置为已录费用
		if (tableName != null && keyId != null) {
			if (tableName.equals("pension_agent_apply")) {//代办服务
				PensionAgentApply apply = new PensionAgentApply();
				apply.setPayerId(curUser.getId());
				apply.setPayerName(curUser.getName());
				apply.setPayFlag(1);
				apply.setId(keyId);
				try {
					pensionAgentApplyMapper.updateByPrimaryKeySelective(apply);
				} catch (Exception ex) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, ex
									.getMessage(), ""));
				}
			} else if (tableName.equals("pension_infusion_record")) {//输液
				PensionInfusionRecord record = new PensionInfusionRecord();
				record.setPayerId(curUser.getId());
				record.setPayerName(curUser.getName());
				record.setPayFlag(1);
				record.setId(keyId);
				try {
					pensionInfusionRecordMapper
							.updateByPrimaryKeySelective(record);
				} catch (Exception ex) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, ex
									.getMessage(), ""));
				}
			} else if (tableName.equals("pension_repair")) {//维修
				PensionRepair record = new PensionRepair();
				record.setPayerId(curUser.getId());
				record.setHandleTime(new Date());
				record.setEnsureEmpId(curUser.getId());
				record.setEnsureEmpName(curUser.getName());
				record.setEnsureDate(new Date());
				record.setPayFlag(1);
				record.setId(keyId);
				try {
					pensionRepairMapper
							.updateByPrimaryKeySelective(record);
				} catch (Exception ex) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, ex
									.getMessage(), ""));
				}
			}
			 else if (tableName.equals("pension_move_apply")) {//搬家
					PensionMoveApply record = new PensionMoveApply();
					record.setPayerId(curUser.getId().intValue());
					record.setEnsureEmpId(curUser.getId());
					record.setEnsureEmpName(curUser.getName());
					record.setEnsureDate(new Date());
					record.setPayFlag(1);
					record.setId(keyId);
					try {
						pensionMoveApplyMapper
								.updateByPrimaryKeySelective(record);
					} catch (Exception ex) {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, ex
										.getMessage(), ""));
					}
				}
			 else if (tableName.equals("pension_vehicle_order")) {//用车
					PensionVehicleOrder record = new PensionVehicleOrder();
					record.setEnsureEmpId(curUser.getId());
					record.setEnsureEmpName(curUser.getName());
					record.setEnsureDate(new Date());
					record.setChargeFlag(YES);
					record.setId(keyId);
					try {
						pensionVehicleOrderMapper
								.updateByPrimaryKeySelective(record);
					} catch (Exception ex) {
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, ex
										.getMessage(), ""));
					}
				}
		}
		return id;
	}

	/**
	 * 插入临时缴费记录（主记录+明细）
	 * @param tempPayment
	 * @param temppaymentdetails
	 * @return
	 */
	@Transactional
	public Long insertTempPayment(PensionTemppayment tempPayment,
			List<PensionTemppaymentdetail> temppaymentdetails) {
		pensionTemppaymentMapper.insertSelective(tempPayment);
		Long id = tempPayment.getId();
		this.insertTempPaymentDetails(id, tempPayment.getStarttime(),
				tempPayment.getEndtime(), tempPayment.getOlderId(),
				temppaymentdetails);
		return id;
	}

	/**
	 * 插入临时缴费明细列表
	 * @param paymentId
	 * @param startTime
	 * @param endTime
	 * @param olderId
	 * @param temppaymentdetails
	 */
	public void insertTempPaymentDetails(Long paymentId, Date startTime,
			Date endTime, Long olderId,
			List<PensionTemppaymentdetail> temppaymentdetails) {
		for (PensionTemppaymentdetail temppaymentdetail : temppaymentdetails) {
			temppaymentdetail.setId(null);
			temppaymentdetail.setStarttime(startTime);
			temppaymentdetail.setEndtime(endTime);
			temppaymentdetail.setPaymentId(paymentId);
			temppaymentdetail.setCleared(NO);
			temppaymentdetail.setGeneratetime(new Date());
			temppaymentdetail.setIspay(NO);
			temppaymentdetail.setOlderId(olderId);
			pensionTemppaymentdetailMapper.insertSelective(temppaymentdetail);
		}
	}

	/**
	 * 更新临时缴费记录
	 * @param tempPayment
	 * @param temppaymentdetails
	 */
	@Transactional
	public void updateTempPayment(PensionTemppayment tempPayment,
			List<PensionTemppaymentdetail> temppaymentdetails) {
		//更新主记录
		pensionTemppaymentMapper.updateByPrimaryKeySelective(tempPayment);
		//删除原有明细列表
		this.deleteTempPaymentDetails(tempPayment.getId());
		//插入新的明细列表
		this.insertTempPaymentDetails(tempPayment.getId(),
				tempPayment.getStarttime(), tempPayment.getEndtime(),
				tempPayment.getOlderId(), temppaymentdetails);
	}

	/**
	 * 逻辑删除临时缴费明细
	 */
	public void deleteTempPaymentDetails(Long peymentId) {
		PensionTemppaymentdetailExample example = new PensionTemppaymentdetailExample();
		example.or().andPaymentIdEqualTo(peymentId);
		PensionTemppaymentdetail temp = new PensionTemppaymentdetail();
		temp.setCleared(YES);
		pensionTemppaymentdetailMapper.updateByExampleSelective(temp, example);
	}

	/**
	 * 根据主记录编号查询明细列表
	 * @param paymentId
	 * @return
	 */
	public List<PensionTemppaymentdetail> selectTempPaymentDetails(
			Long paymentId) {
		PensionTemppaymentdetailExample example = new PensionTemppaymentdetailExample();
		example.or().andClearedEqualTo(NO).andPaymentIdEqualTo(paymentId);
		example.setOrderByClause("GENERATETIME DESC");
		return pensionTemppaymentdetailMapper.selectByExample(example);
	}

	/**
	 * 删除临时缴费记录（主记录+明细）
	 * @param id  临时缴费主记录主键
	 */
	@Transactional
	public void deleteTempPaymentDetail(Long id) {
		this.deleteTempPaymentDetails(id);
		this.deleteTempPayment(id);
	}

	/**
	 * 逻辑删除临时缴费主记录
	 * @param id 临时缴费主记录主键
	 */
	public void deleteTempPayment(Long id) {
		PensionTemppayment temp = new PensionTemppayment();
		temp.setId(id);
		temp.setCleared(YES);
		pensionTemppaymentMapper.updateByPrimaryKeySelective(temp);

	}

	/**
	 * 查询老人信息
	 * @param olderId 老人编号
	 * @return
	 */
	public PensionTemppayment selectInfo(Long olderId) {
		List<PensionTemppayment> list = pensionTemppaymentMapper
				.selectInfo(olderId);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 插入临时缴费记录（主记录+明细）
	 * @param tempPayment
	 * @param temppaymentdetails
	 */
	@Transactional
	public void insertTempPaymentWithNoReturn(PensionTemppayment tempPayment,
			List<PensionTemppaymentdetail> temppaymentdetails) {
		pensionTemppaymentMapper.insertSelective(tempPayment);
		Long id = tempPayment.getId();
		this.insertTempPaymentDetails(id, tempPayment.getStarttime(),
				tempPayment.getEndtime(), tempPayment.getOlderId(),
				temppaymentdetails);
	}

	public Float calculateTotalFees(
			List<PensionTemppaymentdetail> paymentDetails) {
		Float totalFees = new Float(0);
		for (PensionTemppaymentdetail paymentDetail : paymentDetails) {
			totalFees += paymentDetail.getTotalfees();
		}
		return totalFees;
	}

	/**
	 * check选中的要删除的记录，如果此记录由零点餐生成，则不能删除
	 * @param arr
	 * @return
	 */
	public boolean checkDeleteDate(PensionTemppayment arr) {
		boolean flag = false;
		List<PensionTemppaymentdetail> tempPaymentDetails = this
				.selectTempPaymentDetails(arr.getId());
		for (PensionTemppaymentdetail tempPaymentDetail : tempPaymentDetails) {
			if (TEMPPAYMENT_SCRAPPYOLDER.equals(tempPaymentDetail.getSource())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public List<PensionDictRefundtype> selectRefundTypes() {
		return pensionDictRefundtypeMapper.selectByExample(null);
	}

	public PensionDictRefundtype selectRefundTypeName(Long selectRefundTypeId) {
		return pensionDictRefundtypeMapper
				.selectByPrimaryKey(selectRefundTypeId);
	}

}
