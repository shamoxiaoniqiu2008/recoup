package service.financeManage;

import domain.financeManage.PensionTemppayment;

public class TempPayDomain extends PensionTemppayment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int number;
	private String itemName;
	private float itemlFees;
	
	public void setItemFees(float itemFees) {
		this.itemlFees = itemFees;
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
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNumber() {
		return number;
	}

}
