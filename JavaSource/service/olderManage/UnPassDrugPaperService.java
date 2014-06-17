package service.olderManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import persistence.olderManage.PensionDosageMapper;
import persistence.olderManage.PensionDosagedetailMapper;
import service.system.MessageMessage;
import util.JavaConfig;
import util.PmsException;
import domain.olderManage.PensionDosage;
import domain.olderManage.PensionDosagedetail;
import domain.olderManage.PensionDosagedetailExample;

@Service
public class UnPassDrugPaperService {

	@Autowired
	private PensionDosageMapper pensionDosageMapper;
	@Autowired
	private PensionDosagedetailMapper pensionDosagedetailMapper;
	@Autowired
	private MessageMessage messageMessage;

	private final static Integer YES = 1;
	private final static Integer NO = 2;

	public List<PensionDosage> select(Date startDate, Date endDate,
			Long senderId, Long chargerId) {

		return pensionDosageMapper.selectUnPassDosages(startDate, endDate,
				senderId, chargerId);
	}

	public List<PensionDosagedetail> selectMyDosageDetails(Long dosageId) {
		PensionDosagedetailExample example = new PensionDosagedetailExample();
		example.or().andDosageIdEqualTo(dosageId).andClearedEqualTo(NO)
				.andIscheckEqualTo(YES).andCheckresultEqualTo("2");
		return pensionDosagedetailMapper.selectByExample(example);
	}

	public void sendMessages(Long id, String name, Integer chargerId,
			List<PensionDosagedetail> detailList) throws PmsException {
		String messageContent = "员工 " + name + " 提交了一条配药单核对申请！";
		// 消息类别
		String typeIdStr = JavaConfig
				.fetchProperty("DrugService.sendDrugPaper");
		Long messagetypeId = Long.valueOf(typeIdStr);

		String url;

		url = messageMessage.selectMessageTypeUrl(messagetypeId);
		url = url + "?dosageId=" + id;

		List<Long> dptIdList = new ArrayList<Long>();
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(Long.valueOf(chargerId));

		String messagename = "【" + name + "】配药单核对消息";

		messageMessage.sendMessage(messagetypeId, dptIdList, userIdList,
				messagename, messageContent, url, "pension_dosage", id);
		updateDosageDetail(detailList);
	}

	public void updateDosageDetail(List<PensionDosagedetail> detailList) {
		for (PensionDosagedetail temp : detailList) {
			temp.setCheckerId(null);
			temp.setCheckerName("");
			temp.setCheckresult(null);
			temp.setIscheck(NO);
			temp.setNotes("");
			pensionDosagedetailMapper.updateByPrimaryKey(temp);
		}
	}

}
