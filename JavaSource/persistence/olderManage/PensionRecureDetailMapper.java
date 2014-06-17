package persistence.olderManage;

import domain.olderManage.PensionRecureDetail;
import domain.olderManage.PensionRecureDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import service.olderManage.PensionRecureDetailExtend;

public interface PensionRecureDetailMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_recure_detail
	 * @mbggenerated  Sat Oct 12 15:19:41 CST 2013
	 */
	int countByExample(PensionRecureDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_recure_detail
	 * @mbggenerated  Sat Oct 12 15:19:41 CST 2013
	 */
	int deleteByExample(PensionRecureDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_recure_detail
	 * @mbggenerated  Sat Oct 12 15:19:41 CST 2013
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_recure_detail
	 * @mbggenerated  Sat Oct 12 15:19:41 CST 2013
	 */
	int insert(PensionRecureDetail record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_recure_detail
	 * @mbggenerated  Sat Oct 12 15:19:41 CST 2013
	 */
	int insertSelective(PensionRecureDetail record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_recure_detail
	 * @mbggenerated  Sat Oct 12 15:19:41 CST 2013
	 */
	List<PensionRecureDetail> selectByExample(PensionRecureDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_recure_detail
	 * @mbggenerated  Sat Oct 12 15:19:41 CST 2013
	 */
	PensionRecureDetail selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_recure_detail
	 * @mbggenerated  Sat Oct 12 15:19:41 CST 2013
	 */
	int updateByExampleSelective(@Param("record") PensionRecureDetail record,
			@Param("example") PensionRecureDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_recure_detail
	 * @mbggenerated  Sat Oct 12 15:19:41 CST 2013
	 */
	int updateByExample(@Param("record") PensionRecureDetail record,
			@Param("example") PensionRecureDetailExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_recure_detail
	 * @mbggenerated  Sat Oct 12 15:19:41 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionRecureDetail record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_recure_detail
	 * @mbggenerated  Sat Oct 12 15:19:41 CST 2013
	 */
	int updateByPrimaryKey(PensionRecureDetail record);
	
	
	List<PensionRecureDetailExtend> selectExtendByExample(PensionRecureDetailExample example);
}