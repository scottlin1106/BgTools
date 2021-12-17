package com.bgtools;

public class textPara {

    public static String postUrl, addrIp;
    public static String sudoStr = "sudo -i \n";
    public static String paymentStr = "/home/www/webapps/nfwApp/bg-payment/jsp/";
    public static String autoStr = "/home/www/webapps/nfwApp/bg-autopay/jsp/";
    public static String paymentClassesStr = "/home/www/webapps/nfwApp/bg-payment/WEB-INF/classes/";
    public static String autoPayClassesStr = "/home/www/webapps/nfwApp/bg-autopay/WEB-INF/classes/";
    public static String logStr = "tail -f /usr/local/tomcat702/logs/catalina.out";
    public static String SearchStr = "cat /usr/local/tomcat702/logs/catalina.out";
    public static String pstsStr = "cd /tmp/psts/\n cp -r contact.properties payname.properties /home/www/webapps/nfwApp/paystat/WEB-INF/classes/ \n";
    public static String dfSuccess = "autopay/jsp/pay8.jsp";
    public static String dfFailure = "autopay/jsp/pay11.jsp";
    public static String uatUrl = "http://out3rd.top.cxmyjd.com:6080/";
    public static String prdUrl = "http://www.yhsld.com/";
    public static String lsStr = "ls -lst ";


    public static void setIsPrd(boolean isPrd) {
        textPara.isPrd = isPrd;
    }

    public static boolean isPrd = false;


    public static boolean getIsUat() {
        return isUat;
    }

    public static void setIsUat(boolean isUat) {
        textPara.isUat = isUat;
    }

    public static boolean isUat = false;


    public static boolean getIsPrd() {
        return isPrd;
    }
}
