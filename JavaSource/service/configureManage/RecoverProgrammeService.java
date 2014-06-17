/**  
* @Title: RecoverProgrammeService.java 
* @Package service.configureManage 
* @Description: TODO
* @author Justin.Su
* @date 2013-10-11 上午8:58:59 
* @version V1.0
* @Copyright: Copyright (c) Centling Co.Ltd. 2013
* ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
*/ 
package service.configureManage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import persistence.dictionary.PensionDicRecureItemMapper;
import persistence.olderManage.PensionRecureDetailMapper;
import persistence.system.PensionSysUserMapper;

import controller.configureManage.PensionRecureDetailExtend;
import domain.olderManage.PensionRecureDetail;
import domain.olderManage.PensionRecureDetailExample;
import domain.system.PensionSysUser;
import domain.system.PensionSysUserExample;

/** 
 * @ClassName: RecoverProgrammeService
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-10-11 上午8:58:59
 * @version V1.0 
 */

@Service
public class RecoverProgrammeService {
	
	@Autowired
	private PensionRecureDetailMapper pensionRecureDetailMapper;
	@Autowired
	private PensionDicRecureItemMapper pensionDicRecureItemMapper;
	@Autowired
	private PensionSysUserMapper pensionSysUserMapper;
	
	/**
	 * 
	* @Title: getAllPensionRecureDetailExtendList 
	* @Description: TODO
	* @param @return
	* @return List<PensionRecureDetailExtend>
	* @throws 
	* @author Justin.Su
	* @date 2013-10-12 上午9:18:26
	* @version V1.0
	 */
	public List<PensionRecureDetailExtend> getAllPensionRecureDetailExtendList(){
		List<PensionRecureDetailExtend> returnList = new ArrayList<PensionRecureDetailExtend>();
		List<PensionRecureDetail> pensionRecureDetailList = new ArrayList<PensionRecureDetail>();
		pensionRecureDetailList= pensionRecureDetailMapper.selectByExample(null);
		if(pensionRecureDetailList.size() > 0){
			for(PensionRecureDetail prd:pensionRecureDetailList){
				PensionRecureDetailExtend prde =  new PensionRecureDetailExtend();
				prde.setPensionRecureDetail(prd);
				prde.setRecureItemName(pensionDicRecureItemMapper.selectByPrimaryKey(prd.getRecureitemId()).getItemname());
				prde.setRecoverName(getSysUser(prd.getDutynurse()).getLoginname());
				returnList.add(prde);
			}
		}
		return returnList;
	}
	
	/**
	 * 
	* @Title: getSysUser 
	* @Description: TODO
	* @param @param employeeId
	* @param @return
	* @return PensionSysUser
	* @throws 
	* @author Justin.Su
	* @date 2013-10-12 下午3:45:48
	* @version V1.0
	 */
	public PensionSysUser getSysUser(Long employeeId){
		List<PensionSysUser> pensionSysUserList = new ArrayList<PensionSysUser>();
		PensionSysUser psu = new PensionSysUser();
		PensionSysUserExample psue = new PensionSysUserExample();
		psue.or().andEmployeeIdEqualTo(employeeId);
		pensionSysUserList = pensionSysUserMapper.selectByExample(psue);
		if(pensionSysUserList.size() > 0){
			psu = pensionSysUserList.get(0);
		}
		return psu;
	}
	
