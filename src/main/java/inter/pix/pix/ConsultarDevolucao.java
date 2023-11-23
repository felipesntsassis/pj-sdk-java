package inter.pix.pix;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.pix.model.DevolucaoDetalhada;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_PIX_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_PIX_PIX;

@Slf4j
public class ConsultarDevolucao {

    public DevolucaoDetalhada consultar(Config config, String e2eId, String id) throws SdkException {
        log.info("ConsultarDevolucao {} e2eId={} id={}", config.getClientId(), e2eId, id);
        String url = URL_PIX_PIX.replace("AMBIENTE", config.getAmbiente()) + "/" + e2eId + "/devolucao/" + id;
        String json = HttpUtils.callGet(config, url, ESCOPO_PIX_READ, "Erro ao consultar devolução");
        try {
            return new ObjectMapper().readValue(json, DevolucaoDetalhada.class);
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
