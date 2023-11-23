//package inter.banking.pix;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import inter.banking.model.PixDetalhado;
//import inter.exceptions.SdkException;
//import inter.model.Config;
//import inter.model.Erro;
//import inter.utils.HttpUtils;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.IOException;
//
//import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
//import static inter.constants.Constants.ESCOPO_PAGAMENTO_PIX_READ;
//import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
//import static inter.constants.Constants.URL_BANKING_PIX_PAGAMENTO;
//
//@Slf4j
//public class ConsultarPix {
//
//    public PixDetalhado consultar(Config config, String e2eId) throws SdkException {
//        log.info("ConsultarPix {} {}", config.getClientId(), e2eId);
//        String url = URL_BANKING_PIX_PAGAMENTO.replace("AMBIENTE", config.getAmbiente()) + "/" + e2eId;
//        String json = HttpUtils.callGet(config, url, ESCOPO_PAGAMENTO_PIX_READ, "Erro ao consultar pix");
//        try {
//            return new ObjectMapper().readValue(json, PixDetalhado.class);
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
//
//}
