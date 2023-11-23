package inter.banking.pagamento;

import com.fasterxml.jackson.databind.ObjectMapper;
import inter.banking.model.BoletoLote;
import inter.banking.model.ProcessamentoLote;
import inter.banking.model.DarfLote;
import inter.banking.model.ItemLote;
import inter.exceptions.SdkException;
import inter.model.Config;
import inter.model.Erro;
import inter.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static inter.constants.Constants.CERTIFICATE_EXCEPTION_MESSAGE;
import static inter.constants.Constants.ESCOPO_PAGAMENTOS_LOTE_READ;
import static inter.constants.Constants.GENERIC_EXCEPTION_MESSAGE;
import static inter.constants.Constants.URL_BANKING_PAGAMENTO_LOTE;

@Slf4j
public class BuscarLotePagamentos {

    public ProcessamentoLote buscar(Config config, String idLote) throws SdkException {
        log.info("BuscarLotePagamentos {} {}", config.getClientId(), idLote);
        String url = URL_BANKING_PAGAMENTO_LOTE.replace("AMBIENTE", config.getAmbiente()) + "/" + idLote;
        String json = HttpUtils.callGet(config, url, ESCOPO_PAGAMENTOS_LOTE_READ, "Erro ao buscar lote");
        // não remover comentários com padrão //[, //], ou //=
        //[
        JSONParser parser = new JSONParser();
        //]
        try {
            //[
            JSONObject jsonLote = (JSONObject) parser.parse(json);
            JSONArray jsonArray = (JSONArray) jsonLote.get("pagamentos");
            List<ItemLote> pagamentos = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            if (jsonArray != null) {
                for (JSONObject item : (Iterable<JSONObject>) jsonArray) {
                    String tipoPagamento = (String) item.get("tipoPagamento");
                    if (tipoPagamento.equals("BOLETO")) {
                        BoletoLote boletoLote = objectMapper.readValue(item.toJSONString(), BoletoLote.class);
                        pagamentos.add(boletoLote);
                    } else {
                        DarfLote darfLote = objectMapper.readValue(item.toJSONString(), DarfLote.class);
                        pagamentos.add(darfLote);
                    }
                }
                jsonLote.put("pagamentos", null);
            }
            ProcessamentoLote processamentoLote = objectMapper.readValue(jsonLote.toJSONString(), ProcessamentoLote.class);
            processamentoLote.setPagamentos(pagamentos);
            return processamentoLote;
            //]
            //=return new ObjectMapper().readValue(json, ProcessamentoLote.class);
        } catch (IOException | ParseException e) {
            log.error(GENERIC_EXCEPTION_MESSAGE, e);
            throw new SdkException(
                    e.getMessage(),
                    Erro.builder()
                            .title(CERTIFICATE_EXCEPTION_MESSAGE).
                            detail(e.getMessage())
                            .build()
            );
        }
    }

}
