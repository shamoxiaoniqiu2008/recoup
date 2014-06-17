package service.olderManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.configureManage.PensionBedMapper;
import persistence.configureManage.PensionBuildingMapper;
import persistence.configureManage.PensionFloorMapper;
import persistence.configureManage.PensionRoomMapper;
import persistence.olderManage.PensionDepartapproveMapper;
import persistence.olderManage.PensionDepartnotifyMapper;
import persistence.olderManage.PensionOlderMapper;
import domain.configureManage.PensionBed;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionFloor;
import domain.configureManage.PensionRoom;
import domain.olderManage.PensionDepartapprove;
import domain.olderManage.PensionDepartapproveExample;
import domain.olderManage.PensionDepartnotify;
import domain.olderManage.PensionOlder;
import domain.olderManage.PensionOlderExample;

/**
 * 老人管理
 * 
 * @author bill.zhang
 * 
 * 
 */
@Service
public class OlderService {
	
	@Autowired
	private PensionOlderMapper olderMapper;
	@Autowired
	private PensionBedMapper bedMapper;
	@Autowired
	private PensionRoomMapper roomMapper;
	@Autowired
	private PensionBuildingMapper buildMapper;
	@Autowired
	private PensionFloorMapper floorMapper;

	/**
	 * 更新老人状态
	 */
	public void updateOlderByKey(Long older_id,Long bed_id,int status)
	{
		PensionOlder older = selectOlderByKey(older_id);
		older.setStatuses(status);
		olderMapper.updateByPrimaryKeySelective(older);
		
		// 修改床位老人数目
		PensionBed bed = bedMapper.selectByPrimaryKey(bed_id);
		Integer olderNo = bed.getOldernumber();
		bed.setOldernumber(olderNo - 1);
		bedMapper.updateByPrimaryKeySelective(bed);
		// 修改房间老人数目
		PensionRoom room = roomMapper.selectByPrimaryKey(bed.getRoomId());
		Integer roomPlderNo = room.getOldernumber();
		room.setOldernumber(roomPlderNo - 1);
		roomMapper.updateByPrimaryKeySelective(room);
		// 修改楼层老人数目
		PensionFloor floor = floorMapper.selectByPrimaryKey(room.getFloorId());
		Integer floorOlderNo = floor.getOldernumber();
		floor.setOldernumber(floorOlderNo - 1);
		floorMapper.updateByPrimaryKeySelective(floor);
		// 修改大厦老人数目
		PensionBuilding build = buildMapper.selectByPrimaryKey(floor
				.getBuildId());
		Integer buildOlderNo = build.getOldernumber();
		build.setOldernumber(buildOlderNo - 1);
		buildMapper.updateByPrimaryKeySelective(build);
	}
	/**
	 * 查看老人记录
	 */
	public PensionOlder selectOlderByKey(Long older_id)
	{
		return olderMapper.selectByPrimaryKey(older_id);
	}
	/**
	 * 查看在住老人记录
	 */
	public List<PensionOlder> selectOldersByBed(String buildName,String floorName,String roomName,String bedName)
	{
		return olderMapper.selectOldersByBed(buildName,floorName,roomName,bedName);
	}
	

	

}
