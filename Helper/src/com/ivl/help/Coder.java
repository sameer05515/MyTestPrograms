package com.ivl.help;

import java.util.Base64;

public class Coder {

	public String decode(String param) {
		return new String(Base64.getDecoder().decode(param));
	}

	public String encode(String param) {
		return new String(Base64.getEncoder().encode(param.getBytes()));
	}

	public static void main(String... args) {
//		String param = "eyJsZWFkX2lkIjoiU0UwOTk5OSIsInBhbl9udW0iOiJCRklQUDkyMjhQIiwiZG9iIjoiMjBcLzEyXC8xOTg5IiwidG9rZW4iOiJyZHJhaHUxMjM0NWw2NjZAZ21haWwuY29tIiwic2VjcmV0X2tleSI6IjUiLCJyZXFfYXBwX25hbWUiOiJlcXVpdHkiLCJyZXFfc3JjX2lwIjoiIiwiY2xpZW50X2lwIjoiIiwiZmlsbGVyMSI6IiIsImZpbGxlcjIiOiIiLCJmaWxsZXIzIjoiIiwiZGV2aWNlX2lkIjoiIn0=";
//
//		String s = new Coder().decode(param);
//		System.out.println("endoded \n===========\n" + param + "\n=========\n\ndecoded \n===========\n" + s);
//
//		String reencode = new Coder().encode(s);
//
//		String s1 = new Coder().decode(reencode);
//		System.out.println(
//				"\n\nre-endoded \n===========\n" + reencode + "\n=========\n\nre-decoded \n===========\n" + s1);
		
//		String encodedString="{\r\n" + 
//				"   \"lead_id\":\"SE00006692\",\r\n" + 
//				"   \"pan_num\":\"ALSPB1986F\",\r\n" + 
//				"   \"dob\":\"12\\/12\\/1990\",\r\n" + 
//				"   \"token\":\"rdrahu12345l666@gmail.com\",\r\n" + 
//				"   \"secret_key\":\"5\",\r\n" + 
//				"   \"req_app_name\":\"equity\",\r\n" + 
//				"   \"req_src_ip\":\"\",\r\n" + 
//				"   \"client_ip\":\"\",\r\n" + 
//				"   \"filler1\":\"\",\r\n" + 
//				"   \"filler2\":\"\",\r\n" + 
//				"   \"filler3\":\"\",\r\n" + 
//				"   \"device_id\":\"\"\r\n" + 
//				"}";
		
		
//		String encodedString="{\"lead_id\":\"SE00006692\", \"pan_num\":\"ALSPB1986F\", \"dob\":\"12\\/12\\/1990\", \"token\":\"rdrahu12345l666@gmail.com\", \"secret_key\":\"5\", \"req_app_name\":\"equity\", \"req_src_ip\":\"\", \"client_ip\":\"\", \"filler1\":\"\", \"filler2\":\"\", \"filler3\":\"\", \"device_id\":\"\" }";
//		String encodedString="{\"lead_id\":\"SE00006692\", \"pan_num\":\"BJXPK2005G\", \"dob\":\"31\\/12\\/1991\", \"token\":\"rdrahu12345l666@gmail.com\", \"secret_key\":\"5\", \"req_app_name\":\"equity\", \"req_src_ip\":\"\", \"client_ip\":\"\", \"filler1\":\"\", \"filler2\":\"\", \"filler3\":\"\", \"device_id\":\"\" }";
		
		String[] encodedString= {"{\"lead_id\":\"SE00006692\", \"pan_num\":\"AWCPV0810H\", \"dob\":\"14/08/1992\", \"token\":\"rdrahu12345l666@gmail.com\", \"secret_key\":\"5\", \"req_app_name\":\"equity\", \"req_src_ip\":\"\", \"client_ip\":\"\", \"filler1\":\"\", \"filler2\":\"\", \"filler3\":\"\", \"device_id\":\"\" }",
				"{\"lead_id\":\"SE00006692\", \"pan_num\":\"CKKPK1348F\", \"dob\":\"12/12/1991\", \"token\":\"rdrahu12345l666@gmail.com\", \"secret_key\":\"5\", \"req_app_name\":\"equity\", \"req_src_ip\":\"\", \"client_ip\":\"\", \"filler1\":\"\", \"filler2\":\"\", \"filler3\":\"\", \"device_id\":\"\" }",
				"{\"lead_id\":\"SE00006692\", \"pan_num\":\"BTTPD2140K\", \"dob\":\"05/01/1989\", \"token\":\"rdrahu12345l666@gmail.com\", \"secret_key\":\"5\", \"req_app_name\":\"equity\", \"req_src_ip\":\"\", \"client_ip\":\"\", \"filler1\":\"\", \"filler2\":\"\", \"filler3\":\"\", \"device_id\":\"\" }",
				"{\"lead_id\":\"SE00006692\", \"pan_num\":\"BFNPM8877G\", \"dob\":\"20/11/1990\", \"token\":\"rdrahu12345l666@gmail.com\", \"secret_key\":\"5\", \"req_app_name\":\"equity\", \"req_src_ip\":\"\", \"client_ip\":\"\", \"filler1\":\"\", \"filler2\":\"\", \"filler3\":\"\", \"device_id\":\"\" }"};
		
		for(String str:encodedString) {
//			System.out.println(
//					"\n\nencodedString \n===========\n" + str + "\n=========\n\nencodedString \n===========\n");
			String s= new Coder().encode(str);
			
			String s1 = new Coder().decode(s);
			System.out.println(
					"\n\nre-endoded \n===========\n" + s + "\n=========\n\nre-decoded \n===========\n" + s1);
		}
		
//		String aa="eyJsZWFkX2lkIjoiU0UwMDAwNjY5MiIsICJwYW5fbnVtIjoiQkpYUEsyMDA1RyIsICJkb2IiOiIzMVwvMTJcLzE5OTEiLCAidG9rZW4iOiJyZHJhaHUxMjM0NWw2NjZAZ21haWwuY29tIiwgInNlY3JldF9rZXkiOiI1IiwgInJlcV9hcHBfbmFtZSI6ImVxdWl0eSIsICJyZXFfc3JjX2lwIjoiIiwgImNsaWVudF9pcCI6IiIsICJmaWxsZXIxIjoiIiwgImZpbGxlcjIiOiIiLCAiZmlsbGVyMyI6IiIsICJkZXZpY2VfaWQiOiIiIH0=";
//		String s1 = new Coder().decode(aa);
//		System.out.println(s1);
	}

}
