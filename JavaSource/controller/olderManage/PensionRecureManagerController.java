package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

import service.olderManage.PensionOlderRecureExtend;
import service.olderManage.PensionOlderRecureMainExtend;
import service.olderManage.PensionRecureManagerService;
import util.PmsException;
import com.centling.his.util.SessionManager;
import domain.employeeManage.PensionEmployee;

/**
 * 
 * @author:bill
 * @version: 1.0
 * @Date:2013-10-10 下午01:16:44
 */
public class PensionRecureManagerController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient PensionRecureManagerService pensionRecureManagerService;

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
	/**
	 * 所有申请记录列表
	 */
	private List<PensionOlderRecureExtend> olderRecureList = new ArrayList<PensionOlderRecureExtend>();
	private List<PensionOlderRecureExtend> olderRecureDetailList = new ArrayList<PensionOlderRecureExtend>();
	private List<PensionOlderRecureMainExtend> olderRecureMainExtendList = new ArrayList<PensionOlderRecureMainExtend>();
	
	/**
	 * 选中申请记录
	 */
	private PensionOlderRecureExtend selectedRow;
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

	@PostConstruct
	public void init() throws PmsException {
		curUser = (PensionEmployee) SessionManager
				.getSessionAttribute(SessionManager.EMPLOYEE);
		userName = curUser.getName();
		initSql();
		handle = 1;
		searchRecureRecords();
		
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
	 * 条件查询
	 */
	public void searchRecureRecords() {
		olderRecureMainExtendList = pensionRecureManagerService.selectOlderRecureMain(olderId, recureitemId, handle);
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
	}


	
	
	public void saveRecureDetail() {
		if(olderRecureMainExtend.getOlderId() != null && 
				olderRecureMainExtend.getRecureitemId() != null &&
				olderRecureMainExtend.getStarttime() != null) {
			//List<PensionRecureDetail> asfd = pensionRecureManagerService.selectRecureDetail(olderRecureMainExtend.getRecureitemId());
		
			try {
				pensionRecureManagerService.updatePensionOlderRecure(olderRecureMainExtend, olderRecureDetailList);
				olderRecureDetailList = pensionRecureManagerService.selectOlderRecureDetail(olderRecureMainExtend.getId());
				searchRecureRecords();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "保存成功！", ""));
			} catch (PmsException e) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, e.getMessage(), ""));
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "信息不完整！", ""));
		}
	}

	/**
	 * 显示项目
	 */
	public void showDetail() {
		final RequestContext request = RequestContext.getCurrentInstance();
		olderRecureMainExtend = olderRecureMainExtendSel;
		if(olderRecureMainExtend != null && olderRecureMainExtend.getId() != null) {
			olderRecureDetailList = pensionRecureManagerService.selectOlderRecureDetail(olderRecureMainExtend.getId());
			request.addCallbackParam("show", true);
		} else {
			request.addCallbackParam("show", false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "请选择项目", ""));
		}
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

	public void setPensionRecureManagerService(PensionRecureManagerService pensionRecureManagerService) {
		this.pensionRecureManagerService = pensionRecureManagerService;
	}

	public PensionRecureManagerService getPensionRecureManagerService() {
		return pensionRecureManagerService;
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
	
}
