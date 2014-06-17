package persistence.receptionManage;

import domain.receptionManage.PensionAgentApprove;
import domain.receptionManage.PensionAgentApproveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionAgentApproveMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_agent_approve
	 * @mbggenerated  Fri Nov 01 09:10:20 CST 2013
	 */
	int countByExample(PensionAgentApproveExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_agent_approve
	 * @mbggenerated  Fri Nov 01 09:10:20 CST 2013
	 */
	int deleteByExample(PensionAgentApproveExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_agent_approve
	 * @mbggenerated  Fri Nov 01 09:10:20 CST 2013
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_agent_approve
	 * @mbggenerated  Fri Nov 01 09:10:20 CST 2013
	 */
	int insert(PensionAgentApprove record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_agent_approve
	 * @mbggenerated  Fri Nov 01 09:10:20 CST 2013
	 */
	int insertSelective(PensionAgentApprove record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_agent_approve
	 * @mbggenerated  Fri Nov 01 09:10:20 CST 2013
	 */
	List<PensionAgentApprove> selectByExample(PensionAgentApproveExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_agent_approve
	 * @mbggenerated  Fri Nov 01 09:10:20 CST 2013
	 */
	PensionAgentApprove selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_agent_approve
	 * @mbggenerated  Fri Nov 01 09:10:20 CST 2013
	 */
	int updateByExampleSelective(@Param("record") PensionAgentApprove record,
			@Param("example") PensionAgentApproveExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_agent_approve
	 * @mbggenerated  Fri Nov 01 09:10:20 CST 2013
	 */
	int updateByExample(@Param("record") PensionAgentApprove record,
			@Param("example") PensionAgentApproveExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_agent_approve
	 * @mbggenerated  Fri Nov 01 09:10:20 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionAgentApprove record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_agent_approve
	 * @mbggenerated  Fri Nov 01 09:10:20 CST 2013
	 */
	int updateByPrimaryKey(PensionAgentApprove record);

	//查詢各部門意見列表
	List<PensionAgentApprove> selectDeptEvaluations(@Param("applyId") Long applyId);
}