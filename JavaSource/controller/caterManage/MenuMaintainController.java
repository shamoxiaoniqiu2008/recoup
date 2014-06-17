package controller.caterManage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.UploadedFile;

import service.caterManage.MenuMaintainService;
import util.DateUtil;
import util.FileUtil;
import util.Spell;
import domain.caterManage.PensionDicCuisine;
import domain.caterManage.PensionFoodIngredient;
import domain.caterManage.PensionFoodmenu;

/**
 * @author justin
 * 
 */
public class MenuMaintainController implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 * @version V1.0
	 */

	private static final long serialVersionUID = 3458763725904788152L;
	/**
	 * 引入Service
	 */
	private transient MenuMaintainService menuMaintainService;
	/**
	 * 定义页面上新增，修改和删除按钮可用性标记位
	 */
	private boolean addButtonEnableFlag;
	private boolean modifyButtonEnableFlag;
	private boolean deleteButtonEnableFlag;
	// 定义菜单List add by justin.su 2013-09-04
	private List<PensionFoodmenu> pensionFoodmenuList = new ArrayList<PensionFoodmenu>();
	private PensionFoodmenu selectedPensionFoodmenu = new PensionFoodmenu();
	private PensionFoodmenu pensionFoodmenu = new PensionFoodmenu();
	// 定义菜名输入法变量 add by justin.su 2013-10-30
	private String foodToSql;
	private String fitcolFood;
	private String displaycolFood;
	// 菜名ID
	private Long foodId;
	// 菜名
	private String foodName;
	// 菜系列表
	private List<PensionDicCuisine> pensionDicCuisineList = new ArrayList<PensionDicCuisine>();
	private String photoPath;// 用来存储photo的相对路径
	private boolean addFlag = false;
	// 菜品ID
	private Long cuisineId;

	private Long itemId;

	private String itemName;

	private List<PensionFoodIngredient> itemList;

	private PensionFoodIngredient selectedItem;

	private String itemQty;

	private String unit;

	/**
	 * 页面初始化方法
	 * 
	 * @Title: init
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-9-4 下午3:23:12
	 * @version V1.0
	 */
	@PostConstruct
	public void init() {
		// 新增按钮可用
		addButtonEnableFlag = false;
		// 修改按钮不可用
		modifyButtonEnableFlag = true;
		// 删除按钮不可用
		deleteButtonEnableFlag = true;
		// 加载所有的菜单列表
		pensionFoodmenuList = menuMaintainService.getAllFoodmenuList();
		initFoodToSql();
		// 加载所有菜系列表
		pensionDicCuisineList = menuMaintainService.getAllPensionDicCuisine();
	}

	public void initCuisineList() {

	}

	/**
	 * 
	 * @Title: initFoodToSql
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-10-31 上午9:52:22
	 * @version V1.0
	 */
	public void initFoodToSql() {
		foodToSql = " select" + " pf.id as foodId" + " ,pf.name as foodName"
				+ " ,pf.inputcode as inputCode" + " from"
				+ " pension_foodmenu pf" + " where" + " pf.cleared = 2";
		fitcolFood = "inputCode";
		displaycolFood = "编号:0,菜名:1,输入码:2";
	}

	/**
	 * 
	 * @Title: initSearchParam
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-10-31 上午9:53:41
	 * @version V1.0
	 */
	public void initSearchParam() {
		foodId = null;
		foodName = null;
		cuisineId = new Long(0);
	}

	/**
	 * 
	 * @Title: searchRecordByCondition
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-10-31 上午10:08:55
	 * @version V1.0
	 */
	public void searchRecordByCondition() {
		if (foodName == null || "".equals(foodName.trim())) {
			foodId = null;
		}
		pensionFoodmenuList = menuMaintainService
				.getAllFoodmenuListByCondition(foodId, cuisineId);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"查询完毕！", ""));
	}

	/**
	 * 
	 * @Title: setSelectedFlag
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-10-31 上午10:19:35
	 * @version V1.0
	 */
	public void setSelectedFlag(SelectEvent event) {
		modifyButtonEnableFlag = false;
		deleteButtonEnableFlag = false;
	}

	/**
	 * 
	 * @Title: setUnselectedFlag
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-10-31 上午10:19:39
	 * @version V1.0
	 */
	public void setUnselectedFlag(UnselectEvent event) {
		modifyButtonEnableFlag = true;
		deleteButtonEnableFlag = true;
	}

	/**
	 * 
	 * @Title: handleFileUpload
	 * @Description: TODO
	 * @param @param event
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-10-31 下午4:35:53
	 * @version V1.0
	 */
	public void handleFileUpload(FileUploadEvent event) {
		photoPath = null;
		// 图片上传到Tomcat发布目录下的uploaded目录
		UploadedFile file = event.getFile();
		ServletContext servletContext = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String fileName = file.getFileName();
		String upFileName = DateUtil.getFormatDateTime(new Date()) + ".jpg";
		String sourceFileName = servletContext.getRealPath("") + File.separator
				+ "uploaded" + File.separator + fileName;
		String targetFileName = servletContext.getRealPath("") + File.separator
				+ "images" + File.separator + "caterManage" + File.separator
				+ upFileName;

		photoPath = File.separator + "images" + File.separator + "caterManage"
				+ File.separator + upFileName;
		photoPath = photoPath.replace("\\", "/");
		if (addFlag) {
			pensionFoodmenu.setImageurl(photoPath);
		} else {
			selectedPensionFoodmenu.setImageurl(photoPath);
		}
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
			FacesMessage msg = new FacesMessage("图片上传成功！", event.getFile()
					.getFileName());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 删除Tomcat发布目录下的uploaded目录下的文件
		try {
			FileUtil.deleteFile(sourceFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Title: addMenu
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-2 下午4:41:25
	 * @version V1.0
	 */
	public void addMenu() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (pensionFoodmenu.getImageurl() == null
				|| "".equals(pensionFoodmenu.getImageurl().trim())) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先上传图片！", ""));
			return;
		}
		List<String> nameList = menuMaintainService.getAllMenuName();
		if (nameList.contains(pensionFoodmenu.getName())) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "菜名设定重复 ！", ""));
			return;
		}

		if (itemList == null || itemList.size() == 0) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "食材列表不可为空 ！", ""));
			return;
		}
		pensionFoodmenu.setCleared(2);
		pensionFoodmenu.setInputcode(Spell.getFirstSpell(pensionFoodmenu
				.getName()));
		menuMaintainService.insertMenuBySelective(pensionFoodmenu, itemList);
		if (foodName == null || "".equals(foodName.trim())) {
			foodId = null;
		}
		pensionFoodmenuList = menuMaintainService
				.getAllFoodmenuListByCondition(foodId, cuisineId);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"菜品" + pensionFoodmenu.getName() + "新增成功！", ""));
		pensionFoodmenu = new PensionFoodmenu();
		pensionFoodmenu.setCuisineId(0L);
		itemList = new ArrayList<PensionFoodIngredient>();
	}

	/**
	 * 
	 * @Title: getAddInputCode
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午10:57:06
	 * @version V1.0
	 */
	public void getAddInputCode() {
		pensionFoodmenu.setInputcode(Spell.getFirstSpell(pensionFoodmenu
				.getName()));
	}

	/**
	 * 
	 * @Title: getEditInputCode
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-12 上午10:57:37
	 * @version V1.0
	 */
	public void getEditInputCode() {
		selectedPensionFoodmenu.setInputcode(Spell
				.getFirstSpell(selectedPensionFoodmenu.getName()));
	}

	/**
	 * 
	 * @Title: initFoodMenu
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-4 上午8:41:38
	 * @version V1.0
	 */
	public void initFoodMenu() {
		addFlag = true;
		pensionFoodmenu = new PensionFoodmenu();
		itemList = new ArrayList<PensionFoodIngredient>();
		itemId = null;
		itemName = null;
	}

	/**
	 * 
	 * @Title: editButtonPara
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-4 上午10:06:02
	 * @version V1.0
	 */
	public void editButtonPara() {
		addFlag = false;
		itemList = menuMaintainService
				.selectItemCatalogs(selectedPensionFoodmenu.getId());
		itemId = null;
		itemName = null;
	}

	/**
	 * 
	 * @Title: editMenu
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-4 上午10:49:40
	 * @version V1.0
	 */
	public void editMenu() {
		FacesContext context = FacesContext.getCurrentInstance();
		RequestContext requestContext = RequestContext.getCurrentInstance();

		if (selectedPensionFoodmenu.getImageurl() == null
				|| "".equals(selectedPensionFoodmenu.getImageurl().trim())) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "请先上传图片！", ""));
			return;
		}
		List<String> nameList = menuMaintainService
				.getOtherMenuName(selectedPensionFoodmenu);
		if (nameList.contains(selectedPensionFoodmenu.getName())) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "菜名设定重复 ！", ""));
			return;
		}
		if (itemList == null || itemList.size() == 0) {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "食材列表不可为空 ！", ""));
			return;
		}
		selectedPensionFoodmenu.setInputcode(Spell
				.getFirstSpell(selectedPensionFoodmenu.getName()));
		menuMaintainService.updateMenuBySelective(selectedPensionFoodmenu,
				itemList);
		if (foodName == null || "".equals(foodName.trim())) {
			foodId = null;
		}
		pensionFoodmenuList = menuMaintainService
				.getAllFoodmenuListByCondition(foodId, cuisineId);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"菜品" + selectedPensionFoodmenu.getName() + "修改成功！", ""));
		requestContext.addCallbackParam("hide", true);
	}

	/**
	 * 
	 * @Title: deleteMenu
	 * @Description: TODO
	 * @param
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-4 上午11:04:55
	 * @version V1.0
	 */
	public void deleteMenu() {
		FacesContext context = FacesContext.getCurrentInstance();
		menuMaintainService.deleteMenu(selectedPensionFoodmenu);
		if (foodName == null || "".equals(foodName.trim())) {
			foodId = null;
		}
		pensionFoodmenuList = menuMaintainService
				.getAllFoodmenuListByCondition(foodId, cuisineId);
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"菜品" + selectedPensionFoodmenu.getName() + "删除成功！", ""));
	}

	public void addFoodItem() {
		if (itemId == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "食材名称不可为空！",
							""));
			return;
		}
		Float qty = 0.0F;
		try {
			if (itemQty == null || itemQty.equals("")) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "数量不可为空！",
								""));
				return;
			}
			qty = Float.valueOf(itemQty);
			if (qty.compareTo(0.0f) != 1) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN,
								"数量请输入正数！", ""));
				return;
			}
		} catch (NumberFormatException e) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"数量请输入数字！", ""));
		}
		if (unit == null || unit.equals("")) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN,
									"单位不可为空！", ""));
			return;
		}
		PensionFoodIngredient temp = new PensionFoodIngredient();
		temp.setItemId(itemId);
		temp.setIngredientQty(qty);
		temp.setName(itemName);
		temp.setQtyUnit(unit);
		itemList.add(temp);

		itemId = null;
		itemName = "";
		itemQty = "";
		unit = "";
	}

	public void deleteFoodItem(PensionFoodIngredient item) {
		itemList.remove(item);
	}

	/**
	 * @return the menuMaintainService
	 */
	public MenuMaintainService getMenuMaintainService() {
		return menuMaintainService;
	}

	/**
	 * @param menuMaintainService
	 *            the menuMaintainService to set
	 */
	public void setMenuMaintainService(MenuMaintainService menuMaintainService) {
		this.menuMaintainService = menuMaintainService;
	}

	/**
	 * @return the addButtonEnableFlag
	 */
	public boolean isAddButtonEnableFlag() {
		return addButtonEnableFlag;
	}

	/**
	 * @param addButtonEnableFlag
	 *            the addButtonEnableFlag to set
	 */
	public void setAddButtonEnableFlag(boolean addButtonEnableFlag) {
		this.addButtonEnableFlag = addButtonEnableFlag;
	}

	/**
	 * @return the modifyButtonEnableFlag
	 */
	public boolean isModifyButtonEnableFlag() {
		return modifyButtonEnableFlag;
	}

	/**
	 * @param modifyButtonEnableFlag
	 *            the modifyButtonEnableFlag to set
	 */
	public void setModifyButtonEnableFlag(boolean modifyButtonEnableFlag) {
		this.modifyButtonEnableFlag = modifyButtonEnableFlag;
	}

	/**
	 * @return the deleteButtonEnableFlag
	 */
	public boolean isDeleteButtonEnableFlag() {
		return deleteButtonEnableFlag;
	}

	/**
	 * @param deleteButtonEnableFlag
	 *            the deleteButtonEnableFlag to set
	 */
	public void setDeleteButtonEnableFlag(boolean deleteButtonEnableFlag) {
		this.deleteButtonEnableFlag = deleteButtonEnableFlag;
	}

	/**
	 * @return the pensionFoodmenuList
	 */
	public List<PensionFoodmenu> getPensionFoodmenuList() {
		return pensionFoodmenuList;
	}

	/**
	 * @param pensionFoodmenuList
	 *            the pensionFoodmenuList to set
	 */
	public void setPensionFoodmenuList(List<PensionFoodmenu> pensionFoodmenuList) {
		this.pensionFoodmenuList = pensionFoodmenuList;
	}

	/**
	 * @return the foodToSql
	 */
	public String getFoodToSql() {
		return foodToSql;
	}

	/**
	 * @param foodToSql
	 *            the foodToSql to set
	 */
	public void setFoodToSql(String foodToSql) {
		this.foodToSql = foodToSql;
	}

	/**
	 * @return the fitcolFood
	 */
	public String getFitcolFood() {
		return fitcolFood;
	}

	/**
	 * @param fitcolFood
	 *            the fitcolFood to set
	 */
	public void setFitcolFood(String fitcolFood) {
		this.fitcolFood = fitcolFood;
	}

	/**
	 * @return the displaycolFood
	 */
	public String getDisplaycolFood() {
		return displaycolFood;
	}

	/**
	 * @param displaycolFood
	 *            the displaycolFood to set
	 */
	public void setDisplaycolFood(String displaycolFood) {
		this.displaycolFood = displaycolFood;
	}

	/**
	 * @return the foodId
	 */
	public Long getFoodId() {
		return foodId;
	}

	/**
	 * @param foodId
	 *            the foodId to set
	 */
	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}

	/**
	 * @return the foodName
	 */
	public String getFoodName() {
		return foodName;
	}

	/**
	 * @param foodName
	 *            the foodName to set
	 */
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	/**
	 * @return the selectedPensionFoodmenu
	 */
	public PensionFoodmenu getSelectedPensionFoodmenu() {
		return selectedPensionFoodmenu;
	}

	/**
	 * @param selectedPensionFoodmenu
	 *            the selectedPensionFoodmenu to set
	 */
	public void setSelectedPensionFoodmenu(
			PensionFoodmenu selectedPensionFoodmenu) {
		this.selectedPensionFoodmenu = selectedPensionFoodmenu;
	}

	/**
	 * @return the pensionFoodmenu
	 */
	public PensionFoodmenu getPensionFoodmenu() {
		return pensionFoodmenu;
	}

	/**
	 * @param pensionFoodmenu
	 *            the pensionFoodmenu to set
	 */
	public void setPensionFoodmenu(PensionFoodmenu pensionFoodmenu) {
		this.pensionFoodmenu = pensionFoodmenu;
	}

	/**
	 * @return the pensionDicCuisineList
	 */
	public List<PensionDicCuisine> getPensionDicCuisineList() {
		return pensionDicCuisineList;
	}

	/**
	 * @param pensionDicCuisineList
	 *            the pensionDicCuisineList to set
	 */
	public void setPensionDicCuisineList(
			List<PensionDicCuisine> pensionDicCuisineList) {
		this.pensionDicCuisineList = pensionDicCuisineList;
	}

	/**
	 * @return the addFlag
	 */
	public boolean isAddFlag() {
		return addFlag;
	}

	/**
	 * @param addFlag
	 *            the addFlag to set
	 */
	public void setAddFlag(boolean addFlag) {
		this.addFlag = addFlag;
	}

	/**
	 * @return the cuisineId
	 */
	public Long getCuisineId() {
		return cuisineId;
	}

	/**
	 * @param cuisineId
	 *            the cuisineId to set
	 */
	public void setCuisineId(Long cuisineId) {
		this.cuisineId = cuisineId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemList(List<PensionFoodIngredient> itemList) {
		this.itemList = itemList;
	}

	public List<PensionFoodIngredient> getItemList() {
		return itemList;
	}

	public void setSelectedItem(PensionFoodIngredient selectedItem) {
		this.selectedItem = selectedItem;
	}

	public PensionFoodIngredient getSelectedItem() {
		return selectedItem;
	}

	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}

	public String getItemQty() {
		return itemQty;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}

}
