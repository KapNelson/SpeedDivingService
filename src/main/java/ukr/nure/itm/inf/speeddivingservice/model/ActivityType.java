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
@Table(name = "activity_types")
@Entity
public class ActivityType {
    @Id
    private String activityTypeId;
    private String activityTypeName;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sportsman_name")
    private Sportsman sportsman;
    @OneToMany(mappedBy = "activityType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities;
}
