
/* Vestsion 2  增加效率 直接查詢該目錄 以及板號
 * Vestsion 3  增加效率 直接查詢該目錄 
 * Vestsion 4  增加愛金卡
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
   static String version="version4";
	public static void main(String[] args){
		CopyFile cp=new CopyFile();
		System.out.println("版號"+version);


	
		//Map<String ,LinkedList<String>> target= new LinkedHashMap<>();  version 3
		LinkedList<String> target=new LinkedList<>(); //version 3
		cp.getTargetList(target);
		

		

		
		


	
		
		
		
		System.out.println(target.size());
		for(String file:target) {
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



	

	

	private void copyFileUsingFileChannels(File source, File dest) throws IOException 
	{    
	        FileChannel inputChannel = null;    
	        FileChannel outputChannel = null;    
	    try {
	        inputChannel = new FileInputStream(source).getChannel();
	        outputChannel = new FileOutputStream(dest).getChannel();
	        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	    } catch(Exception e) 
	    {
	    	System.out.println(e.getMessage());
	    } finally 
	    {	
	    	outputChannel.close();
	    	inputChannel.close();	
	    }
	}
	

	//version 3
	public LinkedList<String> getTargetList(LinkedList<String> fileList)
	{
		String filePath=".\\fileList.txt";
		FileReader fr = null;
		BufferedReader brdFile = null ;
		try {
			
			 	fr = new FileReader(filePath);//抓CSV檔進java
			 	brdFile = new BufferedReader(fr);//bufferedReader
			 	String strLine = null;
			 	while(brdFile.ready()) 
			 	{   strLine = brdFile.readLine().trim();
			 		System.out.println(strLine);
			 		if(strLine.matches("00220.*")) 
					{	
						//00220.033_00000033_00017_02803.20180927183020_001U.DAT

					    String Dir= strLine.split("_")[3].substring(6, 14);

						String file="C:\\TCPS\\FTP_ROOT\\ECCSIS2.BAK\\"+Dir+"\\"+strLine;

						fileList.add(file);
						System.out.println(file);
					}
			 		else if(strLine.matches("BVTI_.*")) 
			 		{
			 			//BVTI_00160403085420180722210504.DAT
			 			String Dir= strLine.split("_")[1].substring(12,20);
			 			String file="C:\\TCPS\\FTP_ROOT\\BVTI.BAK"+"\\"+Dir+"\\"+strLine;
			 			fileList.add(file);
			 			System.out.println(file);
			 		}
			 		else if(strLine.matches("ICTX2LOG_BV.*")) 
			 		{
			 			//ICTX2LOG_BV-00000429-20180910154241.dat
			 			String Dir=strLine.split("-")[2].substring(0, 8);
			 			String file="C:\\TCPS\\FTP_ROOT\\iCash_Trx.BAK"+"\\"+Dir+"\\"+strLine;
			 			fileList.add(file);
			 			System.out.println(file);
			 		}

			 	}
			 	
			}
		catch(Exception e) {
				System.out.println(e.getMessage());
			}
		finally {

			try {
				brdFile.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileList;
		
		
	}
	public void getFlieList(LinkedList<String> target,LinkedList<String> Filelist) 
	{
		for(String tar:target) 
		{
			
			
		}
	}
}
