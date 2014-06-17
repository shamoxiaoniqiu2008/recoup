/**  
 * @Title: MenuMaintainService.java 
 * @Package service.caterManage
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-4 下午1:39:16 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package service.caterManage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.caterManage.PensionDicCuisineMapper;
import persistence.caterManage.PensionFoodIngredientMapper;
import persistence.caterManage.PensionFoodmenuMapper;
import domain.caterManage.PensionDicCuisine;
import domain.caterManage.PensionFoodIngredient;
import domain.caterManage.PensionFoodIngredientExample;
import domain.caterManage.PensionFoodmenu;
import domain.caterManage.PensionFoodmenuExample;

/**
 * @ClassName: MenuMaintainService
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-4 下午1:39:16
 * @version V1.0
 */
@Service
public class MenuMaintainService {

	@Autowired
	private PensionFoodmenuMapper pensionFoodmenuMapper;
	@Autowired
	private PensionDicCuisineMapper pensionDicCuisineMapper;
	@Autowired
	private PensionFoodIngredientMapper pensionFoodIngredientMapper;

	/**
	 * 页面初始化加载，查询所有的菜单列表
	 * 
	 * @Title: getAllFoodmenuList
	 * @Description: TODO
	 * @param @return
	 * @return List<PensionFoodmenu>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-4 下午3:32:39
	 * @version V1.0
	 */
	public List<PensionFoodmenu> getAllFoodmenuList() {
		List<PensionFoodmenu> totalPensionFoodmenuList = null;
		PensionFoodmenuExample example = new PensionFoodmenuExample();
		example.or().andClearedEqualTo(2);
		totalPensionFoodmenuList = pensionFoodmenuMapper
				.selectByExample(example);
		for (PensionFoodmenu pfd : totalPensionFoodmenuList) {
			pfd.setCuisineName(pensionDicCuisineMapper.selectByPrimaryKey(
					pfd.getCuisineId()).getCuisineName());
		}
		return totalPensionFoodmenuList;
	}

	/**
	 * 
	 * @Title: getAllFoodmenuListByCondition
	 * @Description: TODO
	 * @param @param foodMenuId
	 * @param @return
	 * @return List<PensionFoodmenu>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-10-31 上午10:07:00
	 * @version V1.0
	 */
	public List<PensionFoodmenu> getAllFoodmenuListByCondition(Long foodMenuId,
			Long cuisineId) {
		List<PensionFoodmenu> returnList = null;
		PensionFoodmenuExample pfe = new PensionFoodmenuExample();
		if (cuisineId == 0) {
			pfe.or().andIdEqualTo(foodMenuId).andClearedEqualTo(2);
		} else {
			pfe.or().andIdEqualTo(foodMenuId).andClearedEqualTo(2)
					.andCuisineIdEqualTo(cuisineId);
		}
		returnList = pensionFoodmenuMapper.selectByExample(pfe);
		for (PensionFoodmenu pfd : returnList) {
			pfd.setCuisineName(pensionDicCuisineMapper.selectByPrimaryKey(
					pfd.getCuisineId()).getCuisineName());
		}
		return returnList;
	}

	/**
	 * 
	 * @Title: getAllPensionDicCuisine
	 * @Description: TODO
	 * @param @return
	 * @return List<PensionDicCuisine>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-10-31 下午3:52:27
	 * @version V1.0
	 */
	public List<PensionDicCuisine> getAllPensionDicCuisine() {
		return pensionDicCuisineMapper.selectByExample(null);
	}

	/**
	 * 
	 * @Title: insertMenuBySelective
	 * @Description: TODO
	 * @param @param pensionFoodmenu
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-4 上午8:17:30
	 * @version V1.0
	 */
	public void insertMenuBySelective(PensionFoodmenu pensionFoodmenu,
			List<PensionFoodIngredient> itemList) {
		pensionFoodmenuMapper.insertSelective(pensionFoodmenu);
		for (PensionFoodIngredient item : itemList) {
			item.setMenuId(pensionFoodmenu.getId());
			pensionFoodIngredientMapper.insertSelective(item);
		}
	}

