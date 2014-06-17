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

import service.stockManage.StorageInDetailDomain;
import service.stockManage.StorageInRecordDomain;
import service.stockManage.StorageInService;
import util.DateUtil;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.stockManage.PensionPurchaseRecord;
import domain.stockManage.PensionStorageinDetail;

/**
 * @ClassName: StorageInController
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:50:34
 * @version V1.0
 */
public class StorageInController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */
	private static final long serialVersionUID = -6418299319035249358L;

	private transient StorageInService storageInService;
	// 当前登录用户
	private PensionEmployee curUser;
	// 查询条件
	private Date startDate;
	private Date endDate;
	// 入库记录列表
	private List<StorageInRecordDomain> storageInRecordList = new ArrayList<StorageInRecordDomain>();
	// 选中入库记录
	private StorageInRecordDomain selectedRow;
	// 入库明细列表
	private List<StorageInDetailDomain> storageInDetailList = new ArrayList<StorageInDetailDomain>();
	// 新增入库主记录对象
	private StorageInRecordDomain addStorageInRecord = new StorageInRecordDomain();
	// 新增入库明细对象
	private StorageInDetailDomain addStorageIndetail;
	// 新增入库明细列表
	private List<StorageInDetailDomain> addStorageIndetailList = new ArrayList<StorageInDetailDomain>();
	// 进货价格
	private String purchasePrice;
	// 入库数量
	private String inQty;
	// 选中的物资明细
	private StorageInDetailDomain selectedItemDetail;

	private String purchaseNo;

	private int purchaseFlag;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		endDate = new Date();
		startDate = DateUtil.getBeforeDay(endDate, 7);
		searchStorageInRecord();
	}

	/**
	 * 查询入库记录
	 */
	public void searchStorageInRecord() {
		storageInRecordList = storageInService.selectStorageInRecord(startDate,
				endDate);
		selectedRow = null;
		storageInDetailList = new ArrayList<StorageInDetailDomain>();
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
	public void selectStorageInDetail() {
		storageInDetailList = storageInService
				.selectStorageInDetail(selectedRow.getId());
	}

	/**
	 * 清空入库记录明细
	 */
	public void clearStorageInDetail() {
		storageInDetailList = new ArrayList<StorageInDetailDomain>();
	}

	/**
	 * 初始化新增对话框
	 */
	public void initAddForm() {
		clearAddForm();
		addStorageInRecord.setStorageinTime(new Date());
		addStorageInRecord.setStorageinNo(storageInService.selectStorageinNo());
		purchaseFlag = 1;
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
		addStorageInRecord = selectedRow;
		addStorageIndetailList = storageInService
				.selectStorageInDetail(addStorageInRecord.getId());
		addStorageIndetail = new StorageInDetailDomain();
		request.addCallbackParam("editshow", true);
	}

	/**
	 * 增加一条明细
	 */
	public void addStorageinDetail() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			Float price = Float.valueOf(purchasePrice);
			Integer quality = Integer.valueOf(inQty);
			if (!(quality.intValue() > 0)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"入库数量  请输入正值！", ""));
				return;
			}
			if (!(price.floatValue() > 0)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"进货价格 请输入正值！", ""));
				return;
			}
			for (StorageInDetailDomain detail : addStorageIndetailList) {
				if (detail.getItemId().equals(addStorageIndetail.getItemId())) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该物资已添加！", ""));
					return;
				}
			}
			addStorageIndetail.setCleared(2);
			addStorageIndetail.setInQty(quality);
			addStorageIndetail.setPurchasePrice(price);
			addStorageIndetail.setTotalMoney(quality * price);
			addStorageIndetailList.add(addStorageIndetail);
			addStorageIndetail = new StorageInDetailDomain();
			purchasePrice = "";
			inQty = "";
			request.addCallbackParam("addDetail", true);
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"入库数量和进货价格  只能输入数字！", ""));
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
	public void deleteStoragein() {
		selectedRow.setCleared(1);
		storageInService.deleteStorageinRecord(selectedRow);
		searchStorageInRecord();
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
		addStorageIndetailList.remove(selectedItemDetail);
		selectedItemDetail = new StorageInDetailDomain();
	}

	/**
	 * 清空新增物资对话框
	 */
	public void clearAddForm() {
		addStorageIndetail = new StorageInDetailDomain();
		addStorageIndetailList = new ArrayList<StorageInDetailDomain>();
		addStorageInRecord = new StorageInRecordDomain();
		purchasePrice = "";
		inQty = "";
	}

	/**
	 * 保存一条入库记录
	 */
	public void saveStorageinRecord() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (addStorageInRecord.getStorageinTime() == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "入库时间不可为空！",
							""));
			return;
		}
		if (addStorageIndetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "物资明细列表不可为空！",
							""));
			return;
		}
		for (StorageInDetailDomain temp : addStorageIndetailList) {
			if (temp.getPurchasePrice() == null || temp.getExpireDate() == null
					|| temp.getTotalMoney() == null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"请将物资价格、有效期、总额维护完整！", ""));
				return;
			}
		}
		addStorageInRecord.setOperatorId(curUser.getId());
		addStorageInRecord.setCleared(2);
		addStorageInRecord.setBankflag(2);
		storageInService.insertStorageinRecord(addStorageInRecord,
				addStorageIndetailList);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "新增入库记录成功！", ""));
		searchStorageInRecord();
		request.addCallbackParam("addHide", true);
	}

	/**
	 * 更新入库记录
	 */
	public void updateStorageinRecord() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if (addStorageIndetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "物资明细列表不可为空！",
							""));
			return;
		}
		storageInService.updateStorageinRecord(addStorageInRecord,
				addStorageIndetailList);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "入库记录修改成功！", ""));
		storageInRecordList = storageInService.selectStorageInRecord(startDate,
				endDate);
		storageInDetailList = storageInService
				.selectStorageInDetail(selectedRow.getId());
		request.addCallbackParam("edithide", true);
	}

	/**
	 * 记账
	 */
	public void bankStoragein() {
		if (selectedRow == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条记录！",
							""));
			return;
		}
		if (selectedRow.getBankflag() == 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该记录已记账！", ""));
			return;
		}
		selectedRow.setBankflag(1);
		storageInService.bankStorageinRecord(selectedRow, storageInDetailList);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "记账成功！", ""));
		storageInRecordList = storageInService.selectStorageInRecord(startDate,
				endDate);
	}

	/**
	 * 查询采购单信息
	 */
	public void selectPurchaseRecord() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (purchaseNo == "" || purchaseNo == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "采购单号不可为空！",
							""));
			return;
		}
		PensionPurchaseRecord purchaseRecord = storageInService
				.selectPurchaseRecord(purchaseNo);
		if (purchaseRecord == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该采购单不存在！", ""));
			return;
		}
		if (purchaseRecord.getApproveResult() == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该采购单尚未审核！",
							""));
			return;
		}
		if (purchaseRecord.getApproveResult() == 2) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该采购单审核未通过！",
							""));
			return;
		}
		addStorageInRecord = new StorageInRecordDomain();
		addStorageInRecord.setStorageinNo(storageInService.selectStorageinNo());
		addStorageIndetail = new StorageInDetailDomain();
		addStorageIndetailList = storageInService
				.selectPurchaseDetail(purchaseRecord.getId());
		addStorageInRecord.setStorageinTime(new Date());
		purchaseFlag = 2;
		requestContext.addCallbackParam("purchaseHide", true);
	}

	/**
	 * 初始化采购单导入对话框
	 */
	public void initPurchaseNo() {
		purchaseNo = "";
	}

	/**
	 * 计算物资总额
	 */
	public void calTotalMoney(StorageInDetailDomain detail) {
		try {
			if (detail.getPurchasePrice() != null) {
				Float price = Float.valueOf(detail.getPurchasePrice());
				if (!(price.floatValue() > 0)) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"进货价格 请输入正值！", ""));
					return;
				}
				detail.setTotalMoney(detail.getInQty() * price);
			}
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"进货价格  只能输入数字！", ""));
		}
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

	public StorageInService getStorageInService() {
		return storageInService;
	}

	public void setStorageInService(StorageInService storageInService) {
		this.storageInService = storageInService;
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

	public List<StorageInRecordDomain> getStorageInRecordList() {
		return storageInRecordList;
	}

	public void setStorageInRecordList(
			List<StorageInRecordDomain> storageInRecordList) {
		this.storageInRecordList = storageInRecordList;
	}

	public void setSelectedRow(StorageInRecordDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public StorageInRecordDomain getSelectedRow() {
		return selectedRow;
	}

	public void setStorageInDetailList(
			List<StorageInDetailDomain> storageInDetailList) {
		this.storageInDetailList = storageInDetailList;
	}

	public List<StorageInDetailDomain> getStorageInDetailList() {
		return storageInDetailList;
	}

	public void setAddStorageInRecord(StorageInRecordDomain addStorageInRecord) {
		this.addStorageInRecord = addStorageInRecord;
	}

	public StorageInRecordDomain getAddStorageInRecord() {
		return addStorageInRecord;
	}

	public void setAddStorageIndetail(StorageInDetailDomain addStorageIndetail) {
		this.addStorageIndetail = addStorageIndetail;
	}

	public PensionStorageinDetail getAddStorageIndetail() {
		return addStorageIndetail;
	}

	public void setAddStorageIndetailList(
			List<StorageInDetailDomain> addStorageIndetailList) {
		this.addStorageIndetailList = addStorageIndetailList;
	}

	public List<StorageInDetailDomain> getAddStorageIndetailList() {
		return addStorageIndetailList;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setInQty(String inQty) {
		this.inQty = inQty;
	}

	public String getInQty() {
		return inQty;
	}

	public void setSelectedItemDetail(StorageInDetailDomain selectedItemDetail) {
		this.selectedItemDetail = selectedItemDetail;
	}

	public StorageInDetailDomain getSelectedItemDetail() {
		return selectedItemDetail;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseFlag(int purchaseFlag) {
		this.purchaseFlag = purchaseFlag;
	}

	public int getPurchaseFlag() {
		return purchaseFlag;
	}
}
