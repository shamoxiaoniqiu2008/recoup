package service.stockManage;

import domain.stockManage.PensionStorageinRecord;

public class StorageInRecordDomain extends PensionStorageinRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 入库总额
	 */
	private Float totalMoney;
	/**
	 * 操作员
	 */
	private String operatorName;
	/**
	 * 入库类型
	 */
	private String storageinTypeName;

	public void setTotalMoney(Float totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Float getTotalMoney() {
		return totalMoney;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setStorageinTypeName(String storageinTypeName) {
		this.storageinTypeName = storageinTypeName;
	}

	public String getStorageinTypeName() {
		return storageinTypeName;
	}

}
