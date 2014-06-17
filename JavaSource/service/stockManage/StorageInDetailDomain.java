package service.stockManage;

import domain.stockManage.PensionStorageinDetail;

public class StorageInDetailDomain extends PensionStorageinDetail {

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
	private Float totalMoney;

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTotalMoney(Float totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Float getTotalMoney() {
		return totalMoney;
	}
}
