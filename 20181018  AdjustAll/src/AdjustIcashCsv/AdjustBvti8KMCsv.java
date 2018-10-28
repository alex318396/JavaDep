package AdjustIcashCsv;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class AdjustBvti8KMCsv {
	
	LinkedList<OneDayTwoSessionBean> beanlist=new LinkedList<OneDayTwoSessionBean>();
	//LinkedList<String> mytarget=new LinkedList<String>();
	LinkedHashMap<String, String> mytarget = new LinkedHashMap<String, String> ();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AdjustBvti8KMCsv Bvti=new AdjustBvti8KMCsv();
		Bvti.getTarget();
		Bvti.readFile();
	}
	//BvtiAdjust\BvtiTarget
	
	
	public void getTarget() 
	{
		String home="BvtiAdjust\\BvtiTarget";
		File allFile=new File(home);
		//System.out.println(home+"\\"+allFile.list()[0]);
		mytarget.put(allFile.list()[0], home+"\\"+allFile.list()[0]);
		
	}
	public void readFile()
	{

		LinkedList<OneDayTwoSessionBean> beanlist=new LinkedList<OneDayTwoSessionBean>();
		for(Entry<String, String> entry:mytarget.entrySet())
		{
			
			System.out.println("明細表: "+entry.getKey());
			BufferedReader bf =null;
			DataInputStream in=null;
			int line=1;

			try {
				in = new DataInputStream(new FileInputStream(new File(entry.getValue())));
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
						if(temp.length==15)
						{
							//一般
							//setDataTwentyTwo(bean, temp);
							setDataFifteen(bean, temp);
						}
						else if(temp.length==9) 
						{
							//特許
							//setDataNine(bean, temp);
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

	}
	private void setDataFifteen(OneDayTwoSessionBean bean, String[] temp) {
		// TODO Auto-generated method stub
		/* 15  card_no	card_iss_comp	card_type	route_id	comp_no	operator	bus_id	u_tran_date	
		 * d_tran_date	u_boarding	d_lighting	u_trancode	u_deduct	d_deduct	ecr_type
		*/
		if(!temp[0].equals("NULL")&&temp[0]!=null)
		{
			bean.setCardno_u(temp[0]);
		}
		if(!temp[2].equals("NULL")&&temp[2]!=null)
		{
			bean.setCard_type_u(temp[2]);
		}
		if(!temp[3].equals("NULL")&&temp[3]!=null)
		{
			bean.setRoute_id_u(temp[3]);
		}
		if(!temp[6].equals("NULL")&&temp[6]!=null)
		{
			bean.setBus_plate_u(temp[6]);
		}
		if(!temp[7].equals("NULL")&&temp[7]!=null)
		{	//2018/09/30 17:50:29
			bean.setDatetime_u(temp[7].replace("/", "").replace(":", "").replace(" ", ""));
		}
		if(!temp[8].equals("NULL")&&temp[8]!=null)
		{
			bean.setDatetime_d(temp[8].replace("/", "").replace(":", "").replace(" ", ""));
		}
		
		
		
	}
		
}
