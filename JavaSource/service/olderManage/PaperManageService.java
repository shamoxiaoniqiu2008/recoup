package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.dictionary.PensionDicNationMapper;
import persistence.dictionary.PensionDicPoliticsMapper;
import persistence.dictionary.PensionDicRelationshipMapper;
import persistence.financeManage.PensionNormalpaymentMapper;
import persistence.financeManage.PensionTemppaymentMapper;
import persistence.olderManage.PensionDiagnosisMapper;
import persistence.olderManage.PensionLivingrecordMapper;
import persistence.olderManage.PensionOlderMapper;
import service.financeManage.NormalPayDomain;
import service.financeManage.TempPayDomain;
import service.receptionManage.PensionOlderDomain;
import domain.dictionary.PensionDicNation;
import domain.dictionary.PensionDicNationExample;
import domain.dictionary.PensionDicPolitics;
import domain.dictionary.PensionDicPoliticsExample;
import domain.dictionary.PensionDicRelationship;
import domain.dictionary.PensionDicRelationshipExample;
import domain.olderManage.PensionDiagnosis;
import domain.olderManage.PensionDiagnosisExample;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class PaperManageService {
	@Autowired
	private PensionOlderMapper pensitonOlderMapper;
	@Autowired
	private PensionDicNationMapper nationMapper;
	@Autowired
	private PensionDicPoliticsMapper politicMapper;
	@Autowired
	private PensionNormalpaymentMapper pensionNormalpaymentMapper;
	@Autowired
	private PensionTemppaymentMapper pensionTemppaymentMapper;
	@Autowired
	private PensionDicRelationshipMapper relationShipMapper;
	@Autowired
	PensionDiagnosisMapper pensionDiagnosisMapper;
	@Autowired
	private PensionLivingrecordMapper pensionLivingrecordMapperr;
	
	/**
	 * 查询所有的老人入住记录列表
	 * 
	 * @return
	 */
	public List<PensionOlderDomain> selectPensionInfomation(Long olderId) {
		List<PensionOlderDomain> olderRecordList = new ArrayList<PensionOlderDomain>();
		olderRecordList = pensitonOlderMapper.selectPensionInfomation(olderId);
		for (PensionOlderDomain temp : olderRecordList) {
			convertLivingRecords(temp);
		}
		return olderRecordList;
	}

	/**
	 * 老人入住记录字段转换
	 * 
	 * @param temp
	 */
	public void convertLivingRecords(PensionOlderDomain temp) {
		PensionDicNation nation = nationMapper.selectByPrimaryKey(temp
				.getNationId());
		if (nation != null) {
			temp.setNationName(nation.getType());
		} else {
			temp.setNationName("");
		}
		PensionDicPolitics politic = politicMapper.selectByPrimaryKey(temp
				.getPoliticsId());
		if (politic != null) {
			temp.setPoliticName(politic.getType());
		} else {
			temp.setPoliticName("");
		}
		// 老人性别转换
		if (temp.getSex() == 1) {
			temp.setOldSex("男");
		} else {
			temp.setOldSex("女");
		}
		// 老人类型转换
		if (temp.getTypes() == 1) {
			temp.setOldType("自理");
		} else if (temp.getTypes() == 2) {
			temp.setOldType("半自理");
		} else {
			temp.setOldType("不能自理");
		}
		// 老人状态转换
		switch (temp.getStatuses()) {
		case 1:
			temp.setOlderState("待评估");
			break;
		case 2:
			temp.setOlderState("已评估");
			break;
		case 3:
			temp.setOlderState("入住");
			break;
		case 4:
			temp.setOlderState("请假");
			break;
		default:
			temp.setOlderState("退住");
			break;
		}
		// 申请人性别转换
		if (temp.getApplySex() != null) {
			if (temp.getApplySex() != 1) {
				temp.setApplySexStr("男");
			} else {
				temp.setApplySexStr("女");
			}
		} else {
			temp.setApplySexStr("");
		}

	}

	/**
	 * 查询日常收费信息
	 * 
	 * @param olderId
	 * @return
	 */
	public List<NormalPayDomain> selectNormalPayRecords(Long olderId) {
		List<NormalPayDomain> records = new ArrayList<NormalPayDomain>();
		records = pensionNormalpaymentMapper.selectNomalRecords(olderId);
		return records;
	}

	/**
	 * 查询临时收费信息
	 * 
	 * @param olderId
	 * @return
	 */
	public List<TempPayDomain> selectTempPayRecords(Long olderId) {
		List<TempPayDomain> records = new ArrayList<TempPayDomain>();
		records = pensionTemppaymentMapper.selectTempPayRecords(olderId);
		return records;
	}

	/**
	 * 获取巡诊数据
	 * 
	 * @param olderId
	 * @param diagnosistimeStart
	 * @param diagnosistimeEnd
	 * @param orderByClause
	 * @param firstRec
	 * @param pageSize
	 * @return
	 */
	public List<PensionDiagnosis> selectDiagnosis(Long olderId,
			Date diagnosistimeStart, Date diagnosistimeEnd,
			String orderByClause, Long firstRec, Long pageSize) {
		PensionDiagnosisExample example = new PensionDiagnosisExample();
		example.or().andOlderIdEqualTo(olderId)
				.andDiagnosistimeBetween(diagnosistimeStart, diagnosistimeEnd);

		return pensionDiagnosisMapper.selectExtendByExample(example,
				orderByClause, firstRec, pageSize);
	}
	
	/**
	 * 查询民族列表
	 * 
	 * @return
	 */
	public List<PensionDicNation> selectNationList() {
		List<PensionDicNation> nationList = new ArrayList<PensionDicNation>();
		PensionDicNationExample example = new PensionDicNationExample();
		example.or();
		example.setOrderByClause("Id");
		nationList = nationMapper.selectByExample(example);
		return nationList;
	}

	/**
	 * 查询政治面貌列表
	 * 
	 * @return
	 */
	public List<PensionDicPolitics> selectPoliticList() {
		List<PensionDicPolitics> politicList = new ArrayList<PensionDicPolitics>();
		PensionDicPoliticsExample example = new PensionDicPoliticsExample();
		example.or();
		example.setOrderByClause("Id");
		politicList = politicMapper.selectByExample(example);
		return politicList;
	}

	/**
	 * 查询申请人与老人关系列表
	 * 
	 * @return
	 */
	public List<PensionDicRelationship> selectRelationshipList() {
		List<PensionDicRelationship> RelationshipList = new ArrayList<PensionDicRelationship>();
		PensionDicRelationshipExample example = new PensionDicRelationshipExample();
		example.or();
		example.setOrderByClause("Id");
		RelationshipList = relationShipMapper.selectByExample(example);
		return RelationshipList;
	}
	
	/**
	 * 查询老人家属列表
	 * 
	 * @return
	 */
	public List<PensionFamilyDomain> selectFamilyRecord(Long olderId) {
		List<PensionFamilyDomain> familyRecords = pensionLivingrecordMapperr
				.selectFamily(olderId);
		return familyRecords;
	}
}
