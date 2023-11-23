package inter.constants;

public class Constants {
    private Constants() {
    }

    private static final String URL_BASE = "https://cdpj.partners.AMBIENTE.com.br";
    public static final String URL_TOKEN = URL_BASE + "/oauth/v2/token";
    public static final String CALLBACKS = "callbacks";
    //Banking
    public static final String URL_BANKING_SALDO = URL_BASE + "/banking/v2/saldo";
    public static final String URL_BANKING_EXTRATO = URL_BASE + "/banking/v2/extrato";
    public static final String URL_BANKING_EXTRATO_ENRIQUECIDO = URL_BASE + "/banking/v2/extrato/completo";
    public static final String URL_BANKING_EXTRATO_PDF = URL_BASE + "/banking/v2/extrato/exportar";
    public static final String URL_BANKING_PAGAMENTO = URL_BASE + "/banking/v2/pagamento";
    public static final String URL_BANKING_PAGAMENTO_DARF = URL_BASE + "/banking/v2/pagamento/darf";
    public static final String URL_BANKING_PAGAMENTO_LOTE = URL_BASE + "/banking/v2/pagamento/lote";
    public static final String URL_BANKING_PIX_PAGAMENTO = URL_BASE + "/banking/v2/pix";
    //public static final String URL_BANKING_TED = URL_BASE + "/banking/v2/ted";
    public static final String URL_BANKING_WEBHOOK = URL_BASE + "/banking/v2/webhooks";

    //Pix
    public static final String URL_PIX_PIX = URL_BASE + "/pix/v2/pix";
    public static final String URL_PIX_LOCATIONS = URL_BASE + "/pix/v2/loc";
    public static final String URL_PIX_COBRANCAS_IMEDIATAS = URL_BASE + "/pix/v2/cob";
    public static final String URL_PIX_COBRANCA_COM_VENCIMENTO = URL_BASE + "/pix/v2/cobv";
    public static final String URL_PIX_WEBHOOK_PIX = URL_BASE + "/pix/v2/webhook";

    //Cobranca
    public static final String URL_COBRANCA_BOLETOS = URL_BASE + "/cobranca/v2/boletos";
    public static final String URL_COBRANCA_SUMARIO_BOLETOS = URL_BASE + "/cobranca/v2/boletos/sumario";
    public static final String URL_COBRANCA_WEBHOOK_COBRANCA = URL_BASE + "/cobranca/v2/boletos/webhook";

    public static final String DOC_CERTIFICADO = "https://developers.bancointer.com.br/v4/docs/onde-obter-o-certificado";

    public static final String ESCOPO_EXTRATO_READ = "extrato.read";
    public static final String ESCOPO_PIX_READ = "pix.read";
    public static final String ESCOPO_PIX_WRITE = "pix.write";
    public static final String ESCOPO_BOLETO_COBRANCA_READ = "boleto-cobranca.read";
    public static final String ESCOPO_BOLETO_COBRANCA_WRITE = "boleto-cobranca.write";
    public static final String ESCOPO_PAGAMENTO_BOLETO_READ = "pagamento-boleto.read";
    public static final String ESCOPO_PAGAMENTO_BOLETO_WRITE = "pagamento-boleto.write";
    public static final String ESCOPO_PAGAMENTO_DARF_WRITE = "pagamento-darf.write";
    public static final String ESCOPO_PAGAMENTOS_LOTE_READ= "pagamento-lote.read";
    public static final String ESCOPO_PAGAMENTOS_LOTE_WRITE = "pagamento-lote.write pagamento-boleto.write pagamento-darf.write";
    public static final String ESCOPO_PAGAMENTO_PIX_READ = "pagamento-pix.read";
    public static final String ESCOPO_PAGAMENTO_PIX_WRITE = "pagamento-pix.write";
    //public static final String ESCOPO_TED_WRITE = "ted.write";

    public static final String ESCOPO_PIX_COBRANCA_READ = "cob.read";
    public static final String ESCOPO_PIX_COBRANCA_WRITE = "cob.write";
    public static final String ESCOPO_PIX_COBRANCA_VENCIMENTO_WRITE = "cobv.write";
    public static final String ESCOPO_PIX_COBRANCA_VENCIMENTO_READ = "cobv.read";

    public static final String ESCOPO_LOCATION_READ = "payloadlocation.read";
    public static final String ESCOPO_LOCATION_WRITE = "payloadlocation.write";
    public static final String ESCOPO_WEBHOOK_READ = "webhook.read";
    public static final String ESCOPO_WEBHOOK_WRITE = "webhook.write";
    public static final String ESCOPO_WEBHOOK_BANKING_READ = "webhook-banking.read";
    public static final String ESCOPO_WEBHOOK_BANKING_WRITE = "webhook-banking.write";

    public static final int DAYS_TO_EXPIRE = 30;

    public static final String CERTIFICATE_EXCEPTION_MESSAGE = "Erro no Certificado!";
    public static final String GENERIC_EXCEPTION_MESSAGE = "Erro durante execução do SDK!";

}
