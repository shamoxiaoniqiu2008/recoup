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
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import service.system.PensionSysUserDoman;
import service.system.SysUserService;
import util.PmsException;
import util.Spell;

/**
 * Created by JBoss Tools
 */
@ViewScoped
public class SysUserController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String passWord;
	
	
	private List<PensionSysUserDoman> pensionSysUserDomanList;
	
	private SysUserService sysUserService;
	private PensionSysUserDoman pensionSysUserDomanNow;
	private PensionSysUserDoman pensionSysUserDomanSel; //选中
	private String userPassword;
	
	public String searchUserName;
	public Long searchUserId;
	
	private String testStr;
	private String testStr1;
	//private  List<UserInfo> allUser;
	
	

	@PostConstruct
	public void  init() {
		//allUser = userManageService.findAllUser();
		System.out.println("加载成功");
		searchSysUser();
		
		//testStr ="8888888888888888888888888888888888888888888888888888888888888888888888";
	}


	//添加按钮
	public void showAddForm() {
		pensionSysUserDomanNow  = new PensionSysUserDoman();
		userPassword = null;
	}
	
	//编辑按钮
	public void showEditForm() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if(pensionSysUserDomanSel == null || pensionSysUserDomanSel.getId() == null) {
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请选择要修改/删除的用户" , ""));
			request.addCallbackParam("edit", false);
			return;
		}
		pensionSysUserDomanNow  = pensionSysUserDomanSel;
		request.addCallbackParam("edit", true);
	}
	
/*	public void selOneRow(SelectEvent event) {
		// 修改、删除按钮状态	
		
	}

	public void cancelOneRow(UnselectEvent event) {
		pensionSysUserDomanSel = null;
	}*/
	//查询用户信息
	public void searchSysUser() {
		if(searchUserName == null || searchUserName.isEmpty()) {
			searchUserId = null;
		}
		pensionSysUserDomanList = sysUserService.selectSysUserList(searchUserId) ;
	}
	
	//删除用户
	
	public void delSysUser() {
		if(pensionSysUserDomanNow == null || pensionSysUserDomanNow.getId() == null) {
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请选择要删除的用户" , ""));
			return;
		}

		try {
			sysUserService.delSysUser(pensionSysUserDomanNow);
			searchSysUser();
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
	//保存用户
	public void saveSysUser() {
	
		if(pensionSysUserDomanNow.getEmployeeId() == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请选择对应员工" , ""));
			return;
		}
		
		if(pensionSysUserDomanNow.getRoleId() == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请选择权限" , ""));
			return;
		}
		final RequestContext request = RequestContext.getCurrentInstance();
		try {
			String info;
			if(pensionSysUserDomanNow.getId() == null) {
				sysUserService.insertSysUser(pensionSysUserDomanNow, userPassword);
				request.addCallbackParam("close", false);
				info = "添加用户成功";
			} else {
				sysUserService.updateSysUser(pensionSysUserDomanNow, userPassword);
				request.addCallbackParam("close", true);
				info = "修改用户信息成功";
			}
			
			searchSysUser();
			userPassword = "";
			showAddForm();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							info , ""));
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage() , ""));
			e.printStackTrace();
		}
	}
	
	//获取首字母
	public void readPinYin() {
		String pinyin = Spell.getFirstSpell(pensionSysUserDomanNow.getLoginname());
		pensionSysUserDomanNow.setInputcode(pinyin);
	}
	public void asdf() {
		sysUserService.selectPensionEmployee();
	}

	public void testFun() {
		testStr = "test1";
		testStr1 = "test2";
	}
	


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String sayHello() {
		return "greeting";
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}

	public String getTestStr() {
		return testStr;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public SysUserService getSysUserService() {
		return sysUserService;
	}

	public void setTestStr1(String testStr1) {
		this.testStr1 = testStr1;
	}


	public String getTestStr1() {
		return testStr1;
	}

	public PensionSysUserDoman getPensionSysUserDomanNow() {
		return pensionSysUserDomanNow;
	}


	public void setPensionSysUserDomanNow(PensionSysUserDoman pensionSysUserDomanNow) {
		this.pensionSysUserDomanNow = pensionSysUserDomanNow;
	}


	public List<PensionSysUserDoman> getPensionSysUserDomanList() {
		return pensionSysUserDomanList;
	}


	public void setPensionSysUserDomanList(
			List<PensionSysUserDoman> pensionSysUserDomanList) {
		this.pensionSysUserDomanList = pensionSysUserDomanList;
	}


	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}


	public String getUserPassword() {
		return userPassword;
	}


	public void setPensionSysUserDomanSel(PensionSysUserDoman pensionSysUserDomanSel) {
		this.pensionSysUserDomanSel = pensionSysUserDomanSel;
	}


	public PensionSysUserDoman getPensionSysUserDomanSel() {
		return pensionSysUserDomanSel;
	}


	public String getSearchUserName() {
		return searchUserName;
	}


	public void setSearchUserName(String searchUserName) {
		this.searchUserName = searchUserName;
	}


	public Long getSearchUserId() {
		return searchUserId;
	}


	public void setSearchUserId(Long searchUserId) {
		this.searchUserId = searchUserId;
	}



}