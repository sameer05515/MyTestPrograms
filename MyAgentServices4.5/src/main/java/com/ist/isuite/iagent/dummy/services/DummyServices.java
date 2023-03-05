package com.ist.isuite.iagent.dummy.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ist.iagent.core.service.ServiceExecutor;
import com.ist.isuite.iagent.dummy.pojo.Account;
import com.ist.isuite.iagent.dummy.pojo.ArabicEnglishPojo;
import com.ist.isuite.iagent.dummy.pojo.Card;
import com.ist.isuite.iagent.dummy.pojo.Customer;
import com.ist.isuite.iagent.dummy.pojo.Feedback;
import com.ist.isuite.iagent.dummy.pojo.LastTransaction;
import com.ist.isuite.iagent.dummy.pojo.LastXCall;
import com.ist.isuite.iagent.dummy.pojo.Node;
import com.ist.isuite.iagent.dummy.pojo.ReasonCode;
import com.ist.isuite.iagent.dummy.pojo.ReturnMessage;
import com.ist.isuite.iagent.dummy.pojo.ServiceUsage;
import com.ist.isuite.iagent.dummy.util.DBUtil;

import java.util.List;
import flex.messaging.io.amf.ASObject;

public class DummyServices {
	private static final Logger log = Logger.getLogger(ServiceExecutor.class);

	private final String DEBIT_CARD = "debitCard";
	private final String CREDIT_CARD = "creditCard";
	private final String Accounts = "account";
	private final String Transaction_DEBIT = "DEBIT";
	private final String Transaction_CREDIT = "CREDIT";

	// --------------------------------------------------------------
	public Customer getCustomerDetailsByCustomerId(String customerId) {

		Customer customerObject = new Customer();
		customerObject.setCustomerId(customerId);
		customerObject.setAuthenticated("");
		customerObject.setCustomerName("");
		customerObject.setType("");
		customerObject.setPhoneNumber("");
		customerObject.setLanguage("");
		customerObject.setAddress("");
		customerObject.setAge("");
		customerObject.setCity("");
		customerObject.setCountry("");
		customerObject.setEmail("");
		customerObject.setFax("");
		customerObject.setMaritalStatus("");
		customerObject.setSex("");
		customerObject.setState("");

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from Customers where customerId="
							+ customerId);

