package util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;


public class JavaSN {

	public static String fetchProperty(String key, String path) {
		
		//String path =VehicleServletContextListener.curPropertyFullPath;
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
	
	public static void applyProperties(String curPropertyFullPath,String key , String val) throws Exception {
		File file = new File(curPropertyFullPath);
		if (file.exists()) {
			// 文件已存在
			// 取得所有属性
			LinkedHashMap<String, String> currentHashMap = (LinkedHashMap<String, String>) readProperties(curPropertyFullPath);
			//LinkedHashMap<String, String> defaultHashMap = (LinkedHashMap<String, String>) readProperties(defaultPath);
			TreeMap<String, String> appendHashMap = new TreeMap<String, String>(	);
			
			if (!currentHashMap.containsKey(key)) {
				throw new PmsException("参数不存在");
			}
			
			Iterator iter = currentHashMap.entrySet().iterator();
			// 判定新加入属性
			while (iter.hasNext()) {
				// 遍历默认列表
				Map.Entry entry = (Map.Entry) iter.next();
				// Object key = entry.getKey();
				// Object val = entry.getValue();
				if (entry.getKey().equals(key)) {
					//设置成要设置的数值
					appendHashMap.put((String) entry.getKey(),  val);
				}else {
					//当前已存在，使用现有值
					appendHashMap.put((String) entry.getKey(), (String) entry.getValue());
				}
			}
			// 将新属性写入当前配置
			setProperties(curPropertyFullPath, appendHashMap);
		} else {
			
		}
	}
	
	// 读取properties的全部信息
	private static LinkedHashMap<String, String> readProperties(String filePath) throws IOException {
		LinkedHashMap<String, String> retHashMap = new LinkedHashMap<String, String>();
		InputStream in= null;
		try {
			Properties props = new Properties();
			in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in); // 没有顺序的。。⊙﹏⊙b
			Enumeration en = props.propertyNames();

			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				retHashMap.put(key, Property);
			}
		} catch (IOException e) {
			// TODO: handle exception
			throw new IOException();
		}finally{
			if (in != null) {
				in.close();
			}
		}
		return retHashMap;
	}
	
	private static Boolean setProperties(String filePath, TreeMap<String, String> properties) throws IOException {
		OutputStream fos = null;
		try {
			OrderedProperties prop = new OrderedProperties();
			//InputStream fis = new FileInputStream(filePath);
			// 从输入流中读取属性列表（键和元素对）
			//prop.load(fis);
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			fos = new FileOutputStream(filePath);
			System.out.println("配置文件路径：" + filePath);
			Iterator iter = properties.entrySet().iterator();
			while (iter.hasNext()) {
				// 遍历
				Map.Entry entry = (Map.Entry) iter.next();
				prop.setProperty((String) entry.getKey(), (String) entry.getValue());
				System.out.println((String) entry.getKey() + " = " +  (String) entry.getValue());
			}
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos,"ok");
			
		} catch (IOException e) {
			throw new IOException();
		}finally{
			if (fos != null) {
				fos.close();
			}
		}

		
		return true;
	}
	
	private static class OrderedProperties extends Properties{
	    private static final long serialVersionUID = -4627607243846121965L;
	    private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();
	    /* (non-Javadoc)
	    * @see java.util.Hashtable#equals(java.lang.Object)
	    */
	    
	    @Override
	    public synchronized boolean equals(Object o) {
	    // TODO Auto-generated method stub
	    return super.equals(o);
	    }
	   @Override
	   public int hashCode(){
		   return super.hashCode();
	   }
	    public Enumeration<Object> keys() {
	        return Collections.<Object> enumeration(keys);
	    }
	    public Object put(Object key, Object value) {
	        keys.add(key);
	        return super.put(key, value);
	    }
	    public Set<Object> keySet() {
	        return keys;
	    }
	    public Set<String> stringPropertyNames() {
	        Set<String> set = new LinkedHashSet<String>();
	 
	        for (Object key : this.keys) {
	            set.add((String) key);
	        }
	        return set;
	    }
	}
}
