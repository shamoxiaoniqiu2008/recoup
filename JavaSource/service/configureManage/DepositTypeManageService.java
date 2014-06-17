package service.configureManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.dictionary.PensionDicDeposittypeMapper;
import domain.dictionary.PensionDicDeposittype;
import domain.dictionary.PensionDicDeposittypeExample;


@Service
public class DepositTypeManageService {
	@Autowired
	private PensionDicDeposittypeMapper pensionDicDeposittypeMapper;
	/**
	 * 查询押金类别信息
	 * @param type
	 * @return
	 */
	public List<PensionDicDeposittype> selectTypeRecords(String type) {
		PensionDicDeposittypeExample example = new PensionDicDeposittypeExample();
		example.or().andTypeEqualTo(type).andInvalidedEqualTo(1);
		return pensionDicDeposittypeMapper.selectByExample(example);
	}
	/**
	 * 新增押金类别信息
	 * @param insertedRow
	 */
	public void insertTypeRecord(PensionDicDeposittype insertedRow) {
		pensionDicDeposittypeMapper.insertSelective(insertedRow);
	}
	/**
	 * 删除押金类别信息
	 * @param selectedRow
	 */
	public void deleteTypeRecord(PensionDicDeposittype selectedRow) {
		selectedRow.setInvalided(2);
		pensionDicDeposittypeMapper.updateByPrimaryKeySelective(selectedRow);
	}
	/**
	 * 修改押金类别信息
	 * @param updatedRow
	 */
	public void updateTypeRecord(PensionDicDeposittype updatedRow) {
		pensionDicDeposittypeMapper.updateByPrimaryKeySelective(updatedRow);
	}
	
	public void setPensionDicDeposittypeMapper(
			PensionDicDeposittypeMapper pensionDicDeposittypeMapper) {
		this.pensionDicDeposittypeMapper = pensionDicDeposittypeMapper;
	}

	public PensionDicDeposittypeMapper getPensionDicDeposittypeMapper() {
		return pensionDicDeposittypeMapper;
	}
}
