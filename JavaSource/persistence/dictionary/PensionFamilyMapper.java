package persistence.dictionary;

import domain.dictionary.PensionFamily;
import domain.dictionary.PensionFamilyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionFamilyMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_family
	 * @mbggenerated  Thu Oct 24 15:21:43 CST 2013
	 */
	int countByExample(PensionFamilyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_family
	 * @mbggenerated  Thu Oct 24 15:21:43 CST 2013
	 */
	int deleteByExample(PensionFamilyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_family
	 * @mbggenerated  Thu Oct 24 15:21:43 CST 2013
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_family
	 * @mbggenerated  Thu Oct 24 15:21:43 CST 2013
	 */
	int insert(PensionFamily record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_family
	 * @mbggenerated  Thu Oct 24 15:21:43 CST 2013
	 */
	int insertSelective(PensionFamily record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_family
	 * @mbggenerated  Thu Oct 24 15:21:43 CST 2013
	 */
	List<PensionFamily> selectByExample(PensionFamilyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_family
	 * @mbggenerated  Thu Oct 24 15:21:43 CST 2013
	 */
	PensionFamily selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_family
	 * @mbggenerated  Thu Oct 24 15:21:43 CST 2013
	 */
	int updateByExampleSelective(@Param("record") PensionFamily record,
			@Param("example") PensionFamilyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_family
	 * @mbggenerated  Thu Oct 24 15:21:43 CST 2013
	 */
	int updateByExample(@Param("record") PensionFamily record,
			@Param("example") PensionFamilyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_family
	 * @mbggenerated  Thu Oct 24 15:21:43 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionFamily record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_family
	 * @mbggenerated  Thu Oct 24 15:21:43 CST 2013
	 */
	int updateByPrimaryKey(PensionFamily record);
}