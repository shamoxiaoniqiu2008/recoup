package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.onlyfido.util.MD5Util;



public class Encryption {
	public static String getCPUSerial() {
		String result = "";
		try {
			File file = File.createTempFile("tmp", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);
			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
					+ "Set colItems = objWMIService.ExecQuery _ \n"
					+ "   (\"Select * from Win32_Processor\") \n"
					+ "For Each objItem in colItems \n"
					+ "    Wscript.Echo objItem.ProcessorId \n"
					+ "    exit for  ' do the first cpu only! \n" + "Next \n";

			// + "    exit for  \r\n" + "Next";
			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec(
					"cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
			file.delete();
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		if (result.trim().length() < 1 || result == null) {
			result = "err";
		}
		return result.trim();
	}
	
	
	public static String countKey(String keyWord,String  cupId) {
		//String[] kArray = keyWord.split("-");
		String sub_1 = keyWord.substring(2, 3) ;
		String sub_2 = keyWord.substring(3, 4) ;
		String sub_3 = keyWord.substring(5, 6) ;
		String sub_4 = keyWord.substring(6, 7) ;
		String sub_5 = keyWord.substring(8, 9) ;
		String sub_6 = keyWord.substring(9, 10) ;
		
		//String cupId = Encryption.getCPUSerial();
		/*System.out.println(Encryption.getCPUSerial());
		System.out.println(cupId);*/
		String encryptionStr = MD5Util.encodeStr(cupId+"CentLing");
		//System.out.println(encryptionStr);
		//encryptionencryptionStrStr
		//encryptionStr = "01234567890123456789012345678901";
		String encryptionStr_1 = encryptionStr.substring(0, 5) ;
		String encryptionStr_2 = encryptionStr.substring(5, 10) ;
		String encryptionStr_3 = encryptionStr.substring(10, 15) ;
		String encryptionStr_4 = encryptionStr.substring(15, 20) ;
		String encryptionStr_5 = encryptionStr.substring(20, 25) ;
		String encryptionStr_6 = encryptionStr.substring(25, 30) ;
		String encryptionStr_7 = encryptionStr.substring(30, 32) ;
		
		return	encryptionStr_1+ sub_1+encryptionStr_2+sub_2+encryptionStr_3+sub_3+encryptionStr_4+sub_4+encryptionStr_5+sub_5+encryptionStr_6+sub_6+encryptionStr_7;
	}
	
	public static String readDate(String keyWord) {
		
		String sub_1 = keyWord.substring(5, 6) ;
		String sub_2 = keyWord.substring(11, 12) ;
		String sub_3 = keyWord.substring(17, 18) ;
		String sub_4 = keyWord.substring(23, 24) ;
		String sub_5 = keyWord.substring(29, 30) ;
		String sub_6 = keyWord.substring(35, 36) ;
		return "20"+sub_1+sub_2+"-"+sub_3+sub_4+"-"+sub_5+sub_6;
	}
	
	public static String readKey(String keyWord) {
		
		String encryptionStr_1 = keyWord.substring(0, 5) ;
		String encryptionStr_2 = keyWord.substring(6, 11) ;
		String encryptionStr_3 = keyWord.substring(12, 17) ;
		String encryptionStr_4 = keyWord.substring(18, 23) ;
		String encryptionStr_5 = keyWord.substring(24, 29) ;
		String encryptionStr_6 = keyWord.substring(30, 35) ;
		String encryptionStr_7 = keyWord.substring(36, 38) ;
		
		return encryptionStr_1+""+encryptionStr_2+""+encryptionStr_3+""+encryptionStr_4+""+encryptionStr_5+""+encryptionStr_6+""+encryptionStr_7;
	}

}
