package hello.ria.communicator;

import hello.ria.AbstractBaseTest;
import hello.ria.service.SearchStateConfigurer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by bohdan on 21.02.16.
 */
public class UrlBuilderImplTest extends AbstractBaseTest {

//    private Logger logger = LoggerFactory.getLogger(UrlBuilderImplTest.class);


    @Autowired
    private UrlBuilder urlBuilder;
    @Autowired
    private SearchStateConfigurer stateConfigurer;
    private int markId = 24;
    private int modelId = 239;

    @Test
    public void getAvarege() {
        String avarege = urlBuilder.getAvarege(DEFAULT_CATEGORY, markId, modelId);
        assertNotNull(avarege);
        assertFalse(avarege.isEmpty());
    }

    @Test
    public void getAvaregeOptions() {
        Map<String, String> options = stateConfigurer.getDefaultOptions();
        options.put(MARKS, markId + "");
        options.put(MODELS, modelId + "");
        options.put(CATEGORIES, DEFAULT_CATEGORY + "");
        String avarege = urlBuilder.getAvarege(options);
        System.out.println("\n\n" + avarege + "\n");
//        logger.debug("Testing !! {}", avarege);
        assertNotNull(avarege);
        assertFalse(avarege.isEmpty());
    }
}