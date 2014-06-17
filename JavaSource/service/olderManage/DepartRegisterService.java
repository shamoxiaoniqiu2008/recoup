package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.employeeManage.PensionEmployeeMapper;
import persistence.financeManage.PensionNormalpaymentMapper;
import persistence.financeManage.PensionPaymentrecordMapper;
import persistence.financeManage.PensionTemppaymentMapper;
import persistence.logisticsManage.PensionThingdamageMapper;
import persistence.olderManage.PensionDepartapproveMapper;
import persistence.olderManage.PensionDepartnotifyMapper;
import persistence.olderManage.PensionDepartrecordMapper;
import persistence.olderManage.PensionDepartregisterMapper;
import persistence.olderManage.PensionOlderMapper;
import util.PmsException;
import util.SystemConfig;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionNormalpaymentExample;
import domain.financeManage.PensionPaymentrecord;
import domain.financeManage.PensionPaymentrecordExample;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentExample;
import domain.logisticsManage.PensionThingdamage;
import domain.logisticsManage.PensionThingdamageExample;
import domain.olderManage.PensionDepartnotify;
import domain.olderManage.PensionDepartnotifyExample;
import domain.olderManage.PensionDepartrecord;
import domain.olderManage.PensionDepartrecordExample;
import domain.olderManage.PensionDepartregister;
import domain.olderManage.PensionDepartregisterExample;
import domain.olderManage.PensionOlder;
import domain.system.PensionSysUser;

/**
 * 离院请假
 * 
 * @author bill
 * 
 * 
 */
@Service
public class DepartRegisterService {
	
	@Autowired
	private PensionDepartregisterMapper pensionDepartregisterMapper;
	@Autowired
	private PensionDepartrecordMapper pensionDepartrecordMapper;
	@Autowired
	private PensionDepartnotifyMapper pensionDepartnotifyMapper;
	@Autowired
	private PensionNormalpaymentMapper pensionNormalpaymentMapper;
	@Autowired
	private PensionTemppaymentMapper pensionTemppaymentMapper;
	@Autowired
	private PensionThingdamageMapper pensionThingdamageMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;
	@Autowired
	private SystemConfig systemConfig;
	
	private final static Integer YES=1;
	private final static Integer NO=2;
	
	public List<PensionDepartregister> search(Long olderId,Date startTime,Date endTime,int statuses,Long departID) {
		return pensionDepartregisterMapper.selectDepartInfoByTime(olderId,startTime,endTime,statuses,departID);
	}
	public List<PensionDepartregister> searchByComfirm(Long olderId,Date startTime,Date endTime,int statuses,Long departID) {
		return pensionDepartregisterMapper.searchByComfirm(olderId,startTime,endTime,statuses,departID);
	}

	public Map<String, List<PensionNormalpayment>> selectPensionNormalPaymentDetails(
			Long olderId) {
		PensionNormalpaymentExample example=new PensionNormalpaymentExample();
		example.or().andOlderIdEqualTo(olderId)
				.andIsclosedEqualTo(NO)
				.andClearedEqualTo(NO);
		List<PensionNormalpayment> pensionNormalpayments=pensionNormalpaymentMapper.selectByExample(example);
		Map<String, List<PensionNormalpayment>> normalPaymentMap=new HashMap<String, List<PensionNormalpayment>>();
		List<PensionNormalpayment> paidNormalPayments=new ArrayList<PensionNormalpayment>();
		List<PensionNormalpayment> unPaidNormalPayments=new ArrayList<PensionNormalpayment>();
		for(PensionNormalpayment pensionNormalpayment: pensionNormalpayments){
			if(YES.equals(pensionNormalpayment.getIspay())){
				paidNormalPayments.add(pensionNormalpayment);
			}else if(NO.equals(pensionNormalpayment.getIspay())){
				unPaidNormalPayments.add(pensionNormalpayment);
			}
		}
		normalPaymentMap.put("PAIDNORMALPAYMENTS", paidNormalPayments);
		normalPaymentMap.put("UNPAIDNORMALPAYMENTS", unPaidNormalPayments);
		return normalPaymentMap;
	}




	public Map<String, List<PensionTemppayment>> selectPansionTempPaymentDetails(
			Long olderId) {
		PensionTemppaymentExample example =new PensionTemppaymentExample();
		example.or().andClearedEqualTo(NO)
				.andOlderIdEqualTo(olderId)
				.andIsclosedEqualTo(NO);
		List<PensionTemppayment> pensionTemppayments=pensionTemppaymentMapper.selectByExample(example);
		Map<String, List<PensionTemppayment>> tempPaymentMap=new HashMap<String, List<PensionTemppayment>>();
		List<PensionTemppayment> paidTempPayments=new ArrayList<PensionTemppayment>();
		List<PensionTemppayment> unpaidTempPayments=new ArrayList<PensionTemppayment>();
		for(PensionTemppayment pensionTemppayment: pensionTemppayments){
			if(YES.equals(pensionTemppayment.getIspay())){
				paidTempPayments.add(pensionTemppayment);
			}else if(NO.equals(pensionTemppayment.getIspay())){
				unpaidTempPayments.add(pensionTemppayment);
			}
		}
		tempPaymentMap.put("PAIDTEMPPAYMENTS", paidTempPayments);
		tempPaymentMap.put("UNPAIDTEMPPAYMENTS", unpaidTempPayments);
		return tempPaymentMap;
	}




