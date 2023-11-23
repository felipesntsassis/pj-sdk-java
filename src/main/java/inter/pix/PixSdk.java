package inter.pix;

import inter.pix.model.PaginaCallbacks;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Webhook;
import inter.pix.cob.ConsultarCobrancaImediata;
import inter.pix.cob.ConsultarCobrancasImediatas;
import inter.pix.cob.CriarCobrancaImediata;
import inter.pix.cob.RevisarCobrancaImediata;
import inter.pix.cobv.ConsultarCobrancaComVencimento;
import inter.pix.cobv.ConsultarCobrancasComVencimento;
import inter.pix.cobv.CriarCobrancaComVencimento;
import inter.pix.cobv.RevisarCobrancaComVencimento;
import inter.pix.location.ConsultarLocationsCadastradas;
import inter.pix.location.CriarLocation;
import inter.pix.location.DesvincularLocation;
import inter.pix.location.RecuperarLocation;
import inter.pix.model.Callback;
import inter.pix.model.Cobranca;
import inter.pix.model.CobrancaVencimento;
import inter.pix.model.CobrancaVencimentoDetalhada;
import inter.pix.model.CobrancaDetalhada;
import inter.pix.model.Devolucao;
import inter.pix.model.DevolucaoDetalhada;
import inter.pix.model.FiltroBuscarCallbacks;
import inter.pix.model.FiltroConsultarCobrancasComVencimento;
import inter.pix.model.FiltroConsultarCobrancasImediatas;
import inter.pix.model.FiltroConsultarLocations;
import inter.pix.model.FiltroConsultarPixRecebidos;
import inter.pix.model.Location;
import inter.pix.model.PaginaCobrancas;
import inter.pix.model.PaginaCobrancasVencimento;
import inter.pix.model.PaginaLocations;
import inter.pix.model.PaginaPix;
import inter.pix.model.Pix;
import inter.pix.model.enums.TipoCob;
import inter.pix.pix.ConsultarDevolucao;
import inter.pix.pix.ConsultarPix;
import inter.pix.pix.ConsultarPixRecebidos;
import inter.pix.pix.SolicitarDevolucao;
import inter.pix.webhook.ConsultarCallbacks;
import inter.pix.webhook.CriarWebhook;
import inter.pix.webhook.ExcluirWebhook;
import inter.pix.webhook.ObterWebhook;

import java.util.List;

public class PixSdk {

    private final Config config;
    private CriarCobrancaImediata criarCobrancaImediata;
    private RevisarCobrancaImediata revisarCobrancaImediata;
    private ConsultarCobrancaImediata consultarCobrancaImediata;
    private ConsultarCobrancasImediatas consultarCobrancasImediatas;
    private CriarCobrancaComVencimento criarCobrancaComVencimento;
    private RevisarCobrancaComVencimento revisarCobrancaComVencimento;
    private ConsultarCobrancaComVencimento consultarCobrancaComVencimento;
    private ConsultarCobrancasComVencimento consultarCobrancasComVencimento;
    private CriarLocation criarLocation;
    private ConsultarLocationsCadastradas consultarLocationsCadastradas;
    private RecuperarLocation recuperarLocation;
    private DesvincularLocation desvincularLocation;
    private ConsultarPix consultarPix;
    private ConsultarPixRecebidos consultarPixRecebidos;
    private SolicitarDevolucao solicitarDevolucao;
    private ConsultarDevolucao consultarDevolucao;
    private CriarWebhook criarWebhook;
    private ObterWebhook obterWebhook;
    private ExcluirWebhook excluirWebhook;
    private ConsultarCallbacks consultarCallbacks;

    public PixSdk(Config config) {
        this.config = config;
    }

    /**
     * Crio uma cobrança imediata.
     *
     * @param cobranca Cobrança a ser criada
     * @return cobranaa Cobrança que foi criada
     * @see <a href="https://developers.inter.co/references/pix#tag/Cobranca-Imediata/paths/~1cob~1%7Btxid%7D/put">Criar cobrança imediata</a>
     */
    public CobrancaDetalhada criarCobrancaImediataTxId(Cobranca cobranca) throws SdkException {
        if (criarCobrancaImediata == null) {
            criarCobrancaImediata = new CriarCobrancaImediata();
        }
        return criarCobrancaImediata.criar(config, cobranca);
    }

