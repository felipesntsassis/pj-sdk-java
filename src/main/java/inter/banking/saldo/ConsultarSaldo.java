package inter.banking.saldo;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.banking.model.Saldo;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_EXTRATO_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_BANKING_SALDO;

@Slf4j
public class ConsultarSaldo {

    public Saldo consultar(Config config, String dataSaldo) throws SdkException {
        log.info("ConsultarSaldo {} {}", config.getClientId(), dataSaldo);
        String url = URL_BANKING_SALDO.replace("AMBIENTE", config.getAmbiente());
        if (dataSaldo != null) {
            url += "?dataSaldo=" + dataSaldo;
        }
        String json = HttpUtils.callGet(config, url, ESCOPO_EXTRATO_READ, "Erro ao consultar saldo");
        try {
            return new ObjectMapper().readValue(json, Saldo.class);
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
