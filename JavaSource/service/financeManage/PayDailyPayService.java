package service.financeManage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.dictionary.PensionDicPaywayMapper;
import persistence.financeManage.PensionNormalpaymentMapper;
import persistence.financeManage.PensionNormalpaymentdetailMapper;
import persistence.financeManage.PensionPaymentdetailMapper;
import util.PmsException;
import util.SystemConfig;
import domain.dictionary.PensionDicPayway;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionNormalpaymentdetail;
import domain.financeManage.PensionNormalpaymentdetailExample;
import domain.financeManage.PensionPaymentdetail;

/**
 * 日常缴费
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class PayDailyPayService {

	@Autowired
	private PensionNormalpaymentMapper pensionNormalpaymentMapper;
	@Autowired
	private PensionNormalpaymentdetailMapper pensionNormalpaymentdetailMapper;
	@Autowired
	private PensionPaymentdetailMapper pensionPaymentdetailMapper;
	@Autowired 
	private PensionDicPaywayMapper pensionDicPaywayMapper;
	@Autowired
	private SystemConfig systemConfig;
	
	private final static Integer YES=1;
	private final static Integer NO=2;
	
	private final static Integer PAY_TYPE_NORMAL=1;//支付日常缴费项目
	
	
	
	
	
	
	public List<PensionNormalpayment> search(Integer orderId, String orderName, Integer paidFlag,Date startDate,Date endDate) {
		return pensionNormalpaymentMapper.selectNormalPaymentInfo(orderId,orderName,paidFlag,startDate,endDate);
}

	
	@Transactional
	public void insertNormalPayment(PensionNormalpayment normalPayment,List<PensionNormalpaymentdetail> normalpaymentdetails) {
		pensionNormalpaymentMapper.insertSelective(normalPayment);
		Long id=normalPayment.getId();
		for(PensionNormalpaymentdetail normalpaymentdetail: normalpaymentdetails){
			normalpaymentdetail.setPaymentId(id);
			normalpaymentdetail.setCleared(NO);
			normalpaymentdetail.setIspay(NO);
			normalpaymentdetail.setOlderId(normalPayment.getOlderId());
			normalpaymentdetail.setGeneratetime(new Date());
			pensionNormalpaymentdetailMapper.insertSelective(normalpaymentdetail);
		}
	}
	
	
	@Transactional
	public void updateNormalPayment(PensionNormalpayment normalPayment,List<PensionNormalpaymentdetail> normalpaymentdetails) {
		pensionNormalpaymentMapper.updateByPrimaryKeySelective(normalPayment);
		for(PensionNormalpaymentdetail normalpaymentdetail: normalpaymentdetails){
			pensionNormalpaymentdetailMapper.updateByPrimaryKeySelective(normalpaymentdetail);
			pensionNormalpaymentdetailMapper.insertSelective(normalpaymentdetail);
		}
	}



	public List<PensionNormalpaymentdetail> selectNormalPaymentDetails(Long paymentId) {
		PensionNormalpaymentdetailExample example=new PensionNormalpaymentdetailExample(); 
		example.or().andClearedEqualTo(NO)
				.andPaymentIdEqualTo(paymentId);
		example.setOrderByClause("GENERATETIME DESC");
		return pensionNormalpaymentdetailMapper.selectByExample(example);
	}
	
	@Transactional
	public void deleteNormalPaymentDetail(Long id) {
		pensionNormalpaymentMapper.deleteByPrimaryKey(id);
		PensionNormalpaymentdetailExample example=new PensionNormalpaymentdetailExample();
		example.or().andPaymentIdEqualTo(id);
		pensionNormalpaymentdetailMapper.deleteByExample(example);
	}

	/**
	 * 缴费
	 * @param selectNormalPayment 日常缴费主记录
	 * @param normalPaymentDetails 日常缴费明细列表
	 * @param payWays 支付方式列表（及其支付金额）
	 * @param totalFees 总费用
	 * @param employee 操作员
	 */
	@Transactional
	public void pay(PensionNormalpayment selectNormalPayment,List<PensionNormalpaymentdetail> normalPaymentDetails,
			List<PensionDicPayway> payWays,Float totalFees,PensionEmployee employee) {
		Date curDate=new Date();
		//将缴费明细list全部缴费
		for(PensionNormalpaymentdetail normalPaymentDetail: normalPaymentDetails){
			this.payNormalPaymentDetail(normalPaymentDetail, employee,curDate);
		}
		//将缴费主记录缴费
		this.payNormalPayment(selectNormalPayment, employee,curDate);
		//将追加的项目设置成新增一条主记录和明细list，然后新增
//		this.addFixdFees(selectNormalPayment.getOlderId(),addNormalPaymentDetails,totalFees-selectNormalPayment.getTotalfees(),sysUser);
		//将缴费明细插入到支付明细表
		this.payDetail(selectNormalPayment.getId(),payWays,totalFees,employee.getId(),employee.getName());
	}
	
	/**
	 * 将缴费明细插入到支付明细表
	 * @param paymentid
	 * @param payWays
	 * @param totalFees
	 * @param empoyeeId
	 * @param empolyeeName
	 */
	public void payDetail(Long paymentid, List<PensionDicPayway> payWays,Float totalFees, Long empoyeeId, String empolyeeName) {
		if(totalFees>0){//总金额 >0 缴费
			for(int i=0;i<payWays.size();i++){
				PensionDicPayway payWay=payWays.get(i);
				//如果当前支付方式的支付金额不为0 则插入
				if(payWay.getFee()!=null&&payWay.getFee().floatValue()!=0.0){
					PensionPaymentdetail paymentDetail=new PensionPaymentdetail(); 
					paymentDetail.setCleared(NO);
					paymentDetail.setMoney(payWay.getFee());
					paymentDetail.setPaymentid(paymentid.intValue());
					paymentDetail.setPayStyle_id(payWay.getId());
					paymentDetail.setPaystylename(payWay.getPaywayName());
					paymentDetail.setPaytime(new Date());
					paymentDetail.setPaytypeId(PAY_TYPE_NORMAL);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					pensionPaymentdetailMapper.insertSelective(paymentDetail);
				}
			}
		}else{//退费
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
					paymentDetail.setPaytypeId(PAY_TYPE_NORMAL);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					pensionPaymentdetailMapper.insertSelective(paymentDetail);
				}
			}
		}
	
		
	}

