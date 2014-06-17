package service.olderManage;

import domain.olderManage.PensionHospitalizegroup;

public class PensionHospitalizegroupExtend extends PensionHospitalizegroup {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String managerName;

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerName() {
		return managerName;
	}
	
}
