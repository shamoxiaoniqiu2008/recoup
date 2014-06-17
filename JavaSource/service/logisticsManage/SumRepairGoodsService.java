package service.logisticsManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centling.his.util.DateUtil;

import domain.logisticsManage.PensionGoodsDetail;

import persistence.logisticsManage.PensionGoodsDetailMapper;

@Service
public class SumRepairGoodsService {
	@Autowired
	private PensionGoodsDetailMapper pensionGoodsDetailMapper;
	/**
	 * 根据查询条件 查询对应的消耗量
	 * @param consumptionStartDate
	 * @param consumptionEndDate
	 * @param consumptionItemId
	 * @return
	 */
	public List<PensionGoodsDetail> selectConsumptionRecords(
			Date consumptionStartDate, Date consumptionEndDate,
			Long consumptionItemId) {
		
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", consumptionStartDate);
		if(consumptionEndDate != null){
			map.put("endDate", DateUtil.getNextDay(consumptionEndDate));
		}else{
			map.put("endDate", null);
		}		
		map.put("itemId", consumptionItemId);
		
		return pensionGoodsDetailMapper.selectConsumptionRecords(map);
	}
	/**
	 * 根据查询条件 查询对应的剩余
	 * @param remainEndDate
	 * @param remainItemId
	 * @return
	 */
	public List<PensionGoodsDetail> selectRemainRecords(Date remainEndDate,
			Long remainItemId) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		if(remainEndDate != null){
			map.put("endDate", DateUtil.getNextDay(remainEndDate));
		}else{
			map.put("endDate", null);
		}		
		map.put("itemId", remainItemId);
		return pensionGoodsDetailMapper.selectRemainRecords(map);
	}
	
	public void setPensionGoodsDetailMapper(PensionGoodsDetailMapper pensionGoodsDetailMapper) {
		this.pensionGoodsDetailMapper = pensionGoodsDetailMapper;
	}

	public PensionGoodsDetailMapper getPensionGoodsDetailMapper() {
		return pensionGoodsDetailMapper;
	}
	
}
