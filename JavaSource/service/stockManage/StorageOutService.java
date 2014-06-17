/**  
 * @Title: StorageOutService.java 
 * @Package service.stockManage 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:49:05 
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

import persistence.stockManage.PensionStorageMapper;
import persistence.stockManage.PensionStorageoutDetailMapper;
import persistence.stockManage.PensionStorageoutRecordMapper;
import util.DateUtil;
import domain.stockManage.PensionStorage;
import domain.stockManage.PensionStorageExample;
import domain.stockManage.PensionStorageoutDetail;
import domain.stockManage.PensionStorageoutDetailExample;

/**
 * @ClassName: StorageOutService
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-12 下午3:49:05
 * @version V1.0
 */
@Service
public class StorageOutService {

	@Autowired
	private PensionStorageoutDetailMapper pensionStorageoutDetailMapper;
	@Autowired
	private PensionStorageoutRecordMapper pensionStorageoutRecordMapper;
	@Autowired
	private PensionStorageMapper pensionStorageMapper;

	/**
	 * 查询出库记录
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<StorageOutRecordDomain> selectStorageOutRecord(Date startDate,
			Date endDate) {
		List<StorageOutRecordDomain> storageOutRecordList = new ArrayList<StorageOutRecordDomain>();
		storageOutRecordList = pensionStorageoutRecordMapper
				.selectStorageOutRecord(startDate, endDate);
		return storageOutRecordList;
	}

	/**
	 * 查询物资明细
	 * 
	 * @param recordId
	 * @return
	 */
	public List<StorageOutDetailDomain> selectStorageOutDetail(Long recordId) {
		List<StorageOutDetailDomain> detailList = new ArrayList<StorageOutDetailDomain>();
		detailList = pensionStorageoutDetailMapper
				.selectStorageOutDetail(recordId);
		return detailList;
	}

	/**
	 * 生成出库单号
	 */
	public String selectStoragoutNo() {
		String storageoutDate = DateUtil.format(new Date(), "yyyyMMdd");
		long randomNo = (long) (Math.random() * 9000 + 1000);
		String storageoutNo = storageoutDate + Long.valueOf(randomNo);
		return storageoutNo;
	}

