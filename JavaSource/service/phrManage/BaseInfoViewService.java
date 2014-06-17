/**  
* @Title: ArchivesViewService.java 
* @Package service.phrManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-12-5 上午10:32:45 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package service.phrManage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.dictionary.PensionDicBloodtypeMapper;
import persistence.dictionary.PensionDicDegreeMapper;
import persistence.dictionary.PensionDicMarriageMapper;
import persistence.dictionary.PensionDicNationMapper;
import persistence.dictionary.PensionDicPaymentMapper;
import domain.dictionary.PensionDicBloodtype;
import domain.dictionary.PensionDicBloodtypeExample;
import domain.dictionary.PensionDicDegree;
import domain.dictionary.PensionDicDegreeExample;
import domain.dictionary.PensionDicMarriage;
import domain.dictionary.PensionDicMarriageExample;
import domain.dictionary.PensionDicNation;
import domain.dictionary.PensionDicNationExample;
import domain.dictionary.PensionDicPayment;
import domain.dictionary.PensionDicPaymentExample;

/** 
 * @ClassName: ArchivesViewService 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-12-5 上午10:32:45
 * @version V1.0 
 */
@Service
public class BaseInfoViewService {
	
	@Autowired
	private PensionDicBloodtypeMapper pensionDicBloodtypeMapper;
	@Autowired
	private PensionDicDegreeMapper pensionDicDegreeMapper;
	@Autowired
	private PensionDicNationMapper pensionDicNationMapper;
	@Autowired
	private PensionDicMarriageMapper pensionDicMarriageMapper;
	@Autowired
	private PensionDicPaymentMapper pensionDicPaymentMapper;
	
	/**
	 * 
	* @Title: selectAllPensionDicBloodtype 
	* @Description: 血型
	* @param @return
	* @return List<PensionDicBloodtype>
	* @throws 
	* @author Justin.Su
	* @date 2013-12-5 下午4:58:44
	* @version V1.0
	 */
	public List<PensionDicBloodtype> selectAllPensionDicBloodtype(){
		PensionDicBloodtypeExample example = new PensionDicBloodtypeExample();
		example.or().andClearedEqualTo(2);
		return pensionDicBloodtypeMapper.selectByExample(example);
	}
	
	/**
	 * 
	* @Title: selectAllPensionDicDegree 
	* @Description: 文化程度
	* @param @return
	* @return List<PensionDicDegree>
	* @throws 
	* @author Justin.Su
	* @date 2013-12-5 下午5:33:19
	* @version V1.0
	 */
	public List<PensionDicDegree> selectAllPensionDicDegree(){
		PensionDicDegreeExample example = new PensionDicDegreeExample();
		example.or().andClearedEqualTo(2);
		return pensionDicDegreeMapper.selectByExample(example);
	}
	
	/**
	 * 
	* @Title: selectAllPensionDicNation 
	* @Description: 民族
	* @param @return
	* @return List<PensionDicNation>
	* @throws 
	* @author Justin.Su
	* @date 2013-12-5 下午5:33:30
	* @version V1.0
	 */
	public List<PensionDicNation> selectAllPensionDicNation(){
		PensionDicNationExample example = new PensionDicNationExample();
		return pensionDicNationMapper.selectByExample(example);
	}
	/**
	 * 
	* @Title: selectAllPensionDicMarriage 
	* @Description: 婚姻状况
	* @param @return
	* @return List<PensionDicMarriage>
	* @throws 
	* @author Justin.Su
	* @date 2013-12-5 下午6:48:49
	* @version V1.0
	 */
	public List<PensionDicMarriage> selectAllPensionDicMarriage(){
		PensionDicMarriageExample example = new PensionDicMarriageExample();
		example.or().andClearedEqualTo(2);
		return pensionDicMarriageMapper.selectByExample(example);
	}
	/**
	 * 
	* @Title: selectAllPensionDicPayment 
	* @Description: 医疗费用支付方式
	* @param @return
	* @return List<PensionDicPayment>
	* @throws 
	* @author Justin.Su
	* @date 2013-12-5 下午6:48:57
	* @version V1.0
	 */
	public List<PensionDicPayment> selectAllPensionDicPayment(){
		PensionDicPaymentExample example = new PensionDicPaymentExample();
		example.or().andClearedEqualTo(2);
		return pensionDicPaymentMapper.selectByExample(example);
	}
	
	
}
