/**
 * 
 */
package controller.recoup;

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

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.onlyfido.util.SessionManager;

import service.recoup.RecoupApplyService;
import util.DateUtil;
import util.FileUtil;
import domain.recoup.RecoupApplyDetailExtend;
import domain.recoup.RecoupApplyRecordExtend;
import domain.recoup.RecoupDicCostclass1;
import domain.recoup.RecoupDicCostclass2;
import domain.recoup.RecoupDicPayclass;
import domain.recoup.RecoupDicProject;

/**
 * @author justin
 *
 */

public class RecoupApplyController implements Serializable{
	
	
	  
		/** 
		* @Fields serialVersionUID : TODO
		* @version V1.0
		*/   
	    
	private static final long serialVersionUID = 100000000000001L;
	
	private transient RecoupApplyService recoupApplyService;
	
	private List<RecoupApplyRecordExtend> recordList = new ArrayList<RecoupApplyRecordExtend>();

	private RecoupApplyRecordExtend recordForAdd = new RecoupApplyRecordExtend();

	private List<RecoupApplyDetailExtend> detailListForAdd = new ArrayList<RecoupApplyDetailExtend>();
	
	private RecoupApplyDetailExtend detailForAdd = new RecoupApplyDetailExtend();
	
	private RecoupApplyDetailExtend selectedDetail = new RecoupApplyDetailExtend();
	
	private List<RecoupDicProject> projects = new ArrayList<RecoupDicProject>();
	
	private List<RecoupDicPayclass> payclasses = new ArrayList<RecoupDicPayclass>();
	
	private List<RecoupDicCostclass1> costclasses1 = new ArrayList<RecoupDicCostclass1>();
	
	private List<RecoupDicCostclass2> costclasses2 = new ArrayList<RecoupDicCostclass2>();
	
	private String photoPath = null;
	
	private boolean addFlag = false;
	
	@PostConstruct
	public void init(){
		payclasses = new ArrayList<RecoupDicPayclass>();
		projects = new ArrayList<RecoupDicProject>();
		costclasses1 = new ArrayList<RecoupDicCostclass1>();
		costclasses2 = new ArrayList<RecoupDicCostclass2>();
		projects = recoupApplyService.selectAllProjects();
		payclasses = recoupApplyService.selectAllPayclasses();
		costclasses1 = recoupApplyService.selectAllCostclasses1();
		costclasses2 = recoupApplyService.selectAllCostclasses2();
	}
	
	public void getDetailDefaultValue(){
		detailForAdd = new RecoupApplyDetailExtend();
		detailForAdd.setFeeDatetime(DateUtil.parseDate(DateUtil.getDate(new Date()), "yyyy-MM-dd"));
	}
	
	public void getRecordDefaultValue(){
		recordForAdd = new RecoupApplyRecordExtend();
		recordForAdd.setNeme(SessionManager.getCurEmployee().getName());
		recordForAdd.setApplyDate(DateUtil.parseDate(DateUtil.getDate(new Date()), "yyyy-MM-dd"));
	}
	
	public void addDetailList(){
		if(detailForAdd.equals(null) || detailForAdd.equals("")){
			return;
		}else{
			RecoupApplyDetailExtend detail = new RecoupApplyDetailExtend();
			detail = detailForAdd;
			detailListForAdd.add(detail);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"明细添加成功！" , "明细添加成功！"));
		}	
	}
	
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
				+ "images" + File.separator + "invoice" + File.separator
				+ upFileName;

		photoPath = File.separator + "images" + File.separator + "invoice"
				+ File.separator + upFileName;
		photoPath = photoPath.replace("\\", "/");
		if (addFlag) {
			detailForAdd.setImageUrl(photoPath);
		} else {
			selectedDetail.setImageUrl(photoPath);
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
		 * @return the recordForAdd
		 */  
	    
	public RecoupApplyRecordExtend getRecordForAdd() {
		return recordForAdd;
	}



	  
	    /**
		 * @param recordForAdd the recordForAdd to set
		 */  
	    
	public void setRecordForAdd(RecoupApplyRecordExtend recordForAdd) {
		this.recordForAdd = recordForAdd;
	}




		  
			/**
			 * @return the detailListForAdd
			 */  
		    
		public List<RecoupApplyDetailExtend> getDetailListForAdd() {
			return detailListForAdd;
		}




		  
		    /**
			 * @param detailListForAdd the detailListForAdd to set
			 */  
		    
		public void setDetailListForAdd(List<RecoupApplyDetailExtend> detailListForAdd) {
			this.detailListForAdd = detailListForAdd;
		}




			  
				/**
				 * @return the detailForAdd
				 */  
			    
			public RecoupApplyDetailExtend getDetailForAdd() {
				return detailForAdd;
			}




			  
			    /**
				 * @param detailForAdd the detailForAdd to set
				 */  
			    
			public void setDetailForAdd(RecoupApplyDetailExtend detailForAdd) {
				this.detailForAdd = detailForAdd;
			}


				  
					/**
					 * @return the recoupApplyService
					 */  
				    
				public RecoupApplyService getRecoupApplyService() {
					return recoupApplyService;
				}


				  
				    /**
					 * @param recoupApplyService the recoupApplyService to set
					 */  
				    
				public void setRecoupApplyService(RecoupApplyService recoupApplyService) {
					this.recoupApplyService = recoupApplyService;
				}

					/**
					 * @return the projects
					 */
					public List<RecoupDicProject> getProjects() {
						return projects;
					}

					/**
					 * @param projects the projects to set
					 */
					public void setProjects(List<RecoupDicProject> projects) {
						this.projects = projects;
					}

					public String getPhotoPath() {
						return photoPath;
					}

					public void setPhotoPath(String photoPath) {
						this.photoPath = photoPath;
					}

					/**
					 * @return the selectedDetail
					 */
					public RecoupApplyDetailExtend getSelectedDetail() {
						return selectedDetail;
					}

					/**
					 * @param selectedDetail the selectedDetail to set
					 */
					public void setSelectedDetail(RecoupApplyDetailExtend selectedDetail) {
						this.selectedDetail = selectedDetail;
					}

					/**
					 * @return the recordList
					 */
					public List<RecoupApplyRecordExtend> getRecordList() {
						return recordList;
					}

					/**
					 * @param recordList the recordList to set
					 */
					public void setRecordList(List<RecoupApplyRecordExtend> recordList) {
						this.recordList = recordList;
					}

					/**
					 * @return the payclasses
					 */
					public List<RecoupDicPayclass> getPayclasses() {
						return payclasses;
					}

					/**
					 * @param payclasses the payclasses to set
					 */
					public void setPayclasses(List<RecoupDicPayclass> payclasses) {
						this.payclasses = payclasses;
					}

					/**
					 * @return the costclasses1
					 */
					public List<RecoupDicCostclass1> getCostclasses1() {
						return costclasses1;
					}

					/**
					 * @param costclasses1 the costclasses1 to set
					 */
					public void setCostclasses1(List<RecoupDicCostclass1> costclasses1) {
						this.costclasses1 = costclasses1;
					}

					/**
					 * @return the costclasses2
					 */
					public List<RecoupDicCostclass2> getCostclasses2() {
						return costclasses2;
					}

					/**
					 * @param costclasses2 the costclasses2 to set
					 */
					public void setCostclasses2(List<RecoupDicCostclass2> costclasses2) {
						this.costclasses2 = costclasses2;
					}




}
