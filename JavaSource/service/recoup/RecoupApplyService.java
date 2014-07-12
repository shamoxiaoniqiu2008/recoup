/**
 * 
 */
package service.recoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.recoup.RecoupApplyDetailMapper;
import persistence.recoup.RecoupApplyRecordMapper;
import persistence.recoup.RecoupDicCostclass1Mapper;
import persistence.recoup.RecoupDicCostclass2Mapper;
import persistence.recoup.RecoupDicPayclassMapper;
import persistence.recoup.RecoupDicProjectMapper;
import persistence.recoup.SysDeDatarangeitemMapper;
import util.DateUtil;
import util.FileUtil;
import util.ReceiptNumGenerator;
import domain.recoup.RecoupApplyDetailExtend;
import domain.recoup.RecoupApplyRecord;
import domain.recoup.RecoupApplyRecordExample;
import domain.recoup.RecoupApplyRecordExtend;
import domain.recoup.RecoupDicCostclass1;
import domain.recoup.RecoupDicCostclass1Example;
import domain.recoup.RecoupDicCostclass2;
import domain.recoup.RecoupDicCostclass2Example;
import domain.recoup.RecoupDicPayclass;
import domain.recoup.RecoupDicPayclassExample;
import domain.recoup.RecoupDicProject;
import domain.recoup.RecoupDicProjectExample;
import domain.recoup.SysDeDatarangeitem;
import domain.recoup.SysDeDatarangeitemExample;
import exception.ClassSelectException;
import exception.DetailAddException;
import exception.ItemSelectException;
import exception.PayWaySelectException;
import exception.SuperClassSelectException;

/**
 * @author justin
 * 
 */

@Service
public class RecoupApplyService {

	@Autowired
	private RecoupDicProjectMapper recoupDicProjectMapper;
	@Autowired
	private RecoupDicPayclassMapper recoupDicPayclassMapper;
	@Autowired
	private RecoupDicCostclass1Mapper recoupDicCostclass1Mapper;
	@Autowired
	private RecoupDicCostclass2Mapper recoupDicCostclass2Mapper;
	@Autowired
	private RecoupApplyRecordMapper recoupApplyRecordMapper;
	@Autowired
	private SysDeDatarangeitemMapper sysDeDatarangeitemMapper;
	@Autowired
	private RecoupApplyDetailMapper recoupApplyDetailMapper;

	/**
	 * 
	 * @Title: selectAllProjects
	 * @Description: TODO
	 * @param @return
	 * @return List<RecoupDicProject>
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-2 下午2:13:31
	 * @version V1.0
	 */
	public List<RecoupDicProject> selectAllProjects() {
		RecoupDicProjectExample example = new RecoupDicProjectExample();
		example.or().andClearedEqualTo(2);
		return recoupDicProjectMapper.selectByExample(example);
	}

	/**
	 * 
	 * @Title: selectAllPayclasses
	 * @Description: TODO
	 * @param @return
	 * @return List<RecoupDicPayclass>
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-2 下午2:13:34
	 * @version V1.0
	 */
	public List<RecoupDicPayclass> selectAllPayclasses() {
		RecoupDicPayclassExample example = new RecoupDicPayclassExample();
		example.or().andClearedEqualTo(2);
		example.setOrderByClause("sort_by asc");
		return recoupDicPayclassMapper.selectByExample(example);
	}

	/**
	 * 
	 * @Title: selectAllCostclasses1
	 * @Description: TODO
	 * @param @return
	 * @return List<RecoupDicCostclass1>
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-2 下午2:13:37
	 * @version V1.0
	 */
	public List<RecoupDicCostclass1> selectAllCostclasses1() {
		RecoupDicCostclass1Example example = new RecoupDicCostclass1Example();
		example.or().andClearedEqualTo(2);
		return recoupDicCostclass1Mapper.selectByExample(example);
	}

	/**
	 * 
	 * @Title: selectAllCostclasses2
	 * @Description: TODO
	 * @param @return
	 * @return List<RecoupDicCostclass2>
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-2 下午2:13:41
	 * @version V1.0
	 */
	public List<RecoupDicCostclass2> selectAllCostclasses2() {
		RecoupDicCostclass2Example example = new RecoupDicCostclass2Example();
		example.or().andClearedEqualTo(2);
		return recoupDicCostclass2Mapper.selectByExample(example);
	}

