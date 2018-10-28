package AdjustIcashCsv;

public class OneDayTwoSessionBean {
	
//上車車號	上車路線編號	上車卡號	上車票種	上車交易時間	上車交易金額	上車轉乘優惠	上車票差優惠	上車免費金額	下車車號	下車路線編號	下車卡號	下車票種	下車交易時間	下車交易金額	下車轉乘優惠	下車票差優惠	下車免費金額	上車來源檔	上車清分日	下車來源檔	下車+A1:V1清分日

private String bus_plate_u;
private String bus_plate_d;
private String route_id_u;
private String route_id_d;
private String cardno_d;
private String datetime_d;
private String cardno_u;
private String datetime_u;
private int free_bus_amount_u;
private int free_bus_amount_d;
private String fname_u;
private String fname_d;
private String cal_date_u;
private String cal_date_d;
private String card_type_u;
private String card_type_d;



public String getCardno_d() {
	return cardno_d;
}

public void setCardno_d(String cardno_d) {
	this.cardno_d = cardno_d;
}

public String getDatetime_d() {
	return datetime_d;
}

public void setDatetime_d(String datetime_d) {
	this.datetime_d = datetime_d;
}


public String getFname_u() {
	return fname_u;
}

public void setFname_u(String fname_u) {
	this.fname_u = fname_u;
}

public String getFname_d() {
	return fname_d;
}

public void setFname_d(String fname_d) {
	this.fname_d = fname_d;
}

public String getCal_date_u() {
	return cal_date_u;
}

public void setCal_date_u(String cal_date_u) {
	this.cal_date_u = cal_date_u;
}

public String getCal_date_d() {
	return cal_date_d;
}

public void setCal_date_d(String cal_date_d) {
	this.cal_date_d = cal_date_d;
}

public String getCardno_u() {
	return cardno_u;
}

public void setCardno_u(String cardno_u) {
	this.cardno_u = cardno_u;
}

public String getDatetime_u() {
	return datetime_u;
}

public void setDatetime_u(String datetime_u) {
	this.datetime_u = datetime_u;
}

public int getFree_bus_amount_u() {
	return free_bus_amount_u;
}

public void setFree_bus_amount_u(int free_bus_amount_u) {
	this.free_bus_amount_u = free_bus_amount_u;
}

public int getFree_bus_amount_d() {
	return free_bus_amount_d;
}

public void setFree_bus_amount_d(int free_bus_amount_d) {
	this.free_bus_amount_d = free_bus_amount_d;
}

public String getBus_plate_u() {
	return bus_plate_u;
}

public void setBus_plate_u(String bus_plate_u) {
	this.bus_plate_u = bus_plate_u;
}

public String getBus_plate_d() {
	return bus_plate_d;
}

public void setBus_plate_d(String bus_plate_d) {
	this.bus_plate_d = bus_plate_d;
}

public String getRoute_id_u() {
	return route_id_u;
}

public void setRoute_id_u(String route_id_u) {
	String tempR=route_id_u;
	if(tempR.length()<4) 
	{
		for(int i=tempR.length();i<4;i++) 
		{
			tempR="0"+tempR;
		}
	} 
	this.route_id_u = tempR;
}

public String getRoute_id_d() {
	return route_id_d;
}

public void setRoute_id_d(String route_id_d) {
	this.route_id_d = route_id_d;
}

public String getCard_type_u() {
	return card_type_u;
}

public void setCard_type_u(String card_type_u) {
	this.card_type_u = card_type_u;
}

public String getCard_type_d() {
	return card_type_d;
}

public void setCard_type_d(String card_type_d) {
	this.card_type_d = card_type_d;
}




}
