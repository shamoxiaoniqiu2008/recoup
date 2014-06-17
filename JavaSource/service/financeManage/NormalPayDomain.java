package service.financeManage;

import domain.financeManage.PensionNormalpayment;

public class NormalPayDomain extends PensionNormalpayment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private float number;
	private String itemName;
	private float itemlFees;
	
	private String feeCatalog; 
	
	public void setItemFees(float itemlFees) {
		this.itemlFees = itemlFees;
	}
	public float getItemFees() {
		return itemlFees;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setNumber(float number) {
		this.number = number;
	}
	public float getNumber() {
		return number;
	}
	public void setFeeCatalog(String feeCatalog) {
		this.feeCatalog = feeCatalog;
	}
	public String getFeeCatalog() {
		return feeCatalog;
	}
	
	

}
