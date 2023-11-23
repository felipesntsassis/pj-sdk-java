package inter.banking;

import inter.InterSdk;
import inter.TestUtils;
import inter.banking.model.FiltroBuscarPagamentos;
import inter.banking.model.Pagamento;
import inter.banking.model.PagamentoBoleto;
import inter.banking.model.RespostaIncluirPagamento;
import inter.banking.model.enums.TipoDataPagamento;
import inter.exceptions.SdkException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@ExtendWith(MockitoExtension.class)
class PagamentoTest {
    static InterSdk interSdk;

    @BeforeAll
    static void setUp() throws SdkException {
        interSdk = TestUtils.buildSdk();
    }

    @Test
    void incluir_recuperar_pagamento() {
        try {
            LocalDateTime vencimento = LocalDateTime.now().plusMonths(1);
            String dataVencimento = vencimento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            BigDecimal valorPagar = BigDecimal.valueOf(vencimento.getMinute() + 1);
            PagamentoBoleto pagamentoBoleto = PagamentoBoleto.builder()
                    .dataVencimento(dataVencimento)
                    .valorPagar(valorPagar)
                    .codBarraLinhaDigitavel("83600000001312901380010352598733308090321566")
                    .build();
            RespostaIncluirPagamento resposta = interSdk.banking().incluirPagamento(pagamentoBoleto);
            assertNotNull(resposta);
            assertEquals("APROVADO", resposta.getStatusPagamento());
            System.out.println("codigoTransacao " + resposta.getCodigoTransacao());

            List<Pagamento> pagamentos = interSdk.banking().buscarPagamentos(dataVencimento, dataVencimento, FiltroBuscarPagamentos.builder().filtrarDataPor(TipoDataPagamento.VENCIMENTO).build());
            assertNotNull(pagamentos);
            assertTrue(pagamentos.size() > 0);
            pagamentos.stream().anyMatch(p -> dataVencimento.equals(p.getDataVencimentoDigitada())
                    && (Math.abs(valorPagar.subtract(p.getValorPago()).doubleValue()) < 0.1));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
