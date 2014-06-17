package service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.system.PensionRoleMapper;
import persistence.system.PensionRoleMenuMapper;
import util.PmsException;

import com.centling.his.util.logger.HisLogger;

import domain.system.PensionRole;
import domain.system.PensionRoleExample;
import domain.system.PensionRoleMenu;
import domain.system.PensionRoleMenuExample;


@Service
public class RoleManageService {

	private static HisLogger<?> logger = HisLogger.getLogger(RoleManageService.class);
	
	
	@Autowired
	private PensionRoleMapper pensionRoleMapper;
	
	
	@Autowired
	private PensionRoleMenuMapper pensionRoleMenuMapper;

	//查询角色表
	public List<PensionRole> selectRoleList() {
		PensionRoleExample example = new PensionRoleExample();
		example.or()
		.andClearedEqualTo(2);
		
		 return pensionRoleMapper.selectByExample(example);
	}
	
	/**
	 * 添加新角色
	 * @param pensionRole
	 * @throws PmsException
	 */
	public void insertRole(PensionRole pensionRole) throws PmsException {
		logger.info("新建权限"+pensionRole.getName());
		try {
			int test = pensionRoleMapper.insertSelective(pensionRole);
			System.out.println(test);
			
			logger.info("新建权限"+pensionRole.getName()+"成功");
		} catch(Exception e) {
			throw new PmsException("新建权限失败");
		}
	}
	
	/**
	 * 修改权限信息
	 * @param pensionRole
	 * @throws PmsException 
	 */
	public void updateRole(PensionRole pensionRole) throws PmsException {
		logger.info("修改权限"+pensionRole.getName());
		try {
			pensionRoleMapper.updateByPrimaryKey(pensionRole);
			logger.info("修改权限"+pensionRole.getName()+"成功");
		} catch(Exception e) {
			throw new PmsException("修改权限失败");
		}
	}
	
	/**
	 * 删除权限
	 * @param pensionRole
	 * @throws PmsException
	 */
	public void updateRoleForDel(PensionRole pensionRole) throws PmsException {
		PensionRole pensionRoleTemp = new PensionRole();
		pensionRoleTemp.setId(pensionRole.getId());
		pensionRoleTemp.setCleared(1);
		logger.info("删除权限"+pensionRole.getName());
		try {
			pensionRoleMapper.updateByPrimaryKeySelective(pensionRoleTemp);
			logger.info("删除权限"+pensionRole.getName()+"成功");
		} catch(Exception e) {
			throw new PmsException("删除权限失败");
		}
	}
	
	/**
	 * 根据名称查询
	 * @param roleName
	 * @return
	 */
	public List<PensionRole> selectByName(String roleName) {
		PensionRoleExample example = new PensionRoleExample();
		example.or()
		.andNameEqualTo(roleName)
		.andClearedEqualTo(2);
		
		return pensionRoleMapper.selectByExample(example );
	}
	
	/**
	 * 查询菜单
	 * @return
	 */
	public List<PensionRoleMenuDoman> selectPensionRoleMenuList(Long roleId) {
		PensionRoleMenuExample example = new PensionRoleMenuExample();
		
		example.or()
		.andRoleIdEqualTo(roleId);
		
		return pensionRoleMenuMapper.selectExtendByExample(example);
		
	}
	
	/**
	 * 修改权限菜单
	 * @param pensionRoleMenuDomanList
	 * @param pensionRole
	 * @throws PmsException 
	 */
	public void insertRoleMenu(List<PensionRoleMenuDoman> pensionRoleMenuDomanList, 
			PensionRole pensionRole) throws PmsException {
		for(PensionRoleMenuDoman pensionRoleMenuDoman : pensionRoleMenuDomanList) {
			try{
				//添加
				if(pensionRoleMenuDoman.isRoleFlag() && pensionRoleMenuDoman.getId() == null) {
					PensionRoleMenu pensionRoleMenu = pensionRoleMenuDoman;
					pensionRoleMenu.setMenuId(pensionRoleMenuDoman.getMenuBaseId());
					pensionRoleMenu.setRoleId(pensionRole.getId());
					pensionRoleMenuMapper.insertSelective(pensionRoleMenu);
				}
				//删除
				if(!pensionRoleMenuDoman.isRoleFlag() && pensionRoleMenuDoman.getId() != null) {
					pensionRoleMenuMapper.deleteByPrimaryKey(pensionRoleMenuDoman.getId());
				}
			} catch(Exception e) {
				throw new PmsException("权限设置失败");
			}
		}
	}
	
}
