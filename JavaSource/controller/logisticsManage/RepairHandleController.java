package controller.logisticsManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.dao.DataAccessException;

import service.logisticsManage.PensionRepairExtend;
import service.logisticsManage.RepairHandleService;
import service.olderManage.EntryVacationApplicationService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionGoodsDetail;

public class RepairHandleController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用来在页面中显示的list
	 */
	private List<PensionRepairExtend> records = new ArrayList<PensionRepairExtend>();
	/**
	 * 被处理人维修信息的明细记录
	 */
	private List<PensionGoodsDetail> insertedDetails = new ArrayList<PensionGoodsDetail>();
	/**
	 * 被临时添加的材料明细
	 */
	private PensionGoodsDetail insertedDetail = new PensionGoodsDetail();
	/**
	 * 被查看的明细记录
	 */
	private List<PensionGoodsDetail> details = new ArrayList<PensionGoodsDetail>();
	/**
	 * 被选中的记录
	 */
	private PensionRepairExtend selectedRow;
	/**
	 * 被处理的记录
	 */
	private PensionRepairExtend handledRow;
	/**
	 * 绑定关于老人信息的输入法
	 */
	private String olderNameSql;
	/**
	 * 绑定材料信息的输入法
	 */
	private String goodsSql;
	/**
	 * 起始时间 用作查询条件
	 */
	private Date startDate;
	/**
	 * 截止时间 用作查询条件
	 */
	private Date endDate;
	/**
	 * 老人主键用于查询条件
	 */
	private Long olderId;
	/**
	 * 维修人主键用于查询条件
	 */
	private Long repairerId;
	/**
	 * 维修记录的主键，用作查询条件
	 */
	private Long repairId;
	/**
	 * 是否已处理 用作查询条件
	 */
	private Integer handleFlag = 0;
	/**
	 * 修改和删除按钮是否可用
	 */
	private boolean disHandleButton = true;
	/**
	 * 缴费按钮是否可用
	 */
	private boolean disPayButton = true;
	/**
	 * 用来绑定insertForm中olderName是否可见
	 */
	private boolean olderNameRenderedFlag = true;
	/**
	 * 注入业务
	 */
	private transient RepairHandleService repairHandleService;
	private transient EntryVacationApplicationService entryVacationApplicationService;
	/**
	 * 获取当前用户
	 */
	private PensionEmployee curEmployee = (PensionEmployee) SessionManager
			.getSessionAttribute(SessionManager.EMPLOYEE);

	private String materialAmount;

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
		this.initDate();
		initSql();
		// 获取消息源发给本页面的参数
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String repairId = paramsMap.get("repairId");
		if (repairId != null) {
			this.repairId = Long.valueOf(repairId);
		} else {
			this.repairId = null;
		}
		// 根据参数 和其余默认的查询条件查询出所有的请假申请
		selectRecords();
		// 之后 将其至空
		this.repairId = null;

	}

	/**
	 * 将结束日期设置为今天，起始日期设置为一周前的今天
	 */
	public void initDate(){
		
		Calendar calendar=Calendar.getInstance();
		endDate=new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 0);
		endDate=calendar.getTime();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)-7);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate=calendar.getTime();
  
	}
	
	/**
	 * 初始化sql语句
	 */
	public void initSql() {
		olderNameSql = "select po.name	as oldName,"
				+ "pbuilding.name	  as buildName," + "pf.name		  as floorName,"
				+ "pr.name		  as roomName," + "pb.name		  as bedName,"
				+ "po.id       		  as olderId,"
				+ "po.inputCode		  as inputCode,"
				+ "pf.id              as floorId,"
				+ "pr.id              as roomId,"
				+ "pb.id              as bedId,"
				+ "pbuilding.id       as buildId" + " from pension_older po"
				+ " join pension_livingRecord plr "
				+ "on po.id = plr.older_id " + "join pension_bed pb "
				+ "on plr.bed_id = pb.id " + "join pension_room pr "
				+ "on pb.room_id = pr.id " + "join pension_floor pf "
				+ "on pr.floor_id = pf.id "
				+ "join pension_building pbuilding "
				+ "on pf.build_id = pbuilding.id " + "where po.cleared = 2 "
				+ "and po.statuses = 3";
		goodsSql = "select ps.item_id     as itemId,"
				+ "ps.item_name   as itemName," + "ps.storage_qty as itemQty "
				+ "from pension_storage ps " + "where ps.type_id = 2";// 类型为2
																		// 维修材料
	}

	/**
	 * 查询维修申请记录
	 */
	public void selectRecords() {
		disHandleButton = true;
		disPayButton = true;
		selectedRow = null;
		records = repairHandleService.selectRepairApplicationRecords(startDate,
				endDate, olderId, curEmployee.getId(), handleFlag, repairId);

	}

	/**
	 * 处理维修申请记录
	 */
	public void handleRecord() {
		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		String info = "处理成功！";
		try {

			repairHandleService.handleRepairApplicationRecord(handledRow,
					insertedDetails, curEmployee);
			selectRecords();
		} catch (DataAccessException e) {

			info = "添加操作写入数据库出现异常！";

		} catch (Exception e) {

			e.getStackTrace();
			info = "出现未知异常，请联系系统管理员！";

		}
		if (info.equals("处理成功！")) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					info, info);
			context.addMessage(null, message);
			request.addCallbackParam("success", true);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					info, info);
			context.addMessage(null, message);
			request.addCallbackParam("success", false);
		}
	}

	/**
	 * 查询选定维修的材料明细
	 */
	public void selectDetails(PensionRepairExtend handledRow) {
		details = repairHandleService.selectGoodsDetail(handledRow);
	}

	/**
	 * 新增一个材料明细
	 */
	public void insertDetail() {
		FacesContext context = FacesContext.getCurrentInstance();
		Float qty = 0.0f;
		try {
			qty = Float.valueOf(materialAmount);
			if (qty.compareTo(0.0f) <= 0) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_WARN, "材料数量请输入正值！", "");
				context.addMessage(null, message);
				return;
			}
			Float storageQty = repairHandleService
					.selectStorageQty(insertedDetail.getItemId());
			boolean isExist = false;
			for (PensionGoodsDetail detail : insertedDetails) {
				if (insertedDetail.getItemId().equals(detail.getItemId())) {

					Float goodsQty = detail.getItemQty() + qty;
					// 和库存进行比较
					if (goodsQty > storageQty) {
						FacesMessage message = new FacesMessage(
								FacesMessage.SEVERITY_WARN, "该材料数量不能大于其库存数量！",
								"该材料数量不能大于其库存数量！");
						context.addMessage(null, message);
						return;
					} else {
						detail.setItemQty(goodsQty);
						isExist = true;
						break;
					}
				}
			}
			if (!isExist) {
				if (qty > storageQty) {
					FacesMessage message = new FacesMessage(
							FacesMessage.SEVERITY_WARN, "该材料数量不能大于其库存数量！",
							"该材料数量不能大于其库存数量！");
					context.addMessage(null, message);
					return;
				} else {
					insertedDetail.setItemQty(qty);
					insertedDetails.add(insertedDetail);
				}
			}
			insertedDetail = new PensionGoodsDetail();
			materialAmount = null;
		} catch (NumberFormatException ex) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"材料数量请输入数字！", "");
			context.addMessage(null, message);
		}
	}

	/**
	 * 删除选定的材料明细
	 * 
	 * @param pensionGoodsDetail
	 */
	public void deleteDetail(PensionGoodsDetail pensionGoodsDetail) {
		insertedDetails.remove(pensionGoodsDetail);
	}

	/**
	 * 情况selectForm
	 */
	public void clearSelectForm() {
		startDate = null;
		endDate = null;
		olderId = null;
		repairerId = null;
		handleFlag = 0;
	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
		disHandleButton = false;
		if (selectedRow.getRepairType() != 2) {
			disPayButton = false;
		} else {
			disPayButton = true;
		}

	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
		disHandleButton = true;
		disPayButton = true;
	}

	/**
	 * 讲选中的值赋值给要更新的行
	 */
	public void copyRecordHandledRow() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		boolean success = true;
		handledRow = selectedRow;
		materialAmount = null;
		if (handledRow.getHandleFlag() == 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该记录已经处理，不能再处理!", "该记录已经处理，不能再处理!");
			context.addMessage(null, message);
			success = false;
			return;
		}
		Integer type = handledRow.getRepairType();
		if (type != null && type == 1) {
			olderNameRenderedFlag = true;
		} else {
			olderNameRenderedFlag = false;
		}
		insertedDetails.clear();
		insertedDetail = new PensionGoodsDetail();

		request.addCallbackParam("success", success);
	}

	/**
	 * 将十分已交款字段置为已交款
	 * 
	 * @throws PmsException
	 */
	public void payRepairApplicationRecord() throws PmsException {
		repairHandleService.payMoveApplicationRecord(selectedRow);
	}

	/**
	 * 判断一下被选中的行是否已经缴费
	 * 
	 * @throws PmsException
	 */
	public void checkBeHandledAndPayed() throws PmsException {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		boolean success = true;
		if (selectedRow.getRepairType() == 2) {

		}

		if (selectedRow.getHandleFlag() != null
				&& selectedRow.getHandleFlag() == 2) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"未处理的记录不能缴费！", "未处理的记录不能缴费！");
			context.addMessage(null, message);
			success = false;
		}
		if (selectedRow.getPayFlag() != null && selectedRow.getPayFlag() == 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
					"已缴费的记录不能再缴费！", "已缴费的记录不能再缴费！");
			context.addMessage(null, message);
			success = false;
		}
		if (success) {
			request.addCallbackParam("olderId", selectedRow.getOlderId());
			String priceTypeId = repairHandleService.getPriceTypeId();
			request.addCallbackParam("priceTypeId", priceTypeId);
			request.addCallbackParam("pensionRepairId", selectedRow.getId());
			request.addCallbackParam("tableName", "pension_repair");
		}

		request.addCallbackParam("success", success);
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

	public void setOlderNameSql(String olderNameSql) {
		this.olderNameSql = olderNameSql;
	}

	public String getOlderNameSql() {
		return olderNameSql;
	}

	public void setCurEmployee(PensionEmployee curEmployee) {
		this.curEmployee = curEmployee;
	}

	public PensionEmployee getCurEmployee() {
		return curEmployee;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public void setRecords(List<PensionRepairExtend> records) {
		this.records = records;
	}

	public List<PensionRepairExtend> getRecords() {
		return records;
	}

	public void setHandleFlag(Integer handleFlag) {
		this.handleFlag = handleFlag;
	}

	public Integer getHandleFlag() {
		return handleFlag;
	}

	public void setRepairerId(Long repairerId) {
		this.repairerId = repairerId;
	}

	public Long getRepairerId() {
		return repairerId;
	}

	public EntryVacationApplicationService getEntryVacationApplicationService() {
		return entryVacationApplicationService;
	}

	public void setEntryVacationApplicationService(
			EntryVacationApplicationService entryVacationApplicationService) {
		this.entryVacationApplicationService = entryVacationApplicationService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setSelectedRow(PensionRepairExtend selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionRepairExtend getSelectedRow() {
		return selectedRow;
	}

	public void setHandledRow(PensionRepairExtend handledRow) {
		this.handledRow = handledRow;
	}

	public PensionRepairExtend getHandledRow() {
		return handledRow;
	}

	public void setRepairHandleService(RepairHandleService repairHandleService) {
		this.repairHandleService = repairHandleService;
	}

	public RepairHandleService getRepairHandleService() {
		return repairHandleService;
	}

	public void setDisHandleButton(boolean disHandleButton) {
		this.disHandleButton = disHandleButton;
	}

	public boolean isDisHandleButton() {
		return disHandleButton;
	}

	public void setDetails(List<PensionGoodsDetail> details) {
		this.details = details;
	}

	public List<PensionGoodsDetail> getDetails() {
		return details;
	}

	public void setInsertedDetails(List<PensionGoodsDetail> insertedDetails) {
		this.insertedDetails = insertedDetails;
	}

	public List<PensionGoodsDetail> getInsertedDetails() {
		return insertedDetails;
	}

	public void setInsertedDetail(PensionGoodsDetail insertedDetail) {
		this.insertedDetail = insertedDetail;
	}

	public PensionGoodsDetail getInsertedDetail() {
		return insertedDetail;
	}

	public void setGoodsSql(String goodsSql) {
		this.goodsSql = goodsSql;
	}

	public String getGoodsSql() {
		return goodsSql;
	}

	public void setRepairId(Long repairId) {
		this.repairId = repairId;
	}

	public Long getRepairId() {
		return repairId;
	}

	public void setOlderNameRenderedFlag(boolean olderNameRenderedFlag) {
		this.olderNameRenderedFlag = olderNameRenderedFlag;
	}

	public boolean isOlderNameRenderedFlag() {
		return olderNameRenderedFlag;
	}

	public void setDisPayButton(boolean disPayButton) {
		this.disPayButton = disPayButton;
	}

	public boolean isDisPayButton() {
		return disPayButton;
	}

	public void setMaterialAmount(String materialAmount) {
		this.materialAmount = materialAmount;
	}

	public String getMaterialAmount() {
		return materialAmount;
	}
}
