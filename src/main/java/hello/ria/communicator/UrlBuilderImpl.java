package hello.ria.communicator;

import hello.Transfer;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by bohdan on 04/01/2016.
 */
@ToString
@NoArgsConstructor
@Component
public class UrlBuilderImpl implements UrlBuilder, Transfer {

    private static final String AVERAGE = "/average?marka_id=%d&model_id=%d";

    public String getCategories() {
        return buildUrl(CATEGORIES);
    }

    @Override
    public String getMarks(int category) {
        return buildUrl(CATEGORIES, category, MARKS);
    }

    @Override
    public String getModels(int category, int mark) {
        return buildUrl(CATEGORIES, category, MARKS, mark, MODELS);
    }

    @Override
    public String getAvarege(int category, int mark, int model) {
        return BASE_URL + String.format(AVERAGE, mark, model);
    }

    @Override
    public String getOptions(int category) {
        return buildUrl(CATEGORIES, category, OPTIONS);
    }
//    http://api.auto.ria.com/average?marka_id=24&model_id=239&gear_id=1&gear_id=2&yers=2011&drive_id=2&damage=0&custom=0

    public String buildAverageArgs(Map<String, Integer> map) {
        StringBuilder buffer = new StringBuilder();
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            buffer.append("&")
                    .append(entry.getKey())
                    .append(entry.getValue());
        }
        return buffer.toString();
    }

    public String buildUrl(Object... args){
        StringBuilder buffer = new StringBuilder(BASE_URL);
        for (Object arg : args) {
            buffer.append("/").append(arg);
        }
        return buffer.toString();
    }
}
