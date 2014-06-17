/**  
* @Title: PensionRecureDetailExtend.java 
* @Package controller.configureManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-10-11 下午3:39:03 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package controller.configureManage;

import java.io.Serializable;

import domain.olderManage.PensionRecureDetail;

/** 
 * @ClassName: PensionRecureDetailExtend 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-10-11 下午3:39:03
 * @version V1.0 
 */
public class PensionRecureDetailExtend implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	* @version V1.0
	*/ 
	
	private static final long serialVersionUID = -6813062760284123035L;

	private PensionRecureDetail pensionRecureDetail;
	private String recureItemName;
	private String recoverName;
	
	/**
	 * @return the pensionRecureDetail
	 */
	public PensionRecureDetail getPensionRecureDetail() {
		return pensionRecureDetail;
	}
	/**
	 * @param pensionRecureDetail the pensionRecureDetail to set
	 */
	public void setPensionRecureDetail(PensionRecureDetail pensionRecureDetail) {
		this.pensionRecureDetail = pensionRecureDetail;
	}
	/**
	 * @return the recureItemName
	 */
	public String getRecureItemName() {
		return recureItemName;
	}
	/**
	 * @param recureItemName the recureItemName to set
	 */
	public void setRecureItemName(String recureItemName) {
		this.recureItemName = recureItemName;
	}
	/**
	 * @return the recoverName
	 */
	public String getRecoverName() {
		return recoverName;
	}
	/**
	 * @param recoverName the recoverName to set
	 */
	public void setRecoverName(String recoverName) {
		this.recoverName = recoverName;
	}
	
	
}
