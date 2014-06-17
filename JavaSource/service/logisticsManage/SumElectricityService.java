package service.logisticsManage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.logisticsManage.PensionElectricityRecordMapper;
@Service
public class SumElectricityService {
	@Autowired
	private PensionElectricityRecordMapper pensionElectricityRecordMapper;
	
//	public List<PensionElectricityRecordExtend> selectElectricityConsumptionRecords(
//			Date startDate, Date endDate, Long buildId, Long floorId,
//			Long roomId) {
//		HashMap<String,Object> map = new HashMap<String, Object>();
//		map.put("startDate", startDate);
//		if(endDate != null){
//			map.put("endDate", DateUtil.getNextDay(endDate));
//		}else{
//			map.put("endDate", null);
//		}		
//		map.put("buildId", buildId);
//		map.put("floorId", floorId);
//		map.put("roomId", roomId);
//		return pensionElectricityRecordMapper.selectElectricityConsumptionRecords(map);
//	}


}
