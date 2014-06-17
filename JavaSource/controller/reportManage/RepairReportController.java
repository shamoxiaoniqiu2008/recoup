package controller.reportManage;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.PieChartModel;

import service.logisticsManage.RepairCount;
import service.reportManage.RepairReportService;


/**
 * 日常缴费录入   author:mary liu
 *
 *
 */

public class RepairReportController extends ReportController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient RepairReportService repairReportService;
	

	//
	private Date startDate;
	private Date endDate;
	private List<RepairCount> repairRecords;
	private Integer total;
	private String sortClass;
	
	private PieChartModel repairPie;
	

	
	
	
	@PostConstruct
	public void init(){
		this.initDate();
		sortClass="buildName";
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

	public void selectRepairReports() {
		if(startDate==null||endDate==null){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请选择开始日期和结束日期！", "请选择开始日期不大于结束日期！"));
			this.initDate();
			return;
		}else if(startDate.after(endDate)){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请选择开始日期不大于结束日期！", "请选择开始日期不大于结束日期！"));
			this.initDate();
			return;
		}else{
			repairRecords = repairReportService.selectAccidentRecordByDate(startDate, endDate,null);
			this.createPia();
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("查询完成！", "查询完成！"));
		}
		
	}
	
	public void calculateTotalNum(String sortType){
		total=new Integer(0);
		if(sortClass.equals("buildName")){
			for(RepairCount repairRecord: repairRecords){
				if(repairRecord.getBuildName().equals(sortType))
				total+=repairRecord.getRepairCount();
			}
		}else if(sortClass.equals("floorName")){
			for(RepairCount repairRecord: repairRecords){
				if(repairRecord.getFloorName().equals(sortType))
					total+=repairRecord.getRepairCount();
			}
		}
	}

	/**
	 * 根据报表类生成饼图Map
	 * @param reports
	 * @return
	 */
	public void createPia(){
		repairPie= new PieChartModel();
		Map<String,Number> map=new HashMap<String,Number>();
		List<RepairCount> temps=repairReportService.selectAccidentRecordByDate(startDate, endDate,sortClass);
		if(sortClass.equals("buildName")){
			for(RepairCount repairRecord: temps){
				map.put(repairRecord.getBuildName(),repairRecord.getRepairCount());
			}
		}else if(sortClass.equals("floorName")){
			for(RepairCount repairRecord: temps){
				map.put(repairRecord.getFloorName(),repairRecord.getRepairCount());
			}
		}
		 repairPie.setData(map);
	}

	/**
	 * 创建饼形图数据
	 */
	@Override
	public void createPieList() {
		Map<String,Float> map=new HashMap<String,Float>();
		List<RepairCount> temps=repairReportService.selectAccidentRecordByDate(startDate, endDate,sortClass);
		if(sortClass.equals("buildName")){
			for(RepairCount repairRecord: temps){
				map.put(repairRecord.getBuildName(),repairRecord.getRepairCount().floatValue());
			}
		}else if(sortClass.equals("floorName")){
			for(RepairCount repairRecord: temps){
				map.put(repairRecord.getFloorName(),repairRecord.getRepairCount().floatValue());
			}
		}
		super.setPieMap(map);
	}

	@Override
	public void createColumnList() {
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public RepairReportService getRepairReportService() {
		return repairReportService;
	}

	public void setRepairReportService(RepairReportService repairReportService) {
		this.repairReportService = repairReportService;
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

	public List<RepairCount> getRepairRecords() {
		return repairRecords;
	}

	public void setRepairRecords(List<RepairCount> repairRecords) {
		this.repairRecords = repairRecords;
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

	public PieChartModel getRepairPie() {
		return repairPie;
	}

	public void setRepairPie(PieChartModel repairPie) {
		this.repairPie = repairPie;
	}


}

