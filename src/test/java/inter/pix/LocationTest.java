package inter.pix;

import inter.InterSdk;
import inter.TestUtils;
import inter.exceptions.SdkException;
import inter.pix.model.Location;
import inter.pix.model.enums.TipoCob;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@Disabled
@ExtendWith(MockitoExtension.class)
class LocationTest {
    static InterSdk interSdk;

    @BeforeAll
    static void setUp() throws SdkException {
        interSdk = TestUtils.buildSdk();
    }

    @Test
    void criar_location() {
        try {
            Location location = interSdk.pix().criarLocation(TipoCob.cob);
            assertNotNull(location);
            assertNotNull(location.getLocation());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void consultar_locations() {
        try {
            List<Location> locations = interSdk.pix().consultarLocationsCadastradas("2023-01-01T00:00:00Z", "2023-02-28T00:00:00Z", null);
            assertNotNull(locations);
            assertNotNull(locations.size() > 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void recuperar_location() {
        try {
            Location location = interSdk.pix().recuperarLocation("6272205");
            assertNotNull(location);
            assertNotNull(location.getLocation());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void desvincular_location() {
        try {
            Location location = interSdk.pix().desvincularLocation("6272205");
            assertNotNull(location);
            assertNotNull(location.getLocation());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
