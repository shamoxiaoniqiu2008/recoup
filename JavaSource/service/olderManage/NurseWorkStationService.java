package service.olderManage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.configureManage.PensionBedMapper;
import persistence.configureManage.PensionBuildingMapper;
import persistence.configureManage.PensionFloorMapper;
import persistence.configureManage.PensionRoomMapper;
import persistence.dictionary.PensionDicNationMapper;
import persistence.dictionary.PensionDicPoliticsMapper;
import persistence.financeManage.PensionNormalpaymentMapper;
import persistence.financeManage.PensionTemppaymentMapper;
import persistence.olderManage.PensionLivingrecordMapper;
import persistence.olderManage.PensionOlderMapper;
import service.financeManage.NormalPayDomain;
import service.financeManage.TempPayDomain;
import service.receptionManage.PensionOlderDomain;
import util.DateUtil;
import domain.configureManage.PensionBed;
import domain.configureManage.PensionBedExample;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionBuildingExample;
import domain.configureManage.PensionFloor;
import domain.configureManage.PensionFloorExample;
import domain.configureManage.PensionRoom;
import domain.configureManage.PensionRoomExample;
import domain.dictionary.PensionDicNation;
import domain.dictionary.PensionDicPolitics;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class NurseWorkStationService {
	@Autowired
	private PensionBedMapper bedMapper;
	@Autowired
	private PensionRoomMapper roomMapper;
	@Autowired
	private PensionBuildingMapper buildMapper;
	@Autowired
	private PensionFloorMapper floorMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private PensionOlderMapper pensitonOlderMapper;
	@Autowired
	private PensionDicNationMapper nationMapper;
	@Autowired
	private PensionDicPoliticsMapper politicMapper;
	@Autowired
	private PensionNormalpaymentMapper pensionNormalpaymentMapper;
	@Autowired
	private PensionTemppaymentMapper pensionTemppaymentMapper;
	@Autowired
	private PensionLivingrecordMapper pensionLivingrecordMapperr;

	public String testFun() {
		return "test-Return";
	}

	/**
	 * 查询所有未删除的大厦记录 add by mary 2013-12-10
	 */
	public List<PensionBuilding> selectBuildings() {
		PensionBuildingExample example = new PensionBuildingExample();
		example.or().andClearedEqualTo(2);
		example.setOrderByClause("sortid asc");
		return buildMapper.selectByExample(example);
	}

	/**
	 * 查询所有未删除的楼层记录 add by wensy 2013-01-21
	 */
	public List<PensionFloor> selectFloors(Long buildId) {
		PensionFloorExample example = new PensionFloorExample();
		example.or().andClearedEqualTo(2).andBuildIdEqualTo(buildId);
		example.setOrderByClause("sortid desc");
		return floorMapper.selectByExample(example);
	}

	/**
	 * 查询所有未删除的房间记录 add by wensy 2013-01-21
	 */
	public List<PensionRoom> selectRooms(Long floorId, String way) {
		PensionRoomExample example = new PensionRoomExample();
		example.or().andClearedEqualTo(2).andFloorIdEqualTo(floorId)
				.andWayEqualTo(way);
		return roomMapper.selectByExample(example);
	}

	/**
	 * 查询所有未删除的床位记录 add by wensy 2014-01-21
	 */
	public List<PensionBed> selectBedList(Long roomId) {
		List<PensionBed> bedList = new ArrayList<PensionBed>();
		PensionBedExample example = new PensionBedExample();
		example.or().andRoomIdEqualTo(roomId).andClearedEqualTo(2);
		bedList = bedMapper.selectByExample(example);
		for (PensionBed bedTemp : bedList) {
			List<PensionOlderDomain> olderList = new ArrayList<PensionOlderDomain>();
			olderList = selectOlderList(bedTemp.getId());
			bedTemp.setOlderList(olderList);
		}
		return bedList;
	}

	public List<PensionBuilding> selectBuildList() {
		List<PensionBuilding> buildList = new ArrayList<PensionBuilding>();
		buildList = selectBuildings();
		for (PensionBuilding build : buildList) {
			List<PensionFloor> floorList = new ArrayList<PensionFloor>();
			floorList = selectFloors(build.getId());
			for (PensionFloor floor : floorList) {
				List<PensionRoom> southRoomList = new ArrayList<PensionRoom>();
				southRoomList = selectRooms(floor.getId(), "南");
				for (PensionRoom room : southRoomList) {
					List<PensionOlderDomain> olderList = new ArrayList<PensionOlderDomain>();
					olderList = selectOlderListByRoomId(room.getId());
					room.setOlderList(olderList);
				}
				floor.setSouthRoomList(southRoomList);

				List<PensionRoom> NorthRoomList = new ArrayList<PensionRoom>();
				NorthRoomList = selectRooms(floor.getId(), "北");
				for (PensionRoom room : NorthRoomList) {
					List<PensionOlderDomain> olderList = new ArrayList<PensionOlderDomain>();
					olderList = selectOlderListByRoomId(room.getId());
					room.setOlderList(olderList);
				}
				floor.setNorthRoomList(NorthRoomList);
			}
			build.setFloorList(floorList);
		}
		return buildList;
	}

	/**
	 * 根据床位ID查询老人列表
	 */
	public List<PensionOlderDomain> selectOlderList(Long bedId) {
		ArrayList<PensionOlderDomain> olderList = new ArrayList<PensionOlderDomain>();
		olderList = pensionOlderMapper.selectOlderList(bedId);
		for (PensionOlderDomain olderTemp : olderList) {
			String formatDate = DateUtil.formatDate(olderTemp.getVisitTime());
			olderTemp.setInDate(formatDate);
		}
		return olderList;
	}

	/**
	 * 根据房间ID查询老人列表
	 */
	public List<PensionOlderDomain> selectOlderListByRoomId(Long roomId) {
		ArrayList<PensionOlderDomain> olderList = new ArrayList<PensionOlderDomain>();
		olderList = pensionOlderMapper.selectOlderByRoomId(roomId);
		for (PensionOlderDomain olderTemp : olderList) {
			String formatDate = DateUtil.formatDate(olderTemp.getVisitTime());
			olderTemp.setInDate(formatDate);
		}
		return olderList;
	}

	/**
	 * 查询老人的基本信息
	 * 
	 * @return
	 */
	public List<PensionOlderDomain> selectPensionInfomation(Long olderId) {
		List<PensionOlderDomain> olderRecordList = new ArrayList<PensionOlderDomain>();
		olderRecordList = pensitonOlderMapper.selectPensionInfomation(olderId);
		for (PensionOlderDomain temp : olderRecordList) {
			convertLivingRecords(temp);
		}
		return olderRecordList;
	}

	/**
	 * 老人入住记录字段转换
	 * 
	 * @param temp
	 */
	public void convertLivingRecords(PensionOlderDomain temp) {
		PensionDicNation nation = nationMapper.selectByPrimaryKey(temp
				.getNationId());
		if (nation != null) {
			temp.setNationName(nation.getType());
		} else {
			temp.setNationName("");
		}
		PensionDicPolitics politic = politicMapper.selectByPrimaryKey(temp
				.getPoliticsId());
		if (politic != null) {
			temp.setPoliticName(politic.getType());
		} else {
			temp.setPoliticName("");
		}
		// 老人性别转换
		if (temp.getSex() == 1) {
			temp.setOldSex("男");
		} else {
			temp.setOldSex("女");
		}
		// 老人类型转换
		if (temp.getTypes() == 1) {
			temp.setOldType("自理");
		} else if (temp.getTypes() == 2) {
			temp.setOldType("半自理");
		} else {
			temp.setOldType("不能自理");
		}
		// 老人状态转换
		switch (temp.getStatuses()) {
		case 1:
			temp.setOlderState("待评估");
			break;
		case 2:
			temp.setOlderState("已评估");
			break;
		case 3:
			temp.setOlderState("入住");
			break;
		case 4:
			temp.setOlderState("请假");
			break;
		default:
			temp.setOlderState("退住");
			break;
		}
		// 申请人性别转换
		if (temp.getApplySex() != null) {
			if (temp.getApplySex() != 1) {
				temp.setApplySexStr("男");
			} else {
				temp.setApplySexStr("女");
			}
		} else {
			temp.setApplySexStr("");
		}

	}

	/**
	 * 查询日常收费信息
	 * 
	 * @param olderId
	 * @return
	 */
	public List<NormalPayDomain> selectNormalPayRecords(Long olderId) {
		List<NormalPayDomain> records = new ArrayList<NormalPayDomain>();
		records = pensionNormalpaymentMapper.selectNomalRecords(olderId);
		return records;
	}

	/**
	 * 查询临时收费信息
	 * 
	 * @param olderId
	 * @return
	 */
	public List<TempPayDomain> selectTempPayRecords(Long olderId) {
		List<TempPayDomain> records = new ArrayList<TempPayDomain>();
		records = pensionTemppaymentMapper.selectTempPayRecords(olderId);
		return records;
	}

	/**
	 * 查询老人家属列表
	 * 
	 * @return
	 */
	public List<PensionFamilyDomain> selectFamilyRecord(Long olderId) {
		List<PensionFamilyDomain> familyRecords = pensionLivingrecordMapperr
				.selectFamily(olderId);
		return familyRecords;
	}
}
