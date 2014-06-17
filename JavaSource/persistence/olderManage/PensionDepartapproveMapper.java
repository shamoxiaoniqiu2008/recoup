package persistence.olderManage;

import domain.olderManage.PensionDepartapprove;
import domain.olderManage.PensionDepartapproveExample;
import domain.olderManage.PensionDepartregister;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionDepartapproveMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departapprove
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	int countByExample(PensionDepartapproveExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departapprove
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	int deleteByExample(PensionDepartapproveExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departapprove
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departapprove
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	int insert(PensionDepartapprove record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departapprove
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	int insertSelective(PensionDepartapprove record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departapprove
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	List<PensionDepartapprove> selectByExample(
			PensionDepartapproveExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departapprove
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	PensionDepartapprove selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departapprove
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	int updateByExampleSelective(@Param("record") PensionDepartapprove record,
			@Param("example") PensionDepartapproveExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departapprove
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	int updateByExample(@Param("record") PensionDepartapprove record,
			@Param("example") PensionDepartapproveExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departapprove
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionDepartapprove record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departapprove
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	int updateByPrimaryKey(PensionDepartapprove record);
	
	List<PensionDepartapprove> selectDepartApprove(@Param("departId")Long departId);
}