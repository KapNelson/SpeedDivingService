package ukr.nure.itm.inf.speeddivingservice.service;

import ukr.nure.itm.inf.speeddivingservice.model.chart.ChartLine;

import java.util.List;

public interface ChartService {

    List<String> getLabelsFromMinToMaxDate();

    List<ChartLine> getChartDataForActivityTypeName(String activityTypeName);

    List<ChartLine> getChartDataForSportsmanName(String sportsmanName);
}
