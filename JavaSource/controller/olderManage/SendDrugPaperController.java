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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.olderManage.DrugService;
import util.DateUtil;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.medicalManage.PensionDicDrugreceive;
import domain.olderManage.PensionDosage;
import domain.olderManage.PensionDosagedetail;

public class SendDrugPaperController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private transient DrugService drugService;

	private PensionEmployee employee;

	// 查询

	private Date startDate;
	private Date endDate;
	private Integer sendFlag;

	// 主记录
	private List<PensionDosage> dosages;

	private PensionDosage selectDosage;

	private List<PensionDosagedetail> dosageDetails;

	// 新增

	private Date eatTime;
	private Integer period;

	private List<PensionDosagedetail> addDosageDetails;

	private String addDeliverySQL;

	private final static Integer YES = 1;
	private final static Integer NO = 2;

	/**
	 * 生成配药单失败药品列表
	 */
	private List<PensionDicDrugreceive> drugFailList = new ArrayList<PensionDicDrugreceive>();

	/**
	 * 未生成配药单查询
	 */
	private List<PensionDosagedetail> unGenerateDosageDetails = new ArrayList<PensionDosagedetail>();

	@PostConstruct
	public void init() {

		employee = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);

		this.initSQL();
		endDate = new Date();
		startDate = util.DateUtil.getBeforeDay(endDate, 7);
		sendFlag = 2;
		this.search();

	}

	public void initSQL() {

		// 可以录入日常缴费的老人：pension_older 状态为3 在院 4 请假
		addDeliverySQL = "select pdd.id,pdd.delivery_name,pdd.nursedept_id,pdd.nursedept_name,pdd.charger_id,pdd.charger_name,pdd.input_code from pension_dic_delivery pdd where pdd.clreared = 2";
	}

	public void search() {
		if (!YES.equals(sendFlag) && !NO.equals(sendFlag)) {
			sendFlag = null;
		}
		dosages = drugService.select(startDate, endDate, sendFlag,
				employee.getId(), null);
		selectDosage = null;
		dosageDetails = new ArrayList<PensionDosagedetail>();
	}

	public void clearSearchForm() {
		startDate = null;
		endDate = null;
		sendFlag = null;
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

	public void add() {
		try {
			// 新增的配药单明细列表
			addDosageDetails = new ArrayList<PensionDosagedetail>();
			// 新增的配药单信息
			selectDosage = new PensionDosage();
			selectDosage.setCleared(NO);
			selectDosage.setIssend(NO);
			selectDosage.setSenderId(employee.getId());
			selectDosage.setSenderName(employee.getName());
			selectDosage.setSendtime(new Date());
			period = null;
			eatTime = null;
		} catch (Exception e) {

		}
	}

	public void onDosageSelect(SelectEvent e) {
		dosageDetails = drugService.selectMyDosageDetails(selectDosage.getId());
	}

	public void onDosageUnselect(UnselectEvent e) {
		dosageDetails = new ArrayList<PensionDosagedetail>();
	}

	/**
	 * 删除一条配药单
	 */
	public void delete() {
		try {
			drugService.deleteDosage(selectDosage.getId());
			dosages.remove(selectDosage);
			dosageDetails = new ArrayList<PensionDosagedetail>();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("删除配药单记录成功！", "删除配药单记录成功！"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("删除配药单记录失败！", "删除配药单记录失败！"));
		}
	}

	/**
	 * 保存新增的配药单
	 */
	public void saveDosage() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (addDosageDetails.size() < 1) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("配药单条数需要大于1！", "配药单条数需要大于1！"));
				request.addCallbackParam("validate", false);
				return;
			}
			drugService.insertDosage(selectDosage, addDosageDetails);
			this.search();
			request.addCallbackParam("validate", true);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("保存配药单成功！", "保存配药单成功！"));
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
			request.addCallbackParam("validate", false);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("保存配药单失败！", "保存配药单失败！"));
			request.addCallbackParam("validate", false);
		}
	}

	public void saveAndSend() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (addDosageDetails.size() < 1) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("配药单条数需要大于1！", "配药单条数需要大于1！"));
				request.addCallbackParam("validate", false);
				return;
			}
			drugService.insertDosage(selectDosage, addDosageDetails);
			drugService.sendMessages(selectDosage.getId(), employee.getName(),
					selectDosage.getCharterid());
			selectDosage.setIssend(YES);
			this.search();
			request.addCallbackParam("validate", true);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("保存配药单成功！", "保存配药单成功！"));
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
			request.addCallbackParam("validate", false);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("保存配药单失败！", "保存配药单失败！"));
			request.addCallbackParam("validate", false);
		}
	}

	public void sendMessages() {
		RequestContext request = RequestContext.getCurrentInstance();
		try {
			if (selectDosage.getId() == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("发送配药单消息失败！", "发送配药单消息失败！"));
				request.addCallbackParam("validate", false);
			} else {
				drugService.sendMessages(selectDosage.getId(),
						employee.getName(), selectDosage.getCharterid());
				selectDosage.setIssend(YES);
				search();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("发送配药单消息成功！", "发送配药单消息成功！"));
				request.addCallbackParam("validate", true);
			}
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("发送配药单消息失败！", "发送配药单消息失败！"));
			request.addCallbackParam("validate", false);
		}
	}

	/**
	 * check 常见结果列表dateTable 是否选择了数据 发送前的提示
	 * 
	 * @param context
	 * @param component
	 * @param obj
	 */
	public void checkSendData(FacesContext context, UIComponent component,
			Object obj) {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectDosage == null || selectDosage.getId() == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请先选择数据！", "请先选择数据！"));
			request.addCallbackParam("validate", false);
		} else if (YES.equals(selectDosage.getIssend())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("该配药单消息已发送！", "该配药单消息已发送！"));
			request.addCallbackParam("validate", false);
		} else {
			request.addCallbackParam("validate", true);
		}
	}

	public void checkModifyData(FacesContext context, UIComponent component,
			Object obj) {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectDosage == null || selectDosage.getId() == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请先选择数据！", "请先选择数据！"));
			request.addCallbackParam("validate", false);
		} else if (!drugService.checkIsChecked(selectDosage.getId())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("该配药单消息消息已确认，不能操作！", "该配药单消息消息已确认，不能操作！"));
			request.addCallbackParam("validate", false);
		} else {
			request.addCallbackParam("validate", true);
		}
	}

	/**
	 * 配药失败药品查询
	 */
	public void searchFailDrugList() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		unGenerateDosageDetails = new ArrayList<PensionDosagedetail>();
		// 未成功配药药品
		List<PensionDicDrugreceive> drugFailList = new ArrayList<PensionDicDrugreceive>();
		List<Object> drugList;
		try {
			drugList = drugService.createDetails(selectDosage, eatTime, period)
					.get("drugFail");
			for (Object temp : drugList) {
				PensionDicDrugreceive drug = (PensionDicDrugreceive) temp;
				drugFailList.add(drug);
			}
			// 成功配药药品
			List<Object> drugSuccessList = drugService.createDetails(
					selectDosage, eatTime, period).get("drugSuccess");
			for (Object temp : drugSuccessList) {
				PensionDosagedetail dosage = (PensionDosagedetail) temp;
				unGenerateDosageDetails.add(dosage);
			}
			for (PensionDicDrugreceive detail : drugFailList) {
				PensionDosagedetail dosage = new PensionDosagedetail();
				dosage.setIseatfood(detail.getBeforeafterFlag());
				dosage.setDrugName(detail.getDrugreceiveName());
				dosage.setOlderName(detail.getOlderName());
				dosage.setSingleDose(detail.getSingleDose());
				dosage.setUnit(detail.getUnit());
				dosage.setId(detail.getId());
				dosage.setNotes(detail.getNote());
				dosage.setPeriod(period);
				dosage.setEattime(eatTime);
				unGenerateDosageDetails.add(dosage);
			}
			if (unGenerateDosageDetails.size() > 0) {
				requestContext.addCallbackParam("show", true);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("不存在未生成的配药单！", ""));
				requestContext.addCallbackParam("show", false);
			}
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据配送类型，服药日期和服药时段生成配药单明细
	 */
	public void createDetails() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		try {
			// 未成功配药药品
			drugFailList = new ArrayList<PensionDicDrugreceive>();
			List<Object> drugList = drugService.createDetails(selectDosage,
					eatTime, period).get("drugFail");
			for (Object temp : drugList) {
				PensionDicDrugreceive drug = (PensionDicDrugreceive) temp;
				drugFailList.add(drug);
			}
			if (drugFailList.size() > 0) {
				requestContext.addCallbackParam("notice", true);
				return;
			}
			// 成功配药药品
			addDosageDetails = new ArrayList<PensionDosagedetail>();
			List<Object> drugSuccessList = drugService.createDetails(
					selectDosage, eatTime, period).get("drugSuccess");
			for (Object temp : drugSuccessList) {
				PensionDosagedetail dosage = (PensionDosagedetail) temp;
				addDosageDetails.add(dosage);
			}

			if (addDosageDetails.size() < 1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("您选择的配送类型下没有配药信息，请重新选择！",
								"您选择的配送类型下没有配药信息，请重新选择！"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("生成配药单明细成功！", "生成配药单明细成功！"));
			}
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
		}
	}

	/**
	 * 存在配药失败药品，确认继续配药
	 */
	public void confirmCreateDetails() {
		try {
			// 成功配药药品
			addDosageDetails = new ArrayList<PensionDosagedetail>();
			List<Object> drugSuccessList = drugService.createDetails(
					selectDosage, eatTime, period).get("drugSuccess");
			for (Object temp : drugSuccessList) {
				PensionDosagedetail dosage = (PensionDosagedetail) temp;
				addDosageDetails.add(dosage);
			}

			if (addDosageDetails.size() < 1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("您选择的配送类型下没有配药信息，请重新选择！",
								"您选择的配送类型下没有配药信息，请重新选择！"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("生成配药单明细成功！", "生成配药单明细成功！"));
			}
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage(), e.getMessage()));
		}
	}

	public DrugService getDrugService() {
		return drugService;
	}

	public void setDrugService(DrugService drugService) {
		this.drugService = drugService;
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

	public Integer getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
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

	public List<PensionDosagedetail> getAddDosageDetails() {
		return addDosageDetails;
	}

	public void setAddDosageDetails(List<PensionDosagedetail> addDosageDetails) {
		this.addDosageDetails = addDosageDetails;
	}

	public Date getEatTime() {
		return eatTime;
	}

	public void setEatTime(Date eatTime) {
		this.eatTime = eatTime;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getAddDeliverySQL() {
		return addDeliverySQL;
	}

	public void setAddDeliverySQL(String addDeliverySQL) {
		this.addDeliverySQL = addDeliverySQL;
	}

	public List<PensionDicDrugreceive> getDrugFailList() {
		return drugFailList;
	}

	public void setDrugFailList(List<PensionDicDrugreceive> drugFailList) {
		this.drugFailList = drugFailList;
	}

	public List<PensionDosagedetail> getUnGenerateDosageDetails() {
		return unGenerateDosageDetails;
	}

	public void setUnGenerateDosageDetails(
			List<PensionDosagedetail> unGenerateDosageDetails) {
		this.unGenerateDosageDetails = unGenerateDosageDetails;
	}

}