    /**
     * Revisa uma cobrança imediata.
     *
     * @param cobranca Cobrança a ser criada
     * @return cobranaa Cobrança que foi criada
     * @see <a href="https://developers.inter.co/references/pix#tag/Cobranca-Imediata/paths/~1cob~1%7Btxid%7D/patch">Revisar cobrança imediata</a>
     */
    public CobrancaDetalhada revisarCobrancaImediata(Cobranca cobranca) throws SdkException {
        if (revisarCobrancaImediata == null) {
            revisarCobrancaImediata = new RevisarCobrancaImediata();
        }
        return revisarCobrancaImediata.revisar(config, cobranca);
    }

    /**
     * Consulta uma cobrança através de um determinado txid.
     * @param txId  identificador da cobrança
     * @return cobrança
     * @see <a href="https://developers.inter.co/references/pix#tag/Cobranca-Imediata/paths/~1cob~1%7Btxid%7D/get">Consultar cobrança imediata</a>
     */
    public CobrancaDetalhada consultarCobrancaImediata(String txId) throws SdkException {
        if (consultarCobrancaImediata == null) {
            consultarCobrancaImediata = new ConsultarCobrancaImediata();
        }
        return consultarCobrancaImediata.consultar(config, txId);
    }

    /**
     * Crio uma cobrança imediata. O txid será definido pelo PSP.
     *
     * @param cobranca Cobrança a ser criada
     * @return cobranaa Cobrança que foi criada
     * @see <a href="https://developers.inter.co/references/pix#tag/Cobranca-Imediata/paths/~1cob/post">Criar cobrança imediata</a>
     */
    public CobrancaDetalhada criarCobrancaImediata(Cobranca cobranca) throws SdkException {
        if (criarCobrancaImediata == null) {
            criarCobrancaImediata = new CriarCobrancaImediata();
        }
        return criarCobrancaImediata.criar(config, cobranca);
    }

    /**
     * Consulta cobranças imediatas através de parâmetros como início, fim, cpf, cnpj e status.
     *
     * @param dataInicial Filtra os registros cuja data de criação seja maior ou igual que a data de início. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param dataFinal   Filtra os registros cuja data de criação seja menor ou igual que a data de fim. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param filtro      Filtros para consulta(opcional, pode ser null).
     * @return lista de cobranças
     * @see <a href="https://developers.inter.co/references/pix#tag/Cobranca-Imediata/paths/~1cob/get">Consultar lista de cobranças imediatas</a>
     */
    public List<CobrancaDetalhada> consultarCobrancasImediatas(String dataInicial, String dataFinal, FiltroConsultarCobrancasImediatas filtro) throws SdkException {
        if (consultarCobrancasImediatas == null) {
            consultarCobrancasImediatas = new ConsultarCobrancasImediatas();
        }
        return consultarCobrancasImediatas.consultar(config, dataInicial, dataFinal, filtro);
    }

    /**
     * Consulta cobranças imediatas através de parâmetros como início, fim, cpf, cnpj e status, com paginação.
     *
     * @param dataInicial Filtra os registros cuja data de criação seja maior ou igual que a data de início. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param dataFinal   Filtra os registros cuja data de criação seja menor ou igual que a data de fim. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param filtro      Filtros para consulta(opcional, pode ser null).
     * @param pagina      Número da página iniciando com 0.
     * @return página com lista de cobranças
     * @see <a href="https://developers.inter.co/references/pix#tag/Cobranca-Imediata/paths/~1cob/get">Consultar lista de cobranças imediatas</a>
     */
    public PaginaCobrancas consultarCobrancasImediatas(String dataInicial, String dataFinal, FiltroConsultarCobrancasImediatas filtro, int pagina) throws SdkException {
        if (consultarCobrancasImediatas == null) {
            consultarCobrancasImediatas = new ConsultarCobrancasImediatas();
        }
        return consultarCobrancasImediatas.consultar(config, dataInicial, dataFinal, pagina, null, filtro);
    }

