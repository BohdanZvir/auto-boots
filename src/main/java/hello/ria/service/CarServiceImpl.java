package hello.ria.service;

import hello.Transfer;
import hello.ria.communicator.UrlBuilder;
import hello.ria.model.Payload;
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

    @Autowired
    private UrlBuilder urlBuilder;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Map<String, Object> getModelsMap() {
        Map<String, Object> model = new HashMap<>();
        model.put(PAYLOADS, getCarMarks());
        model.put(URL_PART, "/car/mark");
        return model;
    }

    @Override
    public Map<String, Integer> getMarksMap() {
        Map<String, Integer> map = new TreeMap<>();
        for (Payload payload : getCarMarks()) {
            map.put(payload.getName(), payload.getValue());
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

    private List<Payload> getCarMarks() {
        if (CAR_MARKS.isEmpty()) {
            String url = urlBuilder.getMarks(DEFAULT_CATEGORY);
            CAR_MARKS.addAll(Arrays.asList(restTemplate.getForObject(url, Payload[].class)));
        }
        return CAR_MARKS;
    }
}
