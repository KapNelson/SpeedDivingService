package ukr.nure.itm.inf.speeddivingservice.util;

import com.fastdtw.dtw.FastDTW;
import com.fastdtw.timeseries.TimeSeries;
import com.fastdtw.timeseries.TimeSeriesBase;
import com.fastdtw.timeseries.TimeSeriesItem;
import com.fastdtw.timeseries.TimeSeriesPoint;
import com.fastdtw.util.EuclideanDistance;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

import java.util.ArrayList;
import java.util.List;

public class DTWDistance implements DistanceMeasure {
    public DTWDistance() {
    }

    @Override
    public double compute(double[] a, double[] b) {
        TimeSeries tsA = arrayToTimeSeries(a);
        TimeSeries tsB = arrayToTimeSeries(b);
        return FastDTW.compare(tsA, tsB, new EuclideanDistance()).getDistance();
    }

    private TimeSeriesBase arrayToTimeSeries(double[] arr) {
        List<TimeSeriesItem> timeSeriesItemList = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            timeSeriesItemList.add(new TimeSeriesItem(0, new TimeSeriesPoint(arr)));
        }
        return new TimeSeriesBase(timeSeriesItemList);
    }
}