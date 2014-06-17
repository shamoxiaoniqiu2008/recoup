/**  
 * @Title: StorageInService.java 
 * @Package service.stockManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:48:37 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package service.stockManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.stockManage.PensionPurchaseRecordMapper;
import persistence.stockManage.PensionStorageMapper;
import persistence.stockManage.PensionStorageinDetailMapper;
import persistence.stockManage.PensionStorageinRecordMapper;
import util.DateUtil;
import domain.stockManage.PensionPurchaseRecord;
import domain.stockManage.PensionPurchaseRecordExample;
import domain.stockManage.PensionStorage;
import domain.stockManage.PensionStorageExample;
import domain.stockManage.PensionStorageinDetail;
import domain.stockManage.PensionStorageinDetailExample;

/**
 * @ClassName: StorageInService
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:48:37
 * @version V1.0
 */
@Service
public class StorageInService {

	@Autowired
	private PensionStorageinDetailMapper pensionStorageinDetailMapper;
	@Autowired
	private PensionStorageinRecordMapper pensionStorageinRecordMapper;
	@Autowired
	private PensionStorageMapper pensionStorageMapper;
	@Autowired
	private PensionPurchaseRecordMapper pensionPurchaseRecordMapper;

	/**
	 * 查询入库记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<StorageInRecordDomain> selectStorageInRecord(Date startDate,
			Date endDate) {
		List<StorageInRecordDomain> storageInRecordList = new ArrayList<StorageInRecordDomain>();
		storageInRecordList = pensionStorageinRecordMapper
				.selectStorageinRecord(startDate, endDate);
		return storageInRecordList;
	}

	/**
	 * 查询物资明细
	 * 
	 * @param recordId
	 * @return
	 */
	public List<StorageInDetailDomain> selectStorageInDetail(Long recordId) {
		List<StorageInDetailDomain> detailList = new ArrayList<StorageInDetailDomain>();
		detailList = pensionStorageinDetailMapper
				.selectStorageinDetail(recordId);
		return detailList;
	}

	/**
	 * 查询入库物资明细最大ID
	 * 
	 * @return
	 */
	public Long selectMaxDetailId() {
		PensionStorageinDetailExample example = new PensionStorageinDetailExample();
		example.or();
		example.setOrderByClause("id desc");
		List<PensionStorageinDetail> detailList = pensionStorageinDetailMapper
				.selectByExample(example);
		Long detailId;
		if (detailList.size() != 0) {
			detailId = detailList.get(0).getId();
		} else {
			detailId = 0L;
		}
		return detailId;
	}

	/**
	 * 生成入库单号
	 */
	public String selectStorageinNo() {
		String storageinDate = DateUtil.format(new Date(), "yyyyMMdd");
		long randomNo = (long) (Math.random() * 9000 + 1000);
		String storageinNo = storageinDate + Long.valueOf(randomNo);
		return storageinNo;
	}

