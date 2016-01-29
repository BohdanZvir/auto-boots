package hello.ria.service;

import java.util.Map;

/**
 * Created by bohdan on 24.01.16.
 */
public interface CarService {

    Map<String, ?> getModelsMap();

    Map<String, ?> getCategories();

    Map<String, ?> getCarModels(int id);

    Map<String, ?> getModelStatistic(int markId, int modelId);

    Map<String, ?> getStatistic(int markId, int modelId);

    Map<String, ?> getOptions();
}
