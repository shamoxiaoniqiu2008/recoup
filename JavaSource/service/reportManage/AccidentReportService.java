package service.reportManage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.olderManage.PensionAccidentRecordMapper;
import domain.olderManage.PensionAccidentRecord;

/**

 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class AccidentReportService {
	
	
	@Autowired
	private PensionAccidentRecordMapper pensionAccidentRecordMapper;
	
	public List<PensionAccidentRecord> selectAccidentRecordByDate(Date startDate,Date endDate){
		return pensionAccidentRecordMapper.selectAccidentRecordByDate( startDate, endDate);
		
	}
	
	
	
	


}
