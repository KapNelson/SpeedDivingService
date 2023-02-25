package ukr.nure.itm.inf.speeddivingservice.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import ukr.nure.itm.inf.speeddivingservice.controller.FileApiController;

import static ukr.nure.itm.inf.speeddivingservice.constant.SpeedDivingServiceConstant.HOME_PAGE;

@Controller
public class DefaultFileApiController implements FileApiController {

    @Override
    public String uploadFile(final MultipartFile file) {
        file.getName();

        return HOME_PAGE;
    }

}
