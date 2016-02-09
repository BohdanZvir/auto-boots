package hello.ria.service;

import hello.ria.model.SelectorProps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bohdan on 05.02.16.
 */
@Service
public class DataExtractorImpl implements DataExtractor {

    @Autowired
    private Environment env;

    @Override
    public Map<SelectorProps, Object> getCarData(String html) {
        Map<SelectorProps, Object> carData = new HashMap<>();
        Document doc = Jsoup.parse(html);
        for (SelectorProps prop : SelectorProps.values()) {
            String value = env.getProperty(prop.getKey());
            Elements trans = doc.select(value);
            carData.put(prop, trans.text());
        }
        return carData;
    }

    public String callURL(String textUrl) {
        StringBuilder response = new StringBuilder();
        URLConnection urlConn;
        try {
            URL url = new URL(textUrl);
            urlConn = url.openConnection();
            if (urlConn != null)
                urlConn.setReadTimeout(60 * 1000);
            if (urlConn != null && urlConn.getInputStream() != null) {
                try (
                        Reader in = new InputStreamReader(urlConn.getInputStream(),
                                Charset.defaultCharset());
                        BufferedReader bufferedReader = new BufferedReader(in)) {
                    if (bufferedReader != null) {
                        int cp;
                        while ((cp = bufferedReader.read()) != -1) {
                            response.append((char) cp);
                        }
                        bufferedReader.close();
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling URL:" + textUrl, e);
        }

        return response.toString();
    }


}
