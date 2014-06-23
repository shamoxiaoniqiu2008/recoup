package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.onlyfido.util.VehicleServletContextListener;

public class JavaConfig {

	public static String fetchProperty(String key) {
		
		String path =VehicleServletContextListener.curPropertyFullPath;
		path = path.replace("\\", "/");
		path = path.replace("/", File.separator);
		Properties properties = new Properties();
		InputStream in = null;
		String propertyValue = "";
		try {
			in = new FileInputStream(path);
			properties.load(in);
			//propertyValue = properties.getProperty("login.sucess");
			propertyValue = properties.getProperty(key);
			propertyValue=new String(propertyValue.getBytes("ISO-8859-1"),"utf8");
			//System.out.println(propertyValue);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return propertyValue;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return propertyValue;
		}
		
		return propertyValue;
	}
}
