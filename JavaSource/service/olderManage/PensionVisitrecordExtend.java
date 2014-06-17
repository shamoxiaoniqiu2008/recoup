package service.olderManage;


import domain.olderManage.PensionVisitrecord;

public class PensionVisitrecordExtend extends PensionVisitrecord {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 探访者姓名
	 */
	private String visitorName;
	/**
	 * 探访者部门名字
	 */
	private String deptName;
	/**
	 * 停留时间
	 */
	private String stopTime;

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptName() {
		return deptName;
	}

	public String getStopTime() {

		if(getLeavetime()!=null&&getVisittime()!=null){
			Long stopTime = getLeavetime().getTime()-getVisittime().getTime();
			return stopTime/(60*60*1000)+"小时"+stopTime/(60*1000)%60+"分钟";
		}
		return "";
		
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}
}
