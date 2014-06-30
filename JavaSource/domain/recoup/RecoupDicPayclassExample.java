package domain.recoup;

import java.util.ArrayList;
import java.util.List;

public class RecoupDicPayclassExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	public RecoupDicPayclassExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
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

		public Criteria andClassNameIsNull() {
			addCriterion("class_name is null");
			return (Criteria) this;
		}

		public Criteria andClassNameIsNotNull() {
			addCriterion("class_name is not null");
			return (Criteria) this;
		}

		public Criteria andClassNameEqualTo(String value) {
			addCriterion("class_name =", value, "className");
			return (Criteria) this;
		}

		public Criteria andClassNameNotEqualTo(String value) {
			addCriterion("class_name <>", value, "className");
			return (Criteria) this;
		}

		public Criteria andClassNameGreaterThan(String value) {
			addCriterion("class_name >", value, "className");
			return (Criteria) this;
		}

		public Criteria andClassNameGreaterThanOrEqualTo(String value) {
			addCriterion("class_name >=", value, "className");
			return (Criteria) this;
		}

		public Criteria andClassNameLessThan(String value) {
			addCriterion("class_name <", value, "className");
			return (Criteria) this;
		}

		public Criteria andClassNameLessThanOrEqualTo(String value) {
			addCriterion("class_name <=", value, "className");
			return (Criteria) this;
		}

		public Criteria andClassNameLike(String value) {
			addCriterion("class_name like", value, "className");
			return (Criteria) this;
		}

		public Criteria andClassNameNotLike(String value) {
			addCriterion("class_name not like", value, "className");
			return (Criteria) this;
		}

		public Criteria andClassNameIn(List<String> values) {
			addCriterion("class_name in", values, "className");
			return (Criteria) this;
		}

		public Criteria andClassNameNotIn(List<String> values) {
			addCriterion("class_name not in", values, "className");
			return (Criteria) this;
		}

		public Criteria andClassNameBetween(String value1, String value2) {
			addCriterion("class_name between", value1, value2, "className");
			return (Criteria) this;
		}

		public Criteria andClassNameNotBetween(String value1, String value2) {
			addCriterion("class_name not between", value1, value2, "className");
			return (Criteria) this;
		}

		public Criteria andInputCodeIsNull() {
			addCriterion("input_code is null");
			return (Criteria) this;
		}

		public Criteria andInputCodeIsNotNull() {
			addCriterion("input_code is not null");
			return (Criteria) this;
		}

		public Criteria andInputCodeEqualTo(String value) {
			addCriterion("input_code =", value, "inputCode");
			return (Criteria) this;
		}

		public Criteria andInputCodeNotEqualTo(String value) {
			addCriterion("input_code <>", value, "inputCode");
			return (Criteria) this;
		}

		public Criteria andInputCodeGreaterThan(String value) {
			addCriterion("input_code >", value, "inputCode");
			return (Criteria) this;
		}

		public Criteria andInputCodeGreaterThanOrEqualTo(String value) {
			addCriterion("input_code >=", value, "inputCode");
			return (Criteria) this;
		}

		public Criteria andInputCodeLessThan(String value) {
			addCriterion("input_code <", value, "inputCode");
			return (Criteria) this;
		}

		public Criteria andInputCodeLessThanOrEqualTo(String value) {
			addCriterion("input_code <=", value, "inputCode");
			return (Criteria) this;
		}

		public Criteria andInputCodeLike(String value) {
			addCriterion("input_code like", value, "inputCode");
			return (Criteria) this;
		}

		public Criteria andInputCodeNotLike(String value) {
			addCriterion("input_code not like", value, "inputCode");
			return (Criteria) this;
		}

		public Criteria andInputCodeIn(List<String> values) {
			addCriterion("input_code in", values, "inputCode");
			return (Criteria) this;
		}

		public Criteria andInputCodeNotIn(List<String> values) {
			addCriterion("input_code not in", values, "inputCode");
			return (Criteria) this;
		}

		public Criteria andInputCodeBetween(String value1, String value2) {
			addCriterion("input_code between", value1, value2, "inputCode");
			return (Criteria) this;
		}

		public Criteria andInputCodeNotBetween(String value1, String value2) {
			addCriterion("input_code not between", value1, value2, "inputCode");
			return (Criteria) this;
		}

		public Criteria andNoteIsNull() {
			addCriterion("note is null");
			return (Criteria) this;
		}

		public Criteria andNoteIsNotNull() {
			addCriterion("note is not null");
			return (Criteria) this;
		}

		public Criteria andNoteEqualTo(String value) {
			addCriterion("note =", value, "note");
			return (Criteria) this;
		}

		public Criteria andNoteNotEqualTo(String value) {
			addCriterion("note <>", value, "note");
			return (Criteria) this;
		}

		public Criteria andNoteGreaterThan(String value) {
			addCriterion("note >", value, "note");
			return (Criteria) this;
		}

		public Criteria andNoteGreaterThanOrEqualTo(String value) {
			addCriterion("note >=", value, "note");
			return (Criteria) this;
		}

		public Criteria andNoteLessThan(String value) {
			addCriterion("note <", value, "note");
			return (Criteria) this;
		}

		public Criteria andNoteLessThanOrEqualTo(String value) {
			addCriterion("note <=", value, "note");
			return (Criteria) this;
		}

		public Criteria andNoteLike(String value) {
			addCriterion("note like", value, "note");
			return (Criteria) this;
		}

		public Criteria andNoteNotLike(String value) {
			addCriterion("note not like", value, "note");
			return (Criteria) this;
		}

		public Criteria andNoteIn(List<String> values) {
			addCriterion("note in", values, "note");
			return (Criteria) this;
		}

		public Criteria andNoteNotIn(List<String> values) {
			addCriterion("note not in", values, "note");
			return (Criteria) this;
		}

		public Criteria andNoteBetween(String value1, String value2) {
			addCriterion("note between", value1, value2, "note");
			return (Criteria) this;
		}

		public Criteria andNoteNotBetween(String value1, String value2) {
			addCriterion("note not between", value1, value2, "note");
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

		public Criteria andSortByIsNull() {
			addCriterion("sort_by is null");
			return (Criteria) this;
		}

		public Criteria andSortByIsNotNull() {
			addCriterion("sort_by is not null");
			return (Criteria) this;
		}

		public Criteria andSortByEqualTo(Integer value) {
			addCriterion("sort_by =", value, "sortBy");
			return (Criteria) this;
		}

		public Criteria andSortByNotEqualTo(Integer value) {
			addCriterion("sort_by <>", value, "sortBy");
			return (Criteria) this;
		}

		public Criteria andSortByGreaterThan(Integer value) {
			addCriterion("sort_by >", value, "sortBy");
			return (Criteria) this;
		}

		public Criteria andSortByGreaterThanOrEqualTo(Integer value) {
			addCriterion("sort_by >=", value, "sortBy");
			return (Criteria) this;
		}

		public Criteria andSortByLessThan(Integer value) {
			addCriterion("sort_by <", value, "sortBy");
			return (Criteria) this;
		}

		public Criteria andSortByLessThanOrEqualTo(Integer value) {
			addCriterion("sort_by <=", value, "sortBy");
			return (Criteria) this;
		}

		public Criteria andSortByIn(List<Integer> values) {
			addCriterion("sort_by in", values, "sortBy");
			return (Criteria) this;
		}

		public Criteria andSortByNotIn(List<Integer> values) {
			addCriterion("sort_by not in", values, "sortBy");
			return (Criteria) this;
		}

		public Criteria andSortByBetween(Integer value1, Integer value2) {
			addCriterion("sort_by between", value1, value2, "sortBy");
			return (Criteria) this;
		}

		public Criteria andSortByNotBetween(Integer value1, Integer value2) {
			addCriterion("sort_by not between", value1, value2, "sortBy");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table recoup_dic_payclass
	 * @mbggenerated  Tue Jul 01 06:26:28 CST 2014
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
     * This class corresponds to the database table recoup_dic_payclass
     *
     * @mbggenerated do_not_delete_during_merge Mon Jun 30 22:06:17 CST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}