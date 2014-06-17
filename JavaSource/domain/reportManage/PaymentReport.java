package domain.reportManage;

import java.io.Serializable;
import java.util.Date;

public class PaymentReport implements Serializable {
	private Long olderId;
	private String olderName;
	private String olderSex;
	private Integer olderAge;
	private Long itemId;
	private String itemName;
	private Float itemFees;
	private Float itemNum;
	private Float totalFees;
	private Date genetateTime;
	private Date payTime;
	private String olderStatus;
	private String classes;
	private Integer source;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Long getOlderId() {
		return olderId;
	}
	public void setOlderId(Long olderId) {
		this.olderId = olderId;
	}
	public String getOlderName() {
		return olderName;
	}
	public void setOlderName(String olderName) {
		this.olderName = olderName;
	}
	public String getOlderSex() {
		return olderSex;
	}
	public void setOlderSex(String olderSex) {
		this.olderSex = olderSex;
	}
	public Integer getOlderAge() {
		return olderAge;
	}
	public void setOlderAge(Integer olderAge) {
		this.olderAge = olderAge;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Float getItemFees() {
		return itemFees;
	}
	public void setItemFees(Float itemFees) {
		this.itemFees = itemFees;
	}
	public Float getItemNum() {
		return itemNum;
	}
	public void setItemNum(Float itemNum) {
		this.itemNum = itemNum;
	}
	public Float getTotalFees() {
		return totalFees;
	}
	public void setTotalFees(Float totalFees) {
		this.totalFees = totalFees;
	}
	public Date getGenetateTime() {
		return genetateTime;
	}
	public void setGenetateTime(Date genetateTime) {
		this.genetateTime = genetateTime;
	}
	public String getOlderStatus() {
		return olderStatus;
	}
	public void setOlderStatus(String olderStatus) {
		this.olderStatus = olderStatus;
	}
	public String getClasses() {
		return classes;
	}
	public void setClasses(String classes) {
		this.classes = classes;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	
	
	
}