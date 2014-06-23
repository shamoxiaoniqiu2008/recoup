package controller.system;

import java.io.Serializable;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import domain.system.PensionDept;

import service.system.PensionDeptDoman;
import service.system.DeptManageService;
import util.PmsException;
import util.Spell;


/**
 * Created by JBoss Tools
 */

public class DeptManageController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private transient ItempurseManageService itempurseManageService;
	private transient DeptManageService deptManageService;
	
/*	public Long searchItemId;
	private PensionItempurse pensionItempurse;
	private PensionItempurse pensionItempurseNow;
	private List<PensionItempurse> pensionItempurseList;*/
	private String testStr;
	private TreeNode root;
	private TreeNode child;
	private TreeNode selectedNode;
	List<PensionDeptDoman> PensionDeptDomanList;
	
	private PensionDeptDoman pensionDeptDoman;
	private PensionDeptDoman pensionDeptDomanNew;
	
	@PostConstruct
	public void  init() {
		//allUser = userManageService.findAllUser();
		System.out.println("加载成功");
		fetchDeptList();
		
		createTree();
	}
	

	public void createTree() {
		root = new DefaultTreeNode("全部部门", null);
		root.setExpanded(true);
		PensionDept pensionDept = new PensionDept();

		//DictSectionInfo sectionInfo = new DictSectionInfo();
		pensionDept.setName("全部");
		pensionDept.setId(0L);
		child = new DefaultTreeNode(pensionDept, root);
		//设置一个默认的选中节点。
		child.setSelected(true);
		
		
		selectedNode = child;
		
		List<PensionDept> chilePensionDepts = deptManageService.fetchChildSections(0L);
		buildTree(child, chilePensionDepts);
		//PensionDeptDomanList  = deptManageService.fetchPensionDeptDomanList(0L);
	}

	public void freshTree() {
		root = new DefaultTreeNode("全部部门", null);
		root.setExpanded(true);
		PensionDept pensionDept = new PensionDept();

		//DictSectionInfo sectionInfo = new DictSectionInfo();
		pensionDept.setName("全部");
		pensionDept.setId(0L);
		child = new DefaultTreeNode(pensionDept, root);
		//设置一个默认的选中节点。
		child.setSelected(true);
		List<PensionDept> chilePensionDepts = deptManageService.fetchChildSections(0L);
		buildTree(child, chilePensionDepts);
	}
	// 递归构建科室树
	public void buildTree(TreeNode root, List<PensionDept> pensionDepts) {
		for (PensionDept sectionInfo : pensionDepts) {
			TreeNode treeNode = new DefaultTreeNode(sectionInfo, root);
			treeNode.setExpanded(true);
			List<PensionDept> childPensionDeptns 
			= deptManageService.fetchChildSections(sectionInfo.getId());
			if (childPensionDeptns.size() > 0) {
				buildTree(treeNode, childPensionDeptns);
			}
		}
	}

	// 选择节点
	public void onNodeSelect(NodeSelectEvent event) {
		event.getTreeNode().setSelected(true);
		long currentNodeSectionId = ((PensionDept) event.getTreeNode()
				.getData()).getId();
		PensionDeptDomanList  = deptManageService.fetchPensionDeptDomanList(currentNodeSectionId);

	}
	

	public void fetchDeptList() {
		PensionDept curPensionDept;
		if(selectedNode == null) {
			curPensionDept = new PensionDept();
		} else {
			curPensionDept =  (PensionDept)selectedNode.getData();
		}
		
		PensionDeptDomanList = deptManageService.fetchPensionDeptDomanList(curPensionDept.getId());
	}
	
	
	//获取首字母
	public void readPinYin() {
		String pinyin = Spell.getFirstSpell(pensionDeptDomanNew.getName());
		pensionDeptDomanNew.setInputcode(pinyin);
	}
	
	//添加按钮
	public void showAddForm() {
		pensionDeptDomanNew  = new PensionDeptDoman();
	}
	
	/**
	 * 、修改
	 */
	public void showSelectOneForm() {
		pensionDeptDomanNew = pensionDeptDoman;
		final RequestContext request = RequestContext.getCurrentInstance();
		if(pensionDeptDomanNew == null || pensionDeptDomanNew.getId() == null) {
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请选择要修改/删除的部门" , ""));
			request.addCallbackParam("edit", false);
			return;
		}
		pensionDeptDomanNew  = pensionDeptDoman;
		request.addCallbackParam("selected", true);
	}
	
	//保存数据
	public void saveDept() {
		RequestContext request = RequestContext.getCurrentInstance();
		if(pensionDeptDomanNew.getName() == null || pensionDeptDomanNew.getName().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请输入名称" , ""));
			request.addCallbackParam("close", false);
			return ;
		}
		try {
			List<PensionDept> tempPensionDepts = deptManageService.selectByName(pensionDeptDomanNew.getName());
			if(pensionDeptDomanNew.getId() == null){ //新增
				
				if(tempPensionDepts != null && tempPensionDepts.size()>0) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"【"+pensionDeptDomanNew.getName()+"】已存在" , ""));
					//RequestContext requests = RequestContext.getCurrentInstance();
					request.addCallbackParam("close", false);
					return;
				} else {
					PensionDept curPensionDept =  (PensionDept)selectedNode.getData();
					pensionDeptDomanNew.setParentId(curPensionDept.getId());
					deptManageService.insertDept(pensionDeptDomanNew);
				}
				request.addCallbackParam("close", false);
			} else {
				if(tempPensionDepts != null && tempPensionDepts.size()>0 
						&& !tempPensionDepts.get(0).getId().equals(pensionDeptDomanNew.getId())) {
					
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO,
									"【"+pensionDeptDomanNew.getName()+"】已存在" , ""));
					freshTree();
					fetchDeptList();
					request.addCallbackParam("close", false);
					return;
					
				} else {
					deptManageService.updateDept(pensionDeptDomanNew);
					request.addCallbackParam("close", true);
				}
			}
			freshTree();
			fetchDeptList();
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
	
	public void delDept() {
		if(pensionDeptDomanNew == null || pensionDeptDomanNew.getId() == null) {
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"请选择要删除的部门" , ""));
			return;
		}

		try {
			deptManageService.updateDeptForDel(pensionDeptDomanNew);
			fetchDeptList();
			freshTree();
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
	

	public TreeNode getChild() {
		return child;
	}


	public void setChild(TreeNode child) {
		this.child = child;
	}


	public TreeNode getRoot() {
		return root;
	}


	public void setRoot(TreeNode root) {
		this.root = root;
	}


	public void setTestStr(String testStr) {
		this.testStr = testStr;
	}

	public String getTestStr() {
		return testStr;
	}

	public DeptManageService getDeptManageService() {
		return deptManageService;
	}

	public void setDeptManageService(DeptManageService deptManageService) {
		this.deptManageService = deptManageService;
	}


	public TreeNode getSelectedNode() {
		return selectedNode;
	}


	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}


	public List<PensionDeptDoman> getPensionDeptDomanList() {
		return PensionDeptDomanList;
	}


	public void setPensionDeptDomanList(List<PensionDeptDoman> pensionDeptDomanList) {
		PensionDeptDomanList = pensionDeptDomanList;
	}


	public PensionDeptDoman getPensionDeptDoman() {
		return pensionDeptDoman;
	}


	public void setPensionDeptDoman(PensionDeptDoman pensionDeptDoman) {
		this.pensionDeptDoman = pensionDeptDoman;
	}


	public PensionDeptDoman getPensionDeptDomanNew() {
		return pensionDeptDomanNew;
	}


	public void setPensionDeptDomanNew(PensionDeptDoman pensionDeptDomanNew) {
		this.pensionDeptDomanNew = pensionDeptDomanNew;
	}



	



}