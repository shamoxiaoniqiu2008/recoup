package service.financeManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.dictionary.PensionDicDeposittypeMapper;
import persistence.dictionary.PensionDicPaywayMapper;
import persistence.financeManage.PensionDepositLogMapper;
import persistence.financeManage.PensionDepositMapper;
import persistence.financeManage.PensionPaymentdetailMapper;
import util.PmsException;
import util.SystemConfig;
import domain.dictionary.PensionDicDeposittype;
import domain.dictionary.PensionDicDeposittypeExample;
import domain.dictionary.PensionDicPayway;
import domain.financeManage.PensionDeposit;
import domain.financeManage.PensionDepositLog;
import domain.financeManage.PensionDepositLogExample;
import domain.financeManage.PensionPaymentdetail;

/**
 * 押金缴纳
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class DepositService {
	
	@Autowired
	private PensionDepositMapper pensionDepositMapper;
	@Autowired 
	private PensionDepositLogMapper pensionDepositLogMapper;
	@Autowired
	private PensionDicDeposittypeMapper pensionDicDeposittypeMapper;
	@Autowired
	private PensionPaymentdetailMapper pensionPaymentdetailMapper;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired 
	private PensionDicPaywayMapper pensionDicPaywayMapper;
	
	
	private final static Integer PAY_TYPE_DEPOSIT=3;//缴费类型--押金
	
	
	private final static Integer YES=1;
	private final static Integer NO=2;

	public List<PensionDeposit> search(Long depositId, Integer status,Integer depositStatus) {
		return pensionDepositMapper.selectDepositList(depositId,status,depositStatus);
	}


	/**
	 * 从字典表中读取所有的押金类型
	 */
	public List<PensionDicDeposittype> selectDepositTypes() {
		PensionDicDeposittypeExample example=new PensionDicDeposittypeExample();
		example.or().andInvalidedEqualTo(YES);//有效
		return pensionDicDeposittypeMapper.selectByExample(example);
	}


	/**
	 * 从配置参数中读取押金额
	 */
	public Float selectDepositTypeTotalFees(String depositTypeSysConfig) throws PmsException {
		Float totalFees=null;
		try{
			totalFees=new Float(systemConfig.selectProperty(depositTypeSysConfig));
		}catch(NumberFormatException e){
			totalFees=new Float(0);
			new PmsException("从配置参数中获取押金金额出错！");
		}
		return totalFees;
	}



	/**
	 * 插入 交易明细
	 */
	public void insertPaymentDetail(Long paymentid, List<PensionDicPayway> payWays,boolean backFlag,Long empoyeeId,String empolyeeName) {
		if(!backFlag){//交款
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
					paymentDetail.setPaytypeId(PAY_TYPE_DEPOSIT);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					pensionPaymentdetailMapper.insertSelective(paymentDetail);
				}
			}
		}else{//退款
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
					paymentDetail.setPaytypeId(PAY_TYPE_DEPOSIT);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					paymentDetail.setPayerId(empoyeeId);
					paymentDetail.setPayName(empolyeeName);
					pensionPaymentdetailMapper.insertSelective(paymentDetail);
				}
			}
		}
	
		
	}


	public Long selectDepositLogId() {
		
		return null;
	}

	/**
	 * 保存一条押金缴纳明细
	 * @param backDepositLogId 如果是退押金，表示所退押金明细编号，否则传入null
	 * @param depoait 押金主记录
	 * @param addDepositLog 新增加的押金明细
	 * @param payWays 付款方式
	 * @param backFlag 退押金标识
	 * @param empoyeeId 操作人编号
	 * @param empolyeeName 操作人姓名
	 * @return
	 */
	public Long saveDepositLog(Long backDepositLogId, PensionDeposit depoait, PensionDepositLog addDepositLog,List<PensionDicPayway> payWays,boolean backFlag , Long empoyeeId, String empolyeeName) {
		//更新 老人的押金主记录--主要是押金额有变化
		this.updateDeposit(depoait);
		//如果是退押金，还要将选中的押金明细状态 置为 已退
		if(backDepositLogId!=null){
			this.updateDepositLog(backDepositLogId);
		}
		//插入新的押金明细
		pensionDepositLogMapper.insertSelective(addDepositLog);
		Long depositLogId=addDepositLog.getId();
		//插入交易明细记录
		this.insertPaymentDetail(depositLogId,payWays, backFlag, empoyeeId, empolyeeName);
		return depositLogId;
	}

	/**
	 * 退回押金后，更新押金明细记录的状态为 已退回
	 * @param backDepositLogId
	 */
	public void updateDepositLog(Long backDepositLogId) {
		PensionDepositLog depositLog=new PensionDepositLog();
		depositLog.setId(backDepositLogId);
		depositLog.setBackflag(YES);
		pensionDepositLogMapper.updateByPrimaryKeySelective(depositLog);
		
	}


	/**
	 * 更新老人押金主记录，更新押金额
	 * @param depoait
	 */
	public void updateDeposit(PensionDeposit depoait) {
		pensionDepositMapper.updateByPrimaryKeySelective(depoait);
		
	}


	public PensionDicDeposittype selectDepositType(Long id) {
		return pensionDicDeposittypeMapper.selectByPrimaryKey(id);
	}


	/**
	 * 根据押金主记录ID，读取押金缴纳明细
	 * @param id
	 * @return
	 */
	public List<PensionDepositLog> selectDepositLogs(Long id) {
		PensionDepositLogExample example=new PensionDepositLogExample();
		example.or().andDepositIdEqualTo(id);
		example.setOrderByClause("trade_date DESC");
		return pensionDepositLogMapper.selectByExample(example);
	}

	/**
	 * 过滤老人的押金缴纳明细列表，返回可退的押金明细列表
	 */
	public List<PensionDepositLog> filterBackDepositLogs(
			List<PensionDepositLog> depositLogs) {
		List<PensionDepositLog> backDepositLogs=new ArrayList<PensionDepositLog>();
		for(PensionDepositLog depositLog: depositLogs){
			if(NO.equals(depositLog.getBackflag())&&depositLog.getTradefee().floatValue()>0){
				backDepositLogs.add(depositLog);
			}
		}
		return backDepositLogs;
	}
	
	/**
	 * 从系统参数中读取默认支付方式编号
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
	 * 查询所有的支付方式，并将默认支付方式放在第一位
	 * @param defaultPayWay
	 * @return
	 */
	public List<PensionDicPayway> selectDicPayWays(Long defaultPayWay) {
		return pensionDicPaywayMapper.selectDicPayWaysBySisConfig(defaultPayWay);
		
		
	}

}