	/**
	 * 保存入库主记录及明细
	 * 
	 * @param record
	 * @param detailList
	 */
	@Transactional
	public void insertStorageinRecord(StorageInRecordDomain record,
			List<StorageInDetailDomain> detailList) {
		try {
			// 主记录
			pensionStorageinRecordMapper.insertSelective(record);
			for (StorageInDetailDomain domain : detailList) {
				domain.setRecordId(record.getId());
				domain.setCleared(2);
				pensionStorageinDetailMapper.insert(domain);
				// 更新库存
				PensionStorageExample example = new PensionStorageExample();
				example.or().andItemIdEqualTo(domain.getItemId())
						.andTypeIdEqualTo(domain.getTypeId())
						.andUnitEqualTo(domain.getUnit());
				List<PensionStorage> storageList = pensionStorageMapper
						.selectByExample(example);
				if (storageList.size() != 0) {
					PensionStorage storage = storageList.get(0);
					storage.setStorageQty(storage.getStorageQty()
							+ domain.getInQty());
					storage.setExpireDate(domain.getExpireDate());
					storage.setPurchasePrice(domain.getPurchasePrice());
					pensionStorageMapper.updateByPrimaryKeySelective(storage);
				} else {
					PensionStorage storage = new PensionStorage();
					storage.setExpireDate(domain.getExpireDate());
					storage.setItemId(domain.getItemId());
					storage.setItemName(domain.getItemName());
					storage.setPurchasePrice(domain.getPurchasePrice());
					storage.setStorageQty(domain.getInQty().floatValue());
					storage.setTypeId(domain.getTypeId());
					storage.setTypeName(domain.getTypeName());
					storage.setUnit(domain.getUnit());
					pensionStorageMapper.insert(storage);
				}
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 更新入库记录及明细
	 * 
	 * @param record
	 * @param detailList
	 */
	@Transactional
	public void updateStorageinRecord(StorageInRecordDomain record,
			List<StorageInDetailDomain> detailList) {
		try {
			// 更新主记录
			pensionStorageinRecordMapper.updateByPrimaryKeySelective(record);
			// 更新明细
			PensionStorageinDetailExample example = new PensionStorageinDetailExample();
			example.or().andRecordIdEqualTo(record.getId());
			List<PensionStorageinDetail> oldDetailList = pensionStorageinDetailMapper
					.selectByExample(example);

			PensionStorageExample storageExample = new PensionStorageExample();
			for (PensionStorageinDetail detail : oldDetailList) {
				storageExample.or().andItemIdEqualTo(detail.getItemId())
						.andTypeIdEqualTo(detail.getTypeId())
						.andUnitEqualTo(detail.getUnit());
				PensionStorage storageRecord = pensionStorageMapper
						.selectByExample(storageExample).get(0);
				storageRecord.setStorageQty(storageRecord.getStorageQty()
						- detail.getInQty());
				pensionStorageMapper.updateByPrimaryKeySelective(storageRecord);
				storageExample.clear();
			}

			pensionStorageinDetailMapper.deleteByExample(example);

			for (StorageInDetailDomain temp : detailList) {
				temp.setRecordId(record.getId());
				pensionStorageinDetailMapper.insert(temp);

				storageExample.or().andItemIdEqualTo(temp.getItemId())
						.andTypeIdEqualTo(temp.getTypeId())
						.andUnitEqualTo(temp.getUnit());
				List<PensionStorage> storageList = pensionStorageMapper
						.selectByExample(storageExample);
				if (storageList.size() != 0) {
					PensionStorage storage = storageList.get(0);
					storage.setStorageQty(storage.getStorageQty()
							+ temp.getInQty());
					storage.setExpireDate(temp.getExpireDate());
					storage.setPurchasePrice(temp.getPurchasePrice());
					pensionStorageMapper.updateByPrimaryKeySelective(storage);
				} else {
					PensionStorage storage = new PensionStorage();
					storage.setExpireDate(temp.getExpireDate());
					storage.setItemId(temp.getItemId());
					storage.setItemName(temp.getItemName());
					storage.setPurchasePrice(temp.getPurchasePrice());
					storage.setStorageQty(temp.getInQty().floatValue());
					storage.setTypeId(temp.getTypeId());
					storage.setTypeName(temp.getTypeName());
					storage.setUnit(temp.getUnit());
					pensionStorageMapper.insert(storage);
				}
				storageExample.clear();
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
			System.out.print(ex.getMessage());
		}
	}

	/**
	 * 记账
	 * 
	 * @param record
	 */
	public void bankStorageinRecord(StorageInRecordDomain record,
			List<StorageInDetailDomain> detailList) {
		try {
			for (StorageInDetailDomain detail : detailList) {
				PensionStorageExample example = new PensionStorageExample();
				example.or().andItemIdEqualTo(detail.getItemId())
						.andTypeIdEqualTo(detail.getTypeId());
				List<PensionStorage> storageList = pensionStorageMapper
						.selectByExample(example);
				if (storageList.size() != 0) {
					PensionStorage storage = storageList.get(0);
					storage.setStorageQty(storage.getStorageQty()
							+ detail.getInQty());
					storage.setExpireDate(detail.getExpireDate());
					storage.setPurchasePrice(detail.getPurchasePrice());
					pensionStorageMapper.updateByPrimaryKeySelective(storage);
				} else {
					PensionStorage storage = new PensionStorage();
					storage.setExpireDate(detail.getExpireDate());
					storage.setItemId(detail.getItemId());
					storage.setItemName(detail.getItemName());
					storage.setPurchasePrice(detail.getPurchasePrice());
					storage.setStorageQty(detail.getInQty().floatValue());
					storage.setTypeId(detail.getTypeId());
					storage.setTypeName(detail.getTypeName());
					pensionStorageMapper.insert(storage);
				}
			}
			// 更新主记录
			pensionStorageinRecordMapper.updateByPrimaryKeySelective(record);
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 删除入库记录及明细
	 */
	@Transactional
	public void deleteStorageinRecord(StorageInRecordDomain record) {
		try {
			// 将入库主记录清除标记置为1
			pensionStorageinRecordMapper.updateByPrimaryKeySelective(record);
			// 将明细清除标记置为1
			List<StorageInDetailDomain> detailList = pensionStorageinDetailMapper
					.selectStorageinDetail(record.getId());
			for (StorageInDetailDomain temp : detailList) {
				temp.setCleared(1);
				pensionStorageinDetailMapper.updateByPrimaryKeySelective(temp);

				PensionStorageExample example = new PensionStorageExample();
				example.or().andItemIdEqualTo(temp.getItemId())
						.andTypeIdEqualTo(temp.getTypeId())
						.andUnitEqualTo(temp.getUnit());
				List<PensionStorage> storageList = pensionStorageMapper
						.selectByExample(example);
				if (storageList.size() != 0) {
					PensionStorage storage = storageList.get(0);
					storage.setStorageQty(storage.getStorageQty()
							- temp.getInQty());
					storage.setExpireDate(temp.getExpireDate());
					storage.setPurchasePrice(temp.getPurchasePrice());
					pensionStorageMapper.updateByPrimaryKeySelective(storage);
				}
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 查询采购单信息
	 * 
	 * @return
	 */
	public PensionPurchaseRecord selectPurchaseRecord(String purchaseNo) {
		PensionPurchaseRecord record = new PensionPurchaseRecord();
		PensionPurchaseRecordExample example = new PensionPurchaseRecordExample();
		example.or().andPurchaseNoEqualTo(purchaseNo);
		List<PensionPurchaseRecord> recordList = pensionPurchaseRecordMapper
				.selectByExample(example);
		if (recordList.size() != 0 && recordList != null) {
			record = recordList.get(0);
		} else {
			record = null;
		}
		return record;
	}

	/**
	 * 查询采购单明细
	 * 
	 * @return
	 */
	public List<StorageInDetailDomain> selectPurchaseDetail(Long recordId) {
		List<StorageInDetailDomain> detailList = new ArrayList<StorageInDetailDomain>();
		detailList = pensionStorageinDetailMapper
				.selectPurchaseDetail(recordId);
		for (StorageInDetailDomain temp : detailList) {
			temp.setExpireDate(null);
			temp.setPurchasePrice(null);
			temp.setTotalMoney(null);
		}
		return detailList;
	}
}
