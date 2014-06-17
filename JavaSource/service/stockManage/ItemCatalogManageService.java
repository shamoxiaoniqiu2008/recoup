/**  
 * @Title: PurchaseApplyService.java 
 * @Package service.stockManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:48:01 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package service.stockManage;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.stockManage.PensionItemCatalogMapper;
import domain.stockManage.PensionItemCatalog;

/**
 * @ClassName: PurchaseApplyService
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:48:01
 * @version V1.0
 */
@Service
public class ItemCatalogManageService {
	@Autowired
	private PensionItemCatalogMapper pensionItemCatalogMapper;

	/**
	 * 查询物资目录列表
	 * 
	 * @param typeId
	 * @return
	 */
	public List<PensionItemCatalog> selectItemCalalogs(Long typeId) {
		List<PensionItemCatalog> itemCatalogList = new ArrayList<PensionItemCatalog>();
		itemCatalogList = pensionItemCatalogMapper.selectCatalogList(typeId);
		return itemCatalogList;
	}

	/**
	 * 删除物资目录
	 */
	@Transactional
	public void deleteItemCatalog(PensionItemCatalog record) {
		try {
			pensionItemCatalogMapper.updateByPrimaryKeySelective(record);
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 保存物资目录
	 * 
	 * @param record
	 */
	@Transactional
	public void insertItemCatalog(PensionItemCatalog record) {
		try {
			// 主记录
			pensionItemCatalogMapper.insertSelective(record);
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 更新物资目录
	 * 
	 * @param record
	 */
	@Transactional
	public void updateApplyRecord(PensionItemCatalog record) {
		try {
			// 更新主记录
			pensionItemCatalogMapper.updateByPrimaryKeySelective(record);
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
			System.out.print(ex.getMessage());
		}
	}

}
