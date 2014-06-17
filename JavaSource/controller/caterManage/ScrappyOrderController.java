/**  
 * @Title: ScrappyOrderController.java 
 * @Package controller.caterManage 
 * @Description: 零点餐Controller
 * @author Justin.Su
 * @date 2013-9-4 下午7:56:07 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package controller.caterManage;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.caterManage.ScrappyOrderService;
import util.DateUtil;
import util.PmsException;
import util.SystemConfig;
import domain.caterManage.PensionDicCuisine;
import domain.caterManage.PensionFoodmenu;
import domain.caterManage.PensionOrder;

/**
 * @ClassName: ScrappyOrderController
 * @Description: 零点餐Controller
 * @author Justin.Su
 * @date 2013-9-4 下午7:56:07
 * @version V1.0
 */
public class ScrappyOrderController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */

	private static final long serialVersionUID = 350197285700739216L;
	// 引入零点餐Service
	private transient ScrappyOrderService scrappyOrderService;
	private transient SystemConfig systemConfig;

	// 定义菜单List add by justin.su 2013-09-04
	private List<PensionFoodmenu> pensionFoodmenuList = new ArrayList<PensionFoodmenu>();
	// 已点餐列表
	private List<PensionOrder> pensionOrderList = new ArrayList<PensionOrder>();
	// 选中的点餐项
	private PensionOrder selectedPensionOrder = new PensionOrder();

	// 定义老人姓名输入法变量
	private String nameToSql;
	private String fitcolName;
	private String displaycolName;

	// 定义菜名输入法变量
	private String foodToSql;
	private String fitcolFood;
	private String displaycolFood;

	// 老人ID
	private Long olderId;
	// 老人姓名
	private String olderName;
	// 菜名ID
	private Long foodId;
	// 菜名
	private String foodName;
	// 菜价下限
	private String startPrice;
	// 菜价上线
	private String endPrice;
	// 就餐时间
	private Date diningDate;
	// 就餐时间
	private String diningDateStr;
	// 已点菜品
	private String orderedFoodStr;
	// 是否确认
	private Integer isConfirm;

	// 换菜按钮可用性
	private boolean changeFlag = true;
	// 撤销点餐按钮可用性
	private boolean cancelFlag = true;

	// 编辑Dialog菜单ID
	private Long foodIdEdit;
	// 菜系List
	private List<PensionDicCuisine> pensionDicCuisineList = new ArrayList<PensionDicCuisine>();
	private String cuisinePara = null;
	// 点餐时刻（小时）
	private Long diningHour;
	// 点餐时刻（分钟）
	private Long diningMinitues;
	// 是否派送
	private boolean sendFlag = false;
	private String sendFlagStr = null;

	// 新增 点餐
	// 菜名ID
	private Long addFoodId;
	// 菜名
	private String addFoodName;
	// 数量
	private Integer addFoodNum;
	// 点餐列表
	private List<PensionOrder> addOrderList;

	@PostConstruct
	public void init() {
		initParam();
		// 加载所有的菜单列表
		// pensionFoodmenuList = scrappyOrderService.getAllFoodmenuList();
		// 加载所有的已点餐列表
		pensionOrderList = scrappyOrderService.getAllOrderedList();
		// 初始化老人姓名输入法
		initNameToSql();
		// 初始化菜名输入法
		initFoodToSql();
		// 初始化所有菜系
		initPensionDicCuisineList();
		// 初始化按钮可用性
		changeFlag = true;
		cancelFlag = true;
	}

	/**
	 * 
	 * @Title: initNameToSql
	 * @Description:老人姓名输入法
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-5 上午11:18:48
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
	 * @Title: initFoodToSql
	 * @Description:菜名输入法
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-5 上午11:43:03
	 * @version V1.0
	 */
	public void initFoodToSql() {
		foodToSql = " select" + " pf.id as foodId" + " ,pf.name as foodName"
				+ " ,pf.inputcode as inputCode" + " from"
				+ " pension_foodmenu pf" + " where" + " pf.cleared = 2";
		fitcolFood = "inputCode";
		displaycolFood = "编号:0,菜名:1,输入码:2";
	}

	/**
	 * 
	 * @Title: searchFoodMenu
	 * @Description: 查询按钮调用方法
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-5 下午5:05:48
	 * @version V1.0
	 */
	public void searchFoodMenu() {
		if (foodName == null || "".equals(foodName.trim())) {
			foodId = null;
		}
		pensionFoodmenuList = scrappyOrderService.getPensionFoodmenuList(
				foodId, startPrice, endPrice, cuisinePara);
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"查询完毕！", ""));
	}

	/**
	 * 
	 * @Title: initSearchParam
	 * @Description: 清空按钮调用方法
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-6 上午11:23:09
	 * @version V1.0
	 */
	public void initSearchParam() {
		foodId = null;
		foodName = null;
		startPrice = null;
		endPrice = null;
		cuisinePara = String.valueOf(0);
	}

	/**
	 * 
	 * @Title: scrappyOrder
	 * @Description: 确认点餐按钮调用方法
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-5 下午7:23:37
	 * @version V1.0
	 */
	public void scrappyOrder() {
		FacesContext fc = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (olderId == null) {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"请选择老人！", ""));
			request.addCallbackParam("older", true);
		} else if (diningDate == null) {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"请选择老人就餐时间！", ""));
			request.addCallbackParam("time", true);
		} else {
			if (checkDate()) {// 用餐日期大于当前日期
				if (checkTime()) {// 如果用餐日期是当天，点餐时间需早于系统参数规定的时间
					if (checkHourIsNull()) {// 用餐时间不能为空
						if (checkMinituesIsNull()) {// 用餐时间不能为空
							if (checkHourAndMinitues()) {// 用餐时间，小时数[0,23]，分钟数[0,59]
								request.addCallbackParam("canOrder", true);
								// 初始化新增对话框信息
								this.initAddForm();// 初始化新增菜品输入框
								addOrderList = new ArrayList<PensionOrder>();
								// 时间处理
								diningDate = dealDate();
							} else {
								request.addCallbackParam("canOrder", false);
								fc.addMessage(null, new FacesMessage(
										FacesMessage.SEVERITY_WARN,
										"小时为0~23之间，分钟为0~59之间！", ""));
							}
						} else {
							request.addCallbackParam("minute", true);
							fc.addMessage(null, new FacesMessage(
									FacesMessage.SEVERITY_WARN, "请输入分钟！", ""));
						}
					} else {
						request.addCallbackParam("hour", true);
						fc.addMessage(null, new FacesMessage(
								FacesMessage.SEVERITY_WARN, "请输入小时！", ""));
					}
				} else {
					request.addCallbackParam("canOrder", false);
					fc.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_WARN, "点餐时间必须在"
									+ getDiningTimePara() + "点之前！", ""));
				}
			} else {
				request.addCallbackParam("canOrder", false);
				fc.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_WARN, "点餐日期必须是今天之后的日期！", ""));
			}
		}
	}

	/**
	 * 初始化新增对话框信息
	 */
	public void initAddForm() {
		addFoodId = null;
		addFoodName = null;
		addFoodNum = 1;
	}

	/**
	 * 确认点餐
	 */
	/*
	 * public void orderMenu() { orderedFoodStr = ""; sendFlagStr = ""; if
	 * (sendFlag) { sendFlagStr = "是"; } else { sendFlagStr = "否"; } if
	 * (checkMenuList()) { for (PensionFoodmenu pfd : pensionFoodmenuList) { if
	 * (pfd.isSelectedFlag()) { if (orderedFoodStr == null ||
	 * orderedFoodStr.trim().equals("")) { if (pfd.getOrderNumber() == null) {
	 * pfd.setOrderNumber(new Long(1)); } orderedFoodStr = pfd.getName() + "," +
	 * pfd.getOrderNumber(); } else { if (pfd.getOrderNumber() == null) {
	 * pfd.setOrderNumber(new Long(1)); } orderedFoodStr = orderedFoodStr + ","
	 * + pfd.getName() + "," + pfd.getOrderNumber(); } } }
	 * RequestContext.getCurrentInstance().addCallbackParam("orderMenu", true);
	 * } else { FacesContext.getCurrentInstance().addMessage(null, new
	 * FacesMessage(FacesMessage.SEVERITY_WARN, "请选择菜品！", ""));
	 * RequestContext.getCurrentInstance().addCallbackParam("orderMenu", false);
	 * }
	 * 
	 * }
	 */

	/**
	 * 
	 * @Title: saveOrderRecord
	 * @Description: 保存点菜单
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-6 上午10:36:53
	 * @version V1.0
	 */
	public void saveOrderRecord() {
		FacesContext fc = FacesContext.getCurrentInstance();
		for (PensionFoodmenu pfd : pensionFoodmenuList) {
			if (pfd.isSelectedFlag()) {
				scrappyOrderService.saveFoodMenu(olderId, diningDate, pfd,
						sendFlag);
			} else {
			}
		}
		for (PensionFoodmenu pfd : pensionFoodmenuList) {
			if (pfd.isSelectedFlag()) {
				pfd.setSelectedFlag(false);
			} else {
			}
		}
		pensionOrderList = scrappyOrderService.getAllOrderedList();
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "老人"
				+ olderName + "点餐成功！", ""));
		olderId = null;
		diningDate = null;
		olderName = null;
		diningHour = null;
		diningMinitues = null;

	}

	/**
	 * 保存老人的点餐信息 add by mary.liu 2014-04-08
	 */
	public void saveOrderList() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (addOrderList.size() < 1) {
				fc.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "菜品数目不能小于1！", ""));
				RequestContext.getCurrentInstance().addCallbackParam(
						"saveOrder", false);
			} else {
				// 保存点餐记录
				scrappyOrderService.saveOrderList(addOrderList);
				// pensionOrderList = scrappyOrderService.getAllOrderedList();
				scrappyOrdered();// 查询所有点餐记录
				fc.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "老人" + olderName + "点餐成功！",
						""));
				RequestContext.getCurrentInstance().addCallbackParam(
						"saveOrder", true);
			}
		} catch (Exception e) {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"老人" + olderName + "点餐失败！", ""));
			RequestContext.getCurrentInstance().addCallbackParam("saveOrder",
					false);
		}
	}

	/**
	 * 
	 * @Title: checkMenuList
	 * @Description: 是否选中菜品
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-6 上午10:36:20
	 * @version V1.0
	 */
	public boolean checkMenuList() {
		int selectedNum = 0;
		for (PensionFoodmenu pfd : pensionFoodmenuList) {
			if (pfd.isSelectedFlag()) {
				selectedNum = selectedNum + 1;
			} else {
			}
		}

		if (selectedNum == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @Title: initParam
	 * @Description: 参数初始化
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-6 上午10:36:35
	 * @version V1.0
	 */
	public void initParam() {
		pensionFoodmenuList = null;
		olderId = null;
		olderName = null;
		startPrice = null;
		endPrice = null;
		diningDate = null;
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
		// FacesContext fc = FacesContext.getCurrentInstance();
		if (olderName == null || "".equals(olderName.trim())) {
			olderId = null;
		}
		pensionOrderList = scrappyOrderService.getAllOrderedListByCondition(
				olderId, diningDate, sendFlag ? 1 : 2, isConfirm);
		changeFlag = true;
		cancelFlag = true;
		selectedPensionOrder = new PensionOrder();
		// fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		// "查询完毕！", ""));
	}

	/**
	 * 
	 * @Title: setSelectedFlag
	 * @Description: 选中一行
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-7 下午2:31:06
	 * @version V1.0
	 */
	public void setSelectedFlag(SelectEvent event) {
		selectedPensionOrder
				.setTempSendFlag(selectedPensionOrder.getSendFlag() == 1 ? true
						: false);
		changeFlag = false;
		cancelFlag = false;
	}

	/**
	 * 
	 * @Title: setUnselectedFlag
	 * @Description: 未选中一行
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-7 下午2:31:28
	 * @version V1.0
	 */
	public void setUnselectedFlag(UnselectEvent event) {
		changeFlag = true;
		cancelFlag = true;
	}

	/**
	 * 
	 * @Title: deleteOrderedRecord
	 * @Description: 删除选中的点餐记录
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-7 下午2:48:33
	 * @version V1.0
	 */
	public void deleteOrderedRecord() {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (selectedPensionOrder.getIscomfirm() == 1) {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该条记录已经确认，不能删除！", ""));
		} else {
			scrappyOrderService.deleteOrdered(selectedPensionOrder);
			// 重新查询所有已点餐记录
			pensionOrderList = scrappyOrderService.getAllOrderedList();
			changeFlag = true;
			cancelFlag = true;
			fc.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "老人"
							+ scrappyOrderService
									.getOlderName(selectedPensionOrder
											.getOlderId())
							+ "所点的"
							+ scrappyOrderService
									.getFoodMenuName(selectedPensionOrder
											.getFoodMenuId()) + "删除成功！", ""));
		}

	}

	/**
	 * 
	 * @Title: updateOrderedRecord
	 * @Description: 修改点餐记录
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-19 下午12:14:57
	 * @version V1.0
	 */
	public void updateOrderedRecord() {
		FacesContext fc = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		Date sysDate = new Date();
		// 菜品名和用餐时间不能为空
		if (selectedPensionOrder.getMenuName() == null
				|| selectedPensionOrder.getEattime() == null) {
			request.addCallbackParam("canSaveFlag", false);
			return;
		} else if (selectedPensionOrder.getIscomfirm() == 1) {// 已经确认的记录不能再修改
			request.addCallbackParam("canSaveFlag", false);
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"该条记录已经确认，不能修改！", ""));
			return;
		} else if (selectedPensionOrder.getEattime().before(sysDate)) {// 就餐时间不能小于当前时间
			request.addCallbackParam("canSaveFlag", false);
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"就餐时间不能小于当前时间！", ""));
			return;
		} else {
			// 更新点餐记录
			scrappyOrderService.updateOrdered(foodIdEdit, selectedPensionOrder);
			// selectedPensionOrder.setSendFlagName(selectedPensionOrder
			// .isTempSendFlag() ? "是" : "否");
			scrappyOrdered();// 查询所有点餐记录
			request.addCallbackParam("canSaveFlag", true);
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"保存成功！", ""));
		}

	}

	/**
	 * 
	 * @Title: initPensionDicCuisineList
	 * @Description: 初始化所有菜系
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-9 下午5:28:39
	 * @version V1.0
	 */
	public void initPensionDicCuisineList() {
		pensionDicCuisineList = scrappyOrderService
				.getAllPensionDicCuisineList();
	}

	/**
	 * 
	 * @Title: checkHourAndMinitues
	 * @Description: check小时和分钟
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 下午3:08:22
	 * @version V1.0
	 */
	private boolean checkHourAndMinitues() {
		if (checkHour() && checkMinitues()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: checkHour
	 * @Description: check小时
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 下午3:08:39
	 * @version V1.0
	 */
	private boolean checkHour() {
		if ((diningHour == 0 || diningHour > 0)
				&& (diningHour < 23 || diningHour == 23)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: checkMinitues
	 * @Description: check分钟
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 下午3:08:49
	 * @version V1.0
	 */
	private boolean checkMinitues() {
		if ((diningMinitues == 0 || diningMinitues > 0)
				&& (diningMinitues < 59 || diningMinitues == 59)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: checkDate
	 * @Description: check当前日期与选中日期
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 下午3:56:17
	 * @version V1.0
	 */
	private boolean checkDate() {
		Date sysDate = new Date();
		if (diningDate.after(sysDate)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: checkTime
	 * @Description: check点餐时时间与系统参数设定的时间
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 下午3:56:35
	 * @version V1.0
	 */
	private boolean checkTime() {
		Calendar c = Calendar.getInstance();
		int sysHours = c.get(Calendar.HOUR_OF_DAY);
		String paraString = getDiningTimePara();
		int x = DateUtil.diffDate(diningDate, new Date());
		int paraHours = Long.valueOf(paraString).intValue();
		if (x > 0) {
			return true;
		} else {
			if (sysHours < paraHours) {
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * 
	 * @Title: getDiningTimePara
	 * @Description: 获取系统参数设定的点餐时间，当用餐时间当天时，限制点餐时间在系统参数规定的时间之前
	 * @param @return
	 * @return String
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 下午3:57:18
	 * @version V1.0
	 */
	private String getDiningTimePara() {
		String paraStr = null;
		try {
			paraStr = systemConfig.selectProperty("DINING_DATE_TIME");
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			FacesContext fc = FacesContext.getCurrentInstance();
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"获取系统参数出错！", ""));
		}
		return paraStr;
	}

	/**
	 * 
	 * @Title: checkHourIsNull
	 * @Description: check小时输入框是否为空
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 下午4:13:41
	 * @version V1.0
	 */
	private boolean checkHourIsNull() {
		if (diningHour != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: checkMinituesIsNull
	 * @Description: check分钟输入框是否为空
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 下午4:13:55
	 * @version V1.0
	 */
	private boolean checkMinituesIsNull() {
		if (diningMinitues != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: dealDate
	 * @Description: 日期处理
	 * @param @return
	 * @return Date
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 下午4:50:35
	 * @version V1.0
	 */
	private Date dealDate() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(diningDate);
		cld.set(Calendar.HOUR_OF_DAY, diningHour.intValue());
		cld.set(Calendar.MINUTE, diningMinitues.intValue());

		Date date = cld.getTime();
		SimpleDateFormat df = new SimpleDateFormat("HH:mm￼");
		String timePart = df.format(date);
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		String datePart = df2.format(diningDate);
		String dateTime = datePart + " " + timePart;
		SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm￼");
		Date newDateTime = null;
		try {
			newDateTime = df3.parse(dateTime);
		} catch (ParseException pe) {
			newDateTime = new Date();
		}
		return newDateTime;
	}

	/**
	 * 初始化查询条件
	 */
	public void initSearchPara() {
		// 老人ID
		olderId = null;
		// 老人姓名
		olderName = "";
		diningDate = null;
		diningHour = null;
		diningMinitues = null;
		isConfirm = null;
	}

	/**
	 * 为老人添加一条点餐记录 add by mary.liu 2014-04-08
	 */
	public void addOrder() {
		PensionOrder tempOrder = new PensionOrder();
		tempOrder.setCleared(2);
		tempOrder.setEattime(diningDate);
		tempOrder.setFoodMenuId(addFoodId);
		tempOrder.setIscomfirm(2);
		tempOrder.setIspay(2);
		tempOrder.setMenuName(addFoodName);
		tempOrder.setOlderId(olderId);
		tempOrder.setOlderName(olderName);
		tempOrder.setOrderNumber(addFoodNum);
		tempOrder.setSendFlag(sendFlag ? 1 : 2);
		addOrderList.add(tempOrder);
		this.initAddForm();
	}

	/**
	 * 删除一条点餐记录
	 * 
	 * @param order
	 */
	public void deleteOrder(PensionOrder order) {
		addOrderList.remove(order);
	}

	/**
	 * @return the scrappyOrderService
	 */
	public ScrappyOrderService getScrappyOrderService() {
		return scrappyOrderService;
	}

	/**
	 * @param scrappyOrderService
	 *            the scrappyOrderService to set
	 */
	public void setScrappyOrderService(ScrappyOrderService scrappyOrderService) {
		this.scrappyOrderService = scrappyOrderService;
	}

	/**
	 * @return the pensionFoodmenuList
	 */
	public List<PensionFoodmenu> getPensionFoodmenuList() {
		return pensionFoodmenuList;
	}

	/**
	 * @param pensionFoodmenuList
	 *            the pensionFoodmenuList to set
	 */
	public void setPensionFoodmenuList(List<PensionFoodmenu> pensionFoodmenuList) {
		this.pensionFoodmenuList = pensionFoodmenuList;
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
	 * @return the foodToSql
	 */
	public String getFoodToSql() {
		return foodToSql;
	}

	/**
	 * @param foodToSql
	 *            the foodToSql to set
	 */
	public void setFoodToSql(String foodToSql) {
		this.foodToSql = foodToSql;
	}

	/**
	 * @return the fitcolFood
	 */
	public String getFitcolFood() {
		return fitcolFood;
	}

	/**
	 * @param fitcolFood
	 *            the fitcolFood to set
	 */
	public void setFitcolFood(String fitcolFood) {
		this.fitcolFood = fitcolFood;
	}

	/**
	 * @return the displaycolFood
	 */
	public String getDisplaycolFood() {
		return displaycolFood;
	}

	/**
	 * @param displaycolFood
	 *            the displaycolFood to set
	 */
	public void setDisplaycolFood(String displaycolFood) {
		this.displaycolFood = displaycolFood;
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
	 * @return the foodId
	 */
	public Long getFoodId() {
		return foodId;
	}

	/**
	 * @param foodId
	 *            the foodId to set
	 */
	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	/**
	 * @return the startPrice
	 */
	public String getStartPrice() {
		return startPrice;
	}

	/**
	 * @param startPrice
	 *            the startPrice to set
	 */
	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}

	/**
	 * @return the endPrice
	 */
	public String getEndPrice() {
		return endPrice;
	}

	/**
	 * @param endPrice
	 *            the endPrice to set
	 */
	public void setEndPrice(String endPrice) {
		this.endPrice = endPrice;
	}

	public Date getDiningDate() {
		return diningDate;
	}

	public void setDiningDate(Date diningDate) {
		this.diningDate = diningDate;
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
	 * @return the orderedFoodStr
	 */
	public String getOrderedFoodStr() {
		return orderedFoodStr;
	}

	/**
	 * @param orderedFoodStr
	 *            the orderedFoodStr to set
	 */
	public void setOrderedFoodStr(String orderedFoodStr) {
		this.orderedFoodStr = orderedFoodStr;
	}

	/**
	 * @return the foodName
	 */
	public String getFoodName() {
		return foodName;
	}

	/**
	 * @param foodName
	 *            the foodName to set
	 */
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	/**
	 * @return the pensionOrderList
	 */
	public List<PensionOrder> getPensionOrderList() {
		return pensionOrderList;
	}

	/**
	 * @param pensionOrderList
	 *            the pensionOrderList to set
	 */
	public void setPensionOrderList(List<PensionOrder> pensionOrderList) {
		this.pensionOrderList = pensionOrderList;
	}

	/**
	 * @return the changeFlag
	 */
	public boolean isChangeFlag() {
		return changeFlag;
	}

	/**
	 * @param changeFlag
	 *            the changeFlag to set
	 */
	public void setChangeFlag(boolean changeFlag) {
		this.changeFlag = changeFlag;
	}

	/**
	 * @return the cancelFlag
	 */
	public boolean isCancelFlag() {
		return cancelFlag;
	}

	/**
	 * @param cancelFlag
	 *            the cancelFlag to set
	 */
	public void setCancelFlag(boolean cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	/**
	 * @return the selectedPensionOrder
	 */
	public PensionOrder getSelectedPensionOrder() {
		return selectedPensionOrder;
	}

	/**
	 * @param selectedPensionOrder
	 *            the selectedPensionOrder to set
	 */
	public void setSelectedPensionOrder(PensionOrder selectedPensionOrder) {
		this.selectedPensionOrder = selectedPensionOrder;
	}

	/**
	 * @return the foodIdEdit
	 */
	public Long getFoodIdEdit() {
		return foodIdEdit;
	}

	/**
	 * @param foodIdEdit
	 *            the foodIdEdit to set
	 */
	public void setFoodIdEdit(Long foodIdEdit) {
		this.foodIdEdit = foodIdEdit;
	}

	/**
	 * @return the pensionDicCuisineList
	 */
	public List<PensionDicCuisine> getPensionDicCuisineList() {
		return pensionDicCuisineList;
	}

	/**
	 * @param pensionDicCuisineList
	 *            the pensionDicCuisineList to set
	 */
	public void setPensionDicCuisineList(
			List<PensionDicCuisine> pensionDicCuisineList) {
		this.pensionDicCuisineList = pensionDicCuisineList;
	}

	public String getCuisinePara() {
		return cuisinePara;
	}

	public void setCuisinePara(String cuisinePara) {
		this.cuisinePara = cuisinePara;
	}

	/**
	 * @return the diningHour
	 */
	public Long getDiningHour() {
		return diningHour;
	}

	/**
	 * @param diningHour
	 *            the diningHour to set
	 */
	public void setDiningHour(Long diningHour) {
		this.diningHour = diningHour;
	}

	/**
	 * @return the diningMinitues
	 */
	public Long getDiningMinitues() {
		return diningMinitues;
	}

	/**
	 * @param diningMinitues
	 *            the diningMinitues to set
	 */
	public void setDiningMinitues(Long diningMinitues) {
		this.diningMinitues = diningMinitues;
	}

	public SystemConfig getSystemConfig() {
		return systemConfig;
	}

	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}

	/**
	 * @return the diningDateStr
	 */
	public String getDiningDateStr() {
		return diningDateStr;
	}

	/**
	 * @param diningDateStr
	 *            the diningDateStr to set
	 */
	public void setDiningDateStr(String diningDateStr) {
		this.diningDateStr = diningDateStr;
	}

	/**
	 * @return the sendFlag
	 */
	public boolean isSendFlag() {
		return sendFlag;
	}

	/**
	 * @param sendFlag
	 *            the sendFlag to set
	 */
	public void setSendFlag(boolean sendFlag) {
		this.sendFlag = sendFlag;
	}

	/**
	 * @return the sendFlagStr
	 */
	public String getSendFlagStr() {
		return sendFlagStr;
	}

	/**
	 * @param sendFlagStr
	 *            the sendFlagStr to set
	 */
	public void setSendFlagStr(String sendFlagStr) {
		this.sendFlagStr = sendFlagStr;
	}

	public Integer getIsConfirm() {
		return isConfirm;
	}

	public void setIsConfirm(Integer isConfirm) {
		this.isConfirm = isConfirm;
	}

	public Long getAddFoodId() {
		return addFoodId;
	}

	public void setAddFoodId(Long addFoodId) {
		this.addFoodId = addFoodId;
	}

	public String getAddFoodName() {
		return addFoodName;
	}

	public void setAddFoodName(String addFoodName) {
		this.addFoodName = addFoodName;
	}

	public Integer getAddFoodNum() {
		return addFoodNum;
	}

	public void setAddFoodNum(Integer addFoodNum) {
		this.addFoodNum = addFoodNum;
	}

	public List<PensionOrder> getAddOrderList() {
		return addOrderList;
	}

	public void setAddOrderList(List<PensionOrder> addOrderList) {
		this.addOrderList = addOrderList;
	}

}
