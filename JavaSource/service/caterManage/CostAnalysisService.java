/**  
* @Title: CostAnalysisService.java 
* @Package service.caterManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-9-9 上午11:07:21 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package service.caterManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.caterManage.PensionFoodIngredientMapper;

import controller.caterManage.ExtendForCostAnalysis;

/** 
 * @ClassName: CostAnalysisService 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-9 上午11:07:21
 * @version V1.0 
 */

@Service
public class CostAnalysisService {
	
	@Autowired
	private PensionFoodIngredientMapper pensionFoodIngredientMapper;

	/**
	 * 
	* @Title: getAllCostAnalysisList 
	* @Description: 查询所有记录
	* @param @return
	* @return List<ExtendForCostAnalysis>
	* @throws 
	* @author Justin.Su
	* @date 2013-9-9 下午3:22:43
	* @version V1.0
	 */
	public List<ExtendForCostAnalysis> getAllCostAnalysisList(){
		return pensionFoodIngredientMapper.selectCostAnalysisRecord();
	}
	
	/**
	 * 
	* @Title: getAllCostAnalysisListByCondition 
	* @Description: 根据条件查询
	* @param @param foodId
	* @param @return
	* @return List<ExtendForCostAnalysis>
	* @throws 
	* @author Justin.Su
	* @date 2013-9-9 下午3:22:29
	* @version V1.0
	 */
	public List<ExtendForCostAnalysis> getAllCostAnalysisListByCondition(Long foodId,Date startDate,Date endDate){
		List<ExtendForCostAnalysis> extendForCostAnalysis = new ArrayList<ExtendForCostAnalysis>();
			extendForCostAnalysis = pensionFoodIngredientMapper.selectCostAnalysisRecordByCondition(foodId,startDate,endDate);
		return extendForCostAnalysis;
	}
}
