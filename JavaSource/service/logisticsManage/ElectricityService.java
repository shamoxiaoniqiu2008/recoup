package service.logisticsManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.configureManage.PensionItempurseMapper;
import persistence.logisticsManage.PensionAmmeterMapper;
import persistence.logisticsManage.PensionElectricityRecordMapper;
import persistence.logisticsManage.PensionOlderElectricityMapper;
import util.CustomException;
import util.PmsException;
import util.SystemConfig;
import domain.configureManage.PensionItempurse;
import domain.logisticsManage.PensionAmmeter;
import domain.logisticsManage.PensionElectricityRecord;
import domain.logisticsManage.PensionElectricityRecordExample;
import domain.logisticsManage.PensionOlderElectricity;

@Service
public class ElectricityService {
	@Autowired
	private PensionElectricityRecordMapper pensionElectricityRecordMapper;
	@Autowired
	private PensionOlderElectricityMapper pensionOlderElectricityMapper;
	@Autowired
	private PensionAmmeterMapper pensionAmmeterMapper;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionItempurseMapper pensionItempurseMapper;

	/**
	 * 
	 * @Title: selectElectricityRecordRecords
	 * @Description: TODO
	 * @param @param buildId
	 * @param @param floorId
	 * @param @param roomId
	 * @param @param ammeterId
	 * @param @return
	 * @return List<PensionElectricityRecord>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-9 下午3:12:46
	 * @version V1.0
	 * modify by mary.liu
	 * @param paidFlag 是否计费标识
	 * @param inputEndTime 电表录入起始时间
	 * @param inputStartTime 电表录入结束时间
	 */
	public List<PensionElectricityRecord> selectElectricityRecordRecords(
			Date inputStartTime, Date inputEndTime, Integer paidFlag, Long buildId, Long floorId, Long roomId, Long ammeterId) {
		return pensionElectricityRecordMapper.selectElectricityRecordRecords(
				inputStartTime,inputEndTime,paidFlag,
				buildId, floorId, roomId, ammeterId);
	}

	/**
	 * 
	 * @Title: insertPensionElectricityRecord
	 * @Description: 插入新增用电记录数据
	 * @param @param pere
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-6 下午3:53:30
	 * @version V1.0
	 */
	@Transactional(rollbackFor = Exception.class)
	public void insertPensionElectricityRecord(PensionElectricityRecord pere)
			throws CustomException, Exception {
		PensionElectricityRecordExample example2 = new PensionElectricityRecordExample();
		example2.or().andClearedEqualTo(2)
				.andAmmeterIdEqualTo(pere.getAmmeterId());
		example2.setOrderByClause("id desc");
		List<PensionElectricityRecord> listForAdd = pensionElectricityRecordMapper
				.selectByExample(example2);
		if (listForAdd.size() > 0) {
			PensionElectricityRecord per2 = listForAdd.get(0);
			PensionElectricityRecord per = new PensionElectricityRecord();
			per.setAmmeterId(pere.getAmmeterId());
			per.setInputuserId(pere.getInputuserId());
			per.setDegreesNumber(pere.getDegreesNumber());
			per.setInputTime(pere.getInputTime());
			per.setNote(pere.getNote());
			per.setLastDegreesNumber(per2.getDegreesNumber());
			per.setLastInputTime(per2.getInputTime());
			pensionElectricityRecordMapper.insertSelective(per);
		} else {
			PensionAmmeter pa = pensionAmmeterMapper.selectByPrimaryKey(pere
					.getAmmeterId());
			if (null == pa) {
				throw new CustomException("改电表不存在，请先设置电表！");
			}
			if (null == pa.getInitDatetime()
					|| null == pa.getInitDegreeNumber()) {
				throw new CustomException("请先维护电表的初始读数或者初始日期信息！");
			} else {
				PensionElectricityRecord per = new PensionElectricityRecord();
				per.setAmmeterId(pere.getAmmeterId());
				per.setInputuserId(pere.getInputuserId());
				per.setDegreesNumber(pere.getDegreesNumber());
				per.setInputTime(pere.getInputTime());
				per.setLastInputTime(pa.getInitDatetime());
				per.setNote(pere.getNote());
				per.setLastDegreesNumber(pa.getInitDegreeNumber());
				pensionElectricityRecordMapper.insertSelective(per);
			}

		}

	}

