package hello.ria.service;

import hello.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static hello.ria.service.DataExtractor.DESCRIPTION;
import static org.junit.Assert.*;

/**
 * Created by bohdan on 06.02.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class DataExtractorImplTest {

    @Autowired
    private DataExtractor extractor;

    @Test
    public void getCarData() throws Exception {
        String html = extractor.callURL("https://auto.ria.com/auto_hyundai_i30%20_16876216.html");
        Map<String, ?> carData = extractor.getCarData(html);
        assertNotNull(carData);
        assertFalse(carData.isEmpty());
        assertTrue(carData.containsKey(DESCRIPTION));
        Object desc = carData.get(DESCRIPTION);
        assertNotNull(desc);
    }

    @Test
    public void callURL() throws Exception {
        String response = extractor.callURL("http://www.google.com.ua");
        System.out.println(response);
        assertNotNull(response);
        assertNotEquals("", response);
    }
}