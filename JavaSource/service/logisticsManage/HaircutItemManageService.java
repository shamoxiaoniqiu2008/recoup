package service.logisticsManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.logisticsManage.PensionHaircutApplyMapper;
import persistence.logisticsManage.PensionHaircutScheduleMapper;

import com.centling.his.util.DateUtil;

import domain.logisticsManage.PensionHaircutApplyExtend;
import domain.logisticsManage.PensionHaircutSchedule;

@Service
public class HaircutItemManageService {
	@Autowired
	private PensionHaircutScheduleMapper pensionHaircutScheduleMapper;
	@Autowired
	private PensionHaircutApplyMapper pensionHaircutApplyMapper;
	/**
	 * 查询
	 * @param startDate
	 * @param endDate
	 * @param itemId
	 * @param postFlag 
	 * @return
	 */
	public List<PensionHaircutSchedule> selectHaircutItemRecords(
			Date startDate, Date endDate, Long itemId, Integer postFlag) {
		
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if(endDate != null){
			map.put("endDate", DateUtil.getNextDay(endDate));
		}else{
			map.put("endDate", null);
		}		
		map.put("itemId", itemId);
		map.put("postFlag", postFlag);
		List<PensionHaircutSchedule> haircutScheduleList = pensionHaircutScheduleMapper.selectHaircutItemRecords(map);
		for(PensionHaircutSchedule haircutSchedule: haircutScheduleList){
			haircutSchedule.setDurationStr(haircutSchedule.getDuration()/60+" 小时 ");
			if(haircutSchedule.getDuration()%60 != 0){
				haircutSchedule.setDurationStr(haircutSchedule.getDurationStr() + haircutSchedule.getDuration()%60+" 分钟 ");
			}
		}
		return haircutScheduleList;
	}
	/**
	 * 插入
	 * @param insertedRow
	 */
	public void insertHaircutItemRecord(PensionHaircutSchedule insertedRow) {
		insertedRow.setCleared(2);
		pensionHaircutScheduleMapper.insertSelective(insertedRow);
	}
	/**
	 * 修改
	 * @param updatedRow
	 */
	public void updateHaircutItemRecord(PensionHaircutSchedule updatedRow) {
		pensionHaircutScheduleMapper.updateByPrimaryKeySelective(updatedRow);
	}
	/**
	 * 删除
	 */
	public void deleteHaircutItemRecord(PensionHaircutSchedule selectedRow) {
		selectedRow.setCleared(1);
		pensionHaircutScheduleMapper.updateByPrimaryKeySelective(selectedRow);
		
	}
	/**
	 * 发布
	 * @param selectedRow
	 */
	public void postHaircutItemRecord(PensionHaircutSchedule selectedRow) {
		selectedRow.setPostFlag(1);
		pensionHaircutScheduleMapper.updateByPrimaryKeySelective(selectedRow);
	}
	/**
	 * 查询理发人员详情
	 * @param selectedRow
	 * @return
	 */
	public ArrayList<PensionHaircutApplyExtend> selectReservedOlderDetails(
			PensionHaircutSchedule selectedRow) {
		Long scheduleId = selectedRow.getId();
		return pensionHaircutApplyMapper.selectReservedOlderDetails(scheduleId);
	}
	/**
	 * 为理发的老人登记
	 * @param signOlderDetail
	 * @param selectedRow
	 */
	public void signHaircut(PensionHaircutApplyExtend signOlderDetail,
			PensionHaircutSchedule selectedRow) {
		
		signOlderDetail.setCutedFlag(1);
		signOlderDetail.setHaircutTime(new Date());
		pensionHaircutApplyMapper.updateByPrimaryKeySelective(signOlderDetail);
		
		selectedRow.setHaircutedNumber(selectedRow.getHaircutedNumber()+1);
		pensionHaircutScheduleMapper.updateByPrimaryKeySelective(selectedRow);
	}
	
	public void setPensionHaircutScheduleMapper(
			PensionHaircutScheduleMapper pensionHaircutScheduleMapper) {
		this.pensionHaircutScheduleMapper = pensionHaircutScheduleMapper;
	}

	public PensionHaircutScheduleMapper getPensionHaircutScheduleMapper() {
		return pensionHaircutScheduleMapper;
	}

	public void setPensionHaircutApplyMapper(PensionHaircutApplyMapper pensionHaircutApplyMapper) {
		this.pensionHaircutApplyMapper = pensionHaircutApplyMapper;
	}

	public PensionHaircutApplyMapper getPensionHaircutApplyMapper() {
		return pensionHaircutApplyMapper;
	}
	

}

