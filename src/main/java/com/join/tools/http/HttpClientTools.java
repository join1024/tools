/**
 * 
 */

package com.join.tools;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * 
 * HttpClientTools
 * 
 * @author join
 *
 */
public class HttpClientTools {

    private final static String defaultCharset = Consts.UTF_8.displayName();

    private final static String MIME_TEXT = "text/plain";

    private static CloseableHttpClient httpClient;

    /**
     * 取HTTPCLIENT
     * 
     * @return
     */
    private synchronized static CloseableHttpClient getHttpClient() {
        if (null == httpClient) {
            RequestConfig requestConfig =
                RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(60000).build();
            ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();


            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[] {new X509TrustManager() {

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {

                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {

                    }
                }}, new SecureRandom());
            } catch (Exception e) {
                e.printStackTrace();
            }

            SSLConnectionSocketFactory sslsf =
                new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            Registry<ConnectionSocketFactory> r =
                RegistryBuilder.<ConnectionSocketFactory>create().register("http", plainsf).register("https", sslsf).build();


            HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
            httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).setConnectionManager(cm).build();


        }

        return httpClient;

    }

    /**
     * 发送get请求
     * 
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String doGet(String url) throws IOException {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        String retstr = null;
        HttpGet get = new HttpGet(url);

        try (CloseableHttpResponse response = getHttpClient().execute(get)) {
            HttpEntity entity = response.getEntity();

            if (null != entity) {
                retstr = EntityUtils.toString(entity, defaultCharset);
            }
            EntityUtils.consume(entity);
        }
        return retstr;
    }

    public static ByteResponse doGetToByte(String url) throws IOException {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        ByteResponse br=null;
        HttpGet get = new HttpGet(url);

        int tout=500000;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(tout).setConnectionRequestTimeout(tout)
                .setSocketTimeout(tout).build();
        get.setConfig(requestConfig);

        try (CloseableHttpResponse response = getHttpClient().execute(get)) {
            HttpEntity entity = response.getEntity();

            if (null != entity) {
                String contentType=entity.getContentType()!=null ? entity.getContentType().getValue();
                br=new ByteResponse(contentType,entity.getContentLength(),EntityUtils.toByteArray(entity));
            }
            EntityUtils.consume(entity);
        }
        return br;
    }

    /**
     * 下载
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static byte[] doGetDownload(String url) throws IOException {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        HttpGet get = new HttpGet(url);

        try (CloseableHttpResponse response = getHttpClient().execute(get)) {
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }

            return EntityUtils.toByteArray(entity);
        }

    }

}
