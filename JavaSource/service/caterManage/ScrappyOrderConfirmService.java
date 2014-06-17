/**  
 * @Title: ScrappyOrderConfirmService.java 
 * @Package service.caterManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-9-5 下午1:25:38 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package service.caterManage;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.caterManage.PensionFoodmenuMapper;
import persistence.caterManage.PensionOrderMapper;
import persistence.configureManage.PensionItempurseMapper;
import persistence.olderManage.PensionOlderMapper;
import util.PmsException;
import util.SystemConfig;

import domain.caterManage.PensionFoodmenu;
import domain.caterManage.PensionOrder;
import domain.caterManage.PensionOrderExample;

/**
 * @ClassName: ScrappyOrderConfirmService
 * @Description: 零点餐确认Service
 * @author Justin.Su
 * @date 2013-9-5 下午1:25:38
 * @version V1.0
 */
@Service
public class ScrappyOrderConfirmService {

	@Autowired
	private PensionFoodmenuMapper pensionFoodmenuMapper;
	@Autowired
	private PensionOrderMapper pensionOrderMapper;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;
	@Autowired
	private SystemConfig systemConfig;
	@Autowired
	private PensionItempurseMapper pensionItempurseMapper;

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
		poe.setOrderByClause("eatTime desc");
		totalPensionOrderedList = pensionOrderMapper.selectByExample(poe);
		if (totalPensionOrderedList.size() > 0) {
			for (PensionOrder pensionOrder : totalPensionOrderedList) {
				// 是否交款
				if (pensionOrder.getIspay() == null) {
					pensionOrder.setIsPayName("");
				} else if (pensionOrder.getIspay() == 1) {
					pensionOrder.setIsPayName("是");
				} else {
					pensionOrder.setIsPayName("否");
				}
				// 是否确认
				if (pensionOrder.getIscomfirm() == null) {
					pensionOrder.setIsConfirmName("");
				} else if (pensionOrder.getIscomfirm() == 1) {
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
		return totalPensionOrderedList;
	}

	/**
	 * 
	 * @Title: getOrderedListByCondition
	 * @Description:根据条件查询
	 * @param @param olderId
	 * @param @param startDate
	 * @param @param endDate
	 * @param @param isPayFlag
	 * @param @return
	 * @return List<PensionOrder>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-9 上午10:59:55
	 * @version V1.0
	 */
	public List<PensionOrder> getOrderedListByCondition(Long olderId,
			Date startDate, Date endDate, String isPayFlag, String isConfirmFlag) {
		List<PensionOrder> pensionOrderedList = null;
		pensionOrderedList = pensionOrderMapper.selectOrderedListByCondition(
				olderId, startDate, endDate, Long.valueOf(isPayFlag),
				Long.valueOf(isConfirmFlag));
		if (pensionOrderedList.size() > 0) {
			for (PensionOrder pensionOrder : pensionOrderedList) {
				// 是否交款
				if (pensionOrder.getIspay() == null) {
					pensionOrder.setIsPayName("");
				} else if (pensionOrder.getIspay() == 1) {
					pensionOrder.setIsPayName("是");
				} else {
					pensionOrder.setIsPayName("否");
				}
				// 是否确认
				if (pensionOrder.getIscomfirm() == null) {
					pensionOrder.setIsConfirmName("");
				} else if (pensionOrder.getIscomfirm() == 1) {
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
		return pensionOrderedList;
	}

	/**
	 * 
	 * @Title: getAmount
	 * @Description: 获取菜品单价
	 * @param @param foodId
	 * @param @return
	 * @return Float
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 上午11:20:43
	 * @version V1.0
	 */
	public Float getAmount(Long foodId) {
		return pensionFoodmenuMapper.selectByPrimaryKey(foodId).getPurse();
	}

	/**
	 * 
	 * @Title: getFoodMenu
	 * @Description: 获取菜品对象
	 * @param @param foodId
	 * @param @return
	 * @return PensionFoodmenu
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-10 上午11:20:54
	 * @version V1.0
	 */
	public PensionFoodmenu getFoodMenu(Long foodId) {
		return pensionFoodmenuMapper.selectByPrimaryKey(foodId);
	}

	/**
	 * 
	 * @Title: updateFlag
	 * @Description: TODO
	 * @param @param tempList
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date Sep 18, 2013 5:47:52 PM
	 * @version V1.0
	 */
	public void updateFlag(List<PensionOrder> tempList) {
		for (PensionOrder p : tempList) {
			PensionOrder poU = new PensionOrder();
			PensionOrderExample poe = new PensionOrderExample();
			poU.setIscomfirm(1);
			poe.or().andIdEqualTo(p.getId());
			pensionOrderMapper.updateByExampleSelective(poU, poe);
		}
	}

	/**
	 * 
	 * @Title: updateOrderPaymentId
	 * @Description: TODO
	 * @param @param po
	 * @param @param pId
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-27 上午10:29:41
	 * @version V1.0
	 */
	public void updateOrderPaymentId(PensionOrder po, Long pId) {
		PensionOrder poForUpdate = new PensionOrder();
		PensionOrderExample poe = new PensionOrderExample();
		poForUpdate.setPaymentId(pId);
		poe.or().andIdEqualTo(po.getId());
		pensionOrderMapper.updateByExampleSelective(poForUpdate, poe);
	}

	/**
	 * 
	 * @Title: getSendPurseValue
	 * @Description: TODO
	 * @param @return
	 * @return Float
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-10 下午4:50:04
	 * @version V1.0
	 */
	public Float getSendPurseValue() {
		Float value = (float) 0;
		try {
			value = pensionItempurseMapper.selectByPrimaryKey(
					Long.valueOf(systemConfig
							.selectProperty("FOOD_SEND_ITEMPURSEID")))
					.getPurse();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 
	 * @Title: judgeSendFlag
	 * @Description: TODO
	 * @param @param tmList
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-10 下午5:00:56
	 * @version V1.0
	 */
	public boolean judgeSendFlag(List<PensionOrder> tmList) {
		boolean flag = false;
		List<PensionOrder> judgeList = null;
		Long olderId = tmList.get(0).getOlderId();
		Date eatTime = tmList.get(0).getEattime();
		Calendar ca = Calendar.getInstance();
		ca.setTime(eatTime);
		ca.set(Calendar.SECOND, 0);
		ca.set(Calendar.MILLISECOND, 0);
		PensionOrderExample poep = new PensionOrderExample();
		Date eatDate = ca.getTime();
		poep.or().andOlderIdEqualTo(olderId).andSendFlagEqualTo(1)
				.andIscomfirmEqualTo(2).andIspayEqualTo(2)
				.andEattimeEqualTo(eatDate);
		judgeList = pensionOrderMapper.selectByExample(poep);
		if (judgeList.size() > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

}