	/**
	 * 保存出库主记录及明细
	 * 
	 * @param record
	 * @param detailList
	 */
	@Transactional
	public void insertStorageoutRecord(StorageOutRecordDomain record,
			List<StorageOutDetailDomain> detailList) {
		try {
			// 主记录
			pensionStorageoutRecordMapper.insertSelective(record);

			PensionStorageExample example;
			for (StorageOutDetailDomain domain : detailList) {
				domain.setRecordId(record.getId());
				domain.setCleared(2);
				pensionStorageoutDetailMapper.insert(domain);
				// 更新库存
				example = new PensionStorageExample();
				example.or().andItemIdEqualTo(domain.getItemId())
						.andTypeIdEqualTo(domain.getTypeId())
						.andUnitEqualTo(domain.getUnit());
				PensionStorage storage = pensionStorageMapper.selectByExample(
						example).get(0);
				if (storage.getStorageQty() - domain.getOutQty() < 0) {
					domain.setOutQty(storage.getStorageQty().intValue());
					pensionStorageoutDetailMapper
							.updateByPrimaryKeySelective(domain);
					storage.setStorageQty(0.0f);
				} else {
					storage.setStorageQty(storage.getStorageQty()
							- domain.getOutQty());
				}
				pensionStorageMapper.updateByPrimaryKeySelective(storage);
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 更新出库记录及明细
	 * 
	 * @param record
	 * @param detailList
	 */
	@Transactional
	public void updateStorageoutRecord(StorageOutRecordDomain record,
			List<StorageOutDetailDomain> detailList) {
		try {
			// 更新主记录
			pensionStorageoutRecordMapper.updateByPrimaryKeySelective(record);

			PensionStorageoutDetailExample example = new PensionStorageoutDetailExample();
			example.or().andRecordIdEqualTo(record.getId());
			List<PensionStorageoutDetail> oldDetailList = pensionStorageoutDetailMapper
					.selectByExample(example);

			PensionStorageExample storageExample = new PensionStorageExample();
			for (PensionStorageoutDetail detail : oldDetailList) {
				storageExample.or().andItemIdEqualTo(detail.getItemId())
						.andTypeIdEqualTo(detail.getTypeId())
						.andUnitEqualTo(detail.getUnit());
				PensionStorage storageRecord = pensionStorageMapper
						.selectByExample(storageExample).get(0);
				storageRecord.setStorageQty(storageRecord.getStorageQty()
						+ detail.getOutQty());
				pensionStorageMapper.updateByPrimaryKeySelective(storageRecord);
				storageExample.clear();
			}
			// 更新明细
			pensionStorageoutDetailMapper.deleteByExample(example);

			for (StorageOutDetailDomain temp : detailList) {
				temp.setRecordId(record.getId());
				temp.setCleared(2);
				pensionStorageoutDetailMapper.insert(temp);

				storageExample.or().andItemIdEqualTo(temp.getItemId())
						.andTypeIdEqualTo(temp.getTypeId())
						.andUnitEqualTo(temp.getUnit());
				PensionStorage storage = pensionStorageMapper.selectByExample(
						storageExample).get(0);
				if (storage.getStorageQty() - temp.getOutQty() < 0) {
					temp.setOutQty(storage.getStorageQty().intValue());
					pensionStorageoutDetailMapper
							.updateByPrimaryKeySelective(temp);
					storage.setStorageQty(0.0f);
				} else {
					storage.setStorageQty(storage.getStorageQty()
							- temp.getOutQty());
				}
				pensionStorageMapper.updateByPrimaryKeySelective(storage);
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
	@Transactional
	public void bankStorageoutRecord(StorageOutRecordDomain record,
			List<StorageOutDetailDomain> detailList) {
		try {
			// 更新库存
			for (StorageOutDetailDomain detail : detailList) {
				PensionStorageExample example = new PensionStorageExample();
				example.or().andItemIdEqualTo(detail.getItemId())
						.andTypeIdEqualTo(detail.getTypeId());
				PensionStorage storage = pensionStorageMapper.selectByExample(
						example).get(0);
				if (storage.getStorageQty() - detail.getOutQty() < 0) {
					detail.setOutQty(storage.getStorageQty().intValue());
					pensionStorageoutDetailMapper
							.updateByPrimaryKeySelective(detail);
					storage.setStorageQty(0.0f);
					pensionStorageMapper.updateByPrimaryKeySelective(storage);
				}
			}
			// 更新主记录
			pensionStorageoutRecordMapper.updateByPrimaryKeySelective(record);
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
			System.out.print(ex.getMessage());
		}
	}

	/**
	 * 删除出库记录及明细
	 */
	@Transactional
	public void deleteStorageoutRecord(StorageOutRecordDomain record) {
		try {
			// 将出库主记录清除标记置为1
			pensionStorageoutRecordMapper.updateByPrimaryKeySelective(record);
			// 将明细清除标记置为1
			List<StorageOutDetailDomain> detailList = pensionStorageoutDetailMapper
					.selectStorageOutDetail(record.getId());
			for (StorageOutDetailDomain temp : detailList) {
				temp.setCleared(1);
				pensionStorageoutDetailMapper.updateByPrimaryKeySelective(temp);

				PensionStorageExample example = new PensionStorageExample();
				example.or().andItemIdEqualTo(temp.getItemId())
						.andTypeIdEqualTo(temp.getTypeId())
						.andUnitEqualTo(temp.getUnit());
				PensionStorage storage = pensionStorageMapper.selectByExample(
						example).get(0);

				storage.setStorageQty(storage.getStorageQty()
						+ temp.getOutQty());
				pensionStorageMapper.updateByPrimaryKeySelective(storage);
			}
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
		}
	}
}
