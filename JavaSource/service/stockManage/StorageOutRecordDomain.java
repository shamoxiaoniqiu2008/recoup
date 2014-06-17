package service.stockManage;

import domain.stockManage.PensionStorageoutRecord;

public class StorageOutRecordDomain extends PensionStorageoutRecord {

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
	 * 出库类型
	 */
	private String storageoutTypeName;

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

	public void setStorageoutTypeName(String storageoutTypeName) {
		this.storageoutTypeName = storageoutTypeName;
	}

	public String getStorageoutTypeName() {
		return storageoutTypeName;
	}

}
