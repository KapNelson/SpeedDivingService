package ukr.nure.itm.inf.speeddivingservice.converter;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import ukr.nure.itm.inf.speeddivingservice.model.chart.ChartLine;
import ukr.nure.itm.inf.speeddivingservice.model.clustering.ClusteringData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClusteringDataToChartLineConverter {
    private ClusteringDataToChartLineConverter() {
    }

    public static List<ChartLine> convert(final List<CentroidCluster<ClusteringData>> centroidClusters) {
        final List<ChartLine> chartLines = getCentroidLines(centroidClusters);
        chartLines.addAll(getClusterLines(centroidClusters));

        return chartLines;
    }

    private static List<ChartLine> getCentroidLines(final List<CentroidCluster<ClusteringData>> centroidClusters) {
        final List<ChartLine> centroidLines = new ArrayList<>();

        int name = 1;
        for (CentroidCluster<ClusteringData> centroidCluster : centroidClusters) {
            final ChartLine chartLine = new ChartLine();

            List<Double> data = new ArrayList<>();
            for (double d : centroidCluster.getCenter().getPoint()) {
                data.add(d);
            }

            chartLine.setData(data);
            chartLine.setName(String.format("Centroid %s", name));
            chartLine.setColor("rgb(255,0,0)");

            centroidLines.add(chartLine);
            name++;
        }

        return centroidLines;
    }

    private static List<ChartLine> getClusterLines(final List<CentroidCluster<ClusteringData>> centroidClusters) {
        final List<ChartLine> clusterLines = new ArrayList<>();

        Random rand = new Random();
        for (CentroidCluster<ClusteringData> centroidCluster : centroidClusters) {
            final String color = String.format("rgb(%s,%s,%s)", rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            for (ClusteringData clusteringData : centroidCluster.getPoints()) {
                final ChartLine chartLine = new ChartLine();

                chartLine.setData(clusteringData.getData());
                chartLine.setName(clusteringData.getName());
                chartLine.setColor(color);

                clusterLines.add(chartLine);
            }
        }

        return clusterLines;
    }
}
