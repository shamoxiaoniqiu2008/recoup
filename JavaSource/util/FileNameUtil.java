package util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取文件的扩展名
 * @author seven.xu
 *
 */
public class FileNameUtil {
	/*
	 * Java文件操作 获取文件扩展名
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}
	
	public static String getExtensionName2(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('?');
			//if ((dot > -1) && (dot < (filename.length() - 1))) {
				String ss = filename.substring(dot + 1);
				System.out.println(filename.substring(dot + 1));
				return getExtensionName(ss);
			//}
		}
		return filename;
	}

	/*
	 * Java文件操作 获取不带扩展名的文件名
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}
	
	public static String getFileNameNoEx2(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('?');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}
	/**
	 * 获取文件新的文件名称
	 * 用当前日期精确到秒
	 */
	public static String getNewFileName(String filename){
		Date date=new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
		String newName=simpleDateFormat.format(date)+"."+getExtensionName(filename);
		return newName;
	}
	public static String getNewFileName2(String filename){
		Date date=new Date();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
		String newName=simpleDateFormat.format(date)+"."+getExtensionName2(filename);
		return newName;
	}
	
	/**
	 * jason的汉字检测算法
	 * @param fileName
	 * @return
	 */
	public static String getTempName(String fileName){
	    String tempNewName;
		if(fileName.getBytes().length == fileName.length()) {//非汉字
	    	tempNewName = FileNameUtil.getNewFileName(fileName);
	    } else {
	    	 try {
	    		 fileName = new String(fileName.getBytes(), "UTF-8");
	   		} catch (UnsupportedEncodingException e1) {
	   			e1.printStackTrace();
	   		}
	    	tempNewName = FileNameUtil.getNewFileName2(fileName);
	    }
		return tempNewName;
	}
}

