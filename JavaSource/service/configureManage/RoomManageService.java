package service.configureManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.configureManage.PensionBed;
import domain.configureManage.PensionBedExample;
import domain.configureManage.PensionRoom;
import domain.configureManage.PensionRoomExample;

import persistence.configureManage.PensionRoomMapper;


@Service
public class RoomManageService {
	@Autowired
	private PensionRoomMapper pensionRoomMapper;
	@Autowired
	private BedManageService bedManageService;
	/**
	 * 查询房间信息
	 * @param startDate
	 * @param endDate
	 * @param olderId
	 * @return
	 */
	public List<PensionRoom> selectRoomRecords(String buildName,String floorName,String roomName) {
		// TODO Auto-generated method stub
		PensionRoomExample example = new PensionRoomExample();
		example.or().andClearedEqualTo(2).andBuildnameLike("%"+buildName+"%").andFloornameLike("%"+floorName+"%")
		.andNameLike("%"+roomName+"%");
		
		return pensionRoomMapper.selectByExample(example);
	}
	public List<PensionRoom> selectRoomRecords(Long floorId) {
		// TODO Auto-generated method stub
		PensionRoomExample example = new PensionRoomExample();
		example.or().andFloorIdEqualTo(floorId);
		
		return pensionRoomMapper.selectByExample(example);
	}
	/**
	 * 删除房间信息
	 * @param selectedRoomRow
	 */
	public void deleteRoomRecord(PensionRoom selectedRoomRow) {

		pensionRoomMapper.deleteByPrimaryKey(selectedRoomRow.getId());
	}
	/**
	 * 删除属于楼层的房间
	 * @param selectedRoomRow
	 */
	public void deleteRoomsByFloor(Long floorId)
	{
		PensionRoomExample example = new PensionRoomExample();
		example.or().andFloorIdEqualTo(floorId);
		List<PensionRoom> pensionList = pensionRoomMapper.selectByExample(example);
		for(int i=0;i<pensionList.size();i++)
		{
			PensionRoom room = pensionList.get(i);
			room.setCleared(1);
			updateRoomRecord(room);
		}
	}
	/**
	 * 录入房间信息
	 * @param addedRoomRow
	 */
	public void insertRoomRecord(PensionRoom addedRoomRow) {
		/*
		 * 貌似还缺点儿东西
		 */
		pensionRoomMapper.insertSelective(addedRoomRow);
	}
	/**
	 * 修改房间信息
	 * @param updatedRoomRow
	 */
	public void updateRoomRecord(PensionRoom updatedRoomRow) {
		
		pensionRoomMapper.updateByPrimaryKeySelective(updatedRoomRow);
		
		pensionRoomMapper.updateByPrimaryKeySelective(updatedRoomRow);
		List<PensionBed> beds = bedManageService.selectBedRecords(updatedRoomRow.getId());
		for(int i=0;i<beds.size();i++)
		{
			beds.get(i).setBuildname(updatedRoomRow.getBuildname());
			beds.get(i).setFloorname(updatedRoomRow.getFloorname());
			beds.get(i).setRoomname(updatedRoomRow.getName());
			bedManageService.updateBedRecord(beds.get(i));
		}
	}
	public PensionRoom selectByPrimaryKey(Long id) {
		
		return pensionRoomMapper.selectByPrimaryKey(id);
	}
	
	public void setpensionRoomMapper(PensionRoomMapper pensionRoomMapper) {
		this.pensionRoomMapper = pensionRoomMapper;
	}

	public PensionRoomMapper getpensionRoomMapper() {
		return pensionRoomMapper;
	}
	public List<PensionRoom> selectRoomRecords() {
		// TODO Auto-generated method stub
		PensionRoomExample example = new PensionRoomExample();
		example.or().andClearedEqualTo(2);
		return pensionRoomMapper.selectByExample(example);
	}
	public BedManageService getBedManageService() {
		return bedManageService;
	}
	public void setBedManageService(BedManageService bedManageService) {
		this.bedManageService = bedManageService;
	}
}
