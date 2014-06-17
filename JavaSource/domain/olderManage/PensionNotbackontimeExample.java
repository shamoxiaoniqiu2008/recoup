package domain.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PensionNotbackontimeExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	public PensionNotbackontimeExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
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

		public Criteria andOlderIdIsNull() {
			addCriterion("older_id is null");
			return (Criteria) this;
		}

		public Criteria andOlderIdIsNotNull() {
			addCriterion("older_id is not null");
			return (Criteria) this;
		}

		public Criteria andOlderIdEqualTo(Long value) {
			addCriterion("older_id =", value, "olderId");
			return (Criteria) this;
		}

		public Criteria andOlderIdNotEqualTo(Long value) {
			addCriterion("older_id <>", value, "olderId");
			return (Criteria) this;
		}

		public Criteria andOlderIdGreaterThan(Long value) {
			addCriterion("older_id >", value, "olderId");
			return (Criteria) this;
		}

		public Criteria andOlderIdGreaterThanOrEqualTo(Long value) {
			addCriterion("older_id >=", value, "olderId");
			return (Criteria) this;
		}

		public Criteria andOlderIdLessThan(Long value) {
			addCriterion("older_id <", value, "olderId");
			return (Criteria) this;
		}

		public Criteria andOlderIdLessThanOrEqualTo(Long value) {
			addCriterion("older_id <=", value, "olderId");
			return (Criteria) this;
		}

		public Criteria andOlderIdIn(List<Long> values) {
			addCriterion("older_id in", values, "olderId");
			return (Criteria) this;
		}

		public Criteria andOlderIdNotIn(List<Long> values) {
			addCriterion("older_id not in", values, "olderId");
			return (Criteria) this;
		}

		public Criteria andOlderIdBetween(Long value1, Long value2) {
			addCriterion("older_id between", value1, value2, "olderId");
			return (Criteria) this;
		}

		public Criteria andOlderIdNotBetween(Long value1, Long value2) {
			addCriterion("older_id not between", value1, value2, "olderId");
			return (Criteria) this;
		}

		public Criteria andLeaveIdIsNull() {
			addCriterion("leave_id is null");
			return (Criteria) this;
		}

		public Criteria andLeaveIdIsNotNull() {
			addCriterion("leave_id is not null");
			return (Criteria) this;
		}

		public Criteria andLeaveIdEqualTo(Long value) {
			addCriterion("leave_id =", value, "leaveId");
			return (Criteria) this;
		}

		public Criteria andLeaveIdNotEqualTo(Long value) {
			addCriterion("leave_id <>", value, "leaveId");
			return (Criteria) this;
		}

		public Criteria andLeaveIdGreaterThan(Long value) {
			addCriterion("leave_id >", value, "leaveId");
			return (Criteria) this;
		}

		public Criteria andLeaveIdGreaterThanOrEqualTo(Long value) {
			addCriterion("leave_id >=", value, "leaveId");
			return (Criteria) this;
		}

		public Criteria andLeaveIdLessThan(Long value) {
			addCriterion("leave_id <", value, "leaveId");
			return (Criteria) this;
		}

		public Criteria andLeaveIdLessThanOrEqualTo(Long value) {
			addCriterion("leave_id <=", value, "leaveId");
			return (Criteria) this;
		}

		public Criteria andLeaveIdIn(List<Long> values) {
			addCriterion("leave_id in", values, "leaveId");
			return (Criteria) this;
		}

		public Criteria andLeaveIdNotIn(List<Long> values) {
			addCriterion("leave_id not in", values, "leaveId");
			return (Criteria) this;
		}

		public Criteria andLeaveIdBetween(Long value1, Long value2) {
			addCriterion("leave_id between", value1, value2, "leaveId");
			return (Criteria) this;
		}

		public Criteria andLeaveIdNotBetween(Long value1, Long value2) {
			addCriterion("leave_id not between", value1, value2, "leaveId");
			return (Criteria) this;
		}

		public Criteria andReasonIsNull() {
			addCriterion("reason is null");
			return (Criteria) this;
		}

		public Criteria andReasonIsNotNull() {
			addCriterion("reason is not null");
			return (Criteria) this;
		}

		public Criteria andReasonEqualTo(String value) {
			addCriterion("reason =", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonNotEqualTo(String value) {
			addCriterion("reason <>", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonGreaterThan(String value) {
			addCriterion("reason >", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonGreaterThanOrEqualTo(String value) {
			addCriterion("reason >=", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonLessThan(String value) {
			addCriterion("reason <", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonLessThanOrEqualTo(String value) {
			addCriterion("reason <=", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonLike(String value) {
			addCriterion("reason like", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonNotLike(String value) {
			addCriterion("reason not like", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonIn(List<String> values) {
			addCriterion("reason in", values, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonNotIn(List<String> values) {
			addCriterion("reason not in", values, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonBetween(String value1, String value2) {
			addCriterion("reason between", value1, value2, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonNotBetween(String value1, String value2) {
			addCriterion("reason not between", value1, value2, "reason");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeIsNull() {
			addCriterion("oldBackTime is null");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeIsNotNull() {
			addCriterion("oldBackTime is not null");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeEqualTo(Date value) {
			addCriterion("oldBackTime =", value, "oldbacktime");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeNotEqualTo(Date value) {
			addCriterion("oldBackTime <>", value, "oldbacktime");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeGreaterThan(Date value) {
			addCriterion("oldBackTime >", value, "oldbacktime");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeGreaterThanOrEqualTo(Date value) {
			addCriterion("oldBackTime >=", value, "oldbacktime");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeLessThan(Date value) {
			addCriterion("oldBackTime <", value, "oldbacktime");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeLessThanOrEqualTo(Date value) {
			addCriterion("oldBackTime <=", value, "oldbacktime");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeIn(List<Date> values) {
			addCriterion("oldBackTime in", values, "oldbacktime");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeNotIn(List<Date> values) {
			addCriterion("oldBackTime not in", values, "oldbacktime");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeBetween(Date value1, Date value2) {
			addCriterion("oldBackTime between", value1, value2, "oldbacktime");
			return (Criteria) this;
		}

		public Criteria andOldbacktimeNotBetween(Date value1, Date value2) {
			addCriterion("oldBackTime not between", value1, value2,
					"oldbacktime");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeIsNull() {
			addCriterion("newBackTime is null");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeIsNotNull() {
			addCriterion("newBackTime is not null");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeEqualTo(Date value) {
			addCriterion("newBackTime =", value, "newbacktime");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeNotEqualTo(Date value) {
			addCriterion("newBackTime <>", value, "newbacktime");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeGreaterThan(Date value) {
			addCriterion("newBackTime >", value, "newbacktime");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeGreaterThanOrEqualTo(Date value) {
			addCriterion("newBackTime >=", value, "newbacktime");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeLessThan(Date value) {
			addCriterion("newBackTime <", value, "newbacktime");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeLessThanOrEqualTo(Date value) {
			addCriterion("newBackTime <=", value, "newbacktime");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeIn(List<Date> values) {
			addCriterion("newBackTime in", values, "newbacktime");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeNotIn(List<Date> values) {
			addCriterion("newBackTime not in", values, "newbacktime");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeBetween(Date value1, Date value2) {
			addCriterion("newBackTime between", value1, value2, "newbacktime");
			return (Criteria) this;
		}

		public Criteria andNewbacktimeNotBetween(Date value1, Date value2) {
			addCriterion("newBackTime not between", value1, value2,
					"newbacktime");
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
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_notbackontime
	 * @mbggenerated  Fri Aug 30 11:28:06 CST 2013
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
     * This class corresponds to the database table pension_notbackontime
     *
     * @mbggenerated do_not_delete_during_merge Wed Aug 28 11:01:28 CST 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}