    /**
     * Consulta cobranças imediatas através de parâmetros como início, fim, cpf, cnpj e status, com paginação.
     *
     * @param dataInicial   Filtra os registros cuja data de criação seja maior ou igual que a data de início. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param dataFinal     Filtra os registros cuja data de criação seja menor ou igual que a data de fim. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param filtro        Filtros para consulta(opcional, pode ser null).
     * @param pagina        Número da página iniciando com 0.
     * @param tamanhoPagina Tamanho da páfina, padrão = 50.
     * @return página com lista de cobranças
     * @see <a href="https://developers.inter.co/references/pix#tag/Cobranca-Imediata/paths/~1cob/get">Consultar lista de cobranças imediatas</a>
     */
    public PaginaCobrancas consultarCobrancasImediatas(String dataInicial, String dataFinal, FiltroConsultarCobrancasImediatas filtro, int pagina, int tamanhoPagina) throws SdkException {
        if (consultarCobrancasImediatas == null) {
            consultarCobrancasImediatas = new ConsultarCobrancasImediatas();
        }
        return consultarCobrancasImediatas.consultar(config, dataInicial, dataFinal, pagina, tamanhoPagina, filtro);
    }

    /**
     * Cria uma cobrança com vencimento
     *
     * @param cobranca Cobrança a ser criada
     * @return cobranca Cobrança que foi criada
     * @see <a href="https://developers.inter.co/references/pix#tag/Cobranca-com-Vencimento/paths/~1cobv~1%7Btxid%7D/put">Criar cobrança com vencimento</a>
     */
    public CobrancaVencimentoDetalhada criarCobrancaComVencimentoTxId(String txid, CobrancaVencimento cobranca) throws SdkException {
        if (criarCobrancaComVencimento == null) {
            criarCobrancaComVencimento = new CriarCobrancaComVencimento();
        }
        return criarCobrancaComVencimento.criar(config, txid, cobranca);
    }

    /**
     * Revisa uma cobrança com Vencimento.
     *
     * @param cobranca Cobrança a ser criada
     * @return cobranaa Cobrança que foi criada
     * @see <a href="https://developers.inter.co/references/pix#tag/Cobranca-com-Vencimento/paths/~1cobv~1%7Btxid%7D/patch">Revisar cobrança imediata</a>
     */
    public CobrancaVencimentoDetalhada revisarCobrancaComVencimento(String txid, CobrancaVencimento cobranca) throws SdkException {
        if (revisarCobrancaComVencimento == null) {
            revisarCobrancaComVencimento = new RevisarCobrancaComVencimento();
        }
        return revisarCobrancaComVencimento.revisar(config, txid, cobranca);
    }

    /**
     * Consulta uma cobrança com vencimento através de um determinado txid.
     * @param txId  identificador da cobrança
     * @return cobrança
     * @see <a href="https://developers.inter.co/references/pix#tag/Cobranca-com-Vencimento/paths/~1cobv~1%7Btxid%7D/get">Consultar cobrança imediata</a>
     */
    public CobrancaVencimentoDetalhada consultarCobrancaComVencimento(String txId) throws SdkException {
        if (consultarCobrancaComVencimento == null) {
            consultarCobrancaComVencimento = new ConsultarCobrancaComVencimento();
        }
        return consultarCobrancaComVencimento.consultar(config, txId);
    }

    /**
     * Consulta cobranças com vencimento através de parâmetros como início, fim, cpf, cnpj e status.
     *
     * @param dataInicial Filtra os registros cuja data de criação seja maior ou igual que a data de início. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param dataFinal   Filtra os registros cuja data de criação seja menor ou igual que a data de fim. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param filtro      Filtros para consulta(opcional, pode ser null).
     * @return lista de cobranças
     * @see <a href="https://developers.inter.co/references/pix#tag/Cobranca-com-Vencimento/paths/~1cobv/get">Consultar lista de cobranças imediatas</a>
     */
    public List<CobrancaVencimentoDetalhada> consultarCobrancasComVencimento(String dataInicial, String dataFinal, FiltroConsultarCobrancasComVencimento filtro) throws SdkException {
        if (consultarCobrancasComVencimento == null) {
            consultarCobrancasComVencimento = new ConsultarCobrancasComVencimento();
        }
        return consultarCobrancasComVencimento.consultar(config, dataInicial, dataFinal, filtro);
    }

