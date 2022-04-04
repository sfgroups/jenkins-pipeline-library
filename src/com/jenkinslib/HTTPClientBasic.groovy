
import groovy.json.JsonSlurper
import org.apache.http.HttpEntity
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.client.AuthCache
import org.apache.http.client.CredentialsProvider
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpRequestBase
import org.apache.http.client.protocol.HttpClientContext

import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.impl.auth.BasicScheme
import org.apache.http.impl.client.BasicAuthCache
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import sun.net.httpserver.Request
import utils.AuthInfo
import org.apache.http.util.EntityUtils

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.Executor

class HTTPClientBasic {
    def static API_BASE_URL = 'https://webwerver'
    def static API_URL = "${API_BASE_URL}/api/"
    def static hostname="localhost"

  def static main(args) {
        HTTPClientBasic obj = new HTTPClientBasic()
 
    }

    def CloseableHttpResponse MakeAPICall(HttpRequestBase httpobj) {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        httpobj.setHeader("Accept", "application/json")
        httpobj.setHeader("Content-type", "application/json")
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new SecureRandom());

        UsernamePasswordCredentials creds =new UsernamePasswordCredentials(AuthInfo.USERNAME, AuthInfo.PASSWORD)
        HttpHost targetHost = new HttpHost(hostname, 443, "https");
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                creds)
        credsProvider.setCredentials(AuthScope.ANY,creds);

        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);

        // Add AuthCache to the execution context
        HttpClientContext context = HttpClientContext.create();
        context.setCredentialsProvider(credsProvider);
        context.setAuthCache(authCache);

        CloseableHttpClient httpclient = HttpClientBuilder.create().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(sc).
                setDefaultCredentialsProvider(credsProvider).build()
        CloseableHttpResponse response = httpclient.execute(targetHost,httpobj, context)
        def responseCode = response.getStatusLine().getStatusCode()
        println("Response code ${responseCode}")
        switch (responseCode) {
            case 200:
            case 201: {
                break;
            }
            case 204: {
                break;
            }
            case 400: {
                throw new IOException("400 Bad Request : " + EntityUtils.toString(response.getEntity()))
                break
            }
            case 401: {
                throw new IOException("401 - Unauthorized: Access is denied due to invalid credentials." + EntityUtils.toString(response.getEntity()))
            }
            case 403: {
                throw new IOException("403 Forbidden, you have no authorization to access that resource : " + EntityUtils.toString(response.getEntity()))
                break
            }
            case 500: {
                throw new IOException("500 Internal error : " + EntityUtils.toString(response.getEntity()))
                break;
            }
        }
        return response
    }
}
