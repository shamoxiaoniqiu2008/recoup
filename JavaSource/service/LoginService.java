package service;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centling.his.util.MD5Util;
import com.centling.his.util.logger.HisLogger;

import persistence.UserInfoMapper;
import persistence.employeeManage.PensionEmployeeMapper;
import persistence.system.PensionSysUserMapper;

import util.JavaConfig;
import util.Spell;
import domain.employeeManage.PensionEmployee;
import domain.system.PensionSysUser;
import domain.system.PensionSysUserExample;

@Service
public class LoginService {
	
	@Autowired
	private PensionSysUserMapper pensionSysUserMapper;
	
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;
	
	private static HisLogger<?> logger = HisLogger.getLogger(LoginService.class);
	/**
	 * 用户登录验证
	 * @return
	 */
	public List<PensionSysUser> userLogin(String userName, String password){
		
		logger.info("logger-info-生效");
		logger.debug("logger-debug-生效");//info("logger生效");
		String psw = MD5Util.encodeStr(password);
		//System.out.println(psw);
		/*System.out.println(Spell.getFirstLetter("I love 北京天安门"));*/
		//////////////////////////////////////////////////////
		
		
		/*String asd = JavaConfig.fetchProperty("login.sucess");
		System.out.println(asd);
		
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						asd , ""));*/
		
		//////////////////////////////////////////////////////////
		/*String hzString = "音乐";  
		System.out.println(Spell.getFirstSpell(hzString)); */ 
		
		
		PensionSysUserExample example = new PensionSysUserExample();
		example.or()
		.andUsernameEqualTo(userName)
		.andPasswordEqualTo(psw)
		.andLockedEqualTo(2);
		
	
		logger.info("logger结束");
		return pensionSysUserMapper.selectByExample(example);
	}
	
	/**
	 * 获取用户对应的员工信息
	 * @param id
	 * @return
	 */
	public PensionEmployee selectPensionEmployee(Long id) {
		return pensionEmployeeMapper.selectByPrimaryKey(id);
	}
	
}
