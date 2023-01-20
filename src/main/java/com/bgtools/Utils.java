package com.bgtools;

import javafx.scene.control.Alert;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import net.sf.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static com.bgtools.DateUtil.*;

public class Utils {

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
            copyStr += TextPara.sudoStr;
        }
        if (folder != null) {
            copyStr += str + folder + "\n";
        } else {
            copyStr += str + "\n";
        }

        content.putString(copyStr);
        Clipboard.getSystemClipboard().setContent(content);

        if (!otherStr)
            showaAlert("提示", title + "已複製至剪貼簿", Alert.AlertType.INFORMATION);
        else
            showaAlert("提示", title, Alert.AlertType.INFORMATION);

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
                    showaAlert("提示", "转换内容不得为空", Alert.AlertType.ERROR);
                } else if ("".equalsIgnoreCase(dfName)) {
                    showaAlert("提示", "代付名稱不得为空", Alert.AlertType.ERROR);
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
            showaAlert("提示", "请检查轉換内容", Alert.AlertType.ERROR);
        }
        return postMap;
    }

    public static String setLogStr(Boolean isSudo, String date, String keyword) {
        return setLogStr(isSudo, date, keyword, "0");
    }

    public static String setLogStr(Boolean isSudo, String date, String keyword, String col) {
        String lsStr = "";
        TextPara.setFindLogStr(keyword);
        if (isSudo)
            lsStr = TextPara.sudoStr + TextPara.getFindLogStr();
        else lsStr = TextPara.getFindLogStr();
        return lsStr;
    }


    public static Map<String, Object> hostData(String auth, String method) {
        Map<String, Object> postMap = new HashMap<String, Object>();
        postMap.put("auth", auth);
        postMap.put("method", method);
        return postMap;
    }

    public static String hostStr(String bodyStr, String hostIp, String type) {
        String apenString = "";
        String serverStr = "Tomcat 服務 ：";
        String redisStr = "Redis 服務 ：";
        try {
            if (!"".equals(bodyStr)) {
                JSONObject jsObj = JSONObject.fromObject(bodyStr);

                if (jsObj.optBoolean("serverStatus"))
                    serverStr += "正常";
                else
                    serverStr += "異常";

                if (jsObj.optBoolean("redisStauts"))
                    redisStr += "正常";
                else
                    redisStr += "異常";
                apenString =
                        "测试主机IP ：" + hostIp + "\n"
                                + serverStr + "\n"
                                + redisStr + "\n";
            } else {
                String serverStatus = OkHttp3Util.formPost("http://" + hostIp + "/" + type + "/index.jsp", new HashMap<>());
                if (serverStatus.contains("1")) {
                    serverStr += "正常";
                    apenString =
                            "测试主机IP ：" + hostIp + "\n"
                                    + serverStr + "\n";
                } else {
                    apenString = "連線異常，請檢查主機IP是否正確！";
                }
            }

        } catch (Exception e) {
            apenString = bodyStr;
        }

        return apenString;
    }

    public static String hostText(String hostIp, String masterAuth, String method, String type) {
        String showStr = "";

        showStr = Utils.hostStr(
                OkHttp3Util.formPost("http://" + hostIp + "/pay/jsp/getServerStatus.jsp", Utils.hostData(masterAuth, method))
                , hostIp, type);
        return showStr;

    }

    public static String getHostIp(String hostUrl) {
        String url = "http://ip-api.com/json/";
        if (hostUrl.contains("/")) {
            hostUrl = hostUrl.replaceAll("https://", "").replaceAll("http://", "");
            hostUrl = hostUrl.substring(0, hostUrl.indexOf("/"));
        }
        String HostIP = "";
        String bodyStr = OkHttp3Util.okHttp3Get(url + hostUrl);
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

    public static String AESDecrypt(String secretKey, String secretIv, String message) {
        String result = "";
        try {
            if (secretKey.length() > 15) secretKey = secretKey.substring(0, 16);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes("utf-8"), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(secretIv.getBytes("utf-8"));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] requestMessageBytes = Base64.decode(message);
            byte[] plainText = cipher.doFinal(requestMessageBytes);
            result = new String(plainText);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        } catch (NoSuchPaddingException e) {
            System.err.println(e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            System.err.println(e.getMessage());
        } catch (BadPaddingException e) {
            System.err.println(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println(e.getMessage());
        } catch (InvalidKeyException e) {
            System.err.println(e.getMessage());
        } catch (IllegalBlockSizeException e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    public static String MD5Sign(String data) {
        try {
            if (data == null) {
                return null;
            }
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data.getBytes("UTF-8"));
            byte[] digest = md5.digest();

            StringBuffer hexString = new StringBuffer();
            String strTemp;
            for (int i = 0; i < digest.length; i++) {
                strTemp = Integer.toHexString((digest[i] & 0x000000FF) | 0xFFFFFF00).substring(6);
                hexString.append(strTemp);
            }
            System.out.println("MD5 前：" + data);
            System.out.println("MD5 后：" + hexString.toString().substring(0, 16));

            return hexString.toString().substring(0, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void main(String[] args) throws Exception {

    }


}
