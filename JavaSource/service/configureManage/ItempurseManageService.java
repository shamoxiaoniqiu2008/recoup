package service.configureManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.configureManage.PensionItempurseMapper;
import util.PmsException;

import com.centling.his.util.logger.HisLogger;

import domain.configureManage.PensionItempurse;
import domain.configureManage.PensionItempurseExample;


@Service
public class ItempurseManageService {

	private static HisLogger<?> logger = HisLogger.getLogger(ItempurseManageService.class);
	

	@Autowired
	private PensionItempurseMapper pensionItempurseMapper;


	/**
	 * 查询价表信息
	 * @param id
	 * @return
	 */
	public  List<PensionItempurse> selectItempurseList(Long id) {
		Integer cleared = 2; //未删除
		PensionItempurseExample example = new PensionItempurseExample();
		
		if(id != null && !id.equals((long)0)){
			example.or()
			.andClearedEqualTo(cleared)
			.andIdEqualTo(id);
		} else {
			example.or()
			.andClearedEqualTo(cleared);
		}
		
		return pensionItempurseMapper.selectByExample(example );
	}
	
	
	/**
	 * 新建价表
	 * @param pensionItempurse
	 * @throws PmsException
	 */
	public void insertItempurse(PensionItempurse pensionItempurse) throws PmsException {
		logger.info("新建价表"+pensionItempurse.getItemname());
		try {
			int test = pensionItempurseMapper.insertSelective(pensionItempurse);
			System.out.println(test);
			
			logger.info("新建价表"+pensionItempurse.getItemname()+"成功");
		} catch(Exception e) {
			throw new PmsException("新建价表失败");
		}
	}
	/**
	 * 修改价表信息
	 * @param pensionItempurse
	 * @throws PmsException 
	 */
	public void updateItempurse(PensionItempurse pensionItempurse) throws PmsException {
		logger.info("修改价表"+pensionItempurse.getItemname());
		try {
			pensionItempurseMapper.updateByPrimaryKey(pensionItempurse);
			logger.info("修改价表"+pensionItempurse.getItemname()+"成功");
		} catch(Exception e) {
			throw new PmsException("修改价表失败");
		}
	}
	
	/**
	 * 根据名称查询
	 * @param itemname
	 * @return
	 */
	public List<PensionItempurse> selectByName(String itemname) {
		PensionItempurseExample example = new PensionItempurseExample();
		//PensionRoleExample example = new PensionRoleExample();
		example .or()
		.andItemnameEqualTo(itemname)
		.andClearedEqualTo(2);
		
		return pensionItempurseMapper.selectByExample(example);
	}
	
	/**
	 * 删除价表
	 * @param pensionRole
	 * @throws PmsException
	 */
	public void updateItempurseForDel(PensionItempurse pensionItempurse) throws PmsException {
		PensionItempurse pensionItempurseTemp = new PensionItempurse();
		pensionItempurseTemp.setId(pensionItempurse.getId());
		pensionItempurseTemp.setCleared(1);
		logger.info("删除价表"+pensionItempurse.getItemname());
		try {
			pensionItempurseMapper.updateByPrimaryKeySelective(pensionItempurseTemp);
			logger.info("删除价表"+pensionItempurse.getItemname()+"成功");
		} catch(Exception e) {
			throw new PmsException("删除价表失败");
		}
	}

}
