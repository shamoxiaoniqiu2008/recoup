package service.fingerPrint;

import domain.fingerPrint.PensionFingerprint;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */

public class FingerPrintDomain extends PensionFingerprint {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String roomName;

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getRoomName() {
		return roomName;
	}
}