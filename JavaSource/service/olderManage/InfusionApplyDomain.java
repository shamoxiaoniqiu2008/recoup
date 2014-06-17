package service.olderManage;

import org.springframework.stereotype.Service;

import domain.medicalManage.PensionInfusionApply;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class InfusionApplyDomain extends PensionInfusionApply {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean infusion;
	private boolean proof;
	private boolean sealFlag;

	public void setInfusion(boolean infusion) {
		this.infusion = infusion;
	}

	public boolean getInfusion() {
		return infusion;
	}

	public void setProof(boolean proof) {
		this.proof = proof;
	}

	public boolean getProof() {
		return proof;
	}

	public void setSealFlag(boolean sealFlag) {
		this.sealFlag = sealFlag;
	}

	public boolean isSealFlag() {
		return sealFlag;
	}

}
