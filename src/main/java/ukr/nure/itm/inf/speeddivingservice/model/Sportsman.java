package ukr.nure.itm.inf.speeddivingservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sportsmen")
public class Sportsman {
    @Id
    private String sportsmanName;
    @OneToMany(mappedBy = "sportsman", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivityType> activityTypes;
}
