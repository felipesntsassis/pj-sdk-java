//package inter.banking;
//
//import inter.InterSdk;
//import inter.TestUtils;
//import inter.banking.model.Favorecido;
//import inter.banking.model.InstituicaoFinanceira;
//import inter.banking.model.RespostaIncluirTed;
//import inter.banking.model.Ted;
//import inter.banking.model.enums.OrigemLogin;
//import inter.banking.model.enums.TipoTransferencia;
//import inter.exceptions.SdkException;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.fail;
//
//@Disabled
//@ExtendWith(MockitoExtension.class)
//class TedTest {
//    static InterSdk interSdk;
//
//    @BeforeAll
//    static void setUp() throws SdkException {
//        interSdk = TestUtils.buildSdk();
//    }
//
//    @Test
//    void incluir_ted() {
//        try {
//            //TODO 500
//            InstituicaoFinanceira instituicaoFinanceira = InstituicaoFinanceira.builder()
//                    .codigo("077")
//                    .nome("Banco Inter")
//                    .build();
//            Favorecido favorecido = Favorecido.builder()
//                    .cpfCnpj("90388459018")
//                    .nome("teste sdk")
//                    .conta("9588752")
//                    .agencia("001")
//                    .instituicaoFinanceira(instituicaoFinanceira)
//                    .build();
//            LocalDateTime efetivacao = LocalDateTime.now().plusMonths(1);
//            String dataEfetivacao = efetivacao.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//            Ted ted = Ted.builder()
//                    .tipo(TipoTransferencia.CONTA_CORRENTE)
//                    .valor(BigDecimal.TEN)
//                    .finalidade("00010")
//                    .origem(OrigemLogin.WEB)
//                    .dataEfetivacao(dataEfetivacao)
//                    .favorecido(favorecido)
//                    .build();
//            RespostaIncluirTed resposta = interSdk.banking().incluirTed(ted);
//            assertNotNull(resposta);
//            assertNotNull(resposta.getTitulo());
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail();
//        }
//    }
//}
