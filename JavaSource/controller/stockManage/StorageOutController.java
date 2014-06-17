/**  
 * @Title: StorageInController.java 
 * @Package controller.stockManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:50:34 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package controller.stockManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.stockManage.StorageOutDetailDomain;
import service.stockManage.StorageOutRecordDomain;
import service.stockManage.StorageOutService;
import util.DateUtil;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

/**
 * @ClassName: StorageInController
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:50:34
 * @version V1.0
 */
public class StorageOutController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */
	private static final long serialVersionUID = -6726161983596114497L;

	private transient StorageOutService storageOutService;

	// 当前登录用户
	private PensionEmployee curUser;
	// 查询条件
	private Date startDate;
	private Date endDate;
	// 出库记录列表
	private List<StorageOutRecordDomain> storageOutRecordList = new ArrayList<StorageOutRecordDomain>();
	// 选中出库记录
	private StorageOutRecordDomain selectedRow;
	// 出库明细列表
	private List<StorageOutDetailDomain> storageOutDetailList = new ArrayList<StorageOutDetailDomain>();
	// 新增出库主记录对象
	private StorageOutRecordDomain addStorageOutRecord;
	// 新增出库明细对象
	private StorageOutDetailDomain addStorageOutdetail;
	// 新增出库明细列表
	private List<StorageOutDetailDomain> addStorageOutdetailList = new ArrayList<StorageOutDetailDomain>();
	// 出库数量
	private String outQty;
	// 物资明细ID
	// private Long detailId;
	// 选中的物资明细
	private StorageOutDetailDomain selectedItemDetail;
	// 库存
	private float storageQty;

	// 输入法sql
	private String itemSql;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		initSql();
		endDate = new Date();
		startDate = DateUtil.getBeforeDay(endDate, 7);
		searchStorageOutRecord();
	}

	/**
	 * 初始化sql
	 */
	public void initSql() {
		itemSql = "select pc.id,ps.item_name,pc.input_code,pt.id as typeId,pt.item_name as typeName,"
				+ "ps.storage_qty,date(ps.expire_date),ps.purchase_price,ps.unit from "
				+ "pension_storage ps,pension_dic_itemtype pt,"
				+ "pension_item_catalog pc where pt.id=ps.type_id and ps.item_id=pc.id";
	}

	/**
	 * 查询出库记录
	 */
	public void searchStorageOutRecord() {
		storageOutRecordList = storageOutService.selectStorageOutRecord(
				startDate, endDate);
		selectedRow = null;
		storageOutDetailList = new ArrayList<StorageOutDetailDomain>();
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearch() {
		endDate = null;
		startDate = null;
	}

	/**
	 * 查询入库记录明细
	 */
	public void selectStorageOutDetail() {
		storageOutDetailList = storageOutService
				.selectStorageOutDetail(selectedRow.getId());
	}

	/**
	 * 清空入库记录明细
	 */
	public void clearStorageOutDetail() {
		storageOutDetailList = new ArrayList<StorageOutDetailDomain>();
	}

	/**
	 * 初始化新增出库记录对话框
	 */
	public void initAddForm() {
		addStorageOutRecord = new StorageOutRecordDomain();
		addStorageOutdetail = new StorageOutDetailDomain();
		addStorageOutdetailList = new ArrayList<StorageOutDetailDomain>();
		addStorageOutRecord.setStorageoutTime(new Date());
		outQty = "";
		storageQty = 0.0f;
		addStorageOutRecord.setStorageoutNo(storageOutService
				.selectStoragoutNo());
		addStorageOutRecord.setTotalMoney(0.00f);
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
		addStorageOutRecord = selectedRow;
		addStorageOutdetailList = storageOutService
				.selectStorageOutDetail(addStorageOutRecord.getId());
		addStorageOutdetail = new StorageOutDetailDomain();
		request.addCallbackParam("editshow", true);
	}

	/**
	 * 增加一条明细
	 */
	public void addStorageoutDetail() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			Integer quality = Integer.valueOf(outQty);
			if (!(quality.intValue() > 0)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"出库数量  请输入正值！", ""));
				return;
			}
			if (storageQty < quality) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "库存不足！",
								""));
				return;
			}
			for (StorageOutDetailDomain detail : addStorageOutdetailList) {
				if (detail.getItemId().equals(addStorageOutdetail.getItemId())) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该物资已添加！", ""));
					return;
				}
			}
			// detailId += 1;
			// addStorageOutdetail.setId(detailId);
			addStorageOutdetail.setOutQty(quality);
			addStorageOutdetail.setTotalMoney(quality
					* addStorageOutdetail.getPurchasePrice());
			addStorageOutdetailList.add(addStorageOutdetail);
			addStorageOutRecord.setTotalMoney(addStorageOutRecord
					.getTotalMoney()
					+ addStorageOutdetail.getPurchasePrice()
					* quality);
			addStorageOutdetail = new StorageOutDetailDomain();
			outQty = "";
			request.addCallbackParam("addDetail", true);
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"出库数量  只能输入数字！", ""));
		}
	}

	/**
	 * 删除主记录确认
	 */
	public void delRecordConfirm() {
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
	 * 删除入库记录
	 */
	public void deleteStorageout() {
		selectedRow.setCleared(1);
		storageOutService.deleteStorageoutRecord(selectedRow);
		searchStorageOutRecord();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "删除成功！", ""));
	}

	/**
	 * 删除明细确认
	 */
	public void delDetailConfirm() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (selectedItemDetail == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条明细！",
							""));
			return;
		}
		request.addCallbackParam("confirmDel", true);
	}

	/**
	 * 删除明细
	 */
	public void deleteItemDetail() {
		addStorageOutRecord.setTotalMoney(addStorageOutRecord.getTotalMoney()
				- selectedItemDetail.getTotalMoney());
		addStorageOutdetailList.remove(selectedItemDetail);
		selectedItemDetail = new StorageOutDetailDomain();
	}

	/**
	 * 保存一条出库记录
	 */
	public void saveStorageoutRecord() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (addStorageOutdetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "物资明细列表不可为空！",
							""));
			return;
		}
		addStorageOutRecord.setOperatorId(curUser.getId());
		addStorageOutRecord.setCleared(2);
		addStorageOutRecord.setBankFlag(2);
		storageOutService.insertStorageoutRecord(addStorageOutRecord,
				addStorageOutdetailList);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "新增出库记录成功！", ""));
		searchStorageOutRecord();
		request.addCallbackParam("addHide", true);
	}

	/**
	 * 更新出库记录
	 */
	public void updateStorageoutRecord() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (addStorageOutdetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "物资明细列表不可为空！",
							""));
			return;
		}
		storageOutService.updateStorageoutRecord(addStorageOutRecord,
				addStorageOutdetailList);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "出库记录修改成功！", ""));
		storageOutRecordList = storageOutService.selectStorageOutRecord(
				startDate, endDate);
		storageOutDetailList = storageOutService
				.selectStorageOutDetail(selectedRow.getId());
		request.addCallbackParam("edithide", true);
	}

	/**
	 * 记账
	 */
	public void bankStorageout() {
		if (selectedRow == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条记录！",
							""));
			return;
		}
		if (selectedRow.getBankFlag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该记录已记账！", ""));
			return;
		}
		selectedRow.setBankFlag(1);
		storageOutService.bankStorageoutRecord(selectedRow,
				storageOutDetailList);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "记账成功！", ""));
		storageOutRecordList = storageOutService.selectStorageOutRecord(
				startDate, endDate);
		storageOutDetailList = storageOutService
				.selectStorageOutDetail(selectedRow.getId());
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

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<StorageOutRecordDomain> getStorageOutRecordList() {
		return storageOutRecordList;
	}

	public void setStorageOutRecordList(
			List<StorageOutRecordDomain> storageOutRecordList) {
		this.storageOutRecordList = storageOutRecordList;
	}

	public StorageOutRecordDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(StorageOutRecordDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public List<StorageOutDetailDomain> getStorageOutDetailList() {
		return storageOutDetailList;
	}

	public void setStorageOutDetailList(
			List<StorageOutDetailDomain> storageOutDetailList) {
		this.storageOutDetailList = storageOutDetailList;
	}

	public StorageOutRecordDomain getAddStorageOutRecord() {
		return addStorageOutRecord;
	}

	public void setAddStorageOutRecord(
			StorageOutRecordDomain addStorageOutRecord) {
		this.addStorageOutRecord = addStorageOutRecord;
	}

	public StorageOutDetailDomain getAddStorageOutdetail() {
		return addStorageOutdetail;
	}

	public void setAddStorageOutdetail(
			StorageOutDetailDomain addStorageOutdetail) {
		this.addStorageOutdetail = addStorageOutdetail;
	}

	public List<StorageOutDetailDomain> getAddStorageOutdetailList() {
		return addStorageOutdetailList;
	}

	public void setAddStorageOutdetailList(
			List<StorageOutDetailDomain> addStorageOutdetailList) {
		this.addStorageOutdetailList = addStorageOutdetailList;
	}

	public String getOutQty() {
		return outQty;
	}

	public void setOutQty(String outQty) {
		this.outQty = outQty;
	}

	// public Long getDetailId() {
	// return detailId;
	// }
	//
	// public void setDetailId(Long detailId) {
	// this.detailId = detailId;
	// }

	public StorageOutDetailDomain getSelectedItemDetail() {
		return selectedItemDetail;
	}

	public void setSelectedItemDetail(StorageOutDetailDomain selectedItemDetail) {
		this.selectedItemDetail = selectedItemDetail;
	}

	public void setStorageOutService(StorageOutService storageOutService) {
		this.storageOutService = storageOutService;
	}

	public StorageOutService getStorageOutService() {
		return storageOutService;
	}

	public void setItemSql(String itemSql) {
		this.itemSql = itemSql;
	}

	public String getItemSql() {
		return itemSql;
	}

	public void setStorageQty(float storageQty) {
		this.storageQty = storageQty;
	}

	public float getStorageQty() {
		return storageQty;
	}

}
