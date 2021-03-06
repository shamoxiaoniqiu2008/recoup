package domain.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PensionQualityExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public PensionQualityExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
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

		public Criteria andQualityTypeIsNull() {
			addCriterion("quality_type is null");
			return (Criteria) this;
		}

		public Criteria andQualityTypeIsNotNull() {
			addCriterion("quality_type is not null");
			return (Criteria) this;
		}

		public Criteria andQualityTypeEqualTo(String value) {
			addCriterion("quality_type =", value, "qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityTypeNotEqualTo(String value) {
			addCriterion("quality_type <>", value, "qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityTypeGreaterThan(String value) {
			addCriterion("quality_type >", value, "qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityTypeGreaterThanOrEqualTo(String value) {
			addCriterion("quality_type >=", value, "qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityTypeLessThan(String value) {
			addCriterion("quality_type <", value, "qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityTypeLessThanOrEqualTo(String value) {
			addCriterion("quality_type <=", value, "qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityTypeLike(String value) {
			addCriterion("quality_type like", value, "qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityTypeNotLike(String value) {
			addCriterion("quality_type not like", value, "qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityTypeIn(List<String> values) {
			addCriterion("quality_type in", values, "qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityTypeNotIn(List<String> values) {
			addCriterion("quality_type not in", values, "qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityTypeBetween(String value1, String value2) {
			addCriterion("quality_type between", value1, value2, "qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityTypeNotBetween(String value1, String value2) {
			addCriterion("quality_type not between", value1, value2,
					"qualityType");
			return (Criteria) this;
		}

		public Criteria andQualityNameIsNull() {
			addCriterion("quality_name is null");
			return (Criteria) this;
		}

		public Criteria andQualityNameIsNotNull() {
			addCriterion("quality_name is not null");
			return (Criteria) this;
		}

		public Criteria andQualityNameEqualTo(String value) {
			addCriterion("quality_name =", value, "qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityNameNotEqualTo(String value) {
			addCriterion("quality_name <>", value, "qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityNameGreaterThan(String value) {
			addCriterion("quality_name >", value, "qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityNameGreaterThanOrEqualTo(String value) {
			addCriterion("quality_name >=", value, "qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityNameLessThan(String value) {
			addCriterion("quality_name <", value, "qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityNameLessThanOrEqualTo(String value) {
			addCriterion("quality_name <=", value, "qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityNameLike(String value) {
			addCriterion("quality_name like", value, "qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityNameNotLike(String value) {
			addCriterion("quality_name not like", value, "qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityNameIn(List<String> values) {
			addCriterion("quality_name in", values, "qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityNameNotIn(List<String> values) {
			addCriterion("quality_name not in", values, "qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityNameBetween(String value1, String value2) {
			addCriterion("quality_name between", value1, value2, "qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityNameNotBetween(String value1, String value2) {
			addCriterion("quality_name not between", value1, value2,
					"qualityName");
			return (Criteria) this;
		}

		public Criteria andQualityerIdIsNull() {
			addCriterion("qualityer_id is null");
			return (Criteria) this;
		}

		public Criteria andQualityerIdIsNotNull() {
			addCriterion("qualityer_id is not null");
			return (Criteria) this;
		}

		public Criteria andQualityerIdEqualTo(Long value) {
			addCriterion("qualityer_id =", value, "qualityerId");
			return (Criteria) this;
		}

		public Criteria andQualityerIdNotEqualTo(Long value) {
			addCriterion("qualityer_id <>", value, "qualityerId");
			return (Criteria) this;
		}

		public Criteria andQualityerIdGreaterThan(Long value) {
			addCriterion("qualityer_id >", value, "qualityerId");
			return (Criteria) this;
		}

		public Criteria andQualityerIdGreaterThanOrEqualTo(Long value) {
			addCriterion("qualityer_id >=", value, "qualityerId");
			return (Criteria) this;
		}

		public Criteria andQualityerIdLessThan(Long value) {
			addCriterion("qualityer_id <", value, "qualityerId");
			return (Criteria) this;
		}

		public Criteria andQualityerIdLessThanOrEqualTo(Long value) {
			addCriterion("qualityer_id <=", value, "qualityerId");
			return (Criteria) this;
		}

		public Criteria andQualityerIdIn(List<Long> values) {
			addCriterion("qualityer_id in", values, "qualityerId");
			return (Criteria) this;
		}

		public Criteria andQualityerIdNotIn(List<Long> values) {
			addCriterion("qualityer_id not in", values, "qualityerId");
			return (Criteria) this;
		}

		public Criteria andQualityerIdBetween(Long value1, Long value2) {
			addCriterion("qualityer_id between", value1, value2, "qualityerId");
			return (Criteria) this;
		}

		public Criteria andQualityerIdNotBetween(Long value1, Long value2) {
			addCriterion("qualityer_id not between", value1, value2,
					"qualityerId");
			return (Criteria) this;
		}

		public Criteria andQualityerNameIsNull() {
			addCriterion("qualityer_name is null");
			return (Criteria) this;
		}

		public Criteria andQualityerNameIsNotNull() {
			addCriterion("qualityer_name is not null");
			return (Criteria) this;
		}

		public Criteria andQualityerNameEqualTo(String value) {
			addCriterion("qualityer_name =", value, "qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityerNameNotEqualTo(String value) {
			addCriterion("qualityer_name <>", value, "qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityerNameGreaterThan(String value) {
			addCriterion("qualityer_name >", value, "qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityerNameGreaterThanOrEqualTo(String value) {
			addCriterion("qualityer_name >=", value, "qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityerNameLessThan(String value) {
			addCriterion("qualityer_name <", value, "qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityerNameLessThanOrEqualTo(String value) {
			addCriterion("qualityer_name <=", value, "qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityerNameLike(String value) {
			addCriterion("qualityer_name like", value, "qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityerNameNotLike(String value) {
			addCriterion("qualityer_name not like", value, "qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityerNameIn(List<String> values) {
			addCriterion("qualityer_name in", values, "qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityerNameNotIn(List<String> values) {
			addCriterion("qualityer_name not in", values, "qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityerNameBetween(String value1, String value2) {
			addCriterion("qualityer_name between", value1, value2,
					"qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityerNameNotBetween(String value1, String value2) {
			addCriterion("qualityer_name not between", value1, value2,
					"qualityerName");
			return (Criteria) this;
		}

		public Criteria andQualityResultIsNull() {
			addCriterion("quality_result is null");
			return (Criteria) this;
		}

		public Criteria andQualityResultIsNotNull() {
			addCriterion("quality_result is not null");
			return (Criteria) this;
		}

		public Criteria andQualityResultEqualTo(String value) {
			addCriterion("quality_result =", value, "qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityResultNotEqualTo(String value) {
			addCriterion("quality_result <>", value, "qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityResultGreaterThan(String value) {
			addCriterion("quality_result >", value, "qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityResultGreaterThanOrEqualTo(String value) {
			addCriterion("quality_result >=", value, "qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityResultLessThan(String value) {
			addCriterion("quality_result <", value, "qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityResultLessThanOrEqualTo(String value) {
			addCriterion("quality_result <=", value, "qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityResultLike(String value) {
			addCriterion("quality_result like", value, "qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityResultNotLike(String value) {
			addCriterion("quality_result not like", value, "qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityResultIn(List<String> values) {
			addCriterion("quality_result in", values, "qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityResultNotIn(List<String> values) {
			addCriterion("quality_result not in", values, "qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityResultBetween(String value1, String value2) {
			addCriterion("quality_result between", value1, value2,
					"qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityResultNotBetween(String value1, String value2) {
			addCriterion("quality_result not between", value1, value2,
					"qualityResult");
			return (Criteria) this;
		}

		public Criteria andQualityTimeIsNull() {
			addCriterion("quality_time is null");
			return (Criteria) this;
		}

		public Criteria andQualityTimeIsNotNull() {
			addCriterion("quality_time is not null");
			return (Criteria) this;
		}

		public Criteria andQualityTimeEqualTo(Date value) {
			addCriterion("quality_time =", value, "qualityTime");
			return (Criteria) this;
		}

		public Criteria andQualityTimeNotEqualTo(Date value) {
			addCriterion("quality_time <>", value, "qualityTime");
			return (Criteria) this;
		}

		public Criteria andQualityTimeGreaterThan(Date value) {
			addCriterion("quality_time >", value, "qualityTime");
			return (Criteria) this;
		}

		public Criteria andQualityTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("quality_time >=", value, "qualityTime");
			return (Criteria) this;
		}

		public Criteria andQualityTimeLessThan(Date value) {
			addCriterion("quality_time <", value, "qualityTime");
			return (Criteria) this;
		}

		public Criteria andQualityTimeLessThanOrEqualTo(Date value) {
			addCriterion("quality_time <=", value, "qualityTime");
			return (Criteria) this;
		}

		public Criteria andQualityTimeIn(List<Date> values) {
			addCriterion("quality_time in", values, "qualityTime");
			return (Criteria) this;
		}

		public Criteria andQualityTimeNotIn(List<Date> values) {
			addCriterion("quality_time not in", values, "qualityTime");
			return (Criteria) this;
		}

		public Criteria andQualityTimeBetween(Date value1, Date value2) {
			addCriterion("quality_time between", value1, value2, "qualityTime");
			return (Criteria) this;
		}

		public Criteria andQualityTimeNotBetween(Date value1, Date value2) {
			addCriterion("quality_time not between", value1, value2,
					"qualityTime");
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

		public Criteria andOlderNameIsNull() {
			addCriterion("older_name is null");
			return (Criteria) this;
		}

		public Criteria andOlderNameIsNotNull() {
			addCriterion("older_name is not null");
			return (Criteria) this;
		}

		public Criteria andOlderNameEqualTo(String value) {
			addCriterion("older_name =", value, "olderName");
			return (Criteria) this;
		}

		public Criteria andOlderNameNotEqualTo(String value) {
			addCriterion("older_name <>", value, "olderName");
			return (Criteria) this;
		}

		public Criteria andOlderNameGreaterThan(String value) {
			addCriterion("older_name >", value, "olderName");
			return (Criteria) this;
		}

		public Criteria andOlderNameGreaterThanOrEqualTo(String value) {
			addCriterion("older_name >=", value, "olderName");
			return (Criteria) this;
		}

		public Criteria andOlderNameLessThan(String value) {
			addCriterion("older_name <", value, "olderName");
			return (Criteria) this;
		}

		public Criteria andOlderNameLessThanOrEqualTo(String value) {
			addCriterion("older_name <=", value, "olderName");
			return (Criteria) this;
		}

		public Criteria andOlderNameLike(String value) {
			addCriterion("older_name like", value, "olderName");
			return (Criteria) this;
		}

		public Criteria andOlderNameNotLike(String value) {
			addCriterion("older_name not like", value, "olderName");
			return (Criteria) this;
		}

		public Criteria andOlderNameIn(List<String> values) {
			addCriterion("older_name in", values, "olderName");
			return (Criteria) this;
		}

		public Criteria andOlderNameNotIn(List<String> values) {
			addCriterion("older_name not in", values, "olderName");
			return (Criteria) this;
		}

		public Criteria andOlderNameBetween(String value1, String value2) {
			addCriterion("older_name between", value1, value2, "olderName");
			return (Criteria) this;
		}

		public Criteria andOlderNameNotBetween(String value1, String value2) {
			addCriterion("older_name not between", value1, value2, "olderName");
			return (Criteria) this;
		}

		public Criteria andEvaluationIsNull() {
			addCriterion("evaluation is null");
			return (Criteria) this;
		}

		public Criteria andEvaluationIsNotNull() {
			addCriterion("evaluation is not null");
			return (Criteria) this;
		}

		public Criteria andEvaluationEqualTo(Integer value) {
			addCriterion("evaluation =", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationNotEqualTo(Integer value) {
			addCriterion("evaluation <>", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationGreaterThan(Integer value) {
			addCriterion("evaluation >", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationGreaterThanOrEqualTo(Integer value) {
			addCriterion("evaluation >=", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationLessThan(Integer value) {
			addCriterion("evaluation <", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationLessThanOrEqualTo(Integer value) {
			addCriterion("evaluation <=", value, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationIn(List<Integer> values) {
			addCriterion("evaluation in", values, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationNotIn(List<Integer> values) {
			addCriterion("evaluation not in", values, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationBetween(Integer value1, Integer value2) {
			addCriterion("evaluation between", value1, value2, "evaluation");
			return (Criteria) this;
		}

		public Criteria andEvaluationNotBetween(Integer value1, Integer value2) {
			addCriterion("evaluation not between", value1, value2, "evaluation");
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

		public Criteria andTableNameIsNull() {
			addCriterion("table_name is null");
			return (Criteria) this;
		}

		public Criteria andTableNameIsNotNull() {
			addCriterion("table_name is not null");
			return (Criteria) this;
		}

		public Criteria andTableNameEqualTo(String value) {
			addCriterion("table_name =", value, "tableName");
			return (Criteria) this;
		}

		public Criteria andTableNameNotEqualTo(String value) {
			addCriterion("table_name <>", value, "tableName");
			return (Criteria) this;
		}

		public Criteria andTableNameGreaterThan(String value) {
			addCriterion("table_name >", value, "tableName");
			return (Criteria) this;
		}

		public Criteria andTableNameGreaterThanOrEqualTo(String value) {
			addCriterion("table_name >=", value, "tableName");
			return (Criteria) this;
		}

		public Criteria andTableNameLessThan(String value) {
			addCriterion("table_name <", value, "tableName");
			return (Criteria) this;
		}

		public Criteria andTableNameLessThanOrEqualTo(String value) {
			addCriterion("table_name <=", value, "tableName");
			return (Criteria) this;
		}

		public Criteria andTableNameLike(String value) {
			addCriterion("table_name like", value, "tableName");
			return (Criteria) this;
		}

		public Criteria andTableNameNotLike(String value) {
			addCriterion("table_name not like", value, "tableName");
			return (Criteria) this;
		}

		public Criteria andTableNameIn(List<String> values) {
			addCriterion("table_name in", values, "tableName");
			return (Criteria) this;
		}

		public Criteria andTableNameNotIn(List<String> values) {
			addCriterion("table_name not in", values, "tableName");
			return (Criteria) this;
		}

		public Criteria andTableNameBetween(String value1, String value2) {
			addCriterion("table_name between", value1, value2, "tableName");
			return (Criteria) this;
		}

		public Criteria andTableNameNotBetween(String value1, String value2) {
			addCriterion("table_name not between", value1, value2, "tableName");
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
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table pension_quality
	 * @mbggenerated  Thu Oct 31 17:02:54 CST 2013
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
     * This class corresponds to the database table pension_quality
     *
     * @mbggenerated do_not_delete_during_merge Tue Oct 29 16:20:55 GMT+08:00 2013
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}