package controller.reportManage;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import service.reportManage.PaidReportService;
import domain.reportManage.PaymentReport;


/**
 * 日常缴费录入   author:mary liu
 *
 *
 */

public class UnpaidReportController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient PaidReportService paidReportService;
	

	//
	private Date startDate;
	private Date endDate;
	private List<PaymentReport> unpaidReports;
	private List<PaymentReport> unpaidReportDetails;
	private Float total;
	
	
	
	
	
	
	@PostConstruct
	public void init(){
		this.initDate();
		this.selectPaidReport();
	}
	
	/**
	 * 将结束日期设置为今天，起始日期设置为一个月前的今天
	 */
	public void initDate(){
		
		Calendar calendar=Calendar.getInstance();
		endDate=new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		endDate=calendar.getTime();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate=calendar.getTime();
  
	}
	
	public void selectPaidReport(){
		if(startDate==null||endDate==null){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请选择开始日期和结束日期！", "请选择开始日期不大于结束日期！"));
			this.initDate();
			return;
		}else if(startDate.after(endDate)){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请选择开始日期不大于结束日期！", "请选择开始日期不大于结束日期！"));
			this.initDate();
			return;
		}else{
			unpaidReports=paidReportService.selectUnpaidReportMain(startDate,endDate);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("查询完成！", "查询完成！"));
		}
	}
	
	public void selectUnpaidRecordDetail(Long olderId){
		unpaidReportDetails = paidReportService.selectUnpaidReport(startDate, endDate, olderId);
	}
	
	public void calculateTotal(Long id,Integer source) {
        total = new Float(0);
        for(PaymentReport paidReport: unpaidReportDetails){
        	if(id.equals(paidReport.getItemId())&&source.equals(paidReport.getSource())){
        		total+=paidReport.getTotalFees();
        	}
        }
    }
	
	
	
	

	public PaidReportService getPaidReportService() {
		return paidReportService;
	}

	public void setPaidReportService(PaidReportService paidReportService) {
		this.paidReportService = paidReportService;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}


	public List<PaymentReport> getUnpaidReports() {
		return unpaidReports;
	}

	public void setUnpaidReports(List<PaymentReport> unpaidReports) {
		this.unpaidReports = unpaidReports;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<PaymentReport> getUnpaidReportDetails() {
		return unpaidReportDetails;
	}

	public void setUnpaidReportDetails(List<PaymentReport> unpaidReportDetails) {
		this.unpaidReportDetails = unpaidReportDetails;
	}

}

