package inter.banking.pix;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.banking.model.RespostaIncluirPix;
import inter.banking.model.Pix;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_PAGAMENTO_PIX_WRITE;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_BANKING_PIX_PAGAMENTO;

@Slf4j
public class IncluirPix {

    public RespostaIncluirPix incluir(Config config, Pix pix) throws SdkException {
        log.info("IncluirPix {} {}", config.getClientId(), pix.getDescricao());
        String url = URL_BANKING_PIX_PAGAMENTO.replace("AMBIENTE", config.getAmbiente());
        try {
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(pix);
            json = HttpUtils.callPost(config, url, ESCOPO_PAGAMENTO_PIX_WRITE, "Erro ao incluir pix", json);
            return new ObjectMapper().readValue(json, RespostaIncluirPix.class);
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
