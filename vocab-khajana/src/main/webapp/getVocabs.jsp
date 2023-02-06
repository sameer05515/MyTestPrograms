<%@ page import="java.io.*,java.lang.*,java.util.*,javax.xml.parsers.ParserConfigurationException,org.xml.sax.SAXException,com.prem.vocab.build.proj.VocabBuildConstants.Examples,com.prem.vocab.build.proj.VocabBuildConstants.Meanings,com.prem.vocab.build.proj.VocabBuildConstants.Word,com.prem.vocab.build.proj.util.XMLUtilityImpl"%>  
<%
	int offset=0;
	int count=0;
	try{
		offset=Integer.parseInt(request.getParameter("offset"));			
	}catch(Exception e){

	}
	try{
		count=Integer.parseInt(request.getParameter("count"));			
	}catch(Exception e){

	}



	try {
		XMLUtilityImpl xmlUtilityImpl=new XMLUtilityImpl();
		List<HashMap<String, String>> list = xmlUtilityImpl.getAllDescription();		

		String str=method4(out,list,xmlUtilityImpl);

		StringBuilder objStrBuilder = new StringBuilder("");
		objStrBuilder.append("{");

		objStrBuilder.append("\"txtOffset\" : \""+offset+"\"");
		objStrBuilder.append(",");

		objStrBuilder.append("\"txtCount\" : \""+count+"\"");
		objStrBuilder.append(",");

		objStrBuilder.append("\"words\" : "+str+"");


		objStrBuilder.append("}");

		out.println(objStrBuilder.toString());
		
	} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
