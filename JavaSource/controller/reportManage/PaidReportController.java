package controller.reportManage;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.component.datatable.DataTable;

import service.reportManage.PaidReportService;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.reportManage.PaymentReport;


/**
 * 老人缴费情况一览   author:mary liu
 *
 *
 */

public class PaidReportController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient PaidReportService paidReportService;
	

	//
	private Date startDate;
	private Date endDate;
	private List<PaymentReport> paidReports;
	private List<PaymentReport> detailReports;
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
			paidReports=paidReportService.selectPaidReport(startDate,endDate);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("查询完成！", "查询完成！"));
		}
	}
	
	public void selectOlderPaidRecord(Long olderId){
		detailReports = paidReportService.selectPaidReportDetail(startDate,endDate,olderId);
	}
	
	public void calculateTotal(Long id,Integer source) {
		total = new Float(0);
        for(PaymentReport paidReport: detailReports){
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



	public List<PaymentReport> getPaidReports() {
		return paidReports;
	}

	public void setPaidReports(List<PaymentReport> paidReports) {
		this.paidReports = paidReports;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
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

	public List<PaymentReport> getDetailReports() {
		return detailReports;
	}

	public void setDetailReports(List<PaymentReport> detailReports) {
		this.detailReports = detailReports;
	}

}

