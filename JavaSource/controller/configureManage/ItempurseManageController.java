package controller.configureManage;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.primefaces.context.RequestContext;

import com.centling.his.util.SessionManager;

import domain.configureManage.PensionItempurse;
import domain.employeeManage.PensionEmployee;

import service.configureManage.ItempurseManageService;
import util.PmsException;
import util.Spell;


/**
 * Created by JBoss Tools
 */

public class ItempurseManageController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient ItempurseManageService itempurseManageService;
	
	public Long searchItemId;
	private PensionItempurse pensionItempurse;
	private PensionItempurse pensionItempurseNow;
	private List<PensionItempurse> pensionItempurseList;
	private String testStr;
	
	//add by mary.liu @2013-12-13 for itemPurseType
	private String itemPurseTypeSql;
	private boolean itemPurseTypeFlag;
	
	@PostConstruct
	public void  init() {
		//allUser = userManageService.findAllUser();
		System.out.println("加载成功");
		fetchItempurse();
		
		testStr = "888888888888888888888888888";
		//fetchselectPensionRoles();
		
		itemPurseTypeSql="select p.id,p.item_name,p.input_code from pension_dic_itempursetype p where p.cleared = 2 ";
	}

	public void fetchItempurse() {
		if(pensionItempurseNow == null || pensionItempurseNow.getId() == null) {
			searchItemId = null;
		}
		pensionItempurseList = itempurseManageService.selectItempurseList(searchItemId);
	}
	
	
	//获取首字母
	public void readPinYin() {
		String pinyin = Spell.getFirstSpell(pensionItempurseNow.getItemname());
		pensionItempurseNow.setInputcode(pinyin);
	}
	
	//添加按钮
	public void showAddForm() {
		pensionItempurseNow  = new PensionItempurse();
		itemPurseTypeFlag=false;//默认添加的是 日常项目价表 不选择临时价表项目类型 Mary
	}
	
	/**
	 * 、修改
	 */
	public void showSelectOneForm() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if(pensionItempurse == null || pensionItempurse.getId() == null) {
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请选择要修改/删除的价表项目" , ""));
			request.addCallbackParam("edit", false);
			return;
		}
		pensionItempurseNow  = pensionItempurse;
		if(pensionItempurse.getItemtype()== 1){//选中的价表项目是 日常项目
			itemPurseTypeFlag=false;//默认添加的是 日常项目价表 不选择临时价表项目类型 Mary
		}else if (pensionItempurse.getItemtype()== 2){//选中的价表项目是 临时项目
			itemPurseTypeFlag=true;//默认添加的是 日常项目价表 不选择临时价表项目类型 Mary
		}
		request.addCallbackParam("selected", true);
	}
	
	/**
	 * 当切换价表的类型时 切换日常和临时时
	 */
	public void onItemTypeChange(){
		if(pensionItempurseNow.getItemtype()== 1){//选中的价表项目是 日常项目
			itemPurseTypeFlag=false;//默认添加的是 日常项目价表 不选择临时价表项目类型 Mary
		}else if (pensionItempurseNow.getItemtype()== 2){//选中的价表项目是 临时项目
			itemPurseTypeFlag=true;//默认添加的是 日常项目价表 不选择临时价表项目类型 Mary
		}
	}
	
	//保存数据
	public void saveItempurse() {
		final RequestContext request = RequestContext.getCurrentInstance();
		if(pensionItempurseNow.getItemname() == null || pensionItempurseNow.getItemname().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请输入名称" , ""));
			request.addCallbackParam("close", false);
			return ;
		}
		try {
			List<PensionItempurse> tempPensionItempurses = itempurseManageService.selectByName(pensionItempurseNow.getItemname());
			if(pensionItempurseNow.getId() == null){ //新增
				
				if(tempPensionItempurses != null && tempPensionItempurses.size()>0) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"【"+pensionItempurseNow.getItemname()+"】已存在" , ""));
					request.addCallbackParam("close", false);
					return;
				} else {
					PensionEmployee curEmployee = SessionManager.getCurEmployee();
					if(pensionItempurseNow.getPricetypeName()==null||pensionItempurseNow.getPricetypeName().equals("")){
						pensionItempurseNow.setPricetypeId(null);
					}
					pensionItempurseNow.setRecorderId(curEmployee.getId());
					pensionItempurseNow.setRecorderrname(curEmployee.getName());
					pensionItempurseNow.setRecordtime(new Date()); 
					itempurseManageService.insertItempurse(pensionItempurseNow);
				}
				request.addCallbackParam("close", false);
			} else {
				if(tempPensionItempurses != null && tempPensionItempurses.size()>0 
						&& !tempPensionItempurses.get(0).getId().equals(pensionItempurseNow.getId())) {
					
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"【"+pensionItempurseNow.getItemname()+"】已存在" , ""));
					fetchItempurse();
					request.addCallbackParam("close", false);
					return;
					
				} else {
					if(pensionItempurseNow.getPricetypeName()==null||pensionItempurseNow.getPricetypeName().equals("")){
						pensionItempurseNow.setPricetypeId(null);
					}
					itempurseManageService.updateItempurse(pensionItempurseNow);
					request.addCallbackParam("close", true);
				}
			}
			fetchItempurse();
			showAddForm();
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage(), ""));
			e.printStackTrace();
		}
		
	}
	
	public void delItempurse() {
		if(pensionItempurseNow == null || pensionItempurseNow.getId() == null) {
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请选择要删除的价表项目" , ""));
			return;
		}

		try {
			itempurseManageService.updateItempurseForDel(pensionItempurseNow);
			fetchItempurse();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"删除成功" , ""));
		} catch (PmsException e) {
			// TODO Auto-generated catch block
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							e.getMessage() , ""));
			e.printStackTrace();
		}
	}
	

	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}

	public String getTestStr() {
		return testStr;
	}

	public ItempurseManageService getItempurseManageService() {
		return itempurseManageService;
	}

	public void setItempurseManageService(
			ItempurseManageService itempurseManageService) {
		this.itempurseManageService = itempurseManageService;
	}

	public void setPensionItempurseList(List<PensionItempurse> pensionItempurseList) {
		this.pensionItempurseList = pensionItempurseList;
	}

	public List<PensionItempurse> getPensionItempurseList() {
		return pensionItempurseList;
	}

	public void setPensionItempurse(PensionItempurse pensionItempurse) {
		this.pensionItempurse = pensionItempurse;
	}

	public PensionItempurse getPensionItempurse() {
		return pensionItempurse;
	}

	public Long getSearchItemId() {
		return searchItemId;
	}

	public void setSearchItemId(Long searchItemId) {
		this.searchItemId = searchItemId;
	}

	public PensionItempurse getPensionItempurseNow() {
		return pensionItempurseNow;
	}

	public void setPensionItempurseNow(PensionItempurse pensionItempurseNow) {
		this.pensionItempurseNow = pensionItempurseNow;
	}

	public String getItemPurseTypeSql() {
		return itemPurseTypeSql;
	}

	public void setItemPurseTypeSql(String itemPurseTypeSql) {
		this.itemPurseTypeSql = itemPurseTypeSql;
	}

	public boolean isItemPurseTypeFlag() {
		return itemPurseTypeFlag;
	}

	public void setItemPurseTypeFlag(boolean itemPurseTypeFlag) {
		this.itemPurseTypeFlag = itemPurseTypeFlag;
	}



	



}