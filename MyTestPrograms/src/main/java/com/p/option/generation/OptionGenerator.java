package com.p.option.generation;

public class OptionGenerator {

	public static void main(String[] args) {
		for(String s:statesUts) {
			System.out.println("<option>"
					+ s
					+ "</option>");
		}

	}

	private static String[] statesUts = { "Andra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa",
			"Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala",
			"Madya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Orissa", "Punjab",
			"Rajasthan", "Sikkim", "Tamil Nadu", "Telagana", "Tripura", "Uttaranchal", "Uttar Pradesh", "West Bengal",
			"Andaman and Nicobar Islands", "Chandigarh", "Dadar and Nagar Haveli", "Daman and Diu", "Delhi",
			"Lakshadeep", "Pondicherry"};

}
