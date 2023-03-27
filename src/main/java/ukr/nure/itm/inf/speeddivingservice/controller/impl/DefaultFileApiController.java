package ukr.nure.itm.inf.speeddivingservice.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import ukr.nure.itm.inf.speeddivingservice.controller.FileApiController;
import ukr.nure.itm.inf.speeddivingservice.service.ActivityService;
import ukr.nure.itm.inf.speeddivingservice.util.CSVReaderUtil;

import java.util.List;

import static ukr.nure.itm.inf.speeddivingservice.constant.SpeedDivingServiceConstant.HOME_PAGE;

@Controller
public class DefaultFileApiController implements FileApiController {

    private final ActivityService activityService;

    public DefaultFileApiController(final ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public String uploadFile(final MultipartFile file) {
        List<String[]> csvLines = CSVReaderUtil.readAllLinesFromMultipartFile(file);
        activityService.parseAndSaveSportsmenActivities(csvLines);
        return HOME_PAGE;
    }

}
