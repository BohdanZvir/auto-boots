package hello.ria.service;

import hello.ria.model.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bohdan on 20.02.16.
 */
@Service
public class SearchStateConfigurerImpl implements SearchStateConfigurer {

    @Autowired
    private Environment env;

    @Override
    public Map<String, String> getDefaultOptions() {
        Map<String, String> options = new LinkedHashMap<>();
        for (RequestOptions prop : RequestOptions.values()) {
            String value = env.getProperty(prop.getKey());
            String name = env.getProperty(prop.getName());
            if (name==null || name.isEmpty()) {
                name = prop.toString().toLowerCase();
            }
            if (value.contains(",")) {
                String[] res = value.split(",");
                String superValue = res[0] + "&" + name + "=" + res[1];
                options.put(name,superValue);
            } else {
                options.put(name, value);
            }
        }
        return options;
    }
}
