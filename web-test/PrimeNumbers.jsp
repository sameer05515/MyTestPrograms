<%@ page
	import="java.util.*,com.ist.serv.util.PrimeList,com.ist.serv.util.ServletUtilities"%>
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
				int numPrimes = ServletUtilities.getIntParameter(request,
						"numPrimes", 150);
				int numDigits = ServletUtilities.getIntParameter(request,
						"numDigits", 120);
				PrimeList primeList = findPrimeList(primeListVector, numPrimes,
						numDigits);
				if (primeList == null) {
					primeList = new PrimeList(numPrimes, numDigits, true);
					synchronized (primeListVector) {
						if (primeListVector.size() >= maxPrimeLists)
							primeListVector.removeElementAt(0);
						primeListVector.addElement(primeList);
					}
				}
				Vector currentPrimes = primeList.getPrimes();
				int numCurrentPrimes = currentPrimes.size();
				int numPrimesRemaining = (numPrimes - numCurrentPrimes);
				boolean isLastResult = (numPrimesRemaining == 0);
				if (!isLastResult) {
					response.setHeader("Refresh", "5");
				}
				response.setContentType("text/html");
				//PrintWriter out = response.getWriter();
				String title = "Some " + numDigits + "-Digit Prime Numbers";
				out.println(ServletUtilities.headWithTitle(title)
						+ "<BODY BGCOLOR=\"#FDF5E6\">\n" + "<H2 ALIGN=CENTER>"
						+ title + "</H2>\n" + "<H3>Primes found with " + numDigits
						+ " or more digits: " + numCurrentPrimes + ".</H3>");
				if (isLastResult)
					out.println("<B>Done searching.</B>");
				else
					out.println("<B>Still looking for " + numPrimesRemaining
							+ " more<BLINK>...</BLINK></B>");
				out.println("<OL>");
				for (int i = 0; i < numCurrentPrimes; i++) {
					out.println("  <LI>" + currentPrimes.elementAt(i));
				}
				out.println("</OL>");
			%>
			<%!private static Vector primeListVector = new Vector();
	private static int maxPrimeLists = 30;

	private PrimeList findPrimeList(Vector primeListVector, int numPrimes,
			int numDigits) {
		synchronized (primeListVector) {
			for (int i = 0; i < primeListVector.size(); i++) {
				PrimeList primes = (PrimeList) primeListVector.elementAt(i);
				if ((numPrimes == primes.numPrimes())
						&& (numDigits == primes.numDigits()))
					return (primes);
			}
			return (null);
		}
	}%>
		</center>
	</body>
</html>