	/**
	 * 
	 * @Title: updatePensionElectricityRecord
	 * @Description: TODO
	 * @param @param selectedRow
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-9 下午2:41:08
	 * @version V1.0
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updatePensionElectricityRecord(
			PensionElectricityRecord selectedRow) throws Exception {
		PensionElectricityRecord perForUpdate = new PensionElectricityRecord();
		perForUpdate.setId(selectedRow.getId());
		perForUpdate.setAmmeterId(selectedRow.getAmmeterId());
		perForUpdate.setRoomId(selectedRow.getRoomId());
		perForUpdate.setInputuserId(selectedRow.getInputuserId());
		perForUpdate.setDegreesNumber(selectedRow.getDegreesNumber());
		perForUpdate.setNote(selectedRow.getNote());
		pensionElectricityRecordMapper
				.updateByPrimaryKeySelective(perForUpdate);
	}

	/**
	 * 
	 * @Title: selectPensionOlderElectricityList
	 * @Description: TODO
	 * @param @param sRow
	 * @param @return
	 * @return List<PensionOlderElectricity>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-10 上午9:43:30
	 * @version V1.0
	 */
	public List<PensionOlderElectricity> selectPensionOlderElectricityList(
			PensionElectricityRecord sRow) {
		List<PensionOlderElectricity> tempPensionOlderElectricityList = null;
		if (sRow.getEnsureFlag() == 1) {
			tempPensionOlderElectricityList = pensionOlderElectricityMapper
					.selectPensionOlderElectricityListByEnsured(
							sRow.getRoomId(), sRow.getLastInputTime(),
							sRow.getInputTime());
		} else {
			Long numbers = pensionOlderElectricityMapper.selectOlderNumbers(
					sRow.getRoomId(), sRow.getLastInputTime(),
					sRow.getInputTime());
			if (numbers > 0) {
				tempPensionOlderElectricityList = pensionOlderElectricityMapper
						.selectPensionOlderElectricityList(sRow.getRoomId(),
								sRow.getLastInputTime(), sRow.getInputTime(),
								sRow.getInputTime());
			} else {
				tempPensionOlderElectricityList = new ArrayList<PensionOlderElectricity>();
			}
		}

		return tempPensionOlderElectricityList;
	}

	/**
	 * 
	 * @Title: insertpensionOlderElectricityList
	 * @Description: TODO
	 * @param @param pensionOlderElectricityList
	 * @param @throws Exception
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-10 上午9:43:24
	 * @version V1.0
	 */
	public void insertpensionOlderElectricityList(
			List<PensionOlderElectricity> pensionOlderElectricityList)
			throws Exception {
		if (pensionOlderElectricityList.size() > 0) {
			for (PensionOlderElectricity p : pensionOlderElectricityList) {
				PensionOlderElectricity poe = new PensionOlderElectricity();
				poe.setOlderId(p.getOlderId());
				poe.setOlderName(p.getOlderName());
				poe.setRoomId(p.getRoomId());
				poe.setRoomName(p.getRoomName());
				poe.setFloorId(p.getFloorId());
				poe.setFloorName(p.getFloorName());
				poe.setBuildId(p.getBuildId());
				poe.setBuildName(p.getBuildName());
				poe.setElectricityAmount(p.getEveryDegrees());
				poe.setStartTime(p.getStartTime());
				poe.setEndTime(p.getEndTime());
				poe.setUnitPrice(p.getUnitPrice());
				poe.setInputuserId(p.getInputuserId());
				poe.setInputuserName(p.getInputuserName());
				poe.setElectricityRecordId(p.getElectricityRecordId());
				pensionOlderElectricityMapper.insertSelective(poe);
			}
			Long electricityRecordId = pensionOlderElectricityList.get(0)
					.getElectricityRecordId();
			PensionElectricityRecord pensionElectricityRecordForUpdate = new PensionElectricityRecord();
			pensionElectricityRecordForUpdate.setId(electricityRecordId);
			pensionElectricityRecordForUpdate.setEnsureFlag(1);
			pensionElectricityRecordMapper
					.updateByPrimaryKeySelective(pensionElectricityRecordForUpdate);
		}
	}

	/**
	 * 
	 * @Title: getNearistRecord
	 * @Description: TODO
	 * @param @param ametterId
	 * @param @return
	 * @return PensionElectricityRecord
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-13 上午11:37:18
	 * @version V1.0
	 */
	public PensionElectricityRecord getNearistRecord(Long ametterId) {
		PensionElectricityRecordExample example = new PensionElectricityRecordExample();
		example.or().andAmmeterIdEqualTo(ametterId).andClearedEqualTo(2);
		example.setOrderByClause("input_time desc");
		PensionElectricityRecord per = new PensionElectricityRecord();
		List<PensionElectricityRecord> pensionElectricityRecordList = pensionElectricityRecordMapper
				.selectByExample(example);
		if (pensionElectricityRecordList.size() > 0) {
			per = pensionElectricityRecordList.get(0);
		} else {
			per = null;
		}
		return per;
	}

	/**
	 * 
	 * @Title: getPensionAmmeter
	 * @Description: TODO
	 * @param @param per
	 * @param @return
	 * @return PensionAmmeter
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-13 上午11:42:14
	 * @version V1.0
	 */
	public PensionAmmeter getPensionAmmeter(PensionElectricityRecord per)
			throws CustomException, Exception {
		PensionAmmeter pa = pensionAmmeterMapper.selectByPrimaryKey(per
				.getAmmeterId());
		if (null == pa) {
			throw new CustomException("未维护电表信息！");
		} else {

		}
		return pa;
	}

	/**
	 * 根据系统参数配置的电表主键 获取价表中的电费价格
	 * 
	 * @return
	 * @throws PmsException
	 * @author centling mary.liu 2013-12-16
	 */
	public Float selectElectricityItemPurse() throws PmsException {
		try {
			String valueStr;
			valueStr = systemConfig.selectProperty("ELECTRICITY_PURSE");
			Long f = Long.valueOf(valueStr);
			PensionItempurse itemPurse = pensionItempurseMapper
					.selectByPrimaryKey(f);
			if (itemPurse != null) {
				return itemPurse.getPurse();
			} else {
				throw new PmsException("没有设置电费价表");
			}
		} catch (PmsException e) {
			throw new PmsException("没有设置电费价表系统参数");
		}
	}

}
