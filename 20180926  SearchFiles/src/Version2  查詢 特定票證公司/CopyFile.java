
/* Vestsion 2  增加效率 直接查詢該目錄 以及板號
 * Vestsion 3  增加效率 直接查詢該目錄 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class CopyFile{
   static String version="version3";
	public static void main(String[] args){
		CopyFile cp=new CopyFile();
		System.out.println("版號"+version);
		Map<String ,LinkedList<String>> target= new LinkedHashMap<>();
		
		cp.getTargetList(target);
		
		LinkedList<String> filelist=new LinkedList<>();
		
		cp.getTargetFile(target,filelist);
		
		


	
		
		
		
//		System.out.println(AllFiles.size());
		for(String file:filelist) {
			String[]temp=file.split("\\\\");

			String Destination=".\\output\\"+temp[temp.length-1];
			
			File target1=new File(file);
			File Dest=new File(Destination);

			try {
				cp.copyFileUsingFileChannels(target1,Dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

    }
	public void getTargetList(Map<String ,LinkedList<String>> target)
	{
		String filePath=".\\fileList.txt";
		String key="";
		
		try {
			
			 	FileReader fr = new FileReader(filePath);//抓CSV檔進java
			 	BufferedReader brdFile = new BufferedReader(fr);//bufferedReader
			 	String strLine = null;
			 	while(brdFile.ready()) 
			 	{	strLine = brdFile.readLine();
			 		if(strLine.matches("00220.*")) 
			 		{	key="C:\\\\TCPS\\\\FTP_ROOT\\\\ECCSIS2.BAK";
			 			if(target.containsKey(key)) 
			 			{	 
			 				LinkedList<String> temp=target.get(key);
			 				temp.add(strLine);
			 				target.put(key,temp);
			 			} else {
			 				LinkedList<String> temp= new LinkedList<>();
			 				temp.add(strLine);
			 				target.put(key, temp);
			 			}
			 		}
			 		else if(strLine.matches("BVTI.*")) 
			 		{
			 			key="C:\\\\TCPS\\\\FTP_ROOT\\\\BVTI.BAK";
			 			if(target.containsKey(key)) 
			 			{	 
			 				LinkedList<String> temp=target.get(key);
			 				temp.add(strLine);
			 				target.put(key,temp);
			 			} else {
			 				LinkedList<String> temp= new LinkedList<>();
			 				temp.add(strLine);
			 				target.put(key, temp);
			 			}
			 		}
			 		
			 		System.out.println(strLine);

			 	}
			}catch(Exception e) 
			{
				System.out.println(e.getMessage());
			}
		
		
		
	}
	public void getTargetFile(Map<String ,LinkedList<String>> target,LinkedList<String> filelist) 
	{
		
		for(Map.Entry<String ,LinkedList<String>> entry:target.entrySet()) 
		{
			LinkedList<String> filetar=entry.getValue();
			String dir=entry.getKey();
			File home= new File(dir);
			String[] dirs=home.list();
			for(String dirs_dir:dirs) 
			{	
				File temp=new File(dir+"\\\\"+dirs_dir);
			
				
				checkDir(temp,filetar,filelist);
				
			}

		}
	}
	
	public void checkDir(File dir,LinkedList<String> filetar,LinkedList<String> filelist) 
	{	
		String home=dir.getAbsolutePath();
		if(dir.isDirectory()) 
		{	
			
			
			for(String temp:dir.list()) 
			{
				File newdir=new File(home+"\\\\"+temp);
				checkDir(newdir,filetar,filelist);
			}
			
		} else 
		{
			if(filetar.contains(dir.getName())) 
			{
				filelist.add(dir.getAbsolutePath());
			}
			
		}
		
	}
	

	

	private void copyFileUsingFileChannels(File source, File dest) throws IOException 
	{    
	        FileChannel inputChannel = null;    
	        FileChannel outputChannel = null;    
	    try {
	        inputChannel = new FileInputStream(source).getChannel();
	        outputChannel = new FileOutputStream(dest).getChannel();
	        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	    } finally {
	        inputChannel.close();
	        outputChannel.close();
	    }
	}
  
   
}
