/**  
 * @Title: StockUtil.java 
 * @Package util 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-12-3 下午3:34:24 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package util;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.dictionary.PensionDicItemtypeMapper;
import persistence.stockManage.PensionItemCatalogMapper;
import persistence.stockManage.PensionStorageMapper;
import domain.dictionary.PensionDicItemtype;
import domain.dictionary.PensionDicItemtypeExample;
import domain.stockManage.PensionItemCatalog;
import domain.stockManage.PensionItemCatalogExample;

/**
 * @ClassName: StockUtil
 * @Description: 库存公用方法类
 * @author Justin.Su
 * @date 2013-12-3 下午3:34:24
 * @version V1.0
 */
@Service
public class StockUtil {

	@Autowired
	private PensionStorageMapper pensionStorageMapper;
	@Autowired
	private PensionItemCatalogMapper pensionItemCatalogMapper;
	@Autowired
	private PensionDicItemtypeMapper pensionDicItemtypeMapper;

	
	/**
	 * 
	* @Title: addStorage 
	* @Description: TODO
	* @param @param typeId
	* @param @param itemId
	* @param @param storageQty
	* @param @param purchasePrice
	* @param @return
	* @param @throws CustomException
	* @return Float
	* @throws 
	* @author Justin.Su
	* @date 2013-12-3 下午7:08:21
	* @version V1.0
	 */
	@Transactional(rollbackFor = Exception.class)
	public Float addStorage(Long typeId, Long itemId, Float storageQty,
			Float purchasePrice) throws CustomException {
		Float fQty = (float) 0;
		int number = 0;
		if (plusMinusCheck(storageQty)) {
			// check物资类型或者物资是否存在
			if (checkTypeAndItem(typeId, itemId)) {
				// 如果进货价为空
				if (purchasePrice == null) {
					fQty = pensionStorageMapper.selectStorageQtyWithoutPrice(
							typeId, itemId);
					number = pensionStorageMapper.updateAddQtyWithoutPrice(
							typeId, itemId, storageQty);
					if (number == 0) {
						throw new CustomException("没有符合条件的记录，加库存失败！");
					}
				} else {
					fQty = pensionStorageMapper.selectStorageQtyWithPrice(
							typeId, itemId, purchasePrice);
					number = pensionStorageMapper.updateAddQtyWithPrice(typeId,
							itemId, storageQty, purchasePrice);
					if (number == 0) {
						throw new CustomException("没有符合条件的记录，加库存失败！");
					}
				}
			} else {
				throw new CustomException("物资类型或该物资不存在！");
			}
		} else {
			throw new CustomException("库存数量不能为零或者负数！");
		}

		return fQty;
	}

	
	/**
	 * 
	* @Title: subtractStorage 
	* @Description: TODO
	* @param @param typeId
	* @param @param itemId
	* @param @param storageQty
	* @param @param purchasePrice
	* @param @return
	* @param @throws CustomException
	* @return Float
	* @throws 
	* @author Justin.Su
	* @date 2013-12-3 下午7:08:08
	* @version V1.0
	 */
	@Transactional(rollbackFor = Exception.class)
	public Float subtractStorage(Long typeId, Long itemId, Float storageQty,
			Float purchasePrice) throws CustomException {
		Float fQty = (float) 0;
		int number = 0;
		if (plusMinusCheck(storageQty)) {
			if (checkTypeAndItem(typeId, itemId)) {
				if (purchasePrice == null) {
					fQty = pensionStorageMapper.selectStorageQtyWithoutPrice(
							typeId, itemId);
					number = pensionStorageMapper
							.updateSubtractQtyWithoutPrice(typeId, itemId,
									storageQty);
					if (number == 0) {
						throw new CustomException("没有符合条件的记录，减库存失败！");
					}
				} else {
					fQty = pensionStorageMapper.selectStorageQtyWithoutPrice(
							typeId, itemId);
					number = pensionStorageMapper.updateSubtractQtyWithPrice(
							typeId, itemId, storageQty, purchasePrice);
					if (number == 0) {
						throw new CustomException("没有符合条件的记录，减库存失败！");
					}
				}
			} else {
				throw new CustomException("物资类型或该物资不存在！");
			}
		} else {
			throw new CustomException("库存数量不能为零或者负数！");
		}

		return fQty;
	}

	/**
	 * 
	 * @Title: checkTypeAndItem
	 * @Description: TODO
	 * @param @param typeId
	 * @param @param itemId
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-3 下午5:15:29
	 * @version V1.0
	 */
	private boolean checkTypeAndItem(Long typeId, Long itemId) {
		boolean flag = false;
		List<PensionDicItemtype> pensionDicItemtypeList = null;
		List<PensionItemCatalog> pensionItemCatalogList = null;
		PensionDicItemtypeExample pde = new PensionDicItemtypeExample();
		PensionItemCatalogExample pice = new PensionItemCatalogExample();
		pde.or().andIdEqualTo(typeId).andClearedEqualTo(2);
		pice.or().andIdEqualTo(itemId).andClearedEqualTo(2);
		pensionDicItemtypeList = pensionDicItemtypeMapper.selectByExample(pde);
		pensionItemCatalogList = pensionItemCatalogMapper.selectByExample(pice);
		if (pensionDicItemtypeList.size() > 0
				&& pensionItemCatalogList.size() > 0) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * @Title: plusMinusCheck
	 * @Description: TODO
	 * @param @param qty
	 * @param @return
	 * @return boolean
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-3 下午7:07:50
	 * @version V1.0
	 */
	private boolean plusMinusCheck(Float qty) {
		boolean flag = false;
		BigDecimal qtyBigDecimal = new BigDecimal(qty);
		BigDecimal zeroBigDecimal = new BigDecimal(0);
		if (qtyBigDecimal.compareTo(zeroBigDecimal) == -1
				|| qtyBigDecimal.compareTo(zeroBigDecimal) == 0) {
			flag = false;
		} else {
			flag = true;
		}
		return flag;
	}

}
