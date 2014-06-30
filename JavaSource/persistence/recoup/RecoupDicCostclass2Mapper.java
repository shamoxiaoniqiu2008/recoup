package persistence.recoup;

import domain.recoup.RecoupDicCostclass2;
import domain.recoup.RecoupDicCostclass2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecoupDicCostclass2Mapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass2
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    int countByExample(RecoupDicCostclass2Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass2
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    int deleteByExample(RecoupDicCostclass2Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass2
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass2
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    int insert(RecoupDicCostclass2 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass2
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    int insertSelective(RecoupDicCostclass2 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass2
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    List<RecoupDicCostclass2> selectByExample(RecoupDicCostclass2Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass2
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    RecoupDicCostclass2 selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass2
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    int updateByExampleSelective(@Param("record") RecoupDicCostclass2 record, @Param("example") RecoupDicCostclass2Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass2
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    int updateByExample(@Param("record") RecoupDicCostclass2 record, @Param("example") RecoupDicCostclass2Example example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass2
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    int updateByPrimaryKeySelective(RecoupDicCostclass2 record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_dic_costclass2
     *
     * @mbggenerated Mon Jun 30 22:06:17 CST 2014
     */
    int updateByPrimaryKey(RecoupDicCostclass2 record);
}