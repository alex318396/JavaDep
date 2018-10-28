package readexcel;
/*
 * 20181019  讀取csv產生sql               V1
 * 20181021  不分資料夾集中產出           V2
 * 20181022  讀取excel 檔案              V3 
 * 
 * 
 * 
 * 
 * */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

import com.sun.rowset.internal.Row;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import AdjustIcashCsv.OneDayTwoSessionBean;;

public class AdjustIcashExcel {
	static String  version ="3";
	static LinkedList<OneDayTwoSessionBean > onedaylist=new LinkedList<OneDayTwoSessionBean>();
	static LinkedList<OneDayTwoSessionBean > havevaluelist=new LinkedList<OneDayTwoSessionBean>();
	static LinkedList<OneDayTwoSessionBean > tashlist=new LinkedList<OneDayTwoSessionBean>();
	static LinkedList<OneDayTwoSessionBean > transferlist=new LinkedList<OneDayTwoSessionBean>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AdjustIcashExcel Icash = new AdjustIcashExcel();
		System.out.println("Version :" + version);
		Icash.getAll();
		Icash.outPutTransferSqlFile(transferlist);
		Icash.outPutHaveValueSqlFile(havevaluelist);
		Icash.outPutOneDaySqlFile(onedaylist);
		Icash.outTashSqlFile(tashlist);
	}
	public static void setDataNine(String content , int col , OneDayTwoSessionBean bean) 
	{
		if(!content.equals("NULL")&&content!=null && col==0)
		{
			bean.setBus_plate_u(content);
		}
		if(!content.equals("NULL")&&content!=null &&col==1)
		{
			bean.setRoute_id_u(content);
		}
		if(!content.equals("NULL")&&content!=null &&col==2)
		{
			bean.setCardno_u(content);
		}
		if(!content.equals("NULL")&&content!=null &&col==4)
		{
			bean.setDatetime_u(content);
		}
		if(!content.equals("NULL")&&content!=null &&col==7)
		{
			bean.setFname_u(content);
		}
		if(!content.equals("NULL")&&content!=null &&col==8)
		{
			bean.setCal_date_u(content);
		}
		
		
	}
	//   扣款有值 一日兩段 轉乘適用
	public static void setDataTwentyTwo(String content , int col , OneDayTwoSessionBean bean)
	{
		if(!content.equals("NULL")&&content!=null && col==0)
		{
			bean.setBus_plate_u(content);
		}
		if(!content.equals("NULL")&&content!=null && col==1)
		{
			bean.setRoute_id_u(content);
		}
		if(!content.equals("NULL")&&content!=null && col==2)
		{
			bean.setCardno_u(content);
		}
		if(!content.equals("NULL")&&content!=null && col==4)
		{
			bean.setDatetime_u(content);
		}
		if(!content.equals("NULL")&&content!=null && col==8)
		{
			bean.setFree_bus_amount_u(Integer.parseInt(content));
		}
		if(!content.equals("NULL")&&content!=null && col==9)
		{
			bean.setBus_plate_d(content);
		}
		if(!content.equals("NULL")&&content!=null && col==10)
		{
			bean.setRoute_id_d(content);
		}

//下車車9號	下車路10線編號	下車11卡號	下車12票種 下車交13易時間	下車交14易金額	下車轉15乘優惠	下車票15差優惠	下車1 16免費金額	上車來源17檔	上車清18分日	下車來19源檔	下車清20分日

		if(!content.equals("NULL")&&content!=null && col==11)
		{
			bean.setCardno_d(content);
		}
		if(!content.equals("NULL")&&content!=null && col==13)
		{
			bean.setDatetime_d(content);
		}
		if(!content.equals("NULL")&&content!=null && col==17)
		{
			bean.setFree_bus_amount_d(Integer.parseInt(content));
		}
		if(!content.equals("NULL")&&content!=null && col==18)
		{
			bean.setFname_u(content);
		}
		if(!content.equals("NULL")&&content!=null && col==19)
		{
			bean.setCal_date_u(content);
		}
		if(!content.equals("NULL")&&content!=null && col==20)
		{
			bean.setFname_d(content);
		}
		if(!content.equals("NULL")&&content!=null && col==21)
		{
			bean.setCal_date_d(content);
		}
	}
	public boolean checkStatus_u(OneDayTwoSessionBean bean)
	{
		if(bean.getCal_date_u()!=null &&!bean.getCal_date_u().equals("") && bean.getDatetime_u()!=null&& !bean.getDatetime_u().equals("")) {
			return true;
		} else
		{
			return false;
		}

	}
	public boolean checkStatus_d(OneDayTwoSessionBean bean)
	{
		if(bean.getCal_date_d()!=null &&!bean.getCal_date_d().equals("") && bean.getDatetime_d()!=null&& !bean.getDatetime_d().equals("")) {

			return true;

		} else
		{
			return false;
		}

	}
	public void outPutOneDaySqlFile(LinkedList<OneDayTwoSessionBean> beanlist)
	{

		int count_u=0;
		int count_d=0;
		FileOutputStream fileOutputStream =null;
		try {
			fileOutputStream = new FileOutputStream(new File("OutputSql//adjustIcashOneDay.sql"));
			for(OneDayTwoSessionBean bean:beanlist)
			{
				if(checkStatus_u(bean))
				{
		 			StringBuilder sb=new StringBuilder();
		 			sb.append("update trx_detail set free_bus_amount="+String.valueOf(bean.getFree_bus_amount_u()));
		 			//sb.append("update trx_detail set free_bus_amount= 12");
		 			sb.append(", free_bus_flag='1',cal_status='F' ,cal_date='"+bean.getCal_date_u()
		 					+ "'  where bus_plate='"+bean.getBus_plate_u()+"'");
		 			sb.append(" and txntype='A' ");
		 			sb.append(" and unit_id='0B' ");
		 			sb.append(" and fname='"+bean.getFname_u()+".dat' ");
		 			sb.append(" and txndate='"+bean.getDatetime_u().substring(0, 8)+"' ");
		 			sb.append("and txntime='"+bean.getDatetime_u().substring(8, 14)+"'");
		 			sb.append(" and route_id='"+bean.getRoute_id_u().substring(2)+"'");
		 			sb.append(" and cardno='"+bean.getCardno_u()+"'");
		 			sb.append("  \n");
		 			//System.out.println(sb.toString());
		 			fileOutputStream.write(sb.toString().getBytes());
		 			count_u++;
				}

				if(checkStatus_d(bean))
				{
		 			StringBuilder sb=new StringBuilder();
		 			sb.append("update trx_detail set free_bus_amount="+String.valueOf(bean.getFree_bus_amount_d()));
		 			//sb.append("update trx_detail set free_bus_amount=12 ");
		 			sb.append(", free_bus_flag='1',cal_status='F' ,cal_date='"+bean.getCal_date_d()
 					+ "'  where bus_plate='"+bean.getBus_plate_d()+"'");
		 			sb.append(" and txntype='A' ");
		 			sb.append(" and unit_id='0B' ");
		 			sb.append(" and fname='"+bean.getFname_d()+".dat' ");
		 			sb.append(" and txndate='"+bean.getDatetime_d().substring(0, 8)+"' ");
		 			sb.append("and txntime='"+bean.getDatetime_d().substring(8, 14)+"'");
		 			sb.append(" and route_id='"+bean.getRoute_id_d().substring(2)+"'");
		 			sb.append(" and cardno='"+bean.getCardno_d()+"'");
		 			sb.append("  \n");
		 			//System.out.println(sb.toString());
		 			fileOutputStream.write(sb.toString().getBytes());
		 			count_d++;

				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Outputfile IoException :"+e.getMessage());
		} finally {
			try {
				int all=count_u+count_d;
				System.out.println("一日兩吃上車共"+count_u+"筆");
				System.out.println("一日兩吃下車共"+count_d+"筆");
				System.out.println("一日兩吃一共產生"+all+"筆");
				fileOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	public void outPutHaveValueSqlFile(LinkedList<OneDayTwoSessionBean> beanlist)
	{

		int count_u=0;
		int count_d=0;
		FileOutputStream fileOutputStream =null;
		try {
			fileOutputStream = new FileOutputStream(new File("OutputSql//adjustHaveValue.sql"));
			for(OneDayTwoSessionBean bean:beanlist)
			{
				if(checkStatus_u(bean))
				{
		 			StringBuilder sb=new StringBuilder();
		 			sb.append("update trx_detail set cal_status='F' ,cal_date='"+bean.getCal_date_u()+"'");
		 			sb.append(" where txntype='A' ");
		 			sb.append(" and unit_id='0B' ");
		 			sb.append(" and fname='"+bean.getFname_u()+".dat' ");
		 			sb.append(" and txndate='"+bean.getDatetime_u().substring(0, 8)+"' ");
		 			sb.append("and txntime='"+bean.getDatetime_u().substring(8, 14)+"'");
		 			sb.append(" and route_id='"+bean.getRoute_id_u().substring(2)+"'");
		 			sb.append(" and cardno='"+bean.getCardno_u()+"'");
		 			sb.append("  \n");
		 			//System.out.println(sb.toString());
		 			fileOutputStream.write(sb.toString().getBytes());
		 			count_u++;
				}

				if(checkStatus_d(bean))
				{
		 			StringBuilder sb=new StringBuilder();
		 			sb.append("update trx_detail set cal_status='F' ,cal_date='"+bean.getCal_date_d()+"'");
		 			sb.append(" where txntype='A' ");
		 			sb.append(" and unit_id='0B' ");
		 			sb.append(" and fname='"+bean.getFname_d()+".dat' ");
		 			sb.append(" and txndate='"+bean.getDatetime_d().substring(0, 8)+"' ");
		 			sb.append("and txntime='"+bean.getDatetime_d().substring(8, 14)+"'");
		 			sb.append(" and route_id='"+bean.getRoute_id_d().substring(2)+"'");
		 			sb.append(" and cardno='"+bean.getCardno_u()+"'");
		 			sb.append("  \n");
		 			//System.out.println(sb.toString());
		 			fileOutputStream.write(sb.toString().getBytes());
		 			count_d++;

				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Outputfile IoException :"+e.getMessage());
		} finally {
			try {
				int all=count_u+count_d;
				System.out.println("有值明細上車共"+count_u+"筆");
				System.out.println("有值明細下車共"+count_d+"筆");
				System.out.println("有值明細一共產生"+all+"筆");
				fileOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}
	public void outTashSqlFile(LinkedList<OneDayTwoSessionBean> beanlist)
	{

		int count_u=0;
		int count_d=0;
		FileOutputStream fileOutputStream =null;
		try {
			fileOutputStream = new FileOutputStream(new File("OutputSql//adjustTash.sql"));
			for(OneDayTwoSessionBean bean:beanlist)
			{
				if(checkStatus_u(bean))
				{
//transaction_sn=(select min(transaction_sn) from trx_detail  where txntype in('A','E')  and unit_id='0B'  and fname='ICTX2LOG_BV-00000486-20180805195510.dat'  and txndate='20180805' and txntime='195510' and route_id='0833' and cardno='7111170922006456'  )
		 			StringBuilder sb=new StringBuilder();
		 			sb.append("update trx_detail  set txntype='E',cal_status='F' ,cal_date='"+bean.getCal_date_u()+"'");
		 			sb.append(" where txntype in('A','E') ");
		 			sb.append(" and unit_id='0B' ");
		 			sb.append(" and fname='"+bean.getFname_u()+".dat' ");
		 			sb.append(" and txndate='"+bean.getDatetime_u().substring(0, 8)+"' ");
		 			sb.append("and txntime='"+bean.getDatetime_u().substring(8, 14)+"'");
		 			sb.append(" and route_id='"+bean.getRoute_id_u().substring(2)+"'");
		 			sb.append(" and cardno='"+bean.getCardno_u()+"'");
		 			sb.append(" and transaction_sn=(select min(transaction_sn) from trx_detail where"
		 					+ " txntype in('A','E')  and"
		 					+ " unit_id='0B' and txndate ='"+bean.getDatetime_u().substring(0, 8)+"' "
		 					+ " and txntime='"+bean.getDatetime_u().substring(8, 14)+"' and route_id="
		 					+ "'"+bean.getRoute_id_u().substring(2)+"' and bus_plate='"+bean.getBus_plate_u()+"'"
		 					+ " and cardno='"+bean.getCardno_u()+"')");
		 			sb.append("  \n");
		 			//System.out.println(sb.toString());
		 			fileOutputStream.write(sb.toString().getBytes());
		 			count_u++;
				}

				if(checkStatus_d(bean))
				{
		 			StringBuilder sb=new StringBuilder();
		 			sb.append("update trx_detail  set txntype='E',cal_status='F' ,cal_date='"+bean.getCal_date_u()+"'");
		 			sb.append(" where txntype in('A','E') ");
		 			sb.append(" and unit_id='0B' ");
		 			sb.append(" and fname='"+bean.getFname_d()+".dat' ");
		 			sb.append(" and txndate='"+bean.getDatetime_d().substring(0, 8)+"' ");
		 			sb.append("and txntime='"+bean.getDatetime_d().substring(8, 14)+"'");
		 			sb.append(" and route_id='"+bean.getRoute_id_d().substring(2)+"'");
		 			sb.append(" and cardno='"+bean.getCardno_u()+"'");
		 			sb.append(" and transaction_sn=(select min(transaction_sn) from trx_detail where "
		 					+ " unit_id='0B' and txndate ='"+bean.getDatetime_d().substring(0, 8)+"' "
		 					+ " and txntime='"+bean.getDatetime_d().substring(8, 14)+"' and route_id="
		 					+ "'"+bean.getRoute_id_d()+"' and bus_plate='"+bean.getBus_plate_d()+"'"
		 					+ " and cardno='"+bean.getCardno_d()+"')");
		 			sb.append("  \n");
		 			//System.out.println(sb.toString());
		 			fileOutputStream.write(sb.toString().getBytes());
		 			count_d++;

				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Outputfile IoException :"+e.getMessage());
		} finally {
			try {
				int all=count_u+count_d;
				System.out.println("特許明細上車共"+count_u+"筆");
				System.out.println("特許明細下車共"+count_d+"筆");
				System.out.println("特許明細一共產生"+all+"筆");
				fileOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}
	public void outPutTransferSqlFile(LinkedList<OneDayTwoSessionBean> beanlist)
	{

		int count_u=0;
		int count_d=0;
		FileOutputStream fileOutputStream =null;
		try {
			fileOutputStream = new FileOutputStream(new File("OutputSql//adjustTransfer.sql"));
			for(OneDayTwoSessionBean bean:beanlist)
			{
				if(checkStatus_u(bean))
				{
		 			StringBuilder sb=new StringBuilder();
		 			sb.append("update trx_detail set cal_status='F' ,cal_date='"+bean.getCal_date_u()+"'");
		 			sb.append(" where txntype='A' ");
		 			sb.append(" and unit_id='0B' ");
		 			sb.append(" and fname='"+bean.getFname_u()+".dat' ");
		 			sb.append(" and txndate='"+bean.getDatetime_u().substring(0, 8)+"' ");
		 			sb.append("and txntime='"+bean.getDatetime_u().substring(8, 14)+"'");
		 			sb.append(" and route_id='"+bean.getRoute_id_u().substring(2)+"'");
		 			sb.append(" and cardno='"+bean.getCardno_u()+"'");
		 			sb.append("  \n");
		 			//System.out.println(sb.toString());
		 			fileOutputStream.write(sb.toString().getBytes());
		 			count_u++;
				}

				if(checkStatus_d(bean))
				{
		 			StringBuilder sb=new StringBuilder();
		 			sb.append("update trx_detail set cal_status='F' ,cal_date='"+bean.getCal_date_d()+"'");
		 			sb.append(" where txntype='A' ");
		 			sb.append(" and unit_id='0B' ");
		 			sb.append(" and fname='"+bean.getFname_d()+".dat' ");
		 			sb.append(" and txndate='"+bean.getDatetime_d().substring(0, 8)+"' ");
		 			sb.append("and txntime='"+bean.getDatetime_d().substring(8, 14)+"'");
		 			sb.append(" and route_id='"+bean.getRoute_id_d().substring(2)+"'");
		 			sb.append(" and cardno='"+bean.getCardno_u()+"'");
		 			sb.append("  \n");
		 			//System.out.println(sb.toString());
		 			fileOutputStream.write(sb.toString().getBytes());
		 			count_d++;

				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Outputfile IoException :"+e.getMessage());
		} finally {
			try {
				int all=count_u+count_d;
				System.out.println("轉乘明細上車共"+count_u+"筆");
				System.out.println("轉乘明細明細下車共"+count_d+"筆");
				System.out.println("轉乘明細一共產生"+all+"筆");
				fileOutputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void getAll() 
	{
		File Dir = new File("WorkSpace\\Target");
		String name=Dir.list()[0];
		//System.out.println(Dir+"\\"+name);
		String excelpath=Dir+"\\"+name;
		File excelfile=new File(excelpath);

		try 
		{
		    Workbook book=Workbook.getWorkbook(excelfile);//  
		    
		    for(int i=0;i<4;i++) 
		    {
		    	Sheet sheet=book.getSheet(i);   //獲得第一個工作表對象
		    	//System.out.println(sheet.getName());
		    	if(sheet.getName().equals("特許")) 
		    	{
		    		//System.out.println("------開始產生特許調整sql------");
		    		
		    		for(int rowIndex=1;rowIndex<sheet.getRows();rowIndex++)
		    		{
		    			OneDayTwoSessionBean bean =new OneDayTwoSessionBean();
		    			//System.out.println(sheet.getRows());
		    			for(int colIndex=0;colIndex<sheet.getColumns();colIndex++)
		    			{
		    				//System.out.println(rowIndex+"and" +colIndex);
		    				Cell cell=sheet.getCell(colIndex, rowIndex);
		    				
		    				//System.out.print(cell.getContents()+" ");
		    				setDataNine(cell.getContents(), colIndex, bean);
		    				
		    			}
		    			tashlist.add(bean);
		    		}
		    	}
		    	else if(sheet.getName().equals("扣款(有值)")) 
		    	{
		    		//System.out.println("------開始產生扣款(有值)sql------");
		    		for(int rowIndex=1;rowIndex<sheet.getRows();rowIndex++)
		    		{
		    			OneDayTwoSessionBean bean =new OneDayTwoSessionBean();
		    			//System.out.println(sheet.getRows());
		    			for(int colIndex=0;colIndex<sheet.getColumns();colIndex++)
		    			{
		    				//System.out.println(rowIndex+"and" +colIndex);
		    				Cell cell=sheet.getCell(colIndex, rowIndex);
		    				
		    				//System.out.print(cell.getContents()+" ");
		    				setDataTwentyTwo(cell.getContents(), colIndex, bean);
		    				
		    			}
		    			havevaluelist.add(bean);
		    			
		    		}
		    	}
		     	else if(sheet.getName().equals("一日兩段")) 
		    	{
		     		//System.out.println("------開始產生一日兩段調整sql------");
		     		for(int rowIndex=1;rowIndex<sheet.getRows();rowIndex++)
		    		{
		    			OneDayTwoSessionBean bean =new OneDayTwoSessionBean();
		    			//System.out.println(sheet.getRows());
		    			for(int colIndex=0;colIndex<sheet.getColumns();colIndex++)
		    			{
		    				//System.out.println(rowIndex+"and" +colIndex);
		    				Cell cell=sheet.getCell(colIndex, rowIndex);
		    				
		    				//System.out.print(cell.getContents()+" ");
		    				setDataTwentyTwo(cell.getContents(), colIndex, bean);
		    				
		    			}
		    			onedaylist.add(bean);
		    			
		    		}
		    	}
		     	else if(sheet.getName().equals("轉乘")) 
		    	{
		     		//System.out.println("------開始產生轉乘調整sql------");
		     		for(int rowIndex=1;rowIndex<sheet.getRows();rowIndex++)
		    		{
		    			OneDayTwoSessionBean bean =new OneDayTwoSessionBean();
		    			//System.out.println(sheet.getRows());
		    			for(int colIndex=0;colIndex<sheet.getColumns();colIndex++)
		    			{
		    				//System.out.println(rowIndex+"and" +colIndex);
		    				Cell cell=sheet.getCell(colIndex, rowIndex);
		    				
		    				//System.out.print(cell.getContents()+" ");
		    				setDataTwentyTwo(cell.getContents(), colIndex, bean);
		    				
		    			}
		    			transferlist.add(bean);
		    	
		    		}
		    	}
		    	
		    }
	        
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
}
