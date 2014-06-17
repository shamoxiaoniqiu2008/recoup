
package service.financeManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.financeManage.PensionNormalpaymentMapper;
import persistence.financeManage.PensionPaymentrecordMapper;
import persistence.financeManage.PensionTemppaymentMapper;
import persistence.logisticsManage.PensionThingdamageMapper;
import persistence.olderManage.PensionDepartapproveMapper;
import persistence.olderManage.PensionDepartnotifyMapper;
import persistence.olderManage.PensionDepartrecordMapper;
import persistence.olderManage.PensionDepartregisterMapper;
import persistence.olderManage.PensionOlderMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionNormalpaymentExample;
import domain.financeManage.PensionPaymentrecord;
import domain.financeManage.PensionPaymentrecordExample;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentExample;
import domain.logisticsManage.PensionThingdamage;
import domain.logisticsManage.PensionThingdamageExample;
import domain.olderManage.PensionDepartapprove;
import domain.olderManage.PensionDepartnotify;
import domain.olderManage.PensionDepartnotifyExample;
import domain.olderManage.PensionDepartrecord;
import domain.olderManage.PensionDepartrecordExample;
import domain.olderManage.PensionDepartregister;
import domain.olderManage.PensionOlder;

/**
 * 离院结算
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class SettleAccountsService {
	
	@Autowired
	private PensionDepartregisterMapper pensionDepartregisterMapper;
	@Autowired
	private PensionDepartrecordMapper pensionDepartrecordMapper;
	@Autowired
	private PensionDepartnotifyMapper pensionDepartnotifyMapper;
	@Autowired
	private PensionDepartapproveMapper pensionDepartapproveMapper;
	@Autowired
	private PensionNormalpaymentMapper pensionNormalpaymentMapper;
	@Autowired
	private PensionTemppaymentMapper pensionTemppaymentMapper;
	@Autowired
	private PensionThingdamageMapper pensionThingdamageMapper;
	@Autowired
	private PensionPaymentrecordMapper pensionPaymentrecordMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private MessageMessage messageMessage;
	
	private final static Integer YES=1;
	private final static Integer NO=2;
	
	//pension_paymentrecord
	private final static Integer NORMAL_PAY=1;//正常缴费
	private final static Integer REDO_PAY=2;//撤销记录
	private final static Integer OFFSET_PAY=3;//抵消记录
	
	//pension_older
	private final static Integer CHECK_OUT=5;//老人离院
	private final static Integer COLSED=6;//老人已结算
	
	//pension_departregister
	private final static Integer TO_DEPART=5;//待离院
	private final static Integer DEPART=6;//已结算
	

	
	public List<PensionDepartregister> search(Long olderId) {
		return pensionDepartregisterMapper.selectDepartInfo(olderId);
	}




	public Map<String, List> selectPensionNormalPaymentDetails(
			Long olderId) {
		Float totalFees=new Float(0);
		Float left=new Float(0);
		//查询出老人未结算未清除的日常缴费列表
		PensionNormalpaymentExample example=new PensionNormalpaymentExample();
		example.or().andOlderIdEqualTo(olderId)
				.andIsclosedEqualTo(NO)
				.andClearedEqualTo(NO);
		List<PensionNormalpayment> pensionNormalpayments=pensionNormalpaymentMapper.selectByExample(example);
		Map<String, List> normalPaymentMap=new HashMap<String, List>();
		List<PensionNormalpayment> paidNormalPayments=new ArrayList<PensionNormalpayment>();
		List<PensionNormalpayment> unPaidNormalPayments=new ArrayList<PensionNormalpayment>();
		//将已缴费和未缴费的日常缴费记录分别保存
		for(PensionNormalpayment pensionNormalpayment: pensionNormalpayments){
			if(YES.equals(pensionNormalpayment.getIspay())){
				totalFees+=pensionNormalpayment.getTotalfees();
				paidNormalPayments.add(pensionNormalpayment);
			}else if(NO.equals(pensionNormalpayment.getIspay())){
				left+=pensionNormalpayment.getTotalfees();
				unPaidNormalPayments.add(pensionNormalpayment);
			}
		}
		List<Float> fees=new ArrayList<Float>();
		fees.add(totalFees);
		fees.add(left);
		normalPaymentMap.put("PAIDNORMALPAYMENTS", paidNormalPayments);
		normalPaymentMap.put("UNPAIDNORMALPAYMENTS", unPaidNormalPayments);
		normalPaymentMap.put("FEES", fees);
		return normalPaymentMap;
	}




	public Map<String, List> selectPansionTempPaymentDetails(
			Long olderId) {
		Float totalFees=new Float(0);
		Float left=new Float(0);
		PensionTemppaymentExample example =new PensionTemppaymentExample();
		example.or().andClearedEqualTo(NO)
				.andOlderIdEqualTo(olderId)
				.andIsclosedEqualTo(NO);
		List<PensionTemppayment> pensionTemppayments=pensionTemppaymentMapper.selectByExample(example);
		Map<String, List> tempPaymentMap=new HashMap<String, List>();
		List<PensionTemppayment> paidTempPayments=new ArrayList<PensionTemppayment>();
		List<PensionTemppayment> unpaidTempPayments=new ArrayList<PensionTemppayment>();
		for(PensionTemppayment pensionTemppayment: pensionTemppayments){
			if(YES.equals(pensionTemppayment.getIspay())){
				totalFees+=pensionTemppayment.getTotalfees();
				paidTempPayments.add(pensionTemppayment);
			}else if(NO.equals(pensionTemppayment.getIspay())){
				left+=pensionTemppayment.getTotalfees();
				unpaidTempPayments.add(pensionTemppayment);
			}
		}
		List<Float> fees=new ArrayList<Float>();
		fees.add(totalFees);
		fees.add(left);
		tempPaymentMap.put("PAIDTEMPPAYMENTS", paidTempPayments);
		tempPaymentMap.put("UNPAIDTEMPPAYMENTS", unpaidTempPayments);
		tempPaymentMap.put("FEES", fees);
		return tempPaymentMap;
	}




	public List<PensionThingdamage> selectPensionThingDamage(Long olderId) {
		PensionThingdamageExample example=new PensionThingdamageExample();
		example.or().andOlderIdEqualTo(olderId)
				.andIspayEqualTo(NO)
				.andClearedEqualTo(NO);
		return pensionThingdamageMapper.selectByExample(example);
	}



	/**
	 * 点击确认结算
	 * @param pensionDepartregister
	 * @param pensionNormalpayments
	 * @param pensionTemppayments
	 * @param employee
	 * @return
	 * @throws PmsException
	 */
	@Transactional
	public PensionDepartregister settleAccounts(PensionDepartregister pensionDepartregister, List<PensionNormalpayment> pensionNormalpayments,List<PensionTemppayment> pensionTemppayments, PensionEmployee employee) throws PmsException {
		Date settleDate=new Date();
		Float accountMoney=this.calculateAccountMoney(pensionNormalpayments,pensionTemppayments);
		Long pensionPaymentRecordId=this.insertPaymentRecord(pensionDepartregister.getOlderId(),accountMoney,employee.getName(),settleDate,employee.getId());//插入结算记录 pension_paymentrecord
		this.settleNormalPayments(pensionNormalpayments,pensionPaymentRecordId,settleDate,employee);//结算日常缴费记录
		this.settleTempPayments(pensionTemppayments,pensionPaymentRecordId,settleDate,employee);//结算临时缴费记录
		Integer notifyFlag=this.checkGenerateNotify(pensionDepartregister.getId());
		this.updatePensionDepartrecord(pensionDepartregister.getOlderId(),accountMoney,notifyFlag);//更新 pension_departrecord 
		this.updateOlderStatus(pensionDepartregister.getOlderId(),COLSED);//将老人pension_older 状态改为 已结算
	//	this.updateDepartRegister(pensionDepartregister.getId(),DEPART);//更新pension_departregister老人状态为 离院
		pensionDepartregister.setMoney(accountMoney);
		pensionDepartregister.setIsagree(DEPART);
		pensionDepartregister.setIspay(YES);
		pensionDepartregister.setIsgeneratenotify(notifyFlag);
		return pensionDepartregister;
	}







	




	public void updateOlderStatus(Long olderId, Integer status) {
		PensionOlder pensionOlder=new PensionOlder();
		pensionOlder.setId(olderId);
		pensionOlder.setStatuses(status);
		pensionOlderMapper.updateByPrimaryKeySelective(pensionOlder);
	}




	public Integer checkGenerateNotify(Long registerId) {
		PensionDepartnotifyExample example=new PensionDepartnotifyExample();
		example.or().andDepartIdEqualTo(registerId);
		List<PensionDepartnotify> list=pensionDepartnotifyMapper.selectByExample(example);
		if(list.size()>0){
			return YES;
		}else{
			return NO;
		}
	}




	public void updatePensionDepartrecord(Long olderId, Float accountMoney, Integer notifyFlag) throws PmsException {
		PensionDepartrecordExample example=new PensionDepartrecordExample();
		example.or().andOlderIdEqualTo(olderId.intValue());
		example.setOrderByClause("ID DESC");
		List<PensionDepartrecord> pensionDepartrecords=pensionDepartrecordMapper.selectByExample(example);
		PensionDepartrecord pensionDepartrecord=new PensionDepartrecord();
		if(pensionDepartrecords.size()>0){
			pensionDepartrecord=pensionDepartrecords.get(0);
		}else{
			throw new PmsException("您当前结算的用户不存在！");
		}
		Long departRecordId=pensionDepartrecord.getId();
		PensionDepartrecord temp=new PensionDepartrecord();
		temp.setId(departRecordId);
		temp.setMoney(accountMoney);
		temp.setCleared(NO);
		temp.setIsgeneratenotify(notifyFlag);
		temp.setIspay(YES);
		pensionDepartrecordMapper.updateByPrimaryKeySelective(temp);
		
	}




	public Long insertPaymentRecord(Long olderId, Float accountMoney, String employeeName,Date settleDate,Long employeeId) {
		PensionPaymentrecord pensionPaymentRecord=new PensionPaymentrecord();
		pensionPaymentRecord.setOlderId(olderId);
		pensionPaymentRecord.setAccountmoney(accountMoney);
		pensionPaymentRecord.setCleared2(NO);
		pensionPaymentRecord.setIscancel(NORMAL_PAY);
		pensionPaymentRecord.setPayeeName(employeeName);
		pensionPaymentRecord.setPayeeId(employeeId);
		pensionPaymentRecord.setPaytime(settleDate);
		pensionPaymentrecordMapper.insertSelective(pensionPaymentRecord);
		return pensionPaymentRecord.getId();
	}




	public Float calculateAccountMoney(List<PensionNormalpayment> pensionNormalpayments,List<PensionTemppayment> pensionTemppayments) {
		Float accountMoney=new Float(0);
		for(PensionNormalpayment pensionNormalpayment: pensionNormalpayments){
			accountMoney+=pensionNormalpayment.getTotalfees();
		}
		for(PensionTemppayment pensionTemppayment: pensionTemppayments){
			accountMoney+=pensionTemppayment.getTotalfees();
		}
		return accountMoney;
	}




	public void settleTempPayments(List<PensionTemppayment> pensionTemppayments, Long closeNumber, Date settleDate,PensionEmployee employee) {
		for(PensionTemppayment pensionTemppayment: pensionTemppayments){
			PensionTemppayment temp=new PensionTemppayment();
			temp.setId(pensionTemppayment.getId());
			temp.setIsclosed(YES);
			temp.setClosedId(employee.getId());
			temp.setClosedName(employee.getName());
			temp.setClosedtime(settleDate);
			temp.setClosenumber(closeNumber.intValue());
			pensionTemppaymentMapper.updateByPrimaryKeySelective(temp);
		}
		
	}




	@Transactional
	public void settleNormalPayments(List<PensionNormalpayment> pensionNormalpayments,Long closeNumber, Date settleDate, PensionEmployee employee) {
		for(PensionNormalpayment pensionNormalpayment: pensionNormalpayments){
			PensionNormalpayment temp=new PensionNormalpayment();
			temp.setId(pensionNormalpayment.getId());
			temp.setIsclosed(YES);
			temp.setClosedId(employee.getId());
			temp.setClosedName(employee.getName());
			temp.setClosedtime(settleDate);
			temp.setClosenumber(closeNumber.intValue());
			pensionNormalpaymentMapper.updateByPrimaryKeySelective(temp);
		}
		
	}



	@Transactional
	public void redoSettleAccounts(Long departId,Long olderId, PensionEmployee employee) throws PmsException {
		Long settleAccountId=this.redoPensionPaymentRecord(olderId,employee);
		this.redoNormalPayments(settleAccountId);
		this.redoTempPayments(settleAccountId);
		this.updateOlderStatus(olderId, CHECK_OUT);//老人--离院 pension_older
		//this.updateDepartRegister(departId, TO_DEPART);//离院申请--待离院 pension_departregister
	}


	@Transactional
	private void redoTempPayments(Long settleAccountId) {
		PensionTemppaymentExample example=new PensionTemppaymentExample();
		example.or().andClosenumberEqualTo(settleAccountId.intValue());
		List<PensionTemppayment> pensionTemppayments=pensionTemppaymentMapper.selectByExample(example);
		for(PensionTemppayment pensionTemppayment: pensionTemppayments){
			pensionTemppayment.setIsclosed(NO);
			pensionTemppayment.setClosedId(null);
			pensionTemppayment.setClosedName(null);
			pensionTemppayment.setClosedtime(null);
			pensionTemppayment.setClosenumber(null);
			pensionTemppaymentMapper.updateByPrimaryKey(pensionTemppayment);
		}
	}



	@Transactional
	private void redoNormalPayments(Long settleAccountId) {
		PensionNormalpaymentExample example=new PensionNormalpaymentExample();
		example.or().andClosenumberEqualTo(settleAccountId.intValue());
		List<PensionNormalpayment> pensionNormalPayments=pensionNormalpaymentMapper.selectByExample(example);
		for(PensionNormalpayment pensionNormalPayment: pensionNormalPayments){
			pensionNormalPayment.setIsclosed(NO);
			pensionNormalPayment.setClosedId(null);
			pensionNormalPayment.setClosedName(null);
			pensionNormalPayment.setClosedtime(null);
			pensionNormalPayment.setClosenumber(null);
			pensionNormalpaymentMapper.updateByPrimaryKey(pensionNormalPayment);
		}
		
	}




	@Transactional
	private Long redoPensionPaymentRecord(Long olderId, PensionEmployee employee) throws PmsException {
		PensionPaymentrecordExample example=new PensionPaymentrecordExample();
		example.or().andOlderIdEqualTo(olderId);
		example.setOrderByClause("ID DESC");
		List<PensionPaymentrecord> paymentRecords=pensionPaymentrecordMapper.selectByExample(example);
		PensionPaymentrecord paymentRecord=new PensionPaymentrecord();
		if(paymentRecords.size()>0){
			paymentRecord=paymentRecords.get(0);
		}else{
			throw new PmsException("您要撤销的结算记录不存在！");
		}
		paymentRecord.setIscancel(REDO_PAY);
		pensionPaymentrecordMapper.updateByPrimaryKeySelective(paymentRecord);
		PensionPaymentrecord temp=new PensionPaymentrecord();
		temp.setAccountmoney(new Float(0)-paymentRecord.getAccountmoney());
		temp.setCleared2(NO);
		temp.setIscancel(OFFSET_PAY);//撤销结算
		temp.setPayeeId(employee.getId());
		temp.setOlderId(paymentRecord.getOlderId());
		temp.setPayeeName(employee.getName());
		temp.setPaytime(paymentRecord.getPaytime());
		pensionPaymentrecordMapper.insertSelective(temp);
		return paymentRecord.getId();
	}
	




	public List<PensionDepartregister> selectDepartedInfo(Long departId) {
		return pensionDepartregisterMapper.selectDepartedInfo(departId);
	}

	public void updateMessageProcessor(Long departId, Long employeeId,Long deptId) {
		//消息类别
	     String typeIdStr = JavaConfig.fetchProperty("settleAccountsService.departNotify");
	     Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(employeeId, messagetypeId, "pension_departregister", departId, deptId);
		
	}








	public List<PensionDepartapprove> selectApproves(Long departId) {
		return pensionDepartapproveMapper.selectDepartApprove(departId);
	}




	public PensionOlder selectOlder(Long olderId) {
		return pensionOlderMapper.selectByPrimaryKey(olderId);
	}

}
