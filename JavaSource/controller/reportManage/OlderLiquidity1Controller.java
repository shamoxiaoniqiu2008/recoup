package controller.reportManage;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import service.reportManage.OlderLiquidity1Service;
import service.reportManage.OlderLiquidityService;
import domain.configureManage.PensionBuilding;
import domain.reportManage.OlderLiquidityReport;


/**
 * 日常缴费录入   author:mary liu
 *
 *
 */

public class OlderLiquidity1Controller implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient OlderLiquidity1Service olderLiquidity1Service;
	

	//
	private Date startDate;
	private Date endDate;
	
	private CartesianChartModel bar;//起始日期 老人在大厦的分布情况
	private Integer legendCols;
	
	private Integer startOlderNum;
	private Integer endOlderNum;
	private Integer newIn;
	private Integer newOut;
	
	private List<OlderLiquidityReport> olderLiquidityReports;
	
	public Integer total;
	
	private final static Integer NEW_IN=2;
	private final static Integer NEW_OUT=3;
	
	
	
	
	@PostConstruct
	public void init(){
		this.initDate();
		this.selectOlderLiquidity();
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

	public void selectOlderLiquidity(){
		if(startDate==null||endDate==null){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请选择开始日期和结束日期！", "请选择开始日期不大于结束日期！"));
			this.initDate();
			return;
		}else if(startDate.after(endDate)){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请选择开始日期不大于结束日期！", "请选择开始日期不大于结束日期！"));
			this.initDate();
			return;
		}else{
			Map<String,Object> olderLiquidityReportMap=olderLiquidity1Service.selectOlderLiquidity(startDate,endDate);
			startOlderNum=(Integer) olderLiquidityReportMap.get("StartOlderNum");
			endOlderNum=(Integer) olderLiquidityReportMap.get("EndOlderNum");
			olderLiquidityReports=(List<OlderLiquidityReport>) olderLiquidityReportMap.get("olderLiquidityReports");
			newIn=this.calCount(olderLiquidityReports,NEW_IN);
			newOut=this.calCount(olderLiquidityReports,NEW_OUT);
			bar=new CartesianChartModel();
			ChartSeries chartSeries1 = new ChartSeries();
			chartSeries1.setLabel("起始老人数");  
			chartSeries1.set("",startOlderNum );
			bar.addSeries(chartSeries1);
			ChartSeries chartSeries2 = new ChartSeries();
			chartSeries2.setLabel("新入住老人数");  
			chartSeries2.set("", newIn);
			bar.addSeries(chartSeries2);
			ChartSeries chartSeries3 = new ChartSeries();
			chartSeries3.setLabel("新出院老人数");  
			chartSeries3.set("", newOut);
			bar.addSeries(chartSeries3);
			ChartSeries chartSeries4 = new ChartSeries();
			chartSeries4.setLabel("结束老人数");  
			chartSeries4.set("",endOlderNum );
			bar.addSeries(chartSeries4);
			int max=Math.max(startOlderNum, Math.max(newIn, Math.max(newOut, endOlderNum)));
			for(int i=0;i<4;i++){
				Float a=(float) (max/4);
				Double b=Math.ceil(max/4);
//				if(a.toString().equals(b.toString()) ){
//					break;
//				}else{
//					max+=1;
//				}
			}
			if(max%6 != 0)
			{
				max = ((int)(max/6)+1)*6;
			}
			legendCols=max;
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("查询完成！", "查询完成！"));
		}
	}

	private Integer calCount(List<OlderLiquidityReport> olderLiquidityReports,
			Integer flag) {
		Integer total=new Integer(0);
		for(OlderLiquidityReport olderLiquidityReport: olderLiquidityReports){
			if(flag == olderLiquidityReport.getFlag()){
				total++;
			}
		}
		return total;
	}

	public void calculateTotal(Integer flag){
		total=new Integer(0);
		for(OlderLiquidityReport olderLiquidityReport:olderLiquidityReports){
			if(olderLiquidityReport.getFlag() == flag){
				total++;
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public OlderLiquidity1Service getOlderLiquidity1Service() {
		return olderLiquidity1Service;
	}

	public void setOlderLiquidity1Service(
			OlderLiquidity1Service olderLiquidity1Service) {
		this.olderLiquidity1Service = olderLiquidity1Service;
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

	public CartesianChartModel getBar() {
		return bar;
	}

	public void setBar(CartesianChartModel bar) {
		this.bar = bar;
	}

	public Integer getLegendCols() {
		return legendCols;
	}

	public void setLegendCols(Integer legendCols) {
		this.legendCols = legendCols;
	}

	public Integer getStartOlderNum() {
		return startOlderNum;
	}

	public void setStartOlderNum(Integer startOlderNum) {
		this.startOlderNum = startOlderNum;
	}

	public Integer getEndOlderNum() {
		return endOlderNum;
	}

	public void setEndOlderNum(Integer endOlderNum) {
		this.endOlderNum = endOlderNum;
	}

	public Integer getNewIn() {
		return newIn;
	}

	public void setNewIn(Integer newIn) {
		this.newIn = newIn;
	}

	public Integer getNewOut() {
		return newOut;
	}

	public void setNewOut(Integer newOut) {
		this.newOut = newOut;
	}

	public List<OlderLiquidityReport> getOlderLiquidityReports() {
		return olderLiquidityReports;
	}

	public void setOlderLiquidityReports(
			List<OlderLiquidityReport> olderLiquidityReports) {
		this.olderLiquidityReports = olderLiquidityReports;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}

