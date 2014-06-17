package service.fingerPrint;

import domain.fingerPrint.PensionFingerprintReg;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */

public class FingerRegDomain extends PensionFingerprintReg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String employeeName;//现在已经添加类型了所以应该把它改为employeeName由于现在繁忙 还不用改
	private String astrSerialNo;
	private String roomName;

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomName() {
		return roomName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getAstrSerialNo() {
		return astrSerialNo;
	}

	public void setAstrSerialNo(String astrSerialNo) {
		this.astrSerialNo = astrSerialNo;
	}
}