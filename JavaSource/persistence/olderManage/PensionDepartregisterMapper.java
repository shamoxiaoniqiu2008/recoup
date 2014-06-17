package persistence.olderManage;

import domain.olderManage.PensionDepartregister;
import domain.olderManage.PensionDepartregisterExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionDepartregisterMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departregister
	 * @mbggenerated  Wed Dec 04 17:39:07 CST 2013
	 */
	int countByExample(PensionDepartregisterExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departregister
	 * @mbggenerated  Wed Dec 04 17:39:07 CST 2013
	 */
	int deleteByExample(PensionDepartregisterExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departregister
	 * @mbggenerated  Wed Dec 04 17:39:07 CST 2013
	 */
	int deleteByPrimaryKey(Long id);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departregister
	 * @mbggenerated  Wed Dec 04 17:39:07 CST 2013
	 */
	int insert(PensionDepartregister record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departregister
	 * @mbggenerated  Wed Dec 04 17:39:07 CST 2013
	 */
	int insertSelective(PensionDepartregister record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departregister
	 * @mbggenerated  Wed Dec 04 17:39:07 CST 2013
	 */
	List<PensionDepartregister> selectByExample(
			PensionDepartregisterExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departregister
	 * @mbggenerated  Wed Dec 04 17:39:07 CST 2013
	 */
	PensionDepartregister selectByPrimaryKey(Long id);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departregister
	 * @mbggenerated  Wed Dec 04 17:39:07 CST 2013
	 */
	int updateByExampleSelective(@Param("record") PensionDepartregister record,
			@Param("example") PensionDepartregisterExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departregister
	 * @mbggenerated  Wed Dec 04 17:39:07 CST 2013
	 */
	int updateByExample(@Param("record") PensionDepartregister record,
			@Param("example") PensionDepartregisterExample example);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departregister
	 * @mbggenerated  Wed Dec 04 17:39:07 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionDepartregister record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_departregister
	 * @mbggenerated  Wed Dec 04 17:39:07 CST 2013
	 */
	int updateByPrimaryKey(PensionDepartregister record);
	List<PensionDepartregister> selectDepartInfoByTime(@Param("olderId")Long olderId,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("statues")int statuses,@Param("departId")Long  departID);
	List<PensionDepartregister> searchByComfirm(@Param("olderId")Long olderId,@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("statues")int statuses,@Param("departId")Long  departID);
	// 查询老人信息  mary liu 2013-9-2 
	List<PensionDepartregister> selectDepartInfo(@Param("olderId")Long olderId);
	// 查询 已离院老人信息  mary liu 2013-9-15
	List<PensionDepartregister> selectDepartedInfo(@Param("departId")Long departId);
	//查询起止时间内新出院老人数
	int selectNewOutNum(@Param("startDate")Date startDate, @Param("endDate")Date endDate);
}