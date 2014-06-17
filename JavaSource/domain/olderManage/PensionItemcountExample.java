package domain.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PensionItemcountExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	public PensionItemcountExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
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

		public Criteria andItemIdIsNull() {
			addCriterion("item_id is null");
			return (Criteria) this;
		}

		public Criteria andItemIdIsNotNull() {
			addCriterion("item_id is not null");
			return (Criteria) this;
		}

		public Criteria andItemIdEqualTo(Long value) {
			addCriterion("item_id =", value, "itemId");
			return (Criteria) this;
		}

		public Criteria andItemIdNotEqualTo(Long value) {
			addCriterion("item_id <>", value, "itemId");
			return (Criteria) this;
		}

		public Criteria andItemIdGreaterThan(Long value) {
			addCriterion("item_id >", value, "itemId");
			return (Criteria) this;
		}

		public Criteria andItemIdGreaterThanOrEqualTo(Long value) {
			addCriterion("item_id >=", value, "itemId");
			return (Criteria) this;
		}

		public Criteria andItemIdLessThan(Long value) {
			addCriterion("item_id <", value, "itemId");
			return (Criteria) this;
		}

		public Criteria andItemIdLessThanOrEqualTo(Long value) {
			addCriterion("item_id <=", value, "itemId");
			return (Criteria) this;
		}

		public Criteria andItemIdIn(List<Long> values) {
			addCriterion("item_id in", values, "itemId");
			return (Criteria) this;
		}

		public Criteria andItemIdNotIn(List<Long> values) {
			addCriterion("item_id not in", values, "itemId");
			return (Criteria) this;
		}

		public Criteria andItemIdBetween(Long value1, Long value2) {
			addCriterion("item_id between", value1, value2, "itemId");
			return (Criteria) this;
		}

		public Criteria andItemIdNotBetween(Long value1, Long value2) {
			addCriterion("item_id not between", value1, value2, "itemId");
			return (Criteria) this;
		}

		public Criteria andGroupIdIsNull() {
			addCriterion("group_id is null");
			return (Criteria) this;
		}

		public Criteria andGroupIdIsNotNull() {
			addCriterion("group_id is not null");
			return (Criteria) this;
		}

		public Criteria andGroupIdEqualTo(Long value) {
			addCriterion("group_id =", value, "groupId");
			return (Criteria) this;
		}

		public Criteria andGroupIdNotEqualTo(Long value) {
			addCriterion("group_id <>", value, "groupId");
			return (Criteria) this;
		}

		public Criteria andGroupIdGreaterThan(Long value) {
			addCriterion("group_id >", value, "groupId");
			return (Criteria) this;
		}

		public Criteria andGroupIdGreaterThanOrEqualTo(Long value) {
			addCriterion("group_id >=", value, "groupId");
			return (Criteria) this;
		}

		public Criteria andGroupIdLessThan(Long value) {
			addCriterion("group_id <", value, "groupId");
			return (Criteria) this;
		}

		public Criteria andGroupIdLessThanOrEqualTo(Long value) {
			addCriterion("group_id <=", value, "groupId");
			return (Criteria) this;
		}

		public Criteria andGroupIdIn(List<Long> values) {
			addCriterion("group_id in", values, "groupId");
			return (Criteria) this;
		}

		public Criteria andGroupIdNotIn(List<Long> values) {
			addCriterion("group_id not in", values, "groupId");
			return (Criteria) this;
		}

		public Criteria andGroupIdBetween(Long value1, Long value2) {
			addCriterion("group_id between", value1, value2, "groupId");
			return (Criteria) this;
		}

		public Criteria andGroupIdNotBetween(Long value1, Long value2) {
			addCriterion("group_id not between", value1, value2, "groupId");
			return (Criteria) this;
		}

		public Criteria andNumberIsNull() {
			addCriterion("number is null");
			return (Criteria) this;
		}

		public Criteria andNumberIsNotNull() {
			addCriterion("number is not null");
			return (Criteria) this;
		}

		public Criteria andNumberEqualTo(Integer value) {
			addCriterion("number =", value, "number");
			return (Criteria) this;
		}

		public Criteria andNumberNotEqualTo(Integer value) {
			addCriterion("number <>", value, "number");
			return (Criteria) this;
		}

		public Criteria andNumberGreaterThan(Integer value) {
			addCriterion("number >", value, "number");
			return (Criteria) this;
		}

		public Criteria andNumberGreaterThanOrEqualTo(Integer value) {
			addCriterion("number >=", value, "number");
			return (Criteria) this;
		}

		public Criteria andNumberLessThan(Integer value) {
			addCriterion("number <", value, "number");
			return (Criteria) this;
		}

		public Criteria andNumberLessThanOrEqualTo(Integer value) {
			addCriterion("number <=", value, "number");
			return (Criteria) this;
		}

		public Criteria andNumberIn(List<Integer> values) {
			addCriterion("number in", values, "number");
			return (Criteria) this;
		}

		public Criteria andNumberNotIn(List<Integer> values) {
			addCriterion("number not in", values, "number");
			return (Criteria) this;
		}

		public Criteria andNumberBetween(Integer value1, Integer value2) {
			addCriterion("number between", value1, value2, "number");
			return (Criteria) this;
		}

		public Criteria andNumberNotBetween(Integer value1, Integer value2) {
			addCriterion("number not between", value1, value2, "number");
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
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_itemcount
	 * @mbggenerated  Mon Sep 09 19:04:16 CST 2013
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
     * This class corresponds to the database table pension_itemcount
     *
     * @mbggenerated do_not_delete_during_merge Thu Aug 29 15:47:42 CST 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}