package inter.banking;

import inter.InterSdk;
import inter.TestUtils;
import inter.banking.model.BoletoLote;
import inter.banking.model.DarfLote;
import inter.banking.model.ItemLote;
import inter.banking.model.ProcessamentoLote;
import inter.banking.model.RespostaIncluirPagamentosLote;
import inter.exceptions.SdkException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@ExtendWith(MockitoExtension.class)
class PagamentosLoteTest {
    static InterSdk interSdk;

    @BeforeAll
    static void setUp() throws SdkException {
        interSdk = TestUtils.buildSdk();
    }

    @Test
    void incluir_recuperar_lote() {
        try {
            LocalDateTime vencimento = LocalDateTime.now().plusMonths(1);
            String dataVencimento = vencimento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            BigDecimal valorPagar = BigDecimal.valueOf(vencimento.getMinute() + 1);
            BoletoLote boletoLote = BoletoLote.builder()
                    .dataVencimento(dataVencimento)
                    .valorPagar(valorPagar)
                    .codBarraLinhaDigitavel("83600000001312901380010352598733308090321566")
                    .build();
            DarfLote darfLote = DarfLote.builder()
                    .cnpjCpf("90022400664")
                    .codigoReceita("3084")
                    .dataVencimento(dataVencimento)
                    .descricao("teste sdk")
                    .nomeEmpresa("Inter")
                    .periodoApuracao("2022-04-08")
                    .valorPrincipal("2.50")
                    .referencia("1")
                    .build();
            List<ItemLote> pagamentos = new ArrayList<>();
            pagamentos.add(boletoLote);
            pagamentos.add(darfLote);
            String meuIdentificador = "lote-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));
            RespostaIncluirPagamentosLote resposta = interSdk.banking().incluirPagamentosLote(meuIdentificador, pagamentos);
            assertNotNull(resposta);
            assertNotNull(resposta.getIdLote());
            String idLote = resposta.getIdLote();

            ProcessamentoLote lote = interSdk.banking().buscarLotePagamentos(idLote);
            assertNotNull(lote);
            assertEquals(idLote, lote.getIdLote());
            assertEquals(2, lote.getPagamentos().size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
