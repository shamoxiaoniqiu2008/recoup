package test;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


public class jasonTest {

	
	
	public static String getPinYin(String inputString) {  
        
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();  
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);  
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);  
  
        char[] input = inputString.trim().toCharArray();  
        StringBuffer output = new StringBuffer("");  
  
        try {  
            for (int i = 0; i < input.length; i++) {  
                if (Character.toString(input[i]).matches("[//u4E00-//u9FA5]+")) {  
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);  
                    output.append(temp[0]);  
                    output.append(" ");  
                } else  
                    output.append(Character.toString(input[i]));  
            }  
        } catch (BadHanyuPinyinOutputFormatCombination e) {  
            e.printStackTrace();  
        }  
        return output.toString();  
    }  
      
	
	public static void sadsf(short asdf) {
		
		for(int i=1 ;i<10; i++){
			switch(asdf) {
				case 1:  case 2: //体检项目 //组合项目
					System.out.println("ddddd"+asdf);
					break;
				case 3: //套餐
					System.out.println("zzzz"+asdf);
				default:
					System.out.println("数据异常");
					return;
			}
		}
		System.out.println("ddddddddddddddd=====");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		 String chs = "我是中国人! I'm Chinese!";  
	        System.out.println(chs);  
	        System.out.println(getPinYin(chs));  
		/*String s1="010";
		Long l1=Long.valueOf(s1);
		System.out.println("justin:"+l1);
		System.exit(0);
		
		
		 sadsf((short)4) ;
		System.exit(0);
		// TODO Auto-generated method stub
		List<Long> list = new ArrayList<Long>();
		list.add(1L);
		list.add(2L);
		list.add(3L);
		list.add(4L);
		list.add(5L);
		list.add(6L);
		list.add(7L);
		list.add(8L);
		
		System.out.println(list.size());
		List<Long> asd = list.subList(0, 8);
		
		System.out.println(asd.size());
		
		System.exit(0);
		
		byte aab = (byte) 125;
		if(aab < 0) {
			int aac = 127 + 128 + aab+1 ;
			System.out.print(aac);
		}
		
		System.out.println(aab);
		
		System.exit(0);*/
	}

}
