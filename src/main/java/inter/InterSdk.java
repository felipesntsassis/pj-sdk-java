package inter;

import inter.banking.BankingSdk;
import inter.cobranca.CobrancaSdk;
import inter.cobranca.model.Pessoa;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.pix.PixSdk;
import inter.utils.SslUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static inter.constants.Constants.DAYS_TO_EXPIRE;

@Slf4j
public class InterSdk {
    @Getter
    private final Config config;
    private BankingSdk bankingSdk;
    private PixSdk pixSdk;
    private CobrancaSdk cobrancaSdk;
    private final List<String> avisos;
    public static final String VERSION = "inter-sdk-java v1.0.1.2023-09-26";

    /**
     * Sdk para API de cobrança
     * @return sdk
     */
    public CobrancaSdk cobranca() {
        Pessoa pessoa = new Pessoa();
        pessoa.setCpfCnpj("");
        if (cobrancaSdk == null) {
            cobrancaSdk = new CobrancaSdk(config);
        }
        return cobrancaSdk;
    }

    /**
     * Sdk para API banking
     * @return sdk
     */
    public BankingSdk banking() {
        if (bankingSdk == null) {
            bankingSdk = new BankingSdk(config);
        }
        return bankingSdk;
    }

    /**
     * Sdk para API pix
     * @return sdk
     */
    public PixSdk pix() {
        if (pixSdk == null) {
            pixSdk = new PixSdk(config);
        }
        return pixSdk;
    }

    /**
     * Sdk para acesso às APIs PJ do Inter
     * @param clientId          identificador da aplicação
     * @param clientSecret      segredo da aplicação
     * @param certificado       arquivo do certificado, ex: certs/inter.pfx
     * @param senhaCertificado  senha do certificado
     */
    public InterSdk(String clientId, String clientSecret, String certificado, String senhaCertificado) throws SdkException {
        config = Config.builder()
                .ambiente("bancointer")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .certificado(certificado)
                .senha(senhaCertificado)
                .controleRateLimit(true)
                .build();
        if (!new File("logs").exists()) {
            new File("logs").mkdir();
        }
        String tomorrow = "logs/inter-sdk-" + LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ofPattern("EEE")) + ".log";
        if (new File(tomorrow).exists()) {
            new File(tomorrow).delete();
        }
        log.info(VERSION);
        avisos = new ArrayList<>();
        Date notAfter = SslUtils.isCloseToExpire(certificado, senhaCertificado);
        if (notAfter != null) {
            avisos.add(String.format("Certificado próximo de expirar. Menos de %d dias. Expira em %s.", DAYS_TO_EXPIRE, notAfter));
        }
    }

    public InterSdk(String ambiente, String clientId, String clientSecret, String certificado, String senhaCertificado) throws SdkException {
        this(clientId, clientSecret, certificado, senhaCertificado);
        this.setAmbiente(ambiente);
    }

    /**
     * Retorna a lista de avisos da última operação
     * @return lista de avisos, pode ser vazia
     */
    public List<String> listaAvisos() {
        return avisos;
    }

    /**
     * Configura o modo de debug. No modo debug os dados das requisições e das respostas serão gravados no log.
     */
    public void setDebug(boolean debug) {
        config.setDebug(debug);
    }

    /**
     * Seleciona o ambiente
     * @param ambiente uatbi|bancointer - padrão=bancointer
     */
    public void setAmbiente(String ambiente) {
        config.setAmbiente(ambiente);
    }

    /**
     * Indica se vai fazer controle automático de rate limit
     * @param controle indica se o SDK vai fazer o controle automático - padrão=true
     */
    public void setControleRateLimit(boolean controle) {
        config.setControleRateLimit(controle);
    }

    /**
     * Seleciona a conta corrente.
     * Necessário apenas se a aplicação estiver configurada com múltiplas contas.
     * @param contaCorrente número da conta corrente
     */
    public void setContaCorrente(String contaCorrente) {
        config.setContaCorrente(contaCorrente);
    }

    /**
     * Retorna a conta corrente selecionada.
     * @return conta corrente selecionada
     */
    public String getContaCorrente() {
        return config.getContaCorrente();
    }
}