	/**
	 * 
	 * @Title: updateMenuBySelective
	 * @Description: TODO
	 * @param @param selectedPensionFoodmenu
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-4 上午10:21:57
	 * @version V1.0
	 */
	public void updateMenuBySelective(PensionFoodmenu selectedPensionFoodmenu,
			List<PensionFoodIngredient> itemList) {
		PensionFoodmenu record = new PensionFoodmenu();
		PensionFoodmenuExample example = new PensionFoodmenuExample();
		record.setCuisineId(selectedPensionFoodmenu.getCuisineId());
		record.setDes(selectedPensionFoodmenu.getDes());
		record.setImageurl(selectedPensionFoodmenu.getImageurl());
		record.setName(selectedPensionFoodmenu.getName());
		record.setNotes(selectedPensionFoodmenu.getNotes());
		record.setPurse(selectedPensionFoodmenu.getPurse());
		record.setInputcode(selectedPensionFoodmenu.getInputcode());
		example.or().andIdEqualTo(selectedPensionFoodmenu.getId());
		pensionFoodmenuMapper.updateByExampleSelective(record, example);

		PensionFoodIngredientExample ingreExample = new PensionFoodIngredientExample();
		ingreExample.or().andMenuIdEqualTo(selectedPensionFoodmenu.getId());
		pensionFoodIngredientMapper.deleteByExample(ingreExample);

		for (PensionFoodIngredient item : itemList) {
			item.setMenuId(selectedPensionFoodmenu.getId());
			pensionFoodIngredientMapper.insertSelective(item);
		}

	}

	/**
	 * 
	 * @Title: getAllMenuName
	 * @Description: TODO
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-4 上午8:22:22
	 * @version V1.0
	 */
	public List<String> getAllMenuName() {
		List<PensionFoodmenu> allList = null;
		List<String> menuNameList = new ArrayList<String>();
		PensionFoodmenuExample ex = new PensionFoodmenuExample();
		ex.or().andClearedEqualTo(2);
		allList = pensionFoodmenuMapper.selectByExample(ex);
		for (PensionFoodmenu pf : allList) {
			menuNameList.add(pf.getName());
		}
		return menuNameList;
	}

	/**
	 * 
	 * @Title: getOtherMenuName
	 * @Description: TODO
	 * @param @param pf
	 * @param @return
	 * @return List<String>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-4 上午10:49:54
	 * @version V1.0
	 */
	public List<String> getOtherMenuName(PensionFoodmenu pf) {
		List<PensionFoodmenu> allList = null;
		List<String> menuNameList = new ArrayList<String>();
		PensionFoodmenuExample pfe = new PensionFoodmenuExample();
		pfe.or().andIdNotEqualTo(pf.getId()).andClearedEqualTo(2);
		allList = pensionFoodmenuMapper.selectByExample(pfe);
		for (PensionFoodmenu pfu : allList) {
			menuNameList.add(pfu.getName());
		}
		return menuNameList;
	}

	/**
	 * 
	 * @Title: deleteMenu
	 * @Description: TODO
	 * @param @param pfmu
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-4 上午11:02:03
	 * @version V1.0
	 */
	public void deleteMenu(PensionFoodmenu pfmu) {
		PensionFoodmenu record = new PensionFoodmenu();
		PensionFoodmenuExample example = new PensionFoodmenuExample();
		record.setCleared(1);
		example.or().andIdEqualTo(pfmu.getId());
		pensionFoodmenuMapper.updateByExampleSelective(record, example);
	}

	public List<PensionFoodIngredient> selectItemCatalogs(Long menuId) {
		List<PensionFoodIngredient> ingredientList = new ArrayList<PensionFoodIngredient>();
		PensionFoodIngredientExample example = new PensionFoodIngredientExample();
		example.or().andMenuIdEqualTo(menuId);
		ingredientList = pensionFoodIngredientMapper.selectByExample(example);
		return ingredientList;
	}
}
