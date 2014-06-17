package controller.reportManage;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import service.reportManage.AccidentReportService;
import domain.olderManage.PensionAccidentRecord;


/**
 * 日常缴费录入   author:mary liu
 *
 *
 */

public class AccidentReportController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient AccidentReportService accidentReportService;
	

	//
	private Date startDate;
	private Date endDate;
	private List<PensionAccidentRecord> accidentRecords;
	private Integer total;
	private String sortClass;
	

	
	
	
	@PostConstruct
	public void init(){
		this.initDate();
		sortClass="olderName";
		this.selectaccidentReports();
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
		calendar.set(Calendar.SECOND, 59);
		endDate=calendar.getTime();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate=calendar.getTime();
  
	}

	public void selectaccidentReports() {
		if(startDate==null||endDate==null){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请选择开始日期和结束日期！", "请选择开始日期不大于结束日期！"));
			this.initDate();
			return;
		}else if(startDate.after(endDate)){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请选择开始日期不大于结束日期！", "请选择开始日期不大于结束日期！"));
			this.initDate();
			return;
		}else{
			accidentRecords=accidentReportService.selectAccidentRecordByDate(startDate, endDate);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("查询完成！", "查询完成！"));
		}
		
	}
	
	public void calculateTotalNum(Long id){
		total=new Integer(0);
		if(sortClass.equals("olderName")){
			for(PensionAccidentRecord accidentRecord: accidentRecords){
				if(accidentRecord.getOlderId().equals(id))
				total++;
			}
		}else if(sortClass.equals("accidentType")){
			for(PensionAccidentRecord accidentRecord: accidentRecords){
				if(accidentRecord.getAccidenttypeId().equals(id))
				total++;
			}
		}else if(sortClass.equals("dutynurserName")){
			for(PensionAccidentRecord accidentRecord: accidentRecords){
				if(accidentRecord.getDutynurserId().equals(id))
				total++;
			}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public AccidentReportService getAccidentReportService() {
		return accidentReportService;
	}

	public void setAccidentReportService(AccidentReportService accidentReportService) {
		this.accidentReportService = accidentReportService;
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

	public List<PensionAccidentRecord> getAccidentRecords() {
		return accidentRecords;
	}

	public void setAccidentRecords(List<PensionAccidentRecord> accidentRecords) {
		this.accidentRecords = accidentRecords;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getSortClass() {
		return sortClass;
	}

	public void setSortClass(String sortClass) {
		this.sortClass = sortClass;
	}

	
	
}

