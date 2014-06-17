package service.reportManage;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.configureManage.PensionBuildingMapper;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionBuildingExample;

/**

 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class OlderLiquidityService {
	
	private static final Integer NO_FLAG = 2;
	@Autowired
	private PensionBuildingMapper pensionBuildingMapper;


	
	public List<PensionBuilding> selectBuildings(){
		PensionBuildingExample example=new PensionBuildingExample();
		example.or().andClearedEqualTo(NO_FLAG);
		return pensionBuildingMapper.selectByExample(example);
	}	

	/**
	 * 获取最大值，并且可被4整除
	 * @param series
	 * @return
	 */
	public Integer getMax(List<ChartSeries> series) {
		Integer max=new Integer(0);
		for(ChartSeries serie: series){
			Map<Object, Number> tempMap = serie.getData();
			for (Entry<Object, Number> entry : tempMap.entrySet()) {
				if (max.intValue() < entry.getValue().intValue()) {
					max = entry.getValue().intValue();
				}
			}
		}
		max=(int) (max+Math.pow(10, new Integer(max.toString().length()-1)));
		for(int i=0;i<4;i++){
			Float a=(float) (max/4);
			Double b=Math.ceil(max/4);
			if(a.toString().equals(b.toString()) ){
				break;
			}else{
				max+=1;
			}
		}
		return max;
	}

	public List<PensionBuilding> selectOlderLiquidityByDate(Date date) {
		return pensionBuildingMapper.selectOlderLiquidityByDate(date);
	}


	
	
	
	
	
	
	
	
	


}
