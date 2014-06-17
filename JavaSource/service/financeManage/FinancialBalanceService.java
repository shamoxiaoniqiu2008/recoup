package service.financeManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.dictionary.PensionDicPaywayMapper;
import persistence.dictionary.PensionDicSettletypeMapper;
import persistence.financeManage.PensionDepositLogMapper;
import persistence.financeManage.PensionFinancialpaydetailMapper;
import persistence.financeManage.PensionFinancialpaymentMapper;
import persistence.financeManage.PensionNormalpaymentMapper;
import persistence.financeManage.PensionNormalpaymentdetailMapper;
import persistence.financeManage.PensionTemppaymentMapper;
import persistence.financeManage.PensionTemppaymentdetailMapper;
import persistence.system.PensionDeptMapper;
import util.PmsException;
import util.SystemConfig;
import domain.dictionary.PensionDicPayway;
import domain.financeManage.PensionDepositLog;
import domain.financeManage.PensionDepositLogExample;
import domain.financeManage.PensionFinancialpaydetail;
import domain.financeManage.PensionFinancialpaydetailExample;
import domain.financeManage.PensionFinancialpayment;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionNormalpaymentExample;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentExample;
import domain.system.PensionDept;
	
	/**
	 * 财务结帐
	 * 
	 * @author mary.liu
	 * 
	 * 
	 */
	@Service
	public class FinancialBalanceService {
	
		@Autowired
		private PensionNormalpaymentMapper pensionNormalpaymentMapper;
		@Autowired
		private PensionNormalpaymentdetailMapper pensionNormalpaymentdetailMapper;
		@Autowired
		private PensionTemppaymentMapper pensionTemppaymentMapper;
		@Autowired
		private PensionTemppaymentdetailMapper pensionTemppaymentdetailMapper;
		@Autowired
		private PensionDepositLogMapper pensionDepositLogMapper;
		@Autowired
		private PensionDicSettletypeMapper pensionDicSettletypeMapper;
		@Autowired
		private PensionFinancialpaymentMapper pensionFinancialpaymentMapper;
		@Autowired
		private PensionFinancialpaydetailMapper pensionFinancialpaydetailMapper; 
		@Autowired
		private PensionDicPaywayMapper pensionDicPaywayMapper;
		@Autowired
		private PensionDeptMapper pensionDeptMapper;
		@Autowired
		private SystemConfig systemConfig;
		
		private final static Long FINANCIAL_BALANCE_TYPE_PERSONAGE=new Long(1);//财务结帐-个人
		private final static Long FINANCIAL_BALANCE_TYPE_OFFICE=new Long(2);//财务结帐-科室
		private final static Long FINANCIAL_BALANCE_TYPE_HOSPITAL=new Long(3);//财务结帐-全院
		
		private final static Integer PAYTYPE_NORMAL=1;//缴费类型--日常
		private final static Integer PAYTYPE_TEMP=2;//缴费类型--临时
		private final static Integer PAYTYPE_DEPOSIT=3;//缴费类型--押金
		
		private final static Integer BALANCE=1;//财务结帐
		private final static Integer UNDO_BALANCE=2;//撤销财务结帐
		
		private final static Integer YES_FLAG=1;//是
		private final static Integer NO_FLAG=2;//否
		
		

		/**
		 * 读取系统参数中的 可以查看财务结帐的角色
		 * @param financialBalanceCheckRoleId
		 * @return
		 * @throws PmsException
		 */
		public List<Long> selectFinancialBalanceRoles(String financialBalanceCheckRoleId) throws PmsException {
			List<Long> roles=new ArrayList<Long>();
			try {
				String rolesStr=systemConfig.selectProperty(financialBalanceCheckRoleId);
				  if(rolesStr != null && !rolesStr.isEmpty()) {
				    	 String[]  rolesArr=rolesStr.split(",");
				    	 for(int i = 0; i < rolesArr.length; i++) {
				    		 roles.add(Long.valueOf(rolesArr[i]));
					     }
				     }
			} catch (NumberFormatException e) {
				throw new PmsException("系统参数设置可以查看财务结帐记录的角色有误！",new Throwable("FINANCIAL_BALANCE_CHECK_ROLE"));
			} catch (PmsException e) {
				throw new PmsException("系统参数还没有设置可以查看财务结帐记录的角色！",new Throwable("FINANCIAL_BALANCE_CHECK_ROLE"));
			}
			return roles;
		}
		/**
		 * 读取系统参数中的 财务部门编号
		 * @param financialDeptId
		 * @return
		 * @throws PmsException
		 */
		public Map<String,Object> selectFinancialDepts(String financialDeptId) throws PmsException {
			Map<String,Object> deptIdMap=new HashMap<String,Object>();
			List<Long> deptIds=new ArrayList<Long>();
			String deptIdsStr=null;
			try {
				deptIdsStr=systemConfig.selectProperty(financialDeptId);
				  if(deptIdsStr != null && !deptIdsStr.isEmpty()) {
				    	 String[]  deptIdsArr=deptIdsStr.split(",");
				    	 for(int i = 0; i < deptIdsArr.length; i++) {
				    		 deptIds.add(Long.valueOf(deptIdsArr[i]));
					     }
				     }
				  if(deptIds.size()<1){
					  throw new PmsException("请先在系统参数中设置财务部的编号有误！",new Throwable("FINANCIAL_DEPT"));
				  }
			} catch (NumberFormatException e) {
				throw new PmsException("系统参数设置财务部的编号有误！",new Throwable("FINANCIAL_DEPT"));
			} catch (PmsException e) {
				throw new PmsException("系统参数还没有设置可以查看财务部ID！",new Throwable("FINANCIAL_DEPT"));
			}
			deptIdMap.put("DEPT_ID", deptIds);
			deptIdMap.put("DEPT_ID_STR", deptIdsStr);
			return deptIdMap;
		}

		//查询
		public List<PensionFinancialpayment> search(Date startDate, Date endDate, Long deptId, Long empId, Long financialBalanceType) {
			if(empId==null&&deptId==null){
				if(FINANCIAL_BALANCE_TYPE_PERSONAGE.equals(financialBalanceType)){//如果结帐类型是个人，则能查询的员工只有自己
					return pensionFinancialpaymentMapper.selectFinancialPayments(startDate,endDate,deptId,empId);
				}else if(FINANCIAL_BALANCE_TYPE_OFFICE.equals(financialBalanceType)){//如果结帐类型是科室，则能查询的员工是本科室的所有员工
					return pensionFinancialpaymentMapper.selectFinancialPayments(startDate,endDate,deptId,null);
				}else if(FINANCIAL_BALANCE_TYPE_HOSPITAL.equals(financialBalanceType)){//如果结帐类型是全院，则能查询的员工是所有财务部门的所有员工
					return pensionFinancialpaymentMapper.selectFinancialPayments(startDate,endDate,null,null);
				}else{
					return pensionFinancialpaymentMapper.selectFinancialPayments(startDate,endDate,null,null);
				}
			}else{
				return pensionFinancialpaymentMapper.selectFinancialPayments(startDate,endDate,deptId,empId);
			}
		}

		//查询可结帐的缴费记录
		public Map<String,Object> searchBalanceInfo(Long financialBalanceType,//结帐类型
				Date balanceStartDate,//结帐开始时间
				Date balanceEndDate,//结帐结束时间
				Long empId,//当前员工编号
				Long deptId,//当前部门编号
				Integer settleFlag//是否已结帐
				) {
			//查询出的缴费记录包括 日常缴费记录，临时缴费记录，押金缴费记录
			List<PensionNormalpayment> normalpayments=new ArrayList<PensionNormalpayment>(); 
			List<PensionTemppayment> temppayments=new ArrayList<PensionTemppayment>(); 
			List<PensionDepositLog> depositLogs=new ArrayList<PensionDepositLog>();
			List<PensionDicPayway> dicPayWays=new ArrayList<PensionDicPayway>();
			if(FINANCIAL_BALANCE_TYPE_PERSONAGE.equals(financialBalanceType)){
				//如果结帐类型为个人，就只查询由本人收费的缴费记录
				normalpayments=pensionNormalpaymentMapper.selectNormalBalancePayments(balanceStartDate,balanceEndDate,empId,null,settleFlag);
				temppayments=pensionTemppaymentMapper.selectTempBalanceaPayments(balanceStartDate,balanceEndDate,empId,null,settleFlag);
				depositLogs=pensionDepositLogMapper.selectBalanceDepositLogs(balanceStartDate, balanceEndDate, empId, null,settleFlag);
			}else if(FINANCIAL_BALANCE_TYPE_OFFICE.equals(financialBalanceType)){
				//如果结帐类型为科室，就查询本科室的缴费记录selectNormalBalancePayments
				normalpayments=pensionNormalpaymentMapper.selectNormalBalancePayments(balanceStartDate,balanceEndDate,null,deptId,settleFlag);
				temppayments=pensionTemppaymentMapper.selectTempBalanceaPayments(balanceStartDate,balanceEndDate,null,deptId,settleFlag);
				depositLogs=pensionDepositLogMapper.selectBalanceDepositLogs(balanceStartDate, balanceEndDate, null, deptId,settleFlag);
			}else if(FINANCIAL_BALANCE_TYPE_HOSPITAL.equals(financialBalanceType)){
				//如果结帐类型是全员，就查询所有缴费记录
				normalpayments=pensionNormalpaymentMapper.selectNormalBalancePayments(balanceStartDate,balanceEndDate,null,null,settleFlag);
				temppayments=pensionTemppaymentMapper.selectTempBalanceaPayments(balanceStartDate,balanceEndDate,null,null,settleFlag);
				depositLogs=pensionDepositLogMapper.selectBalanceDepositLogs(balanceStartDate, balanceEndDate, null, null,settleFlag);
			}
			//结帐总额初始化为0
			Float totalFee=new Float(0);
			//日常缴费记录主键--用于从pension_paymentdetail中查询缴费明细
			List<Long> normalIdList=new ArrayList<Long>();
			if(normalpayments!=null&&normalpayments.size()>0){
				for(PensionNormalpayment normalpayment:normalpayments){
					totalFee+=normalpayment.getTotalfees();
					normalIdList.add(normalpayment.getId());
				}
			}
			//临时缴费记录主键--用于从pension_paymentdetail中查询缴费明细
			List<Long> tempIdList=new ArrayList<Long>();
			if(temppayments!=null&&temppayments.size()>0){
				for(PensionTemppayment temppayment: temppayments){
					totalFee+=temppayment.getTotalfees();
					tempIdList.add(temppayment.getId());
				}
			}
			//押金缴费记录主键--用于从pension_paymentdetail中查询缴费明细
			List<Long> depositLogIdList=new ArrayList<Long>();
			if(depositLogs!=null&&depositLogs.size()>0){
				for(PensionDepositLog depositLog:depositLogs){
					totalFee+=depositLog.getTradefee();
					depositLogIdList.add(depositLog.getId());
				}
			}
			if(normalIdList==null||normalIdList.size()<1){
				normalIdList=null;
			}
			if(tempIdList==null||tempIdList.size()<1){
				tempIdList=null;
			}
			if(depositLogIdList==null||depositLogIdList.size()<1){
				depositLogIdList=null;
			}
			if(FINANCIAL_BALANCE_TYPE_PERSONAGE.equals(financialBalanceType)){
				//对应pension_paymentdetail中paymentID和pay_type_id
				dicPayWays=pensionDicPaywayMapper.selectBalancePayWays(
						normalIdList, PAYTYPE_NORMAL,
						tempIdList,PAYTYPE_TEMP,
						depositLogIdList,PAYTYPE_DEPOSIT,
						empId, null);
			}else if(FINANCIAL_BALANCE_TYPE_OFFICE.equals(financialBalanceType)){
				//对应pension_paymentdetail中paymentID和pay_type_id
				dicPayWays=pensionDicPaywayMapper.selectBalancePayWays(
						normalIdList, PAYTYPE_NORMAL,
						tempIdList,PAYTYPE_TEMP,
						depositLogIdList,PAYTYPE_DEPOSIT,
						null, deptId);
			}else if(FINANCIAL_BALANCE_TYPE_HOSPITAL.equals(financialBalanceType)){
				//对应pension_paymentdetail中paymentID和pay_type_id
				dicPayWays=pensionDicPaywayMapper.selectBalancePayWays(
						normalIdList, PAYTYPE_NORMAL,
						tempIdList,PAYTYPE_TEMP,
						depositLogIdList,PAYTYPE_DEPOSIT,
						null, null);
			}
			Map<String,Object> balanceInfoMap=new HashMap<String,Object>();
			balanceInfoMap.put("NORMAL_PAYMENT", normalpayments);
			balanceInfoMap.put("TEMP_PAYMENT",temppayments);
			balanceInfoMap.put("DEPOSIT_LOG",depositLogs);
			balanceInfoMap.put("TOTAL_FEE",totalFee);
			balanceInfoMap.put("PAY_WAY",dicPayWays);
			return balanceInfoMap;
		}

		
		



		/**
		 * 从系统参数中读取财务结帐类型
		 * @param financialBalanceType
		 * @return
		 * @throws PmsException
		 */
		public Long selectFinancialBalanceType(String financialBalanceType) throws PmsException {
			List<Long> balanceTypeIds=new ArrayList<Long>();
			try {
				String balanceTypeStr=systemConfig.selectProperty(financialBalanceType);
				  if(balanceTypeStr != null && !balanceTypeStr.isEmpty()) {
				    	 String[]  balanceTypeArr=balanceTypeStr.split(",");
				    	 for(int i = 0; i < balanceTypeArr.length; i++) {
				    		 balanceTypeIds.add(Long.valueOf(balanceTypeArr[i]));
					     }
				     }
				  if(balanceTypeIds.size()<1){
					  throw new PmsException("系统参数还没有设置财务结帐类型！",new Throwable("FINANCIAL_BALANCE_TYPE"));
				  }
			} catch (NumberFormatException e) {
				throw new PmsException("系统参数设置财务结帐类型有误！",new Throwable("FINANCIAL_BALANCE_TYPE"));
			} catch (PmsException e) {
				throw new PmsException("系统参数还没有设置财务结帐类型！",new Throwable("FINANCIAL_BALANCE_TYPE"));
			}
			return balanceTypeIds.get(0);
		}

		public String selectDeptName(Long deptId) {
			PensionDept dept=pensionDeptMapper.selectByPrimaryKey(deptId);
			if(dept!=null){
				return dept.getName();
			}else{
				return "";
			}
		}

		//结帐
		@Transactional
		public void insertFinancialPayment(PensionFinancialpayment financialPayment,List<PensionDicPayway> payWays, 
				List<PensionNormalpayment> normalPayments, 
				List<PensionTemppayment> tempPayments, 
				List<PensionDepositLog> depositLogs) {
			pensionFinancialpaymentMapper.insertSelective(financialPayment);
			this.insertFinancialPaydetail(financialPayment.getId(),payWays,financialPayment.getPaytime());
			this.updateBalanceNormalPayments(financialPayment.getId(),normalPayments,BALANCE);
			this.updateBalanceTempPayments(financialPayment.getId(),tempPayments,BALANCE);
			this.updateBalanceDepositLogs(financialPayment.getId(),depositLogs,BALANCE);
		}
		/**
		 * 更新结帐记录 -- 押金
		 * @param settleId
		 * @param depositLogs
		 * @param balanceFlag
		 */
		@Transactional
		private void updateBalanceDepositLogs(Long settleId,List<PensionDepositLog> depositLogs, Integer balanceFlag) {
			for(PensionDepositLog depositLog:depositLogs){
				PensionDepositLog temp=new PensionDepositLog();
				temp.setId(depositLog.getId());
				temp.setSettledFlag(YES_FLAG);
				temp.setSettleId(settleId);
				pensionDepositLogMapper.updateByPrimaryKeySelective(temp);
			}
		}
		
		/**
		 * 更新结帐记录 -- 临时缴费
		 * @param settleId
		 * @param tempPayments
		 * @param balanceFlag
		 */
		@Transactional
		private void updateBalanceTempPayments(Long settleId,List<PensionTemppayment> tempPayments, Integer balanceFlag) {
			for(PensionTemppayment tempPayment: tempPayments){
				PensionTemppayment temp=new PensionTemppayment();
				temp.setId(tempPayment.getId());
				temp.setSettledFlag(YES_FLAG);
				temp.setSettleId(settleId);
				pensionTemppaymentMapper.updateByPrimaryKeySelective(temp);
			}
		}
		
		/**
		 * 更新结帐记录 -- 日常缴费
		 * @param settleId
		 * @param normalPayments
		 * @param balanceFlag
		 */
		@Transactional
		private void updateBalanceNormalPayments(Long settleId,List<PensionNormalpayment> normalPayments, Integer balanceFlag) {
			for(PensionNormalpayment normalPayment:normalPayments){
				PensionNormalpayment temp=new PensionNormalpayment();
				temp.setId(normalPayment.getId());
				temp.setSettledFlag(YES_FLAG);
				temp.setSettleId(settleId);
				pensionNormalpaymentMapper.updateByPrimaryKeySelective(temp);
			}
		}

		/**
		 * 插入结帐记录明细
		 * @param id
		 * @param payWays
		 * @param paytime
		 */
		@Transactional
		private void insertFinancialPaydetail(Long id,List<PensionDicPayway> payWays,Date paytime) {
			for(PensionDicPayway payWay: payWays){
				PensionFinancialpaydetail financialpaydetail=new PensionFinancialpaydetail();
				financialpaydetail.setMoney(payWay.getFee());
				financialpaydetail.setPayment_id(id);
				financialpaydetail.setPaystyleId(payWay.getId());
				financialpaydetail.setPaytime(paytime);
				pensionFinancialpaydetailMapper.insertSelective(financialpaydetail);
			}
			
		}

	
		//撤销结帐
		@Transactional
		public void undoBalance(PensionFinancialpayment financialPayment) {
			this.updateUndoBalanceFinancialPayment(financialPayment.getId());
			this.updateUndoBalanceFinancialPaydetail(financialPayment.getId());
			this.updateUndoBalanceNormalPayments(financialPayment.getId());
			this.updateUndoBalanceTempPayments(financialPayment.getId());
			this.updateUndoBalanceDepositLogs(financialPayment.getId());
		}

		private void updateUndoBalanceDepositLogs(Long financialPaymentId) {
			PensionDepositLogExample example=new PensionDepositLogExample();
			example.or().andSettledFlagEqualTo(YES_FLAG)
				.andSettleIdEqualTo(financialPaymentId);
			PensionDepositLog temp=new PensionDepositLog();
			temp.setSettledFlag(NO_FLAG);
			temp.setSettleId(null);
			pensionDepositLogMapper.updateByExampleSelective(temp, example);
		}

		private void updateUndoBalanceTempPayments(Long financialPaymentId) {
			PensionTemppaymentExample example =new PensionTemppaymentExample();
			example.or().andSettledFlagEqualTo(YES_FLAG)
			.andSettleIdEqualTo(financialPaymentId);
			PensionTemppayment temp=new PensionTemppayment();
			temp.setSettledFlag(NO_FLAG);
			temp.setSettleId(null);
			pensionTemppaymentMapper.updateByExampleSelective(temp, example);
			
		}

		private void updateUndoBalanceNormalPayments(Long financialPaymentId) {
			PensionNormalpaymentExample example=new PensionNormalpaymentExample();
			example.or().andSettledFlagEqualTo(YES_FLAG)
				.andSettleIdEqualTo(financialPaymentId);
			PensionNormalpayment temp=new PensionNormalpayment();
			temp.setSettledFlag(NO_FLAG);
			temp.setSettleId(null);
			pensionNormalpaymentMapper.updateByExampleSelective(temp, example);
			
		}

		private void updateUndoBalanceFinancialPaydetail(Long financialPaymentId) {
			PensionFinancialpaydetailExample example=new PensionFinancialpaydetailExample(); 
			example.or().andPayment_idEqualTo(financialPaymentId);
			PensionFinancialpaydetail temp=new PensionFinancialpaydetail();
			temp.setCleared(YES_FLAG);
			pensionFinancialpaydetailMapper.updateByExampleSelective(temp, example);
		}

		private void updateUndoBalanceFinancialPayment(Long financialPaymentId) {
			PensionFinancialpayment tempFinancialpayment=new PensionFinancialpayment();
			tempFinancialpayment.setId(financialPaymentId);
			tempFinancialpayment.setCleared(YES_FLAG);
			pensionFinancialpaymentMapper.updateByPrimaryKeySelective(tempFinancialpayment);
		}
	}
