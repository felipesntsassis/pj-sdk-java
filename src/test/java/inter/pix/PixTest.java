package inter.pix;

import inter.InterSdk;
import inter.TestUtils;
import inter.exceptions.SdkException;
import inter.pix.model.Devolucao;
import inter.pix.model.DevolucaoDetalhada;
import inter.pix.model.Pix;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@ExtendWith(MockitoExtension.class)
class PixTest {
    static InterSdk interSdk;

    @BeforeAll
    static void setUp() throws SdkException {
        interSdk = TestUtils.buildSdk();
    }

    @Test
    void consultar_pix() {
        try {
            String e2eId = "E00416968202204201931rPWhBOmhWmN";
            Pix resposta = interSdk.pix().consultarPix(e2eId);
            assertNotNull(resposta);
            assertEquals("90403518000169", resposta.getChave());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void consultar_pix_recebidos() {
        try {
            String dataInicial = "2022-04-01T00:00:00Z";
            String dataFinal = "2022-04-30T23:59:59Z";
            List<Pix> resposta = interSdk.pix().consultarPixRecebidos(dataInicial, dataFinal, null);
            assertNotNull(resposta);
            assertTrue(resposta.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void solicitar_devolucao() {
        try {
            //TODO 400 - Erro na Chamada Lateral com o Pagamento Instantaneo
            String e2eId = "E00416968202204201931rPWhBOmhWmN";
            String id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmm"));
            Devolucao devolucao = Devolucao.builder().valor("50.00").build();
            DevolucaoDetalhada resposta = interSdk.pix().solicitarDevolucao(e2eId, id, devolucao);
            assertNotNull(resposta);
            assertNotNull(resposta.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void consultar_devolucao() {
        try {
            String e2eId = "E17184037202302022054qlNqeoz24ly";
            String id = "0302231153";
            DevolucaoDetalhada resposta = interSdk.pix().consultarDevolucao(e2eId, id);
            assertNotNull(resposta);
            assertNotNull(resposta.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
