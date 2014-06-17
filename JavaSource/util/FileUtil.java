/**  
 * @Title: FileUtil.java 
 * @Package util 
 * @Description: TODO
 * @author Justin.Su
 * @date 2013-11-1 下午3:34:20 
 * @version V1.0
 * @Copyright: Copyright (c) Centling Co.Ltd. 2013
 * ★★★★★★★★版权所有※拷贝必究 ★★★★★★★★
 */
package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName: FileUtil
 * @Description: 文件操作工具类
 * @author Justin.Su
 * @date 2013-11-1 下午3:34:20
 * @version V1.0
 */
public class FileUtil {

	/**
	 * 
	 * @Title: copyFile
	 * @Description: 复制文件
	 * @param @param sourceFile
	 * @param @param targetFile
	 * @param @throws IOException
	 * @return void
	 * @throws
	 * @author Justin.Su
	 * @date 2013-11-1 下午3:36:44
	 * @version V1.0
	 */
	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);
		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);
		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();
		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}

	/**
	 * 
	* @Title: copyDirectiory 
	* @Description: 复制文件夹
	* @param @param sourceDir
	* @param @param targetDir
	* @param @throws IOException
	* @return void
	* @throws 
	* @author Justin.Su
	* @date 2013-11-1 下午3:37:47
	* @version V1.0
	 */
	public static void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}
	
	/**  
	 * 删除单个文件  
	 * @param   sPath    被删除文件的文件名  
	 * @return 单个文件删除成功返回true，否则返回false  
	 */  
	public static void deleteFile(String sPath) throws IOException{   
	    File file = new File(sPath);   
	    // 路径为文件且不为空则进行删除   
	    if (file.isFile() && file.exists()) {   
	        file.delete();   
	    }   
	}  
	
	/**  
	 *  根据路径删除指定的目录或文件，无论存在与否  
	 *@param sPath  要删除的目录或文件  
	 *@return 删除成功返回 true，否则返回 false。  
	 */  
	public static void DeleteFolder(String sPath) throws IOException{   
	   File file = new File(sPath);   
	    // 判断目录或文件是否存在   
	    if (!file.exists()) {  // 不存在返回 false   
	    } else {   
	        // 判断是否为文件   
	        if (file.isFile()) {  // 为文件时调用删除文件方法   
	            deleteFile(sPath);   
	        } else {  // 为目录时调用删除目录方法   
	            deleteDirectory(sPath);   
	        }   
	    }   
	}  
	
	
	/**  
	 * 删除目录（文件夹）以及目录下的文件  
	 * @param   sPath 被删除目录的文件路径  
	 * @return  目录删除成功返回true，否则返回false  
	 */  
	public static void deleteDirectory(String sPath) throws IOException{   
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符   
	    if (!sPath.endsWith(File.separator)) {   
	        sPath = sPath + File.separator;   
	    }   
	    File dirFile = new File(sPath);   
	    //如果dir对应的文件不存在，或者不是一个目录，则退出   
	    if (!dirFile.exists() || !dirFile.isDirectory()) {   
	    }   
	    boolean flag = true;   
	    //删除文件夹下的所有文件(包括子目录)   
	    File[] files = dirFile.listFiles();   
	    for (int i = 0; i < files.length; i++) {   
	        //删除子文件   
	        if (files[i].isFile()) {   
	            deleteFile(files[i].getAbsolutePath());   
	            if (!flag) break;   
	        } //删除子目录   
	        else {   
	            deleteDirectory(files[i].getAbsolutePath());   
	        }   
	    }   
	} 
	
	
}
