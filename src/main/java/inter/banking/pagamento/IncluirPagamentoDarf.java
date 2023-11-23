package inter.banking.pagamento;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.banking.model.PagamentoDarf;
import inter.banking.model.RespostaIncluirPagamentoDarf;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_PAGAMENTO_DARF_WRITE;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_BANKING_PAGAMENTO_DARF;

@Slf4j
public class IncluirPagamentoDarf {

    public RespostaIncluirPagamentoDarf incluir(Config config, PagamentoDarf pagamento) throws SdkException {
        log.info("IncluirPagamentoDarf {} {}", config.getClientId(), pagamento.getCodigoReceita());
        String url = URL_BANKING_PAGAMENTO_DARF.replace("AMBIENTE", config.getAmbiente());
        try {
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(pagamento);
            json = HttpUtils.callPost(config, url, ESCOPO_PAGAMENTO_DARF_WRITE, "Erro ao incluir pagamento de darf", json);
            return new ObjectMapper().readValue(json, RespostaIncluirPagamentoDarf.class);
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
