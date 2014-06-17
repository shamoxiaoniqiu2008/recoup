/**  
* @Title: IngredientStorageController.java 
* @Package controller.caterManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-9-9 上午11:12:56 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package controller.caterManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import service.caterManage.IngredientStorageService;
import util.JavaConfig;
import domain.stockManage.PensionStorage;

/** 
 * @ClassName: IngredientStorageController 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-9 上午11:12:56
 * @version V1.0 
 */
public class IngredientStorageController implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	* @version V1.0
	*/ 
	
	private static final long serialVersionUID = 4915299966712695383L;
	
	private transient IngredientStorageService ingredientStorageService;
	
	List<PensionStorage> pensionStorageList = new ArrayList<PensionStorage>();
	
	//获取食材库存类别ID
	String itemTypeId = null;
	//食材名称输入法
	private String itemNameToSql;
	private String fitcolItemName;
	private String displaycolItemName;
	
	private String itemName;
	private Long itemId;
	private boolean zeroStorageFlag;
	
	@PostConstruct
	public void init(){
		itemTypeId = JavaConfig.fetchProperty("IngredientStorageController.itemTypeId");
//		pensionStorageList = ingredientStorageService.getAllStorage(Long.valueOf(itemTypeId));
		zeroStorageFlag = false;
		this.getRecordByCondition();
		initItemNameToSql();
	}
	
	public void initItemNameToSql() {
		itemNameToSql = " select"
						+ " ps.item_id as itemId"
						+"  ,ps.item_name as itemName"
						+"  ,pic.input_code as inputCode"
					+" from"
						+" pension_item_catalog pic"
					+" inner join"
						+" pension_storage ps"
					+" on"
						+" pic.id = ps.item_id"
					+" where ps.type_id ="+Long.valueOf(itemTypeId);
		fitcolItemName = "inputCode";
		displaycolItemName = "编号:0:hide,名称:1,输入码:2";
	}
	
	/**
	 * 
	* @Title: getRecordByCondition 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-11-5 下午5:41:32
	* @version V1.0
	 */
	public void getRecordByCondition(){
		if (itemName == null || "".equals(itemName.trim())) {
			itemId = null;
		}
		pensionStorageList = ingredientStorageService.getStorageByCondition(itemId, zeroStorageFlag,Long.valueOf(itemTypeId));
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"查询完毕！", ""));
	}
	
	/**
	 * 
	* @Title: initSearchPara 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-12-3 下午3:05:22
	* @version V1.0
	 */
	public void initSearchPara(){
		itemId = null;
		itemName = null;
		zeroStorageFlag = false;
	}


	/**
	 * @return the pensionStorageList
	 */
	public List<PensionStorage> getPensionStorageList() {
		return pensionStorageList;
	}


	/**
	 * @param pensionStorageList the pensionStorageList to set
	 */
	public void setPensionStorageList(List<PensionStorage> pensionStorageList) {
		this.pensionStorageList = pensionStorageList;
	}


	/**
	 * @return the itemTypeId
	 */
	public String getItemTypeId() {
		return itemTypeId;
	}


	/**
	 * @param itemTypeId the itemTypeId to set
	 */
	public void setItemTypeId(String itemTypeId) {
		this.itemTypeId = itemTypeId;
	}






	/**
	 * @return the ingredientStorageService
	 */
	public IngredientStorageService getIngredientStorageService() {
		return ingredientStorageService;
	}






	/**
	 * @param ingredientStorageService the ingredientStorageService to set
	 */
	public void setIngredientStorageService(
			IngredientStorageService ingredientStorageService) {
		this.ingredientStorageService = ingredientStorageService;
	}

	/**
	 * @return the itemNameToSql
	 */
	public String getItemNameToSql() {
		return itemNameToSql;
	}

	/**
	 * @param itemNameToSql the itemNameToSql to set
	 */
	public void setItemNameToSql(String itemNameToSql) {
		this.itemNameToSql = itemNameToSql;
	}

	/**
	 * @return the fitcolItemName
	 */
	public String getFitcolItemName() {
		return fitcolItemName;
	}

	/**
	 * @param fitcolItemName the fitcolItemName to set
	 */
	public void setFitcolItemName(String fitcolItemName) {
		this.fitcolItemName = fitcolItemName;
	}

	/**
	 * @return the displaycolItemName
	 */
	public String getDisplaycolItemName() {
		return displaycolItemName;
	}

	/**
	 * @param displaycolItemName the displaycolItemName to set
	 */
	public void setDisplaycolItemName(String displaycolItemName) {
		this.displaycolItemName = displaycolItemName;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemId
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the zeroStorageFlag
	 */
	public boolean isZeroStorageFlag() {
		return zeroStorageFlag;
	}

	/**
	 * @param zeroStorageFlag the zeroStorageFlag to set
	 */
	public void setZeroStorageFlag(boolean zeroStorageFlag) {
		this.zeroStorageFlag = zeroStorageFlag;
	}


}
