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
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import com.onlyfido.util.SessionManager;

import domain.system.PensionRole;
import domain.system.PensionSysUser;

import service.system.PensionRoleMenuDoman;
import service.system.PensionSysUserDoman;
import service.system.RoleManageService;
import service.system.UserPasswordService;
import util.PmsException;
import util.Spell;


/**
 * Created by JBoss Tools
 */

public class RoleManageController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient RoleManageService roleManageService;
	
	private List<PensionRole> pensionRoleList;
	private PensionRole pensionRole;
	private PensionRole pensionRoleNow;
	private List<PensionRoleMenuDoman> pensionRoleMenuDomanList;
	
	private String testStr;
	
	@PostConstruct
	public void  init() {
		//allUser = userManageService.findAllUser();
		//System.out.println("加载成功");
		fetchRole();
		if(pensionRoleList != null && pensionRoleList.size() > 0) {
			pensionRole = pensionRoleList.get(0);
			pensionRoleNow = pensionRole;
			fetchselectPensionRoles();
		}
	}

	public void fetchRole() {
		pensionRoleList = roleManageService.selectRoleList();
	}
	
	//获取首字母
	public void readPinYin() {
		String pinyin = Spell.getFirstSpell(pensionRoleNow.getName());
		pensionRoleNow.setInputcode(pinyin);
	}
	
	//添加按钮
	public void showAddForm() {
		pensionRoleNow  = new PensionRole();
	}
	
	public void showSelectOneForm() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if(pensionRole == null || pensionRole.getId() == null) {
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请选择要修改/删除的权限" , ""));
			request.addCallbackParam("edit", false);
			return;
		}
		pensionRoleNow  = pensionRole;
		request.addCallbackParam("selected", true);
	}
	
	//保存数据
	public void saveRole() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if(pensionRoleNow.getName() == null || pensionRoleNow.getName().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请输入名称" , ""));
			request.addCallbackParam("close", false);
			return ;
		}
		try {
			List<PensionRole> tempPensionRoles = roleManageService.selectByName(pensionRoleNow.getName());
			if(pensionRoleNow.getId() == null){
				
				if(tempPensionRoles != null && tempPensionRoles.size()>0) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"【"+pensionRoleNow.getName()+"】已存在" , ""));
					request.addCallbackParam("close", false);
					return;
				} else {
					roleManageService.insertRole(pensionRoleNow);
				}
				
				
				request.addCallbackParam("close", false);
			} else {
				if(tempPensionRoles != null && tempPensionRoles.size()>0 && !tempPensionRoles.get(0).getId().equals(pensionRoleNow.getId())) {
					
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"【"+pensionRoleNow.getName()+"】已存在" , ""));
					fetchRole();
					request.addCallbackParam("close", false);
					return;
					
				} else {
					roleManageService.updateRole(pensionRoleNow);
					request.addCallbackParam("close", true);
				}
			}
			fetchRole();
			showAddForm();
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void delRole() {
		if(pensionRoleNow == null || pensionRoleNow.getId() == null) {
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请选择要删除的权限" , ""));
			return;
		}

		try {
			roleManageService.updateRoleForDel(pensionRoleNow);
			fetchRole();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"删除成功" , ""));
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage() , ""));
			e.printStackTrace();
		}
	}
	
	/**
	 * 选中
	 * @param event
	 */
	public void selectOneRole(SelectEvent event) {
		 
		pensionRoleNow = pensionRole;
		fetchselectPensionRoles();
	}
	
	/**
	 * 取消选中
	 * @param event
	 */
	public void unselectOneRole(UnselectEvent event) {
		pensionRoleNow = pensionRole = new PensionRole(); 
		pensionRoleMenuDomanList = new ArrayList<PensionRoleMenuDoman>();	 
	}
	
	/**
	 * 获取权限
	 */
	public void fetchselectPensionRoles() {
		if(pensionRoleNow.getId() == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请先选择某个权限" , ""));
		} else {
			pensionRoleMenuDomanList = roleManageService.selectPensionRoleMenuList(pensionRoleNow.getId());
		}
	}
	
	
	/**
	 * 保存权限对应菜单
	 */
	public void saveRoleMenu() {

		if(pensionRoleNow == null || pensionRoleNow.getId() == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请先选择某个权限" , ""));
			return;
		} 
		try {
			roleManageService.insertRoleMenu(pensionRoleMenuDomanList, pensionRoleNow);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"保存成功" , ""));
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		}
		
	}
	
	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}

	public String getTestStr() {
		return testStr;
	}

	public void setRoleManageService(RoleManageService roleManageService) {
		this.roleManageService = roleManageService;
	}

	public RoleManageService getRoleManageService() {
		return roleManageService;
	}

	public List<PensionRole> getPensionRoleList() {
		return pensionRoleList;
	}

	public void setPensionRoleList(List<PensionRole> pensionRoleList) {
		this.pensionRoleList = pensionRoleList;
	}

	public PensionRole getPensionRole() {
		return pensionRole;
	}

	public void setPensionRole(PensionRole pensionRole) {
		this.pensionRole = pensionRole;
	}

	public PensionRole getPensionRoleNow() {
		return pensionRoleNow;
	}

	public void setPensionRoleNow(PensionRole pensionRoleNow) {
		this.pensionRoleNow = pensionRoleNow;
	}

	public void setPensionRoleMenuDomanList(List<PensionRoleMenuDoman> pensionRoleMenuDomanList) {
		this.pensionRoleMenuDomanList = pensionRoleMenuDomanList;
	}

	public List<PensionRoleMenuDoman> getPensionRoleMenuDomanList() {
		return pensionRoleMenuDomanList;
	}



	



}