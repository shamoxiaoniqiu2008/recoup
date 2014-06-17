package persistence.dictionary;

import domain.dictionary.PensionDicAccidenttype;
import domain.dictionary.PensionDicAccidenttypeExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionDicAccidenttypeMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_accidenttype
	 * @mbggenerated  Fri Nov 15 09:07:08 CST 2013
	 */
	int countByExample(PensionDicAccidenttypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_accidenttype
	 * @mbggenerated  Fri Nov 15 09:07:08 CST 2013
	 */
	int deleteByExample(PensionDicAccidenttypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_accidenttype
	 * @mbggenerated  Fri Nov 15 09:07:08 CST 2013
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_accidenttype
	 * @mbggenerated  Fri Nov 15 09:07:08 CST 2013
	 */
	int insert(PensionDicAccidenttype record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_accidenttype
	 * @mbggenerated  Fri Nov 15 09:07:08 CST 2013
	 */
	int insertSelective(PensionDicAccidenttype record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_accidenttype
	 * @mbggenerated  Fri Nov 15 09:07:08 CST 2013
	 */
	List<PensionDicAccidenttype> selectByExample(
			PensionDicAccidenttypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_accidenttype
	 * @mbggenerated  Fri Nov 15 09:07:08 CST 2013
	 */
	PensionDicAccidenttype selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_accidenttype
	 * @mbggenerated  Fri Nov 15 09:07:08 CST 2013
	 */
	int updateByExampleSelective(
			@Param("record") PensionDicAccidenttype record,
			@Param("example") PensionDicAccidenttypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_accidenttype
	 * @mbggenerated  Fri Nov 15 09:07:08 CST 2013
	 */
	int updateByExample(@Param("record") PensionDicAccidenttype record,
			@Param("example") PensionDicAccidenttypeExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_accidenttype
	 * @mbggenerated  Fri Nov 15 09:07:08 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionDicAccidenttype record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_accidenttype
	 * @mbggenerated  Fri Nov 15 09:07:08 CST 2013
	 */
	int updateByPrimaryKey(PensionDicAccidenttype record);
	//生成该类型事故的老人数目
	List<PensionDicAccidenttype> selectDicAccidentTypes(@Param("startDate")Date startDate, @Param("endDate")Date endDate);
}