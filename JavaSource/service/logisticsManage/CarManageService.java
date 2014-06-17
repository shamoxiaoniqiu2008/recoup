package service.logisticsManage;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.logisticsManage.PensionVehicle;
import domain.logisticsManage.PensionVehicleExample;


import persistence.logisticsManage.PensionVehicleMapper;
import service.system.MessageMessage;



/** 
 * @ClassName: CarManageService 
 * @Description: TODO
 * @author bill
 * @date 2013-11-10 上午11:07:21
 * @version V1.0 
 */

@Service
public class CarManageService {
	@Autowired
	private PensionVehicleMapper pensionVehicleMapper;
	@Autowired
	private MessageMessage messageMessage;
	
	/**
	 * 查询车辆列表
	 * @return 车辆列表
	 */
	public List<PensionVehicle> selectCarRecords(String carNumber) {
		return pensionVehicleMapper.selectByCarNumber(carNumber);
		
	}
	/**
	 * 删除车辆信息
	 * @param selectedCarRow
	 */
	public void deleteBuildRecord(PensionVehicle selectedCarRow) {

		pensionVehicleMapper.deleteByPrimaryKey(selectedCarRow.getId());
	}
	/**
	 * 录入车辆信息
	 * @param addedBuildRow
	 */
	public void insertBuildRecord(PensionVehicle addedCarRow) {
		/*
		 * 貌似还缺点儿东西
		 */
		pensionVehicleMapper.insertSelective(addedCarRow);
	}
	/**
	 * 修改车辆信息
	 * @param updatedBuildRow
	 */
	public void updateBuildRecord(PensionVehicle updatedBuildRow) {
		
		pensionVehicleMapper.updateByPrimaryKeySelective(updatedBuildRow);
		
	}
	public void setPensionVehicleMapper(PensionVehicleMapper pensionVehicleMapper) {
		this.pensionVehicleMapper = pensionVehicleMapper;
	}
	public PensionVehicleMapper getPensionVehicleMapper() {
		return pensionVehicleMapper;
	}
	public void setMessageMessage(MessageMessage messageMessage) {
		this.messageMessage = messageMessage;
	}
	public MessageMessage getMessageMessage() {
		return messageMessage;
	}

}
