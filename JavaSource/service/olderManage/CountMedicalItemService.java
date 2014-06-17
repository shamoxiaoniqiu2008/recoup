package service.olderManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.olderManage.PensionHospitalizegroupMapper;
import persistence.olderManage.PensionHospitalizeregisterMapper;
import persistence.olderManage.PensionItemcountMapper;

@Service
public class CountMedicalItemService {
	@Autowired
	private PensionItemcountMapper pensionItemcountMapper;
	@Autowired
	private PensionHospitalizegroupMapper pensionHospitalizegroupMapper;
	@Autowired
	private PensionHospitalizeregisterMapper pensionHospitalizeregisterMapper;
	/**
	 * 
	 * 查询被选中行对应的项目统计记录
	 * @param selectedRow
	 * @return
	 */
	public List<PensionItemcountExtend> selectMedicalItemRecord(PensionHospitalizegroupExtend selectedRow) {
		Long groupId = selectedRow.getId();
		return pensionItemcountMapper.selectMedicalItemRecord(groupId);
		
	}
	/**
	 * 
	 * 查询被选中行对应的项目明细记录
	 * @param selectedRow
	 * @return
	 */
	public List<PensionHospitalizeregisterExtend> selectMedicalItemDetails(Long groupId,Long hospitalizetypeId) {
		
		return pensionHospitalizeregisterMapper.selectMedicalItemDetails(groupId,hospitalizetypeId);
	}	
	
	/**
	 * 生成统计单
	 * @param viewedRow
	 * @param itemRecords
	 */
	public void generateItemCountPaper(PensionHospitalizegroupExtend viewedRow, List<PensionItemcountExtend> itemRecords) {
		Long groupId = viewedRow.getId();
		viewedRow.setGenerated(1); 
		pensionHospitalizegroupMapper.updateByPrimaryKeySelective(viewedRow);
		/*for(PensionItemcountExtend item:itemRecords){
			item.setGroupId(groupId);
			
			pensionItemcountMapper.insertSelective(item);
		}*/
	}
	
	public void setPensionItemcountMapper(PensionItemcountMapper pensionItemcountMapper) {
		this.pensionItemcountMapper = pensionItemcountMapper;
	}

	public PensionItemcountMapper getPensionItemcountMapper() {
		return pensionItemcountMapper;
	}

	public void setPensionHospitalizegroupMapper(
			PensionHospitalizegroupMapper pensionHospitalizegroupMapper) {
		this.pensionHospitalizegroupMapper = pensionHospitalizegroupMapper;
	}

	public PensionHospitalizegroupMapper getPensionHospitalizegroupMapper() {
		return pensionHospitalizegroupMapper;
	}
	
	public void setPensionHospitalizeregisterMapper(
			PensionHospitalizeregisterMapper pensionHospitalizeregisterMapper) {
		this.pensionHospitalizeregisterMapper = pensionHospitalizeregisterMapper;
	}
	public PensionHospitalizeregisterMapper getPensionHospitalizeregisterMapper() {
		return pensionHospitalizeregisterMapper;
	}

	
}
