package com.fun.likechat.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HttpClient {
    public static final String CHARACTER_ENCODING = "UTF-8";
    public static final String CHARACTER_GBK = "GBK";

    /**
     * post 请求，返回String的数据包
     * 
     * @param url
     * @param requestData
     * @return
     * @throws IOException
     */
    public static String postRequestGBK(String url, String requestData) throws IOException {
        return postRequestGBK(url, requestData.getBytes(CHARACTER_GBK), null);
    }

    /**
     * post 请求，返回String的数据包
     * 
     * @param url
     * @param requestData
     * @return
     * @throws IOException
     */
    public static String postRequest(String url, String requestData) throws IOException {
        return postRequest(url, requestData.getBytes(CHARACTER_ENCODING), null);
    }

    /**
     * post 请求，返回String的状态吗
     * 
     * @param url
     * @param requestData
     * @return
     * @throws IOException
     */
    public static String postRequestReturnCode(String url, String requestData) throws IOException {
        return Integer.toString(postRequestReturnCode(url,
                requestData.getBytes(CHARACTER_ENCODING), null));
    }

    /**
     * HTTPS 请求，返回String的数据包
     * 
     * @param url
     * @param requestData
     * @return
     * @throws IOException
     */
    public static String httpsPostRequest(String url, String requestData) throws Exception {
        return httpsPostRequest(url,requestData.getBytes(CHARACTER_ENCODING), null);
    }

    /**
     * post请求并返回数据包
     * 
     * @param url
     *            请求url
     * @param requestData
     *            请求数据
     * @param requestProperties
     *            请求包体
     * @return byte[] 数据包
     * @throws IOException
     */
    public static String postRequest(String url, byte[] requestData, Properties requestProperties)
            throws IOException {
        HttpURLConnection httpConn = null;
        StringBuffer sBuffer = new StringBuffer("");
        try {
            httpConn = (HttpURLConnection) new URL(url).openConnection();
            // 封住包体
            if (requestProperties != null) {
            }
            String length = "0";
            if (requestData != null) {
                length = Integer.toString(requestData.length);
            }
            httpConn.setConnectTimeout(15000);
            httpConn.setRequestMethod("POST");
//            httpConn.setRequestProperty("Content-type",
//                    "application/x-www-form-urlencoded;text/xml;charset=UTF-8");
            httpConn.setRequestProperty("Content-type",
                    "multipart/form-data;charset=UTF-8");
            httpConn.setRequestProperty("Connection", "close");
            httpConn.setRequestProperty("Content-Length", length);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            OutputStream outStream = httpConn.getOutputStream();
            outStream.write(requestData);
            outStream.flush();
            outStream.close();
            BufferedReader in = null;
            String inputLine = null;
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),
                    CHARACTER_ENCODING));
            while ((inputLine = in.readLine()) != null) {
                sBuffer.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
                httpConn = null;
            }
        }

        return sBuffer.toString();
    }

    public static int postRequestReturnCode(String url, byte[] requestData,
            Properties requestProperties) throws IOException {
        HttpURLConnection httpConn = null;
        StringBuffer sBuffer = new StringBuffer("");
        int code = 9999;
        try {
            httpConn = (HttpURLConnection) new URL(url).openConnection();
            // 封住包体
            if (requestProperties != null) {

            }
            String length = "0";
            if (requestData != null) {
                length = Integer.toString(requestData.length);
            }
            httpConn.setConnectTimeout(15000);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Connection", "close");
            httpConn.setRequestProperty("Content-Length", length);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            OutputStream outStream = httpConn.getOutputStream();
            outStream.write(requestData);
            outStream.flush();
            outStream.close();
            BufferedReader in = null;
            String inputLine = null;
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),
                    CHARACTER_ENCODING));
            while ((inputLine = in.readLine()) != null) {
                sBuffer.append(inputLine);
            }
            in.close();
            code = httpConn.getResponseCode();
            if (isNumeric(sBuffer.toString())) {
                if ("200".equals(sBuffer.toString())) {
                    code = 200;
                } else {
                    code = 9999;
                }
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
                httpConn = null;
            }
        }
        return code;
    }

    public static String postRequestGBK(String url, byte[] requestData, Properties requestProperties)
            throws IOException {
        HttpURLConnection httpConn = null;
        StringBuffer sBuffer = new StringBuffer("");
        try {
            httpConn = (HttpURLConnection) new URL(url).openConnection();
            // 封住包体
            if (requestProperties != null) {
            }
            String length = "0";
            if (requestData != null) {
                length = Integer.toString(requestData.length);
            }
            httpConn.setConnectTimeout(15000);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Connection", "close");
            httpConn.setRequestProperty("Content-Length", length);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            OutputStream outStream = httpConn.getOutputStream();
            outStream.write(requestData);
            outStream.flush();
            outStream.close();
            BufferedReader in = null;
            String inputLine = null;
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), CHARACTER_GBK));
            while ((inputLine = in.readLine()) != null) {
                sBuffer.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
                httpConn = null;
            }
        }
        return sBuffer.toString();
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static String httpsPostRequest(String url, byte[] requestData,
            Properties requestProperties) throws Exception {
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                new java.security.SecureRandom());
        HttpsURLConnection httpsConn = null;
        StringBuffer sBuffer = new StringBuffer("");
        try {
            httpsConn = (HttpsURLConnection) new URL(url).openConnection();
            // 封住包体
            if (requestProperties != null) {

            }
            httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            httpsConn.setSSLSocketFactory(sc.getSocketFactory());
            httpsConn.setConnectTimeout(15000);
            httpsConn.setDoInput(true);
            httpsConn.setDoOutput(true);
            // OutputStream outStream = httpsConn.getOutputStream();
            BufferedOutputStream hurlBufOus = new BufferedOutputStream(httpsConn.getOutputStream());
            hurlBufOus.write(requestData);
            hurlBufOus.flush();
            hurlBufOus.close();
            httpsConn.connect();
            // System.out.println(httpsConn.getResponseCode());
            BufferedReader in = null;
            String inputLine = null;
            in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(),
                    CHARACTER_ENCODING));
            while ((inputLine = in.readLine()) != null) {
                sBuffer.append(inputLine);
            }
            in.close();
        } finally {
            if (httpsConn != null) {
                httpsConn.disconnect();
                httpsConn = null;
            }
        }
        return sBuffer.toString();
    }

    /**
     * post 请求，返回String的数据包
     * 
     * @param url
     * @param requestData
     * @return
     * @throws Exception
     */
    public static String postRequest4MakeCode(String url, String requestData) throws Exception {
        return postRequest4MakeCode(url, requestData.getBytes(CHARACTER_ENCODING), null);
    }

    /**
     * post请求并返回数据包
     * 
     * @param url
     *            请求url
     * @param requestData
     *            请求数据
     * @param requestProperties
     *            请求包体
     * @return byte[] 数据包
     * @throws IOException
     */
    public static String postRequest4MakeCode(String url, byte[] requestData,
            Properties requestProperties) throws IOException {
        HttpURLConnection httpConn = null;
        StringBuffer sBuffer = new StringBuffer("");
        try {
            httpConn = (HttpURLConnection) new URL(url).openConnection();
            // 封住包体
            if (requestProperties != null) {

            }
            String length = "0";
            if (requestData != null) {
                length = Integer.toString(requestData.length);
            }
            httpConn.setConnectTimeout(15000);
            httpConn.setReadTimeout(15000);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-type",
                    "application/x-www-form-urlencoded;text/xml;charset=UTF-8");

            httpConn.setRequestProperty("Connection", "close");
            httpConn.setRequestProperty("Content-Length", length);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            OutputStream outStream = httpConn.getOutputStream();
            outStream.write(requestData);
            outStream.flush();
            outStream.close();
            BufferedReader in = null;
            String inputLine = null;
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(),
                    CHARACTER_ENCODING));
            while ((inputLine = in.readLine()) != null) {
                sBuffer.append(inputLine);
            }
            in.close();

        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
                httpConn = null;
            }
        }
        return sBuffer.toString();
    }

    /**
     * 判断字符是否是数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (str.matches("\\d*")) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        //HttpClient.postRequest("http://192.168.20.27:8080/tasting_server/api/c_special_area", "");
       //HttpClient.postRequest("http://192.168.11.165:8080/tasting_server/api/c_special_area", "");
      // HttpClient.postRequest("http://localhost:8080/tasting_server/api/user/info/detail", "111");
        String b=URLEncoder.encode("验证码123456","GBK");
        //System.out.println(HttpClient.postRequest("http://3g.3gxcm.com/sms/push_mt.jsp?cpid=lanmifeng&cppwd=lanmifeng&phone=18910266103&spnumber=&msgcont="+b, ""));
       String aaString="aaaaa//////";
       
       System.out.println(URLEncoder.encode(aaString,"UTF-8"));
       System.out.println(URLDecoder.decode(b, "UTF-8"));
       ApplicationContext app = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
       RedisFactory redisFactory = (RedisFactory) app.getBean("redisFactory");
       String[][] aa=new String[][]{new String[]{"1","aa"},new String[]{"17","bb"},new String[]{"20","cc"}};
       redisFactory.set("11", "11");
    }

}