package service.personnelManage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.hrManage.PensionArrangeTemplateDetailMapper;
import persistence.hrManage.PensionArrangeTemplateMapper;
import persistence.hrManage.PensionStaffArrangeMapper;
import domain.hrManage.PensionArrangeTemplate;
import domain.hrManage.PensionArrangeTemplateDetail;
import domain.hrManage.PensionArrangeTemplateDetailExample;
import domain.hrManage.PensionStaffArrange;

/**
 * 日常缴费
 * 
 * @author mary.liu
 * 
 * 
 */
@Service
public class StaffScheduleService {
	@Autowired
	private PensionStaffArrangeMapper pensionStaffArrangeMapper;
	@Autowired
	private PensionArrangeTemplateMapper pensionArrangeTemplateMapper;
	@Autowired
	private PensionArrangeTemplateDetailMapper pensionArrangeTemplateDetailMapper;

	private static final Integer SOLID_YES = 1;// 是
	private static final Integer SOLID_NO = 2;// 否

	public List<PensionStaffArrange> searchStaffArranges(Date startDate,
			Date endDate, Long deptId, Long empId) {
		return pensionStaffArrangeMapper.selectStaffArranges(startDate,
				endDate, deptId, empId);
	}

	/**
	 * 删除选中的多条记录
	 * 
	 * @param staffArranges
	 */
	@Transactional
	public void deleteStaffArrange(PensionStaffArrange[] staffArranges) {
		for (PensionStaffArrange staffArrange : staffArranges) {
			PensionStaffArrange arrange = new PensionStaffArrange();
			arrange.setId(staffArrange.getId());
			arrange.setCleared(SOLID_YES);
			pensionStaffArrangeMapper.updateByPrimaryKeySelective(arrange);
		}

	}

	/**
	 * 插入多条排班记录
	 * 
	 * @param arranges
	 * @return
	 */
	@Transactional
	public List<PensionStaffArrange> saveAddArranges(
			List<PensionStaffArrange> arranges) {
		for (PensionStaffArrange arrange : arranges) {
			arrange.setId(null);
			pensionStaffArrangeMapper.insertSelective(arrange);
		}
		return arranges;
	}

	public void updateArrange(PensionStaffArrange modifyArrange) {
		pensionStaffArrangeMapper.updateByPrimaryKeySelective(modifyArrange);

	}

	public List<PensionStaffArrange> saveCopyArranges(
			PensionStaffArrange[] arranges, Date copyStartDate,
			Date copyEndDate, Date startDate, Date endDate) {
		GregorianCalendar startDateGc = new GregorianCalendar();// 新建一GregorianCalendar类
		startDateGc.setTime(startDate);
		GregorianCalendar endDateGc = new GregorianCalendar();// 新建一GregorianCalendar类
		endDateGc.setTime(endDate);
		GregorianCalendar copyStartDateGc = new GregorianCalendar();// 新建一GregorianCalendar类
		copyStartDateGc.setTime(copyStartDate);
		GregorianCalendar arrangeDateGc = new GregorianCalendar();// 新建一GregorianCalendar类
		List<PensionStaffArrange> tempArranges = new ArrayList<PensionStaffArrange>();
		if (startDateGc.equals(endDateGc)) {
			for (PensionStaffArrange arrange : arranges) {
				arrange.setId(null);
				arrange.setArrangeTime(copyStartDateGc.getTime());
				tempArranges.add(arrange);
			}
		} else {
			while (startDateGc.before(endDateGc)
					|| startDateGc.equals(endDateGc)) {
				for (PensionStaffArrange arrange : arranges) {
					arrangeDateGc.setTime(arrange.getArrangeTime());
					if (arrangeDateGc.equals(startDateGc)) {
						arrange.setId(null);
						arrange.setArrangeTime(copyStartDateGc.getTime());
						tempArranges.add(arrange);
					}
				}
				startDateGc.add(Calendar.DAY_OF_MONTH, 1);
				copyStartDateGc.add(Calendar.DAY_OF_MONTH, 1);
			}
		}
		return this.saveAddArranges(tempArranges);
	}

