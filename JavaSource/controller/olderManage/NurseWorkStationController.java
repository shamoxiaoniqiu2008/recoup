package controller.olderManage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import service.financeManage.NormalPayDomain;
import service.financeManage.TempPayDomain;
import service.olderManage.NurseWorkStationService;
import service.olderManage.PensionFamilyDomain;
import service.receptionManage.PensionOlderDomain;
import util.PmsException;
import domain.configureManage.PensionBed;
import domain.configureManage.PensionBuilding;
import domain.configureManage.PensionRoom;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-28 下午01:16:44
 */

public class NurseWorkStationController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient NurseWorkStationService nurseWorkStationService;

	private List<PensionBuilding> buildList = new ArrayList<PensionBuilding>();

	private List<PensionBed> bedList = new ArrayList<PensionBed>();

	private List<PensionOlderDomain> olderList = new ArrayList<PensionOlderDomain>();

	private PensionBed selectedBed;

	private PensionOlderDomain selectedOlder;

	private PensionRoom selectedRoom;

	private PensionOlderDomain viewPensionOlder;

	/**
	 * 日常收费
	 */
	private List<NormalPayDomain> normalPayList;
	/**
	 * 临时收费
	 */
	private List<TempPayDomain> tempPayList;
	// 家属信息
	private List<PensionFamilyDomain> familyList = new ArrayList<PensionFamilyDomain>();

	@PostConstruct
	public void init() throws PmsException {
		buildList = nurseWorkStationService.selectBuildList();
	}

	public void showSelectedolderInfo() {
		Long olderId = selectedOlder.getId();
		viewPensionOlder = nurseWorkStationService.selectPensionInfomation(
				olderId).get(0);
		normalPayList = nurseWorkStationService.selectNormalPayRecords(olderId);
		tempPayList = nurseWorkStationService.selectTempPayRecords(olderId);
		familyList = nurseWorkStationService.selectFamilyRecord(olderId);
	}

	/**
	 * 根据房间ID查询床位列表
	 */
	public void selectBedList() {
		bedList = nurseWorkStationService.selectBedList(selectedRoom.getId());
	}

	public void setBuildList(List<PensionBuilding> buildList) {
		this.buildList = buildList;
	}

	public List<PensionBuilding> getBuildList() {
		return buildList;
	}

	public void setBedList(List<PensionBed> bedList) {
		this.bedList = bedList;
	}

	public List<PensionBed> getBedList() {
		return bedList;
	}

	public void setSelectedBed(PensionBed selectedBed) {
		this.selectedBed = selectedBed;
	}

	public PensionBed getSelectedBed() {
		return selectedBed;
	}

	public void setOlderList(List<PensionOlderDomain> olderList) {
		this.olderList = olderList;
	}

	public List<PensionOlderDomain> getOlderList() {
		return olderList;
	}

	public void setSelectedOlder(PensionOlderDomain selectedOlder) {
		this.selectedOlder = selectedOlder;
	}

	public PensionOlderDomain getSelectedOlder() {
		return selectedOlder;
	}

	public PensionOlderDomain getViewPensionOlder() {
		return viewPensionOlder;
	}

	public void setViewPensionOlder(PensionOlderDomain viewPensionOlder) {
		this.viewPensionOlder = viewPensionOlder;
	}

	public List<NormalPayDomain> getNormalPayList() {
		return normalPayList;
	}

	public void setNormalPayList(List<NormalPayDomain> normalPayList) {
		this.normalPayList = normalPayList;
	}

	public List<TempPayDomain> getTempPayList() {
		return tempPayList;
	}

	public void setTempPayList(List<TempPayDomain> tempPayList) {
		this.tempPayList = tempPayList;
	}

	public List<PensionFamilyDomain> getFamilyList() {
		return familyList;
	}

	public void setFamilyList(List<PensionFamilyDomain> familyList) {
		this.familyList = familyList;
	}

	public void setNurseWorkStationService(
			NurseWorkStationService nurseWorkStationService) {
		this.nurseWorkStationService = nurseWorkStationService;
	}

	public NurseWorkStationService getNurseWorkStationService() {
		return nurseWorkStationService;
	}

	public void setSelectedRoom(PensionRoom selectedRoom) {
		this.selectedRoom = selectedRoom;
	}

	public PensionRoom getSelectedRoom() {
		return selectedRoom;
	}

}
