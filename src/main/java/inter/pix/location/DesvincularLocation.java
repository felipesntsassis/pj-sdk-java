package inter.pix.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.pix.model.Location;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_LOCATION_WRITE;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_PIX_LOCATIONS;

@Slf4j
public class DesvincularLocation {

    public Location desvincular(Config config, String id) throws SdkException {
        log.info("DesvincularLocation {} id={}", config.getClientId(), id);
        String url = URL_PIX_LOCATIONS.replace("AMBIENTE", config.getAmbiente()) + "/" + id + "/txid";
        String json = HttpUtils.callDelete(config, url, ESCOPO_LOCATION_WRITE, "Erro ao desvincular location");
        try {
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
