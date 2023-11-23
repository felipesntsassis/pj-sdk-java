package inter.cobranca;

import inter.InterSdk;
import inter.TestUtils;
import inter.exceptions.SdkException;
import inter.model.Webhook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@ExtendWith(MockitoExtension.class)
class WebhookCobrancaTestRemover {
    static InterSdk interSdk;

    @BeforeAll
    static void setUp() throws SdkException {
        interSdk = TestUtils.buildSdk();
    }

    @Test
    void criar_excluir_webhook() {
        try {
            String actual = null;
            try {
                Webhook webhook = interSdk.cobranca().obterWebhook();
                actual = webhook.getWebhookUrl();
            } catch (Exception e) {
            }

            interSdk.cobranca().criarWebhook("https://webhook.site/b166ebb1-d9ed-4215-82b8-147828761cf6");
            System.out.println("criarWebhook");

            Webhook webhook = interSdk.cobranca().obterWebhook();
            assertNotNull(webhook);
            assertNotNull(webhook.getWebhookUrl());
            System.out.println("obterWebhook " + webhook.getWebhookUrl());

            interSdk.cobranca().excluirWebhook();
            System.out.println("excluirWebhook");

            try {
                interSdk.cobranca().obterWebhook();
                fail();
            } catch (Exception e) {
            }

            if (actual != null) {
                interSdk.cobranca().criarWebhook(actual);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
