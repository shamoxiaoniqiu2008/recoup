package service.logisticsManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centling.his.util.DateUtil;

import persistence.logisticsManage.PensionRepairMapper;

@Service
public class SumRepairCountService {
	@Autowired
	private PensionRepairMapper pensionRepairMapper;
	
	public List<RepairCount> selectRepairCountRecords(Date startDate,
			Date endDate, Long buildId, Long floorId, Long roomId) {
		
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if(endDate != null){
			map.put("endDate", DateUtil.getNextDay(endDate));
		}else{
			map.put("endDate", null);
		}		
		map.put("buildId", buildId);
		map.put("floorId", floorId);
		map.put("roomId", roomId);
		return pensionRepairMapper.selectRepairCountRecords(map);
	}

	public void setPensionRepairMapper(PensionRepairMapper pensionRepairMapper) {
		this.pensionRepairMapper = pensionRepairMapper;
	}

	public PensionRepairMapper getPensionRepairMapper() {
		return pensionRepairMapper;
	}

}
