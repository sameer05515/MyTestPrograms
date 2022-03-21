package com.p.algo.expert;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class GetNewFieldName {
	
//	static HashMap<String, Integer> fieldCount=new HashMap<String, Integer>();

	public static void main(String[] args) {
		String[] rawIndexFieldNames= {"Ram","Ram","Ram","Ram 1","Ram 2","Ram 3","Ram ","Ram 5","ram","ram  ","ram 1"};
//		String[] output= new String[input.length];
		Set<String> outputSet=new LinkedHashSet<>();
		HashMap<String, Integer> keyCountMap=new HashMap<String, Integer>() ;
//		for(String inp:input) {			
//			SS newInpMap=getNewFieldName(inp,keyCountMap);
//			String newInp=newInpMap.getKey();
//			keyCountMap=newInpMap.getKeyCountMap();
//			newKeyAndKeyMapObj.add(newInp);			
//		}
		
		for(String rawIndexFieldName:rawIndexFieldNames) {			
			HashMap<String, Object> newFieldNameAndMap=getNewFieldNameAndMap(rawIndexFieldName,keyCountMap);
			String newIndexFieldName=(String)newFieldNameAndMap.get("newIndexFieldName");
			keyCountMap=(HashMap<String, Integer>)newFieldNameAndMap.get("keyCountMap");
			outputSet.add(newIndexFieldName);			
		}
		System.out.println(outputSet);
		System.out.println(outputSet.size()==rawIndexFieldNames.length);

	}

//	private static SS getNewFieldName(String inp,HashMap<String, Integer> keyCountMap) {
//		String uniqueInp=new String(inp.trim());
//				
//		if(keyCountMap.containsKey(uniqueInp.toLowerCase())) {
//			SS newKeyAndKeyMapObj=new SS();
//			int count=keyCountMap.get(uniqueInp.toLowerCase());
//			keyCountMap.put(uniqueInp.toLowerCase(), count+1);
//			String newInp=uniqueInp+" "+count;
//			if(keyCountMap.containsKey(newInp.toLowerCase())) {
//				newKeyAndKeyMapObj=getNewFieldName(uniqueInp,keyCountMap);
//			}else {
//				keyCountMap.put(newInp.toLowerCase(), 1);
//				newKeyAndKeyMapObj=new SS(newInp,keyCountMap);
//			}			
//			return newKeyAndKeyMapObj;
//		}else {
//			keyCountMap.put(uniqueInp.toLowerCase(), 1);
//			SS newKeyAndKeyMapObj=new SS(uniqueInp,keyCountMap);
//			return newKeyAndKeyMapObj;
//		}
//		
//	}
//	
	private static HashMap<String, Object> getNewFieldNameAndMap(String rawIndexFieldName,HashMap<String, Integer> keyCountMap) {
		String rawIndexFieldNameCopy=new String(rawIndexFieldName.trim());
				
		if(keyCountMap.containsKey(rawIndexFieldNameCopy.toLowerCase())) {
			HashMap<String, Object> newKeyAndKeyMapObj=new HashMap<String, Object>();
			int count=keyCountMap.get(rawIndexFieldNameCopy.toLowerCase());
			keyCountMap.put(rawIndexFieldNameCopy.toLowerCase(), count+1);
			String newFieldName=rawIndexFieldNameCopy+" "+count;
//			String newFieldName=rawIndexFieldNameCopy+count;
			if(keyCountMap.containsKey(newFieldName.toLowerCase())) {
				newKeyAndKeyMapObj=getNewFieldNameAndMap(rawIndexFieldNameCopy,keyCountMap);
			}else {
				keyCountMap.put(newFieldName.toLowerCase(), 1);
				newKeyAndKeyMapObj.put("newIndexFieldName", newFieldName);
				newKeyAndKeyMapObj.put("keyCountMap", keyCountMap);
			}			
			return newKeyAndKeyMapObj;
		}else {
			keyCountMap.put(rawIndexFieldNameCopy.toLowerCase(), 1);
			HashMap<String, Object> newKeyAndKeyMapObj=new HashMap<String, Object>();
			newKeyAndKeyMapObj.put("newIndexFieldName", rawIndexFieldNameCopy);
			newKeyAndKeyMapObj.put("keyCountMap", keyCountMap);
			return newKeyAndKeyMapObj;
		}
		
	}

}
