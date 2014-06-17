package controller.olderManage;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import service.olderManage.ViewRoomService;
import domain.configureManage.PensionBuilding;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-11-20 上午08:30:44
 */

public class ViewRoomController implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient ViewRoomService viewRoomService;

	private List<PensionBuilding> buildList;

	private Long buildIdArr[] = new Long[10];

	public Long[] getBuildIdArr() {
		return buildIdArr;
	}

	public void setBuildIdArr(Long[] buildIdArr) {
		this.buildIdArr = buildIdArr;
	}

	private int buildNum;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	public void init() {
		buildList = viewRoomService.selectBuildList();
		for (int i = 0; i < buildList.size(); i++) {
			buildIdArr[i] = buildList.get(i).getId();
		}
	}

	public ViewRoomService getViewRoomService() {
		return viewRoomService;
	}

	public void setViewRoomService(ViewRoomService viewRoomService) {
		this.viewRoomService = viewRoomService;
	}

	public List<PensionBuilding> getBuildList() {
		return buildList;
	}

	public void setBuildList(List<PensionBuilding> buildList) {
		this.buildList = buildList;
	}

	public int getBuildNum() {
		return buildNum;
	}

	public void setBuildNum(int buildNum) {
		this.buildNum = buildNum;
	}
}
