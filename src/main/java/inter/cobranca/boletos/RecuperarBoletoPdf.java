package inter.cobranca.boletos;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.model.RetornoPdf;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_BOLETO_COBRANCA_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_COBRANCA_BOLETOS;

@Slf4j
public class RecuperarBoletoPdf {

    public void recuperar(Config config, String nossoNumero, String arquivo) throws SdkException {
        log.info("RecuperarBoletoPdf {} nossoNumero={}", config.getClientId(), nossoNumero);
        String url = URL_COBRANCA_BOLETOS.replace("AMBIENTE", config.getAmbiente()) + "/" + nossoNumero + "/pdf";
        String json = HttpUtils.callGet(config, url, ESCOPO_BOLETO_COBRANCA_READ, "Erro ao recuperar boleto pdf");
        try {
            RetornoPdf retornoPdf = new ObjectMapper().readValue(json, RetornoPdf.class);
            byte[] decodedBytes = Base64.getDecoder().decode(retornoPdf.getPdf());
            try (FileOutputStream stream = new FileOutputStream(arquivo)) {
                stream.write(decodedBytes);
            }
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
