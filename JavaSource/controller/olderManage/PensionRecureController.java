package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;

import service.olderManage.PensionOlderRecureExtend;
import service.olderManage.PensionOlderRecureMainExtend;
import service.olderManage.PensionRecureDetailExtend;
import service.olderManage.PensionRecureService;
import util.PmsException;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;

/**
 * 
 * @author:bill
 * @version: 1.0
 * @Date:2013-10-10 下午01:16:44
 */
public class PensionRecureController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient PensionRecureService pensionRecureService;

	/**
	 * 查询条件
	 */
	//老人名称
	private String olderName;
	//康复项目
	private String recureItem;
	
	private Long olderId;
	private String inputDivName;
	private String inputDivId;
	
	private Long recureitemId;
	private Integer handle;

	private String recureitemName;
	

	//定义康复责任人输入法变量
	private String recoverToSql;
	private String fitcolRecover;
	private String displaycolRecover;
	
	//根据选中的康复方案条目生成的老人的康复明细 add by mary 
	private List<PensionOlderRecureExtend> olderRecureList = new ArrayList<PensionOlderRecureExtend>();
	private List<PensionOlderRecureExtend> olderRecureDetailList = new ArrayList<PensionOlderRecureExtend>();
	private List<PensionOlderRecureExtend> selectedRecureDetailList = new ArrayList<PensionOlderRecureExtend>();
	private List<PensionOlderRecureMainExtend> olderRecureMainExtendList = new ArrayList<PensionOlderRecureMainExtend>();
	private List<PensionOlderRecureMainExtend> olderRecureItemList = new ArrayList<PensionOlderRecureMainExtend>();
	private List<PensionRecureDetailExtend> recureList_src = new ArrayList<PensionRecureDetailExtend>();
	private List<PensionRecureDetailExtend> recureList_tar = new ArrayList<PensionRecureDetailExtend>();;
	private DualListModel<PensionRecureDetailExtend> items; 
	
	//第二步 修改康复项目明细
	private PensionOlderRecureMainExtend addOlderRecureMainExtend;
	private PensionRecureDetailExtend selectRecureDetailExtend;
	private PensionRecureDetailExtend copyRecureDetailExtend;
	
	//第三部 增删改 老人康复明细
	private PensionOlderRecureExtend selectOlderRecure;
	
	
	/**
	 * 转换器
	 */
	private RecureConveter recureConveter = new RecureConveter();
	/**
	 * 选中申请记录
	 */
	private PensionOlderRecureExtend selectedRow;
	/**
	 * 新增计划单项
	 */
	private PensionOlderRecureExtend addRow;
	/**
	 * 操作标记 1 为新增 2 为修改
	 */
	private Short operationId;
	/**
	 * 新增和修改对话框申请记录
	 */     
	//private PensionOlderRecureMain pensionOlderRecureMain;
	private PensionOlderRecureMainExtend olderRecureMainExtend;
	private PensionOlderRecureMainExtend olderRecureMainExtendSel;
	private String userName;
	private PensionEmployee curUser;
	private Date startTime;
	/**
	 * 输入法sql
	 */
	private String olderSQL;
	private String newOlderSQL;
	private String itemSQL;
	private String recureDetailSql;
	
	private static final Integer YES_FLAG=1;
	private static final Integer NO_FLAG=2;

	@PostConstruct
	public void init() throws PmsException {
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		userName = curUser.getName();
		initSql();
		handle = 1;
		searchRecureRecords();
		items = new DualListModel<PensionRecureDetailExtend>(recureList_src, recureList_tar);

	}
	
	
	
	public void initAddRow()
	{
		if(olderRecureMainExtend != null&&olderRecureMainExtend.getOlderId() != null && 
		olderRecureMainExtend.getRecureitemId() != null &&olderRecureMainExtend.getStarttime() != null) 
		{
			addRow = new PensionOlderRecureExtend();
			addRow.setRecureitemId(olderRecureMainExtend.getRecureitemId());
			addRow.setRecureMainId(olderRecureMainExtend.getId());
			addRow.setOlderId(olderRecureMainExtend.getOlderId());
			addRow.setRecuredetailId(new Long(1));
		}else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "信息不完整！", ""));
		}
	}
	
	/**
	 * 清空查询条件
	 */
	public void clearSearchForm() {
		olderName = "";
		olderId = null;
		handle = 1;
		recureitemId = null;
		recureitemName = "";
	}
	
	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		//addOlderRecure = new PensionOlderRecureExtend();
	}
	/**
	 * 更新子项目
	 */

	public void updateItemList() {
		//addOlderRecure = new PensionOlderRecureExtend();
		recureList_src = pensionRecureService.selectRecureDetail(olderRecureMainExtend.getRecureitemId()) ;
		recureConveter.setList(recureList_src);
		items = new DualListModel<PensionRecureDetailExtend>(recureList_src, recureList_tar);
		
	}
	/**
	 * pickList transfer listener
	 * 当“下一步”从“第一步”到“第二步”
	 */
    public void onTransfer(TransferEvent event) {  
//        StringBuilder builder = new StringBuilder();  
//        for(Object item : event.getItems()) {  
//            builder.append(((PensionRecureDetailExtend) item).getName()).append("<br />");  
//        }  
        recureList_tar = items.getTarget();
       
        
    } 
    
    
    /**
     * pickList wizard listener
     */
    public String handleFlow(FlowEvent event) {
    	String currentStepId = event.getOldStep();
    	if(currentStepId.contentEquals("personal")){
    		recureList_tar=items.getTarget();
    		if(recureList_tar.size()<=0)
        	{
                FacesMessage msg = new FacesMessage("提示", "可选项目不能为空！");  
                FacesContext.getCurrentInstance().addMessage(null, msg);  
        		return currentStepId;
        	}
    	}else if(currentStepId.contentEquals("personal1")){
    		/*olderRecureMainExtend = olderRecureMainExtendSel;
    		if(olderRecureMainExtend != null && olderRecureMainExtend.getId() != null) {
    			olderRecureDetailList = pensionRecureService.selectOlderRecureDetail(olderRecureMainExtend.getId());
    		}*/
    		addOlderRecureMainExtend=new PensionOlderRecureMainExtend();
    		Date startTime=new Date();
    		olderRecureDetailList = new ArrayList<PensionOlderRecureExtend>();
    		for(PensionRecureDetailExtend recureDetail:recureList_tar){
    			try{
    				if(recureDetail.getStarttime()==null){
    					FacesMessage msg = new FacesMessage("提示", "请选择开始时间！");  
                        FacesContext.getCurrentInstance().addMessage(null, msg);  
                		return currentStepId;
    				}
    				if(recureDetail.getStarttime().before(startTime)){
    					startTime=recureDetail.getStarttime();
    				}
    				if(recureDetail.getTotalday()==null||recureDetail.getNumber()==null){
    					FacesMessage msg = new FacesMessage("提示", "请填写持续时间和服务次数！");  
                        FacesContext.getCurrentInstance().addMessage(null, msg);  
                		return currentStepId;
    				}
    				String[] scheme=recureDetail.getScheme().split(",");
    				for(int i=0;i<recureDetail.getNumber();i++){
    					Calendar calendar = new GregorianCalendar(); 
    					calendar.setTime(recureDetail.getStarttime()); 
    					calendar.add(calendar.DATE,new Integer(scheme[i]));// 
    					Date planTime=calendar.getTime();   // 
    					PensionOlderRecureExtend tempOlderRecureExtend=new PensionOlderRecureExtend();
    					tempOlderRecureExtend.setCleared(NO_FLAG);
    					tempOlderRecureExtend.setOlderId(olderRecureMainExtend.getOlderId());
    					tempOlderRecureExtend.setOlderName(olderRecureMainExtend.getOlderName());
    					tempOlderRecureExtend.setRecureitemId(olderRecureMainExtend.getRecureitemId());
    					tempOlderRecureExtend.setRecureitemName(olderRecureMainExtend.getRecureitemName());
    					tempOlderRecureExtend.setRecureitemId(recureDetail.getId());
    					tempOlderRecureExtend.setRecuredetailName(recureDetail.getName());
    					tempOlderRecureExtend.setRecuredetailDetails(recureDetail.getDetail());
    					tempOlderRecureExtend.setRealnurse(recureDetail.getDutynurse());
    					tempOlderRecureExtend.setRealnurseName(recureDetail.getDutynurseName());
    					tempOlderRecureExtend.setPlantime(planTime);
    					olderRecureDetailList.add(tempOlderRecureExtend);
    				}
    				
    			}catch(NumberFormatException e){
    				FacesMessage msg = new FacesMessage("提示", "选中的康复计划的执行方案有误！");  
                    FacesContext.getCurrentInstance().addMessage(null, msg);  
            		return currentStepId;
    			}catch(Exception e){
    				FacesMessage msg = new FacesMessage("提示", "生成执行计划失败！");  
                    FacesContext.getCurrentInstance().addMessage(null, msg);  
            		return currentStepId;
    			}
    			addOlderRecureMainExtend.setInputer(curUser.getId());
				addOlderRecureMainExtend.setInputerName(curUser.getName());
				addOlderRecureMainExtend.setInputtime(new Date());
				addOlderRecureMainExtend.setOlderId(olderRecureMainExtend.getOlderId());
				addOlderRecureMainExtend.setRecureitemId(olderRecureMainExtend.getRecureitemId());
				addOlderRecureMainExtend.setStarttime(startTime); 
				selectOlderRecure=null;
    		}
    	}else if(currentStepId.contentEquals("personal2")){
    		
    	} 
    	
    	return event.getNewStep();
    	
    }
    
    public void createOlderRecures(){
    	if(olderRecureMainExtend!=null&&olderRecureMainExtend.getRecureitemId()!=null){
    		recureList_tar=pensionRecureService.selectRecureDetail(olderRecureMainExtend.getRecureitemId());
    		olderRecureDetailList = new ArrayList<PensionOlderRecureExtend>();
    		Date planTime=null;
    		if(olderRecureMainExtend.getStarttime()==null){
    			FacesMessage msg = new FacesMessage("提示", "请填写开始时间！");  
    			FacesContext.getCurrentInstance().addMessage(null, msg);  
    			return;
    		}else{
    			planTime=olderRecureMainExtend.getStarttime();
    		}
    		for(PensionRecureDetailExtend recureDetail:recureList_tar){
    			try{
    				if(recureDetail.getTotalday()==null||recureDetail.getNumber()==null){
    					FacesMessage msg = new FacesMessage("提示", "请填写持续时间和服务次数！");  
    					FacesContext.getCurrentInstance().addMessage(null, msg);  
    					return;
    				}
    				String[] scheme=recureDetail.getScheme().split(",");
    				for(int i=0;i<recureDetail.getNumber();i++){
    					Calendar calendar = new GregorianCalendar(); 
    					calendar.setTime(planTime); 
    					calendar.add(calendar.DATE,new Integer(scheme[i])-1);// 
    					PensionOlderRecureExtend tempOlderRecureExtend=new PensionOlderRecureExtend();
    					tempOlderRecureExtend.setCleared(NO_FLAG);
    					tempOlderRecureExtend.setOlderId(olderRecureMainExtend.getOlderId());
    					tempOlderRecureExtend.setOlderName(olderRecureMainExtend.getOlderName());
    					tempOlderRecureExtend.setRecureitemId(recureDetail.getId());
    					tempOlderRecureExtend.setRecuredetailName(recureDetail.getName());
    					tempOlderRecureExtend.setRecuredetailDetails(recureDetail.getDetail());
    					tempOlderRecureExtend.setRealnurse(recureDetail.getDutynurse());
    					tempOlderRecureExtend.setRealnurseName(recureDetail.getDutynurseName());
    					tempOlderRecureExtend.setPlantime(calendar.getTime());
    					olderRecureDetailList.add(tempOlderRecureExtend);
    				}
    				
    			}catch(NumberFormatException e){
    				FacesMessage msg = new FacesMessage("提示", "选中的康复计划的执行方案有误！");  
    				FacesContext.getCurrentInstance().addMessage(null, msg);  
    			}catch(Exception e){
    				FacesMessage msg = new FacesMessage("提示", "生成执行计划失败！");  
    				FacesContext.getCurrentInstance().addMessage(null, msg);  
    			}
    		}
    	}else{
    		FacesMessage msg = new FacesMessage("提示", "请选择康复项目！");  
			FacesContext.getCurrentInstance().addMessage(null, msg);
    	}
    }
    
	/**
	 * 条件查询
	 */
	public void searchRecureRecords() {
		olderRecureMainExtendList = pensionRecureService.selectOlderRecureMain(olderId, recureitemId, handle);
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
		newOlderSQL = "select pr.older_id as olderId,po.`name` as olderName,po.inputCode as inputCode,bed.`name` as bedName,"
				+ "room.`name` as roomName,floor.`name` as  floorName,build.`name` as buildName,if(po.statuses=3,'入住','请假') as olderState   "
				+ "from pension_livingrecord pr,pension_older po,pension_bed bed,"
				+ "pension_room room,pension_floor floor,pension_building build  where  "
				+ "po.id=pr.older_id and bed.id=pr.bed_id and room.id=bed.room_id  "
				+ "and room.floor_id=floor.id and build.id=floor.build_id  and pr.cleared=2 and po.cleared=2 and po.statuses in(3,4) ";
		itemSQL = "select * from pension_dic_recure_item where invalided = 1";
		recoverToSql ="select"
				+" pe.id as userId"
				+" ,pe.name as userName"
				+" ,pe.inputcode as inputCode"
				+" from"
				+" pension_employee pe";
		
		recureDetailSql="SELECT p.id,p.name, IF(LENGTH(p.detail) > 7,substring(p.detail,1,7),p.detail),p.detail,p.dutyNurse,e.name "
				+" from pension_recure_detail p　 left join pension_employee e on p.dutyNurse = e.id "
				+" where p.cleared = 2";		
	}


	/**
	 * 插入康复计划
	 */
	public void insertRecureDetail()
	{
		
	}
	
	public void countRecureDetail() {
		if(olderRecureMainExtend.getOlderId() != null && 
				olderRecureMainExtend.getRecureitemId() != null &&
				olderRecureMainExtend.getStarttime() != null) {
			//List<PensionRecureDetail> asfd = pensionRecureService.selectRecureDetail(olderRecureMainExtend.getRecureitemId());
			try {
				olderRecureDetailList = pensionRecureService.generateOlderRecureExtend(olderRecureMainExtend.getRecureitemId(), olderRecureMainExtend);
				searchRecureRecords();
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
				e.printStackTrace();
			}
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "信息不完整！", ""));
		}
				
	}
	
	
	public void saveRecureDetail() {
		if(olderRecureMainExtend.getOlderId() != null && 
				olderRecureMainExtend.getRecureitemId() != null &&
				olderRecureMainExtend.getStarttime() != null) {
			//List<PensionRecureDetail> asfd = pensionRecureService.selectRecureDetail(olderRecureMainExtend.getRecureitemId());
		
			try {
				pensionRecureService.insertPensionOlderRecure(olderRecureDetailList);
				olderRecureDetailList = pensionRecureService.selectOlderRecureDetail(olderRecureMainExtend.getId());
				searchRecureRecords();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "保存成功！", ""));
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
				e.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "信息不完整！", ""));
		}
	}
	public void saveSingleRecureDetail() {
		if(olderRecureMainExtend.getOlderId() != null &&olderRecureMainExtend.getRecureitemId() != null &&olderRecureMainExtend.getStarttime() != null) 
		{
			try 
			{
				pensionRecureService.insertSinglePensionOlderRecure(addRow);
				olderRecureDetailList = pensionRecureService.selectOlderRecureDetail(olderRecureMainExtend.getId());
				searchRecureRecords();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "保存成功！", ""));
			} catch (PmsException e) 
			{
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
				e.printStackTrace();
			}
		} else 
		{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "信息不完整！", ""));
		}
	}
	
	/**
	 * 将新增的老人康复明细记录插入到老人康复记录列表中
	 */
	public void addOlderRecure(){
		olderRecureDetailList.add(selectOlderRecure);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "新增成功！", ""));
	}
	public void deleteRecureDetail() {
		if(olderRecureMainExtend.getOlderId() != null && 
		    olderRecureMainExtend.getRecureitemId() != null &&
		    olderRecureMainExtend.getStarttime() != null) {
			try {
				pensionRecureService.deletePensionOlderRecure(selectedRecureDetailList);
				olderRecureDetailList = pensionRecureService.selectOlderRecureDetail(olderRecureMainExtend.getId());
				searchRecureRecords();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "删除成功！", ""));
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
				e.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "信息不完整！", ""));
		}
	}
	
	/**
	 * 点击新增按钮
	 */
	public void showAdd() {
		 olderRecureMainExtend = new PensionOlderRecureMainExtend();
		 olderRecureDetailList = new ArrayList<PensionOlderRecureExtend>();
	}
	/**
	 * 点击新增按钮
	 */
	public void beforeAddOlderRecureMain() {
		recureList_src=new ArrayList<PensionRecureDetailExtend>();
		recureList_tar=new ArrayList<PensionRecureDetailExtend>();
		items = new DualListModel<PensionRecureDetailExtend>(recureList_src, recureList_tar);
		olderRecureMainExtend = new PensionOlderRecureMainExtend();
		olderRecureDetailList = new ArrayList<PensionOlderRecureExtend>();
		olderRecureMainExtend.setInputer(curUser.getId());
		olderRecureMainExtend.setInputerName(curUser.getName());
		olderRecureMainExtend.setInputtime(new Date());
	}
	/**
	 * 显示项目
	 */
	public void showDetail() {
		RequestContext request = RequestContext.getCurrentInstance();
		olderRecureMainExtend = olderRecureMainExtendSel;
		if(olderRecureMainExtend != null && olderRecureMainExtend.getId() != null) {
			olderRecureDetailList = pensionRecureService.selectOlderRecureDetail(olderRecureMainExtend.getId());
			request.addCallbackParam("show", true);
		} else {
			request.addCallbackParam("show", false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "请选择项目", ""));
		}
	}
	
	
	public void delRecure() {
		olderRecureMainExtend = olderRecureMainExtendSel;
		if(olderRecureMainExtend != null && olderRecureMainExtend.getId() != null) {
			
			try {
				pensionRecureService.delOlderRecure(olderRecureMainExtend.getId());
				searchRecureRecords();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "删除成功", ""));
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
				e.printStackTrace();
			}
		
		} else {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "请选择项目", ""));
		}
	}
	
	//保存备注
	public void saveNotes() {
		if(olderRecureMainExtend != null && olderRecureMainExtend.getId() != null) {
			try {
				pensionRecureService.saveOlderRecureMain(olderRecureMainExtend);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "修改成功", ""));
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "修改失败", ""));
				e.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "修改失败", ""));
		}
	}
	
	/**
	 * 点击【编辑】按钮
	 * 
	 */
	public void editRecureDetail(PensionRecureDetailExtend recureDetailExtend){
		selectRecureDetailExtend=recureDetailExtend;
		copyRecureDetailExtend=new PensionRecureDetailExtend();
		copyRecureDetailExtend.setName(recureDetailExtend.getName());
		copyRecureDetailExtend.setStarttime(recureDetailExtend.getStarttime());
		copyRecureDetailExtend.setTotalday(recureDetailExtend.getTotalday());
		copyRecureDetailExtend.setNumber(recureDetailExtend.getNumber());
		copyRecureDetailExtend.setDutynurse(recureDetailExtend.getDutynurse());
		copyRecureDetailExtend.setDutynurseName(recureDetailExtend.getDutynurseName());
	}
	
	/**
	 * 保存对康复计划的编辑
	 */
	public void saveEditRecureDetail(){
		int index=recureList_tar.indexOf(selectRecureDetailExtend);
		recureList_tar.remove(index);
		selectRecureDetailExtend.setName(copyRecureDetailExtend.getName());
		selectRecureDetailExtend.setStarttime(copyRecureDetailExtend.getStarttime());
		selectRecureDetailExtend.setTotalday(copyRecureDetailExtend.getTotalday());
		selectRecureDetailExtend.setNumber(copyRecureDetailExtend.getNumber());
		selectRecureDetailExtend.setDutynurse(copyRecureDetailExtend.getDutynurse());
		selectRecureDetailExtend.setDutynurseName(copyRecureDetailExtend.getDutynurseName());
		String schemeStr=pensionRecureService.createScheme(selectRecureDetailExtend.getTotalday(),selectRecureDetailExtend.getNumber());
		selectRecureDetailExtend.setScheme(schemeStr);
		recureList_tar.add(index, selectRecureDetailExtend);
	}
	
	/**
	 * 点击【新增】
	 */
	public void beforeAddOlderRecure(){
		selectOlderRecure=new PensionOlderRecureExtend();
		this.initRecureDetailSql();
	}
	
	/**
	 * 点击【修改】
	 */
	public void beforeModifyOlderRecure(){
		RequestContext request = RequestContext.getCurrentInstance();
		if(selectOlderRecure!=null){
			this.initRecureDetailSql();
			request.addCallbackParam("validate", true);
		}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请先选中一条记录！", "请先选中一条记录！"));
			request.addCallbackParam("validate", false);
		}
	}
	
	/**
	 * 保存修改的记录
	 */
	public void saveOlderRecure(){
		//新增
		if(selectOlderRecure.getId()==null){
			selectOlderRecure.setCleared(NO_FLAG);
			selectOlderRecure.setOlderId(olderRecureMainExtend.getOlderId());
			selectOlderRecure.setOlderName(olderRecureMainExtend.getOlderName());
			selectOlderRecure.setRecureitemId(olderRecureMainExtend.getRecureitemId());
			selectOlderRecure.setRecuredetailName(olderRecureMainExtend.getRecureitemName());
			olderRecureDetailList.add(selectOlderRecure);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("新增记录成功！", "新增记录成功！"));
		}else{//修改
			
		}
	}
	
	public void deleteOlderRecure(){
		if(selectOlderRecure!=null){
			olderRecureDetailList.remove(selectOlderRecure);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("删除记录成功！", "删除记录成功！"));
		}else{
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("请先选中一条记录！", "请先选中一条记录！"));
		}
	}
	
	/**
	 * 保存新增的老人康复主记录和明细
	 */
	public void saveOlderRecures(){
		RequestContext request = RequestContext.getCurrentInstance();
		try{
			pensionRecureService.insertOlderRecures(addOlderRecureMainExtend,olderRecureDetailList);
			olderRecureMainExtendList.add(addOlderRecureMainExtend);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("新增成功！", "新增成功！"));
			request.addCallbackParam("validate", true);
		}catch(Exception e){
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("新增失败！", "新增失败！"));
			request.addCallbackParam("validate", false);
		}
	}
	
	
	public void checkSelectDate(){
		RequestContext request = RequestContext.getCurrentInstance();
		if(selectOlderRecure == null ){
			request.addCallbackParam("validate", false);
		}else{
			request.addCallbackParam("validate", true);
		}
	}
	
	public void onOlderRecureSelect(SelectEvent e){
		
	}
	
	public void onOlderRecureUnselect(UnselectEvent e){
		
	}
	
	
	
	
	/**
	 * 康复明细的SQL
	 */
	private void initRecureDetailSql() {
		recureDetailSql="select p.name,if(length(p.detail) > 10 ,substr(p.detail,0,10),p.detail),p.detail,p.dutyNurse,e.name "
					+" from pension_recure_detail p　"
					+" left join pension_employee e "
					+" on p.dutyNurse = e.id "
					+" where p.recureItem_id = " + olderRecureMainExtend.getRecureitemId()
					+"　and p.cleared = 2";
	}



	public String getOlderName() {
		return olderName;
	}

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public void setSelectedRow(PensionOlderRecureExtend selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PensionOlderRecureExtend getSelectedRow() {
		return selectedRow;
	}

	public void setOperationId(Short operationId) {
		this.operationId = operationId;
	}

	public Short getOperationId() {
		return operationId;
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

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setCurUser(PensionEmployee curUser) {
		this.curUser = curUser;
	}

	public PensionEmployee getCurUser() {
		return curUser;
	}

	public void setRecureItem(String recureItem) {
		this.recureItem = recureItem;
	}

	public String getRecureItem() {
		return recureItem;
	}

	public void setOlderRecureList(List<PensionOlderRecureExtend> olderRecureList) {
		this.olderRecureList = olderRecureList;
	}

	public List<PensionOlderRecureExtend> getOlderRecureList() {
		return olderRecureList;
	}


	public void setNewOlderSQL(String newOlderSQL) {
		this.newOlderSQL = newOlderSQL;
	}

	public String getNewOlderSQL() {
		return newOlderSQL;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setPensionRecureService(PensionRecureService pensionRecureService) {
		this.pensionRecureService = pensionRecureService;
	}

	public PensionRecureService getPensionRecureService() {
		return pensionRecureService;
	}

	public void setItemSQL(String itemSQL) {
		this.itemSQL = itemSQL;
	}

	public String getItemSQL() {
		return itemSQL;
	}

	public void setOlderRecureMainExtend(PensionOlderRecureMainExtend olderRecureMainExtend) {
		this.olderRecureMainExtend = olderRecureMainExtend;
	}

	public PensionOlderRecureMainExtend getOlderRecureMainExtend() {
		return olderRecureMainExtend;
	}

	public void setOlderRecureDetailList(List<PensionOlderRecureExtend> olderRecureDetailList) {
		this.olderRecureDetailList = olderRecureDetailList;
	}

	public List<PensionOlderRecureExtend> getOlderRecureDetailList() {
		return olderRecureDetailList;
	}

	public void setInputDivName(String inputDivName) {
		this.inputDivName = inputDivName;
	}

	public String getInputDivName() {
		return inputDivName;
	}

	public void setInputDivId(String inputDivId) {
		this.inputDivId = inputDivId;
	}

	public String getInputDivId() {
		return inputDivId;
	}

	public void setOlderRecureMainExtendList(
			List<PensionOlderRecureMainExtend> olderRecureMainExtendList) {
		this.olderRecureMainExtendList = olderRecureMainExtendList;
	}

	public List<PensionOlderRecureMainExtend> getOlderRecureMainExtendList() {
		return olderRecureMainExtendList;
	}

	public Long getRecureitemId() {
		return recureitemId;
	}

	public void setRecureitemId(Long recureitemId) {
		this.recureitemId = recureitemId;
	}

	public Integer getHandle() {
		return handle;
	}

	public void setHandle(Integer handle) {
		this.handle = handle;
	}

	public void setRecureitemName(String recureitemName) {
		this.recureitemName = recureitemName;
	}

	public String getRecureitemName() {
		return recureitemName;
	}

	public void setOlderRecureMainExtendSel(PensionOlderRecureMainExtend olderRecureMainExtendSel) {
		this.olderRecureMainExtendSel = olderRecureMainExtendSel;
	}

	public PensionOlderRecureMainExtend getOlderRecureMainExtendSel() {
		return olderRecureMainExtendSel;
	}


	public void setRecureList_tar(List<PensionRecureDetailExtend> recureList_tar) {
		this.recureList_tar = recureList_tar;
	}

	public List<PensionRecureDetailExtend> getRecureList_tar() {
		return recureList_tar;
	}


	public void setRecureList_src(List<PensionRecureDetailExtend> recureList_src) {
		this.recureList_src = recureList_src;
	}

	public List<PensionRecureDetailExtend> getRecureList_src() {
		return recureList_src;
	}

	public void setItems(DualListModel<PensionRecureDetailExtend> items) {
		this.items = items;
	}

	public DualListModel<PensionRecureDetailExtend> getItems() {
		return items;
	}

	public void setRecureConveter(RecureConveter recureConveter) {
		this.recureConveter = recureConveter;
	}

	public RecureConveter getRecureConveter() {
		return recureConveter;
	}

	public void setOlderRecureItemList(List<PensionOlderRecureMainExtend> olderRecureItemList) {
		this.olderRecureItemList = olderRecureItemList;
	}

	public List<PensionOlderRecureMainExtend> getOlderRecureItemList() {
		return olderRecureItemList;
	}

	public void setSelectedRecureDetailList(List<PensionOlderRecureExtend> selectedRecureDetailList) {
		this.selectedRecureDetailList = selectedRecureDetailList;
	}

	public List<PensionOlderRecureExtend> getSelectedRecureDetailList() {
		return selectedRecureDetailList;
	}

	public void setAddRow(PensionOlderRecureExtend addRow) {
		this.addRow = addRow;
	}

	public PensionOlderRecureExtend getAddRow() {
		return addRow;
	}


	public String getRecoverToSql() {
		return recoverToSql;
	}


	public void setRecoverToSql(String recoverToSql) {
		this.recoverToSql = recoverToSql;
	}


	public String getFitcolRecover() {
		return fitcolRecover;
	}


	public void setFitcolRecover(String fitcolRecover) {
		this.fitcolRecover = fitcolRecover;
	}


	public String getDisplaycolRecover() {
		return displaycolRecover;
	}


	public void setDisplaycolRecover(String displaycolRecover) {
		this.displaycolRecover = displaycolRecover;
	}



	public PensionOlderRecureMainExtend getAddOlderRecureMainExtend() {
		return addOlderRecureMainExtend;
	}



	public void setAddOlderRecureMainExtend(
			PensionOlderRecureMainExtend addOlderRecureMainExtend) {
		this.addOlderRecureMainExtend = addOlderRecureMainExtend;
	}



	public PensionRecureDetailExtend getSelectRecureDetailExtend() {
		return selectRecureDetailExtend;
	}



	public void setSelectRecureDetailExtend(
			PensionRecureDetailExtend selectRecureDetailExtend) {
		this.selectRecureDetailExtend = selectRecureDetailExtend;
	}



	public PensionRecureDetailExtend getCopyRecureDetailExtend() {
		return copyRecureDetailExtend;
	}



	public void setCopyRecureDetailExtend(
			PensionRecureDetailExtend copyRecureDetailExtend) {
		this.copyRecureDetailExtend = copyRecureDetailExtend;
	}



	public PensionOlderRecureExtend getSelectOlderRecure() {
		return selectOlderRecure;
	}



	public void setSelectOlderRecure(PensionOlderRecureExtend selectOlderRecure) {
		this.selectOlderRecure = selectOlderRecure;
	}



	public String getRecureDetailSql() {
		return recureDetailSql;
	}



	public void setRecureDetailSql(String recureDetailSql) {
		this.recureDetailSql = recureDetailSql;
	}
	
}
