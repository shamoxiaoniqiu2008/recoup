package persistence.phrManage;

import domain.phrManage.PensionOlderillRepository;
import domain.phrManage.PensionOlderillRepositoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionOlderillRepositoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_olderill_repository
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    int countByExample(PensionOlderillRepositoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_olderill_repository
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    int deleteByExample(PensionOlderillRepositoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_olderill_repository
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_olderill_repository
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    int insert(PensionOlderillRepository record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_olderill_repository
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    int insertSelective(PensionOlderillRepository record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_olderill_repository
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    List<PensionOlderillRepository> selectByExample(PensionOlderillRepositoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_olderill_repository
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    PensionOlderillRepository selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_olderill_repository
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    int updateByExampleSelective(@Param("record") PensionOlderillRepository record, @Param("example") PensionOlderillRepositoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_olderill_repository
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    int updateByExample(@Param("record") PensionOlderillRepository record, @Param("example") PensionOlderillRepositoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_olderill_repository
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    int updateByPrimaryKeySelective(PensionOlderillRepository record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pension_olderill_repository
     *
     * @mbggenerated Thu Dec 05 13:20:45 CST 2013
     */
    int updateByPrimaryKey(PensionOlderillRepository record);
}