package service.olderManage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.olderManage.PensionOlderRecure;
import domain.olderManage.PensionOlderRecureExample;
import domain.olderManage.PensionOlderRecureMain;
import domain.olderManage.PensionOlderRecureMainExample;



import persistence.olderManage.PensionOlderRecureMainMapper;
import persistence.olderManage.PensionOlderRecureMapper;
import persistence.olderManage.PensionRecureDetailMapper;
import util.PmsException;

/**
 * 
 * @author:bill
 * @version: 1.0
 * @Date:2013-10-10 上午16:16:44
 */
@Service
public class PensionRecureTaskService {
	
	@Autowired
	PensionRecureDetailMapper pensionRecureDetailMapper;
	
	@Autowired
	PensionOlderRecureMainMapper pensionOlderRecureMainMapper;
	
	@Autowired
	PensionOlderRecureMapper pensionOlderRecureMapper;

	
	/**
	 * 保存康复结果
	 * @param olderRecureDetail
	 * @throws PmsException
	 */
	public void updatePensionOlderRecure(PensionOlderRecureExtend olderRecureDetail) throws PmsException {
		PensionOlderRecure record = olderRecureDetail;
		if(record.getId() != null) {
			try{
				pensionOlderRecureMapper.updateByPrimaryKeySelective(record);
			} catch(Exception e) {
				throw new PmsException("保存失败");
			}
		}
	}
	
	

	/**
	 * 查询明细
	 * @param recureMainId
	 * @return
	 */
	public List<PensionOlderRecureExtend> selectOlderRecureDetail(Long recureMainId) {
		PensionOlderRecureExample example = new PensionOlderRecureExample();
		example.or()
		.andRecureMainIdEqualTo(recureMainId)
		.andClearedEqualTo(2);
		 return pensionOlderRecureMapper.selectExtendByExample(example);
	}
	
	/**
	 * 获取主记录
	 * @param olderId
	 * @param recureitemId
	 * @param handle
	 * @return
	 */
	public List<PensionOlderRecureMainExtend> selectOlderRecureMain(Long olderId, Long recureitemId, Integer handle) {
		PensionOlderRecureMainExample example = new PensionOlderRecureMainExample();
		
		example.or()
		.andOlderIdEqualTo(olderId)
		.andRecureitemIdEqualTo(recureitemId)
		.andClearedEqualTo(2)
		.andIshandleEqualTo(handle);
		
		example.setOrderByClause(" id desc");
		
		return pensionOlderRecureMainMapper.selectExtendByExample(example);
	}
	
	
	
	public List<PensionOlderRecureExtend> selectMyRecureDetail(Long employeeId,Date start, Date end,Integer handle) {
		PensionOlderRecureExample example = new PensionOlderRecureExample();
		
		example.or()
		.andRealnurseEqualTo(employeeId)
		.andPlantimeBetween(start, end)
		.andClearedEqualTo(2)
		.andIshandleEqualTo(handle);
		
		example.setOrderByClause(" planTime ");
		 return pensionOlderRecureMapper.selectExtendByExample(example);
	}
	
	/**
	 * 获取康复信息
	 * @param id
	 * @return
	 */
	public PensionOlderRecureMainExtend selectRecureMain(Long id) {
		PensionOlderRecureMainExample example = new PensionOlderRecureMainExample();
		
		example.or()
		.andIdEqualTo(id);
		
		List<PensionOlderRecureMainExtend> list = pensionOlderRecureMainMapper.selectExtendByExample(example);
		if(list != null && list.size() >0) {
			return list.get(0);
		} else {
			return new PensionOlderRecureMainExtend();
		}
	}
}
