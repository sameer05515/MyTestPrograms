package com.p.appconfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import org.hibernate.Session;

import com.google.gson.Gson;
import com.p.cd.CdApi;
import com.p.cd.CdResponse;
import com.p.fo.FoApi;
import com.p.fo.FoResponse;
import com.p.hiber.HibernateUtil;
import com.p.response.cm.CmApi;
import com.p.response.cm.CmResponse;

public class Parser {
	

	public CmResponse getParseCM(String resp) throws Exception {
		

		Log.notice.info("getParseCM process....");

		Gson g = null;
		String respp = null;
		g = new Gson();
		CmResponse t = null;

		try {

			try {
				t = g.fromJson(resp, CmResponse.class);
				Log.notice.info("messageId: " + t.getData().getMsgId());
			} catch (Exception e) {
				throw e;
			}

			if (t.getStatus().equalsIgnoreCase("success")) {

				respp = t.getData().getTradesInquiry();

				StringTokenizer st = null;
				st = new StringTokenizer(respp, "^");
				ArrayList<CmApi> list = null;
				list = new ArrayList<CmApi>();

				int i = 0;
				while (st.hasMoreTokens()) {
					if (i == 0) {
						st.nextElement();
						i++;
					}

					if (i >= 1) {
						list = getData(st.nextToken(), list);
					}

				}

				

				Log.notice.info(">>>>>>>>>>>>>>>>>>>>> cm list size=====>: " + list.size());
				String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
				try (PrintStream ps = new PrintStream(PropertiesFile.getSeqLogPath() + "\\cm-" + timeStamp + ".txt");) {

					for (CmApi c : list) {
						ps.println(c);
					}
					Log.notice.info(">>>>>>>>>>>>>>>>>>>>> cm list size=====>: " + list.size());
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				
				Log.notice.info(">>>>>>>>>>>>>>>>>>>>> cm list size=====>: " + list.size());

				/*
				 * //---------resea startr
				 * 
				 * 
				 * CmApi [] k =list.toArray(new CmApi[list.size()]); DaoUtil oj=null; oj = new
				 * DaoUtil(); oj.insertCMEx(k);
				 * 
				 * //-----end
				 */
				Session cmsession = null;
				cmsession = HibernateUtil.openCmSession();
				cmsession.getTransaction().begin();

				// System.out.println("goign to call insertExp------" + new
				// Date(System.currentTimeMillis()));
				// int ii=0;
				boolean isInserted;
				isInserted = true;
				try {

					for (CmApi p : list) {

						String temp = null;
						temp = p.toString();
						try {

							// writeCmTrade(temp); // comment file write
							String[] temparray = null;

							temparray = temp.split(",");

							DaoUtil oj = null;
							oj = new DaoUtil();
							// oj.insertCM(temparray);

							oj.insertExp(temparray, cmsession);
							// ii=ii+1;
							// System.out.println("cm ii: "+ ii);

						} catch (Exception e) {
							// System.out.println("exception occured during writeCmTreade");
							Log.notice.info("exception occured during save Cm Trade");
							throw e;
						}
					}
					cmsession.getTransaction().commit();
					HibernateUtil.closeCmSession();

				} catch (Exception e) {
					/* System.out.println(e); */
					isInserted = false;
					Log.notice.info("exception occured during save Cm Trade");
					if(cmsession.isOpen()) {
						HibernateUtil.closeCmSession();
					}
				}

				if (isInserted) {
					Log.notice.info("CM DATA INSERTED SUCCESSFULLY");

				} else {

					Log.notice.info("CM DATA NOT INSERTED");
				}

				/*
				 * System.out.println("logging finish------: "+ new
				 * Date(System.currentTimeMillis()));
				 */
			} else {

				// System.out.println("DID NOT GET SUCCESS STATUS FROM CM API");
				Log.notice.info("DID NOT GET SUCCESS STATUS FROM CM API");
			}
			// System.out.println("=================================");
		} catch (Exception e) {
			t = null;

			if (e.toString().contains("NoSuchElementException")) {
				Log.notice.info("NoSuchElementException found means data not available");
				t = new CmResponse();
				t.setStatus("data not available");
			} else {
				// System.out.println(e);
				Log.notice.info("exceprtion occured during parsing cm response: ", e);

			}
		}
		return t;
	}

	// ------------------------------CD

	public CdResponse getParseCD(String resp) throws Exception {
		// resp
		// ="{\"status\":\"success\",\"messages\":{\"code\":\"01010000\"},\"data\":{
		// \"msgId\":\"08756201908130000001\",
		// \"tradesInquiry\":\"6,20190710,,,4721493,10000
		// ^627554,N,2019071225000117,09092019143140,676,8,20000,1,1100000000089183,35,15232,1,581536,08756,1,59,6001,09092019143140,1,0,N,,0,3.333333333331E14,4104,N,,,,,,,,
		// \" } }";
		// resp="{\"status\":\"success\",\"messages\":{\"code\":\"01010000\"},\"data\":{
		// \"msgId\":\"08756201908130000001\",
		// \"tradesInquiry\":\"6,20190710,,,4721493,10000^627558,N,2019071225000118,81737602367488,676,10,20000,0,1100000000089190,35,15232,1,581540,08756,1,59,6001,1247216819,1,1,N,,0,3.333333333331E14,4168,N,,,,,,,,^627554,N,2019071225000117,81737602367488,676,8,25000,1,1100000000089183,37,15232,1,581536,08756,1,59,6001,1247216819,0,0,N,,0,3.333333333331E14,4104,Y,,,,,,,,
		// \" } }";

		// System.out.println("res string is: "+ resp);
		// resp=TestClass.readUsingScanner("D:\\mnj_data\\cdcd.txt");
		Log.notice.info("getParseCD process....");
		Gson gson = null;
		String respp_cd = null;
		gson = new Gson();
		CdResponse tt = null;

		try {

			try {
				tt = gson.fromJson(resp, CdResponse.class);
				Log.notice.info("messageId: " + tt.getData().getMsgId());
			} catch (Exception e) {
				// System.out.println("here 8: "+ e);

				throw e;
			}

			// System.out.println("=> "+ t.getData().getTradesInquiry());

			if (tt.getStatus().equalsIgnoreCase("success")) {

				respp_cd = tt.getData().getTradesInquiry();

				StringTokenizer stt = null;
				stt = new StringTokenizer(respp_cd, "^");
				ArrayList<CdApi> listcd = null;
				listcd = new ArrayList<CdApi>();

				int i = 0;
				while (stt.hasMoreTokens()) {
					if (i == 0) {
						stt.nextElement();
						i++;
					}

					if (i >= 1) {
						listcd = getDataCd(stt.nextToken(), listcd);
					}

				}

				
				Log.notice.info("cd list size=====>: " + listcd.size());

				String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
				try (PrintStream ps = new PrintStream(PropertiesFile.getSeqLogPath() + "\\cd-" + timeStamp + ".txt");) {

					for (CdApi c : listcd) {
						ps.println(c);
					}
					Log.notice.info(">>>>>>>>>>>>>>>>>>>>> cd list size=====>: " + listcd.size());
				} catch (Exception e1) {

					e1.printStackTrace();
				}
				
				Log.notice.info(">>>>>>>>>>>>>>>>>>>>> cd list size=====>: " + listcd.size());

				// int ii=0;
				Session cdsession = null;
				cdsession = HibernateUtil.openCdSession();
				cdsession.getTransaction().begin();
				for (CdApi pcd : listcd) {
					// System.out.println("trade_number: "+ p.getTrdNo());

					String tempp = null;
					tempp = pcd.toString();
					try {
						// System.out.println("result=>"+ temp);

						// writeCmTrade(temp); //corrrently commnd
						String[] temparraycd = null;

						temparraycd = tempp.split(",");

						DaoUtil obj = null;
						obj = new DaoUtil();
						// obj.insertCD(temparraycd);

						obj.insertCDexp(temparraycd, cdsession);
						// Mk k = new Mk();
						// k.DataInserted(temp);
						/*
						 * ii=ii+1; System.out.println("cd ii: "+ ii);
						 */

					} catch (Exception e) {
						// System.out.println("exception occured during writeCmTreade");
						Log.notice.info("exception occured during savingCdTrade | cd data not save",e);
					}
				}

				cdsession.getTransaction().commit();
				HibernateUtil.closeCdSession();

			} else {

				// System.out.println("DID NOT GET SUCCESS STATUS FROM CM API");
				Log.notice.info("DID NOT GET SUCCESS STATUS FROM CD API: " + tt.getStatus());
			}
			// System.out.println("=================================");
		} catch (Exception e) {
			tt = null;

			if (e.toString().contains("NoSuchElementException")) {
				Log.notice.info("NoSuchElementException found means data not available");
				tt = new CdResponse();
				tt.setStatus("data not available");
			} else {
				// System.out.println(e);
				Log.notice.info("exceprtion occured during parsing cd response: ", e);

			}
		}
		return tt;
	}

	// ------CD END

	// =======fo

	public FoResponse getParseFO(String foresp) throws Exception {
		// foresp
		// ="{\"status\":\"success\",\"messages\":{\"code\":\"01010000\"},\"data\":{
		// \"msgId\":\"08756201908130000001\",
		// \"tradesInquiry\":\"6,20190710,,,4721493,10000
		// ^792584,1,12513262,77173883310166,49488,75,927710,2,1100000000103668,25,4612,1,482166,08756,,2,6001,1177580006,1,,111111111111100,P,08756,IDFCFIRSTB,
		// ,OPTSTK,1253975400,2000,CE,,,,,,,, \" } }";
		//

		// System.out.println("res string is: "+ resp);

		Gson g = null;
		String forespp = null;
		g = new Gson();
		FoResponse fot = null;

		try {
			try {

				fot = g.fromJson(foresp, FoResponse.class);
				Log.notice.info("messageId: " + fot.getData().getMsgId());
			} catch (Exception e) {
				throw e;
			}
			if (fot.getStatus().equalsIgnoreCase("success")) {

				forespp = fot.getData().getTradesInquiry();

				StringTokenizer st = null;
				st = new StringTokenizer(forespp, "^");
				ArrayList<FoApi> list = null;
				list = new ArrayList<FoApi>();

				int i = 0;
				while (st.hasMoreTokens()) {
					if (i == 0) {
						st.nextElement();
						i++;
					}

					if (i >= 1) {
						list = getDatafo(st.nextToken(), list);
					}

				}

				
//				Log.notice.info("list size=====>: " + list.size());
//
				String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
				try (PrintStream ps = new PrintStream(PropertiesFile.getSeqLogPath() + "\\fo-" + timeStamp + ".txt");) {

					for (FoApi c : list) {
						ps.println(c);
					}
					Log.notice.info(">>>>>>>>>>>>>>>>>>>>> fo list size=====>: " + list.size());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Log.notice.info(">>>>>>>>>>>>>>>>>>>>> fo list size=====>: " + list.size());

				// System.out.println("goign to call insertExp------" + new
				// Date(System.currentTimeMillis()));
				Session fosession = null;
				fosession = HibernateUtil.openFoSession();
				fosession.getTransaction().begin();

				boolean foisInserted;
				foisInserted = true;
				try {

					for (FoApi pp : list) {
						// System.out.println("trade_number: "+ p.getTrdNo());

						String tempp = null;
						tempp = pp.toString();
						try {
							// System.out.println("result=>"+ temp);

							// writeCmTrade(temp); //corrrently commnd
							String[] tempFoarray = null;

							tempFoarray = tempp.split(",");

							DaoUtil oj = null;
							oj = new DaoUtil();
							// oj.insertFO(tempFoarray);

							oj.insertFOexp(tempFoarray, fosession);

							// Mk k = new Mk();
							// k.DataInserted(temp);

						} catch (Exception e) {
							// System.out.println("exception occured during writeCmTreade");
							Log.notice.info("exception occured during saving FoTrade");
							throw e;

						}
					}
					fosession.getTransaction().commit();
					HibernateUtil.closeFoSession();
					// System.out.println("logging finish------: "+ new
					// Date(System.currentTimeMillis()));

				} catch (Exception e) {
					/* System.out.println(e); */
					foisInserted = false;
					Log.notice.info("exception occured during save FO Trade");
				}

				if (foisInserted) {
					Log.notice.info("FO DATA INSERTED SUCCESSFULLY");

				} else {

					Log.notice.info("FO DATA NOT INSERTED");
				}

			} else {

				// System.out.println("DID NOT GET SUCCESS STATUS FROM CM API");
				Log.notice.info("DID NOT GET SUCCESS STATUS FROM FO API");
			}
			// System.out.println("=================================");
		} catch (Exception e) {
			fot = null;

			if (e.toString().contains("NoSuchElementException")) {
				Log.notice.info("NoSuchElementException found means data not available");
				fot = new FoResponse();
				fot.setStatus("data not available");
			} else {
				// System.out.println(e);
				Log.notice.info("exceprtion occured during parsing fo response: ", e);

			}
		}
		return fot;
	}

	// =====fo end

	// ----start

	public ArrayList<FoApi> getDatafo(String data, ArrayList<FoApi> list) {

		String[] t = data.split(","); // need to garbaj coll
		// CmApi obj1 =null;

		// ArrayList<CmApi> list=null;
		// list= new ArrayList<CmApi>();
		FoApi foobj1 = null;
		foobj1 = new FoApi();
		for (int i = 0; i < t.length; i++) {
			foobj1 = getActualDataFo(t[i], foobj1, i);
		}

		if (foobj1 == null) {
			return list;
		}
		int seq = Integer.parseInt(foobj1.getSeqNo());
		if (Utility.getMaxFOSequenceNo() <= seq) {
			Utility.setMaxFOSequenceNo(seq);
		}

		list.add(foobj1);
		return list;
	}

	// end

	public ArrayList<CmApi> getData(String data, ArrayList<CmApi> list) {

		String[] t = data.split(","); // need to garbaj coll
		// CmApi obj1 =null;

		// ArrayList<CmApi> list=null;
		// list= new ArrayList<CmApi>();
		CmApi obj1 = null;
		obj1 = new CmApi();
		for (int i = 0; i < t.length; i++) {
			obj1 = getActualData(t[i], obj1, i);
		}
		if (obj1 == null) {
			return list;
		}
		int seq = Integer.parseInt(obj1.getSeqNo());
		if (Utility.getMaxCMSequenceNo() <= seq) {
			Utility.setMaxCMSequenceNo(seq);
		}
		list.add(obj1);
		return list;
	}

	public ArrayList<CdApi> getDataCd(String data, ArrayList<CdApi> list) {

		String[] t = data.split(","); // need to garbaj coll
		// CmApi obj1 =null;

		// ArrayList<CmApi> list=null;
		// list= new ArrayList<CmApi>();
		CdApi cdobj1 = null;
		cdobj1 = new CdApi();
		for (int i = 0; i < t.length; i++) {
			cdobj1 = getActualDataCd(t[i], cdobj1, i);
		}

		if (cdobj1 == null) {
			return list;
		}
		int seq = Integer.parseInt(cdobj1.getSeqNo());
		if (Utility.getMaxCDSequenceNo() <= seq) {
			Utility.setMaxCDSequenceNo(seq);
		}

		list.add(cdobj1);
		return list;
	}

	// CD ----------

	public CdApi getActualDataCd(String data, CdApi cdobj, int index) {

		if (index == 0) {
			cdobj.setSeqNo(data);
		} else if (index == 1) {
			cdobj.setMkt(data);
		} else if (index == 2) {
			cdobj.setTrdNo(data);
		} else if (index == 3) {
			cdobj.setTrdTm(data);
		} else if (index == 4) {
			cdobj.setTkn(data);
		} else if (index == 5) {
			cdobj.setTrdQty(data);
		} else if (index == 6) {
			cdobj.setTrdPrc(data);
		} else if (index == 7) {
			cdobj.setBsFlg(data);
		} else if (index == 8) {
			cdobj.setOrdNo(data);
		} else if (index == 9) {
			cdobj.setBrnCd(data);
		} else if (index == 10) {
			cdobj.setUsrId(data);
		} else if (index == 11) {
			cdobj.setProCli(data);
		} else if (index == 12) {
			cdobj.setCliActNo(data);
		} else if (index == 13) {
			cdobj.setCpCd(data);
		} else if (index == 14) {
			cdobj.setRemarks(data);
		} else if (index == 15) {
			cdobj.setActTyp(data);
		} else if (index == 16) {
			cdobj.setTCd(data);
		} else if (index == 17) {
			cdobj.setOrdTm(data);
		} else if (index == 18) {
			cdobj.setBooktype(data);
		} else if (index == 19) {
			cdobj.setOppTmCd(data);
		} else if (index == 20) {
			cdobj.setCtclId(data);
		} else if (index == 21) {
			cdobj.setStatus(data);
		} else if (index == 22) {
			cdobj.setTmCd(data);
		} else if (index == 23) {
			cdobj.setSym(data);
		} else if (index == 24) {
			cdobj.setSer(data);
		} else if (index == 25) {
			cdobj.setInst(data);
		} else if (index == 26) {
			cdobj.setExpDt(data);

		} else if (index == 27) {
			cdobj.setStrPrc(data);
		} else if (index == 28) {
			cdobj.setOptType(data);
		} else if (index == 29) {
			cdobj.setFill1(data);
		} else if (index == 30) {
			cdobj.setFill2(data);
		} else if (index == 31) {
			cdobj.setFill3(data);
		} else if (index == 32) {
			cdobj.setFill4(data);
		} else if (index == 33) {
			cdobj.setFill5(data);
		} else if (index == 34) {
			cdobj.setFill6(data);
		} else if (index == 35) {
			cdobj.setFill7(data);
		} else if (index == 36) {
			cdobj.setFill8(data);
		} else {

		}

		return cdobj;

	}

	public CmApi getActualData(String data, CmApi obj, int index) {

		if (index == 0) {
			obj.setSeqNo(data);
		} else if (index == 1) {
			obj.setMkt(data);
		} else if (index == 2) {
			obj.setTrdNo(data);
		} else if (index == 3) {
			obj.setTrdTm(data);
		} else if (index == 4) {
			obj.setTkn(data);
		} else if (index == 5) {
			obj.setTrdQty(data);
		} else if (index == 6) {
			obj.setTrdPrc(data);
		} else if (index == 7) {
			obj.setBsFlg(data);
		} else if (index == 8) {
			obj.setOrdNo(data);
		} else if (index == 9) {
			obj.setBrnCd(data);
		} else if (index == 10) {
			obj.setUsrId(data);
		} else if (index == 11) {
			obj.setProCli(data);
		} else if (index == 12) {
			obj.setCliActNo(data);
		} else if (index == 13) {
			obj.setCpCd(data);
		} else if (index == 14) {
			obj.setRemarks(data);
		} else if (index == 15) {
			obj.setActTyp(data);
		} else if (index == 16) {
			obj.setTCd(data);
		} else if (index == 17) {
			obj.setOrdTm(data);
		} else if (index == 18) {
			obj.setMktTyp(data);
		} else if (index == 19) {
			obj.setAucNo(data);
		} else if (index == 20) {
			obj.setStpTyp(data);
		} else if (index == 21) {
			obj.setOppBrokerCd(data);
		} else if (index == 22) {
			obj.setTrdTrigPrc(data);
		} else if (index == 23) {
			obj.setCtclId(data);
		} else if (index == 24) {
			obj.setOrdInst(data);
		} else if (index == 25) {
			obj.setSecIdentifier(data);
		} else if (index == 26) {
			obj.setSym(data);

			// System.out.println("tradeno; "+ obj.getTrdNo() +" ------symbold: " +
			// obj.getSym()+".");

		} else if (index == 27) {
			obj.setSer(data);
		}else if (index == 28) {
			obj.setSecName(data);
		} else if (index == 29) {
			obj.setIntrumentType(data);
		} /*else if (index == 28) {
			obj.setInst(data);
		} else if (index == 29) {
			obj.setExpDt(data);
		} else if (index == 30) {
			obj.setStrPrc(data);
		} else if (index == 31) {
			obj.setOptType(data);
		}*/ else if (index == 32) {
			obj.setFill1(data);
		} else if (index == 33) {
			obj.setFill2(data);
		} else if (index == 34) {
			obj.setFill3(data);
		} else if (index == 35) {
			obj.setFill4(data);
		} else if (index == 36) {
			obj.setFill5(data);
		} else if (index == 37) {
			obj.setFill6(data);
		} else if (index == 38) {
			obj.setFill7(data);
		} else if (index == 39) {
			obj.setFill8(data);
		} else {

		}

		return obj;

	}

	public void writeCmTrade(String resp) throws Exception {

		String cmpath = "D:\\Trades\\" + getDate() + "\\CM";

		File files = new File(cmpath);
		boolean isfolderAvailable = false;
		if (!files.exists()) {
			if (files.mkdirs()) {
				System.out.println("directories are created!");
				isfolderAvailable = true;
			} else {
				System.out.println("Failed to create  directories!");
			}
		} else {
			isfolderAvailable = true;
		}

		if (isfolderAvailable) {
			String fileContent = null;
			fileContent = resp;
			try {
				// BufferedWriter writer = new BufferedWriter(new FileWriter(cmpath+"//"+
				// getTimestamp()+"//TradeCM.csv"));
				File file = new File(cmpath + "\\TradeCM.txt");
				if (file.exists()) {

					FileWriter fr = new FileWriter(file, true);
					BufferedWriter br = new BufferedWriter(fr);
					br.newLine();
					br.write(fileContent);
					br.close();
					fr.close();

				} else {
					file.createNewFile();
					FileWriter fr = new FileWriter(file, true);
					BufferedWriter br = new BufferedWriter(fr);
					br.write(fileContent);
					br.close();
					fr.close();
				}

			} catch (Exception e) {
				System.out.println(e);
				throw e;
			}

		} else {
			System.out.println(cmpath + "is not available");
		}
	}

	// fo start

	public FoApi getActualDataFo(String data, FoApi cdobj, int index) {

		if (index == 0) {
			cdobj.setSeqNo(data);
		} else if (index == 1) {
			cdobj.setMkt(data);
		} else if (index == 2) {
			cdobj.setTrdNo(data);
		} else if (index == 3) {
			cdobj.setTrdTm(data);
		} else if (index == 4) {
			cdobj.setTkn(data);
		} else if (index == 5) {
			cdobj.setTrdQty(data);
		} else if (index == 6) {
			cdobj.setTrdPrc(data);
		} else if (index == 7) {
			cdobj.setBsFlg(data);
		} else if (index == 8) {
			cdobj.setOrdNo(data);
		} else if (index == 9) {
			cdobj.setBrnCd(data);
		} else if (index == 10) {
			cdobj.setUsrId(data);
		} else if (index == 11) {
			cdobj.setProCli(data);
		} else if (index == 12) {
			cdobj.setCliActNo(data);
		} else if (index == 13) {
			cdobj.setCpCd(data);
		} else if (index == 14) {
			cdobj.setRemarks(data);
		} else if (index == 15) {
			cdobj.setActTyp(data);
		} else if (index == 16) {
			cdobj.setTCd(data);
		} else if (index == 17) {
			cdobj.setOrdTm(data);
		} else if (index == 18) {
			cdobj.setBooktype(data);
		} else if (index == 19) {
			cdobj.setOppTmCd(data);
		} else if (index == 20) {
			cdobj.setCtclId(data);
		} else if (index == 21) {
			cdobj.setStatus(data);
		} else if (index == 22) {
			cdobj.setTmCd(data);
		} else if (index == 23) {
			cdobj.setSym(data);
		} else if (index == 24) {
			cdobj.setSer(data);
		} else if (index == 25) {
			cdobj.setInst(data);
		} else if (index == 26) {
			cdobj.setExpDt(data);

		} else if (index == 27) {
			cdobj.setStrPrc(data);
		} else if (index == 28) {
			cdobj.setOptType(data);
		} else if (index == 29) {
			cdobj.setFill1(data);
		} else if (index == 30) {
			cdobj.setFill2(data);
		} else if (index == 31) {
			cdobj.setFill3(data);
		} else if (index == 32) {
			cdobj.setFill4(data);
		} else if (index == 33) {
			cdobj.setFill5(data);
		} else if (index == 34) {
			cdobj.setFill6(data);
		} else if (index == 35) {
			cdobj.setFill7(data);
		} else if (index == 36) {
			cdobj.setFill8(data);
		} else {

		}

		return cdobj;

	}

	// fo end

	static String getDate() {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		String timestamp = dateFormat.format(new Date());
		return timestamp;
	}

}
