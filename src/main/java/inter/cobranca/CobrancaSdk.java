package inter.cobranca;

import inter.cobranca.boletos.EmitirBoleto;
import inter.cobranca.boletos.RecuperarBoletoDetalhado;
import inter.cobranca.boletos.RecuperarBoletos;
import inter.cobranca.boletos.RecuperarSumarioBoletos;
import inter.cobranca.model.Boleto;
import inter.cobranca.model.BoletoDetalhado;
import inter.cobranca.model.FiltroRecuperarBoletos;
import inter.cobranca.model.FiltroRecuperarSumarioBoletos;
import inter.cobranca.model.Ordenacao;
import inter.cobranca.model.PaginaBoletos;
import inter.cobranca.model.PaginaCallbacks;
import inter.cobranca.model.RespostaEmitirBoleto;
import inter.cobranca.model.Sumario;
import inter.cobranca.model.enums.MotivoCancelamento;
import inter.cobranca.webhook.ConsultarCallbacks;
import inter.cobranca.webhook.CriarWebhook;
import inter.cobranca.webhook.ExcluirWebhook;
import inter.cobranca.webhook.ObterWebhook;
import inter.cobranca.boletos.CancelarBoleto;
import inter.cobranca.boletos.RecuperarBoletoPdf;
import inter.cobranca.model.Callback;
import inter.cobranca.model.FiltroBuscarCallbacks;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Webhook;

import java.util.List;

public class CobrancaSdk {

    private final Config config;
    private EmitirBoleto emitirBoleto;
    private RecuperarBoletos recuperarBoletos;
    private RecuperarSumarioBoletos recuperarSumarioBoletos;
    private RecuperarBoletoDetalhado recuperarBoletoDetalhado;
    private RecuperarBoletoPdf recuperarBoletoPdf;
    private CancelarBoleto cancelarBoleto;
    private CriarWebhook criarWebhook;
    private ObterWebhook obterWebhook;
    private ExcluirWebhook excluirWebhook;
    private ConsultarCallbacks consultarCallbacks;

    public CobrancaSdk(Config config) {
        this.config = config;
    }

    /**
     * Emite um novo boleto registrado.
     * <p>O boleto emitido estará disponível para consulta e pagamento, após um tempo apróximado de 5
     * minutos da sua inclusão. Esse tempo é necessário para o registro do boleto na CIP.</p>
     *
     * @param boleto Dados do boleto a ser emitido
     * @return objeto com nossoNumero, codigoBarras e linhaDigitavel
     * @see <a href="https://developers.bancointer.com.br/v4/reference/incluirboleto">Emitir boleto de cobrança</a>
     */
    public RespostaEmitirBoleto emitirBoleto(Boleto boleto) throws SdkException {
        if (emitirBoleto == null) {
            emitirBoleto = new EmitirBoleto();
        }
        return emitirBoleto.emitir(config, boleto);
    }

    /**
     * Recupera uma coleção de Boletos por um período específico, de acordo com os parâmetros informados.
     *
     * @param dataInicial Data inicial, em acordo com o campo "filtrarDataPor". Formato aceito: YYYY-MM-DD.
     * @param dataFinal   Data final, em acordo com o campo "filtrarDataPor". Formato aceito: YYYY-MM-DD.
     * @param filtro      Filtros para consulta(opcional, pode ser null).
     * @param ordenacao   Ordenação do resultado(opcional, pode ser null).
     * @return lista de boletos
     * @see <a href="https://developers.bancointer.com.br/v4/reference/pesquisarboletos">Recuperar coleção de boletos</a>
     */
    public List<BoletoDetalhado> recuperarBoletos(String dataInicial, String dataFinal, FiltroRecuperarBoletos filtro, Ordenacao ordenacao) throws SdkException {
        if (recuperarBoletos == null) {
            recuperarBoletos = new RecuperarBoletos();
        }
        return recuperarBoletos.recuperar(config, dataInicial, dataFinal, filtro, ordenacao);
    }

    /**
     * Recupera uma coleção de Boletos por um período específico, de acordo com os parâmetros informados, com paginação.
     *
     * @param dataInicial Data inicial, em acordo com o campo "filtrarDataPor". Formato aceito: YYYY-MM-DD.
     * @param dataFinal   Data final, em acordo com o campo "filtrarDataPor". Formato aceito: YYYY-MM-DD.
     * @param filtro      Filtros para consulta(opcional, pode ser null).
     * @param ordenacao   Ordenação do resultado(opcional, pode ser null).
     * @param pagina      Número da página iniciando com 0
     * @return página com lista de boletos
     * @see <a href="https://developers.bancointer.com.br/v4/reference/pesquisarboletos">Recuperar coleção de boletos</a>
     */
    public PaginaBoletos recuperarBoletos(String dataInicial, String dataFinal, FiltroRecuperarBoletos filtro, Ordenacao ordenacao, int pagina) throws SdkException {
        if (recuperarBoletos == null) {
            recuperarBoletos = new RecuperarBoletos();
        }
        return recuperarBoletos.recuperar(config, dataInicial, dataFinal, pagina, null, filtro, ordenacao);
    }

