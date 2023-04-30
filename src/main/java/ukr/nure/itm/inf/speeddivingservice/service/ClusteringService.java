package ukr.nure.itm.inf.speeddivingservice.service;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import ukr.nure.itm.inf.speeddivingservice.model.clustering.ClusteringData;

import java.util.List;

public interface ClusteringService {
    List<ClusteringData> getClusteringDataForActivityTypeName(String activityTypeName);
    List<CentroidCluster<ClusteringData>> kMeansClustering(List<ClusteringData> clusteringDataList, int k, boolean isDTW, boolean isNormalize);
    List<CentroidCluster<ClusteringData>> cMeansClustering(List<ClusteringData> clusteringDataList, int k, boolean isDTW, boolean isNormalize);
    List<ClusteringData> normalize(List<ClusteringData> dataList);
}
