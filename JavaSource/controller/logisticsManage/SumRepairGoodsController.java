package controller.logisticsManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import domain.logisticsManage.PensionGoodsDetail;

import service.logisticsManage.SumRepairGoodsService;
import util.DateUtil;

public class SumRepairGoodsController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private transient SumRepairGoodsService sumRepairGoodsService;
	/**
	 * 消耗量统计记录
	 */
	private List<PensionGoodsDetail> consumptionRecords = new ArrayList<PensionGoodsDetail>();
	/**
	 * 消耗量起始时间 用作查询条件
	 */
	private Date consumptionStartDate;
	/**
	 * 消耗量截止时间 用作查询条件
	 */
	private Date consumptionEndDate;
	/**
	 * 被查询消耗量的材料Id
	 */
	private Long consumptionItemId;
	/**
	 * 剩余量统计记录
	 */
	private List<PensionGoodsDetail> remainRecords = new ArrayList<PensionGoodsDetail>();
	/**
	 * 剩余量截止时间 用作查询条件
	 */
	private Date remainEndDate;
	/**
	 * 被查询剩余量的材料Id 
	 */
	private Long remainItemId;
	/**
	 * 绑定材料信息的输入法
	 */
	private String goodsSql;
	/**
	 * @Description:初始化数据方法.
	 * @return: void
	 * @exception:
	 * @throws:
	 * @version: 1.0
	 * @author: Tim li
	 */
	@PostConstruct
	public void init() {
		initSQL();
		consumptionStartDate = DateUtil.parseDate(DateUtil.getMonthBegin(DateUtil.formatDate(new Date())));  
		consumptionEndDate = new Date();
		remainEndDate = DateUtil.parseDate(DateUtil.getMonthBegin(DateUtil.formatDate(new Date())));
		selectConsumptionRecords();
		selectRemainRecords();
		
	}
	/**
	 * 初始化前台输入法的sql
	 */
	public void initSQL()
	{
		goodsSql = "select ps.item_id     as itemId," +
		"ps.item_name   as itemName," +
		"ps.storage_qty as itemQty " +
		"from pension_storage ps " +
		"where ps.type_id = 2";//类型为2 维修材料
	}
	/**
	 * 根据查询条件 查询对应的消耗量
	 */
	public void selectConsumptionRecords()
	{
		setConsumptionRecords(sumRepairGoodsService.selectConsumptionRecords(consumptionStartDate,consumptionEndDate,consumptionItemId));
	}
	/**
	 * 根据查询条件查询出对应的剩余量
	 */
	public void selectRemainRecords(){
		
		setRemainRecords(sumRepairGoodsService.selectRemainRecords(remainEndDate,remainItemId));
	}
	/**
	 * 清空查询剩余量条件
	 */
	public void clearConsumptionSelectForm(){
		consumptionStartDate = null;
		consumptionEndDate = null;
		consumptionItemId = null;
	}
	public void clearRemainSelectForm(){
		remainEndDate = null;
		remainItemId = null;
	}
	public void setConsumptionRecords(List<PensionGoodsDetail> consumptionRecords) {
		this.consumptionRecords = consumptionRecords;
	}

	public List<PensionGoodsDetail> getConsumptionRecords() {
		return consumptionRecords;
	}

	public void setRemainRecords(List<PensionGoodsDetail> remainRecords) {
		this.remainRecords = remainRecords;
	}

	public List<PensionGoodsDetail> getRemainRecords() {
		return remainRecords;
	}

	public void setGoodsSql(String goodsSql) {
		this.goodsSql = goodsSql;
	}

	public String getGoodsSql() {
		return goodsSql;
	}
	
	public SumRepairGoodsService getSumRepairGoodsService() {
		return sumRepairGoodsService;
	}

	public void setSumRepairGoodsService(SumRepairGoodsService sumRepairGoodsService) {
		this.sumRepairGoodsService = sumRepairGoodsService;
	}

	public Date getConsumptionStartDate() {
		return consumptionStartDate;
	}

	public void setConsumptionStartDate(Date consumptionStartDate) {
		this.consumptionStartDate = consumptionStartDate;
	}

	public Date getConsumptionEndDate() {
		return consumptionEndDate;
	}

	public void setConsumptionEndDate(Date consumptionEndDate) {
		this.consumptionEndDate = consumptionEndDate;
	}

	public Long getConsumptionItemId() {
		return consumptionItemId;
	}

	public void setConsumptionItemId(Long consumptionItemId) {
		this.consumptionItemId = consumptionItemId;
	}

	public Date getRemainEndDate() {
		return remainEndDate;
	}

	public void setRemainEndDate(Date remainEndDate) {
		this.remainEndDate = remainEndDate;
	}

	public Long getRemainItemId() {
		return remainItemId;
	}

	public void setRemainItemId(Long remainItemId) {
		this.remainItemId = remainItemId;
	}

}
