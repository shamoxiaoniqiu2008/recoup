package controller.olderManage;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.UnselectEvent;

import service.financeManage.NormalPayDomain;
import service.financeManage.TempPayDomain;
import service.olderManage.PaperManageService;
import service.olderManage.PensionFamilyDomain;
import service.receptionManage.LivingApplyService;
import service.receptionManage.PensionOlderDomain;
import util.PmsException;
import domain.dictionary.PensionDicNation;
import domain.dictionary.PensionDicPolitics;
import domain.dictionary.PensionDicRelationship;
import domain.olderManage.PensionDiagnosis;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-28 下午01:16:44
 */
public class PaperManageController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient PaperManageService paperManageService;
	private transient LivingApplyService livingApplyService;
	/**
	 * 查询条件
	 */
	private String olderName;
	private Long olderId;
	/**
	 * 所有老人档案记录列表
	 */
	private List<PensionOlderDomain> olderRecordList = new ArrayList<PensionOlderDomain>();
	/**
	 * 选中申请记录
	 */
	private PensionOlderDomain selectedRow;
	/**
	 * 查看申请记录
	 */
	private PensionOlderDomain viewPensionOlder;
	/**
	 * 民族列表
	 */
	private List<PensionDicNation> nationList;
	/**
	 * 政治面貌列表
	 */
	private List<PensionDicPolitics> politicList;

	/**
	 * 新增和修改对话框申请记录
	 */
	private PensionOlderDomain addOlderInfo = new PensionOlderDomain();
	/**
	 * 输入法sql
	 */
	private String olderSQL;
	private List<PensionDicRelationship> relationshipList;
	/**
	 * 日常收费
	 */
    private List<NormalPayDomain> normalPayList;
	/**
	 * 临时收费
	 */
    private List<TempPayDomain> tempPayList;
   // 家属信息
	private List<PensionFamilyDomain> familyList = new ArrayList<PensionFamilyDomain>();
    
    private PensionDiagnosis lastOneDiagnosis;
    
    private List<PensionDiagnosis> pensionDiagnosisList;
    
	@PostConstruct
	public void init() throws PmsException {
		initNation();
		initPolitics();
		initRelationShip();
		searchAllOlderRecords();
		initSql();
	}

	/**
	 * @Description: 初始化民族列表
	 */
	public void initNation() {
		setNationList(paperManageService.selectNationList());
	}

	/**
	 * @Description: 初始化政治面貌列表
	 */
	public void initPolitics() {
		setPoliticList(paperManageService.selectPoliticList());
	}

	/**
	 * @Description: 初始化关系列表
	 */
	public void initRelationShip() {
		relationshipList = paperManageService.selectRelationshipList();
	}

	/**
	 * 初始化输入法sql
	 */
	public void initSql() {

		olderSQL = "select pr.older_id as olderId,po.`name` as olderName,po.inputCode as inputCode,bed.`name` as bedName,"
				+ "room.`name` as roomName,floor.`name` as  floorName,build.`name` as buildName  "
				+ "from pension_livingrecord pr,pension_older po,pension_bed bed,"
				+ "pension_room room,pension_floor floor,pension_building build  where  "
				+ "po.id=pr.older_id and bed.id=pr.bed_id and room.id=bed.room_id  "
				+ "and room.floor_id=floor.id and build.id=floor.build_id  and pr.cleared=2 and po.cleared=2 ";
	}

	/**
	 * 查询出所有的老人申请记录
	 */
	public void searchAllOlderRecords() {
		olderRecordList = paperManageService.selectPensionInfomation(olderId);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "查询完成！", ""));
	}

	public void showViewForm() {
		final FacesContext context = FacesContext.getCurrentInstance();
		final RequestContext request=RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选中一条记录！", ""));
			
		} else {
			viewPensionOlder = selectedRow;
			olderId=viewPensionOlder.getId();
			normalPayList=paperManageService.selectNormalPayRecords(olderId);
			tempPayList=paperManageService.selectTempPayRecords(olderId);
			familyList=paperManageService.selectFamilyRecord(olderId);
			request.addCallbackParam("show", true);
		}
	}

	
	public void showHealthRecords() {
		final FacesContext context = FacesContext.getCurrentInstance();
		final RequestContext request=RequestContext.getCurrentInstance();
		if (selectedRow == null) {
			request.addCallbackParam("show", false);
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先选中一条记录！", ""));
			
		} else {
			viewPensionOlder = selectedRow;
			List<PensionDiagnosis> diagnosisLastOne = paperManageService
			.selectDiagnosis(viewPensionOlder.getId(), null, null," diagnosisTime desc ", 0L,1L);
			
			if(diagnosisLastOne != null && diagnosisLastOne.size() > 0) {
				lastOneDiagnosis = diagnosisLastOne.get(0);
				SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM"); 
				String datePart = df2.format(lastOneDiagnosis.getDiagnosistime());
				String timePart = "00:00:00";
				String dateTime = datePart + "01 " + timePart;
				
				SimpleDateFormat df3=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startTime = null;
				try{
					startTime = df3.parse(dateTime);
				} catch (ParseException pe) {
					startTime = new Date();
				}
				pensionDiagnosisList = paperManageService
					.selectDiagnosis(viewPensionOlder.getId(), startTime,
						lastOneDiagnosis.getDiagnosistime(),
						" diagnosisTime asc ", null,null);
			} else {
				lastOneDiagnosis = new PensionDiagnosis();
				pensionDiagnosisList = new ArrayList<PensionDiagnosis>();
			}
			/*olderId=viewPensionOlder.getId().intValue();
			normalPayList=paperManageService.selectNormalPayRecords(olderId);
			tempPayList=paperManageService.selectTempPayRecords(olderId);*/
			request.addCallbackParam("show", true);
		}
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
	 * tab页切换
	 * @param e
	 */
   public void onChangeTab(TabChangeEvent e){
		
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public void setOlderSQL(String olderSQL) {
		this.olderSQL = olderSQL;
	}

	public String getOlderSQL() {
		return olderSQL;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public Long getOlderId() {
		return olderId;
	}

	public void setOlderRecordList(List<PensionOlderDomain> olderRecordList) {
		this.olderRecordList = olderRecordList;
	}

	public List<PensionOlderDomain> getOlderRecordList() {
		return olderRecordList;
	}

	public void setAddOlderInfo(PensionOlderDomain addOlderInfo) {
		this.addOlderInfo = addOlderInfo;
	}

	public PensionOlderDomain getAddOlderInfo() {
		return addOlderInfo;
	}

	public PaperManageService getPaperManageService() {
		return paperManageService;
	}

	public void setPaperManageService(PaperManageService paperManageService) {
		this.paperManageService = paperManageService;
	}

	public PensionOlderDomain getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(PensionOlderDomain selectedRow) {
		this.selectedRow = selectedRow;
	}

	public void setRelationshipList(
			List<PensionDicRelationship> relationshipList) {
		this.relationshipList = relationshipList;
	}

	public List<PensionDicRelationship> getRelationshipList() {
		return relationshipList;
	}

	public LivingApplyService getLivingApplyService() {
		return livingApplyService;
	}

	public void setLivingApplyService(LivingApplyService livingApplyService) {
		this.livingApplyService = livingApplyService;
	}

	public void setPoliticList(List<PensionDicPolitics> politicList) {
		this.politicList = politicList;
	}

	public List<PensionDicPolitics> getPoliticList() {
		return politicList;
	}

	public void setNationList(List<PensionDicNation> nationList) {
		this.nationList = nationList;
	}

	public List<PensionDicNation> getNationList() {
		return nationList;
	}

	public void setViewPensionOlder(PensionOlderDomain viewPensionOlder) {
		this.viewPensionOlder = viewPensionOlder;
	}

	public PensionOlderDomain getViewPensionOlder() {
		return viewPensionOlder;
	}

	public void setNormalPayList(List<NormalPayDomain> normalPayList) {
		this.normalPayList = normalPayList;
	}

	public List<NormalPayDomain> getNormalPayList() {
		return normalPayList;
	}

	public void setTempPayList(List<TempPayDomain> tempPayList) {
		this.tempPayList = tempPayList;
	}

	public List<TempPayDomain> getTempPayList() {
		return tempPayList;
	}

	

	public void setPensionDiagnosisList(List<PensionDiagnosis> pensionDiagnosisList) {
		this.pensionDiagnosisList = pensionDiagnosisList;
	}

	public List<PensionDiagnosis> getPensionDiagnosisList() {
		return pensionDiagnosisList;
	}

	public void setLastOneDiagnosis(PensionDiagnosis lastOneDiagnosis) {
		this.lastOneDiagnosis = lastOneDiagnosis;
	}

	public PensionDiagnosis getLastOneDiagnosis() {
		return lastOneDiagnosis;
	}

	public void setFamilyList(List<PensionFamilyDomain> familyList) {
		this.familyList = familyList;
	}

	public List<PensionFamilyDomain> getFamilyList() {
		return familyList;
	}
}
