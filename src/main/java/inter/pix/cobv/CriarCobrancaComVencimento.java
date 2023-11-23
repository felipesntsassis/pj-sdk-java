package inter.pix.cobv;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.pix.model.CobrancaVencimento;
import inter.pix.model.CobrancaVencimentoDetalhada;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_PIX_COBRANCA_VENCIMENTO_WRITE;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_PIX_COBRANCA_COM_VENCIMENTO;

@Slf4j
public class CriarCobrancaComVencimento {

    public CobrancaVencimentoDetalhada criar(Config config, String txid, CobrancaVencimento cobranca) throws SdkException {
        log.info("CriarCobrancaComVencimento {} {}", config.getClientId(), txid);
        String url = URL_PIX_COBRANCA_COM_VENCIMENTO.replace("AMBIENTE", config.getAmbiente()) + "/" + txid;
        try {
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(cobranca);
            json = HttpUtils.callPut(config, url, ESCOPO_PIX_COBRANCA_VENCIMENTO_WRITE, "Erro ao criar cobrança com vencimento", json);
            return new ObjectMapper().readValue(json, CobrancaVencimentoDetalhada.class);
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
