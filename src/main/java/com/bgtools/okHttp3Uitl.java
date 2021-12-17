package com.bgtools;

import okhttp3.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class okHttp3Uitl extends Exception {
    static OkHttpClient okHttpClient = new OkHttpClient();

    public static String formPostSkipSSL(String url, Map<String, String> reqMap) {
        return okHttp3Post(url, reqMap, "", true);
    }

    public static String okHttp3Post(String url, Map<String, String> reqMap, String reqType, boolean skipSSL) {
        String respBody = "";
        try {
            if (!reqMap.isEmpty()) {
                okHttpClient.newBuilder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS)
                        .build();
                List<String> keys = new ArrayList<String>(reqMap.keySet());
                FormBody.Builder builder = new FormBody.Builder();
                for (String key : keys) {
                    builder.add(key, reqMap.get(key));
                }
                RequestBody body = builder.build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                Call call = okHttpClient.newCall(request);
                Response response = call.execute();
                if (200 == response.code()) {
                    respBody = response.body().string();
                } else {
                    respBody = "連線異常！";
                }

            }
        } catch (SocketTimeoutException e2) {
            respBody = "連線異常！";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respBody;
    }

    public static String okHttp3Get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            String res = okHttpClient.newCall(request).execute().body().string();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void main(String[] args) throws Exception {
        String url = "http://34.96.165.158/pay/jsp/getServerStatus.jsp";
        Map<String, String> reqMap = new HashMap<String, String>();
        reqMap.put("method", "1");
        reqMap.put("auth", "Xup6ru4aup6@bofa");

        String str = formPostSkipSSL(url, reqMap);
        System.out.println("str = " + str);
    }
}
