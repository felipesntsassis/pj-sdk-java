package inter.pix.cob;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.pix.model.CobrancaDetalhada;
import inter.pix.model.FiltroConsultarCobrancasImediatas;
import inter.pix.model.PaginaCobrancas;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_PIX_COBRANCA_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_PIX_COBRANCAS_IMEDIATAS;

@Slf4j
public class ConsultarCobrancasImediatas {

    public PaginaCobrancas consultar(Config config, String dataInicial, String dataFinal, int pagina, Integer tamanhoPagina, FiltroConsultarCobrancasImediatas filtro) throws SdkException {
        log.info("ConsultarCobrancasImediatas {} {}-{} pagina={}", config.getClientId(), dataInicial, dataFinal, pagina);
        return getPage(config, dataInicial, dataFinal, pagina, tamanhoPagina, filtro);
    }

    public List<CobrancaDetalhada> consultar(Config config, String dataInicial, String dataFinal, FiltroConsultarCobrancasImediatas filtro) throws SdkException {
        log.info("ConsultarCobrancasImediatas {} {}-{}", config.getClientId(), dataInicial, dataFinal);
        int pagina = 0;
        PaginaCobrancas paginaCobrancas;
        List<CobrancaDetalhada> cobrancas = new ArrayList<>();
        do {
            paginaCobrancas = getPage(config, dataInicial, dataFinal, pagina, null, filtro);
            cobrancas.addAll(paginaCobrancas.getCobrancas());
            pagina++;
        } while (pagina < paginaCobrancas.getQuantidadeDePaginas());
        return cobrancas;
    }

    private PaginaCobrancas getPage(Config config, String dataInicial, String dataFinal, int pagina, Integer tamanhoPagina, FiltroConsultarCobrancasImediatas filtro) throws SdkException {
        String url = URL_PIX_COBRANCAS_IMEDIATAS.replace("AMBIENTE", config.getAmbiente()) + "?inicio=" + dataInicial + "&fim=" + dataFinal
                + "&paginacao.paginaAtual=" + pagina
                + (tamanhoPagina != null ? "&paginacao.itensPorPagina=" + tamanhoPagina : "")
                + addfilters(filtro);
        String json = HttpUtils.callGet(config, url, ESCOPO_PIX_COBRANCA_READ, "Erro ao consultar cobranças imediatas");
        try {
            return new ObjectMapper().readValue(json, PaginaCobrancas.class);
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

    private String addfilters(FiltroConsultarCobrancasImediatas filtro) {
        if (filtro == null) {
            return "";
        }
        StringBuilder filter = new StringBuilder();
        if (filtro.getCpf() != null) {
            filter.append("&cpf").append("=").append(filtro.getCpf());
        }
        if (filtro.getCnpj() != null) {
            filter.append("&cnpj").append("=").append(filtro.getCnpj());
        }
        if (filtro.getLocationPresente() != null) {
            filter.append("&locationPresente").append("=").append(filtro.getLocationPresente());
        }
        if (filtro.getStatus() != null) {
            filter.append("&status").append("=").append(filtro.getStatus().toString());
        }
        return filter.toString();
    }
}
