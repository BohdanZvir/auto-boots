package hello.ria.communicator;

import hello.ria.AbstractBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by bohdan on 21.02.16.
 */
public class UrlBuilderImplTest extends AbstractBaseTest {

    @Autowired
    private UrlBuilder urlBuilder;

    @Test
    public void getAvarege() {
        String avarege = urlBuilder.getAvarege(DEFAULT_CATEGORY, 24, 239);
        assertNotNull(avarege);
        assertFalse(avarege.isEmpty());
    }
}