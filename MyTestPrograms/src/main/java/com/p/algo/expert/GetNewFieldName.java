package com.p.algo.expert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public class GetNewFieldName {

//	static HashMap<String, Integer> fieldCount=new HashMap<String, Integer>();

    public static void main(String[] args) {

//		List<String> rawIndexFieldNames= Arrays.asList("Phone no1","Phone no1",
//				"Phone no1","Phone no1","Phone no1","Phone no11",
//				"Phone no11","Phone no11","Phone no11");

//		List<String> rawIndexFieldNames= Arrays.asList();

        List<String> rawIndexFieldNames = Arrays.asList("Doc", "Doc", "Doc", "Doc", "Doc1", "Doc", "Doc1", "Doc1", "Doc", "Doc11");

//		List<String> rawIndexFieldNames= Arrays.asList("01","011","01","011","1","3","2","1","02","02","02","02","03","03","03","03","3","3","3","3");

//		String[] output= new String[input.length];
//		Set<String> outputSet=new LinkedHashSet<>();
        HashMap<String, Integer> keyCountMap = new HashMap<String, Integer>();
//		for(String inp:input) {			
//			SS newInpMap=getNewFieldName(inp,keyCountMap);
//			String newInp=newInpMap.getKey();
//			keyCountMap=newInpMap.getKeyCountMap();
//			newKeyAndKeyMapObj.add(newInp);			
//		}

        LinkedHashSet<String> outputSet1 = new LinkedHashSet<>();
        for (String rawIndexFieldName : rawIndexFieldNames) {
            String newIndexFieldName = new String(rawIndexFieldName.trim());
            newIndexFieldName = newIndexFieldName != null ? newIndexFieldName.replaceAll("[\\s/\\\\:\\*\\-\\<\\>\\?”'\\.\\(\\)\\+\\!@#%\\^&;~\\{\\}\\|\\[\\]]", "") : "";
            if (!newIndexFieldName.isEmpty()) {
                HashMap<String, Object> newFieldNameAndMap = getNewFieldNameAndMap(newIndexFieldName, keyCountMap);
                newIndexFieldName = (String) newFieldNameAndMap.get("newIndexFieldName");
                keyCountMap = (HashMap<String, Integer>) newFieldNameAndMap.get("keyCountMap");
                outputSet1.add(newIndexFieldName);
            }
        }

        keyCountMap = new HashMap<String, Integer>();
        LinkedHashSet<String> outputSet2 = new LinkedHashSet<>();
        List<String> keyNames = new ArrayList<>();
        for (String rawIndexFieldName : rawIndexFieldNames) {
            rawIndexFieldName = rawIndexFieldName != null ? rawIndexFieldName.replaceAll("[\\s/\\\\:\\*\\-\\<\\>\\?”'\\.\\(\\)\\+\\!@#%\\^&;~\\{\\}\\|\\[\\]]", "") : "";
            String newIndexFieldName = new String(rawIndexFieldName.trim());

            if (!newIndexFieldName.isEmpty()) {
                while (keyNames.contains(newIndexFieldName.toLowerCase())) {
                    int occurrenceCount = 0;
                    if (keyCountMap.containsKey(newIndexFieldName.toLowerCase())) {
                        occurrenceCount = keyCountMap.get(newIndexFieldName.toLowerCase()) + 1;
                    } else {
                        occurrenceCount = 1;
                    }
                    keyCountMap.put(newIndexFieldName.toLowerCase(), occurrenceCount);
                    newIndexFieldName = newIndexFieldName + String.valueOf(occurrenceCount);
                }
                keyNames.add(newIndexFieldName.toLowerCase());
                outputSet2.add(newIndexFieldName);
            }
        }

        keyCountMap = new HashMap<String, Integer>();
        LinkedHashSet<String> outputSet3 = new LinkedHashSet<>();
        List<String> keyNames1 = new ArrayList<>();
        for (String rawIndexFieldName : rawIndexFieldNames) {
            rawIndexFieldName = rawIndexFieldName != null ? rawIndexFieldName.replaceAll("[\\s/\\\\:\\*\\-\\<\\>\\?”'\\.\\(\\)\\+\\!@#%\\^&;~\\{\\}\\|\\[\\]]", "") : "";
            String newIndexFieldName = new String(rawIndexFieldName.trim());

            String newIndexFieldNameLC = newIndexFieldName.toLowerCase();
            if (!newIndexFieldName.isEmpty()) {
                while (keyNames1.contains(newIndexFieldNameLC)) {
                    int occurrenceCount = 1;
                    if (keyCountMap.containsKey(newIndexFieldNameLC)) {
                        occurrenceCount = keyCountMap.get(newIndexFieldNameLC) + 1;
                    } /*
                     * else { occurrenceCount = 1; }
                     */
                    keyCountMap.put(newIndexFieldNameLC, occurrenceCount);
                    newIndexFieldName = rawIndexFieldName.trim() + String.valueOf(occurrenceCount);
                    newIndexFieldNameLC = newIndexFieldName.toLowerCase();
                }
                keyNames1.add(newIndexFieldNameLC);
                outputSet3.add(newIndexFieldName);
            }
        }

        System.out.println(rawIndexFieldNames);
        System.out.println(outputSet1);
        System.out.println(outputSet2);
        System.out.println(outputSet3);
        System.out.println((outputSet2.size() == rawIndexFieldNames.size()) && (rawIndexFieldNames.size() == outputSet3.size()));

//		
//		for(String s:rawIndexFieldNames)
//			System.out.print(s+"\t\t");
//		System.out.println();
//		for(String s:outputSet1)
//			System.out.print(s+"\t\t");
//		System.out.println();
//		for(String s:outputSet2)F
//			System.out.print(s+"\t\t");
//		System.out.println();
        Iterator<String> i1 = rawIndexFieldNames.iterator();
        Iterator<String> i2 = outputSet1.iterator();
        Iterator<String> i3 = outputSet2.iterator();
        Iterator<String> i4 = outputSet3.iterator();

        for (; i1.hasNext() && i2.hasNext() && i3.hasNext() && i4.hasNext(); ) {
            System.out.println(i2.next() + "\t\t\t\t" + i3.next() + "\t\t\t\t" + i4.next() + "\t\t\t\t" + i1.next());
        }

    }

    private static int findCountInList(String str, List<String> list) {
        Iterator<String> listItt = list.iterator();
        int count = 0;
        while (listItt.hasNext()) {
            String value = listItt.next();
            count = value.equals(str) ? count + 1 : count;
        }
        return count;
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
    private static HashMap<String, Object> getNewFieldNameAndMap(String rawIndexFieldName, HashMap<String, Integer> keyCountMap) {
        String rawIndexFieldNameCopy = new String(rawIndexFieldName.trim());

        if (keyCountMap.containsKey(rawIndexFieldNameCopy.toLowerCase())) {
            HashMap<String, Object> newKeyAndKeyMapObj = new HashMap<String, Object>();
            int count = keyCountMap.get(rawIndexFieldNameCopy.toLowerCase());
            keyCountMap.put(rawIndexFieldNameCopy.toLowerCase(), count + 1);
            String newFieldName = rawIndexFieldNameCopy + count;
            if (keyCountMap.containsKey(newFieldName.toLowerCase())) {
                newKeyAndKeyMapObj = getNewFieldNameAndMap(rawIndexFieldNameCopy, keyCountMap);
            } else {
                keyCountMap.put(newFieldName.toLowerCase(), 1);
                newKeyAndKeyMapObj.put("newIndexFieldName", newFieldName);
                newKeyAndKeyMapObj.put("keyCountMap", keyCountMap);
            }
            return newKeyAndKeyMapObj;
        } else {
            keyCountMap.put(rawIndexFieldNameCopy.toLowerCase(), 1);
            HashMap<String, Object> newKeyAndKeyMapObj = new HashMap<String, Object>();
            newKeyAndKeyMapObj.put("newIndexFieldName", rawIndexFieldNameCopy);
            newKeyAndKeyMapObj.put("keyCountMap", keyCountMap);
            return newKeyAndKeyMapObj;
        }

    }


}
