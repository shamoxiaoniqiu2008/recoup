package service.olderManage;

import domain.olderManage.PensionOlderRecureMain;

public class PensionOlderRecureMainExtend extends PensionOlderRecureMain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String olderName;  //老人姓名
	private String recureitemName; //项目列表
	private String inputerName;   //录入者姓名
	public String getOlderName() {
		return olderName;
	}
	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}
	public String getRecureitemName() {
		return recureitemName;
	}
	public void setRecureitemName(String recureitemName) {
		this.recureitemName = recureitemName;
	}
	public String getInputerName() {
		return inputerName;
	}
	public void setInputerName(String inputerName) {
		this.inputerName = inputerName;
	}

	
	
}