%>
<%!
	public static final String exerciseNodeContainer="exerciseNodeContainer";
	public static final String exerciseHeaderNode="exerciseHeaderNode";
	public static final String currentExerciseNodeAllowedTimeToDisplay="currentExerciseNodeAllowedTimeToDisplay";
	public static final String exerciseNode="exerciseNode";
	public static final String exerciseNodeImageList="exerciseNodeImageList";
	public static final String exerciseNodeImage="exerciseNodeImage";
	public static final String fileWriterURL="http://127.0.0.1:8888/bce/fileWriter?documentId=";
	public static final String imageWidth="500";
	public static final String imageHeight="500";
	public static final String allowedTimeForWord="120";


	private String method4(JspWriter out, List<HashMap<String, String>> list, XMLUtilityImpl xmlUtilityImpl)
	throws Exception {

	int allCount = 0;
	StringBuilder objStrBuilder = new StringBuilder("");
	objStrBuilder.append("[");
	for (int i = list.size() - 1; i >= 0; i--) {
	HashMap<String, String> mapObject = list.get(i);

	if (i != list.size() - 1) {
	objStrBuilder.append(",");
	}
	objStrBuilder.append("{");
	if (mapObject.keySet() != null && mapObject.keySet().size() > 0) {

	objStrBuilder.append(append("id", "" + (i + 1)));
	objStrBuilder.append(",");
	objStrBuilder.append(append(Word.node.getName(), getValue(mapObject, Word.node.getName())));
	objStrBuilder.append(",");
	objStrBuilder.append(append(Word.TYPE.getName(), getValue(mapObject, Word.TYPE.getName())));
	objStrBuilder.append(",");

	objStrBuilder.append("\"" + Meanings.node.getName() + "\":");
	objStrBuilder.append("[");
	String mapValueForKey = getValue(mapObject, Meanings.node.getName());

	if (mapValueForKey != null && !mapValueForKey.trim().equalsIgnoreCase("")) {
	String[] strarr = mapValueForKey.trim().split("-->");
	int count = 0;
	for (String str : strarr) {

	if (count>0) {
	objStrBuilder.append(",");
	}

	objStrBuilder.append("{");
	objStrBuilder.append(append("id", "" + (++count)));
	objStrBuilder.append(",");
	objStrBuilder.append(append("meaning", str));
	objStrBuilder.append("}");
	}
	}
	objStrBuilder.append("]");

	objStrBuilder.append(",");

	objStrBuilder.append("\"" + Examples.node.getName() + "\":");
	objStrBuilder.append("[");
	mapValueForKey = getValue(mapObject, Examples.node.getName());

	if (mapValueForKey != null && !mapValueForKey.trim().equalsIgnoreCase("")) {
	String[] strarr = mapValueForKey.trim().split("-->");
	int count = 0;
	for (String str : strarr) {

	if (count>0) {
	objStrBuilder.append(",");
	}

	objStrBuilder.append("{");
	objStrBuilder.append(append("id", "" + (++count)));
	objStrBuilder.append(",");
	objStrBuilder.append(append("example", str));
	objStrBuilder.append("}");
	}
	}
	objStrBuilder.append("]");
	}
	objStrBuilder.append("}");
	}

	objStrBuilder.append("]");

	return objStrBuilder.toString();

	}

	private void method3(JspWriter out,List<HashMap<String, String>> list,XMLUtilityImpl xmlUtilityImpl) throws Exception{

		int allCount=0;
		for (int i = list.size() - 1; i >= 0; i--) {
			HashMap<String, String> mapObject = list.get(i);

			if (mapObject.keySet() != null && mapObject.keySet().size() > 0) {

				///////////////
				out.println("<div id=\""+exerciseNodeContainer+(i+1)+"\">");
				out.println("<div id=\"" + exerciseHeaderNode+(i+1)+"\" class=\"excerciseHeader\">" + getValue(mapObject,
				Word.node.getName()) +"</div>");
				out.println("<div id=\"" + currentExerciseNodeAllowedTimeToDisplay+(i+1)+"\" >" + allowedTimeForWord +"</div>");
				out.println("<div id=\""+exerciseNode+(i+1)+"\" class=\"htmlClass prem\">");
				out.println("<div id=\""+exerciseNodeImageList+(i+1)+"\">");
				out.println("<div id=\""+exerciseNodeImage+(i+1)+"_"+(i+1)+"\">");
				//////////////

				out.println("<div class=\"planet premu426\">");

				out.println("<table>");
				String mapValueForKey = getValue(mapObject,
				Word.node.getName());
				out.println("<tr>");
				out.println("<td>");
				out.println(Word.node.getName() + ":" + "<h1>"
				+(++allCount)+" : "+ mapValueForKey + "</h1>");
				out.println("</td>");


				mapValueForKey = getValue(mapObject, Word.TYPE.getName());

				out.println("<td>");
				out.println(Word.TYPE.getName() + ":" + "<h1>"
				+ mapValueForKey + "</h1>");
				out.println("</td></tr>");

				mapValueForKey = getValue(mapObject,
				Meanings.node.getName());

				if (mapValueForKey != null
				&& !mapValueForKey.trim().equalsIgnoreCase("")) {
				String[] strarr = mapValueForKey.trim().split("-->");
				int count = 0;
				out.println("<tr>");
				out.println("<td>");
				out.println(Meanings.node.getName());
				out.println("</td>");
				out.println("<td>");
				for (String str : strarr) {

				out.println("<h1>" +(++count) + " : " + " : " +  str
				+ "</h1>");
				}
				out.println("</td></tr>");

				}					

				mapValueForKey = getValue(mapObject,
				Examples.node.getName());

				if (mapValueForKey != null
				&& !mapValueForKey.trim().equalsIgnoreCase("")) {
				String[] strarr = mapValueForKey.trim().split("-->");
				int count = 0;
				out.println("<tr>");
				out.println("<td>");
				out.println(Examples.node.getName());
				out.println("</td>");
				out.println("<td>");
				for (String str : strarr) {
					out.println("<h1>" +(++count) + " : " + " : " +  str+ "</h1>");
				}
				out.println("</td></tr>");

				}

				out.println("</table>");
				out.println("</div>");

				///////////
				out.println("</div>");
				out.println("</div>");
				out.println("</div>");
				out.println("</div>");
				/////////
			}

		}

		out.println(list.size());
	}

	private static String append(String key, String value) {
		return "\"" + key + "\"" + ":" + "\"" + value + "\"";
	}

	public static String getValue(HashMap<String, String> mapObject, String key) {
		String ret = "";
		if (mapObject.containsKey(key)) {
			ret = mapObject.get(key);
			ret = (ret != null) ? ret : "";
		}

		return ret;
	}

%>