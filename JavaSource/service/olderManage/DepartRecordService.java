package service.olderManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.olderManage.PensionDepartapproveMapper;
import persistence.olderManage.PensionDepartrecordMapper;
import persistence.olderManage.PensionOlderMapper;
import domain.olderManage.PensionDepartapprove;
import domain.olderManage.PensionDepartapproveExample;
import domain.olderManage.PensionDepartrecord;
import domain.olderManage.PensionOlder;

/**
 * 离院计费
 * 
 * @author bill.zhang
 * 
 * 
 */
@Service
public class DepartRecordService {
	
	@Autowired
	private PensionDepartrecordMapper departrecordMapper;



	/**
	 * 离院计费申请插入
	 */
	public void insertDepartApproveRecord(PensionDepartrecord departrecord)
	{
		departrecordMapper.insertSelective(departrecord);
	}
	

}
