package service.logisticsManage;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import persistence.logisticsManage.PensionAmmeterMapper;

import domain.logisticsManage.PensionAmmeterExtend;

@Service
public class AmmeterManageService {
	
	@Autowired
	private PensionAmmeterMapper pensionAmmeterMapper;

	/**
	 * 
	* @Title: selectAmmeterRecords 
	* @Description: TODO
	* @param @param roomId
	* @param @param validFlag
	* @param @return
	* @return List<PensionAmmeterExtend>
	* @throws 
	* @author Justin.Su
	* @date 2013-12-13 上午10:25:48
	* @version V1.0
	 */
	public List<PensionAmmeterExtend> selectAmmeterRecords(Long roomId,
			Integer validFlag) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("roomId", roomId);
		map.put("validFlag", validFlag);
		return pensionAmmeterMapper.selectAmmeterRecords(map);
		
	}

	/**
	 * 
	* @Title: deleteAmmeterRecord 
	* @Description: TODO
	* @param @param selectedRow
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-12-13 上午10:25:53
	* @version V1.0
	 */
	public void deleteAmmeterRecord(PensionAmmeterExtend selectedRow) {
		selectedRow.setCleared(1);
		pensionAmmeterMapper.updateByPrimaryKeySelective(selectedRow);
	}

	/**
	 * 
	* @Title: insertAmmeterRecord 
	* @Description: TODO
	* @param @param insertedRow
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-12-13 上午10:25:58
	* @version V1.0
	 */
	public void insertAmmeterRecord(PensionAmmeterExtend insertedRow) {
		insertedRow.setCleared(2);
		pensionAmmeterMapper.insertSelective(insertedRow);
	}

	/**
	 * 
	* @Title: updateAmmeterRecord 
	* @Description: TODO
	* @param @param updatedRow
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-12-13 上午10:26:03
	* @version V1.0
	 */
	public void updateAmmeterRecord(PensionAmmeterExtend updatedRow) {
		pensionAmmeterMapper.updateByPrimaryKeySelective(updatedRow);
		
	}

	/**
	 * 
	* @Title: fillField 
	* @Description: TODO
	* @param @param roomId
	* @param @return
	* @return List<PensionAmmeterExtend>
	* @throws 
	* @author Justin.Su
	* @date 2013-12-13 上午10:26:15
	* @version V1.0
	 */
	public List<PensionAmmeterExtend> fillField(Long roomId) {
		return pensionAmmeterMapper.fillField(roomId);
	}

	/**
	 * 
	* @Title: setPensionAmmeterMapper 
	* @Description: TODO
	* @param @param pensionAmmeterMapper
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-12-13 上午10:26:20
	* @version V1.0
	 */
	public void setPensionAmmeterMapper(PensionAmmeterMapper pensionAmmeterMapper) {
		this.pensionAmmeterMapper = pensionAmmeterMapper;
	}

	public PensionAmmeterMapper getPensionAmmeterMapper() {
		return pensionAmmeterMapper;
	}

}
