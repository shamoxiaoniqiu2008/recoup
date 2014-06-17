package service.configureManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.configureManage.PensionFloor;
import domain.configureManage.PensionFloorExample;
import domain.configureManage.PensionRoom;
import domain.configureManage.PensionRoomExample;

import persistence.configureManage.PensionFloorMapper;


@Service
public class FloorManageService {
	@Autowired
	private PensionFloorMapper pensionFloorMapper;
	@Autowired
	private RoomManageService roomManageService;
	/**
	 * 查询楼层信息
	 * @param startDate
	 * @param endDate
	 * @param olderId
	 * @return
	 */
	public List<PensionFloor> selectFloorRecords(String buildName,String floorName) {
		// TODO Auto-generated method stub
		PensionFloorExample example = new PensionFloorExample();
		example.or().andClearedEqualTo(2).andBuildnameLike("%"+buildName+"%").andNameLike("%"+floorName+"%");
		return pensionFloorMapper.selectByExample(example);
	}
	public List<PensionFloor> selectFloorRecords(Long buildId) {
		// TODO Auto-generated method stub
		PensionFloorExample example = new PensionFloorExample();
		example.or().andBuildIdEqualTo(buildId);
		return pensionFloorMapper.selectByExample(example);
	}
	/**
	 * 删除楼层信息
	 * @param selectedFloorRow
	 */
	public void deleteFloorRecord(PensionFloor selectedFloorRow) {

		pensionFloorMapper.deleteByPrimaryKey(selectedFloorRow.getId());
	}
	
	/**
	 * 删除属于楼层的房间
	 * @param selectedRoomRow
	 */
	public void deleteFloorsByBuild(Long buildId)
	{
		PensionFloorExample example = new PensionFloorExample();
		example.or().andBuildIdEqualTo(buildId);
		List<PensionFloor> pensionList = pensionFloorMapper.selectByExample(example);
		for(int i=0;i<pensionList.size();i++)
		{
			PensionFloor floor = pensionList.get(i);
			floor.setCleared(1);
			updateFloorRecord(floor);
		}
	}
	/**
	 * 录入楼层信息
	 * @param addedFloorRow
	 */
	public void insertFloorRecord(PensionFloor addedFloorRow) {
		/*
		 * 貌似还缺点儿东西
		 */
		pensionFloorMapper.insertSelective(addedFloorRow);
	}
	/**
	 * 修改楼层信息
	 * @param updatedFloorRow
	 */
	public void updateFloorRecord(PensionFloor updatedFloorRow) {
		
		pensionFloorMapper.updateByPrimaryKeySelective(updatedFloorRow);
		List<PensionRoom> rooms = roomManageService.selectRoomRecords(updatedFloorRow.getId());
		for(int i=0;i<rooms.size();i++)
		{
			rooms.get(i).setBuildname(updatedFloorRow.getBuildname());
			rooms.get(i).setFloorname(updatedFloorRow.getName());
			
			roomManageService.updateRoomRecord(rooms.get(i));
		}
	}
	
	public PensionFloor selectByPrimaryKey(Long id) {
		
		return pensionFloorMapper.selectByPrimaryKey(id);
	}
	
	public void setpensionFloorMapper(PensionFloorMapper pensionFloorMapper) {
		this.pensionFloorMapper = pensionFloorMapper;
	}

	public PensionFloorMapper getpensionFloorMapper() {
		return pensionFloorMapper;
	}
	public List<PensionFloor> selectFloorRecords() {
		// TODO Auto-generated method stub
		PensionFloorExample example = new PensionFloorExample();
		example.or().andClearedEqualTo(2);
		return pensionFloorMapper.selectByExample(example);
	}
	public RoomManageService getRoomManageService() {
		return roomManageService;
	}
	public void setRoomManageService(RoomManageService roomManageService) {
		this.roomManageService = roomManageService;
	}
}
