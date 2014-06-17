package service.configureManage;

import domain.system.PensionDept;

public class PensionDeptDoman extends PensionDept {
	
	private String managerName;

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerName() {
		return managerName;
	}

}
