package service.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dictionary.PensionDicMessageMapper;
import persistence.system.PensionMessagedelrecordMapper;
import persistence.system.PensionMessagesMapper;
import util.PmsException;

import com.centling.his.util.logger.HisLogger;

import domain.dictionary.PensionDicMessage;
import domain.system.PensionMessagedelrecord;
import domain.system.PensionMessages;
import domain.system.PensionMessagesExample;

@SuppressWarnings("serial")
@Service
public class MessageMessage implements Serializable {

	@Autowired
	PensionMessagesMapper pensionMessagesMapper;
	@Autowired
	PensionDicMessageMapper pensionDicMessageMapper;
	@Autowired
	PensionMessagedelrecordMapper pensionMessagedelrecordMapper;

	private static HisLogger<?> logger = HisLogger
			.getLogger(MessageMessage.class);

	/**
	 * 发送消息，将消息保存到消息表中
	 * 
	 * @param messageType
	 *            消息类别id 来源消息类别表
	 * @param dptIdList
	 *            发送到科室id列表，来源科室字典表
	 * @param userIdList
	 *            发送到用户id列表，来用用户信息表
	 * @param messageContent
	 *            发送的消息内容
	 * @param url
	 *            点击消息跳转到的地址 来源消息类别表+传值
	 * @param tableName
	 *            数据表名称
	 * @param tableKeyId
	 *            数据表主键
	 * @throws PmsException
	 */
	public void sendMessage(Long messagetypeId, List<Long> dptIdList,
			List<Long> userIdList, String messagename, String messageContent,
			String url, String tableName, Long tableKeyId) throws PmsException {

		logger.info("发送消息【" + messagetypeId + "】开始");
		if ((dptIdList == null || dptIdList.size() == 0)
				&& (userIdList == null || userIdList.size() == 0)) {
			throw new PmsException("请选设置发送到部门或发送到员工");
		}
		try {
			// 是否发送到个人，发送个人，部门忽略
			if (userIdList != null && userIdList.size() > 0) {
				for (Long userId : userIdList) {

					PensionMessages record = new PensionMessages();

					record.setMessagetypeId(messagetypeId);
					// record.setDeptId(deptId);
					record.setUserId(userId);
					record.setMessagename(messagename);
					record.setNotes(messageContent);
					record.setMessageUrl(url);
					record.setMessageDate(new Date());
					record.setTableName(tableName);
					record.setTableKeyId(tableKeyId);

					pensionMessagesMapper.insertSelective(record);

					System.out.println("发送到用户成功");
				}
			} else { // 发送到部门
				for (Long dptId : dptIdList) {

					PensionMessages record = new PensionMessages();

					record.setMessagetypeId(messagetypeId);
					record.setDeptId(dptId);
					// record.setUserId(userId);
					record.setMessagename(messagename);
					record.setNotes(messageContent);
					record.setMessageUrl(url);
					record.setMessageDate(new Date());
					record.setTableName(tableName);
					record.setTableKeyId(tableKeyId);

					pensionMessagesMapper.insertSelective(record);

					System.out.println("发送到部门成功");
				}
			}
			logger.info("发送消息【" + messagetypeId + "】完成");
		} catch (Exception e) {
			throw new PmsException("发送消息失败");
		}
	}

	/**
	 * 查询未处理的消息记录数
	 * 
	 * @param dptId
	 * @param userId
	 * @return
	 */
	public Integer readMessageCount(Long dptId, Long userId) {
		PensionMessagesExample example = new PensionMessagesExample();
		Integer clearedEqual = 2; // 未删除
		Integer isprocessor = 2; // 未处理
		example.or().andDeptIdEqualTo(dptId).andClearedEqualTo(clearedEqual)
				.andIsprocessorEqualTo(isprocessor);

		example.or().andUserIdEqualTo(userId).andClearedEqualTo(clearedEqual)
				.andIsprocessorEqualTo(isprocessor);

		return pensionMessagesMapper.countExtendByExample(example, userId);
	}

	/**
	 * 
	 * @param dptId
	 *            部门id
	 * @param userId
	 *            用户id
	 * @param clearedEqual
	 *            是否删除 1删除 2未删
	 * @param isprocessor
	 *            是否处理 1处理 2未处理
	 * @return
	 */
	public List<MessageDoman> readMessages(Long dptId, Long userId,
			Integer clearedEqual, Integer isprocessor, Long messageType) {

		PensionMessagesExample example = new PensionMessagesExample();
		example.or().andDeptIdEqualTo(dptId).andClearedEqualTo(clearedEqual)
				.andIsprocessorEqualTo(isprocessor)
				.andMessagetypeIdEqualTo(messageType);

		example.or().andUserIdEqualTo(userId).andClearedEqualTo(clearedEqual)
				.andIsprocessorEqualTo(isprocessor)
				.andMessagetypeIdEqualTo(messageType);
		return pensionMessagesMapper.selectExtendByExample(example, userId);

	}

