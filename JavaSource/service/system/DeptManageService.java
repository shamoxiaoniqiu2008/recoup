package service.system;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlyfido.util.logger.HisLogger;

import persistence.system.PensionDeptMapper;
import service.system.PensionDeptDoman;
import util.PmsException;


import domain.system.PensionDept;
import domain.system.PensionDeptExample;


@Service
public class DeptManageService {

	private static HisLogger<?> logger = HisLogger.getLogger(DeptManageService.class);
	
	@Autowired
	private PensionDeptMapper pensionDeptMapper;


	/**
	 * 获取部门类别（树）
	 * @param parentId
	 * @return
	 */
	public List<PensionDept> fetchChildSections(Long parentId) {
		
		PensionDeptExample example = new PensionDeptExample();
		example.or()
		.andParentIdEqualTo(parentId)
		.andClearedEqualTo(2);
		example.setOrderByClause("id ASC");
		return pensionDeptMapper.selectByExample(example );
	}
	
	
	/**
	 * 获取部门列表
	 * @param parentId
	 * @return
	 */
	public List<PensionDeptDoman> fetchPensionDeptDomanList(Long parentId){
		
		PensionDeptExample example = new PensionDeptExample();
		example.or()
		.andParentIdEqualTo(parentId)
		.andClearedEqualTo(2);
		example.setOrderByClause("id ASC");
		return pensionDeptMapper.selectExtendByExample(example);
	}
	
	/**
	 * 新建部门
	 * @param pensionDeptDoman
	 * @throws PmsException
	 */
	public void insertDept(PensionDeptDoman pensionDeptDoman) throws PmsException {
		logger.info("新建部门"+pensionDeptDoman.getName());
		try {
			PensionDept pensionDept = pensionDeptDoman;
			int test = pensionDeptMapper.insertSelective(pensionDept);
			System.out.println(test);
			
			logger.info("新建部门"+pensionDeptDoman.getName()+"成功");
		} catch(Exception e) {
			throw new PmsException("新建部门失败");
		}
	}
	
	/**
	 * 修改部门信息
	 * @param pensionDeptDoman
	 * @throws PmsException
	 */
	public void updateDept(PensionDeptDoman pensionDeptDoman) throws PmsException {
		logger.info("修改部门"+pensionDeptDoman.getName());
		try {
			PensionDept pensionDept = pensionDeptDoman;
			pensionDeptMapper.updateByPrimaryKey(pensionDept);
			logger.info("修改部门"+pensionDeptDoman.getName()+"成功");
		} catch(Exception e) {
			throw new PmsException("修改部门失败");
		}
	}
	
	/**
	 * 根据名称查询
	 * @param itemname
	 * @return
	 */
	public List<PensionDept> selectByName(String name) {
		PensionDeptExample example = new PensionDeptExample();
		example.or()
		.andNameEqualTo(name)
		.andClearedEqualTo(2);
		
		return pensionDeptMapper.selectByExample(example);
	}
	
	/**
	 * 根据主键查询
	 * @param itemname
	 * @return
	 */
	public List<PensionDept> selectById(List<Long> values) {
		PensionDeptExample example = new PensionDeptExample();
		example.or()
		.andIdIn(values)
		.andClearedEqualTo(2);
		
		return pensionDeptMapper.selectByExample(example);
	}
	
	/**
	 * 删除部门
	 * @param pensionRole
	 * @throws PmsException
	 */
	public void updateDeptForDel(PensionDeptDoman pensionDeptDoman) throws PmsException {
		PensionDept pensionDept = new PensionDept();
		pensionDept.setId(pensionDeptDoman.getId());
		pensionDept.setCleared(1);
		logger.info("删除部门"+pensionDeptDoman.getName());
		try {
			pensionDeptMapper.updateByPrimaryKeySelective(pensionDept);
			logger.info("删除部门"+pensionDeptDoman.getName()+"成功");
		} catch(Exception e) {
			throw new PmsException("删除部门失败");
		}
	}

}
