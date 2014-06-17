package service.configureManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.configureManage.PensionBed;
import domain.configureManage.PensionBedExample;

import persistence.configureManage.PensionBedMapper;


@Service
public class BedManageService {
	@Autowired
	private PensionBedMapper pensionBedMapper;
	/**
	 * 查询床位信息
	 * @param startDate
	 * @param endDate
	 * @param olderId
	 * @return
	 */
	public List<PensionBed> selectBedRecords(String buildName,String floorName,String roomName,String bedName) {
		// TODO Auto-generated method stub
		PensionBedExample example = new PensionBedExample();
		example.or().andClearedEqualTo(2).andBuildnameLike("%"+buildName+"%").andFloornameLike("%"+floorName+"%")
		.andRoomnameLike("%"+roomName+"%").andNameLike("%"+bedName+"%");
		
		return pensionBedMapper.selectByExample(example);
	}
	public List<PensionBed> selectBedRecords(Long roomId) {
		// TODO Auto-generated method stub
		PensionBedExample example = new PensionBedExample();
		example.or().andRoomIdEqualTo(roomId);
		
		return pensionBedMapper.selectByExample(example);
	}
	/**
	 * 删除床位信息
	 * @param selectedBedRow
	 */
	public void deleteBedRecord(PensionBed selectedBedRow) {

		pensionBedMapper.deleteByPrimaryKey(selectedBedRow.getId());
	}
	/**
	 * 录入床位信息
	 * @param addedBedRow
	 */
	public void insertBedRecord(PensionBed addedBedRow) {
		/*
		 * 貌似还缺点儿东西
		 */
		pensionBedMapper.insertSelective(addedBedRow);
	}
	/**
	 * 修改床位信息
	 * @param updatedBedRow
	 */
	public void updateBedRecord(PensionBed updatedBedRow) {
		
		pensionBedMapper.updateByPrimaryKeySelective(updatedBedRow);
	}
	
	/**
	 * 删除属于房间的床位
	 * @param selectedRoomRow
	 */
	public void deleteBedsByRoom(Long roomId)
	{
		PensionBedExample example = new PensionBedExample();
		example.or().andRoomIdEqualTo(roomId);
		List<PensionBed> pensionList = pensionBedMapper.selectByExample(example);
		for(int i=0;i<pensionList.size();i++)
		{
			PensionBed bed = pensionList.get(i);
			bed.setCleared(1);
			updateBedRecord(bed);
		}
	}
	
	public void setpensionBedMapper(PensionBedMapper pensionBedMapper) {
		this.pensionBedMapper = pensionBedMapper;
	}

	public PensionBedMapper getpensionBedMapper() {
		return pensionBedMapper;
	}

}
