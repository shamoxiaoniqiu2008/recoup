package persistence.dictionary;

import domain.dictionary.PensionDicDegree;
import domain.dictionary.PensionDicDegreeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionDicDegreeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dic_degree
     *
     * @mbggenerated Thu Dec 05 13:20:52 CST 2013
     */
    int countByExample(PensionDicDegreeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dic_degree
     *
     * @mbggenerated Thu Dec 05 13:20:52 CST 2013
     */
    int deleteByExample(PensionDicDegreeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dic_degree
     *
     * @mbggenerated Thu Dec 05 13:20:52 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dic_degree
     *
     * @mbggenerated Thu Dec 05 13:20:52 CST 2013
     */
    int insert(PensionDicDegree record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dic_degree
     *
     * @mbggenerated Thu Dec 05 13:20:52 CST 2013
     */
    int insertSelective(PensionDicDegree record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dic_degree
     *
     * @mbggenerated Thu Dec 05 13:20:52 CST 2013
     */
    List<PensionDicDegree> selectByExample(PensionDicDegreeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dic_degree
     *
     * @mbggenerated Thu Dec 05 13:20:52 CST 2013
     */
    PensionDicDegree selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dic_degree
     *
     * @mbggenerated Thu Dec 05 13:20:52 CST 2013
     */
    int updateByExampleSelective(@Param("record") PensionDicDegree record, @Param("example") PensionDicDegreeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dic_degree
     *
     * @mbggenerated Thu Dec 05 13:20:52 CST 2013
     */
    int updateByExample(@Param("record") PensionDicDegree record, @Param("example") PensionDicDegreeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dic_degree
     *
     * @mbggenerated Thu Dec 05 13:20:52 CST 2013
     */
    int updateByPrimaryKeySelective(PensionDicDegree record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_dic_degree
     *
     * @mbggenerated Thu Dec 05 13:20:52 CST 2013
     */
    int updateByPrimaryKey(PensionDicDegree record);
}