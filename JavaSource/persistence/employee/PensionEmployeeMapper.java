package persistence.employee;

import domain.employee.PensionEmployee;
import domain.employee.PensionEmployeeExample;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionEmployeeMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	int countByExample(PensionEmployeeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	int deleteByExample(PensionEmployeeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	int insert(PensionEmployee record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	int insertSelective(PensionEmployee record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	List<PensionEmployee> selectByExample(PensionEmployeeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	PensionEmployee selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	int updateByExampleSelective(@Param("record") PensionEmployee record,
			@Param("example") PensionEmployeeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	int updateByExample(@Param("record") PensionEmployee record,
			@Param("example") PensionEmployeeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionEmployee record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_employee
	 * @mbggenerated  Mon Nov 25 14:55:01 CST 2013
	 */
	int updateByPrimaryKey(PensionEmployee record);
}