package ukr.nure.itm.inf.speeddivingservice.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ukr.nure.itm.inf.speeddivingservice.model.Activity;
import ukr.nure.itm.inf.speeddivingservice.model.ActivityType;
import ukr.nure.itm.inf.speeddivingservice.model.Sportsman;
import ukr.nure.itm.inf.speeddivingservice.repository.ActivityRepository;
import ukr.nure.itm.inf.speeddivingservice.repository.ActivityTypeRepository;
import ukr.nure.itm.inf.speeddivingservice.repository.SportsmanRepository;
import ukr.nure.itm.inf.speeddivingservice.service.ActivityService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ukr.nure.itm.inf.speeddivingservice.constant.SpeedDivingServiceConstant.InputFileColumnHeaderConstant.*;
import static ukr.nure.itm.inf.speeddivingservice.util.CSVReaderUtil.CSV_REGEX;

@Service
public class DefaultActivityService implements ActivityService {
    private final static Logger LOGGER = LogManager.getLogger(DefaultActivityService.class);
    private final ActivityRepository activityRepository;
    private final SportsmanRepository sportsmanRepository;
    private final ActivityTypeRepository activityTypeRepository;

    public DefaultActivityService(ActivityRepository activityRepository, SportsmanRepository sportsmanRepository, ActivityTypeRepository activityTypeRepository) {
        this.activityRepository = activityRepository;
        this.sportsmanRepository = sportsmanRepository;
        this.activityTypeRepository = activityTypeRepository;
    }

    @Override
    public void parseAndSaveSportsmenActivities(final List<String[]> listActivitiesOfString) {
        LOGGER.info("~~~~~~~Parsing has started~~~~~~~");

        final String[] columnHeaders = listActivitiesOfString.get(0)[0].split(CSV_REGEX);
        final int nameColumnHeaderIndex = getColumnHeaderIndex(columnHeaders, NAME);
        final int surnameColumnHeaderIndex = getColumnHeaderIndex(columnHeaders, SURNAME);
        final int typeColumnHeaderIndex = getColumnHeaderIndex(columnHeaders, TYPE);
        final int dateColumnHeaderIndex = getColumnHeaderIndex(columnHeaders, DATE);
        final int resultColumnHeaderIndex = getColumnHeaderIndex(columnHeaders, RESULT);
        listActivitiesOfString.remove(0);

        parseAndSaveSportsmen(listActivitiesOfString, nameColumnHeaderIndex, surnameColumnHeaderIndex);
        parseAndSaveActivityTypes(listActivitiesOfString, typeColumnHeaderIndex, nameColumnHeaderIndex, surnameColumnHeaderIndex);
        parseAndSaveActivities(listActivitiesOfString, dateColumnHeaderIndex, resultColumnHeaderIndex, typeColumnHeaderIndex, nameColumnHeaderIndex, surnameColumnHeaderIndex);

        LOGGER.info("~~~~~~~Parsing completed successfully~~~~~~~");
    }

    private void parseAndSaveSportsmen(final List<String[]> listActivitiesOfString, final int nameColumnHeaderIndex, final int surnameColumnHeaderIndex) {
        final List<Sportsman> sportsmen = new ArrayList<>();

        for (String[] activityOfString : listActivitiesOfString) {
            final String[] splitActivityOfString = activityOfString[0].split(CSV_REGEX);
            final String sportsmanName = String.format("%s %s", splitActivityOfString[nameColumnHeaderIndex], splitActivityOfString[surnameColumnHeaderIndex]);

            if (sportsmanName.isBlank()) {
                continue;
            }

            final Optional<Sportsman> sportsman = sportsmanRepository.findSportsmanBySportsmanName(sportsmanName);

            if (sportsman.isEmpty()) {
                final Sportsman newSportsman = new Sportsman();
                newSportsman.setSportsmanName(sportsmanName);
                newSportsman.setActivityTypes(new ArrayList<>());
                sportsmen.add(newSportsman);
            }
        }

        sportsmanRepository.saveAll(sportsmen);
    }

