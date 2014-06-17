package service.olderManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.hpl.sparta.ParseException;

import persistence.employeeManage.PensionEmployeeMapper;
import persistence.olderManage.PensionRingrecordMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import domain.configureManage.PensionRingExample;
import domain.employeeManage.PensionEmployee;
import domain.olderManage.PensionRingrecord;
import domain.olderManage.PensionRingrecordExample;
import domain.system.PensionMessagesExample;


@Service
public class RingService {

	@Autowired
	private PensionRingrecordMapper pensionRingrecordMapper;
	
	@Autowired
	private MessageMessage messageMessage;
	
	@Autowired
	private PensionEmployeeMapper employeeMapper;
	
	public List<PensionRingrecord> search(Date startDate, Date endDate,Integer ringstatus) {
		return pensionRingrecordMapper.selectRingRecords(startDate, endDate, ringstatus);
	}
	
	/**
	 * 查询未处理的呼叫记录数
	 * @return
	 */
	public Integer readRingCount() {
		PensionRingrecordExample example = new PensionRingrecordExample();
		Integer clearedEqual = 2;   //未删除
		Integer isprocessor = 1;    //未处理
		example.or()
		.andClearedEqualTo(clearedEqual)
		.andRingstatusEqualTo(isprocessor);
		return pensionRingrecordMapper.countByExample(example);		
	}
	
	/**
	 * 检测呼叫请求响应，未响应就发送消息
	 * @return
	 * @throws PmsException 
	 */
	public void sendNotifyToTop() throws PmsException {

		List<PensionRingrecord> list = search(null,null,1);
		
	     String topLever = JavaConfig.fetchProperty("Ring.topLever");
	     String serviceTime = JavaConfig.fetchProperty("Ring.serviceTime");
	     
	     List<Long> topLeverList = new ArrayList<Long>();
	     List<Long> serviceTimeList = new ArrayList<Long>();
	     
	     if(topLever != null && !topLever.isEmpty())
	     {
	    	 String[] budgetDptId=topLever.split(",");
	    	 for(int i = 0; i < budgetDptId.length; i++) 
	    	 {
	    		 topLeverList.add(Long.valueOf(budgetDptId[i]));
		     }
	     }
	     
	     if(serviceTime != null && !serviceTime.isEmpty())
	     {
	    	 String[] budgetDptId=serviceTime.split(",");
	    	 for(int i = 0; i < budgetDptId.length; i++) 
	    	 {
	    		 serviceTimeList.add(Long.valueOf(budgetDptId[i]));
	    	 }
	     }
	     
	     for(int i = 0;i<list.size();i++)
	     {
	    	 PensionRingrecord ringrecord = list.get(i);
	    	 Date date = new Date();
	    	 Long time = date.getTime()-ringrecord.getRingtime().getTime();
    		 int currentLevelIndex = ringrecord.getHandlelevel()-1;
    		 if(currentLevelIndex>=serviceTimeList.size()||currentLevelIndex>=topLeverList.size())continue;
    		 if(time>=serviceTimeList.get(currentLevelIndex)*60*1000)
    		 {
    			 //发送消息给topLeverList[currentLevelIndex];
    			 //发送消息
    		     String messageContent = ringrecord.getRoomName()+"房间呼叫未响应！";
    		     //消息类别
    		     String typeIdStr = JavaConfig.fetchProperty("Ring.waitingRing");
    		     Long messagetypeId = Long.valueOf(typeIdStr);
    		     
    		     String url;
    			 url = messageMessage.selectMessageTypeUrl(messagetypeId);
    			 url = url+"?recordId="+ringrecord.getId();
    			 
    			 List<Long> userIdList = new ArrayList<Long>();
    			 userIdList.add(topLeverList.get(currentLevelIndex));
    			 
    		     String messagename = "【"+ringrecord.getRoomName()+"】呼叫请求";
    		     
    		     messageMessage.sendMessage(messagetypeId, null, userIdList, messagename, messageContent, url,"pension_ringrecord",ringrecord.getId());
    		     
    		     //更新当前处理级别
    		     ringrecord.setHandlelevel(ringrecord.getHandlelevel()+1);
    		     //更新当前处理人
    		     PensionEmployee employee = employeeMapper.selectByPrimaryKey(topLeverList.get(currentLevelIndex));
    		     ringrecord.setHandlername(employee.getName());
    		     pensionRingrecordMapper.updateByPrimaryKeySelective(ringrecord);
    		 
    		 }
    		 
    		 //清除已处理消息
    		 

	     }
		
	}
	
}
