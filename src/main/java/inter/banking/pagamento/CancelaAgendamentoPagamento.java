package inter.banking.pagamento;

import inter.exceptions.SdkException;
import inter.model.Config;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import static inter.constants.Constants.ESCOPO_PAGAMENTO_BOLETO_WRITE;
import static inter.constants.Constants.URL_BANKING_PAGAMENTO;

@Slf4j
public class CancelaAgendamentoPagamento {

    public void cancelar(Config config, String codigoTransacao) throws SdkException {
        log.info("Cancelando Agendamento de Pagamento {} {}", config.getClientId(), codigoTransacao);
        String url = URL_BANKING_PAGAMENTO.replace("AMBIENTE", config.getAmbiente()) + "/" + codigoTransacao;
        HttpUtils.callDelete(config, url, ESCOPO_PAGAMENTO_BOLETO_WRITE, "Erro ao cancelar agendamento");
    }

}
