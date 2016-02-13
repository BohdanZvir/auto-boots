package hello.ria.service;

import hello.Application;
import hello.ria.model.SelectorProps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by bohdan on 06.02.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class DataExtractorImplTest {

    public static final String URL = "https://auto.ria.com/auto_hyundai_i30%20_16876216.html";
    @Autowired
    private DataExtractor extractor;

    @Test
    public void getCarData() throws Exception {
        Map<String, ?> carData = extractor.getCarData(URL);
        for (Map.Entry<String, ?> entry : carData.entrySet()){
            System.out.printf(" %s : %s \n", entry.getKey(), entry.getValue());
        }
        assertNotNull(carData);
        assertFalse(carData.isEmpty());
        assertTrue(carData.containsKey(SelectorProps.DESCRIPTION.toString()));
        Object desc = carData.get(SelectorProps.DESCRIPTION.toString());
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