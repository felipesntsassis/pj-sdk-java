package inter.cobranca.boletos;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.cobranca.model.FiltroRecuperarSumarioBoletos;
import inter.cobranca.model.Sumario;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_BOLETO_COBRANCA_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_COBRANCA_SUMARIO_BOLETOS;

@Slf4j
public class RecuperarSumarioBoletos {

    public Sumario recuperar(Config config, String dataInicial, String dataFinal, FiltroRecuperarSumarioBoletos filtro) throws SdkException {
        log.info("RecuperarSumarioBoletos {} {}-{}", config.getClientId(), dataInicial, dataFinal);
        String url = URL_COBRANCA_SUMARIO_BOLETOS.replace("AMBIENTE", config.getAmbiente()) + "?dataInicial=" + dataInicial + "&dataFinal=" + dataFinal
                + addfilters(filtro);
        String json = HttpUtils.callGet(config, url, ESCOPO_BOLETO_COBRANCA_READ, "Erro ao recuperar sumário de boletos");
        try {
            return new ObjectMapper().readValue(json, Sumario.class);
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

    private String addfilters(FiltroRecuperarSumarioBoletos filtro) {
        if (filtro == null) {
            return "";
        }
        StringBuilder filter = new StringBuilder();
        if (filtro.getFiltrarDataPor() != null) {
            filter.append("&filtrarDataPor").append("=").append(filtro.getFiltrarDataPor().toString());
        }
        if (filtro.getSituacao() != null) {
            filter.append("&situacao").append("=").append(filtro.getSituacao().toString());
        }
        if (filtro.getNome() != null) {
            filter.append("&nome").append("=").append(filtro.getNome());
        }
        if (filtro.getEmail() != null) {
            filter.append("&email").append("=").append(filtro.getEmail());
        }
        if (filtro.getCpfCnpj() != null) {
            filter.append("&cpfCnpj").append("=").append(filtro.getCpfCnpj());
        }
        return filter.toString();
    }
}
