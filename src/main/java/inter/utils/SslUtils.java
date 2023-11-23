package inter.utils;

import inter.exceptions.CertificadoException;
import inter.exceptions.CertificadoExpiradoException;
import inter.exceptions.CertificadoNaoEncontradoException;
import inter.exceptions.SdkException;
import inter.model.Erro;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Enumeration;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.DAYS_TO_EXPIRE;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;

@Slf4j
public class SslUtils {
    private SslUtils() {
    }

    public static BasicHttpClientConnectionManager buildConnectionManager(String certificate, String password) throws SdkException {
        if (!new File(certificate).exists()) {
            throw new CertificadoNaoEncontradoException(certificate);
        }
        SSLContext sslContext = buildSslContext(certificate, password);
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("https", sslConnectionSocketFactory).build();
        return new BasicHttpClientConnectionManager(socketFactoryRegistry);
    }

    private static SSLContext buildSslContext(String certificate, String password) throws SdkException {

        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            KeyStore keyStore = getKeyStore(certificate, password);
            checkExpiration(keyStore);
            KeyManagerFactory keyManagerFactory = buildKeyManagerFactory(keyStore, password);
            sslContext.init(keyManagerFactory.getKeyManagers(), null, new java.security.SecureRandom());
            return sslContext;
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException | KeyStoreException |
                 KeyManagementException e) {
            log.error(CERTIFICATE_EXCEPTION_MESSAGE, e);
            throw new CertificadoException(
                    e.getMessage(),
                    Erro.builder()
                            .title(CERTIFICATE_EXCEPTION_MESSAGE).
                            detail(e.getMessage())
                            .build()
            );
        }
    }

    public static KeyStore getKeyStore(String certificate, String password) throws SdkException {

        try {
            FileInputStream fileInputStream = new FileInputStream(certificate);
            KeyStore keyStore = loadCertificate(fileInputStream, password);
            fileInputStream.close();
            return keyStore;
        } catch (IOException ioException) {
            log.error(GENERIC_EXCEPTION_MESSAGE, ioException);
            throw new SdkException(
                    ioException.getMessage(),
                    Erro.builder()
                            .title(CERTIFICATE_EXCEPTION_MESSAGE).
                            detail(ioException.getMessage())
                            .build()
            );
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
            log.error(CERTIFICATE_EXCEPTION_MESSAGE, e);
            throw new CertificadoException(
                    e.getMessage(),
                    Erro.builder()
                            .title(CERTIFICATE_EXCEPTION_MESSAGE).
                            detail(e.getMessage())
                            .build()
            );
        }
    }

    private static KeyStore loadCertificate(FileInputStream fileInputStream, String password) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("pkcs12");
        keyStore.load(fileInputStream, password.toCharArray());
        return keyStore;
    }

    public static Date isCloseToExpire(String certificate, String password) throws SdkException {
        KeyStore keyStore = getKeyStore(certificate, password);
        return checkExpiration(keyStore);
    }

    private static Date checkExpiration(KeyStore keyStore) throws SdkException {
        try {
            Enumeration enumeration = keyStore.aliases();
            Date notAfter = null;
            while (enumeration.hasMoreElements()) {
                String alias = (String) enumeration.nextElement();
                X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
                log.info("Certificadoemissor={} expiracao={}", certificate.getIssuerDN().getName(), certificate.getNotAfter());
                if (certificate.getNotAfter().before(Date.from(LocalDateTime.now().plusDays(DAYS_TO_EXPIRE).atZone(ZoneId.systemDefault()).toInstant()))) {
                    notAfter = certificate.getNotAfter();
                }
                if (certificate.getNotAfter().before(new Date())) {
                    throw new CertificadoExpiradoException(certificate.getNotAfter());
                }
            }
            return notAfter;
        } catch (KeyStoreException e) {
            log.error(CERTIFICATE_EXCEPTION_MESSAGE, e);
            throw new CertificadoException(
                    e.getMessage(),
                    Erro.builder()
                            .title(CERTIFICATE_EXCEPTION_MESSAGE).
                            detail(e.getMessage())
                            .build()
            );
        }
    }

    private static KeyManagerFactory buildKeyManagerFactory(KeyStore keyStrore, String password) throws NoSuchAlgorithmException,
            UnrecoverableKeyException, KeyStoreException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStrore, password.toCharArray());
        return keyManagerFactory;
    }
}
