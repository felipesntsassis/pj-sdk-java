package inter.banking;

import inter.InterSdk;
import inter.TestUtils;
import inter.banking.model.Chave;
import inter.banking.model.DadosBancarios;
import inter.banking.model.Destinatario;
import inter.banking.model.InstituicaoFinanceira;
import inter.banking.model.Pix;
import inter.banking.model.RespostaIncluirPix;
import inter.banking.model.enums.TipoConta;
import inter.exceptions.SdkException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@ExtendWith(MockitoExtension.class)
class PixPagamentoTest {
    static InterSdk interSdk;

    @BeforeAll
    static void setUp() throws SdkException {
        interSdk = TestUtils.buildSdk();
    }

    @Test
    void incluir_pix_chave() {
        try {
            //TODO chave pix válida
            Destinatario chave = Chave.builder()
                    .chave("7e537884-6740-43f1-83a3-784e0e905881")
                    .build();
            Pix pix = Pix.builder()
                    .valor(BigDecimal.ONE)
                    .destinatario(chave)
                    .build();
            RespostaIncluirPix resposta = interSdk.banking().incluirPix(pix);
            assertNotNull(resposta);
            assertNotNull(resposta.getEndToEndId());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void incluir_pix_chave_inexistente() {
        try {
            Destinatario chave = Chave.builder()
                    .chave("7e537884-6740-43f1-83a3-784e0e905881")
                    .build();
            Pix pix = Pix.builder()
                    .valor(BigDecimal.ONE)
                    .destinatario(chave)
                    .build();
            interSdk.banking().incluirPix(pix);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof SdkException);
            SdkException sdkException = (SdkException) e;
            assertNotNull(sdkException);
            assertNotNull(sdkException.getErro());
            assertNotNull(sdkException.getErro().getDetail());
            assertTrue(sdkException.getErro().getDetail().contains("Parece que essa chave não existe"));
        }
    }

    @Test
    void incluir_pix_dados_bancarios() {
        try {
            InstituicaoFinanceira instituicaoFinanceira = InstituicaoFinanceira.builder()
                    .codigo("077")
                    .nome("banco Inter")
                    .ispb("00416968")
                    .build();
            Destinatario chave = DadosBancarios.builder()
                    .nome("teste sdk")
                    .contaCorrente("9588752")
                    .tipoConta(TipoConta.CONTA_CORRENTE)
                    .cpfCnpj("90403518000169")
                    .agencia("0001")
                    .instituicaoFinanceira(instituicaoFinanceira)
                    .build();
            Pix pix = Pix.builder()
                    .valor(BigDecimal.ONE)
                    .destinatario(chave)
                    .build();
            RespostaIncluirPix resposta = interSdk.banking().incluirPix(pix);
            assertNotNull(resposta);
            assertNotNull(resposta.getEndToEndId());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
