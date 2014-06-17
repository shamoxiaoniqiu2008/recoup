package service.olderManage;

import org.springframework.stereotype.Service;

import domain.dictionary.PensionFamily;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class PensionFamilyDomain extends PensionFamily {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String relationShipStr;
	
	private Boolean defaultFamily;

	public void setRelationShipStr(String relationShipStr) {
		this.relationShipStr = relationShipStr;
	}

	public String getRelationShipStr() {
		return relationShipStr;
	}

	public void setDefaultFamily(Boolean defaultFamily) {
		this.defaultFamily = defaultFamily;
	}

	public Boolean getDefaultFamily() {
		return defaultFamily;
	}
	
}
