package com.kibou;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientTest {

	public final static String htttPost(String url, String parameters) {
		URI uri;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e1) {
			throw new RuntimeException(e1);
		}
		HttpClient httpClient = buildClient("https".equals(uri.getScheme()));
		StringBuffer result = new StringBuffer();
		long start = System.currentTimeMillis();
		try {
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json;charset=UTF-8");
			post.setEntity(new StringEntity(parameters, "UTF-8"));
			// HttpEntity entity = post.getEntity();
			// logger.info("[{}]post to {} with parameters:{}", start, url,
			// parameters);
			HttpResponse response = httpClient.execute(post);
			long end = System.currentTimeMillis();
			int status = response.getStatusLine().getStatusCode();
			// logger.info("[{}]cost time:{}ms response code:{}", start, (end -
			// start), status);
			// if (200 != status) {
			// throw new FailedRequestException("Http Status：" + status);
			// }
			BufferedReader bReader = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent(), "UTF8"));
			String line = "";
			while ((line = bReader.readLine()) != null) {
				result.append(line);
			}
			// logger.info("[{}]response content:{}", start, result.toString());
		} catch (IOException e) {
			// logger.error("[{}]htttPost() error: {}", start, e);
			throw new RuntimeException(e.getMessage(), e.getCause());
		}
		return result.toString();
	}

	/**
	 * Create a httpClient instance
	 * 
	 * @param isSSL
	 * @return HttpClient instance
	 */
	final static HttpClient buildClient(boolean isSSL) {
		HttpClientBuilder builder = HttpClientBuilder.create();
		if (isSSL) {
			X509TrustManager xtm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			try {
				SSLContext ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);

				ctx.init(null, new TrustManager[] { xtm }, null);

				// SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
				SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx);
				builder.setSSLSocketFactory(socketFactory);

				// httpClient.getConnectionManager().getSchemeRegistry().register(new
				// Scheme("https", 443, socketFactory));

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		HttpClient httpClient = builder.build();

		return httpClient;
	}
}
