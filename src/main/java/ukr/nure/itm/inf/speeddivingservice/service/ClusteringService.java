package ukr.nure.itm.inf.speeddivingservice.service;

import ukr.nure.itm.inf.speeddivingservice.model.chart.ChartLine;

import java.util.List;

public interface ClusteringService {
    List<List<ChartLine>> kMeansClustering(List<ChartLine> chartLines, int k);
}
