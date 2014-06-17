package service.reportManage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.logisticsManage.PensionRepairMapper;
import service.logisticsManage.RepairCount;

/**

 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class RepairReportService {
	
	
	@Autowired
	private PensionRepairMapper pensionRepairMapper;
	
	public List<RepairCount> selectAccidentRecordByDate(Date startDate,Date endDate, String sortClass){
		return pensionRepairMapper.selectRepairRecordByDate( startDate, endDate,sortClass);
		
	}
	
	
	
	


}
