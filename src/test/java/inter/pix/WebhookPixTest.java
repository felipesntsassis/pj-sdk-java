package inter.pix;

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
class WebhookPixTest {
    static InterSdk interSdk;

    @BeforeAll
    static void setUp() throws SdkException {
        interSdk = TestUtils.buildSdk();
    }

    @Test
    void criar_excluir_webhook() {
        try {
            String chave = "90403518000169";
            String actual = null;
            try {
                Webhook webhook = interSdk.pix().obterWebhook(chave);
                actual = webhook.getWebhookUrl();
            } catch (Exception e) {
            }

            interSdk.pix().criarWebhook(chave, "https://webhook.site/b166ebb1-d9ed-4215-82b8-147828761cf6");
            System.out.println("criarWebhook");

            Webhook webhook = interSdk.pix().obterWebhook(chave);
            assertNotNull(webhook);
            assertNotNull(webhook.getWebhookUrl());
            System.out.println("obterWebhook " + webhook.getWebhookUrl());

            interSdk.pix().excluirWebhook(chave);
            System.out.println("excluirWebhook");

            try {
                interSdk.pix().obterWebhook(chave);
                fail();
            } catch (Exception e) {
            }

            if (actual != null) {
                interSdk.pix().criarWebhook(chave, actual);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
