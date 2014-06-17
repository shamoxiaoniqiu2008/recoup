package service.receptionManage;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.dictionary.PensionDicRelationshipMapper;
import persistence.receptionManage.PensionDicVisitstyleMapper;
import persistence.receptionManage.PensionGuestrecordMapper;
import util.PmsException;
import util.SystemConfig;
import domain.dictionary.PensionDicRelationship;
import domain.dictionary.PensionDicRelationshipExample;
import domain.receptionManage.PensionDicVisitstyle;
import domain.receptionManage.PensionDicVisitstyleExample;
import domain.receptionManage.PensionGuestrecord;
import domain.receptionManage.VisitOlderReport;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-09-04 下午02:16:44
 */
@Service
public class VisitorManageService {

	@Autowired
	private PensionDicVisitstyleMapper pensionDicVisitstyleMapper;
	@Autowired
	private PensionGuestrecordMapper pensionGuestrecordMapper;
	@Autowired
	private PensionDicRelationshipMapper relationShipMapper;
	@Autowired
	private SystemConfig systemConfig;

	/**
	 * 查询探访类型字典表
	 * 
	 * @return
	 */
	public List<PensionDicVisitstyle> selectVisitTypeRecords() {
		List<PensionDicVisitstyle> visitRecords = new ArrayList<PensionDicVisitstyle>();
		PensionDicVisitstyleExample example = new PensionDicVisitstyleExample();
		example.or();
		example.setOrderByClause("id");
		visitRecords = pensionDicVisitstyleMapper.selectByExample(example);
		return visitRecords;
	}

	/**
	 * 查询所有访客记录
	 * 
	 * @return
	 */
	public List<GuestRecordDomain> selectAllGuestRecords(Date startDate,
			Date endDate) {
		List<GuestRecordDomain> guestRecords = new ArrayList<GuestRecordDomain>();
		guestRecords = pensionGuestrecordMapper.selectAllGuestRecords(
				startDate, endDate);
		return guestRecords;
	}

	/**
	 * 按条件查询访客记录
	 * 
	 * @return
	 */
	public List<GuestRecordDomain> selectGuestRecords(Long styleId,
			Integer olderId, Date startDate, Date endDate) {
		List<GuestRecordDomain> guestRecords = new ArrayList<GuestRecordDomain>();
		if (olderId != null) {
			guestRecords = pensionGuestrecordMapper
					.selectGuestRecordsByOlderId(olderId, startDate, endDate);
		} else if (styleId == null || styleId == 0L) {
			guestRecords = pensionGuestrecordMapper.selectAllGuestRecords(
					startDate, endDate);
		} else {
			guestRecords = pensionGuestrecordMapper
					.selectGuestRecordsByStyleId(styleId, startDate, endDate);

		}
		return guestRecords;
	}

	/**
	 * 插入一条访客记录
	 * 
	 * @param record
	 */
	@Transactional
	public void insertGuestRecord(PensionGuestrecord record) {
		pensionGuestrecordMapper.insertSelective(record);
	}

	/**
	 * 更新一条访客记录
	 * 
	 * @param record
	 */
	@Transactional
	public void updateGuestRecord(PensionGuestrecord record) {
		pensionGuestrecordMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 探访统计
	 * 
	 * @return
	 */
	public List<GuestRecordDomain> selectStatistics(Integer oldId,
			Date startDate, Date endDate) {
		return pensionGuestrecordMapper.selectStatistics(oldId, startDate,
				endDate);
	}

	/**
	 * 查询关系字典表表
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

	public List<VisitOlderReport> search(Date startDate, Date endDate) {
		List<VisitOlderReport> visitOlderReports = new ArrayList<VisitOlderReport>();
		visitOlderReports = pensionGuestrecordMapper.selectVisitOlderReports(
				startDate, endDate);
		return visitOlderReports;
	}

	/**
	 * 将报表类的名称后加上所占比例
	 * 
	 * @param itemPurseReports
	 * @return
	 */
	public List<VisitOlderReport> packVisitOlderReports(
			List<VisitOlderReport> visitOlderReports) {
		List<VisitOlderReport> temp = new ArrayList<VisitOlderReport>();
		Float visitTotal = new Float(0);// 探访总次数
		// 设置百分数格式
		NumberFormat percentFormat = new DecimalFormat("0%");
		// 求得老人总数
		for (VisitOlderReport visitOlderReport : visitOlderReports) {
			visitTotal += visitOlderReport.getOlderCount();
		}
		List<Long> visitNumParam = selectVisitNumList();
		// 重置每条探访统计记录的显示名--加了该记录所占百分数
		for (int j = 0; j < visitNumParam.size(); j++) {
			Long olderCount = 0L;
			for (int i = 0; i < visitOlderReports.size(); i++) {
				VisitOlderReport visitOlderReport = visitOlderReports.get(i);
				Long visitNum = Long.valueOf(visitOlderReport.getVisitNumber());
				if (j == visitNumParam.size() - 1) {
					if (visitNum >= visitNumParam.get(j)) {
						olderCount += visitOlderReport.getOlderCount();
					}
				} else {
					if (visitNum >= visitNumParam.get(j)
							&& visitNum < visitNumParam.get(j + 1)) {
						olderCount += visitOlderReport.getOlderCount();
					} else if (visitNum < visitNumParam.get(j)) {
						continue;
					} else {
						break;
					}
				}
			}
			if (j == visitNumParam.size() - 1) {
				VisitOlderReport tempReport = new VisitOlderReport();
				tempReport.setVisitNumber("大于" + visitNumParam.get(j) + "次   "
						+ percentFormat.format(olderCount / visitTotal));
				tempReport.setOlderCount(olderCount);
				temp.add(tempReport);
			} else {
				VisitOlderReport tempReport = new VisitOlderReport();
				tempReport.setVisitNumber(visitNumParam.get(j) + "—"
						+ visitNumParam.get(j + 1) + "次   "
						+ percentFormat.format(olderCount / visitTotal));
				tempReport.setOlderCount(olderCount);
				temp.add(tempReport);
			}
		}
		return temp;
	}

	/**
	 * 根据系统参数分离探访老人参数设置
	 * 
	 * @return
	 * @throws PmsException
	 */
	public List<Long> selectVisitNumList() {
		List<Long> numberList = new ArrayList<Long>();
		String visit_num_str = null;
		try {
			visit_num_str = systemConfig.selectProperty("VISIT_OLDER_REPORT");
		} catch (PmsException e) {
			e.printStackTrace();
		}
		if (visit_num_str != null && !visit_num_str.isEmpty()) {
			String[] visit_num_arr = visit_num_str.split(",");
			for (int i = 0; i < visit_num_arr.length; i++) {
				numberList.add(Long.valueOf(visit_num_arr[i]));
			}
		} else {
			return null;
		}
		return numberList;
	}
}
