/**  
 * @Title: ScrappyOrderService.java 
 * @Package service.caterManage 
 * @Description: 零点餐Service
 * @author Justin.Su
 * @date 2013-9-4 下午7:54:35 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package service.caterManage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.caterManage.PensionDicCuisine;
import domain.caterManage.PensionFoodmenu;
import domain.caterManage.PensionFoodmenuExample;
import domain.caterManage.PensionOrder;
import domain.caterManage.PensionOrderExample;
import domain.olderManage.PensionOlder;

import persistence.caterManage.PensionDicCuisineMapper;
import persistence.caterManage.PensionFoodmenuMapper;
import persistence.caterManage.PensionOrderMapper;
import persistence.olderManage.PensionOlderMapper;

/**
 * @ClassName: ScrappyOrderService
 * @Description: 零点餐Service
 * @author Justin.Su
 * @date 2013-9-4 下午7:54:35
 * @version V1.0
 */
@Service
public class ScrappyOrderService {

	@Autowired
	private PensionFoodmenuMapper pensionFoodmenuMapper;
	@Autowired
	private PensionOrderMapper pensionOrderMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private PensionDicCuisineMapper pensionDicCuisineMapper;

	private static final Integer NO_FLAG = 2;// 否

	/**
	 * 
	 * @Title: getAllFoodmenuList
	 * @Description: 查询所有的菜单列表
	 * @param @return
	 * @return List<PensionFoodmenu>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-5 下午6:47:00
	 * @version V1.0
	 */
	public List<PensionFoodmenu> getAllFoodmenuList() {
		List<PensionFoodmenu> totalPensionFoodmenuList = null;
		PensionFoodmenuExample pfe = new PensionFoodmenuExample();
		pfe.or().andClearedEqualTo(NO_FLAG);// 未删除的 add by mary 2013-12-16
		totalPensionFoodmenuList = pensionFoodmenuMapper.selectByExample(pfe);
		if (totalPensionFoodmenuList.size() > 0) {
			for (PensionFoodmenu pf : totalPensionFoodmenuList) {
				pf.setCuisineName(pensionDicCuisineMapper.selectByPrimaryKey(
						pf.getCuisineId()).getCuisineName());
			}
		}
		return totalPensionFoodmenuList;
	}

	/**
	 * 
	 * @Title: getAllOrderedList
	 * @Description: 查询所有已点餐列表
	 * @param @return
	 * @return List<PensionOrder>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-6 下午3:26:41
	 * @version V1.0
	 */
	public List<PensionOrder> getAllOrderedList() {
		List<PensionOrder> totalPensionOrderedList = null;
		PensionOrderExample poe = new PensionOrderExample();
		poe.setOrderByClause("id desc");
		totalPensionOrderedList = pensionOrderMapper.selectByExample(poe);
		if (totalPensionOrderedList.size() > 0) {
			for (PensionOrder pensionOrder : totalPensionOrderedList) {
				// 是否交款
				if (pensionOrder.getIspay() == 1) {
					pensionOrder.setIsPayName("是");
				} else {
					pensionOrder.setIsPayName("否");
				}
				// 是否确认
				if (pensionOrder.getIscomfirm() == 1) {
					pensionOrder.setIsConfirmName("是");
				} else {
					pensionOrder.setIsConfirmName("否");
				}
				// 是否派送
				if (pensionOrder.getSendFlag() == null) {
					pensionOrder.setSendFlagName("未知");
					pensionOrder.setTempSendFlag(false);
				} else if (pensionOrder.getSendFlag() == 1) {
					pensionOrder.setSendFlagName("是");
					pensionOrder.setTempSendFlag(true);
				} else {
					pensionOrder.setSendFlagName("否");
					pensionOrder.setTempSendFlag(false);
				}
				// 老人姓名
				pensionOrder.setOlderName(pensionOlderMapper
						.selectByPrimaryKey(pensionOrder.getOlderId())
						.getName());
				// 菜名
				pensionOrder.setMenuName(pensionFoodmenuMapper
						.selectByPrimaryKey(pensionOrder.getFoodMenuId())
						.getName());
			}
		}
		return totalPensionOrderedList;
	}

