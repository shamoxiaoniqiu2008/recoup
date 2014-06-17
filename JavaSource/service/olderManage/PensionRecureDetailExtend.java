package service.olderManage;

import java.util.Date;

import domain.olderManage.PensionRecureDetail;

public class PensionRecureDetailExtend extends PensionRecureDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dutynurseName;
	private String recureitemName;
	private Date starttime;
	private Date endime;
	public String getRecureitemName() {
		return recureitemName;
	}
	public void setRecureitemName(String recureitemName) {
		this.recureitemName = recureitemName;
	}
	public void setDutynurseName(String dutynurseName) {
		this.dutynurseName = dutynurseName;
	}
	public String getDutynurseName() {
		return dutynurseName;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setEndime(Date endime) {
		this.endime = endime;
	}
	public Date getEndime() {
		return endime;
	}
	
}
