package com.p.control.cron;
//package com.tacteonltd.control.cron;
//
//import java.util.StringTokenizer;
//
//import com.tacteonltd.control.string.STRING;
//import com.tacteonltd.control.time.TIME;
//
//public class CRON {
//    public static String humanReadable(String value){
//        StringBuffer sb = new StringBuffer();
//        try{
//            StringTokenizer tokens = new StringTokenizer(value, " ", false);
//            humanizeSeconds(tokens.nextToken(), sb);sb.append("\n");
//            humanizeMinutes(tokens.nextToken(), sb);sb.append("\n");
//            humanizeHours(tokens.nextToken(), sb);sb.append("\n");
//            String dom = tokens.nextToken();
//            String month = tokens.nextToken();
//            String dow = tokens.nextToken();
//            humanizeDOMs(dom, dow, sb);sb.append("\n");
//            humanizeMonths(month, sb);sb.append("\n");
//            humanizeDOWs(dow, dom, sb);
//        }catch(Throwable t){
//            t.printStackTrace();
//        }
//        return sb.toString();
//    }
//    private static void humanizeDOWs(String value, String dom, StringBuffer sb){
//        value=value.trim();
//        if(STRING.isNumeric(value)){
//            sb.append("and the " + value + postFix(value) + " day-of-the-week("+TIME.getWeekDayName(Integer.parseInt(value))+"). "); 
//        }
//        else if(value.equals("*"))
//        {
//            sb.append("and every day-of-the-week. ");
//        }
//        else if(value.equals("?"))
//        {
//            sb.append("and whatever day-of-the-week it is for day"+(STRING.isNumeric(dom) ? "":"s")+"-of-the-month :"+(STRING.isNumeric(dom) ? dom:"that match"+(STRING.isNumeric(dom) ? "es":"")+" " + dom + "."));
//        }
//        else if(value.indexOf("/")>-1){
//            String first = value.substring(0, value.indexOf("/"));
//            String every = value.substring(value.indexOf("/") + 1);
//            sb.append("and the " + first + postFix(first) + " day-of-the-week and every " + every + " day" + (Integer.parseInt(every) == 1 ? "":"s") + " following. "); 
//        }
//        else if(value.indexOf(",")>-1){
//            StringTokenizer tokens = new StringTokenizer(value, ",", false);
//            sb.append("and the following days of the week: ");
//            int added = 0;
//            while(tokens.hasMoreTokens()){
//                String token = tokens.nextToken();
//                sb.append((added==0 ? "":", ") + token);added++;
//            }
//            sb.append(".");
//        }
//        else if(value.indexOf("-")>-1){
//            StringTokenizer tokens = new StringTokenizer(value, "-", false);
//            sb.append("and for every day of the week from " + tokens.nextToken() + " through " + tokens.nextToken() + ". ");
//        }
//        else {
//            sb.append("and the following days of the week: " + value + ".");
//        }
//    }
//    private static void humanizeMonths(String value, StringBuffer sb){
//        value=value.trim();
//        if(STRING.isNumeric(value)){
//            sb.append("the " + value + postFix(value) + " month("+TIME.getMonthName(Integer.parseInt(value))+"), "); 
//        }
//        else if(value.equals("*"))
//        {
//            sb.append("every month, ");
//        }
//        else if(value.indexOf("/")>-1){
//            String first = value.substring(0, value.indexOf("/"));
//            String every = value.substring(value.indexOf("/") + 1);
//            sb.append("the " + first + postFix(first) + " month("+TIME.getMonthName(Integer.parseInt(value))+") and every " + every + " month" + (Integer.parseInt(every) == 1 ? "":"s") + " following, "); 
//        }
//        else if(value.indexOf(",")>-1){
//            StringTokenizer tokens = new StringTokenizer(value, ",", false);
//            sb.append("the following months: ");
//            int added = 0;
//            while(tokens.hasMoreTokens()){
//                sb.append((added==0 ? "":", ") + tokens.nextToken());added++;
//            }
//        }
//    }
//    private static void humanizeDOMs(String value, String dow, StringBuffer sb){
//        value=value.trim();
//        if(STRING.isNumeric(value)){
//            sb.append("the " + value + postFix(value) + " day-of-the-month, "); 
//        }
//        else if(value.equals("*"))
//        {
//            sb.append("every day-of-the-month, ");
//        }
//        else if(value.equals("?"))
//        {
//            sb.append("whatever day-of-the-month falls upon " + dow + " , ");
//        }
//        else if(value.indexOf("-")>-1){
//            StringTokenizer tokens = new StringTokenizer(value, "-", false);
//            String from = tokens.nextToken();
//            String through = tokens.nextToken();
//            int year = TIME.getYear(System.currentTimeMillis());
//            int month = TIME.getMonth(System.currentTimeMillis());
//            String last_dom = "" + TIME.getLastDOM(year, month);
//            sb.append("for every day of the month from " + from + " through " + (through.equals("L") ? "the last("+year +"." +month + "."+last_dom+")":through) );
//        }
//        else if(value.indexOf("/")>-1){
//            String first = value.substring(0, value.indexOf("/"));
//            String every = value.substring(value.indexOf("/") + 1);
//            sb.append("the " + first + postFix(first) + " day-of-the-month and every " + every + " day" + (Integer.parseInt(every) == 1 ? "":"s") + " following, "); 
//        }
//        else if(value.indexOf(",")>-1){
//            StringTokenizer tokens = new StringTokenizer(value, ",", false);
//            sb.append("the following days of the month: ");
//            int added = 0;
//            while(tokens.hasMoreTokens()){
//                String token = tokens.nextToken();
//                if(token.equals("L")){
//                    int year = TIME.getYear(System.currentTimeMillis());
//                    int month = TIME.getMonth(System.currentTimeMillis());
//                    String last_dom = "" + TIME.getLastDOM(year, month);
//                    sb.append((added==0 ? "":", ") + "the last("+year +"." +month + "."+last_dom+")");
//                }
//                else{
//                    sb.append((added==0 ? "":", ") + token);added++;
//                }
//            }
//        }
//    }
//    private static void humanizeSeconds(String value, StringBuffer sb){
//        value=value.trim();
//        if(STRING.isNumeric(value)){
//            sb.append("on the " + value + postFix(value) + " second, "); 
//        }
//        else if(value.equals("*"))
//        {
//            sb.append("on every second, ");
//        }
//        else if(value.indexOf("/")>-1){
//            String first = value.substring(0, value.indexOf("/"));
//            String every = value.substring(value.indexOf("/") + 1);
//            sb.append("on the " + first + postFix(first) + " second and every " + every + " second" + (Integer.parseInt(every) == 1 ? "":"s") + " following, "); 
//        }
//        else if(value.indexOf("-")>-1){
//            StringTokenizer tokens = new StringTokenizer(value, "-", false);
//            sb.append("for every second from " + tokens.nextToken() + " through " + tokens.nextToken() + " ");
//        }
//        else if(value.indexOf(",")>-1){
//            StringTokenizer tokens = new StringTokenizer(value, ",", false);
//            sb.append("on the following seconds: ");
//            int added = 0;
//            while(tokens.hasMoreTokens()){
//                sb.append((added==0 ? "":", ") + tokens.nextToken());added++;
//            }
//        }
//    }
//    private static void humanizeMinutes(String value, StringBuffer sb){
//        value=value.trim();
//        if(STRING.isNumeric(value)){
//            sb.append("the " + value + postFix(value) + " minute, "); 
//        }
//        else if(value.equals("*"))
//        {
//            sb.append("every minute, ");
//        }
//        else if(value.indexOf("/")>-1){
//            String first = value.substring(0, value.indexOf("/"));
//            String every = value.substring(value.indexOf("/") + 1);
//            sb.append("the " + first + postFix(first) + " minute and every " + every + " minute" + (Integer.parseInt(every) == 1 ? "":"s") + " following, "); 
//        }
//        else if(value.indexOf("-")>-1){
//            StringTokenizer tokens = new StringTokenizer(value, "-", false);
//            sb.append("for every minute from " + tokens.nextToken() + " through " + tokens.nextToken() + " ");
//        }
//        else if(value.indexOf(",")>-1){
//            StringTokenizer tokens = new StringTokenizer(value, ",", false);
//            sb.append("the following minutes: ");
//            int added = 0;
//            while(tokens.hasMoreTokens()){
//                sb.append((added==0 ? "":", ") + tokens.nextToken());added++;
//            }
//        }
//    }
//    private static void humanizeHours(String value, StringBuffer sb){
//        value=value.trim();
//        if(STRING.isNumeric(value)){
//            sb.append("the " + value + postFix(value) + " hour, "); 
//        }
//        else if(value.equals("*"))
//        {
//            sb.append("every hour, ");
//        }
//        else if(value.indexOf("/")>-1){
//            String first = value.substring(0, value.indexOf("/"));
//            String every = value.substring(value.indexOf("/") + 1);
//            sb.append("the " + first + postFix(first) + " hour and every " + every + " hour" + (Integer.parseInt(every) == 1 ? "":"s") + " following, "); 
//        }
//        else if(value.indexOf("-")>-1){
//            StringTokenizer tokens = new StringTokenizer(value, "-", false);
//            sb.append("for every hour from " + tokens.nextToken() + " through " + tokens.nextToken() + " ");
//        }
//        else if(value.indexOf(",")>-1){
//            StringTokenizer tokens = new StringTokenizer(value, ",", false);
//            sb.append("the following hours: ");
//            int added = 0;
//            while(tokens.hasMoreTokens()){
//                sb.append((added==0 ? "":", ") + tokens.nextToken());added++;
//            }
//        }
//    }
//    private static String postFix(String value){
//        String post = null; 
//            if(value.substring(value.length()-1).equals("1"))post = "st";
//            else if(value.substring(value.length()-1).equals("2"))post = "nd";
//            else if(value.substring(value.length()-1).equals("3"))post = "rd";
//            else post="th";
//        return post;
//    }
//}