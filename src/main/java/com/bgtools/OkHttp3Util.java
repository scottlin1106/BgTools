package com.bgtools;

import net.sf.json.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Author: Scott
 * Date: 2022-07-27
 */

public class OkHttp3Util {

    public static String formPost(String url, Map<String, Object> postMap) {
        return formPost(url, postMap, null, 3, 3);
    }

    public static String formPost(String url, Map<String, Object> postMap, int connectTimeout) {
        return formPost(url, postMap, null, connectTimeout, connectTimeout);
    }

    public static String formPost(String url, Map<String, Object> postMap, int connectTimeout, int readTimeout) {
        return formPost(url, postMap, null, connectTimeout, readTimeout);
    }

    public static String formPostWithHeader(String url, Map<String, Object> postMap, Map<String, Object> headerMap) {
        return formPost(url, postMap, headerMap, 3, 3);
    }

    public static String formPostWithHeader(String url, Map<String, Object> postMap, Map<String, Object> headerMap, int connectTimeout) {
        return formPost(url, postMap, headerMap, connectTimeout, 3);
    }

    public static String formPostWithHeader(String url, Map<String, Object> postMap, Map<String, Object> headerMap, int connectTimeout, int readTimeout) {
        return formPost(url, postMap, headerMap, connectTimeout, readTimeout);
    }

    public static String formPost(String url, Map<String, Object> postMap, Map<String, Object> headerMap, int connectTimeout, int readTimeout) {
        String body = "";
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    .build();

            FormBody.Builder builder = new FormBody.Builder();
            if (postMap != null) {
                for (String key : postMap.keySet()) {
                    builder.add(key, (String) postMap.get(key));
                }
            }
            RequestBody formBody = builder.build();
            Headers.Builder headers = new Headers.Builder();
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    headers.add(key, (String) headerMap.get(key));
                }
            }
            Headers headers1 = headers.build();

            Request request = new Request.Builder()
                    .url(url)
                    .headers(headers1)
                    .post(formBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            body = response.body().string();

            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String jsonPost(String url, JSONObject postJson) {
        return jsonPost(url, postJson, null, 10, 10);
    }

    public static String jsonPost(String url, JSONObject postJson, int connectTimeout) {
        return jsonPost(url, postJson, null, connectTimeout, connectTimeout);
    }

    public static String jsonPost(String url, JSONObject postJson, int connectTimeout, int readTimeout) {
        return jsonPost(url, postJson, null, connectTimeout, readTimeout);
    }

    public static String jsonPostWithHeader(String url, JSONObject postJson, Map<String, Object> headerMap) {
        return jsonPost(url, postJson, headerMap, 10, 10);
    }

    public static String jsonPostWithHeader(String url, JSONObject postJson, Map<String, Object> headerMap, int connectTimeout) {
        return jsonPost(url, postJson, headerMap, connectTimeout, 10);
    }

    public static String jsonPostWithHeader(String url, JSONObject postJson, Map<String, Object> headerMap, int connectTimeout, int readTimeout) {
        return jsonPost(url, postJson, headerMap, connectTimeout, readTimeout);
    }

    public static String jsonPost(String url, JSONObject postJson, Map<String, Object> headerMap, int connectTimeout, int readTimeout) {
        String body = "";
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    .build();

            RequestBody jsonBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postJson.toString());

            Headers.Builder headers = new Headers.Builder();
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    headers.add(key, (String) headerMap.get(key));
                }
            }
            Headers reqHeaders = headers.build();

            Request request = new Request.Builder()
                    .url(url)
                    .headers(reqHeaders)
                    .post(jsonBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            body = response.body().string();

            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String multipartPost(String url, Map<String, Object> postMap) {
        return multipartPost(url, postMap, null, 10, 10);
    }

    public static String multipartPost(String url, Map<String, Object> postMap, int connectTimeout) {
        return multipartPost(url, postMap, null, connectTimeout, connectTimeout);
    }

    public static String multipartPost(String url, Map<String, Object> postMap, int connectTimeout, int readTimeout) {
        return multipartPost(url, postMap, null, connectTimeout, readTimeout);
    }

    public static String multipartPostWithHeader(String url, Map<String, Object> postMap, Map<String, Object> headerMap) {
        return multipartPost(url, postMap, headerMap, 10, 10);
    }

    public static String multipartPostWithHeader(String url, Map<String, Object> postMap, Map<String, Object> headerMap, int connectTimeout) {
        return multipartPost(url, postMap, headerMap, connectTimeout, 10);
    }

    public static String multipartPostWithHeader(String url, Map<String, Object> postMap, Map<String, Object> headerMap, int connectTimeout, int readTimeout) {
        return multipartPost(url, postMap, headerMap, connectTimeout, readTimeout);
    }

    public static String multipartPost(String url, Map<String, Object> postMap, Map<String, Object> headerMap, int connectTimeout, int readTimeout) {
        String body = "";
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    .build();

            MultipartBody.Builder multipart = new MultipartBody.Builder("MyBoundary");
            if (postMap != null) {
                for (String key : postMap.keySet()) {
                    multipart.addFormDataPart(key, (String) postMap.get(key));
                }
            }
            RequestBody multipartBody = multipart.build();

            Headers.Builder headers = new Headers.Builder();
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    headers.add(key, (String) headerMap.get(key));
                }
            }
            Headers reqHeaders = headers.build();

            Request request = new Request.Builder()
                    .url(url)
                    .headers(reqHeaders)
                    .post(multipartBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            body = response.body().string();

            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String okHttp3Get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();

            String res = okHttpClient.newCall(request).execute().body().string();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        String url = "http://35.220.204.236/pay/index.jsp";
//        String url = "http://pay.dxin.xyz/api/pay/create_order";
        String response = Utils.getHostIp("zhmff-top.bananavalue.com");
        System.out.println("[ response ] :" + response);
    }
}
