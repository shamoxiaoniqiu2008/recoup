package service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.system.PensionSysUserMapper;
import util.PmsException;

import com.centling.his.util.MD5Util;
import com.centling.his.util.logger.HisLogger;


import domain.system.PensionSysUser;



@Service
public class UserPasswordService {

	private static HisLogger<?> logger = HisLogger.getLogger(UserPasswordService.class);
	
	@Autowired
	private PensionSysUserMapper pensionSysUserMapper;
	

	/**
	 * 修改密码
	 * @param id
	 * @param password
	 * @throws PmsException
	 */
	public void updateSysUser(Long id, String password) throws PmsException {
		PensionSysUser pensionSysUser = new PensionSysUser();
		pensionSysUser.setId(id);
		logger.info(pensionSysUser.getUsername() + " 修改密码 ");
		try{
			if(password != null && !password.isEmpty()) {
				String psw = MD5Util.encodeStr(password);
				pensionSysUser.setPassword(psw);
			}
			pensionSysUserMapper.updateByPrimaryKeySelective(pensionSysUser);
			logger.info(pensionSysUser.getUsername() + " 修改密码成功 ");
			//insert(pensionSysUser);
		} catch(Exception e) {
			logger.info(pensionSysUser.getUsername() + " 修改密码失败 ");
			throw new PmsException("修改用户失败");
		}	
	}
}
