package AdjustIcashCsv;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class AdjustIcashCsv {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AdjustIcashCsv Icash=new AdjustIcashCsv();
		LinkedList<String > onedaylist=new LinkedList<String>();
		LinkedList<String > havevaluelist=new LinkedList<String>();
		LinkedList<String > tashlist=new LinkedList<String>();
		LinkedList<String > transferlist=new LinkedList<String>();
		

//      一日兩吃 報表調帳
		Icash.getOneDayList(onedaylist);
		LinkedList<OneDayTwoSessionBean> OneDayBean=Icash.readFile(onedaylist);
		Icash.outPutOneDaySqlFile(OneDayBean);
//		有值報表調帳
		Icash.getHaveValueList(havevaluelist);
		LinkedList<OneDayTwoSessionBean> HaveValueBean=Icash.readFile(havevaluelist);
		Icash.outPutHaveValueSqlFile(HaveValueBean);
//		特許交易調帳
		Icash.getTashList(tashlist);
		LinkedList<OneDayTwoSessionBean> tashbean=Icash.readFile(tashlist);
		Icash.outTashSqlFile(tashbean);
//		轉乘調帳
		Icash.getTransferList(transferlist);
		LinkedList<OneDayTwoSessionBean> transferbean=Icash.readFile(transferlist);
		Icash.outPutTransferSqlFile(transferbean);
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
		 			//sb.append("update trx_detail set free_bus_amount="+String.valueOf(bean.getFree_bus_amount_u()));
		 			sb.append("update trx_detail set free_bus_amount= 12");
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
	public void getTargetList(LinkedList<String> mytarget)
	{
		String Target="WorkSpace//Target";

		File file=new File(Target);

		String []list = file.list();

		for(String target:list)
		{
		//	System.out.println(target);
			mytarget.add(Target+"//"+target);
		}

	}
	

	public void getOneDayList(LinkedList<String> mytarget)
	{
		String OneDayDir="WorkSpace//OneDayTwoSession";

		File file=new File(OneDayDir);

		String []list = file.list();

		for(String target:list)
		{
		//	System.out.println(target);
			mytarget.add(OneDayDir+"//"+target);
		}

	}
	public void getHaveValueList(LinkedList<String> mytarget)
	{
		String HaveValueDir="WorkSpace//HaveValue";

		File file=new File(HaveValueDir);

		String []list = file.list();

		for(String target:list)
		{
		//	System.out.println(target);
			mytarget.add(HaveValueDir+"//"+target);
		}

	}
	
	//Tash
	public void getTashList(LinkedList<String> mytarget)
	{
		String TashDir="WorkSpace//Tash";

		File file=new File(TashDir);

		String []list = file.list();

		for(String target:list)
		{
		//	System.out.println(target);
			mytarget.add(TashDir+"//"+target);
		}

	}
	
	//Transfer
	
	public void getTransferList(LinkedList<String> mytarget)
	{
		String Transfer="WorkSpace//Transfer";

		File file=new File(Transfer);

		String []list = file.list();

		for(String target:list)
		{
		//	System.out.println(target);
			mytarget.add(Transfer+"//"+target);
		}

	}

	public LinkedList<OneDayTwoSessionBean> readFile(LinkedList<String> mytarget)
	{

		LinkedList<OneDayTwoSessionBean> beanlist=new LinkedList<OneDayTwoSessionBean>();
		for(String filepath:mytarget)
		{
			System.out.println(filepath);
			BufferedReader bf =null;
			DataInputStream in=null;
			int line=1;

			try {


				in = new DataInputStream(new FileInputStream(new File(filepath)));
				bf=new BufferedReader(new InputStreamReader(in,"ASCII"));
				while(bf.ready())
				{
					OneDayTwoSessionBean bean = new OneDayTwoSessionBean();
					String strLine=bf.readLine();
					if(line==1)
					{
						line++;

					} else {


						//System.out.println(strLine);
						String temp[] = strLine.split(",");
						if(temp.length==22)
						{
							//一般
							setDataTwentyTwo(bean, temp);
						}
						else if(temp.length==9) 
						{
							//特許
							setDataNine(bean, temp);
						}

						beanlist.add(bean);


					}

				}


			} catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				System.out.println("readFile part 1 error: "+e.getMessage());
			}catch(Exception e){
				System.out.println("readFile part 2 error: "+e.getMessage());
			}
			finally {
				try {
					bf.close();
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}


		}
		return beanlist;
	}
	//特許專用  沒有上下車之分 已上車來表示
	public void setDataNine(OneDayTwoSessionBean bean , String []temp) 
	{
		if(!temp[0].equals("NULL")&&temp[0]!=null)
		{
			bean.setBus_plate_u(temp[0]);
		}
		if(!temp[1].equals("NULL")&&temp[1]!=null)
		{
			bean.setRoute_id_u(temp[1]);
		}
		if(!temp[2].equals("NULL")&&temp[2]!=null)
		{
			bean.setCardno_u(temp[2]);
		}
		if(!temp[4].equals("NULL")&&temp[4]!=null)
		{
			bean.setDatetime_u(temp[4]);
		}
		if(!temp[7].equals("NULL")&&temp[7]!=null)
		{
			bean.setFname_u(temp[7]);
		}
		if(!temp[8].equals("NULL")&&temp[8]!=null)
		{
			bean.setCal_date_u(temp[8]);
		}
		
		
	}
	//   扣款有值 一日兩段 轉乘適用
	public void setDataTwentyTwo(OneDayTwoSessionBean bean , String []temp)
	{
		if(!temp[0].equals("NULL")&&temp[0]!=null)
		{
			bean.setBus_plate_u(temp[0]);
		}
		if(!temp[1].equals("NULL")&&temp[1]!=null)
		{
			bean.setRoute_id_u(temp[1]);
		}
		if(!temp[2].equals("NULL")&&temp[2]!=null)
		{
			bean.setCardno_u(temp[2]);
		}
		if(!temp[4].equals("NULL")&&temp[4]!=null)
		{
			bean.setDatetime_u(temp[4]);
		}
		if(!temp[8].equals("NULL")&&temp[8]!=null)
		{
			bean.setFree_bus_amount_u(Integer.parseInt(temp[8]));
		}
		if(!temp[9].equals("NULL")&&temp[9]!=null)
		{
			bean.setBus_plate_d(temp[9]);
		}
		if(!temp[10].equals("NULL")&&temp[10]!=null)
		{
			bean.setRoute_id_d(temp[10]);
		}

//下車車9號	下車路10線編號	下車11卡號	下車12票種 下車交13易時間	下車交14易金額	下車轉15乘優惠	下車票15差優惠	下車1 16免費金額	上車來源17檔	上車清18分日	下車來19源檔	下車清20分日

		if(!temp[11].equals("NULL")&&temp[11]!=null)
		{
			bean.setCardno_d(temp[11]);
		}
		if(!temp[13].equals("NULL")&&temp[13]!=null)
		{
			bean.setDatetime_d(temp[13]);
		}
		if(!temp[17].equals("NULL")&&temp[17]!=null)
		{
			bean.setFree_bus_amount_d(Integer.parseInt(temp[17]));
		}
		if(!temp[18].equals("NULL")&&temp[18]!=null)
		{
			bean.setFname_u(temp[18]);
		}
		if(!temp[19].equals("NULL")&&temp[19]!=null)
		{
			bean.setCal_date_u(temp[19]);
		}
		if(!temp[20].equals("NULL")&&temp[20]!=null)
		{
			bean.setFname_d(temp[20]);
		}
		if(!temp[21].equals("NULL")&&temp[21]!=null)
		{
			bean.setCal_date_d(temp[21]);
		}
	}
}
