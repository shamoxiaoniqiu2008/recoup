/**
 * 
 */
package service.recoup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.recoup.RecoupDicCostclass1;
import domain.recoup.RecoupDicCostclass1Example;
import domain.recoup.RecoupDicCostclass2;
import domain.recoup.RecoupDicCostclass2Example;
import domain.recoup.RecoupDicPayclass;
import domain.recoup.RecoupDicPayclassExample;
import domain.recoup.RecoupDicProject;
import domain.recoup.RecoupDicProjectExample;
import persistence.recoup.RecoupDicCostclass1Mapper;
import persistence.recoup.RecoupDicCostclass2Mapper;
import persistence.recoup.RecoupDicPayclassMapper;
import persistence.recoup.RecoupDicProjectMapper;
import util.DateUtil;
import util.FileUtil;

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
	
	/**
	 * 
	 * @return
	 */
	public List<RecoupDicProject> selectAllProjects(){
		RecoupDicProjectExample example = new RecoupDicProjectExample();
		example.or().andClearedEqualTo(2);
		return recoupDicProjectMapper.selectByExample(example);
	}
	
	
	public List<RecoupDicPayclass> selectAllPayclasses(){
		RecoupDicPayclassExample example = new RecoupDicPayclassExample();
		example.or().andClearedEqualTo(2);
		example.setOrderByClause("sort_by asc");
		return recoupDicPayclassMapper.selectByExample(example);
	}
	
	public List<RecoupDicCostclass1> selectAllCostclasses1(){
		RecoupDicCostclass1Example example = new RecoupDicCostclass1Example();
		example.or().andClearedEqualTo(2);
		return recoupDicCostclass1Mapper.selectByExample(example);
	}
	
	
	
	public List<RecoupDicCostclass2> selectAllCostclasses2(){
		RecoupDicCostclass2Example example = new RecoupDicCostclass2Example();
		example.or().andClearedEqualTo(2);
		return recoupDicCostclass2Mapper.selectByExample(example);
	}


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
}
