package controller.configureManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionFloor;


public class FloorManageController implements Serializable{

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
	private List<PensionFloor> floors = new ArrayList<PensionFloor>();
	
	private PensionFloor selectedFloor = new PensionFloor();
	/**
	 * 选中记录
	 */
	private PensionFloor addedFloorRow = new PensionFloor();
	private PensionBuilding buildRow;
	
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
	
	private String buildSQL;
	private String floorSQL;
	private String name;
	private String notes;
	
	@PostConstruct
	public void init()
	{
		initSQL();
		selectFloorRecords();
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
	}
	public void clearSearchForm()
	{
		buildName = "";
		floorName = "";
	}
	/**
	 * 未选中一条记录，设置按钮可用状态
	 */
	public void setUnableFlag(UnselectEvent event) {
		disabledFlag=true;
		clearAddForm();
	}
	public void selectFloorRecords()
	{
		floors = floorManageService.selectFloorRecords(buildName,floorName);
		setUnableFlag(null);
	}
	/**
	 * 逻辑删除申请记录
	 */
	public void deleteFloorRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request=RequestContext.getCurrentInstance(); 
		if(selectedFloor.getOldernumber()>0)
		{
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, selectedFloor
							.getName() + JavaConfig.fetchProperty("FloorManageController.UnDelete"), ""));
		}
		else if(selectedFloor.getBednumber()>0)
		{
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, selectedFloor
							.getName() + JavaConfig.fetchProperty("FloorManageController.hasBed"), ""));
		}
		else
		{
			selectedFloor.setCleared(1);
			floorManageService.updateFloorRecord(selectedFloor);
			/*delete rooms belong to floor*/
			
			roomManageService.deleteRoomsByFloor(selectedFloor.getId());
			
			buildRow = builderManageService.selectByPrimaryKey(selectedFloor.getBuildId());
			buildRow.setFloornumber(buildRow.getFloornumber()-1);
			builderManageService.updateBuildRecord(buildRow);
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
				FacesMessage.SEVERITY_INFO, selectedFloor
						.getName() + JavaConfig.fetchProperty("FloorManageController.Delete"), ""));
		selectFloorRecords();
		}
	}
	/**
	 * 保存楼层申请
	 */
	public void insertFloorRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request=RequestContext.getCurrentInstance();
		if (operationId == (short) 1) {
			addedFloorRow = new PensionFloor();
			addedFloorRow.setName(selectedFloor.getName());
			addedFloorRow.setNotes(selectedFloor.getNotes());
			addedFloorRow.setBuildname(selectedFloor.getBuildname());
			addedFloorRow.setBuildId(selectedFloor.getBuildId());
			
			
			floorManageService.insertFloorRecord(addedFloorRow);
			
			buildRow = builderManageService.selectByPrimaryKey(addedFloorRow.getBuildId());
			buildRow.setFloornumber(buildRow.getFloornumber()+1);
			builderManageService.updateBuildRecord(buildRow);
			
			request.addCallbackParam("sendMessage", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, addedFloorRow
							.getName() + JavaConfig.fetchProperty("FloorManageController.Add"), ""));
		} else {
			floorManageService.updateFloorRecord(selectedFloor);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, selectedFloor.getName() + JavaConfig.fetchProperty("FloorManageController.Update"), ""));
			request.addCallbackParam("sendMessage",true);
		}
		selectFloorRecords();

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
		selectedFloor=new PensionFloor();
	}
	public List<PensionFloor> getFloors() {
		return floors;
	}

	public void setFloors(List<PensionFloor> floors) {
		this.floors = floors;
	}

	public FloorManageService getFloorManageService() {
		return floorManageService;
	}

	public void setFloorManageService(FloorManageService floorManageService) {
		this.floorManageService = floorManageService;
	}
	public PensionFloor getSelectedFloor() {
		return selectedFloor;
	}
	public void setSelectedFloor(PensionFloor selectedFloor) {
		this.selectedFloor = selectedFloor;
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

	/**
	 * @param builderManageService the builderManageService to set
	 */
	public void setBuilderManageService(BuilderManageService builderManageService) {
		this.builderManageService = builderManageService;
	}

	/**
	 * @return the builderManageService
	 */
	public BuilderManageService getBuilderManageService() {
		return builderManageService;
	}

	/**
	 * @param roomManageService the roomManageService to set
	 */
	public void setRoomManageService(RoomManageService roomManageService) {
		this.roomManageService = roomManageService;
	}

	/**
	 * @return the roomManageService
	 */
	public RoomManageService getRoomManageService() {
		return roomManageService;
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
	
	
}
