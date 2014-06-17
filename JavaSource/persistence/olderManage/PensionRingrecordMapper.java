package persistence.olderManage;

import domain.olderManage.PensionDosage;
import domain.olderManage.PensionRingrecord;
import domain.olderManage.PensionRingrecordExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionRingrecordMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_ringrecord
	 * @mbggenerated  Sun Oct 06 14:52:26 CST 2013
	 */
	int countByExample(PensionRingrecordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_ringrecord
	 * @mbggenerated  Sun Oct 06 14:52:26 CST 2013
	 */
	int deleteByExample(PensionRingrecordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_ringrecord
	 * @mbggenerated  Sun Oct 06 14:52:26 CST 2013
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_ringrecord
	 * @mbggenerated  Sun Oct 06 14:52:26 CST 2013
	 */
	int insert(PensionRingrecord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_ringrecord
	 * @mbggenerated  Sun Oct 06 14:52:26 CST 2013
	 */
	int insertSelective(PensionRingrecord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_ringrecord
	 * @mbggenerated  Sun Oct 06 14:52:26 CST 2013
	 */
	List<PensionRingrecord> selectByExample(PensionRingrecordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_ringrecord
	 * @mbggenerated  Sun Oct 06 14:52:26 CST 2013
	 */
	PensionRingrecord selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_ringrecord
	 * @mbggenerated  Sun Oct 06 14:52:26 CST 2013
	 */
	int updateByExampleSelective(@Param("record") PensionRingrecord record,
			@Param("example") PensionRingrecordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_ringrecord
	 * @mbggenerated  Sun Oct 06 14:52:26 CST 2013
	 */
	int updateByExample(@Param("record") PensionRingrecord record,
			@Param("example") PensionRingrecordExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_ringrecord
	 * @mbggenerated  Sun Oct 06 14:52:26 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionRingrecord record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_ringrecord
	 * @mbggenerated  Sun Oct 06 14:52:26 CST 2013
	 */
	int updateByPrimaryKey(PensionRingrecord record);

	/**
	 * 按条件查询发药记录
	 */
	List<PensionRingrecord> selectRingRecords(@Param("startDate")Date startDate, @Param("endDate")Date endDate,@Param("ringstatus")Integer ringstatus);

}