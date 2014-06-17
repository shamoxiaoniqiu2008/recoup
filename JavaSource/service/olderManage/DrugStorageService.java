package service.olderManage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.medicalManage.PensionDrugreceiveRecordMapper;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-15 上午10:59:44
 */
@Service
public class DrugStorageService {
	@Autowired
	private PensionDrugreceiveRecordMapper pensionDrugreceiveRecordMapper;

	/**
	 * 查询即将过期药品
	 * 
	 * @param olderId
	 * @param drugName
	 * @return
	 */
	public List<DrugReceiveDomain> selectDrugRecord(Long olderId,
			String drugName) {
		List<DrugReceiveDomain> drugRecords = new ArrayList<DrugReceiveDomain>();
		drugRecords = pensionDrugreceiveRecordMapper.selectDrugRecord(olderId,
				drugName);
		return drugRecords;
	}

	/**
	 * 查询药物明细
	 * 
	 * @param drugId
	 * @return
	 */
	public List<DrugReceiveDetailDomain> selectDrugDetail(String drugName,
			Long olderId) {
		List<DrugReceiveDetailDomain> detailList = new ArrayList<DrugReceiveDetailDomain>();
		detailList = pensionDrugreceiveRecordMapper.selectDrugDetail(drugName,
				olderId);
		return detailList;
	}

}
