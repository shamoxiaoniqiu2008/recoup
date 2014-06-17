package domain.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PensionDosageExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	public PensionDosageExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null || value.toString().equals("")) {
				addCriterion("1=1");
				return;
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null
					|| value1.toString().equals("")
					|| value2.toString().equals("")) {
				addCriterion("1=1");
				return;
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andIdIsNull() {
			addCriterion("id is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(Long value) {
			addCriterion("id =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Long value) {
			addCriterion("id <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Long value) {
			addCriterion("id >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Long value) {
			addCriterion("id >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Long value) {
			addCriterion("id <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Long value) {
			addCriterion("id <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Long> values) {
			addCriterion("id in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Long> values) {
			addCriterion("id not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Long value1, Long value2) {
			addCriterion("id between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Long value1, Long value2) {
			addCriterion("id not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIssendIsNull() {
			addCriterion("isSend is null");
			return (Criteria) this;
		}

		public Criteria andIssendIsNotNull() {
			addCriterion("isSend is not null");
			return (Criteria) this;
		}

		public Criteria andIssendEqualTo(Integer value) {
			addCriterion("isSend =", value, "issend");
			return (Criteria) this;
		}

		public Criteria andIssendNotEqualTo(Integer value) {
			addCriterion("isSend <>", value, "issend");
			return (Criteria) this;
		}

		public Criteria andIssendGreaterThan(Integer value) {
			addCriterion("isSend >", value, "issend");
			return (Criteria) this;
		}

		public Criteria andIssendGreaterThanOrEqualTo(Integer value) {
			addCriterion("isSend >=", value, "issend");
			return (Criteria) this;
		}

		public Criteria andIssendLessThan(Integer value) {
			addCriterion("isSend <", value, "issend");
			return (Criteria) this;
		}

		public Criteria andIssendLessThanOrEqualTo(Integer value) {
			addCriterion("isSend <=", value, "issend");
			return (Criteria) this;
		}

		public Criteria andIssendIn(List<Integer> values) {
			addCriterion("isSend in", values, "issend");
			return (Criteria) this;
		}

		public Criteria andIssendNotIn(List<Integer> values) {
			addCriterion("isSend not in", values, "issend");
			return (Criteria) this;
		}

		public Criteria andIssendBetween(Integer value1, Integer value2) {
			addCriterion("isSend between", value1, value2, "issend");
			return (Criteria) this;
		}

		public Criteria andIssendNotBetween(Integer value1, Integer value2) {
			addCriterion("isSend not between", value1, value2, "issend");
			return (Criteria) this;
		}

		public Criteria andSendtimeIsNull() {
			addCriterion("sendTime is null");
			return (Criteria) this;
		}

		public Criteria andSendtimeIsNotNull() {
			addCriterion("sendTime is not null");
			return (Criteria) this;
		}

		public Criteria andSendtimeEqualTo(Date value) {
			addCriterion("sendTime =", value, "sendtime");
			return (Criteria) this;
		}

		public Criteria andSendtimeNotEqualTo(Date value) {
			addCriterion("sendTime <>", value, "sendtime");
			return (Criteria) this;
		}

		public Criteria andSendtimeGreaterThan(Date value) {
			addCriterion("sendTime >", value, "sendtime");
			return (Criteria) this;
		}

		public Criteria andSendtimeGreaterThanOrEqualTo(Date value) {
			addCriterion("sendTime >=", value, "sendtime");
			return (Criteria) this;
		}

		public Criteria andSendtimeLessThan(Date value) {
			addCriterion("sendTime <", value, "sendtime");
			return (Criteria) this;
		}

		public Criteria andSendtimeLessThanOrEqualTo(Date value) {
			addCriterion("sendTime <=", value, "sendtime");
			return (Criteria) this;
		}

		public Criteria andSendtimeIn(List<Date> values) {
			addCriterion("sendTime in", values, "sendtime");
			return (Criteria) this;
		}

		public Criteria andSendtimeNotIn(List<Date> values) {
			addCriterion("sendTime not in", values, "sendtime");
			return (Criteria) this;
		}

		public Criteria andSendtimeBetween(Date value1, Date value2) {
			addCriterion("sendTime between", value1, value2, "sendtime");
			return (Criteria) this;
		}

		public Criteria andSendtimeNotBetween(Date value1, Date value2) {
			addCriterion("sendTime not between", value1, value2, "sendtime");
			return (Criteria) this;
		}

		public Criteria andSenderNameIsNull() {
			addCriterion("sender_name is null");
			return (Criteria) this;
		}

		public Criteria andSenderNameIsNotNull() {
			addCriterion("sender_name is not null");
			return (Criteria) this;
		}

		public Criteria andSenderNameEqualTo(String value) {
			addCriterion("sender_name =", value, "senderName");
			return (Criteria) this;
		}

		public Criteria andSenderNameNotEqualTo(String value) {
			addCriterion("sender_name <>", value, "senderName");
			return (Criteria) this;
		}

		public Criteria andSenderNameGreaterThan(String value) {
			addCriterion("sender_name >", value, "senderName");
			return (Criteria) this;
		}

		public Criteria andSenderNameGreaterThanOrEqualTo(String value) {
			addCriterion("sender_name >=", value, "senderName");
			return (Criteria) this;
		}

		public Criteria andSenderNameLessThan(String value) {
			addCriterion("sender_name <", value, "senderName");
			return (Criteria) this;
		}

		public Criteria andSenderNameLessThanOrEqualTo(String value) {
			addCriterion("sender_name <=", value, "senderName");
			return (Criteria) this;
		}

		public Criteria andSenderNameLike(String value) {
			addCriterion("sender_name like", value, "senderName");
			return (Criteria) this;
		}

		public Criteria andSenderNameNotLike(String value) {
			addCriterion("sender_name not like", value, "senderName");
			return (Criteria) this;
		}

		public Criteria andSenderNameIn(List<String> values) {
			addCriterion("sender_name in", values, "senderName");
			return (Criteria) this;
		}

		public Criteria andSenderNameNotIn(List<String> values) {
			addCriterion("sender_name not in", values, "senderName");
			return (Criteria) this;
		}

		public Criteria andSenderNameBetween(String value1, String value2) {
			addCriterion("sender_name between", value1, value2, "senderName");
			return (Criteria) this;
		}

		public Criteria andSenderNameNotBetween(String value1, String value2) {
			addCriterion("sender_name not between", value1, value2,
					"senderName");
			return (Criteria) this;
		}

		public Criteria andSenderIdIsNull() {
			addCriterion("sender_id is null");
			return (Criteria) this;
		}

		public Criteria andSenderIdIsNotNull() {
			addCriterion("sender_id is not null");
			return (Criteria) this;
		}

		public Criteria andSenderIdEqualTo(Long value) {
			addCriterion("sender_id =", value, "senderId");
			return (Criteria) this;
		}

		public Criteria andSenderIdNotEqualTo(Long value) {
			addCriterion("sender_id <>", value, "senderId");
			return (Criteria) this;
		}

		public Criteria andSenderIdGreaterThan(Long value) {
			addCriterion("sender_id >", value, "senderId");
			return (Criteria) this;
		}

		public Criteria andSenderIdGreaterThanOrEqualTo(Long value) {
			addCriterion("sender_id >=", value, "senderId");
			return (Criteria) this;
		}

		public Criteria andSenderIdLessThan(Long value) {
			addCriterion("sender_id <", value, "senderId");
			return (Criteria) this;
		}

		public Criteria andSenderIdLessThanOrEqualTo(Long value) {
			addCriterion("sender_id <=", value, "senderId");
			return (Criteria) this;
		}

		public Criteria andSenderIdIn(List<Long> values) {
			addCriterion("sender_id in", values, "senderId");
			return (Criteria) this;
		}

		public Criteria andSenderIdNotIn(List<Long> values) {
			addCriterion("sender_id not in", values, "senderId");
			return (Criteria) this;
		}

		public Criteria andSenderIdBetween(Long value1, Long value2) {
			addCriterion("sender_id between", value1, value2, "senderId");
			return (Criteria) this;
		}

		public Criteria andSenderIdNotBetween(Long value1, Long value2) {
			addCriterion("sender_id not between", value1, value2, "senderId");
			return (Criteria) this;
		}

		public Criteria andNotesIsNull() {
			addCriterion("notes is null");
			return (Criteria) this;
		}

		public Criteria andNotesIsNotNull() {
			addCriterion("notes is not null");
			return (Criteria) this;
		}

		public Criteria andNotesEqualTo(String value) {
			addCriterion("notes =", value, "notes");
			return (Criteria) this;
		}

		public Criteria andNotesNotEqualTo(String value) {
			addCriterion("notes <>", value, "notes");
			return (Criteria) this;
		}

		public Criteria andNotesGreaterThan(String value) {
			addCriterion("notes >", value, "notes");
			return (Criteria) this;
		}

		public Criteria andNotesGreaterThanOrEqualTo(String value) {
			addCriterion("notes >=", value, "notes");
			return (Criteria) this;
		}

		public Criteria andNotesLessThan(String value) {
			addCriterion("notes <", value, "notes");
			return (Criteria) this;
		}

		public Criteria andNotesLessThanOrEqualTo(String value) {
			addCriterion("notes <=", value, "notes");
			return (Criteria) this;
		}

		public Criteria andNotesLike(String value) {
			addCriterion("notes like", value, "notes");
			return (Criteria) this;
		}

		public Criteria andNotesNotLike(String value) {
			addCriterion("notes not like", value, "notes");
			return (Criteria) this;
		}

		public Criteria andNotesIn(List<String> values) {
			addCriterion("notes in", values, "notes");
			return (Criteria) this;
		}

		public Criteria andNotesNotIn(List<String> values) {
			addCriterion("notes not in", values, "notes");
			return (Criteria) this;
		}

		public Criteria andNotesBetween(String value1, String value2) {
			addCriterion("notes between", value1, value2, "notes");
			return (Criteria) this;
		}

		public Criteria andNotesNotBetween(String value1, String value2) {
			addCriterion("notes not between", value1, value2, "notes");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdIsNull() {
			addCriterion("deliveryId is null");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdIsNotNull() {
			addCriterion("deliveryId is not null");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdEqualTo(Long value) {
			addCriterion("deliveryId =", value, "deliveryId");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdNotEqualTo(Long value) {
			addCriterion("deliveryId <>", value, "deliveryId");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdGreaterThan(Long value) {
			addCriterion("deliveryId >", value, "deliveryId");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdGreaterThanOrEqualTo(Long value) {
			addCriterion("deliveryId >=", value, "deliveryId");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdLessThan(Long value) {
			addCriterion("deliveryId <", value, "deliveryId");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdLessThanOrEqualTo(Long value) {
			addCriterion("deliveryId <=", value, "deliveryId");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdIn(List<Long> values) {
			addCriterion("deliveryId in", values, "deliveryId");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdNotIn(List<Long> values) {
			addCriterion("deliveryId not in", values, "deliveryId");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdBetween(Long value1, Long value2) {
			addCriterion("deliveryId between", value1, value2, "deliveryId");
			return (Criteria) this;
		}

		public Criteria andDeliveryIdNotBetween(Long value1, Long value2) {
			addCriterion("deliveryId not between", value1, value2, "deliveryId");
			return (Criteria) this;
		}

		public Criteria andCharteridIsNull() {
			addCriterion("charterId is null");
			return (Criteria) this;
		}

		public Criteria andCharteridIsNotNull() {
			addCriterion("charterId is not null");
			return (Criteria) this;
		}

		public Criteria andCharteridEqualTo(Integer value) {
			addCriterion("charterId =", value, "charterid");
			return (Criteria) this;
		}

		public Criteria andCharteridNotEqualTo(Integer value) {
			addCriterion("charterId <>", value, "charterid");
			return (Criteria) this;
		}

		public Criteria andCharteridGreaterThan(Integer value) {
			addCriterion("charterId >", value, "charterid");
			return (Criteria) this;
		}

		public Criteria andCharteridGreaterThanOrEqualTo(Integer value) {
			addCriterion("charterId >=", value, "charterid");
			return (Criteria) this;
		}

		public Criteria andCharteridLessThan(Integer value) {
			addCriterion("charterId <", value, "charterid");
			return (Criteria) this;
		}

		public Criteria andCharteridLessThanOrEqualTo(Integer value) {
			addCriterion("charterId <=", value, "charterid");
			return (Criteria) this;
		}

		public Criteria andCharteridIn(List<Integer> values) {
			addCriterion("charterId in", values, "charterid");
			return (Criteria) this;
		}

		public Criteria andCharteridNotIn(List<Integer> values) {
			addCriterion("charterId not in", values, "charterid");
			return (Criteria) this;
		}

		public Criteria andCharteridBetween(Integer value1, Integer value2) {
			addCriterion("charterId between", value1, value2, "charterid");
			return (Criteria) this;
		}

		public Criteria andCharteridNotBetween(Integer value1, Integer value2) {
			addCriterion("charterId not between", value1, value2, "charterid");
			return (Criteria) this;
		}

		public Criteria andCharternameIsNull() {
			addCriterion("charterName is null");
			return (Criteria) this;
		}

		public Criteria andCharternameIsNotNull() {
			addCriterion("charterName is not null");
			return (Criteria) this;
		}

		public Criteria andCharternameEqualTo(String value) {
			addCriterion("charterName =", value, "chartername");
			return (Criteria) this;
		}

		public Criteria andCharternameNotEqualTo(String value) {
			addCriterion("charterName <>", value, "chartername");
			return (Criteria) this;
		}

		public Criteria andCharternameGreaterThan(String value) {
			addCriterion("charterName >", value, "chartername");
			return (Criteria) this;
		}

		public Criteria andCharternameGreaterThanOrEqualTo(String value) {
			addCriterion("charterName >=", value, "chartername");
			return (Criteria) this;
		}

		public Criteria andCharternameLessThan(String value) {
			addCriterion("charterName <", value, "chartername");
			return (Criteria) this;
		}

		public Criteria andCharternameLessThanOrEqualTo(String value) {
			addCriterion("charterName <=", value, "chartername");
			return (Criteria) this;
		}

		public Criteria andCharternameLike(String value) {
			addCriterion("charterName like", value, "chartername");
			return (Criteria) this;
		}

		public Criteria andCharternameNotLike(String value) {
			addCriterion("charterName not like", value, "chartername");
			return (Criteria) this;
		}

		public Criteria andCharternameIn(List<String> values) {
			addCriterion("charterName in", values, "chartername");
			return (Criteria) this;
		}

		public Criteria andCharternameNotIn(List<String> values) {
			addCriterion("charterName not in", values, "chartername");
			return (Criteria) this;
		}

		public Criteria andCharternameBetween(String value1, String value2) {
			addCriterion("charterName between", value1, value2, "chartername");
			return (Criteria) this;
		}

		public Criteria andCharternameNotBetween(String value1, String value2) {
			addCriterion("charterName not between", value1, value2,
					"chartername");
			return (Criteria) this;
		}

		public Criteria andDeliverynameIsNull() {
			addCriterion("deliveryName is null");
			return (Criteria) this;
		}

		public Criteria andDeliverynameIsNotNull() {
			addCriterion("deliveryName is not null");
			return (Criteria) this;
		}

		public Criteria andDeliverynameEqualTo(String value) {
			addCriterion("deliveryName =", value, "deliveryname");
			return (Criteria) this;
		}

		public Criteria andDeliverynameNotEqualTo(String value) {
			addCriterion("deliveryName <>", value, "deliveryname");
			return (Criteria) this;
		}

		public Criteria andDeliverynameGreaterThan(String value) {
			addCriterion("deliveryName >", value, "deliveryname");
			return (Criteria) this;
		}

		public Criteria andDeliverynameGreaterThanOrEqualTo(String value) {
			addCriterion("deliveryName >=", value, "deliveryname");
			return (Criteria) this;
		}

		public Criteria andDeliverynameLessThan(String value) {
			addCriterion("deliveryName <", value, "deliveryname");
			return (Criteria) this;
		}

		public Criteria andDeliverynameLessThanOrEqualTo(String value) {
			addCriterion("deliveryName <=", value, "deliveryname");
			return (Criteria) this;
		}

		public Criteria andDeliverynameLike(String value) {
			addCriterion("deliveryName like", value, "deliveryname");
			return (Criteria) this;
		}

		public Criteria andDeliverynameNotLike(String value) {
			addCriterion("deliveryName not like", value, "deliveryname");
			return (Criteria) this;
		}

		public Criteria andDeliverynameIn(List<String> values) {
			addCriterion("deliveryName in", values, "deliveryname");
			return (Criteria) this;
		}

		public Criteria andDeliverynameNotIn(List<String> values) {
			addCriterion("deliveryName not in", values, "deliveryname");
			return (Criteria) this;
		}

		public Criteria andDeliverynameBetween(String value1, String value2) {
			addCriterion("deliveryName between", value1, value2, "deliveryname");
			return (Criteria) this;
		}

		public Criteria andDeliverynameNotBetween(String value1, String value2) {
			addCriterion("deliveryName not between", value1, value2,
					"deliveryname");
			return (Criteria) this;
		}

		public Criteria andClearedIsNull() {
			addCriterion("cleared is null");
			return (Criteria) this;
		}

		public Criteria andClearedIsNotNull() {
			addCriterion("cleared is not null");
			return (Criteria) this;
		}

		public Criteria andClearedEqualTo(Integer value) {
			addCriterion("cleared =", value, "cleared");
			return (Criteria) this;
		}

		public Criteria andClearedNotEqualTo(Integer value) {
			addCriterion("cleared <>", value, "cleared");
			return (Criteria) this;
		}

		public Criteria andClearedGreaterThan(Integer value) {
			addCriterion("cleared >", value, "cleared");
			return (Criteria) this;
		}

		public Criteria andClearedGreaterThanOrEqualTo(Integer value) {
			addCriterion("cleared >=", value, "cleared");
			return (Criteria) this;
		}

		public Criteria andClearedLessThan(Integer value) {
			addCriterion("cleared <", value, "cleared");
			return (Criteria) this;
		}

		public Criteria andClearedLessThanOrEqualTo(Integer value) {
			addCriterion("cleared <=", value, "cleared");
			return (Criteria) this;
		}

		public Criteria andClearedIn(List<Integer> values) {
			addCriterion("cleared in", values, "cleared");
			return (Criteria) this;
		}

		public Criteria andClearedNotIn(List<Integer> values) {
			addCriterion("cleared not in", values, "cleared");
			return (Criteria) this;
		}

		public Criteria andClearedBetween(Integer value1, Integer value2) {
			addCriterion("cleared between", value1, value2, "cleared");
			return (Criteria) this;
		}

		public Criteria andClearedNotBetween(Integer value1, Integer value2) {
			addCriterion("cleared not between", value1, value2, "cleared");
			return (Criteria) this;
		}

		public Criteria andNursedeptidIsNull() {
			addCriterion("nurseDeptId is null");
			return (Criteria) this;
		}

		public Criteria andNursedeptidIsNotNull() {
			addCriterion("nurseDeptId is not null");
			return (Criteria) this;
		}

		public Criteria andNursedeptidEqualTo(Long value) {
			addCriterion("nurseDeptId =", value, "nursedeptid");
			return (Criteria) this;
		}

		public Criteria andNursedeptidNotEqualTo(Long value) {
			addCriterion("nurseDeptId <>", value, "nursedeptid");
			return (Criteria) this;
		}

		public Criteria andNursedeptidGreaterThan(Long value) {
			addCriterion("nurseDeptId >", value, "nursedeptid");
			return (Criteria) this;
		}

		public Criteria andNursedeptidGreaterThanOrEqualTo(Long value) {
			addCriterion("nurseDeptId >=", value, "nursedeptid");
			return (Criteria) this;
		}

		public Criteria andNursedeptidLessThan(Long value) {
			addCriterion("nurseDeptId <", value, "nursedeptid");
			return (Criteria) this;
		}

		public Criteria andNursedeptidLessThanOrEqualTo(Long value) {
			addCriterion("nurseDeptId <=", value, "nursedeptid");
			return (Criteria) this;
		}

		public Criteria andNursedeptidIn(List<Long> values) {
			addCriterion("nurseDeptId in", values, "nursedeptid");
			return (Criteria) this;
		}

		public Criteria andNursedeptidNotIn(List<Long> values) {
			addCriterion("nurseDeptId not in", values, "nursedeptid");
			return (Criteria) this;
		}

		public Criteria andNursedeptidBetween(Long value1, Long value2) {
			addCriterion("nurseDeptId between", value1, value2, "nursedeptid");
			return (Criteria) this;
		}

		public Criteria andNursedeptidNotBetween(Long value1, Long value2) {
			addCriterion("nurseDeptId not between", value1, value2,
					"nursedeptid");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameIsNull() {
			addCriterion("nurseDeptName is null");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameIsNotNull() {
			addCriterion("nurseDeptName is not null");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameEqualTo(String value) {
			addCriterion("nurseDeptName =", value, "nursedeptname");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameNotEqualTo(String value) {
			addCriterion("nurseDeptName <>", value, "nursedeptname");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameGreaterThan(String value) {
			addCriterion("nurseDeptName >", value, "nursedeptname");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameGreaterThanOrEqualTo(String value) {
			addCriterion("nurseDeptName >=", value, "nursedeptname");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameLessThan(String value) {
			addCriterion("nurseDeptName <", value, "nursedeptname");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameLessThanOrEqualTo(String value) {
			addCriterion("nurseDeptName <=", value, "nursedeptname");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameLike(String value) {
			addCriterion("nurseDeptName like", value, "nursedeptname");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameNotLike(String value) {
			addCriterion("nurseDeptName not like", value, "nursedeptname");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameIn(List<String> values) {
			addCriterion("nurseDeptName in", values, "nursedeptname");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameNotIn(List<String> values) {
			addCriterion("nurseDeptName not in", values, "nursedeptname");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameBetween(String value1, String value2) {
			addCriterion("nurseDeptName between", value1, value2,
					"nursedeptname");
			return (Criteria) this;
		}

		public Criteria andNursedeptnameNotBetween(String value1, String value2) {
			addCriterion("nurseDeptName not between", value1, value2,
					"nursedeptname");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_dosage
	 * @mbggenerated  Thu Nov 21 13:43:52 CST 2013
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table pension_dosage
     *
     * @mbggenerated do_not_delete_during_merge Thu Aug 29 15:47:42 CST 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}