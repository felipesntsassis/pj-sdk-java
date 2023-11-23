package inter.pix.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.pix.model.Callback;
import inter.pix.model.FiltroBuscarCallbacks;
import inter.pix.model.PaginaCallbacks;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static inter.constants.Constants.CALLBACKS;
import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_WEBHOOK_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_PIX_WEBHOOK_PIX;

@Slf4j
public class ConsultarCallbacks {

    public PaginaCallbacks buscar(Config config, String dataHoraInicio, String dataHoraFim, int pagina, Integer tamanhoPagina, FiltroBuscarCallbacks filtro) throws SdkException {
        log.info("BuscarCallbacks {} {}-{}", config.getClientId(), dataHoraInicio, dataHoraFim);
        return getPage(config, dataHoraInicio, dataHoraFim, pagina, tamanhoPagina, filtro);
    }

    public List<Callback> buscar(Config config, String dataHoraInicio, String dataHoraFim, FiltroBuscarCallbacks filtro) throws SdkException {
        log.info("BuscarCallbacks {} {}-{}", config.getClientId(), dataHoraInicio, dataHoraFim);
        int pagina = 0;
        PaginaCallbacks paginaCallbacks;
        List<Callback> callbacks = new ArrayList<>();
        do {
            paginaCallbacks = getPage(config, dataHoraInicio, dataHoraFim, pagina, null, filtro);
            callbacks.addAll(paginaCallbacks.getCallbacks());
            pagina++;
        } while (pagina < paginaCallbacks.getTotalPaginas());

        return callbacks;
    }

    private PaginaCallbacks getPage(Config config, String dataHoraInicio, String dataHoraFim, int pagina, Integer tamanhoPagina, FiltroBuscarCallbacks filtro) throws SdkException {
        String url = URL_PIX_WEBHOOK_PIX.replace("AMBIENTE", config.getAmbiente())
                + "/" + CALLBACKS
                + "?dataHoraInicio=" + dataHoraInicio + "&dataHoraFim=" + dataHoraFim
                + "&paginaAtual=" + pagina
                + (tamanhoPagina != null ? "&itensPorPagina=" + tamanhoPagina : "")
                + addfilters(filtro);
        String json = HttpUtils.callGet(config, url, ESCOPO_WEBHOOK_READ, "Erro ao recuperar callbacks");
        try {
            return new ObjectMapper().readValue(json, PaginaCallbacks.class);
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

    private String addfilters(FiltroBuscarCallbacks filtro) {
        if (filtro == null) {
            return "";
        }

        StringBuilder filter = new StringBuilder();
        if (filtro.getTxid() != null) {
            filter.append("&txid").append("=").append(filtro.getTxid());
        }

        return filter.toString();
    }






    // ==================================================


//    public List<Callback> buscar(Config config, String dataHoraInicio, String dataHoraFim, FiltroBuscarCallbacks filtro) throws SdkException {
//        log.info("BuscarCallbacks{} {}-{}", config.getClientId(), dataHoraInicio, dataHoraFim);
//        String url = URL_PIX_WEBHOOK_PIX.replace("AMBIENTE", config.getAmbiente())
//                + "/" + CALLBACKS
//                + "?dataHoraInicio=" + dataHoraInicio + "&dataHoraFim=" + dataHoraFim
//                + addfilters(filtro);
//        String json = HttpUtils.callGet(config, url, ESCOPO_WEBHOOK_READ, "Erro ao buscar callbacks");
//        try {
//            return new ObjectMapper().readValue(json, new TypeReference<List<Callback>>() {
//            });
//        } catch (IOException ioException) {
//            log.error(GENERIC_EXCEPTION_MESSAGE, ioException);
//            throw new SdkException(
//                    ioException.getMessage(),
//                    Erro.builder()
//                            .title(CERTIFICATE_EXCEPTION_MESSAGE).
//                            detail(ioException.getMessage())
//                            .build()
//            );
//        }
//    }

//    private String addfilters(FiltroBuscarCallbacks filtro) {
//        if (filtro == null) {
//            return "";
//        }
//
//        StringBuilder filter = new StringBuilder();
//        if (filtro.getTxid() != null) {
//            filter.append("&txid").append("=").append(filtro.getTxid());
//        }
//
//        return filter.toString();
//    }

}
