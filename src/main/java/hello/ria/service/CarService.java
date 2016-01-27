package hello.ria.service;

import java.util.Map;

/**
 * Created by bohdan on 24.01.16.
 */
public interface CarService {

    Map<String, Object> getModelsMap();

    Map<String, Integer> getMarksMap();

    Map<String, Object> getCategories();

    Map<String, Object> getCarModels(int id);
}
