package ukr.nure.itm.inf.speeddivingservice.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping(value = "/chart")
public interface ChartController {
    @RequestMapping(value = "/chartForActivityType", method = POST)
    String constructChartForActivityType(Model model, @RequestParam("activityType") String activityType);
    @RequestMapping(value = "/chartForSportsman", method = POST)
    String constructChartForSportsman(Model model, @RequestParam("sportsmanName") String sportsmanName);
}
