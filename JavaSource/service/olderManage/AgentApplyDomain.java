package service.olderManage;

import domain.receptionManage.PensionAgentApply;

public class AgentApplyDomain extends PensionAgentApply{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer evaluation;

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	public Integer getEvaluation() {
		return evaluation;
	}
	
}
