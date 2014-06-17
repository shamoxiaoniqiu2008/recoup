package controller.configureManage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.dao.DataAccessException;

import domain.dictionary.PensionDicDeposittype;


import service.configureManage.DepositTypeManageService;
import util.Spell;

public class DepositTypeManageController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionDicDeposittype> typeRecords = new ArrayList<PensionDicDeposittype>();
	/**
	 * 被选中的记录
	 */
	private PensionDicDeposittype[] selectedRows;
	/**
	 * 被录入的记录
	 */
	private PensionDicDeposittype insertedRow = new PensionDicDeposittype();
	/**
	 * 被修改的记录
	 */
	private PensionDicDeposittype updatedRow = new PensionDicDeposittype();
	/**
	 * 押金类别 用作查询参数
	 */
	private String type;
	/**
	 * 押金类型id
	 */
	private Long typeId;
	/**
	 * 押金类别sql
	 */
	private String typeSql;
	/**
	 * 修改和删除按钮是否可用
	 */
	private boolean disUpdateButton = true;
	/**
	 * 修改删除是否可用
	 */
	private boolean disDeleteButton = true;
	/**
	 * 注入业务
	 */
	private transient DepositTypeManageService depositTypeManageService;
	
	/**
	 * @Description:初始化数据方法.
	 * @return: void
	 * @exception:
	 * @throws:
	 * @version: 1.0
	 * @author: Tim li
	 */
	@PostConstruct
	public void init() {
		initSql();
		selectTypeRecords();
	}
	
	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		typeSql = "select pdd.type as type, pdd.inputCode as inputcode, pdd.id as id " +
				"from pension_dic_deposittype pdd " +
				"where pdd.invalided = 1";
	}
	
	/**
	 * 查询押金类别信息
	 */
	public void selectTypeRecords(){
		disDeleteButton = true;
		setDisUpdateButton(true);
		selectedRows = null;
		typeRecords = depositTypeManageService.selectTypeRecords(type);
	}
	/**
	 * 删除押金类别信息
	 */
	public void deleteTypeRecord(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		for(PensionDicDeposittype selectedRow:selectedRows){
			depositTypeManageService.deleteTypeRecord(selectedRow);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"删除成功！", "删除成功！");
			context.addMessage(null, message);
		}
		disDeleteButton =true;
		selectTypeRecords();
		
	}
	/**
	 * 录入押金类别记录
	 */
	public void insertTypeRecord(){
		
		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "添加成功";
		try {
			
			depositTypeManageService.insertTypeRecord(insertedRow);
			selectTypeRecords();
		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";
			
		} catch (Exception e) {

			e.getStackTrace();
			info = "出现未知异常，请联系系统管理员！";

		}
		clearInsertForm();
		if(info.equals("添加成功")){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);	
			context.addMessage(null, message);
			request.addCallbackParam("success", true);
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);	
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		}
		
	}
	/**
	 * 修改押金类别记录
	 */
	public void updateTypeRecord(){
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		String info = "修改成功";
		depositTypeManageService.updateTypeRecord(updatedRow);
		selectTypeRecords();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				info, info);	
		context.addMessage(null, message);
		request.addCallbackParam("success", true);
		
	}
	/**
	 * 
	 * 清空insertFrom
	 */
	public void clearInsertForm() {
		insertedRow = new PensionDicDeposittype();
	}
	/**
	 * 情况selectForm
	 */
	public void clearSelectForm(){
		type = null;
		typeId = null;
	}
	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		
		setDisDeleteButton(false);
		if(selectedRows.length == 1){
			setDisUpdateButton(false);
		}else{
			setDisUpdateButton(true);
		}

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		if(selectedRows.length == 1){
			setDisUpdateButton(false);
		}
		if(selectedRows.length == 0){
			setDisUpdateButton(true);
			setDisDeleteButton(true);
		}
	}
	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordUpdatedRow() {
		updatedRow = selectedRows[0];
	}
	
	/**
	 * 由押金类别获取拼音码
	 */
	public void updateInputCode() {
		if(insertedRow.getType()!=null){
			insertedRow.setInputcode(Spell.getFirstSpell(insertedRow
					.getType()));
		}
		if(updatedRow.getType()!=null){
			updatedRow.setInputcode(Spell.getFirstSpell(updatedRow
					.getType()));
		}
	}

	public void setTypeRecords(List<PensionDicDeposittype> typeRecords) {
		this.typeRecords = typeRecords;
	}

	public List<PensionDicDeposittype> getTypeRecords() {
		return typeRecords;
	}

	public void setDisUpdateButton(boolean disUpdateButton) {
		this.disUpdateButton = disUpdateButton;
	}

	public boolean isDisUpdateButton() {
		return disUpdateButton;
	}

	public void setDisDeleteButton(boolean disDeleteButton) {
		this.disDeleteButton = disDeleteButton;
	}

	public boolean isDisDeleteButton() {
		return disDeleteButton;
	}

	public void setDepositTypeManageService(DepositTypeManageService depositTypeManageService) {
		this.depositTypeManageService = depositTypeManageService;
	}

	public DepositTypeManageService getDepositTypeManageService() {
		return depositTypeManageService;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	public PensionDicDeposittype[] getSelectedRows() {
		return selectedRows;
	}

	public void setSelectedRows(PensionDicDeposittype[] selectedRows) {
		this.selectedRows = selectedRows;
	}

	public PensionDicDeposittype getInsertedRow() {
		return insertedRow;
	}

	public void setInsertedRow(PensionDicDeposittype insertedRow) {
		this.insertedRow = insertedRow;
	}

	public PensionDicDeposittype getUpdatedRow() {
		return updatedRow;
	}

	public void setUpdatedRow(PensionDicDeposittype updatedRow) {
		this.updatedRow = updatedRow;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTypeSql(String typeSql) {
		this.typeSql = typeSql;
	}

	public String getTypeSql() {
		return typeSql;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getTypeId() {
		return typeId;
	}

}
