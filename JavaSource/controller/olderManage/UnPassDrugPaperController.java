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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.olderManage.UnPassDrugPaperService;
import util.DateUtil;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.olderManage.PensionDosage;
import domain.olderManage.PensionDosagedetail;

public class UnPassDrugPaperController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient UnPassDrugPaperService unPassDrugPaperService;

	private PensionEmployee employee;

	// 查询

	private Date startDate;
	private Date endDate;

	// 主记录
	private List<PensionDosage> dosages;

	private PensionDosage selectDosage;

	private List<PensionDosagedetail> dosageDetails;

	@PostConstruct
	public void init() {

		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);

		endDate = new Date();
		startDate = util.DateUtil.getBeforeDay(endDate, 7);
		this.search();

	}

	public void search() {
		dosages = unPassDrugPaperService.select(startDate, endDate,
				employee.getId(), null);
		selectDosage = null;
		dosageDetails = new ArrayList<PensionDosagedetail>();
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

	public void selectLastDay() {
		if (endDate == null) {
			endDate = new Date();
		}
		endDate = DateUtil.getBeforeDay(endDate, -1);
		startDate = endDate;
		search();

	}

	public void selectNextDay() {
		if (endDate == null) {
			endDate = new Date();
		}
		endDate = DateUtil.getBeforeDay(endDate, 1);
		startDate = endDate;
		search();
	}

	public void onDosageSelect(SelectEvent e) {
		dosageDetails = unPassDrugPaperService
				.selectMyDosageDetails(selectDosage.getId());
	}

	public void onDosageUnselect(UnselectEvent e) {
		dosageDetails = new ArrayList<PensionDosagedetail>();
	}

	public void sendMessages() {
		try {
			if (selectDosage == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("请先选中一条配药单！", "请先选中一条配药单！"));
			} else {
				unPassDrugPaperService.sendMessages(selectDosage.getId(),
						employee.getName(), selectDosage.getCharterid(),
						dosageDetails);
				search();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("发送配药单消息成功！", "发送配药单消息成功！"));
			}
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("发送配药单消息失败！", "发送配药单消息失败！"));
		}
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

	public PensionEmployee getEmployee() {
		return employee;
	}

	public void setEmployee(PensionEmployee employee) {
		this.employee = employee;
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

	public UnPassDrugPaperService getUnPassDrugPaperService() {
		return unPassDrugPaperService;
	}

	public void setUnPassDrugPaperService(
			UnPassDrugPaperService unPassDrugPaperService) {
		this.unPassDrugPaperService = unPassDrugPaperService;
	}

}
