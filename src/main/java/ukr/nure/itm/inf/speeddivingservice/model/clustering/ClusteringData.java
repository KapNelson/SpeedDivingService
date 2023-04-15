package ukr.nure.itm.inf.speeddivingservice.model.clustering;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.math3.ml.clustering.Clusterable;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClusteringData implements Clusterable {
    private String name;
    private List<Double> data;

    @Override
    public double[] getPoint() {
        double[] point = new double[data.size()];
        for (int i = 0; i < data.size(); i++) {
            point[i] = data.get(i);
        }
        return point;
    }
}