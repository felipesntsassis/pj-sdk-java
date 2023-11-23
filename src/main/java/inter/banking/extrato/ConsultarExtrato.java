package inter.banking.extrato;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.banking.model.Extrato;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_EXTRATO_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_BANKING_EXTRATO;

@Slf4j
public class ConsultarExtrato {

    public Extrato consultar(Config config, String dataInicial, String dataFinal) throws SdkException {
        log.info("ConsultarExtrato {} {}-{}", config.getClientId(), dataInicial, dataFinal);
        String url = URL_BANKING_EXTRATO.replace("AMBIENTE", config.getAmbiente()) + "?dataInicio=" + dataInicial + "&dataFim=" + dataFinal;
        String json = HttpUtils.callGet(config, url, ESCOPO_EXTRATO_READ, "Erro ao consultar extrato");
        try {
            return new ObjectMapper().readValue(json, Extrato.class);
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
