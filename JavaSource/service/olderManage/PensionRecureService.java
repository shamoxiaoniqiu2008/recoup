package service.olderManage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centling.his.util.SessionManager;

import domain.employeeManage.PensionEmployee;
import domain.olderManage.PensionOlderRecure;
import domain.olderManage.PensionOlderRecureExample;
import domain.olderManage.PensionOlderRecureMain;
import domain.olderManage.PensionOlderRecureMainExample;
import domain.olderManage.PensionRecureDetail;
import domain.olderManage.PensionRecureDetailExample;

import persistence.olderManage.PensionDiagnosisMapper;
import persistence.olderManage.PensionOlderRecureMainMapper;
import persistence.olderManage.PensionOlderRecureMapper;
import persistence.olderManage.PensionRecureDetailMapper;
import util.DateUtil;
import util.PmsException;

/**
 * 
 * @author:bill
 * @version: 1.0
 * @Date:2013-10-10 上午16:16:44
 */
@Service
public class PensionRecureService {
	
	@Autowired
	PensionRecureDetailMapper pensionRecureDetailMapper;
	
	@Autowired
	PensionOlderRecureMainMapper pensionOlderRecureMainMapper;
	
	@Autowired
	PensionOlderRecureMapper pensionOlderRecureMapper;
	/**
	 * 获取康复计划明细
	 * @param recureitemId
	 * @return
	 */
	public List<PensionRecureDetailExtend> selectRecureDetail(Long recureitemId) {
		PensionRecureDetailExample example = new PensionRecureDetailExample();

		Integer cleared = 2; //未删除
		example.or()
		.andRecureitemIdEqualTo(recureitemId)
		.andClearedEqualTo(cleared);
		example.setOrderByClause(" steps asc ");
		
		 return pensionRecureDetailMapper.selectExtendByExample(example );
	}
	
