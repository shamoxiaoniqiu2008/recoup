package service.olderManage;

import domain.medicalManage.PensionDrugreceiveDetail;

public class DrugReceiveDetailDomain extends PensionDrugreceiveDetail {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String drugreceiveName;
	private boolean morningF;
	private boolean noonF;
	private boolean nightF;
	private boolean eatTimeF;
	private boolean inUseF;
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

	public boolean isInUseF() {
		return inUseF;
	}

	public void setInUseF(boolean inUseF) {
		this.inUseF = inUseF;
	}

	public boolean isValidF() {
		return validF;
	}

	public void setValidF(boolean validF) {
		this.validF = validF;
	}

	public void setDrugreceiveName(String drugreceiveName) {
		this.drugreceiveName = drugreceiveName;
	}

	public String getDrugreceiveName() {
		return drugreceiveName;
	}

}
