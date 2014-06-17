package controller.logisticsManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import domain.logisticsManage.PensionVehicle;

import service.logisticsManage.CarManageService;

@SuppressWarnings("serial")
public class CarManageController implements Serializable {

	/**
	 * 注入业务
	 */
	private transient CarManageService carManageService;

	/**
	 * 所有记录列表
	 */
	private List<PensionVehicle> cars = new ArrayList<PensionVehicle>();
	/**
	 * 选中记录
	 */
	private PensionVehicle selectedCar = new PensionVehicle();
	/**
	 * 新记录
	 */
	private PensionVehicle addCarRow = new PensionVehicle();

	private String holdPeoples;// 容纳 人数
	private String remainFuel;// 当前油量
	private String currentKilometers;// 当前公里数
	private String candriveKilometers;// 可用公里数

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
	private String carSQL;
	private String empSQL;
	private String carNumber = null;
	private String title;

	@PostConstruct
	public void init() {
		initSQL();
		selectCarRecords();
		disabledFlag = true;
	}

	/**
	 * 选中一条记录，设置按钮可用状态
	 */
	public void setEnableFlag(SelectEvent event) {
		// 修改、删除按钮状态
		disabledFlag = false;
	}

	/**
	 * 未选中一条记录，设置按钮可用状态
	 */
	public void setUnableFlag(UnselectEvent event) {
		disabledFlag = true;
		clearAddForm();
	}

	public void initSQL() {
		carSQL = "select pension_vehicle.* ,pension_employee.name as employeeName "
				+ "from pension_vehicle right join pension_employee on pension_vehicle.contacter_id = pension_employee.id "
				+ "where pension_vehicle.cleared = 2";
		empSQL = "select * from pension_employee p where p.cleared=2 ";
	}

	public void clearSearchForm() {
		carNumber = null;
	}

	public void selectCarRecords() {
		if (carNumber == "")
			clearSearchForm();
		cars = carManageService.selectCarRecords(carNumber);
		setUnableFlag(null);
	}

	/**
	 * 逻辑删除车辆信息
	 */
	public void deleteCarRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();

		selectedCar.setCleared(1);
		carManageService.updateBuildRecord(selectedCar);
		request.addCallbackParam("sendMessage", true);
		facesContext.addMessage("", new FacesMessage(
				FacesMessage.SEVERITY_INFO, selectedCar.getPlateNumber()
						+ "删除成功", ""));
		selectCarRecords();
	}

	/**
	 * 保存车辆
	 */
	public void insertCarRecord() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final RequestContext request = RequestContext.getCurrentInstance();
		// 验证前台输入数据
		try {
			if (holdPeoples != null && !"".equals(holdPeoples)
					&& new Integer(holdPeoples).intValue() < 1) {
				facesContext.addMessage("", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "请正确输入容纳人数！", ""));
				request.addCallbackParam("validate", false);
				return;
			}
			Float tempRemainFuel = new Float(0);
			if (remainFuel != null && !"".equals(remainFuel)
					&& new Float(remainFuel).floatValue() > 0) {
				tempRemainFuel = new Float(remainFuel);
			} /*else {
				facesContext.addMessage("", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "请正确输入剩余油量！", ""));
				request.addCallbackParam("validate", false);
				return;
			}*/
			Float tempCurrentKilometers = new Float(0);
			if (currentKilometers != null && !"".equals(currentKilometers)
					&& new Float(currentKilometers).floatValue() > 0) {
				tempCurrentKilometers = new Float(currentKilometers);
			} else {
				facesContext.addMessage("", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "请正确输入当前公里数！", ""));
				request.addCallbackParam("validate", false);
				return;
			}
			Float tempCandriveKilometers = new Float(0);
			if (candriveKilometers != null && !"".equals(candriveKilometers)
					&& new Float(candriveKilometers).floatValue() > 0) {
				tempCandriveKilometers = new Float(candriveKilometers);
			} else {
				facesContext.addMessage("", new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "请正确输入可用公里数！", ""));
				request.addCallbackParam("validate", false);
				return;
			}
			selectedCar.setHoldPeoples(new Integer(holdPeoples));
			selectedCar.setRemainFuel(tempRemainFuel);
			selectedCar.setCurrentKilometers(tempCurrentKilometers);
			selectedCar.setCandriveKilometers(tempCandriveKilometers);
		} catch (NumberFormatException e) {
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "请输入有效数字！", ""));
			request.addCallbackParam("validate", false);
			return;
		}

		if (operationId == (short) 1) {
			carManageService.insertBuildRecord(selectedCar);
			request.addCallbackParam("validate", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, selectedCar.getPlateNumber()
							+ "添加成功", ""));
		} else {
			carManageService.updateBuildRecord(selectedCar);
			request.addCallbackParam("validate", true);
			facesContext.addMessage("", new FacesMessage(
					FacesMessage.SEVERITY_INFO, selectedCar.getPlateNumber()
							+ "更新成功", ""));

		}
		selectCarRecords();

	}

	/**
	 * 修改触发事件
	 */
	public void showAddForm() {
		operationId = 1;
		title = "新增车辆";
		clearAddForm();
	}

	/**
	 * 修改触发事件
	 */
	public void showEditForm() {
		operationId = 2;
		title = "编辑车辆";
		remainFuel = selectedCar.getRemainFuel().toString();
		holdPeoples = selectedCar.getHoldPeoples().toString();
		candriveKilometers = selectedCar.getCandriveKilometers().toString();
		currentKilometers = selectedCar.getCurrentKilometers().toString();
	}

	/**
	 * 清空新增对话框
	 */
	public void clearAddForm() {
		selectedCar = new PensionVehicle();
		holdPeoples = null;
		candriveKilometers = null;
		currentKilometers = null;
		remainFuel = null;
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

	public void setCarManageService(CarManageService carManageService) {
		this.carManageService = carManageService;
	}

	public CarManageService getCarManageService() {
		return carManageService;
	}

	public void setCarSQL(String carSQL) {
		this.carSQL = carSQL;
	}

	public String getCarSQL() {
		return carSQL;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setAddCarRow(PensionVehicle addCarRow) {
		this.addCarRow = addCarRow;
	}

	public PensionVehicle getAddCarRow() {
		return addCarRow;
	}

	public void setSelectedCar(PensionVehicle selectedCar) {
		this.selectedCar = selectedCar;
	}

	public PensionVehicle getSelectedCar() {
		return selectedCar;
	}

	public void setCars(List<PensionVehicle> cars) {
		this.cars = cars;
	}

	public List<PensionVehicle> getCars() {
		return cars;
	}

	public void setEmpSQL(String empSQL) {
		this.empSQL = empSQL;
	}

	public String getEmpSQL() {
		return empSQL;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public String getHoldPeoples() {
		return holdPeoples;
	}

	public void setHoldPeoples(String holdPeoples) {
		this.holdPeoples = holdPeoples;
	}

	public String getRemainFuel() {
		return remainFuel;
	}

	public void setRemainFuel(String remainFuel) {
		this.remainFuel = remainFuel;
	}

	public String getCurrentKilometers() {
		return currentKilometers;
	}

	public void setCurrentKilometers(String currentKilometers) {
		this.currentKilometers = currentKilometers;
	}

	public String getCandriveKilometers() {
		return candriveKilometers;
	}

	public void setCandriveKilometers(String candriveKilometers) {
		this.candriveKilometers = candriveKilometers;
	}

	public Short getOperationId() {
		return operationId;
	}

	public void setOperationId(Short operationId) {
		this.operationId = operationId;
	}
}
