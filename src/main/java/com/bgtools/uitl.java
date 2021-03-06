package com.bgtools;

import javafx.scene.control.Alert;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class uitl {
    public static String getLogDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.toString();
    }


    public static Boolean validateDate(String day) {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.parse(day);
        long day1 = now.toEpochDay();
        long day2 = date.toEpochDay();

        if (Math.abs(day2 - day1) > 7) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean checkDate(String sourceDate) {
        if (sourceDate == null) {
            return false;
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(sourceDate);
            return true;
        } catch (Exception e) {
        }
        return false;

    }

    public static void copyStr(String str, String title) {
        copyStr(str, title, null, false, false);
    }

    public static void copyStr(String str, String title, boolean sudo, Boolean otherStr) {
        copyStr(str, title, null, sudo, otherStr);
    }

    public static void copyStr(String str, String title, String folderName) {
        copyStr(str, title, folderName, false, false);
    }

    public static void copyStr(String str, String title, Boolean sudo) {
        copyStr(str, title, null, sudo, false);
    }

    public static void copyStr(String str, String title, String folder, Boolean sudo) {
        copyStr(str, title, folder, sudo, false);
    }

    public static void copyStr(String str, String title, String folder, Boolean sudo, Boolean otherStr) {
        final ClipboardContent content = new ClipboardContent();
        String copyStr = "";
        if (sudo) {
            copyStr += textPara.sudoStr;
        }
        if (folder != null) {
            copyStr += str + folder + "\n";
        } else {
            copyStr += str + "\n";
        }

        content.putString(copyStr);
        Clipboard.getSystemClipboard().setContent(content);

        if (!otherStr)
            showaAlert("??????", title + "?????????????????????", Alert.AlertType.INFORMATION);
        else
            showaAlert("??????", title, Alert.AlertType.INFORMATION);

    }

    public static Map<String, String> result(String bodystr) {
        Map<String, String> resultMap = new LinkedHashMap<String, String>();
        bodystr = bodystr.replaceAll(" ", "");
        String[] token = bodystr.split(",");
        for (int i = 0; i < token.length; i++) {
            if (token[i].split("=").length > 1) {
                resultMap.put(token[i].split("=")[0], token[i].substring(token[i].indexOf("=") + 1));
            } else {
                resultMap.put(token[i].split("=")[0], "");
            }
        }
        return resultMap;
    }

    public static Map<String, Object> dfOutStr(String inStr, String dfName) {
        Map<String, Object> postMap = new HashMap<String, Object>();

        try {
            if (inStr.contains(", ") && inStr.contains("=")) {
                Map<String, String> extMap = result(inStr);
                if (inStr.contains("ProviderCode")) {
                    String ProviderCode = (String) extMap.get("ProviderCode");
                    dfName = ProviderCode.replaceAll(".sign", "");
                    postMap.put("payname", dfName);
                }
                if ("".equalsIgnoreCase(inStr)) {
                    showaAlert("??????", "????????????????????????", Alert.AlertType.ERROR);
                } else if ("".equalsIgnoreCase(dfName)) {
                    showaAlert("??????", "????????????????????????", Alert.AlertType.ERROR);
                } else {
                    List<String> list = new ArrayList();
                    list.add("sn");
                    list.add("payId");
                    list.add("withdrawId");
                    list.add("realAmount");
                    list.add("operatorId");
                    list.add("digest");
                    list.add("bankName");
                    list.add("orderNo");

                    if (extMap.get("OrderAmount") != null && !"null".equalsIgnoreCase((String) extMap.get("OrderAmount")) && !"".equals(extMap.get("OrderAmount"))) {
                        extMap.put("realAmount", extMap.get("OrderAmount"));
                        extMap.remove("OrderAmount");
                    }

                    List<String> keys = new ArrayList<String>(extMap.keySet());
                    for (String str : list) {
                        for (String extStr : keys) {
                            if (str.equalsIgnoreCase(extStr)) {
                                postMap.put(str, extMap.get(extStr));
                            }
                        }
                    }
                }

            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            showaAlert("??????", "?????????????????????", Alert.AlertType.ERROR);
        }
        return postMap;
    }

    public static String setLogStr(String date, String keyword, String col, Boolean isUat) {

        String lsStr = textPara.SearchStr;
        if (!"".equals(keyword)) {
            if (isUat) {
                lsStr = textPara.SearchStr + " | grep -C " + col + " " + keyword;
            } else {
                if (isNumeric(keyword)) {
                    String day = keyword.substring(4, 6);
                    if (keyword.length() > 7 && Integer.parseInt(day) < 31) {
                        String year = "20" + keyword.substring(0, 2);
                        String month = keyword.substring(2, 4);
                        Integer hour = Integer.parseInt(keyword.substring(6, 8));
                        String vDate = year + "-" + month + "-" + day;
                        if (checkDate(vDate) && validateDate(vDate)) {
                            lsStr = textPara.SearchStr + "." + getLogDate(vDate) + ".log";
                        } else if (!validateDate(vDate)) {
                            showaAlert("??????", "??????????????????LOG????????????(??????)?????????????????????", Alert.AlertType.INFORMATION);
                            lsStr = "";
                        }

                    }
                } else {
                    lsStr = textPara.SearchStr + "." + date + ".log";
                }
                if (!"".equals(lsStr))
                    lsStr += " | grep -C " + col + " " + keyword;
            }
        } else if (!isUat) {
            lsStr = textPara.SearchStr + "." + date + ".log";
        }
        return lsStr;
    }

    public static Map<String, Object> hostData(String auth, String method) {
        Map<String, Object> postMap = new HashMap<String, Object>();
        postMap.put("auth", auth);
        postMap.put("method", method);
        return postMap;
    }

    public static String hostStr(String bodyStr, String hostIp,String type) {
        String apenString = "";
        String serverStr = "Tomcat ?????? ???";
        String redisStr = "Redis ?????? ???";
        try {
            if (!"".equals(bodyStr)) {
                JSONObject jsObj = JSONObject.fromObject(bodyStr);

                if (jsObj.optBoolean("serverStatus"))
                    serverStr += "??????";
                else
                    serverStr += "??????";

                if (jsObj.optBoolean("redisStauts"))
                    redisStr += "??????";
                else
                    redisStr += "??????";
                apenString =
                        "????????????IP ???" + hostIp + "\n"
                                + serverStr + "\n"
                                + redisStr + "\n";
            } else {
                String serverStatus = okHttp3Util.formPost("http://" + hostIp + "/"+type+"/index.jsp", new HashMap<>());
                if (serverStatus.contains("1")) {
                    serverStr += "??????";
                    apenString =
                            "????????????IP ???" + hostIp + "\n"
                                    + serverStr + "\n";
                } else {
                    apenString = "??????????????????????????????IP???????????????";
                }
            }

        } catch (Exception e) {
            apenString = bodyStr;
        }

        return apenString;
    }

    public static String hostText(String hostIp, String masterAuth, String method,String type) {
        String showStr = "";

        showStr = uitl.hostStr(
                okHttp3Util.formPost("http://" + hostIp + "/pay/jsp/getServerStatus.jsp", uitl.hostData(masterAuth, method))
                , hostIp,type);
        return showStr;

    }

    public static String getHostIp(String hostUrl) {
        String url = "http://ip-api.com/json/";
        if (hostUrl.contains("/")) {
            hostUrl = hostUrl.replaceAll("https://", "").replaceAll("http://", "");
            hostUrl = hostUrl.substring(0, hostUrl.indexOf("/"));
        }
        String HostIP = "";
        String bodyStr = okHttp3Util.okHttp3Get(url + hostUrl);
        HostIP = JSONObject.fromObject(bodyStr).optString("query");
        return HostIP;

    }

    public static void showaAlert(String title, String text, Alert.AlertType AlertType) {
        Alert alert = new Alert(AlertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.getDialogPane().getScene().getWindow();
        alert.showAndWait();
    }

    public static boolean isNumeric(String str) {
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static String nullToSpace(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    public static boolean isNullOrSpace(String str) {
        if (nullToSpace(str).equals("")) {
            return true;
        } else {
            return false;
        }
    }


    public static void main(String[] args) throws Exception {

    }


}
