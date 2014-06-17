package service.system;

import domain.system.PensionSysUser;

public class PensionSysUserDoman extends PensionSysUser{

	private String employeeName;
	private String roleName;
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
