/**  
 * @Title: CostAnalysisController.java 
 * @Package controller.caterManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-9 上午11:07:41 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package controller.caterManage;

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

import service.caterManage.CostAnalysisService;

/**
 * @ClassName: CostAnalysisController
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-9 上午11:07:41
 * @version V1.0
 */
public class CostAnalysisController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */

	private static final long serialVersionUID = 8646324820170184210L;

	private transient CostAnalysisService costAnalysisService;

	// 所有食材列表
	private List<ExtendForCostAnalysis> extendForCostAnalysisList = new ArrayList<ExtendForCostAnalysis>();
	// 提取的食材列表
	private List<ExtendForCostAnalysis> drawCostAnalysisList = new ArrayList<ExtendForCostAnalysis>();
	// 选中的可修改食材
	private ExtendForCostAnalysis selectCostAnalysis;
	// 菜品ID
	private Long foodId;
	// 菜品名称
	private String foodName;

	// 定义菜名输入法变量
	private String foodToSql;
	private String fitcolFood;
	private String displaycolFood;

	// 定义时间接收参数
	private Date startDate;
	private Date endDate;

	@PostConstruct
	public void init() {
		// 初始化菜品输入法
		initFoodToSql();
		// 查询所有的配料统计
		extendForCostAnalysisList = costAnalysisService
				.getAllCostAnalysisList();
	}

	/**
	 * 
	 * @Title: initFoodToSql
	 * @Description: 菜品输入法
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-9 下午2:37:47
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
	 * @Title: getCostAnalysisListByCondition
	 * @Description: 根据条件查询
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-9 下午2:50:54
	 * @version V1.0
	 */
	public void getCostAnalysisListByCondition() {
		if (foodName == null || "".equals(foodName.trim())) {
			foodId = null;
		}
		extendForCostAnalysisList = costAnalysisService
				.getAllCostAnalysisListByCondition(foodId, startDate, endDate);
		FacesContext fc = FacesContext.getCurrentInstance();
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"查询完毕！", ""));
	}

	/**
	 * 提取需要采购的食材列表
	 */
	public void drawCostAnalysisList() {
		drawCostAnalysisList = new ArrayList<ExtendForCostAnalysis>();
		for (ExtendForCostAnalysis costAnalysis : extendForCostAnalysisList) {
			if (costAnalysis.getGreensQty() > costAnalysis.getStorageQty()) {
				drawCostAnalysisList.add(costAnalysis);
			}
		}
		if (drawCostAnalysisList.size() > 0) {
			RequestContext.getCurrentInstance().addCallbackParam("validate",
					true);
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "没有需要采购的食材！",
							""));
			RequestContext.getCurrentInstance().addCallbackParam("validate",
					false);
		}
	}

	/**
	 * 选中一条采购单信息
	 */
	public void onCostAnalysisSelect(SelectEvent e) {

	}

	/**
	 * 取消选中一条采购单信息
	 */
	public void onCostAnalysisUnselect(UnselectEvent e) {
		selectCostAnalysis = null;
	}

	/**
	 * 更新采购单信息
	 */
	public void updateCostAnalysisList() {
		try {
			for (ExtendForCostAnalysis costAnalysis : drawCostAnalysisList) {
				if (costAnalysis.getFoodMenuId() == selectCostAnalysis
						.getFoodMenuId()) {
					int index = drawCostAnalysisList.indexOf(costAnalysis);
					drawCostAnalysisList.remove(index);
					drawCostAnalysisList.add(index, selectCostAnalysis);
					break;
				}
			}
			RequestContext.getCurrentInstance().addCallbackParam("validate",
					true);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"修改需要采购的食材出错！", ""));
			RequestContext.getCurrentInstance().addCallbackParam("validate",
					false);
		}
	}

	/**
	 * check 常见结果列表dateTable 是否选择了数据
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSelectData() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectCostAnalysis == null) {
			request.addCallbackParam("validate", false);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "请先选择数据！",
							"请先选择数据！"));
		} else {
			request.addCallbackParam("validate", true);
		}
	}

	/**
	 * @return the extendForCostAnalysisList
	 */
	public List<ExtendForCostAnalysis> getExtendForCostAnalysisList() {
		return extendForCostAnalysisList;
	}

	/**
	 * @param extendForCostAnalysisList
	 *            the extendForCostAnalysisList to set
	 */
	public void setExtendForCostAnalysisList(
			List<ExtendForCostAnalysis> extendForCostAnalysisList) {
		this.extendForCostAnalysisList = extendForCostAnalysisList;
	}

	public CostAnalysisService getCostAnalysisService() {
		return costAnalysisService;
	}

	public void setCostAnalysisService(CostAnalysisService costAnalysisService) {
		this.costAnalysisService = costAnalysisService;
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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<ExtendForCostAnalysis> getDrawCostAnalysisList() {
		return drawCostAnalysisList;
	}

	public ExtendForCostAnalysis getSelectCostAnalysis() {
		return selectCostAnalysis;
	}

	public void setSelectCostAnalysis(ExtendForCostAnalysis selectCostAnalysis) {
		this.selectCostAnalysis = selectCostAnalysis;
	}

	public void setDrawCostAnalysisList(
			List<ExtendForCostAnalysis> drawCostAnalysisList) {
		this.drawCostAnalysisList = drawCostAnalysisList;
	}

}
