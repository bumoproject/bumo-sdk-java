package io.bumo.crypto.http;


import io.bumo.encryption.utils.http.Verifier;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

public class HttpKit {

    private static final String DEFAULT_CHARSET = "UTF-8";
    public static boolean enableSSL = false;

    /**
     * 发送Get请求
     *
     * @param url
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws KeyManagementException
     */
    public static String get(String url, Boolean https) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, KeyManagementException {
        StringBuffer bufferRes = null;
        TrustManager[] tm = {new MyX509TrustManager()};
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象  
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        URL urlGet = new URL(url);
        HttpsURLConnection http = (HttpsURLConnection) urlGet.openConnection();
        // 连接超时
        http.setConnectTimeout(25000);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(25000);
        http.setRequestMethod("GET");
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setSSLSocketFactory(ssf);
        http.setHostnameVerifier(new Verifier());
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();

        InputStream in = http.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
        String valueString = null;
        bufferRes = new StringBuffer();
        while ((valueString = read.readLine()) != null) {
            bufferRes.append(valueString);
        }
        read.close();
        in.close();
        if (http != null) {
            // 关闭连接
            http.disconnect();
        }
        return bufferRes.toString();
    }

    /**
     * 发送Get请求
     *
     * @param url
     * @return
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws KeyManagementException
     */
    public static String get(String url) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, KeyManagementException {
        if (enableSSL) {
            return get(url, true);
        } else {
            StringBuffer bufferRes = null;
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            // 连接超时
            http.setConnectTimeout(25000);
            // 读取超时 --服务器响应比较慢，增大时间
            http.setReadTimeout(25000);
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();

            InputStream in = http.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
            String valueString = null;
            bufferRes = new StringBuffer();
            while ((valueString = read.readLine()) != null) {
                bufferRes.append(valueString);
            }
            read.close();
            in.close();
            if (http != null) {
                // 关闭连接
                http.disconnect();
            }
            return bufferRes.toString();
        }
    }

    /**
     * 发送Get请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static String get(String url, Map<String, String> params) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        return get(initParams(url, params));
    }

    /**
     * 发送Post请求
     *
     * @param url
     * @param params
     * @param https
     * @return
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static String post(String url, String params, Boolean https) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        StringBuffer bufferRes = null;
        TrustManager[] tm = {new MyX509TrustManager()};
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象  
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        URL urlGet = new URL(url);
        HttpsURLConnection http = (HttpsURLConnection) urlGet.openConnection();
        // 连接超时
        http.setConnectTimeout(50000);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(50000);
        http.setRequestMethod("POST");
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setSSLSocketFactory(ssf);
        http.setHostnameVerifier(new Verifier());
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();

        OutputStream out = http.getOutputStream();
        out.write(params.getBytes("UTF-8"));
        out.flush();
        out.close();

        InputStream in = http.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
        String valueString = null;
        bufferRes = new StringBuffer();
        while ((valueString = read.readLine()) != null) {
            bufferRes.append(valueString);
        }
        read.close();
        in.close();
        if (http != null) {
            // 关闭连接
            http.disconnect();
        }
        return bufferRes.toString();
    }

    /**
     * 发送Post请求
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static String post(String url, String params) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        if (enableSSL) {
            return post(url, params, true);
        } else {
            StringBuffer bufferRes = null;
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            // 连接超时
            http.setConnectTimeout(50000);
            // 读取超时 --服务器响应比较慢，增大时间
            http.setReadTimeout(50000);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();

            OutputStream out = http.getOutputStream();
            out.write(params.getBytes("UTF-8"));
            out.flush();
            out.close();

            InputStream in = http.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
            String valueString = null;
            bufferRes = new StringBuffer();
            while ((valueString = read.readLine()) != null) {
                bufferRes.append(valueString);
            }
            read.close();
            in.close();
            if (http != null) {
                // 关闭连接
                http.disconnect();
            }
            return bufferRes.toString();
        }
    }

    /**
     * 构造url
     *
     * @param url
     * @param params
     * @return
     */
    private static String initParams(String url, Map<String, String> params) {
        if (null == params || params.isEmpty()) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        if (url.indexOf("?") == -1) {
            sb.append("?");
        } else {
            sb.append("&");
        }
        boolean first = true;
        for (Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=");
            if (value == null || value.isEmpty()) {
                try {
                    sb.append(URLEncoder.encode(value, DEFAULT_CHARSET));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    public static void main(String[] args) throws Exception {
//    	String fname = "dsasdas.mp4";
//    	String s = fname.substring(0, fname.lastIndexOf("."));
//    	String f = fname.substring(s.length()+1);
//		System.out.println(f);
    }
}

/**
 * 证书管理
 */
class MyX509TrustManager implements X509TrustManager {

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }
}