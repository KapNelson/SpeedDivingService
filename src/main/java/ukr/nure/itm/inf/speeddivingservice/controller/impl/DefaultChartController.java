package ukr.nure.itm.inf.speeddivingservice.controller.impl;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ukr.nure.itm.inf.speeddivingservice.controller.ChartController;
import ukr.nure.itm.inf.speeddivingservice.converter.ClusteringDataToChartLineConverter;
import ukr.nure.itm.inf.speeddivingservice.model.chart.ChartLine;
import ukr.nure.itm.inf.speeddivingservice.model.clustering.ClusteringData;
import ukr.nure.itm.inf.speeddivingservice.model.clustering.ClusteringMethod;
import ukr.nure.itm.inf.speeddivingservice.service.ChartService;
import ukr.nure.itm.inf.speeddivingservice.service.ClusteringService;

import java.util.ArrayList;
import java.util.List;

import static ukr.nure.itm.inf.speeddivingservice.constant.SpeedDivingServiceConstant.CHART_PAGE;
import static ukr.nure.itm.inf.speeddivingservice.constant.SpeedDivingServiceConstant.CLUSTER_CHART_PAGE;

@Controller
public class DefaultChartController implements ChartController {
    private final static Logger LOGGER = LogManager.getLogger(DefaultChartController.class);
    private final ChartService chartService;
    private final ClusteringService clusteringService;

    public DefaultChartController(ChartService chartService, ClusteringService clusteringService) {
        this.chartService = chartService;
        this.clusteringService = clusteringService;
    }

    @Override
    public String constructChartForActivityType(final Model model, final String activityType) {
        model.addAttribute("labels", chartService.getLabelsFromMinToMaxDate());

        final List<ChartLine> chartLines = chartService.getChartDataForActivityTypeName(activityType);

        model.addAttribute("chartLines", chartLines);

        return CHART_PAGE;
    }

    @Override
    public String constructChartForSportsman(final Model model, final String sportsmanName) {
        model.addAttribute("labels", chartService.getLabelsFromMinToMaxDate());

        final List<ChartLine> chartLines = chartService.getChartDataForSportsmanName(sportsmanName);

        model.addAttribute("chartLines", chartLines);

        return CHART_PAGE;
    }

    @Override
    public String constructChartForActivityTypeClusters(final Model model, final String activityType, final ClusteringMethod clusteringMethod, final boolean isNormalize, final boolean isDTW) {
        List<ClusteringData> clusteringDataList = clusteringService.getClusteringDataForActivityTypeName(activityType);

        List<CentroidCluster<ClusteringData>> centroidClusters = null;
        switch (clusteringMethod) {
            case K_MEANS -> {
                centroidClusters = clusteringService.kMeansClustering(clusteringDataList, 8, isDTW, isNormalize);
            }
            case FUZZY_C_MEANS -> {
                centroidClusters = clusteringService.cMeansClustering(clusteringDataList, 8, isDTW, isNormalize);
            }
        }

        if (centroidClusters == null) {
            LOGGER.error("Clustering failed!");
            return CLUSTER_CHART_PAGE;
        }

        List<Integer> labels = new ArrayList<>();
        for (int i = 0; i < centroidClusters.get(0).getCenter().getPoint().length; i++) {
            labels.add(i);
        }

        model.addAttribute("labels", labels);
        model.addAttribute("chartLines", ClusteringDataToChartLineConverter.convert(centroidClusters));

        return CLUSTER_CHART_PAGE;
    }
}
