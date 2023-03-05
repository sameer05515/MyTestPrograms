<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="java.io.*,java.util.*,javax.xml.parsers.ParserConfigurationException,org.xml.sax.SAXException,com.prem.vocab.build.proj.VocabBuildConstants.Examples,com.prem.vocab.build.proj.VocabBuildConstants.Meanings,com.prem.vocab.build.proj.VocabBuildConstants.Word,com.prem.vocab.build.proj.util.XMLUtilityImpl"%>
<style>
body {
 width: 650px;
 margin: 0 auto;
 background: green;
 /*background: #000;*/
 color: #FFF;
 font: 12px sans-serif;
}
h1 {
 font-size: 24px;
}
h2 {
 font-size: 18px;
 margin-top: 0;
}
a {
 color: #FFF;
}
a:focus,
a:hover {
 text-decoration: none;
}
table {
 margin-bottom: 10px;
 border-spacing: 0;
}
caption {
 margin-bottom: 10px;
 font-size: 14px;
 font-weight: bold;
 text-align: left;
}
th,
td {
 padding: 0 10px 0 0;
 text-align: left;
}
.planet {
 margin: 10px 0;
 padding: 20px 20px 20px 200px;
 border: 1px solid #FFF;
 background-position: 100px 20px;
 /*background-repeat: no-repeat;*/
}

.prem{
background-image: url(http://127.0.0.1:8888/bce/css/prem.jpg);
}
</style>

<%
	
	try {
		XMLUtilityImpl xmlUtilityImpl=new XMLUtilityImpl();
		List<HashMap<String, String>> list = xmlUtilityImpl
					.getAllDescription();

			//PrintStream out = System.out;

			method3(out,list,xmlUtilityImpl);

			out.println(list.size());
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

private void method3(JspWriter out,List<HashMap<String, String>> list,XMLUtilityImpl xmlUtilityImpl) throws Exception{

				int allCount=0;
				for (int i = list.size() - 1; i >= 0; i--) {
				HashMap<String, String> mapObject = list.get(i);

				if (mapObject.keySet() != null && mapObject.keySet().size() > 0) {
					
					out.println("<div class=\"planet prem\">");
					
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
							
							out.println("<h1>" +(++count) + " : " + " : " +  str
									+ "</h1>");
						}
						out.println("</td></tr>");

					}
					
					out.println("</table>");
					out.println("</div>");
				}
				
			}

			out.println(list.size());
				
			
			
			
}%>


<%!
private void method2(JspWriter out,List<HashMap<String, String>> list,XMLUtilityImpl xmlUtilityImpl) throws Exception{
for (int i = list.size() - 1; i >= 0; i--) {
				HashMap<String, String> mapObject = list.get(i);

				if (mapObject.keySet() != null && mapObject.keySet().size() > 0) {
					out.println("--------------------------");
					out.println("<div class=\"planet prem\">");
					
					String mapValueForKey = getValue(mapObject,
							Word.node.getName());
					
					out.println(Word.node.getName() + ":" + "<h1>"
							+ mapValueForKey + "</h1>");
					

					mapValueForKey = getValue(mapObject, Word.TYPE.getName());
					
					out.println(Word.TYPE.getName() + ":" + "<h1>"
							+ mapValueForKey + "</h1>");
					

					mapValueForKey = getValue(mapObject,
							Meanings.node.getName());
					
					if (mapValueForKey != null
							&& !mapValueForKey.trim().equalsIgnoreCase("")) {
						String[] strarr = mapValueForKey.trim().split("-->");
						int count = 0;
						out.println(Meanings.node.getName());
						for (String str : strarr) {
							out.println("<h1>" +(++count) + " : " + ":" +  str
									+ "</h1>");
						}

					}					

					mapValueForKey = getValue(mapObject,
							Examples.node.getName());
					
					if (mapValueForKey != null
							&& !mapValueForKey.trim().equalsIgnoreCase("")) {
						String[] strarr = mapValueForKey.trim().split("-->");
						int count = 0;
						out.println(Examples.node.getName());
						for (String str : strarr) {
							out.println("<h1>" +(++count) + " : " + ":" +  str
									+ "</h1>");
						}

					}
					
					out.println("</div>");
				}
				
			}

			out.println(list.size());
				
			
			
			
}%>

<%!
private void method1(JspWriter out,List<HashMap<String, String>> list,XMLUtilityImpl xmlUtilityImpl) throws Exception{
for (int i = list.size() - 1; i >= 0; i--) {
				HashMap<String, String> mapObject = list.get(i);

				if (mapObject.keySet() != null && mapObject.keySet().size() > 0) {
					out.println("--------------------------");
					out.println("<div class=\"planet prem\">");
					for (Object key : mapObject.keySet()) {
						String mapValueForKey = mapObject.get(key);
						// out.println(key + " : " + mapValueForKey);
						if (key.equals(Word.node.getName())) {
							out.println("<font size=\"3\" style=\"font-weight: bold\" color=\"red\">");
							out.println(Word.node.getName() + ":" + "<h1>"
									+ mapValueForKey+ "</h1>" );
							out.println("</font><br/>");
						}

						if (key.equals(Word.TYPE.getName())) {
							out.println(Word.TYPE.getName() + ":" + "<h1>"
									+ mapValueForKey + "</h1>");
						}

						if (key.equals(Meanings.node.getName())) {
							if (mapValueForKey != null
									&& !mapValueForKey.trim().equalsIgnoreCase(
											"")) {
								String[] strarr = mapValueForKey.trim().split(
										"-->");
								int count = 0;
								out.println(Examples.node.getName());
								for (String str : strarr) {
									out.println((++count) + " : " + ":"
											+ "<h1>" + str + "</h1>");
								}

							}

						}

						if (key.equals(Examples.node.getName())) {
							// out.println(Examples.node.getName()
							// + ":" + "<h1>" + mapValueForKey + "</h1>");
							if (mapValueForKey != null
									&& !mapValueForKey.trim().equalsIgnoreCase(
											"")) {
								String[] strarr = mapValueForKey.trim().split(
										"-->");
								int count = 0;
								out.println(Examples.node.getName());
								for (String str : strarr) {
									out.println((++count) + " : " + ":"
											+ "<h1>" + str + "</h1>");
								}

							}
						}

					}
					out.println("</div>");
				}
				// System.out.println("--------------------------");
			}
}
%>

<%!
public static String getValue(HashMap<String, String> mapObject, String key) {
		String ret = "";
		if (mapObject.containsKey(key)) {
			ret = mapObject.get(key);
			ret = (ret != null) ? ret : "";
		}

		return ret;
	}

%>