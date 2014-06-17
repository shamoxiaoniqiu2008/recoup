package service.olderManage;

import domain.medicalManage.PensionInfusionRecord;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-6 上午10:36:44
 */

public class InfusionRecordDomain extends PensionInfusionRecord {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String olderName;
	
	private Long olderId;

	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}

	public String getOlderName() {
		return olderName;
	}

	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}

	public Long getOlderId() {
		return olderId;
	}

}
