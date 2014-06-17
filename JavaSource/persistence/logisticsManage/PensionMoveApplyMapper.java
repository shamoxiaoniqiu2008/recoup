package persistence.logisticsManage;

import domain.logisticsManage.PensionMoveApply;
import domain.logisticsManage.PensionMoveApplyExample;
import domain.logisticsManage.PensionMoveApplyExtend;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionMoveApplyMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_move_apply
	 * @mbggenerated  Mon Mar 03 11:04:52 CST 2014
	 */
	int countByExample(PensionMoveApplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_move_apply
	 * @mbggenerated  Mon Mar 03 11:04:52 CST 2014
	 */
	int deleteByExample(PensionMoveApplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_move_apply
	 * @mbggenerated  Mon Mar 03 11:04:52 CST 2014
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_move_apply
	 * @mbggenerated  Mon Mar 03 11:04:52 CST 2014
	 */
	int insert(PensionMoveApply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_move_apply
	 * @mbggenerated  Mon Mar 03 11:04:52 CST 2014
	 */
	int insertSelective(PensionMoveApply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_move_apply
	 * @mbggenerated  Mon Mar 03 11:04:52 CST 2014
	 */
	List<PensionMoveApply> selectByExample(PensionMoveApplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_move_apply
	 * @mbggenerated  Mon Mar 03 11:04:52 CST 2014
	 */
	PensionMoveApply selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_move_apply
	 * @mbggenerated  Mon Mar 03 11:04:52 CST 2014
	 */
	int updateByExampleSelective(@Param("record") PensionMoveApply record,
			@Param("example") PensionMoveApplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_move_apply
	 * @mbggenerated  Mon Mar 03 11:04:52 CST 2014
	 */
	int updateByExample(@Param("record") PensionMoveApply record,
			@Param("example") PensionMoveApplyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_move_apply
	 * @mbggenerated  Mon Mar 03 11:04:52 CST 2014
	 */
	int updateByPrimaryKeySelective(PensionMoveApply record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_move_apply
	 * @mbggenerated  Mon Mar 03 11:04:52 CST 2014
	 */
	int updateByPrimaryKey(PensionMoveApply record);

	/**
     * 
     * @param map
     * @return
     */
	List<PensionMoveApplyExtend> selectMoveApplicationRecords(
			HashMap<String, Object> map);

	List<PensionMoveApplyExtend> fillField(@Param("olderId")Long olderId);
	
}