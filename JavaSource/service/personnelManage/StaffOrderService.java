package service.personnelManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.hrManage.PensionStaffArrangeMapper;
import domain.hrManage.PensionStaffArrange;
import domain.hrManage.PensionStaffArrangeExample;

/**
 * @ClassName: CarOrderService
 * @Description: TODO
 * @author bill
 * @date 2013-11-10 上午11:07:21
 * @version V1.0
 */

@Service
public class StaffOrderService {
	@Autowired
	private PensionStaffArrangeMapper pensionStaffArrangeMapper;

	/**
	 * 查询车辆列表
	 * 
	 * @param orderId
	 * @return 车辆列表
	 */
	public List<PensionStaffArrange> selectAllStaffArrangeRecords() {
		PensionStaffArrangeExample example = new PensionStaffArrangeExample();
		example.or().andClearedEqualTo(2);
		return pensionStaffArrangeMapper.selectByExample(example);
	}

	/**
	 * 查询车辆列表
	 * 
	 * @return 车辆列表
	 */
	public List<PensionStaffArrange> selectStaffOrderRecords(Long id) {
		PensionStaffArrangeExample example = new PensionStaffArrangeExample();
		example.or().andDepartIdEqualTo(id);
		return pensionStaffArrangeMapper.selectByExample(example);
	}

	/**
	 * 查询单次预约信息
	 * 
	 * @return 单条信息
	 */
	public PensionStaffArrange selectStaffOrderById(Long id) {
		return pensionStaffArrangeMapper.selectByPrimaryKey(id);
	}
}
