<%@ page import="java.io.*,java.util.*"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css;" href="lipstick.css">
		<title>Auto Refresh Header Example</title>
	</head>
	<body>
		<center>
			<h2>
				Auto Refresh Header Example
			</h2>
			<%
				// Set refresh, autoload time as 5 seconds
				response.setIntHeader("Refresh", 1);
				// Get current time
				Calendar calendar = new GregorianCalendar();
				String am_pm;
				int hour = calendar.get(Calendar.HOUR);
				int minute = calendar.get(Calendar.MINUTE);
				int second = calendar.get(Calendar.SECOND);
				if (calendar.get(Calendar.AM_PM) == 0)
					am_pm = "AM";
				else
					am_pm = "PM";
				String CT = String.format("%02d", hour) + ":"
						+ String.format("%02d", minute) + ":"
						+ String.format("%02d", second) + " " + am_pm;
				out.println("Current Time: " + CT + "\n");
			%>
		</center>
	</body>
</html>