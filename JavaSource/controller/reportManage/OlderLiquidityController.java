package controller.reportManage;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

import service.reportManage.OlderLiquidityService;
import domain.configureManage.PensionBuilding;


/**
 * 日常缴费录入   author:mary liu
 *
 *
 */

public class OlderLiquidityController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient OlderLiquidityService olderLiquidityService;
	

	//
	private Date startDate;
	private Date endDate;
	
	private CartesianChartModel bar;//起始日期 老人在大厦的分布情况
	private Integer legendCols;
	private List<PensionBuilding> buildings;
	private List<PensionBuilding> startBulidings;
	private List<PensionBuilding> endBulidings;
	
	
	
	
	@PostConstruct
	public void init(){
		this.initDate();
		this.selectOlderLiquidity();
		buildings=olderLiquidityService.selectBuildings();
		PensionBuilding building = new PensionBuilding();
		building.setName("合计");
		buildings.add(building);
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
			startBulidings=olderLiquidityService.selectOlderLiquidityByDate(startDate);
			PensionBuilding buliding1 = new PensionBuilding();
			Integer startNum = new Integer(0);
			for(PensionBuilding building:startBulidings){
				startNum += building.getOldernumber();
			}
			buliding1.setOldernumber(startNum);
			startBulidings.add(buliding1);
			endBulidings=olderLiquidityService.selectOlderLiquidityByDate(endDate);
			PensionBuilding buliding2 = new PensionBuilding();
			Integer endNum = new Integer(0);
			for(PensionBuilding building:endBulidings){
				endNum += building.getOldernumber();
			}
			buliding2.setOldernumber(endNum);
			endBulidings.add(buliding2);
			bar=new CartesianChartModel();
			ChartSeries startChartSeries = new ChartSeries();
			startChartSeries.setLabel("起始时间老人分布情况");  
			for(PensionBuilding startBuliding:startBulidings){
				startChartSeries.set(startBuliding.getName(), startBuliding.getOldernumber());  
			}
			bar.addSeries(startChartSeries);
			
			//查询 得出 结束时间的数据
			ChartSeries endChartSeries = new ChartSeries();
			endChartSeries.setLabel("结束时间老人分布情况");  
			for(PensionBuilding endBuliding:endBulidings){
				endChartSeries.set(endBuliding.getName(), endBuliding.getOldernumber());  
			}
			bar.addSeries(endChartSeries);
			legendCols=olderLiquidityService.getMax(bar.getSeries());
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("查询完成！", "查询完成！"));
		}
	}

	
	
	
	
	
	
	
	
	
	
	

	public OlderLiquidityService getOlderLiquidityService() {
		return olderLiquidityService;
	}




	public void setOlderLiquidityService(OlderLiquidityService olderLiquidityService) {
		this.olderLiquidityService = olderLiquidityService;
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

	public List<PensionBuilding> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<PensionBuilding> buildings) {
		this.buildings = buildings;
	}

	public List<PensionBuilding> getStartBulidings() {
		return startBulidings;
	}

	public void setStartBulidings(List<PensionBuilding> startBulidings) {
		this.startBulidings = startBulidings;
	}

	public List<PensionBuilding> getEndBulidings() {
		return endBulidings;
	}

	public void setEndBulidings(List<PensionBuilding> endBulidings) {
		this.endBulidings = endBulidings;
	}

	
}

