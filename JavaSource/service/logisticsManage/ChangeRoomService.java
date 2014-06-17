package service.logisticsManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centling.his.util.DateUtil;

import persistence.logisticsManage.PensionChangeroomMapper;

import domain.logisticsManage.PensionChangeroomExtend;

@Service
public class ChangeRoomService {
	@Autowired
	private PensionChangeroomMapper pensionChangeroomMapper;

	public List<PensionChangeroomExtend> selectChangeroomApplicationRecords(
			Date startDate, Date endDate, Long olderId) {
		
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if(endDate != null){
			map.put("endDate", DateUtil.getNextDay(endDate));
		}else{
			map.put("endDate", null);
		}		
		map.put("olderId", olderId);
		return null;

		
	}

	public void deleteChangeroomApplicationRecord(
			PensionChangeroomExtend selectedRow) {
		selectedRow.setCleared(1);
		pensionChangeroomMapper.updateByPrimaryKeySelective(selectedRow);
		
	}

	public void insertChangeroomApplicationRecord(
			PensionChangeroomExtend insertedRow) {
		//insertedRow.setChangeroomTime(new Date());
		insertedRow.setCleared(2);
		pensionChangeroomMapper.insertSelective(insertedRow);
		
	}

	public void updateChangeroomApplicationRecord(
			PensionChangeroomExtend updatedRow) {
		pensionChangeroomMapper.updateByPrimaryKeySelective(updatedRow);
	}

	
	public List<PensionChangeroomExtend> fillBedField(Long newbedId) {
		return pensionChangeroomMapper.fillBedField(newbedId);
	}
	
	public void setPensionChangeroomMapper(PensionChangeroomMapper pensionChangeroomMapper) {
		this.pensionChangeroomMapper = pensionChangeroomMapper;
	}

	public PensionChangeroomMapper getPensionChangeroomMapper() {
		return pensionChangeroomMapper;
	}

}
