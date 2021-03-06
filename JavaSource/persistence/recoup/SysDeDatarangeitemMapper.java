package persistence.recoup;

import domain.recoup.SysDeDatarangeitem;
import domain.recoup.SysDeDatarangeitemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysDeDatarangeitemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_de_datarangeitem
     *
     * @mbggenerated Fri Jul 04 09:34:37 CST 2014
     */
    int countByExample(SysDeDatarangeitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_de_datarangeitem
     *
     * @mbggenerated Fri Jul 04 09:34:37 CST 2014
     */
    int deleteByExample(SysDeDatarangeitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_de_datarangeitem
     *
     * @mbggenerated Fri Jul 04 09:34:37 CST 2014
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_de_datarangeitem
     *
     * @mbggenerated Fri Jul 04 09:34:37 CST 2014
     */
    int insert(SysDeDatarangeitem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_de_datarangeitem
     *
     * @mbggenerated Fri Jul 04 09:34:37 CST 2014
     */
    int insertSelective(SysDeDatarangeitem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_de_datarangeitem
     *
     * @mbggenerated Fri Jul 04 09:34:37 CST 2014
     */
    List<SysDeDatarangeitem> selectByExample(SysDeDatarangeitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_de_datarangeitem
     *
     * @mbggenerated Fri Jul 04 09:34:37 CST 2014
     */
    SysDeDatarangeitem selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_de_datarangeitem
     *
     * @mbggenerated Fri Jul 04 09:34:37 CST 2014
     */
    int updateByExampleSelective(@Param("record") SysDeDatarangeitem record, @Param("example") SysDeDatarangeitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_de_datarangeitem
     *
     * @mbggenerated Fri Jul 04 09:34:37 CST 2014
     */
    int updateByExample(@Param("record") SysDeDatarangeitem record, @Param("example") SysDeDatarangeitemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_de_datarangeitem
     *
     * @mbggenerated Fri Jul 04 09:34:37 CST 2014
     */
    int updateByPrimaryKeySelective(SysDeDatarangeitem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_de_datarangeitem
     *
     * @mbggenerated Fri Jul 04 09:34:37 CST 2014
     */
    int updateByPrimaryKey(SysDeDatarangeitem record);
}