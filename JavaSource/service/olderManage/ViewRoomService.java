package service.olderManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.configureManage.PensionBuildingMapper;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionBuildingExample;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-20 上午9:03:44
 */
@Service
public class ViewRoomService {
	@Autowired
	private PensionBuildingMapper pensionBuildingMapper;

	public List<PensionBuilding> selectBuildList() {
		PensionBuildingExample example = new PensionBuildingExample();
		example.or().andClearedEqualTo(2);
		return pensionBuildingMapper.selectByExample(example);
	}

}