	/**
	 * 生成计划
	 * @param recureitemId
	 * @param olderRecureMainExtend
	 * @return
	 * @throws PmsException
	 */
	public List<PensionOlderRecureExtend> generateOlderRecureExtend(Long recureitemId,
			PensionOlderRecureMainExtend olderRecureMainExtend) throws PmsException {
		List<PensionRecureDetailExtend> recureDetailList = selectRecureDetail(recureitemId);
		List<PensionOlderRecureExtend> olderRecureList = new ArrayList<PensionOlderRecureExtend>();
		Long recureMainId = null;
		if(recureDetailList != null) {
			
			PensionOlderRecureMain olderRecureMain = olderRecureMainExtend;
			Long olderId = olderRecureMain.getOlderId();

			olderRecureMain.setInputtime(new Date());
			PensionEmployee tempPensionEmployee = SessionManager.getCurEmployee();
			olderRecureMain.setInputer(tempPensionEmployee.getId());
			olderRecureMain.setIshandle(1);
			olderRecureMain.setCleared(2);
			System.out.println(olderRecureMain.getId());
			pensionOlderRecureMainMapper.insertSelective(olderRecureMain);
			System.out.println(olderRecureMain.getId());
			recureMainId = olderRecureMain.getId();
			olderRecureMainExtend.setId(recureMainId);
			Date startDate = olderRecureMain.getStarttime();
			//DateUtil.addDate(startDate, 1);
			Integer totleDay = 0;
			for(PensionRecureDetailExtend recureDetail:recureDetailList) {
				Integer number = recureDetail.getNumber();
				if(recureDetail.getScheme()  == null) {
					throw new PmsException("请维护【" + recureDetail.getName() + "】执行计划");
				}else{
					String[] arrDate = recureDetail.getScheme().split(",");
					Integer arrLengh = arrDate.length;
					 if(!number.equals(arrLengh)){
						 throw new PmsException("【" + recureDetail.getName() + "】执行计划维护错误");
					 }
					// prel
					 Date oneStepDateTime = DateUtil.addDate(startDate, totleDay);
					 totleDay = totleDay + recureDetail.getTotalday();
					 
					 for(int i =0;i<arrLengh.intValue();i++ ){
						 PensionOlderRecureExtend tempOlderRecureExtend = new PensionOlderRecureExtend();
						 //`older_id 老人Id',
						tempOlderRecureExtend.setOlderId(olderId);
						tempOlderRecureExtend.setOlderName(olderRecureMainExtend.getOlderName());
						// 康复类别ID',
						tempOlderRecureExtend.setRecureitemId(recureitemId);
						//康复主表ID',					
						tempOlderRecureExtend.setRecureMainId(recureMainId);
//`recureDetail_id` INT(11) NOT NULL COMMENT '康复步骤ID',
						tempOlderRecureExtend.setRecuredetailId(recureDetail.getId());
						tempOlderRecureExtend.setRecuredetailName(recureDetail.getName());
						tempOlderRecureExtend.setRecuredetailDetails(recureDetail.getDetail());
						tempOlderRecureExtend.setRecuredetailName(recureDetail.getName()); 
//`planTime` DATE NOT NULL COMMENT '计划日期',
						Integer thisDate = Integer.valueOf(arrDate[i]);
						Date plantime = DateUtil.addDate(oneStepDateTime, (thisDate - 1));
						
						tempOlderRecureExtend.setPlantime(plantime);
//`handleTime` DATE NULL DEFAULT NULL COMMENT '处理日期',
//`realNurse` INT(11) NULL DEFAULT NULL COMMENT '实际责任人ID',
						tempOlderRecureExtend.setRealnurse(recureDetail.getDutynurse());
						tempOlderRecureExtend.setRealnurseName(recureDetail.getDutynurseName());
//`handleNurse` INT(11) NULL DEFAULT NULL COMMENT '实际处理人ID',
//`isHandle` INT(11) NULL DEFAULT '2' COMMENT '是否已处理：1是2否',
						tempOlderRecureExtend.setIshandle(2);
//`handleResult` VARCHAR(1000) NULL DEFAULT NULL COMMENT '实施情况',
//`recureResult` VARCHAR(500) NULL DEFAULT NULL COMMENT '康复效果',
//`evaluate` INT(11) NULL DEFAULT NULL COMMENT '老人评价:1满意2不满意',
//`notes` VARCHAR(100) NULL DEFAULT NULL COMMENT '备注',
//`cleared` INT(11) NOT NULL DEFAULT '2' COMMENT '清除标记:1是2否'
						tempOlderRecureExtend.setCleared(2);
						tempOlderRecureExtend.setSteps(recureDetail.getSteps());
						tempOlderRecureExtend.setDetail(recureDetail.getDetail());
						olderRecureList.add(tempOlderRecureExtend);
					 }
					 
					 
				}
			}
			insertPensionOlderRecure(olderRecureList); 
			olderRecureList = selectOlderRecureDetail(recureMainId);
		}
		
		return olderRecureList;
	}
	/**
	 * 保存计划
	 * @param recureMainId
	 * @return
	 */
	public void insertPensionOlderRecure(List<PensionOlderRecureExtend> olderRecureDetailList) throws PmsException {
		for(PensionOlderRecureExtend tempPensionOlderRecureExtend:olderRecureDetailList) {
			PensionOlderRecure record = tempPensionOlderRecureExtend;
			
			if(tempPensionOlderRecureExtend.getId() == null) {
				try{
					pensionOlderRecureMapper.insertSelective(record );
				} catch(Exception e) {
					throw new PmsException("添加失败");
				}
				
			} else {
				try{
					pensionOlderRecureMapper.updateByPrimaryKeySelective(record);
				} catch(Exception e) {
					throw new PmsException("保存失败");
				}
			}
		}
	}
	/**
	 * 保存单条计划
	 * @param recureMainId
	 * @return
	 */
	public void insertSinglePensionOlderRecure( PensionOlderRecureExtend record) throws PmsException {
			
		try{
			pensionOlderRecureMapper.insertSelective(record);
		} catch(Exception e) {
			throw new PmsException("添加失败");
		}
				
	}
	/**
	 * 删除计划
	 * @param recureMainId
	 * @return
	 */
	public void deletePensionOlderRecure(List<PensionOlderRecureExtend> olderRecureDetailList) throws PmsException {
		for(PensionOlderRecureExtend record:olderRecureDetailList) {
			try{
				record.setCleared(1);
				pensionOlderRecureMapper.updateByPrimaryKey(record);
			} catch(Exception e) {
				throw new PmsException("删除失败");
			}
				
		}
	}
	
	/**
	 * 查询明细
	 * @param recureMainId
	 * @return
	 */
	public List<PensionOlderRecureExtend> selectOlderRecureDetail(Long recureMainId) {
		PensionOlderRecureExample example = new PensionOlderRecureExample();
		example.or()
		.andRecureMainIdEqualTo(recureMainId)
		.andClearedEqualTo(2);
		 return pensionOlderRecureMapper.selectExtendByExample(example);
	}
	
	/**
	 * 删除
	 * @param recureMainId
	 * @throws PmsException 
	 */
	public void delOlderRecure(Long recureMainId) throws PmsException {
		PensionOlderRecureExample examples = new PensionOlderRecureExample();
		examples.or()
		.andRecureMainIdEqualTo(recureMainId)
		.andIshandleEqualTo(1);
		
		List<PensionOlderRecureExtend> tempList = pensionOlderRecureMapper.selectExtendByExample(examples );
		if(tempList != null && tempList.size() >0){
			throw new PmsException("项目已经进行了，不能删除该项目");
		}
		
		try{
			PensionOlderRecureMain record = new PensionOlderRecureMain();
			record.setId(recureMainId);
			record.setCleared(1);
			pensionOlderRecureMainMapper.updateByPrimaryKeySelective(record);
			
			PensionOlderRecureExample example = new PensionOlderRecureExample();
			example.or()
			.andRecureMainIdEqualTo(recureMainId);
			PensionOlderRecure records = new PensionOlderRecure();
			records.setCleared(1);
			pensionOlderRecureMapper.updateByExampleSelective(records , example);
		} catch(Exception e) {
			throw new PmsException("删除失败");
		}
	}
	

