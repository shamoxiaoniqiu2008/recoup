package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import service.olderManage.DrugNoticeService;
import service.olderManage.DrugReceiveDomain;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-15 上午10:58:44
 */

public class DrugNoticeController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient DrugNoticeService drugNoticeService;
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
	 * 数量不足药品列表
	 */
	private List<DrugReceiveDomain> shortageDrugList = new ArrayList<DrugReceiveDomain>();

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
		if(drugName==""){
			drugName=null;
		}
		overDulDrugList = drugNoticeService.selectOverDulDrugRecord(olderId,
				drugName);
		shortageDrugList = drugNoticeService.selectShortageDrugRecord(olderId,
				drugName);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "查询完成", ""));
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearch() {
		olderId = null;
		olderName = "";
		drugName = null;
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

	public void setDrugNoticeService(DrugNoticeService drugNoticeService) {
		this.drugNoticeService = drugNoticeService;
	}

	public DrugNoticeService getDrugNoticeService() {
		return drugNoticeService;
	}

	public List<DrugReceiveDomain> getOverDulDrugList() {
		return overDulDrugList;
	}

	public void setOverDulDrugList(List<DrugReceiveDomain> overDulDrugList) {
		this.overDulDrugList = overDulDrugList;
	}

	public List<DrugReceiveDomain> getShortageDrugList() {
		return shortageDrugList;
	}

	public void setShortageDrugList(List<DrugReceiveDomain> shortageDrugList) {
		this.shortageDrugList = shortageDrugList;
	}
}
