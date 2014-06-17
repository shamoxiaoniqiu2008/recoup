/**  
* @Title: ExtendForCostAnalysis.java 
* @Package controller.caterManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-9-9 下午1:44:47 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package controller.caterManage;

import java.io.Serializable;

/** 
 * @ClassName: ExtendForCostAnalysis 
 * @Description: 菜品成本统计扩展类
 * @author Justin.Su
 * @date 2013-9-9 下午1:44:47
 * @version V1.0 
 */
public class ExtendForCostAnalysis implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	* @version V1.0
	*/ 
	
	private static final long serialVersionUID = -1144358481504976267L;
	//菜品ID
	private Long foodMenuId;
	//配料名称
	private String greensName;
	//菜品名称
	private String foodMenuName;
	//配料数量
	private Float greensQty;
	//配料单位
	private String qtyUnit;
	//库存数量
	private Float storageQty;
	//采购量
	private Float purchaseQty;
	//参考价格
	private Float price;
	
	public Long getFoodMenuId() {
		return foodMenuId;
	}
	public void setFoodMenuId(Long foodMenuId) {
		this.foodMenuId = foodMenuId;
	}
	public String getGreensName() {
		return greensName;
	}
	public void setGreensName(String greensName) {
		this.greensName = greensName;
	}
	public String getFoodMenuName() {
		return foodMenuName;
	}
	public void setFoodMenuName(String foodMenuName) {
		this.foodMenuName = foodMenuName;
	}
	public Float getGreensQty() {
		return greensQty;
	}
	public void setGreensQty(Float greensQty) {
		this.greensQty = greensQty;
	}
	public String getQtyUnit() {
		return qtyUnit;
	}
	public void setQtyUnit(String qtyUnit) {
		this.qtyUnit = qtyUnit;
	}
	public Float getStorageQty() {
		return storageQty;
	}
	public void setStorageQty(Float storageQty) {
		this.storageQty = storageQty;
	}
	public Float getPurchaseQty() {
		return purchaseQty;
	}
	public void setPurchaseQty(Float purchaseQty) {
		this.purchaseQty = purchaseQty;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	
}
