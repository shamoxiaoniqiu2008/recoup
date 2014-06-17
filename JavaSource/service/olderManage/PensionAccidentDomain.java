package service.olderManage;

import domain.olderManage.PensionAccidentRecord;

public class PensionAccidentDomain extends PensionAccidentRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  accidenttypeName;
	public void setAccidenttypeName(String accidenttypeName) {
		this.accidenttypeName = accidenttypeName;
	}
	public String getAccidenttypeName() {
		return accidenttypeName;
	}

}
