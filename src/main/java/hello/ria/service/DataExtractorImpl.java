package hello.ria.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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

    public static final String DESCRIPTION = "#description";
    public static final String DESC1 = "#final_page__main_info_block > div.span5 > div > div > section:nth-child(10) > div.title.bold";
    public static final String TRANSMISSION = "#final_page__main_info_block > div.span5 > div > div > section:nth-child(10) > div.box-panel.rocon > dl > dd:nth-child(2) > strong";

    @Override
    public Map<String, ?> getCarData(String html) {
        Map<String, Object> carData = new HashMap<>();
        Document doc = Jsoup.parse(html);
        Elements links = doc.select(DESC1);
        System.out.println("\n\n" +links.text() + "\n");

        carData.put(DESCRIPTION, links.text());
        Elements desc = doc.select(DESC1);
        System.out.println(desc);

        Elements trans = doc.select(TRANSMISSION);
        System.out.println(trans);
        Elements desc3 = doc.select(DESC1);
        return carData;
    }

    public String callURL(String textUrl) {
//        System.out.println("Requeted URL:" + textUrl);
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
