package hello.ria.service;

import hello.Transfer;
import hello.ria.communicator.UrlBuilder;
import hello.ria.model.Payload;
import hello.ria.model.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by bohdan on 24.01.16.
 */
@Service
public class CarServiceImpl implements CarService, Transfer {

    private static List<Payload> CAR_MARKS = new LinkedList<>();
    private static Map<Integer, String> CAR_MODELS = new TreeMap<>();

    @Autowired
    private UrlBuilder urlBuilder;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DataExtractor extractor;

    @Override
    public Map<String, Object> getModelsMap() {
        Map<String, Object> model = new HashMap<>();
        model.put(PAYLOADS, getMarksCache());
        model.put(URL_PART, "/car/mark");
        return model;
    }

    public Map<Integer, String> getMarksMap() {
        Map<Integer, String> map = new TreeMap<>();
        for (Payload payload : getMarksCache()) {
            map.put(payload.getValue(), payload.getName());
        }
        return map;
    }

    @Override
    public Map<String, Object> getCategories() {
        Map<String, Object> model = new HashMap<>();
        String url = urlBuilder.getCategories();
        Payload[] categories = restTemplate.getForObject(url, Payload[].class);
        for (Payload category : categories) {
            category.setValue(null);
        }
        model.put(PAYLOADS, categories);
        model.put(URL_PART, "/car/mark");
        return model;
    }

    @Override
    public Map<String, Object> getCarModels(int markId) {
        Map<String, Object> model = new HashMap<>();
        model.put(PAYLOADS, getModelsCache(markId));
        model.put(URL_PART, "/car/mark/" + markId);
        return model;
    }

    @Override
    public Map<String, Object> getModelStatistic(String markId, String modelId, Map<String, String> options) {
        options.put(MARKS, markId);
        options.put(MODELS, modelId);
        options.put(CATEGORIES, DEFAULT_CATEGORY+ "");
        String url = urlBuilder.getAvarege(options);
        Statistic statistic = restTemplate.getForObject(url, Statistic.class);
        Map<String, Object> model = new HashMap<>();
        model.put(STATISTIC, statistic);
        String urlPart = "https://auto.ria.com/auto_" +
                getMarksMap().get(new Integer(markId)) + "_" +
                CAR_MODELS.get(new Integer(modelId)) + "_";
        String adsUrl = urlPart.toLowerCase();
        Map<Integer, ?> table = getStatTableMap(statistic, adsUrl);
        model.put(TABLE, table);
        model.put(CONTENT, adsUrl);
        return model;
    }

    @Override
    public Map<String, ?> getStatistic(int markId, int modelId) {
        String url = urlBuilder.getAvarege(DEFAULT_CATEGORY, markId, modelId);
        Statistic statistic = restTemplate.getForObject(url, Statistic.class);
        Map<String, Object> model = new HashMap<>();
        model.put(JSON_VALUE, statistic);
        return model;
    }

    @Override
    public Map<String, ?> getOptions() {
        String url = urlBuilder.getOptions(DEFAULT_CATEGORY);
        Payload[] payloads = restTemplate.getForObject(url, Payload[].class);
        Map<String, Object> model = new HashMap<>();
        model.put(PAYLOADS, payloads);
        return model;
    }

    private Map<Integer, ?> getStatTableMap(Statistic statistic, String urlPart) {
        Map<Integer, Map<String, String>> table = new TreeMap<>();
        for (Map.Entry<Integer, Integer> entry : statistic.buildMap().entrySet()) {
            String adUrl = urlPart + entry.getValue() + ".html";

            Map<String, String> carData = extractor.getCarData(adUrl);
            carData.put(URL, adUrl);
            table.put(entry.getKey(), carData);
        }
        return table;
    }

    private Payload[] getModelsCache(int markId) {
        String url = urlBuilder.getModels(DEFAULT_CATEGORY, markId);
        Payload[] payloads = restTemplate.getForObject(url, Payload[].class);
        for (Payload payload : payloads) {
            CAR_MODELS.put(payload.getValue(), payload.getName());
        }
        return payloads;
    }

    private List<Payload> getMarksCache() {
        if (CAR_MARKS.isEmpty()) {
            String url = urlBuilder.getMarks(DEFAULT_CATEGORY);
            CAR_MARKS.addAll(Arrays.asList(restTemplate.getForObject(url, Payload[].class)));
        }
        return CAR_MARKS;
    }

}
