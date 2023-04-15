package ukr.nure.itm.inf.speeddivingservice.service.impl;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.FuzzyKMeansClusterer;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.apache.commons.math3.ml.distance.EuclideanDistance;
import org.springframework.stereotype.Service;
import ukr.nure.itm.inf.speeddivingservice.model.Activity;
import ukr.nure.itm.inf.speeddivingservice.model.ActivityType;
import ukr.nure.itm.inf.speeddivingservice.model.Sportsman;
import ukr.nure.itm.inf.speeddivingservice.model.clustering.ClusteringData;
import ukr.nure.itm.inf.speeddivingservice.repository.SportsmanRepository;
import ukr.nure.itm.inf.speeddivingservice.service.ClusteringService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DefaultClusteringService implements ClusteringService {
    private final SportsmanRepository sportsmanRepository;

    public DefaultClusteringService(SportsmanRepository sportsmanRepository) {
        this.sportsmanRepository = sportsmanRepository;
    }

    @Override
    public List<ClusteringData> getClusteringDataForActivityTypeName(final String activityTypeName) {
        List<ClusteringData> clusteringDataList = new ArrayList<>();

        List<Sportsman> sportsmen = IterableUtils.toList(sportsmanRepository.findAll());
        for (Sportsman sportsman : sportsmen) {
            ClusteringData clusteringData = new ClusteringData();
            clusteringData.setName(sportsman.getSportsmanName());

            List<Double> singleData = new ArrayList<>();
            List<Activity> activities = new ArrayList<>();
            for (ActivityType activityType : sportsman.getActivityTypes()) {
                if (activityTypeName.equals(activityType.getActivityTypeName())) {
                    activities = activityType.getActivities();
                }
            }

            for (Activity activity : activities) {
                if (activity.getResult() != null && activity.getResult().getSecond() != 0) {
                    singleData.add((double) (activity.getResult().getMinute() * 60 + activity.getResult().getSecond()));
                }
            }

            if (!singleData.isEmpty()) {
                clusteringData.setData(singleData);
                clusteringDataList.add(clusteringData);
            }
        }

        return clusteringDataList;
    }

    @Override
    public List<ClusteringData> prepareToKMeansClustering(final List<ClusteringData> clusteringDataList) {
        int maxResultSize = 0;
        for (ClusteringData clusteringData : clusteringDataList) {
            maxResultSize = Math.max(clusteringData.getData().size(), maxResultSize);
        }

        List<ClusteringData> newClusteringDataList = new ArrayList<>(clusteringDataList.size());
        for (ClusteringData clusteringData : clusteringDataList) {
            double sum = 0;
            for (Double data : clusteringData.getData()) {
                sum += data;
            }

            double avg = sum / clusteringData.getData().size();

            List<Double> newDataList = new ArrayList<>(maxResultSize);
            for (int i = 0; i < maxResultSize; i++) {
                newDataList.add(avg);
            }
            for (int i = 0; i < clusteringData.getData().size(); i++) {
                newDataList.set(i, clusteringData.getData().get(i));
            }

            Collections.shuffle(newDataList);
            clusteringData.setData(newDataList);
            newClusteringDataList.add(clusteringData);
        }

        return newClusteringDataList;
    }

    @Override
    public List<CentroidCluster<ClusteringData>> kMeansClustering(final List<ClusteringData> clusteringDataList, int k) {
        KMeansPlusPlusClusterer<ClusteringData> clusters = new KMeansPlusPlusClusterer<>(k);
        return clusters.cluster(clusteringDataList);
    }

    @Override
    public List<CentroidCluster<ClusteringData>> cMeansClustering(final List<ClusteringData> clusteringDataList, int k) {
        FuzzyKMeansClusterer<ClusteringData> clusters = new FuzzyKMeansClusterer<>(k, 2.0, 100, new EuclideanDistance());
        return clusters.cluster(clusteringDataList);
    }

    @Override
    public List<ClusteringData> normalize(List<ClusteringData> dataList) {
        List<Double> minValues = new ArrayList<>();
        List<Double> maxValues = new ArrayList<>();
        int dataSize = dataList.get(0).getData().size();

        for (int i = 0; i < dataSize; i++) {
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            for (ClusteringData data : dataList) {
                double value = data.getData().get(i);
                if (value < min) {
                    min = value;
                }
                if (value > max) {
                    max = value;
                }
            }
            minValues.add(min);
            maxValues.add(max);
        }

        List<ClusteringData> normalizedDataList = new ArrayList<>();
        for (ClusteringData data : dataList) {
            List<Double> normalizedData = new ArrayList<>();
            for (int i = 0; i < dataSize; i++) {
                double value = data.getData().get(i);
                double min = minValues.get(i);
                double max = maxValues.get(i);
                double normalizedValue = (value - min) / (max - min);
                normalizedData.add(normalizedValue);
            }
            ClusteringData normalizedPoint = new ClusteringData(data.getName(), normalizedData);
            normalizedDataList.add(normalizedPoint);
        }
        return normalizedDataList;
    }
}
