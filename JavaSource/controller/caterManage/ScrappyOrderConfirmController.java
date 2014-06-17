/**  
 * @Title: ScrappyOrderConfirmController.java 
 * @Package controller.caterManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-5 下午1:26:04 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package controller.caterManage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.caterManage.ScrappyOrderConfirmService;
import service.financeManage.TempPayService;

import com.centling.his.util.SessionManager;

import domain.caterManage.PensionOrder;
import domain.financeManage.PensionTemppayment;
import domain.financeManage.PensionTemppaymentdetail;

/**
 * @ClassName: ScrappyOrderConfirmController
 * @Description: 零点餐确认Controller
 * @author Justin.Su
 * @date 2013-9-5 下午1:26:04
 * @version V1.0
 */
public class ScrappyOrderConfirmController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */

	private static final long serialVersionUID = 8072557531157980633L;
	// 零点餐确认Service
	private transient ScrappyOrderConfirmService scrappyOrderConfirmService;
	private transient TempPayService tempPayService;

	// 已点餐列表
	private List<PensionOrder> pensionOrderList = new ArrayList<PensionOrder>();
	// 老人ID
	private Long olderId;
	// 老人姓名
	private String olderName;
	// 就餐时间区间
	private Date startDiningDate;
	private Date endDiningDate;
	// 定义老人姓名输入法变量
	private String nameToSql;
	private String fitcolName;
	private String displaycolName;
	// 是否交款静态常量
	private String payFlag = "" + 3;
	// 是否确认
	private String confirmFlag = "" + 2;
	// 是否全选
	private boolean selectedAllFlag = false;
	// 是否全选当天
	private boolean selectedTodayFlag = false;

	private List<PensionOrder> selectedRows = new ArrayList<PensionOrder>();

	@PostConstruct
	public void init() {
		// 按条件查询
		scrappyOrderedByCondition();
		// 初始化老人姓名输入法
		initNameToSql();
	}

	/**
	 * 
	 * @Title: initNameToSql
	 * @Description: 老人姓名输入法
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-9 上午10:03:16
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
	 * 
	 * @Title: scrappyOrdered
	 * @Description: 查看所有已点餐列表
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-8 下午3:09:16
	 * @version V1.0
	 */
	public void scrappyOrdered() {
		FacesContext fc = FacesContext.getCurrentInstance();
		pensionOrderList = scrappyOrderConfirmService.getAllOrderedList();
		for (PensionOrder po : pensionOrderList) {
			if (po.getIscomfirm() == 1) {
				po.setSelectDisableFlag(true);
			}
		}
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"查询完毕！", ""));
	}

	/**
	 * 
	 * @Title: scrappyOrderedByCondition
	 * @Description: 根据条件查询
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-9 上午10:59:22
	 * @version V1.0
	 */
	public void scrappyOrderedByCondition() {
		// FacesContext fc = FacesContext.getCurrentInstance();
		if (olderName == null || "".equals(olderName.trim())) {
			olderId = null;
		}
		pensionOrderList = scrappyOrderConfirmService
				.getOrderedListByCondition(olderId, startDiningDate,
						endDiningDate, payFlag, confirmFlag);
		for (PensionOrder po : pensionOrderList) {
			if (po.getIscomfirm() == 1) {
				po.setSelectDisableFlag(true);
			}
		}
		// fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		// "查询完毕！", ""));
	}

	/**
	 * 
	 * @Title: saveTempPayInfo
	 * @Description: 确认点餐
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 上午11:21:35
	 * @version V1.0
	 */
	public void saveTempPayInfo() {
		List<PensionOrder> tempPensionOrderList = new ArrayList<PensionOrder>();
		for (PensionOrder p : selectedRows) {
			if (p.getIscomfirm().equals(2)) {
				tempPensionOrderList.add(p);
			}
		}
		if (tempPensionOrderList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"待确认点餐列表记录为空！！", ""));
			return;
		}
		// 计费操作
		savePay(tempPensionOrderList);
		// 更新确认状态
		scrappyOrderConfirmService.updateFlag(tempPensionOrderList);
		// pensionOrderList = scrappyOrderConfirmService.getAllOrderedList();l
		scrappyOrderedByCondition();
		selectedAllFlag = false;
		selectedTodayFlag = false;
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"点餐确认完毕！", ""));
	}

	/**
	 * 
	 * @Title: savePay
	 * @Description: 计费操作
	 * @param @param selectedPensionOrderList
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 上午11:19:26
	 * @version V1.0
	 */
	public void savePay(List<PensionOrder> selectedPensionOrderList) {
		Float sendPrice = scrappyOrderConfirmService.getSendPurseValue();
		Map<Long, List<PensionOrder>> tempMap = returnMap(selectedPensionOrderList);
		for (Object o : tempMap.keySet()) {
			PensionTemppayment tempPayment = new PensionTemppayment();
			List<PensionTemppaymentdetail> temppaymentdetails = new ArrayList<PensionTemppaymentdetail>();
			List<PensionOrder> poList = tempMap.get(o);
			Float f = (float) 0;
			for (PensionOrder por : poList) {
				f = f
						+ scrappyOrderConfirmService.getAmount(por
								.getFoodMenuId()) * por.getOrderNumber();
			}

			if (scrappyOrderConfirmService.judgeSendFlag(poList)) {
				f = f + sendPrice;
			}
			tempPayment = fillPensionTemppayment(poList.get(0), f);
			temppaymentdetails = fillPensionTemppaymentdetails(poList);
			Long pId = tempPayService.insertTempPayment(tempPayment,
					temppaymentdetails);
			// 更新临时收费表主键
			for (PensionOrder pou : poList) {
				scrappyOrderConfirmService.updateOrderPaymentId(pou, pId);
			}
		}
	}

	/**
	 * 
	 * @Title: returnMap
	 * @Description: 按老人ID返回分组
	 * @param @param selectedPensionOrderList
	 * @param @return
	 * @return Map<Long,List<PensionOrder>>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 上午11:19:45
	 * @version V1.0
	 */
	public Map<Long, List<PensionOrder>> returnMap(
			List<PensionOrder> selectedPensionOrderList) {
		Map<Long, List<PensionOrder>> map = new HashMap<Long, List<PensionOrder>>();
		for (PensionOrder po : selectedPensionOrderList) {
			if (map.containsKey(po.getOlderId())) {
				map.get(po.getOlderId()).add(po);
			} else {
				map.put(po.getOlderId(), new ArrayList<PensionOrder>());
				map.get(po.getOlderId()).add(po);
			}
		}
		return map;
	}

	/**
	 * 
	 * @Title: fillPensionTemppayment
	 * @Description: 获取计费主记录对象
	 * @param @param pensionOrder
	 * @param @param f
	 * @param @return
	 * @return PensionTemppayment
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 上午11:20:02
	 * @version V1.0
	 */
	public PensionTemppayment fillPensionTemppayment(PensionOrder pensionOrder,
			Float f) {
		PensionTemppayment tempPaymentForFill = new PensionTemppayment();
		tempPaymentForFill.setOlderId(pensionOrder.getOlderId());
		tempPaymentForFill.setStarttime(new Date());
		tempPaymentForFill.setEndtime(new Date());
		tempPaymentForFill.setTotalfees(f);
		tempPaymentForFill.setGeneratetime(new Date());
		tempPaymentForFill.setGeneratorId(SessionManager.getCurEmployee()
				.getId());
		tempPaymentForFill.setGeneratorName(SessionManager.getCurEmployee()
				.getName());
		tempPaymentForFill.setIspay(2);
		tempPaymentForFill.setIsclosed(2);
		tempPaymentForFill.setCleared(2);
		return tempPaymentForFill;
	}

	/**
	 * 
	 * @Title: fillPensionTemppaymentdetails
	 * @Description: 获取计费明细记录List
	 * @param @param porList
	 * @param @return
	 * @return List<PensionTemppaymentdetail>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 上午11:20:21
	 * @version V1.0
	 */
	public List<PensionTemppaymentdetail> fillPensionTemppaymentdetails(
			List<PensionOrder> porList) {
		List<PensionTemppaymentdetail> pensionTemppaymentdetailList = new ArrayList<PensionTemppaymentdetail>();
		for (PensionOrder pod : porList) {
			PensionTemppaymentdetail ptd = new PensionTemppaymentdetail();
			ptd.setOlderId(pod.getOlderId());
			ptd.setItemsId(pod.getFoodMenuId());
			ptd.setItemName(scrappyOrderConfirmService.getFoodMenu(
					pod.getFoodMenuId()).getName());
			ptd.setItemlfees(scrappyOrderConfirmService.getAmount(pod
					.getFoodMenuId()));
			ptd.setNumber((float) pod.getOrderNumber());
			ptd.setTotalfees(scrappyOrderConfirmService.getAmount(pod
					.getFoodMenuId()));
			ptd.setGeneratetime(new Date());
			ptd.setIspay(2);
			ptd.setRecordId(SessionManager.getCurEmployee().getId());
			ptd.setRecordtime(new Date());
			ptd.setSource(2);
			ptd.setTotalfees(scrappyOrderConfirmService.getAmount(pod
					.getFoodMenuId()) * pod.getOrderNumber());
			pensionTemppaymentdetailList.add(ptd);
		}
		return pensionTemppaymentdetailList;
	}

	/**
	 * 
	 * @Title: checkDialogShow
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-22 上午11:49:05
	 * @version V1.0
	 */
	public void checkDialogShow() {
		RequestContext request = RequestContext.getCurrentInstance();
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRows == null || selectedRows.size() == 0) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请选择要确认的项目！", ""));
			request.addCallbackParam("isOKFlag", false);
		} else {
			request.addCallbackParam("isOKFlag", true);
		}
	}

	/**
	 * 
	 * @Title: initPara
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-22 下午1:20:47
	 * @version V1.0
	 */
	public void initPara() {
		olderId = null;
		// 老人姓名
		olderName = "";
		// 就餐时间区间
		startDiningDate = null;
		endDiningDate = null;
		payFlag = "3";
		// 是否确认
		confirmFlag = "3";
	}

	/**
	 * 
	 * @Title: setSelectedAllFlag
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-23 上午11:25:26
	 * @version V1.0
	 */
	public void setSelectedAllFlag(ValueChangeEvent event) {
		boolean flag = (Boolean) event.getNewValue();
		selectedTodayFlag = false;
		// 如果是全选
		if (flag) {
			for (PensionOrder po : pensionOrderList) {
				if (po.getIscomfirm() != 1) {
					selectedRows.add(po);
				}
			}
		} else {// 如果是取消全选
			selectedRows = new ArrayList<PensionOrder>();
		}
	}

	/**
	 * 选中当天
	 * 
	 * @param event
	 */
	public void setSelectedTodayFlag(ValueChangeEvent event) {
		Date today = new Date();
		boolean flag = (Boolean) event.getNewValue();
		selectedRows = new ArrayList<PensionOrder>();
		selectedAllFlag = false;
		// 如果是选中当天
		if (flag) {
			for (PensionOrder po : pensionOrderList) {
				if (po.getIscomfirm() != 1
						&& this.isEqualDay(today, po.getEattime())) {
					selectedRows.add(po);
				}
			}
		}
	}

	/**
	 * 重写判断两个日期是不是一天
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public boolean isEqualDay(Date d1, Date d2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(d1).equals(sdf.format(d2));
	}

	/**
	 * 
	 * @Title: checkIsSelectAll
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-10 上午10:19:20
	 * @version V1.0
	 */
	public void checkIsSelectAll(ValueChangeEvent event) {
		int number = 0;
		int listSize = pensionOrderList.size();
		for (PensionOrder po : pensionOrderList) {
			if (po.isChoseFlag()) {
				number = number + 1;
			}
		}
		if (number == listSize) {
			selectedAllFlag = true;
		} else {
			selectedAllFlag = false;
		}

	}

	/**
	 * datatable被选中时候的触发事件
	 */
	public void selectRecord(SelectEvent e) {
	}

	/**
	 * datetable不给选中时的触发事件
	 */
	public void unSelectRecord(UnselectEvent e) {
	}

	/**
	 * @return the scrappyOrderConfirmService
	 */
	public ScrappyOrderConfirmService getScrappyOrderConfirmService() {
		return scrappyOrderConfirmService;
	}

	/**
	 * @param scrappyOrderConfirmService
	 *            the scrappyOrderConfirmService to set
	 */
	public void setScrappyOrderConfirmService(
			ScrappyOrderConfirmService scrappyOrderConfirmService) {
		this.scrappyOrderConfirmService = scrappyOrderConfirmService;
	}

	public List<PensionOrder> getPensionOrderList() {
		return pensionOrderList;
	}

	public void setPensionOrderList(List<PensionOrder> pensionOrderList) {
		this.pensionOrderList = pensionOrderList;
	}

	/**
	 * @return the olderId
	 */
	public Long getOlderId() {
		return olderId;
	}

	/**
	 * @param olderId
	 *            the olderId to set
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
	 * @param olderName
	 *            the olderName to set
	 */
	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	/**
	 * @return the startDiningDate
	 */
	public Date getStartDiningDate() {
		return startDiningDate;
	}

	/**
	 * @param startDiningDate
	 *            the startDiningDate to set
	 */
	public void setStartDiningDate(Date startDiningDate) {
		this.startDiningDate = startDiningDate;
	}

	/**
	 * @return the endDiningDate
	 */
	public Date getEndDiningDate() {
		return endDiningDate;
	}

	/**
	 * @param endDiningDate
	 *            the endDiningDate to set
	 */
	public void setEndDiningDate(Date endDiningDate) {
		this.endDiningDate = endDiningDate;
	}

	/**
	 * @return the nameToSql
	 */
	public String getNameToSql() {
		return nameToSql;
	}

	/**
	 * @param nameToSql
	 *            the nameToSql to set
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
	 * @param fitcolName
	 *            the fitcolName to set
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
	 * @param displaycolName
	 *            the displaycolName to set
	 */
	public void setDisplaycolName(String displaycolName) {
		this.displaycolName = displaycolName;
	}

	/**
	 * @return the payFlag
	 */
	public String getPayFlag() {
		return payFlag;
	}

	/**
	 * @param payFlag
	 *            the payFlag to set
	 */
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	/**
	 * @return the confirmFlag
	 */
	public String getConfirmFlag() {
		return confirmFlag;
	}

	/**
	 * @param confirmFlag
	 *            the confirmFlag to set
	 */
	public void setConfirmFlag(String confirmFlag) {
		this.confirmFlag = confirmFlag;
	}

	/**
	 * @return the tempPayService
	 */
	public TempPayService getTempPayService() {
		return tempPayService;
	}

	/**
	 * @param tempPayService
	 *            the tempPayService to set
	 */
	public void setTempPayService(TempPayService tempPayService) {
		this.tempPayService = tempPayService;
	}

	/**
	 * @return the selectedAllFlag
	 */
	public boolean isSelectedAllFlag() {
		return selectedAllFlag;
	}

	/**
	 * @param selectedAllFlag
	 *            the selectedAllFlag to set
	 */
	public void setSelectedAllFlag(boolean selectedAllFlag) {
		this.selectedAllFlag = selectedAllFlag;
	}

	public boolean isSelectedTodayFlag() {
		return selectedTodayFlag;
	}

	public void setSelectedTodayFlag(boolean selectedTodayFlag) {
		this.selectedTodayFlag = selectedTodayFlag;
	}

	public List<PensionOrder> getSelectedRows() {
		return selectedRows;
	}

	public void setSelectedRows(List<PensionOrder> selectedRows) {
		this.selectedRows = selectedRows;
	}

}
