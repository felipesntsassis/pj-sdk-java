package inter.banking;

import inter.InterSdk;
import inter.TestUtils;
import inter.banking.model.Extrato;
import inter.banking.model.Saldo;
import inter.banking.model.TransacaoEnriquecida;
import inter.exceptions.SdkException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@ExtendWith(MockitoExtension.class)
class ExtratoSaldoTest {
    static InterSdk interSdk;

    @BeforeAll
    static void setUp() throws SdkException {
        interSdk = TestUtils.buildSdk();
    }

    @Test
    void consultar_extrato() {
        try {
            Extrato extrato = interSdk.banking().consultarExtrato("2023-02-01", "2023-02-28");
            assertNotNull(extrato);
            assertNotNull(extrato.getTransacoes());
            assertTrue(extrato.getTransacoes().size() > 0);
            System.out.println("consultarExtrato " + extrato.getTransacoes().size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void recuperar_extrato_pdf() {
        try {
            if (new File("extrato.pdf").exists()) {
                new File("extrato.pdf").delete();
            }
            interSdk.banking().recuperarExtratoPdf("2023-02-01", "2023-02-28", "extrato.pdf");
            if (!new File("extrato.pdf").exists()) {
                fail();
            }
            System.out.println("recuperarExtratoPdf extrato.pdf " + Files.size(Paths.get("extrato.pdf")));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void consultar_extrato_detalhado() {
        try {
            List<TransacaoEnriquecida> transacoes = interSdk.banking().consultarExtratoEnriquecido("2023-02-01", "2023-02-28", null);
            assertNotNull(transacoes);
            assertTrue(transacoes.size() > 0);
            System.out.println("consultarExtratoEnriquecido " + transacoes.size());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void consultar_saldo() {
        try {
            Saldo saldo = interSdk.banking().consultarSaldo();
            assertNotNull(saldo);
            System.out.println("consultarSaldo " + saldo.getDisponivel());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
