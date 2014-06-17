package service.financeManage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.caterManage.PensionOrderMapper;
import persistence.dictionary.PensionDicPaywayMapper;
import persistence.financeManage.PensionPaymentdetailMapper;
import persistence.financeManage.PensionTemppaymentMapper;
import persistence.financeManage.PensionTemppaymentdetailMapper;
import util.PmsException;
import util.SystemConfig;
import domain.caterManage.PensionOrder;
import domain.caterManage.PensionOrderExample;
import domain.dictionary.PensionDicPayway;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionPaymentdetail;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentdetail;
import domain.financeManage.PensionTemppaymentdetailExample;

/**
 * 日常缴费
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class PayTempPayService {

	@Autowired
	private PensionTemppaymentMapper pensionTemppaymentMapper;
	@Autowired
	private PensionTemppaymentdetailMapper pensionTemppaymentdetailMapper;
	@Autowired
	private PensionPaymentdetailMapper pensionPaymentdetailMapper;
	@Autowired
	PensionOrderMapper pensionOrderMapper;
	@Autowired 
	private PensionDicPaywayMapper pensionDicPaywayMapper;
	@Autowired
	private SystemConfig systemConfig;
	
	
	private final static Integer YES=1;
	private final static Integer NO=2;
	
	private final static Integer PAY_TYPE_TEMP=2;
	
	
	
	public List<PensionTemppayment> search(Integer orderId,String orderName,  Integer paidFlag,Date startDate,Date endDate) {
		return pensionTemppaymentMapper.selectTempPaymentInfo(orderId,orderName,paidFlag,startDate,endDate);
	}

	

	
	



	public List<PensionTemppaymentdetail> selectTempPaymentDetails(Long paymentId) {
		PensionTemppaymentdetailExample example=new PensionTemppaymentdetailExample(); 
		example.or().andClearedEqualTo(NO)
				.andPaymentIdEqualTo(paymentId);
		example.setOrderByClause("GENERATETIME DESC");
		return pensionTemppaymentdetailMapper.selectByExample(example);
	}
	/**
	 * 删除临时缴费项目及其对应的明细
	 * @param id
	 */
	@Transactional
	public void deleteTempPaymentDetail(Long id) {
		pensionTemppaymentMapper.deleteByPrimaryKey(id);
		PensionTemppaymentdetailExample example=new PensionTemppaymentdetailExample();
		example.or().andPaymentIdEqualTo(id);
		pensionTemppaymentdetailMapper.deleteByExample(example);
	}

	/**
	 * 收费
	 * @param selectTempPayment
	 * @param tempPaymentDetails
	 * @param payWays
	 * @param totalFees
	 * @param employee
	 */
	@Transactional
	public void pay(PensionTemppayment selectTempPayment,List<PensionTemppaymentdetail> tempPaymentDetails,
			List<PensionDicPayway> payWays,Float totalFees,PensionEmployee employee) {
		Date curDate=new Date();
		//将缴费明细list全部缴费
		for(PensionTemppaymentdetail tempPaymentDetail: tempPaymentDetails){
			this.payTempPaymentDetail(tempPaymentDetail,curDate);
		}
		if(tempPaymentDetails.size()>0){
			PensionTemppaymentdetail tempPaymentdetail=tempPaymentDetails.get(0);
			//如果该临时缴费项目来源于零点餐，将零点餐明细设置为已收费
			if(NO.equals(tempPaymentdetail.getSource())){
				this.updatePensionOrderPaymentId(tempPaymentdetail.getPaymentId());
			}
		}
		//将缴费主记录缴费
		this.payTempPayment(selectTempPayment, employee,curDate);
		//将追加的项目设置成新增一条主记录和明细list，然后新增
//		this.addFixdFees(selectNormalPayment.getOlderId(),addNormalPaymentDetails,totalFees-selectNormalPayment.getTotalfees(),sysUser);
		this.payDetail(selectTempPayment.getId(),payWays,totalFees,employee.getId(),employee.getName());
	}
	
	/**
	 * 设置零点餐明细为已收费 
	 * @param paymentId
	 */
	public void updatePensionOrderPaymentId(Long paymentId) {
		PensionOrderExample example=new PensionOrderExample();
		example.or().andClearedEqualTo(NO).andPaymentIdEqualTo(paymentId);
		PensionOrder order=new PensionOrder();
		order.setIspay(YES);
		pensionOrderMapper.updateByExampleSelective(order, example);
		
	}








	/**
	 * 向付款明细表(Pension_paymentdetail)中存入缴费明细
	 * @param paymentid
	 * @param payWays
	 * @param totalFees
	 * @param empoyeeId
	 * @param empolyeeName
	 */
	public void payDetail(Long paymentid,List<PensionDicPayway> payWays,Float totalFees,Long empoyeeId, String empolyeeName) {
		if(totalFees>0){
			for(int i=0;i<payWays.size();i++){
				PensionDicPayway payWay=payWays.get(i);
				if(payWay.getFee()!=null&&payWay.getFee().floatValue()!=0.0){
					PensionPaymentdetail paymentDetail=new PensionPaymentdetail(); 
					paymentDetail.setCleared(NO);
					paymentDetail.setMoney(payWay.getFee());
					paymentDetail.setPaymentid(paymentid.intValue());
					paymentDetail.setPayStyle_id(payWay.getId());
					paymentDetail.setPaystylename(payWay.getPaywayName());
					paymentDetail.setPaytime(new Date());
					paymentDetail.setPaytypeId(PAY_TYPE_TEMP);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					pensionPaymentdetailMapper.insertSelective(paymentDetail);
				}
			}
		}else{
			for(int i=0;i<payWays.size();i++){
				PensionDicPayway payWay=payWays.get(i);
				if(payWay.getFee()!=null&&payWay.getFee().floatValue()!=0.0){
					PensionPaymentdetail paymentDetail=new PensionPaymentdetail(); 
					paymentDetail.setCleared(NO);
					paymentDetail.setMoney(-payWay.getFee());
					paymentDetail.setPaymentid(paymentid.intValue());
					paymentDetail.setPayStyle_id(payWay.getId());
					paymentDetail.setPaystylename(payWay.getPaywayName());
					paymentDetail.setPaytime(new Date());
					paymentDetail.setPaytypeId(PAY_TYPE_TEMP);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					pensionPaymentdetailMapper.insertSelective(paymentDetail);
				}
			}
		}
	}


	/*private void addFixdFees(Long olderId,List<PensionNormalpaymentdetail> normalPaymentDetails,Float totalFees,PensionSysUser sysUser) {
		PensionNormalpayment normalPayment=new PensionNormalpayment();
		normalPayment.setOlderId(olderId);
		normalPayment.setTotalfees(totalFees);
		normalPayment.setIsclosed(NO);
		normalPayment.setIspay(NO);
		normalPayment.setPayeeId(sysUser.getId());
		normalPayment.setPayeeName(sysUser.getUsername());
		normalPayment.setPaytime(new Date());
//		this.insertNormalPayment(normalPayment, normalPaymentDetails);
//		this.pay(normalPayment, normalPaymentDetails, null, totalFees, cash, card, trans, sysUser);
	}*/


	/**
	 * 将临时缴费项目设置为已收费 并设置收费人和收费时间
	 * @param tempPayment
	 * @param employee
	 */
	public void payTempPayment(PensionTemppayment tempPayment,PensionEmployee employee,Date curDate) {
		PensionTemppayment temp=new PensionTemppayment();
		temp.setId(tempPayment.getId());
		temp.setIspay(YES);
		temp.setPayeeId(employee.getId());
		temp.setPayeeName(employee.getName());
		temp.setPaytime(curDate);
		pensionTemppaymentMapper.updateByPrimaryKeySelective(temp);
	}


	/**
	 * 将临时缴费项目明细设置为已收费
	 * @param tempPaymentDetail
	 * @param curDate 
	 */
	public void payTempPaymentDetail(PensionTemppaymentdetail tempPaymentDetail, Date curDate){
		PensionTemppaymentdetail temp=new PensionTemppaymentdetail();
		temp.setId(tempPaymentDetail.getId());
		temp.setPaytime(curDate);
		temp.setIspay(YES);
		pensionTemppaymentdetailMapper.updateByPrimaryKeySelective(temp);
		
	}

	/**
	 * 读取系统参数中设置的支付方式
	 * @return
	 * @throws PmsException
	 */
	public Long selectDefaultPayWay() throws PmsException{
		Long defaultPayWay;
		try {
			String defaultPayWayStr = systemConfig.selectProperty("DEFAULT_PAY_WAY");
			if(defaultPayWayStr!=null && defaultPayWayStr.length()>0){
				defaultPayWay=new Long(defaultPayWayStr);
			}else{
				throw new PmsException("请首先在系统参数中指定默认付款方式！");
			}
		} catch (PmsException e) {
			throw new PmsException("读取系统参数时出错！");
		}
		return defaultPayWay;
	}
	
	/**
	 * 获取默认支付方式
	 * @param defaultPayWay
	 * @return
	 */
	public List<PensionDicPayway> selectDicPayWays(Long defaultPayWay) {
		return pensionDicPaywayMapper.selectDicPayWaysBySisConfig(defaultPayWay);
		
		
	}







}
