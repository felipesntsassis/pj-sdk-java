package inter.banking.pagamento;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.banking.model.RespostaIncluirPagamentosLote;
import inter.banking.model.ItemLote;
import inter.banking.model.Lote;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_PAGAMENTOS_LOTE_WRITE;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_BANKING_PAGAMENTO_LOTE;

@Slf4j
public class IncluirPagamentosLote {

    public RespostaIncluirPagamentosLote incluir(Config config, String meuIdentificador, List<ItemLote> pagamentos) throws SdkException {
        log.info("IncluirPagamentosLote {} {} {}", config.getClientId(), meuIdentificador, pagamentos.size());
        String url = URL_BANKING_PAGAMENTO_LOTE.replace("AMBIENTE", config.getAmbiente());
        Lote request = Lote.builder()
                .meuIdentificador(meuIdentificador)
                .pagamentos(pagamentos)
                .build();
        try {
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request);
            json = HttpUtils.callPost(config, url, ESCOPO_PAGAMENTOS_LOTE_WRITE, "Erro ao incluir pagamentos em lote", json);
            return new ObjectMapper().readValue(json, RespostaIncluirPagamentosLote.class);
        } catch (IOException ioException) {
            log.error(GENERIC_EXCEPTION_MESSAGE, ioException);
            throw new SdkException(
                    ioException.getMessage(),
                    Erro.builder()
                            .title(CERTIFICATE_EXCEPTION_MESSAGE).
                            detail(ioException.getMessage())
                            .build()
            );
        }
    }

}
