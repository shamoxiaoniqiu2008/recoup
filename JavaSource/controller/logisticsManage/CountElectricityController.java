package controller.logisticsManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import service.logisticsManage.CountElectricityService;

import com.centling.his.util.SessionManager;

import domain.configureManage.PensionItempurse;
import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionOlderElectricity;

public class CountElectricityController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient CountElectricityService countElectricityService;

	// 查询条件
	private Long olderId;
	private String olderName;
	private Date startDate;
	private Date endDate;
	private String olderSql;
	private String freeOlderSql;

	// 主记录
	private List<PensionOlderElectricity> electricitys;
	private Float total;
	private List<PensionOlderElectricity> allElectricitys = new ArrayList<PensionOlderElectricity>();

	// 新增免费额度
	private List<PensionOlderElectricity> freeOlders;;
	private PensionOlderElectricity freeElectricity;
	private Long freeElectricityId;
	private String freeElectricityName;
	private String freeLimit;
	private Date freeStartDate;
	private Date freeEndDate;

	// 交电费
	private Long payOlderId;
	private String payOlderName;
	private List<PensionOlderElectricity> olderElectricitys;
	private List<PensionOlderElectricity> freeReocrds;
	private Float shouldPayAmount;
	private Float freeAmount;
	private Float payAmount;
	private Float totalFee;

	private PensionItempurse electricityPurse;

	private PensionEmployee employee;

	@PostConstruct
	public void init() {
		this.initDate();
		this.search();
		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		olderSql = "select p.older_id,p.older_name,po.inputCode "
				+ "from pension_older_electricity p,pension_older po "
				+ "where po.id=p.older_id group by p.older_id,p.older_name";
		String endTime = util.DateUtil.formatHDate(endDate);
		String startTime = util.DateUtil.formatHDate(startDate);
		freeOlderSql = "select p.id,p.older_id,p.older_name,"
				+ "po.inputCode,p.unit_price,"
				+ "p.room_id,p.floor_id,p.build_id,p.room_name,"
				+ "p.floor_name,p.build_name,"
				+ "sum(p.electricity_amount) as electricity_amount"
				+ " from pension_older_electricity p,pension_older po"
				+ " where po.id=p.older_id and  p.charge_flag = 2 "
				+ " and p.start_time<=DATE_FORMAT('" + endTime
				+ "','%Y-%m-%d %T')" + " and p.end_time>=DATE_FORMAT('"
				+ startTime + "','%Y-%m-%d %T')" + " group by p.older_id";
	}

	/**
	 * 将结束日期设置为今天，起始日期设置为一个月前的今天
	 */
	public void initDate() {

		Calendar calendar = Calendar.getInstance();
		endDate = new Date();
		calendar.setTime(endDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		endDate = calendar.getTime();
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		startDate = calendar.getTime();

	}

	/**
	 * 搜索
	 */
	public void search() {
		if (startDate == null || endDate == null) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "请输入起止时间！",
							""));
			this.initDate();
			return;
		} else if (startDate.after(endDate)) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"开始时间不能大于截止时间！", "开始时间不能大于截止时间！"));
			this.initDate();
			return;
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(endDate);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			endDate = calendar.getTime();
			electricitys = countElectricityService.search(olderId, startDate,
					endDate);
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "查询完成！",
							"查询完成！"));
		}
	}

	/**
	 * 按老人编号计算老人用电总额
	 * 
	 * @param id
	 */
	public void calculateTotalNum(Long id) {
		total = new Float(0);
		for (PensionOlderElectricity electricity : electricitys) {
			if (id.equals(electricity.getOlderId())) {
				total += electricity.getElectricityAmount();
			}
		}

	}

	/**
	 * 设置选中的老人的最大免费用电额度--即该老人的当前用电总额度
	 */
	public void settleMaxFreeLimit() {
		if (freeElectricityId == null) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "请选择老人！",
							"请选择老人！"));
		} else {
			freeLimit = null;
			allElectricitys = countElectricityService.search(null, startDate,
					endDate);
			for (PensionOlderElectricity freeOlder : allElectricitys) {
				if (freeOlder.getOlderId().equals(freeElectricityId)
						&& freeOlder.getChargeFlag() == 2) {
					freeElectricity = freeOlder;
				}
			}
		}
	}

	/**
	 * 插入免费试用的用电额度
	 */
	public void addFreeLimit() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (freeElectricity == null) {
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "请选择老人！",
								"请选择老人！"));
				request.addCallbackParam("validate", false);
			} else if (freeEndDate.before(startDate)
					|| freeStartDate.after(endDate)) {
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"选择的开始结束时间不正确，请重新选择！", "选择的开始结束时间不正确，请重新选择！"));
				request.addCallbackParam("validate", false);
			} else if (new Float(new Float(freeLimit)).floatValue() <= 0) {
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"免费额度请输入正数！", "免费额度请输入正数！"));
				request.addCallbackParam("validate", false);
			} else {
				electricityPurse = countElectricityService
						.selectElectricityPurse();
				if (electricityPurse == null) {
					electricityPurse = new PensionItempurse();
					FacesContext.getCurrentInstance().addMessage(
							"",
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"没有电费价表！", "没有电费价表！"));
					request.addCallbackParam("validate", false);
				} else {
					freeElectricity.setUnitPrice(electricityPurse.getPurse());
					// 将免费额度以负数计入数据库
					freeElectricity.setElectricityAmount(-new Float(freeLimit));
					freeElectricity.setId(null);
					freeElectricity.setStartTime(freeStartDate);
					freeElectricity.setEndTime(freeEndDate);
					freeElectricity.setInputuserId(employee.getId());
					freeElectricity.setInputuserName(employee.getName());
					countElectricityService
							.insertOlderElectricity(freeElectricity);
					this.search();
					FacesContext.getCurrentInstance().addMessage(
							"",
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"新增免费用电额度成功！", "新增免费用电额度成功！"));
					request.addCallbackParam("validate", true);
				}
			}
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "免费额度请输入数字！",
							"免费额度请输入数字！"));
			request.addCallbackParam("validate", false);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "新增免费用电额度失败！",
							"新增免费用电额度失败！"));
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * 在添加免费用电额度之前查询可以输入免费额度的老人 ! 该处查询的是 有未缴电费记录的老人
	 */
	public void beforeAddFreeElectricity() {
		RequestContext request = RequestContext.getCurrentInstance();
		freeOlders = countElectricityService
				.searchFreeOlder(startDate, endDate);
		if (freeOlders == null || freeOlders.size() < 1) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"没有可以添加免费额度的老人！", "没有可以添加免费额度的老人！"));
			request.addCallbackParam("validate", false);
		} else {
			freeElectricityId = null;
			freeElectricity = null;
			freeElectricityName = "";
			freeLimit = null;
			freeStartDate = startDate;
			freeEndDate = endDate;
			request.addCallbackParam("validate", true);
		}
	}

	/**
	 * 在电费缴费之前
	 */
	public void beforePayElectricity() {
		// 查出需要缴费的老人
		this.beforeAddFreeElectricity();
		// 选中的要缴费的老人
		payOlderId = null;
		payOlderName = "";
		// 老人历史记录
		olderElectricitys = new ArrayList<PensionOlderElectricity>();
		// 总额
		shouldPayAmount = new Float(0);
		// 免费度数
		freeAmount = new Float(0);
		// 应缴
		payAmount = new Float(0);

	}

	/**
	 * 根据选中的老人查询该老人 的应缴历史记录，总度数，免费额度，应缴
	 */
	public void showElectricityAmount() {
		if (payOlderId != null) {
			// 总额
			shouldPayAmount = new Float(0);
			// 免费度数
			freeAmount = new Float(0);
			// 应缴
			payAmount = new Float(0);
			// 应缴费用
			totalFee = new Float(0);
			olderElectricitys = new ArrayList<PensionOlderElectricity>();
			allElectricitys = countElectricityService.search(null, startDate,
					endDate);
			for (PensionOlderElectricity electricity : allElectricitys) {
				if (electricity.getChargeFlag() == 2
						&& payOlderId.equals(electricity.getOlderId())
						&& electricity.getElectricityAmount().floatValue() >= 0) {
					olderElectricitys.add(electricity);
					totalFee += electricity.getUnitPrice()
							* electricity.getElectricityAmount();
					shouldPayAmount += electricity.getElectricityAmount();
				} else if (electricity.getChargeFlag() == 2
						&& payOlderId.equals(electricity.getOlderId())
						&& electricity.getElectricityAmount().floatValue() < 0) {
					freeAmount -= electricity.getElectricityAmount();
					olderElectricitys.add(electricity);
					totalFee += electricity.getUnitPrice()
							* electricity.getElectricityAmount();
				}

			}
			payAmount = shouldPayAmount - freeAmount;
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "检索老人用电信息成功！",
							"检索老人用电信息成功！"));
			if (totalFee.floatValue() < 0) {
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"老人缴费金额小于0，不能缴费！", "老人缴费金额小于0，不能缴费！"));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "请先选择老人！",
							"请先选择老人！"));
		}
	}

	/**
	 * check老人应缴电费，金额不能小于0
	 */
	public void checkElectricityFee() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (totalFee.floatValue() > 0) {
			request.addCallbackParam("validate", true);
		} else {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"老人缴费金额小于0，不能缴费！", "老人缴费金额小于0，不能缴费！"));
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * 交电费
	 */
	public void payElectricity() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			electricityPurse = countElectricityService.selectElectricityPurse();
			if (electricityPurse == null) {
				electricityPurse = new PensionItempurse();
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"没有电费价表！", "没有电费价表！"));
			} else {
				countElectricityService.payElectricity(olderElectricitys,
						electricityPurse.getId(),
						electricityPurse.getItemname(), totalFee,
						employee.getId(), employee.getName());
				for (PensionOlderElectricity electricity : olderElectricitys) {
					for (PensionOlderElectricity temp : electricitys) {
						if (electricity.getId().equals(temp.getId())) {
							temp.setChargeFlag(1);
							break;
						}
					}
				}
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "收费成功！",
								"收费成功！"));
				request.addCallbackParam("validate", true);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "收费失败！",
							"收费失败！"));
			request.addCallbackParam("validate", false);
		}

	}

	public CountElectricityService getCountElectricityService() {
		return countElectricityService;
	}

	public void setCountElectricityService(
			CountElectricityService countElectricityService) {
		this.countElectricityService = countElectricityService;
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

	public List<PensionOlderElectricity> getElectricitys() {
		return electricitys;
	}

	public void setElectricitys(List<PensionOlderElectricity> electricitys) {
		this.electricitys = electricitys;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public List<PensionOlderElectricity> getFreeOlders() {
		return freeOlders;
	}

	public void setFreeOlders(List<PensionOlderElectricity> freeOlders) {
		this.freeOlders = freeOlders;
	}

	public PensionOlderElectricity getFreeElectricity() {
		return freeElectricity;
	}

	public void setFreeElectricity(PensionOlderElectricity freeElectricity) {
		this.freeElectricity = freeElectricity;
	}

	public String getFreeLimit() {
		return freeLimit;
	}

	public void setFreeLimit(String freeLimit) {
		this.freeLimit = freeLimit;
	}

	public Long getPayOlderId() {
		return payOlderId;
	}

	public void setPayOlderId(Long payOlderId) {
		this.payOlderId = payOlderId;
	}

	public List<PensionOlderElectricity> getOlderElectricitys() {
		return olderElectricitys;
	}

	public void setOlderElectricitys(
			List<PensionOlderElectricity> olderElectricitys) {
		this.olderElectricitys = olderElectricitys;
	}

	public Float getShouldPayAmount() {
		return shouldPayAmount;
	}

	public void setShouldPayAmount(Float shouldPayAmount) {
		this.shouldPayAmount = shouldPayAmount;
	}

	public Float getFreeAmount() {
		return freeAmount;
	}

	public void setFreeAmount(Float freeAmount) {
		this.freeAmount = freeAmount;
	}

	public Float getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Float payAmount) {
		this.payAmount = payAmount;
	}

	public Long getFreeElectricityId() {
		return freeElectricityId;
	}

	public void setFreeElectricityId(Long freeElectricityId) {
		this.freeElectricityId = freeElectricityId;
	}

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
	}

	public Date getFreeStartDate() {
		return freeStartDate;
	}

	public void setFreeStartDate(Date freeStartDate) {
		this.freeStartDate = freeStartDate;
	}

	public Date getFreeEndDate() {
		return freeEndDate;
	}

	public void setFreeEndDate(Date freeEndDate) {
		this.freeEndDate = freeEndDate;
	}

	public List<PensionOlderElectricity> getFreeReocrds() {
		return freeReocrds;
	}

	public void setFreeReocrds(List<PensionOlderElectricity> freeReocrds) {
		this.freeReocrds = freeReocrds;
	}

	public PensionItempurse getElectricityPurse() {
		return electricityPurse;
	}

	public void setElectricityPurse(PensionItempurse electricityPurse) {
		this.electricityPurse = electricityPurse;
	}

	public Float getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Float totalFee) {
		this.totalFee = totalFee;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getOlderSql() {
		return olderSql;
	}

	public void setOlderSql(String olderSql) {
		this.olderSql = olderSql;
	}

	public void setFreeOlderSql(String freeOlderSql) {
		this.freeOlderSql = freeOlderSql;
	}

	public String getFreeOlderSql() {
		return freeOlderSql;
	}

	public void setFreeElectricityName(String freeElectricityName) {
		this.freeElectricityName = freeElectricityName;
	}

	public String getFreeElectricityName() {
		return freeElectricityName;
	}

	public void setPayOlderName(String payOlderName) {
		this.payOlderName = payOlderName;
	}

	public String getPayOlderName() {
		return payOlderName;
	}

	public void setAllElectricitys(List<PensionOlderElectricity> allElectricitys) {
		this.allElectricitys = allElectricitys;
	}

	public List<PensionOlderElectricity> getAllElectricitys() {
		return allElectricitys;
	}

}