	public List<PensionThingdamage> selectPensionThingDamage(Long olderId) {
		PensionThingdamageExample example=new PensionThingdamageExample();
		example.or().andOlderIdEqualTo(olderId)
				.andIspayEqualTo(NO)
				.andClearedEqualTo(NO);
		return pensionThingdamageMapper.selectByExample(example);
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




	public void settleTempPayments(List<PensionTemppayment> pensionTemppayments, Long closeNumber, Date settleDate,PensionSysUser sysUser) {
		for(PensionTemppayment pensionTemppayment: pensionTemppayments){
			PensionTemppayment temp=new PensionTemppayment();
			temp.setId(pensionTemppayment.getId());
			temp.setIsclosed(YES);
			temp.setClosedId(sysUser.getId());
			temp.setClosedName(sysUser.getUsername());
			temp.setClosedtime(settleDate);
			temp.setClosenumber(closeNumber.intValue());
			pensionTemppaymentMapper.updateByPrimaryKeySelective(temp);
		}
		
	}




	@Transactional
	public void settleNormalPayments(List<PensionNormalpayment> pensionNormalpayments,Long closeNumber, Date settleDate, PensionSysUser sysUser) {
		for(PensionNormalpayment pensionNormalpayment: pensionNormalpayments){
			PensionNormalpayment temp=new PensionNormalpayment();
			temp.setId(pensionNormalpayment.getId());
			temp.setIsclosed(YES);
			temp.setClosedId(sysUser.getId());
			temp.setClosedName(sysUser.getUsername());
			temp.setClosedtime(settleDate);
			temp.setClosenumber(closeNumber.intValue());
			pensionNormalpaymentMapper.updateByPrimaryKeySelective(temp);
		}
		
	}

//	@Transactional
//	private void redoTempPayments(Long settleAccountId) {
//		PensionTemppaymentExample example=new PensionTemppaymentExample();
//		example.or().andClosenumberEqualTo(settleAccountId.intValue());
//		List<PensionTemppayment> pensionTemppayments=pensionTemppaymentMapper.selectByExample(example);
//		for(PensionTemppayment pensionTemppayment: pensionTemppayments){
//			pensionTemppayment.setIsclosed(NO);
//			pensionTemppayment.setClosedId(null);
//			pensionTemppayment.setClosedName(null);
//			pensionTemppayment.setClosedtime(null);
//			pensionTemppayment.setClosenumber(null);
//			pensionTemppaymentMapper.updateByPrimaryKey(pensionTemppayment);
//		}
//	}

	// @Transactional
	// private void redoNormalPayments(Long settleAccountId) {
	// PensionNormalpaymentExample example=new PensionNormalpaymentExample();
	// example.or().andClosenumberEqualTo(settleAccountId.intValue());
	// List<PensionNormalpayment>
	// pensionNormalPayments=pensionNormalpaymentMapper.selectByExample(example);
	// for(PensionNormalpayment pensionNormalPayment: pensionNormalPayments){
	// pensionNormalPayment.setIsclosed(NO);
	// pensionNormalPayment.setClosedId(null);
	// pensionNormalPayment.setClosedName(null);
	// pensionNormalPayment.setClosedtime(null);
	// pensionNormalPayment.setClosenumber(null);
	// pensionNormalpaymentMapper.updateByPrimaryKey(pensionNormalPayment);
	// }
	//
	// }


	
	/**
	 * 离院申请插入
	 */
	public void insertDepartRegisterRecord(PensionDepartregister departregister)
	{
		pensionDepartregisterMapper.insertSelective(departregister);
	}
	/**
	 * 通过主键查询记录
	 */
	public PensionDepartregister selectByPrimaryKey(Long id)
	{
		return pensionDepartregisterMapper.selectByPrimaryKey(id);
	}
	/**
	 * 离院申请更新
	 */
	public void updateDepartRegisterRecord(PensionDepartregister departregister)
	{
		pensionDepartregisterMapper.updateByPrimaryKeySelective(departregister);
	}
	/**
	 * 离院申请删除
	 */
	public void deleteDepartRegisterRecord(PensionDepartregister departregister)
	{
		departregister.setCleared(1);
		pensionDepartregisterMapper.updateByPrimaryKeySelective(departregister);
		
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
					.selectProperty("DEPART_NOTIFY_DPT_ID");
		} catch (PmsException e) {
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
					.selectProperty("DEPART_NOTIFY_EMP_ID");
		} catch (PmsException e) {
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
	
	
	public List<Long> selectDeptIdList(List<Long> empList) {
		List<Long> deptList = new ArrayList<Long>();
		for (Long empId : empList) {
			Long deptId = pensionEmployeeMapper.selectByPrimaryKey(empId)
					.getDeptId();
			deptList.add(deptId);
		}
		return deptList;
	}

}
