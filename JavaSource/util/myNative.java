package util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class myNative {

	public static double[] writeToDat(String path1,String path2,String path_out) 
	{
		File file1 = new File(path1);
		File file2 = new File(path2);
		File file_out = new File(path_out);
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		double[] nums = null;
		try 
		{
			BufferedReader bw = new BufferedReader(new FileReader(file1));
			String line = null;//因为不知道有几行数据，所以先存入list集合中
			while((line = bw.readLine()) != null)
			{
				list1.add(line);
			}
			bw.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		try 
		{
			BufferedReader bw = new BufferedReader(new FileReader(file2));
			String line = null;//因为不知道有几行数据，所以先存入list集合中
			while((line = bw.readLine()) != null)
			{
				list2.add(line);
			}
			bw.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		try 
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file_out));
			nums = new double[list1.size()];
			for(int i=0;i<list1.size();i++)
			{
				String s1 = (String) list1.get(i)+"\n";
				s1 = "#" + s1.substring(s1.indexOf('=')+1);
				String s2 = (String) list2.get(i)+"\n";
				bw.write(s1);
				bw.write(s2);
			}
			bw.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		return nums;
	}
	
	public static void main(String[] args) 
	{
		String dir = "D:/native2ascii/";
		String path1 = dir+"pageConfig_old.properties";
		String path2 = dir+"pageConfig_new.properties";
		String path_out = dir+"pageConfig_final.properties";
		double[] nums = writeToDat(path1,path2,path_out);
		for(int i=0;i<nums.length;i++)
		{
			System.out.println(nums[i]);
		}
	}
}
