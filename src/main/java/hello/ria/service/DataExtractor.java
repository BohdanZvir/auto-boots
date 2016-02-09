package hello.ria.service;

import java.util.Map;

/**
 * Created by bohdan on 05.02.16.
 */
public interface DataExtractor {
    String DESCRIPTION = "Description";
    Map<String,?> getCarData(String url);
//    Map<String, ?> getCarData(Environment env, String html);
    String callURL(String textUrl);
}
