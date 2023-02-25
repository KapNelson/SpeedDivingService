package ukr.nure.itm.inf.speeddivingservice.controller.impl;

import org.springframework.stereotype.Controller;
import ukr.nure.itm.inf.speeddivingservice.controller.CoreApiController;

import static ukr.nure.itm.inf.speeddivingservice.constant.SpeedDivingServiceConstant.HOME_PAGE;

@Controller
public class DefaultCoreApiController implements CoreApiController {
    @Override
    public String redirectToMainPage() {
        return HOME_PAGE;
    }
}