    private void parseAndSaveActivityTypes(final List<String[]> listActivitiesOfString, final int typeColumnHeaderIndex, final int nameColumnHeaderIndex, final int surnameColumnHeaderIndex) {
        final List<ActivityType> activityTypes = new ArrayList<>();

        for (String[] activityOfString : listActivitiesOfString) {
            final String[] splitActivityOfString = activityOfString[0].split(CSV_REGEX);
            final String activityTypeName = splitActivityOfString[typeColumnHeaderIndex];
            final String sportsmanName = String.format("%s %s", splitActivityOfString[nameColumnHeaderIndex], splitActivityOfString[surnameColumnHeaderIndex]);

            if (activityTypeName.isBlank()) {
                continue;
            }

            final Optional<Sportsman> sportsman = sportsmanRepository.findSportsmanBySportsmanName(sportsmanName);
            if (sportsman.isPresent()) {
                final Optional<ActivityType> activityType = activityTypeRepository.findActivityTypeByActivityTypeNameAndSportsman(activityTypeName, sportsman.get());

                if (activityType.isEmpty()) {
                    final ActivityType newActivityType = new ActivityType();
                    newActivityType.setActivityTypeId(String.format("%s_%s", activityTypeName, sportsman.get().getSportsmanName()));
                    newActivityType.setActivityTypeName(activityTypeName);
                    newActivityType.setSportsman(sportsman.get());
                    newActivityType.setActivities(new ArrayList<>());

                    activityTypes.add(newActivityType);
                }
            }
        }

        activityTypeRepository.saveAll(activityTypes);
    }

    private void parseAndSaveActivities(final List<String[]> listActivitiesOfString, final int dateColumnHeaderIndex, final int resultColumnHeaderIndex, final int typeColumnHeaderIndex, final int nameColumnHeaderIndex, final int surnameColumnHeaderIndex) {
        final List<Activity> activities = new ArrayList<>();

        for (String[] activityOfString : listActivitiesOfString) {
            final String[] splitActivityOfString = activityOfString[0].split(CSV_REGEX);
            final String activityTypeName = splitActivityOfString[typeColumnHeaderIndex];
            final String sportsmanName = String.format("%s %s", splitActivityOfString[nameColumnHeaderIndex], splitActivityOfString[surnameColumnHeaderIndex]);
            final LocalDate activityDate = convertStringToDate(splitActivityOfString[dateColumnHeaderIndex]);
            final LocalTime activityResult = convertStringToTime(splitActivityOfString[resultColumnHeaderIndex]);

            final Optional<Sportsman> sportsman = sportsmanRepository.findSportsmanBySportsmanName(sportsmanName);
            if (sportsman.isPresent()) {
                final Optional<ActivityType> activityType = activityTypeRepository.findActivityTypeByActivityTypeNameAndSportsman(activityTypeName, sportsman.get());

                if (activityType.isPresent()) {
                    final Activity activity = new Activity();
                    activity.setActivityType(activityType.get());
                    activity.setDate(activityDate);
                    activity.setResult(activityResult);
                    activities.add(activity);
                }
            }
        }

        activityRepository.saveAll(activities);
    }

    private int getColumnHeaderIndex(final String[] columnHeaders, final String columnHeader) {
        int columnHeaderIndex = 0;

        for (String header : columnHeaders) {
            if (columnHeader.equals(header)) {
                return columnHeaderIndex;
            }
            columnHeaderIndex++;
        }

        return columnHeaderIndex;
    }

    private LocalDate convertStringToDate(final String dateOfString) {
        if (dateOfString.isBlank()) {
            return null;
        }
        return LocalDate.parse(dateOfString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private LocalTime convertStringToTime(final String timeOfString) {
        if ("99:99:99".equals(timeOfString) || "88:88:88".equals(timeOfString) || timeOfString.isBlank()) {
            return null;
        }
        final String[] splitTimeOfString = timeOfString.split(":");
        try {
            return LocalTime.of(0, Integer.parseInt(splitTimeOfString[0]), Integer.parseInt(splitTimeOfString[1]), Integer.parseInt(splitTimeOfString[2]));
        } catch (Exception e) {
            return null;
        }
    }
}
