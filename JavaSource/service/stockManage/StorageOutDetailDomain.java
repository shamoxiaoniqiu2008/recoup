package service.stockManage;

import domain.stockManage.PensionStorageoutDetail;

public class StorageOutDetailDomain extends PensionStorageoutDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 物资类型
	 */
	private String typeName;
	/**
	 * 物资总额
	 */
	private float totalMoney;

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTotalMoney(float totalMoney) {
		this.totalMoney = totalMoney;
	}

	public float getTotalMoney() {
		return totalMoney;
	}

}
