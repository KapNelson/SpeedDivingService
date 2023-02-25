package ukr.nure.itm.inf.speeddivingservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

public interface CoreApiController {
    @RequestMapping(value = "/home", method = GET)
    String redirectToMainPage();
}
