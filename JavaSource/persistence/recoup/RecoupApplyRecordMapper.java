package persistence.recoup;

import domain.recoup.RecoupApplyRecord;
import domain.recoup.RecoupApplyRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecoupApplyRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_apply_record
     *
     * @mbggenerated Wed Jun 25 21:47:21 CST 2014
     */
    int countByExample(RecoupApplyRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_apply_record
     *
     * @mbggenerated Wed Jun 25 21:47:21 CST 2014
     */
    int deleteByExample(RecoupApplyRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_apply_record
     *
     * @mbggenerated Wed Jun 25 21:47:21 CST 2014
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_apply_record
     *
     * @mbggenerated Wed Jun 25 21:47:21 CST 2014
     */
    int insert(RecoupApplyRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_apply_record
     *
     * @mbggenerated Wed Jun 25 21:47:21 CST 2014
     */
    int insertSelective(RecoupApplyRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_apply_record
     *
     * @mbggenerated Wed Jun 25 21:47:21 CST 2014
     */
    List<RecoupApplyRecord> selectByExample(RecoupApplyRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_apply_record
     *
     * @mbggenerated Wed Jun 25 21:47:21 CST 2014
     */
    RecoupApplyRecord selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_apply_record
     *
     * @mbggenerated Wed Jun 25 21:47:21 CST 2014
     */
    int updateByExampleSelective(@Param("record") RecoupApplyRecord record, @Param("example") RecoupApplyRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_apply_record
     *
     * @mbggenerated Wed Jun 25 21:47:21 CST 2014
     */
    int updateByExample(@Param("record") RecoupApplyRecord record, @Param("example") RecoupApplyRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_apply_record
     *
     * @mbggenerated Wed Jun 25 21:47:21 CST 2014
     */
    int updateByPrimaryKeySelective(RecoupApplyRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recoup_apply_record
     *
     * @mbggenerated Wed Jun 25 21:47:21 CST 2014
     */
    int updateByPrimaryKey(RecoupApplyRecord record);
}