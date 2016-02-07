package hello.ria.service;

import hello.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by bohdan on 06.02.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class DataExtractorImplTest {

    @Autowired
    private DataExtractor extractor;

    @Test
    public void testGetCarData() throws Exception {

    }

    @Test
    public void testCallURL() throws Exception {

        System.out.println(extractor.callURL("http://www.google.com.ua"));
    }
}