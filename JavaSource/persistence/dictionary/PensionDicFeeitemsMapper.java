package persistence.dictionary;

import domain.dictionary.PensionDicFeeitems;
import domain.dictionary.PensionDicFeeitemsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionDicFeeitemsMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_feeitems
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	int countByExample(PensionDicFeeitemsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_feeitems
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	int deleteByExample(PensionDicFeeitemsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_feeitems
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_feeitems
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	int insert(PensionDicFeeitems record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_feeitems
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	int insertSelective(PensionDicFeeitems record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_feeitems
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	List<PensionDicFeeitems> selectByExample(PensionDicFeeitemsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_feeitems
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	PensionDicFeeitems selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_feeitems
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	int updateByExampleSelective(@Param("record") PensionDicFeeitems record,
			@Param("example") PensionDicFeeitemsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_feeitems
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	int updateByExample(@Param("record") PensionDicFeeitems record,
			@Param("example") PensionDicFeeitemsExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_feeitems
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionDicFeeitems record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_feeitems
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	int updateByPrimaryKey(PensionDicFeeitems record);
}