    public PaginaCobrancasVencimento consultarCobrancasComVencimento(String dataInicial, String dataFinal, FiltroConsultarCobrancasComVencimento filtro, int pagina) throws SdkException {
        if (consultarCobrancasComVencimento == null) {
            consultarCobrancasComVencimento = new ConsultarCobrancasComVencimento();
        }
        return consultarCobrancasComVencimento.consultar(config, dataInicial, dataFinal, pagina, null, filtro);
    }

    public PaginaCobrancasVencimento consultarCobrancasComVencimento(String dataInicial, String dataFinal, FiltroConsultarCobrancasComVencimento filtro, int pagina, int tamanhoPagina) throws SdkException {
        if (consultarCobrancasComVencimento == null) {
            consultarCobrancasComVencimento = new ConsultarCobrancasComVencimento();
        }
        return consultarCobrancasComVencimento.consultar(config, dataInicial, dataFinal, pagina, tamanhoPagina, filtro);
    }

    /**
     * Cria location do payload
     *
     * @param tipoCob cov|cobv
     * @return dados da location
     * @see <a href="https://developers.inter.co/references/pix#tag/Location/paths/~1loc/post">Criar location do payload</a>
     */
    public Location criarLocation(TipoCob tipoCob) throws SdkException {
        if (criarLocation == null) {
            criarLocation = new CriarLocation();
        }
        return criarLocation.criar(config, tipoCob);
    }

    /**
     * Consulta locations cadastradas.
     *
     * @param dataInicial Filtra os registros cuja data de criação seja maior ou igual que a data de início. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param dataFinal   Filtra os registros cuja data de criação seja menor ou igual que a data de fim. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param filtro      Filtros para consulta(opcional, pode ser null).
     * @return lista de locations
     * @see <a href="https://developers.inter.co/references/pix#tag/Location/paths/~1loc/get">Consultar locations cadastradas</a>
     */
    public List<Location> consultarLocationsCadastradas(String dataInicial, String dataFinal, FiltroConsultarLocations filtro) throws SdkException {
        if (consultarLocationsCadastradas == null) {
            consultarLocationsCadastradas = new ConsultarLocationsCadastradas();
        }
        return consultarLocationsCadastradas.consultar(config, dataInicial, dataFinal, filtro);
    }

    /**
     * Consulta locations cadastradas.
     *
     * @param dataInicial Filtra os registros cuja data de criação seja maior ou igual que a data de início. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param dataFinal   Filtra os registros cuja data de criação seja menor ou igual que a data de fim. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param filtro      Filtros para consulta(opcional, pode ser null).
     * @param pagina      Número da página iniciando com 0.
     * @return página com lista de locations
     * @see <a href="https://developers.inter.co/references/pix#tag/Location/paths/~1loc/get">Consultar locations cadastradas</a>
     */
    public PaginaLocations consultarLocationsCadastradas(String dataInicial, String dataFinal, FiltroConsultarLocations filtro, int pagina) throws SdkException {
        if (consultarLocationsCadastradas == null) {
            consultarLocationsCadastradas = new ConsultarLocationsCadastradas();
        }
        return consultarLocationsCadastradas.consultar(config, dataInicial, dataFinal, pagina, null, filtro);
    }

    /**
     * Consulta locations cadastradas.
     *
     * @param dataInicial   Filtra os registros cuja data de criação seja maior ou igual que a data de início. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param dataFinal     Filtra os registros cuja data de criação seja menor ou igual que a data de fim. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param filtro        Filtros para consulta(opcional, pode ser null).
     * @param pagina        Número da página iniciando com 0.
     * @param tamanhoPagina Tamanho da páfina, padrão = 100.
     * @return página com lista de locations
     * @see <a href="https://developers.inter.co/references/pix#tag/Location/paths/~1loc/get">Consultar locations cadastradas</a>
     */
    public PaginaLocations consultarLocationsCadastradas(String dataInicial, String dataFinal, FiltroConsultarLocations filtro, int pagina, int tamanhoPagina) throws SdkException {
        if (consultarLocationsCadastradas == null) {
            consultarLocationsCadastradas = new ConsultarLocationsCadastradas();
        }
        return consultarLocationsCadastradas.consultar(config, dataInicial, dataFinal, pagina, tamanhoPagina, filtro);
    }

