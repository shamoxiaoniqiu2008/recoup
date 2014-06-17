package service.olderManage;


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
public class PensionRecureManagerService {
	
	@Autowired
	PensionRecureDetailMapper pensionRecureDetailMapper;
	
	@Autowired
	PensionOlderRecureMainMapper pensionOlderRecureMainMapper;
	
	@Autowired
	PensionOlderRecureMapper pensionOlderRecureMapper;

	
	/**
	 * 更新计划
	 * @param olderRecureDetailList
	 * @throws PmsException
	 */
	public void updatePensionOlderRecure(PensionOlderRecureMainExtend olderRecureMainExtend,
			List<PensionOlderRecureExtend> olderRecureDetailList) throws PmsException {
		
		Integer isHandle = 2; //完成
		for(PensionOlderRecureExtend tempPensionOlderRecureExtend:olderRecureDetailList) {
			PensionOlderRecure record = tempPensionOlderRecureExtend;
			if(tempPensionOlderRecureExtend.getId() != null) {
				try{
					Integer ish = 2;
					if(record.getIshandle().equals(ish) ){
						isHandle = 1;
					}
					pensionOlderRecureMapper.updateByPrimaryKeySelective(record);
					
				} catch(Exception e) {
					throw new PmsException("保存失败");
				}
			}
		}
		
		if(olderRecureMainExtend.getNotes() != null) {
			PensionOlderRecureMain tempPensionOlderRecureMain = olderRecureMainExtend;
			tempPensionOlderRecureMain.setId(olderRecureMainExtend.getId());
			tempPensionOlderRecureMain.setNotes(tempPensionOlderRecureMain.getNotes());
			tempPensionOlderRecureMain.setIshandle(isHandle);
			try {
				pensionOlderRecureMainMapper.updateByPrimaryKeySelective(tempPensionOlderRecureMain);
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
}
