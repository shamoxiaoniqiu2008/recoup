package controller.stockManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import service.stockManage.StorageOutDetailDomain;
import service.stockManage.SumStorageService;
import util.DateUtil;
import domain.dictionary.PensionDicItemtype;
import domain.stockManage.PensionStorage;

public class SumStorageController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private transient SumStorageService sumStorageService;
	/**
	 * 消耗量统计记录
	 */
	private List<StorageOutDetailDomain> storageOutRecordList = new ArrayList<StorageOutDetailDomain>();

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
	private List<PensionStorage> remainRecords = new ArrayList<PensionStorage>();
	/**
	 * 被查询剩余量的材料Id
	 */
	private Long remainItemId;
	/**
	 * 绑定材料信息的输入法
	 */
	private String goodsSql;
	private String remainGoodsSql;
	// 消耗量查询条件
	private String itemType;
	private Long typeId;
	// 物资类型类表
	private List<PensionDicItemtype> itemTypeList;

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
		initTypeList();
		consumptionEndDate = new Date();
		consumptionStartDate = DateUtil
				.getBeforeMonthDay(consumptionEndDate, 1);
		selectStorageoutRecords();
		selectRemainRecords();
	}

	/**
	 * 初始化前台输入法的sql
	 */
	public void initSQL() {
		goodsSql = "select pc.id,pc.item_name,pc.input_code,pc.type_id,"
				+ "pt.item_name as typeName from pension_item_catalog pc,"
				+ "pension_dic_itemtype pt where pc.type_id=pt.id and pc.cleared=2";
		remainGoodsSql = "select pc.id,pc.item_name,pc.input_code,pc.type_id,"
				+ "pt.item_name as typeName from pension_item_catalog pc,"
				+ "pension_dic_itemtype pt where pc.type_id=pt.id and pc.cleared=2";
	}

	private void initTypeList() {
		itemTypeList = sumStorageService.selectItemType();
		PensionDicItemtype type = new PensionDicItemtype();
		type.setId(0L);
		type.setItemName("全部");
		itemTypeList.add(0, type);
	}

	/**
	 * sql根据物资类型改变
	 */
	public void changeSql() {
		if (typeId != 0L) {
			goodsSql = "select pc.id,pc.item_name,pc.input_code,pc.type_id,"
					+ "pt.item_name as typeName from pension_item_catalog pc,"
					+ "pension_dic_itemtype pt where pc.type_id=pt.id and pc.cleared=2  "
					+ "and pc.type_id=" + typeId;
		} else {
			goodsSql = "select pc.id,pc.item_name,pc.input_code,pc.type_id,"
					+ "pt.item_name as typeName from pension_item_catalog pc,"
					+ "pension_dic_itemtype pt where pc.type_id=pt.id and pc.cleared=2";
		}
	}

	/**
	 * 根据查询条件 查询对应的消耗量
	 */
	public void selectStorageoutRecords() {
		storageOutRecordList = sumStorageService.selectStorageoutRecords(
				consumptionStartDate, consumptionEndDate, consumptionItemId,
				typeId);
	}

	/**
	 * 根据查询条件查询出对应的剩余量
	 */
	public void selectRemainRecords() {
		remainRecords = sumStorageService.selectRemainRecords(remainItemId);
	}

	/**
	 * 清空查询消耗量条件
	 */
	public void clearConsumptionSelectForm() {
		consumptionStartDate = null;
		consumptionEndDate = null;
		consumptionItemId = null;
		typeId = 0L;
	}

	public void setRemainRecords(List<PensionStorage> remainRecords) {
		this.remainRecords = remainRecords;
	}

	public List<PensionStorage> getRemainRecords() {
		return remainRecords;
	}

	public void setGoodsSql(String goodsSql) {
		this.goodsSql = goodsSql;
	}

	public String getGoodsSql() {
		return goodsSql;
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

	public void setItemTypeList(List<PensionDicItemtype> itemTypeList) {
		this.itemTypeList = itemTypeList;
	}

	public List<PensionDicItemtype> getItemTypeList() {
		return itemTypeList;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemType() {
		return itemType;
	}

	public Long getRemainItemId() {
		return remainItemId;
	}

	public void setRemainItemId(Long remainItemId) {
		this.remainItemId = remainItemId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setStorageOutRecordLiat(
			List<StorageOutDetailDomain> storageOutRecordList) {
		this.storageOutRecordList = storageOutRecordList;
	}

	public List<StorageOutDetailDomain> getStorageOutRecordList() {
		return storageOutRecordList;
	}

	public SumStorageService getSumStorageService() {
		return sumStorageService;
	}

	public void setSumStorageService(SumStorageService sumStorageService) {
		this.sumStorageService = sumStorageService;
	}

	public void setStorageOutRecordList(
			List<StorageOutDetailDomain> storageOutRecordList) {
		this.storageOutRecordList = storageOutRecordList;
	}

	public void setRemainGoodsSql(String remainGoodsSql) {
		this.remainGoodsSql = remainGoodsSql;
	}

	public String getRemainGoodsSql() {
		return remainGoodsSql;
	}

}
