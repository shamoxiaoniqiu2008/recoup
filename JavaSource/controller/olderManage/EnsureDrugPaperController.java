package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.context.RequestContext;
import org.primefaces.event.UnselectEvent;

import service.olderManage.DrugService;
import util.DateUtil;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.olderManage.PensionDosagedetail;

public class EnsureDrugPaperController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient DrugService drugService;

	private PensionEmployee employee;

	private Date startDate;
	private Date endDate;
	private String ensureFlag;

	private List<PensionDosagedetail> dosageDetails;

	private PensionDosagedetail[] checkDosageDetails;

	private Integer size;

	private final static Integer YES = 1;

	@PostConstruct
	public void init() {

		ensureFlag = "2";
		startDate = DateUtil.parseDate(DateUtil.formatDate(new Date()));
		endDate = startDate;
		size = 0;

		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);

		this.search();
	}

	public void search() {
		dosageDetails = drugService.selectEnsureDosageDetail(startDate,
				endDate, employee.getId(), ensureFlag);
	}

	public void clearSearchForm() {
		startDate = null;
		endDate = null;
		ensureFlag = "";
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

	public void ensure() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (checkDosageDetails == null || checkDosageDetails.length == 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请先选中条目！", "请先选中条目！"));
			request.addCallbackParam("validate", false);
		} else {
			for (PensionDosagedetail dosageDetail : checkDosageDetails) {
				if (YES.equals(dosageDetail.getIsensure())) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage("已经服药确认的条目，不能重复确认！",
									"已经服药确认的条目，不能重复确认！"));
					request.addCallbackParam("validate", false);
					return;
				}
				if (dosageDetail.getCheckresult() == null
						|| "2".equals(dosageDetail.getCheckresult())) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage("未核对通过的配药单，不能确认！",
									"未核对通过的配药单，不能确认！"));
					request.addCallbackParam("validate", false);
					return;
				}
			}
			size = checkDosageDetails.length;
			request.addCallbackParam("validate", true);
		}
	}

	public void saveEnsure() {
		try {
			drugService.updateDosageDetailsEnsure(checkDosageDetails,
					employee.getId(), employee.getName());
			this.search();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage("确认 " + size + " 条配药单服药成功！", "确认 " + size
							+ " 条配药单服药成功！"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("确认配药单服药出错！", "确认配药单服药出错！"));
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

	public List<PensionDosagedetail> getDosageDetails() {
		return dosageDetails;
	}

	public void setDosageDetails(List<PensionDosagedetail> dosageDetails) {
		this.dosageDetails = dosageDetails;
	}

	public PensionDosagedetail[] getCheckDosageDetails() {
		return checkDosageDetails;
	}

	public void setCheckDosageDetails(PensionDosagedetail[] checkDosageDetails) {
		this.checkDosageDetails = checkDosageDetails;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setEnsureFlag(String ensureFlag) {
		this.ensureFlag = ensureFlag;
	}

	public String getEnsureFlag() {
		return ensureFlag;
	}

}