    /**
     * Recupera uma coleção de Boletos por um período específico, de acordo com os parâmetros informados, com paginação.
     *
     * @param dataInicial   Data inicial, em acordo com o campo "filtrarDataPor". Formato aceito: YYYY-MM-DD.
     * @param dataFinal     Data final, em acordo com o campo "filtrarDataPor". Formato aceito: YYYY-MM-DD.
     * @param filtro        Filtros para consulta(opcional, pode ser null).
     * @param ordenacao     Ordenação do resultado(opcional, pode ser null).
     * @param pagina        Número da página iniciando com 0
     * @param tamanhoPagina Tamanho da páfina, padrão = 100.
     * @return página com lista de boletos
     * @see <a href="https://developers.bancointer.com.br/v4/reference/pesquisarboletos">Recuperar coleção de boletos</a>
     */
    public PaginaBoletos recuperarBoletos(String dataInicial, String dataFinal, FiltroRecuperarBoletos filtro, Ordenacao ordenacao, int pagina, int tamanhoPagina) throws SdkException {
        if (recuperarBoletos == null) {
            recuperarBoletos = new RecuperarBoletos();
        }
        return recuperarBoletos.recuperar(config, dataInicial, dataFinal, pagina, tamanhoPagina, filtro, ordenacao);
    }

    /**
     * Recupera o sumário de uma coleção de Boletos por um período específico, de acordo com os parâmetros informados.
     *
     * @param dataInicial Data inicial, em acordo com o campo "filtrarDataPor". Formato aceito: YYYY-MM-DD.
     * @param dataFinal   Data final, em acordo com o campo "filtrarDataPor". Formato aceito: YYYY-MM-DD.
     * @param filtro      Filtros para consulta(opcional, pode ser null).
     * @return sumário
     * @see <a href="https://developers.bancointer.com.br/v4/reference/consultarsumario-1">Recuperar sumário de boletos</a>
     */
    public Sumario recuperarSumarioBoletos(String dataInicial, String dataFinal, FiltroRecuperarSumarioBoletos filtro) throws SdkException {
        if (recuperarSumarioBoletos == null) {
            recuperarSumarioBoletos = new RecuperarSumarioBoletos();
        }
        return recuperarSumarioBoletos.recuperar(config, dataInicial, dataFinal, filtro);
    }

    /**
     * Recupera um boleto detalhado de acordo com o parâmetro nossoNumero informado.
     *
     * @param nossoNumero Nosso número associado ao boleto.
     * @return boleto detalhado
     * @see <a href="https://developers.bancointer.com.br/v4/reference/consultarboleto-1">Recuperar boleto detalhado</a>
     */
    public BoletoDetalhado recuperarBoletoDetalhado(String nossoNumero) throws SdkException {
        if (recuperarBoletoDetalhado == null) {
            recuperarBoletoDetalhado = new RecuperarBoletoDetalhado();
        }
        return recuperarBoletoDetalhado.recuperar(config, nossoNumero);
    }

    /**
     * Recupera um boleto em pdf de acordo com o parâmetro nossoNumero informado.
     *
     * @param nossoNumero Nosso número associado ao boleto.
     * @param arquivo     Arquivo PDF que será gravado
     * @see <a href="https://developers.bancointer.com.br/v4/reference/descarregarpdfboleto-1">Recuperar boleto em PDF</a>
     */
    public void recuperarBoletoPdf(String nossoNumero, String arquivo) throws SdkException {
        if (recuperarBoletoPdf == null) {
            recuperarBoletoPdf = new RecuperarBoletoPdf();
        }
        recuperarBoletoPdf.recuperar(config, nossoNumero, arquivo);
    }

    /**
     * Cancela um boleto.
     *
     * @param nossoNumero        Nosso número associado ao boleto
     * @param motivoCancelamento Domínio que descreve o tipo de cancelamento sendo solicitado.
     * @see <a href="https://developers.bancointer.com.br/v4/reference/baixarboleto-1">Cancelar boleto</a>
     */
    public void cancelarBoleto(String nossoNumero, MotivoCancelamento motivoCancelamento) throws SdkException {
        if (cancelarBoleto == null) {
            cancelarBoleto = new CancelarBoleto();
        }
        cancelarBoleto.cancelar(config, nossoNumero, motivoCancelamento);
    }

    /**
     * Método destinado a criar um webhook para receber notificações de boletos pagos e cancelados (callbacks)
     *
     * @param webhookUrl
     * @see <a href="https://developers.bancointer.com.br/v4/reference/webhookput">Criar webhook</a>
     */
    public void criarWebhook(String webhookUrl) throws SdkException {
        if (criarWebhook == null) {
            criarWebhook = new CriarWebhook();
        }
        criarWebhook.criar(config, webhookUrl);
    }

    /**
     * Obter webhook cadastrado
     *
     * @return webhook
     * @see <a href="https://developers.bancointer.com.br/v4/reference/webhookget">Obter webhook cadastrado</a>
     */
    public Webhook obterWebhook() throws SdkException {
        if (obterWebhook == null) {
            obterWebhook = new ObterWebhook();
        }
        return obterWebhook.obter(config);
    }

    /**
     * Exclui o webhook
     *
     * @see <a href="https://developers.bancointer.com.br/v4/reference/webhookdelete">Excluir webhook</a>
     */
    public void excluirWebhook() throws SdkException {
        if (excluirWebhook == null) {
            excluirWebhook = new ExcluirWebhook();
        }
        excluirWebhook.excluir(config);
    }

    public List<Callback> consultarCallbacks(String dataHoraInicio, String dataHoraFim, FiltroBuscarCallbacks filtro) throws SdkException {
        if (consultarCallbacks== null) {
            consultarCallbacks = new ConsultarCallbacks();
        }
        return consultarCallbacks.buscar(config, dataHoraInicio, dataHoraFim, filtro);
    }

    public PaginaCallbacks consultarCallbacks(String dataHoraInicio, String dataHoraFim, int tamanhoPagina, int pagina, FiltroBuscarCallbacks filtro) throws SdkException {
        if (consultarCallbacks== null) {
            consultarCallbacks = new ConsultarCallbacks();
        }
        return consultarCallbacks.buscar(config, dataHoraInicio, dataHoraFim, pagina, tamanhoPagina, filtro);
    }

}
