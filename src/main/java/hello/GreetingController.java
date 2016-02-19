package hello;

import hello.ria.model.RequestOptions;
import hello.ria.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class GreetingController implements Transfer {

    private Map<String,String> options;
    @Autowired
    private CarService carService;
    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        options = new LinkedHashMap<>();
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
            System.out.println("!!!!!!!!!!!!!!" + name + " :: " + value);
        }
    }

    @RequestMapping("/")
    public String index(Model model) {
        return carMarks(model);
    }

    @RequestMapping("/categories")
    public String greeting(Model model) {
        model.addAllAttributes(carService.getCategories());
        return PAYLD_PAGE;
    }

    @RequestMapping("/car/mark")
    public String carMarks(Model model){
        model.addAllAttributes(carService.getModelsMap());
        return PAYLD_PAGE;
    }

    @RequestMapping("/car/mark/{id}")
    public String carModels(@PathVariable("id") int id, Model model){
        model.addAllAttributes(carService.getCarModels(id));
        return PAYLD_PAGE;
    }

    @RequestMapping("/car/mark/{mark_id}/{model_id}")
    public String carStat(@PathVariable("mark_id") int markId,
                          @PathVariable("model_id") int modelId,
                          Model model){
        model.addAllAttributes(carService.getModelStatistic(markId, modelId));
        return STAT_PAGE;
    }

    @RequestMapping("/car/options")
    public String carOptions(Model model) {
        model.addAllAttributes(carService.getOptions());
        return PAYLD_PAGE;
    }
    
    @RequestMapping("/car/table")
    public String carTable(@RequestParam(value="narrow", defaultValue= "true") boolean narrow,
                           @RequestParam(value="mark", defaultValue= "55") int mark,
                           @RequestParam(value="car_model"/*, defaultValue= "55"*/) int modelId,
                           Model model) {
        model.addAllAttributes(carService.getStatistic(mark, modelId));
        return PAYLD_PAGE;
    }
}
