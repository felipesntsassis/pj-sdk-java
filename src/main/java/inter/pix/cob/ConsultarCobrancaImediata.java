package inter.pix.cob;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.pix.model.CobrancaDetalhada;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_PIX_COBRANCA_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_PIX_COBRANCAS_IMEDIATAS;

@Slf4j
public class ConsultarCobrancaImediata {

    public CobrancaDetalhada consultar(Config config, String txId) throws SdkException {
        log.info("ConsultarCobrancaImediata {} txId={}", config.getClientId(), txId);
        String url = URL_PIX_COBRANCAS_IMEDIATAS.replace("AMBIENTE", config.getAmbiente()) + "/" + txId;
        String json = HttpUtils.callGet(config, url, ESCOPO_PIX_COBRANCA_READ, "Erro ao consultar cobrança imediata");
        try {
            return new ObjectMapper().readValue(json, CobrancaDetalhada.class);
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
