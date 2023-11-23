package inter.cobranca.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.model.Webhook;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_BOLETO_COBRANCA_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_COBRANCA_WEBHOOK_COBRANCA;

@Slf4j
public class ObterWebhook {

    public Webhook obter(Config config) throws SdkException {
        log.info("ObterWebhook cobrança {}", config.getClientId());
        String url = URL_COBRANCA_WEBHOOK_COBRANCA.replace("AMBIENTE", config.getAmbiente());
        String json = HttpUtils.callGet(config, url, ESCOPO_BOLETO_COBRANCA_READ, "Erro ao obter webhook");
        try {
            return new ObjectMapper().readValue(json, Webhook.class);
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
