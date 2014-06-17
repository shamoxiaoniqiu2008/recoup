package service.fingerPrint;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.fingerPrint.PensionFingerPrintDaily;
import domain.fingerPrint.PensionFingerprint;
import domain.fingerPrint.PensionFingerprintExample;

import persistence.fingerPrint.PensionFingerPrintDailyMapper;
import persistence.fingerPrint.PensionFingerprintMapper;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class FingerPrintManageService {
	@Autowired
	private PensionFingerprintMapper pensionFingerprintMapper;
	@Autowired
	private PensionFingerPrintDailyMapper pensionFingerPrintDailyMapper;

	/**
	 * 查询指纹机列表
	 * 
	 * @return
	 */
	@Transactional
	public List<FingerPrintDomain> selectFingerPrints(Long roomId) {
		List<PensionFingerPrintDaily> dailyList = new ArrayList<PensionFingerPrintDaily>();
		dailyList = pensionFingerPrintDailyMapper.selectDailyPrints();
		int temp = 0;
		for (PensionFingerPrintDaily record : dailyList) {
			if (temp != 0) {
				if (dailyList.get(temp).getAstrSerialNo()
						.equals(dailyList.get(temp - 1).getAstrSerialNo())) {
					temp++;
					continue;
				}
			}
			temp++;
			List<PensionFingerprint> printList = new ArrayList<PensionFingerprint>();
			PensionFingerprintExample example = new PensionFingerprintExample();
			example.or().andAnDeviceIdEqualTo(record.getAnDeviceId())
					.andAstrSerialNoEqualTo(record.getAstrSerialNo());
			printList = pensionFingerprintMapper.selectByExample(example);
			PensionFingerprint print;
			if (printList != null && printList.size() != 0) {
				print = new PensionFingerprint();
				print = printList.get(0);
				print.setAstrDeviceIp(record.getAstrDeviceIp());
				print.setInputTime(record.getRecordTime());
				pensionFingerprintMapper.updateByPrimaryKeySelective(print);
			} else {
				print = new PensionFingerprint();
				print.setAnDeviceId(record.getAnDeviceId());
				print.setAstrDeviceIp(record.getAstrDeviceIp());
				print.setInputTime(record.getRecordTime());
				print.setAstrSerialNo(record.getAstrSerialNo());
				print.setTypeId(1);
				print.setName("员工");
				print.setCleared(2);
				pensionFingerprintMapper.insert(print);
			}
		}
		List<FingerPrintDomain> fingerPrintList = new ArrayList<FingerPrintDomain>();
		fingerPrintList = pensionFingerprintMapper.selectFingerPrints(roomId);
		return fingerPrintList;
	}

	/**
	 * 更新一条指纹机设备
	 * 
	 * @param fingerPrint
	 */
	@Transactional
	public void updateFingerPrint(FingerPrintDomain fingerPrint) {
		try {
			pensionFingerprintMapper.updateByPrimaryKeySelective(fingerPrint);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}
}
