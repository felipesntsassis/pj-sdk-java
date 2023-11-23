package inter.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.CertificadoException;
import inter.exceptions.ClientException;
import inter.exceptions.SdkException;
import inter.exceptions.ServerException;
import inter.model.Config;
import inter.model.Erro;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;

@Slf4j
public class HttpUtils {
    private static final int SLEEP = 60000;
    private static final int CLIENT_ERROR_BASE = 400;
    private static final int SERVER_ERROR_BASE = 500;
    private static final int TOO_MANY_REQUESTS = 429;
    private static final String APPLICATION_JSON = "application/json";
    private static String lastUrl;
    private static String lastRequest;

    private HttpUtils() {
    }

    public static String callGet(Config config, String url, String scope, String message) throws SdkException {
        log.info("http GET {}", url);
        HttpGet httpGet = new HttpGet(url);
        return call(config, httpGet, url, scope, message);
    }

    public static String callPut(Config config, String url, String scope, String message, String json) throws SdkException {
        log.info("http PUT {}", url);
        HttpPut httpPut = new HttpPut(url);
        httpPut.addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
        httpPut.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
        if (config.isDebug()) {
            log.info(json);
        }
        lastRequest = json;
        return call(config, httpPut, url, scope, message);
    }

    public static String callPatch(Config config, String url, String scope, String message, String json) throws SdkException {
        log.info("http PATCH {}", url);
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
        httpPatch.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
        if (config.isDebug()) {
            log.info(json);
        }
        lastRequest = json;
        return call(config, httpPatch, url, scope, message);
    }

    public static String callPost(Config config, String url, String scope, String message, String json) throws SdkException {
        log.info("http POST {}", url);
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
        httpPost.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
        if (config.isDebug()) {
            log.info(json);
        }
        lastRequest = json;
        return call(config, httpPost, url, scope, message);
    }

    public static String callDelete(Config config, String url, String scope, String message) throws SdkException {
        log.info("http DELETE {}", url);
        HttpDelete httpDelete = new HttpDelete(url);
        return call(config, httpDelete, url, scope, message);
    }

    private static String call(Config config, HttpRequestBase httpRequest, String url, String scope, String message) throws SdkException {

        try {
            lastUrl = url;
            String accessToken = TokenUtils.obter(config, scope);
            BasicHttpClientConnectionManager connectionManager = SslUtils.buildConnectionManager(config.getCertificado(), config.getSenha());
            CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
            httpRequest.addHeader("Authorization", "Bearer " + accessToken);
            if (config.getContaCorrente() != null) {
                httpRequest.addHeader("x-conta-corrente", config.getContaCorrente());
            }
            httpRequest.addHeader("x-inter-sdk", "java");
            httpRequest.addHeader("x-inter-sdk-version", "1.0.0");
            CloseableHttpResponse response = httpClient.execute(httpRequest);
            boolean retry = handleResponse(url, response, message, config.isControleRateLimit());
            if (retry) {
                try {
                    Thread.sleep(SLEEP);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return call(config, httpRequest, url, scope, message);
            }
            HttpEntity body = response.getEntity();
            String result = body != null ? EntityUtils.toString(body, StandardCharsets.UTF_8) : null;
            if (config.isDebug() && result != null) {
                log.info(result);
            }

            return result;

        } catch (NoSuchAlgorithmException | UnrecoverableKeyException |
                 CertificateException | KeyStoreException | KeyManagementException e) {
            log.error(CERTIFICATE_EXCEPTION_MESSAGE, e);
            throw new CertificadoException(
                    e.getMessage(),
                    Erro.builder()
                            .title(CERTIFICATE_EXCEPTION_MESSAGE).
                            detail(e.getMessage())
                            .build()
            );
        } catch (IOException ioException) {
            log.error(GENERIC_EXCEPTION_MESSAGE, ioException);
            throw new SdkException(
                    ioException.getMessage(),
                    Erro.builder()
                            .title(CERTIFICATE_EXCEPTION_MESSAGE).
                            detail(ioException.getMessage())
                            .build()
            );
        }
    }

    public static boolean handleResponse(String url, CloseableHttpResponse response, String message, boolean rateLimitControl) throws SdkException, IOException {
        log.info("http status={} {}", response.getStatusLine(), url);
        if (response.getStatusLine().getStatusCode() >= SERVER_ERROR_BASE) {
            HttpEntity body = response.getEntity();
            String json = EntityUtils.toString(body, StandardCharsets.UTF_8);
            ServerException e = new ServerException(message, json.isEmpty() ? Erro.builder().title(response.getStatusLine().toString()).build() : new ObjectMapper().readValue(json, Erro.class));
            logAndThrowException(e);
        } else if (response.getStatusLine().getStatusCode() >= CLIENT_ERROR_BASE) {
            if (response.getStatusLine().getStatusCode() == TOO_MANY_REQUESTS && rateLimitControl) {
                return true;
            }
            HttpEntity body = response.getEntity();
            String json = EntityUtils.toString(body, StandardCharsets.UTF_8);
            Erro erro;
            try {
                erro = json.isEmpty() ? Erro.builder().title(response.getStatusLine().toString()).build() : new ObjectMapper().readValue(json, Erro.class);
            } catch (JsonProcessingException e) {
                erro = Erro.builder().title(response.getStatusLine().toString()).build();
            }
            ClientException e = new ClientException(message, erro);
            logAndThrowException(e);
        }
        return false;
    }

    private static void logAndThrowException(SdkException e) throws SdkException {
        log.error(e.getErro().getTitle(), e);
        log.error(e.getErro().getDetail());
        if (e.getErro().getViolacoes() != null) {
            e.getErro().getViolacoes().forEach(v -> log.error(v.toString()));
        }
        throw e;
    }

    public static String getLastUrl() {
        return lastUrl;
    }

    public static String getLastRequest() {
        return lastRequest;
    }
}
