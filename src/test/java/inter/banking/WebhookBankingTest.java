package inter.banking;

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
class WebhookBankingTest {
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
                Webhook webhook = interSdk.banking().obterWebhook();
                actual = webhook.getWebhookUrl();
            } catch (Exception e) {
            }

            interSdk.banking().criarWebhook("https://webhook.site/b166ebb1-d9ed-4215-82b8-147828761cf6");
            System.out.println("criarWebhook");

            Webhook webhook = interSdk.banking().obterWebhook();
            assertNotNull(webhook);
            assertNotNull(webhook.getWebhookUrl());
            System.out.println("obterWebhook " + webhook.getWebhookUrl());

            interSdk.banking().excluirWebhook();
            System.out.println("excluirWebhook");

            try {
                interSdk.banking().obterWebhook();
                fail();
            } catch (Exception e) {
            }

            if (actual != null) {
                interSdk.banking().criarWebhook(actual);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
