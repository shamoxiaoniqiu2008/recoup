package service.system;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.UserInfoMapper;
import persistence.system.PensionMenuMapper;
import service.LoginService;
import service.system.PensionMenus;

import com.onlyfido.util.SessionManager;
import com.onlyfido.util.logger.HisLogger;

import domain.system.PensionMenu;
import domain.system.PensionMenuExample;
import domain.system.PensionSysUser;




@Service
public class MainService {

	private static HisLogger<?> logger = HisLogger.getLogger(UserInfoMapper.class);
	
/*	@Autowired
	private PensionSysUserMapper pensionSysUserMapper;*/
	
	@Autowired
	private PensionMenuMapper pensionMenuMapper;
	
	@Autowired
	private LoginService loginService;
	/**
	 * 获取菜单
	 * @param level
	 * @return
	 */
	public List<PensionMenu> selectMenuList(Integer level, String parentId) {
		PensionMenuExample example = new PensionMenuExample();
		example.or()
		.andLevelsEqualTo(level)
		.andParentIdEqualTo(parentId);
		
		String orderByClause = " sort asc ";
		example.setOrderByClause(orderByClause );
		return pensionMenuMapper.selectByExample(example );
	}

	/**
	 * 获取两级菜单
	 */
	public List<PensionMenus> selectMenus() {
		List<PensionMenu> pensionMenuFist = selectMenuList(1,"0");
		List<PensionMenus> meuns = new ArrayList<PensionMenus>();
		//PensionMenus
		
		for( PensionMenu pensionMenu : pensionMenuFist) {
			PensionMenus pensionMenus = new PensionMenus();
			//List<PensionMenu> pensionMenuSecond = selectMenuList(2,pensionMenu.getId());
			List<PensionMenu> pensionMenuSecond = selectMenuListByRole(pensionMenu.getId());
			for(PensionMenu pMenu : pensionMenuSecond){
				String ioc = "";
				String toUrl = "";
				if(pMenu.getUrl() != null && !pMenu.getUrl().isEmpty()){
					
					String tempUrl = "";
					if(pMenu.getUrl().lastIndexOf("/") >= 0){
						tempUrl = pMenu.getUrl().substring(pMenu.getUrl().lastIndexOf("/"));
					} else {
						tempUrl = pMenu.getUrl();
					}
					ioc = tempUrl.substring(0,tempUrl.lastIndexOf("."))+".png";
					//连接其他系统
					if(pMenu.getUrl().lastIndexOf("http") >= 0 || pMenu.getUrl().lastIndexOf("HTTP") >= 0 ) {
						toUrl = pMenu.getUrl();
					} else {
						toUrl = pensionMenu.getUrl()+"/"+pMenu.getUrl();
					}
				}
				pMenu.setIoc(ioc);
				pMenu.setToUrl(toUrl);
			}

			//pensionMenuSecond.add(0,pensionMenu);
			pensionMenus.setPensionMenuFirst(pensionMenu);
			pensionMenus.setPensionMenusList(pensionMenuSecond);
			
			
			meuns.add(pensionMenus);
		}
		return meuns;
	}
	
	
	/**
	 * 获取权限菜单
	 * @param parentId
	 * @return
	 */
	public List<PensionMenu> selectMenuListByRole(String parentId) {
		Long roleId = SessionManager.getCurUser().getRoleId();
		return pensionMenuMapper.selectExtendByExample(parentId, roleId);
	}
	
	/**
	 * 解锁登录
	 * @param password
	 */
	public List<PensionSysUser> unlock(String password) {
		String userName = ((PensionSysUser) SessionManager.getCurUser()).getUsername();
		return loginService.userLogin(userName , password);
	}
	/**
	 * 测试函数
	 * @return
	 */
	public String testFun() {
		return "123456789";
	}
	


}
