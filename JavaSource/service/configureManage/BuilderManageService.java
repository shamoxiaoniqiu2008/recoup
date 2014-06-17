package service.configureManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.configureManage.PensionBuildingMapper;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionBuildingExample;
import domain.configureManage.PensionFloor;


@Service
public class BuilderManageService {
	@Autowired
	private PensionBuildingMapper pensionBuildingMapper;
	@Autowired
	private FloorManageService floorManageService;
	/**
	 * 查询大厦信息
	 * @param startDate
	 * @param endDate
	 * @param olderId
	 * @return
	 */
	public List<PensionBuilding> selectBuildRecords(String buildName) {
		// TODO Auto-generated method stub
		PensionBuildingExample example = new PensionBuildingExample();
		example.or().andClearedEqualTo(2).andNameLike("%"+buildName+"%");
		return pensionBuildingMapper.selectByExample(example);
	}
	/**
	 * 删除大厦信息
	 * @param selectedBuildRow
	 */
	public void deleteBuildRecord(PensionBuilding selectedBuildRow) {

		pensionBuildingMapper.deleteByPrimaryKey(selectedBuildRow.getId());
	}
	/**
	 * 录入大厦信息
	 * @param addedBuildRow
	 */
	public void insertBuildRecord(PensionBuilding addedBuildRow) {
		/*
		 * 貌似还缺点儿东西
		 */
		pensionBuildingMapper.insertSelective(addedBuildRow);
	}
	/**
	 * 修改大厦信息
	 * @param updatedBuildRow
	 */
	public void updateBuildRecord(PensionBuilding updatedBuildRow) {
		
		pensionBuildingMapper.updateByPrimaryKeySelective(updatedBuildRow);
		List<PensionFloor> floors = floorManageService.selectFloorRecords(updatedBuildRow.getId());
		for(int i=0;i<floors.size();i++)
		{
			floors.get(i).setBuildname(updatedBuildRow.getName());
			floorManageService.updateFloorRecord(floors.get(i));
		}
		
	}
	
	public PensionBuilding selectByPrimaryKey(Long id) {
		
		return pensionBuildingMapper.selectByPrimaryKey(id);
	}
	
	public void setpensionBuildingMapper(PensionBuildingMapper pensionBuildingMapper) {
		this.pensionBuildingMapper = pensionBuildingMapper;
	}

	public PensionBuildingMapper getpensionBuildingMapper() {
		return pensionBuildingMapper;
	}
	public List<PensionBuilding> selectBuildRecords() {
		// TODO Auto-generated method stub
		PensionBuildingExample example = new PensionBuildingExample();
		example.or().andClearedEqualTo(2);
		return pensionBuildingMapper.selectByExample(example);
	}
	public void setFloorManageService(FloorManageService floorManageService) {
		this.floorManageService = floorManageService;
	}
	public FloorManageService getFloorManageService() {
		return floorManageService;
	}
}
