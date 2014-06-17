package service.olderManage;

import domain.nurseManage.PensionDispenseOlder;

public class DispenseDomain extends PensionDispenseOlder {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean validF;
	private String deliveryName;
	private String chargerName;
	private String nursedeptName;

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setValidF(boolean validF) {
		this.validF = validF;
	}

	public boolean getValidF() {
		return validF;
	}

	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}

	public String getChargerName() {
		return chargerName;
	}

	public void setNursedeptName(String nursedeptName) {
		this.nursedeptName = nursedeptName;
	}

	public String getNursedeptName() {
		return nursedeptName;
	}

}
