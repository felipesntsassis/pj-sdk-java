package inter.pix.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.pix.model.CriarLocationRequest;
import inter.pix.model.Location;
import inter.pix.model.enums.TipoCob;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_LOCATION_WRITE;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_PIX_LOCATIONS;

@Slf4j
public class CriarLocation {

    public Location criar(Config config, TipoCob tipoCob) throws SdkException {
        log.info("CriarLocation pix {} {}", config.getClientId(), tipoCob);
        String url = URL_PIX_LOCATIONS.replace("AMBIENTE", config.getAmbiente());
        CriarLocationRequest request = CriarLocationRequest.builder().tipoCob(tipoCob).build();
        try {
            String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(request);
            json = HttpUtils.callPost(config, url, ESCOPO_LOCATION_WRITE, "Erro ao criar location", json);
            return new ObjectMapper().readValue(json, Location.class);
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
