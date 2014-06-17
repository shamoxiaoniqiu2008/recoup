package service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.olderManage.PensionRingMapper;

import domain.olderManage.PensionRing;
import domain.olderManage.PensionRingExample;

@Service
public class RingManageService {

	@Autowired
	private PensionRingMapper pensionRingMapper;
	/**
	 * 插入新记录
	 * @return
	 */
	public  void addRing(PensionRing ring){
		pensionRingMapper.insertSelective(ring);
		
	}
	/**
	 * 更新呼叫器记录
	 * @return
	 */
	public  void updateRing(PensionRing ring){
		pensionRingMapper.updateByPrimaryKeySelective(ring);
		
	}
	/**
	 * 查询呼叫器记录
	 * @return
	 */
	public  List<PensionRing> selectRing(Long roomID,Long nurseID){
		return pensionRingMapper.selectRing(roomID, nurseID);
		
	}
	
	

	public void setPensionRingMapper(PensionRingMapper pensionRingMapper) {
		this.pensionRingMapper = pensionRingMapper;
	}

	public PensionRingMapper getPensionRingMapper() {
		return pensionRingMapper;
	}
	
}
