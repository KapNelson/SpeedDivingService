package ukr.nure.itm.inf.speeddivingservice.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ukr.nure.itm.inf.speeddivingservice.controller.ChartController;
import ukr.nure.itm.inf.speeddivingservice.model.chart.ChartLine;
import ukr.nure.itm.inf.speeddivingservice.service.ChartService;

import java.util.ArrayList;
import java.util.List;

import static ukr.nure.itm.inf.speeddivingservice.constant.SpeedDivingServiceConstant.CHART_PAGE;

@Controller
public class DefaultChartController implements ChartController {

    private final ChartService chartService;

    public DefaultChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @Override
    public String constructChartForActivityType(final Model model, final String activityType) {
        model.addAttribute("labels", chartService.getLabelsFromMinToMaxDate());

        final List<ChartLine> chartLines = chartService.getChartDataForActivityTypeName(activityType);
        final List<String> chartLineNames = new ArrayList<>();
        final List<List<Integer>> chartLineData = new ArrayList<>();
        for (ChartLine chartLine : chartLines) {
            chartLineNames.add(chartLine.getName());
            chartLineData.add(chartLine.getData());
        }


        model.addAttribute("lineNames", chartLineNames);
        model.addAttribute("data", chartLineData);

        return CHART_PAGE;
    }

    @Override
    public String constructChartForSportsman(final Model model, final String sportsmanName) {
        model.addAttribute("labels", chartService.getLabelsFromMinToMaxDate());

        final List<ChartLine> chartLines = chartService.getChartDataForSportsmanName(sportsmanName);
        final List<String> chartLineNames = new ArrayList<>();
        final List<List<Integer>> chartLineData = new ArrayList<>();
        for (ChartLine chartLine : chartLines) {
            chartLineNames.add(chartLine.getName());
            chartLineData.add(chartLine.getData());
        }

        model.addAttribute("lineNames", chartLineNames);
        model.addAttribute("data", chartLineData);

        return CHART_PAGE;
    }
}
