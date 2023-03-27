package ukr.nure.itm.inf.speeddivingservice.model.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChartLine {
    private String name;
    private List<Integer> data;
}
