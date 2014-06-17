package service.olderManage;

import domain.medicalManage.PensionDicDrugreceive;

public class DrugDicDomain extends PensionDicDrugreceive {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean morningF;
	private boolean noonF;
	private boolean nightF;
	private boolean eatTimeF;
	private boolean validF;

	public boolean isMorningF() {
		return morningF;
	}

	public void setMorningF(boolean morningF) {
		this.morningF = morningF;
	}

	public boolean isNoonF() {
		return noonF;
	}

	public void setNoonF(boolean noonF) {
		this.noonF = noonF;
	}

	public boolean isNightF() {
		return nightF;
	}

	public void setNightF(boolean nightF) {
		this.nightF = nightF;
	}

	public boolean isEatTimeF() {
		return eatTimeF;
	}

	public void setEatTimeF(boolean eatTimeF) {
		this.eatTimeF = eatTimeF;
	}

	public void setValidF(boolean validF) {
		this.validF = validF;
	}

	public boolean getValidF() {
		return validF;
	}

}
