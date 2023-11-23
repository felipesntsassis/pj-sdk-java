package inter.utils;

import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.RespostaObterToken;
import inter.oauth.ObterToken;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    private TokenUtils() {
    }

    private static final int ADDITIONAL_TIME = 60;
    private static final Map<String, RespostaObterToken> TOKEN_MAP = new HashMap<>();

    public static String obter(Config config, String scope) throws SdkException, UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        RespostaObterToken respostaObterToken = getFromMap(config.getClientId(), config.getClientSecret(), scope);
        boolean isValid = validate(respostaObterToken);
        if (!isValid) {
            respostaObterToken = new ObterToken().obter(config, scope);
            addToMap(config.getClientId(), config.getClientSecret(), scope, respostaObterToken);
        }
        return respostaObterToken.getAccessToken();
    }

    private static boolean validate(RespostaObterToken respostaObterToken) {
        if (respostaObterToken == null) {
            return false;
        }
        long expirationDate = respostaObterToken.getCreatedAt() + respostaObterToken.getExpiresIn();
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        return (now + ADDITIONAL_TIME) <= expirationDate;
    }

    private static RespostaObterToken getFromMap(String clientId, String clientSecret, String scope) {
        String chave = String.join(":", clientId, clientSecret, scope);
        return TOKEN_MAP.get(chave);
    }

    private static void addToMap(String clientId, String clientSecret, String scope, RespostaObterToken respostaObterToken) {
        String chave = String.join(":", clientId, clientSecret, scope);
        TOKEN_MAP.put(chave, respostaObterToken);
    }
}
