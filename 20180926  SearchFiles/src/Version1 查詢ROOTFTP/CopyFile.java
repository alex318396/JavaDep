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
import java.util.LinkedList;

public class CopyFile{
  
	public static void main(String[] args){
		CopyFile cp=new CopyFile();
		String Dir="C:\\TCPS\\FTP_ROOT";
		LinkedList<String> AllFiles=new LinkedList<String>();
		LinkedList<String> TargetList=new LinkedList<String>();
		cp.getTargetList(TargetList);
		cp.getAllFiles(Dir,AllFiles,TargetList);
		
		
		
//		System.out.println(AllFiles.size());
		for(String file:AllFiles) {
			String[]temp=file.split("\\\\");

			String Destination=".\\output\\"+temp[temp.length-1];
			
			File target=new File(file);
			File Dest=new File(Destination);

			try {
				cp.copyFileUsingFileChannels(target,Dest);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

    }
	public LinkedList<String> getTargetList(LinkedList<String> TargetList)
	{
		String filePath=".\\fileList.txt";
		
		try {
			
			 	FileReader fr = new FileReader(filePath);//抓CSV檔進java
			 	BufferedReader brdFile = new BufferedReader(fr);//bufferedReader
			 	String strLine = null;
			 	while((strLine = brdFile.readLine())!=null) 
			 	{
			 		System.out.println(strLine);
			 		TargetList.add(strLine);
			 	}
			}catch(Exception e) 
			{
				System.out.println("error");
			}
		return TargetList;
		
		
	}
	public LinkedList<String> getAllFiles(String Home,LinkedList<String> AllFiles,LinkedList<String> TargetList) 
	{   String Dir=Home;
		File a = new File(Dir);

		String[] filenames;
		String fullpath = a.getAbsolutePath();
		if(a.isDirectory()){
			 filenames=a.list();
			 for (int i = 0 ; i < filenames.length ; i++){         
				 File tempFile = new File(fullpath + "\\" + filenames[i]);
			     if(tempFile.isDirectory())
			     {	 Dir=Home+"\\"+filenames[i];
			    	 getAllFiles(Dir,AllFiles,TargetList);
//			    	 System.out.println("目錄:" + filenames[i]);
			     } else {
			    	 //System.out.println("檔案:" + filenames[i]);
			    	//System.out.println("路徑:"+fullpath+"\\" + filenames[i]);
			    	 for(String targ :TargetList) 
			    	 {
			    		 if(filenames[i].equals(targ)) 
			    		 {
			    			 AllFiles.add(fullpath+"\\" + filenames[i]);
			    		 }
			    	 }
			    	 Dir="C:\\TCPS\\FTP_ROOT";
			    	 
			    
			     }
			      }
		 } else {
			 System.out.println("[" + a + "]不是目錄");
		 } 	

		 return AllFiles;
		
	}
	
	public void readFiles() 
	{
		
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
