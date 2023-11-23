package inter;

import inter.exceptions.CertificadoExpiradoException;
import inter.exceptions.SdkException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Disabled
@ExtendWith(MockitoExtension.class)
public class TestUtils {
    @BeforeAll
    public static InterSdk buildSdk() throws SdkException {
        String ambiente = "uatbi";
        String clientId = "2179676f-3069-44de-96c3-07739bcded35";
        String clientSecret = "29f8fe86-efab-4b31-a5f6-6d22ebcb2014";
        String certificate = "certs/hml/inter.pfx";
        String password = "intersdk";
        InterSdk interSdk = new InterSdk(clientId, clientSecret, certificate, password);
        interSdk.setDebug(true);
        interSdk.setAmbiente(ambiente);
        return interSdk;
    }
}
