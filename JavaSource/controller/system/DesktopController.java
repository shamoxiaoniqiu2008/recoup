/*******************************************************************************
 * Copyright (c) 2010 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package controller.system;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

import com.onlyfido.util.SessionManager;

import domain.system.PensionMenu;
import domain.system.PensionSysUser;

import service.system.MainService;
import service.system.PensionMenus;


/**
 * Created by JBoss Tools
 */
@ViewScoped
public class DesktopController implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String testStr;
	//private  List<UserInfo> allUser;
	private transient MainService mainService;
	
	List<PensionMenus> pensionMenusList;
    private Map<String, String> roleMap;
    
    private Integer activeIndex;//导航页tab索引 add by mary.liu 2014-04-10
	
	@PostConstruct
	public void  init() {
		//allUser = userManageService.findAllUser();
		roleMap = new HashMap<String,String>();
		roleMap.put("12345","dddddddddddddddddd");
		System.out.println("加载成功");
		testStr = mainService.testFun();
		pensionMenusList = mainService.selectMenus();
		for(PensionMenus pensionMenus : pensionMenusList ) {
			if(pensionMenus != null && pensionMenus.getPensionMenusList() != null) {
				for(PensionMenu pensionMenu : pensionMenus.getPensionMenusList()){
					
					if(pensionMenu.getId() == null) {
						System.out.println(pensionMenu);
					}
					if(pensionMenus.getPensionMenuFirst().getUrl() == null) {
						System.out.println(pensionMenus.getPensionMenuFirst());
					}
					if(pensionMenu.getUrl() == null) {
						System.out.println(pensionMenu);
					}
					roleMap.put(pensionMenu.getId(), pensionMenus.getPensionMenuFirst().getUrl() +"/"+ pensionMenu.getUrl()) ;
				}
			}
		}
		//String sss = SessionManager.fectchLock();
		//System.out.println(sss);
	}

	//add by mary.liu 2014-04-10
	public void onTabChange(TabChangeEvent e){
		
	}
	
	public String getTestStr() {
		return testStr;
	}

	public void leaveSystem() {
		final HttpSession session = (HttpSession) FacesContext
		.getCurrentInstance().getExternalContext().getSession(false);
		session.invalidate();
	}

	
	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}


	public MainService getMainService() {
		return mainService;
	}


	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	public List<PensionMenus> getPensionMenusList() {
		return pensionMenusList;
	}

	public void setPensionMenusList(List<PensionMenus> pensionMenusList) {
		this.pensionMenusList = pensionMenusList;
	}


	public void setRoleMap(Map<String, String> roleMap) {
		this.roleMap = roleMap;
	}

	public Map<String, String> getRoleMap() {
		return roleMap;
	}

	public Integer getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(Integer activeIndex) {
		this.activeIndex = activeIndex;
	}




}