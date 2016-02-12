package hello.ria.service;

import hello.Transfer;
import hello.ria.communicator.UrlBuilder;
import hello.ria.model.Payload;
import hello.ria.model.SelectorProps;
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
    public Map<String, Object> getModelStatistic(int markId, int modelId) {
        String url = urlBuilder.getAvarege(DEFAULT_CATEGORY, markId, modelId);
        Statistic statistic = restTemplate.getForObject(url, Statistic.class);
        Map<String, Object> model = new HashMap<>();
        model.put(STATISTIC, statistic);
        String urlPart = "https://auto.ria.com/auto_" +
                getMarksMap().get(markId) + "_" +
                CAR_MODELS.get(modelId) + "_";
        String adsUrl = urlPart.toLowerCase();
        Map<Integer, String> table = getStatTableMap(statistic, adsUrl);
        model.put(TABLE, table);
        model.put("content", getCarData(adsUrl));
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

    private Map<Integer, String> getStatTableMap(Statistic statistic, String urlPart) {
        Map<Integer, String> table = new TreeMap<Integer, String>();
        for (Map.Entry<Integer, Integer> entry : statistic.buildMap().entrySet()) {
            table.put(entry.getKey(), urlPart + entry.getValue() + ".html");
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

    public Map<SelectorProps, ?> getCarData(String url){
        return extractor.getCarData(url);
    }
}
