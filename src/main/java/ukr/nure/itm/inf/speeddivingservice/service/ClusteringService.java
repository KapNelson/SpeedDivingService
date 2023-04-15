package ukr.nure.itm.inf.speeddivingservice.service;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import ukr.nure.itm.inf.speeddivingservice.model.clustering.ClusteringData;

import java.util.List;

public interface ClusteringService {
    List<ClusteringData> getClusteringDataForActivityTypeName(String activityTypeName);
    List<ClusteringData> prepareToKMeansClustering(List<ClusteringData> clusteringDataList);
    List<CentroidCluster<ClusteringData>> kMeansClustering(List<ClusteringData> clusteringDataList, int k);
    List<CentroidCluster<ClusteringData>> cMeansClustering(List<ClusteringData> clusteringDataList, int k);
    List<ClusteringData> normalize(List<ClusteringData> dataList);
}