	/**
	 * 
	 * @Title: getPensionFoodmenuList
	 * @Description:根据条件查询菜品
	 * @param @param foodId
	 * @param @param startPrice
	 * @param @param endPrice
	 * @param @return
	 * @return List<PensionFoodmenu>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-5 下午5:06:34
	 * @version V1.0
	 */
	public List<PensionFoodmenu> getPensionFoodmenuList(Long foodId,
			String startPrice, String endPrice, String cuisinePara) {
		List<PensionFoodmenu> returnList = new ArrayList<PensionFoodmenu>();
		PensionFoodmenuExample pfe = new PensionFoodmenuExample();
		if (Long.valueOf(cuisinePara) == 0) {
			if (startPrice == null && endPrice == null) {
				if (foodId == null) {
					pfe.or().andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
														// 2013-12-16
					returnList = pensionFoodmenuMapper.selectByExample(pfe);
				} else {
					pfe.or().andIdEqualTo(foodId).andClearedEqualTo(NO_FLAG);// 未删除的
																				// add
																				// by
																				// mary
																				// 2013-12-16
					returnList = pensionFoodmenuMapper.selectByExample(pfe);
				}
			} else if (startPrice != null && endPrice == null) {
				float startPriceF = Float.valueOf(startPrice);
				BigDecimal startBig = new BigDecimal(startPriceF);
				startPriceF = startBig.setScale(2, BigDecimal.ROUND_HALF_UP)
						.floatValue();
				pfe.or().andIdEqualTo(foodId)
						.andPurseGreaterThanOrEqualTo(startPriceF)
						.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
													// 2013-12-16
				returnList = pensionFoodmenuMapper.selectByExample(pfe);
			} else if (startPrice == null && endPrice != null) {
				float endPriceF = Float.valueOf(endPrice);
				BigDecimal endBig = new BigDecimal(endPriceF);
				endPriceF = endBig.setScale(2, BigDecimal.ROUND_HALF_UP)
						.floatValue();
				pfe.or().andIdEqualTo(foodId)
						.andPurseLessThanOrEqualTo(endPriceF)
						.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
													// 2013-12-16
				returnList = pensionFoodmenuMapper.selectByExample(pfe);
			} else {
				float startPriceF = Float.valueOf(startPrice);
				float endPriceF = Float.valueOf(endPrice);
				BigDecimal startBig = new BigDecimal(startPriceF);
				startPriceF = startBig.setScale(2, BigDecimal.ROUND_HALF_UP)
						.floatValue();
				BigDecimal endBig = new BigDecimal(endPriceF);
				endPriceF = endBig.setScale(2, BigDecimal.ROUND_HALF_UP)
						.floatValue();
				pfe.or().andIdEqualTo(foodId)
						.andPurseGreaterThanOrEqualTo(startPriceF)
						.andPurseLessThanOrEqualTo(endPriceF)
						.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
													// 2013-12-16
				returnList = pensionFoodmenuMapper.selectByExample(pfe);
			}
		} else {
			if (startPrice == null && endPrice == null) {
				if (foodId == null) {
					pfe.or().andCuisineIdEqualTo(Long.valueOf(cuisinePara))
							.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
														// 2013-12-16
					returnList = pensionFoodmenuMapper.selectByExample(pfe);
				} else {
					pfe.or().andIdEqualTo(foodId)
							.andCuisineIdEqualTo(Long.valueOf(cuisinePara))
							.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
														// 2013-12-16
					returnList = pensionFoodmenuMapper.selectByExample(pfe);
				}
			} else if (startPrice != null && endPrice == null) {
				float startPriceF = Float.valueOf(startPrice);
				BigDecimal startBig = new BigDecimal(startPriceF);
				startPriceF = startBig.setScale(2, BigDecimal.ROUND_HALF_UP)
						.floatValue();
				if (foodId == null) {
					pfe.or().andPurseGreaterThanOrEqualTo(startPriceF)
							.andCuisineIdEqualTo(Long.valueOf(cuisinePara))
							.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
														// 2013-12-16
				} else {
					pfe.or().andIdEqualTo(foodId)
							.andPurseGreaterThanOrEqualTo(startPriceF)
							.andCuisineIdEqualTo(Long.valueOf(cuisinePara))
							.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
														// 2013-12-16
				}
				pfe.or().andIdEqualTo(foodId)
						.andPurseGreaterThanOrEqualTo(startPriceF)
						.andCuisineIdEqualTo(Long.valueOf(cuisinePara))
						.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
													// 2013-12-16
				returnList = pensionFoodmenuMapper.selectByExample(pfe);
			} else if (startPrice == null && endPrice != null) {
				float endPriceF = Float.valueOf(endPrice);
				BigDecimal endBig = new BigDecimal(endPriceF);
				endPriceF = endBig.setScale(2, BigDecimal.ROUND_HALF_UP)
						.floatValue();
				if (foodId == null) {
					pfe.or().andPurseLessThanOrEqualTo(endPriceF)
							.andCuisineIdEqualTo(Long.valueOf(cuisinePara))
							.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
														// 2013-12-16
				} else {
					pfe.or().andIdEqualTo(foodId)
							.andPurseLessThanOrEqualTo(endPriceF)
							.andCuisineIdEqualTo(Long.valueOf(cuisinePara))
							.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
														// 2013-12-16
				}

				returnList = pensionFoodmenuMapper.selectByExample(pfe);
			} else {
				float startPriceF = Float.valueOf(startPrice);
				float endPriceF = Float.valueOf(endPrice);
				BigDecimal startBig = new BigDecimal(startPriceF);
				startPriceF = startBig.setScale(2, BigDecimal.ROUND_HALF_UP)
						.floatValue();
				BigDecimal endBig = new BigDecimal(endPriceF);
				endPriceF = endBig.setScale(2, BigDecimal.ROUND_HALF_UP)
						.floatValue();
				if (foodId == null) {
					pfe.or().andPurseGreaterThanOrEqualTo(startPriceF)
							.andPurseLessThanOrEqualTo(endPriceF)
							.andCuisineIdEqualTo(Long.valueOf(cuisinePara))
							.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
														// 2013-12-16
				} else {
					pfe.or().andIdEqualTo(foodId)
							.andPurseGreaterThanOrEqualTo(startPriceF)
							.andPurseLessThanOrEqualTo(endPriceF)
							.andCuisineIdEqualTo(Long.valueOf(cuisinePara))
							.andClearedEqualTo(NO_FLAG);// 未删除的 add by mary
														// 2013-12-16
				}

				returnList = pensionFoodmenuMapper.selectByExample(pfe);
			}
		}

		if (returnList.size() > 0) {
			for (PensionFoodmenu pf : returnList) {
				pf.setCuisineName(pensionDicCuisineMapper.selectByPrimaryKey(
						pf.getCuisineId()).getCuisineName());
			}
		}

		return returnList;
	}

