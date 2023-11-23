package inter.banking;

import inter.InterSdk;
import inter.TestUtils;
import inter.banking.model.PagamentoDarf;
import inter.banking.model.RespostaIncluirPagamentoDarf;
import inter.banking.model.RetornoPagamentoDarf;
import inter.exceptions.SdkException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@ExtendWith(MockitoExtension.class)
class PagamentoDarfTest {
    static InterSdk interSdk;

    @BeforeAll
    static void setUp() throws SdkException {
        interSdk = TestUtils.buildSdk();
    }

    @Test
    void incluir_pagamento_darf() {
        try {
            LocalDateTime vencimento = LocalDateTime.now().plusMonths(1);
            String dataVencimento = vencimento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            PagamentoDarf pagamentoDarf = PagamentoDarf.builder()
                    .cnpjCpf("90022400664")
                    .codigoReceita("0191")
                    .dataVencimento(dataVencimento)
                    .descricao("teste sdk")
                    .nomeEmpresa("Inter")
                    .periodoApuracao("2022-04-08")
                    .valorPrincipal("2.50")
                    .referencia("1")
                    .build();
            RespostaIncluirPagamentoDarf resposta = interSdk.banking().incluirPagamentoDarf(pagamentoDarf);
            assertNotNull(resposta);
            assertNotNull(resposta.getCodigoTransacao());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void buscar_pagamentos_darf() {
        try {
            List<RetornoPagamentoDarf> pagamentos = interSdk.banking().buscarPagamentosDarf("2022-04-01", "2022-04-30", null);
            assertNotNull(pagamentos);
            assertTrue(pagamentos.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
