package ukr.nure.itm.inf.speeddivingservice.service.impl;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;
import ukr.nure.itm.inf.speeddivingservice.model.Activity;
import ukr.nure.itm.inf.speeddivingservice.model.ActivityType;
import ukr.nure.itm.inf.speeddivingservice.model.Sportsman;
import ukr.nure.itm.inf.speeddivingservice.model.chart.ChartLine;
import ukr.nure.itm.inf.speeddivingservice.repository.ActivityRepository;
import ukr.nure.itm.inf.speeddivingservice.repository.SportsmanRepository;
import ukr.nure.itm.inf.speeddivingservice.service.ChartService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class DefaultChartService implements ChartService {
    private final ActivityRepository activityRepository;
    private final SportsmanRepository sportsmanRepository;

    public DefaultChartService(ActivityRepository activityRepository, SportsmanRepository sportsmanRepository) {
        this.activityRepository = activityRepository;
        this.sportsmanRepository = sportsmanRepository;
    }

    @Override
    public List<String> getLabelsFromMinToMaxDate() {
        LocalDate maxDate = activityRepository.findFirstByOrderByDateDesc().getDate();
        LocalDate minDate = activityRepository.findFirstByOrderByDateAsc().getDate();
        int fullYears = maxDate.getYear() - minDate.getYear();
        int fullMonth = fullYears * 12 + maxDate.getMonthValue() + minDate.getMonthValue() - 2;

        List<String> labels = new ArrayList<>(fullMonth);
        final Calendar calendar = new GregorianCalendar(minDate.getYear(), minDate.getMonthValue() - 1, minDate.getDayOfMonth());
        DateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
        for (int i = 0; i <= fullMonth; i++) {
            labels.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.MONTH, 1);
        }

        return labels;
    }

    @Override
    public List<ChartLine> getChartDataForActivityTypeName(final String activityTypeName) {
        List<String> labels = getLabelsFromMinToMaxDate();
        LocalDate minDate = activityRepository.findFirstByOrderByDateAsc().getDate();
        List<ChartLine> data = new ArrayList<>();

        List<Sportsman> sportsmen = IterableUtils.toList(sportsmanRepository.findAll());
        for (Sportsman sportsman : sportsmen) {
            ChartLine chartLine = new ChartLine();
            chartLine.setName(sportsman.getSportsmanName());
            List<Integer> singleData = new ArrayList<>(labels.size());
            for (int j = 0; j < labels.size(); j++) {
                singleData.add(null);
            }

            List<Activity> activities = new ArrayList<>();
            for (ActivityType activityType : sportsman.getActivityTypes()) {
                if (activityTypeName.equals(activityType.getActivityTypeName())) {
                    activities.addAll(activityType.getActivities());
                }
            }

            for (Activity activity : activities) {
                int fullYears = activity.getDate().getYear() - minDate.getYear();
                int fullMonth = fullYears * 12 + activity.getDate().getMonthValue() - minDate.getMonthValue();

                if (activity.getResult() != null && activity.getResult().getSecond() != 0) {
                    singleData.set(fullMonth, activity.getResult().getMinute() * 60 + activity.getResult().getSecond());
                }
            }

            chartLine.setData(singleData);
            data.add(chartLine);
        }

        return data;
    }

    @Override
    public List<ChartLine> getChartDataForSportsmanName(final String sportsmanName) {
        List<String> labels = getLabelsFromMinToMaxDate();
        LocalDate minDate = activityRepository.findFirstByOrderByDateAsc().getDate();
        List<ChartLine> data = new ArrayList<>();
        Optional<Sportsman> sportsman = sportsmanRepository.findSportsmanBySportsmanName(sportsmanName);

        if (sportsman.isPresent()) {
            for (ActivityType activityType : sportsman.get().getActivityTypes()) {
                ChartLine chartLine = new ChartLine();
                chartLine.setName(activityType.getActivityTypeName());

                List<Integer> singleData = new ArrayList<>(labels.size());
                for (int j = 0; j < labels.size(); j++) {
                    singleData.add(null);
                }

                for (Activity activity : activityType.getActivities()) {
                    int fullYears = activity.getDate().getYear() - minDate.getYear();
                    int fullMonth = fullYears * 12 + activity.getDate().getMonthValue() - minDate.getMonthValue();

                    if (activity.getResult() != null && activity.getResult().getSecond() != 0) {
                        singleData.set(fullMonth, activity.getResult().getMinute() * 60 + activity.getResult().getSecond());
                    }
                }

                chartLine.setData(singleData);
                data.add(chartLine);
            }
        }

        return data;
    }
}
