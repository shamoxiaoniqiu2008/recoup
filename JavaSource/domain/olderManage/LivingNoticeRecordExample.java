package domain.olderManage;

import java.util.ArrayList;
import java.util.List;

public class LivingNoticeRecordExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table living_notice_record
     * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	public LivingNoticeRecordExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
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

		public Criteria andDeptIdIsNull() {
			addCriterion("dept_id is null");
			return (Criteria) this;
		}

		public Criteria andDeptIdIsNotNull() {
			addCriterion("dept_id is not null");
			return (Criteria) this;
		}

		public Criteria andDeptIdEqualTo(Long value) {
			addCriterion("dept_id =", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdNotEqualTo(Long value) {
			addCriterion("dept_id <>", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdGreaterThan(Long value) {
			addCriterion("dept_id >", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdGreaterThanOrEqualTo(Long value) {
			addCriterion("dept_id >=", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdLessThan(Long value) {
			addCriterion("dept_id <", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdLessThanOrEqualTo(Long value) {
			addCriterion("dept_id <=", value, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdIn(List<Long> values) {
			addCriterion("dept_id in", values, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdNotIn(List<Long> values) {
			addCriterion("dept_id not in", values, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdBetween(Long value1, Long value2) {
			addCriterion("dept_id between", value1, value2, "deptId");
			return (Criteria) this;
		}

		public Criteria andDeptIdNotBetween(Long value1, Long value2) {
			addCriterion("dept_id not between", value1, value2, "deptId");
			return (Criteria) this;
		}

		public Criteria andEmpIdIsNull() {
			addCriterion("emp_id is null");
			return (Criteria) this;
		}

		public Criteria andEmpIdIsNotNull() {
			addCriterion("emp_id is not null");
			return (Criteria) this;
		}

		public Criteria andEmpIdEqualTo(Long value) {
			addCriterion("emp_id =", value, "empId");
			return (Criteria) this;
		}

		public Criteria andEmpIdNotEqualTo(Long value) {
			addCriterion("emp_id <>", value, "empId");
			return (Criteria) this;
		}

		public Criteria andEmpIdGreaterThan(Long value) {
			addCriterion("emp_id >", value, "empId");
			return (Criteria) this;
		}

		public Criteria andEmpIdGreaterThanOrEqualTo(Long value) {
			addCriterion("emp_id >=", value, "empId");
			return (Criteria) this;
		}

		public Criteria andEmpIdLessThan(Long value) {
			addCriterion("emp_id <", value, "empId");
			return (Criteria) this;
		}

		public Criteria andEmpIdLessThanOrEqualTo(Long value) {
			addCriterion("emp_id <=", value, "empId");
			return (Criteria) this;
		}

		public Criteria andEmpIdIn(List<Long> values) {
			addCriterion("emp_id in", values, "empId");
			return (Criteria) this;
		}

		public Criteria andEmpIdNotIn(List<Long> values) {
			addCriterion("emp_id not in", values, "empId");
			return (Criteria) this;
		}

		public Criteria andEmpIdBetween(Long value1, Long value2) {
			addCriterion("emp_id between", value1, value2, "empId");
			return (Criteria) this;
		}

		public Criteria andEmpIdNotBetween(Long value1, Long value2) {
			addCriterion("emp_id not between", value1, value2, "empId");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table living_notice_record
	 * @mbggenerated  Fri Feb 07 10:19:57 CST 2014
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
     * This class corresponds to the database table living_notice_record
     *
     * @mbggenerated do_not_delete_during_merge Tue Sep 10 17:28:07 CST 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}