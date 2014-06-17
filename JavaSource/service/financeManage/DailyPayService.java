package service.financeManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.configureManage.PensionItempurseMapper;
import persistence.dictionary.PensionDictRefundtypeMapper;
import persistence.financeManage.PensionNormalpaymentMapper;
import persistence.financeManage.PensionNormalpaymentdetailMapper;
import persistence.financeManage.PensionOlderItempurseMapper;
import persistence.olderManage.PensionLivingLogMapper;
import util.CauculateMonthUtil;
import util.PmsException;
import util.SystemConfig;
import domain.configureManage.PensionItempurse;
import domain.dictionary.PensionDictRefundtype;
import domain.employeeManage.PensionEmployee;
import domain.financeManage.PensionNormalpayment;
import domain.financeManage.PensionNormalpaymentdetail;
import domain.financeManage.PensionNormalpaymentdetailExample;
import domain.financeManage.PensionOlderItempurse;
import domain.financeManage.PensionOlderItempurseExample;
import domain.olderManage.PensionLivingLog;
import domain.olderManage.PensionLivingLogExample;
import domain.system.PensionDept;
import domain.system.PensionDeptExample;

/**
 * 日常缴费
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class DailyPayService {

	@Autowired
	private PensionNormalpaymentMapper pensionNormalpaymentMapper;
	@Autowired
	private PensionNormalpaymentdetailMapper pensionNormalpaymentdetailMapper;
	@Autowired
	private PensionLivingLogMapper pensionLivingLogMapper;
	@Autowired
	private PensionItempurseMapper pensionItempurseMapper;
	@Autowired
	private PensionDictRefundtypeMapper pensionDictRefundtypeMapper;
	@Autowired
	private PensionOlderItempurseMapper pensionOlderItempurseMapper; 
	@Autowired
	private SystemConfig systemConfig;

	private final static Integer YES=1;
	private final static Integer NO=2;
	
	private final static Integer NORMALPAYMENT_BEDLEVEL=2;//由床位记录生成缴费记录
	
	private final static Integer MONTH=30;
	
	/**
	 * 查询
	 * @param endDate 录入时间 起始时间
	 * @param startDate 录入时间 结束时间
	 */
	public List<PensionNormalpayment> search(Integer orderId, String orderName, Integer paidFlag, Date startDate, Date endDate) {
			return pensionNormalpaymentMapper.selectNormalPaymentInfo(orderId,orderName,paidFlag,startDate,endDate);
	}

	/**
	 * 插入一条日常缴费主记录
	 */
	@Transactional
	public Long insertNormalPayment(PensionNormalpayment normalPayment,List<PensionNormalpaymentdetail> normalpaymentdetails) {
		//插入主记录
		pensionNormalpaymentMapper.insertSelective(normalPayment);
		//返回主记录的ID
		Long id=normalPayment.getId();
		//插入对应的明细
		this.insertNormalPaymentDetails(id, normalPayment.getOlderId(), normalpaymentdetails);
		return id;
	}
	
	
	/**
	 * 逐条插入日常缴费明细
	 */
	public void insertNormalPaymentDetails(Long paymentId,Long olderId,List<PensionNormalpaymentdetail> normalpaymentdetails){
		for(PensionNormalpaymentdetail normalpaymentdetail: normalpaymentdetails){
			normalpaymentdetail.setId(null);
			normalpaymentdetail.setPaymentId(paymentId);
			normalpaymentdetail.setCleared(NO);
			normalpaymentdetail.setGeneratetime(new Date());
			normalpaymentdetail.setIspay(NO);
			normalpaymentdetail.setOlderId(olderId);
			pensionNormalpaymentdetailMapper.insertSelective(normalpaymentdetail);
		}
	}
	
	
	/**
	 *更新日常缴费主记录 
	 */
	@Transactional
	public void updateNormalPayment(PensionNormalpayment normalPayment,List<PensionNormalpaymentdetail> normalpaymentdetails) {
		pensionNormalpaymentMapper.updateByPrimaryKeySelective(normalPayment);
		//将该记录之前对应的明细记录删除
		this.deleteNormalPaymentDetails(normalPayment.getId());
		//重新插入新的明细记录
		this.insertNormalPaymentDetails(normalPayment.getId(), normalPayment.getOlderId(), normalpaymentdetails);
	}

	/**
	 * 逻辑删除一条日常缴费明细
	 */
	public void deleteNormalPaymentDetails(Long peymentId){
		PensionNormalpaymentdetailExample example=new PensionNormalpaymentdetailExample(); 
		example.or().andPaymentIdEqualTo(peymentId);
		PensionNormalpaymentdetail temp=new PensionNormalpaymentdetail();
		temp.setCleared(YES);
		pensionNormalpaymentdetailMapper.updateByExampleSelective(temp, example);
	}
	
	/**
	 * 根据日常缴费主记录编号获取对应的缴费明细列表
	 */
	public List<PensionNormalpaymentdetail> selectNormalPaymentDetails(Long paymentId) {
		PensionNormalpaymentdetailExample example=new PensionNormalpaymentdetailExample(); 
		example.or().andClearedEqualTo(NO)
				.andPaymentIdEqualTo(paymentId);
		example.setOrderByClause("GENERATETIME DESC");
		return pensionNormalpaymentdetailMapper.selectByExample(example);
	}

	/**
	 * 删除日常缴费明细
	 */
	@Transactional
	public void deleteNormalPaymentDetail(Long id) {
		this.deleteNormalPaymentDetails(id);
		this.deleteNormalPayment(id);
	}


	/**
	 * 逻辑删除日常缴费主记录
	 * @param id
	 */
	public void deleteNormalPayment(Long id) {
		PensionNormalpayment temp=new PensionNormalpayment(); 
		temp.setId(id);
		temp.setCleared(YES);
		pensionNormalpaymentMapper.updateByPrimaryKeySelective(temp);
		
	}


	/**
	 * 获取老人基本信息
	 */
	public PensionNormalpayment selectInfo(Long olderId) {
		List<PensionNormalpayment> list=pensionNormalpaymentMapper.selectInfo(olderId);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public List<PensionLivingLog> selectLivingLogInfo(Long olderId) {
		return pensionLivingLogMapper.selectLivingLogInfo(olderId);
	}
	

	
	@Transactional
	public List<PensionNormalpaymentdetail> createNormalPayment(List<PensionLivingLog> tempLivingLogs, Date today, Date fixPayDate, Long employeeId, String employeeName) throws PmsException {
		/*List<PensionNormalpaymentdetail> normalPaymentDetails=new ArrayList<PensionNormalpaymentdetail>();
		//List<PensionLivingLog> livingLogs=new ArrayList<PensionLivingLog>();
	//	Map<String,List> returnMap = new HashMap<String, List>();
	//	returnMap.put("NORMALPAYMENTDETAILS", normalPaymentDetails);
		//returnMap.put("LIVINGLOGS",livingLogs);
		for(PensionLivingLog livingLog: tempLivingLogs){
			Date updateTimeNew=livingLog.getUpdateTimeNew();
			Date payToDate=livingLog.getPayToDate();
			Date updateDate=livingLog.getUpdateTime();
			Float number=null;
			if(updateTimeNew==null){
				if(payToDate==null){//从该床位换走的日期为空，缴费到日期为空，即从换到该床位的日期算起
					number=this.culDisDate(updateDate, fixPayDate);
					PensionNormalpaymentdetail nursePaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getNursePurse(),livingLog.getNurseItemPurseName(), livingLog.getNurseItemPurseId(),number, livingLog.getOlderId(), employeeId, employeeName,updateDate, fixPayDate);
					PensionNormalpaymentdetail bedPaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getBedPurse(),livingLog.getBedItemPurseName(), livingLog.getBedItemPurseId(), number,livingLog.getOlderId(), employeeId, employeeName,updateDate, fixPayDate);
					normalPaymentDetails.add(bedPaymentDetail);
					normalPaymentDetails.add(nursePaymentDetail);
//					livingLog.setThisMonths(number);
//					livingLog.setThisTotalFees(number*(livingLog.getBedPurse()+livingLog.getNursePurse()));
				}else{//从该床位换走的日期不为空，缴费到日期不为空，即从缴费到的日期开始算
					number=this.culDisDate(payToDate, fixPayDate);
					PensionNormalpaymentdetail nursePaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getNursePurse(),livingLog.getNurseItemPurseName(), livingLog.getNurseItemPurseId(),number, livingLog.getOlderId(), employeeId, employeeName,payToDate, fixPayDate);
					PensionNormalpaymentdetail bedPaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getBedPurse(),livingLog.getBedItemPurseName(), livingLog.getBedItemPurseId(), number,livingLog.getOlderId(), employeeId, employeeName,payToDate, fixPayDate);
					normalPaymentDetails.add(bedPaymentDetail);
					normalPaymentDetails.add(nursePaymentDetail);
//					livingLog.setThisMonths(number);
//					livingLog.setThisTotalFees(number*(livingLog.getBedPurse()+livingLog.getNursePurse()));
				}
//				number=this.culDisDate(updateDate, fixPayDate);
//				livingLog.setTotaldays(number);
//				livingLog.setPayToDate(fixPayDate);
//				livingLog.setTotalfees(number*(livingLog.getBedPurse()+livingLog.getNursePurse()));
			}else if(this.createCalendar(updateTimeNew).equals(this.createCalendar(updateDate))){
				//如果某人在一个床位上待了不到一天，如果已缴费，本床位上的缴费全退，付款天数为0，付款为0
				if(payToDate!=null){
					number=this.culDisDate(updateDate, payToDate);
					PensionNormalpaymentdetail nursePaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getNursePurse(),livingLog.getNurseItemPurseName(), livingLog.getNurseItemPurseId(),-number, livingLog.getOlderId(), employeeId, employeeName,updateDate, payToDate);
					PensionNormalpaymentdetail bedPaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getBedPurse(),livingLog.getBedItemPurseName(), livingLog.getBedItemPurseId(), -number,livingLog.getOlderId(), employeeId, employeeName,updateDate, payToDate);
					normalPaymentDetails.add(bedPaymentDetail);
					normalPaymentDetails.add(nursePaymentDetail);
//					livingLog.setThisMonths(-number);
//					livingLog.setThisTotalFees(-number*(livingLog.getBedPurse()+livingLog.getNursePurse()));
				}
//				livingLog.setTotaldays(new Float(0));
//				livingLog.setPayToDate(updateTimeNew);
//				livingLog.setTotalfees(new Float(0));
			}else{
				if(payToDate==null){//如果当前老人已从该床位换走，并且缴费日期为空，即从换到该床位的日期算到换走的日期
					number=this.culDisDate(updateDate, updateTimeNew);
					PensionNormalpaymentdetail nursePaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getNursePurse(),livingLog.getNurseItemPurseName(), livingLog.getNurseItemPurseId(),number, livingLog.getOlderId(), employeeId, employeeName,updateDate, updateTimeNew);
					PensionNormalpaymentdetail bedPaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getBedPurse(),livingLog.getBedItemPurseName(), livingLog.getBedItemPurseId(), number,livingLog.getOlderId(), employeeId, employeeName,updateDate, updateTimeNew);
					normalPaymentDetails.add(bedPaymentDetail);
					normalPaymentDetails.add(nursePaymentDetail);
//					livingLog.setThisMonths(number);
//					livingLog.setThisTotalFees(number*(livingLog.getBedPurse()+livingLog.getNursePurse()));
				}else if(!this.createCalendar(updateTimeNew).equals(this.createCalendar(payToDate))){//如果缴费到的日期跟换走的日期不一致，需要补缴获退费
					if( payToDate.after(updateTimeNew)){//如果缴费到的日期在换走的日期之后，退从换走的日期到缴费到的日期的费用
						number=this.culDisDate(updateTimeNew, payToDate);
						PensionNormalpaymentdetail nursePaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getNursePurse(),livingLog.getNurseItemPurseName(), livingLog.getNurseItemPurseId(),-number, livingLog.getOlderId(), employeeId, employeeName,updateTimeNew, payToDate);
						PensionNormalpaymentdetail bedPaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getBedPurse(),livingLog.getBedItemPurseName(), livingLog.getBedItemPurseId(), -number,livingLog.getOlderId(), employeeId, employeeName,updateTimeNew, payToDate);
						normalPaymentDetails.add(bedPaymentDetail);
						normalPaymentDetails.add(nursePaymentDetail);
//						livingLog.setThisMonths(-number);
//						livingLog.setThisTotalFees(-number*(livingLog.getBedPurse()+livingLog.getNursePurse()));
					}else{//如果缴费到的日期在换走的日期之前，补缴从缴费到日期到换走日期的费用
						number=this.culDisDate(payToDate, updateTimeNew);
						PensionNormalpaymentdetail nursePaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getNursePurse(),livingLog.getNurseItemPurseName(), livingLog.getNurseItemPurseId(),number, livingLog.getOlderId(), employeeId, employeeName,payToDate, updateTimeNew);
						PensionNormalpaymentdetail bedPaymentDetail=this.createPensionNormalPaymentDetail(livingLog.getId(),today, livingLog.getBedPurse(),livingLog.getBedItemPurseName(), livingLog.getBedItemPurseId(), number,livingLog.getOlderId(), employeeId, employeeName,payToDate, updateTimeNew);
						normalPaymentDetails.add(bedPaymentDetail);
						normalPaymentDetails.add(nursePaymentDetail);
//						livingLog.setThisMonths(number);
//						livingLog.setThisTotalFees(number*(livingLog.getBedPurse()+livingLog.getNursePurse()));
					}
				}
//				number=this.culDisDate(updateDate, updateTimeNew);
//				livingLog.setIsclose(YES);
//				livingLog.setTotaldays(number);
//				livingLog.setPayToDate(updateTimeNew);
//				livingLog.setTotalfees(number*(livingLog.getBedPurse()+livingLog.getNursePurse()));
			}
//			livingLogs.add(livingLog);
		}
		return normalPaymentDetails;*/
		return null;
	}


	/**
	 * 
	 * @Title: disMonth
	 * @Description: 两个日期之间相差的月数 和所余天数/30
	 * @param @param date1 before date2
	 * @author mary.liu
	 */
	public Float culDisDate(Date date1, Date date2,boolean naturalMonthFlag) {
		if(naturalMonthFlag){
			//自然月算法--1/1-1/31 是一个月；1/15-3/4 是 1.7个月 掐头去尾中间留整
			return CauculateMonthUtil.cauculateDiffDay(date1, date2);
		}else{//非自然月算法 -- 1/1-2/1 是一个月；1/15-3/4是 1.57个月 先留整，再算余天
			CauculateMonthUtil dateCalculate=CauculateMonthUtil.calculate(date1,date2);
			Long months=dateCalculate.getDifferenceOfMonths();
			Long days=dateCalculate.getDifferenceOfDays();
			return months+days.floatValue()/MONTH;
		}
	}

	/**
	 * 生成一条 日常缴费记录明细
	 * @param livingLogId
	 * @param today
	 * @param itemPurse
	 * @param itemName
	 * @param itemId
	 * @param number
	 * @param olderId
	 * @param employeeId
	 * @param employeeName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public PensionNormalpaymentdetail createPensionNormalPaymentDetail(Long livingLogId,Date today,Float itemPurse,String itemName,Long itemId,Float number,Long olderId,Long employeeId, String employeeName, Date startDate, Date endDate){
		PensionNormalpaymentdetail dailyPaydetail=new PensionNormalpaymentdetail();
		dailyPaydetail.setGeneratetime(today);
		dailyPaydetail.setItemlfees(itemPurse);
		dailyPaydetail.setItemName(itemName);
		dailyPaydetail.setItemsId(itemId);
		dailyPaydetail.setOlderId(olderId);
		dailyPaydetail.setPayeeId(employeeId);
		dailyPaydetail.setPayeeName(employeeName);
		dailyPaydetail.setNumber(number);
		dailyPaydetail.setTotalfees(dailyPaydetail.getItemlfees()*dailyPaydetail.getNumber());
		dailyPaydetail.setCleared(NO);
		dailyPaydetail.setIspay(NO);
		dailyPaydetail.setSource(NORMALPAYMENT_BEDLEVEL);
		dailyPaydetail.setStartDate(startDate);
		dailyPaydetail.setEndDate(endDate);
		dailyPaydetail.setLivingLogId(livingLogId);
		return dailyPaydetail;
	}
	
	public PensionItempurse selectItemPurse(Long id) {
		return pensionItempurseMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据日常缴费明细列表计算总费用
	 * @param paymentDetails
	 * @return
	 */
	public Float calculateTotalFees( List<PensionNormalpaymentdetail> paymentDetails) {
		Float totalFees=new Float(0);
		for(PensionNormalpaymentdetail paymentDetail: paymentDetails){
			totalFees+=paymentDetail.getTotalfees();
		}
		return totalFees;
	}

	/**
	 * 更新换床记录
	 * @param livingLogs
	 * @param fixPayDate
	 */
	public void saveFixNormalPayment(List<PensionLivingLog> livingLogs, Date fixPayDate) {
		for(PensionLivingLog livingLog: livingLogs){
			pensionLivingLogMapper.updateByPrimaryKeySelective(livingLog);
		}
	}

	public List<PensionNormalpayment> selectNormalPaymentList(Long olderId) {
		return pensionNormalpaymentMapper.selectNormalPaymentList(olderId);
	}
	
	public Long createCalendar(Date date){
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
		Long dateFormat=Long.valueOf(sFormat.format(date));
		return dateFormat;
	}

	public boolean checkDeleteDate(PensionNormalpayment arr) {
		boolean flag=false;
		List<PensionNormalpaymentdetail> normalPaymentDetails=this.selectNormalPaymentDetails(arr.getId());
		for(PensionNormalpaymentdetail normalPaymentDetail:normalPaymentDetails){
			if(NORMALPAYMENT_BEDLEVEL.equals(normalPaymentDetail.getSource())){
				flag=true;
				break;
			}
		}
		return flag;
	}

	public List<PensionNormalpaymentdetail> selectPaidPayment(Long olderId) {
		PensionNormalpaymentdetailExample example=new PensionNormalpaymentdetailExample();
		example.or().andClearedEqualTo(NO)
				.andOlderIdEqualTo(olderId)
				.andSourceEqualTo(NO);//来源--固定缴费 -- 2
		example.setOrderByClause("ID DESC");
		return pensionNormalpaymentdetailMapper.selectByExample(example);
	}

	public List<PensionDictRefundtype> selectRefundTypes() {
		return pensionDictRefundtypeMapper.selectByExample(null);
	}

	public PensionDictRefundtype selectRefundTypeName(Long selectRefundTypeId) {
		return pensionDictRefundtypeMapper.selectByPrimaryKey(selectRefundTypeId);
	}

	/**
	 * 查询老人的床位缴费记录
	 * @param olderId
	 * @return
	 * @throws PmsException 
	 */
	public List<PensionNormalpaymentdetail> selectOlderLivingLogList(Long olderId,Long empId,String empName) throws PmsException{
		PensionLivingLogExample livingLogExample = new PensionLivingLogExample();
		livingLogExample.or().andOlderIdEqualTo(olderId)
			.andIscloseEqualTo(NO);
		List<PensionLivingLog> livingLogList = pensionLivingLogMapper.selectByExample(livingLogExample);
		Date today = new Date();
		Calendar cal=Calendar.getInstance();//获取当前日期
		cal.setTime(today);
		cal.add(Calendar.MONTH,1);//月增加1天
		cal.add(Calendar.DAY_OF_MONTH,-1);//日期倒数一日,既得到本月最后一天
		Date fixPayDate = cal.getTime();
		return this.createNormalPayment(livingLogList, today, fixPayDate, empId, empName);
	}
	
	/**
	 * 根据老人编号查询老人常用缴费项
	 * @param olderId 老人编号
	 * @param employee 操作员
	 * @return
	 * @throws PmsException 
	 */
	public List<PensionNormalpaymentdetail> selectOlderItempurseList(Long olderId, PensionEmployee employee,boolean naturalMonthFlag) throws PmsException {
		List<PensionNormalpaymentdetail> normalPaymentDetailList = new ArrayList<PensionNormalpaymentdetail>();
		//查询老人对应的缴费项目
		PensionOlderItempurseExample example = new PensionOlderItempurseExample();
		example.or().andOlderIdEqualTo(olderId)
			.andStopFlagEqualTo(NO)
			.andClearedEqualTo(NO);
		List<PensionOlderItempurse> olderItempurseList = pensionOlderItempurseMapper.selectByExample(example);
		Calendar calender = Calendar.getInstance();
		for(PensionOlderItempurse olderItempurse: olderItempurseList){
			Date paytoDate = null;//缴费到日期
			Date startDate = null;//缴费开始日期
			Date endDate = null;//缴费结束日期
			Float num = null;
			if(naturalMonthFlag){
				//如果该条记录的开始日期和结束日期相同，则是第一次缴费
				if(olderItempurse.getStartdate().equals(olderItempurse.getEnddate())){
					calender.setTime(olderItempurse.getStartdate());
					calender.set(Calendar.DAY_OF_MONTH, calender.get(Calendar.DAY_OF_MONTH) + 1);
					Date calDate = calender.getTime();
					calender.set(Calendar.DAY_OF_MONTH, calender.get(Calendar.DAY_OF_MONTH) - 1);
					startDate = calender.getTime();
					calender.set(Calendar.MONTH, calender.get(Calendar.MONTH) + 1);
					calender.set(Calendar.DAY_OF_MONTH, 0);
					endDate = calender.getTime();
					num = CauculateMonthUtil.cauculateDiffDay(calDate, endDate);
				}else{//如果不是第一次缴费
					//缴费到日期就是上次缴费的结束日期
					paytoDate = olderItempurse.getEnddate();
					//本次缴费开始日期就是缴费到日期+1
					calender.setTime(paytoDate);
					calender.set(Calendar.DAY_OF_MONTH, calender.get(Calendar.DAY_OF_MONTH) + 1);
					Date calDate = calender.getTime();
					startDate = paytoDate;
					//缴费结束日期就是缴费起始日期所在月的最后一天
					calender.set(Calendar.MONTH, calender.get(Calendar.MONTH) + 1);
					calender.set(Calendar.DAY_OF_MONTH, 0);
					endDate = calender.getTime();
					num = CauculateMonthUtil.cauculateDiffDay(calDate, endDate);
				}
			}
			PensionItempurse itemPurse = this.selectItemPurse(olderItempurse.getItempurseId());
			PensionNormalpaymentdetail normalpaymentDetail = new PensionNormalpaymentdetail();
			normalpaymentDetail.setCleared(NO);
			normalpaymentDetail.setEndDate(endDate);
			normalpaymentDetail.setNumber(num);
			normalpaymentDetail.setIspay(NO);
			normalpaymentDetail.setItemlfees(itemPurse.getPurse());
			normalpaymentDetail.setItemName(itemPurse.getItemname());
			normalpaymentDetail.setItemsId(itemPurse.getId());
			normalpaymentDetail.setNotes(itemPurse.getNotes());
			normalpaymentDetail.setOlderId(olderId);
			normalpaymentDetail.setPayeeId(employee.getId());
			normalpaymentDetail.setPayeeName(employee.getName());
//				normalpaymentDetail.setPaytime(new Date());明细生成时间
			normalpaymentDetail.setStartDate(startDate);
			normalpaymentDetail.setSource(YES);//价表中生成的日常缴费项目
			normalpaymentDetail.setTotalfees(normalpaymentDetail.getItemlfees() * normalpaymentDetail.getNumber());
			normalpaymentDetail.setPaytoDate(paytoDate);
			normalPaymentDetailList.add(normalpaymentDetail);
		}
		return normalPaymentDetailList;
	}

	/**
	 * 更新老人常用缴费项目
	 * @param normalPaymentDetails
	 */
	public void updateOlderItempurse(List<PensionNormalpaymentdetail> normalPaymentDetails) {
		if (normalPaymentDetails.size() > 0) {
			Long olderId = normalPaymentDetails.get(0).getOlderId();
			Calendar calendar = Calendar.getInstance();
			for(PensionNormalpaymentdetail normalPaymentDetail:normalPaymentDetails){
				PensionOlderItempurseExample example = new PensionOlderItempurseExample();
				example.or().andOlderIdEqualTo(olderId)
					.andItempurseIdEqualTo(normalPaymentDetail.getItemsId())
					.andStopFlagEqualTo(NO)
					.andClearedEqualTo(NO);
				//如果是退费，则缴费结束日期 是当前缴费到日期的前一天
				Date payEndDate = null;
				if(normalPaymentDetail.getNumber().floatValue() < 0){
					payEndDate = normalPaymentDetail.getStartDate();
				}
				if(pensionOlderItempurseMapper.countByExample(example) > 0){
					PensionOlderItempurse record = new PensionOlderItempurse();
					if(payEndDate != null){
						record.setEnddate(payEndDate);	
					}else{
						record.setEnddate(normalPaymentDetail.getEndDate());
					}
					pensionOlderItempurseMapper.updateByExampleSelective(record, example);
				}else{
					PensionOlderItempurse record = new PensionOlderItempurse();
					record.setOlderId(olderId);
					record.setItempurseId(normalPaymentDetail.getItemsId());
					record.setStartdate(normalPaymentDetail.getStartDate());
					if(payEndDate != null){
						record.setEnddate(payEndDate);	
					}else{
						record.setEnddate(normalPaymentDetail.getEndDate());
					}
					pensionOlderItempurseMapper.insertSelective(record);
				}
			}
		}
	}

	/**
	 * 停缴老人的缴费项目
	 * @param olderId
	 * @param itemsId
	 * @param detailId 
	 */
	public void stopOlderItempurse(Long olderId, Long itemsId, Long detailId) {
		PensionOlderItempurseExample example = new PensionOlderItempurseExample();
		example.or().andClearedEqualTo(NO)
			.andOlderIdEqualTo(olderId)
			.andItempurseIdEqualTo(itemsId)
			.andStopFlagEqualTo(NO);
		PensionOlderItempurse record = new PensionOlderItempurse();
		record.setStopFlag(YES);
		pensionOlderItempurseMapper.updateByExampleSelective(record, example);
		//如果是【修改】时停缴，则将删除缴费明细的该记录
		if(detailId != null){
			PensionNormalpaymentdetail detail = new PensionNormalpaymentdetail();
			detail.setId(detailId);
			detail.setCleared(YES);
			pensionNormalpaymentdetailMapper.updateByPrimaryKeySelective(detail);
		}
	}
	
	/**
	 * 从系统参数中读取是否启用自然月算法
	 */
	public boolean enableNaturalMonth() {
		try{
			String enable = systemConfig.selectProperty("NATURAL_MONTH_ENABLED");
			if(Integer.valueOf(enable) == YES){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
}