	/**
	 * 获取消息连接地址
	 * 
	 * @param id
	 * @return
	 * @throws PmsException
	 */
	public String selectMessageTypeUrl(Long id) throws PmsException {
		PensionDicMessage pensionDicMessage = pensionDicMessageMapper
				.selectByPrimaryKey(id);

		if (pensionDicMessage == null || pensionDicMessage.getUrl() == null
				|| pensionDicMessage.getUrl().isEmpty()) {
			throw new PmsException("请设置消息类别与连接地址");
		} else {
			return pensionDicMessage.getUrl();
		}

	}

	/**
	 * 设置消息对应事件已经完成
	 * 
	 * @param processorId
	 * @param messagetypeId
	 * @param tableNameEqual
	 * @param tableKeyId
	 */
	public void updateMessageProcessor(Long processorId, Long messagetypeId,
			String tableName, Long tableKeyId, Long deptId) {
		PensionMessages record = new PensionMessages();
		record.setProcessorId(processorId);
		Integer isprocessor = 1;
		record.setIsprocessor(isprocessor);
		record.setProcessorDate(new Date());
		PensionMessagesExample example = new PensionMessagesExample();

		example.or().andMessagetypeIdEqualTo(messagetypeId)
				.andTableNameEqualTo(tableName)
				.andTableKeyIdEqualTo(tableKeyId).andUserIdEqualTo(processorId);

		example.or().andMessagetypeIdEqualTo(messagetypeId)
				.andTableNameEqualTo(tableName)
				.andTableKeyIdEqualTo(tableKeyId).andDeptIdEqualTo(deptId)
				.andUserIdIsNull();

		pensionMessagesMapper.updateByExampleSelective(record, example);

	}

	/**
	 * 删除消息
	 * 
	 * @param deletorId
	 * @param messageId
	 * @throws PmsException
	 */
	public void insertDelMessage(Long deletorId, Long messageId)
			throws PmsException {
		try {
			PensionMessagedelrecord record = new PensionMessagedelrecord();
			record.setDeletorId(deletorId);
			record.setDeletetime(new Date());
			record.setMessageId(messageId);
			pensionMessagedelrecordMapper.insertSelective(record);
		} catch (Exception e) {
			throw new PmsException("删除消息失败");
			// TODO: handle exception
		}

	}

	/**
	 * 撤销已发送消息
	 * 
	 * @param messagetypeId
	 * @param tableName
	 * @param tableKeyId
	 */
	public void deleteMessage(Long messagetypeId, String tableName,
			Long tableKeyId) {
		PensionMessagesExample example = new PensionMessagesExample();
		example.or().andMessagetypeIdEqualTo(messagetypeId)
				.andTableNameEqualTo(tableName)
				.andTableKeyIdEqualTo(tableKeyId);
		pensionMessagesMapper.deleteByExample(example);
	}

	/**
	 * 测试函数
	 * 
	 * @return
	 */
	public String testFun() {
		return "1234567890";
	}

	public List<MessageEchartsDeman> classifyMessages(Long dptId, Long userId) {
		List<MessageDoman> messageList = this.readMessages(dptId, userId, 2, 2, null);
		List<String> typeName = new ArrayList<String>();
		List<MessageEchartsDeman> demans = new ArrayList<MessageEchartsDeman>();
		for(MessageDoman message:messageList){
			String messageType = message.getMessagTypeName();
			if(typeName.contains(messageType)){
				for(MessageEchartsDeman deman: demans){
					if(deman.getMessageName().equals(messageType)){
						deman.setMessageNum(deman.getMessageNum() + 1 );
						break;
					}
				}
			}else{
				MessageEchartsDeman deman = new MessageEchartsDeman();
				deman.setMessageId(message.getMessagetypeId());
				deman.setMessageName(messageType);
				deman.setMessageNum(1);
				deman.setMessageUrl("/");
				deman.setType(1);//属于消息类别
				demans.add(deman);
				typeName.add(messageType);
			}
		}
		for(MessageDoman message:messageList){
			MessageEchartsDeman deman = new MessageEchartsDeman();
			deman.setMessageId(message.getId());
			deman.setMessageName(message.getMessagename());
			deman.setMessageUrl(message.getMessageUrl());
			deman.setMessageNum(1);
			deman.setType(2);//属于消息类别
			demans.add(deman);
			
		}
		return demans;
	}
}
