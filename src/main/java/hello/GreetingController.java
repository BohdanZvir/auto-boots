package hello;

import hello.ria.communicator.UrlBuilder;
import hello.ria.model.Payload;
import hello.ria.model.Statistic;
import hello.ria.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class GreetingController implements Transfer {

    @Autowired
    private UrlBuilder urlBuilder;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CarService carService;

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
        String url = urlBuilder.getAvarege(DEFAULT_CATEGORY, markId, modelId);
        Statistic statistic = restTemplate.getForObject(url, Statistic.class);
        model.
                addAttribute(STATISTIC, statistic).
                addAttribute(TABLE, statistic.buildMap());
        return STAT_PAGE;
    }

    @RequestMapping("/car/options")
    public String carOptions(Model model) {
        model.addAttribute(PAYLOADS, restTemplate.getForObject(urlBuilder.getOptions(1), Payload[].class));
        return PAYLD_PAGE;
    }
    
    @RequestMapping("/car/table")
    public String carTable(@RequestParam(value="narrow", defaultValue= "true") boolean narrow,
                           @RequestParam(value="mark", defaultValue= "55") int mark,
                           @RequestParam(value="car_model"/*, defaultValue= "55"*/) int car_model,
                           Model model) {
        model.addAttribute(JSON_VALUE, restTemplate.getForObject(urlBuilder.getAvarege(1, mark, car_model), Statistic.class));
        return PAYLD_PAGE;
    }
}
