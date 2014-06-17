package service.system;

import java.io.Serializable;

import domain.system.PensionMessages;

public class MessageDoman extends PensionMessages implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String messagTypeName;
/*	private String deptName;
	private String userName;*/
	public String getMessagTypeName() {
		return messagTypeName;
	}
	public void setMessagTypeName(String messagTypeName) {
		this.messagTypeName = messagTypeName;
	}
/*	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}*/
	
	
}
