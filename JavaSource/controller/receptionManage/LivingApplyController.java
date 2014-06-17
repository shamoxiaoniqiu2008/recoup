package controller.receptionManage;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import service.receptionManage.LivingApplyService;
import service.receptionManage.PensionOlderDomain;
import util.IDCardUtil;
import util.IdcardValidator;
import util.PmsException;
import util.Spell;
import domain.dictionary.PensionDicNation;
import domain.dictionary.PensionDicPolitics;
import domain.dictionary.PensionDicRelationship;
import domain.receptionManage.PensionLivingapply;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-28 下午01:16:44
 */

public class LivingApplyController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient LivingApplyService livingApplyService;

	private String teststr;

	/**
	 * 查询条件
	 */
	private String personState;
	private String applier;
	private String olderName;
	private Long olderId;
	private String types;
	private Date StartDate;
	private Date endDate;
	/**
	 * 所有申请记录列表
	 */
	private List<PensionOlderDomain> pensionOlderList = new ArrayList<PensionOlderDomain>();
	/**
	 * 选中申请记录
	 */
	private PensionOlderDomain selectedRow;
	/**
	 * 操作标记 1 为新增 2 为修改
	 */
	private Short operationId;
	/**
	 * 新增和修改对话框申请记录
	 */
	private PensionOlderDomain addPensionOlder = new PensionOlderDomain();
	/**
	 * 民族列表
	 */
	private List<PensionDicNation> nationList;
	/**
	 * 政治面貌列表
	 */
	private List<PensionDicPolitics> politicList;
	/**
	 * 申请人与老人关系记录表
	 */
	private List<PensionDicRelationship> relationshipList;
	private PensionLivingapply application;
	private boolean applyFlag;
	private boolean noticeFlag;
	private String olderSQL;
	private Boolean addOlderStatuseFlag;
	private Boolean clearFlag;

	@PostConstruct
	public void init() {
		Map<String, String> paramsMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		String oldId = paramsMap.get("olderId");
		if (oldId != null) {
			olderId = Long.valueOf(oldId);
		} else {
			olderId = null;
		}
		initNation();
		initPolitics();
		initRelationShip();
		searchAllApplications();
		applyFlag = true;
		noticeFlag = true;
		initSql();
		operationId = 3;
	}

	/**
	 * 初始化输入法sql
	 */
	public void initSql() {
		olderSQL = "select po.id, po.name,po.inputCode from pension_older po,pension_livingapply pl where pl.older_id=po.id and po.cleared=2 and pl.cleared=2 and po.statuses in(1,2)";
	}

	/**
	 * 查询出所有的老人申请记录
	 */
	public void searchAllApplications() {
		pensionOlderList = livingApplyService.selectAllApplications(olderId);
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		applier = "";
		olderName = "";
		StartDate = null;
		endDate = null;
		personState = "";
		types = "";
		olderId = null;
	}

	/**
	 * 由老人姓名获取拼音码
	 */
	public void updateInputCode() {
		addPensionOlder.setInputcode(Spell.getFirstSpell(addPensionOlder
				.getName()));
	}

	/**
	 * 根据条件查询老人入住申请记录
	 */
	public void searchApplications() {
		if (olderName == "" || olderName == null) {
			olderId = null;
		}
		pensionOlderList = livingApplyService.selectApplications(personState,
				applier, olderId, types, StartDate, endDate);
		selectedRow = null;
	}

	/**
	 * 选中一条记录，设置按钮可用状态
	 */
	public void setEnableFlag(SelectEvent event) {
	}

	/**
	 * 未选中一条记录，设置按钮可用状态
	 */
	public void setUnableFlag(UnselectEvent event) {
	}

	/**
	 * 新增触发事件
	 */
	public void showAddForm() {
		operationId = 1;
		clearAddForm();
		addOlderStatuseFlag = true;
		addPensionOlder.setStatuses(1);
		clearFlag = false;

	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		addPensionOlder = new PensionOlderDomain();
		initRelationShip();
		initPolitics();
		initNation();
	}

	/**
	 * 修改触发事件
	 */
	public void showEditForm() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
			request.addCallbackParam("show", false);
		} else {
			operationId = 2;
			addPensionOlder = selectedRow;
			addOlderStatuseFlag = true;
			request.addCallbackParam("show", true);
			clearFlag = true;
		}
	}

	/**
	 * 发送评估触发事件
	 */
	public void showEvaluate() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
		} else if (selectedRow.getStatuses() == 2
				&& selectedRow.getEvaluateresult() == 1) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "该老人入住申请已评估通过！", ""));
		} else {
			operationId = 3;
			sentMessage();
		}
	}

	/**
	 * 删除触发事件
	 */
	public void showDelForm() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
			request.addCallbackParam("show", false);
		} else {
			request.addCallbackParam("show", true);
		}
	}

	/**
	 * 保存并提交入住申请
	 */
	public void saveAndSendApplication() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		getApplication();
		if (operationId == 1) {
			addPensionOlder.setCleared(2);
			livingApplyService.insertPensionOlder(addPensionOlder, application);
			sentMessage();
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, addPensionOlder.getName()
							+ "入住申请增加成功!", ""));
			searchApplications();
		} else {
			livingApplyService.updatePensionOlder(addPensionOlder);
			application.setApplytime(null);
			livingApplyService.updateApplication(application);
			sentMessage();
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, addPensionOlder.getName()
							+ "入住申请修改成功!", ""));
			addPensionOlder = new PensionOlderDomain();
			request.addCallbackParam("edit", true);
			searchApplications();
		}
	}

	/**
	 * 发送通知
	 * 
	 * @throws PmsException
	 */
	public void sentMessage() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (operationId == 1 || operationId == 2) {
			try {
				Long olderId = livingApplyService.selectOlderId(
						addPensionOlder.getName(),
						addPensionOlder.getIdcardnum());
				addPensionOlder.setId(olderId);
				livingApplyService.sentMessage(addPensionOlder);
				searchApplications();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"入住申请消息发送成功!", ""));
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e
								.getMessage(), ""));
				e.printStackTrace();
			}
		} else if (operationId == 3) {
			if (selectedRow == null) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_INFO, "请先选中一条待评估记录！", ""));
			} else {
				try {
					livingApplyService.sentMessage(selectedRow);
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"入住申请消息发送成功!", ""));
					// searchApplications();
				} catch (PmsException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, e
									.getMessage(), ""));
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 逻辑删除申请记录
	 */
	public void deleteApplication() {
		addPensionOlder = selectedRow;
		getApplication();
		livingApplyService.deleteApplication(application);
		searchApplications();
	}

	/**
	 * @Description: 初始化民族列表
	 */
	public void initNation() {
		nationList = livingApplyService.selectNationList();
	}

	/**
	 * @Description: 初始化政治面貌列表
	 */
	public void initPolitics() {
		politicList = livingApplyService.selectPoliticList();
	}

	/**
	 * @Description: 初始化关系列表
	 */
	public void initRelationShip() {
		relationshipList = livingApplyService.selectRelationshipList();
	}

	/**
	 * 保存一条入住申请
	 */
	public void saveApplication() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		getApplication();
		if (operationId == 1) {
			addPensionOlder.setCleared(2);
			livingApplyService.insertPensionOlder(addPensionOlder, application);
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, addPensionOlder.getName()
							+ "入住申请已增加", ""));
			searchApplications();
		} else {
			livingApplyService.updatePensionOlder(addPensionOlder);
			application.setApplytime(null);
			livingApplyService.updateApplication(application);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, addPensionOlder.getName()
							+ "入住申请已修改", ""));
			addPensionOlder = new PensionOlderDomain();
			request.addCallbackParam("edit", true);
			searchApplications();
		}
	}

	/**
	 * 申请记录封装
	 * 
	 * @return
	 */
	public PensionLivingapply getApplication() {
		application = new PensionLivingapply();
		application.setId(addPensionOlder.getApplyId());
		application.setApplytime(new Date());
		application.setCleared(2);
		application.setName(addPensionOlder.getApplyName());
		application.setNotes(addPensionOlder.getNotes());
		application.setPhonenum(addPensionOlder.getPhoneNum());
		application.setRelationship(addPensionOlder.getRelationShip());
		application.setSex(addPensionOlder.getApplySex());
		application.setOlderId(addPensionOlder.getId());
		application.setPensionCategary(addPensionOlder.getPensionCategary());
		return application;
	}
	
	/**
	 * 
	* @Title: getBirthdayAndSex 
	* @Description: TODO
	* @param 
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2014-6-4 上午10:31:51
	* @version V1.0
	 */
	public void getBirthdayAndSex(){
		if(addPensionOlder == null){
			return;
		}else{
			String idcard = addPensionOlder.getIdcardnum();
			try {
				IdcardValidator validator = new IdcardValidator();
				if (validator.isValidatedAllIdcard(idcard)) {
					if (idcard.length() == 15) {
						idcard = validator.convertIdcarBy15bit(idcard);
					}
					// 获取性别
					String id17 = idcard.substring(16, 17);
					if (Integer.parseInt(id17) % 2 != 0) {
						addPensionOlder.setSex(1);
					} else {
						addPensionOlder.setSex(2);
					}
					// 获取出生日期
					String birthday = idcard.substring(6, 14);
					Date birthdate = new SimpleDateFormat("yyyyMMdd")
							.parse(birthday);
					addPensionOlder.setBirthday(birthdate);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public void selectAge(SelectEvent selectEvent) {
		int age = util.DateUtil.selectAge(addPensionOlder.getBirthday());
		addPensionOlder.setAge(age);
	}

	public String getPersonState() {
		return personState;
	}

	public void setPersonState(String personState) {
		this.personState = personState;
	}

	public String getApplier() {
		return applier;
	}

	public void setApplier(String applier) {
		this.applier = applier;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public Date getStartDate() {
		return StartDate;
	}

	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public LivingApplyService getLivingApplyService() {
		return livingApplyService;
	}

	public void setLivingApplyService(LivingApplyService livingApplyService) {
		this.livingApplyService = livingApplyService;
	}

	public String getTeststr() {
		return teststr;
	}

	public void setTeststr(String teststr) {
		this.teststr = teststr;
	}

	public void setPensionOlderList(List<PensionOlderDomain> pensionOlderList) {
		this.pensionOlderList = pensionOlderList;
	}

	public List<PensionOlderDomain> getPensionOlderList() {
		return pensionOlderList;
	}

	public void setSelectedRow(PensionOlderDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionOlderDomain getSelectedRow() {
		return selectedRow;
	}

	public void setOperationId(Short operationId) {
		this.operationId = operationId;
	}

	public Short getOperationId() {
		return operationId;
	}

	public void setAddPensionOlder(PensionOlderDomain addPensionOlder) {
		this.addPensionOlder = addPensionOlder;
	}

	public PensionOlderDomain getAddPensionOlder() {
		return addPensionOlder;
	}

	public List<PensionDicNation> getNationList() {
		return nationList;
	}

	public void setNationList(List<PensionDicNation> nationList) {
		this.nationList = nationList;
	}

	public List<PensionDicRelationship> getRelationshipList() {
		return relationshipList;
	}

	public void setRelationshipList(
			List<PensionDicRelationship> relationshipList) {
		this.relationshipList = relationshipList;
	}

	public void setPoliticList(List<PensionDicPolitics> politicList) {
		this.politicList = politicList;
	}

	public List<PensionDicPolitics> getPoliticList() {
		return politicList;
	}

	public void setApplication(PensionLivingapply application) {
		this.application = application;
	}

	public void setApplyFlag(boolean applyFlag) {
		this.applyFlag = applyFlag;
	}

	public boolean isApplyFlag() {
		return applyFlag;
	}

	public void setNoticeFlag(boolean noticeFlag) {
		this.noticeFlag = noticeFlag;
	}

	public boolean isNoticeFlag() {
		return noticeFlag;
	}

	public void setOlderSQL(String olderSQL) {
		this.olderSQL = olderSQL;
	}

	public String getOlderSQL() {
		return olderSQL;
	}

	public Boolean getAddOlderStatuseFlag() {
		return addOlderStatuseFlag;
	}

	public void setAddOlderStatuseFlag(Boolean addOlderStatuseFlag) {
		this.addOlderStatuseFlag = addOlderStatuseFlag;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setClearFlag(Boolean clearFlag) {
		this.clearFlag = clearFlag;
	}

	public Boolean getClearFlag() {
		return clearFlag;
	}

}
