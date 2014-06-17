package controller.configureManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;


import service.configureManage.BedManageService;
import service.configureManage.BuilderManageService;
import service.configureManage.FloorManageService;
import service.configureManage.RoomManageService;
import service.olderManage.OlderService;
import util.JavaConfig;
import domain.configureManage.PensionBed;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionFloor;
import domain.configureManage.PensionRoom;
import domain.olderManage.PensionOlder;
import domain.system.PensionDept;

@ManagedBean(name="bedManageController")
@ViewScoped
public class BedManageController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient BedManageService bedManageService;
	private transient RoomManageService roomManageService;
	private transient FloorManageService floorManageService;
	private transient BuilderManageService builderManageService;
	private transient OlderService olderService;

	/**
	 * 所有记录列表
	 */
	private List<PensionBed> beds = new ArrayList<PensionBed>();
	
	private PensionBed selectedBed = new PensionBed();
	/**
	 * 选中记录
	 */
	private PensionBed addedBedRow = new PensionBed();
	private PensionRoom roomRow;
	private PensionFloor floorRow;
	private PensionBuilding buildRow;
	private List<PensionOlder> older = new ArrayList<PensionOlder>();
	
	/**
	 * 设置按钮的可用性
	 */
	private boolean disabledFlag;
	/**
	 * 操作标记 1 为新增 2 为修改
	 */
	private Short operationId;
	/**
	 * 参数列表
	 */
	private String buildName = "";
	private String floorName = "";
	private String roomName = "";
	private String bedName = "";
	
	private String buildSQL;
	private String floorSQL;
	private String roomSQL;
	private String bedSQL;
	
	//设置拓扑树
	private TreeNode root;  
	private TreeNode selectedNode; 
	@PostConstruct
	public void init()
	{
		initSQL();
		selectBedRecords();
		disabledFlag=true;
		initTreeNode();
	}
	// 选择节点
	public void onNodeSelect(NodeSelectEvent event) {
		event.getTreeNode().setSelected(true);
		String type = event.getTreeNode().getType();
//		olderService.selectOlderByKey(new Long(10));
		if(type == "default")
		{
			older = olderService.selectOldersByBed(null, null, null, null);
		}
		if(type == "build")
		{
			PensionBuilding o = (PensionBuilding)event.getTreeNode().getData() ;
			older = olderService.selectOldersByBed(o.getName(),null, null, null);
		}
		if(type == "floor")
		{
			PensionFloor o = (PensionFloor)event.getTreeNode().getData() ;
			older = olderService.selectOldersByBed(null, o.getName(), null, null);
		}
		if(type == "room")
		{
			PensionRoom o = (PensionRoom)event.getTreeNode().getData() ;
			older = olderService.selectOldersByBed(null, null, o.getName(), null);
		}
		if(type == "bed")
		{
			PensionBed o = (PensionBed)event.getTreeNode().getData() ;
			older = olderService.selectOldersByBed(null, null, null, o.getName());
		}

	}
	public void initTreeNode()
	{
		List<PensionBuilding> buildList = builderManageService.selectBuildRecords();
		root = new DefaultTreeNode("养老院", null); 
		root.setExpanded(true);
		for(int i=0;i<buildList.size();i++)
		{
			TreeNode buildNode = new DefaultTreeNode("build",buildList.get(i), root); 
//			buildNode.setExpanded(true);
			List<PensionFloor> floorList = floorManageService.selectFloorRecords(buildList.get(i).getId());
			for(int j=0;j<floorList.size();j++)
			{
				TreeNode floorNode = new DefaultTreeNode("floor",floorList.get(j), buildNode); 
//				floorNode.setExpanded(true);
				List<PensionRoom> roomList = roomManageService.selectRoomRecords(floorList.get(j).getId());
				for(int k=0;k<roomList.size();k++)
				{
					TreeNode roomNode = new DefaultTreeNode("room",roomList.get(k), floorNode);
//					roomNode.setExpanded(true);
					List<PensionBed> bedList = bedManageService.selectBedRecords(roomList.get(k).getId());
					for(int s=0;s<bedList.size();s++)
					{
						TreeNode bedNode = new DefaultTreeNode("bed",bedList.get(s), roomNode); 
					}
				}
			}
		}
		
	}
	 public void add() 
	 {
		 RequestContext requestContext = RequestContext.getCurrentInstance();
		 requestContext.addCallbackParam("type", selectedNode.getType().toString());
	 }
	public void initSQL()
	{
		buildSQL = "select * from pension_building  where cleared = 2";
		floorSQL = "select * from pension_floor where cleared = 2 and buildName like '%" +buildName + "'";
		roomSQL = "select * from pension_room where cleared = 2 and floorName like '%" +floorName + "' and buildName like '%" + buildName +"'";
		bedSQL = "select * from pension_bed where cleared = 2 and roomName like '%" + roomName + "' and floorName like '%" +floorName + "' and buildName like '%" + buildName + "'";
	}
	/**
	 * 选中一条记录，设置按钮可用状态
	 */
	public void setEnableFlag(SelectEvent event) {
		//修改、删除按钮状态
		disabledFlag=false;
	}

	public void clearSearchForm()
	{
		buildName = "";
		floorName = "";
		roomName = "";
		bedName = "";
		
	}
	/**
	 * 未选中一条记录，设置按钮可用状态
	 */
	public void setUnableFlag(UnselectEvent event) {
		disabledFlag=true;
		clearAddForm();
	}
	public void selectBedRecords()
	{
		beds = bedManageService.selectBedRecords(buildName,floorName,roomName,bedName);
		setUnableFlag(null);
	}
	/**
	 * 逻辑删除申请记录
	 */
	public void deleteBedRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request=RequestContext.getCurrentInstance(); 
		if(selectedBed.getOldernumber()>0)
		{
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
			FacesMessage.SEVERITY_INFO, selectedBed
							.getName() + JavaConfig.fetchProperty("BedManageController.UnDelete"), ""));
		}
		else
		{
			selectedBed.setCleared(1);
			bedManageService.updateBedRecord(selectedBed);
			
			roomRow = roomManageService.selectByPrimaryKey(selectedBed.getRoomId());
			roomRow.setBednumber(roomRow.getBednumber()-1);
			roomManageService.updateRoomRecord(roomRow);
			
			
			floorRow = floorManageService.selectByPrimaryKey(roomRow.getFloorId());
			floorRow.setBednumber(floorRow.getBednumber()-1);
			floorManageService.updateFloorRecord(floorRow);
			
			buildRow = builderManageService.selectByPrimaryKey(floorRow.getBuildId());
			buildRow.setBednumber(buildRow.getBednumber()-1);
			builderManageService.updateBuildRecord(buildRow);
			
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
				FacesMessage.SEVERITY_INFO, selectedBed
						.getName() + JavaConfig.fetchProperty("BedManageController.Delete"), ""));
		}
		selectBedRecords();
	}
	/**
	 * 保存一条入住申请
	 */
	public void insertBedRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request=RequestContext.getCurrentInstance();

		if (operationId == (short) 1) {
			addedBedRow = new PensionBed();
			addedBedRow.setName(selectedBed.getName());
			addedBedRow.setBuildname(selectedBed.getBuildname());
			addedBedRow.setFloorname(selectedBed.getFloorname());
			addedBedRow.setRoomname(selectedBed.getRoomname());
			addedBedRow.setNotes(selectedBed.getNotes());
			addedBedRow.setRoomId(selectedBed.getRoomId());
			addedBedRow.setTypeId(selectedBed.getTypeId());
			addedBedRow.setTypename(selectedBed.getTypename());
			bedManageService.insertBedRecord(addedBedRow);
			
			roomRow = roomManageService.selectByPrimaryKey(addedBedRow.getRoomId());
			roomRow.setBednumber(roomRow.getBednumber()+1);
			roomManageService.updateRoomRecord(roomRow);
			
			floorRow = floorManageService.selectByPrimaryKey(roomRow.getFloorId());
			floorRow.setBednumber(floorRow.getBednumber()+1);
			floorManageService.updateFloorRecord(floorRow);
			
			buildRow = builderManageService.selectByPrimaryKey(floorRow.getBuildId());
			buildRow.setBednumber(buildRow.getBednumber()+1);
			builderManageService.updateBuildRecord(buildRow);
			
			
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, addedBedRow
							.getName() + JavaConfig.fetchProperty("BedManageController.Add"), ""));
		} else {
			addedBedRow = selectedBed;
			bedManageService.updateBedRecord(addedBedRow);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, addedBedRow.getName() + JavaConfig.fetchProperty("BedManageController.Update"), ""));
			request.addCallbackParam("sendMessage",true);
		}
		selectBedRecords();

	}
	
	/**
	 * 修改触发事件
	 */
	public void showAddForm() {
		operationId = 1;
	}
	/**
	 * 修改触发事件
	 */
	public void showEditForm() {
		operationId = 2;
	}
	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		selectedBed=new PensionBed();
	}
	public List<PensionBed> getBeds() {
		return beds;
	}

	public void setBeds(List<PensionBed> beds) {
		this.beds = beds;
	}

	public BedManageService getBedManageService() {
		return bedManageService;
	}

	public void setBedManageService(BedManageService bedManageService) {
		this.bedManageService = bedManageService;
	}
	public PensionBed getSelectedBed() {
		return selectedBed;
	}
	public void setSelectedBed(PensionBed selectedBed) {
		this.selectedBed = selectedBed;
	}
	/**
	 * @return the disabledFlag
	 */
	public boolean isDisabledFlag() {
		return disabledFlag;
	}
	/**
	 * @param disabledFlag the disabledFlag to set
	 */
	public void setDisabledFlag(boolean disabledFlag) {
		this.disabledFlag = disabledFlag;
	}

	public RoomManageService getRoomManageService() {
		return roomManageService;
	}

	public void setRoomManageService(RoomManageService roomManageService) {
		this.roomManageService = roomManageService;
	}

	public FloorManageService getFloorManageService() {
		return floorManageService;
	}

	public void setFloorManageService(FloorManageService floorManageService) {
		this.floorManageService = floorManageService;
	}

	public BuilderManageService getBuilderManageService() {
		return builderManageService;
	}

	public void setBuilderManageService(BuilderManageService builderManageService) {
		this.builderManageService = builderManageService;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getBedName() {
		return bedName;
	}

	public void setBedName(String bedName) {
		this.bedName = bedName;
	}

	public String getBuildSQL() {
		return buildSQL;
	}

	public void setBuildSQL(String buildSQL) {
		this.buildSQL = buildSQL;
	}

	public String getFloorSQL() {
		return floorSQL;
	}

	public void setFloorSQL(String floorSQL) {
		this.floorSQL = floorSQL;
	}

	public String getRoomSQL() {
		return roomSQL;
	}

	public void setRoomSQL(String roomSQL) {
		this.roomSQL = roomSQL;
	}

	public String getBedSQL() {
		return bedSQL;
	}

	public void setBedSQL(String bedSQL) {
		this.bedSQL = bedSQL;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}
	public void setOlderService(OlderService olderService) {
		this.olderService = olderService;
	}
	public OlderService getOlderService() {
		return olderService;
	}
	public void setOlder(List<PensionOlder> older) {
		this.older = older;
	}
	public List<PensionOlder> getOlder() {
		return older;
	}
	
	
}
