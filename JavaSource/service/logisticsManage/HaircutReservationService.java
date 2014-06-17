package service.logisticsManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.logisticsManage.PensionHaircutApplyMapper;
import persistence.logisticsManage.PensionHaircutScheduleMapper;
import util.PmsException;
import util.SystemConfig;

import com.centling.his.util.DateUtil;

import domain.logisticsManage.PensionHaircutApplyExtend;
import domain.logisticsManage.PensionHaircutSchedule;

@Service
public class HaircutReservationService{
	@Autowired
	private PensionHaircutScheduleMapper pensionHaircutScheduleMapper;
	@Autowired
	private PensionHaircutApplyMapper pensionHaircutApplyMapper;
	@Autowired
	private SystemConfig systemConfig;

	public List<PensionHaircutSchedule> selectHaircutItemRecords(
			Date startDate, Date endDate, Long itemId) {
		
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("startDate", startDate);
		if(endDate != null){
			map.put("endDate", DateUtil.getNextDay(endDate));
		}else{
			map.put("endDate", null);
		}		
		map.put("itemId", itemId);
		map.put("postFlag", 1);
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
	 * 为老人预约
	 * @param selectedRow
	 * @param reserveOlderRow
	 */
	public void reserveHaircut(PensionHaircutSchedule selectedRow, PensionHaircutApplyExtend reserveOlderRow) {
		
		reserveOlderRow.setScheduleId(selectedRow.getId());
		reserveOlderRow.setSignTime(new Date());
		pensionHaircutApplyMapper.insertSelective(reserveOlderRow);
		
		selectedRow.setCurrentOrdernumber(selectedRow.getCurrentOrdernumber()+1);
		pensionHaircutScheduleMapper.updateByPrimaryKeySelective(selectedRow);
		
	}
	/**
	 * 查询预约记录
	 * @param selectedRow
	 * @return
	 */
	public ArrayList<PensionHaircutApplyExtend> selectReservedOlderDetails(
			PensionHaircutSchedule selectedRow) {

		Long scheduleId = selectedRow.getId();
		return pensionHaircutApplyMapper.selectReservedOlderDetails(scheduleId);
		
	}
	/**
	 * 取消老人的预约记录
	 * @param cancelOlderDetail
	 * @param selectedRow
	 */
	public void cancelHaircut(PensionHaircutApplyExtend cancelOlderDetail, PensionHaircutSchedule selectedRow) {

		cancelOlderDetail.setCleared(1);
		pensionHaircutApplyMapper.updateByPrimaryKeySelective(cancelOlderDetail);
		
		selectedRow.setCurrentOrdernumber(selectedRow.getCurrentOrdernumber()-1);
		pensionHaircutScheduleMapper.updateByPrimaryKeySelective(selectedRow);
		
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
	
	/**
	 * 获得 取消预约最少应提前的时间 
	 * @throws PmsException 
	 * @throws NumberFormatException 
	 */
	public Long getShortestCancelTime() throws NumberFormatException, PmsException {
		return Long.valueOf(systemConfig.selectProperty("ADVANCE_CANCEL_HAIRCAUT_TIME").trim());
	}
	
	
	public PensionHaircutScheduleMapper getPensionHaircutScheduleMapper() {
		return pensionHaircutScheduleMapper;
	}

	
	public void setPensionHaircutScheduleMapper(
			PensionHaircutScheduleMapper pensionHaircutScheduleMapper) {
		this.pensionHaircutScheduleMapper = pensionHaircutScheduleMapper;
	}

	public void setPensionHaircutApplyMapper(PensionHaircutApplyMapper pensionHaircutApplyMapper) {
		this.pensionHaircutApplyMapper = pensionHaircutApplyMapper;
	}

	public PensionHaircutApplyMapper getPensionHaircutApplyMapper() {
		return pensionHaircutApplyMapper;
	}
	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	

}
