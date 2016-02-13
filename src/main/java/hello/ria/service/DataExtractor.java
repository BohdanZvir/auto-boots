package hello.ria.service;

import java.util.Map;

/**
 * Created by bohdan on 05.02.16.
 */
public interface DataExtractor {
    Map<String, String> getCarData(String url);
    String callURL(String textUrl);
}
