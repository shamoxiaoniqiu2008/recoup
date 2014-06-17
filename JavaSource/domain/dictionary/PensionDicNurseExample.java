package domain.dictionary;

import java.util.ArrayList;
import java.util.List;

public class PensionDicNurseExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public PensionDicNurseExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
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

		public Criteria andInputcodeIsNull() {
			addCriterion("inputCode is null");
			return (Criteria) this;
		}

		public Criteria andInputcodeIsNotNull() {
			addCriterion("inputCode is not null");
			return (Criteria) this;
		}

		public Criteria andInputcodeEqualTo(String value) {
			addCriterion("inputCode =", value, "inputcode");
			return (Criteria) this;
		}

		public Criteria andInputcodeNotEqualTo(String value) {
			addCriterion("inputCode <>", value, "inputcode");
			return (Criteria) this;
		}

		public Criteria andInputcodeGreaterThan(String value) {
			addCriterion("inputCode >", value, "inputcode");
			return (Criteria) this;
		}

		public Criteria andInputcodeGreaterThanOrEqualTo(String value) {
			addCriterion("inputCode >=", value, "inputcode");
			return (Criteria) this;
		}

		public Criteria andInputcodeLessThan(String value) {
			addCriterion("inputCode <", value, "inputcode");
			return (Criteria) this;
		}

		public Criteria andInputcodeLessThanOrEqualTo(String value) {
			addCriterion("inputCode <=", value, "inputcode");
			return (Criteria) this;
		}

		public Criteria andInputcodeLike(String value) {
			addCriterion("inputCode like", value, "inputcode");
			return (Criteria) this;
		}

		public Criteria andInputcodeNotLike(String value) {
			addCriterion("inputCode not like", value, "inputcode");
			return (Criteria) this;
		}

		public Criteria andInputcodeIn(List<String> values) {
			addCriterion("inputCode in", values, "inputcode");
			return (Criteria) this;
		}

		public Criteria andInputcodeNotIn(List<String> values) {
			addCriterion("inputCode not in", values, "inputcode");
			return (Criteria) this;
		}

		public Criteria andInputcodeBetween(String value1, String value2) {
			addCriterion("inputCode between", value1, value2, "inputcode");
			return (Criteria) this;
		}

		public Criteria andInputcodeNotBetween(String value1, String value2) {
			addCriterion("inputCode not between", value1, value2, "inputcode");
			return (Criteria) this;
		}

		public Criteria andLevelIsNull() {
			addCriterion("level is null");
			return (Criteria) this;
		}

		public Criteria andLevelIsNotNull() {
			addCriterion("level is not null");
			return (Criteria) this;
		}

		public Criteria andLevelEqualTo(String value) {
			addCriterion("level =", value, "level");
			return (Criteria) this;
		}

		public Criteria andLevelNotEqualTo(String value) {
			addCriterion("level <>", value, "level");
			return (Criteria) this;
		}

		public Criteria andLevelGreaterThan(String value) {
			addCriterion("level >", value, "level");
			return (Criteria) this;
		}

		public Criteria andLevelGreaterThanOrEqualTo(String value) {
			addCriterion("level >=", value, "level");
			return (Criteria) this;
		}

		public Criteria andLevelLessThan(String value) {
			addCriterion("level <", value, "level");
			return (Criteria) this;
		}

		public Criteria andLevelLessThanOrEqualTo(String value) {
			addCriterion("level <=", value, "level");
			return (Criteria) this;
		}

		public Criteria andLevelLike(String value) {
			addCriterion("level like", value, "level");
			return (Criteria) this;
		}

		public Criteria andLevelNotLike(String value) {
			addCriterion("level not like", value, "level");
			return (Criteria) this;
		}

		public Criteria andLevelIn(List<String> values) {
			addCriterion("level in", values, "level");
			return (Criteria) this;
		}

		public Criteria andLevelNotIn(List<String> values) {
			addCriterion("level not in", values, "level");
			return (Criteria) this;
		}

		public Criteria andLevelBetween(String value1, String value2) {
			addCriterion("level between", value1, value2, "level");
			return (Criteria) this;
		}

		public Criteria andLevelNotBetween(String value1, String value2) {
			addCriterion("level not between", value1, value2, "level");
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

		public Criteria andInvalidedIsNull() {
			addCriterion("invalided is null");
			return (Criteria) this;
		}

		public Criteria andInvalidedIsNotNull() {
			addCriterion("invalided is not null");
			return (Criteria) this;
		}

		public Criteria andInvalidedEqualTo(Integer value) {
			addCriterion("invalided =", value, "invalided");
			return (Criteria) this;
		}

		public Criteria andInvalidedNotEqualTo(Integer value) {
			addCriterion("invalided <>", value, "invalided");
			return (Criteria) this;
		}

		public Criteria andInvalidedGreaterThan(Integer value) {
			addCriterion("invalided >", value, "invalided");
			return (Criteria) this;
		}

		public Criteria andInvalidedGreaterThanOrEqualTo(Integer value) {
			addCriterion("invalided >=", value, "invalided");
			return (Criteria) this;
		}

		public Criteria andInvalidedLessThan(Integer value) {
			addCriterion("invalided <", value, "invalided");
			return (Criteria) this;
		}

		public Criteria andInvalidedLessThanOrEqualTo(Integer value) {
			addCriterion("invalided <=", value, "invalided");
			return (Criteria) this;
		}

		public Criteria andInvalidedIn(List<Integer> values) {
			addCriterion("invalided in", values, "invalided");
			return (Criteria) this;
		}

		public Criteria andInvalidedNotIn(List<Integer> values) {
			addCriterion("invalided not in", values, "invalided");
			return (Criteria) this;
		}

		public Criteria andInvalidedBetween(Integer value1, Integer value2) {
			addCriterion("invalided between", value1, value2, "invalided");
			return (Criteria) this;
		}

		public Criteria andInvalidedNotBetween(Integer value1, Integer value2) {
			addCriterion("invalided not between", value1, value2, "invalided");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_dic_nurse
	 * @mbggenerated  Thu Aug 29 15:35:28 CST 2013
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
     * This class corresponds to the database table pension_dic_nurse
     *
     * @mbggenerated do_not_delete_during_merge Wed Aug 28 10:46:54 CST 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}