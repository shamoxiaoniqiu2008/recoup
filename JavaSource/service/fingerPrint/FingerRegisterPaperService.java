package service.fingerPrint;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.fingerPrint.PensionFingerprintRegMapper;

/**
 * 
 * @author:Wensy Yang
 * @version: 1.0
 * @Date:2013-8-29 上午10:16:44
 */
@Service
public class FingerRegisterPaperService {
	@Autowired
	private PensionFingerprintRegMapper pensionFingerprintRegMapper;

	/**
	 * 查询指纹机登记列表
	 * @param type 
	 * 
	 * @return
	 */
	public List<FingerRegDomain> selectFingerRegRecords(Long peopleId, Integer type) {
		List<FingerRegDomain> fingerRegList = new ArrayList<FingerRegDomain>();
		fingerRegList = pensionFingerprintRegMapper
				.selectFingerRegRecords(peopleId,type);
		return fingerRegList;
	}

	/**
	 * 插入一条指纹登记记录
	 */
	@Transactional
	public void insertFingerReg(FingerRegDomain addFingerReg) {
		try {
			addFingerReg.setCleared(2);
			pensionFingerprintRegMapper.insertSelective(addFingerReg);
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, ex
							.getMessage(), ""));
		}
	}

	/**
	 * 更新一条指纹机设备
	 * 
	 * @param fingerPrint
	 */
	@Transactional
	public void updateFingerReg(FingerRegDomain fingerPrint) {
		try {
			pensionFingerprintRegMapper
					.updateByPrimaryKeySelective(fingerPrint);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}

	/**
	 * 删除指纹登记记录
	 */
	@Transactional
	public void deleteFingerReg(FingerRegDomain fingerPrint) {
		try {
			pensionFingerprintRegMapper.deleteByPrimaryKey(fingerPrint.getId());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), ""));
		}
	}
}
