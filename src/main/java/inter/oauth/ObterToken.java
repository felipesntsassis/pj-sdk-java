package inter.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.RespostaObterToken;
import inter.utils.HttpUtils;
import inter.utils.SslUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static inter.constants.Constants.URL_TOKEN;

@Slf4j
public class ObterToken {
    public RespostaObterToken obter(Config config, String escopos) throws IOException, SdkException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        log.info("ObterToken {} {}", config.getClientId(), escopos);
        BasicHttpClientConnectionManager connectionManager = SslUtils.buildConnectionManager(config.getCertificado(), config.getSenha());
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        String url = URL_TOKEN.replace("AMBIENTE", config.getAmbiente());
        HttpPost request = new HttpPost(url);
        List<NameValuePair> prms = new ArrayList<>();
        prms.add(new BasicNameValuePair("client_id", config.getClientId()));
        prms.add(new BasicNameValuePair("client_secret", config.getClientSecret()));
        prms.add(new BasicNameValuePair("grant_type", "client_credentials"));
        prms.add(new BasicNameValuePair("scope", escopos));
        request.setEntity(new UrlEncodedFormEntity(prms, StandardCharsets.UTF_8));
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        CloseableHttpResponse response = httpClient.execute(request);
        HttpUtils.handleResponse(url, response, "Erro ao obter token", config.isControleRateLimit());
        HttpEntity body = response.getEntity();
        String json = EntityUtils.toString(body, "UTF-8");
        RespostaObterToken respostaToken = new ObjectMapper().readValue(json, RespostaObterToken.class);
        respostaToken.setCreatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        return respostaToken;
    }
}
