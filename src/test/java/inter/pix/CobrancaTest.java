package inter.pix;

import inter.InterSdk;
import inter.TestUtils;
import inter.exceptions.SdkException;
import inter.pix.model.Calendario;
import inter.pix.model.Cobranca;
import inter.pix.model.CobrancaDetalhada;
import inter.pix.model.Devedor;
import inter.pix.model.Valor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@ExtendWith(MockitoExtension.class)
class CobrancaTest {
    static InterSdk interSdk;

    @BeforeAll
    static void setUp() throws SdkException {
        interSdk = TestUtils.buildSdk();
    }

    @Test
    void criar_cobranca() {
        try {
            Cobranca cobranca = Cobranca.builder()
                    .calendario(Calendario.builder().expiracao(86400).build())
                    .devedor(Devedor.builder().cpf("90388459018").nome("Rafael Nunes").build())
                    .valor(Valor.builder().original("0.01").build())
                    .chave("90403518000169")
                    .build();
            CobrancaDetalhada cobrancaDetalhada = interSdk.pix().criarCobrancaImediata(cobranca);
            assertNotNull(cobrancaDetalhada);
            assertNotNull(cobrancaDetalhada.getTxid());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void criar_cobranca_txit() {
        try {
            Cobranca cobranca = Cobranca.builder()
                    .calendario(Calendario.builder().expiracao(86400).build())
                    .devedor(Devedor.builder().cpf("90388459018").nome("Rafael Nunes").build())
                    .valor(Valor.builder().original("0.01").build())
                    .chave("90403518000169")
                    .txid("34f656b2fb444ba4aeefecda1878e101")
                    .build();
            CobrancaDetalhada cobrancaDetalhada = interSdk.pix().criarCobrancaImediata(cobranca);
            assertNotNull(cobrancaDetalhada);
            assertNotNull(cobrancaDetalhada.getTxid());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void revisar_cobranca() {
        try {
            Cobranca cobranca = Cobranca.builder()
                    .calendario(Calendario.builder().expiracao(86400).build())
                    .devedor(Devedor.builder().cpf("90388459018").nome("Rafael Nunes").build())
                    .valor(Valor.builder().original("0.01").build())
                    .chave("90403518000169")
                    .txid("34f656b2fb444ba4aeefecda1878e101")
                    .build();
            CobrancaDetalhada cobrancaDetalhada = interSdk.pix().revisarCobrancaImediata(cobranca);
            assertNotNull(cobrancaDetalhada);
            assertNotNull(cobrancaDetalhada.getTxid());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void consultar_cobranca() {
        try {
            CobrancaDetalhada cobrancaDetalhada = interSdk.pix().consultarCobrancaImediata("q33vsvj0zwzwhtt4wnbmed7qkgkkck733ks");
            assertNotNull(cobrancaDetalhada);
            assertNotNull(cobrancaDetalhada.getTxid());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void consultar_cobrancas() {
        try {
            List<CobrancaDetalhada> cobrancas = interSdk.pix().consultarCobrancasImediatas("2022-04-01T00:00:00Z", "2022-04-30T23:59:59Z", null);
            assertNotNull(cobrancas);
            assertNotNull(cobrancas.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
