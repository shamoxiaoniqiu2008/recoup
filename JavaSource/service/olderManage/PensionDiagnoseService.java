package service.olderManage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.olderManage.PensionDiagnosisMapper;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class PensionDiagnoseService {
	@Autowired
	private PensionDiagnosisMapper pensionDiagnosisMapper;

	public String testFun() {
		return "test-Return";
	}

	/**
	 * 查询所有的老人入住巡诊记录
	 * 
	 * @return
	 */
	public List<PensionDiagnoseDomain> selectDiagnoseRecords(Long olderId,
			Date startDate, Date endDate) {
		return pensionDiagnosisMapper.selectDiagnoseRecords(olderId, startDate,
				endDate);
	}

	/**
	 * 插入一条巡诊记录
	 * 
	 * @param record
	 */
	@Transactional
	public void insertDiagnose(PensionDiagnoseDomain record) {
		pensionDiagnosisMapper.insertSelective(record);
	}

	/**
	 * 更新一条巡诊记录
	 * 
	 * @param record
	 */
	@Transactional
	public void updateDiagnose(PensionDiagnoseDomain record) {
		pensionDiagnosisMapper.updateByPrimaryKey(record);
	}

	/**
	 * 修改巡诊记录清除标记
	 * 
	 * @param record
	 */
	@Transactional
	public void deleteDiagnose(PensionDiagnoseDomain record) {
		record.setCleared(1);
		pensionDiagnosisMapper.updateByPrimaryKeySelective(record);
	}

}
