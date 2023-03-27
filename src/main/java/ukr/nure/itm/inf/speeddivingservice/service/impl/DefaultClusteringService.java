package ukr.nure.itm.inf.speeddivingservice.service.impl;

import org.springframework.stereotype.Service;
import ukr.nure.itm.inf.speeddivingservice.model.chart.ChartLine;
import ukr.nure.itm.inf.speeddivingservice.service.ClusteringService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DefaultClusteringService implements ClusteringService {
    @Override
    public List<List<ChartLine>> kMeansClustering(List<ChartLine> chartLines, int k) {
        return null;
    }
}
