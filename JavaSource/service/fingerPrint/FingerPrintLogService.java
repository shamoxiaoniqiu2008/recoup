package service.fingerPrint;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.fingerPrint.PensionFingerPrintDaily;

import persistence.fingerPrint.PensionFingerPrintDailyMapper;

/**
 * 
 * @author:bill
 * @version: 1.0
 * @date:2013-12-3 
 */
@Service
public class FingerPrintLogService {
	@Autowired
	private PensionFingerPrintDailyMapper pensionFingerPrintDailyMapper;

	/**
	 * 查询员工指纹日志
	 * @param map
	 * @author bill
	 * @return 
	 */
	public List<PensionFingerPrintDaily> selectByEmployee(String roomNumber,String peopleName,Date startTime,Date endTime)
	{
		return pensionFingerPrintDailyMapper.selectByEmployee(roomNumber, peopleName, startTime, endTime);
	}
	/**
	 * 查询老人指纹日志
	 * @param map
	 * @author bill
	 * @return 
	 */
	public List<PensionFingerPrintDaily> selectByOlder(String roomNumber,String peopleName,Date startTime,Date endTime)
	{
		return pensionFingerPrintDailyMapper.selectByOlder(roomNumber, peopleName, startTime, endTime);
	}

}
