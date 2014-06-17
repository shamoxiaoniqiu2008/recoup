package controller.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.centling.his.util.DateUtil;
import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

import service.olderManage.CountMedicalItemService;
import service.olderManage.PensionHospitalizegroupExtend;
import service.olderManage.PensionHospitalizeregisterExtend;
import service.olderManage.PensionItemcountExtend;
import service.olderManage.ViewItemCountPaperService;
import util.PmsException;

public class ViewItemCountPaperController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionHospitalizegroupExtend> records = new ArrayList<PensionHospitalizegroupExtend>();
	/**
	 * 每条分组记录 都对应着一个 项目记录 itemRecords 用来在前台显示该就医项目记录
	 */
	private List<PensionItemcountExtend> itemRecords = new ArrayList<PensionItemcountExtend>();
	/**
	 * 每条分组记录 都对应着一个 项目明细 itemDetails 用来在前台显示该就医项目记录
	 */
	private List<PensionHospitalizeregisterExtend> itemDetails = new ArrayList<PensionHospitalizeregisterExtend>();
	/**
	 * 被选中的分组记录
	 */
	private PensionHospitalizegroupExtend selectedGroupRow;
	/**
	 * 被查看的分组记录
	 */
	private PensionHospitalizegroupExtend viewedGroupRow;
	/**
	 * 被选中的项目统计记录
	 */
	private PensionItemcountExtend selectedItemCountRow;
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 分组Id
	 */
	private Long groupId;

	private String groupCollectionInfo;
	private boolean disUpdateButton;

	public String getGroupCollectionInfo() {
		return groupCollectionInfo;
	}

	public void setGroupCollectionInfo(String groupCollectionInfo) {
		this.groupCollectionInfo = groupCollectionInfo;
	}

	/**
	 * 注入业务
	 */
	private transient CountMedicalItemService countMedicalItemService;
	private transient ViewItemCountPaperService viewItemCountPaperService;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee) SessionManager
			.getSessionAttribute(SessionManager.EMPLOYEE);
	private List<PensionHospitalizeregisterExtend> oneGroupOlder;
	private PensionHospitalizeregisterExtend selectedRows;
	private PensionHospitalizeregisterExtend updatedRow;

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
		// 获取消息源发给本页面的参数
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String groupId = paramsMap.get("groupId");
		if (groupId != null) {
			this.groupId = Long.valueOf(groupId);
		} else {
			this.groupId = null;
		}
		startDate = DateUtil.getFirstDayOfCurrentMonth();
		endDate = new Date();
		selectGroupRecords();
		viewItemCountPaperService.updateMessage(this.groupId,
				curEmployee.getId(), curEmployee.getDeptId());
		// 之后 将其至空
		this.groupId = null;

	}

	public void selectNextMonth() {
		Map<String, Date> map = DateUtil
				.findNextMonthFirstAndLastDay(new Date());
		startDate = map.get("firstDayOfMonth");
		endDate = map.get("lastDayOfMonth");
	}

	public void selectLastMonth() {
		Map<String, Date> map = DateUtil
				.findLastMonthFirstAndLastDay(new Date());
		startDate = map.get("firstDayOfMonth");
		endDate = map.get("lastDayOfMonth");
	}

	/**
	 * 初始化sql语句
	 */
	public void initSql() {

	}

	/**
	 * 查询分组记录
	 */
	public void selectGroupRecords() {
		selectedGroupRow = new PensionHospitalizegroupExtend();
		groupCollectionInfo = "";
		oneGroupOlder = new ArrayList<PensionHospitalizeregisterExtend>();
		setRecords(viewItemCountPaperService.selectGroupRecords(groupId,
				startDate, endDate));
	}

	/**
	 * 查询被选中行对应的项目统计记录
	 * 
	 * @param selectedRow
	 */
	public void selectMedicalItemRecords(
			PensionHospitalizegroupExtend selectedRow) {
		viewedGroupRow = selectedRow;
		itemRecords = countMedicalItemService
				.selectMedicalItemRecord(viewedGroupRow);
		itemDetails = new ArrayList<PensionHospitalizeregisterExtend>();
	}

	/**
	 * 查询被选中行对应的项目明细
	 * 
	 * @param selectedRow
	 */
	public void selectMedicalItemDetails() {
		itemDetails = countMedicalItemService.selectMedicalItemDetails(
				viewedGroupRow.getId(), selectedItemCountRow.getItemId());
	}

	/**
	 * 清空选择条件
	 */
	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		groupId = null;
	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {

		oneGroupOlder = viewItemCountPaperService
				.selectRegisterRecords(selectedGroupRow.getId());
		groupCollectionInfo = viewItemCountPaperService
				.selectGroupCollection(selectedGroupRow.getId());
		selectedRows = null;
		disUpdateButton = true;
	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		selectedRows = null;
		disUpdateButton = true;
	}

	public void selecRegtRecord(SelectEvent e) {
		disUpdateButton = false;
	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRegRecord(UnselectEvent e) {
		disUpdateButton = true;
	}

	public void copyRecordUpdatedRow() {

		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		updatedRow = selectedRows;
		if (updatedRow == null || updatedRow.getId() == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"先选择老人", "");
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		} else {
			if (updatedRow.getBacktime() == null) {
				updatedRow.setBacktime(new Date());
			}
			request.addCallbackParam("success", true);
		}
	}

	/**
	 * 老人就医返院后的登记
	 */
	public void backedRegister() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();

		updatedRow.setIsback(1);
		try {
			viewItemCountPaperService.backedRegister(updatedRow);
			oneGroupOlder = viewItemCountPaperService
					.selectRegisterRecords(selectedGroupRow.getId());
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"登记成功！", "登记成功！");
			context.addMessage(null, message);
			request.addCallbackParam("success", true);
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					e.getMessage(), "");
			context.addMessage(null, message);
			e.printStackTrace();
			request.addCallbackParam("success", false);
		}

	}

	public PensionHospitalizeregisterExtend getUpdatedRow() {
		return updatedRow;
	}

	public void setUpdatedRow(PensionHospitalizeregisterExtend updatedRow) {
		this.updatedRow = updatedRow;
	}

	/**
	 * detail被选中时候的触发事件
	 */
	public void selectDetail(SelectEvent e) {
		selectMedicalItemDetails();

	}

	/**
	 * detail不给选中时的触发事件
	 */
	public void unSelectDetail(UnselectEvent e) {
		itemDetails = new ArrayList<PensionHospitalizeregisterExtend>();
	}

	public void setRecords(List<PensionHospitalizegroupExtend> records) {
		this.records = records;
	}

	public List<PensionHospitalizegroupExtend> getRecords() {
		return records;
	}

	public void setItemRecords(List<PensionItemcountExtend> itemRecords) {
		this.itemRecords = itemRecords;
	}

	public List<PensionItemcountExtend> getItemRecords() {
		return itemRecords;
	}

	public void setViewItemCountPaperService(
			ViewItemCountPaperService viewItemCountPaperService) {
		this.viewItemCountPaperService = viewItemCountPaperService;
	}

	public ViewItemCountPaperService getViewItemCountPaperService() {
		return viewItemCountPaperService;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public CountMedicalItemService getCountMedicalItemService() {
		return countMedicalItemService;
	}

	public void setCountMedicalItemService(
			CountMedicalItemService countMedicalItemService) {
		this.countMedicalItemService = countMedicalItemService;
	}

	public void setSelectedItemCountRow(
			PensionItemcountExtend selectedItemCountRow) {
		this.selectedItemCountRow = selectedItemCountRow;
	}

	public PensionItemcountExtend getSelectedItemCountRow() {
		return selectedItemCountRow;
	}

	public void setItemDetails(
			List<PensionHospitalizeregisterExtend> itemDetails) {
		this.itemDetails = itemDetails;
	}

	public List<PensionHospitalizeregisterExtend> getItemDetails() {
		return itemDetails;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}

	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}

	public void setSelectedGroupRow(
			PensionHospitalizegroupExtend selectedGroupRow) {
		this.selectedGroupRow = selectedGroupRow;
	}

	public PensionHospitalizegroupExtend getSelectedGroupRow() {
		return selectedGroupRow;
	}

	public void setOneGroupOlder(
			List<PensionHospitalizeregisterExtend> oneGroupOlder) {
		this.oneGroupOlder = oneGroupOlder;
	}

	public List<PensionHospitalizeregisterExtend> getOneGroupOlder() {
		return oneGroupOlder;
	}

	public void setSelectedRows(PensionHospitalizeregisterExtend selectedRows) {
		this.selectedRows = selectedRows;
	}

	public PensionHospitalizeregisterExtend getSelectedRows() {
		return selectedRows;
	}

	public void setDisUpdateButton(boolean disUpdateButton) {
		this.disUpdateButton = disUpdateButton;
	}

	public boolean isDisUpdateButton() {
		return disUpdateButton;
	}
}
