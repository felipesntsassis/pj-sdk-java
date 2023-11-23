package inter.pix.pix;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.pix.model.Devolucao;
import inter.pix.model.DevolucaoDetalhada;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_PIX_WRITE;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_PIX_PIX;

@Slf4j
public class SolicitarDevolucao {

    public DevolucaoDetalhada solicitar(Config config, String e2eId, String id, Devolucao devolucao) throws SdkException {
        log.info("SolicitarDevolucao {} e2eId={} id={}", config.getClientId(), e2eId, id);
        String url = URL_PIX_PIX.replace("AMBIENTE", config.getAmbiente()) + "/" + e2eId + "/devolucao/" + id;
        try {
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(devolucao);
            json = HttpUtils.callPut(config, url, ESCOPO_PIX_WRITE, "Erro ao solicitar devolução", json);
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
