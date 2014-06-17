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
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;


import service.configureManage.BedManageService;
import service.configureManage.BuilderManageService;
import service.configureManage.FloorManageService;
import service.configureManage.RoomManageService;
import util.JavaConfig;
import domain.configureManage.PensionBed;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionFloor;
import domain.configureManage.PensionRoom;

@ManagedBean(name="roomManageController")
@ViewScoped
public class RoomManageController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient BedManageService bedManageService;
	private transient RoomManageService roomManageService;
	private transient FloorManageService floorManageService;
	private transient BuilderManageService builderManageService;
	/**
	 * 所有记录列表
	 */
	private List<PensionRoom> beds = new ArrayList<PensionRoom>();
	private PensionFloor floorRow = new PensionFloor();
	private PensionBuilding buildRow;
	private PensionRoom selectedRoom = new PensionRoom();
	/**
	 * 选中记录
	 */
	private PensionRoom addedRoomRow = new PensionRoom();
	
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
	
	private String buildSQL;
	private String floorSQL;
	private String roomSQL;
	private String name;
	private String notes;
	
	@PostConstruct
	public void init()
	{
		initSQL();
		selectRoomRecords();
		disabledFlag=true;
	}

	/**
	 * 选中一条记录，设置按钮可用状态
	 */
	public void setEnableFlag(SelectEvent event) {
		//修改、删除按钮状态
		disabledFlag=false;
	}
	public void initSQL()
	{
		buildSQL = "select * from pension_building  where cleared = 2";
		floorSQL = "select * from pension_floor where cleared = 2 and buildName like '%" +buildName + "'";
		roomSQL = "select * from pension_room where cleared = 2 and floorName like '%" +floorName + "' and buildName like '%" + buildName +"'";
	}
	public void clearSearchForm()
	{
		buildName = "";
		floorName = "";
		roomName = "";
		
	}
	/**
	 * 未选中一条记录，设置按钮可用状态
	 */
	public void setUnableFlag(UnselectEvent event) {
		disabledFlag=true;
		clearAddForm();
	}
	public void selectRoomRecords()
	{
		beds = roomManageService.selectRoomRecords(buildName,floorName,roomName);
		setUnableFlag(null);
	}
	/**
	 * 逻辑删除房间
	 */
	public void deleteRoomRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request=RequestContext.getCurrentInstance(); 
		if(selectedRoom.getOldernumber()>0)
		{
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, selectedRoom
							.getName() + JavaConfig.fetchProperty("RoomManageController.UnDelete"), ""));
		}
		else
		{
			selectedRoom.setCleared(1);
			roomManageService.updateRoomRecord(selectedRoom);
			
			floorRow = floorManageService.selectByPrimaryKey(selectedRoom.getFloorId());
			floorRow.setRoomnumber(floorRow.getRoomnumber()-1);
			floorManageService.updateFloorRecord(floorRow);
			
			buildRow = builderManageService.selectByPrimaryKey(floorRow.getBuildId());
			buildRow.setRoomnumber(buildRow.getRoomnumber()-1);
			builderManageService.updateBuildRecord(buildRow);
			
			/*delete beds belong to room*/
			
			bedManageService.deleteBedsByRoom(selectedRoom.getId());
			
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
				FacesMessage.SEVERITY_INFO, selectedRoom
						.getName() + JavaConfig.fetchProperty("RoomManageController.Delete"), ""));
		selectRoomRecords();
		}
	}
	/**
	 * 保存一条入住申请
	 */
	public void insertRoomRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request=RequestContext.getCurrentInstance();

		if (operationId == (short) 1) {
			
			addedRoomRow = new PensionRoom();
			addedRoomRow.setName(selectedRoom.getName());
			addedRoomRow.setNotes(selectedRoom.getNotes());
			addedRoomRow.setWay(selectedRoom.getWay());
			addedRoomRow.setPhone(selectedRoom.getPhone());
			addedRoomRow.setAddress(selectedRoom.getAddress());
			addedRoomRow.setBuildname(selectedRoom.getBuildname());
			addedRoomRow.setFloorname(selectedRoom.getFloorname());
			addedRoomRow.setFloorId(selectedRoom.getFloorId());

			roomManageService.insertRoomRecord(addedRoomRow);
			
			
			floorRow = floorManageService.selectByPrimaryKey(addedRoomRow.getFloorId());
			floorRow.setRoomnumber(floorRow.getRoomnumber()+1);
			floorManageService.updateFloorRecord(floorRow);
			
			buildRow = builderManageService.selectByPrimaryKey(floorRow.getBuildId());
			buildRow.setRoomnumber(buildRow.getRoomnumber()+1);
			builderManageService.updateBuildRecord(buildRow);
			
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, addedRoomRow
							.getName() + JavaConfig.fetchProperty("RoomManageController.Add"), ""));
		} else {
			roomManageService.updateRoomRecord(selectedRoom);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, selectedRoom.getName() + JavaConfig.fetchProperty("RoomManageController.Update"), ""));
			request.addCallbackParam("sendMessage",true);
		}
		selectRoomRecords();

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
		selectedRoom=new PensionRoom();
	}
	public List<PensionRoom> getRooms() {
		return beds;
	}

	public void setRooms(List<PensionRoom> beds) {
		this.beds = beds;
	}

	public RoomManageService getRoomManageService() {
		return roomManageService;
	}

	public void setRoomManageService(RoomManageService roomManageService) {
		this.roomManageService = roomManageService;
	}
	public PensionRoom getSelectedRoom() {
		return selectedRoom;
	}
	public void setSelectedRoom(PensionRoom selectedRoom) {
		this.selectedRoom = selectedRoom;
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
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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

	/**
	 * @param bedManageService the bedManageService to set
	 */
	public void setBedManageService(BedManageService bedManageService) {
		this.bedManageService = bedManageService;
	}

	/**
	 * @return the bedManageService
	 */
	public BedManageService getBedManageService() {
		return bedManageService;
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
	
	
}
