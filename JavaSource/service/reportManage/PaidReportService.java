package service.reportManage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.financeManage.PensionNormalpaymentMapper;
import domain.reportManage.PaymentReport;

/**

 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class PaidReportService {
	
	
	@Autowired
	private PensionNormalpaymentMapper pensionNormalpaymentMapper;
	
	
	
	
	/**
	 * 按截止日期查询老人缴费记录
	 * @param startDate
	 * @param endDate 
	 * @return
	 */
	public List<PaymentReport> selectPaidReport(Date startDate, Date endDate) {
		return pensionNormalpaymentMapper.selectPaidReportMain(startDate,endDate);
	}
	
	/**
	 * 按截止日期查询老人缴费记录
	 * @param startDate
	 * @param endDate 
	 * @return
	 */
	public List<PaymentReport> selectPaidReportDetail(Date startDate, Date endDate,Long olderId) {
		return pensionNormalpaymentMapper.selectPaidReport(startDate,endDate,olderId);
	}
	
	/**
	 * 按截止日期查询老人欠费记录
	 * @param deadLine
	 * @param endDate 
	 * @return
	 */
	public List<PaymentReport> selectUnpaidReportMain(Date startDate, Date endDate) {
		return pensionNormalpaymentMapper.selectUnpaidReportMain(startDate,endDate);
	}
	
	/**
	 * 按截止日期查询老人欠费记录
	 * @param deadLine
	 * @param endDate 
	 * @return
	 */
	public List<PaymentReport> selectUnpaidReport(Date startDate, Date endDate,Long olderId) {
		return pensionNormalpaymentMapper.selectUnpaidReport(startDate,endDate,olderId);
	}
	


}
