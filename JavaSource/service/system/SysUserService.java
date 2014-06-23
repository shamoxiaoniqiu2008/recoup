package service.system;

import java.io.Serializable;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.employee.PensionEmployeeMapper;
import persistence.system.PensionSysUserMapper;
import util.PmsException;

import com.onlyfido.util.MD5Util;
import com.onlyfido.util.logger.HisLogger;

import domain.employee.PensionEmployee;
import domain.employee.PensionEmployeeExample;
import domain.system.PensionSysUser;
import domain.system.PensionSysUserExample;


@Service
public class SysUserService implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO
	* @version V1.0
	*/ 
	
	private static final long serialVersionUID = 1884486107229305186L;

	private static HisLogger<?> logger = HisLogger.getLogger(SysUserService.class);
	
	@Autowired
	private PensionSysUserMapper pensionSysUserMapper;
	
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;
	
	
	
	/**
	 * 插入用户信息表
	 * @param record
	 * @throws PmsException 
	 */
	public void insertSysUser(PensionSysUser record) throws PmsException {
		try {
			pensionSysUserMapper.insert(record);
		} catch (Exception e) {
			throw new PmsException("插入用户表失败");
		}
	}
	
	/**
	 * 查询员工表
	 * @return
	 */
	public List<PensionEmployee> selectPensionEmployee() {
		PensionEmployeeExample example = new PensionEmployeeExample();
		return pensionEmployeeMapper.selectByExample(example );
	}

	/**
	 * 查询用户信息
	 * @return 
	 */
	public List<PensionSysUserDoman> selectSysUserList(Long searchUserId) {
		PensionSysUserExample example = new PensionSysUserExample();
		Integer cleared = 2; //未删除
		if(searchUserId != null && searchUserId.compareTo((long)0) > 0) {
			example.or()
			.andClearedEqualTo(cleared)
			.andIdEqualTo(searchUserId);
		} else {
			example.or()
			.andClearedEqualTo(cleared);
		}
		
		
		return pensionSysUserMapper.selectExtendByExample(example);
	}
	
	/**
	 * 添加用户
	 * @param pensionSysUserDoman
	 * @param password
	 * @throws PmsException 
	 */
	public void insertSysUser(PensionSysUserDoman pensionSysUserDoman, String password) throws PmsException {
		
		if(selectByUserName(pensionSysUserDoman.getUsername())) {
			throw new PmsException("【"+pensionSysUserDoman.getUsername()+"】用户已存在，请勿重复添加");
		}
		try{
			String psw = MD5Util.encodeStr(password);
			pensionSysUserDoman.setPassword(psw);
			PensionSysUser pensionSysUser = pensionSysUserDoman;
			pensionSysUserMapper.insertSelective(pensionSysUser);
			//insert(pensionSysUser);
		} catch(Exception e) {
			throw new PmsException("添加用户失败");
		}
		
	}
	/**
	 *  修改用户
	 * @param pensionSysUserDoman
	 * @param password
	 * @throws PmsException
	 */
	public void updateSysUser(PensionSysUserDoman pensionSysUserDoman, String password) throws PmsException {
		try{
			if(password != null && !password.isEmpty()) {
				String psw = MD5Util.encodeStr(password);
				pensionSysUserDoman.setPassword(psw);
			}
			PensionSysUser pensionSysUser = pensionSysUserDoman;
			pensionSysUserMapper.updateByPrimaryKeySelective(pensionSysUser);
			//insert(pensionSysUser);
		} catch(Exception e) {
			throw new PmsException("修改用户失败");
		}
		
	}
	
	/**
	 * 删除用户
	 * @param pensionSysUserDoman
	 * @throws PmsException
	 */
	public void delSysUser(PensionSysUserDoman pensionSysUserDoman) throws PmsException {
		try{

			PensionSysUser pensionSysUser = new PensionSysUser();
			pensionSysUser.setId(pensionSysUserDoman.getId());
			Integer cleared = 1; //删除
			pensionSysUser.setCleared(cleared );
			pensionSysUserMapper.updateByPrimaryKeySelective(pensionSysUser);
			//insert(pensionSysUser);
		} catch(Exception e) {
			throw new PmsException("删除用户失败");
		}
		
	}
	
	/**
	 * 是否重名
	 * @param userName
	 * @return
	 */
	public boolean selectByUserName(String userName) {
		PensionSysUserExample example = new PensionSysUserExample();
		example.or()
		.andUsernameEqualTo(userName);
		//.andUsernameLike("%"+userName+"%");
		
		List<PensionSysUser> pensionSysUsers = pensionSysUserMapper.selectByExample(example );
		if(pensionSysUsers != null && pensionSysUsers.size() > 0) {
			return true;
		} else {
			return false;
		}
		
	}
}
