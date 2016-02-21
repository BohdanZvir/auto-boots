package hello;

import hello.ria.service.CarService;
import hello.ria.service.SearchStateConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
public class GreetingController implements Transfer {

    private Map<String,String> options;
    @Autowired
    private CarService carService;
    @Autowired
    private SearchStateConfigurer stateConfigurer;

    @PostConstruct
    public void init() {
        options = stateConfigurer.getDefaultOptions();
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
    public String carStat(@PathVariable("mark_id") String markId,
                          @PathVariable("model_id") String modelId,
                          Model model){
        model.addAllAttributes(carService.getModelStatistic(markId, modelId, options));
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