/*
	private void addFixdFees(Long olderId,List<PensionNormalpaymentdetail> normalPaymentDetails,Float totalFees,PensionSysUser sysUser) {
		PensionNormalpayment normalPayment=new PensionNormalpayment();
		normalPayment.setOlderId(olderId);
		normalPayment.setTotalfees(totalFees);
		normalPayment.setIsclosed(NO);
		normalPayment.setIspay(NO);
		normalPayment.setPayeeId(sysUser.getId());
		normalPayment.setPayeeName(sysUser.getUsername());
		normalPayment.setPaytime(new Date());
		this.insertNormalPayment(normalPayment, normalPaymentDetails);
//		this.pay(normalPayment, normalPaymentDetails, null, totalFees, cash, card, trans, sysUser);
	}*/


	/**
	 * 保存收费信息 缴费主记录
	 * @param normalPayment
	 * @param employee
	 * @param curDate
	 */
	public void payNormalPayment(PensionNormalpayment normalPayment,PensionEmployee employee, Date curDate) {
		PensionNormalpayment temp=new PensionNormalpayment();
		temp.setId(normalPayment.getId());
		temp.setIspay(YES);//设置为已收费
		temp.setPayeeId(employee.getId());//收费人编号
		temp.setPayeeName(employee.getName());//收费人姓名
		temp.setPaytime(curDate);//收费日期
		pensionNormalpaymentMapper.updateByPrimaryKeySelective(temp);
	}

	
	/**
	 * 更新保存日常缴费明细
	 * @param normalPaymentDetail
	 * @param employee
	 * @param curDate
	 */
	public void payNormalPaymentDetail(PensionNormalpaymentdetail normalPaymentDetail,PensionEmployee employee, Date curDate){
		PensionNormalpaymentdetail temp=new PensionNormalpaymentdetail();
		temp.setId(normalPaymentDetail.getId());
		temp.setIspay(YES);//设置为已收费
		temp.setPayeeId(employee.getId());//收费人编号
		temp.setPayeeName(employee.getName());//收费人姓名
		temp.setPaytime(curDate);//收费时间
		temp.setCollecttime(curDate);//收费时间
		pensionNormalpaymentdetailMapper.updateByPrimaryKeySelective(temp);
		
	}

	/**
	 * 从系统参数中读取默认的付款方式
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
	
	public List<PensionDicPayway> selectDicPayWays(Long defaultPayWay) {
		return pensionDicPaywayMapper.selectDicPayWaysBySisConfig(defaultPayWay);
		
		
	}
}
