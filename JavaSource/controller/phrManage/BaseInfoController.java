/**  
* @Title: ArchivesViewController.java 
* @Package controller.phrManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-12-5 上午10:33:05 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package controller.phrManage;

import java.io.Serializable;
import javax.annotation.PostConstruct;

import service.phrManage.BaseInfoViewService;
import domain.phrManage.PhrExtend;
/** 
 * @ClassName: ArchivesViewController 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-12-5 上午10:33:05
 * @version V1.0 
 */
public class BaseInfoController implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	* @version V1.0
	*/ 
	private static final long serialVersionUID = 7696122370960042443L;
	
	private transient BaseInfoViewService baseInfoViewService;
	
	private PhrExtend hrExtend = new PhrExtend();
	
	
	// 老人ID
	private Long olderId;
	// 老人姓名
	private String olderName;
		
	// 定义老人姓名输入法变量
	private String nameToSql;
	private String fitcolName;
	private String displaycolName;
	
	
	@PostConstruct
	public void init() {
		// 初始化老人姓名输入法
		initNameToSql();
		hrExtend.setPensionDicBloodtypeList(baseInfoViewService.selectAllPensionDicBloodtype());
		hrExtend.setPensionDicDegreeList(baseInfoViewService.selectAllPensionDicDegree());
		hrExtend.setPensionDicNationList(baseInfoViewService.selectAllPensionDicNation());
		hrExtend.setPensionDicMarriageList(baseInfoViewService.selectAllPensionDicMarriage());
		hrExtend.setPensionDicPaymentList(baseInfoViewService.selectAllPensionDicPayment());
		
	}

	/**
	 * 
	* @Title: initNameToSql 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-12-5 下午4:03:23
	* @version V1.0
	 */
	public void initNameToSql() {
		nameToSql = " select" + " po.id as olderId" + " ,po.name as olderName"
				+ " ,po.inputcode as inputCode"
				+ " ,case po.sex when 1 then '男' else '女' end as sexName"
				+ " from" + " pension_older po" + " where" + " po.statuses = 3"
				+ " and" + " po.cleared = 2";
		fitcolName = "olderId,inputCode";
		displaycolName = "编号:0,姓名:1,输入码:2,性别:3";
	}

	/**
	 * @return the nameToSql
	 */
	public String getNameToSql() {
		return nameToSql;
	}


	/**
	 * @param nameToSql the nameToSql to set
	 */
	public void setNameToSql(String nameToSql) {
		this.nameToSql = nameToSql;
	}


	/**
	 * @return the fitcolName
	 */
	public String getFitcolName() {
		return fitcolName;
	}


	/**
	 * @param fitcolName the fitcolName to set
	 */
	public void setFitcolName(String fitcolName) {
		this.fitcolName = fitcolName;
	}


	/**
	 * @return the displaycolName
	 */
	public String getDisplaycolName() {
		return displaycolName;
	}


	/**
	 * @param displaycolName the displaycolName to set
	 */
	public void setDisplaycolName(String displaycolName) {
		this.displaycolName = displaycolName;
	}




	/**
	 * @return the olderId
	 */
	public Long getOlderId() {
		return olderId;
	}




	/**
	 * @param olderId the olderId to set
	 */
	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}




	/**
	 * @return the olderName
	 */
	public String getOlderName() {
		return olderName;
	}




	/**
	 * @param olderName the olderName to set
	 */
	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	/**
	 * @return the baseInfoViewService
	 */
	public BaseInfoViewService getBaseInfoViewService() {
		return baseInfoViewService;
	}

	/**
	 * @param baseInfoViewService the baseInfoViewService to set
	 */
	public void setBaseInfoViewService(BaseInfoViewService baseInfoViewService) {
		this.baseInfoViewService = baseInfoViewService;
	}

	/**
	 * @return the hrExtend
	 */
	public PhrExtend getHrExtend() {
		return hrExtend;
	}

	/**
	 * @param hrExtend the hrExtend to set
	 */
	public void setHrExtend(PhrExtend hrExtend) {
		this.hrExtend = hrExtend;
	}

}
