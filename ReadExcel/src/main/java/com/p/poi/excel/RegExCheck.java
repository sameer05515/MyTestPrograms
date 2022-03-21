package com.p.poi.excel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExCheck {
	
	public static void main(String[] args) {
		String implicitString = "\\w+ \\d{1,2}-\\d{1,2}-\\d{4}";
		String def=".+";
		
		String explicitSearchString = "(\\w+ \\d{1,2})-(\\d{1,2})-(\\d{4})";
		
		String value="aaaaaaa";
		String value2="aaaaaaaaaaaaaaaaaaaaaa";
		isContainsExplicitGroup(implicitString, value);
		isContainsExplicitGroup(explicitSearchString, value);
		isContainsExplicitGroup(def, value);
		
		isContainsExplicitGroup(implicitString, value2);
		isContainsExplicitGroup(explicitSearchString, value2);
		isContainsExplicitGroup(def, value2);
		
		
	}
	
	private static boolean isContainsExplicitGroup(String valueRegex, String valueText){
        boolean containsExplicitGroup=false;
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(valueRegex, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(valueText);
        int count = matcher.groupCount();
        
        if(count>0 ) {
            containsExplicitGroup=true;
        }
        System.out.println(count+" "+valueRegex+" "+valueText+" "+containsExplicitGroup);
        return containsExplicitGroup;
    }

}
