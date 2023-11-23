package inter.cobranca;

import inter.InterSdk;
import inter.TestUtils;
import inter.cobranca.model.Boleto;
import inter.cobranca.model.BoletoDetalhado;
import inter.cobranca.model.Pessoa;
import inter.cobranca.model.RespostaEmitirBoleto;
import inter.cobranca.model.Sumario;
import inter.cobranca.model.enums.MotivoCancelamento;
import inter.cobranca.model.enums.TipoPessoa;
import inter.exceptions.SdkException;
import inter.model.RespostaObterToken;
import inter.oauth.ObterToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@ExtendWith(MockitoExtension.class)
class BoletoTest {
    static InterSdk interSdk;

    @BeforeAll
    static void setUp() throws SdkException {
        interSdk = TestUtils.buildSdk();
    }

    @Test
    void obter_token() {
        try {
            ObterToken obterToken = new ObterToken();
            RespostaObterToken resposta = obterToken.obter(interSdk.getConfig(), "pix.read");
            assertNotNull(resposta);
            assertNotNull(resposta.getAccessToken());
            System.out.println("obterToken " + resposta.getAccessToken());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void recuperar_boletos() {
        try {
            List<BoletoDetalhado> boletos = interSdk.cobranca().recuperarBoletos("2023-02-01", "2023-02-28", null, null);
            assertNotNull(boletos);
            assertTrue(boletos.size() > 0);
            System.out.println("recuperarBoletos " + boletos.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void recuperar_sumario_boletos() {
        try {
            Sumario sumario = interSdk.cobranca().recuperarSumarioBoletos("2023-02-01", "2023-02-28", null);
            assertNotNull(sumario);
            assertNotNull(sumario.getVencidos());
            assertNotNull(sumario.getVencidos().getQuantidade());
            assertTrue(sumario.getVencidos().getQuantidade() > 0);
            System.out.println("recuperarSumarioBoletos " + sumario.getVencidos().getQuantidade());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void recuperar_boleto_detalhado() {
        try {
            BoletoDetalhado boleto = interSdk.cobranca().recuperarBoletoDetalhado("00789684380");
            assertNotNull(boleto);
            assertNotNull(boleto.getNossoNumero());
            System.out.println("recuperarBoletoDetalhado " + boleto.getNossoNumero());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void recuperar_boleto_pdf() {
        try {
            if (new File("boleto.pdf").exists()) {
                new File("boleto.pdf").delete();
            }
            interSdk.cobranca().recuperarBoletoPdf("00789684380", "boleto.pdf");
            if (!new File("boleto.pdf").exists()) {
                fail();
            }
            System.out.println("recuperarBoletoPdf boleto.pdf " + Files.size(Paths.get("boleto.pdf")));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void emitir_cancelar_consultar() {
        try {
            Pessoa pagador = Pessoa.builder()
                    .cpfCnpj("90388459018")
                    .tipoPessoa(TipoPessoa.FISICA)
                    .nome("Teste da SDK")
                    .endereco("Prédio do Inter")
                    .cidade("BH")
                    .uf("MG")
                    .cep("30000000")
                    .build();
            LocalDateTime vencimento = LocalDateTime.now().plusMonths(1);
            Boleto boleto = Boleto.builder()
                    .seuNumero(vencimento.format(DateTimeFormatter.ofPattern("ddHHmmss")))
                    .valorNominal(BigDecimal.valueOf(2.5))
                    .dataVencimento(vencimento.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .numDiasAgenda(0)
                    .pagador(pagador)
                    .build();
            RespostaEmitirBoleto resposta = interSdk.cobranca().emitirBoleto(boleto);
            assertNotNull(resposta);
            assertNotNull(resposta.getNossoNumero());
            String nossoNumero = resposta.getNossoNumero();
            ;
            System.out.println("emitirBoleto " + nossoNumero);

            // O boleto emitido estará disponível para consulta e pagamento, após um tempo apróximado de 5 minutos da sua inclusão. Esse tempo é necessário para o registro do boleto na CIP.
            for (int i = 0; i < 12; i++) {
                try {
                    BoletoDetalhado boletoDetalhado = interSdk.cobranca().recuperarBoletoDetalhado(nossoNumero);
                    assertNotNull(boletoDetalhado);
                    assertNotNull(boletoDetalhado.getSituacao());
                    System.out.println("recuperarBoletoDetalhado " + boletoDetalhado.getSituacao());
                    break;
                } catch (SdkException e) {
                    if (e.getErro() == null || !e.getErro().getTitle().equals("Não encontrado.")) {
                        break;
                    }
                } catch (Exception e) {
                    break;
                }
            }

            interSdk.cobranca().cancelarBoleto(nossoNumero, MotivoCancelamento.APEDIDODOCLIENTE);

            BoletoDetalhado boletoDetalhado = interSdk.cobranca().recuperarBoletoDetalhado(nossoNumero);
            assertNotNull(boletoDetalhado);
            assertNotNull(boletoDetalhado.getSituacao());
            //TODO situacao=EMABERTO?
            System.out.println("recuperarBoletoDetalhado " + boletoDetalhado.getMotivoCancelamento());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
