package service.olderManage;

import domain.nurseManage.PensionDaycare;

public class DayCareDomain extends PensionDaycare {

	private static final long serialVersionUID = 1L;
	// 是否执行
	private boolean executeF;

	public boolean isExecuteF() {
		return executeF;
	}

	public void setExecuteF(boolean executeF) {
		this.executeF = executeF;
	}
}