    /**
     * Recupera a location do payload.
     *
     * @param id identificador da location
     * @return location
     * @see <a href="https://developers.inter.co/references/pix#tag/Location/paths/~1loc~1%7Bid%7D/get">Recuperar location do payload</a>
     */
    public Location recuperarLocation(String id) throws SdkException {
        if (recuperarLocation == null) {
            recuperarLocation = new RecuperarLocation();
        }
        return recuperarLocation.recuperar(config, id);
    }

    /**
     * Endpoint utilizado para desvincular uma cobrança de uma location.
     * <p>Se executado com sucesso, a entidade loc não apresentará mais um txid,
     * se apresentava anteriormente à chamada. Adicionalmente, a entidade cob ou cobv associada ao
     * txid desvinculado também passará a não mais apresentar um location. Esta operação
     * não altera o status da cob ou cobv em questão.</p>
     *
     * @param id identificador da location
     * @return cobrança representada pelo txid informado desvinculada com sucesso.
     * @see <a href="https://developers.inter.co/references/pix#tag/Location/paths/~1loc~1%7Bid%7D~1txid/delete">Desvincular uma cobrança de uma location</a>
     */
    public Location desvincularLocation(String id) throws SdkException {
        if (desvincularLocation == null) {
            desvincularLocation = new DesvincularLocation();
        }
        return desvincularLocation.desvincular(config, id);
    }

    /**
     * Consulta um pix através de um determinado EndToEndId.
     *
     * @param e2eId Id único para identificação do pagamento Pix.
     * @return pix
     * @see <a href="https://developers.bancointer.com.br/v4/reference/get_pix-e2eid">Consultar pix</a>
     */
    public Pix consultarPix(String e2eId) throws SdkException {
        if (consultarPix == null) {
            consultarPix = new ConsultarPix();
        }
        return consultarPix.consultar(config, e2eId);
    }

    /**
     * Consulta pix por um período específico, de acordo com os parâmetros informados.
     *
     * @param dataInicial Filtra os registros cuja data de criação seja maior ou igual que a data de início. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param dataFinal   Filtra os registros cuja data de criação seja menor ou igual que a data de fim. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param filtro      Filtros para consulta(opcional, pode ser null).
     * @return lista de pix recebidos
     * @see <a href="https://developers.inter.co/references/pix#tag/Pix/paths/~1pix~1%7Be2eId%7D/get">Consultar pix recebidos</a>
     */
    public List<Pix> consultarPixRecebidos(String dataInicial, String dataFinal, FiltroConsultarPixRecebidos filtro) throws SdkException {
        if (consultarPixRecebidos == null) {
            consultarPixRecebidos = new ConsultarPixRecebidos();
        }
        return consultarPixRecebidos.consultar(config, dataInicial, dataFinal, filtro);
    }

    /**
     * Consulta pix por um período específico, de acordo com os parâmetros informados.
     *
     * @param dataInicial Filtra os registros cuja data de criação seja maior ou igual que a data de início. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param dataFinal   Filtra os registros cuja data de criação seja menor ou igual que a data de fim. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param filtro      Filtros para consulta(opcional, pode ser null).
     * @param pagina      Número da página iniciando com 0.
     * @return página com lista de pix recebidos
     * @see <a href="https://developers.inter.co/references/pix#tag/Pix/paths/~1pix/get">Consultar pix recebidos</a>
     */
    public PaginaPix consultarPixRecebidos(String dataInicial, String dataFinal, FiltroConsultarPixRecebidos filtro, int pagina) throws SdkException {
        if (consultarPixRecebidos == null) {
            consultarPixRecebidos = new ConsultarPixRecebidos();
        }
        return consultarPixRecebidos.consultar(config, dataInicial, dataFinal, pagina, null, filtro);
    }

    /**
     * Consulta pix por um período específico, de acordo com os parâmetros informados.
     *
     * @param dataInicial   Filtra os registros cuja data de criação seja maior ou igual que a data de início. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param dataFinal     Filtra os registros cuja data de criação seja menor ou igual que a data de fim. Formato yyyy-MM-ddTHH:mm:ssZ.
     * @param filtro        Filtros para consulta(opcional, pode ser null).
     * @param pagina        Número da página iniciando com 0.
     * @param tamanhoPagina Tamanho da páfina, padrão = 100.
     * @return página com lista de pix recebidos
     * @see <a href="https://developers.bancointer.com.br/v4/reference/get_pix">Consultar pix recebidos</a>
     */
    public PaginaPix consultarPixRecebidos(String dataInicial, String dataFinal, FiltroConsultarPixRecebidos filtro, int pagina, int tamanhoPagina) throws SdkException {
        if (consultarPixRecebidos == null) {
            consultarPixRecebidos = new ConsultarPixRecebidos();
        }
        return consultarPixRecebidos.consultar(config, dataInicial, dataFinal, pagina, tamanhoPagina, filtro);
    }

