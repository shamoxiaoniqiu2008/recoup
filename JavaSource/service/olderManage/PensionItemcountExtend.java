package service.olderManage;

import domain.olderManage.PensionItemcount;

public class PensionItemcountExtend extends PensionItemcount{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String itemName;

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemName() {
		return itemName;
	}

}
