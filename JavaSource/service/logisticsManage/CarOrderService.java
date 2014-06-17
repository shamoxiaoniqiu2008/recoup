package service.logisticsManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.employeeManage.PensionEmployeeMapper;
import persistence.logisticsManage.PensionVehicleMapper;
import persistence.logisticsManage.PensionVehicleOrderMapper;
import service.system.MessageMessage;
import util.PmsException;
import util.SystemConfig;
import domain.employeeManage.PensionEmployee;
import domain.logisticsManage.PensionVehicle;
import domain.logisticsManage.PensionVehicleOrder;
import domain.logisticsManage.PensionVehicleOrderExample;

/**
 * @ClassName: CarOrderService
 * @Description: TODO
 * @author bill
 * @date 2013-11-10 上午11:07:21
 * @version V1.0
 */

@Service
public class CarOrderService {

	@Autowired
	private PensionVehicleOrderMapper vehicleOrderMapper;
	@Autowired
	private PensionVehicleMapper pensionVehicleMapper;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 查询车辆列表
	 * 
	 * @param orderId
	 * @return 车辆列表
	 */
	public List<PensionVehicleOrder> selectCarOrderByMultiRecords(Long id,
			Long olderId, Integer state, Integer register, Long orderId,
			Date starDate, Date endDate) {
		PensionVehicleOrderExample example = new PensionVehicleOrderExample();
		if (orderId == null) {
			if (starDate != null && endDate != null) {
				endDate.setDate(endDate.getDate() + 1);
				example.or().andVehicleIdEqualTo(id).andClearedEqualTo(2)
						.andVehicleStateEqualTo(state)
						.andOlderIdEqualTo(olderId)
						.andRegisterFlagEqualTo(register)
						.andActualGotimeGreaterThanOrEqualTo(starDate)
						.andActualGotimeLessThan(endDate);
			} else if (starDate != null && endDate == null) {
				example.or().andVehicleIdEqualTo(id).andClearedEqualTo(2)
						.andVehicleStateEqualTo(state)
						.andOlderIdEqualTo(olderId)
						.andRegisterFlagEqualTo(register)
						.andActualGotimeGreaterThanOrEqualTo(starDate);
			} else if (starDate == null && endDate != null) {
				endDate.setDate(endDate.getDate() + 1);
				example.or().andVehicleIdEqualTo(id).andClearedEqualTo(2)
						.andVehicleStateEqualTo(state)
						.andOlderIdEqualTo(olderId)
						.andRegisterFlagEqualTo(register)
						.andActualGotimeLessThan(endDate);
			} else {
				example.or().andVehicleIdEqualTo(id).andClearedEqualTo(2)
						.andVehicleStateEqualTo(state)
						.andOlderIdEqualTo(olderId)
						.andRegisterFlagEqualTo(register);
			}
			return vehicleOrderMapper.selectByExample(example);
		} else {
			PensionVehicleOrder tempCar = vehicleOrderMapper
					.selectByPrimaryKey(orderId);
			List<PensionVehicleOrder> tempList = new ArrayList<PensionVehicleOrder>();
			tempList.add(tempCar);
			return tempList;
		}

	}

	/**
	 * 查询车辆列表
	 * 
	 * @return 车辆列表
	 */
	public List<PensionVehicleOrder> selectCarOrderRecords(Long id,
			Long olderId, Integer state, Integer register) {
		PensionVehicleOrderExample example = new PensionVehicleOrderExample();
		example.or().andVehicleIdEqualTo(id).andClearedEqualTo(2)
				.andVehicleStateLessThan(state).andOlderIdEqualTo(olderId)
				.andRegisterFlagLessThan(register);
		return vehicleOrderMapper.selectByExample(example);

	}

	/**
	 * 查询单次预约信息
	 * 
	 * @return 单条信息
	 */
	public PensionVehicleOrder selectCarOrderById(Long id) {
		return vehicleOrderMapper.selectByPrimaryKey(id);

	}

	/**
	 * 删除车辆预约信息
	 * 
	 * @param selectedCarRow
	 */
	public void deleteOrderRecord(PensionVehicleOrder vehicleOrder) {
		vehicleOrderMapper.deleteByPrimaryKey(vehicleOrder.getId());
	}

	/**
	 * 录入车辆预约信息
	 * 
	 * @param addedBuildRow
	 */
	public void insertOrderRecord(PensionVehicleOrder addedCarOrderRow) {
		/*
		 * 貌似还缺点儿东西
		 */
		vehicleOrderMapper.insertSelective(addedCarOrderRow);
	}

