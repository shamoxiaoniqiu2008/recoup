package controller.olderManage;

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

import service.olderManage.CarePlanDomain;
import service.olderManage.DayCareDomain;
import service.olderManage.DayCarePlanService;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-18 上午08:30:44
 */

public class DayCarePlanController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient DayCarePlanService dayCarePlanService;
	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;

	/**
	 * 选中的护理计划
	 */
	private CarePlanDomain selectedRow;
	/**
	 * 护理计划列表
	 */
	private List<CarePlanDomain> carePlanList = new ArrayList<CarePlanDomain>();
	/**
	 * 新增护理计划对象
	 */
	private CarePlanDomain addCarePlan = new CarePlanDomain();

	private int operationFlag;
	// 新增时老人输入法
	private String olderSql;
	// 查询时老人输入法
	private String selectOlderSql;
	/**
	 * 护理记录列表
	 */
	private List<DayCareDomain> dayCareList = new ArrayList<DayCareDomain>();

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		selectOlderSql = "select distinct pd.older_id,pd.older_name,po.inputCode "
				+ "from pension_older po,pension_daycare_plan pd "
				+ "where pd.older_id=po.id and pd.cleared=2";
		olderSql = "select po.id,po.name,po.inputCode,pe.name as nurseName   "
				+ "from pension_employee pe,pension_livingrecord pl,pension_older po "
				+ "where pl.nurse_id=pe.id and pl.older_id=po.id and po.statuses in(3,4)";
		searchCarePlan();
	}

	/**
	 * 查询护理计划列表
	 */
	public void searchCarePlan() {
		carePlanList = dayCarePlanService.selectPlanList(olderId);
		dayCareList.clear();
		selectedRow = null;
	}

	/**
	 * 点击修改按钮触发
	 */
	public void showEditForm() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
		} else if (selectedRow.getGenerateFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"护理记录已生成，不可修改", ""));
		} else {
			addCarePlan = selectedRow;
			requestContext.addCallbackParam("edit", true);
		}
	}

	/**
	 * 点击删除按钮触发
	 */
	public void showDelForm() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
		} else if (selectedRow.getGenerateFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"护理记录已生成，不可删除", ""));
		} else {
			requestContext.addCallbackParam("del", true);
		}
	}

	/**
	 * 删除一条护理计划
	 */
	public void deleteApplication() {
		selectedRow.setCleared(1);
		dayCarePlanService.updateCarePlan(selectedRow);
		searchCarePlan();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "删除成功", ""));
	}

	/**
	 * 选中一行时触发
	 * 
	 */
	public void selectDaycareRecord(SelectEvent e) {
		dayCareList = dayCarePlanService.selectDaycareList(selectedRow.getId());
	}

	/**
	 * 未选中一行时触发
	 * 
	 */
	public void clearDaycareRecord(UnselectEvent e) {
		dayCareList = new ArrayList<DayCareDomain>();
	}

	/**
	 * 保存一条护理计划
	 * 
	 */
	public void saveCarePlan() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (addCarePlan.getEndTime().before(addCarePlan.getStartTime())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"结束日期不能小于开始日期", ""));
			requestContext.addCallbackParam("add", "fail");
		} else {
			addCarePlan.setCleared(2);
			addCarePlan.setGenerateFlag(2);
			addCarePlan.setFamilySign(1);
			dayCarePlanService.insertCarePlan(addCarePlan);
			searchCarePlan();
			requestContext.addCallbackParam("add", "success");
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"新增护理计划成功", ""));
		}
	}

	/**
	 * 保存并生成护理计划
	 */
	public void saveAndGenerateCarePlan() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (addCarePlan.getEndTime().before(addCarePlan.getStartTime())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"结束日期不能小于开始日期", ""));
			requestContext.addCallbackParam("add", "fail");
		} else {
			addCarePlan.setCleared(2);
			addCarePlan.setGenerateFlag(1);
			addCarePlan.setFamilySign(1);
			// 保存护理计划
			dayCarePlanService.insertAndGenerateCarePlan(addCarePlan);
			searchCarePlan();
			requestContext.addCallbackParam("add", "success");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "护理计划保存并生成成功",
							""));
		}
	}

	/**
	 * 根据开始日期和结束日期获取持续时间
	 */
	public void selectLastTime() {
		Date startDate = addCarePlan.getStartTime();
		Date endDate = addCarePlan.getEndTime();
		if (endDate != null && startDate != null) {
			if (endDate.before(startDate)) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"结束日期不能小于开始日期", ""));
				return;
			}
			addCarePlan
					.setLastDays(util.DateUtil.diffDate(endDate, startDate) + 1);
		}
		if (operationFlag == 2) {
			selectExecutePlan();
		}
	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		addCarePlan = new CarePlanDomain();
		addCarePlan.setSignFlag(true);
	}

	/**
	 * 修改一条护理计划
	 */
	public void updateCarePlan() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (addCarePlan.getEndTime().before(addCarePlan.getStartTime())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"结束日期不能小于开始日期", ""));
			requestContext.addCallbackParam("editSuccess", false);
		} else {
			dayCarePlanService.updateCarePlan(addCarePlan);
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "护理计划修改成功", ""));
			requestContext.addCallbackParam("editSuccess", true);
		}
	}

	/**
	 * 更新并生成护理计划
	 */
	public void updateAndGeneratePlan() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (addCarePlan.getEndTime().before(addCarePlan.getStartTime())) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"结束日期不能小于开始日期", ""));
			requestContext.addCallbackParam("editSuccess", false);
		} else {
			addCarePlan.setGenerateFlag(1);
			dayCarePlanService.updateCarePlan(addCarePlan);
			dayCarePlanService.insertDayCare(addCarePlan);
			dayCareList = dayCarePlanService.selectDaycareList(addCarePlan
					.getId());
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "护理计划修改并生成成功", ""));
			requestContext.addCallbackParam("editSuccess", true);
		}
	}

	/**
	 * 由持续时间、次数生成执行计划
	 */
	public void selectExecutePlan() {
		String plan = "";
		int countNumbers = addCarePlan.getCountNumbers();
		int lastDays = addCarePlan.getLastDays();
		int frequency = countNumbers / lastDays;
		if (frequency >= 1) {
			int surplus = countNumbers % lastDays;
			for (int i = 1; i <= lastDays; i++) {
				for (int j = 1; j <= frequency; j++) {
					plan = plan + i + ", ";
				}
				if (surplus != 0) {
					plan = plan + i + ", ";
					surplus--;
				}
			}
		} else {
			frequency = lastDays / countNumbers;
			int j = 1;
			for (int i = 1; i <= lastDays;) {
				if (j <= countNumbers) {
					plan = plan + i + ", ";
					i += frequency;
					++j;
				} else {
					break;
				}
			}
		}
		plan = plan.substring(0, plan.length() - 2);
		addCarePlan.setExecutePlan(plan);
	}

	/**
	 * 生成护理记录
	 */
	public void generateCarePlan() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选中一条记录！", ""));
			return;
		}
		if (selectedRow.getFamilySign() == 2) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "家属未签字确认！", ""));
			return;
		}
		if (selectedRow.getGenerateFlag() == 1) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "该护理计划已生成！", ""));
			return;
		}
		dayCarePlanService.insertDayCare(selectedRow);
		searchCarePlan();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"护理记录生成成功！", ""));
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

	public void setDayCarePlanService(DayCarePlanService dayCarePlanService) {
		this.dayCarePlanService = dayCarePlanService;
	}

	public DayCarePlanService getDayCarePlanService() {
		return dayCarePlanService;
	}

	public void setCarePlanList(List<CarePlanDomain> carePlanList) {
		this.carePlanList = carePlanList;
	}

	public List<CarePlanDomain> getCarePlanList() {
		return carePlanList;
	}

	public void setAddCarePlan(CarePlanDomain addCarePlan) {
		this.addCarePlan = addCarePlan;
	}

	public CarePlanDomain getAddCarePlan() {
		return addCarePlan;
	}

	public CarePlanDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(CarePlanDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public int getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(int operationFlag) {
		this.operationFlag = operationFlag;
	}

	public void setOlderSql(String olderSql) {
		this.olderSql = olderSql;
	}

	public String getOlderSql() {
		return olderSql;
	}

	public void setDayCareList(List<DayCareDomain> dayCareList) {
		this.dayCareList = dayCareList;
	}

	public List<DayCareDomain> getDayCareList() {
		return dayCareList;
	}

	public void setSelectOlderSql(String selectOlderSql) {
		this.selectOlderSql = selectOlderSql;
	}

	public String getSelectOlderSql() {
		return selectOlderSql;
	}

}
