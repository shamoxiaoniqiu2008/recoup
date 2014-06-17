package persistence.system;

import domain.system.PensionMessages;
import domain.system.PensionMessagesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import service.system.MessageDoman;

public interface PensionMessagesMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	int countByExample(PensionMessagesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	int deleteByExample(PensionMessagesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	int insert(PensionMessages record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	int insertSelective(PensionMessages record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	List<PensionMessages> selectByExample(PensionMessagesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	PensionMessages selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	int updateByExampleSelective(@Param("record") PensionMessages record,
			@Param("example") PensionMessagesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	int updateByExample(@Param("record") PensionMessages record,
			@Param("example") PensionMessagesExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	int updateByPrimaryKeySelective(PensionMessages record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_messages
	 * @mbggenerated  Mon Sep 09 14:35:27 CST 2013
	 */
	int updateByPrimaryKey(PensionMessages record);

	List<MessageDoman> selectExtendByExample(@Param("example") PensionMessagesExample example,
			@Param("userId") Long userId);
	
	int countExtendByExample(@Param("example") PensionMessagesExample example, @Param("userId") Long userId);
}