    /**
     * Solicitauma devolução através de um E2EID do Pix e do ID da devolução.
     *
     * @param e2eId     Id único para identificação do pagamento Pix.
     * @param id        Id gerado pelo cliente para representar unicamente uma devolução.
     * @param devolucao Dados da devolução.
     * @return devolução detalhada
     * @see <a href="https://developers.inter.co/references/pix#tag/Pix/paths/~1pix~1%7Be2eId%7D~1devolucao~1%7Bid%7D/put">Consultar devolução</a>
     */
    public DevolucaoDetalhada solicitarDevolucao(String e2eId, String id, Devolucao devolucao) throws SdkException {
        if (solicitarDevolucao == null) {
            solicitarDevolucao = new SolicitarDevolucao();
        }
        return solicitarDevolucao.solicitar(config, e2eId, id, devolucao);
    }

    /**
     * Consulta uma devolução através de um E2EID do Pix e do ID da devolução.
     *
     * @param e2eId Id único para identificação do pagamento Pix.
     * @param id    Id gerado pelo cliente para representar unicamente uma devolução.
     * @return devolução
     * @see <a href="https://developers.inter.co/references/pix#tag/Pix/paths/~1pix~1%7Be2eId%7D~1devolucao~1%7Bid%7D/get">Consultar devolução</a>
     */
    public DevolucaoDetalhada consultarDevolucao(String e2eId, String id) throws SdkException {
        if (consultarDevolucao == null) {
            consultarDevolucao = new ConsultarDevolucao();
        }
        return consultarDevolucao.consultar(config, e2eId, id);
    }

    /**
     * Método destinado a criar um webhook para receber notificações de cobranças Pix recebidas (callbacks).
     *
     * @param webhookUrl
     * @param chave      Chave pix
     * @see <a href="https://developers.inter.co/references/pix#tag/Webhook/operation/webhookPut">Criar webhook</a>
     */
    public void criarWebhook(String webhookUrl, String chave) throws SdkException {
        if (criarWebhook == null) {
            criarWebhook = new CriarWebhook();
        }
        criarWebhook.criar(config, webhookUrl, chave);
    }

    /**
     * Obter webhook cadastrado
     *
     * @param chave Chave pix
     * @return webhook
     * @see <a href="https://developers.inter.co/references/pix#tag/Webhook/operation/webhookGet">Obter webhook cadastrado</a>
     */
    public Webhook obterWebhook(String chave) throws SdkException {
        if (obterWebhook == null) {
            obterWebhook = new ObterWebhook();
        }
        return obterWebhook.obter(config, chave);
    }

    /**
     * Exclui o webhook
     *
     * @param chave Chave pix
     * @see <a href="https://developers.inter.co/references/pix#tag/Webhook/operation/webhookDelete">Excluir webhook</a>
     */
    public void excluirWebhook(String chave) throws SdkException {
        if (excluirWebhook == null) {
            excluirWebhook = new ExcluirWebhook();
        }
        excluirWebhook.excluir(config, chave);
    }

    public PaginaCallbacks consultarCallbacks(String dataHoraInicio, String dataHoraFim, int tamanhoPagina, int pagina, FiltroBuscarCallbacks filtro) throws SdkException {
        if (consultarCallbacks== null) {
            consultarCallbacks = new ConsultarCallbacks();
        }
        return consultarCallbacks.buscar(config, dataHoraInicio, dataHoraFim, pagina, tamanhoPagina, filtro);
    }

    public List<Callback> consultarCallbacks(String dataHoraInicio, String dataHoraFim, FiltroBuscarCallbacks filtro) throws SdkException {
        if (consultarCallbacks== null) {
            consultarCallbacks = new ConsultarCallbacks();
        }
        return consultarCallbacks.buscar(config, dataHoraInicio, dataHoraFim, filtro);
    }

}