	/**
	 * 根据模板编号查询模板明细信息
	 * 
	 * @param templateId
	 * @return
	 */
	public List<PensionArrangeTemplateDetail> searchArrangeTemplates(
			Long templateId) {
		return pensionArrangeTemplateDetailMapper
				.selectArrangeTemplateDetails(templateId);
	}

	public void deleteArrangeTemplate(Long templateId) {
		PensionArrangeTemplate arrangeTemplate = new PensionArrangeTemplate();
		arrangeTemplate.setId(templateId);
		arrangeTemplate.setCleared(SOLID_YES);
		pensionArrangeTemplateMapper
				.updateByPrimaryKeySelective(arrangeTemplate);
		PensionArrangeTemplateDetailExample example = new PensionArrangeTemplateDetailExample();
		example.or().andTemplateIdEqualTo(templateId)
				.andClearedEqualTo(SOLID_NO);
		PensionArrangeTemplateDetail record = new PensionArrangeTemplateDetail();
		record.setCleared(SOLID_YES);
		pensionArrangeTemplateDetailMapper.updateByExampleSelective(record,
				example);
	}

	public void updateArrangeTemplate(PensionArrangeTemplate selectTemplate) {
		pensionArrangeTemplateMapper
				.updateByPrimaryKeySelective(selectTemplate);
	}

	public PensionStaffArrange[] sortArray(PensionStaffArrange[] selectArranges) {

		for (int i = 1; i < selectArranges.length; i++) {
			int k = i;
			while (k > 0
					&& selectArranges[k].getArrangeTime().before(
							selectArranges[k - 1].getArrangeTime())) {
				PensionStaffArrange tempArrange = selectArranges[k];
				selectArranges[k] = selectArranges[k - 1];
				selectArranges[k - 1] = tempArrange;
				k--;
			}
		}
		return selectArranges;
	}

	/**
	 * 更新模板明细
	 * 
	 * @param template
	 * @param details
	 */
	public void updateArrangeTemplateDetail(PensionArrangeTemplate template,
			List<PensionArrangeTemplateDetail> details) {
		PensionArrangeTemplateDetailExample example = new PensionArrangeTemplateDetailExample();
		example.or().andTemplateIdEqualTo(template.getId())
				.andDepartIdEqualTo(template.getDeptId())
				.andClearedEqualTo(SOLID_NO);
		PensionArrangeTemplateDetail record = new PensionArrangeTemplateDetail();
		record.setCleared(SOLID_YES);
		pensionArrangeTemplateDetailMapper.updateByExampleSelective(record,
				example);
		for (PensionArrangeTemplateDetail detail : details) {
			detail.setId(null);
			detail.setTemplateId(template.getId());
			pensionArrangeTemplateDetailMapper.insertSelective(detail);
		}
	}

	/**
	 * 新增人员排班模板
	 * 
	 * @param template
	 * @param details
	 */
	public void insertArrangeTemplate(PensionArrangeTemplate template,
			List<PensionArrangeTemplateDetail> details) {
		pensionArrangeTemplateMapper.insertSelective(template);
		for (PensionArrangeTemplateDetail detail : details) {
			detail.setTemplateId(template.getId());
			pensionArrangeTemplateDetailMapper.insertSelective(detail);
		}
	}

	public PensionArrangeTemplateDetail insertArrangeTemplateDetail(
			PensionArrangeTemplateDetail template) {
		pensionArrangeTemplateDetailMapper.insertSelective(template);
		return template;
	}

	public List<PensionArrangeTemplateDetail> searchTemplateDetails(Long tempId) {
		PensionArrangeTemplateDetailExample example = new PensionArrangeTemplateDetailExample();
		example.or().andTemplateIdEqualTo(tempId).andClearedEqualTo(2);
		return pensionArrangeTemplateDetailMapper.selectByExample(example);
	}

}
