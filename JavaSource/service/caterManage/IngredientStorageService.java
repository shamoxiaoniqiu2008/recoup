/**  
* @Title: IngredientStorageService.java 
* @Package service.caterManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-9-9 上午11:13:16 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package service.caterManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.dictionary.PensionDicItemtypeMapper;
import persistence.stockManage.PensionItemCatalogMapper;
import persistence.stockManage.PensionStorageMapper;
import domain.dictionary.PensionDicItemtype;
import domain.stockManage.PensionStorage;
import domain.stockManage.PensionStorageExample;

/** 
 * @ClassName: IngredientStorageService 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-9 上午11:13:16
 * @version V1.0 
 */
@Service
public class IngredientStorageService {

	@Autowired
	private PensionStorageMapper pensionStorageMapper;
	@Autowired
	private PensionDicItemtypeMapper pensionDicItemtypeMapper;
	@Autowired
	private PensionItemCatalogMapper pensionItemCatalogMapper;
	
	/**
	 * 
	* @Title: getAllStorage 
	* @Description: TODO
	* @param @param typeId
	* @param @return
	* @return List<PensionStorage>
	* @throws 
	* @author Justin.Su
	* @date 2013-11-5 上午10:19:35
	* @version V1.0
	 */
	public List<PensionStorage> getAllStorage(Long typeId){
		List<PensionStorage> retrunList =null;
		PensionStorageExample pse = new PensionStorageExample();
		pse.or().andTypeIdEqualTo(typeId);
		retrunList = pensionStorageMapper.selectByExample(pse);
		for(PensionStorage ps : retrunList){
			ps.setTypeName(pensionDicItemtypeMapper.selectByPrimaryKey(ps.getTypeId()).getItemName());
			ps.setUnit(pensionItemCatalogMapper.selectByPrimaryKey(ps.getItemId()).getUnit());
		}
		return retrunList;
	}
	
	public List<PensionStorage> getStorageByCondition(Long itemId,boolean zeroFlag,Long itemTypeId){
		List<PensionStorage> retrunList =null;
		PensionStorageExample pse = new PensionStorageExample();
		if(zeroFlag){
			pse.or().andItemIdEqualTo(itemId).andStorageQtyGreaterThanOrEqualTo(Float.valueOf(0)).andTypeIdEqualTo(itemTypeId);
		}else{
			pse.or().andItemIdEqualTo(itemId).andStorageQtyGreaterThan(Float.valueOf(0)).andTypeIdEqualTo(itemTypeId);
		}
		retrunList = pensionStorageMapper.selectByExample(pse);
		for(PensionStorage ps : retrunList){
			ps.setTypeName(pensionDicItemtypeMapper.selectByPrimaryKey(ps.getTypeId()).getItemName());
			ps.setUnit(pensionItemCatalogMapper.selectByPrimaryKey(ps.getItemId()).getUnit());
		}
		return retrunList;
	}
}
