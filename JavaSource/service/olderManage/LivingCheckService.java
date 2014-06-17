package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.employeeManage.PensionEmployeeMapper;
import persistence.olderManage.PensionLivingnotifyMapper;
import persistence.olderManage.PensionOlderMapper;
import service.system.MessageMessage;
import util.JavaConfig;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class LivingCheckService {
	@Autowired
	private PensionLivingnotifyMapper pensionLivingnotifyMapper;
	@Autowired
	private PensionEmployeeMapper pensionEmployeeMapper;
	@Autowired
	private MessageMessage messageMessage;
	@Autowired
	private PensionOlderMapper pensionOlderMapper;

	/**
	 * 查询未质检事件
	 * 
	 * @param deptId
	 * @param olderId
	 * @return
	 */
	public List<PensionNoticeDomain> selectUnDealEventList(Long olderId,
			Date startDate, Date endDate, String checkFlag) {
		List<PensionNoticeDomain> eventList = new ArrayList<PensionNoticeDomain>();
		eventList = pensionLivingnotifyMapper.selectUnCheckEventList(olderId,
				startDate, endDate, checkFlag);
		for (PensionNoticeDomain temp : eventList) {
			temp.setHandleName(pensionEmployeeMapper.selectByPrimaryKey(
					temp.getHandlerId()).getName());
			if (temp.getCheckerId() != null) {
				temp.setCheckName(pensionEmployeeMapper.selectByPrimaryKey(
						temp.getCheckerId()).getName());
			}
		}
		return eventList;
	}

	/**
	 * 事件确认
	 * 
	 * @param domain
	 */
	@Transactional
	public void updateEventRecords(PensionNoticeDomain domain, Long olderId,
			Long userId, Long deptId) {
		pensionLivingnotifyMapper.updateByPrimaryKeySelective(domain);
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("LivingNoticeController.messagetypeId");
		Long messagetypeId = Long.valueOf(typeIdStr);
		messageMessage.updateMessageProcessor(userId, messagetypeId,
				"pension_livingnotify", olderId, deptId);
	}

	/**
	 * 根据老人ID查询老人信息
	 * 
	 * @param olderId
	 * @return
	 */
	public String selectOlderName(Long olderId) {
		return pensionOlderMapper.selectByPrimaryKey(olderId).getName();
	}

}
