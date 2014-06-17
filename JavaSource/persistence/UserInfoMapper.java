package persistence;

import domain.UserInfo;
import domain.UserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_info
	 * @mbggenerated  Fri Jul 26 23:29:04 CST 2013
	 */
	int countByExample(UserInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_info
	 * @mbggenerated  Fri Jul 26 23:29:04 CST 2013
	 */
	int deleteByExample(UserInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_info
	 * @mbggenerated  Fri Jul 26 23:29:04 CST 2013
	 */
	int deleteByPrimaryKey(Long userId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_info
	 * @mbggenerated  Fri Jul 26 23:29:04 CST 2013
	 */
	int insert(UserInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_info
	 * @mbggenerated  Fri Jul 26 23:29:04 CST 2013
	 */
	int insertSelective(UserInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_info
	 * @mbggenerated  Fri Jul 26 23:29:04 CST 2013
	 */
	List<UserInfo> selectByExample(UserInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_info
	 * @mbggenerated  Fri Jul 26 23:29:04 CST 2013
	 */
	UserInfo selectByPrimaryKey(Long userId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_info
	 * @mbggenerated  Fri Jul 26 23:29:04 CST 2013
	 */
	int updateByExampleSelective(@Param("record") UserInfo record,
			@Param("example") UserInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_info
	 * @mbggenerated  Fri Jul 26 23:29:04 CST 2013
	 */
	int updateByExample(@Param("record") UserInfo record,
			@Param("example") UserInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_info
	 * @mbggenerated  Fri Jul 26 23:29:04 CST 2013
	 */
	int updateByPrimaryKeySelective(UserInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user_info
	 * @mbggenerated  Fri Jul 26 23:29:04 CST 2013
	 */
	int updateByPrimaryKey(UserInfo record);
}