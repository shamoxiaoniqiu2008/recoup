package service.system;

import domain.system.PensionRoleMenu;

public class PensionRoleMenuDoman extends PensionRoleMenu {

	private String menuName;
	private boolean roleFlag;
	private Short roleLevel;
	private String menuBaseId;

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuName() {
		return menuName;
	}


	public void setRoleLevel(Short roleLevel) {
		this.roleLevel = roleLevel;
	}

	public Short getRoleLevel() {
		return roleLevel;
	}

	public String getMenuBaseId() {
		return menuBaseId;
	}

	public void setMenuBaseId(String menuBaseId) {
		this.menuBaseId = menuBaseId;
	}

	public void setRoleFlag(boolean roleFlag) {
		this.roleFlag = roleFlag;
	}

	public boolean isRoleFlag() {
		return roleFlag;
	}


}
