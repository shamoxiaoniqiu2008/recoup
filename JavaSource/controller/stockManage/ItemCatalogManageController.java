/**  
 * @Title: PurchaseApplyController.java 
 * @Package controller.stockManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:49:41 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package controller.stockManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.stockManage.ItemCatalogManageService;
import util.Spell;
import domain.stockManage.PensionItemCatalog;

/**
 * @ClassName: PurchaseApplyController
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:49:41
 * @version V1.0
 */
public class ItemCatalogManageController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */
	private static final long serialVersionUID = -6649046518368341195L;

	private transient ItemCatalogManageService itemCatalogManageService;
	// 查询条件
	private Long typeId;
	// 选中的物资目录
	private PensionItemCatalog selectedRow;
	// 物资目录列表
	private List<PensionItemCatalog> itemCatalogList = new ArrayList<PensionItemCatalog>();
	// 新增物资目录
	private PensionItemCatalog addItemCatalog;

	@PostConstruct
	public void init() {
		searchItemCatalog();
	}

	public void searchItemCatalog() {
		itemCatalogList = itemCatalogManageService.selectItemCalalogs(typeId);
		selectedRow = null;
	}

	/**
	 * 初始化新增对话框
	 */
	public void initAddForm() {
		addItemCatalog = new PensionItemCatalog();
		System.out.println(addItemCatalog.getTypeName());
	}

	/**
	 * 初始化修改对话框
	 */
	public void initEditForm() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条记录！",
							""));
			return;
		}
		addItemCatalog = selectedRow;
		request.addCallbackParam("editshow", true);
	}

	/**
	 * 删除按钮触发
	 */
	public void delItemConfirm() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条记录！",
							""));
			return;
		}
		request.addCallbackParam("delRecord", true);
	}

	/**
	 * 删除
	 */
	public void deletItemCatalog() {
		selectedRow.setCleared(1);
		itemCatalogManageService.deleteItemCatalog(selectedRow);
		searchItemCatalog();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "删除成功！", ""));
	}

	/**
	 * 保存一条物资目录
	 */
	public void saveItemCatalog() {
		final RequestContext request = RequestContext.getCurrentInstance();
		addItemCatalog.setCleared(2);
		itemCatalogManageService.insertItemCatalog(addItemCatalog);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "新增物资目录成功！", ""));
		searchItemCatalog();
		request.addCallbackParam("addHide", true);
	}

	/**
	 * 更新入库记录
	 */
	public void updateItemCatalog() {
		final RequestContext request = RequestContext.getCurrentInstance();
		itemCatalogManageService.updateApplyRecord(addItemCatalog);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "物资目录修改成功！", ""));
		searchItemCatalog();
		request.addCallbackParam("edithide", true);
	}

	/**
	 * 选中一行时触发
	 * 
	 */
	public void setEnableFlag(SelectEvent e) {

	}

	/**
	 * 未选中一行时触发
	 * 
	 */
	public void setUnableFlag(UnselectEvent e) {

	}

	/**
	 * 由物资名称获取拼音码
	 */
	public void updateInputCode() {
		addItemCatalog.setInputCode(Spell.getFirstSpell(addItemCatalog
				.getItemName()));
	}

	public PensionItemCatalog getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(PensionItemCatalog selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setItemCatalogManageService(
			ItemCatalogManageService itemCatalogManageService) {
		this.itemCatalogManageService = itemCatalogManageService;
	}

	public ItemCatalogManageService getItemCatalogManageService() {
		return itemCatalogManageService;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setItemCatalogList(List<PensionItemCatalog> itemCatalogList) {
		this.itemCatalogList = itemCatalogList;
	}

	public List<PensionItemCatalog> getItemCatalogList() {
		return itemCatalogList;
	}

	public void setAddItemCatalog(PensionItemCatalog addItemCatalog) {
		this.addItemCatalog = addItemCatalog;
	}

	public PensionItemCatalog getAddItemCatalog() {
		return addItemCatalog;
	}

}
