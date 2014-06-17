package service.system;


//导航页echarts饼状图显示消息用demon
public class MessageEchartsDeman {

	private Long messageId;
	
	private String messageName;
	
	private Integer messageNum;
	
	private String messageUrl;
	
	private Integer type;//1--消息类型；2--消息

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public Integer getMessageNum() {
		return messageNum;
	}

	public void setMessageNum(Integer messageNum) {
		this.messageNum = messageNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getMessageUrl() {
		return messageUrl;
	}

	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}
	
	
	
	
}
