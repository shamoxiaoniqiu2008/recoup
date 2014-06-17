/**  
* @Title: PhrExtend.java 
* @Package domain.phrManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-12-5 下午3:32:29 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package domain.phrManage;

import java.util.ArrayList;
import java.util.List;

import domain.dictionary.PensionDicBloodtype;
import domain.dictionary.PensionDicDegree;
import domain.dictionary.PensionDicMarriage;
import domain.dictionary.PensionDicNation;
import domain.dictionary.PensionDicPayment;
import domain.olderManage.PensionOlder;

/** 
 * @ClassName: PhrExtend 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-12-5 下午3:32:29
 * @version V1.0 
 */
public class PhrExtend {
	
	//老人对象
	private PensionOlder pensionOlder;
	//血型字典List
	private List<PensionDicBloodtype> pensionDicBloodtypeList = new ArrayList<PensionDicBloodtype>();
	//文化程度List
	private List<PensionDicDegree> pensionDicDegreeList = new ArrayList<PensionDicDegree>();
	//民族字典List
	private List<PensionDicNation> pensionDicNationList = new ArrayList<PensionDicNation>();
	//婚姻状况List
	private List<PensionDicMarriage> pensionDicMarriageList = new ArrayList<PensionDicMarriage>();
	//医疗费用支付方式
	private List<PensionDicPayment> pensionDicPaymentList = new ArrayList<PensionDicPayment>();
	//过敏史
	
	//既往史
	
	//手术
	
	//外伤
	
	//输血
	
	//家族史
	
	//遗传病史
	
	//残疾史
	
	
	
	
	/**
	 * @return the pensionOlder
	 */
	public PensionOlder getPensionOlder() {
		return pensionOlder;
	}

	/**
	 * @param pensionOlder the pensionOlder to set
	 */
	public void setPensionOlder(PensionOlder pensionOlder) {
		this.pensionOlder = pensionOlder;
	}

	/**
	 * @return the pensionDicBloodtypeList
	 */
	public List<PensionDicBloodtype> getPensionDicBloodtypeList() {
		return pensionDicBloodtypeList;
	}

	/**
	 * @param pensionDicBloodtypeList the pensionDicBloodtypeList to set
	 */
	public void setPensionDicBloodtypeList(
			List<PensionDicBloodtype> pensionDicBloodtypeList) {
		this.pensionDicBloodtypeList = pensionDicBloodtypeList;
	}

	/**
	 * @return the pensionDicDegreeList
	 */
	public List<PensionDicDegree> getPensionDicDegreeList() {
		return pensionDicDegreeList;
	}

	/**
	 * @param pensionDicDegreeList the pensionDicDegreeList to set
	 */
	public void setPensionDicDegreeList(List<PensionDicDegree> pensionDicDegreeList) {
		this.pensionDicDegreeList = pensionDicDegreeList;
	}

	/**
	 * @return the pensionDicNationList
	 */
	public List<PensionDicNation> getPensionDicNationList() {
		return pensionDicNationList;
	}

	/**
	 * @param pensionDicNationList the pensionDicNationList to set
	 */
	public void setPensionDicNationList(List<PensionDicNation> pensionDicNationList) {
		this.pensionDicNationList = pensionDicNationList;
	}

	/**
	 * @return the pensionDicMarriageList
	 */
	public List<PensionDicMarriage> getPensionDicMarriageList() {
		return pensionDicMarriageList;
	}

	/**
	 * @param pensionDicMarriageList the pensionDicMarriageList to set
	 */
	public void setPensionDicMarriageList(List<PensionDicMarriage> pensionDicMarriageList) {
		this.pensionDicMarriageList = pensionDicMarriageList;
	}

	/**
	 * @return the pensionDicPaymentList
	 */
	public List<PensionDicPayment> getPensionDicPaymentList() {
		return pensionDicPaymentList;
	}

	/**
	 * @param pensionDicPaymentList the pensionDicPaymentList to set
	 */
	public void setPensionDicPaymentList(List<PensionDicPayment> pensionDicPaymentList) {
		this.pensionDicPaymentList = pensionDicPaymentList;
	}
	
	
	
}
