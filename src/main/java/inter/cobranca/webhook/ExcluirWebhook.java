package inter.cobranca.webhook;

import inter.exceptions.SdkException;
import inter.model.Config;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import static inter.constants.Constants.ESCOPO_BOLETO_COBRANCA_WRITE;
import static inter.constants.Constants.URL_COBRANCA_WEBHOOK_COBRANCA;

@Slf4j
public class ExcluirWebhook {

    public void excluir(Config config) throws SdkException {
        log.info("ExcluirWebhook cobrança {}", config.getClientId());
        String url = URL_COBRANCA_WEBHOOK_COBRANCA.replace("AMBIENTE", config.getAmbiente());
        HttpUtils.callDelete(config, url, ESCOPO_BOLETO_COBRANCA_WRITE, "Erro ao excluir webhook");
    }

}
