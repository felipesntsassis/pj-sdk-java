package inter.pix.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.pix.model.FiltroConsultarLocations;
import inter.pix.model.Location;
import inter.pix.model.PaginaLocations;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_LOCATION_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_PIX_LOCATIONS;

@Slf4j
public class ConsultarLocationsCadastradas {

    public PaginaLocations consultar(Config config, String dataInicial, String dataFinal, int pagina, Integer tamanhoPagina, FiltroConsultarLocations filtro) throws SdkException {
        log.info("ConsultarLocationsCadastradas {} {}-{} pagina={}", config.getClientId(), dataInicial, dataFinal, pagina);
        return getPage(config, dataInicial, dataFinal, pagina, tamanhoPagina, filtro);
    }

    public List<Location> consultar(Config config, String dataInicial, String dataFinal, FiltroConsultarLocations filtro) throws SdkException {
        log.info("ConsultarLocationsCadastradas {} {}-{}", config.getClientId(), dataInicial, dataFinal);
        int pagina = 0;
        PaginaLocations paginaLocations;
        List<Location> locs = new ArrayList<>();
        do {
            paginaLocations = getPage(config, dataInicial, dataFinal, pagina, null, filtro);
            locs.addAll(paginaLocations.getLocs());
            pagina++;
        } while (pagina < paginaLocations.getQuantidadeDePaginas());
        return locs;
    }

    private PaginaLocations getPage(Config config, String dataInicial, String dataFinal, int pagina, Integer tamanhoPagina, FiltroConsultarLocations filtro) throws SdkException {
        String url = URL_PIX_LOCATIONS.replace("AMBIENTE", config.getAmbiente()) + "?inicio=" + dataInicial + "&fim=" + dataFinal
                + "&paginacao.paginaAtual=" + pagina
                + (tamanhoPagina != null ? "&paginacao.itensPorPagina=" + tamanhoPagina : "")
                + addfilters(filtro);
        String json = HttpUtils.callGet(config, url, ESCOPO_LOCATION_READ, "Erro ao consultar locations");
        try {
            return new ObjectMapper().readValue(json, PaginaLocations.class);
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

    private String addfilters(FiltroConsultarLocations filtro) {
        if (filtro == null) {
            return "";
        }
        StringBuilder filter = new StringBuilder();
        if (filtro.getTxIdPresente() != null) {
            filter.append("&txIdPresente").append("=").append(filtro.getTxIdPresente());
        }
        if (filtro.getTipoCob() != null) {
            filter.append("&tipoCob").append("=").append(filtro.getTipoCob().toString());
        }
        return filter.toString();
    }
}
