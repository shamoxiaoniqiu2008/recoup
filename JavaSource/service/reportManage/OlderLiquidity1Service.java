package service.reportManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.configureManage.PensionBuildingMapper;
import persistence.olderManage.PensionDepartregisterMapper;
import persistence.olderManage.PensionLivingrecordMapper;
import domain.configureManage.PensionBuilding;
import domain.reportManage.OlderLiquidityReport;

/**

 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class OlderLiquidity1Service {
	

	@Autowired
	private PensionBuildingMapper pensionBuildingMapper;
	@Autowired
	private PensionLivingrecordMapper pensionLivingrecordMapper;
	@Autowired
	private PensionDepartregisterMapper pensionDepartregisterMapper;

	private final static Integer IN_TOTAL=1;
	private final static Integer NEW_IN=2;
	private final static Integer NEW_OUT=3;
	
	
	public Map<String,Object> selectOlderLiquidity(Date startDate,
			Date endDate) {
		//开始时间点的在院老人总数
		Integer startOlderNum=pensionBuildingMapper.selectOlderLiquidityByDateInTotal(startDate);
		//结束时间点的在院老人总数
		Integer endOlderNum=pensionBuildingMapper.selectOlderLiquidityByDateInTotal(endDate);
		List<OlderLiquidityReport> olderLiquidityReports=new ArrayList<OlderLiquidityReport>();
		olderLiquidityReports.addAll(pensionLivingrecordMapper.selectOlderLiquidityReports(IN_TOTAL,startDate,endDate));
		olderLiquidityReports.addAll(pensionLivingrecordMapper.selectOlderLiquidityReports(NEW_IN,startDate,endDate));
		olderLiquidityReports.addAll(pensionLivingrecordMapper.selectOlderLiquidityReports(NEW_OUT,startDate,endDate));
		Map<String,Object> olderLiquidityReportMap=new HashMap<String,Object>();
		olderLiquidityReportMap.put("StartOlderNum",startOlderNum );
		olderLiquidityReportMap.put("EndOlderNum",endOlderNum );
		olderLiquidityReportMap.put("olderLiquidityReports",olderLiquidityReports );
		return olderLiquidityReportMap;
	}


	
	
	
	
	
	
	
	
	


}
