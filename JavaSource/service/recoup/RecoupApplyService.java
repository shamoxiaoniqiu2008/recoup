/**
 * 
 */
package service.recoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private RecoupApplyDetailMapper recoupApplyDetailMapper;
	@Autowired
	private SysDeDatarangeitemMapper sysDeDatarangeitemMapper;
	
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
	public List<RecoupDicProject> selectAllProjects(){
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
	public List<RecoupDicPayclass> selectAllPayclasses(){
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
	public List<RecoupDicCostclass1> selectAllCostclasses1(){
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
	public List<RecoupDicCostclass2> selectAllCostclasses2(){
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
	public String  fileUpload(String currentPath, UploadedFile file) {
		String photoPath = null;
		String fileName = file.getFileName();
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		String upFileName = uuid + "-" + DateUtil.getFormatDateTime(new Date())
				+ fileName.substring(fileName.lastIndexOf("."));
		String sourceFileName = currentPath + File.separator
				+ "uploaded" + File.separator + fileName;
		String targetFileName = currentPath + File.separator
				+ "images" + File.separator + "invoice" + File.separator
				+ upFileName;
		// 检查文件路径是否存在
		String tempPath = currentPath + File.separator
				+ "uploaded";
		File tf = new File(tempPath);
		if (!tf.exists()) {
			tf.mkdir();
		}
		String realPath = currentPath + File.separator
				+ "images" + File.separator + "invoice";
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
	public BigDecimal getAmount(RecoupApplyDetailExtend detailForAdd){
		BigDecimal tempAmount = new BigDecimal(0);
		if(detailForAdd.getPrice().compareTo(BigDecimal.ZERO)== 0 || null == detailForAdd.getQty() ){
			tempAmount = BigDecimal.ZERO;
		}
		else{
			tempAmount = detailForAdd.getPrice().multiply(new BigDecimal ( detailForAdd.getQty()));
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
	public List<RecoupDicCostclass2> getCostclass2ByTypeId1(Long typeId1){
		RecoupDicCostclass2Example example = new RecoupDicCostclass2Example();
		example.or().andClass1IdEqualTo(typeId1);
		return recoupDicCostclass2Mapper.selectByExample(example);
	}

	/**
	 * 保存报销记录
	 * @param recordForAdd
	 * @return
	 */
	public long saveRecoupApplyRecord(RecoupApplyRecordExtend recordForAdd) {
		RecoupApplyRecord applyRecord = (RecoupApplyRecord) recordForAdd;
		applyRecord.setPayState(1); //支付状态，1未支付
//		applyRecord.getApplyDate().toString();
//		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//		String applyDate = sf.format(applyRecord.getApplyDate()); 
		
		String receiptNum = ReceiptNumGenerator.getInstance().getReceiptNum(applyRecord.getProjCode(),"01","Y01","userid","C",applyRecord.getMoney());
		applyRecord.setReceiptNo(receiptNum);
		recoupApplyRecordMapper.insertSelective(applyRecord);
		return recordForAdd.getId();
	}
	/**
	 * 保存报销明细
	 * @param recordId 父记录ID
	 * @param detailListForAdd 费用明细
	 */
	public void saveRecoupApplyDetailExtend(
			long recordId, List<RecoupApplyDetailExtend> detailListForAdd) {
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
	public List<SysDeDatarangeitem> selectAllItem(String rangeCode){
		SysDeDatarangeitemExample example = new SysDeDatarangeitemExample();
		example.or().andRangeCodeLike(rangeCode);
		return sysDeDatarangeitemMapper.selectByExample(example);
	}
}
