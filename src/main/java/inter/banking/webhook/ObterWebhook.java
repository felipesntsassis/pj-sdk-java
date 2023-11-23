package inter.banking.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.model.Webhook;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.*;

@Slf4j
public class ObterWebhook {

    public Webhook obter(Config config) throws SdkException {
        log.info("ObterWebhook banking {}", config.getClientId());
        String url = URL_BANKING_WEBHOOK.replace("AMBIENTE", config.getAmbiente()) + "/pix-pagamento";
        String json = HttpUtils.callGet(config, url, ESCOPO_WEBHOOK_BANKING_READ, "Erro ao obter webhook");
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
