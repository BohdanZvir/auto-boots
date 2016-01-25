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

    private static List<Payload> payloads = new LinkedList<>();
    @Autowired
    private UrlBuilder urlBuilder;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Map<String, Object> getModelsMap() {
        Map<String, Object> model = new HashMap<>();
        model.put(PAYLOADS, getPayloads());
        model.put(URL_PART, "/car/mark");
        return model;
    }

    @Override
    public Map<String, Integer> getMarksMap() {
        if (payloads.isEmpty()) {
            getPayloads();
        }
        Map<String, Integer> map = new TreeMap<>();
        for (Payload payload : payloads) {
            map.put(payload.getName(), payload.getValue());
        }
        return map;
    }

    private List<Payload> getPayloads() {
        if (payloads.isEmpty()) {
            String url = urlBuilder.getMarks(DEFAULT_CATEGORY);
            payloads.addAll(Arrays.asList(restTemplate.getForObject(url, Payload[].class)));
        }
        return payloads;
    }
}
