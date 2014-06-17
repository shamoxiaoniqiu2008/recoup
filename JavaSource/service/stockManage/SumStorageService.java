package service.stockManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.dictionary.PensionDicItemtypeMapper;
import persistence.logisticsManage.PensionGoodsDetailMapper;

import com.centling.his.util.DateUtil;

import domain.dictionary.PensionDicItemtype;
import domain.dictionary.PensionDicItemtypeExample;
import domain.stockManage.PensionStorage;

@Service
public class SumStorageService {
	@Autowired
	private PensionGoodsDetailMapper pensionGoodsDetailMapper;
	@Autowired
	private PensionDicItemtypeMapper pensionDicItemtypeMapper;

	/**
	 * 查询物资类型列表
	 * 
	 * @return
	 */
	public List<PensionDicItemtype> selectItemType() {
		PensionDicItemtypeExample example = new PensionDicItemtypeExample();
		example.or().andClearedEqualTo(2);
		example.setOrderByClause("id asc");
		List<PensionDicItemtype> typeList = new ArrayList<PensionDicItemtype>();
		typeList = pensionDicItemtypeMapper.selectByExample(example);
		return typeList;
	}

	/**
	 * 根据查询条件 查询对应的消耗量
	 * 
	 * @param consumptionStartDate
	 * @param consumptionEndDate
	 * @param consumptionItemId
	 * @return
	 */
	public List<StorageOutDetailDomain> selectStorageoutRecords(
			Date consumptionStartDate, Date consumptionEndDate,
			Long consumptionItemId, Long itemTypeId) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", consumptionStartDate);
		if (consumptionEndDate != null) {
			map.put("endDate", DateUtil.getNextDay(consumptionEndDate));
		} else {
			map.put("endDate", null);
		}
		map.put("itemId", consumptionItemId);
		map.put("typeId", itemTypeId);
		return pensionGoodsDetailMapper.selectStorageoutRecords(map);
	}

	/**
	 * 根据查询条件 查询对应的剩余
	 * 
	 * @param remainItemId
	 * @return
	 */
	public List<PensionStorage> selectRemainRecords(Long remainItemId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", remainItemId);
		return pensionGoodsDetailMapper.selectStorageRecords(map);
	}

	public void setPensionGoodsDetailMapper(
			PensionGoodsDetailMapper pensionGoodsDetailMapper) {
		this.pensionGoodsDetailMapper = pensionGoodsDetailMapper;
	}

	public PensionGoodsDetailMapper getPensionGoodsDetailMapper() {
		return pensionGoodsDetailMapper;
	}

}
