package domain.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PensionHospitalizegroupExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	public PensionHospitalizegroupExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
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

		public Criteria andManagerIdIsNull() {
			addCriterion("manager_id is null");
			return (Criteria) this;
		}

		public Criteria andManagerIdIsNotNull() {
			addCriterion("manager_id is not null");
			return (Criteria) this;
		}

		public Criteria andManagerIdEqualTo(Long value) {
			addCriterion("manager_id =", value, "managerId");
			return (Criteria) this;
		}

		public Criteria andManagerIdNotEqualTo(Long value) {
			addCriterion("manager_id <>", value, "managerId");
			return (Criteria) this;
		}

		public Criteria andManagerIdGreaterThan(Long value) {
			addCriterion("manager_id >", value, "managerId");
			return (Criteria) this;
		}

		public Criteria andManagerIdGreaterThanOrEqualTo(Long value) {
			addCriterion("manager_id >=", value, "managerId");
			return (Criteria) this;
		}

		public Criteria andManagerIdLessThan(Long value) {
			addCriterion("manager_id <", value, "managerId");
			return (Criteria) this;
		}

		public Criteria andManagerIdLessThanOrEqualTo(Long value) {
			addCriterion("manager_id <=", value, "managerId");
			return (Criteria) this;
		}

		public Criteria andManagerIdIn(List<Long> values) {
			addCriterion("manager_id in", values, "managerId");
			return (Criteria) this;
		}

		public Criteria andManagerIdNotIn(List<Long> values) {
			addCriterion("manager_id not in", values, "managerId");
			return (Criteria) this;
		}

		public Criteria andManagerIdBetween(Long value1, Long value2) {
			addCriterion("manager_id between", value1, value2, "managerId");
			return (Criteria) this;
		}

		public Criteria andManagerIdNotBetween(Long value1, Long value2) {
			addCriterion("manager_id not between", value1, value2, "managerId");
			return (Criteria) this;
		}

		public Criteria andCarnumberIsNull() {
			addCriterion("carNumber is null");
			return (Criteria) this;
		}

		public Criteria andCarnumberIsNotNull() {
			addCriterion("carNumber is not null");
			return (Criteria) this;
		}

		public Criteria andCarnumberEqualTo(String value) {
			addCriterion("carNumber =", value, "carnumber");
			return (Criteria) this;
		}

		public Criteria andCarnumberNotEqualTo(String value) {
			addCriterion("carNumber <>", value, "carnumber");
			return (Criteria) this;
		}

		public Criteria andCarnumberGreaterThan(String value) {
			addCriterion("carNumber >", value, "carnumber");
			return (Criteria) this;
		}

		public Criteria andCarnumberGreaterThanOrEqualTo(String value) {
			addCriterion("carNumber >=", value, "carnumber");
			return (Criteria) this;
		}

		public Criteria andCarnumberLessThan(String value) {
			addCriterion("carNumber <", value, "carnumber");
			return (Criteria) this;
		}

		public Criteria andCarnumberLessThanOrEqualTo(String value) {
			addCriterion("carNumber <=", value, "carnumber");
			return (Criteria) this;
		}

		public Criteria andCarnumberLike(String value) {
			addCriterion("carNumber like", value, "carnumber");
			return (Criteria) this;
		}

		public Criteria andCarnumberNotLike(String value) {
			addCriterion("carNumber not like", value, "carnumber");
			return (Criteria) this;
		}

		public Criteria andCarnumberIn(List<String> values) {
			addCriterion("carNumber in", values, "carnumber");
			return (Criteria) this;
		}

		public Criteria andCarnumberNotIn(List<String> values) {
			addCriterion("carNumber not in", values, "carnumber");
			return (Criteria) this;
		}

		public Criteria andCarnumberBetween(String value1, String value2) {
			addCriterion("carNumber between", value1, value2, "carnumber");
			return (Criteria) this;
		}

		public Criteria andCarnumberNotBetween(String value1, String value2) {
			addCriterion("carNumber not between", value1, value2, "carnumber");
			return (Criteria) this;
		}

		public Criteria andLeavetimeIsNull() {
			addCriterion("leaveTime is null");
			return (Criteria) this;
		}

		public Criteria andLeavetimeIsNotNull() {
			addCriterion("leaveTime is not null");
			return (Criteria) this;
		}

		public Criteria andLeavetimeEqualTo(Date value) {
			addCriterion("leaveTime =", value, "leavetime");
			return (Criteria) this;
		}

		public Criteria andLeavetimeNotEqualTo(Date value) {
			addCriterion("leaveTime <>", value, "leavetime");
			return (Criteria) this;
		}

		public Criteria andLeavetimeGreaterThan(Date value) {
			addCriterion("leaveTime >", value, "leavetime");
			return (Criteria) this;
		}

		public Criteria andLeavetimeGreaterThanOrEqualTo(Date value) {
			addCriterion("leaveTime >=", value, "leavetime");
			return (Criteria) this;
		}

		public Criteria andLeavetimeLessThan(Date value) {
			addCriterion("leaveTime <", value, "leavetime");
			return (Criteria) this;
		}

		public Criteria andLeavetimeLessThanOrEqualTo(Date value) {
			addCriterion("leaveTime <=", value, "leavetime");
			return (Criteria) this;
		}

		public Criteria andLeavetimeIn(List<Date> values) {
			addCriterion("leaveTime in", values, "leavetime");
			return (Criteria) this;
		}

		public Criteria andLeavetimeNotIn(List<Date> values) {
			addCriterion("leaveTime not in", values, "leavetime");
			return (Criteria) this;
		}

		public Criteria andLeavetimeBetween(Date value1, Date value2) {
			addCriterion("leaveTime between", value1, value2, "leavetime");
			return (Criteria) this;
		}

		public Criteria andLeavetimeNotBetween(Date value1, Date value2) {
			addCriterion("leaveTime not between", value1, value2, "leavetime");
			return (Criteria) this;
		}

		public Criteria andBacktimeIsNull() {
			addCriterion("backTime is null");
			return (Criteria) this;
		}

		public Criteria andBacktimeIsNotNull() {
			addCriterion("backTime is not null");
			return (Criteria) this;
		}

		public Criteria andBacktimeEqualTo(Date value) {
			addCriterion("backTime =", value, "backtime");
			return (Criteria) this;
		}

		public Criteria andBacktimeNotEqualTo(Date value) {
			addCriterion("backTime <>", value, "backtime");
			return (Criteria) this;
		}

		public Criteria andBacktimeGreaterThan(Date value) {
			addCriterion("backTime >", value, "backtime");
			return (Criteria) this;
		}

		public Criteria andBacktimeGreaterThanOrEqualTo(Date value) {
			addCriterion("backTime >=", value, "backtime");
			return (Criteria) this;
		}

		public Criteria andBacktimeLessThan(Date value) {
			addCriterion("backTime <", value, "backtime");
			return (Criteria) this;
		}

		public Criteria andBacktimeLessThanOrEqualTo(Date value) {
			addCriterion("backTime <=", value, "backtime");
			return (Criteria) this;
		}

		public Criteria andBacktimeIn(List<Date> values) {
			addCriterion("backTime in", values, "backtime");
			return (Criteria) this;
		}

		public Criteria andBacktimeNotIn(List<Date> values) {
			addCriterion("backTime not in", values, "backtime");
			return (Criteria) this;
		}

		public Criteria andBacktimeBetween(Date value1, Date value2) {
			addCriterion("backTime between", value1, value2, "backtime");
			return (Criteria) this;
		}

		public Criteria andBacktimeNotBetween(Date value1, Date value2) {
			addCriterion("backTime not between", value1, value2, "backtime");
			return (Criteria) this;
		}

		public Criteria andCountsIsNull() {
			addCriterion("counts is null");
			return (Criteria) this;
		}

		public Criteria andCountsIsNotNull() {
			addCriterion("counts is not null");
			return (Criteria) this;
		}

		public Criteria andCountsEqualTo(Integer value) {
			addCriterion("counts =", value, "counts");
			return (Criteria) this;
		}

		public Criteria andCountsNotEqualTo(Integer value) {
			addCriterion("counts <>", value, "counts");
			return (Criteria) this;
		}

		public Criteria andCountsGreaterThan(Integer value) {
			addCriterion("counts >", value, "counts");
			return (Criteria) this;
		}

		public Criteria andCountsGreaterThanOrEqualTo(Integer value) {
			addCriterion("counts >=", value, "counts");
			return (Criteria) this;
		}

		public Criteria andCountsLessThan(Integer value) {
			addCriterion("counts <", value, "counts");
			return (Criteria) this;
		}

		public Criteria andCountsLessThanOrEqualTo(Integer value) {
			addCriterion("counts <=", value, "counts");
			return (Criteria) this;
		}

		public Criteria andCountsIn(List<Integer> values) {
			addCriterion("counts in", values, "counts");
			return (Criteria) this;
		}

		public Criteria andCountsNotIn(List<Integer> values) {
			addCriterion("counts not in", values, "counts");
			return (Criteria) this;
		}

		public Criteria andCountsBetween(Integer value1, Integer value2) {
			addCriterion("counts between", value1, value2, "counts");
			return (Criteria) this;
		}

		public Criteria andCountsNotBetween(Integer value1, Integer value2) {
			addCriterion("counts not between", value1, value2, "counts");
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

		public Criteria andGeneratedIsNull() {
			addCriterion("generated is null");
			return (Criteria) this;
		}

		public Criteria andGeneratedIsNotNull() {
			addCriterion("generated is not null");
			return (Criteria) this;
		}

		public Criteria andGeneratedEqualTo(Integer value) {
			addCriterion("generated =", value, "generated");
			return (Criteria) this;
		}

		public Criteria andGeneratedNotEqualTo(Integer value) {
			addCriterion("generated <>", value, "generated");
			return (Criteria) this;
		}

		public Criteria andGeneratedGreaterThan(Integer value) {
			addCriterion("generated >", value, "generated");
			return (Criteria) this;
		}

		public Criteria andGeneratedGreaterThanOrEqualTo(Integer value) {
			addCriterion("generated >=", value, "generated");
			return (Criteria) this;
		}

		public Criteria andGeneratedLessThan(Integer value) {
			addCriterion("generated <", value, "generated");
			return (Criteria) this;
		}

		public Criteria andGeneratedLessThanOrEqualTo(Integer value) {
			addCriterion("generated <=", value, "generated");
			return (Criteria) this;
		}

		public Criteria andGeneratedIn(List<Integer> values) {
			addCriterion("generated in", values, "generated");
			return (Criteria) this;
		}

		public Criteria andGeneratedNotIn(List<Integer> values) {
			addCriterion("generated not in", values, "generated");
			return (Criteria) this;
		}

		public Criteria andGeneratedBetween(Integer value1, Integer value2) {
			addCriterion("generated between", value1, value2, "generated");
			return (Criteria) this;
		}

		public Criteria andGeneratedNotBetween(Integer value1, Integer value2) {
			addCriterion("generated not between", value1, value2, "generated");
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

		public Criteria andSendedIsNull() {
			addCriterion("sended is null");
			return (Criteria) this;
		}

		public Criteria andSendedIsNotNull() {
			addCriterion("sended is not null");
			return (Criteria) this;
		}

		public Criteria andSendedEqualTo(Integer value) {
			addCriterion("sended =", value, "sended");
			return (Criteria) this;
		}

		public Criteria andSendedNotEqualTo(Integer value) {
			addCriterion("sended <>", value, "sended");
			return (Criteria) this;
		}

		public Criteria andSendedGreaterThan(Integer value) {
			addCriterion("sended >", value, "sended");
			return (Criteria) this;
		}

		public Criteria andSendedGreaterThanOrEqualTo(Integer value) {
			addCriterion("sended >=", value, "sended");
			return (Criteria) this;
		}

		public Criteria andSendedLessThan(Integer value) {
			addCriterion("sended <", value, "sended");
			return (Criteria) this;
		}

		public Criteria andSendedLessThanOrEqualTo(Integer value) {
			addCriterion("sended <=", value, "sended");
			return (Criteria) this;
		}

		public Criteria andSendedIn(List<Integer> values) {
			addCriterion("sended in", values, "sended");
			return (Criteria) this;
		}

		public Criteria andSendedNotIn(List<Integer> values) {
			addCriterion("sended not in", values, "sended");
			return (Criteria) this;
		}

		public Criteria andSendedBetween(Integer value1, Integer value2) {
			addCriterion("sended between", value1, value2, "sended");
			return (Criteria) this;
		}

		public Criteria andSendedNotBetween(Integer value1, Integer value2) {
			addCriterion("sended not between", value1, value2, "sended");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_hospitalizegroup
	 * @mbggenerated  Tue Nov 05 14:57:18 CST 2013
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
     * This class corresponds to the database table pension_hospitalizegroup
     *
     * @mbggenerated do_not_delete_during_merge Thu Aug 29 15:47:42 CST 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}