	/**
	 * 
	 * @Title: fileUpload
	 * @Description: TODO
	 * @param @param currentPath
	 * @param @param file
	 * @param @return
	 * @return String
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-2 下午2:13:45
	 * @version V1.0
	 */
	public String fileUpload(String currentPath, UploadedFile file) {
		String photoPath = null;
		String fileName = file.getFileName();
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		String upFileName = uuid + "-" + DateUtil.getFormatDateTime(new Date())
				+ fileName.substring(fileName.lastIndexOf("."));
		String sourceFileName = currentPath + File.separator + "uploaded"
				+ File.separator + fileName;
		String targetFileName = currentPath + File.separator + "images"
				+ File.separator + "invoice" + File.separator + upFileName;
		// 检查文件路径是否存在
		String tempPath = currentPath + File.separator + "uploaded";
		File tf = new File(tempPath);
		if (!tf.exists()) {
			tf.mkdir();
		}
		String realPath = currentPath + File.separator + "images"
				+ File.separator + "invoice";
		File rf = new File(realPath);
		if (!rf.exists()) {
			rf.mkdirs();
		}

		photoPath = File.separator + "images" + File.separator + "invoice"
				+ File.separator + upFileName;
		photoPath = photoPath.replace("\\", "/");

		try {
			FileOutputStream fos = new FileOutputStream(
					new File(sourceFileName));
			InputStream is = file.getInputstream();
			int BUFFER_SIZE = 8192;
			byte[] buffer = new byte[BUFFER_SIZE];
			int a;
			while (true) {
				a = is.read(buffer);
				if (a < 0)
					break;
				fos.write(buffer, 0, a);
				fos.flush();
			}
			fos.close();
			is.close();
			FacesMessage msg = new FacesMessage("图片上传成功！", file.getFileName());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 图片拷贝到images目录对应的文件夹
		File sourceFile = new File(sourceFileName);
		File targetFile = new File(targetFileName);
		try {
			FileUtil.copyFile(sourceFile, targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 删除Tomcat发布目录下的uploaded目录下的文件
		try {
			FileUtil.deleteFile(sourceFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return photoPath;

	}

	/**
	 * 
	 * @Title: getAmount
	 * @Description: TODO
	 * @param @param detailForAdd
	 * @param @return
	 * @return BigDecimal
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-2 下午09:18:49
	 * @version V1.0
	 */
	public BigDecimal getAmount(RecoupApplyDetailExtend detailForAdd) {
		BigDecimal tempAmount = new BigDecimal(0);
		if (detailForAdd.getPrice().compareTo(BigDecimal.ZERO) == 0
				|| null == detailForAdd.getQty()) {
			tempAmount = BigDecimal.ZERO;
		} else {
			tempAmount = detailForAdd.getPrice().multiply(
					new BigDecimal(detailForAdd.getQty()));
		}
		return tempAmount;
	}

	/**
	 * 
	 * @Title: getCostclass2ByTypeId1
	 * @Description: TODO
	 * @param @param typeId1
	 * @param @return
	 * @return List<RecoupDicCostclass2>
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-3 上午12:06:21
	 * @version V1.0
	 */
	public List<RecoupDicCostclass2> getCostclass2ByTypeId1(Long typeId1) {
		RecoupDicCostclass2Example example = new RecoupDicCostclass2Example();
		example.or().andClass1IdEqualTo(typeId1);
		return recoupDicCostclass2Mapper.selectByExample(example);
	}

	/**
	 * 保存报销记录
	 * 
	 * @param recordForAdd
	 * @return
	 */
	public long saveRecoupApplyRecord(RecoupApplyRecordExtend recordForAdd) {
		RecoupApplyRecord applyRecord = (RecoupApplyRecord) recordForAdd;
		applyRecord.setPayState(1); // 支付状态，1未支付
		// applyRecord.getApplyDate().toString();
		// SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		// String applyDate = sf.format(applyRecord.getApplyDate());

		String receiptNum = ReceiptNumGenerator.getInstance().getReceiptNum(
				applyRecord.getProjCode(), "01", "Y01", "userid", "C",
				applyRecord.getMoney());
		applyRecord.setReceiptNo(receiptNum);
		recoupApplyRecordMapper.insertSelective(applyRecord);
		return recordForAdd.getId();
	}

	/**
	 * 保存报销明细
	 * 
	 * @param recordId
	 *            父记录ID
	 * @param detailListForAdd
	 *            费用明细
	 */
	public void saveRecoupApplyDetailExtend(long recordId,
			List<RecoupApplyDetailExtend> detailListForAdd) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @Title: selectAllItem
	 * @Description: TODO
	 * @param @param rangeCode
	 * @param @return
	 * @return List<SysDeDatarangeitem>
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-6 下午10:47:31
	 * @version V1.0
	 */
	public List<SysDeDatarangeitem> selectAllItem(String rangeCode) {
		SysDeDatarangeitemExample example = new SysDeDatarangeitemExample();
		example.or().andRangeCodeLike(rangeCode);
		return sysDeDatarangeitemMapper.selectByExample(example);
	}

	/**
	 * 
	 * @Title: selectAllpayItem
	 * @Description: TODO
	 * @param @param rangeCode
	 * @param @param parentId
	 * @param @return
	 * @return List<SysDeDatarangeitem>
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-9 下午10:46:50
	 * @version V1.0
	 */
	public List<SysDeDatarangeitem> selectAllpayItem(String rangeCode,
			Long parentId) {
		SysDeDatarangeitemExample example = new SysDeDatarangeitemExample();
		if (parentId == 1L) {
			example.or().andRangeCodeEqualTo(rangeCode).andParentIdIsNull();
			example.or().andRangeCodeEqualTo(rangeCode).andParentIdEqualTo("");
		} else {
			example.or().andRangeCodeEqualTo(rangeCode).andParentIdIsNotNull();
			example.or().andRangeCodeEqualTo(rangeCode)
					.andParentIdNotEqualTo("");
		}
		return sysDeDatarangeitemMapper.selectByExample(example);
	}

	/**
	 * 
	 * @Title: selectAllpayItemBy
	 * @Description: TODO
	 * @param @param parentId
	 * @param @return
	 * @return List<SysDeDatarangeitem>
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-9 下午10:46:54
	 * @version V1.0
	 */
	public List<SysDeDatarangeitem> selectAllpayItemBy(String parentId) {
		SysDeDatarangeitemExample example = new SysDeDatarangeitemExample();
		example.or().andParentIdEqualTo(parentId);
		return sysDeDatarangeitemMapper.selectByExample(example);
	}

	/**
	 * 
	 * @Title: insertApplyRecordAndDetail
	 * @Description: 保存或者提交报销记录（主记录及明细记录）
	 * @param @param recordForAdd
	 * @param @param detailListForAdd
	 * @param @param status
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-10 上午12:00:38
	 * @version V1.0
	 */
	@Transactional(rollbackFor = Exception.class)
	public void insertApplyRecordAndDetail(
			RecoupApplyRecordExtend recordForAdd,
			List<RecoupApplyDetailExtend> detailListForAdd, int status) {
		String receiptNum = ReceiptNumGenerator.getInstance().getReceiptNum(
				recordForAdd.getProjCode(), "01", "Y01", "userid", "C",
				recordForAdd.getMoney());
		recordForAdd.setReceiptNo(receiptNum);
		recordForAdd.setState(status);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		recordForAdd.setApplyDate(format
				.format(recordForAdd.getApplyDateTime()).toString());
		int recordId = recoupApplyRecordMapper.insertSelective(recordForAdd);
		if (detailListForAdd.size() > 0) {
			for (RecoupApplyDetailExtend detail : detailListForAdd) {
				detail.setRecordId((long) recordId);
				detail.setFeeDatetime(format.format(detail.getFeeDate())
						.toString());
				recoupApplyDetailMapper.insertSelective(detail);
			}
		}
	}

	/**
	 * 
	 * @Title: getRecoupBy
	 * @Description: TODO
	 * @param @param projectCode
	 * @param @param payState
	 * @param @param applyDateStart
	 * @param @param applyDateEnd
	 * @param @return
	 * @return List<RecoupApplyRecordExtend>
	 * @throws
	 * @author Justin.Su
	 * @date 2014-7-12 上午10:44:37
	 * @version V1.0
	 */
	public List<RecoupApplyRecordExtend> getRecoupBy(String projectCode,
			Integer payState, Date applyDateStart, Date applyDateEnd) {
		return recoupApplyRecordMapper.selectRecoupListBy(projectCode,
				payState, applyDateStart, applyDateEnd);
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean checkValue(RecoupApplyRecordExtend record,
			List<RecoupApplyDetailExtend> detailList)
			throws ItemSelectException, DetailAddException,
			SuperClassSelectException, ClassSelectException,
			PayWaySelectException {
		boolean flag = true;
		if (record.getProjCode().equals("0")) {
			flag = false;
			throw new ItemSelectException("请选择项目！");
		}
		if (detailList.size() < 0 || detailList.size() == 0) {
			flag = false;
			throw new DetailAddException("请添加明细！");
		}
		if (record.getExpTypeCodeP().equals("0")) {
			flag = false;
			throw new SuperClassSelectException("请选择一级费用类别！");
		}
		if (record.getExpTypeCode().equals("0")) {
			flag = false;
			throw new ClassSelectException("请选择二级费用类别！");
		}
		if (record.getPayType().equals("0")) {
			flag = false;
			throw new PayWaySelectException("请选择支付方式！");
		}
		else{
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 
		* @Title: deleteRecoupRecord 
		* @Description: TODO
		* @param @param selectedRecord
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-12 下午03:42:49
		* @version V1.0
	 */
	public void deleteRecoupRecord(RecoupApplyRecordExtend selectedRecord){
		RecoupApplyRecord record = new RecoupApplyRecord();
		record.setCleared(1);
		record.setState(5);
		RecoupApplyRecordExample example = new RecoupApplyRecordExample();
		example.or().andIdEqualTo(selectedRecord.getId());
		recoupApplyRecordMapper.updateByExampleSelective(record, example);
	}
	
	/**
	 * 
		* @Title: updateRecoupRecord 
		* @Description: TODO
		* @param @param selectedRecord
		* @return void
		* @throws 
		* @author Justin.Su
		* @date 2014-7-12 下午04:08:47
		* @version V1.0
	 */
	public void updateRecoupRecord(RecoupApplyRecordExtend selectedRecord){
		RecoupApplyRecord record = new RecoupApplyRecord();
		record.setState(2);
		RecoupApplyRecordExample example = new RecoupApplyRecordExample();
		example.or().andIdEqualTo(selectedRecord.getId());
		recoupApplyRecordMapper.updateByExampleSelective(record, example);
	}

}
