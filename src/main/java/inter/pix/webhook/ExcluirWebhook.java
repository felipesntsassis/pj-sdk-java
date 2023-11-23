package inter.pix.webhook;

import inter.exceptions.SdkException;
import inter.model.Config;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import static inter.constants.Constants.ESCOPO_WEBHOOK_WRITE;
import static inter.constants.Constants.URL_PIX_WEBHOOK_PIX;

@Slf4j
public class ExcluirWebhook {

    public void excluir(Config config, String chave) throws SdkException {
        log.info("ExcluirWebhook pix {} {}", config.getClientId(), chave);
        String url = URL_PIX_WEBHOOK_PIX.replace("AMBIENTE", config.getAmbiente()) + "/" + chave;
        HttpUtils.callDelete(config, url, ESCOPO_WEBHOOK_WRITE, "Erro ao excluir webhook");
    }

}