			if (rs.next()) {

				customerObject.setCustomerId(customerId);
				customerObject.setAuthenticated(rs.getString("authenticated"));
				customerObject.setCustomerName(rs.getString("name"));
				customerObject.setType(rs.getString("type"));
				customerObject.setPhoneNumber(rs.getString("phoneNumber"));
				customerObject.setLanguage(rs.getString("language"));
				customerObject.setAddress(rs.getString("address"));
				customerObject.setAge(rs.getString("age"));
				customerObject.setCity(rs.getString("city"));
				customerObject.setCountry(rs.getString("country"));
				customerObject.setEmail(rs.getString("email"));
				customerObject.setFax(rs.getString("fax"));
				customerObject.setMaritalStatus(rs.getString("maritalStatus"));
				customerObject.setSex(rs.getString("sex"));
				customerObject.setState(rs.getString("state"));

			}

		} catch (SQLException e) {
			log.error("getCustomerDetailsByCustomerId:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log
							.error("getCustomerDetailsByCustomerId:closing connection:"
									+ e);
				}

			}

		}

		return customerObject;

	}

	// --------------------------------------------------------------
	public ArrayList<Customer> getAllCustomers() {

		ArrayList<Customer> customerList = new ArrayList<Customer>();
		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Customers");

			while (rs.next()) {

				Customer customerObject = new Customer();
				customerObject.setCustomerId(rs.getString("customerId"));
				customerObject.setAuthenticated(rs.getString("authenticated"));
				customerObject.setCustomerName(rs.getString("name"));
				customerObject.setType(rs.getString("type"));
				customerObject.setPhoneNumber(rs.getString("phoneNumber"));
				customerObject.setLanguage(rs.getString("language"));
				customerObject.setAddress(rs.getString("address"));
				customerObject.setAge(rs.getString("age"));
				customerObject.setCity(rs.getString("city"));
				customerObject.setCountry(rs.getString("country"));
				customerObject.setEmail(rs.getString("email"));
				customerObject.setFax(rs.getString("fax"));
				customerObject.setMaritalStatus(rs.getString("maritalStatus"));
				customerObject.setSex(rs.getString("sex"));
				customerObject.setState(rs.getString("maritalStatus"));
				customerList.add(customerObject);
			}

		} catch (SQLException e) {
			log.error("getAllCustomers:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getAllCustomers:closing connection:" + e);
				}

			}

		}

		return customerList;

	}

	// --------------------------------------------------------------
	public ServiceUsage getCustomerServiceUsage(String customerId) {

		ServiceUsage serviceUsage = new ServiceUsage();
		serviceUsage.setCustomerId(customerId);
		serviceUsage.setAgentTransfer("");
		serviceUsage.setAtm("");
		serviceUsage.setBranch("");

		serviceUsage.setIbanking("");
		serviceUsage.setIvr("");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from MonthlyServiceUsage where customerId="
							+ customerId);

			if (rs.next()) {

				serviceUsage.setAtm(rs.getString("atm"));
				serviceUsage.setBranch(rs.getString("branch"));
				serviceUsage.setAgentTransfer(rs.getString("agentTransfer"));
				serviceUsage.setIbanking(rs.getString("ibanking"));
				serviceUsage.setIvr(rs.getString("ivr"));

			}

		} catch (SQLException e) {
			log.error("getCustomerServiceUsage:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log
							.error("getCustomerServiceUsage:closing connection:"
									+ e);
				}

			}

		}

		return serviceUsage;

	}

	// --------------------------------------------------------------
	public ReturnMessage saveServiceUsage(Object obj) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error saveServiceUsage");
		boolean isExistingCustomer = false;
		ASObject asObject = (ASObject) obj;

		if ((null != asObject) && (null != asObject.get("customerId"))
				&& !("".equals(asObject.get("customerId")))) {

			String customerId = "" + asObject.get("customerId");
			String atm = (asObject.get("atm") == null) ? "" : ""
					+ asObject.get("atm");
			String ivr = (asObject.get("ivr") == null) ? "" : ""
					+ asObject.get("ivr");
			String branch = (asObject.get("branch") == null) ? "" : ""
					+ asObject.get("branch");
			String ibanking = (asObject.get("ibanking") == null) ? "" : ""
					+ asObject.get("ibanking");
			String agentTransfer = (asObject.get("agentTransfer") == null) ? ""
					: "" + asObject.get("agentTransfer");

			Connection con = DBUtil.getInstance().getConnection();

			try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from MonthlyServiceUsage where customerId="
								+ customerId);

				isExistingCustomer = rs.next();

			} catch (SQLException e) {
				log.error("saveServiceUsage:executing query" + e);
				message.setCode("0");
				message.setMessage(e + "");

			} finally {

				if (null != con) {

					try {
						con.close();
					} catch (SQLException e) {

						log.error("saveServiceUsage:closing connection:" + e);
					}

				}

			}

			if (isExistingCustomer) {

				updateServiceUsage(customerId, ivr, branch, ibanking, atm,
						agentTransfer);
			} else {

				addServiceUsage(customerId, ivr, branch, ibanking, atm,
						agentTransfer);
			}

		} else {
			message.setCode("0");
			message.setMessage("Customer ID not defined");

		}

		return message;

	}

	// --------------------------------------------------------------
	public ReturnMessage updateServiceUsage(String customerId, String ivr,
			String branch, String ibanking, String atm, String agentTransfer) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error while updating Service usage");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "update MonthlyServiceUsage set ivr=?,branch=?,ibanking=?,atm=?,agentTransfer=? where customerId=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, ivr);
			pstmt.setString(2, branch);
			pstmt.setString(3, ibanking);
			pstmt.setString(4, atm);
			pstmt.setString(5, agentTransfer);

			pstmt.setString(6, customerId);
			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Service Usage Updated Successfully");
		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Updating Service usage\n:" + e);
			log.error("Service usage:executing query" + e);
		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					log.error("Service usage:closing connection:" + e);
				}
			}
		}

		return message;

	}

	// --------------------------------------------------------------
	public ReturnMessage addServiceUsage(String customerId, String ivr,
			String branch, String ibanking, String atm, String agentTransfer) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error while adding Service usage");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "insert into  MonthlyServiceUsage (customerId,ivr,branch,ibanking,atm,agentTransfer) values(?,?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId);
			pstmt.setString(2, ivr);
			pstmt.setString(3, branch);
			pstmt.setString(4, ibanking);
			pstmt.setString(5, atm);
			pstmt.setString(6, agentTransfer);

			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Service Usage adding Successfully");
		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR adding Service usage\n:" + e);
			log.error("addServiceUsage:executing query" + e);
		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					log.error("addServiceUsage:closing connection:" + e);
				}
			}
		}

		return message;

	}

	// --------------------------------------------------------------
	public ReturnMessage addNewCustomer(Object obj) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		boolean isExistingCustomer = false;
		ASObject asObject = (ASObject) obj;

		if ((null != asObject) && (null != asObject.get("customerId"))
				&& !("".equals(asObject.get("customerId")))) {

			String customerId = "" + asObject.get("customerId");
			String authenticated = (asObject.get("authenticated") == null) ? ""
					: "" + asObject.get("authenticated");
			String name = (asObject.get("name") == null) ? "" : ""
					+ asObject.get("name");
			String type = (asObject.get("type") == null) ? "" : ""
					+ asObject.get("type");
			String phoneNumber = (asObject.get("phoneNumber") == null) ? ""
					: "" + asObject.get("phoneNumber");
			String language = (asObject.get("language") == null) ? "" : ""
					+ asObject.get("language");
			String address = (asObject.get("address") == null) ? "" : ""
					+ asObject.get("address");
			String age = (asObject.get("age") == null) ? "" : ""
					+ asObject.get("age");
			String city = (asObject.get("city") == null) ? "" : ""
					+ asObject.get("city");
			String country = (asObject.get("country") == null) ? "" : ""
					+ asObject.get("country");
			String email = (asObject.get("email") == null) ? "" : ""
					+ asObject.get("email");
			String fax = (asObject.get("fax") == null) ? "" : ""
					+ asObject.get("fax");
			String maritalStatus = (asObject.get("maritalStatus") == null) ? ""
					: "" + asObject.get("maritalStatus");
			String sex = (asObject.get("sex") == null) ? "" : ""
					+ asObject.get("sex");
			String state = (asObject.get("state") == null) ? "" : ""
					+ asObject.get("state");

			Connection con = DBUtil.getInstance().getConnection();

			try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from Customers where customerId="
								+ customerId);

				isExistingCustomer = rs.next();

			} catch (SQLException e) {
				log.error("addNewCustomer:executing query" + e);
				message.setCode("0");
				message.setMessage(e + "");

			} finally {

				if (null != con) {

					try {
						con.close();
					} catch (SQLException e) {

						log.error("addNewCustomer:closing connection:" + e);
					}

				}

			}

			if (isExistingCustomer) {

				message = updateCustomer(customerId, authenticated, name, type,
						phoneNumber, language, address, age, city, country,
						email, fax, maritalStatus, sex, state);
			} else {

				message = addCustomer(customerId, authenticated, name, type,
						phoneNumber, language, address, age, city, country,
						email, fax, maritalStatus, sex, state);
			}

		} else {
			message.setCode("0");
			message.setMessage("Customer ID not defined");

		}

		return message;

	}

	// --------------------------------------------------------------
	public ReturnMessage updateCustomer(String customerId,
			String authenticated, String name, String type, String phoneNumber,
			String language, String address, String age, String city,
			String country, String email, String fax, String maritalStatus,
			String sex, String state) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error while updating Customer info");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "update Customers set name=?,age=?,sex=?,maritalStatus=?,address=?,city=?,state=?,country=?,type=?,authenticated=?,language=?,phoneNumber=?,email=?,fax=? where customerId=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, age);
			pstmt.setString(3, sex);
			pstmt.setString(4, maritalStatus);
			pstmt.setString(5, address);
			pstmt.setString(6, city);
			pstmt.setString(7, state);
			pstmt.setString(8, country);
			pstmt.setString(9, type);
			pstmt.setString(10, authenticated);
			pstmt.setString(11, language);
			pstmt.setString(12, phoneNumber);
			pstmt.setString(13, email);
			pstmt.setString(14, fax);
			pstmt.setString(15, customerId);
			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Customer Updated Successfully");
		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Updating Customer\n:" + e);
			log.error("updateCustomer:executing query" + e);
		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					log.error("updateCustomer:closing connection:" + e);
				}
			}
		}

		return message;

	}

	// --------------------------------------------------------------
	public ReturnMessage addCustomer(String customerId, String authenticated,
			String name, String type, String phoneNumber, String language,
			String address, String age, String city, String country,
			String email, String fax, String maritalStatus, String sex,
			String state) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error while adding Customer info");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "insert into  Customers (customerId,name,age,sex,maritalStatus,address,city,state,country,type,authenticated,language,phoneNumber,email,fax) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, customerId);
			pstmt.setString(2, name);
			pstmt.setString(3, age);
			pstmt.setString(4, sex);
			pstmt.setString(5, maritalStatus);
			pstmt.setString(6, address);
			pstmt.setString(7, city);
			pstmt.setString(8, state);
			pstmt.setString(9, country);
			pstmt.setString(10, type);
			pstmt.setString(11, authenticated);
			pstmt.setString(12, language);
			pstmt.setString(13, phoneNumber);
			pstmt.setString(14, email);
			pstmt.setString(15, fax);

			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Customer added Successfully");
		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR adding  Customer\n:" + e);
			log.error("addCustomer:executing query" + e);
		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					log.error("addCustomer:closing connection:" + e);
				}
			}
		}

		return message;

	}

	// --------------------------------------------------------------
	public Account getAccountDetailByAccountNumber(String accountNumber) {

		Account accountObject = new Account();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from Accounts where accountNo="
							+ accountNumber);

			if (rs.next()) {

				accountObject.setAccountBalance(rs.getDouble("accountBalance"));
				accountObject.setAccountNo(rs.getString("accountNo"));
				accountObject.setAccountType(rs.getString("accountType"));
				accountObject.setCustomerId(rs.getString("customerId"));
				accountObject.setLastTransactionAmount(rs
						.getDouble("lastTransactionAmount"));
				accountObject.setLastTransactionDate(rs
						.getDate("lastTransactionDate"));
				accountObject.setLastTransactionType(rs
						.getString("lastTransactionType"));
				accountObject.setCurrency(rs.getString("currency"));
			}

		} catch (SQLException e) {
			log.error("getAccountDetailByAccountNumber:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log
							.error("getAccountDetailByAccountNumber:closing connection:"
									+ e);
				}

			}

		}

		return accountObject;
	}

	// --------------------------------------------------------------
	public ReturnMessage addUpdateAccount(ASObject asObject) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		boolean isExistingCustomer = false;

		if ((null != asObject) && (null != asObject.get("customerId"))
				&& !("".equals(asObject.get("customerId")))
				&& (null != asObject.get("accountNo"))
				&& !("".equals(asObject.get("accountNo")))) {

			String customerId = "" + asObject.get("customerId");
			String accountNo = (asObject.get("accountNo") == null) ? "" : ""
					+ asObject.get("accountNo");
			try {
				Long.parseLong(accountNo);
			} catch (Exception e) {

				log.error("addUpdateAccount:" + e);
				message.setMessage("please enter a valid Account Number");
				return message;
			}
			Double accountBalance = 0.0;
			try {
				accountBalance = Double.parseDouble((asObject
						.get("accountBalance") == null) ? "" : ""
						+ asObject.get("accountBalance"));

			} catch (Exception e) {

				message.setMessage("please enter a valid Account Balance");
				return message;

			}

			String accountType = (asObject.get("accountType") == null) ? ""
					: "" + asObject.get("accountType");

			String lastTransactionType = (asObject.get("lastTransactionType") == null) ? ""
					: "" + asObject.get("lastTransactionType");

			Double lastTransactionAmount = 0.0;
			try {

				lastTransactionAmount = Double.parseDouble((asObject
						.get("lastTransactionAmount") == null) ? "" : ""
						+ asObject.get("lastTransactionAmount"));

			} catch (Exception e) {

				message
						.setMessage("please enter a valid Last Transaction Amount");
				return message;

			}

			String currency = (asObject.get("currency") == null) ? "" : ""
					+ asObject.get("currency");

			Connection con = DBUtil.getInstance().getConnection();

			try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from Accounts where accountNo="
								+ accountNo);

				isExistingCustomer = rs.next();

			} catch (SQLException e) {
				log.error("addUpdateAccount:executing query" + e);
				message.setCode("0");
				message.setMessage(e + "");

			} finally {

				if (null != con) {

					try {
						con.close();
					} catch (SQLException e) {

						log.error("addUpdateAccount:closing connection:" + e);
					}

				}

			}

			if (isExistingCustomer) {

				message = updateAccount(accountNo, customerId, accountBalance,
						accountType, lastTransactionType,
						lastTransactionAmount, currency);
			} else {

				message = addAccount(accountNo, customerId, accountBalance,
						accountType, lastTransactionType,
						lastTransactionAmount, currency);
			}

			addLastTransaction(accountNo, lastTransactionAmount,
					lastTransactionType, Accounts);

		} else {
			message.setCode("0");
			message.setMessage("Customer ID  Or Accoutn Number not defined");

		}

		log.info("Return Message:" + message);

		return message;
	}

	// --------------------------------------------------------------
	public ReturnMessage updateAccount(String accountNo, String customerId,
			Double accountBalance, String accountType,
			String lastTransactionType, Double lastTransactionAmount,
			String currency) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error while updateAccount");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "update Accounts set customerId=?,"
					+ "accountBalance=?," + "accountType=?,"
					+ "lastTransactionDate=?," + "lastTransactionType=?,"
					+ "lastTransactionAmount=?," + "currency=? where "
					+ "accountNo=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId);
			pstmt.setDouble(2, accountBalance);
			pstmt.setString(3, accountType);
			pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(5, lastTransactionType);
			pstmt.setDouble(6, lastTransactionAmount);
			pstmt.setString(7, currency);
			pstmt.setString(8, accountNo);
			;
			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Account Updated Successfully");
		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Updating Account\n:" + e);
			log.error("updateAccount:executing query" + e);
		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					log.error("updateAccount:closing connection:" + e);
				}
			}
		}

		return message;

	}

	// --------------------------------------------------------------
	public ReturnMessage addAccount(String accountNo, String customerId,
			Double accountBalance, String accountType,
			String lastTransactionType, Double lastTransactionAmount,
			String currency) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error while addAccount");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "insert into  Accounts values(?,?,?,?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, accountNo);
			pstmt.setString(2, customerId);
			pstmt.setDouble(3, accountBalance);
			pstmt.setString(4, accountType);
			pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(6, lastTransactionType);
			pstmt.setDouble(7, lastTransactionAmount);
			pstmt.setString(8, currency);

			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Account Added Successfully");
		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Adding Account\n:" + e);
			log.error("addAccount:executing query" + e);
		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					log.error("addAccount:closing connection:" + e);
				}
			}
		}

		return message;

	}

	// --------------------------------------------------------------
	public ReturnMessage addUpdateCreditCard(ASObject asObject) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		boolean isExistingCustomer = false;

		if ((null != asObject) && (null != asObject.get("customerId"))
				&& !("".equals(asObject.get("customerId")))
				&& (null != asObject.get("cardNumber"))
				&& !("".equals(asObject.get("cardNumber")))) {

			String cardNumber = "" + asObject.get("cardNumber");
			try {
				Long.parseLong(cardNumber);
			} catch (Exception e) {

				log.error("addUpdateCreditCard:" + e);
				message.setMessage("please enter a valid Card Number");
				return message;
			}
			String customerId = "" + asObject.get("customerId");
			String cardType = (asObject.get("cardType") == null) ? "" : ""
					+ asObject.get("cardType");
			Double lastTransactionAmount = 0.0;

			try {
				lastTransactionAmount = Double.parseDouble((asObject
						.get("lastTransactionAmount") == null) ? "" : ""
						+ asObject.get("lastTransactionAmount"));

			} catch (Exception e) {

				log.error("addUpdateCreditCard:" + e);
				message
						.setMessage("please enter a valid Last Transaction Amount");
				return message;
			}

			String accountNumber = (asObject.get("accountNumber") == null) ? ""
					: "" + asObject.get("accountNumber");

			try {
				Long.parseLong(accountNumber);
			} catch (Exception e) {

				log.error("addUpdateCreditCard:" + e);
				message.setMessage("please enter a valid Account Number");
				return message;
			}

			String cardStatus = (asObject.get("cardStatus") == null) ? "" : ""
					+ asObject.get("cardStatus");

			String lastTransactionType = (asObject.get("lastTransactionType") == null) ? ""
					: "" + asObject.get("lastTransactionType");

			String currency = (asObject.get("currency") == null) ? "" : ""
					+ asObject.get("currency");

			Connection con = DBUtil.getInstance().getConnection();

			try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from CreditCard where cardNumber="
								+ cardNumber);

				isExistingCustomer = rs.next();

			} catch (SQLException e) {
				log.error("addUpdateCreditCard:executing query" + e);
				message.setCode("0");
				message.setMessage(e + "");

			} finally {

				if (null != con) {

					try {
						con.close();
					} catch (SQLException e) {

						log
								.error("addUpdateCreditCard:closing connection:"
										+ e);
					}

				}

			}

			if (isExistingCustomer) {

				message = updateCreditCard(cardNumber, customerId, cardType,
						lastTransactionAmount, accountNumber,
						lastTransactionType, cardStatus, currency);
			} else {

				message = addCreditCard(cardNumber, customerId, cardType,
						lastTransactionAmount, accountNumber,
						lastTransactionType, cardStatus, currency);
			}

			addLastTransaction(cardNumber, lastTransactionAmount,
					lastTransactionType, CREDIT_CARD);

		} else {
			message.setCode("0");
			message
					.setMessage("Customer ID  Or Credit Card Number not defined");

		}

		log.info("Return Message:" + message);

		return message;
	}

	// --------------------------------------------------------------
	public ReturnMessage updateCreditCard(String cardNumber, String customerId,
			String cardType, Double lastTransactionAmount,
			String accountNumber, String lastTransactionType,
			String cardStatus, String currency) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error while updateCreditCard");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "update CreditCard set customerId=?," + "cardType=?,"
					+ "lastTransactionAmount=?," + "accountNumber=?,"
					+ "lastTransactionDate=?," + "lastTransactionType=?,"
					+ "cardStatus=?, currency=? where " + "cardNumber=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId);
			pstmt.setString(2, cardType);
			pstmt.setDouble(3, lastTransactionAmount);
			pstmt.setString(4, accountNumber);
			pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

			pstmt.setString(6, lastTransactionType);
			pstmt.setString(7, cardStatus);
			pstmt.setString(8, currency);
			pstmt.setString(9, cardNumber);
			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Credit Card Updated Successfully");
		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Updating Credit Card\n:" + e);
			log.error("updateCreditCard:executing query" + e);
		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					log.error("updateCreditCard:closing connection:" + e);
				}
			}
		}

		return message;

	}

	// --------------------------------------------------------------
	public ReturnMessage addCreditCard(String cardNumber, String customerId,
			String cardType, Double lastTransactionAmount,
			String accountNumber, String lastTransactionType,
			String cardStatus, String currency) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error while updateCreditCard");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "insert into CreditCard values (?,?,?,?,?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, cardNumber);
			pstmt.setString(2, customerId);
			pstmt.setString(3, cardType);
			pstmt.setDouble(4, lastTransactionAmount);
			pstmt.setString(5, accountNumber);
			pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

			pstmt.setString(7, lastTransactionType);
			pstmt.setString(8, cardStatus);
			pstmt.setString(9, currency);
			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Credit Card Added Successfully");
		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Adding Credit Card\n:" + e);
			log.error("addCreditCard:executing query" + e);
		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					log.error("addCreditCard:closing connection:" + e);
				}
			}
		}

		return message;

	}

	public ReturnMessage addUpdateDebitCard(ASObject asObject) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		boolean isExistingCustomer = false;

		if ((null != asObject) && (null != asObject.get("customerId"))
				&& !("".equals(asObject.get("customerId")))
				&& (null != asObject.get("cardNumber"))
				&& !("".equals(asObject.get("cardNumber")))) {

			String cardNumber = "" + asObject.get("cardNumber");
			try {
				Long.parseLong(cardNumber);
			} catch (Exception e) {

				log.error("addUpdateDebitCard:" + e);
				message.setMessage("please enter a valid Card Number");
				return message;
			}

			String customerId = "" + asObject.get("customerId");
			String cardType = (asObject.get("cardType") == null) ? "" : ""
					+ asObject.get("cardType");

			Double lastTransactionAmount = 0.0;
			try {
				lastTransactionAmount = Double.parseDouble((asObject
						.get("lastTransactionAmount") == null) ? "" : ""
						+ asObject.get("lastTransactionAmount"));
			} catch (Exception e) {

				log.error("addUpdateDebitCard:" + e);
				message
						.setMessage("please enter a valid Last Transaction Amount");
				return message;
			}

			String accountNumber = (asObject.get("accountNumber") == null) ? ""
					: "" + asObject.get("accountNumber");

			try {
				Long.parseLong(accountNumber);
			} catch (Exception e) {

				log.error("addUpdateDebitCard:" + e);
				message.setMessage("please enter a valid Account Number");
				return message;
			}

			String cardStatus = (asObject.get("cardStatus") == null) ? "" : ""
					+ asObject.get("cardStatus");

			String lastTransactionType = (asObject.get("lastTransactionType") == null) ? ""
					: "" + asObject.get("lastTransactionType");

			String currency = (asObject.get("currency") == null) ? "" : ""
					+ asObject.get("currency");

			Connection con = DBUtil.getInstance().getConnection();

			try {

				Statement stmt = con.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from DeditCard where cardNumber="
								+ cardNumber);

				isExistingCustomer = rs.next();

			} catch (SQLException e) {
				log.error("addUpdateDebitCard:executing query" + e);
				message.setCode("0");
				message.setMessage(e + "");

			} finally {

				if (null != con) {

					try {
						con.close();
					} catch (SQLException e) {

						log.error("addUpdateDebitCard:closing connection:" + e);
					}

				}

			}

			if (isExistingCustomer) {

				message = updateDebitCard(cardNumber, customerId, cardType,
						lastTransactionAmount, accountNumber,
						lastTransactionType, cardStatus, currency);
			} else {

				message = addDebitCard(cardNumber, customerId, cardType,
						lastTransactionAmount, accountNumber,
						lastTransactionType, cardStatus, currency);
			}

			addLastTransaction(cardNumber, lastTransactionAmount,
					lastTransactionType, DEBIT_CARD);

		} else {
			message.setCode("0");
			message.setMessage("Customer ID  Or Debit Card Number not defined");

		}

		log.info("Return Message:" + message);

		return message;
	}

	// --------------------------------------------------------------
	public ReturnMessage updateDebitCard(String cardNumber, String customerId,
			String cardType, Double lastTransactionAmount,
			String accountNumber, String lastTransactionType,
			String cardStatus, String currency) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error while updateDebitCard");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "update DeditCard set customerId=?," + "cardType=?,"
					+ "lastTransactionAmount=?," + "accountNumber=?,"
					+ "lastTransactionDate=?," + "lastTransactionType=?,"
					+ "cardStatus=?, currency=? where " + "cardNumber=?";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, customerId);
			pstmt.setString(2, cardType);
			pstmt.setDouble(3, lastTransactionAmount);
			pstmt.setString(4, accountNumber);
			pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

			pstmt.setString(6, lastTransactionType);
			pstmt.setString(7, cardStatus);
			pstmt.setString(8, currency);
			pstmt.setString(9, cardNumber);
			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Debit Card Updated Successfully");
		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Updating Debit Card\n:" + e);
			log.error("updateDebitCard:executing query" + e);
		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					log.error("updateDebitCard:closing connection:" + e);
				}
			}
		}

		return message;

	}

	// --------------------------------------------------------------
	public ReturnMessage addDebitCard(String cardNumber, String customerId,
			String cardType, Double lastTransactionAmount,
			String accountNumber, String lastTransactionType,
			String cardStatus, String currency) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error while addDebitCard");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "insert into DeditCard values (?,?,?,?,?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, cardNumber);
			pstmt.setString(2, customerId);
			pstmt.setString(3, cardType);
			pstmt.setDouble(4, lastTransactionAmount);
			pstmt.setString(5, accountNumber);
			pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

			pstmt.setString(7, lastTransactionType);
			pstmt.setString(8, cardStatus);
			pstmt.setString(9, currency);
			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Debit Card Added Successfully");
		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Adding Debit Card\n:" + e);
			log.error("addDebitCard:executing query" + e);
		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					log.error("addDebitCard:closing connection:" + e);
				}
			}
		}

		return message;

	}

	// --------------------------------------------------------------
	public Card getCreditCardDetailByCardNumber(String creditCardNumber) {

		Card cardObject = new Card();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from CreditCard where cardNumber="
							+ creditCardNumber);

			if (rs.next()) {

				cardObject.setAccountNumber(rs.getString("accountNumber"));
				cardObject.setCardNumber(rs.getString("cardNumber"));
				cardObject.setCardStatus(rs.getString("cardStatus"));
				cardObject.setCardType(rs.getString("cardType"));
				cardObject.setCurrency(rs.getString("currency"));
				cardObject.setCustomerId(rs.getString("customerId"));
				cardObject.setLastTransactionAmount(rs
						.getDouble("lastTransactionAmount"));
				cardObject.setLastTransactionDate(rs
						.getDate("lastTransactionDate"));
				cardObject.setLastTransactionType(rs
						.getString("lastTransactionType"));

			}

		} catch (SQLException e) {
			log.error("getCreditCardDetailByCardNumber:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log
							.error("getCreditCardDetailByCardNumber:closing connection:"
									+ e);
				}

			}

		}

		return cardObject;
	}

	// --------------------------------------------------------------
	public Card getDebitCardDetailByCardNumber(String debitCardNumber) {

		Card cardObject = new Card();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from DeditCard where cardNumber="
							+ debitCardNumber);

			if (rs.next()) {

				cardObject.setAccountNumber(rs.getString("accountNumber"));
				cardObject.setCardNumber(rs.getString("cardNumber"));
				cardObject.setCardStatus(rs.getString("cardStatus"));
				cardObject.setCardType(rs.getString("cardType"));
				cardObject.setCurrency(rs.getString("currency"));
				cardObject.setCustomerId(rs.getString("customerId"));
				cardObject.setLastTransactionAmount(rs
						.getDouble("lastTransactionAmount"));
				cardObject.setLastTransactionDate(rs
						.getDate("lastTransactionDate"));
				cardObject.setLastTransactionType(rs
						.getString("lastTransactionType"));

			}

		} catch (SQLException e) {
			log.error("getDebitCardDetailByCardNumber:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log
							.error("getDebitCardDetailByCardNumber:closing connection:"
									+ e);
				}

			}

		}

		return cardObject;
	}

	// --------------------------------------------------------------
	public ArrayList<String> getAllDebitCardNumberList(String customerId) {

		ArrayList<String> cardList = new ArrayList<String>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from DeditCard where customerId="
							+ customerId);

			while (rs.next()) {

				cardList.add(rs.getString("cardNumber"));
			}

		} catch (SQLException e) {
			log.error("getAllDebitCardNumberList:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getAllDebitCardNumberList:closing connection:"
							+ e);
				}

			}

		}

		return cardList;
	}

	public ArrayList<Card> getAllDebitCardDetails(String customerId) {

		ArrayList<Card> cardList = new ArrayList<Card>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from DeditCard where customerId="
							+ customerId);

			while (rs.next()) {

				Card cardObject = new Card();
				cardObject.setAccountNumber(rs.getString("accountNumber"));
				cardObject.setCardNumber(rs.getString("cardNumber"));
				cardObject.setCardStatus(rs.getString("cardStatus"));
				cardObject.setCardType(rs.getString("cardType"));
				cardObject.setCurrency(rs.getString("currency"));
				cardObject.setCustomerId(rs.getString("customerId"));
				cardObject.setLastTransactionAmount(rs
						.getDouble("lastTransactionAmount"));
				cardObject.setLastTransactionDate(rs
						.getDate("lastTransactionDate"));
				cardObject.setLastTransactionType(rs
						.getString("lastTransactionType"));

				cardList.add(cardObject);
			}

		} catch (SQLException e) {
			log.error("getAllDebitCardDetails:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getAllDebitCardDetails:closing connection:" + e);
				}

			}

		}

		return cardList;
	}

	// --------------------------------------------------------------
	public ArrayList<Card> getAllCreditCardDetails(String customerId) {

		ArrayList<Card> cardList = new ArrayList<Card>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from CreditCard where customerId="
							+ customerId);

			while (rs.next()) {

				Card cardObject = new Card();
				cardObject.setAccountNumber(rs.getString("accountNumber"));
				cardObject.setCardNumber(rs.getString("cardNumber"));
				cardObject.setCardStatus(rs.getString("cardStatus"));
				cardObject.setCardType(rs.getString("cardType"));
				cardObject.setCurrency(rs.getString("currency"));
				cardObject.setCustomerId(rs.getString("customerId"));
				cardObject.setLastTransactionAmount(rs
						.getDouble("lastTransactionAmount"));
				cardObject.setLastTransactionDate(rs
						.getDate("lastTransactionDate"));
				cardObject.setLastTransactionType(rs
						.getString("lastTransactionType"));

				cardList.add(cardObject);
			}

		} catch (SQLException e) {
			log.error("getAllCreditCardDetails:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log
							.error("getAllCreditCardDetails:closing connection:"
									+ e);
				}

			}

		}

		return cardList;
	}

	public ArrayList<String> getAllCreditCardNumberList(String customerId) {

		ArrayList<String> cardList = new ArrayList<String>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from CreditCard where customerId="
							+ customerId);

			while (rs.next()) {

				cardList.add(rs.getString("cardNumber"));
			}

		} catch (SQLException e) {
			log.error("getAllCreditCardNumberList:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getAllCreditCardNumberList:closing connection:"
							+ e);
				}

			}

		}

		return cardList;
	}

	public ArrayList<String> getAllAccountumberList(String customerId) {

		ArrayList<String> accountList = new ArrayList<String>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from Accounts where customerId="
							+ customerId);

			while (rs.next()) {

				accountList.add(rs.getString("accountNo"));
			}

		} catch (SQLException e) {
			log.error("getAllAccountumberList:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getAllAccountumberList:closing connection:" + e);
				}

			}

		}

		return accountList;
	}

	// --------------------------------------------------------------
	public ArrayList<Account> getAllAccountDetails(String customerId) {

		ArrayList<Account> accountList = new ArrayList<Account>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from Accounts where customerId="
							+ customerId);

			while (rs.next()) {

				Account accountObject = new Account();
				accountObject.setAccountBalance(rs.getDouble("accountBalance"));
				accountObject.setAccountNo(rs.getString("accountNo"));
				accountObject.setAccountType(rs.getString("accountType"));
				accountObject.setCustomerId(rs.getString("customerId"));
				accountObject.setLastTransactionAmount(rs
						.getDouble("lastTransactionAmount"));
				accountObject.setLastTransactionDate(rs
						.getDate("lastTransactionDate"));
				accountObject.setLastTransactionType(rs
						.getString("lastTransactionType"));

				accountObject.setCurrency(rs.getString("currency"));
				accountList.add(accountObject);
			}

		} catch (SQLException e) {
			log.error("getAllAccountDetails:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getAllAccountDetails:closing connection:" + e);
				}

			}

		}

		return accountList;
	}

	// --------------------------------------------------------------
	public ArrayList<ReasonCode> getAllReasonCodes() {

		ArrayList<ReasonCode> reasonCodeList = new ArrayList<ReasonCode>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from ReasonCode ");

			while (rs.next()) {

				ReasonCode reasonCodeObject = new ReasonCode();

				reasonCodeObject.setId(rs.getInt("id") + "");
				reasonCodeObject.setReasonCode(rs.getString("reasonCode"));

				reasonCodeList.add(reasonCodeObject);
			}

		} catch (SQLException e) {
			log.error("getAllReasonCodes:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getAllReasonCodes:closing connection:" + e);
				}

			}

		}

		return reasonCodeList;

	}

	// --------------------------------------------------------------
	public ReturnMessage addReasonCode(String reasonCode) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("insert into ReasonCode (reasonCode) values ('"
					+ reasonCode + "')");

			message.setCode("1");
			message.setMessage("Reason Added Successfully");

		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Saving Reason Code\n:" + e);
			log.error("addReasonCode:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("addReasonCode:closing connection:" + e);
				}

			}

		}

		return message;
	}

	// --------------------------------------------------------------
	public ReturnMessage saveFeedback(String customerId, String agentId,
			String feedBackAbout, String comments) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			stmt.executeUpdate("insert into feedback values ('" + customerId
					+ "','" + agentId + "','" + feedBackAbout + "','"
					+ comments + "')");

			message.setCode("1");
			message.setMessage("Feedback Saved Successfully");

		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Saving saveFeedback\n:" + e);
			log.error("saveFeedback:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("saveFeedback:closing connection:" + e);
				}

			}

		}

		return message;
	}

	// --------------------------------------------------------------
	public ArrayList<Feedback> getAllFeedbacksByCustomerId(String customerId) {

		ArrayList<Feedback> feedbackList = new ArrayList<Feedback>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from Feedback where customerId="
							+ customerId);

			while (rs.next()) {

				Feedback feedbackObject = new Feedback();

				feedbackObject.setAgentId(rs.getString("agentId"));
				feedbackObject.setComments(rs.getString("comments"));
				feedbackObject.setCustomerId(rs.getString("customerId"));
				feedbackObject.setFeedBackAbout(rs.getString("feedBackAbout"));

				feedbackList.add(feedbackObject);
			}

		} catch (SQLException e) {
			log.error("getAllFeedbacksByCustomerId:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getAllFeedbacksByCustomerId:closing connection:"
							+ e);
				}

			}

		}

		return feedbackList;
	}

	// --------------------------------------------------------------
	public ArrayList<Feedback> getAllFeedbacksByAgentId(String agentId) {

		ArrayList<Feedback> feedbackList = new ArrayList<Feedback>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from Feedback where agentId="
							+ agentId);

			while (rs.next()) {

				Feedback feedbackObject = new Feedback();

				feedbackObject.setAgentId(rs.getString("agentId"));
				feedbackObject.setComments(rs.getString("comments"));
				feedbackObject.setCustomerId(rs.getString("customerId"));
				feedbackObject.setFeedBackAbout(rs.getString("feedBackAbout"));

				feedbackList.add(feedbackObject);
			}

		} catch (SQLException e) {
			log.error("getAllFeedbacksByAgentId:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getAllFeedbacksByAgentId:closing connection:"
							+ e);
				}

			}

		}

		return feedbackList;
	}

	// --------------------------------------------------------------
	public ArrayList<LastXCall> getLastXCallsByANI(String ani) {

		ArrayList<LastXCall> lastCallList = new ArrayList<LastXCall>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from LastXCalls where ani=" + ani
							+ " order by dateTime desc limit 5");

			while (rs.next()) {

				LastXCall lastCallObject = new LastXCall();

				lastCallObject.setAgentId(rs.getString("agentId"));
				lastCallObject.setAni(rs.getString("ani"));
				lastCallObject.setCustomerId(rs.getString("customerId"));
				lastCallObject.setDateTime(rs.getDate("dateTime"));
				lastCallObject.setId(rs.getString("id"));
				lastCallList.add(lastCallObject);
			}

		} catch (SQLException e) {
			log.error("getLastXCallsByANI:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getLastXCallsByANI:closing connection:" + e);
				}

			}

		}

		return lastCallList;
	}

	// --------------------------------------------------------------
	public ArrayList<LastXCall> getLastXCallsByAgentId(String agentId) {

		ArrayList<LastXCall> lastCallList = new ArrayList<LastXCall>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from LastXCalls where agentId="
							+ agentId + " order by dateTime desc limit 5");

			while (rs.next()) {

				LastXCall lastCallObject = new LastXCall();

				lastCallObject.setAgentId(rs.getString("agentId"));
				lastCallObject.setAni(rs.getString("ani"));
				lastCallObject.setCustomerId(rs.getString("customerId"));
				lastCallObject.setDateTime(rs.getDate("dateTime"));
				lastCallObject.setId(rs.getString("id"));
				lastCallList.add(lastCallObject);
			}

		} catch (SQLException e) {
			log.error("getLastXCallsByAgentId:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getLastXCallsByAgentId:closing connection:" + e);
				}

			}

		}

		return lastCallList;
	}

	// --------------------------------------------------------------
	public ArrayList<LastXCall> getLastXCallsByCustomerId(String customerId) {

		ArrayList<LastXCall> lastCallList = new ArrayList<LastXCall>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from LastXCalls where customerId="
							+ customerId + " order by dateTime desc limit 5");

			while (rs.next()) {

				LastXCall lastCallObject = new LastXCall();

				lastCallObject.setAgentId(rs.getString("agentId"));
				lastCallObject.setAni(rs.getString("ani"));
				lastCallObject.setCustomerId(rs.getString("customerId"));
				lastCallObject.setDateTime(rs.getDate("dateTime"));
				lastCallObject.setId(rs.getString("id"));
				lastCallList.add(lastCallObject);
			}

		} catch (SQLException e) {
			log.error("getLastXCallsByCustomerId:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getLastXCallsByCustomerId:closing connection:"
							+ e);
				}

			}

		}

		return lastCallList;
	}

	// --------------------------------------------------------------
	public ArrayList<LastXCall> getLastXCalls(String ani, String customerId,
			String agentId) {

		ArrayList<LastXCall> lastCallList = new ArrayList<LastXCall>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT  * FROM LastXCalls where customerId='"
							+ customerId
							+ "' or agentId='"
							+ agentId
							+ "' or ani='" + ani + "'");

			while (rs.next()) {

				LastXCall lastCallObject = new LastXCall();

				lastCallObject.setAgentId(rs.getString("agentId"));
				lastCallObject.setAni(rs.getString("ani"));
				lastCallObject.setCustomerId(rs.getString("customerId"));
				lastCallObject.setDateTime(rs.getDate("dateTime"));
				lastCallObject.setId(rs.getString("id"));
				lastCallList.add(lastCallObject);
			}

		} catch (SQLException e) {
			log.error("getLastXCalls:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getLastXCalls:closing connection:" + e);
				}

			}

		}

		return lastCallList;
	}

	// --------------------------------------------------------------
	public ReturnMessage saveCallLog(String customerId, String ani,
			String agentId) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "insert into LastXCalls values(?,?,?,?,?)";

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, System.currentTimeMillis() + "");
			pstmt.setString(2, customerId);
			pstmt.setString(3, ani);
			pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			pstmt.setString(5, agentId);
			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Call Log Saved Successfully");

		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Saving CallLog\n:" + e);
			log.error("saveCallLog:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("saveCallLog:closing connection:" + e);
				}

			}

		}

		return message;
	}

	// --------------------------------------------------------------
	public ReturnMessage blockDebitCard(String cardNumber) {

		return updateCardStatus(cardNumber, "BLOCKED", false);
	}

	// --------------------------------------------------------------
	public ReturnMessage unBlockDebitCard(String cardNumber) {

		return updateCardStatus(cardNumber, "ACTIVE", false);
	}

	// --------------------------------------------------------------
	public ReturnMessage blockCreditCard(String cardNumber) {

		return updateCardStatus(cardNumber, "BLOCKED", true);
	}

	// --------------------------------------------------------------
	public ReturnMessage unBlockCreditCard(String cardNumber) {

		return updateCardStatus(cardNumber, "ACTIVE", true);
	}

	// --------------------------------------------------------------
	public ReturnMessage updateCardStatus(String cardNumber, String status,
			boolean isCreditCard) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "";

			if (isCreditCard) {

				sql = "update CreditCard set cardStatus=? where cardNumber=?";

			} else {

				sql = "update DeditCard set cardStatus=? where cardNumber=?";
			}

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setString(2, cardNumber);
			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Card status updated successfully");

		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Updating Card status\n:" + e);
			log.error("updateCardStatus:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("updateCardStatus:closing connection:" + e);
				}

			}

		}

		return message;
	}

	public ReturnMessage addUpdateLastTransaction(String number, Double amount,
			String transactionType, String type) {
		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		boolean isExisting = false;

		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "";
			if (Accounts.equals(type)) {

				sql = "select * from AccountLastTransactions where cardNumber=?";
			} else if (CREDIT_CARD.equals(type)) {

				sql = "select * from CreditCardLastTransactions where cardNumber=?";
			} else {
				sql = "select * from DebitCardLastTransactions where cardNumber=?";
			}

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, number);

		} catch (SQLException e) {
			log.error("addUpdateLastTransaction:executing query" + e);
		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {
					log.error("addUpdateLastTransaction:closing connection:"
							+ e);
				}
			}
		}

		if (isExisting) {

			message = updateLastTransaction(number, amount, transactionType,
					type);
		} else {

			message = addLastTransaction(number, amount, transactionType, type);
		}

		return message;

	}

	// --------------------------------------------------------------
	public ReturnMessage updateLastTransaction(String number, Double amount,
			String transactionType, String type) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "";

			if (Accounts.equals(type)) {

				sql = "update  Accounts set lastTransactionDate=?,lastTransactionType=?, lastTransactionAmount=? where accountNo=?";
			} else if (CREDIT_CARD.equals(type)) {

				sql = "update  CreditCard set lastTransactionDate=?,lastTransactionType=?, lastTransactionAmount=? where cardNumber=?";
			} else if (DEBIT_CARD.equals(type)) {
				sql = "update  DeditCard set lastTransactionDate=?,lastTransactionType=?, lastTransactionAmount=? where cardNumber=?";
			} else {
				log.debug("updateLastTransaction:Invalid type found [" + type
						+ "] returning without updating");
				message.setMessage("invalid type :" + type);
				return message;

			}

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			if (Transaction_DEBIT.equalsIgnoreCase(transactionType)) {
				pstmt.setString(2, "DEBIT");

			} else {

				pstmt.setString(2, "CREDIT");
			}

			pstmt.setDouble(3, amount);
			pstmt.setString(4, number);
			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Transaction updated successfully");

		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR Updating Transaction\n:" + e);
			log.error("updateLastTransaction:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("updateLastTransaction:closing connection:" + e);
				}

			}

		}

		return message;
	}

	// --------------------------------------------------------------
	public ReturnMessage addLastTransaction(String number, Double amount,
			String transactionType, String type) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		String sql = "";

		if (Accounts.equals(type)) {

			sql = "insert into  AccountLastTransactions values(?,?,?,?)";
		} else if (CREDIT_CARD.equals(type)) {

			sql = "insert into  CreditCardLastTransactions values(?,?,?,?)";
		} else if (DEBIT_CARD.equals(type)) {
			sql = "insert into  DebitCardLastTransactions values(?,?,?,?)";
		} else {
			log.debug("addLastTransaction:Invalid type found [" + type
					+ "] returning without updating");
			message.setMessage("invalid type :" + type);
			return message;
		}

		Connection con = DBUtil.getInstance().getConnection();
		try {

			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, number);
			if (Transaction_DEBIT.equalsIgnoreCase(transactionType)) {
				pstmt.setDouble(2, amount);
				pstmt.setDouble(3, 0.0);
			} else {

				pstmt.setDouble(2, 0.0);
				pstmt.setDouble(3, amount);

			}

			pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

			pstmt.executeUpdate();
			message.setCode("1");
			message.setMessage("Transaction added successfully");

		} catch (SQLException e) {
			message.setCode("0");
			message.setMessage("ERROR adding Transaction\n:" + e);
			log.error("addLastTransaction:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("addLastTransaction:closing connection:" + e);
				}

			}

		}

		return message;
	}

	public ReturnMessage doFundTransfer(String fromAccount, String toAccount,
			String amount) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		double transferAmount;
		if (null == fromAccount || "".equals(fromAccount) || null == toAccount
				|| "".equals(toAccount)) {

			message.setMessage("From and To account must have valid numbers");
			return message;
		}

		if (fromAccount.equals(toAccount)) {

			message.setMessage("From and To account can not be same");
			return message;

		}
		try {
			transferAmount = Double.parseDouble(amount);

		} catch (Exception e) {

			log.error("fundTransfer:" + e);

			message.setMessage("please enter a valid amount");
			return message;
		}
		double fromAccountUpdatedValue = getAccountBalance(fromAccount);
		double toAccountUpdatedValue = getAccountBalance(toAccount);

		if (fromAccountUpdatedValue <= 0
				|| transferAmount > fromAccountUpdatedValue) {

			message.setMessage("insufficient amount.");
			return message;
		}

		fromAccountUpdatedValue -= transferAmount;
		toAccountUpdatedValue += transferAmount;

		updateAccountBalance(fromAccount, fromAccountUpdatedValue);
		updateAccountBalance(toAccount, toAccountUpdatedValue);

		addLastTransaction(fromAccount, transferAmount, Transaction_CREDIT,
				Accounts);
		addLastTransaction(toAccount, transferAmount, Transaction_DEBIT,
				Accounts);

		updateLastTransaction(fromAccount, transferAmount, Transaction_CREDIT,
				Accounts);
		updateLastTransaction(toAccount, transferAmount, Transaction_DEBIT,
				Accounts);

		message.setCode("1");
		message.setMessage("Funds Transfered succesfully.");

		return message;
	}

	public ReturnMessage makeCardPayment(String fromAccount, String cardNumber,
			String amount) {

		ReturnMessage message = new ReturnMessage();
		message.setCode("0");
		message.setMessage("unknown error");
		double transferAmount;
		if (null == fromAccount || "".equals(fromAccount) || null == cardNumber
				|| "".equals(cardNumber)) {

			message.setMessage("Account and Card must have valid numbers");
			return message;
		}

		try {
			transferAmount = Double.parseDouble(amount);

		} catch (Exception e) {

			log.error("fundTransfer:" + e);

			message.setMessage("please enter a valid amount");
			return message;
		}
		double fromAccountUpdatedValue = getAccountBalance(fromAccount);

		if (fromAccountUpdatedValue <= 0
				|| transferAmount > fromAccountUpdatedValue) {

			message.setMessage("insufficient amount.");
			return message;
		}

		fromAccountUpdatedValue -= transferAmount;

		updateAccountBalance(fromAccount, fromAccountUpdatedValue);

		addLastTransaction(fromAccount, transferAmount, Transaction_CREDIT,
				Accounts);
		addLastTransaction(cardNumber, transferAmount, Transaction_DEBIT,
				CREDIT_CARD);

		updateLastTransaction(fromAccount, transferAmount, Transaction_CREDIT,
				Accounts);
		updateLastTransaction(cardNumber, transferAmount, Transaction_DEBIT,
				CREDIT_CARD);

		message.setCode("1");
		message.setMessage("Funds Transfered succesfully.");

		return message;
	}

	private void updateAccountBalance(String accountNumber, double amount) {

		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "update   Accounts set accountBalance=" + amount
					+ " where accountNo='" + accountNumber + "'";

			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			log.error("updateAccountBalance:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("updateAccountBalance:closing connection:" + e);
				}

			}

		}

	}

	private double getAccountBalance(String accountNumber) {

		double accountBalance = 0.0;
		Connection con = DBUtil.getInstance().getConnection();
		try {

			String sql = "select accountBalance from Accounts where accountNo='"
					+ accountNumber + "'";
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				accountBalance = rs.getDouble("accountBalance");

			}

		} catch (SQLException e) {
			log.error("getAccountBalance:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getAccountBalance:closing connection:" + e);
				}

			}

		}

		return accountBalance;

	}

	public ArrayList<LastTransaction> getLastTransactionCreditCard(
			String cardNumber) {

		ArrayList<LastTransaction> lastTransactionList = new ArrayList<LastTransaction>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT  * FROM CreditCardLastTransactions where cardNumber='"
							+ cardNumber + "' order by dateTime desc");

			while (rs.next()) {

				LastTransaction lastTransactionObject = new LastTransaction();

				lastTransactionObject.setCredit(rs.getDouble("credit"));
				lastTransactionObject.setDebit(rs.getDouble("debit"));
				lastTransactionObject.setDatetime(rs.getDate("dateTime"));
				lastTransactionObject.setNumber(cardNumber);
				lastTransactionList.add(lastTransactionObject);
			}

		} catch (SQLException e) {
			log.error("getLastTransactionCreditCard:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log
							.error("getLastTransactionCreditCard:closing connection:"
									+ e);
				}

			}

		}

		return lastTransactionList;
	}

	public ArrayList<LastTransaction> getLastTransactionDebitCard(
			String cardNumber) {

		ArrayList<LastTransaction> lastTransactionList = new ArrayList<LastTransaction>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("SELECT  * FROM DebitCardLastTransactions where cardNumber='"
							+ cardNumber + "'  order by dateTime desc");

			while (rs.next()) {

				LastTransaction lastTransactionObject = new LastTransaction();

				lastTransactionObject.setCredit(rs.getDouble("credit"));
				lastTransactionObject.setDebit(rs.getDouble("debit"));
				lastTransactionObject.setDatetime(rs.getDate("dateTime"));
				lastTransactionObject.setNumber(cardNumber);
				lastTransactionList.add(lastTransactionObject);
			}

		} catch (SQLException e) {
			log.error("getLastTransactionDebitCard:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getLastTransactionDebitCard:closing connection:"
							+ e);
				}

			}

		}

		return lastTransactionList;
	}

	public ArrayList<LastTransaction> getLastTransactionAccount(
			String cardNumber) {

		ArrayList<LastTransaction> lastTransactionList = new ArrayList<LastTransaction>();

		Connection con = DBUtil.getInstance().getConnection();
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select  * from AccountLastTransactions where cardNumber='"
							+ cardNumber + "' order by dateTime desc");

			while (rs.next()) {

				LastTransaction lastTransactionObject = new LastTransaction();

				lastTransactionObject.setCredit(rs.getDouble("credit"));
				lastTransactionObject.setDebit(rs.getDouble("debit"));
				lastTransactionObject.setDatetime(rs.getDate("dateTime"));
				lastTransactionObject.setNumber(cardNumber);
				lastTransactionList.add(lastTransactionObject);
			}

		} catch (SQLException e) {
			log.error("getLastTransactionAccount:executing query" + e);

		} finally {

			if (null != con) {

				try {
					con.close();
				} catch (SQLException e) {

					log.error("getLastTransactionAccount:closing connection:"
							+ e);
				}

			}

		}

		return lastTransactionList;
	}

	public void saveAttachments(ArrayList<ASObject> list) {

		FileOutputStream fout = null;
		for (ASObject ob : list) {

			try {
				byte[] byteArry = (byte[]) ob.get("data");

				//System.out.println(byteArry + "");

				fout = new FileOutputStream("d:\\test\\" + ob.get("name"));

				// fout.write(byteArry);
				// fout.close();
				// fout.flush();

			} catch (IOException e) {
				// TODO Auto-generated catch block""
				log.error("saveAttachments" + e);

				try {
					fout.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					// log.error(e1);
				}

			}

		}

	}

	public void showObjectClass(Object obj) {

		if (obj == null) {

			log.info("showObjectClass: obj is null");

		} else {

			log.info("showObjectClass: obj class is:"
					+ obj.getClass().getName());
			log.info("showObjectClass: simpleName :"
					+ obj.getClass().getSimpleName());
			log.info("showObjectClass: is Array :" + obj.getClass().isArray());

			if (obj instanceof String) {

				log.info("obj value :" + obj);
			}

			if (obj instanceof ASObject) {

				Set<String> keySet = ((ASObject) obj).keySet();

				if (keySet == null) {

					log
							.info("no property defined in the object.key set is null");

				} else {

					for (String key : keySet) {

						log.info("Object Property is :" + key + ">>"
								+ ((ASObject) obj).get(key));

						showObjectClass(((ASObject) obj).get(key));

					}

				}

			}
		}

	}

	public void showString(String obj) {

		log.info(obj);

	}

	public List<Node> getTreeData() {
		int[] count = { 2, 3, 4, 1, 1, 4, 1 };
		List<Node> list = new ArrayList<Node>();
		int level = 1;
		for (int i = 0; i <= count.length - 1; i++) {
			Node parent = new Node("node [" + (i + 1) + "]", level);
			List<Node> children = getChildrens(parent, count[i], 1);
			parent.setChildren(children);
			list.add(parent);
		}
		return list;
	}

	private List<Node> getChildrens(Node parentNode, int countOfChildrens,
			int level) {
		List<Node> children = null;
		for (int j = 0; j < countOfChildrens; j++) {
			if (children == null) {
				children = new ArrayList<Node>();
			}
			Node child = new Node(parentNode.getLabel() + "[" + (j + 1) + "]",
					parentNode.getLevel() + 1);
			List<Node> childChildren = getChildrens(child,
					countOfChildrens - 1, parentNode.getLevel() + 1);
			child.setChildren(childChildren);
			children.add(child);
		}
		return children;
	}

	public ArrayList<Customer> getArabicData() {

		ArrayList<Customer> customerListArabic = new ArrayList<Customer>();

	  for(int i=0;i<5;i++){
		Customer obj = new Customer();
		obj.setAddress("عنوان العميل");
		obj.setAge("العمر العملاء");
		obj.setAuthenticated("العملاء مصادقة");
		obj.setCity("العملاء مدينة");
		obj.setCountry("بلد العملاء");
		obj.setCustomerId("تحديد عدد العملاء");
		obj.setCustomerName("ﺓﻳﺩﻭﻌﺳﻟﺍ ﺓﻳﺑﺭﻌﻟﺍ ﺓﻛﻟﻣﻣﻟﺍ");
		obj.setEmail("البريد الإلكتروني");
		obj.setFax("099999");
		obj.setSex("Male");
		obj.setLanguage("لغة");
		obj.setMaritalStatus("الحالة الإجتماعية");
		obj.setState("state");
		obj.setType("type");
		customerListArabic.add(obj);
	  }
		
		
		return customerListArabic;

	}

	public List<ArabicEnglishPojo>  getMixedLanguageDataObjectList(){
		
		
		ArrayList<ArabicEnglishPojo> mixedLanguageObjectList = new ArrayList<ArabicEnglishPojo>();
		
		for(int i=0;i<5;i++){
			
			ArabicEnglishPojo obj=new ArabicEnglishPojo();
			
			obj.setArabicText("ﺓﻳﺩﻭﻌﺳﻟﺍ ﺓﻳﺑﺭﻌﻟﺍ ﺓﻛﻟﻣﻣﻟﺍ");
			obj.setEnglishText("Customer Name");
			
			mixedLanguageObjectList.add(obj);
			
		}
		
		
		
		return mixedLanguageObjectList;
	}
	
	
}
