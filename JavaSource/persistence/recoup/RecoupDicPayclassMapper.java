package persistence.recoup;

import domain.recoup.RecoupDicPayclass;
import domain.recoup.RecoupDicPayclassExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecoupDicPayclassMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	int countByExample(RecoupDicPayclassExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	int deleteByExample(RecoupDicPayclassExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	int insert(RecoupDicPayclass record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	int insertSelective(RecoupDicPayclass record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	List<RecoupDicPayclass> selectByExample(RecoupDicPayclassExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	RecoupDicPayclass selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	int updateByExampleSelective(@Param("record") RecoupDicPayclass record,
			@Param("example") RecoupDicPayclassExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	int updateByExample(@Param("record") RecoupDicPayclass record,
			@Param("example") RecoupDicPayclassExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	int updateByPrimaryKeySelective(RecoupDicPayclass record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	int updateByPrimaryKey(RecoupDicPayclass record);
}