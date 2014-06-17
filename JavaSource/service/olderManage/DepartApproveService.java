package service.olderManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.olderManage.PensionDepartapproveMapper;
import persistence.olderManage.PensionOlderMapper;
import domain.olderManage.PensionDepartapprove;
import domain.olderManage.PensionDepartapproveExample;
import domain.olderManage.PensionOlder;

/**
 * 离院审批
 * 
 * @author bill.zhang
 * 
 * 
 */
@Service
public class DepartApproveService {

	@Autowired
	private PensionDepartapproveMapper departapproveMapper;

	@Autowired
	private PensionOlderMapper pensionOlderMapper;

	public void updateOlderStatus(Long olderId, Integer status) {
		PensionOlder pensionOlder = new PensionOlder();
		pensionOlder.setId(olderId);
		pensionOlder.setStatuses(status);
		pensionOlderMapper.updateByPrimaryKeySelective(pensionOlder);
	}

	/**
	 * 离院申请插入
	 */
	public void insertDepartApproveRecord(PensionDepartapprove departapprove) {
		departapproveMapper.insertSelective(departapprove);
	}

	/**
	 * 离院申请更新
	 */
	public void updateDepartApproveRecord(PensionDepartapprove departapprove) {
		PensionDepartapproveExample example = new PensionDepartapproveExample();
		example.or().andDepartIdEqualTo(departapprove.getDepartId())
				.andDeptIdEqualTo(departapprove.getDeptId())
				.andClearedEqualTo(2);
		departapproveMapper.updateByExampleSelective(departapprove, example);
	}

	/**
	 * 离院查询
	 */
	public List<PensionDepartapprove> selectDepartApprove(long depart_id) {
		return departapproveMapper.selectDepartApprove(depart_id);
	}

	/**
	 * 查询单条记录
	 */
	public List<PensionDepartapprove> selectByPrimaryKey(long id) {
		PensionDepartapproveExample example = new PensionDepartapproveExample();
		example.or().andDepartIdEqualTo(id).andClearedEqualTo(2);
		return departapproveMapper.selectByExample(example);
	}

}