	/**
	 * 获取主记录
	 * @param olderId
	 * @param recureitemId
	 * @param handle
	 * @return
	 */
	public List<PensionOlderRecureMainExtend> selectOlderRecureMain(Long olderId, Long recureitemId, Integer handle) {
		PensionOlderRecureMainExample example = new PensionOlderRecureMainExample();
		
		example.or()
		.andOlderIdEqualTo(olderId)
		.andRecureitemIdEqualTo(recureitemId)
		.andClearedEqualTo(2)
		.andIshandleEqualTo(handle);
		
		example.setOrderByClause(" id desc");
		
		return pensionOlderRecureMainMapper.selectExtendByExample(example);
	}
	
	/**
	 * 修改
	 * @param olderRecureMainExtend
	 * @throws PmsException 
	 */
	public void saveOlderRecureMain(PensionOlderRecureMainExtend olderRecureMainExtend) throws PmsException {
		try{
			PensionOlderRecureMain record = new PensionOlderRecureMain();
			record.setId(olderRecureMainExtend.getId());
			record.setNotes(olderRecureMainExtend.getNotes());
			pensionOlderRecureMainMapper.updateByPrimaryKeySelective(record );
		} catch(Exception e) {
			throw new PmsException("修改失败");
		}
	}

	/**
	 * 根据持续时间和服务次数生成执行计划
	 * @param totalday
	 * @param number
	 * @return
	 */
	public String createScheme(Integer totalday, Integer number) {
		BigDecimal bigNumber = new BigDecimal(number);
		BigDecimal bigTotalday = new BigDecimal(totalday);
		BigDecimal  micDecimal = bigTotalday.divide(bigNumber, 0, BigDecimal.ROUND_FLOOR);
		int stepNum = micDecimal.intValue();
		int maxNum = bigNumber.intValue();
		int totalNum = bigTotalday.intValue();
		if(maxNum == 1){
			return "1";
		}else{
			if(micDecimal.compareTo(new BigDecimal(1)) == -1){
				stepNum = 1;
				return this.excuteSchemaBellow(maxNum,stepNum,totalNum);
			}else{
				return this.excuteSchemaAbove(maxNum,stepNum);
			}
		}
	}
	
	/**
	 * from RecoverProgrammeController mary
	 * @param maxNum
	 * @param stepNum
	 * @param totalNum
	 * @return
	 */
	public String excuteSchemaBellow(int maxNum,int stepNum,int totalNum){
		String str = "";
		int currentNum = 1;
		if(totalNum < maxNum){
			int subNum = maxNum - totalNum;
			for(int i=0;i<subNum;i++){
				str = str+String.valueOf(1)+",";
			}
		}else{
			str = String.valueOf(1)+",";
		}
		
		for(int i=0; i< totalNum; i++){
			if(i==0){
				str = str + String.valueOf(currentNum)+",";
			}else{
				currentNum = currentNum + stepNum;
				str = str + String.valueOf(currentNum)+",";
			}
		}
		str = str.substring(0, str.length()-1);
		return str;
	}
	
	/**
	 * from RecoverProgrammeController mary
	 * @param maxNum
	 * @param stepNum
	 * @return
	 */
	public String excuteSchemaAbove(int maxNum,int stepNum){
		String str = "";
		int currentNum = 1;
		for(int i=0; i< maxNum; i++){
			if(i==0){
				str = String.valueOf(currentNum)+",";
			}else{
				currentNum = currentNum + stepNum;
				str = str + String.valueOf(currentNum)+",";
			}
		}
		str = str.substring(0, str.length()-1);
		return str;
	}
	
	/**
	 * 插入一条老人康复项目主记录和一组明细
	 * @param addOlderRecureMainExtend
	 * @param olderRecureDetailList
	 */
	public void insertOlderRecures(
			PensionOlderRecureMainExtend olderRecureMainExtend,
			List<PensionOlderRecureExtend> olderRecureDetailList) {
			pensionOlderRecureMainMapper.insertSelective(olderRecureMainExtend);
			Long id=olderRecureMainExtend.getId();
			for(PensionOlderRecureExtend olderRecureDetail: olderRecureDetailList){
				olderRecureDetail.setRecureMainId(id);
				pensionOlderRecureMapper.insertSelective(olderRecureDetail);
			}
	}
	
			
}
