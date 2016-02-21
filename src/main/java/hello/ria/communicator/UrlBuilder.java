package hello.ria.communicator;

import java.util.Map;

/**
 * Created by bohdan on 04/01/2016.
 */
public interface UrlBuilder {

    String getCategories();

    String getMarks(int category);

    String getModels(int category, int mark);

    String getAvarege(int category, int mark, int model);

    String getAvarege(Map<String, String> options);

    String getOptions(int category);
}