	/**
	 * 
	 * @Title: saveFoodMenu
	 * @Description: 保存点餐结果
	 * @param @param olderId
	 * @param @param diningDate
	 * @param @param pensionFoodmenu
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-6 下午3:27:04
	 * @version V1.0
	 */
	@Transactional(rollbackFor = Exception.class)
	public void saveFoodMenu(Long olderId, Date diningDate,
			PensionFoodmenu pensionFoodmenu, boolean sendFlagTemp) {
		PensionOrder po = new PensionOrder();
		po.setOlderId(olderId);
		po.setFoodMenuId(pensionFoodmenu.getId());
		po.setEattime(diningDate);
		po.setIscomfirm(2);
		po.setIspay(2);
		po.setCleared(2);
		po.setNotes(null);
		po.setOrderNumber(pensionFoodmenu.getOrderNumber().intValue());
		if (sendFlagTemp) {
			po.setSendFlag(1);
		} else {
			po.setSendFlag(2);
		}
		pensionOrderMapper.insertSelective(po);
	}

	/**
	 * 
	 * @Title: getAllOrderedListByCondition
	 * @Description: 根据条件查询所有已点餐列表
	 * @param @param olderId
	 * @param @param eatDate
	 * @param @return
	 * @return List<PensionOrder>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-7 上午9:46:36
	 * @version V1.0
	 * @param sendFlag 是否发送
	 * @param isConfirm 是否确认 
	 */
	public List<PensionOrder> getAllOrderedListByCondition(Long olderId,
			Date eatDate, int sendFlag, Integer isConfirm) {
		List<PensionOrder> queryPensionOrderList = null;
		queryPensionOrderList = pensionOrderMapper.selectByExampleLimit(
				olderId, eatDate,sendFlag,isConfirm);
		if (queryPensionOrderList.size() > 0) {
			for (PensionOrder pensionOrder : queryPensionOrderList) {
				// 是否交款
				if (pensionOrder.getIspay() == 1) {
					pensionOrder.setIsPayName("是");
				} else {
					pensionOrder.setIsPayName("否");
				}
				// 是否确认
				if (pensionOrder.getIscomfirm() == 1) {
					pensionOrder.setIsConfirmName("是");
				} else {
					pensionOrder.setIsConfirmName("否");
				}
				// 是否派送
				if (pensionOrder.getSendFlag() == 1) {
					pensionOrder.setSendFlagName("是");
				} else {
					pensionOrder.setSendFlagName("否");
				}
				// 老人姓名
				pensionOrder.setOlderName(pensionOlderMapper
						.selectByPrimaryKey(pensionOrder.getOlderId())
						.getName());
				// 菜名
				pensionOrder.setMenuName(pensionFoodmenuMapper
						.selectByPrimaryKey(pensionOrder.getFoodMenuId())
						.getName());
			}
		}

		return queryPensionOrderList;
	}

