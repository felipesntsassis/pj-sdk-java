package inter.pix.pix;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.pix.model.FiltroConsultarPixRecebidos;
import inter.pix.model.PaginaPix;
import inter.pix.model.Pix;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_PIX_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_PIX_PIX;

@Slf4j
public class ConsultarPixRecebidos {

    public PaginaPix consultar(Config config, String dataInicial, String dataFinal, int pagina, Integer tamanhoPagina, FiltroConsultarPixRecebidos filtro) throws SdkException {
        log.info("ConsultarPixRecebidos {} {}-{} pagina={}", config.getClientId(), dataInicial, dataFinal, pagina);
        return getPage(config, dataInicial, dataFinal, pagina, tamanhoPagina, filtro);
    }

    public List<Pix> consultar(Config config, String dataInicial, String dataFinal, FiltroConsultarPixRecebidos filtro) throws SdkException {
        log.info("ConsultarPixRecebidos {} {}-{}", config.getClientId(), dataInicial, dataFinal);
        int pagina = 0;
        PaginaPix paginaPix;
        List<Pix> listaPix = new ArrayList<>();
        do {
            paginaPix = getPage(config, dataInicial, dataFinal, pagina, null, filtro);
            listaPix.addAll(paginaPix.getListaPix());
            pagina++;
        } while (pagina < paginaPix.getQuantidadeDePaginas());
        return listaPix;
    }

    private PaginaPix getPage(Config config, String dataInicial, String dataFinal, int pagina, Integer tamanhoPagina, FiltroConsultarPixRecebidos filtro) throws SdkException {
        String url = URL_PIX_PIX.replace("AMBIENTE", config.getAmbiente()) + "?inicio=" + dataInicial + "&fim=" + dataFinal
                + "&paginacao.paginaAtual=" + pagina
                + (tamanhoPagina != null ? "&paginacao.itensPorPagina=" + tamanhoPagina : "")
                + addfilters(filtro);
        String json = HttpUtils.callGet(config, url, ESCOPO_PIX_READ, "Erro ao consultar pix recebidos");
        try {
            return new ObjectMapper().readValue(json, PaginaPix.class);
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

    private String addfilters(FiltroConsultarPixRecebidos filtro) {
        if (filtro == null) {
            return "";
        }
        StringBuilder filter = new StringBuilder();
        if (filtro.getTxId() != null) {
            filter.append("&txId").append("=").append(filtro.getTxId());
        }
        if (filtro.getTxIdPresente() != null) {
            filter.append("&txIdPresente").append("=").append(filtro.getTxIdPresente());
        }
        if (filtro.getDevolucaoPresente() != null) {
            filter.append("&devolucaoPresente").append("=").append(filtro.getDevolucaoPresente());
        }
        if (filtro.getCpf() != null) {
            filter.append("&cpf").append("=").append(filtro.getCpf());
        }
        if (filtro.getCnpj() != null) {
            filter.append("&cnpj").append("=").append(filtro.getCnpj());
        }
        if (filtro.getTxId() != null) {
            filter.append("&txId").append("=").append(filtro.getTxId());
        }
        return filter.toString();
    }
}
