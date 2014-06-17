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

import service.reportManage.CapitalFlowService;
import domain.configureManage.PensionBuilding;
import domain.reportManage.CapitalFlowReport;


/**
 * 现金流动情况一览   author:mary liu
 *
 *
 */

public class CapitalFlowController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient CapitalFlowService capitalFlowService;
	
	
	
	//查询
	private Date startDate;
	private Date endDate;
	
	private List<PensionBuilding> buildings;
	private List<CapitalFlowReport> itemPurseReports;//表格显示
	private List<CapitalFlowReport> foodMenuReports;//
	private List<CapitalFlowReport> refundTypeReports;
	private List<CapitalFlowReport> paywayReports;
	
	private PieChartModel itemPursePie;
	private PieChartModel foodMenuPie;
	private PieChartModel refundTypePie;  
	private PieChartModel paywayPie;  
	
	private Float totalIn;
	private Float totalOut;
	
	
	@PostConstruct
	public void init(){
		this.initDate();
		this.initBuilding();
		this.search();
		
	}
	
	public void initBuilding(){
		buildings=capitalFlowService.selectBulidings();
	}
	
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
	/**
	 * 查询
	 */
	public void search(){
		try{
			if(startDate==null||endDate==null){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请选择开始日期和结束日期！", "请选择开始日期不大于结束日期！"));
				this.initDate();
			}else if(startDate.after(endDate)){
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("请选择开始日期不大于结束日期！", "请选择开始日期不大于结束日期！"));
				this.initDate();
			}else{
				Map<String,List<CapitalFlowReport>> capitalFlowMap=capitalFlowService.search(startDate,endDate);
				itemPurseReports=capitalFlowMap.get("ITEMPURSE");
				itemPursePie=new PieChartModel();
				List<CapitalFlowReport> itemPurseTemp=capitalFlowService.packItemPurseReports(itemPurseReports);
				itemPursePie.setData(this.createPiaMap(itemPurseTemp));
				foodMenuReports=capitalFlowMap.get("FOODMENU");
				foodMenuPie=new PieChartModel();
				List<CapitalFlowReport> foodMenuTemp=capitalFlowService.packItemPurseReports(foodMenuReports);
				foodMenuPie.setData(this.createPiaMap(foodMenuTemp));
				refundTypeReports=capitalFlowMap.get("REFUND");
				refundTypePie=new PieChartModel();
				refundTypePie.setData(this.createPiaMap(refundTypeReports));
				paywayReports=capitalFlowMap.get("PAYWAY");
				paywayPie=new PieChartModel();
				paywayPie.setData(this.createPiaMap(paywayReports));
				this.calculateTotal(itemPurseReports,foodMenuReports,refundTypeReports);
				FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("查询完成！", "查询完成！"));
			}
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("查询出错！", "查询出错！"));
		}
	}
	
	
	

	private void calculateTotal(List<CapitalFlowReport> itemPurseReports,
			List<CapitalFlowReport> foodMenuReports,
			List<CapitalFlowReport> refundTypeReports) {
		totalIn=new Float(0);
		totalOut=new Float(0);
		for(CapitalFlowReport itemPurseReport: itemPurseReports){
			totalIn+=itemPurseReport.getTotalFees();
		}
		for(CapitalFlowReport foodMenuReport: foodMenuReports){
			totalIn+=foodMenuReport.getTotalFees();
		}
		for(CapitalFlowReport refundTypeReport: refundTypeReports){
			totalOut+=refundTypeReport.getTotalFees();
		}
		
	}

	/**
	 * 根据报表类生成饼图Map
	 * @param reports
	 * @return
	 */
	public Map<String,Number> createPiaMap(List<CapitalFlowReport> reports){
		 Map<String,Number> map=new HashMap<String,Number>();
		 for(CapitalFlowReport report: reports){
			 if(report.getTotalFees().floatValue() != 0.0){//当总费用不为0时，才在饼状图中显示
				 map.put(report.getItemName(), report.getTotalFees());
			 }
		 }
		 return map;
	}
	
	
	
	
	
	
	
	
	
	

	public CapitalFlowService getCapitalFlowService() {
		return capitalFlowService;
	}

	public void setCapitalFlowService(CapitalFlowService capitalFlowService) {
		this.capitalFlowService = capitalFlowService;
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

	public List<PensionBuilding> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<PensionBuilding> buildings) {
		this.buildings = buildings;
	}

	public List<CapitalFlowReport> getItemPurseReports() {
		return itemPurseReports;
	}

	public void setItemPurseReports(List<CapitalFlowReport> itemPurseReports) {
		this.itemPurseReports = itemPurseReports;
	}

	public List<CapitalFlowReport> getRefundTypeReports() {
		return refundTypeReports;
	}

	public void setRefundTypeReports(List<CapitalFlowReport> refundTypeReports) {
		this.refundTypeReports = refundTypeReports;
	}

	public List<CapitalFlowReport> getPaywayReports() {
		return paywayReports;
	}

	public void setPaywayReports(List<CapitalFlowReport> paywayReports) {
		this.paywayReports = paywayReports;
	}


	public PieChartModel getRefundTypePie() {
		return refundTypePie;
	}

	public void setRefundTypePie(PieChartModel refundTypePie) {
		this.refundTypePie = refundTypePie;
	}

	public PieChartModel getPaywayPie() {
		return paywayPie;
	}

	public void setPaywayPie(PieChartModel paywayPie) {
		this.paywayPie = paywayPie;
	}

	public PieChartModel getItemPursePie() {
		return itemPursePie;
	}

	public void setItemPursePie(PieChartModel itemPursePie) {
		this.itemPursePie = itemPursePie;
	}

	public List<CapitalFlowReport> getFoodMenuReports() {
		return foodMenuReports;
	}

	public void setFoodMenuReports(List<CapitalFlowReport> foodMenuReports) {
		this.foodMenuReports = foodMenuReports;
	}

	public PieChartModel getFoodMenuPie() {
		return foodMenuPie;
	}

	public void setFoodMenuPie(PieChartModel foodMenuPie) {
		this.foodMenuPie = foodMenuPie;
	}

	public Float getTotalIn() {
		return totalIn;
	}

	public void setTotalIn(Float totalIn) {
		this.totalIn = totalIn;
	}

	public Float getTotalOut() {
		return totalOut;
	}

	public void setTotalOut(Float totalOut) {
		this.totalOut = totalOut;
	}

}