	/**
	 * 
	* @Title: insertInto 
	* @Description: TODO
	* @param @param prd
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-12 下午3:45:53
	* @version V1.0
	 */
	@Transactional
	public void insertInto(PensionRecureDetail prd){
		pensionRecureDetailMapper.insertSelective(prd);
	}
	
	
	/**
	 * 
	* @Title: updateDetail 
	* @Description: TODO
	* @param @param pensionRecureDetail
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-14 上午9:47:56
	* @version V1.0
	 */
	@Transactional
	public void updateDetail(PensionRecureDetail pensionRecureDetail){
		PensionRecureDetail record = pensionRecureDetail;
		pensionRecureDetailMapper.updateByPrimaryKeySelective(record);
	}
	
	
	/**
	 * 
	* @Title: deleteDetail 
	* @Description: TODO
	* @param @param id
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-10-14 下午1:09:57
	* @version V1.0
	 */
	@Transactional
	public void deleteDetail(Long id){
		pensionRecureDetailMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 
	* @Title: getRecoverNameById 
	* @Description: TODO
	* @param @param recoverId
	* @param @return
	* @return String
	* @throws 
	* @author Justin.Su
	* @date 2013-10-14 下午1:11:20
	* @version V1.0
	 */
	public String getRecoverNameById(Long recoverId){
		return pensionDicRecureItemMapper.selectByPrimaryKey(recoverId).getItemname();
	}
	
	/**
	 * 
	* @Title: getPensionRecureDetailExtendListById 
	* @Description: TODO
	* @param @param recoverId
	* @param @return
	* @return List<PensionRecureDetailExtend>
	* @throws 
	* @author Justin.Su
	* @date 2013-10-14 下午4:32:24
	* @version V1.0
	 */
	public List<PensionRecureDetailExtend> getPensionRecureDetailExtendListById(Long recoverId){
		List<PensionRecureDetailExtend> returnList = new ArrayList<PensionRecureDetailExtend>();
		List<PensionRecureDetail> pensionRecureDetailList = new ArrayList<PensionRecureDetail>();
		PensionRecureDetailExample example = new PensionRecureDetailExample();
		example.or().andRecureitemIdEqualTo(recoverId);
		pensionRecureDetailList= pensionRecureDetailMapper.selectByExample(example);
		if(pensionRecureDetailList.size() > 0){
			for(PensionRecureDetail prd:pensionRecureDetailList){
				PensionRecureDetailExtend prde =  new PensionRecureDetailExtend();
				prde.setPensionRecureDetail(prd);
				prde.setRecureItemName(pensionDicRecureItemMapper.selectByPrimaryKey(prd.getRecureitemId()).getItemname());
				prde.setRecoverName(getSysUser(prd.getDutynurse()).getLoginname());
				returnList.add(prde);
			}
		}
		return returnList;
	}
	
	/**
	 * 
	* @Title: getPensionRecureDetailList 
	* @Description: TODO
	* @param @param pensionRecureDetail
	* @param @return
	* @return List<PensionRecureDetail>
	* @throws 
	* @author Justin.Su
	* @date 2013-10-23 下午7:26:59
	* @version V1.0
	 */
	public List<PensionRecureDetail>  getPensionRecureDetailList(PensionRecureDetail pensionRecureDetail){
		List<PensionRecureDetail> returnList = new ArrayList<PensionRecureDetail>();
		PensionRecureDetailExample prde = new PensionRecureDetailExample();
		prde.or().andRecureitemIdEqualTo(pensionRecureDetail.getRecureitemId()).andStepsEqualTo(pensionRecureDetail.getSteps());
		returnList = pensionRecureDetailMapper.selectByExample(prde);
		return returnList;
	}
	
	/**
	 * 
	* @Title: getAllPensionRecureDetailExtendListById 
	* @Description: TODO
	* @param @param Id
	* @param @return
	* @return List<PensionRecureDetailExtend>
	* @throws 
	* @author Justin.Su
	* @date 2013-10-24 上午9:12:58
	* @version V1.0
	 */
	public List<PensionRecureDetailExtend> getAllPensionRecureDetailExtendListById(Long Id){
		List<PensionRecureDetailExtend> returnList = new ArrayList<PensionRecureDetailExtend>();
		List<PensionRecureDetail> pensionRecureDetailList = new ArrayList<PensionRecureDetail>();
		if(Id == null){
			pensionRecureDetailList= pensionRecureDetailMapper.selectByExample(null);
		}else{
			PensionRecureDetailExample prde = new PensionRecureDetailExample();
			prde.or().andRecureitemIdEqualTo(Id);
			pensionRecureDetailList= pensionRecureDetailMapper.selectByExample(prde);
		}
		PensionRecureDetailExample prde = new PensionRecureDetailExample();
		prde.or().andRecureitemIdEqualTo(Id);
		pensionRecureDetailList= pensionRecureDetailMapper.selectByExample(prde);
		if(pensionRecureDetailList.size() > 0){
			for(PensionRecureDetail prd:pensionRecureDetailList){
				PensionRecureDetailExtend prded =  new PensionRecureDetailExtend();
				prded.setPensionRecureDetail(prd);
				prded.setRecureItemName(pensionDicRecureItemMapper.selectByPrimaryKey(prd.getRecureitemId()).getItemname());
				prded.setRecoverName(getSysUser(prd.getDutynurse()).getLoginname());
				returnList.add(prded);
			}
		}
		return returnList;
	}
}
