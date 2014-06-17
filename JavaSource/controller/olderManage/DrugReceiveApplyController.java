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

import service.olderManage.DispenseDomain;
import service.olderManage.DrugDicDomain;
import service.olderManage.DrugReceiveApplyService;
import service.olderManage.DrugReceiveDetailDomain;
import service.olderManage.DrugReceiveDomain;
import util.PmsException;
import util.Spell;
import domain.medicalManage.PensionDicDrugreceive;
import domain.nurseManage.PensionDicDelivery;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-10-30 上午08:16:44
 */

public class DrugReceiveApplyController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient DrugReceiveApplyService drugReceiveApplyService;
	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;
	private String isAudit;
	private String auditResult;
	private Long delivery;
	/**
	 * 输入法sql
	 */
	private String olderSql;
	private String addOlderSql;
	private String drugOlderSql;
	private String drugSql;
	private String addDrugSql;
	private String addDrugOlderSql;
	/**
	 * 选中的药物接收申请记录
	 */
	private DrugReceiveDomain selectedRow;
	/**
	 * 药物接收申请列表
	 */
	private List<DrugReceiveDomain> drugApplyList = new ArrayList<DrugReceiveDomain>();
	/**
	 * 新增药物接收主记录
	 */
	private DrugReceiveDomain addDrugApply = new DrugReceiveDomain();
	/**
	 * 新增药物接收明细
	 */
	private DrugReceiveDetailDomain addDrugApplyDetail;
	/**
	 * 选中药物接收明细
	 */
	private DrugReceiveDetailDomain selectedDrugDetail;
	/**
	 * 新增药物接收明细集合
	 */
	private List<DrugReceiveDetailDomain> drugDetailList;
	private int saveFlag;
	private String totalAmount;
	// 摆药老人列表
	private List<DispenseDomain> olderList = new ArrayList<DispenseDomain>();
	// 摆药查询条件
	private String dispenseOlderName;
	private Long dispenseOlderId;
	// 选中的摆药老人
	private DispenseDomain selectedDispense;
	// 新增摆药老人
	private DispenseDomain addDispenseOlder;
	// 编辑摆药老人
	private DispenseDomain editDispenseOlder;

	private Long detailId;
	// 药物字典对话框查询条件
	private String drugName;
	private Long drugOlderId;
	private String drugOlderName;
	// 选中的药物对象
	private DrugDicDomain selectedDrug;
	// 药物列表
	private List<DrugDicDomain> drugList;

	// 新增药物字典对象
	private DrugDicDomain addDrug;
	// 编辑药物字典对象
	private DrugDicDomain editDrug;
	// 配送类别字典表
	private List<PensionDicDelivery> deliveryList = new ArrayList<PensionDicDelivery>();

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		initSql();
		initDelieryList();
		searchApplications();
		detailId = drugReceiveApplyService.selectMaxDetailId();
	}

	/**
	 * 初始化老人输入法
	 */
	public void initSql() {
		olderSql = "select  distinct p.older_id,p.older_name,po.inputCode "
				+ "from pension_dispense_older p,pension_older po  "
				+ "where p.older_id=po.id and p.cleared=2 and  po.cleared=2";

		addOlderSql = "select pe.id,pe.name,pe.inputCode from pension_older pe where pe.cleared=2 and pe.statuses in(3,4)";

		drugOlderSql = "select distinct po.id,po.name,po.inputCode from pension_dic_drugreceive t,pension_older po where t.older_id=po.id";

		drugSql = "select distinct t.drugreceive_name,t.input_code from pension_dic_drugreceive t";

		addDrugSql = "select distinct t.id, t.drugreceive_name,t.older_name,t.input_code,"
				+ "t.single_dose,t.unit,if(t.morning_flag=1,'是','否')as morning_flag,"
				+ "if(t.noon_flag=1,'是','否')as noon_flag,"
				+ "if(t.night_flag=1,'是','否') as night_flag,"
				+ "if(t.beforeafter_flag=1,'是','否') as beforeafter_flag,"
				+ "if(t.valid_flag=1,'是','否') as valid_flag  "
				+ " from pension_dic_drugreceive t ";
		addDrugOlderSql = "select distinct p.older_id,p.older_name,po.inputCode "
				+ "from pension_dispense_older p,pension_older po  "
				+ "where p.older_id=po.id and p.cleared=2 and  po.cleared=2"
				+ " and po.statuses in(3,4)";

	}

	/**
	 * 初始化配送类别字典表
	 */
	public void initDelieryList() {
		PensionDicDelivery temp = new PensionDicDelivery();
		temp.setDeliveryName("全部");
		temp.setId(0L);
		deliveryList = drugReceiveApplyService.selectDelieryList();
		deliveryList.add(0, temp);
	}

	/**
	 * 查询药物接收申请列表
	 */
	public void searchApplications() {
		drugApplyList = drugReceiveApplyService.selectApplications(olderId,
				isAudit, auditResult, delivery);
		selectedRow = null;
		drugDetailList = new ArrayList<DrugReceiveDetailDomain>();
	}

	/**
	 * 清空查询条件
	 */
	public void clearSearch() {
		olderId = null;
		olderName = "";
		isAudit = null;
		auditResult = null;
		delivery = 0L;
	}

	/**
	 * 选中一行时触发
	 * 
	 */
	public void setEnableFlag(SelectEvent e) {

	}

	/**
	 * 未选中一行时触发
	 * 
	 */
	public void setUnableFlag(UnselectEvent e) {

	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		addDrugApply = new DrugReceiveDomain();
		addDrugApplyDetail = new DrugReceiveDetailDomain();
		drugDetailList = new ArrayList<DrugReceiveDetailDomain>();
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
			requestContext.addCallbackParam("edit", false);
		} else if (selectedRow.getSendFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该申请已提交，不可更改",
							""));
			requestContext.addCallbackParam("edit", false);
		} else {
			addDrugApply = selectedRow;
			addDrugApplyDetail = new DrugReceiveDetailDomain();
			drugDetailList = drugReceiveApplyService
					.selectDrugDetails(addDrugApply.getId());
			selectOlderDrug();
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
		} else if (selectedRow.getSendFlag() == 1) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "该申请已提交，不可删除",
							""));
		} else {
			requestContext.addCallbackParam("del", true);
		}
	}

	/**
	 * 新增药物接收申请并发送审核消息
	 */
	public void saveAndSendApplication() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (drugDetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "药物列表不可为空！",
							""));
			return;
		}
		addDrugApply.setAuditFlag(2);
		addDrugApply.setCleared(2);
		addDrugApply.setSendFlag(1);
		drugReceiveApplyService.insertAndSendDrugApply(addDrugApply,
				drugDetailList);
		searchApplications();
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "药物接收申请新增并提交成功！",
						""));
		requestContext.addCallbackParam("addHide", true);
	}

	/**
	 * 发送通知
	 */
	public void sentMessage() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "请先选中一条记录！", ""));
			return;
		}
		if (selectedRow.getSendFlag() == 1) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "该申请已提交！", ""));
			return;
		}
		try {
			selectedRow.setSendFlag(1);
			drugReceiveApplyService.updateDrugRecord(selectedRow);
			drugReceiveApplyService.sentMessage(selectedRow);
			searchApplications();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "发送消息成功", ""));
		} catch (PmsException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		}
	}

	/**
	 * 删除一条药物接收申请
	 */
	public void deleteApplication() {
		selectedRow.setCleared(1);
		drugReceiveApplyService.delDrugApply(selectedRow);
		searchApplications();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "删除成功", ""));
	}

	/**
	 * 增加一条明细
	 */
	public void addDrugDetail() {
		try {
			Integer totalTemp = Integer.valueOf(totalAmount);
			for (DrugReceiveDetailDomain detail : drugDetailList) {
				if (addDrugApplyDetail.getDrugreceiveName().equals(
						detail.getDrugreceiveName())) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该药物已添加！", ""));
					return;
				}
			}
			if (!(totalTemp.floatValue() > 0)) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("总量 请输正值！", ""));
				return;
			}
			if (addDrugApplyDetail.getShelflifeTime().before(new Date())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"保质期必须大于当前日期", ""));
				return;
			}
			convertDrugDetail(addDrugApplyDetail);
			addDrugApplyDetail.setTotalAmount(totalTemp);
			detailId += 1;
			addDrugApplyDetail.setId(detailId);
			addDrugApplyDetail.setCleared(2);
			drugDetailList.add(addDrugApplyDetail);
			addDrugApplyDetail = new DrugReceiveDetailDomain();
			totalAmount = "";
		} catch (NumberFormatException e) {
			PensionDicDrugreceive dicTemp = new PensionDicDrugreceive();
			dicTemp = drugReceiveApplyService.selectDrugDic(addDrugApplyDetail
					.getDrugreceiveId());
			addDrugApplyDetail.setSingleDose(dicTemp.getSingleDose());
			addDrugApplyDetail.setUnit(dicTemp.getUnit());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "总量 只能输入数字！",
							""));
		}
	}

	/**
	 * 药物明细字段转换
	 * 
	 * @param domain
	 */
	public void convertDrugDetail(DrugReceiveDetailDomain domain) {
		if (domain.isInUseF()) {
			domain.setUseFlag(1);
		} else {
			domain.setUseFlag(2);
		}
		PensionDicDrugreceive dicTemp = drugReceiveApplyService
				.selectDrugDic(domain.getDrugreceiveId());
		if (dicTemp.getBeforeafterFlag() == 1) {
			domain.setBeforeAfterFlag(1);
			domain.setEatTimeF(true);
		} else {
			domain.setBeforeAfterFlag(2);
			domain.setEatTimeF(false);
		}
		if (dicTemp.getMorningFlag() == 1) {
			domain.setMorningFlag(1);
			domain.setMorningF(true);
		} else {
			domain.setMorningFlag(2);
			domain.setMorningF(false);
		}
		if (dicTemp.getNoonFlag() == 1) {
			domain.setNoonFlag(1);
			domain.setNoonF(true);
		} else {
			domain.setNoonFlag(2);
			domain.setNoonF(false);
		}
		if (dicTemp.getNightFlag() == 1) {
			domain.setNightFlag(1);
			domain.setNightF(true);
		} else {
			domain.setNightFlag(2);
			domain.setNightF(false);
		}
		if (dicTemp.getValidFlag() == 1) {
			domain.setValidFlag(1);
			domain.setValidF(true);
		} else {
			domain.setValidFlag(2);
			domain.setValidF(false);
		}
		domain.setSingleDose(dicTemp.getSingleDose());
		domain.setUnit(dicTemp.getUnit());
	}

	/**
	 * 确认是否删除明细
	 */
	public void delDetailCinfirm() {
		RequestContext request = RequestContext.getCurrentInstance();
		if (selectedDrugDetail == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "请先选中一条明细！",
							""));
			return;
		} else {
			request.addCallbackParam("detailShow", true);
		}
	}

	/**
	 * 删除明细
	 */
	public void deleteDrugDetail() {
		drugDetailList.remove(selectedDrugDetail);
	}

	/**
	 * 保存一条药物接收申请
	 * 
	 */
	public void saveApplication() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (drugDetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "药物列表不可为空！",
							""));
			return;
		}
		addDrugApply.setAuditFlag(2);
		addDrugApply.setCleared(2);
		addDrugApply.setSendFlag(2);
		drugReceiveApplyService.insertDrugApply(addDrugApply, drugDetailList);
		searchApplications();
		FacesContext.getCurrentInstance()
				.addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"新增药物接收申请成功！", ""));
		requestContext.addCallbackParam("addHide", true);
	}

	/**
	 * 修改药物申请
	 */
	public void updateEditApplication() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (drugDetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "药品列表不可为空！",
							""));
			return;
		}
		drugReceiveApplyService.updateDrugApply(addDrugApply, drugDetailList);
		searchApplications();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "修改成功！", ""));
		requestContext.addCallbackParam("editHide", true);

	}

	/**
	 * 修改药物接收申请并提交
	 */
	public void updateAndSendEditApplication() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (drugDetailList.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "药品列表不可为空！",
							""));
			return;
		}
		addDrugApply.setSendFlag(1);
		drugReceiveApplyService.updateAndSendDrugApply(addDrugApply,
				drugDetailList);
		searchApplications();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "修改成功！", ""));
		requestContext.addCallbackParam("editHide", true);
	}

	/**
	 * 查询全部摆药老人列表
	 */
	public void selectOlderList() {
		dispenseOlderId = null;
		dispenseOlderName = null;
		searchDispenseOlders();
	}

	/**
	 * 根据老人ID查询摆药老人
	 */
	public void searchDispenseOlders() {
		olderList = drugReceiveApplyService
				.selectDispenseOlders(dispenseOlderId);
		selectedDispense = null;
	}

	/**
	 * 保存老人摆药信息
	 */
	public void saveDispenseRecord() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		for (DispenseDomain older : olderList) {
			if (older.getOlderId().equals(addDispenseOlder.getOlderId())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "该老人已存在！",
								""));
				return;
			}
		}
		addDispenseOlder.setCleared(2);
		if (addDispenseOlder.getValidF()) {
			addDispenseOlder.setValidFlag(1);
		} else {
			addDispenseOlder.setValidFlag(2);
		}
		drugReceiveApplyService.insertDispenseOlder(addDispenseOlder);
		selectOlderList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "新增成功！", ""));
		requestContext.addCallbackParam("dispenseHide", true);
	}

	/**
	 * 清空新增摆药对话框
	 */
	public void clearAddDispenseForm() {
		addDispenseOlder = new DispenseDomain();
	}

	/**
	 * 修改老人摆药信息
	 */
	public void updateDispenseRecord() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (editDispenseOlder.getValidF()) {
			editDispenseOlder.setValidFlag(1);
		} else {
			editDispenseOlder.setValidFlag(2);
		}
		drugReceiveApplyService.updateDispenseOlder(editDispenseOlder);
		selectOlderList();
		searchApplications();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "修改成功！", ""));
		requestContext.addCallbackParam("editDispenseHide", true);
	}

	/**
	 * 摆药对话框修改按钮触发
	 */
	public void showEditDispenseForm() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		if (selectedDispense == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
		} else {
			editDispenseOlder = selectedDispense;
			requestContext.addCallbackParam("editDispenseShow", true);
		}
	}

	/**
	 * 查询所有的药物字典
	 * 
	 * @return
	 */
	public void selectDrugList() {
		drugOlderId = null;
		drugName = "";
		drugOlderName = null;
		searchDrugList();
	}

	/**
	 * 根据条件查询药物字典
	 */
	public void searchDrugList() {
		drugList = drugReceiveApplyService.selectDrugRecords(drugOlderId,
				drugName);
		selectedDrug = null;
	}

	/**
	 * 清空新增药物字典对话框
	 */
	public void clearAddDrugForm() {
		addDrug = new DrugDicDomain();
	}

	/**
	 * 保存新增药物字典
	 */
	public void saveDrugRecord() {
		RequestContext requestContext = RequestContext.getCurrentInstance();
		convertTemp(addDrug);
		addDrug.setInputCode(Spell.getFirstSpell(addDrug.getDrugreceiveName()));
		addDrug.setCleared(2);
		drugReceiveApplyService.insertDrugDic(addDrug);
		searchDrugList();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "新增成功", ""));
		addDrug = new DrugDicDomain();
		requestContext.addCallbackParam("addDrugHide", true);
	}

	/**
	 * 清空药物字典查询条件
	 */
	public void clearDrugCondition() {
		drugName = "";
		drugOlderName = null;
		drugOlderId = null;
	}

	public void convertTemp(DrugDicDomain domain) {
		if (domain.isEatTimeF()) {
			domain.setBeforeafterFlag(1);
		} else {
			domain.setBeforeafterFlag(2);
		}
		if (domain.isMorningF()) {
			domain.setMorningFlag(1);
		} else {
			domain.setMorningFlag(2);
		}
		if (domain.isNoonF()) {
			domain.setNoonFlag(1);
		} else {
			domain.setNoonFlag(2);
		}
		if (domain.isNightF()) {
			domain.setNightFlag(1);
		} else {
			domain.setNightFlag(2);
		}
		if (domain.getValidF()) {
			domain.setValidFlag(1);
		} else {
			domain.setValidFlag(2);
		}
	}

	/**
	 * 输入老人姓名后更新药物字典SQL
	 */
	public void selectOlderDrug() {
		addDrugSql = "select t.id, t.drugreceive_name,t.older_name,t.input_code,"
				+ "t.single_dose,t.unit,if(t.morning_flag=1,'是','否')as morning_flag,"
				+ "if(t.noon_flag=1,'是','否')as noon_flag,"
				+ "if(t.night_flag=1,'是','否') as night_flag,"
				+ "if(t.beforeafter_flag=1,'是','否') as beforeafter_flag,"
				+ "if(t.valid_flag=1,'是','否') as valid_flag  "
				+ " from pension_dic_drugreceive t where t.older_id="
				+ addDrugApply.getOlderId();
	}

	/**
	 * 启用药物字典
	 */
	public void startDrug() {
		if (selectedDrug == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
			return;
		}
		if (selectedDrug.getValidF()) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该药物字典已启用", ""));
			return;
		}
		selectedDrug.setValidFlag(1);
		drugReceiveApplyService.updateDrugDic(selectedDrug);
		searchDrugList();
		if (selectedRow != null) {
			viewDetailList();
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "药物字典启用成功", ""));
	}

	/**
	 * 停用药物字典
	 */
	public void stopDrug() {
		if (selectedDrug == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"请先选中一条记录", ""));
			return;
		}
		if (!selectedDrug.getValidF()) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"该药物字典已停用", ""));
			return;
		}
		selectedDrug.setValidFlag(2);
		drugReceiveApplyService.updateDrugDic(selectedDrug);
		searchDrugList();
		if (selectedRow != null) {
			viewDetailList();
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "药物字典停用成功", ""));
	}

	public void viewDetailList() {
		drugDetailList = drugReceiveApplyService.selectDrugDetails(selectedRow
				.getId());
	}

	public void clearDetailList() {
		drugDetailList = new ArrayList<DrugReceiveDetailDomain>();
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public Long getDrugOlderId() {
		return drugOlderId;
	}

	public void setDrugOlderId(Long drugOlderId) {
		this.drugOlderId = drugOlderId;
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
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

	public void setOlderSql(String olderSql) {
		this.olderSql = olderSql;
	}

	public String getOlderSql() {
		return olderSql;
	}

	public void setDrugReceiveApplyService(
			DrugReceiveApplyService drugReceiveApplyService) {
		this.drugReceiveApplyService = drugReceiveApplyService;
	}

	public DrugReceiveApplyService getDrugReceiveApplyService() {
		return drugReceiveApplyService;
	}

	public void setAddOlderSql(String addOlderSql) {
		this.addOlderSql = addOlderSql;
	}

	public String getAddOlderSql() {
		return addOlderSql;
	}

	public void setDrugApplyList(List<DrugReceiveDomain> drugApplyList) {
		this.drugApplyList = drugApplyList;
	}

	public List<DrugReceiveDomain> getDrugApplyList() {
		return drugApplyList;
	}

	public void setAddDrugApply(DrugReceiveDomain addDrugApply) {
		this.addDrugApply = addDrugApply;
	}

	public DrugReceiveDomain getAddDrugApply() {
		return addDrugApply;
	}

	public List<DrugReceiveDetailDomain> getDrugDetailList() {
		return drugDetailList;
	}

	public void setDrugDetailList(List<DrugReceiveDetailDomain> drugDetailList) {
		this.drugDetailList = drugDetailList;
	}

	public void setSelectedRow(DrugReceiveDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setAddDrugApplyDetail(DrugReceiveDetailDomain addDrugApplyDetail) {
		this.addDrugApplyDetail = addDrugApplyDetail;
	}

	public DrugReceiveDomain getSelectedRow() {
		return selectedRow;
	}

	public DrugReceiveDetailDomain getAddDrugApplyDetail() {
		return addDrugApplyDetail;
	}

	public int getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(int saveFlag) {
		this.saveFlag = saveFlag;
	}

	public void setSelectedDrugDetail(DrugReceiveDetailDomain selectedDrugDetail) {
		this.selectedDrugDetail = selectedDrugDetail;
	}

	public DrugReceiveDetailDomain getSelectedDrugDetail() {
		return selectedDrugDetail;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setOlderList(List<DispenseDomain> olderList) {
		this.olderList = olderList;
	}

	public List<DispenseDomain> getOlderList() {
		return olderList;
	}

	public String getDispenseOlderName() {
		return dispenseOlderName;
	}

	public void setDispenseOlderName(String dispenseOlderName) {
		this.dispenseOlderName = dispenseOlderName;
	}

	public Long getDispenseOlderId() {
		return dispenseOlderId;
	}

	public void setDispenseOlderId(Long dispenseOlderId) {
		this.dispenseOlderId = dispenseOlderId;
	}

	public void setSelectedDispense(DispenseDomain selectedDispense) {
		this.selectedDispense = selectedDispense;
	}

	public DispenseDomain getSelectedDispense() {
		return selectedDispense;
	}

	public DispenseDomain getEditDispenseOlder() {
		return editDispenseOlder;
	}

	public void setEditDispenseOlder(DispenseDomain editDispenseOlder) {
		this.editDispenseOlder = editDispenseOlder;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getDetailId() {
		return detailId;
	}

	public DispenseDomain getAddDispenseOlder() {
		return addDispenseOlder;
	}

	public void setAddDispenseOlder(DispenseDomain addDispenseOlder) {
		this.addDispenseOlder = addDispenseOlder;
	}

	public void setDrugOlderSq(String drugOlderSql) {
		this.drugOlderSql = drugOlderSql;
	}

	public String getDrugOlderSq() {
		return drugOlderSql;
	}

	public void setDrugSql(String drugSql) {
		this.drugSql = drugSql;
	}

	public String getDrugSql() {
		return drugSql;
	}

	public void setDrugOlderName(String drugOlderName) {
		this.drugOlderName = drugOlderName;
	}

	public String getDrugOlderName() {
		return drugOlderName;
	}

	public String getDrugOlderSql() {
		return drugOlderSql;
	}

	public void setDrugOlderSql(String drugOlderSql) {
		this.drugOlderSql = drugOlderSql;
	}

	public DrugDicDomain getSelectedDrug() {
		return selectedDrug;
	}

	public void setSelectedDrug(DrugDicDomain selectedDrug) {
		this.selectedDrug = selectedDrug;
	}

	public List<DrugDicDomain> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<DrugDicDomain> drugList) {
		this.drugList = drugList;
	}

	public DrugDicDomain getAddDrug() {
		return addDrug;
	}

	public void setAddDrug(DrugDicDomain addDrug) {
		this.addDrug = addDrug;
	}

	public DrugDicDomain getEditDrug() {
		return editDrug;
	}

	public void setEditDrug(DrugDicDomain editDrug) {
		this.editDrug = editDrug;
	}

	public void setAddDrugSql(String addDrugSql) {
		this.addDrugSql = addDrugSql;
	}

	public String getAddDrugSql() {
		return addDrugSql;
	}

	public void setAddDrugOlderSql(String addDrugOlderSql) {
		this.addDrugOlderSql = addDrugOlderSql;
	}

	public String getAddDrugOlderSql() {
		return addDrugOlderSql;
	}

	public void setDeliveryList(List<PensionDicDelivery> deliveryList) {
		this.deliveryList = deliveryList;
	}

	public List<PensionDicDelivery> getDeliveryList() {
		return deliveryList;
	}

	public void setDelivery(Long delivery) {
		this.delivery = delivery;
	}

	public Long getDelivery() {
		return delivery;
	}
}
