package inter.banking.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.CriarWebhookRequest;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.*;

@Slf4j
public class CriarWebhook {

    public void criar(Config config, String webhookUrl) throws SdkException {
        log.info("CriarWebhook banking {} {}", config.getClientId(), webhookUrl);
        String url = URL_BANKING_WEBHOOK.replace("AMBIENTE", config.getAmbiente()) + "/pix-pagamento";
        CriarWebhookRequest request = CriarWebhookRequest.builder().webhookUrl(webhookUrl).build();
        try {
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request);
            HttpUtils.callPut(config, url, ESCOPO_WEBHOOK_BANKING_WRITE, "Erro ao criar webhook", json);
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
