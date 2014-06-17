package service.olderManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.olderManage.PensionDepartapproveMapper;
import persistence.olderManage.PensionDepartnotifyMapper;
import persistence.olderManage.PensionOlderMapper;
import domain.olderManage.PensionDepartapprove;
import domain.olderManage.PensionDepartapproveExample;
import domain.olderManage.PensionDepartnotify;
import domain.olderManage.PensionOlder;

/**
 * 离院审批
 * 
 * @author bill.zhang
 * 
 * 
 */
@Service
public class DepartApprovenofityService {
	
	@Autowired
	private PensionDepartnotifyMapper departnotifyMapper;


	/**
	 * 插入离院通知
	 */
	public void insertDepartApproveRecord(PensionDepartnotify departnotify)
	{
		departnotifyMapper.insertSelective(departnotify);
	}
	/**
	 * 更新离院通知
	 */
	public void updateDepartApproveRecord(PensionDepartnotify departnotify)
	{
		departnotifyMapper.updateByPrimaryKeySelective(departnotify);
	}
	/**
	 * 离院通知查询
	 */
	public List<PensionDepartnotify> selectDepartNotify(Long depart_id,Long dept_id)
	{
		return departnotifyMapper.selectDepartNotify(depart_id,dept_id);
	}
//	/**
//	 * 查询单条记录
//	 */
//	public List<PensionDepartapprove> selectByPrimaryKey(long id)
//	{
//		PensionDepartapproveExample example = new PensionDepartapproveExample();
//		example.or().andDepartIdEqualTo(id).andClearedEqualTo(2);
//		return departapproveMapper.selectByExample(example);
//	}
//	

}