	/**
	 * 
	 * @Title: deleteOrdered
	 * @Description: 删除选中行记录
	 * @param @param pensionOrder
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-7 下午2:36:19
	 * @version V1.0
	 */
	public void deleteOrdered(PensionOrder pensionOrder) {
		pensionOrderMapper.deleteByPrimaryKey(pensionOrder.getId());
	}

	/**
	 * 
	 * @Title: getOlderName
	 * @Description: 获取老人姓名
	 * @param @param olderId
	 * @param @return
	 * @return String
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-7 下午2:44:33
	 * @version V1.0
	 */
	public String getOlderName(Long olderId) {
		PensionOlder pol = new PensionOlder();
		pol = pensionOlderMapper.selectByPrimaryKey(olderId);
		return pol.getName();
	}

	/**
	 * 
	 * @Title: getFoodMenuName
	 * @Description: 获取菜名
	 * @param @param foodMenuId
	 * @param @return
	 * @return String
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-7 下午2:45:40
	 * @version V1.0
	 */
	public String getFoodMenuName(Long foodMenuId) {
		PensionFoodmenu pfm = new PensionFoodmenu();
		pfm = pensionFoodmenuMapper.selectByPrimaryKey(foodMenuId);
		return pfm.getName();
	}

	/**
	 * 
	 * @Title: updateOrdered
	 * @Description: 更新记录
	 * @param @param menuId
	 * @param @param pensionOrder
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-7 下午5:19:31
	 * @version V1.0
	 */
	public void updateOrdered(Long menuId, PensionOrder pensionOrder) {
		PensionOrder poForUpdate = new PensionOrder();
		poForUpdate.setFoodMenuId(menuId);
		poForUpdate.setEattime(pensionOrder.getEattime());
		poForUpdate.setNotes(pensionOrder.getNotes());
		poForUpdate.setSendFlag(pensionOrder.isTempSendFlag()?1:2);
		PensionOrderExample poe = new PensionOrderExample();
		poe.or().andIdEqualTo(pensionOrder.getId());
		pensionOrderMapper.updateByExampleSelective(poForUpdate, poe);
	}

	/**
	 * 
	 * @Title: getAllPensionDicCuisineList
	 * @Description: 获取所有菜系List
	 * @param @return
	 * @return List<PensionDicCuisine>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-9 下午5:27:49
	 * @version V1.0
	 */
	public List<PensionDicCuisine> getAllPensionDicCuisineList() {
		List<PensionDicCuisine> totalPensionDicCuisineList = null;
		totalPensionDicCuisineList = pensionDicCuisineMapper
				.selectByExample(null);
		return totalPensionDicCuisineList;
	}

	/**
	 * 保存老人的点餐信息
	 * @author mary.liu 2014-04-08
	 * @param addOrderList
	 */
	public void saveOrderList(List<PensionOrder> orderList) {
		for(PensionOrder order: orderList){
			pensionOrderMapper.insertSelective(order);
		}
		
	}

}
