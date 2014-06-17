package persistence.fingerPrint;

import domain.fingerPrint.DictFingerprintType;
import domain.fingerPrint.DictFingerprintTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictFingerprintTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dict_fingerprint_type
     *
     * @mbggenerated Wed Sep 25 15:24:41 CST 2013
     */
    int countByExample(DictFingerprintTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dict_fingerprint_type
     *
     * @mbggenerated Wed Sep 25 15:24:41 CST 2013
     */
    int deleteByExample(DictFingerprintTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dict_fingerprint_type
     *
     * @mbggenerated Wed Sep 25 15:24:41 CST 2013
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dict_fingerprint_type
     *
     * @mbggenerated Wed Sep 25 15:24:41 CST 2013
     */
    int insert(DictFingerprintType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dict_fingerprint_type
     *
     * @mbggenerated Wed Sep 25 15:24:41 CST 2013
     */
    int insertSelective(DictFingerprintType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dict_fingerprint_type
     *
     * @mbggenerated Wed Sep 25 15:24:41 CST 2013
     */
    List<DictFingerprintType> selectByExample(DictFingerprintTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dict_fingerprint_type
     *
     * @mbggenerated Wed Sep 25 15:24:41 CST 2013
     */
    DictFingerprintType selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dict_fingerprint_type
     *
     * @mbggenerated Wed Sep 25 15:24:41 CST 2013
     */
    int updateByExampleSelective(@Param("record") DictFingerprintType record, @Param("example") DictFingerprintTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dict_fingerprint_type
     *
     * @mbggenerated Wed Sep 25 15:24:41 CST 2013
     */
    int updateByExample(@Param("record") DictFingerprintType record, @Param("example") DictFingerprintTypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dict_fingerprint_type
     *
     * @mbggenerated Wed Sep 25 15:24:41 CST 2013
     */
    int updateByPrimaryKeySelective(DictFingerprintType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dict_fingerprint_type
     *
     * @mbggenerated Wed Sep 25 15:24:41 CST 2013
     */
    int updateByPrimaryKey(DictFingerprintType record);
}