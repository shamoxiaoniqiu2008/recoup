package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import service.olderManage.DrugReceiveDetailDomain;
import service.olderManage.DrugReceiveDomain;
import service.olderManage.DrugStorageService;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-15 上午10:58:44
 */

public class DrugStorageController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient DrugStorageService drugStorageService;
	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;
	private String drugName;
	/**
	 * 输入法sql
	 */
	private String olderSql;
	private String drugSql;
	/**
	 * 过期药品列表
	 */
	private List<DrugReceiveDomain> overDulDrugList = new ArrayList<DrugReceiveDomain>();
	/**
	 * 药物明细列表
	 */
	private List<DrugReceiveDetailDomain> drugDetailList = new ArrayList<DrugReceiveDetailDomain>();

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		initSql();
		searchDrugList();
	}

	/**
	 * 查询报警药品列表
	 */
	public void searchDrugList() {
		if (drugName == "") {
			drugName = null;
		}
		overDulDrugList = drugStorageService
				.selectDrugRecord(olderId, drugName);
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearch() {
		olderId = null;
		olderName = "";
		drugName = null;
		initSql();
	}

	/**
	 * 查询明细
	 * 
	 * @param selectRecord
	 */
	public void selectDrugDetail(DrugReceiveDomain selectRecord) {
		drugDetailList = drugStorageService.selectDrugDetail(
				selectRecord.getDrugreceiveName(), selectRecord.getOlderId());
	}

	/**
	 * 初始化老人输入法
	 */
	public void initSql() {
		olderSql = "select pe.id,pe.name,pe.inputCode from pension_older pe where pe.cleared=2 and pe.statuses not  in(1,2)";
		drugSql = "select t.id, t.drugreceive_name,t.input_code from pension_dic_drugreceive t";
	}

	public void updateDrugSql() {
		drugSql = "select t.id, t.drugreceive_name,t.input_code from pension_dic_drugreceive t where t.older_id="
				+ olderId;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public void setOlderSql(String olderSql) {
		this.olderSql = olderSql;
	}

	public String getOlderSql() {
		return olderSql;
	}

	public void setDrugSql(String drugSql) {
		this.drugSql = drugSql;
	}

	public String getDrugSql() {
		return drugSql;
	}

	public List<DrugReceiveDomain> getOverDulDrugList() {
		return overDulDrugList;
	}

	public void setOverDulDrugList(List<DrugReceiveDomain> overDulDrugList) {
		this.overDulDrugList = overDulDrugList;
	}

	public void setDrugStorageService(DrugStorageService drugStorageService) {
		this.drugStorageService = drugStorageService;
	}

	public DrugStorageService getDrugStorageService() {
		return drugStorageService;
	}

	public void setDrugDetailList(List<DrugReceiveDetailDomain> drugDetailList) {
		this.drugDetailList = drugDetailList;
	}

	public List<DrugReceiveDetailDomain> getDrugDetailList() {
		return drugDetailList;
	}
}
