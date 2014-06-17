package service.financeManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.financeManage.PensionDepositLogMapper;
import persistence.financeManage.PensionDepositMapper;
import persistence.financeManage.PensionNormalpaymentMapper;
import persistence.financeManage.PensionNormalpaymentdetailMapper;
import persistence.financeManage.PensionTemppaymentMapper;
import persistence.financeManage.PensionTemppaymentdetailMapper;
import persistence.olderManage.PensionOlderMapper;
import util.PmsException;
import domain.financeManage.PensionDeposit;
import domain.financeManage.PensionDepositExample;
import domain.financeManage.PensionDepositLog;
import domain.financeManage.PensionDepositLogExample;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionNormalpaymentExample;
import domain.financeManage.PensionNormalpaymentdetail;
import domain.financeManage.PensionNormalpaymentdetailExample;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentExample;
import domain.financeManage.PensionTemppaymentdetail;
import domain.financeManage.PensionTemppaymentdetailExample;
import domain.olderManage.PensionOlder;

/**
 * 查看缴费记录
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class PaymentLogService {
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private PensionNormalpaymentMapper pensionNormalpaymentMapper;
	@Autowired
	private PensionNormalpaymentdetailMapper pensionNormalpaymentdetailMapper;
	@Autowired
	private PensionTemppaymentMapper pensionTemppaymentMapper;
	@Autowired 
	private PensionTemppaymentdetailMapper pensionTemppaymentdetailMapper;
	@Autowired
	private PensionDepositMapper pensionDepositMapper;
	@Autowired
	private PensionDepositLogMapper pensionDepositLogMapper;
	
	private final static Integer YES=1;
	private final static Integer NO=2;
	
	public List<PensionOlder> search(Long olderId, Integer status) {
		return pensionOlderMapper.selectPaymentOlders(olderId, status);
	}

	/**
	 * 根据老人编号查询该老人的日常缴费信息
	 * @param olderId
	 * @return
	 */
	public List<PensionNormalpayment> selectNormalPayments(Long olderId) {
		return pensionNormalpaymentMapper.selectNormalPaymentList(olderId);
	}

	/**
	 * 根据老人编号查询该老人的临时缴费信息
	 * @param olderId
	 * @return
	 */
	public List<PensionTemppayment> selectTempPayments(Long olderId) {
		return pensionTemppaymentMapper.selectTempPaymentRecords(olderId);
	}

	/**
	 * 根据老人编号查询老人的押金缴纳记录
	 * @param olderId
	 * @return
	 * @throws PmsException
	 */
	public PensionDeposit selectDeposit(Long olderId) throws PmsException {
		PensionDepositExample example=new PensionDepositExample();
		example.or().andOlderIdEqualTo(olderId);
		List<PensionDeposit> deposits=pensionDepositMapper.selectByExample(example);
		if(deposits.size()>0){
			return deposits.get(0);
		}else{
			throw new PmsException("该老人没有押金信息");
		}
	}

	/**
	 * 根据押金缴纳主记录，查询押金缴纳明细
	 * @param depositId
	 * @return
	 */
	public List<PensionDepositLog> selectDepositLogs(Long depositId) {
		PensionDepositLogExample example=new PensionDepositLogExample();
		example.or().andDepositIdEqualTo(depositId);
		example.setOrderByClause("ID DESC");
		return pensionDepositLogMapper.selectByExample(example);
	}

	/**
	 * 根据日常缴费主记录主键，查询日常缴费记录明细
	 * @param paymentId
	 * @return
	 */
	public List<PensionNormalpaymentdetail> selectNormalDetails(Long paymentId) {
		PensionNormalpaymentdetailExample example=new PensionNormalpaymentdetailExample();
		example.or().andPaymentIdEqualTo(paymentId).andClearedEqualTo(NO);
		example.setOrderByClause("ID DESC");
		return pensionNormalpaymentdetailMapper.selectByExample(example);
	}

	/**
	 * 根据临时缴费主记录主键，查询临时缴费记录明细
	 * @param paymentId
	 * @return
	 */
	public List<PensionTemppaymentdetail> selectTempDetails(Long paymentId) {
		PensionTemppaymentdetailExample example=new PensionTemppaymentdetailExample();
		example.or().andPaymentIdEqualTo(paymentId).andClearedEqualTo(NO);
		example.setOrderByClause("ID DESC");
		return pensionTemppaymentdetailMapper.selectByExample(example);
	}


}
