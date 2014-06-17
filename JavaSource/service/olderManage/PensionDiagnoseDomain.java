package service.olderManage;

import domain.olderManage.PensionDiagnosis;


/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-9-16 上午10:16:44
 */
public class PensionDiagnoseDomain extends PensionDiagnosis {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String olderName;
	private String recorderName;

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setRecorderName(String recorderName) {
		this.recorderName = recorderName;
	}

	public String getRecorderName() {
		return recorderName;
	}
	
}
