package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.olderManage.DrugService;
import util.DateUtil;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.olderManage.PensionDosage;
import domain.olderManage.PensionDosagedetail;
import domain.system.PensionDept;

public class CheckDrugPaperController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient DrugService drugService;

	private List<PensionDept> nurseDepts;

	private PensionEmployee employee;

	private Date startDate;
	private Date endDate;

	private List<PensionDosage> dosages;

	private PensionDosage selectDosage;

	private List<PensionDosagedetail> dosageDetails;

	private PensionDosagedetail checkDosageDetail;

	private String nurseSQL;

	private Integer size;

	private Long dosageId;

	private final static Integer YES = 1;

	@PostConstruct
	public void init() {

		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String dosageIdStr = paramsMap.get("dosageId");
		if (dosageIdStr != null) {
			dosageId = Long.valueOf(dosageIdStr);
		} else {
			dosageId = null;
		}

		size = 0;

		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		endDate = new Date();
		startDate = DateUtil.getBeforeDay(endDate, 7);

		this.search();
	}

	public void search() {
		if (dosageId != null) {
			startDate = null;
			endDate = null;
			dosages = drugService.selectByPrimaryKey(dosageId);
			if (dosages.size() > 0) {
				selectDosage = dosages.get(0);
				dosageDetails = drugService.selectDosageDetails(selectDosage
						.getId());
			}
			dosageId = null;
		} else {
			dosages = drugService.select(startDate, endDate, null, null,
					employee.getId());
			selectDosage = null;
			dosageDetails = new ArrayList<PensionDosagedetail>();
		}
	}

	public void clearSearchForm() {
		startDate = null;
		endDate = null;
	}

	/**
	 * check 开始日期要不大于结束日期
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSearchDate(FacesContext context, UIComponent component,
			Object obj) {
		Calendar startDate = (Calendar) component
				.findComponent("searchForm:startDate");
		Date start = (Date) startDate.getValue();
		Calendar endDate = (Calendar) component
				.findComponent("searchForm:endDate");
		Date end = (Date) endDate.getValue();
		if (start != null && end != null && start.after(end)) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "日期时间应该不大于结束日期！",
					"日期时间应该不大于结束日期！");
			throw new ValidatorException(message);
		}
	}

	public void check() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (checkDosageDetail == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请先选中一条记录！", "请先选中一条记录！"));
			request.addCallbackParam("validate", false);
			return;
		} else {
			if (YES.equals(checkDosageDetail.getIsensure())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("已经服药确认的条目，不能重复核对！",
								"已经服药确认的条目，不能重复核对！"));
				request.addCallbackParam("validate", false);
				return;
			}
			if (YES.equals(checkDosageDetail.getIscheck())) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("已经核对的条目，不能重复核对！", "已经核对的条目，不能重复核对！"));
				request.addCallbackParam("validate", false);
				return;
			}
		}
		request.addCallbackParam("validate", true);
		checkDosageDetail.setNotes("");
	}

	public void saveCheck() {
		try {
			// 更新数据库 设置为已确认
			checkDosageDetail.setIscheck(YES);
			checkDosageDetail.setCheckresult("1");
			checkDosageDetail.setCheckerId(employee.getId());
			checkDosageDetail.setCheckerName(employee.getName());
			drugService.updateDosageDetailsCheck(checkDosageDetail);
			// 将消息设置为已读
			boolean checkFlag = true;
			for (PensionDosagedetail temp : dosageDetails) {
				if (temp.getIscheck() != null && temp.getIscheck() == 1) {
					continue;
				} else {
					checkFlag = false;
					break;
				}
			}
			if (checkFlag) {
				drugService.checkDosageDetails(selectDosage.getId(),
						employee.getId(), employee.getDeptId());
			}
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("核对配药单成功！", "核对配药单成功！"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("核对配药单出错！", "核对配药单出错！"));
		}
	}

	public void unEvaluateDosage() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			// 更新数据库 设置为已确认
			checkDosageDetail.setIscheck(YES);
			checkDosageDetail.setCheckresult("2");
			checkDosageDetail.setCheckerId(employee.getId());
			checkDosageDetail.setCheckerName(employee.getName());
			drugService.updateDosageDetailsCheck(checkDosageDetail);
			// 将消息设置为已读
			boolean checkFlag = true;
			for (PensionDosagedetail temp : dosageDetails) {
				if (temp.getIscheck() != null && temp.getIscheck() == 1) {
					continue;
				} else {
					checkFlag = false;
					break;
				}
			}
			if (checkFlag) {
				drugService.checkDosageDetails(selectDosage.getId(),
						employee.getId(), employee.getDeptId());
			}
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("核对配药单成功！", "核对配药单成功！"));
			request.addCallbackParam("success", true);

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("核对配药单出错！", "核对配药单出错！"));
		}
	}

	public void onDosageSelect(SelectEvent e) {
		dosageDetails = drugService.selectDosageDetails(selectDosage.getId());
		if (dosageDetails.size() < 1) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage("该配药单下没有发往本部门的条目信息！",
									"该配药单下没有发往本部门的条目信息！"));
		}
	}

	public void onDosageUnselect(UnselectEvent e) {
		dosageDetails = new ArrayList<PensionDosagedetail>();
	}

	public DrugService getDrugService() {
		return drugService;
	}

	public void setDrugService(DrugService drugService) {
		this.drugService = drugService;
	}

	public List<PensionDept> getNurseDepts() {
		return nurseDepts;
	}

	public void setNurseDepts(List<PensionDept> nurseDepts) {
		this.nurseDepts = nurseDepts;
	}

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
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

	public List<PensionDosage> getDosages() {
		return dosages;
	}

	public void setDosages(List<PensionDosage> dosages) {
		this.dosages = dosages;
	}

	public PensionDosage getSelectDosage() {
		return selectDosage;
	}

	public void setSelectDosage(PensionDosage selectDosage) {
		this.selectDosage = selectDosage;
	}

	public List<PensionDosagedetail> getDosageDetails() {
		return dosageDetails;
	}

	public void setDosageDetails(List<PensionDosagedetail> dosageDetails) {
		this.dosageDetails = dosageDetails;
	}

	public String getNurseSQL() {
		return nurseSQL;
	}

	public void setNurseSQL(String nurseSQL) {
		this.nurseSQL = nurseSQL;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Long getDosageId() {
		return dosageId;
	}

	public void setDosageId(Long dosageId) {
		this.dosageId = dosageId;
	}

	public void setCheckDosageDetail(PensionDosagedetail checkDosageDetail) {
		this.checkDosageDetail = checkDosageDetail;
	}

	public PensionDosagedetail getCheckDosageDetail() {
		return checkDosageDetail;
	}

}
