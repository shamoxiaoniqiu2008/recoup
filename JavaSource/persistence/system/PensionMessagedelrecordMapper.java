package persistence.system;

import domain.system.PensionMessagedelrecord;
import domain.system.PensionMessagedelrecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionMessagedelrecordMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messagedelrecord
	 * @mbggenerated  Sun Sep 01 12:34:16 CST 2013
	 */
	int countByExample(PensionMessagedelrecordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messagedelrecord
	 * @mbggenerated  Sun Sep 01 12:34:16 CST 2013
	 */
	int deleteByExample(PensionMessagedelrecordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messagedelrecord
	 * @mbggenerated  Sun Sep 01 12:34:16 CST 2013
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messagedelrecord
	 * @mbggenerated  Sun Sep 01 12:34:16 CST 2013
	 */
	int insert(PensionMessagedelrecord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messagedelrecord
	 * @mbggenerated  Sun Sep 01 12:34:16 CST 2013
	 */
	int insertSelective(PensionMessagedelrecord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messagedelrecord
	 * @mbggenerated  Sun Sep 01 12:34:16 CST 2013
	 */
	List<PensionMessagedelrecord> selectByExample(
			PensionMessagedelrecordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messagedelrecord
	 * @mbggenerated  Sun Sep 01 12:34:16 CST 2013
	 */
	PensionMessagedelrecord selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messagedelrecord
	 * @mbggenerated  Sun Sep 01 12:34:16 CST 2013
	 */
	int updateByExampleSelective(
			@Param("record") PensionMessagedelrecord record,
			@Param("example") PensionMessagedelrecordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messagedelrecord
	 * @mbggenerated  Sun Sep 01 12:34:16 CST 2013
	 */
	int updateByExample(@Param("record") PensionMessagedelrecord record,
			@Param("example") PensionMessagedelrecordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messagedelrecord
	 * @mbggenerated  Sun Sep 01 12:34:16 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionMessagedelrecord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messagedelrecord
	 * @mbggenerated  Sun Sep 01 12:34:16 CST 2013
	 */
	int updateByPrimaryKey(PensionMessagedelrecord record);
}