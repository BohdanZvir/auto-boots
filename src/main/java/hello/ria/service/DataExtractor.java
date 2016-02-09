package hello.ria.service;

import hello.ria.model.SelectorProps;

import java.util.Map;

/**
 * Created by bohdan on 05.02.16.
 */
public interface DataExtractor {
    Map<SelectorProps,?> getCarData(String url);
    String callURL(String textUrl);
}
