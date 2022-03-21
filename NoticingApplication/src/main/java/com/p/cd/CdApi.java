package com.p.cd;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import com.p.appconfiguration.Log;

public class CdApi {
	private String seqNo=" ";
	private String mkt=" ";
	private String trdNo=" ";
	private String trdTm=" ";
	private String tkn=" ";
	private String trdQty=" ";
	private String trdPrc=" ";
	private String bsFlg=" ";
	private String ordNo=" ";
	private String brnCd =" ";
	private String usrId=" ";
	private String proCli=" ";
	private String cliActNo=" ";
	private String cpCd=" ";
	private String remarks=" ";
	private String actTyp=" ";
	private String TCd=" ";
	private String ordTm=" ";
	private String booktype=" ";
	private String oppTmCd=" ";
	private String ctclId=" ";
	private String status=" ";
	private String TmCd=" ";
	private String sym=" ";
	private String ser=" ";
	private String inst=" ";
	private String expDt=" ";
	private String strPrc=" ";
	private String optType=" ";
	private String fill1=" ";
	private String fill2=" ";
	private String fill3=" ";
	private String fill4=" ";
	private String fill5=" ";
	private String fill6=" ";
	private String fill7=" ";
	private String fill8=" ";
	
	
	
	public String getBooktype() {
		return booktype;
	}
	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}
	public String getOppTmCd() {
		return oppTmCd;
	}
	public void setOppTmCd(String oppTmCd) {
		this.oppTmCd = oppTmCd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTmCd() {
		return TmCd;
	}
	public void setTmCd(String tmCd) {
		TmCd = tmCd;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getMkt() {
		return mkt;
	}
	public void setMkt(String mkt) {
		this.mkt = mkt;
	}
	public String getTrdNo() {
		return trdNo;
	}
	public void setTrdNo(String trdNo) {
		this.trdNo = trdNo;
	}
	public String getTrdTm() {
		return trdTm;
	}
	public void setTrdTm(String trdTm) {
		this.trdTm=this.getTrdTime(Long.valueOf(trdTm));
		/*this.trdTm = trdTm;*/
	}
	public String getTkn() {
		return tkn;
	}
	public void setTkn(String tkn) {
		this.tkn = tkn;
	}
	public String getTrdQty() {
		return trdQty;
	}
	public void setTrdQty(String trdQty) {
		this.trdQty = trdQty;
	}
	public String getTrdPrc() {
		return trdPrc;
	}
	public void setTrdPrc(String trdPrc) {
		this.trdPrc = trdPrc;
	}
	public String getBsFlg() {
		return bsFlg;
	}
	public void setBsFlg(String bsFlg) {
		this.bsFlg = bsFlg;
	}
	public String getOrdNo() {
		return ordNo;
	}
	public void setOrdNo(String ordNo) {
		this.ordNo = ordNo;
	}
	public String getBrnCd() {
		return brnCd;
	}
	public void setBrnCd(String brnCd) {
		this.brnCd = brnCd;
	}
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	public String getProCli() {
		return proCli;
	}
	public void setProCli(String proCli) {
		this.proCli = proCli;
	}
	public String getCliActNo() {
		return cliActNo;
	}
	public void setCliActNo(String cliActNo) {
		this.cliActNo = cliActNo;
	}
	public String getCpCd() {
		return cpCd;
	}
	public void setCpCd(String cpCd) {
		this.cpCd = cpCd;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getActTyp() {
		return actTyp;
	}
	public void setActTyp(String actTyp) {
		this.actTyp = actTyp;
	}
	public String getTCd() {
		return TCd;
	}
	public void setTCd(String tCd) {
		TCd = tCd;
	}
	public String getOrdTm() {
		return ordTm;
	}
	public void setOrdTm(String ordTm) {		
		this.ordTm=this.getOrdTime(Long.valueOf(ordTm));
	}
	
	
	
	
	
	
	public String getCtclId() {
		return ctclId;
	}
	public void setCtclId(String ctclId) {
		this.ctclId = ctclId;
	}
	
	public String getSym() {
		return sym;
	}
	public void setSym(String sym) {
		this.sym = sym;
	}
	public String getSer() {
		return ser;
	}
	public void setSer(String ser) {
		this.ser = ser;
	}
	public String getInst() {
		return inst;
	}
	public void setInst(String inst) {
		this.inst = inst;
	}
	public String getExpDt() {
		return expDt;
	}
	public void setExpDt(String expDt) {
		this.expDt= this.getExpDate(Long.valueOf(expDt));
		//this.expDt = expDt;
	}
	public String getStrPrc() {
		return strPrc;
	}
	public void setStrPrc(String strPrc) {
		this.strPrc = strPrc;
	}
	public String getOptType() {
		return optType;
	}
	public void setOptType(String optType) {
		this.optType = optType;
	}
	public String getFill1() {
		return fill1;
	}
	public void setFill1(String fill1) {
		this.fill1 = fill1;
	}
	public String getFill2() {
		return fill2;
	}
	public void setFill2(String fill2) {
		this.fill2 = fill2;
	}
	public String getFill3() {
		return fill3;
	}
	public void setFill3(String fill3) {
		this.fill3 = fill3;
	}
	public String getFill4() {
		return fill4;
	}
	public void setFill4(String fill4) {
		this.fill4 = fill4;
	}
	public String getFill5() {
		return fill5;
	}
	public void setFill5(String fill5) {
		this.fill5 = fill5;
	}
	public String getFill6() {
		return fill6;
	}
	public void setFill6(String fill6) {
		this.fill6 = fill6;
	}
	public String getFill7() {
		return fill7;
	}
	public void setFill7(String fill7) {
		this.fill7 = fill7;
	}
	public String getFill8() {
		return fill8;
	}
	public void setFill8(String fill8) {
		this.fill8 = fill8;
	}
	@Override
	public String toString() {
		return seqNo + "," + 
				mkt + "," + 
				trdNo + "," + 
				trdTm + "," + 
				tkn + "," + 
				trdQty + "," + 
				trdPrc + "," + 
				bsFlg + "," + 
				ordNo + "," + 
				brnCd  + "," + 
				usrId + "," + 
				proCli + "," + 
				cliActNo + "," + 
				cpCd + "," + 
				remarks + "," + 
				actTyp + "," + 
				TCd + "," + 
				ordTm + "," + 
				booktype + "," + 
				oppTmCd + "," + 
				ctclId + "," + 
				status + "," + 
				TmCd + "," + 
				sym + "," + 
				ser + "," + 
				inst + "," + 
				expDt + "," + 
				strPrc + "," + 
				optType + "," + 
				fill1 + "," + 
				fill2 + "," + 
				fill3 + "," + 
				fill4 + "," + 
				fill5 + "," + 
				fill6 + "," + 
				fill7 + "," + 
				fill8;
	}
	
	
	//----
	
	
		//1.
		
			public  String getTrdTime(long dtValjiffies)
			{
				String date="";
				try{
				 long retDate = (int)(dtValjiffies / 65536L) + 315513000l;
				 Date newDate = new Date(retDate * 1000);
				 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
				 simpleDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
				 date = simpleDateFormat.format(newDate);
				}catch(Exception e)
				{
					System.out.println("Exception while parsing getTrdTime ");
					Log.notice.debug("Exception while parsing getTrdTime: "+dtValjiffies+ " jiffes" );
				}
				 //System.out.println("TRADETIME: :=========>"+date);
				 return date;
			}
			
			//2.
			
			public  String getOrdTime(long orddtValjiffies)
			{
				String date="";
				try{ 
				 long retDate = (int)(orddtValjiffies / 65536L) + 315513000l;
				 Date newDate = new Date(retDate * 1000);
				 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
				 simpleDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
				 date = simpleDateFormat.format(newDate);
				 //System.out.println("ordtime:=========>"+date);
				}catch(Exception e )
				{
					System.out.println("Exception while parsing getOrdTime ");
					Log.notice.debug("Exception while parsing getOrdTime: "+orddtValjiffies+ " jiffes" );	
				}
				 return date;
			}
			
			
			//3.
			
			public String getExpDate(long exprydatesecs)
			{	
				String formattedDate="";
				try{
				CharSequence kk="01011980 00:00:00";
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm:ss");
				LocalDateTime datetime = LocalDateTime.parse(kk, dateTimeFormatter); //date time calculate 1980
				datetime=datetime.plusSeconds(exprydatesecs);
				formattedDate = datetime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				}catch(Exception e){
					System.out.println("Exception while parsing getExpDate ");
					Log.notice.debug("Exception while parsing getExpDate: "+exprydatesecs);	
				}
					
				return formattedDate;
			}
			
			
	
	
	
	
}