	/**
	 * 修改车辆预约信息
	 * 
	 * @param updatedBuildRow
	 */
	public void updateOrderRecord(PensionVehicleOrder record) {

		vehicleOrderMapper.updateByPrimaryKey(record);
	}

	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}

	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

	public void setVehicleOrderMapper(
			PensionVehicleOrderMapper vehicleOrderMapper) {
		this.vehicleOrderMapper = vehicleOrderMapper;
	}

	public PensionVehicleOrderMapper getVehicleOrderMapper() {
		return vehicleOrderMapper;
	}

	/**
	 * 根据车辆信息表主键查询信息
	 * 
	 * @author centling Mary 2013-11-29
	 * @param vehicleId
	 * @return
	 */
	public PensionVehicle selectVehicle(Long vehicleId) {
		return pensionVehicleMapper.selectByPrimaryKey(vehicleId);
	}

	/**
	 * 出车检查确认
	 * 
	 * @author centling Mary 2013-11-29
	 * @param id
	 * @param actualGotime
	 * @param carInfo
	 * @param expectDistance
	 *            预计里程 null:没有修改预计里程；否则，修改预计里程 add by mary.liu 2014-02-24
	 * @return
	 */
	public void ensureDrawOut(Long id, Date actualGotime,
			PensionVehicle carInfo, Float expectDistance) {
		// 将实际出车时间更新至 pension_vehicleorder
		PensionVehicleOrder vehicleOrder = new PensionVehicleOrder();
		vehicleOrder.setId(id);
		vehicleOrder.setActualGotime(actualGotime);// 实际出车时间
		vehicleOrder.setRegisterFlag(1);// 是否出车登记 -- 是
		vehicleOrder.setVehicleState(2);// 车辆状态 -- 出车中
		if (expectDistance != null) { // add by mary.liu 2014-02-24
			vehicleOrder.setExpectDistance(expectDistance);
		}
		vehicleOrderMapper.updateByPrimaryKeySelective(vehicleOrder);
		// 将当前公里数，剩余油量，可用公里数 更新至pension_vehicle
		PensionVehicle tempCar = new PensionVehicle();
		tempCar.setId(carInfo.getId());
		tempCar.setCandriveKilometers(carInfo.getCandriveKilometers());
		tempCar.setRemainFuel(carInfo.getRemainFuel());
		tempCar.setCurrentKilometers(carInfo.getCurrentKilometers());
		pensionVehicleMapper.updateByPrimaryKeySelective(tempCar);
	}

	/**
	 * 根据联系人编号 查找联系人姓名
	 * 
	 * @author centling Mary 2013-11-29
	 * @param contacterId
	 * @return
	 */
	public String selectContacter(Long contacterId) {
		PensionEmployee emp = pensionEmployeeMapper
				.selectByPrimaryKey(contacterId);
		;
		if (emp == null) {
			return "";
		} else {
			return emp.getName();
		}
	}

	/**
	 * 出车收费
	 * 
	 * @author centling Mary 2013-11-29
	 * @param vehicleId
	 *            出车编号
	 * @param orderId
	 *            预约记录编号
	 * @param actualBacktime
	 *            实际返回时间
	 * @param actualDistance
	 *            实际出车里程
	 * @param currentKilometers
	 *            当前公里数
	 * @param candriveKilometers
	 *            可行公里数
	 * @param remainFuel
	 *            剩余油量
	 */
	public void chargeFee(Long vehicleId, Long orderId, Date actualBacktime,
			Float actualDistance, Float currentKilometers,
			Float candriveKilometers, Float remainFuel) {
		// 更新车辆信息表
		PensionVehicle vehicle = new PensionVehicle();
		vehicle.setId(vehicleId);
		vehicle.setRemainFuel(remainFuel);
		vehicle.setCandriveKilometers(candriveKilometers);
		vehicle.setCurrentKilometers(currentKilometers);
		pensionVehicleMapper.updateByPrimaryKeySelective(vehicle);
		// 更新车辆预约记录表
		PensionVehicleOrder order = new PensionVehicleOrder();
		order.setId(orderId);
		order.setActualBacktime(actualBacktime);
		order.setActualDistance(actualDistance);
		order.setVehicleState(3);// 已返回
		vehicleOrderMapper.updateByPrimaryKeySelective(order);

	}

	/**
	 * 【费用录入】，将收费标识 置为‘是’
	 * 
	 * @param empName
	 * @param empId
	 */
	public void charge(Long id, Long empId, String empName) {
		PensionVehicleOrder vehicleOrder = new PensionVehicleOrder();
		vehicleOrder.setId(id);
		vehicleOrder.setChargeFlag(1);
		vehicleOrder.setEnsureEmpId(empId);
		vehicleOrder.setEnsureEmpName(empName);
		vehicleOrder.setEnsureDate(new Date());
		vehicleOrderMapper.updateByPrimaryKeySelective(vehicleOrder);

	}

	/**
	 * 获得价表类型
	 * 
	 * @return
	 * @throws PmsException
	 */
	public String getPriceTypeId() throws PmsException {
		try {
			String carStr = systemConfig
					.selectProperty("CAR_ITEM_PURSE_TYPE_ID");
			if (carStr != null && !carStr.isEmpty()) {
				return carStr;
			}
		} catch (NumberFormatException e) {
			throw new PmsException("系统参数设置用车价表类型有误！");
		} catch (PmsException e) {
			throw new PmsException("系统参数还没有设置用车价表类型！");
		}
		return null;

	}

}
