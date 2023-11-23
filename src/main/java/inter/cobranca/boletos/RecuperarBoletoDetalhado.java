package inter.cobranca.boletos;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.cobranca.model.BoletoDetalhado;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_BOLETO_COBRANCA_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_COBRANCA_BOLETOS;

@Slf4j
public class RecuperarBoletoDetalhado {

    public BoletoDetalhado recuperar(Config config, String nossoNumero) throws SdkException {
        log.info("RecuperarBoletoDetalhado {} nossoNumero={}", config.getClientId(), nossoNumero);
        String url = URL_COBRANCA_BOLETOS.replace("AMBIENTE", config.getAmbiente()) + "/" + nossoNumero;
        String json = HttpUtils.callGet(config, url, ESCOPO_BOLETO_COBRANCA_READ, "Erro ao recuperar boleto detalhado");
        try {
            return new ObjectMapper().readValue(json, BoletoDetalhado.class);
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
