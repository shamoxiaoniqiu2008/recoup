package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.medicalManage.PensionDrugreceiveRecordMapper;
import util.DateUtil;
import util.PmsException;
import util.SystemConfig;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-15 上午10:59:44
 */
@Service
public class DrugNoticeService {
	@Autowired
	private PensionDrugreceiveRecordMapper pensionDrugreceiveRecordMapper;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 查询即将过期药品
	 * 
	 * @param olderId
	 * @param drugName
	 * @return
	 */
	public List<DrugReceiveDomain> selectOverDulDrugRecord(Long olderId,
			String drugName) {
		List<DrugReceiveDomain> overDulDrugRecords = new ArrayList<DrugReceiveDomain>();
		int overDulDay = 0;
		try {
			try {
				overDulDay = Integer.valueOf(systemConfig
						.selectProperty("DRUG_OVERDUL_DAY"));
			} catch (PmsException e) {
				e.printStackTrace();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		Date noticeDate = DateUtil.addDate(new Date(), overDulDay);
		overDulDrugRecords = pensionDrugreceiveRecordMapper
				.selectOverDulDrugRecord(olderId, drugName, noticeDate);
		return overDulDrugRecords;
	}

	/**
	 * 查询药量不足药品
	 * 
	 * @param olderId
	 * @param drugName
	 * @return
	 */
	public List<DrugReceiveDomain> selectShortageDrugRecord(Long olderId,
			String drugName) {
		List<DrugReceiveDomain> AllDrugRecords = pensionDrugreceiveRecordMapper
				.selectShortageDrugRecord(olderId, drugName);
		int drugNum = 0;
		try {
			drugNum = Integer.valueOf(systemConfig
					.selectProperty("DRUG_SHORTAGE_QUATITY"));
		} catch (PmsException e) {
			e.printStackTrace();
		}
		
		List<DrugReceiveDomain> shortageDrugRecords=new ArrayList<DrugReceiveDomain>();
		for (DrugReceiveDomain drug:AllDrugRecords) {
			int dayCount = 0;
			if (drug.getMorningFlag()) {
				dayCount += 1;
			}
			if (drug.getNoonFlag()) {
				dayCount += 1;
			}
			if (drug.getNightFlag()) {
				dayCount += 1;
			}
			float minCount = drug.getSingleDose() * dayCount * drugNum;
			if (minCount >drug.getTotalAmount()) {
				shortageDrugRecords.add(drug);
			}
		}
		return shortageDrugRecords;
	}
}
