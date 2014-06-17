package persistence.logisticsManage;

import domain.logisticsManage.PensionOlderElectricity;
import domain.logisticsManage.PensionOlderElectricityExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PensionOlderElectricityMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pension_older_electricity
	 * 
	 * @mbggenerated Tue Dec 10 10:16:04 CST 2013
	 */
	int countByExample(PensionOlderElectricityExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pension_older_electricity
	 * 
	 * @mbggenerated Tue Dec 10 10:16:04 CST 2013
	 */
	int deleteByExample(PensionOlderElectricityExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pension_older_electricity
	 * 
	 * @mbggenerated Tue Dec 10 10:16:04 CST 2013
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pension_older_electricity
	 * 
	 * @mbggenerated Tue Dec 10 10:16:04 CST 2013
	 */
	int insert(PensionOlderElectricity record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pension_older_electricity
	 * 
	 * @mbggenerated Tue Dec 10 10:16:04 CST 2013
	 */
	int insertSelective(PensionOlderElectricity record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pension_older_electricity
	 * 
	 * @mbggenerated Tue Dec 10 10:16:04 CST 2013
	 */
	List<PensionOlderElectricity> selectByExample(
			PensionOlderElectricityExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pension_older_electricity
	 * 
	 * @mbggenerated Tue Dec 10 10:16:04 CST 2013
	 */
	PensionOlderElectricity selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pension_older_electricity
	 * 
	 * @mbggenerated Tue Dec 10 10:16:04 CST 2013
	 */
	int updateByExampleSelective(
			@Param("record") PensionOlderElectricity record,
			@Param("example") PensionOlderElectricityExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pension_older_electricity
	 * 
	 * @mbggenerated Tue Dec 10 10:16:04 CST 2013
	 */
	int updateByExample(@Param("record") PensionOlderElectricity record,
			@Param("example") PensionOlderElectricityExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pension_older_electricity
	 * 
	 * @mbggenerated Tue Dec 10 10:16:04 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionOlderElectricity record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table pension_older_electricity
	 * 
	 * @mbggenerated Tue Dec 10 10:16:04 CST 2013
	 */
	int updateByPrimaryKey(PensionOlderElectricity record);

	/**
	 * 
	 * @Title: selectPensionOlderElectricityList
	 * @Description: TODO
	 * @param @param roomId
	 * @param @param startDate
	 * @param @param endDate
	 * @param @param inputDate
	 * @param @return
	 * @return List<PensionOlderElectricity>
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-9 下午8:11:14
	 * @version V1.0
	 */
	List<PensionOlderElectricity> selectPensionOlderElectricityList(
			@Param("roomId") Long roomId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("inputDate") Date inputDate);

	/**
	 * 
	 * @Title: selectOlderNumbers
	 * @Description: TODO
	 * @param @param roomId
	 * @param @param startDate
	 * @param @param endDate
	 * @param @return
	 * @return Long
	 * @throws
	 * @author Justin.Su
	 * @date 2013-12-9 下午8:11:18
	 * @version V1.0
	 */
	Long selectOlderNumbers(@Param("roomId") Long roomId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * 根据起止日期查询该时间段内的老人用电记录
	 * @author centling mary.liu 2013-12-09
	 * @return
	 */
	List<PensionOlderElectricity> searchOlderElectricity(@Param("olderId")Long olderId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * 根据起止日期查询该时间段内的老人及其用电总额
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<PensionOlderElectricity> searchFreeOlder(
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	/**
	 * 
	* @Title: selectPensionOlderElectricityListByEnsured 
	* @Description: TODO
	* @param @param roomId
	* @param @param startTime
	* @param @param endTime
	* @param @return
	* @return List<PensionOlderElectricity>
	* @throws 
	* @author Justin.Su
	* @date 2013-12-12 下午12:56:34
	* @version V1.0
	 */
	List<PensionOlderElectricity> selectPensionOlderElectricityListByEnsured(
			@Param("roomId") Long roomId, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);
}