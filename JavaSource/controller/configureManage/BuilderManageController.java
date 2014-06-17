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
import domain.configureManage.PensionBuilding;

@ManagedBean(name = "builderManageController")
@ViewScoped
public class BuilderManageController implements Serializable {

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
	private List<PensionBuilding> builds = new ArrayList<PensionBuilding>();

	private PensionBuilding selectedBuild = new PensionBuilding();
	/**
	 * 选中记录
	 */
	private PensionBuilding addedBuildRow = new PensionBuilding();

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

	private String buildSQL;
	private String name;
	private String notes;

	@PostConstruct
	public void init() {
		initSQL();
		selectBuildRecords();
		disabledFlag = true;
	}

	/**
	 * 选中一条记录，设置按钮可用状态
	 */
	public void setEnableFlag(SelectEvent event) {
		// 修改、删除按钮状态
		disabledFlag = false;
	}

	public void initSQL() {
		buildSQL = "select * from pension_building  where cleared = 2";
	}

	public void clearSearchForm() {
		buildName = "";
	}

	/**
	 * 未选中一条记录，设置按钮可用状态
	 */
	public void setUnableFlag(UnselectEvent event) {
		disabledFlag = true;
		clearAddForm();
	}

	public void selectBuildRecords() {
		builds = builderManageService.selectBuildRecords(buildName);
		setUnableFlag(null);
	}

	/**
	 * 逻辑删除大厦记录
	 */
	public void deleteBuildRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		if (selectedBuild.getOldernumber() > 0) {
			request.addCallbackParam("sendMessage", true);
			facesContext
					.addMessage(
							"",
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									selectedBuild.getName()
											+ JavaConfig
													.fetchProperty("BuilderManageController.UnDelete"),
									""));
		} else if (selectedBuild.getBednumber() > 0) {
			request.addCallbackParam("sendMessage", true);
			facesContext
					.addMessage(
							"",
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									selectedBuild.getName()
											+ JavaConfig
													.fetchProperty("BuilderManageController.hasBed"),
									""));
		} else {
			selectedBuild.setCleared(1);
			builderManageService.updateBuildRecord(selectedBuild);
			/* delete floors belong to build */

			floorManageService.deleteFloorsByBuild(selectedBuild.getId());

			request.addCallbackParam("sendMessage", true);
			facesContext
					.addMessage(
							"",
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									selectedBuild.getName()
											+ JavaConfig
													.fetchProperty("BuilderManageController.Delete"),
									""));
			selectBuildRecords();
		}
	}

	/**
	 * 保存大厦
	 */
	public void insertBuildRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();

		if (operationId == (short) 1) {
			addedBuildRow = new PensionBuilding();
			addedBuildRow.setName(selectedBuild.getName());
			addedBuildRow.setNotes(selectedBuild.getNotes());
			addedBuildRow.setBuildingCategary(selectedBuild
					.getBuildingCategary());
			builderManageService.insertBuildRecord(addedBuildRow);
			request.addCallbackParam("sendMessage", true);
			facesContext
					.addMessage(
							"",
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									addedBuildRow.getName()
											+ JavaConfig
													.fetchProperty("BuilderManageController.Add"),
									""));
		} else {
			builderManageService.updateBuildRecord(selectedBuild);
			request.addCallbackParam("sendMessage", true);
			facesContext
					.addMessage(
							"",
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									selectedBuild.getName()
											+ JavaConfig
													.fetchProperty("BuilderManageController.Update"),
									""));

		}
		selectBuildRecords();

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
		selectedBuild = new PensionBuilding();
	}

	public List<PensionBuilding> getBuilds() {
		return builds;
	}

	public void setBuilds(List<PensionBuilding> builds) {
		this.builds = builds;
	}

	public BuilderManageService getBuilderManageService() {
		return builderManageService;
	}

	public void setBuilderManageService(
			BuilderManageService builderManageService) {
		this.builderManageService = builderManageService;
	}

	public PensionBuilding getSelectedBuild() {
		return selectedBuild;
	}

	public void setSelectedBuild(PensionBuilding selectedBuild) {
		this.selectedBuild = selectedBuild;
	}

	/**
	 * @return the disabledFlag
	 */
	public boolean isDisabledFlag() {
		return disabledFlag;
	}

	/**
	 * @param disabledFlag
	 *            the disabledFlag to set
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
	 * @param name
	 *            the name to set
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
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @param bedManageService
	 *            the bedManageService to set
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

	/**
	 * @param roomManageService
	 *            the roomManageService to set
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
	 * @param floorManageService
	 *            the floorManageService to set
	 */
	public void setFloorManageService(FloorManageService floorManageService) {
		this.floorManageService = floorManageService;
	}

	/**
	 * @return the floorManageService
	 */
	public FloorManageService getFloorManageService() {
		return floorManageService;
	}

	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	public String getBuildSQL() {
		return buildSQL;
	}

	public void setBuildSQL(String buildSQL) {
		this.buildSQL = buildSQL;
	}

}
