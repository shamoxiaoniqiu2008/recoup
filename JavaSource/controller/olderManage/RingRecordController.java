package controller.olderManage;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.olderManage.RingService;
import domain.olderManage.PensionRingrecord;

public class RingRecordController  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient RingService ringService;
	
	private Date startDate;
	
	private Date endDate;
	
	private Integer ringstatus;
	
	
	private List<PensionRingrecord> ringRecords;
	
	private PensionRingrecord selectRingRecord;
	
	
	@PostConstruct
	public void init(){
		ringstatus=1;
		this.search();
	}
	
	public void search(){
		ringRecords=ringService.search(startDate,endDate,ringstatus);
	}
	
	public void clearSearchForm(){
		startDate=null;
		endDate=null;
		ringstatus=null;
	}
	
	/**
	 * check 开始日期要不大于结束日期
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSearchDate(FacesContext context,UIComponent component, Object obj){
		Calendar  startDate = (Calendar) component.findComponent("searchForm:startDate");
		Date start=(Date)startDate.getValue();
		Calendar  endDate = (Calendar) component.findComponent("searchForm:endDate");
		Date end=(Date)endDate.getValue();
		if(start!=null && end!=null && start.after(end)){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"日期时间应该不大于结束日期！","日期时间应该不大于结束日期！");
			throw new ValidatorException(message);
		}
	}

	public void onRingRecordSelect(SelectEvent e){
		
	}
	
	public void onRingRecordUnselect(UnselectEvent e){
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public RingService getRingService() {
		return ringService;
	}

	public void setRingService(RingService ringService) {
		this.ringService = ringService;
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

	public Integer getRingstatus() {
		return ringstatus;
	}

	public void setRingstatus(Integer ringstatus) {
		this.ringstatus = ringstatus;
	}

	public List<PensionRingrecord> getRingRecords() {
		return ringRecords;
	}

	public void setRingRecords(List<PensionRingrecord> ringRecords) {
		this.ringRecords = ringRecords;
	}

	public PensionRingrecord getSelectRingRecord() {
		return selectRingRecord;
	}

	public void setSelectRingRecord(PensionRingrecord selectRingRecord) {
		this.selectRingRecord = selectRingRecord;
	}
	
	
}
