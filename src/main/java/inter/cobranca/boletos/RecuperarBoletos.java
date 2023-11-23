package inter.cobranca.boletos;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.cobranca.model.BoletoDetalhado;
import inter.cobranca.model.FiltroRecuperarBoletos;
import inter.cobranca.model.Ordenacao;
import inter.cobranca.model.PaginaBoletos;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_BOLETO_COBRANCA_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_COBRANCA_BOLETOS;

@Slf4j
public class RecuperarBoletos {

    public PaginaBoletos recuperar(Config config, String dataInicial, String dataFinal, int pagina, Integer tamanhoPagina, FiltroRecuperarBoletos filtro, Ordenacao ordenacao) throws SdkException {
        log.info("RecuperarBoletos {} {}-{}", config.getClientId(), dataInicial, dataFinal);
        return getPage(config, dataInicial, dataFinal, pagina, tamanhoPagina, filtro, ordenacao);
    }

    public List<BoletoDetalhado> recuperar(Config config, String dataInicial, String dataFinal, FiltroRecuperarBoletos filtro, Ordenacao ordenacao) throws SdkException {
        log.info("RecuperarBoletos {} {}-{}", config.getClientId(), dataInicial, dataFinal);
        int pagina = 0;
        PaginaBoletos paginaBoletos;
        List<BoletoDetalhado> boletos = new ArrayList<>();
        do {
            paginaBoletos = getPage(config, dataInicial, dataFinal, pagina, null, filtro, ordenacao);
            boletos.addAll(paginaBoletos.getBoletos());
            pagina++;
        } while (pagina < paginaBoletos.getTotalPaginas());
        return boletos;
    }

    private PaginaBoletos getPage(Config config, String dataInicial, String dataFinal, int pagina, Integer tamanhoPagina, FiltroRecuperarBoletos filtro, Ordenacao ordenacao) throws SdkException {
        String url = URL_COBRANCA_BOLETOS.replace("AMBIENTE", config.getAmbiente()) + "?dataInicial=" + dataInicial + "&dataFinal=" + dataFinal
                + "&paginaAtual=" + pagina
                + (tamanhoPagina != null ? "&itensPorPagina=" + tamanhoPagina : "")
                + addfilters(filtro)
                + addSort(ordenacao);
        String json = HttpUtils.callGet(config, url, ESCOPO_BOLETO_COBRANCA_READ, "Erro ao recuperar boletos");
        try {
            return new ObjectMapper().readValue(json, PaginaBoletos.class);
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

    private String addfilters(FiltroRecuperarBoletos filtro) {
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
        if (filtro.getNossoNumero() != null) {
            filter.append("&nossoNumero").append("=").append(filtro.getNossoNumero());
        }
        return filter.toString();
    }

    private String addSort(Ordenacao ordenacao) {
        if (ordenacao == null) {
            return "";
        }
        StringBuilder order = new StringBuilder();
        if (ordenacao.getOrdenarPor() != null) {
            order.append("&ordenarPor").append("=").append(ordenacao.getOrdenarPor().toString());
        }
        if (ordenacao.getTipoOrdenacao() != null) {
            order.append("&tipoOrdenacao").append("=").append(ordenacao.getTipoOrdenacao().toString());
        }
        return order.toString();
    }

}
